package com.DomainLogicLayer.Commands;

import com.Common.Registry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrainNeuralNetTest {

    static TrainNeuralNet _trainNeuralNetCommand;

    @BeforeAll
    static void setUp() {
        _trainNeuralNetCommand = (TrainNeuralNet) CommandFactory.instantiateTrainNeuralNet(
                Registry.getRESOURCEFILEPATH()+"train.exe",
                0.00023,
                128,
                6000
                );
        _trainNeuralNetCommand.execute();
    }

    @Test
    void TrainNeuralNet(){

        assertNotNull(_trainNeuralNetCommand);

    }
}