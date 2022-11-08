package com.brunycharotte.mastermindfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MasterMindLauncher extends Application {

    Parent root;
    Scene scene;


    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("MasterMind_FX.fxml"));
        scene = new Scene(root);
        stage.setTitle("MasterMind");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
