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

        HBox form = new HBox(10, new Label("Data:"), txtData, chkPresente, new Label("Matrícula:"), txtMatricula, btnSalvar, btnExcluir);

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
            String data = txtData.getText();
            String matricula = txtMatricula.getText();
            boolean presente = chkPresente.isSelected();

            if (!data.matches("\\d{2}/\\d{2}/\\d{4}")) {
                throw new Exception("Formato de data inválido! Use DD/MM/AAAA.");
            }
            if(matricula.isEmpty()) throw new Exception("Informe a matrícula.");

            Frequencia nova = new Frequencia(data, presente, matricula);
            listaMemoria.add(nova);
            obsFrequencia.add(nova);
            dao.salvarDados(listaMemoria);

            txtData.clear(); txtMatricula.clear(); chkPresente.setSelected(false);
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }

    private void removerFrequencia() {
        Frequencia selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            listaMemoria.remove(selecionada);
            obsFrequencia.remove(selecionada);
            dao.salvarDados(listaMemoria);
        }
    }
}
