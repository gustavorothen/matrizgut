package model;

import menu.ComponenteMenu;
import menu.Menu;
import menu.MenuItem;
import service.CriarCompromissos;
import service.IntCriadorDeCompromissos;

import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Agenda implements ComponenteMenu {
    protected String nome;
    protected Scanner scanner = new Scanner(System.in);
    protected IntCriadorDeCompromissos compromissoMenu = new CriarCompromissos();

    public Agenda(String nome) {
        this.nome = nome;
    }

    public void exibir() {
        iniciar();
    }

    public String getNome() {
        return nome;
    }

    public void iniciar() {
        Menu menu = new Menu(this.nome);

        menu.adicionar(new MenuItem("Adicionar Compromisso", () -> compromissoMenu.adicionarCompromisso(scanner)));
        menu.adicionar(new MenuItem("Alterar Compromisso", () -> compromissoMenu.alterarCompromisso(scanner)));
        menu.adicionar(new MenuItem("Apagar Compromisso", () -> {
            if (compromissoMenu instanceof CriarCompromissos cc) {
                cc.apagarCompromisso(scanner);
            }
        }));
        menu.adicionar(new MenuItem("Ver Tarefas de Hoje", this::verTarefasHoje));
        menu.adicionar(new MenuItem("Ver Tarefas do Mês", () -> verTarefasMes(scanner)));
        menu.adicionar(new MenuItem("Listar Todos os Compromissos", compromissoMenu::listarTodosCompromissos));
        try {
            menu.exibir();
        } catch (SairException e) {
            System.out.print(" Voltando a Menu Principal");
        }
    }

    protected void verTarefasHoje() {
        Calendar hoje = Calendar.getInstance();
        int dia = hoje.get(Calendar.DAY_OF_MONTH);
        int mes = hoje.get(Calendar.MONTH) + 1;
        compromissoMenu.verTarefasPorData(dia, mes);
    }

    protected void verTarefasMes(Scanner scanner) {
        System.out.print("Digite o mês (1 a 12): ");
        int mes = validarEntrada(1, 12);
        compromissoMenu.verTarefasPorMes(mes);
    }

    protected int validarEntrada(int min, int max) {
        while (true) {
            try {
                int valor = Integer.parseInt(scanner.nextLine());
                if (valor >= min && valor <= max) return valor;
                System.out.print("Valor fora do intervalo. Tente novamente: ");
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Digite um número: ");
            }
        }
    }


    static class SairException extends RuntimeException {}
}
