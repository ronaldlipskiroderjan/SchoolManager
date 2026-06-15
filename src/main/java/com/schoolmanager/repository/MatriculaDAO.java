package com.schoolmanager.repository;

import com.schoolmanager.model.Matricula;

import java.io.*;
import java.util.ArrayList;

public class MatriculaDAO {
    private final String ARQUIVO = "matriculas.dat";

    public void adicionar(Matricula matricula) {
        ArrayList<Matricula> lista = listarTodos();
        lista.add(matricula);
        salvarTodos(lista);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Matricula> listarTodos() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            return (ArrayList<Matricula>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler matriculas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void atualizar(String alunoId, String turmaId, Matricula atualizado) {
        ArrayList<Matricula> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            Matricula m = lista.get(i);
            if (m.getAlunoId().equals(alunoId) && m.getTurmaId().equals(turmaId)) {
                lista.set(i, atualizado);
                break;
            }
        }
        salvarTodos(lista);
    }

    public void remover(String alunoId, String turmaId) {
        ArrayList<Matricula> lista = listarTodos();
        lista.removeIf(m -> m.getAlunoId().equals(alunoId) && m.getTurmaId().equals(turmaId));
        salvarTodos(lista);
    }

    private void salvarTodos(ArrayList<Matricula> matriculas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(matriculas);
        } catch (IOException e) {
            System.out.println("Erro ao salvar matriculas: " + e.getMessage());
        }
    }
}
