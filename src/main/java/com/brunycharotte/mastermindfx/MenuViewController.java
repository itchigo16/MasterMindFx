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


    public void onPlay(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MasterMind_FX.fxml"));
        root = fxmlLoader.load();
        MainControler controler = fxmlLoader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        controler.updateScore(0, 0);
        stage.show();
    }


    public void quitButton() {
        Stage stage1 = (Stage) boutonQuitter.getScene().getWindow();
        stage1.close();
    }



}
