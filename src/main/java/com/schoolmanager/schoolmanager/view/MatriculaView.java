package com.schoolmanager.schoolmanager.view;

import com.schoolmanager.schoolmanager.model.Matricula;
import com.schoolmanager.schoolmanager.repository.MatriculaDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class MatriculaView {
    private final MatriculaDAO dao = new MatriculaDAO();
    private final TableView<Matricula> tabela = new TableView<>();

    private final TextField txtData = new TextField();
    private final TextField txtAlunoId = new TextField();
    private final TextField txtTurmaId = new TextField();

    public void iniciarTela() {
        Stage stage = new Stage();
        stage.setTitle("Alocação - Controle de Matrículas");

        txtData.setPromptText("DD/MM/AAAA");
        txtAlunoId.setPromptText("Digite o CPF do Aluno (ID)");
        txtTurmaId.setPromptText("Digite o Código da Turma (ID)");

        Button btnSalvar = new Button("Efetivar Matrícula");
        Button btnExcluir = new Button("Remover Matrícula");

        TableColumn<Matricula, String> colData = new TableColumn<>("Data Matrícula");
        colData.setCellValueFactory(new PropertyValueFactory<>("dataMatricula"));

        TableColumn<Matricula, String> colAluno = new TableColumn<>("CPF Aluno (Chave)");
        colAluno.setCellValueFactory(new PropertyValueFactory<>("alunoId"));

        TableColumn<Matricula, String> colTurma = new TableColumn<>("Código Turma (Chave)");
        colTurma.setCellValueFactory(new PropertyValueFactory<>("turmaId"));

        tabela.getColumns().addAll(colData, colAluno, colTurma);
        tabela.setPrefHeight(200);

        btnSalvar.setOnAction(e -> executarCadastro());
        btnExcluir.setOnAction(e -> executarExclusao());

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                txtData.setText(novo.getDataMatricula());
                txtAlunoId.setText(novo.getAlunoId());
                txtTurmaId.setText(novo.getTurmaId());
            }
        });

        VBox formulario = new VBox(10,
                new Label("Data da Matrícula:"), txtData,
                new Label("Identificação do Aluno (CPF):"), txtAlunoId,
                new Label("Código da Turma:"), txtTurmaId,
                new HBox(10, btnSalvar, btnExcluir)
        );
        formulario.setPadding(new Insets(15));

        VBox layoutPrincipal = new VBox(10, formulario, new Label("Vínculos Ativos (Matrículas):"), tabela);
        layoutPrincipal.setPadding(new Insets(15));

        carregarTabela();

        Scene scene = new Scene(layoutPrincipal, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void executarCadastro() {
        String data = txtData.getText().trim();
        String alunoId = txtAlunoId.getText().trim();
        String turmaId = txtTurmaId.getText().trim();

        if (data.isEmpty() || alunoId.isEmpty() || turmaId.isEmpty()) {
            mostrarAlerta("Validação", "Todos os campos devem ser preenchidos.");
            return;
        }

        if (!data.matches("\\d{2}/\\d{2}/\\d{4}")) {
            mostrarAlerta("Formatação", "A data deve seguir rigorosamente o padrão DD/MM/AAAA.");
            return;
        }

        List<Matricula> lista = dao.listarTodos();
        Matricula existente = null;

        for (Matricula m : lista) {
            if (m.getAlunoId().equals(alunoId) && m.getTurmaId().equalsIgnoreCase(turmaId)) {
                existente = m;
                break;
            }
        }

        if (existente != null) {
            existente.setDataMatricula(data);
            dao.salvarLista(lista);
        } else {
            dao.adicionar(new Matricula(data, alunoId, turmaId));
        }

        limparCampos();
        carregarTabela();
    }

    private void executarExclusao() {
        Matricula selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Aviso", "Selecione uma linha para deletar.");
            return;
        }
        List<Matricula> lista = dao.listarTodos();
        lista.removeIf(m -> m.getAlunoId().equals(selecionada.getAlunoId()) && m.getTurmaId().equalsIgnoreCase(selecionada.getTurmaId()));
        dao.salvarLista(lista);
        limparCampos();
        carregarTabela();
    }

    private void carregarTabela() {
        tabela.setItems(FXCollections.observableArrayList(dao.listarTodos()));
    }

    private void limparCampos() {
        txtData.clear();
        txtAlunoId.clear();
        txtTurmaId.clear();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}