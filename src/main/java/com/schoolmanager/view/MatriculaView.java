package com.schoolmanager.view;

import com.schoolmanager.model.Matricula;
import com.schoolmanager.repository.MatriculaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MatriculaView {
    private final MatriculaDAO dao = new MatriculaDAO();
    private final TableView<Matricula> tabela = new TableView<>();
    private final ObservableList<Matricula> dadosTabela = FXCollections.observableArrayList();

    private final TextField txtData = new TextField();
    private final TextField txtAlunoId = new TextField();
    private final TextField txtTurmaId = new TextField();
    private Matricula itemSelecionado;

    public VBox iniciarTela() {
        VBox painel = new VBox(10);
        painel.setPadding(new Insets(15));

        txtData.setPromptText("DD/MM/AAAA");
        txtAlunoId.setPromptText("CPF do Aluno");
        txtTurmaId.setPromptText("Código da Turma");

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction(e -> executarCadastro());

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.setOnAction(e -> executarAtualizacao());

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setOnAction(e -> executarExclusao());

        HBox form = new HBox(10, txtData, txtAlunoId, txtTurmaId, btnSalvar, btnAtualizar, btnExcluir);

        TableColumn<Matricula, String> colData = new TableColumn<>("Data Matrícula");
        colData.setCellValueFactory(new PropertyValueFactory<>("dataMatricula"));

        TableColumn<Matricula, String> colAluno = new TableColumn<>("CPF Aluno");
        colAluno.setCellValueFactory(new PropertyValueFactory<>("alunoId"));

        TableColumn<Matricula, String> colTurma = new TableColumn<>("Código Turma");
        colTurma.setCellValueFactory(new PropertyValueFactory<>("turmaId"));

        tabela.getColumns().addAll(colData, colAluno, colTurma);
        tabela.setItems(dadosTabela);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                itemSelecionado = novo;
                txtData.setText(novo.getDataMatricula());
                txtAlunoId.setText(novo.getAlunoId());
                txtTurmaId.setText(novo.getTurmaId());
            }
        });

        carregarTabela();

        painel.getChildren().addAll(new Label("Gestão de Matrículas"), form, tabela);
        return painel;
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
            mostrarAlerta("Formatação", "A data deve seguir o padrão DD/MM/AAAA.");
            return;
        }

        dao.adicionar(new Matricula(data, alunoId, turmaId));
        txtData.clear(); txtAlunoId.clear(); txtTurmaId.clear();
        carregarTabela();
    }

    private void executarAtualizacao() {
        if (itemSelecionado == null) {
            mostrarAlerta("Aviso", "Selecione uma matrícula na tabela para atualizar.");
            return;
        }
        String data = txtData.getText().trim();
        String alunoId = txtAlunoId.getText().trim();
        String turmaId = txtTurmaId.getText().trim();

        if (data.isEmpty() || alunoId.isEmpty() || turmaId.isEmpty()) {
            mostrarAlerta("Validação", "Todos os campos devem ser preenchidos.");
            return;
        }

        if (!data.matches("\\d{2}/\\d{2}/\\d{4}")) {
            mostrarAlerta("Formatação", "A data deve seguir o padrão DD/MM/AAAA.");
            return;
        }

        String alunoIdOriginal = itemSelecionado.getAlunoId();
        String turmaIdOriginal = itemSelecionado.getTurmaId();
        dao.atualizar(alunoIdOriginal, turmaIdOriginal, new Matricula(data, alunoId, turmaId));
        txtData.clear(); txtAlunoId.clear(); txtTurmaId.clear();
        tabela.getSelectionModel().clearSelection();
        itemSelecionado = null;
        carregarTabela();
    }

    private void executarExclusao() {
        Matricula selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Aviso", "Selecione uma matrícula para excluir.");
            return;
        }
        dao.remover(selecionada.getAlunoId(), selecionada.getTurmaId());
        carregarTabela();
    }

    private void carregarTabela() {
        dadosTabela.clear();
        dadosTabela.addAll(dao.listarTodos());
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
