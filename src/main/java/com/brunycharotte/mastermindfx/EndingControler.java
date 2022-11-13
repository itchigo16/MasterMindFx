package com.brunycharotte.mastermindfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class EndingControler {

    @FXML
    Button boutonQuitter;
    @FXML
    Label winnerLabel;
    @FXML
    Label scoreBot;
    @FXML
    Label scoreJoueur;
    Parent root;
    Scene scene;
    Stage stage;

    public void restart(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MasterMindPlayer.fxml"));
        root = fxmlLoader.load();
        MainControler controler = fxmlLoader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        controler.setManche(1);
        controler.updateScore(0, 0);
        controler.setHistoriqueMancheSaver(new HistoriqueMancheSaver());
        stage.show();
    }

    public void setPlayerWinner() {
        winnerLabel.setText("Bien joué! Vous avez battut le robot !");
    }

    public void setRobotWinner() {
        winnerLabel.setText("Le robot a gagné... Dommage!");
    }


    public void updateScore(int scoreJoueur, int scoreRobot) {
        this.scoreJoueur.setText(scoreJoueur + "");
        this.scoreBot.setText(scoreRobot + "");
        if (scoreJoueur > scoreRobot) {
            setPlayerWinner();
        } else {
            setRobotWinner();
        }

    }

    public void quitButton() {
        Stage stage1 = (Stage) boutonQuitter.getScene().getWindow();
        stage1.close();
    }

    HistoriqueMancheSaver historiqueMancheSaver;


    public void updateManche(HistoriqueMancheSaver historiqueMancheSaver) {
        this.historiqueMancheSaver = historiqueMancheSaver;
        for (int i = 0; i < 10; i++) {
            VBox historyBox = (VBox) historiquePane1.getChildren().get(0);
            GridPane pane = (GridPane) historyBox.getChildren().get(i);
            GridPane gridPane = (GridPane) pane.getChildren().get(1);
            Label labelWinner = (Label) gridPane.getChildren().get(1);
            Label labelPts = (Label) gridPane.getChildren().get(2);
            labelWinner.setText("WINNER: " + historiqueMancheSaver.saveWinners[i]);
            labelPts.setText("PTS gagnés: " + historiqueMancheSaver.savePTS[i]);
        }

    }

    @FXML
    Pane historiquePane1;
    @FXML
    Pane gamePanel;
    public void afficherHistorique() {
        gamePanel.setEffect(new GaussianBlur());
        historiquePane1.setVisible(true);
    }

    public void enleverHistorique() {
        gamePanel.setEffect(null);
        historiquePane1.setVisible(false);
    }



}
