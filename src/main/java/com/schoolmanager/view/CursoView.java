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

public class CursoView extends VBox {

    private final CursoDAO dao = new CursoDAO();
    private final TableView<Curso> tabela = new TableView<>();
    private final ObservableList<Curso> dadosTabela = FXCollections.observableArrayList();

    private final TextField txtNome = new TextField();
    private final TextField txtDuracao = new TextField();
    private final TextField txtCoordenadorId = new TextField();

    private Curso itemSelecionado;

    public CursoView() {
        setPadding(new Insets(15));
        setSpacing(10);

        txtNome.setPromptText("Nome do Curso");
        txtDuracao.setPromptText("Duração (Semestres)");
        txtCoordenadorId.setPromptText("ID do Professor Coordenador");

        Button btnSalvar = new Button("Salvar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");

        btnSalvar.setOnAction(e -> adicionar());
        btnAtualizar.setOnAction(e -> atualizar());
        btnExcluir.setOnAction(e -> remover());

        HBox form = new HBox(10,
                new Label("Nome:"), txtNome,
                new Label("Duração:"), txtDuracao,
                new Label("ID Coordenador:"), txtCoordenadorId,
                btnSalvar, btnAtualizar, btnExcluir);

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

        getChildren().addAll(new Label("Gestão de Cursos"), form, tabela);
    }

    private void adicionar() {
        try {
            String nome = txtNome.getText().trim();
            int duracao = Integer.parseInt(txtDuracao.getText().trim());
            String coordenadorId = txtCoordenadorId.getText().trim();

            if (nome.isEmpty() || coordenadorId.isEmpty()) {
                mostrarAlerta("Erro", "Preencha todos os campos.");
                return;
            }

            dao.adicionar(new Curso(nome, duracao, coordenadorId));
            carregarTabela();
            limparCampos();
        } catch (NumberFormatException ex) {
            mostrarAlerta("Erro de Formato", "Duração deve ser um número inteiro.");
        }
    }

    private void atualizar() {
        if (itemSelecionado == null) {
            mostrarAlerta("Aviso", "Selecione um curso na tabela para atualizar.");
            return;
        }
        try {
            String nome = txtNome.getText().trim();
            int duracao = Integer.parseInt(txtDuracao.getText().trim());
            String coordenadorId = txtCoordenadorId.getText().trim();

            if (nome.isEmpty() || coordenadorId.isEmpty()) {
                mostrarAlerta("Erro", "Preencha todos os campos.");
                return;
            }

            dao.atualizar(itemSelecionado.getNome(), new Curso(nome, duracao, coordenadorId));
            carregarTabela();
            limparCampos();
        } catch (NumberFormatException ex) {
            mostrarAlerta("Erro de Formato", "Duração deve ser um número inteiro.");
        }
    }

    private void remover() {
        Curso selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Aviso", "Selecione um curso na tabela.");
            return;
        }

        dao.remover(selecionado.getNome());
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
        txtDuracao.clear();
        txtCoordenadorId.clear();
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
