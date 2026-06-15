package com.schoolmanager.repository;

import com.schoolmanager.model.Curso;

import java.io.*;
import java.util.ArrayList;

public class CursoDAO {
    private final String ARQUIVO = "cursos.dat";

    public void adicionar(Curso curso) {
        ArrayList<Curso> lista = listarTodos();
        lista.add(curso);
        salvarTodos(lista);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Curso> listarTodos() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            return (ArrayList<Curso>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler cursos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void atualizar(String nome, Curso atualizado) {
        ArrayList<Curso> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNome().equals(nome)) {
                lista.set(i, atualizado);
                break;
            }
        }
        salvarTodos(lista);
    }

    public void remover(String nome) {
        ArrayList<Curso> lista = listarTodos();
        lista.removeIf(c -> c.getNome().equals(nome));
        salvarTodos(lista);
    }

    private void salvarTodos(ArrayList<Curso> cursos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(cursos);
        } catch (IOException e) {
            System.out.println("Erro ao salvar cursos: " + e.getMessage());
        }
    }
}
