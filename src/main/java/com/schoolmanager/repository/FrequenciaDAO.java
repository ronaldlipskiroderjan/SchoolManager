package com.schoolmanager.repository;

import com.schoolmanager.model.Frequencia;

import java.io.*;
import java.util.ArrayList;

public class FrequenciaDAO {
    private final String ARQUIVO = "frequencias.dat";

    public void adicionar(Frequencia frequencia) {
        ArrayList<Frequencia> lista = listarTodos();
        lista.add(frequencia);
        salvarTodos(lista);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Frequencia> listarTodos() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            return (ArrayList<Frequencia>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar frequências: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void atualizar(String matriculaId, String dataAula, Frequencia atualizado) {
        ArrayList<Frequencia> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            Frequencia f = lista.get(i);
            if (f.getMatriculaId().equals(matriculaId) && f.getDataAula().equals(dataAula)) {
                lista.set(i, atualizado);
                break;
            }
        }
        salvarTodos(lista);
    }

    public void remover(String matriculaId, String dataAula) {
        ArrayList<Frequencia> lista = listarTodos();
        lista.removeIf(f -> f.getMatriculaId().equals(matriculaId) && f.getDataAula().equals(dataAula));
        salvarTodos(lista);
    }

    private void salvarTodos(ArrayList<Frequencia> frequencias) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(frequencias);
        } catch (IOException e) {
            System.err.println("Erro ao salvar frequências: " + e.getMessage());
        }
    }
}
