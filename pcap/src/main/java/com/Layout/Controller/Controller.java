package com.Layout.Controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;


public class Controller {

    @FXML
    VBox vboxPane = new VBox();

    public void hideLog(MouseEvent mouseEvent) {
        vboxPane.setVisible(false);
    }

    public void showLog(MouseEvent mouseEvent) {
        vboxPane.setVisible(true);
    }
}

