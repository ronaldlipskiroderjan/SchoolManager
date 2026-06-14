package com.schoolmanager.repository;

import com.schoolmanager.model.Matricula;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAO {
    private static final String FILE_NAME = "matriculas.dat";

    @SuppressWarnings("unchecked")
    public List<Matricula> listarTodos() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Matricula>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler matriculas.dat: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void salvarLista(List<Matricula> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.out.println("Erro ao gravar em matriculas.dat: " + e.getMessage());
        }
    }

    public void adicionar(Matricula matricula) {
        List<Matricula> lista = listarTodos();
        lista.add(matricula);
        salvarLista(lista);
    }
}