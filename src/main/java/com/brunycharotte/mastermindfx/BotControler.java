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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class BotControler {

    private Parent root;
    private Scene scene;
    private Stage stage;
    public int[] guessedCode = new int[4];

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

    int[][] cod = new int[9][4];
    int[][] rep = new int[9][2];

    @FXML
    Slider bienPlaceSlider;

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

    HashMap<Integer, Paint> ensembleCouleurs = new HashMap<>(6);

    public void updateScore(int scoreJoueur, int scoreRobot) {
        scoreJ1_int = scoreJoueur;
        this.scoreRobot = scoreRobot;
        this.scoreJoueur.setText(scoreJ1_int + "");
        this.scoreBot.setText(scoreRobot + "");
    }
    public void switchToBot(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MasterMind_FX.fxml"));
        root = fxmlLoader.load();
        MainControler controler = fxmlLoader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        controler.setEnsembleCouleurs(this.ensembleCouleurs);
        controler.updateScore(scoreJ1_int, scoreRobot);
        stage.show();
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
            circle.setFill(ensembleCouleurs.get(0));
        }
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
        int[] nbBienMalPlace = checkAnswerIsCorrect();
        if (!Arrays.equals(nbBienMalPlace, new int[]{-1, -1})) {
            HBox mainHbox = getRow();
            GridPane gridPane = (GridPane) mainHbox.getChildren().get(2);
            for (int i = 0; i < nbBienMalPlace[0]; i++) {
                ((Circle) gridPane.getChildren().get(i)).setFill(ensembleCouleurs.get(0));
            }
            for (int i = 0; i < nbBienMalPlace[1]; i++) {
                ((Circle) gridPane.getChildren().get(i + nbBienMalPlace[0])).setFill(ensembleCouleurs.get(1));
            }
            rep[(16 - activeRow)/2] = nbBienMalPlace;
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
            cod[(16 - activeRow)/2] = intCode;

            activeRow -= 2;

            int levelBot = 2;

            updateNewCode(levelBot);

        }
    }

    public static boolean passeCodeSuivantLexico(int[] cod1) {
        int index = cod1.length - 1; // On prend le dernier indice
        int nbCouleurs = 6;

        while ((cod1[index] + 1) > nbCouleurs - 1) { // Permet de passer à l'emplacement suivant si la valeur à cet emplacement est de taille maximale
            index--; // Passage à l'emplacement suivant
            if (index < 0) { // Test si c'est le dernier emplacement
                return false; // Si c'est le dernier et que la valeur est de taille maximale alors on ne peut pas effectuer l'action
            }
        }

        for (int i = index + 1; i < cod1.length; i++) { // S'il y a eu passage à l'emplacement suivant, on met les valeurs des emplacements précédents à 0
            cod1[i] = 0;
        }

        cod1[index]++; // On incrémente la valeur pour rester dans l'ordre lexicographique
        return true;
    }

    //___________________________________________________________________

    /**
     * pré-requis : cod est une matrice, rep est une matrice à 2 colonnes, 0 <= nbCoups < cod.length
     * et  nbCoups < rep.length
     * résultat : vrai ssi cod[nbCoups] est compatible avec les nbCoups premières lignes de cod et de rep,
     * c'est-à-dire que si cod[nbCoups] était le code secret, les réponses aux nbCoups premières
     * propositions de cod seraient les nbCoups premières réponses de rep
     */
    public boolean estCompat(int level) {
        for (int i = 0; i < (16 - activeRow)/2; i++) {
            int[] nbbienmalplacescodI = MasterMindAlgo.nbBienMalPlaces(cod[i], cod[(16 - activeRow)/2]);
            if (level == 1) {
                for (int j = 0; j < 2; j++) {
                    if (nbbienmalplacescodI[j] > 1) nbbienmalplacescodI[j] -= 1;
                    if (rep[i][j] > 1) rep[i][j] -= 1;
                }
            }
            if (Arrays.equals(nbbienmalplacescodI, rep[i])) {
                return false;
            }
        }

        return true;
    }

    //___________________________________________________________________

    /**
     * pré-requis : cod est une matrice, rep est une matrice à 2 colonnes, 0 < nbCoups < cod.length
     * et nbCoups < rep.length
     * action : met dans cod[nbCoups] le plus petit code (selon l'ordre lexicographique dans l'ensemble
     * des codes de longueur cod[0].length à valeurs de 0 à nbCouleurs-1) qui est à la fois plus grand que
     * cod[nbCoups-1] selon cet ordre et compatible avec les nbCoups premières lignes de cod et de rep,
     * si ce code existe
     * résultat : vrai ssi l'action a pu être effectuée
     */
    public boolean passePropSuivante(int level) {
        int nbCoups = (16 - activeRow)/2;
        cod[nbCoups] = Arrays.copyOf(cod[nbCoups- 1], 4);
        while (passeCodeSuivantLexico(cod[nbCoups])) { // Tant que il en reste dans les possibilité ( genre 00 -> 01 -> 10 -> 11 ) jsp comment expliquer, mais bref ça sort de la boucle quand il y a plus de possibilité et ça return false
            if (estCompat(level)) { // si le nombre est compatible ça return vrai
                return true;
            }
        }
        return false;
    }


    private void updateNewCode(int level) {
        if (passePropSuivante(level)) {
            for (int i = 0; i < 4; i++) {
                Circle circle = (Circle) getCircleHbox().getChildren().get(i);
                circle.setFill(ensembleCouleurs.get(cod[(16-activeRow)/2][i]));
            }
        }
    }
}
