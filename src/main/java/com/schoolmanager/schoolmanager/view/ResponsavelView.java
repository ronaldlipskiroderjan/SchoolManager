package com.schoolmanager.schoolmanager.view;

import com.schoolmanager.schoolmanager.model.Responsavel;
import com.schoolmanager.schoolmanager.repository.ResponsavelDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class ResponsavelView extends VBox {
    private TextField txtNome, txtCpf, txtEmail, txtTelefone, txtAlunoMatricula;
    private TableView<Responsavel> tabela;
    private ObservableList<Responsavel> obsResponsaveis;
    private ArrayList<Responsavel> listaMemoria;
    private ResponsavelDAO dao;

    public ResponsavelView() {
        dao = new ResponsavelDAO();
        listaMemoria = dao.listarTodos();
        obsResponsaveis = FXCollections.observableArrayList(listaMemoria);

        setPadding(new Insets(15));
        setSpacing(10);

        txtNome = new TextField(); txtNome.setPromptText("Nome completo");
        txtCpf = new TextField(); txtCpf.setPromptText("CPF (ex: 000.000.000-00)");
        txtEmail = new TextField(); txtEmail.setPromptText("E-mail");
        txtTelefone = new TextField(); txtTelefone.setPromptText("Telefone (ex: (11) 99999-9999)");
        txtAlunoMatricula = new TextField(); txtAlunoMatricula.setPromptText("Matrícula do Aluno");

        Button btnSalvar = new Button("Salvar");
        Button btnExcluir = new Button("Excluir");

        HBox form = new HBox(10,
                new Label("Nome:"), txtNome,
                new Label("CPF:"), txtCpf,
                new Label("E-mail:"), txtEmail,
                new Label("Telefone:"), txtTelefone,
                new Label("Matrícula Aluno:"), txtAlunoMatricula,
                btnSalvar, btnExcluir);

        tabela = new TableView<>();
        tabela.setItems(obsResponsaveis);

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

        btnSalvar.setOnAction(e -> adicionarResponsavel());
        btnExcluir.setOnAction(e -> removerResponsavel());

        getChildren().addAll(new Label("Gerenciamento de Responsáveis"), form, tabela);
    }

    private void adicionarResponsavel() {
        try {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            String email = txtEmail.getText();
            String telefone = txtTelefone.getText();
            String alunoMatricula = txtAlunoMatricula.getText();

            if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || telefone.isEmpty() || alunoMatricula.isEmpty()) {
                mostrarAlerta("Erro", "Preencha todos os campos.");
                return;
            }

            Responsavel novo = new Responsavel(nome, cpf, email, telefone, alunoMatricula);
            listaMemoria.add(novo);
            obsResponsaveis.add(novo);
            dao.salvarTodos(listaMemoria);

            txtNome.clear(); txtCpf.clear(); txtEmail.clear(); txtTelefone.clear(); txtAlunoMatricula.clear();
        } catch (Exception ex) {
            mostrarAlerta("Erro", ex.getMessage());
        }
    }

    private void removerResponsavel() {
        Responsavel selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            listaMemoria.remove(selecionado);
            obsResponsaveis.remove(selecionado);
            dao.salvarTodos(listaMemoria);
        } else {
            mostrarAlerta("Aviso", "Selecione um responsável na tabela.");
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
