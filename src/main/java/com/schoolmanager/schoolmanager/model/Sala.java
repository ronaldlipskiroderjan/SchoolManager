package com.schoolmanager.schoolmanager.model;
import java.io.Serializable;

public class Sala implements Serializable {
    private static final long serialVersionUID = 1L;

    private String numero;
    private String bloco;
    private int capacidade;

    public Sala(String numero, String bloco, int capacidade) {
        this.numero = numero;
        this.bloco = bloco;
        this.capacidade = capacidade;
    }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getBloco() { return bloco; }
    public void setBloco(String bloco) { this.bloco = bloco; }

    public int getCapacidade() { return capacidade; }
    public void setCapacidade(int capacidade) { this.capacidade = capacidade; }

    @Override
    public String toString() {
        return "Sala: " + numero + " - Bloco: " + bloco + " (Capacidade: " + capacidade + ")";
    }
}