package service;

import java.time.LocalDate;

public class Compromisso implements Comparable<Compromisso> {
    private int id;
    private String descricao;
    private int diaSemana;
    private int gravidade;
    private int urgencia;
    private int tendencia;
    private int prioridade;
    private int diaMes;
    private int mes;

    public Compromisso(int id, String descricao, int diaSemana, int gravidade, int urgencia, int tendencia, int diaMes, int mes) {
        this.id = id;
        this.descricao = descricao;
        this.diaSemana = diaSemana;
        this.gravidade = gravidade;
        this.urgencia = urgencia;
        this.tendencia = tendencia;
        this.diaMes = diaMes;
        this.mes = mes;
        calcularPrioridade();
    }

    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public int getDiaSemana() { return diaSemana; }
    public int getGravidade() { return gravidade; }
    public int getUrgencia() { return urgencia; }
    public int getTendencia() { return tendencia; }
    public int getDiaMes() { return diaMes; }
    public int getMes() { return mes; }


    public void calcularPrioridade() {
        this.prioridade = this.gravidade * this.urgencia * this.tendencia;
    }

    @Override
    public int compareTo(Compromisso outro) {
        return Integer.compare(outro.prioridade, this.prioridade); // Decrescente
    }

    @Override
    public String toString() {
        String diaSemanaTexto = switch (diaSemana) {
            case 1 -> "Segunda-feira";
            case 2 -> "Terça-feira";
            case 3 -> "Quarta-feira";
            case 4 -> "Quinta-feira";
            case 5 -> "Sexta-feira";
            default -> "";
        };

        return String.format("%s | %s | %d/%d | Gravidade: %d | Urgência: %d | Tendência: %d | Prioridade: %d",
                descricao, diaSemanaTexto, diaMes, mes, gravidade, urgencia, tendencia, prioridade);
    }
}