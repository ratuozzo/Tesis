package com.DomainLogicLayer.Commands;

import java.io.IOException;

public class TrainNeuralNet extends Command {

    private String _filePath;
    private Double _learningRate;
    private int _batchSize;
    private int _epochs;

    public TrainNeuralNet(String filePath, Double learningRate, int batchSize, int epochs) {

        _filePath = filePath;
        _learningRate = learningRate;
        _batchSize = batchSize;
        _epochs = epochs;

    }

    @Override
    public void execute() {
        String[] cmdArray = new String[5];

        cmdArray[0] = "train.exe";
        cmdArray[1] = _filePath;
        cmdArray[2] = _learningRate.toString();
        cmdArray[3] = Integer.toString(_batchSize);
        cmdArray[4] = Integer.toString(_epochs);

        try {
            Runtime.getRuntime().exec(cmdArray,null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
