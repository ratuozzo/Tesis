package com.Common.Entity.Connections;

import com.Common.Entity.Socket;
import com.Common.Registry;
import com.DomainLogicLayer.CommandFactory;
import com.DomainLogicLayer.ReadPcap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pcap4j.packet.TcpPacket;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TcpConnectionTest {

    TelnetConnection tc;

    String _filePath;

    ReadPcap readCommand;

    @BeforeEach
    void setUp() {
        _filePath = Registry.getPCAPFILEPATH() +"telnet/telnet-slcw-c-cmd.pcap";
        readCommand = (ReadPcap) CommandFactory.instantiateReadPcap(_filePath);
        readCommand.execute();

        ArrayList<Socket> sockets = Socket.packetToSockets(readCommand.getOutput().get(0));

        tc = new TelnetConnection(sockets.get(0), sockets.get(1));

        for (int i = 0; i < readCommand.getOutput().size() ; i++) {
            tc.addPacket(readCommand.getOutput().get(i).get(TcpPacket.class));
        }

    }

    @Test
    void getPacketInteger() {

        assertEquals(8,tc.packetToInteger(tc._packets.get(0).get(TcpPacket.class)));
        assertEquals(8+4,tc.packetToInteger(tc._packets.get(1).get(TcpPacket.class)));
        assertEquals(4,tc.packetToInteger(tc._packets.get(2).get(TcpPacket.class)));
        assertEquals(4+2,tc.packetToInteger(tc._packets.get(293).get(TcpPacket.class)));
        assertEquals(1,tc.packetToInteger(tc._packets.get(296).get(TcpPacket.class)));

    }

}