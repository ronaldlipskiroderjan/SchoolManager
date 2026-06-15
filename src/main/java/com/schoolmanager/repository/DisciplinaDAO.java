package com.schoolmanager.repository;

import com.schoolmanager.model.Disciplina;

import java.io.*;
import java.util.ArrayList;

public class DisciplinaDAO {
    private final String ARQUIVO = "disciplinas.dat";

    public void adicionar(Disciplina disciplina) {
        ArrayList<Disciplina> lista = listarTodos();
        lista.add(disciplina);
        salvarTodos(lista);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Disciplina> listarTodos() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            return (ArrayList<Disciplina>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler disciplinas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void atualizar(String codigo, Disciplina atualizado) {
        ArrayList<Disciplina> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigo().equals(codigo)) {
                lista.set(i, atualizado);
                break;
            }
        }
        salvarTodos(lista);
    }

    public void remover(String codigo) {
        ArrayList<Disciplina> lista = listarTodos();
        lista.removeIf(d -> d.getCodigo().equals(codigo));
        salvarTodos(lista);
    }

    private void salvarTodos(ArrayList<Disciplina> disciplinas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(disciplinas);
        } catch (IOException e) {
            System.out.println("Erro ao salvar disciplinas: " + e.getMessage());
        }
    }
}
