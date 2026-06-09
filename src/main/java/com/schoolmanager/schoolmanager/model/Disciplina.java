package com.schoolmanager.schoolmanager.model;

import java.io.Serializable;

public class Disciplina implements Serializable {
    private static final long serialVersionUID = 1L;

    private String codigo;
    private String nome;
    private int cargaHoraria;
    private String ementa;
    private String professorId;

    public Disciplina(String codigo, String nome, int cargaHoraria, String ementa, String professorId) {
        this.codigo = codigo;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.ementa = ementa;
        this.professorId = professorId;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    public String getEmenta() { return ementa; }
    public void setEmenta(String ementa) { this.ementa = ementa; }

    public String getProfessorId() { return professorId; }
    public void setProfessorId(String professorId) { this.professorId = professorId; }

    @Override
    public String toString() {
        return "Disciplina{" +
                "codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", cargaHoraria=" + cargaHoraria +
                ", ementa='" + ementa + '\'' +
                ", professorId='" + professorId + '\'' +
                '}';
    }
}
