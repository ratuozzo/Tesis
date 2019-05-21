package com.DomainLogicLayer.Commands;

import com.Common.Registry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrainAndEvaluateDataTest {

    static TrainAndEvaluateData _trainAndEvaluateDataCommand;

    @BeforeAll
    static void setUp() {

        _trainAndEvaluateDataCommand = (TrainAndEvaluateData) CommandFactory.instantiateTrainAndEvaluateData(
                Registry.getRESOURCEFILEPATH()+"train.csv",
                Registry.getRESOURCEFILEPATH()+"evaluate.csv",
                0.00023,
                128,
                6000
                );
        _trainAndEvaluateDataCommand.execute();
    }

    @Test
    void TrainNeuralNet(){
        assertNotNull(_trainAndEvaluateDataCommand);

    }
}