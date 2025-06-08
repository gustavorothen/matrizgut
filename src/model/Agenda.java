package model;

import service.CriarCompromissos;
import service.IntCriadorDeCompromissos;

import java.util.*;

public abstract class Agenda {
    protected String nome;
    protected int sairMenuAgenda = 0;
    protected Scanner scanner = new Scanner(System.in);
    protected IntCriadorDeCompromissos compromissoMenu = new CriarCompromissos();

    public Agenda(String nome) {
        this.nome = nome;
    }

    public int getSairMenuAgenda() {
        return sairMenuAgenda;
    }

    public void iniciar() {
        compromissoMenu.atualizarPrioridades();

        while (true) {
            exibirMenu();
            int opcao = validarEntrada(1, 7);

            switch (opcao) {
                case 1 -> compromissoMenu.adicionarCompromisso(scanner);
                case 2 -> compromissoMenu.alterarCompromisso(scanner);
                case 3 -> verTarefasHoje();
                case 4 -> verTarefasMes(scanner);
                case 5 -> compromissoMenu.listarTodosCompromissos();
                case 6 -> {
                    System.out.println("Trocando...");
                    return;
                }
                case 7 -> {
                    this.sairMenuAgenda = 7;
                    return;
                }
            }
        }
    }

    protected void exibirMenu() {
        System.out.println(this.nome);
        System.out.println("1. Adicionar service.Compromisso");
        System.out.println("2. Alterar service.Compromisso");
        System.out.println("3. Ver Tarefas de Hoje");
        System.out.println("4. Ver Tarefas do Mês");
        System.out.println("5. Listar Todos os Compromissos");
        System.out.println("6. Trocar de Agenda");
        System.out.println("7. Sair");
        System.out.print("Escolha uma opção: ");
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
                int valor = scanner.nextInt();
                scanner.nextLine();
                if (valor >= min && valor <= max) return valor;
                System.out.print("Valor fora do intervalo. Tente novamente: ");
            } catch (InputMismatchException e) {
                System.out.print("Entrada inválida. Digite um número: ");
                scanner.nextLine();
            }
        }
    }
}