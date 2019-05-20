package com.DomainLogicLayer.Commands;

import com.Common.Registry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TrainAndEvaluateData extends Command {


    private String _filePathTrain;
    private String _filePathEaluate;
    private Double _learningRate;
    private int _batchSize;
    private int _epochs;

    public TrainAndEvaluateData(String filePathTrain, String filePathEvaluate, Double learningRate, int batchSize, int epochs) {

        _filePathTrain = filePathTrain;
        _filePathEaluate = filePathEvaluate;
        _learningRate = learningRate;
        _batchSize = batchSize;
        _epochs = epochs;
    }

    @Override
    public void execute() {
        String[] cmdArray = new String[6];

        cmdArray[0] = Registry.getRESOURCEFILEPATH()+ "trainandevaluate.exe";
        cmdArray[1] = _filePathTrain;
        cmdArray[2] = _filePathEaluate;
        cmdArray[3] = _learningRate.toString();
        cmdArray[4] = Integer.toString(_batchSize);
        cmdArray[5] = Integer.toString(_epochs);

        ProcessBuilder builder = new ProcessBuilder(cmdArray);
        builder.redirectErrorStream(true);
        Process process;
        try {
            process = builder.start();


            InputStreamReader iStream = new  InputStreamReader(process.getInputStream());
            BufferedReader br = new BufferedReader(iStream);

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
