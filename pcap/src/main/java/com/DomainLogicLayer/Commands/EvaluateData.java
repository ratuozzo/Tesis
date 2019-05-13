package com.DomainLogicLayer.Commands;

import java.io.IOException;

public class EvaluateData extends Command {


    private String _filePath;;
    private Double _treshold;

    public EvaluateData(String filePath, Double treshold) {

        _filePath = filePath;
        _treshold = treshold;

    }

    @Override
    public void execute() {
        String[] cmdArray = new String[5];

        cmdArray[0] = "evaluate.exe";
        cmdArray[1] = _filePath;
        cmdArray[3] = _treshold.toString();

        try {
            Runtime.getRuntime().exec(cmdArray,null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
