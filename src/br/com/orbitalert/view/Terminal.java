package br.com.orbitalert.view;

import br.com.orbitalert.beans.OrbitAlertSistema;
import br.com.orbitalert.entities.Alerta;
import br.com.orbitalert.entities.TipoAlerta;
import java.util.List;
import java.util.Scanner;

// Camada de interface
public class Terminal {

    private OrbitAlertSistema model;
    private Scanner scanner;

    public Terminal(OrbitAlertSistema model) {
        this.model = model;
        this.scanner = new Scanner(System.in);
    }

    // Loop principal: exibe o menu até escolher sair (0).
    public void exibirMenu() {
        int opcao = 0;
        do {
            System.out.println("\n                                       ️  ");
            System.out.println("   ORBIT ALERT - MONITORAMENTO ORBITAL ");
            System.out.println("                                       ️    ");
            System.out.println("1. Listar TODOS os alertas");
            System.out.println("2. Listar apenas alertas CRÍTICOS");
            System.out.println("3. Buscar por município");
            System.out.println("4. Cadastrar novo alerta AMBIENTAL");
            System.out.println("5. Cadastrar novo alerta ESPACIAL");
            System.out.println("6. Atualizar risco de um alerta (por ID)");
            System.out.println("7. Remover alerta (por ID)");
            System.out.println("8. Ordenar alertas por risco (maior > menor)");
            System.out.println("0. Sair");
            System.out.println("_________________________");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                escolha(opcao);
            } catch (NumberFormatException e) {
                System.out.println("\nErro! Por favor, digite um número válido.");
            }
        } while (opcao != 0);
    }

    private void escolha(int opcao) {
        switch (opcao) {
            case 1: imprimirPainel(model.obterTodosAlertas(), "TODOS OS ALERTAS REGISTRADOS"); break;
            case 2: imprimirPainel(model.obterAlertasCriticos(), "  ALERTAS CRÍTICOS  "); break;
            case 3: telaBuscarPorMunicipio(); break;
            case 4: telaRegistrarAmbiental(); break;
            case 5: telaRegistrarEspacial(); break;
            case 6: telaAtualizarRisco(); break;
            case 7: telaRemover(); break;
            case 8: imprimirPainel(model.ordenarPorRiscoDescendente(), "ALERTAS ORDENADOS POR RISCO"); break;
            case 0: System.out.println("\nEncerrando conexão com a rede de satélites. Até logo!"); break;
            default: System.out.println("\n[ERRO] Opção inexistente no menu.");
        }
    }

    // Imprime lista de alertas — toString() e gerarNotificacao().
    private void imprimirPainel(List<Alerta> alertas, String titulo) {
        System.out.println("\n    " + titulo + "    ");
        if (alertas.isEmpty()) {
            System.out.println("Nenhum alerta encontrado.");
            return;
        }
        for (Alerta a : alertas) {
            System.out.println(a.toString());
            System.out.println("   " + a.gerarNotificacao());
        }
    }

    private void telaRegistrarAmbiental() {
        System.out.println("\n    NOVO ALERTA AMBIENTAL    ");
        System.out.print("Município afetado: ");
        String municipio = scanner.nextLine();
        System.out.println("Tipo: 1-Queimada | 2-Desmatamento | 3-Inundação | 4-Seca | 5-Terremoto | 6-Tsunami | 7-Chuva");
        System.out.print("Opção: ");
        try {
            int opTipo = Integer.parseInt(scanner.nextLine());
            if (opTipo < 1 || opTipo > 7) {
                System.out.println("Erro! Tipo inválido.");
                return;
            }
            TipoAlerta tipo = TipoAlerta.values()[opTipo - 1];
            System.out.print("Índice de risco (0 a 100): ");
            double risco = Double.parseDouble(scanner.nextLine());
            // O bean devolve true (deu certo) ou false (dados inválidos).
            boolean sucesso = model.registrarAlertaAmbiental(municipio, tipo, risco);
            if (sucesso) {
                System.out.println("Sucesso! Alerta ambiental registrado.");
            } else {
                System.out.println("Falha! Dados inválidos (verifique município e risco de 0 a 100).");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro! Digite valores numéricos válidos.");
        }
    }

    private void telaRegistrarEspacial() {
        System.out.println("\n    NOVO ALERTA ESPACIAL   ");
        System.out.print("Região/Município afetado: ");
        String municipio = scanner.nextLine();
        System.out.print("Satélite/Sonda de origem: ");
        String satelite = scanner.nextLine();
        try {
            System.out.print("Altitude (km): ");
            double altitude = Double.parseDouble(scanner.nextLine());
            System.out.print("Índice de risco (0 a 100): ");
            double risco = Double.parseDouble(scanner.nextLine());
            // O bean devolve true (deu certo) ou false (dados inválidos).
            boolean sucesso = model.registrarAlertaEspacial(municipio, satelite, altitude, risco);
            if (sucesso) {
                System.out.println("Sucesso! Alerta espacial registrado.");
            } else {
                System.out.println("Falha! Dados inválidos (verifique satélite, altitude e risco de 0 a 100).");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro! Digite valores numéricos válidos.");
        }
    }

    private void telaBuscarPorMunicipio() {
        System.out.println("\n    BUSCAR POR MUNICÍPIO    ");
        System.out.print("Digite parte do nome do município: ");
        String termo = scanner.nextLine();
        imprimirPainel(model.buscarPorMunicipio(termo), "RESULTADOS PARA: " + termo);
    }

    private void telaAtualizarRisco() {
        System.out.println("\n    ATUALIZAR RISCO   ");
        try {
            System.out.print("ID do alerta: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Novo índice de risco (0 a 100): ");
            double novoRisco = Double.parseDouble(scanner.nextLine());
            // O bean devolve true se atualizou, false se o ID não existe ou o risco é inválido.
            boolean sucesso = model.atualizarRisco(id, novoRisco);
            if (sucesso) {
                System.out.println("Sucesso! Risco atualizado.");
            } else {
                System.out.println("Falha! ID não encontrado ou risco fora de 0 a 100.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro! Valores numéricos inválidos.");
        }
    }

    private void telaRemover() {
        System.out.println("\n    REMOVER ALERTA    ");
        try {
            System.out.print("ID do alerta a remover: ");
            int id = Integer.parseInt(scanner.nextLine());
            boolean removeu = model.removerPorId(id);
            System.out.println(removeu ? "Sucesso! Alerta removido." : "Falha! ID não encontrado.");
        } catch (NumberFormatException e) {
            System.out.println("Erro! ID deve ser um número.");
        }
    }

}
