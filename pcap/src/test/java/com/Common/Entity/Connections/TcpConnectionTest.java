package com.Common.Entity.Connections;

import com.Common.Registry;
import com.DomainLogicLayer.CommandFactory;
import com.DomainLogicLayer.ReadPcap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import static org.junit.jupiter.api.Assertions.*;

class TcpConnectionTest {

    TelnetConnection tc;

    String _filePath;

    ReadPcap readCommand;

    @BeforeEach
    void setUp() {
        _filePath = Registry.PCAPFILEPATH+"telnet/telnet-slcw-c-cmd.pcap";
        readCommand = (ReadPcap) CommandFactory.instantiateReadPcap(_filePath);
        readCommand.execute();

        tc = new TelnetConnection();

        for (int i = 0; i < readCommand._output.size() ; i++) {
            tc.addPacket(readCommand._output.get(i).get(TcpPacket.class));
        }

    }

    @Test
    void getPacketInteger() {

        assertEquals(8,tc.packetToInteger(tc._packets.get(0)));
        assertEquals(8+4,tc.packetToInteger(tc._packets.get(1)));
        assertEquals(4,tc.packetToInteger(tc._packets.get(2)));
        assertEquals(4+2,tc.packetToInteger(tc._packets.get(293)));
        assertEquals(1,tc.packetToInteger(tc._packets.get(296)));

    }

}