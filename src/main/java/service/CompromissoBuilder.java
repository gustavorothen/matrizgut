package service;

import java.time.LocalDate;

public class CompromissoBuilder {
    private int id;
    private String descricao;
    private int diaSemana;
    private int gravidade;
    private int urgencia;
    private int tendencia;
    private int diaMes;
    private int mes;

    public CompromissoBuilder builderId(int id) {
        this.id = id;
        return this;
    }

    public CompromissoBuilder builderDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public CompromissoBuilder builderDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
        return this;
    }

    public CompromissoBuilder builderGravidade(int gravidade) {
        this.gravidade = gravidade;
        return this;
    }

    public CompromissoBuilder builderUrgencia(int urgencia) {
        this.urgencia = urgencia;
        return this;
    }

    public CompromissoBuilder builderTendencia(int tendencia) {
        this.tendencia = tendencia;
        return this;
    }

    public CompromissoBuilder builderDiaMes(int diaMes) {
        this.diaMes = diaMes;
        return this;
    }

    public CompromissoBuilder builderMes(int mes) {
        this.mes = mes;
        return this;
    }

    public Compromisso build() {
        Compromisso compromisso = new Compromisso(id, descricao, diaSemana, gravidade, urgencia, tendencia, diaMes, mes);
        return compromisso;
    }
}

