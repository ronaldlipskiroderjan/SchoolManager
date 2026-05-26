package br.pucpr.schoolmanager.model;

public class Mensalidade {
    private String mesReferencia;
    private double valorCobrado;
    private String dataVencimento;

    public Mensalidade(String mesReferencia, double valorCobrado, String dataVencimento) {
        this.mesReferencia = mesReferencia;
        setValorCobrado(valorCobrado);
        this.dataVencimento = dataVencimento;
    }

    public String getMesReferencia() {
        return mesReferencia;
    }

    public void setMesReferencia(String mesReferencia) {
        this.mesReferencia = mesReferencia;
    }

    public double getValorCobrado() {
        return valorCobrado;
    }

    public void setValorCobrado(double valorCobrado) {
        if(valorCobrado <= 0){
            throw new IllegalArgumentException("O valor deve ser maior que zero!");
        }
        this.valorCobrado = valorCobrado;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @Override
    public String toString() {
        return "Mensalidade{" +
                "mesReferencia='" + mesReferencia + '\'' +
                ", valorCobrado=" + valorCobrado +
                ", dataVencimento='" + dataVencimento + '\'' +
                '}';
    }
}
