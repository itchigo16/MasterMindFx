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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class ChoisirCodeControler {


    Parent root;
    Scene scene;
    Stage stage;

    HashMap<Integer, Paint> ensembleCouleurs = new HashMap<>(6);

    int scoreJ1_int = 0;
    int scoreRobot = 0;

    @FXML
    Label scoreJoueur;

    @FXML
    Label scoreBot;

    @FXML
    Button vert;
    @FXML
    Button rouge;
    @FXML
    Button violet;
    @FXML
    Button orange;
    @FXML
    Button yellow;
    @FXML
    Button bleu;

    @FXML
    HBox rowCirclePick;

    @FXML
    Button boutonQuitter;

    int manche;
    @FXML
    Label labelManche;

    public void setManche(int manche) {
        this.manche = manche;
        labelManche.setText("manche numéro " + this.manche);
    }


    public void setEnsembleCouleurs(HashMap<Integer, Paint> ensembleCouleurs) {
        this.ensembleCouleurs = ensembleCouleurs;
    }

    public void updateScore(int scoreJoueur, int scoreRobot) {
        scoreJ1_int = scoreJoueur;
        this.scoreRobot = scoreRobot;
        this.scoreJoueur.setText(scoreJ1_int + "");
        this.scoreBot.setText(scoreRobot + "");
    }

    public void switchToBot(ActionEvent event) throws IOException {
        if (checkCode()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MasterMindBot.fxml"));
            root = fxmlLoader.load();

            BotControler controler = fxmlLoader.getController();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);

            controler.setEnsembleCouleurs(this.ensembleCouleurs);
            controler.updateScore(scoreJ1_int, scoreRobot);
            controler.setSecretCode(getSecretCodeFromColors());
            controler.updateSecretCodeRow();
            controler.setManche(this.manche);
            controler.updateManche(this.historiqueMancheSaver);
            controler.putFirstCode();

            stage.show();
        }

    }

    HistoriqueMancheSaver historiqueMancheSaver;
    public void setHistoriqueMancheSaver(HistoriqueMancheSaver historiqueMancheSaver) {
        this.historiqueMancheSaver = historiqueMancheSaver;
    }

    @FXML
    Pane leavingPane;
    @FXML
    Pane gamePanel;

    public void afficherQuitterMenu() {
        gamePanel.setEffect(new GaussianBlur());
        leavingPane.setVisible(true);
    }

    public void quitterQuitterMenu() {
        gamePanel.setEffect(null);
        leavingPane.setVisible(false);
    }

    private int[] getSecretCodeFromColors() {
        int[] secretCode = new int[4];
        String[] code = new String[4];
        for (int i = 0; i < 4; i++) {
            code[i] = ((Circle) rowCirclePick.getChildren().get(i)).getFill().toString();
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                if (ensembleCouleurs.get(j).toString().equals(code[i])) {
                    secretCode[i] = j;
                }
            }
        }
        System.out.println(Arrays.toString(secretCode));
        return secretCode;

    }

    private boolean checkCode() {
        for (int i = 0; i < 4; i++) { // lgCode
            if (((Circle) rowCirclePick.getChildren().get(i)).getFill().toString().equals("0xffffffff")) return false;
        }
        return true;
    }

    public void quitButton() {
        Stage stage1 = (Stage) boutonQuitter.getScene().getWindow();
        stage1.close();
    }

    public void removeCircle(MouseEvent event) {
        String id = event.getPickResult().getIntersectedNode().getId();
        String nb = id.charAt(id.length() - 1) + "";
        System.out.println(nb);
        ((Circle) rowCirclePick.getChildren().get(Integer.parseInt(nb) - 1)).setFill(Paint.valueOf("0xffffffff"));
    }

    private int checkEmptyCase() {
        for (int i = 0; i < 4; i++) { // 4 pour lgCode = 4
            Circle circle = ((Circle) rowCirclePick.getChildren().get(i));
            if (circle.getFill().toString().equals("0xffffffff")) {
                return i;
            }
        }
        return -1;
    }

    private Circle getRightCircle() {
        return ((Circle) rowCirclePick.getChildren().get(checkEmptyCase()));
    }


    public void onClickVert() {
        if (checkEmptyCase() != -1) {
            getRightCircle().setFill(vert.getBackground().getFills().get(0).getFill());
        }
    }

    public void onClickRed() {
        if (checkEmptyCase() != -1) {
            getRightCircle().setFill(rouge.getBackground().getFills().get(0).getFill());
        }

    }

    public void onClickViolet() {
        if (checkEmptyCase() != -1) {
            getRightCircle().setFill(violet.getBackground().getFills().get(0).getFill());
        }

    }

    public void onClickJaune() {
        if (checkEmptyCase() != -1) {
            getRightCircle().setFill(yellow.getBackground().getFills().get(0).getFill());
        }

    }

    public void onClickOrange() {
        if (checkEmptyCase() != -1) {
            getRightCircle().setFill(orange.getBackground().getFills().get(0).getFill());
        }

    }

    public void onClickBlue() {
        if (checkEmptyCase() != -1) {
            getRightCircle().setFill(bleu.getBackground().getFills().get(0).getFill());
        }

    }

}
