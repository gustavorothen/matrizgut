package service;

import java.time.LocalDate;

public class Compromisso implements Comparable<Compromisso> {
    private String descricao;
    private int diaSemana;
    private int gravidade;
    private int urgencia;
    private int tendencia;
    private int prioridade;
    private int diaMes;
    private int mes;
    private LocalDate dataCriacao;

    public Compromisso(String descricao, int diaSemana, int gravidade, int urgencia, int tendencia, int diaMes, int mes) {
        this.descricao = descricao;
        setDiaSemana(diaSemana);
        setGravidade(gravidade);
        setUrgencia(urgencia);
        setTendencia(tendencia);
        setDiaMes(diaMes);
        setMes(mes);
        this.dataCriacao = LocalDate.now();
        calcularPrioridade();
    }

    public String getDescricao() { return descricao; }
    public int getDiaSemana() { return diaSemana; }
    public int getGravidade() { return gravidade; }
    public int getUrgencia() { return urgencia; }
    public int getTendencia() { return tendencia; }
    public int getDiaMes() { return diaMes; }
    //public int getFrequencia()
    public int getMes() { return mes; }
    public LocalDate getDataCriacao() { return dataCriacao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }

    private int validarValor(int valor, int min, int max) {
        return Math.max(min, Math.min(valor, max));
    }

    public void setDiaSemana(int diaSemana) {
        this.diaSemana = validarValor(diaSemana, 1, 5);
    }

    public void setGravidade(int gravidade) {
        this.gravidade = validarValor(gravidade, 1, 5);
        calcularPrioridade();
    }

    public void setUrgencia(int urgencia) {
        this.urgencia = validarValor(urgencia, 1, 5);
        calcularPrioridade();
    }

    public void setTendencia(int tendencia) {
        this.tendencia = validarValor(tendencia, 1, 5);
        calcularPrioridade();
    }

    public void setDiaMes(int diaMes) {
        this.diaMes = validarValor(diaMes, 1, 31);
    }

    public void setMes(int mes) {
        this.mes = validarValor(mes, 1, 12);
    }

    private void calcularPrioridade() {
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