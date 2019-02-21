package com.DomainLogicLayer.Filters;

import com.Common.Entity.Connections.*;
import com.Common.Registry;
import com.DomainLogicLayer.CommandFactory;
import com.DomainLogicLayer.Orchestrate;
import com.DomainLogicLayer.ReadMultiplePcaps;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FilterByProtocolTest {

    static FilterByProtocol command;

    @BeforeAll
    static void setUp() {
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


        ReadMultiplePcaps commandReadMultiple = (ReadMultiplePcaps) CommandFactory.instantiateReadMultiplePcaps(_filePaths);
        commandReadMultiple.execute();



        Orchestrate orchCommand = (Orchestrate) CommandFactory.instantiateOrchestrateCommand(commandReadMultiple.getPackets());
        orchCommand.execute();

        command = (FilterByProtocol) CommandFactory.instantiateFilterByProtocol(orchCommand.getClosedConnections());
        command.execute();
    }

    @Test
    void TelnetClassification(){

        int connectionSize = 0;
        for (ArrayList<Connection> protocolConnections: command.getOutput()) {
                if (protocolConnections.get(0).getClass() == TelnetConnection.class) {
                    connectionSize = protocolConnections.size();
                }
        }
        assertEquals(5,connectionSize);

    }

    @Test
    void SshClassification(){

        int connectionSize = 0;
        for (ArrayList<Connection> protocolConnections: command.getOutput()) {
            if (protocolConnections.get(0).getClass() == SshConnection.class) {
                connectionSize = protocolConnections.size();
            }
        }
        assertEquals(6,connectionSize);

    }

    @Test
    void FtpCommandClassification(){

        int connectionSize = 0;
        for (ArrayList<Connection> protocolConnections: command.getOutput()) {
            if (protocolConnections.get(0).getClass() == FtpCommandConnection.class) {
                connectionSize = protocolConnections.size();
            }
        }
        assertEquals(10,connectionSize);

    }

    @Test
    void FtpDataClassification(){

        int connectionSize = 0;
        for (ArrayList<Connection> protocolConnections: command.getOutput()) {
            if (protocolConnections.get(0).getClass() == FtpDataConnection.class) {
                connectionSize = protocolConnections.size();
            }
        }
        assertEquals(10,connectionSize);

    }

    @Test
    void HttpClassification(){

        int connectionSize = 0;
        for (ArrayList<Connection> protocolConnections: command.getOutput()) {
            if (protocolConnections.get(0).getClass() == HttpConnection.class) {
                connectionSize = protocolConnections.size();
            }
        }
        assertEquals(15,connectionSize);

    }

    @Test
    void IcmpClassification(){

        int connectionSize = 0;
        for (ArrayList<Connection> protocolConnections: command.getOutput()) {
            if (protocolConnections.get(0).getClass() == IcmpConnection.class) {
                connectionSize = protocolConnections.size();
            }
        }
        assertEquals(4,connectionSize);

    }

    @Test
    void OtherClassification(){

        int connectionSize = 0;
        for (ArrayList<Connection> protocolConnections: command.getOutput()) {
            if (protocolConnections.get(0).getClass() == OtherConnection.class) {
                connectionSize = protocolConnections.size();
            }
        }
        assertEquals(14,connectionSize);

    }


}