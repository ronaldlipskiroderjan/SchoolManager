package com.schoolmanager.schoolmanager.view;

import com.schoolmanager.schoolmanager.model.Matricula;
import com.schoolmanager.schoolmanager.repository.MatriculaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class MatriculaView {
    private final MatriculaDAO dao = new MatriculaDAO();
    private final TableView<Matricula> tabela = new TableView<>();
    private final ObservableList<Matricula> dadosTabela = FXCollections.observableArrayList();

    private final TextField txtData = new TextField();
    private final TextField txtAlunoId = new TextField();
    private final TextField txtTurmaId = new TextField();

    public VBox iniciarTela() {
        VBox painel = new VBox(10);
        painel.setPadding(new Insets(15));

        txtData.setPromptText("DD/MM/AAAA");
        txtAlunoId.setPromptText("CPF do Aluno");
        txtTurmaId.setPromptText("Código da Turma");

        Button btnSalvar = new Button("Efetivar Matrícula");
        btnSalvar.setOnAction(e -> executarCadastro());

        Button btnExcluir = new Button("Remover Matrícula");
        btnExcluir.setOnAction(e -> executarExclusao());

        Button btnLimpar = new Button("Limpar");
        btnLimpar.setOnAction(e -> limparCampos());

        HBox form = new HBox(10, txtData, txtAlunoId, txtTurmaId, btnSalvar, btnExcluir, btnLimpar);

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
                txtData.setText(novo.getDataMatricula());
                txtAlunoId.setText(novo.getAlunoId());
                txtTurmaId.setText(novo.getTurmaId());
            }
        });

        carregarTabela();

        painel.getChildren().addAll(
                new Label("Gestão de Matrículas"),
                form,
                new Label("Vínculos Ativos (Matrículas):"),
                tabela
        );
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
            mostrarAlerta("Aviso", "Selecione uma linha para remover.");
            return;
        }
        List<Matricula> lista = dao.listarTodos();
        lista.removeIf(m -> m.getAlunoId().equals(selecionada.getAlunoId())
                && m.getTurmaId().equalsIgnoreCase(selecionada.getTurmaId()));
        dao.salvarLista(lista);
        limparCampos();
        carregarTabela();
    }

    private void carregarTabela() {
        dadosTabela.clear();
        dadosTabela.addAll(dao.listarTodos());
    }

    private void limparCampos() {
        txtData.clear();
        txtAlunoId.clear();
        txtTurmaId.clear();
        tabela.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}