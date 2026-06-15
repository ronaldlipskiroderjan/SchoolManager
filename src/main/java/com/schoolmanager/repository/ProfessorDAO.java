package com.schoolmanager.repository;

import com.schoolmanager.model.Professor;

import java.io.*;
import java.util.ArrayList;

public class ProfessorDAO {
    private final String ARQUIVO = "professores.dat";

    public void adicionar(Professor professor) {
        ArrayList<Professor> lista = listarTodos();
        lista.add(professor);
        salvarTodos(lista);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Professor> listarTodos() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            return (ArrayList<Professor>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler professores: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void atualizar(String cpf, Professor atualizado) {
        ArrayList<Professor> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCpf().equals(cpf)) {
                lista.set(i, atualizado);
                break;
            }
        }
        salvarTodos(lista);
    }

    public void remover(String cpf) {
        ArrayList<Professor> lista = listarTodos();
        lista.removeIf(p -> p.getCpf().equals(cpf));
        salvarTodos(lista);
    }

    private void salvarTodos(ArrayList<Professor> professores) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(professores);
        } catch (IOException e) {
            System.out.println("Erro ao salvar professores: " + e.getMessage());
        }
    }
}
