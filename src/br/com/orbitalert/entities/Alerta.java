package br.com.orbitalert.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Classe abstrata base — Herança e Polimorfismo. Implementa Notificavel.
public abstract class Alerta implements Notificavel {

    // Atributos encapsulados (privados).
    private int id;
    private String municipio;
    private double indiceRisco;
    private LocalDateTime dataRegistro;

    public Alerta(int id, String municipio, double indiceRisco) {
        this.id = id;
        this.municipio = municipio;
        this.indiceRisco = indiceRisco;
        this.dataRegistro = LocalDateTime.now();
    }

    // Métodos abstratos: cada subclasse define seu próprio comportamento (Polimorfismo).
    public abstract String getCategoria();
    public abstract String getNivelSeveridade();

    public String getDataFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dataRegistro.format(formatter);
    }

    // getter e setter
    public int getId() { return id; }
    public void setId(int id) {
        this.id = id; }

    public String getMunicipio() {
        return municipio; }

    public void setMunicipio(String municipio) {
        this.municipio = municipio; }

    public double getIndiceRisco() {
        return indiceRisco; }

    public void setIndiceRisco(double indiceRisco) {
        this.indiceRisco = indiceRisco; }

    public LocalDateTime getDataRegistro() {
        return dataRegistro; }


    @Override
    public String toString() {
        return String.format("#%d [%s] (%s) Município: %s | Risco: %.1f%% | Severidade: %s",
                this.id,
                getDataFormatada(),
                getCategoria(),
                this.municipio,
                this.indiceRisco,
                getNivelSeveridade());
    }
}
