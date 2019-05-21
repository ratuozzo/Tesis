package com.Layout;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PacketEvaluationController implements Initializable {

    private Scene _nextScene;
    private Scene _previouseScene;

    @FXML
    private Button previousSceneButton;

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == previousSceneButton) {
            openPreviousScene(event);
        }

}

    public void setPreviousScene(Scene previousScene) {
        _previouseScene = previousScene;
    }

    public void openPreviousScene(ActionEvent actionEvent) {
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        nextStage.setX(bounds.getMinX());
        nextStage.setY(bounds.getMinY());
        nextStage.setWidth(bounds.getWidth());
        nextStage.setHeight(bounds.getHeight());

        nextStage.setTitle("Seleccionar Paquetes");
        nextStage.setScene(_nextScene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
