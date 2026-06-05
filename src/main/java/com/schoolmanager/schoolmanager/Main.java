package com.schoolmanager.schoolmanager;

import com.schoolmanager.schoolmanager.view.AvaliacaoView;
import com.schoolmanager.schoolmanager.view.FrequenciaView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

    private BorderPane painelPrincipal;

    @Override
    public void start(Stage palcoPrimario) {
        painelPrincipal = new BorderPane();

        VBox menuLateral = new VBox(15);
        menuLateral.setPadding(new Insets(20));
        menuLateral.setStyle("-fx-background-color: #2c3e50;");
        menuLateral.setPrefWidth(220);

        Label tituloMenu = new Label("Menu Escolar");
        tituloMenu.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        tituloMenu.setStyle("-fx-text-fill: white;");

        Button btnAlunos = criarBotaoMenu("1. Discentes");
        Button btnProfessores = criarBotaoMenu("2. Docentes");
        Button btnCursos = criarBotaoMenu("3. Organização");
        Button btnMatriculas = criarBotaoMenu("4. Alocação");

        Button btnAvaliacoes = criarBotaoMenu("5. Avaliações (Notas)");
        Button btnFrequencias = criarBotaoMenu("5. Frequência (Chamada)");

        btnAvaliacoes.setOnAction(e -> painelPrincipal.setCenter(new AvaliacaoView()));
        btnFrequencias.setOnAction(e -> painelPrincipal.setCenter(new FrequenciaView()));

        menuLateral.getChildren().addAll(
                tituloMenu,
                new Label(" "), // Espaçador
                btnAlunos, btnProfessores, btnCursos, btnMatriculas,
                new Label(" "), // Espaçador
                btnAvaliacoes, btnFrequencias
        );

        VBox telaBoasVindas = new VBox(10);
        telaBoasVindas.setAlignment(Pos.CENTER);
        Label lblBemVindo = new Label("Bem-vindo ao Sistema de Gestão Escolar");
        lblBemVindo.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        Label lblInstrucao = new Label("Selecione um módulo no menu lateral para iniciar.");
        telaBoasVindas.getChildren().addAll(lblBemVindo, lblInstrucao);

        painelPrincipal.setLeft(menuLateral);
        painelPrincipal.setCenter(telaBoasVindas);

        Scene cena = new Scene(painelPrincipal, 900, 600);
        palcoPrimario.setTitle("Sistema de Gestão Escolar - RA3");
        palcoPrimario.setScene(cena);
        palcoPrimario.show();
    }

    private Button criarBotaoMenu(String texto) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPadding(new Insets(10));
        btn.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-cursor: hand;");

        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #1abc9c; -fx-text-fill: white; -fx-cursor: hand;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-cursor: hand;"));

        return btn;
    }

    public static void main(String[] args) {
        launch(args);
    }
}