package com.DomainLogicLayer.Commands;

import com.Common.Registry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class WriteToCSVTest {

    static WriteToCSV writeToCSVCommandTrain;
    static WriteToCSV writeToCSVCommandBigFlows;
    static WriteToCSV writeToCSVCommandDownloaded;

    @BeforeAll
    static void setUp() {

        ArrayList<String> _filePathBigFlow = new ArrayList<>();
        _filePathBigFlow.add(Registry.getPCAPFILEPATH() +"bigFlows.pcap");

        ArrayList<String> _filePaths;
        _filePaths = new ArrayList<>();

        _filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-slcl-c.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-slcw-c-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-slcw-c-putty.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-swcl-c.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-swcw-c.pcap");

        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-slcl-c.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-slcw-c-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-slcw-c-putty.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-swcl-c.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-swcw-c-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-swcw-c-putty.pcap");

        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-slcl-c-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-slcl-c-fz.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-slcw-c-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-slcw-c-fz.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-swcl-c-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-swcl-c-fz.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-swcw-c-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-swcw-c-fz.pcap");

        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcl-c-c1.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcl-c-c2.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcl-c-c3.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcw-c-c1.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcw-c-c2.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcw-c-c3.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcl-c-c1.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcl-c-c2.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcl-c-c3.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcw-c-c1.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcw-c-c2.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcw-c-c3.pcap");

        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-slcl-c.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-slcw-c.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-swcl-c.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-swcw-c.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-slcl-c-onlyC.pcap");

        ReadMultiplePcaps commandReadMultipleBigFlow = (ReadMultiplePcaps) CommandFactory.instantiateReadMultiplePcaps(_filePathBigFlow);
        commandReadMultipleBigFlow.execute();

        Orchestrate commandOrchestrateBigFlow = (Orchestrate) CommandFactory.instantiateOrchestrate(commandReadMultipleBigFlow.getPackets());
        commandOrchestrateBigFlow.execute();

        writeToCSVCommandBigFlows = (WriteToCSV) CommandFactory.instantiateWriteToCSV(commandOrchestrateBigFlow.getClosedConnections(),"evaluate.csv");
        writeToCSVCommandBigFlows.execute();

        ReadMultiplePcaps commandReadMultiple = (ReadMultiplePcaps) CommandFactory.instantiateReadMultiplePcaps(_filePaths);
        commandReadMultiple.execute();

        Orchestrate commandOrchestrate = (Orchestrate) CommandFactory.instantiateOrchestrate(commandReadMultiple.getPackets());
        commandOrchestrate.execute();

        writeToCSVCommandTrain = (WriteToCSV) CommandFactory.instantiateWriteToCSV(commandOrchestrate.getClosedConnections(),"train.csv");
        writeToCSVCommandTrain.execute();
    }

    @Test
    void csvWriter(){

        assertTrue(true);

    }

}