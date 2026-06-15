package com.schoolmanager.view;

import com.schoolmanager.model.Curso;
import com.schoolmanager.repository.CursoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CursoView {
    private CursoDAO dao = new CursoDAO();
    private TableView<Curso> tabela = new TableView<>();
    private ObservableList<Curso> dadosTabela = FXCollections.observableArrayList();

    private TextField txtNome = new TextField();
    private TextField txtDuracao = new TextField();
    private TextField txtCoordenadorId = new TextField();
    private Curso itemSelecionado;

    public VBox iniciarTela() {
        VBox painel = new VBox(10);
        painel.setPadding(new Insets(15));

        txtNome.setPromptText("Nome do Curso");
        txtDuracao.setPromptText("Duração (Semestres)");
        txtCoordenadorId.setPromptText("ID do Professor Coordenador");

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction(e -> executarCadastro());

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.setOnAction(e -> executarAtualizacao());

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setOnAction(e -> excluirRegistro());

        HBox form = new HBox(10, txtNome, txtDuracao, txtCoordenadorId, btnSalvar, btnAtualizar, btnExcluir);

        TableColumn<Curso, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Curso, Integer> colDuracao = new TableColumn<>("Duração");
        colDuracao.setCellValueFactory(new PropertyValueFactory<>("duracaoSemestres"));

        TableColumn<Curso, String> colCoordenador = new TableColumn<>("ID Coordenador");
        colCoordenador.setCellValueFactory(new PropertyValueFactory<>("coordenadorId"));

        tabela.getColumns().addAll(colNome, colDuracao, colCoordenador);
        tabela.setItems(dadosTabela);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                itemSelecionado = novo;
                txtNome.setText(novo.getNome());
                txtDuracao.setText(String.valueOf(novo.getDuracaoSemestres()));
                txtCoordenadorId.setText(novo.getCoordenadorId());
            }
        });

        carregarTabela();

        painel.getChildren().addAll(new Label("Gestão de Cursos (Membro 3)"), form, tabela);
        return painel;
    }

    private void executarCadastro() {
        try {
            String nome = txtNome.getText();
            int duracao = Integer.parseInt(txtDuracao.getText());
            String coordenadorId = txtCoordenadorId.getText();

            if (nome.isEmpty() || coordenadorId.isEmpty()) {
                mostrarAlerta("Erro", "Preencha todos os campos.");
                return;
            }

            Curso novo = new Curso(nome, duracao, coordenadorId);
            dao.adicionar(novo);
            txtNome.clear(); txtDuracao.clear(); txtCoordenadorId.clear();
            carregarTabela();
        } catch (NumberFormatException ex) {
            mostrarAlerta("Erro de Formato", "Duração deve ser um número inteiro.");
        }
    }

    private void executarAtualizacao() {
        if (itemSelecionado == null) {
            mostrarAlerta("Aviso", "Selecione um curso na tabela para atualizar.");
            return;
        }
        try {
            String nome = txtNome.getText();
            int duracao = Integer.parseInt(txtDuracao.getText());
            String coordenadorId = txtCoordenadorId.getText();

            if (nome.isEmpty() || coordenadorId.isEmpty()) {
                mostrarAlerta("Erro", "Preencha todos os campos.");
                return;
            }

            String nomeOriginal = itemSelecionado.getNome();
            dao.atualizar(nomeOriginal, new Curso(nome, duracao, coordenadorId));
            txtNome.clear(); txtDuracao.clear(); txtCoordenadorId.clear();
            tabela.getSelectionModel().clearSelection();
            itemSelecionado = null;
            carregarTabela();
        } catch (NumberFormatException ex) {
            mostrarAlerta("Erro de Formato", "Duração deve ser um número inteiro.");
        }
    }

    private void carregarTabela() {
        dadosTabela.clear();
        dadosTabela.addAll(dao.listarTodos());
    }

    private void excluirRegistro() {
        Curso selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            dao.remover(selecionado.getNome());
            carregarTabela();
        } else {
            mostrarAlerta("Aviso", "Selecione um curso na tabela.");
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