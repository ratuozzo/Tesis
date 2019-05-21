package com.DomainLogicLayer.Commands;

import com.Common.Registry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TrainAndEvaluateData extends Command {


    private String _filePathTrain;
    private String _filePathEaluate;
    private Double _learningRate;
    private int _batchSize;
    private int _epochs;
    public ArrayList<String> _output;

    public TrainAndEvaluateData(String filePathTrain, String filePathEvaluate, Double learningRate, int batchSize, int epochs) {

        _filePathTrain = filePathTrain;
        _filePathEaluate = filePathEvaluate;
        _learningRate = learningRate;
        _batchSize = batchSize;
        _epochs = epochs;
        _output = new ArrayList<>();
    }

    @Override
    public void execute() {
        String[] cmdArray = new String[7];

        cmdArray[0] = Registry.getRESOURCEFILEPATH()+ "trainandevaluate.exe";
        cmdArray[1] = _filePathTrain;
        cmdArray[2] = _filePathEaluate;
        cmdArray[3] = _learningRate.toString();
        cmdArray[4] = Integer.toString(_batchSize);
        cmdArray[5] = Integer.toString(_epochs);
        cmdArray[6] = Registry.getRESOURCEFILEPATH();

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
                if (line.startsWith("[")) {
                    line=line.replace("[","");
                    line=line.split("]")[0];
                    line=line.replace(" ",",");
                    _output.add(line);
                }
            }
            process.waitFor();
            /*for (int i = 0; i < _output.size() ; i++) {
                System.out.println(_output.get(i));
            }*/
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
