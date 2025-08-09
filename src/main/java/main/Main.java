package main;

import model.Agenda;
import menu.Menu;
import menu.MenuItem;
import model.Agendas;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(CompromissoGUI::new);
    }

    public static void iniciar() {
        new Thread(() -> {
            Agenda agendaTrabalho = new Agendas("MATRIZ GUT");

            Menu menu = new Menu("Menu Principal");
            menu.adicionar(new MenuItem(agendaTrabalho.getNome(), agendaTrabalho::exibir));
            menu.adicionar(new MenuItem("Ir para Interface Gráfica", () -> {
                SwingUtilities.invokeLater(CompromissoGUI::new);
            }));

            menu.exibir();
        }).start();
    }
}
