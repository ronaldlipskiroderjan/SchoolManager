package com.schoolmanager.view;

import com.schoolmanager.model.Disciplina;
import com.schoolmanager.repository.DisciplinaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DisciplinaView extends VBox {

    private final DisciplinaDAO dao = new DisciplinaDAO();
    private final TableView<Disciplina> tabela = new TableView<>();
    private final ObservableList<Disciplina> dadosTabela = FXCollections.observableArrayList();

    private final TextField txtCodigo = new TextField();
    private final TextField txtNome = new TextField();
    private final TextField txtCargaHoraria = new TextField();
    private final TextField txtEmenta = new TextField();
    private final TextField txtProfessorId = new TextField();

    private Disciplina itemSelecionado;

    public DisciplinaView() {
        setPadding(new Insets(15));
        setSpacing(10);

        txtCodigo.setPromptText("Código (ex: MAT001)");
        txtNome.setPromptText("Nome da Disciplina");
        txtCargaHoraria.setPromptText("Carga Horária (h)");
        txtEmenta.setPromptText("Ementa");
        txtProfessorId.setPromptText("CPF do Professor");

        Button btnSalvar = new Button("Salvar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");

        btnSalvar.setOnAction(e -> adicionar());
        btnAtualizar.setOnAction(e -> atualizar());
        btnExcluir.setOnAction(e -> remover());

        HBox form = new HBox(10,
                new Label("Código:"), txtCodigo,
                new Label("Nome:"), txtNome,
                new Label("Carga (h):"), txtCargaHoraria,
                new Label("Ementa:"), txtEmenta,
                new Label("CPF Professor:"), txtProfessorId,
                btnSalvar, btnAtualizar, btnExcluir);

        TableColumn<Disciplina, String> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));

        TableColumn<Disciplina, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Disciplina, Integer> colCarga = new TableColumn<>("Carga (h)");
        colCarga.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));

        TableColumn<Disciplina, String> colEmenta = new TableColumn<>("Ementa");
        colEmenta.setCellValueFactory(new PropertyValueFactory<>("ementa"));

        TableColumn<Disciplina, String> colProfessor = new TableColumn<>("CPF Professor");
        colProfessor.setCellValueFactory(new PropertyValueFactory<>("professorId"));

        tabela.getColumns().addAll(colCodigo, colNome, colCarga, colEmenta, colProfessor);
        tabela.setItems(dadosTabela);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                itemSelecionado = novo;
                txtCodigo.setText(novo.getCodigo());
                txtNome.setText(novo.getNome());
                txtCargaHoraria.setText(String.valueOf(novo.getCargaHoraria()));
                txtEmenta.setText(novo.getEmenta());
                txtProfessorId.setText(novo.getProfessorId());
            }
        });

        carregarTabela();

        getChildren().addAll(new Label("Gerenciamento de Disciplinas"), form, tabela);
    }

    private void adicionar() {
        try {
            String codigo = txtCodigo.getText().trim();
            String nome = txtNome.getText().trim();
            int cargaHoraria = Integer.parseInt(txtCargaHoraria.getText().trim());
            String ementa = txtEmenta.getText().trim();
            String professorId = txtProfessorId.getText().trim();

            if (codigo.isEmpty() || nome.isEmpty() || ementa.isEmpty() || professorId.isEmpty()) {
                mostrarAlerta("Erro", "Preencha todos os campos.");
                return;
            }

            dao.adicionar(new Disciplina(codigo, nome, cargaHoraria, ementa, professorId));
            carregarTabela();
            limparCampos();
        } catch (NumberFormatException ex) {
            mostrarAlerta("Erro de Formato", "Carga horária deve ser um número inteiro.");
        }
    }

    private void atualizar() {
        if (itemSelecionado == null) {
            mostrarAlerta("Aviso", "Selecione uma disciplina na tabela para atualizar.");
            return;
        }
        try {
            String codigo = txtCodigo.getText().trim();
            String nome = txtNome.getText().trim();
            int cargaHoraria = Integer.parseInt(txtCargaHoraria.getText().trim());
            String ementa = txtEmenta.getText().trim();
            String professorId = txtProfessorId.getText().trim();

            if (codigo.isEmpty() || nome.isEmpty() || ementa.isEmpty() || professorId.isEmpty()) {
                mostrarAlerta("Erro", "Preencha todos os campos.");
                return;
            }

            dao.atualizar(itemSelecionado.getCodigo(), new Disciplina(codigo, nome, cargaHoraria, ementa, professorId));
            carregarTabela();
            limparCampos();
        } catch (NumberFormatException ex) {
            mostrarAlerta("Erro de Formato", "Carga horária deve ser um número inteiro.");
        }
    }

    private void remover() {
        Disciplina selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Aviso", "Selecione uma disciplina na tabela.");
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
        txtNome.clear();
        txtCargaHoraria.clear();
        txtEmenta.clear();
        txtProfessorId.clear();
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
