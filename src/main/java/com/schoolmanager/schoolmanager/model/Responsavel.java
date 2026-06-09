package com.schoolmanager.schoolmanager.model;

import java.io.Serializable;

public class Responsavel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String alunoMatricula;

    public Responsavel(String nome, String cpf, String email, String telefone, String alunoMatricula) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.alunoMatricula = alunoMatricula;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getAlunoMatricula() { return alunoMatricula; }
    public void setAlunoMatricula(String alunoMatricula) { this.alunoMatricula = alunoMatricula; }

    @Override
    public String toString() {
        return "Responsavel{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", alunoMatricula='" + alunoMatricula + '\'' +
                '}';
    }
}
