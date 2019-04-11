package com.DomainLogicLayer.Commands;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrainNeuralNetTest {

    static TrainNeuralNet _trainNeuralNetCommand;

    @BeforeAll
    static void setUp() {
        _trainNeuralNetCommand = (TrainNeuralNet) CommandFactory.instantiateTrainNeuralNet();
        _trainNeuralNetCommand.execute();
    }

    @Test
    void TrainNeuralNet(){

        assertNotNull(_trainNeuralNetCommand.getVae());

    }
}