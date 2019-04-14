package com.DomainLogicLayer.Commands;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class EvaluateDataTest {

    static TrainNeuralNet _trainNeuralNetCommand;
    static EvaluateData _evaluateDataCommand;

    @BeforeAll
    static void setUp() {
        _trainNeuralNetCommand = (TrainNeuralNet) CommandFactory.instantiateTrainNeuralNet();
        _trainNeuralNetCommand.execute();

        _evaluateDataCommand = (EvaluateData) CommandFactory.instantiateEvaluateData(_trainNeuralNetCommand.getVae());
        _evaluateDataCommand.execute();
    }

    @Test
    void TrainNeuralNet(){
        Scanner sc = new Scanner(System.in);
        sc.next();
        assertNotNull(_evaluateDataCommand);

    }
}