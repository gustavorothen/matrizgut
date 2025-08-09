package menu;

public class MenuItem implements ComponenteMenu {
    private final String nome;
    private final Runnable acao;

    public MenuItem(String nome, Runnable acao) {
        this.nome = nome;
        this.acao = acao;
    }

    @Override
    public void exibir() {
        System.out.println("\nExecutando: " + nome);
        acao.run();
    }

    public String getNome() {
        return nome;
    }
}
