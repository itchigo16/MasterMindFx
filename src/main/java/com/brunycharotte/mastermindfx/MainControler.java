package com.brunycharotte.mastermindfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class MainControler {


    int scoreJ1_int = 0;
    int scoreRobot = 0;
    HashMap<Integer, Paint> ensembleCouleurs = new HashMap<>(6);

    private boolean canClick = true;



    int[] secretCode = MasterMindAlgo.codeAleat();

    @FXML
    Label secretCodeLabel;
    @FXML
    Button validerButton;
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
    VBox plateau;
    @FXML
    Button boutonEffacer;

    @FXML
    Button changerButton;

    @FXML
    Button boutonQuitter;

    int activeRow = 16;

    Parent root;
    Scene scene;
    Stage stage;

    int manche = 1;
    @FXML
    Label labelManche;


    public void setManche(int manche) {
        this.manche = manche;
        labelManche.setText("manche numéro " + this.manche);

    }

    public void updateScore(int scoreJoueur, int scoreRobot) {
        scoreJ1_int = scoreJoueur;
        this.scoreRobot = scoreRobot;
        this.scoreJoueur.setText(scoreJ1_int + "");
        this.scoreBot.setText(scoreRobot + "");
    }

    public void switchToBot(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MasterMindPickCode.fxml"));
        root = fxmlLoader.load();
        ChoisirCodeControler controler = fxmlLoader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        controler.setEnsembleCouleurs(this.ensembleCouleurs);
        controler.updateScore(scoreJ1_int, scoreRobot);
        manche++;
        controler.setManche(this.manche++);
        stage.show();
    }

    public void onClickVert() {
        if (checkEmptyCase() != -1 && canClick) {
            getRightCircle().setFill(vert.getBackground().getFills().get(0).getFill());
        }
    }

    public void onClickRed() {
        if (checkEmptyCase() != -1 && canClick) {
            getRightCircle().setFill(rouge.getBackground().getFills().get(0).getFill());
        }

    }

    public void onClickViolet() {
        if (checkEmptyCase() != -1 && canClick) {
            getRightCircle().setFill(violet.getBackground().getFills().get(0).getFill());
        }

    }

    public void onClickJaune() {
        if (checkEmptyCase() != -1 && canClick) {
            getRightCircle().setFill(yellow.getBackground().getFills().get(0).getFill());
        }

    }

    public void onClickOrange() {
        if (checkEmptyCase() != -1 && canClick) {
            getRightCircle().setFill(orange.getBackground().getFills().get(0).getFill());
        }

    }

    public void onClickBlue() {
        if (checkEmptyCase() != -1 && canClick) {
            getRightCircle().setFill(bleu.getBackground().getFills().get(0).getFill());
        }

    }

    public HBox getCurrentRow() {
        HBox mainRow = (HBox) plateau.getChildren().get(activeRow);
        return (HBox) mainRow.getChildren().get(1);
    }

    private Circle getRightCircle() {
        HBox lastRow = (HBox) plateau.getChildren().get(activeRow);
        HBox idk = (HBox) lastRow.getChildren().get(1);
        return ((Circle) idk.getChildren().get(checkEmptyCase()));
    }

    private int checkEmptyCase() {
        HBox lastRow = (HBox) plateau.getChildren().get(activeRow);
        HBox hBox = (HBox) lastRow.getChildren().get(1);
        for (int i = 0; i < 4; i++) { // 4 pour lgCode = 4
            Circle circle = ((Circle) hBox.getChildren().get(i));
            if (circle.getFill().toString().equals("0xffffffff")) {
                return i;
            }
        }
        return -1;
    }


    private void setEnsembleCouleurs() {
        Button[] listeButton = {vert, rouge, bleu, violet, orange, yellow};
        int i = 0;
        for (Button bouton :
                listeButton) {
            ensembleCouleurs.put(i, bouton.getBackground().getFills().get(0).getFill());
            i++;
        }
    }

    public void setEnsembleCouleurs(HashMap<Integer, Paint> ensembleCouleurs) {
        this.ensembleCouleurs = ensembleCouleurs;
    }

    public void envoyerCode() {
        if (canClick) {
            System.out.println(Arrays.toString(secretCode));
            setEnsembleCouleurs();
            if (checkEmptyCase() == -1) {
                String[] code = new String[4];
                for (int i = 0; i < 4; i++) {
                    code[i] = ((Circle) getCurrentRow().getChildren().get(i)).getFill().toString();
                }
                int[] intCode = new int[4];
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 6; j++) {
                        if (ensembleCouleurs.get(j).toString().equals(code[i])) {
                            intCode[i] = j;
                        }
                    }

                }
                System.out.println(Arrays.toString(intCode));


                placeBienMalPlace(MasterMindAlgo.nbBienMalPlaces(secretCode, intCode));
                if (Arrays.equals(secretCode, intCode)) { // WIN
                    System.out.println("gg");
                    canClick = false;
                    validerButton.setVisible(false);
                    changerButton.setVisible(true);

                    scoreRobot += Integer.parseInt(((Label) ((HBox) plateau.getChildren().get(activeRow)).getChildren().get(0)).getText().charAt(0) + "");
                } else if (activeRow > 1) {
                    updateRowIDHighlight();
                    activeRow -= 2;
                } // MANCHE PAS FINIS
                else { // LOSE SI MANCHE FINIS
                    int[] nbBienMalPlaces = MasterMindAlgo.nbBienMalPlaces(secretCode, intCode);

                    scoreRobot += Integer.parseInt(((Label) ((HBox) plateau.getChildren().get(activeRow)).getChildren().get(0)).getText().charAt(0) + "");
                    scoreRobot += nbBienMalPlaces[1] + 2 * (intCode.length - (nbBienMalPlaces[0] + nbBienMalPlaces[1]));

                    // Malus : nbMalPlaces + 2 × (lgCode − (nbBienPlaces + nbMalPlaces))
                    setSecretCodeLabel();
                    secretCodeLabel.setVisible(true);
                    boutonEffacer.setVisible(false);

                    changerButton.setVisible(true);
                    canClick = false;
                    System.out.println("BAD");
                }
            }
        }
    }

    private void setSecretCodeLabel() {
        Paint[] code = intToPaintCode();
        HBox hBox = (HBox) secretCodeLabel.getChildrenUnmodifiable().get(0);
        for (int i = 0; i < 4; i++) {
            Circle circle = (Circle) hBox.getChildren().get(i);
            circle.setFill(code[i]);
        }
    }

    private Paint[] intToPaintCode() {
        Paint[] paints = new Paint[4];
        for (int i = 0; i < 4; i++) {
            paints[i] = ensembleCouleurs.get(secretCode[i]);
        }
        return paints;
    }

    private void updateRowIDHighlight() {
        HBox mainRow = (HBox) plateau.getChildren().get(activeRow);
        Label elt = (Label) mainRow.getChildren().get(0);
        elt.setId("");
        elt.setOpacity(0.25);
        mainRow = (HBox) plateau.getChildren().get(activeRow - 2);
        elt = (Label) mainRow.getChildren().get(0);
        elt.setId("gros");
    }


    public void quitButton() {
        Stage stage1 = (Stage) boutonQuitter.getScene().getWindow();
        stage1.close();
    }

    private void placeBienMalPlace(int[] nbBienMalPlace) {
        GridPane gridPane = getActiveNbBienMal();
        for (int i = 0; i < nbBienMalPlace[0]; i++) {
            ((Circle) gridPane.getChildren().get(i)).setFill(ensembleCouleurs.get(0));
        }
        for (int i = 0; i < nbBienMalPlace[1]; i++) {
            ((Circle) gridPane.getChildren().get(i + nbBienMalPlace[0])).setFill(ensembleCouleurs.get(1));
        }
    }

    private GridPane getActiveNbBienMal() {
        HBox activeRowBox = (HBox) plateau.getChildren().get(activeRow);
        return (GridPane) activeRowBox.getChildren().get(2);
    }

    public void removeLastCircle() {
        if (canClick) {
            HBox activeRowBOX = (HBox) plateau.getChildren().get(activeRow);
            HBox hBox = (HBox) activeRowBOX.getChildren().get(1);
            for (int i = 3; i >= 0; i--) {
                Circle circle = (Circle) hBox.getChildren().get(i);
                if (!circle.getFill().toString().equals("0xffffffff")) {
                    circle.setFill(Paint.valueOf("0xffffffff"));
                    return;
                }
            }
        }
    }

}
