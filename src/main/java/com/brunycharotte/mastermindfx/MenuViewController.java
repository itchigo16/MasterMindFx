package com.brunycharotte.mastermindfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class MenuViewController {

    Parent root;
    Scene scene;
    Stage stage;

    @FXML
    VBox plateau;
    @FXML
    Button boutonQuitter;
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

    public void onPlay(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TutoView.fxml"));
        root = fxmlLoader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void quitButton() {
        Stage stage1 = (Stage) boutonQuitter.getScene().getWindow();
        stage1.close();
    }
    
    public void setBackgroundRandom() {
        Random random = new Random();
        Button[] listeButton = {vert, rouge, bleu, violet, orange, yellow};
        for (int i = 0; i < 9; i++) {
            HBox circlesBox = getCircleHbox(i*2);
            for (int j = 0; j < 4; j++) {
                Circle circle = (Circle) circlesBox.getChildren().get(j);
                circle.setFill(listeButton[random.nextInt(0, 6)].getBackground().getFills().get(0).getFill());
            }
        }
    }
    
    private HBox getCircleHbox(int n){
        HBox hBox = (HBox) plateau.getChildren().get(n);
        return (HBox) hBox.getChildren().get(1);
    }
    



}
