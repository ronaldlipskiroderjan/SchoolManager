package br.pucpr.schoolmanager.model;

import java.io.Serializable;

public class Curso implements Serializable {
    private String nome; // [cite: 97]
    private int duracaoSemestres; // [cite: 98]
    private String coordenadorId; // [cite: 99]

    public Curso(String nome, int duracaoSemestres, String coordenadorId) {
        this.nome = nome;
        this.duracaoSemestres = duracaoSemestres;
        this.coordenadorId = coordenadorId;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getDuracaoSemestres() { return duracaoSemestres; }
    public void setDuracaoSemestres(int duracaoSemestres) { this.duracaoSemestres = duracaoSemestres; }

    public String getCoordenadorId() { return coordenadorId; }
    public void setCoordenadorId(String coordenadorId) { this.coordenadorId = coordenadorId; }
}