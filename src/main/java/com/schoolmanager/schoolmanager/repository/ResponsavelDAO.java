package com.schoolmanager.schoolmanager.repository;

import com.schoolmanager.schoolmanager.model.Responsavel;

import java.io.*;
import java.util.ArrayList;

public class ResponsavelDAO {
    private final String ARQUIVO = "responsaveis.dat";

    public void salvarTodos(ArrayList<Responsavel> responsaveis) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(responsaveis);
        } catch (IOException e) {
            System.out.println("Erro ao salvar responsáveis: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Responsavel> listarTodos() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            return (ArrayList<Responsavel>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler responsáveis: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void adicionar(Responsavel responsavel) {
        ArrayList<Responsavel> responsaveis = listarTodos();
        responsaveis.add(responsavel);
        salvarTodos(responsaveis);
    }
}
