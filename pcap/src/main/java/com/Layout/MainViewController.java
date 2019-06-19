package com.Layout;

import com.Common.Entity.Connections.Connection;
import com.Common.Entity.Rule;
import com.Common.Registry;
import com.DomainLogicLayer.Commands.*;
import com.DomainLogicLayer.Filters.*;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.pcap4j.packet.Packet;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class MainViewController implements Initializable {

    private FileChooser fileChooser = new FileChooser();
    private ArrayList<Packet> _rawPacketsTrain;
    private ArrayList<Packet> _rawPacketsEvaluate;
    private ArrayList<Connection> _closedConnectionsTrain;
    private ArrayList<Connection> _closedConnectionsEvaluate;
    private ArrayList<ArrayList<Connection>> _currentConnections;
    private ArrayList<String> _evaluatedConnections;


    private int _currentPane = 0;

    private HashMap<Integer, Filter> _filteredConnections = new HashMap<>();

    private final String SOURCEIP = "SrcIp";
    private final String DESTINATIONIP = "DstIp";
    private final String SOURCEPORT = "SrcPort";
    private final String DESTINATIONPORT = "DstPort";
    private final String FINALFILTER = "FinalFilter";

    @FXML
    private ScrollPane masonryScrollPane;

    @FXML
    private Pane trainAndEvaluationPane;

    @FXML
    private JFXMasonryPane packetEvaluationPane;

    @FXML
    private AnchorPane paneFilePicker, visualizationPane, rulesPane;

    @FXML
    private GridPane trainFileListPane, evaluationFileListPane;

    @FXML
    private Button continueButton, backButton;

    @FXML
    private VBox trainFilePickerVbox, evaluationFilePickerVbox;

    @FXML
    private TextField learningRate, epochNumber;

    @FXML
    private TableView rulesTable;

    public void continueButtonAction() {

        if (_currentPane == 0) {

            getConnections(_rawPacketsTrain, true);

            FilterBySrcIp filterBySrcIp = new FilterBySrcIp(_closedConnectionsTrain);
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
            trainAndEvaluationPane.toFront();
            continueButton.setDisable(true);
            _currentPane = 2;
        } else if (_currentPane == 2) {
            trainAndEvaluate();
            visualizationPane.toFront();
            loadImages();
            _currentPane = 3;
        } else if (_currentPane == 3) {
            rulesPane.toFront();
            addRulesToTable();
            _currentPane = 4;
        }

    }

    private void addRulesToTable() {
        for (int i = 0; i < _evaluatedConnections.size(); i++) {
            String[] rawRuleString = _evaluatedConnections.get(i).split(",");
            int[] rawRuleInteger = new int[rawRuleString.length];
            for (int j = 0; j < rawRuleString.length; j++) {
                Double number = Double.valueOf(rawRuleString[j]) * 1000;
                rawRuleInteger[j] = number.intValue();
            }
            String srcIp = rawRuleInteger[0]+"."+rawRuleInteger[1]+"."+rawRuleInteger[2]+"."+rawRuleInteger[3];
            String dstIp = rawRuleInteger[4]+"."+rawRuleInteger[5]+"."+rawRuleInteger[6]+"."+rawRuleInteger[7];
            Rule rule = new Rule(
                    srcIp,
                    rawRuleInteger[8],
                    dstIp,
                    rawRuleInteger[9]
            );
            rulesTable.getItems().add(rule);
        }
    }

    private void loadImages() {

        createImageViews();

        File lossFile = new File(Registry.getPLOTFILEPATH() + "lossvsiteration.png");
        File trainFile = new File(Registry.getPLOTFILEPATH() + "train.png");
        File evaluationFile = new File(Registry.getPLOTFILEPATH() + "evaluate.png");
        try {
            Image lossImage = new Image(lossFile.toURI().toURL().toString());
            Image trainImage = new Image(trainFile.toURI().toURL().toString());
            Image evaluationImage = new Image(evaluationFile.toURI().toURL().toString());

            ImageView lossImageView = (ImageView) visualizationPane.getScene().lookup("#lossImageView");
            lossImageView.setImage(lossImage);

            ImageView trainImageView = (ImageView) visualizationPane.getScene().lookup("#trainImageView");
            trainImageView.setImage(trainImage);

            ImageView evaluationImageView = (ImageView) visualizationPane.getScene().lookup("#evaluationImageView");
            evaluationImageView.setImage(evaluationImage);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void createImageViews() {
        ImageView lossImageView = new ImageView();
        lossImageView.setId("lossImageView");
        ImageView trainImageView = new ImageView();
        trainImageView.setId("trainImageView");
        ImageView evaluationImageView = new ImageView();
        evaluationImageView.setId("evaluationImageView");

        Label lossLabel = new Label("Perdida vs Iteraciones");
        Label trainLabel = new Label("Puntuación Entrenamiento");
        Label evaluationLabel = new Label("Puntuación Evaluación");

        visualizationPane.setLeftAnchor(lossLabel, 350.0);
        visualizationPane.setTopAnchor(lossLabel, 10.0);
        visualizationPane.setLeftAnchor(lossImageView, 350.0);
        visualizationPane.setTopAnchor(lossImageView, 30.0);
        lossImageView.setFitWidth(600);
        lossImageView.setFitHeight(350);

        visualizationPane.setLeftAnchor(trainLabel, 120.0);
        visualizationPane.setBottomAnchor(trainLabel, 370.0);
        visualizationPane.setLeftAnchor(trainImageView, 100.0);
        visualizationPane.setBottomAnchor(trainImageView, 15.0);
        trainImageView.setFitWidth(600);
        trainImageView.setFitHeight(350);

        visualizationPane.setLeftAnchor(evaluationLabel, 720.0);
        visualizationPane.setBottomAnchor(evaluationLabel, 370.0);
        visualizationPane.setLeftAnchor(evaluationImageView, 700.0);
        visualizationPane.setBottomAnchor(evaluationImageView, 15.0);
        evaluationImageView.setFitWidth(600);
        evaluationImageView.setFitHeight(350);

        visualizationPane.getChildren().add(lossLabel);
        visualizationPane.getChildren().add(trainLabel);
        visualizationPane.getChildren().add(evaluationLabel);

        visualizationPane.getChildren().add(lossImageView);
        visualizationPane.getChildren().add(trainImageView);
        visualizationPane.getChildren().add(evaluationImageView);
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

    }

    private void getConnections(ArrayList<Packet> input, Boolean train) {
        Orchestrate orchestrate = (Orchestrate) CommandFactory.instantiateOrchestrate(input);
        orchestrate.execute();
        if (train) {
            _closedConnectionsTrain = orchestrate.getClosedConnections();
        } else {
            _closedConnectionsEvaluate = orchestrate.getClosedConnections();
        }
    }

    public void handlePickTrainFilesAction(ActionEvent event) {
        ArrayList<String> fileList = new ArrayList<>();
        List<File> list = fileChooser.showOpenMultipleDialog(paneFilePicker.getScene().getWindow());
        for (int i = 0; i < list.size(); i++) {
            fileList.add(list.get(i).getPath());
            Label fileLabel = new Label(list.get(i).getPath());
            trainFileListPane.add(fileLabel,0,i);
        }
        ReadMultiplePcaps readMultiplePcaps = (ReadMultiplePcaps) CommandFactory.instantiateReadMultiplePcaps(fileList);
        readMultiplePcaps.execute();
        _rawPacketsTrain = readMultiplePcaps.getPackets();
        if (_rawPacketsTrain.size() > 1) {
            continueButton.setDisable(false);
        }
        System.out.println(readMultiplePcaps.getPackets().size());
    }

    public void handlePickEvaluationFilesAction(ActionEvent event) {
        ArrayList<String> fileList = new ArrayList<>();
        List<File> list = fileChooser.showOpenMultipleDialog(trainAndEvaluationPane.getScene().getWindow());
        for (int i = 0; i < list.size(); i++) {
            fileList.add(list.get(i).getPath());
            Label fileLabel = new Label(list.get(i).getPath());
            evaluationFileListPane.add(fileLabel,0,i);
        }
        ReadMultiplePcaps readMultiplePcaps = (ReadMultiplePcaps) CommandFactory.instantiateReadMultiplePcaps(fileList);
        readMultiplePcaps.execute();
        _rawPacketsEvaluate = readMultiplePcaps.getPackets();
        if (_rawPacketsEvaluate.size() > 1) {
            continueButton.setDisable(false);
        }
        System.out.println(readMultiplePcaps.getPackets().size());
    }

    private void trainAndEvaluate() {
        removeExcludedConnections();
        WriteToCSV writeToCSVTrain = (WriteToCSV) CommandFactory.instantiateWriteToCSV(_closedConnectionsTrain, "train.csv");
        writeToCSVTrain.execute();
        getConnections(_rawPacketsEvaluate, false);
        WriteToCSV writeToCSVEvaluate = (WriteToCSV) CommandFactory.instantiateWriteToCSV(_closedConnectionsEvaluate, "evaluate.csv");
        writeToCSVEvaluate.execute();
        TrainAndEvaluateData trainAndEvaluateData = null;
        System.out.println(learningRate.getText());
        System.out.println(epochNumber.getText());
        try {
            int batchSize = countFileLines(Registry.getCSVFILEPATH()+"train.csv");
            trainAndEvaluateData = (TrainAndEvaluateData) CommandFactory.instantiateTrainAndEvaluateData(
                    Registry.getCSVFILEPATH()+"train.csv",
                    Registry.getCSVFILEPATH()+"evaluate.csv",
                    Double.valueOf(learningRate.getText()),
                    batchSize,
                    Integer.valueOf(epochNumber.getText())
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        trainAndEvaluateData.execute();
        _evaluatedConnections = trainAndEvaluateData._output;
        System.out.println("Listo");
    }

    private int countFileLines(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }

    private void removeExcludedConnections() {
        for (int i = 0; i < _closedConnectionsTrain.size(); i++) {
            if (!_closedConnectionsTrain.get(i).getIncluded()) {
                _closedConnectionsTrain.remove(_closedConnectionsTrain.get(i));
            }
        }
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
                        SOURCEPORT,
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
                        FINALFILTER,
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

        if (previousButton.getId().split(",")[1].equals(DESTINATIONIP)) {
            label.setId(SOURCEIP);
            newMasonry(label);
        } else if (previousButton.getId().split(",")[1].equals(DESTINATIONPORT)) {
            label.setId(SOURCEPORT);
            newMasonry(label);
        } else if (previousButton.getId().split(",")[1].equals(FINALFILTER)) {
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
        previousPaneButton.setText("Volver");
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
        output.setLeftAnchor(excludeButton, 60.0);

        output.setBottomAnchor(previousPaneButton, 5.0);
        output.setRightAnchor(previousPaneButton, 5.0);

        output.setBottomAnchor(includeButton,5.0);
        output.setLeftAnchor(includeButton,5.0);

        if (enabled) {
            output.setStyle("-fx-background-color:rgb(" + random.nextInt(255) + "," + random.nextInt(255) + "," + random.nextInt(255) + ")");
        } else {
            output.setStyle("-fx-background-color: grey");
            excludeButton.setDisable(true);
            includeButton.setDisable(false);
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
                if (input.get(i).get(0).getSrcIp().replace("/", "").equals(auxText[0])) {
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
        } else if (button.getId().equals(DESTINATIONIP)) {
            ArrayList<ArrayList<Connection>> connections = _filteredConnections.get(1).getOutput();
            for (int i = 0; i < connections.size(); i++) {
                if (auxText[1].equals(Integer.toString(connections.get(i).get(0).getSrcPort()))) {
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
        trainFilePickerVbox.setAlignment(Pos.CENTER);
        evaluationFilePickerVbox.setAlignment(Pos.CENTER);
        continueButton.setDisable(true);
        backButton.setDisable(true);
        TableView rulesTable = new TableView();
        rulesTable.setEditable(true);
        TableColumn srcIp = new TableColumn("Ip Origen");
        srcIp.setCellValueFactory(new PropertyValueFactory<>("_srcIp"));
        TableColumn srcPort = new TableColumn("Puerto Origen");
        srcPort.setCellValueFactory(new PropertyValueFactory<>("_srcPort"));
        TableColumn dstIp = new TableColumn("Ip Destino");
        dstIp.setCellValueFactory(new PropertyValueFactory<>("_dstIp"));
        TableColumn dstPort = new TableColumn("Puerto Destino");
        dstPort.setCellValueFactory(new PropertyValueFactory<>("_dstPort"));

        rulesTable.getColumns().addAll(srcIp, srcPort, dstIp, dstPort);
        rulesPane.setBottomAnchor(rulesTable, 15.0);
        rulesPane.setLeftAnchor(rulesTable, 15.0);
        rulesPane.setRightAnchor(rulesTable, 15.0);

        rulesPane.getChildren().add(rulesTable);
    }

}
