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
import java.util.ArrayList;

public class ProfessorView extends VBox {
    private TextField txtNome, txtCpf, txtEmail, txtTitulacao, txtEspecialidade;
    private TableView<Professor> tabela;
    private ObservableList<Professor> obsProfessores;
    private ArrayList<Professor> listaMemoria;
    private ProfessorDAO dao;
    private Professor itemSelecionado;

    public ProfessorView() {
        dao = new ProfessorDAO();
        listaMemoria = dao.listarTodos();
        obsProfessores = FXCollections.observableArrayList(listaMemoria);

        setPadding(new Insets(15));
        setSpacing(10);

        txtNome = new TextField(); txtNome.setPromptText("Nome completo");
        txtCpf = new TextField(); txtCpf.setPromptText("CPF (ex: 000.000.000-00)");
        txtEmail = new TextField(); txtEmail.setPromptText("E-mail");
        txtTitulacao = new TextField(); txtTitulacao.setPromptText("Titulação (ex: Doutor)");
        txtEspecialidade = new TextField(); txtEspecialidade.setPromptText("Especialidade");

        Button btnSalvar = new Button("Salvar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");

        HBox form = new HBox(10,
                new Label("Nome:"), txtNome,
                new Label("CPF:"), txtCpf,
                new Label("E-mail:"), txtEmail,
                new Label("Titulação:"), txtTitulacao,
                new Label("Especialidade:"), txtEspecialidade,
                btnSalvar, btnAtualizar, btnExcluir);

        tabela = new TableView<>();
        tabela.setItems(obsProfessores);

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

        btnSalvar.setOnAction(e -> adicionarProfessor());
        btnAtualizar.setOnAction(e -> atualizarProfessor());
        btnExcluir.setOnAction(e -> removerProfessor());

        getChildren().addAll(new Label("Gerenciamento de Professores (Docentes)"), form, tabela);
    }

    private void adicionarProfessor() {
        try {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            String email = txtEmail.getText();
            String titulacao = txtTitulacao.getText();
            String especialidade = txtEspecialidade.getText();

            if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || titulacao.isEmpty() || especialidade.isEmpty()) {
                mostrarAlerta("Erro", "Preencha todos os campos.");
                return;
            }

            Professor novo = new Professor(nome, cpf, email, titulacao, especialidade);
            dao.adicionar(novo);
            listaMemoria.add(novo);
            obsProfessores.add(novo);

            txtNome.clear(); txtCpf.clear(); txtEmail.clear(); txtTitulacao.clear(); txtEspecialidade.clear();
        } catch (Exception ex) {
            mostrarAlerta("Erro", ex.getMessage());
        }
    }

    private void atualizarProfessor() {
        if (itemSelecionado == null) {
            mostrarAlerta("Aviso", "Selecione um professor na tabela para atualizar.");
            return;
        }
        String nome = txtNome.getText();
        String cpf = txtCpf.getText();
        String email = txtEmail.getText();
        String titulacao = txtTitulacao.getText();
        String especialidade = txtEspecialidade.getText();

        if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || titulacao.isEmpty() || especialidade.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        String cpfOriginal = itemSelecionado.getCpf();
        dao.atualizar(cpfOriginal, new Professor(nome, cpf, email, titulacao, especialidade));
        itemSelecionado.setNome(nome);
        itemSelecionado.setCpf(cpf);
        itemSelecionado.setEmail(email);
        itemSelecionado.setTitulacao(titulacao);
        itemSelecionado.setEspecialidade(especialidade);
        tabela.refresh();

        txtNome.clear(); txtCpf.clear(); txtEmail.clear(); txtTitulacao.clear(); txtEspecialidade.clear();
        tabela.getSelectionModel().clearSelection();
        itemSelecionado = null;
    }

    private void removerProfessor() {
        Professor selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            dao.remover(selecionado.getCpf());
            listaMemoria.remove(selecionado);
            obsProfessores.remove(selecionado);
        } else {
            mostrarAlerta("Aviso", "Selecione um professor na tabela.");
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
