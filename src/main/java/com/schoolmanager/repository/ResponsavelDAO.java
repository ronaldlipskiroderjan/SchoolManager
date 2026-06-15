package com.schoolmanager.repository;

import com.schoolmanager.model.Responsavel;

import java.io.*;
import java.util.ArrayList;

public class ResponsavelDAO {
    private final String ARQUIVO = "responsaveis.dat";

    public void adicionar(Responsavel responsavel) {
        ArrayList<Responsavel> lista = listarTodos();
        lista.add(responsavel);
        salvarTodos(lista);
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

    public void atualizar(String cpf, Responsavel atualizado) {
        ArrayList<Responsavel> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCpf().equals(cpf)) {
                lista.set(i, atualizado);
                break;
            }
        }
        salvarTodos(lista);
    }

    public void remover(String cpf) {
        ArrayList<Responsavel> lista = listarTodos();
        lista.removeIf(r -> r.getCpf().equals(cpf));
        salvarTodos(lista);
    }

    private void salvarTodos(ArrayList<Responsavel> responsaveis) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(responsaveis);
        } catch (IOException e) {
            System.out.println("Erro ao salvar responsáveis: " + e.getMessage());
        }
    }
}
