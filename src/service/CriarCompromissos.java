package service;

import java.util.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CriarCompromissos implements IntCriadorDeCompromissos {
    private List<Compromisso> compromissos = new ArrayList<>();

    @Override
    public void adicionarCompromisso(Scanner scanner) {
        System.out.print("\nQuantos compromissos deseja cadastrar? ");
        int quant = validarEntrada(scanner, 1, 100); // limite arbitrário para segurança

        for (int i = 0; i < quant; i++) {
            System.out.print("Descrição do service.Compromisso: ");
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
            System.out.println("1 - Muito baixo | 2 - Baixo | 3 - Médio | 4 - Alto | 5 - Muito alto :");
            int gravidade = validarEntrada(scanner, 1, 5);

            System.out.print("Urgência (1 a 5) ");
            System.out.println("1 - Sem urgência | ... | 5 - Muito urgente :");
            int urgencia = validarEntrada(scanner, 1, 5);

            System.out.print("Tendência (1 a 5) ");
            System.out.println("1 - Não tende a se agravar | ... | 5 - Agravar muito rapidamente :");
            int tendencia = validarEntrada(scanner, 1, 5);

            Compromisso c = new Compromisso(descricao, diaSemana, gravidade, urgencia, tendencia, diaMes, mes);
            compromissos.add(c);
            Collections.sort(compromissos);

            System.out.println("service.Compromisso adicionado com sucesso!");
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

        Compromisso c = compromissos.get(indice);
        System.out.println("Alterando: " + c);
        System.out.println("Deixe em branco para manter o valor atual.");

        System.out.print("Nova descrição (" + c.getDescricao() + "): ");
        String descricao = scanner.nextLine();
        if (!descricao.isEmpty()) c.setDescricao(descricao);

        System.out.print("Novo dia da semana (1 a 5, atual: " + c.getDiaSemana() + "): ");
        String diaSemanaInput = scanner.nextLine();
        if (!diaSemanaInput.isEmpty()) c.setDiaSemana(Integer.parseInt(diaSemanaInput));

        System.out.print("Novo mês (1 a 12, atual: " + c.getMes() + "): ");
        String mesInput = scanner.nextLine();
        int mes = mesInput.isEmpty() ? c.getMes() : Integer.parseInt(mesInput);

        int maxDia = getMaxDiasMes(mes);
        System.out.printf("Novo dia do mês (1 a %d, atual: %d): ", maxDia, c.getDiaMes());
        String diaMesInput = scanner.nextLine();
        int diaMes = diaMesInput.isEmpty() ? c.getDiaMes() : Integer.parseInt(diaMesInput);

        System.out.print("Nova gravidade (1 a 5, atual: " + c.getGravidade() + "): ");
        String gravidadeInput = scanner.nextLine();
        if (!gravidadeInput.isEmpty()) c.setGravidade(Integer.parseInt(gravidadeInput));

        System.out.print("Nova urgência (1 a 5, atual: " + c.getUrgencia() + "): ");
        String urgenciaInput = scanner.nextLine();
        if (!urgenciaInput.isEmpty()) c.setUrgencia(Integer.parseInt(urgenciaInput));

        System.out.print("Nova tendência (1 a 5, atual: " + c.getTendencia() + "): ");
        String tendenciaInput = scanner.nextLine();
        if (!tendenciaInput.isEmpty()) c.setTendencia(Integer.parseInt(tendenciaInput));

        c.setMes(mes);
        c.setDiaMes(diaMes);

        Collections.sort(compromissos);
        System.out.println("service.Compromisso alterado com sucesso!");
    }

    @Override
    public void listarTodosCompromissos() {
        if (compromissos.isEmpty()) {
            System.out.println("Nenhum compromisso cadastrado.");
            return;
        }

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

    @Override
    public void atualizarPrioridades() {
        LocalDate hoje = LocalDate.now();

        for (Compromisso c : compromissos) {
            long mesesPassados = ChronoUnit.MONTHS.between(
                    LocalDate.of(c.getDataCriacao().getYear(), c.getDataCriacao().getMonth(), 1),
                    LocalDate.of(hoje.getYear(), hoje.getMonth(), 1)
            );

            if (mesesPassados > 0) {
                c.setGravidade(Math.min(5, c.getGravidade() + (int) mesesPassados));
                c.setUrgencia(Math.min(5, c.getUrgencia() + (int) mesesPassados));
                c.setTendencia(Math.min(5, c.getTendencia() + (int) mesesPassados));
            }
        }

        Collections.sort(compromissos);
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