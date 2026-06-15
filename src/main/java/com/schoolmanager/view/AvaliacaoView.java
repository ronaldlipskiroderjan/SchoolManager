package com.schoolmanager.view;

import com.schoolmanager.model.Avaliacao;
import com.schoolmanager.repository.AvaliacaoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AvaliacaoView extends VBox {

    private final AvaliacaoDAO dao = new AvaliacaoDAO();
    private final TableView<Avaliacao> tabela = new TableView<>();
    private final ObservableList<Avaliacao> dadosTabela = FXCollections.observableArrayList();

    private final TextField txtNota = new TextField();
    private final TextField txtTipo = new TextField();
    private final TextField txtMatricula = new TextField();

    private Avaliacao itemSelecionado;

    public AvaliacaoView() {
        setPadding(new Insets(15));
        setSpacing(10);

        txtNota.setPromptText("Nota (ex: 8.5)");
        txtTipo.setPromptText("Tipo (ex: Prova 1)");
        txtMatricula.setPromptText("ID Matrícula");

        Button btnSalvar = new Button("Salvar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");

        btnSalvar.setOnAction(e -> adicionar());
        btnAtualizar.setOnAction(e -> atualizar());
        btnExcluir.setOnAction(e -> remover());

        HBox form = new HBox(10,
                new Label("Nota:"), txtNota,
                new Label("Tipo:"), txtTipo,
                new Label("Matrícula:"), txtMatricula,
                btnSalvar, btnAtualizar, btnExcluir);

        TableColumn<Avaliacao, Double> colNota = new TableColumn<>("Nota");
        colNota.setCellValueFactory(new PropertyValueFactory<>("valorNota"));

        TableColumn<Avaliacao, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn<Avaliacao, String> colMatricula = new TableColumn<>("Matrícula");
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matriculaId"));

        tabela.getColumns().addAll(colNota, colTipo, colMatricula);
        tabela.setItems(dadosTabela);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                itemSelecionado = novo;
                txtNota.setText(String.valueOf(novo.getValorNota()));
                txtTipo.setText(novo.getTipo());
                txtMatricula.setText(novo.getMatriculaId());
            }
        });

        carregarTabela();

        getChildren().addAll(new Label("Gerenciamento de Avaliações"), form, tabela);
    }

    private void adicionar() {
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

            dao.adicionar(new Avaliacao(nota, tipo, matricula));
            carregarTabela();
            limparCampos();
        } catch (NumberFormatException ex) {
            mostrarAlerta("Erro de Formato", "A nota deve ser um número válido (ex: 8.5).");
        }
    }

    private void atualizar() {
        if (itemSelecionado == null) {
            mostrarAlerta("Aviso", "Selecione uma avaliação na tabela para atualizar.");
            return;
        }
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

            dao.atualizar(itemSelecionado.getMatriculaId(), itemSelecionado.getTipo(),
                    new Avaliacao(nota, tipo, matricula));
            carregarTabela();
            limparCampos();
        } catch (NumberFormatException ex) {
            mostrarAlerta("Erro de Formato", "A nota deve ser um número válido (ex: 8.5).");
        }
    }

    private void remover() {
        Avaliacao selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Aviso", "Selecione uma avaliação na tabela.");
            return;
        }

        dao.remover(selecionada.getMatriculaId(), selecionada.getTipo());
        carregarTabela();
        limparCampos();
    }

    private void carregarTabela() {
        dadosTabela.clear();
        dadosTabela.addAll(dao.listarTodos());
        itemSelecionado = null;
    }

    private void limparCampos() {
        txtNota.clear();
        txtTipo.clear();
        txtMatricula.clear();
        tabela.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
