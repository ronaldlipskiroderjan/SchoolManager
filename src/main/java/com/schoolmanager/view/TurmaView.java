package com.schoolmanager.view;

import com.schoolmanager.model.Turma;
import com.schoolmanager.repository.TurmaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TurmaView extends VBox {

    private final TurmaDAO dao = new TurmaDAO();
    private final TableView<Turma> tabela = new TableView<>();
    private final ObservableList<Turma> dadosTabela = FXCollections.observableArrayList();

    private final TextField txtCodigo = new TextField();
    private final TextField txtSemestre = new TextField();
    private final TextField txtDisciplinaId = new TextField();

    private Turma itemSelecionado;

    public TurmaView() {
        setPadding(new Insets(15));
        setSpacing(10);

        txtCodigo.setPromptText("Código (ex: TURMA-A)");
        txtSemestre.setPromptText("Semestre (ex: 2026.1)");
        txtDisciplinaId.setPromptText("ID da Disciplina");

        Button btnSalvar = new Button("Salvar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");

        btnSalvar.setOnAction(e -> adicionar());
        btnAtualizar.setOnAction(e -> atualizar());
        btnExcluir.setOnAction(e -> remover());

        HBox form = new HBox(10,
                new Label("Código:"), txtCodigo,
                new Label("Semestre:"), txtSemestre,
                new Label("ID Disciplina:"), txtDisciplinaId,
                btnSalvar, btnAtualizar, btnExcluir);

        TableColumn<Turma, String> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));

        TableColumn<Turma, String> colSemestre = new TableColumn<>("Semestre");
        colSemestre.setCellValueFactory(new PropertyValueFactory<>("semestre"));

        TableColumn<Turma, String> colDisciplina = new TableColumn<>("ID Disciplina");
        colDisciplina.setCellValueFactory(new PropertyValueFactory<>("disciplinaId"));

        tabela.getColumns().addAll(colCodigo, colSemestre, colDisciplina);
        tabela.setItems(dadosTabela);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                itemSelecionado = novo;
                txtCodigo.setText(novo.getCodigo());
                txtSemestre.setText(novo.getSemestre());
                txtDisciplinaId.setText(novo.getDisciplinaId());
            }
        });

        carregarTabela();

        getChildren().addAll(new Label("Gestão de Turmas"), form, tabela);
    }

    private void adicionar() {
        String codigo = txtCodigo.getText().trim();
        String semestre = txtSemestre.getText().trim();
        String disciplinaId = txtDisciplinaId.getText().trim();

        if (codigo.isEmpty() || semestre.isEmpty() || disciplinaId.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        dao.adicionar(new Turma(codigo, semestre, disciplinaId));
        carregarTabela();
        limparCampos();
    }

    private void atualizar() {
        if (itemSelecionado == null) {
            mostrarAlerta("Aviso", "Selecione uma turma na tabela para atualizar.");
            return;
        }

        String codigo = txtCodigo.getText().trim();
        String semestre = txtSemestre.getText().trim();
        String disciplinaId = txtDisciplinaId.getText().trim();

        if (codigo.isEmpty() || semestre.isEmpty() || disciplinaId.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        dao.atualizar(itemSelecionado.getCodigo(), new Turma(codigo, semestre, disciplinaId));
        carregarTabela();
        limparCampos();
    }

    private void remover() {
        Turma selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Aviso", "Selecione uma turma na tabela.");
            return;
        }

        dao.remover(selecionada.getCodigo());
        carregarTabela();
        limparCampos();
    }

    private void carregarTabela() {
        dadosTabela.clear();
        dadosTabela.addAll(dao.listarTodos());
        itemSelecionado = null;
    }

    private void limparCampos() {
        txtCodigo.clear();
        txtSemestre.clear();
        txtDisciplinaId.clear();
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
