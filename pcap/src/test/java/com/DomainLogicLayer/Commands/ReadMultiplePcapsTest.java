package com.DomainLogicLayer.Commands;

import com.Common.Registry;
import com.DomainLogicLayer.Commands.CommandFactory;
import com.DomainLogicLayer.Commands.ReadMultiplePcaps;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReadMultiplePcapsTest {

    static ArrayList<String> _filePaths;

    @BeforeAll
    static void setUp() {
        _filePaths = new ArrayList<String>();
        _filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-slcl-c.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"telnet/telnet-swcl-c.pcap");

        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-slcl-c.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ssh/ssh-swcl-c.pcap");

        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-slcl-c-fz.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ftp/ftp-swcl-c-cmd.pcap");

        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-slcl-c-c1.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"http/http-swcl-c-c3.pcap");

        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-slcl-c.pcap");
        _filePaths.add(Registry.getPCAPFILEPATH() +"ping/ping-swcl-c.pcap");
    }

    @Test
    void readMultiplePcaps() {

        ReadMultiplePcaps command = (ReadMultiplePcaps) CommandFactory.instantiateReadMultiplePcaps(_filePaths);

        command.execute();

        assertNotNull(command.getPackets());
        assertEquals(180+358+8+8+10+13+48+135+667+198, command.getPackets().size());


    }

}