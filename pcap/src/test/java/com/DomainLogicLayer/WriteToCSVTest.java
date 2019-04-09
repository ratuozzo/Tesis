package com.DomainLogicLayer;

import com.Common.Registry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class WriteToCSVTest {

    static WriteToCSV writeToCSVCommand;

    @BeforeAll
    static void setUp() {

        ArrayList<String> _filePaths;
        _filePaths = new ArrayList<>();
        _filePaths.add(Registry.getPCAPFILEPATH() +"bigFlows.pcap");



        ReadMultiplePcaps commandReadMultiple = (ReadMultiplePcaps) CommandFactory.instantiateReadMultiplePcaps(_filePaths);
        commandReadMultiple.execute();

        Orchestrate commandOrchestrate = (Orchestrate) CommandFactory.instantiateOrchestrateCommand(commandReadMultiple.getPackets());
        commandOrchestrate.execute();

        writeToCSVCommand = (WriteToCSV) CommandFactory.instantiateWriteToCSV(commandOrchestrate.getClosedConnections());
        writeToCSVCommand.execute();

    }

    @Test
    void csvWriter(){

        assertTrue(true);

    }

}