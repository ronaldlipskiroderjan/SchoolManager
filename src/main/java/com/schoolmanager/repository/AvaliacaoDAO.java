package com.schoolmanager.repository;

import com.schoolmanager.model.Avaliacao;

import java.io.*;
import java.util.ArrayList;

public class AvaliacaoDAO {
    private final String ARQUIVO = "avaliacoes.dat";

    public void salvarDados(ArrayList<Avaliacao> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.err.println("Erro ao salvar avaliações: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Avaliacao> carregarDados() {
        ArrayList<Avaliacao> lista = new ArrayList<>();
        File file = new File(ARQUIVO);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                lista = (ArrayList<Avaliacao>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar avaliações: " + e.getMessage());
            }
        }
        return lista;
    }
}
