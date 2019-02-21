package com.DomainLogicLayer;

import com.Common.Entity.Connections.*;
import com.Common.Registry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrchestrateCommandTestBigFlows {


    static OrchestrateCommand command;

    @BeforeAll
    static void setUp() {
        ArrayList<String> _filePaths;
        _filePaths = new ArrayList<>();


        _filePaths.add(Registry.getPCAPFILEPATH() +"bigFlows.pcap");

        ReadMultiplePcaps commandReadMultiple = (ReadMultiplePcaps) CommandFactory.instantiateReadMultiplePcaps(_filePaths);
        commandReadMultiple.execute();

        command = (OrchestrateCommand) CommandFactory.instantiateOrchestrateCommand(commandReadMultiple.getOutput());
        command.execute();
    }

    @Test
    void TelnetClassification(){

        int count = 0;
        for (Connection connection: command.getClosedConnections()) {
            if (connection.getClass() == TelnetConnection.class) {
                count++;
            }
        }
        assertEquals(0,count);

    }

    @Test
    void IcmpClassification(){

        int count = 0;
        for (Connection connection: command.getClosedConnections()) {
            if (connection.getClass() == IcmpConnection.class) {
                count++;
            }
        }
        assertEquals(1859,count);

    }

    @Test
    void SshClassification(){

        int count = 0;
        for (Connection connection: command.getClosedConnections()) {
            if (connection.getClass() == SshConnection.class) {
                count++;
            }
        }
        assertEquals(22,count);

    }

    @Test
    void FtpCommandClassification(){

        int count = 0;
        for (Connection connection: command.getClosedConnections()) {
            if (connection.getClass() == FtpCommandConnection.class) {
                count++;
            }
        }
        assertEquals(5,count);

    }

    @Test
    void FtpDataClassification(){

        int count = 0;
        for (Connection connection: command.getClosedConnections()) {
            if (connection.getClass() == FtpDataConnection.class) {
                count++;
            }
        }
        assertEquals(0,count);

    }

    @Test
    void HttpClassification(){

        int count = 0;
        for (Connection connection: command.getClosedConnections()) {
            if (connection.getClass() == HttpConnection.class) {
                count++;
            }
        }
        assertEquals(3214,count);

    }

    @Test
    void OtherClassification(){

        int count = 0;
        for (Connection connection: command.getClosedConnections()) {
            if (connection.getClass() == OtherConnection.class) {
                count++;
            }
        }
        assertEquals(1929,count);

    }


}