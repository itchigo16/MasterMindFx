package com.brunycharotte.mastermindfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class BotControler {

    Parent root;
    Scene scene;
    Stage stage;

    int[][] cod = new int[9][4];
    int[][] rep = new int[9][2];

    int[] secretCode;

    @FXML
    Slider bienPlaceSlider;

    @FXML
    HBox rowSecretCode;

    @FXML
    Slider malPlaceSlider;

    int activeRow = 16;

    @FXML
    VBox plateau;


    int scoreJ1_int = 0;
    int scoreRobot = 0;

    @FXML
    Label scoreJoueur;
    @FXML
    Label scoreBot;

    boolean canClick = true;

    @FXML
    Button changerButton;
    @FXML
    Button validerButton;
    int manche;
    @FXML
    Label labelManche;
    @FXML
    Button boutonQuitter;

    public void setManche(int manche) {
        this.manche = manche;
        labelManche.setText("manche numéro " + this.manche);
    }

    HashMap<Integer, Paint> ensembleCouleurs = new HashMap<>(6);

    public void setSecretCode(int[] secretCode) {
        this.secretCode = secretCode;
    }

    public void updateScore(int scoreJoueur, int scoreRobot) {
        scoreJ1_int = scoreJoueur;
        this.scoreRobot = scoreRobot;
        this.scoreJoueur.setText(scoreJ1_int + "");
        this.scoreBot.setText(scoreRobot + "");
    }

    @FXML
    Pane leavingPane;

    public void afficherQuitterMenu() {
        gamePanel.setEffect(new GaussianBlur());
        leavingPane.setVisible(true);
    }

    public void quitterQuitterMenu() {
        gamePanel.setEffect(null);
        leavingPane.setVisible(false);
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

    HistoriqueMancheSaver historiqueMancheSaver;


    public void updateManche(HistoriqueMancheSaver historiqueMancheSaver) {
        this.historiqueMancheSaver = historiqueMancheSaver;
        int index = 0;
        while (historiqueMancheSaver.savePTS[index] != 0) {
            index++;
        }
        for (int i = 0; i < index; i++) {
            VBox historyBox = (VBox) historiquePane1.getChildren().get(0);
            GridPane pane = (GridPane) historyBox.getChildren().get(i);
            GridPane gridPane = (GridPane) pane.getChildren().get(1);
            Label labelWinner = (Label) gridPane.getChildren().get(1);
            Label labelPts = (Label) gridPane.getChildren().get(2);
            labelWinner.setText("WINNER: " + historiqueMancheSaver.saveWinners[i]);
            labelPts.setText("PTS gagnés: " + historiqueMancheSaver.savePTS[i]);
        }

    }



    public void switchToHuman(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader;
        if (manche != 10) {
            fxmlLoader = new FXMLLoader(getClass().getResource("MasterMindPlayer.fxml"));
            root = fxmlLoader.load();
            MainControler controler = fxmlLoader.getController();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            controler.setEnsembleCouleurs(this.ensembleCouleurs);
            controler.updateScore(scoreJ1_int, scoreRobot);
            manche++;
            controler.setManche(this.manche++);
            controler.updateManche(this.historiqueMancheSaver);
        } else {
            fxmlLoader = new FXMLLoader(getClass().getResource("EndingView.fxml"));
            root = fxmlLoader.load();
            EndingControler controler = fxmlLoader.getController();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            controler.updateScore(this.scoreJ1_int, this.scoreRobot);
            controler.updateManche(this.historiqueMancheSaver);
        }
        stage.show();
    }


    public void quitButton() {
        Stage stage1 = (Stage) boutonQuitter.getScene().getWindow();
        stage1.close();
    }

    public void setEnsembleCouleurs(HashMap<Integer, Paint> ensembleCouleurs) {
        this.ensembleCouleurs = ensembleCouleurs;
    }

    public HBox getRow(int n) {
        return (HBox) plateau.getChildren().get(n);
    }

    public HBox getRow() {
        return getRow(activeRow);
    }

    public HBox getCircleHbox(int n) {
        HBox mainBox = (HBox) plateau.getChildren().get(n);
        return (HBox) mainBox.getChildren().get(1);
    }

    public HBox getCircleHbox() {
        return getCircleHbox(activeRow);
    }

    public void putFirstCode() {
        HBox firstCode = getCircleHbox();
        for (int i = 0; i < 4; i++) {
            Circle circle = (Circle) firstCode.getChildren().get(i);
            Random random = new Random();;
            circle.setFill(ensembleCouleurs.get(random.nextInt(6)));
        }
        System.out.println(activeRow);
        aGagne();
    }

    private void updateRowIDHighlight() {
        int row = activeRow + 2;
        HBox mainRow = (HBox) plateau.getChildren().get(row);
        Label elt = (Label) mainRow.getChildren().get(0);
        elt.setId("");
        elt.setOpacity(0.25);
        mainRow = (HBox) plateau.getChildren().get(row - 2);
        elt = (Label) mainRow.getChildren().get(0);
        elt.setId("gros");
    }

    private int[] checkAnswerIsCorrect() {
        int[] resultat = new int[2];
        if (bienPlaceSlider.getValue() + malPlaceSlider.getValue() > 4) {
            resultat[0] = -1;
            resultat[1] = -1;
            return resultat;
        }
        resultat[0] = (int) bienPlaceSlider.getValue();
        System.out.println(resultat[0]);
        resultat[1] = (int) malPlaceSlider.getValue();
        System.out.println(resultat[1]);
        return resultat;
    }

    public void updateBienMalPlace() {
        if (canClick) {
            int[] nbBienMalPlace = checkAnswerIsCorrect();
            if (!Arrays.equals(nbBienMalPlace, new int[]{-1, -1})) {
                String[] code = new String[4];
                for (int i = 0; i < 4; i++) {
                    code[i] = ((Circle) getCircleHbox().getChildren().get(i)).getFill().toString();
                }
                int[] intCode = new int[4];
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 6; j++) {
                        if (ensembleCouleurs.get(j).toString().equals(code[i])) {
                            intCode[i] = j;
                        }
                    }
                }
                if (Arrays.equals(nbBienMalPlace, MasterMindAlgo.nbBienMalPlaces(secretCode, intCode))) {
                    HBox mainHbox = getRow();
                    GridPane gridPane = (GridPane) mainHbox.getChildren().get(2);
                    for (int i = 0; i < nbBienMalPlace[0]; i++) {
                        ((Circle) gridPane.getChildren().get(i)).setFill(ensembleCouleurs.get(0));
                    }
                    for (int i = 0; i < nbBienMalPlace[1]; i++) {
                        ((Circle) gridPane.getChildren().get(i + nbBienMalPlace[0])).setFill(ensembleCouleurs.get(1));
                    }
                    rep[(16 - activeRow) / 2] = nbBienMalPlace;

                    cod[(16 - activeRow) / 2] = intCode;

                    updateNewCode();
                } else System.out.println("not good");

            }

        }
    }

    public static boolean passeCodeSuivantLexico(int[] cod1) {
        int index = cod1.length - 1; // On prend le dernier indice
        int nbCouleurs = 6;

        while ((cod1[index] + 1) > nbCouleurs - 1) { // Permet de passer à l'emplacement suivant si la valeur à cet emplacement est de taille maximale
            index--; // Passage à l'emplacement suivant
            if (index < 0) { // Test si c'est le dernier emplacement
                Arrays.fill(cod1, 0); // Remplissage du tableau avec des 0
                return true;
            }
        }

        for (int i = index + 1; i < cod1.length; i++) { // S'il y a eu passage à l'emplacement suivant, on met les valeurs des emplacements précédents à 0
            cod1[i] = 0;
        }

        cod1[index]++; // On incrémente la valeur pour rester dans l'ordre lexicographique
        return true;
    }

    public void updateSecretCodeRow() {
        for (int i = 0; i < 4; i++) {
            ((Circle) rowSecretCode.getChildren().get(i)).setFill(ensembleCouleurs.get(secretCode[i]));
        }
    }


    public boolean estCompat() {
        for (int i = 0; i < (16 - activeRow) / 2; i++) {
            int[] nbbienmalplacescodI = MasterMindAlgo.nbBienMalPlaces(cod[i], cod[(16 - activeRow) / 2]);
            if (!Arrays.equals(nbbienmalplacescodI, rep[i])) {
                return false;
            }
        }

        return true;
    }



    public boolean passePropSuivante() {
        int nbCoups = (16 - activeRow) / 2;
        cod[nbCoups] = Arrays.copyOf(cod[nbCoups - 1], 4);
        while (passeCodeSuivantLexico(cod[nbCoups])) { // Tant que il en reste dans les possibilité ( genre 00 -> 01 -> 10 -> 11 ) jsp comment expliquer, mais bref ça sort de la boucle quand il y a plus de possibilité et ça return false
            if (estCompat()) { // si le nombre est compatible ça return vrai
                return true;
            }
        }
        return false;
    }

    int ptsGagne;
    String winner;

    private void updateNewCode() {
        activeRow -= 2;
        if (activeRow < 0) {// si le bot arrive a la fin de ses essaies ( c'est jamais arrivé a moins de glitch)
            activeRow+= 2; //just pour recup les bons bien mal place
            canClick = false;
            changerButton.setVisible(true);
            validerButton.setVisible(false);
            // Malus : nbMalPlaces + 2 × (lgCode − (nbBienPlaces + nbMalPlaces))
            int bienPlace = rep[8 - activeRow/2][0];
            System.out.println(bienPlace);
            int malPlace = rep[8 - activeRow/2][1];
            int malus = malPlace + 2 * (4 - (bienPlace + malPlace));
            ptsGagne = 8 - activeRow / 2 + 1 + malus;
            scoreJ1_int += ptsGagne;
            historiqueMancheSaver.setWinnerPts(manche, "JOUEUR", ptsGagne);
        }
        else if (passePropSuivante()) {
            for (int i = 0; i < 4; i++) {
                Circle circle = (Circle) getCircleHbox().getChildren().get(i);
                circle.setFill(ensembleCouleurs.get(cod[(16 - activeRow) / 2][i]));
                updateRowIDHighlight();
            }
            aGagne();
        }
    }

    private void aGagne() {
        if (Arrays.equals(getSecretCodeFromColors(), secretCode)) {
            System.out.println("GG");
            winner = "JOUEUR";
            if (manche == 10) {
                changerButton.setText("FIN !");
            }
            changerButton.setVisible(true);
            validerButton.setVisible(false);

            scoreJ1_int += 8 - activeRow / 2 + 1;
            ptsGagne = 8 - activeRow / 2 + 1;
            historiqueMancheSaver.setWinnerPts(manche, "ROBOT", ptsGagne);
            canClick = false;
        }
    }


    private int[] getSecretCodeFromColors() {
        int[] secretCode = new int[4];
        String[] code = new String[4];
        for (int i = 0; i < 4; i++) {
            code[i] = ((Circle) getCircleHbox().getChildren().get(i)).getFill().toString();
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
}
