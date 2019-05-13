package com.DomainLogicLayer.Commands;

import com.Common.Registry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class EvaluateDataTest {

    static TrainNeuralNet _trainNeuralNetCommand;
    static EvaluateData _evaluateDataCommand;

    @BeforeAll
    static void setUp() {
        _trainNeuralNetCommand = (TrainNeuralNet) CommandFactory.instantiateTrainNeuralNet(
                Registry.getRESOURCEFILEPATH()+"train.exe",
                0.00023,
                128,
                6000
        );
        _trainNeuralNetCommand.execute();

        _evaluateDataCommand = (EvaluateData) CommandFactory.instantiateEvaluateData(
                Registry.getRESOURCEFILEPATH()+"evaluate.exe",
                0.02
                );
        _evaluateDataCommand.execute();
    }

    @Test
    void TrainNeuralNet(){
        assertNotNull(_evaluateDataCommand);

    }
}