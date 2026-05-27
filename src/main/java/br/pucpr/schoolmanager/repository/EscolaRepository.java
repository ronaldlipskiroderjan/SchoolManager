package br.pucpr.schoolmanager.repository;

import br.pucpr.schoolmanager.model.Funcionario;
import br.pucpr.schoolmanager.model.Turma;

import java.io.*;
import java.util.ArrayList;

public class EscolaRepository {
    public void salvarFuncionario(Funcionario funcionario){
        try {
            FileWriter fw = new FileWriter("funcionarios.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(funcionario.toString());
            bw.newLine();
            bw.close();

        } catch (IOException e) {
            System.out.println("Erro ao tentar salvar o arquivo: " + e.getMessage());
        }


    }

    public void salvarTurma(Turma turma){
        try {
            FileWriter fw = new FileWriter("turmas.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(turma.toString());
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("Erro ao tentar salvar o arquivo: " + e.getMessage());
        }
    }

    public ArrayList<Funcionario> listarFuncionarios(){
        ArrayList<Funcionario> listaFuncionarios = new ArrayList<>();

        try {
            FileReader fr = new FileReader("funcionarios.txt");
            BufferedReader br = new BufferedReader(fr);

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                Funcionario f = new Funcionario(dados[0], Double.parseDouble(dados[1]), dados[2]);

                listaFuncionarios.add(f);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return listaFuncionarios;
    }

    public ArrayList<Turma> listarTurmas(){
        ArrayList<Turma> listaTurmas = new ArrayList<>();

        try {
            FileReader fr = new FileReader("turmas.txt");
            BufferedReader br = new BufferedReader(fr);

            String linha;
            while ((linha = br.readLine()) != null){
                String[] dados = linha.split(";");
                Turma t = new Turma(dados[0], Integer.parseInt(dados[1]), dados[2]);

                listaTurmas.add(t);
            }
        } catch (IOException e){
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return listaTurmas;
    }

    public void atualizarFuncionarios(ArrayList<Funcionario> listaFuncionarios){
        try{
            FileWriter fw = new FileWriter("funcionarios.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (Funcionario f : listaFuncionarios){
                bw.write(f.toString());
                bw.newLine();
            }
            bw.close();

        } catch (IOException e){
            System.out.println("Erro ao atualizar o arquivo: " + e.getMessage());
        }
    }
    public void atualizarTurmas(ArrayList<Turma> listaTurmas){
        try{
            FileWriter fw = new FileWriter("turmas.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (Turma t : listaTurmas){
                bw.write(t.toString());
                bw.newLine();
            }
            bw.close();

        } catch (IOException e){
            System.out.println("Erro ao atualizar o arquivo: " + e.getMessage());
        }
    }
}
