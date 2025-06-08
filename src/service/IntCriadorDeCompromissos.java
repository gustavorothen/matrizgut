package service;

import java.util.Scanner;

public interface IntCriadorDeCompromissos {
    void adicionarCompromisso(Scanner scanner);
    void alterarCompromisso(Scanner scanner);
    void listarTodosCompromissos();
    void verTarefasPorData(int dia, int mes);
    void verTarefasPorMes(int mes);
    void atualizarPrioridades();
}