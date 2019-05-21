package com;

import com.Layout.FilePickerController;
import com.Layout.PacketEvaluationController;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader filePickerLoader = new FXMLLoader(getClass().getResource("/Views/filePickerView.fxml"));
        Parent filePicker = filePickerLoader.load();
        Scene filePickerScene = new Scene(filePicker, 300, 275);

        FXMLLoader packetEvaluationLoader = new FXMLLoader(getClass().getResource("/Views/packetEvaluationView.fxml"));
        Parent packetEvaluation = packetEvaluationLoader.load();
        Scene packetEvaluationScene = new Scene(packetEvaluation, 300, 275);

        FilePickerController filePickerController = filePickerLoader.getController();
        filePickerController.setNextScene(packetEvaluationScene);

        PacketEvaluationController packetEvaluationController = packetEvaluationLoader.getController();
        packetEvaluationController.setPreviousScene(filePickerScene);

        stage.setTitle("Seleccionar Archivos");
        stage.setScene(filePickerScene);
        stage.setMaximized(true);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}