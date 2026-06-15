package com.schoolmanager.repository;

import com.schoolmanager.model.Turma;

import java.io.*;
import java.util.ArrayList;

public class TurmaDAO {
    private final String ARQUIVO = "turmas.dat";

    public void adicionar(Turma turma) {
        ArrayList<Turma> lista = listarTodos();
        lista.add(turma);
        salvarTodos(lista);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Turma> listarTodos() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            return (ArrayList<Turma>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler turmas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void atualizar(String codigo, Turma atualizado) {
        ArrayList<Turma> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigo().equals(codigo)) {
                lista.set(i, atualizado);
                break;
            }
        }
        salvarTodos(lista);
    }

    public void remover(String codigo) {
        ArrayList<Turma> lista = listarTodos();
        lista.removeIf(t -> t.getCodigo().equals(codigo));
        salvarTodos(lista);
    }

    private void salvarTodos(ArrayList<Turma> turmas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(turmas);
        } catch (IOException e) {
            System.out.println("Erro ao salvar turmas: " + e.getMessage());
        }
    }
}
