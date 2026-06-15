package com.schoolmanager;

import com.schoolmanager.view.AlunoView;
import com.schoolmanager.view.AvaliacaoView;
import com.schoolmanager.view.CursoView;
import com.schoolmanager.view.DisciplinaView;
import com.schoolmanager.view.FrequenciaView;
import com.schoolmanager.view.MatriculaView;
import com.schoolmanager.view.ProfessorView;
import com.schoolmanager.view.ResponsavelView;
import com.schoolmanager.view.SalaView;
import com.schoolmanager.view.TurmaView;
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

        VBox menuLateral = new VBox(10);
        menuLateral.setPadding(new Insets(20));
        menuLateral.setStyle("-fx-background-color: #2c3e50;");
        menuLateral.setPrefWidth(220);

        Label tituloMenu = new Label("Menu Escolar");
        tituloMenu.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        tituloMenu.setStyle("-fx-text-fill: white;");

        Button btnAlunos = criarBotaoMenu("1. Discentes");
        Button btnProfessores = criarBotaoMenu("2. Docentes");
        Button btnDisciplinas = criarBotaoMenu("3. Disciplinas");
        Button btnResponsaveis = criarBotaoMenu("4. Responsáveis");
        Button btnCursos = criarBotaoMenu("5. Cursos");
        Button btnTurmas = criarBotaoMenu("6. Turmas");
        Button btnAvaliacoes = criarBotaoMenu("7. Notas");
        Button btnFrequencias = criarBotaoMenu("8. Frequência");
        Button btnSalas = criarBotaoMenu("9. Salas");
        Button btnMatriculas = criarBotaoMenu("10. Matrículas");

        btnAlunos.setOnAction(e -> painelPrincipal.setCenter(new AlunoView()));
        btnProfessores.setOnAction(e -> painelPrincipal.setCenter(new ProfessorView()));
        btnDisciplinas.setOnAction(e -> painelPrincipal.setCenter(new DisciplinaView()));
        btnResponsaveis.setOnAction(e -> painelPrincipal.setCenter(new ResponsavelView()));
        btnCursos.setOnAction(e -> painelPrincipal.setCenter(new CursoView()));
        btnTurmas.setOnAction(e -> painelPrincipal.setCenter(new TurmaView()));
        btnAvaliacoes.setOnAction(e -> painelPrincipal.setCenter(new AvaliacaoView()));
        btnFrequencias.setOnAction(e -> painelPrincipal.setCenter(new FrequenciaView()));
        btnSalas.setOnAction(e -> painelPrincipal.setCenter(new SalaView()));
        btnMatriculas.setOnAction(e -> painelPrincipal.setCenter(new MatriculaView()));

        menuLateral.getChildren().addAll(
                tituloMenu,
                btnAlunos,
                btnProfessores,
                btnDisciplinas,
                btnResponsaveis,
                btnCursos,
                btnTurmas,
                btnAvaliacoes,
                btnFrequencias,
                btnSalas,
                btnMatriculas
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
        palcoPrimario.setTitle("Sistema de Gestão Escolar");
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