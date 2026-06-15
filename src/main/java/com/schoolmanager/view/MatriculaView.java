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

public class MatriculaView extends VBox {

    private final MatriculaDAO dao = new MatriculaDAO();
    private final TableView<Matricula> tabela = new TableView<>();
    private final ObservableList<Matricula> dadosTabela = FXCollections.observableArrayList();

    private final TextField txtData = new TextField();
    private final TextField txtAlunoId = new TextField();
    private final TextField txtTurmaId = new TextField();

    private Matricula itemSelecionado;

    public MatriculaView() {
        setPadding(new Insets(15));
        setSpacing(10);

        txtData.setPromptText("DD/MM/AAAA");
        txtAlunoId.setPromptText("CPF do Aluno");
        txtTurmaId.setPromptText("Código da Turma");

        Button btnSalvar = new Button("Salvar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");

        btnSalvar.setOnAction(e -> adicionar());
        btnAtualizar.setOnAction(e -> atualizar());
        btnExcluir.setOnAction(e -> remover());

        HBox form = new HBox(10,
                new Label("Data:"), txtData,
                new Label("CPF Aluno:"), txtAlunoId,
                new Label("Código Turma:"), txtTurmaId,
                btnSalvar, btnAtualizar, btnExcluir);

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

        getChildren().addAll(new Label("Gestão de Matrículas"), form, tabela);
    }

    private void adicionar() {
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
        carregarTabela();
        limparCampos();
    }

    private void atualizar() {
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

        dao.atualizar(itemSelecionado.getAlunoId(), itemSelecionado.getTurmaId(),
                new Matricula(data, alunoId, turmaId));
        carregarTabela();
        limparCampos();
    }

    private void remover() {
        Matricula selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Aviso", "Selecione uma matrícula para excluir.");
            return;
        }

        dao.remover(selecionada.getAlunoId(), selecionada.getTurmaId());
        carregarTabela();
        limparCampos();
    }

    private void carregarTabela() {
        dadosTabela.clear();
        dadosTabela.addAll(dao.listarTodos());
        itemSelecionado = null;
    }

    private void limparCampos() {
        txtData.clear();
        txtAlunoId.clear();
        txtTurmaId.clear();
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
