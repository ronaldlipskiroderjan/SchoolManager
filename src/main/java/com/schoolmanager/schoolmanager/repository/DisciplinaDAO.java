package com.schoolmanager.schoolmanager.repository;

import com.schoolmanager.schoolmanager.model.Disciplina;

import java.io.*;
import java.util.ArrayList;

public class DisciplinaDAO {
    private final String ARQUIVO = "disciplinas.dat";

    public void salvarTodos(ArrayList<Disciplina> disciplinas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(disciplinas);
        } catch (IOException e) {
            System.out.println("Erro ao salvar disciplinas: " + e.getMessage());
        }
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

    public void adicionar(Disciplina disciplina) {
        ArrayList<Disciplina> disciplinas = listarTodos();
        disciplinas.add(disciplina);
        salvarTodos(disciplinas);
    }
}
