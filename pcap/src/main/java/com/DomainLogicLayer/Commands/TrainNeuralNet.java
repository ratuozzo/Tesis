package com.DomainLogicLayer.Commands;

import com.Common.Registry;

import java.io.*;

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


        cmdArray[0] = Registry.getRESOURCEFILEPATH()+"train.exe";
        cmdArray[1] = _filePath;
        cmdArray[2] = _learningRate.toString();
        cmdArray[3] = Integer.toString(_batchSize);
        cmdArray[4] = Integer.toString(_epochs);

        ProcessBuilder builder = new ProcessBuilder(cmdArray);
        builder.redirectErrorStream(true);
        Process process = null;
        try {
            process = builder.start();


        InputStreamReader istream = new  InputStreamReader(process.getInputStream());
        BufferedReader br = new BufferedReader(istream);

        String line;
        while ((line = br.readLine()) != null){
            System.out.println(line);
        }

        process.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
