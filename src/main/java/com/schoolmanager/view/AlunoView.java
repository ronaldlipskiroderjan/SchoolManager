package com.schoolmanager.view;

import com.schoolmanager.model.Aluno;
import com.schoolmanager.repository.AlunoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AlunoView extends VBox {

    private final AlunoDAO dao = new AlunoDAO();
    private final TableView<Aluno> tabela = new TableView<>();
    private final ObservableList<Aluno> dadosTabela = FXCollections.observableArrayList();

    private final TextField txtNome = new TextField();
    private final TextField txtCpf = new TextField();
    private final TextField txtEmail = new TextField();
    private final TextField txtMatricula = new TextField();
    private final TextField txtTelefone = new TextField();

    private Aluno itemSelecionado;

    public AlunoView() {
        setPadding(new Insets(15));
        setSpacing(10);

        txtNome.setPromptText("Nome completo");
        txtCpf.setPromptText("CPF (ex: 000.000.000-00)");
        txtEmail.setPromptText("E-mail");
        txtMatricula.setPromptText("Matrícula (ex: 2024001)");
        txtTelefone.setPromptText("Telefone (ex: (11) 99999-9999)");

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
                new Label("Matrícula:"), txtMatricula,
                new Label("Telefone:"), txtTelefone,
                btnSalvar, btnAtualizar, btnExcluir);

        TableColumn<Aluno, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Aluno, String> colCpf = new TableColumn<>("CPF");
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        TableColumn<Aluno, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Aluno, String> colMatricula = new TableColumn<>("Matrícula");
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));

        TableColumn<Aluno, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        tabela.getColumns().addAll(colNome, colCpf, colEmail, colMatricula, colTelefone);
        tabela.setItems(dadosTabela);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                itemSelecionado = novo;
                txtNome.setText(novo.getNome());
                txtCpf.setText(novo.getCpf());
                txtEmail.setText(novo.getEmail());
                txtMatricula.setText(novo.getMatricula());
                txtTelefone.setText(novo.getTelefone());
            }
        });

        carregarTabela();

        getChildren().addAll(new Label("Gerenciamento de Alunos (Discentes)"), form, tabela);
    }

    private void adicionar() {
        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        String email = txtEmail.getText().trim();
        String matricula = txtMatricula.getText().trim();
        String telefone = txtTelefone.getText().trim();

        if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || matricula.isEmpty() || telefone.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        dao.adicionar(new Aluno(nome, cpf, email, matricula, telefone));
        carregarTabela();
        limparCampos();
    }

    private void atualizar() {
        if (itemSelecionado == null) {
            mostrarAlerta("Aviso", "Selecione um aluno na tabela para atualizar.");
            return;
        }

        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        String email = txtEmail.getText().trim();
        String matricula = txtMatricula.getText().trim();
        String telefone = txtTelefone.getText().trim();

        if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || matricula.isEmpty() || telefone.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        dao.atualizar(itemSelecionado.getMatricula(), new Aluno(nome, cpf, email, matricula, telefone));
        carregarTabela();
        limparCampos();
    }

    private void remover() {
        Aluno selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Aviso", "Selecione um aluno na tabela.");
            return;
        }

        dao.remover(selecionado.getMatricula());
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
        txtMatricula.clear();
        txtTelefone.clear();
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
