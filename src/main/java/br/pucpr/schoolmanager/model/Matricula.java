package br.pucpr.schoolmanager.model;

public class Matricula {
    private String statusRegistro;
    private double valorTotal;
    private String dataEfetivacao;

    public Matricula(String statusRegistro, double valorTotal, String dataEfetivacao) {
        this.statusRegistro = statusRegistro;
        setValorTotal(valorTotal);
        this.dataEfetivacao = dataEfetivacao;
    }

    public String getStatusRegistro() {
        return statusRegistro;
    }

    public void setStatusRegistro(String statusRegistro) {
        this.statusRegistro = statusRegistro;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        if (valorTotal <= 0) {
            throw new IllegalArgumentException("O valor total deve ser maior que zero!.");
        }
        this.valorTotal = valorTotal;
    }

    public String getDataEfetivacao() {
        return dataEfetivacao;
    }

    public void setDataEfetivacao(String dataEfetivacao) {
        this.dataEfetivacao = dataEfetivacao;
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "statusRegistro='" + statusRegistro + '\'' +
                ", valorTotal=" + valorTotal +
                ", dataEfetivacao='" + dataEfetivacao + '\'' +
                '}';
    }
}
