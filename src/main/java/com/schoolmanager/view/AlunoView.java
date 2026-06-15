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
import java.util.ArrayList;

public class AlunoView extends VBox {
    private TextField txtNome, txtCpf, txtEmail, txtMatricula, txtTelefone;
    private TableView<Aluno> tabela;
    private ObservableList<Aluno> obsAlunos;
    private ArrayList<Aluno> listaMemoria;
    private AlunoDAO dao;
    private Aluno itemSelecionado;

    public AlunoView() {
        dao = new AlunoDAO();
        listaMemoria = dao.listarTodos();
        obsAlunos = FXCollections.observableArrayList(listaMemoria);

        setPadding(new Insets(15));
        setSpacing(10);

        txtNome = new TextField(); txtNome.setPromptText("Nome completo");
        txtCpf = new TextField(); txtCpf.setPromptText("CPF (ex: 000.000.000-00)");
        txtEmail = new TextField(); txtEmail.setPromptText("E-mail");
        txtMatricula = new TextField(); txtMatricula.setPromptText("Matrícula (ex: 2024001)");
        txtTelefone = new TextField(); txtTelefone.setPromptText("Telefone (ex: (11) 99999-9999)");

        Button btnSalvar = new Button("Salvar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");

        HBox form = new HBox(10,
                new Label("Nome:"), txtNome,
                new Label("CPF:"), txtCpf,
                new Label("E-mail:"), txtEmail,
                new Label("Matrícula:"), txtMatricula,
                new Label("Telefone:"), txtTelefone,
                btnSalvar, btnAtualizar, btnExcluir);

        tabela = new TableView<>();
        tabela.setItems(obsAlunos);

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

        btnSalvar.setOnAction(e -> adicionarAluno());
        btnAtualizar.setOnAction(e -> atualizarAluno());
        btnExcluir.setOnAction(e -> removerAluno());

        getChildren().addAll(new Label("Gerenciamento de Alunos (Discentes)"), form, tabela);
    }

    private void adicionarAluno() {
        try {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            String email = txtEmail.getText();
            String matricula = txtMatricula.getText();
            String telefone = txtTelefone.getText();

            if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || matricula.isEmpty() || telefone.isEmpty()) {
                mostrarAlerta("Erro", "Preencha todos os campos.");
                return;
            }

            Aluno novo = new Aluno(nome, cpf, email, matricula, telefone);
            dao.adicionar(novo);
            listaMemoria.add(novo);
            obsAlunos.add(novo);

            txtNome.clear(); txtCpf.clear(); txtEmail.clear(); txtMatricula.clear(); txtTelefone.clear();
        } catch (Exception ex) {
            mostrarAlerta("Erro", ex.getMessage());
        }
    }

    private void atualizarAluno() {
        if (itemSelecionado == null) {
            mostrarAlerta("Aviso", "Selecione um aluno na tabela para atualizar.");
            return;
        }
        String nome = txtNome.getText();
        String cpf = txtCpf.getText();
        String email = txtEmail.getText();
        String matricula = txtMatricula.getText();
        String telefone = txtTelefone.getText();

        if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || matricula.isEmpty() || telefone.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        String matriculaOriginal = itemSelecionado.getMatricula();
        dao.atualizar(matriculaOriginal, new Aluno(nome, cpf, email, matricula, telefone));
        itemSelecionado.setNome(nome);
        itemSelecionado.setCpf(cpf);
        itemSelecionado.setEmail(email);
        itemSelecionado.setMatricula(matricula);
        itemSelecionado.setTelefone(telefone);
        tabela.refresh();

        txtNome.clear(); txtCpf.clear(); txtEmail.clear(); txtMatricula.clear(); txtTelefone.clear();
        tabela.getSelectionModel().clearSelection();
        itemSelecionado = null;
    }

    private void removerAluno() {
        Aluno selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            dao.remover(selecionado.getMatricula());
            listaMemoria.remove(selecionado);
            obsAlunos.remove(selecionado);
        } else {
            mostrarAlerta("Aviso", "Selecione um aluno na tabela.");
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
