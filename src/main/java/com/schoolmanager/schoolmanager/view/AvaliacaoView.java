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

        HBox form = new HBox(10,
                new Label("Nota:"), txtNota,
                new Label("Tipo:"), txtTipo,
                new Label("Matrícula:"), txtMatricula,
                btnSalvar, btnExcluir);

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
            String notaStr = txtNota.getText().trim();
            String tipo = txtTipo.getText().trim();
            String matricula = txtMatricula.getText().trim();

            if (notaStr.isEmpty() || tipo.isEmpty() || matricula.isEmpty()) {
                mostrarAlerta("Validação", "Preencha todos os campos.");
                return;
            }

            double nota = Double.parseDouble(notaStr.replace(",", "."));

            if (nota < 0 || nota > 10) {
                mostrarAlerta("Regra de Negócio", "A nota deve estar entre 0 e 10.");
                return;
            }

            Avaliacao nova = new Avaliacao(nota, tipo, matricula);
            listaMemoria.add(nova);
            obsAvaliacoes.add(nova);
            dao.salvarDados(listaMemoria);

            txtNota.clear(); txtTipo.clear(); txtMatricula.clear();

        } catch (NumberFormatException ex) {
            mostrarAlerta("Erro de Formato", "A nota deve ser um número válido (ex: 8.5).");
        } catch (Exception ex) {
            mostrarAlerta("Erro", ex.getMessage() != null ? ex.getMessage() : "Erro inesperado.");
        }
    }

    private void removerAvaliacao() {
        Avaliacao selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Aviso", "Selecione uma avaliação na tabela para excluir.");
            return;
        }
        listaMemoria.remove(selecionada);
        obsAvaliacoes.remove(selecionada);
        dao.salvarDados(listaMemoria);
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}