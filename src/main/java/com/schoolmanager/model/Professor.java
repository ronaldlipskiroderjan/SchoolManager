package com.schoolmanager.model;

import java.io.Serializable;

public class Professor implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String cpf;
    private String email;
    private String titulacao;
    private String especialidade;

    public Professor(String nome, String cpf, String email, String titulacao, String especialidade) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.titulacao = titulacao;
        this.especialidade = especialidade;
    }

    public String getNome() {
        return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTitulacao() { return titulacao; }
    public void setTitulacao(String titulacao) { this.titulacao = titulacao; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    @Override
    public String toString() {
        return "Professor{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", titulacao='" + titulacao + '\'' +
                ", especialidade='" + especialidade + '\'' +
                '}';
    }
}
