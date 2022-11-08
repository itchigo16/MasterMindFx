package com.brunycharotte.mastermindfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
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


    HashMap<Integer, Paint> ensembleCouleurs = new HashMap<>(6);
    int[] code = new int[4];

    private boolean canClick = true;

    int[] secretCode = MasterMindAlgo.codeAleat();
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
    Button changerButton;

    int activeRow = 16;

    private Parent root;
    private Scene scene;
    private Stage stage;


    public void switchToBot(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MasterMindBot.fxml"));
        root = fxmlLoader.load();
        BotControler controler = fxmlLoader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        controler.setEnsembleCouleurs(this.ensembleCouleurs);
        controler.putFirstCode();
        stage.show();
    }

    public void onClickVert() {
        if (checkEmptyCase() != -1 && canClick){
            getRightCircle().setFill(vert.getBackground().getFills().get(0).getFill());
        }

    }
    public void onClickRed() {
        if (checkEmptyCase() != -1 && canClick){
            getRightCircle().setFill(rouge.getBackground().getFills().get(0).getFill());
        }

    }
    public void onClickViolet() {
        if (checkEmptyCase() != -1 && canClick){
            getRightCircle().setFill(violet.getBackground().getFills().get(0).getFill());
        }

    }
    public void onClickJaune() {
        if (checkEmptyCase() != -1 && canClick){
            getRightCircle().setFill(yellow.getBackground().getFills().get(0).getFill());
        }

    }
    public void onClickOrange() {
        if (checkEmptyCase() != -1 && canClick){
            getRightCircle().setFill(orange.getBackground().getFills().get(0).getFill());
        }

    }
    public void onClickBlue() {
        if (checkEmptyCase() != -1 && canClick){
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
            if (circle.getFill().toString().equals("0xffffffff")){
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
                    changerButton.setVisible(true);
                } else if (activeRow > 1) activeRow -= 2; // MANCHE PAS FINIS
                else { // LOSE SI MANCHE FINIS
                    changerButton.setVisible(true);
                    canClick = false;
                    System.out.println("BAD");
                }

            }
        }
    }

    private void clearAll(){
        for (int i = 0; i <= 16; i++) {
            if (i%2 == 0) {
                HBox hBox = getNRow(i);
                for (int j = 0; j < 4; j++) {
                    Circle circle = (Circle) hBox.getChildren().get(j);
                    circle.setFill(Paint.valueOf("0xffffffff"));
                }
            }
        }
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

    public Button getYellow() {
        return yellow;
    }

    public Button getBleu() {
        return bleu;
    }

    public Button getOrange() {
        return orange;
    }

    public Button getRouge() {
        return rouge;
    }

    public Button getVert() {
        return vert;
    }

    public Button getViolet() {
        return violet;
    }

    public HBox getNRow(int n){
        HBox mainRow = (HBox) plateau.getChildren().get(n);
        return (HBox) mainRow.getChildren().get(1);
    }


}
