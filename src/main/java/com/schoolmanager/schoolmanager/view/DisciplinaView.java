package com.schoolmanager.schoolmanager.view;

import com.schoolmanager.schoolmanager.model.Disciplina;
import com.schoolmanager.schoolmanager.repository.DisciplinaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class DisciplinaView extends VBox {
    private TextField txtCodigo, txtNome, txtCargaHoraria, txtEmenta, txtProfessorId;
    private TableView<Disciplina> tabela;
    private ObservableList<Disciplina> obsDisciplinas;
    private ArrayList<Disciplina> listaMemoria;
    private DisciplinaDAO dao;

    public DisciplinaView() {
        dao = new DisciplinaDAO();
        listaMemoria = dao.listarTodos();
        obsDisciplinas = FXCollections.observableArrayList(listaMemoria);

        setPadding(new Insets(15));
        setSpacing(10);

        txtCodigo = new TextField(); txtCodigo.setPromptText("Código (ex: MAT001)");
        txtNome = new TextField(); txtNome.setPromptText("Nome da Disciplina");
        txtCargaHoraria = new TextField(); txtCargaHoraria.setPromptText("Carga Horária (h)");
        txtEmenta = new TextField(); txtEmenta.setPromptText("Ementa");
        txtProfessorId = new TextField(); txtProfessorId.setPromptText("CPF do Professor");

        Button btnSalvar = new Button("Salvar");
        Button btnExcluir = new Button("Excluir");

        HBox form = new HBox(10,
                new Label("Código:"), txtCodigo,
                new Label("Nome:"), txtNome,
                new Label("Carga (h):"), txtCargaHoraria,
                new Label("Ementa:"), txtEmenta,
                new Label("CPF Professor:"), txtProfessorId,
                btnSalvar, btnExcluir);

        tabela = new TableView<>();
        tabela.setItems(obsDisciplinas);

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

        btnSalvar.setOnAction(e -> adicionarDisciplina());
        btnExcluir.setOnAction(e -> removerDisciplina());

        getChildren().addAll(new Label("Gerenciamento de Disciplinas"), form, tabela);
    }

    private void adicionarDisciplina() {
        try {
            String codigo = txtCodigo.getText();
            String nome = txtNome.getText();
            int cargaHoraria = Integer.parseInt(txtCargaHoraria.getText());
            String ementa = txtEmenta.getText();
            String professorId = txtProfessorId.getText();

            if (codigo.isEmpty() || nome.isEmpty() || ementa.isEmpty() || professorId.isEmpty()) {
                mostrarAlerta("Erro", "Preencha todos os campos.");
                return;
            }

            Disciplina nova = new Disciplina(codigo, nome, cargaHoraria, ementa, professorId);
            listaMemoria.add(nova);
            obsDisciplinas.add(nova);
            dao.salvarTodos(listaMemoria);

            txtCodigo.clear(); txtNome.clear(); txtCargaHoraria.clear();
            txtEmenta.clear(); txtProfessorId.clear();
        } catch (NumberFormatException ex) {
            mostrarAlerta("Erro de Formato", "Carga horária deve ser um número inteiro.");
        } catch (Exception ex) {
            mostrarAlerta("Erro", ex.getMessage());
        }
    }

    private void removerDisciplina() {
        Disciplina selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            listaMemoria.remove(selecionada);
            obsDisciplinas.remove(selecionada);
            dao.salvarTodos(listaMemoria);
        } else {
            mostrarAlerta("Aviso", "Selecione uma disciplina na tabela.");
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
