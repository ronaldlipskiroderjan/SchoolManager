package com.schoolmanager.repository;

import com.schoolmanager.model.Avaliacao;

import java.io.*;
import java.util.ArrayList;

public class AvaliacaoDAO {
    private final String ARQUIVO = "avaliacoes.dat";

    public void adicionar(Avaliacao avaliacao) {
        ArrayList<Avaliacao> lista = listarTodos();
        lista.add(avaliacao);
        salvarTodos(lista);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Avaliacao> listarTodos() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            return (ArrayList<Avaliacao>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar avaliações: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void atualizar(String matriculaId, String tipo, Avaliacao atualizado) {
        ArrayList<Avaliacao> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            Avaliacao a = lista.get(i);
            if (a.getMatriculaId().equals(matriculaId) && a.getTipo().equals(tipo)) {
                lista.set(i, atualizado);
                break;
            }
        }
        salvarTodos(lista);
    }

    public void remover(String matriculaId, String tipo) {
        ArrayList<Avaliacao> lista = listarTodos();
        lista.removeIf(a -> a.getMatriculaId().equals(matriculaId) && a.getTipo().equals(tipo));
        salvarTodos(lista);
    }

    private void salvarTodos(ArrayList<Avaliacao> avaliacoes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(avaliacoes);
        } catch (IOException e) {
            System.err.println("Erro ao salvar avaliações: " + e.getMessage());
        }
    }
}
