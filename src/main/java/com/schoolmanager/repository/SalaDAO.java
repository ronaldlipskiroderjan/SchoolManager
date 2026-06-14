package com.schoolmanager.repository;
import com.schoolmanager.model.Sala;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SalaDAO {
    private static final String FILE_NAME = "salas.dat";

    @SuppressWarnings("unchecked")
    public List<Sala> listarTodos() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(FILE_NAME)))) {
            return (List<Sala>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler salas.dat: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void salvarLista(List<Sala> lista) {

        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(FILE_NAME)))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.out.println("Erro ao gravar em salas.dat: " + e.getMessage());
        }
    }

    public void adicionar(Sala sala) {
        List<Sala> lista = listarTodos();
        lista.add(sala);
        salvarLista(lista);
    }
}