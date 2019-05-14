package com;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("logtest.fxml"));

        Scene scene = new Scene(root, 300, 275);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}