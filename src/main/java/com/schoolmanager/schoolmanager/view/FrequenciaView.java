package com.schoolmanager.schoolmanager.view;

import com.schoolmanager.schoolmanager.model.Frequencia;
import com.schoolmanager.schoolmanager.repository.FrequenciaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class FrequenciaView extends VBox {
    private TextField txtData, txtMatricula;
    private CheckBox chkPresente;
    private TableView<Frequencia> tabela;
    private ObservableList<Frequencia> obsFrequencia;
    private ArrayList<Frequencia> listaMemoria;
    private FrequenciaDAO dao;

    public FrequenciaView() {
        dao = new FrequenciaDAO();
        listaMemoria = dao.carregarDados();
        obsFrequencia = FXCollections.observableArrayList(listaMemoria);

        setPadding(new Insets(15));
        setSpacing(10);

        txtData = new TextField(); txtData.setPromptText("DD/MM/AAAA");
        chkPresente = new CheckBox("Presente?");
        txtMatricula = new TextField(); txtMatricula.setPromptText("ID Matrícula");

        Button btnSalvar = new Button("Salvar");
        Button btnExcluir = new Button("Excluir");

        HBox form = new HBox(10,
                new Label("Data:"), txtData,
                chkPresente,
                new Label("Matrícula:"), txtMatricula,
                btnSalvar, btnExcluir);

        tabela = new TableView<>();
        tabela.setItems(obsFrequencia);

        TableColumn<Frequencia, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(new PropertyValueFactory<>("dataAula"));

        TableColumn<Frequencia, Boolean> colStatus = new TableColumn<>("Presente");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("presente"));

        TableColumn<Frequencia, String> colMatricula = new TableColumn<>("Matrícula");
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matriculaId"));

        tabela.getColumns().addAll(colData, colStatus, colMatricula);

        btnSalvar.setOnAction(e -> adicionarFrequencia());
        btnExcluir.setOnAction(e -> removerFrequencia());

        getChildren().addAll(new Label("Registro de Frequência"), form, tabela);
    }

    private void adicionarFrequencia() {
        try {
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

            Frequencia nova = new Frequencia(data, presente, matricula);
            listaMemoria.add(nova);
            obsFrequencia.add(nova);
            dao.salvarDados(listaMemoria);

            txtData.clear(); txtMatricula.clear(); chkPresente.setSelected(false);

        } catch (Exception ex) {
            mostrarAlerta("Erro", ex.getMessage() != null ? ex.getMessage() : "Erro inesperado.");
        }
    }

    private void removerFrequencia() {
        Frequencia selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Aviso", "Selecione um registro de frequência para excluir.");
            return;
        }
        listaMemoria.remove(selecionada);
        obsFrequencia.remove(selecionada);
        dao.salvarDados(listaMemoria);
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
