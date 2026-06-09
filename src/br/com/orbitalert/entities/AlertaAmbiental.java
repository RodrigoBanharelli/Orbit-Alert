package br.com.orbitalert.entities;

// Subclasse de Alerta — representa alertas ambientais na Terra.
public class AlertaAmbiental extends Alerta {

    private TipoAlerta tipo;

    public AlertaAmbiental(int id, String municipio, TipoAlerta tipo, double indiceRisco) {
        super(id, municipio, indiceRisco);
        this.tipo = tipo;
    }

    // --- GETTERS E SETTERS ---
    public TipoAlerta getTipo() { return tipo; }
    public void setTipo(TipoAlerta tipo) { this.tipo = tipo; }

    // --- OVERRIDES ---
    @Override
    public String getCategoria() {
        return "AMBIENTAL";
    }

    // Regras de severidade próprias desta subclasse.
    @Override
    public String getNivelSeveridade() {
        if (getIndiceRisco() >= 80.0) return "CRÍTICO";
        if (getIndiceRisco() >= 50.0) return "ALTO";
        if (getIndiceRisco() >= 20.0) return "MODERADO";
        return "BAIXO";
    }

    @Override
    public String gerarNotificacao() {
        return String.format(" %s %s detectado(a) em %s — Risco %.1f%%",
                getNivelSeveridade(), this.tipo, getMunicipio(), getIndiceRisco());
    }

    @Override
    public String toString() {
        return super.toString() + " | Tipo: " + this.tipo;
    }
}
