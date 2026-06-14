package com.schoolmanager.model;

import java.io.Serializable;

public class Frequencia implements Serializable {
    private static final long serialVersionUID = 1L;

    private String dataAula;
    private boolean presente;
    private String matriculaId;

    public Frequencia(String dataAula, boolean presente, String matriculaId) {
        this.dataAula = dataAula;
        this.presente = presente;
        this.matriculaId = matriculaId;
    }

    public String getDataAula() { return dataAula; }
    public void setDataAula(String dataAula) { this.dataAula = dataAula; }

    public boolean isPresente() { return presente; }
    public void setPresente(boolean presente) { this.presente = presente; }

    public String getMatriculaId() { return matriculaId; }
    public void setMatriculaId(String matriculaId) { this.matriculaId = matriculaId; }

    @Override
    public String toString() {
        return "Frequencia{" +
                "dataAula='" + dataAula + '\'' +
                ", presente=" + presente +
                ", matriculaId='" + matriculaId + '\'' +
                '}';
    }
}
