package com.schoolmanager.schoolmanager.view;

import com.schoolmanager.schoolmanager.model.Sala;
import com.schoolmanager.schoolmanager.repository.SalaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class SalaView {
    private final SalaDAO dao = new SalaDAO();
    private final TableView<Sala> tabela = new TableView<>();
    private final ObservableList<Sala> dadosTabela = FXCollections.observableArrayList();

    private final TextField txtNumero = new TextField();
    private final TextField txtBloco = new TextField();
    private final TextField txtCapacidade = new TextField();

    public VBox iniciarTela() {
        VBox painel = new VBox(10);
        painel.setPadding(new Insets(15));

        txtNumero.setPromptText("Ex: 101, 202-A");
        txtBloco.setPromptText("Ex: Bloco Azul, Prédio Politécnica");
        txtCapacidade.setPromptText("Capacidade (Ex: 35)");

        Button btnSalvar = new Button("Salvar Sala");
        btnSalvar.setOnAction(e -> executarCadastro());

        Button btnExcluir = new Button("Excluir Selecionada");
        btnExcluir.setOnAction(e -> executarExclusao());

        Button btnLimpar = new Button("Limpar");
        btnLimpar.setOnAction(e -> limparCampos());

        HBox form = new HBox(10, txtNumero, txtBloco, txtCapacidade, btnSalvar, btnExcluir, btnLimpar);

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
                txtNumero.setText(novo.getNumero());
                txtBloco.setText(novo.getBloco());
                txtCapacidade.setText(String.valueOf(novo.getCapacidade()));
            }
        });

        carregarTabela();

        painel.getChildren().addAll(
                new Label("Gestão de Salas"),
                form,
                new Label("Salas Cadastradas:"),
                tabela
        );
        return painel;
    }

    private void executarCadastro() {
        try {
            String numero = txtNumero.getText().trim();
            String bloco = txtBloco.getText().trim();
            String capacidadeStr = txtCapacidade.getText().trim();

            if (numero.isEmpty() || bloco.isEmpty() || capacidadeStr.isEmpty()) {
                mostrarAlerta("Validação", "Todos os campos devem ser preenchidos.");
                return;
            }

            int capacidade = Integer.parseInt(capacidadeStr);
            if (capacidade <= 0) {
                mostrarAlerta("Regra de Negócio", "A capacidade deve ser maior que zero.");
                return;
            }

            List<Sala> lista = dao.listarTodos();
            Sala existente = null;
            for (Sala s : lista) {
                if (s.getNumero().equalsIgnoreCase(numero)) {
                    existente = s;
                    break;
                }
            }

            if (existente != null) {
                existente.setBloco(bloco);
                existente.setCapacidade(capacidade);
                dao.salvarLista(lista);
            } else {
                dao.adicionar(new Sala(numero, bloco, capacidade));
            }

            limparCampos();
            carregarTabela();

        } catch (NumberFormatException ex) {
            mostrarAlerta("Erro de Tipo", "A capacidade aceita apenas números inteiros.");
        }
    }

    private void executarExclusao() {
        Sala selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Aviso", "Selecione uma sala para excluir.");
            return;
        }
        List<Sala> lista = dao.listarTodos();
        lista.removeIf(s -> s.getNumero().equalsIgnoreCase(selecionada.getNumero()));
        dao.salvarLista(lista);
        limparCampos();
        carregarTabela();
    }

    private void carregarTabela() {
        dadosTabela.clear();
        dadosTabela.addAll(dao.listarTodos());
    }

    private void limparCampos() {
        txtNumero.clear();
        txtBloco.clear();
        txtCapacidade.clear();
        tabela.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}