package br.pucpr.schoolmanager.model;

import java.io.Serializable;

public class Turma implements Serializable {
    private String codigo; // [cite: 103]
    private String semestre; // [cite: 104]
    private String disciplinaId; // [cite: 105]

    public Turma(String codigo, String semestre, String disciplinaId) {
        this.codigo = codigo;
        this.semestre = semestre;
        this.disciplinaId = disciplinaId;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getSemestre() { return semestre; }
    public void setSemestre(String semestre) { this.semestre = semestre; }

    public String getDisciplinaId() { return disciplinaId; }
    public void setDisciplinaId(String disciplinaId) { this.disciplinaId = disciplinaId; }
}