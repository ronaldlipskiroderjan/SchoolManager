package com.schoolmanager.model;

import java.io.Serializable;

public class Avaliacao implements Serializable {
    private static final long serialVersionUID = 1L;

    private double valorNota;
    private String tipo;
    private String matriculaId;

    public Avaliacao(double valorNota, String tipo, String matriculaId) {
        this.valorNota = valorNota;
        this.tipo = tipo;
        this.matriculaId = matriculaId;
    }

    public double getValorNota() { return valorNota; }
    public void setValorNota(double valorNota) { this.valorNota = valorNota; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMatriculaId() { return matriculaId; }
    public void setMatriculaId(String matriculaId) { this.matriculaId = matriculaId; }

    @Override
    public String toString() {
        return "Avaliacao{" +
                "valorNota=" + valorNota +
                ", tipo='" + tipo + '\'' +
                ", matriculaId='" + matriculaId + '\'' +
                '}';
    }
}