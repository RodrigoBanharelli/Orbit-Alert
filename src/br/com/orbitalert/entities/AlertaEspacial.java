package br.com.orbitalert.entities;

// Subclasse de Alerta — representa alertas captados por satélites/sondas.
public class AlertaEspacial extends Alerta {

    private String origemSatelite;
    private double altitudeKm;

    public AlertaEspacial(int id, String municipio, String origemSatelite, double altitudeKm, double indiceRisco) {
        super(id, municipio, indiceRisco);
        this.origemSatelite = origemSatelite;
        this.altitudeKm = altitudeKm;
    }

    // --- GETTERS E SETTERS ---
    public String getOrigemSatelite() { return origemSatelite; }
    public void setOrigemSatelite(String origemSatelite) { this.origemSatelite = origemSatelite; }
    public double getAltitudeKm() { return altitudeKm; }
    public void setAltitudeKm(double altitudeKm) { this.altitudeKm = altitudeKm; }

    // --- OVERRIDES ---
    @Override
    public String getCategoria() {
        return "ESPACIAL";
    }

    // Regras de severidade mais rigorosas que as do alerta ambiental.
    @Override
    public String getNivelSeveridade() {
        if (getIndiceRisco() >= 70.0) return "CRÍTICO";
        if (getIndiceRisco() >= 40.0) return "ALTO";
        if (getIndiceRisco() >= 15.0) return "MODERADO";
        return "BAIXO";
    }

    @Override
    public String gerarNotificacao() {
        return String.format("🚀 [%s] Anomalia espacial detectada por %s a %.1f km — Risco %.1f%% sobre %s",
                getNivelSeveridade(), this.origemSatelite, this.altitudeKm, getIndiceRisco(), getMunicipio());
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Satélite: %s | Altitude: %.1f km", origemSatelite, altitudeKm);
    }
}
