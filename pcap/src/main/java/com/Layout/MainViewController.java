package com.Layout;

import com.Common.Entity.Connections.Connection;
import com.DomainLogicLayer.Commands.CommandFactory;
import com.DomainLogicLayer.Commands.Orchestrate;
import com.DomainLogicLayer.Commands.ReadMultiplePcaps;
import com.DomainLogicLayer.Filters.*;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.pcap4j.packet.Packet;

import java.io.File;
import java.net.URL;
import java.util.*;

public class MainViewController implements Initializable {

    private FileChooser fileChooser = new FileChooser();
    private ArrayList<Packet> _rawPackets;
    private ArrayList<Connection> _closedConnections;
    private ArrayList<ArrayList<Connection>> _currentConnections;


    private int _currentPane = 0;

    private HashMap<Integer, Filter> _filteredConnections = new HashMap<>();

    private final String SOURCEIP = "SrcIp";
    private final String DESTINATIONIP = "DstIp";
    private final String SOURCEPORT = "SrcPort";
    private final String DESTINATIONPORT = "DstPort";

    @FXML
    private ScrollPane masonryScrollPane;

    @FXML
    private Pane paneRed, paneYellow;

    @FXML
    private JFXMasonryPane packetEvaluationPane;

    @FXML
    private AnchorPane paneFilePicker;

    @FXML
    private GridPane fileListPane;

    @FXML
    private Button menuFilePicker, continueButton, buttonRed, buttonYellow, backButton;

    @FXML
    private VBox filePickerVbox;

    public void continueButtonAction() {

        if (_currentPane == 0) {

            getConnections();

            FilterBySrcIp filterBySrcIp = new FilterBySrcIp(_closedConnections);
            filterBySrcIp.execute();
            _currentConnections = filterBySrcIp.getOutput();

            _filteredConnections.put(0, filterBySrcIp);

            for (int i = 0; i < _currentConnections.size(); i++) {

                int packetsNumber = 0;

                for (int j = 0; j < _currentConnections.get(i).size(); j++) {
                    packetsNumber += _currentConnections.get(i).get(j).getPackets().size();
                }


                AnchorPane anchorPane = createMasonryPane(_currentConnections.get(i).get(0).getSrcIp(),
                        SOURCEPORT,
                        packetsNumber,
                        _currentConnections.get(i).size(),
                        maximumDimensions(_currentConnections),
                        _currentConnections.get(i).get(0).getIncluded());

                packetEvaluationPane.getChildren().add(anchorPane);

                anchorPane.setOnMouseClicked((e) -> newMasonry(anchorPane.getChildren().get(0)));
            }

            packetEvaluationPane.setPrefHeight(packetEvaluationPane.getScene().getWindow().getHeight() - 200);
            packetEvaluationPane.setPrefWidth(packetEvaluationPane.getScene().getWindow().getWidth());
            masonryScrollPane.setFitToWidth(true);
            masonryScrollPane.toFront();

            _currentPane = 1;
            backButton.setDisable(false);

        } else if (_currentPane == 1) {
            paneRed.toFront();
        }

    }

    public void backButtonAction(ActionEvent event) {

        if (_currentPane == 1) {
            paneFilePicker.toFront();
            backButton.setDisable(true);
            packetEvaluationPane.getChildren().clear();
            _currentPane = 0;
        }

    }

    public void handleButtonAction(ActionEvent event) {

        if (event.getSource() == menuFilePicker) {
            paneFilePicker.toFront();

        } else if (event.getSource() == buttonRed) {
            paneRed.toFront();
        } else if (event.getSource() == buttonYellow) {
            paneYellow.toFront();
        }
    }

    private void getConnections() {
        Orchestrate orchestrate = (Orchestrate) CommandFactory.instantiateOrchestrate(_rawPackets);
        orchestrate.execute();
        _closedConnections = orchestrate.getClosedConnections();
    }

    public void handlePickFilesAction(ActionEvent event) {
        ArrayList<String> fileList = new ArrayList<>();
        List<File> list = fileChooser.showOpenMultipleDialog(paneFilePicker.getScene().getWindow());
        for (int i = 0; i < list.size(); i++) {
            fileList.add(list.get(i).getPath());
            Label fileLabel = new Label(list.get(i).getPath());
            fileListPane.add(fileLabel,0,i);
        }
        ReadMultiplePcaps readMultiplePcaps = (ReadMultiplePcaps) CommandFactory.instantiateReadMultiplePcaps(fileList);
        readMultiplePcaps.execute();
        _rawPackets = readMultiplePcaps.getPackets();
        if (_rawPackets.size() > 1) {
            continueButton.setDisable(false);
        }
        System.out.println(readMultiplePcaps.getPackets().size());
    }

    private void newMasonry(Node node) {

        Label label = (Label) node;

        packetEvaluationPane.getChildren().clear();
        // First filter, by source ip
        if (label.getId().equals(SOURCEIP)) {
            _currentConnections = _filteredConnections.get(0).getOutput();

            for (int i = 0; i < _currentConnections.size(); i++) {

                int packetsNumber = 0;

                for (int j = 0; j < _currentConnections.get(i).size(); j++) {
                    packetsNumber += _currentConnections.get(i).get(j).getPackets().size();
                }

                AnchorPane anchorPane = createMasonryPane(
                        _currentConnections.get(i).get(0).getSrcIp(),
                        DESTINATIONIP,
                        packetsNumber,
                        _currentConnections.get(i).size(),
                        maximumDimensions(_currentConnections),
                        _currentConnections.get(i).get(0).getIncluded()
                );

                anchorPane.setOnMouseClicked((e) -> newMasonry(anchorPane.getChildren().get(0)));
                packetEvaluationPane.getChildren().add(anchorPane);
            }
            masonryScrollPane.setVvalue(0);
        } // Second filter, by source port
        else if (label.getId().equals(SOURCEPORT)) {
            ArrayList<Connection> connections = connectionForFilter(_filteredConnections.get(0).getOutput(), label.getText(), SOURCEPORT);
            FilterBySrcPort filterBySrcPort= new FilterBySrcPort(connections);
            filterBySrcPort.execute();
            _currentConnections = filterBySrcPort.getOutput();

            _filteredConnections.put(1, filterBySrcPort);

            for (int i = 0; i < _currentConnections.size(); i++) {

                int packetsNumber = 0;

                for (int j = 0; j < _currentConnections.get(i).size(); j++) {
                    packetsNumber += _currentConnections.get(i).get(j).getPackets().size();
                }

                AnchorPane anchorPane = createMasonryPane(
                        _currentConnections.get(i).get(0).getSrcIp() + "->" +
                                _currentConnections.get(i).get(0).getSrcPort(),
                        DESTINATIONIP,
                        packetsNumber,
                        _currentConnections.get(i).size(),
                        maximumDimensions(_currentConnections),
                        _currentConnections.get(i).get(0).getIncluded()
                );

                anchorPane.setOnMouseClicked((e) -> newMasonry(anchorPane.getChildren().get(0)));
                packetEvaluationPane.getChildren().add(anchorPane);
            }
            masonryScrollPane.setVvalue(0);
        } //Third filter, by destination ip
        else if (label.getId().equals(DESTINATIONIP)) {
            ArrayList<Connection> connections = connectionForFilter(_filteredConnections.get(1).getOutput(), label.getText(), DESTINATIONIP);
            FilterByDstIp filterByDstIp = new FilterByDstIp(connections);
            filterByDstIp.execute();
            _currentConnections = filterByDstIp.getOutput();

            _filteredConnections.put(2, filterByDstIp);

            for (int i = 0; i < _currentConnections.size(); i++) {

                int packetsNumber = 0;

                for (int j = 0; j < _currentConnections.get(i).size(); j++) {
                    packetsNumber += _currentConnections.get(i).get(j).getPackets().size();
                }

                AnchorPane anchorPane = createMasonryPane(
                        _currentConnections.get(i).get(0).getSrcIp() + "->" +
                                _currentConnections.get(i).get(0).getSrcPort() + "->" +
                                _currentConnections.get(i).get(0).getDstIp(),
                        DESTINATIONPORT,
                        packetsNumber,
                        _currentConnections.get(i).size(),
                        maximumDimensions(_currentConnections),
                        _currentConnections.get(i).get(0).getIncluded()
                );

                anchorPane.setOnMouseClicked((e) -> newMasonry(anchorPane.getChildren().get(0)));
                packetEvaluationPane.getChildren().add(anchorPane);
            }
            masonryScrollPane.setVvalue(0);
        } //Fourth filter, by destination port
        else if (label.getId().equals(DESTINATIONPORT)) {
            ArrayList<Connection> connections = connectionForFilter(_filteredConnections.get(2).getOutput(), label.getText(), DESTINATIONPORT);
            FilterByDstPort filterByDstPort = new FilterByDstPort(connections);
            filterByDstPort.execute();
            _currentConnections = filterByDstPort.getOutput();

            _filteredConnections.put(3, filterByDstPort);

            for (int i = 0; i < _currentConnections.size(); i++) {

                int packetsNumber = 0;

                for (int j = 0; j < _currentConnections.get(i).size(); j++) {
                    packetsNumber += _currentConnections.get(i).get(j).getPackets().size();
                }

                AnchorPane anchorPane = createMasonryPane(
                        _currentConnections.get(i).get(0).getSrcIp() + "->" +
                                _currentConnections.get(i).get(0).getSrcPort() + "->" +
                                _currentConnections.get(i).get(0).getDstIp() + "->" +
                                _currentConnections.get(i).get(0).getDstPort(),
                        DESTINATIONPORT,
                        packetsNumber,
                        _currentConnections.get(i).size(),
                        maximumDimensions(_currentConnections),
                        _currentConnections.get(i).get(0).getIncluded()
                );

                packetEvaluationPane.getChildren().add(anchorPane);
            }
            masonryScrollPane.setVvalue(0);
        }
    }


    private void previousMasonry(Node node) {

        Button previousButton = (Button) node;
        Label label = new Label();
        label.setText(previousButton.getId().split(",")[0]);

        if (previousButton.getId().split(",")[1].equals(SOURCEPORT)) {
            label.setId(SOURCEIP);
            newMasonry(label);
        } else if (previousButton.getId().split(",")[1].equals(DESTINATIONIP)) {
            label.setId(SOURCEPORT);
            newMasonry(label);
        } else if (previousButton.getId().split(",")[1].equals(DESTINATIONPORT)) {
            label.setId(DESTINATIONIP);
            newMasonry(label);
        }

    }

    private AnchorPane createMasonryPane(String text, String id, int packetsNumber, int currentHeight, int[] dimensions, Boolean enabled) {

        Random random = new Random();

        text = text.replace("/", "");

        Label titleLabel = new Label(text);
        titleLabel.setStyle("-fx-font-weight: 15");
        titleLabel.setId(id);

        Label packetsLabel = new Label("Paquetes: " + Integer.toString(packetsNumber));

        Button excludeButton = new Button();
        excludeButton.setText("Excluir");
        excludeButton.setId(id);
        excludeButton.setPrefSize(50,50);

        Button previousPaneButton = new Button();
        previousPaneButton.setText("Vovler");
        previousPaneButton.setId(text+","+id);
        previousPaneButton.setPrefSize(50,50);
        if (id.equals(SOURCEPORT)) {
            previousPaneButton.setDisable(true);
        }

        Button includeButton = new Button();
        includeButton.setText("Incluir");
        includeButton.setId(id);
        includeButton.setPrefSize(50, 50);
        includeButton.setDisable(true);

        AnchorPane output = new AnchorPane();


        output.setBottomAnchor(excludeButton,5.0);
        output.setRightAnchor(excludeButton, 5.0);

        output.setBottomAnchor(previousPaneButton, 5.0);
        output.setLeftAnchor(previousPaneButton, 60.0);

        output.setBottomAnchor(includeButton,5.0);
        output.setLeftAnchor(includeButton,5.0);

        if (enabled) {
            output.setStyle("-fx-background-color:rgb(" + random.nextInt(255) + "," + random.nextInt(255) + "," + random.nextInt(255) + ")");
        } else {
            output.setStyle("-fx-background-color: grey");
        }
        output.setPrefSize(( (currentHeight * 200) / dimensions[0]) + 150, ((packetsNumber * 100) / dimensions[1]) + 100);

        output.getChildren().add(titleLabel);

        output.setTopAnchor(titleLabel,10.0);
        output.setLeftAnchor(titleLabel,5.0);
        output.setRightAnchor(titleLabel,40.0);

        output.getChildren().add(packetsLabel);

        output.setTopAnchor(packetsLabel,30.0);
        output.setLeftAnchor(packetsLabel,5.0);
        output.setRightAnchor(packetsLabel,40.0);

        previousPaneButton.setOnMouseClicked((e) -> previousMasonry(previousPaneButton));

        excludeButton.setOnMouseClicked((e) -> excludeConnection(output, includeButton, excludeButton));
        includeButton.setOnMouseClicked((event -> includeConnection(output, includeButton, excludeButton)));

        output.getChildren().add(excludeButton);
        output.getChildren().add(previousPaneButton);
        output.getChildren().add(includeButton);

        return output;
    }

    private ArrayList<Connection> connectionForFilter(ArrayList<ArrayList<Connection>> input, String text, String typeOfFilter) {

        ArrayList<Connection> output = new ArrayList<>();

        String[] auxText = text.split("->");

        if (typeOfFilter.equals(SOURCEPORT)) {
            for (int i = 0; i < input.size(); i++) {
                if (input.get(i).get(0).getSrcIp().replace("/", "").equals(text)) {
                    for (int j = 0; j < input.get(i).size(); j++) {
                        output.add(input.get(i).get(j));
                    }
                }
            }
        } else if (typeOfFilter.equals(DESTINATIONIP)) {
            for (int i = 0; i < input.size(); i++) {
                if (Integer.toString(input.get(i).get(0).getSrcPort()).replace("/", "").equals(auxText[1])) {
                    for (int j = 0; j < input.get(i).size(); j++) {
                        output.add(input.get(i).get(j));
                    }
                }
            }
        } else if (typeOfFilter.equals(DESTINATIONPORT)) {
            for (int i = 0; i < input.size(); i++) {
                if (input.get(i).get(0).getDstIp().replace("/", "").equals(auxText[2])) {
                    for (int j = 0; j < input.get(i).size(); j++) {
                        output.add(input.get(i).get(j));
                    }
                }
            }
        }

        return output;
    }

    private int[] maximumDimensions(ArrayList<ArrayList<Connection>> input) {
        int[] output = {0,0};

        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).size() > output[0]) {
                output[0] = input.get(i).size();
            }
            for (int j = 0; j < input.get(i).size(); j++) {
                if (input.get(i).get(j).getPackets().size() > output[1]) {
                    output[1] = input.get(i).get(j).getPackets().size();
                }
            }
        }
        return output;
    }

    private void excludeConnection(AnchorPane anchorPane, Node includeButton, Node excludeButton) {
        anchorPane.setStyle("-fx-background-color: grey");
        includeButton.setDisable(false);
        excludeButton.setDisable(true);

        changeConnectionStatus(excludeButton, false, anchorPane);

    }

    private void includeConnection(AnchorPane anchorPane, Node includeButton, Node excludeButton) {
        anchorPane.setStyle("-fx-background-color: yellow");
        includeButton.setDisable(true);
        excludeButton.setDisable(false);

        changeConnectionStatus(includeButton, true, anchorPane);

    }

    private void changeConnectionStatus(Node button, Boolean status, AnchorPane anchorPane) {

        Label label = (Label) anchorPane.getChildren().get(0);
        String[] auxText = label.getText().split("->");

        if (button.getId().equals(SOURCEPORT)) {
            ArrayList<ArrayList<Connection>> connections = _filteredConnections.get(0).getOutput();
            for (int i = 0; i < connections.size(); i++) {
                if (auxText[0].equals(connections.get(i).get(0).getSrcIp().replace("/", ""))) {
                    for (int j = 0; j < connections.get(i).size(); j++) {
                        connections.get(i).get(j).setIncluded(status);
                    }
                }
            }
            System.out.println("Parada");
        } else if (button.getId().equals(DESTINATIONIP)) {
            ArrayList<ArrayList<Connection>> connections = _filteredConnections.get(1).getOutput();
            for (int i = 0; i < connections.size(); i++) {
                if (auxText[1].equals(connections.get(i).get(0).getSrcPort())) {
                    for (int j = 0; j < connections.get(i).size(); j++) {
                        connections.get(i).get(j).setIncluded(status);
                    }
                }
            }
        }
        else if (button.getId().equals(DESTINATIONPORT)) {
            ArrayList<ArrayList<Connection>> connections = _filteredConnections.get(2).getOutput();
            for (int i = 0; i < connections.size(); i++) {
                if (auxText[2].equals(connections.get(i).get(0).getDstIp().replace("/", ""))) {
                    for (int j = 0; j < connections.get(i).size(); j++) {
                        connections.get(i).get(j).setIncluded(status);
                    }
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rv) {
        filePickerVbox.setAlignment(Pos.CENTER);
        continueButton.setDisable(true);
        backButton.setDisable(true);
    }

}
