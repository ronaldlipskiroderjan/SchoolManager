package com.schoolmanager.schoolmanager.repository;

import com.schoolmanager.schoolmanager.model.Professor;

import java.io.*;
import java.util.ArrayList;

public class ProfessorDAO {
    private final String ARQUIVO = "professores.dat";

    public void salvarTodos(ArrayList<Professor> professores) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(professores);
        } catch (IOException e) {
            System.out.println("Erro ao salvar professores: " + e.getMessage());
        }
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

    public void adicionar(Professor professor) {
        ArrayList<Professor> professores = listarTodos();
        professores.add(professor);
        salvarTodos(professores);
    }
}
