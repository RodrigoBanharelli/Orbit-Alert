package br.com.orbitalert.view;

import br.com.orbitalert.beans.OrbitAlertSistema;

// Ponto de entrada da aplicação.
public class Main {

    public static void main(String[] args) {
        OrbitAlertSistema model = new OrbitAlertSistema();
        Terminal view = new Terminal(model);
        view.exibirMenu();
    }
}
