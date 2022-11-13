package com.brunycharotte.mastermindfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class TutoView {

    Parent root;
    Scene scene;
    Stage stage;

    int indexImage = 0;

    @FXML
    ImageView displayedImage;
    @FXML
    Button suivant;


    Image humainDecodeur = new Image(getClass().getResourceAsStream("/humainDecodeur.jpg"));
    Image choisisCode = new Image(getClass().getResourceAsStream("/choisisCode.jpg"));
    Image robotDecodeur = new Image(getClass().getResourceAsStream("/robotDecodeur.jpg"));
    Image finManche = new Image(getClass().getResourceAsStream("/finManche.png"));
    Image[] images = {humainDecodeur, choisisCode, robotDecodeur, finManche};

    public void onClickSuivant(ActionEvent event) throws IOException  {
        if (indexImage == 2) suivant.setText("JOUER !");
        if (indexImage != 3) {
            indexImage++;
            displayedImage.setImage(images[indexImage]);
        }
        else {
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
    }

    public void onClickPrecedent() {
        if (indexImage == 3) suivant.setText("Suivant");
        if (indexImage != 0) {
            indexImage--;
            displayedImage.setImage(images[indexImage]);
        }
    }

    public void onPlay(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MasterMindPlayer.fxml"));
        root = fxmlLoader.load();
        MainControler controler = fxmlLoader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        controler.updateScore(0, 0);
        stage.show();
    }

}