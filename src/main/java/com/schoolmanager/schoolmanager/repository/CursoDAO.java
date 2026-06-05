package com.schoolmanager.schoolmanager.repository;

import br.pucpr.schoolmanager.model.Curso;
import java.io.*;
import java.util.ArrayList;

public class CursoDAO {
    private final String ARQUIVO = "cursos.dat"; //

    public void salvarTodos(ArrayList<Curso> cursos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(cursos);
        } catch (IOException e) {
            System.out.println("Erro ao salvar cursos: " + e.getMessage());
        }
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

    public void adicionar(Curso curso) {
        ArrayList<Curso> cursos = listarTodos();
        cursos.add(curso);
        salvarTodos(cursos);
    }
}