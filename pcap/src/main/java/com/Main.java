package com;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {



    @Override
    public void start(Stage stage) throws Exception {


        FXMLLoader filePickerLoader = new FXMLLoader(getClass().getResource("/Views/mainView.fxml"));
        Parent filePicker = filePickerLoader.load();
        Scene filePickerScene = new Scene(filePicker, 300, 275);

        stage.setTitle("Seleccionar Archivos");
        stage.setScene(filePickerScene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}