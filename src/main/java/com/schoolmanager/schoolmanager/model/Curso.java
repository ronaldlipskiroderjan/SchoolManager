package com.schoolmanager.schoolmanager.model;

import java.io.Serializable;

public class Curso implements Serializable {
    private String nome;
    private int duracaoSemestres;
    private String coordenadorId;

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