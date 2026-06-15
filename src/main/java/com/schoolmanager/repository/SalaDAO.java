package com.schoolmanager.repository;

import com.schoolmanager.model.Sala;

import java.io.*;
import java.util.ArrayList;

public class SalaDAO {
    private final String ARQUIVO = "salas.dat";

    public void adicionar(Sala sala) {
        ArrayList<Sala> lista = listarTodos();
        lista.add(sala);
        salvarTodos(lista);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Sala> listarTodos() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            return (ArrayList<Sala>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler salas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void atualizar(String numero, Sala atualizado) {
        ArrayList<Sala> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNumero().equals(numero)) {
                lista.set(i, atualizado);
                break;
            }
        }
        salvarTodos(lista);
    }

    public void remover(String numero) {
        ArrayList<Sala> lista = listarTodos();
        lista.removeIf(s -> s.getNumero().equals(numero));
        salvarTodos(lista);
    }

    private void salvarTodos(ArrayList<Sala> salas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(salas);
        } catch (IOException e) {
            System.out.println("Erro ao salvar salas: " + e.getMessage());
        }
    }
}
