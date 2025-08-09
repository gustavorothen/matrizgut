package menu;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu implements ComponenteMenu {
    private final String nome;
    private final List<ComponenteMenu> itens = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public Menu(String nome) {
        this.nome = nome;
    }

    public void adicionar(ComponenteMenu item) {
        itens.add(item);
    }

    @Override
    public void exibir() {
        while (true) {
            System.out.println("\n=== " + nome + " ===");
            for (int i = 0; i < itens.size(); i++) {
                System.out.println((i + 1) + ". " + getNome(itens.get(i)));
            }
            System.out.println((itens.size() + 1) + ". Sair");
            System.out.print("Escolha uma opção: ");

            int escolha = validarEntrada(1, itens.size() + 1);

            if (escolha == itens.size() + 1) break;

            itens.get(escolha - 1).exibir();
        }
    }

    private int validarEntrada(int min, int max) {
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

    private String getNome(ComponenteMenu item) {
        if (item instanceof MenuItem mi) {
            return mi.getNome();
        } else if (item instanceof model.Agenda ag) {
            return ag.getNome(); // nome é protected em Agenda
        } else if (item instanceof Menu m) {
            return m.nome;
        } else {
            return item.getClass().getSimpleName();
        }
    }
}
