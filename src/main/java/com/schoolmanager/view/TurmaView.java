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
import java.util.ArrayList;

public class TurmaView {
    private TurmaDAO dao = new TurmaDAO();
    private TableView<Turma> tabela = new TableView<>();
    private ObservableList<Turma> dadosTabela = FXCollections.observableArrayList();

    private TextField txtCodigo = new TextField();
    private TextField txtSemestre = new TextField();
    private TextField txtDisciplinaId = new TextField();

    public VBox iniciarTela() {
        VBox painel = new VBox(10);
        painel.setPadding(new Insets(15));

        txtCodigo.setPromptText("Código (ex: TURMA-A)");
        txtSemestre.setPromptText("Semestre (ex: 2026.1)");
        txtDisciplinaId.setPromptText("ID da Disciplina");

        Button btnSalvar = new Button("Cadastrar Turma");
        btnSalvar.setOnAction(e -> executarCadastro());

        Button btnExcluir = new Button("Excluir Selecionada");
        btnExcluir.setOnAction(e -> excluirRegistro());

        HBox form = new HBox(10, txtCodigo, txtSemestre, txtDisciplinaId, btnSalvar, btnExcluir);

        TableColumn<Turma, String> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));

        TableColumn<Turma, String> colSemestre = new TableColumn<>("Semestre");
        colSemestre.setCellValueFactory(new PropertyValueFactory<>("semestre"));

        TableColumn<Turma, String> colDisciplina = new TableColumn<>("ID Disciplina");
        colDisciplina.setCellValueFactory(new PropertyValueFactory<>("disciplinaId"));

        tabela.getColumns().addAll(colCodigo, colSemestre, colDisciplina);
        tabela.setItems(dadosTabela);
        carregarTabela();

        painel.getChildren().addAll(new Label("Gestão de Turmas (Membro 3)"), form, tabela);
        return painel;
    }

    private void executarCadastro() {
        String codigo = txtCodigo.getText();
        String semestre = txtSemestre.getText();
        String disciplinaId = txtDisciplinaId.getText();

        if (codigo.isEmpty() || semestre.isEmpty() || disciplinaId.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        Turma nova = new Turma(codigo, semestre, disciplinaId);
        dao.adicionar(nova);
        txtCodigo.clear(); txtSemestre.clear(); txtDisciplinaId.clear();
        carregarTabela();
    }

    private void carregarTabela() {
        dadosTabela.clear();
        dadosTabela.addAll(dao.listarTodos());
    }

    private void excluirRegistro() {
        Turma selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            dadosTabela.remove(selecionada);
            dao.salvarTodos(new ArrayList<>(dadosTabela));
            carregarTabela();
        } else {
            mostrarAlerta("Aviso", "Selecione uma turma na tabela.");
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}