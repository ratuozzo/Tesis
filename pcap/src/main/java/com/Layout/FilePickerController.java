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

public class FilePickerController implements Initializable {

    private FileChooser fileChooser = new FileChooser();
    private Scene _nextScene;

    @FXML
    private Pane paneRed, paneGreen, paneYellow;

    @FXML
    private AnchorPane paneFilePicker;

    @FXML
    private GridPane fileListPane;

    @FXML
    private Button menuFilePicker, continueButton, buttonRed, buttonYellow, filePickerButton;

    @FXML
    private VBox filePickerVbox;

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == menuFilePicker) {
            paneFilePicker.toFront();
        } else if (event.getSource() == continueButton) {
            System.out.println("next scene");
            openNextScene(event);
        } else if (event.getSource() == buttonRed) {
            paneRed.toFront();
        } else if (event.getSource() == buttonYellow) {
            paneYellow.toFront();
        } else if(event.getSource() == filePickerButton) {
            List<File> list = fileChooser.showOpenMultipleDialog(paneFilePicker.getScene().getWindow());
            String filesPath = "";
            for (int i = 0; i < list.size(); i++) {
                Label fileLabel = new Label(list.get(i).getPath());
                fileListPane.add(fileLabel,0,i);
            }
            System.out.println(list);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rv) {
        filePickerVbox.setAlignment(Pos.CENTER);
    }

    public void setNextScene(Scene nextScene) {
        _nextScene = nextScene;
    }

    public void openNextScene(ActionEvent actionEvent) {

        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        nextStage.setX(bounds.getMinX());
        nextStage.setY(bounds.getMinY());
        nextStage.setWidth(bounds.getWidth());
        nextStage.setHeight(bounds.getHeight());

        nextStage.setTitle("Evaluar Paquetes");
        nextStage.setScene(_nextScene);
    }
}
