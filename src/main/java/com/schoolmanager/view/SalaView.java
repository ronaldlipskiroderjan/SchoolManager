package com.schoolmanager.view;

import com.schoolmanager.model.Sala;
import com.schoolmanager.repository.SalaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SalaView extends VBox {

    private final SalaDAO dao = new SalaDAO();
    private final TableView<Sala> tabela = new TableView<>();
    private final ObservableList<Sala> dadosTabela = FXCollections.observableArrayList();

    private final TextField txtNumero = new TextField();
    private final TextField txtBloco = new TextField();
    private final TextField txtCapacidade = new TextField();

    private Sala itemSelecionado;

    public SalaView() {
        setPadding(new Insets(15));
        setSpacing(10);

        txtNumero.setPromptText("Ex: 101, 202-A");
        txtBloco.setPromptText("Ex: Bloco Azul, Prédio Politécnica");
        txtCapacidade.setPromptText("Capacidade (Ex: 35)");

        Button btnSalvar = new Button("Salvar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");

        btnSalvar.setOnAction(e -> adicionar());
        btnAtualizar.setOnAction(e -> atualizar());
        btnExcluir.setOnAction(e -> remover());

        HBox form = new HBox(10,
                new Label("Número:"), txtNumero,
                new Label("Bloco:"), txtBloco,
                new Label("Capacidade:"), txtCapacidade,
                btnSalvar, btnAtualizar, btnExcluir);

        TableColumn<Sala, String> colNumero = new TableColumn<>("Número");
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));

        TableColumn<Sala, String> colBloco = new TableColumn<>("Bloco");
        colBloco.setCellValueFactory(new PropertyValueFactory<>("bloco"));

        TableColumn<Sala, Integer> colCapacidade = new TableColumn<>("Capacidade");
        colCapacidade.setCellValueFactory(new PropertyValueFactory<>("capacidade"));

        tabela.getColumns().addAll(colNumero, colBloco, colCapacidade);
        tabela.setItems(dadosTabela);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                itemSelecionado = novo;
                txtNumero.setText(novo.getNumero());
                txtBloco.setText(novo.getBloco());
                txtCapacidade.setText(String.valueOf(novo.getCapacidade()));
            }
        });

        carregarTabela();

        getChildren().addAll(new Label("Gestão de Salas"), form, tabela);
    }

    private void adicionar() {
        try {
            String numero = txtNumero.getText().trim();
            String bloco = txtBloco.getText().trim();
            int capacidade = Integer.parseInt(txtCapacidade.getText().trim());

            if (numero.isEmpty() || bloco.isEmpty()) {
                mostrarAlerta("Validação", "Todos os campos devem ser preenchidos.");
                return;
            }

            if (capacidade <= 0) {
                mostrarAlerta("Regra de Negócio", "A capacidade deve ser maior que zero.");
                return;
            }

            dao.adicionar(new Sala(numero, bloco, capacidade));
            carregarTabela();
            limparCampos();
        } catch (NumberFormatException ex) {
            mostrarAlerta("Erro de Tipo", "A capacidade aceita apenas números inteiros.");
        }
    }

    private void atualizar() {
        if (itemSelecionado == null) {
            mostrarAlerta("Aviso", "Selecione uma sala na tabela para atualizar.");
            return;
        }
        try {
            String numero = txtNumero.getText().trim();
            String bloco = txtBloco.getText().trim();
            int capacidade = Integer.parseInt(txtCapacidade.getText().trim());

            if (numero.isEmpty() || bloco.isEmpty()) {
                mostrarAlerta("Validação", "Todos os campos devem ser preenchidos.");
                return;
            }

            if (capacidade <= 0) {
                mostrarAlerta("Regra de Negócio", "A capacidade deve ser maior que zero.");
                return;
            }

            dao.atualizar(itemSelecionado.getNumero(), new Sala(numero, bloco, capacidade));
            carregarTabela();
            limparCampos();
        } catch (NumberFormatException ex) {
            mostrarAlerta("Erro de Tipo", "A capacidade aceita apenas números inteiros.");
        }
    }

    private void remover() {
        Sala selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Aviso", "Selecione uma sala para excluir.");
            return;
        }

        dao.remover(selecionada.getNumero());
        carregarTabela();
        limparCampos();
    }

    private void carregarTabela() {
        dadosTabela.clear();
        dadosTabela.addAll(dao.listarTodos());
        itemSelecionado = null;
    }

    private void limparCampos() {
        txtNumero.clear();
        txtBloco.clear();
        txtCapacidade.clear();
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
