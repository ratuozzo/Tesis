package com.DomainLogicLayer;

import com.Common.Entity.Connections.*;
import com.Common.Registry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrchestrateCommandTestServer {


    static OrchestrateCommand command;

    @BeforeAll
    static void setUp() {
        ArrayList<String> _filePaths;
        _filePaths = new ArrayList<>();


        _filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-slcl-s.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-slcw-s-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-slcw-s-putty.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-swcl-s.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-swcw-s.pcap");

        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-slcl-s.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-slcw-s-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-slcw-s-putty.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-swcl-s.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-swcw-s-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-swcw-s-putty.pcap");

        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-slcl-s-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-slcl-s-fz.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-slcw-s-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-slcw-s-fz.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-swcl-s-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-swcl-s-fz.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-swcw-s-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-swcw-s-fz.pcap");

        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcl-s-c1.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcl-s-c2.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcl-s-c3.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcw-s-c1.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcw-s-c2.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcw-s-c3.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcl-s-c1.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcl-s-c2.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcl-s-c3.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcw-s-c1.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcw-s-c2.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcw-s-c3.pcap");

        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-slcl-s.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-slcw-s.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-swcl-s.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-swcw-s.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-slcl-c-onlyC.pcap");

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
        assertEquals(5,count);

    }

    @Test
    void IcmpClassification(){

        int count = 0;
        for (Connection connection: command.getClosedConnections()) {
            if (connection.getClass() == IcmpConnection.class) {
                count++;
            }
        }
        assertEquals(4,count);

    }

    @Test
    void SshClassification(){

        int count = 0;
        for (Connection connection: command.getClosedConnections()) {
            if (connection.getClass() == SshConnection.class) {
                count++;
            }
        }
        assertEquals(6,count);

    }

    @Test
    void FtpCommandClassification(){

        int count = 0;
        for (Connection connection: command.getClosedConnections()) {
            if (connection.getClass() == FtpCommandConnection.class) {
                count++;
            }
        }
        assertEquals(10,count);

    }

    @Test
    void FtpDataClassification(){

        int count = 0;
        for (Connection connection: command.getClosedConnections()) {
            if (connection.getClass() == FtpDataConnection.class) {
                count++;
            }
        }
        assertEquals(10,count);

    }

    @Test
    void HttpClassification(){

        int count = 0;
        for (Connection connection: command.getClosedConnections()) {
            if (connection.getClass() == HttpConnection.class) {
                count++;
            }
        }
        assertEquals(15,count);

    }

    @Test
    void OtherClassification(){

        int count = 0;
        for (Connection connection: command.getClosedConnections()) {
            if (connection.getClass() == OtherConnection.class) {
                count++;
            }
        }
        assertEquals(14,count);

    }


}