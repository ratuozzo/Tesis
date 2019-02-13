package com.DomainLogicLayer;

import com.Common.Entity.Connections.TcpConnection;
import com.Common.Entity.Connections.TelnetConnection;
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
        _filePaths = new ArrayList<String>();

        _filePaths.add(Registry.PCAPFILEPATH+"telnet/telnet-slcl-c.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"telnet/telnet-slcl-s.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"telnet/telnet-slcw-c-cmd.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"telnet/telnet-slcw-s-cmd.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"telnet/telnet-slcw-c-putty.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"telnet/telnet-slcw-s-putty.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"telnet/telnet-swcl-c.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"telnet/telnet-swcl-s.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"telnet/telnet-swcw-c.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"telnet/telnet-swcw-s.pcap");

        _filePaths.add(Registry.PCAPFILEPATH+"ssh/ssh-slcl-c.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ssh/ssh-slcl-s.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ssh/ssh-slcw-c-cmd.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ssh/ssh-slcw-s-cmd.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ssh/ssh-slcw-c-putty.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ssh/ssh-slcw-s-putty.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ssh/ssh-swcl-c.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ssh/ssh-swcl-s.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ssh/ssh-swcw-c-cmd.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ssh/ssh-swcw-s-cmd.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ssh/ssh-swcw-c-putty.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ssh/ssh-swcw-s-putty.pcap");

        _filePaths.add(Registry.PCAPFILEPATH+"ftp/ftp-slcl-c-cmd.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ftp/ftp-slcl-s-cmd.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ftp/ftp-slcl-c-fz.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ftp/ftp-slcl-s-fz.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ftp/ftp-slcw-c-cmd.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ftp/ftp-slcw-s-cmd.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ftp/ftp-slcw-c-fz.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ftp/ftp-slcw-s-fz.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ftp/ftp-swcl-c-cmd.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ftp/ftp-swcl-s-cmd.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ftp/ftp-swcl-c-fz.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ftp/ftp-swcl-s-fz.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ftp/ftp-swcw-c-cmd.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ftp/ftp-swcw-s-cmd.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ftp/ftp-swcw-c-fz.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ftp/ftp-swcw-s-fz.pcap");

        _filePaths.add(Registry.PCAPFILEPATH+"http/http-slcl-c-c1.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-slcl-c-c2.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-slcl-c-c3.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-slcl-s-c1.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-slcl-s-c2.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-slcl-s-c3.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-slcw-c-c1.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-slcw-c-c2.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-slcw-c-c3.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-slcw-s-c1.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-slcw-s-c2.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-slcw-s-c3.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-swcl-c-c1.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-swcl-c-c2.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-swcl-c-c3.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-swcl-s-c1.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-swcl-s-c2.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-swcl-s-c3.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-swcw-c-c1.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-swcw-c-c2.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-swcw-c-c3.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-swcw-s-c1.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-swcw-s-c2.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-swcw-s-c3.pcap");

        _filePaths.add(Registry.PCAPFILEPATH+"ping/ping-slcl-c.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ping/ping-slcl-s.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ping/ping-slcw-c.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ping/ping-slcw-s.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ping/ping-swcl-c.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ping/ping-swcl-s.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ping/ping-swcw-c.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ping/ping-swcw-s.pcap");

        ReadMultiplePcaps commandReadMultiple = (ReadMultiplePcaps) CommandFactory.instantiateReadMultiplePcaps(_filePaths);
        commandReadMultiple.execute();

        command = (OrchestrateCommand) CommandFactory.instantiateOrchestrateCommand(commandReadMultiple.getOutput());
        command.execute();
    }

    @Test
    void GetProtocolClassTest(){

    }

    @Test
    void TelnetClasification(){
        ArrayList<TelnetConnection> telnet = new ArrayList<>();

        int count = 0;
        for (TcpConnection connection: command.getTcpConnections()) {
            if (connection.getClass() == TelnetConnection.class) {
                count++;
            }
        }
        assertEquals(10,count);

    }


}