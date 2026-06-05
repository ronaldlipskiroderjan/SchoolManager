package com.schoolmanager.schoolmanager.repository;

import com.schoolmanager.schoolmanager.model.Frequencia;

import java.io.*;
import java.util.ArrayList;

public class FrequenciaDAO {
    private final String ARQUIVO = "frequencias.dat";

    public void salvarDados(ArrayList<Frequencia> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.err.println("Erro ao salvar frequências: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Frequencia> carregarDados() {
        ArrayList<Frequencia> lista = new ArrayList<>();
        File file = new File(ARQUIVO);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                lista = (ArrayList<Frequencia>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar frequências: " + e.getMessage());
            }
        }
        return lista;
    }
}
