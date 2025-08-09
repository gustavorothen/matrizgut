package service;

import sql.CompromissoDAO;
import java.time.LocalDate;
import java.util.*;

public class CriarCompromissos implements IntCriadorDeCompromissos {
    private List<Compromisso> compromissos;
    private CompromissoDAO dao = new CompromissoDAO();

    public CriarCompromissos() {
        compromissos = dao.listarTodos();
        Collections.sort(compromissos);
    }

    @Override
    public void adicionarCompromisso(Scanner scanner) {
        System.out.print("\nQuantos compromissos deseja cadastrar? ");
        int quant = validarEntrada(scanner, 1, 20);

        for (int i = 0; i < quant; i++) {
            System.out.print("Descrição do Compromisso: ");
            String descricao = scanner.nextLine();

            System.out.print("Dia da semana (1 a 5) ");
            System.out.println("1 - Segunda | 2 - Terça | 3 - Quarta | 4 - Quinta | 5 - Sexta :");
            int diaSemana = validarEntrada(scanner, 1, 5);

            System.out.print("Mês (1 a 12): ");
            int mes = validarEntrada(scanner, 1, 12);

            int maxDia = getMaxDiasMes(mes);
            System.out.printf("Dia do mês (1 a %d): ", maxDia);
            int diaMes = validarEntrada(scanner, 1, maxDia);

            System.out.print("Gravidade (1 a 5) ");
            int gravidade = validarEntrada(scanner, 1, 5);

            System.out.print("Urgência (1 a 5) ");
            int urgencia = validarEntrada(scanner, 1, 5);

            System.out.print("Tendência (1 a 5) ");
            int tendencia = validarEntrada(scanner, 1, 5);

            Compromisso c = new CompromissoBuilder()
                    .builderDescricao(descricao)
                    .builderDiaSemana(diaSemana)
                    .builderGravidade(gravidade)
                    .builderUrgencia(urgencia)
                    .builderTendencia(tendencia)
                    .builderDiaMes(diaMes)
                    .builderMes(mes)
                    .build();

            dao.salvar(c);
            compromissos = dao.listarTodos(); // Atualiza com IDs
            Collections.sort(compromissos);

            System.out.println("Compromisso adicionado com sucesso!");
        }
    }

    @Override
    public void alterarCompromisso(Scanner scanner) {
        if (compromissos.isEmpty()) {
            System.out.println("Nenhum compromisso cadastrado.");
            return;
        }

        listarTodosCompromissos();
        System.out.print("Digite o índice do compromisso a alterar (0 a " + (compromissos.size() - 1) + "): ");
        int indice = validarEntrada(scanner, 0, compromissos.size() - 1);

        Compromisso atual = compromissos.get(indice);
        System.out.println("Alterando: " + atual);
        System.out.println("Deixe em branco ou digite 0 para manter o valor atual.");

        System.out.print("Nova descrição (" + atual.getDescricao() + "): ");
        String descricao = scanner.nextLine();
        if (descricao.isEmpty()) descricao = atual.getDescricao();

        System.out.print("Novo dia da semana (1 a 5, atual: " + atual.getDiaSemana() + "): ");
        int diaSemana = validarEntrada(scanner, 0, 5);
        if (diaSemana == 0) diaSemana = atual.getDiaSemana();

        System.out.print("Novo mês (1 a 12, atual: " + atual.getMes() + "): ");
        int mes = validarEntrada(scanner, 0, 12);
        if (mes == 0) mes = atual.getMes();

        int maxDia = getMaxDiasMes(mes);
        System.out.printf("Novo dia do mês (1 a %d, atual: %d): ", maxDia, atual.getDiaMes());
        int diaMes = validarEntrada(scanner, 0, maxDia);
        if (diaMes == 0) diaMes = atual.getDiaMes();

        System.out.print("Nova gravidade (1 a 5, atual: " + atual.getGravidade() + "): ");
        int gravidade = validarEntrada(scanner, 0, 5);
        if (gravidade == 0) gravidade = atual.getGravidade();

        System.out.print("Nova urgência (1 a 5, atual: " + atual.getUrgencia() + "): ");
        int urgencia = validarEntrada(scanner, 0, 5);
        if (urgencia == 0) urgencia = atual.getUrgencia();

        System.out.print("Nova tendência (1 a 5, atual: " + atual.getTendencia() + "): ");
        int tendencia = validarEntrada(scanner, 0, 5);
        if (tendencia == 0) tendencia = atual.getTendencia();

        Compromisso novo = new CompromissoBuilder()
                .builderId(atual.getId()) // mantém o ID original
                .builderDescricao(descricao)
                .builderDiaSemana(diaSemana)
                .builderGravidade(gravidade)
                .builderUrgencia(urgencia)
                .builderTendencia(tendencia)
                .builderDiaMes(diaMes)
                .builderMes(mes)
                .build();

        dao.alterar(novo);
        compromissos.set(indice, novo);
        Collections.sort(compromissos);

        System.out.println("Compromisso alterado com sucesso!");
    }

    public void apagarCompromisso(Scanner scanner) {
        if (compromissos.isEmpty()) {
            System.out.println("Nenhum compromisso cadastrado.");
            return;
        }

        listarTodosCompromissos();
        System.out.print("Digite o índice do compromisso a apagar (0 a " + (compromissos.size() - 1) + "): ");
        int indice = validarEntrada(scanner, 0, compromissos.size() - 1);

        Compromisso c = compromissos.remove(indice);
        dao.apagar(c.getId());

        System.out.println("Compromisso apagado com sucesso!");
    }

    @Override
    public void listarTodosCompromissos() {
        if (compromissos.isEmpty()) {
            System.out.println("Nenhum compromisso cadastrado.");
            return;
        }

        Collections.sort(compromissos);

        System.out.println("\nLista de Compromissos:");
        for (int i = 0; i < compromissos.size(); i++) {
            System.out.println("[" + i + "] " + compromissos.get(i));
        }
    }

    @Override
    public void verTarefasPorData(int dia, int mes) {
        boolean encontrou = false;
        for (Compromisso c : compromissos) {
            if (c.getDiaMes() == dia && c.getMes() == mes) {
                System.out.println(c);
                encontrou = true;
            }
        }
        if (!encontrou) System.out.println("Nenhuma tarefa para " + dia + "/" + mes + ".");
    }

    @Override
    public void verTarefasPorMes(int mes) {
        boolean encontrou = false;
        for (Compromisso c : compromissos) {
            if (c.getMes() == mes) {
                System.out.println(c);
                encontrou = true;
            }
        }
        if (!encontrou) System.out.println("Nenhuma tarefa para o mês " + mes + ".");
    }

    private int getMaxDiasMes(int mes) {
        return switch (mes) {
            case 2 -> 28;
            case 4, 6, 9, 11 -> 30;
            default -> 31;
        };
    }

    private int validarEntrada(Scanner scanner, int min, int max) {
        while (true) {
            try {
                int valor = scanner.nextInt();
                scanner.nextLine();
                if (valor >= min && valor <= max) return valor;
                System.out.print("Entrada fora do intervalo. Tente novamente: ");
            } catch (InputMismatchException e) {
                System.out.print("Entrada inválida! Digite um número: ");
                scanner.nextLine();
            }
        }
    }
}
