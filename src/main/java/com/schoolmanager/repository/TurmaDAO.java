package com.schoolmanager.repository;

import com.schoolmanager.model.Turma; // <-- Esta é a linha mágica que resolve todos os erros da sua imagem!

import java.io.*;
import java.util.ArrayList;

public class TurmaDAO {
    private final String ARQUIVO = "turmas.dat";

    public void salvarTodos(ArrayList<Turma> turmas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(turmas);
        } catch (IOException e) {
            System.out.println("Erro ao salvar turmas: " + e.getMessage());
        }
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

    public void adicionar(Turma turma) {
        ArrayList<Turma> turmas = listarTodos();
        turmas.add(turma);
        salvarTodos(turmas);
    }
}