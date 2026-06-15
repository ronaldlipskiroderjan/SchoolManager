package com.schoolmanager.view;

import com.schoolmanager.model.Responsavel;
import com.schoolmanager.repository.ResponsavelDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ResponsavelView extends VBox {

    private final ResponsavelDAO dao = new ResponsavelDAO();
    private final TableView<Responsavel> tabela = new TableView<>();
    private final ObservableList<Responsavel> dadosTabela = FXCollections.observableArrayList();

    private final TextField txtNome = new TextField();
    private final TextField txtCpf = new TextField();
    private final TextField txtEmail = new TextField();
    private final TextField txtTelefone = new TextField();
    private final TextField txtAlunoMatricula = new TextField();

    private Responsavel itemSelecionado;

    public ResponsavelView() {
        setPadding(new Insets(15));
        setSpacing(10);

        txtNome.setPromptText("Nome completo");
        txtCpf.setPromptText("CPF (ex: 000.000.000-00)");
        txtEmail.setPromptText("E-mail");
        txtTelefone.setPromptText("Telefone (ex: (11) 99999-9999)");
        txtAlunoMatricula.setPromptText("Matrícula do Aluno");

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
                new Label("Telefone:"), txtTelefone,
                new Label("Matrícula Aluno:"), txtAlunoMatricula,
                btnSalvar, btnAtualizar, btnExcluir);

        TableColumn<Responsavel, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Responsavel, String> colCpf = new TableColumn<>("CPF");
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        TableColumn<Responsavel, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Responsavel, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        TableColumn<Responsavel, String> colAluno = new TableColumn<>("Matrícula Aluno");
        colAluno.setCellValueFactory(new PropertyValueFactory<>("alunoMatricula"));

        tabela.getColumns().addAll(colNome, colCpf, colEmail, colTelefone, colAluno);
        tabela.setItems(dadosTabela);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                itemSelecionado = novo;
                txtNome.setText(novo.getNome());
                txtCpf.setText(novo.getCpf());
                txtEmail.setText(novo.getEmail());
                txtTelefone.setText(novo.getTelefone());
                txtAlunoMatricula.setText(novo.getAlunoMatricula());
            }
        });

        carregarTabela();

        getChildren().addAll(new Label("Gerenciamento de Responsáveis"), form, tabela);
    }

    private void adicionar() {
        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        String email = txtEmail.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String alunoMatricula = txtAlunoMatricula.getText().trim();

        if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || telefone.isEmpty() || alunoMatricula.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        dao.adicionar(new Responsavel(nome, cpf, email, telefone, alunoMatricula));
        carregarTabela();
        limparCampos();
    }

    private void atualizar() {
        if (itemSelecionado == null) {
            mostrarAlerta("Aviso", "Selecione um responsável na tabela para atualizar.");
            return;
        }

        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        String email = txtEmail.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String alunoMatricula = txtAlunoMatricula.getText().trim();

        if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || telefone.isEmpty() || alunoMatricula.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        dao.atualizar(itemSelecionado.getCpf(), new Responsavel(nome, cpf, email, telefone, alunoMatricula));
        carregarTabela();
        limparCampos();
    }

    private void remover() {
        Responsavel selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Aviso", "Selecione um responsável na tabela.");
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
        txtTelefone.clear();
        txtAlunoMatricula.clear();
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
