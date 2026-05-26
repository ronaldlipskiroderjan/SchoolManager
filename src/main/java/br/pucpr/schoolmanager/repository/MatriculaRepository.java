package br.pucpr.schoolmanager.repository;

import br.pucpr.schoolmanager.model.Matricula;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculaRepository {
    private static final String FILE_PATH = "dados/matricula.txt";

    public void salvar(Matricula matricula) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))){
            writer.write(matricula.toString());
            writer.newLine();
        }
    }

    public List<Matricula> listarTodos() throws IOException {
        List<Matricula> matriculas = new ArrayList<>();
        File arquivo = new File(FILE_PATH);

        if (!arquivo.exists()) {
            return matriculas;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    String[] campos = linha.split(";");
                    if(campos.length == 3) {
                        Matricula m = new Matricula(
                                campos[0],
                                Double.parseDouble(campos[1]),
                                campos[2]
                        );
                        matriculas.add(m);
                    }
                }
            }
        }
        return matriculas;
    }

    public void sobrescrever(List<Matricula> matriculas) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Matricula m : matriculas) {
                writer.write(m.toString());
                writer.newLine();
            }
        }
    }
}
