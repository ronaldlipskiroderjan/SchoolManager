package com.schoolmanager.schoolmanager.view;

import com.schoolmanager.schoolmanager.model.Avaliacao;
import com.schoolmanager.schoolmanager.repository.AvaliacaoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class AvaliacaoView extends VBox {
    private TextField txtNota, txtTipo, txtMatricula;
    private TableView<Avaliacao> tabela;
    private ObservableList<Avaliacao> obsAvaliacoes;
    private ArrayList<Avaliacao> listaMemoria;
    private AvaliacaoDAO dao;

    public AvaliacaoView() {
        dao = new AvaliacaoDAO();
        listaMemoria = dao.carregarDados();
        obsAvaliacoes = FXCollections.observableArrayList(listaMemoria);

        setPadding(new Insets(15));
        setSpacing(10);

        txtNota = new TextField(); txtNota.setPromptText("Nota (ex: 8.5)");
        txtTipo = new TextField(); txtTipo.setPromptText("Tipo (ex: Prova 1)");
        txtMatricula = new TextField(); txtMatricula.setPromptText("ID Matrícula");

        Button btnSalvar = new Button("Salvar");
        Button btnExcluir = new Button("Excluir");

        HBox form = new HBox(10, new Label("Nota:"), txtNota, new Label("Tipo:"), txtTipo, new Label("Matrícula:"), txtMatricula, btnSalvar, btnExcluir);

        tabela = new TableView<>();
        tabela.setItems(obsAvaliacoes);

        TableColumn<Avaliacao, Double> colNota = new TableColumn<>("Nota");
        colNota.setCellValueFactory(new PropertyValueFactory<>("valorNota"));

        TableColumn<Avaliacao, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn<Avaliacao, String> colMatricula = new TableColumn<>("Matrícula");
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matriculaId"));

        tabela.getColumns().addAll(colNota, colTipo, colMatricula);

        btnSalvar.setOnAction(e -> adicionarAvaliacao());
        btnExcluir.setOnAction(e -> removerAvaliacao());

        getChildren().addAll(new Label("Gerenciamento de Avaliações"), form, tabela);
    }

    private void adicionarAvaliacao() {
        try {
            double nota = Double.parseDouble(txtNota.getText().replace(",", ".")); // Tratamento de campos numéricos
            String tipo = txtTipo.getText();
            String matricula = txtMatricula.getText();

            if(tipo.isEmpty() || matricula.isEmpty()) throw new Exception("Preencha todos os campos");

            Avaliacao nova = new Avaliacao(nota, tipo, matricula);
            listaMemoria.add(nova);
            obsAvaliacoes.add(nova);
            dao.salvarDados(listaMemoria);

            txtNota.clear(); txtTipo.clear(); txtMatricula.clear();
        } catch (NumberFormatException ex) {
            System.err.println("Erro: A nota deve ser um número válido!");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void removerAvaliacao() {
        Avaliacao selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            listaMemoria.remove(selecionada);
            obsAvaliacoes.remove(selecionada);
            dao.salvarDados(listaMemoria);
        }
    }
}