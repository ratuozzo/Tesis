package com.DomainLogicLayer;

import com.Common.Entity.Connections.*;
import com.Common.Registry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrchestrateCommandTest {


    static OrchestrateCommand command;

    @BeforeAll
    static void setUp() {
        ArrayList<String> _filePaths;
        _filePaths = new ArrayList<>();

        //_filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-slcl-c.pcap");
        //_filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-slcl-s.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-slcw-c-cmd.pcap");
        /*_filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-slcw-s-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-slcw-c-putty.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-slcw-s-putty.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-swcl-c.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-swcl-s.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-swcw-c.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-swcw-s.pcap");

        /*_filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-slcl-c.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-slcl-s.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-slcw-c-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-slcw-s-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-slcw-c-putty.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-slcw-s-putty.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-swcl-c.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-swcl-s.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-swcw-c-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-swcw-s-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-swcw-c-putty.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-swcw-s-putty.pcap");*/

        /*_filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-slcl-c-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-slcl-s-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-slcl-c-fz.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-slcl-s-fz.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-slcw-c-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-slcw-s-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-slcw-c-fz.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-slcw-s-fz.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-swcl-c-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-swcl-s-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-swcl-c-fz.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-swcl-s-fz.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-swcw-c-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-swcw-s-cmd.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-swcw-c-fz.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-swcw-s-fz.pcap");

        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcl-c-c1.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcl-c-c2.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcl-c-c3.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcl-s-c1.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcl-s-c2.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcl-s-c3.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcw-c-c1.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcw-c-c2.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcw-c-c3.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcw-s-c1.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcw-s-c2.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcw-s-c3.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcl-c-c1.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcl-c-c2.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcl-c-c3.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcl-s-c1.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcl-s-c2.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcl-s-c3.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcw-c-c1.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcw-c-c2.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcw-c-c3.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcw-s-c1.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcw-s-c2.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcw-s-c3.pcap");

        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-slcl-c.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-slcl-s.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-slcw-c.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-slcw-s.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-swcl-c.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-swcl-s.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-swcw-c.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-swcw-s.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-slcl-c-onlyC.pcap");*/

        ReadMultiplePcaps commandReadMultiple = (ReadMultiplePcaps) CommandFactory.instantiateReadMultiplePcaps(_filePaths);
        commandReadMultiple.execute();

        command = (OrchestrateCommand) CommandFactory.instantiateOrchestrateCommand(commandReadMultiple.getOutput());
        command.execute();
    }

    @Test
    void TelnetClassification(){

        int count = 0;
        for (Connection connection: command.getConnections()) {
            if (connection.getClass() == TelnetConnection.class) {
                count++;
            }
        }
        assertEquals(10,count);

    }

    @Test
    void IcmpClassification(){

        int count = 0;
        for (Connection connection: command.getConnections()) {
            if (connection.getClass() == IcmpConnection.class) {
                count++;
            }
        }
        assertEquals(8+4,count);

    }

    @Test
    void SshClassification(){

        int count = 0;
        for (Connection connection: command.getConnections()) {
            if (connection.getClass() == SshConnection.class) {
                count++;
            }
        }
        assertEquals(12,count);

    }

    @Test
    void FtpClassification(){

        int count = 0;
        for (Connection connection: command.getConnections()) {
            if (connection.getClass() == FtpConnection.class) {
                count++;
            }
        }
        assertEquals(16,count);

    }

    @Test
    void HttpClassification(){

        int count = 0;
        for (Connection connection: command.getConnections()) {
            if (connection.getClass() == HttpConnection.class) {
                count++;
            }
        }
        assertEquals(24,count);

    }


}