package com.schoolmanager.view;

import com.schoolmanager.model.Frequencia;
import com.schoolmanager.repository.FrequenciaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FrequenciaView extends VBox {

    private final FrequenciaDAO dao = new FrequenciaDAO();
    private final TableView<Frequencia> tabela = new TableView<>();
    private final ObservableList<Frequencia> dadosTabela = FXCollections.observableArrayList();

    private final TextField txtData = new TextField();
    private final CheckBox chkPresente = new CheckBox("Presente?");
    private final TextField txtMatricula = new TextField();

    private Frequencia itemSelecionado;

    public FrequenciaView() {
        setPadding(new Insets(15));
        setSpacing(10);

        txtData.setPromptText("DD/MM/AAAA");
        txtMatricula.setPromptText("ID Matrícula");

        Button btnSalvar = new Button("Salvar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");

        btnSalvar.setOnAction(e -> adicionar());
        btnAtualizar.setOnAction(e -> atualizar());
        btnExcluir.setOnAction(e -> remover());

        HBox form = new HBox(10,
                new Label("Data:"), txtData,
                chkPresente,
                new Label("Matrícula:"), txtMatricula,
                btnSalvar, btnAtualizar, btnExcluir);

        TableColumn<Frequencia, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(new PropertyValueFactory<>("dataAula"));

        TableColumn<Frequencia, Boolean> colStatus = new TableColumn<>("Presente");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("presente"));

        TableColumn<Frequencia, String> colMatricula = new TableColumn<>("Matrícula");
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matriculaId"));

        tabela.getColumns().add(colData);
        tabela.getColumns().add(colStatus);
        tabela.getColumns().add(colMatricula);
        tabela.setItems(dadosTabela);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                itemSelecionado = novo;
                txtData.setText(novo.getDataAula());
                chkPresente.setSelected(novo.isPresente());
                txtMatricula.setText(novo.getMatriculaId());
            }
        });

        carregarTabela();

        getChildren().addAll(new Label("Registro de Frequência"), form, tabela);
    }

    private void adicionar() {
        String data = txtData.getText().trim();
        String matricula = txtMatricula.getText().trim();
        boolean presente = chkPresente.isSelected();

        if (data.isEmpty() || matricula.isEmpty()) {
            mostrarAlerta("Validação", "Preencha todos os campos obrigatórios.");
            return;
        }

        if (!data.matches("\\d{2}/\\d{2}/\\d{4}")) {
            mostrarAlerta("Formatação", "A data deve seguir o padrão DD/MM/AAAA.");
            return;
        }

        dao.adicionar(new Frequencia(data, presente, matricula));
        carregarTabela();
        limparCampos();
    }

    private void atualizar() {
        if (itemSelecionado == null) {
            mostrarAlerta("Aviso", "Selecione um registro de frequência para atualizar.");
            return;
        }

        String data = txtData.getText().trim();
        String matricula = txtMatricula.getText().trim();
        boolean presente = chkPresente.isSelected();

        if (data.isEmpty() || matricula.isEmpty()) {
            mostrarAlerta("Validação", "Preencha todos os campos obrigatórios.");
            return;
        }

        if (!data.matches("\\d{2}/\\d{2}/\\d{4}")) {
            mostrarAlerta("Formatação", "A data deve seguir o padrão DD/MM/AAAA.");
            return;
        }

        dao.atualizar(itemSelecionado.getMatriculaId(), itemSelecionado.getDataAula(),
                new Frequencia(data, presente, matricula));
        carregarTabela();
        limparCampos();
    }

    private void remover() {
        Frequencia selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Aviso", "Selecione um registro de frequência para excluir.");
            return;
        }

        dao.remover(selecionada.getMatriculaId(), selecionada.getDataAula());
        carregarTabela();
        limparCampos();
    }

    private void carregarTabela() {
        dadosTabela.clear();
        dadosTabela.addAll(dao.listarTodos());
        itemSelecionado = null;
    }

    private void limparCampos() {
        txtData.clear();
        chkPresente.setSelected(false);
        txtMatricula.clear();
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
