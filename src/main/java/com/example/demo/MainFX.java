package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainFX extends Application {

    public static No raiz = new No();

    @Override
    public void start(Stage stage) {
        carregarTabela();

        Label labelMorse = new Label("Morse: ");
        TextField campoMorse = new TextField();
        Button btnTraduzir = new Button("Traduzir");
        Label labelResultado = new Label("Resultado: ");

        FlowPane painelTopo = new FlowPane();
        painelTopo.setHgap(10);
        painelTopo.getChildren().add(labelMorse);
        painelTopo.getChildren().add(campoMorse);
        painelTopo.getChildren().add(btnTraduzir);
        painelTopo.getChildren().add(labelResultado);

        Canvas canvas = new Canvas(1000, 550);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        VBox layout = new VBox();
        layout.getChildren().add(painelTopo);
        layout.getChildren().add(canvas);

        btnTraduzir.setOnAction(e -> {
            String textoDigitado = campoMorse.getText();
            String textoTraduzido = traduzir(textoDigitado);
            labelResultado.setText("Resultado: " + textoTraduzido);
        });

        desenharArvore(gc, 500, 40, 240, 50);

        Scene cena = new Scene(layout, 1000, 600);
        stage.setTitle("Árvore Binária - Código Morse");
        stage.setScene(cena);
        stage.show();
    }

    public void desenharArvore(GraphicsContext gc, double x, double y, double dx, double dy) {
        desenharNo(gc, raiz, x, y, dx, dy);
    }

    public void desenharNo(GraphicsContext gc, No no, double x, double y, double dx, double dy) {
        if (no == null) {
            return;
        }

        if (no.filhoEsquerdo != null) {
            gc.setStroke(Color.GRAY);
            gc.strokeLine(x, y, x - dx, y + dy);
            gc.setFill(Color.BLUE);
            gc.fillText(".", x - (dx / 2) - 5, y + (dy / 2));
            desenharNo(gc, no.filhoEsquerdo, x - dx, y + dy, dx / 2, dy);
        }

        if (no.filhoDireito != null) {
            gc.setStroke(Color.GRAY);
            gc.strokeLine(x, y, x + dx, y + dy);
            gc.setFill(Color.RED);
            gc.fillText("-", x + (dx / 2) + 5, y + (dy / 2));
            desenharNo(gc, no.filhoDireito, x + dx, y + dy, dx / 2, dy);
        }

        gc.setFill(Color.LIGHTBLUE);
        gc.fillOval(x - 12, y - 12, 24, 24);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - 12, y - 12, 24, 24);

        String letra = "*";
        if (no.caractere != null) {
            letra = no.caractere.toString();
        }

        gc.setFill(Color.BLACK);
        gc.fillText(letra, x - 4, y + 4);
    }

    public static void inserir(String morse, char letra) {
        No atual = raiz;
        for (int i = 0; i < morse.length(); i++) {
            char c = morse.charAt(i);
            if (c == '.') {
                if (atual.filhoEsquerdo == null) {
                    atual.filhoEsquerdo = new No();
                }
                atual = atual.filhoEsquerdo;
            } else if (c == '-') {
                if (atual.filhoDireito == null) {
                    atual.filhoDireito = new No();
                }
                atual = atual.filhoDireito;
            }
        }
        atual.caractere = letra;
    }

    public static Character buscar(String morse) {
        No atual = raiz;
        for (int i = 0; i < morse.length(); i++) {
            char c = morse.charAt(i);
            if (c == '.') {
                atual = atual.filhoEsquerdo;
            } else if (c == '-') {
                atual = atual.filhoDireito;
            }

            if (atual == null) {
                return null;
            }
        }
        return atual.caractere;
    }

    public static String traduzir(String morse) {
        if (morse == null || morse.trim().equals("")) {
            return "";
        }

        String resultado = "";
        String[] palavras = morse.split(" / ");

        for (int i = 0; i < palavras.length; i++) {
            String[] simbolos = palavras[i].trim().split(" ");
            for (int j = 0; j < simbolos.length; j++) {
                if (simbolos[j].equals("")) continue;

                Character letra = buscar(simbolos[j]);
                if (letra != null) {
                    resultado = resultado + letra;
                } else {
                    resultado = resultado + "?";
                }
            }
            resultado = resultado + " ";
        }

        return resultado.trim();
    }

    public static void carregarTabela() {
        inserir(".-", 'A');    inserir("-...", 'B');  inserir("-.-.", 'C');
        inserir("-..", 'D');   inserir(".", 'E');     inserir("..-.", 'F');
        inserir("--.", 'G');   inserir("....", 'H');  inserir("..", 'I');
        inserir(".---", 'J');  inserir("-.-", 'K');   inserir(".-..", 'L');
        inserir("--", 'M');    inserir("-.", 'N');    inserir("---", 'O');
        inserir(".--.", 'P');  inserir("--.-", 'Q');  inserir(".-.", 'R');
        inserir("...", 'S');   inserir("-", 'T');     inserir("..-", 'U');
        inserir("...-", 'V');  inserir(".--", 'W');   inserir("-..-", 'X');
        inserir("-.--", 'Y');  inserir("--..", 'Z');

        inserir("-----", '0'); inserir(".----", '1'); inserir("..---", '2');
        inserir("...--", '3'); inserir("....-", '4'); inserir(".....", '5');
        inserir("-....", '6'); inserir("--...", '7'); inserir("---..", '8');
        inserir("----.", '9');
    }

    public static void main(String[] args) {
        launch(args);
    }
}