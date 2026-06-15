package com.schoolmanager.repository;

import com.schoolmanager.model.Aluno;

import java.io.*;
import java.util.ArrayList;

public class AlunoDAO {
    private final String ARQUIVO = "alunos.dat";

    public void adicionar(Aluno aluno) {
        ArrayList<Aluno> lista = listarTodos();
        lista.add(aluno);
        salvarTodos(lista);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Aluno> listarTodos() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            return (ArrayList<Aluno>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler alunos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void atualizar(String matricula, Aluno atualizado) {
        ArrayList<Aluno> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getMatricula().equals(matricula)) {
                lista.set(i, atualizado);
                break;
            }
        }
        salvarTodos(lista);
    }

    public void remover(String matricula) {
        ArrayList<Aluno> lista = listarTodos();
        lista.removeIf(a -> a.getMatricula().equals(matricula));
        salvarTodos(lista);
    }

    private void salvarTodos(ArrayList<Aluno> alunos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(alunos);
        } catch (IOException e) {
            System.out.println("Erro ao salvar alunos: " + e.getMessage());
        }
    }
}
