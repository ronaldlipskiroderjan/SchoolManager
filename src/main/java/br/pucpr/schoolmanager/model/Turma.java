package br.pucpr.schoolmanager.model;

public class Turma {
    private String codigo;
    private int semestre;
    private String data;

    public Turma(String codigo, int semestre, String data) {
        this.codigo = codigo;
        this.semestre = semestre;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString(){
        return codigo + ";" + semestre + ";" + data;
    }


}
