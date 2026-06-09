package br.com.orbitalert.entities;

// Interface: contrato que toda classe notificável deve cumprir (Abstração).
public interface Notificavel {

    String gerarNotificacao();

    // Método default: implementação padrão pode ser sobrescrita.
    default int calcularPrioridade(double indiceRisco) {
        if (indiceRisco >= 80.0) return 1;
        if (indiceRisco >= 50.0) return 2;
        return 3;
    }
}
