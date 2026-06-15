package com.schoolmanager.view;

import com.schoolmanager.model.Professor;
import com.schoolmanager.repository.ProfessorDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProfessorView extends VBox {

    private final ProfessorDAO dao = new ProfessorDAO();
    private final TableView<Professor> tabela = new TableView<>();
    private final ObservableList<Professor> dadosTabela = FXCollections.observableArrayList();

    private final TextField txtNome = new TextField();
    private final TextField txtCpf = new TextField();
    private final TextField txtEmail = new TextField();
    private final TextField txtTitulacao = new TextField();
    private final TextField txtEspecialidade = new TextField();

    private Professor itemSelecionado;

    public ProfessorView() {
        setPadding(new Insets(15));
        setSpacing(10);

        txtNome.setPromptText("Nome completo");
        txtCpf.setPromptText("CPF (ex: 000.000.000-00)");
        txtEmail.setPromptText("E-mail");
        txtTitulacao.setPromptText("Titulação (ex: Doutor)");
        txtEspecialidade.setPromptText("Especialidade");

        Button btnSalvar = new Button("Salvar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");

        btnSalvar.setOnAction(e -> adicionar());
        btnAtualizar.setOnAction(e -> atualizar());
        btnExcluir.setOnAction(e -> remover());

        HBox form = new HBox(10,
                new Label("Nome:"), txtNome,
                new Label("CPF:"), txtCpf,
                new Label("E-mail:"), txtEmail,
                new Label("Titulação:"), txtTitulacao,
                new Label("Especialidade:"), txtEspecialidade,
                btnSalvar, btnAtualizar, btnExcluir);

        TableColumn<Professor, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Professor, String> colCpf = new TableColumn<>("CPF");
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        TableColumn<Professor, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Professor, String> colTitulacao = new TableColumn<>("Titulação");
        colTitulacao.setCellValueFactory(new PropertyValueFactory<>("titulacao"));

        TableColumn<Professor, String> colEspecialidade = new TableColumn<>("Especialidade");
        colEspecialidade.setCellValueFactory(new PropertyValueFactory<>("especialidade"));

        tabela.getColumns().addAll(colNome, colCpf, colEmail, colTitulacao, colEspecialidade);
        tabela.setItems(dadosTabela);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                itemSelecionado = novo;
                txtNome.setText(novo.getNome());
                txtCpf.setText(novo.getCpf());
                txtEmail.setText(novo.getEmail());
                txtTitulacao.setText(novo.getTitulacao());
                txtEspecialidade.setText(novo.getEspecialidade());
            }
        });

        carregarTabela();

        getChildren().addAll(new Label("Gerenciamento de Professores (Docentes)"), form, tabela);
    }

    private void adicionar() {
        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        String email = txtEmail.getText().trim();
        String titulacao = txtTitulacao.getText().trim();
        String especialidade = txtEspecialidade.getText().trim();

        if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || titulacao.isEmpty() || especialidade.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        dao.adicionar(new Professor(nome, cpf, email, titulacao, especialidade));
        carregarTabela();
        limparCampos();
    }

    private void atualizar() {
        if (itemSelecionado == null) {
            mostrarAlerta("Aviso", "Selecione um professor na tabela para atualizar.");
            return;
        }

        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        String email = txtEmail.getText().trim();
        String titulacao = txtTitulacao.getText().trim();
        String especialidade = txtEspecialidade.getText().trim();

        if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || titulacao.isEmpty() || especialidade.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        dao.atualizar(itemSelecionado.getCpf(), new Professor(nome, cpf, email, titulacao, especialidade));
        carregarTabela();
        limparCampos();
    }

    private void remover() {
        Professor selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Aviso", "Selecione um professor na tabela.");
            return;
        }

        dao.remover(selecionado.getCpf());
        carregarTabela();
        limparCampos();
    }

    private void carregarTabela() {
        dadosTabela.clear();
        dadosTabela.addAll(dao.listarTodos());
        itemSelecionado = null;
    }

    private void limparCampos() {
        txtNome.clear();
        txtCpf.clear();
        txtEmail.clear();
        txtTitulacao.clear();
        txtEspecialidade.clear();
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
