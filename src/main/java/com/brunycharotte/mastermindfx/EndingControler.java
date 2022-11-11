package com.brunycharotte.mastermindfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MasterMind_FX.fxml"));
        root = fxmlLoader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
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


}
