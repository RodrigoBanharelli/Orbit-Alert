package br.com.orbitalert.beans;

import br.com.orbitalert.entities.Alerta;
import br.com.orbitalert.entities.AlertaAmbiental;
import br.com.orbitalert.entities.AlertaEspacial;
import br.com.orbitalert.entities.TipoAlerta;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Camada de gerência (bean) — controla o CRUD e as regras de negócio dos alertas.
public class OrbitAlertSistema {

    private List<Alerta> baseDeAlertas;
    private int proximoId;

    public OrbitAlertSistema() {
        this.baseDeAlertas = new ArrayList<>();
        this.proximoId = 1;
        carregarDadosIniciais();
    }

    // exemplos de alertas ambientais e espaciais.
    private void carregarDadosIniciais() {
        baseDeAlertas.add(new AlertaAmbiental(proximoId++, "Ribeirão Preto - SP", TipoAlerta.QUEIMADA, 88.5));
        baseDeAlertas.add(new AlertaAmbiental(proximoId++, "Manaus - AM", TipoAlerta.DESMATAMENTO, 95.0));
        baseDeAlertas.add(new AlertaAmbiental(proximoId++, "Porto Alegre - RS", TipoAlerta.INUNDACAO, 45.3));
        baseDeAlertas.add(new AlertaEspacial(proximoId++, "Brasília - DF", "Amazonia-1", 700.0, 72.0));
    }

    // CREATE — registra novo alerta ambiental. Retorna true se deu certo, false se os dados forem inválidos.
    public boolean registrarAlertaAmbiental(String municipio, TipoAlerta tipo, double risco) {
        // Se os campos comuns forem inválidos ou o tipo estiver nulo, cancela.
        if (!camposValidos(municipio, risco) || tipo == null) {
            return false;
        }
        baseDeAlertas.add(new AlertaAmbiental(proximoId++, municipio, tipo, risco));
        return true;
    }

    // CREATE — registra novo alerta espacial. Retorna true se deu certo, false se inválido.
    public boolean registrarAlertaEspacial(String municipio, String satelite, double altitude, double risco) {
        // Valida campos comuns + satélite preenchido + altitude positiva.
        if (!camposValidos(municipio, risco) || satelite == null || satelite.trim().isEmpty() || altitude <= 0) {
            return false;
        }
        baseDeAlertas.add(new AlertaEspacial(proximoId++, municipio, satelite, altitude, risco));
        return true;
    }

    // Método auxiliar: devolve true se município e risco forem válidos.
    private boolean camposValidos(String municipio, double risco) {
        if (municipio == null || municipio.trim().isEmpty()) {
            return false;
        }
        if (risco < 0.0 || risco > 100.0) {
            return false;
        }
        return true;
    }

    // READ — todos os alertas.
    public List<Alerta> obterTodosAlertas() {
        return baseDeAlertas;
    }

    // READ — filtra apenas alertas CRÍTICOS (Polimorfismo via getNivelSeveridade).
    public List<Alerta> obterAlertasCriticos() {
        List<Alerta> criticos = new ArrayList<>();
        for (Alerta a : baseDeAlertas) {
            if (a.getNivelSeveridade().equals("CRÍTICO")) {
                criticos.add(a);
            }
        }
        return criticos;
    }

    // READ — busca por município (parcial, case-insensitive).
    public List<Alerta> buscarPorMunicipio(String termo) {
        List<Alerta> resultados = new ArrayList<>();
        if (termo == null || termo.trim().isEmpty()) return resultados;
        String termoLower = termo.toLowerCase();
        for (Alerta a : baseDeAlertas) {
            if (a.getMunicipio().toLowerCase().contains(termoLower)) {
                resultados.add(a);
            }
        }
        return resultados;
    }

    public Alerta buscarPorId(int id) {
        for (Alerta a : baseDeAlertas) {
            if (a.getId() == id) return a;
        }
        return null;
    }

    // UPDATE — altera o índice de risco de um alerta pelo ID. Retorna true se conseguiu.
    public boolean atualizarRisco(int id, double novoRisco) {
        Alerta alvo = buscarPorId(id);
        // Se não achou o ID ou o risco está fora de 0..100, falha.
        if (alvo == null || novoRisco < 0.0 || novoRisco > 100.0) {
            return false;
        }
        alvo.setIndiceRisco(novoRisco);
        return true;
    }

    // DELETE — remove alerta por ID.
    public boolean removerPorId(int id) {
        Alerta alvo = buscarPorId(id);
        if (alvo == null) return false;
        return baseDeAlertas.remove(alvo);
    }

    // Ordena os alertas do maior risco para o menor.
    public List<Alerta> ordenarPorRiscoDescendente() {
        List<Alerta> copia = new ArrayList<>(baseDeAlertas);
        copia.sort(Comparator.comparingDouble(Alerta::getIndiceRisco).reversed());
        return copia;
    }
}
