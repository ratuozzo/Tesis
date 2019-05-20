package com.Layout;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private FileChooser fileChooser = new FileChooser();
    private File file;

    @FXML
    private Pane paneFilePicker, paneRed, paneGreen, paneYellow;

    @FXML
    private Button menuFilePicker, buttonGreen, buttonRed, buttonYellow, filePickerButton;

    @FXML
    private TextField filesTextField;

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == menuFilePicker) {
            paneFilePicker.toFront();
        } else if (event.getSource() == buttonGreen) {
            paneGreen.toFront();
        } else if (event.getSource() == buttonRed) {
            paneRed.toFront();
        } else if (event.getSource() == buttonYellow) {
            paneYellow.toFront();
        } else if(event.getSource() == filePickerButton) {
            List<File> list = fileChooser.showOpenMultipleDialog(paneFilePicker.getScene().getWindow());
            String filesPath = "";
            for (int i = 0; i < list.size(); i++) {
                filesPath = filesPath + "," + list.get(i);
            }
            filesTextField.setText(filesPath);
            System.out.println(list);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rv) {

    }

}
