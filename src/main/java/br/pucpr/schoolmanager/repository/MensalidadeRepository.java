package br.pucpr.schoolmanager.repository;

import br.pucpr.schoolmanager.model.Mensalidade;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MensalidadeRepository {
    private static final String FILE_PATH = "dados/mensalidade.txt";

    public void salvar(Mensalidade mensalidade) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(mensalidade.toString());
            writer.newLine();
        }
    }

    public List<Mensalidade> listarTodos() throws IOException {
        List<Mensalidade> mensalidades = new ArrayList<>();
        File arquivo = new File(FILE_PATH);

        if (!arquivo.exists()) {
            return mensalidades;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    String[] campos = linha.split(";");
                    if (campos.length == 3) {
                        Mensalidade m = new Mensalidade(
                                campos[0],
                                Double.parseDouble(campos[1]),
                                campos[2]
                        );
                        mensalidades.add(m);
                    }
                }
            }
        }
        return mensalidades;
    }

    public void sobrescrever(List<Mensalidade> mensalidades) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))){
            for (Mensalidade m : mensalidades) {
                writer.write(m.toString());
                writer.newLine();
            }
        }
    }
}
