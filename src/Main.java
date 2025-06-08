import model.Agenda;
import model.AgendaPessoal;
import model.AgendaTrabalho;

public class Main {
    public static void main(String[] args) {
        Agenda agenda = new AgendaTrabalho("== MATRIZ GUT DA AGENDA DE TRABALHO ==");
        Agenda agenda2 = new AgendaPessoal("== MATRIZ GUT DA AGENDA PESSOAL ==");
        do {
            agenda.iniciar();
            agenda2.iniciar();
        } while (agenda.getSairMenuAgenda() < 7 || agenda2.getSairMenuAgenda() < 7);

    }
}