package com.DomainLogicLayer.Commands;

import com.Common.Registry;
import com.DomainLogicLayer.Commands.CommandFactory;
import com.DomainLogicLayer.Commands.ReadPcap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReadPcapTest {

    static String _filePath;

    @BeforeAll
    static void setUp() {
       _filePath = Registry.getPCAPFILEPATH() +"telnet/telnet-slcl-c.pcap";
    }

    @Test
    void readPcap() {

        ReadPcap command = (ReadPcap) CommandFactory.instantiateReadPcap(_filePath);
        command.execute();

        assertNotNull(command.getOutput());
        assertEquals(180, command.getOutput().size());
    }
}