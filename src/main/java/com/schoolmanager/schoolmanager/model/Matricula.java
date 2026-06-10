package com.schoolmanager.schoolmanager.model;
import java.io.Serializable;

public class Matricula implements Serializable {
    private static final long serialVersionUID = 1L;

    private String dataMatricula;
    private String alunoId;
    private String turmaId;

    public Matricula(String dataMatricula, String alunoId, String turmaId) {
        this.dataMatricula = dataMatricula;
        this.alunoId = alunoId;
        this.turmaId = turmaId;
    }

    public String getDataMatricula() { return dataMatricula; }
    public void setDataMatricula(String dataMatricula) { this.dataMatricula = dataMatricula; }

    public String getAlunoId() { return alunoId; }
    public void setAlunoId(String alunoId) { this.alunoId = alunoId; }

    public String getTurmaId() { return turmaId; }
    public void setTurmaId(String turmaId) { this.turmaId = turmaId; }

    @Override
    public String toString() {
        return "Matrícula Data: " + dataMatricula + " | Aluno (CPF): " + alunoId + " | Turma: " + turmaId;
    }
}