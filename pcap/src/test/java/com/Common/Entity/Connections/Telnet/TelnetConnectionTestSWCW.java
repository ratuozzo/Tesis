package com.Common.Entity.Connections.Telnet;

import com.Common.Entity.Connections.TelnetConnection;
import com.Common.Entity.Socket;
import com.Common.Registry;
import com.DomainLogicLayer.CommandFactory;
import com.DomainLogicLayer.ReadPcap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pcap4j.packet.TcpPacket;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TelnetConnectionTestSWCW {

    TelnetConnection tc;

    String _filePath;

    ReadPcap readCommandClient;
    ReadPcap readCommandServer;

    @BeforeEach
    void setUp() {
        _filePath = Registry.PCAPFILEPATH+"telnet/telnet-swcw-c.pcap";
        readCommandClient = (ReadPcap) CommandFactory.instantiateReadPcap(_filePath);
        readCommandClient.execute();

        _filePath = Registry.PCAPFILEPATH+"telnet/telnet-swcw-s.pcap";
        readCommandServer = (ReadPcap) CommandFactory.instantiateReadPcap(_filePath);
        readCommandServer.execute();

        ArrayList<Socket> sockets = Socket.packetToSockets(readCommandClient.getOutput().get(0));

        tc = new TelnetConnection(sockets.get(0), sockets.get(1));

    }

    //Begin Cliente
    @Test
    void addPacketPlus1Client() {

        int oldSize = tc.getPackets().size();
        tc.addPacket(readCommandClient.getOutput().get(0).get(TcpPacket.class));

        assertEquals(oldSize+1, tc.getPackets().size());

    }

    @Test
    void openingClient() {

        tc.addPacket(readCommandClient.getOutput().get(0).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING,tc.getOpenedStatus());
        tc.addPacket(readCommandClient.getOutput().get(1).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING,tc.getOpenedStatus());

    }

    @Test
    void openedCleanlyClient() {

        for (int i = 0; i < readCommandClient.getOutput().size() ; i++) {
            tc.addPacket(readCommandClient.getOutput().get(i).get(TcpPacket.class));
        }

        assertEquals(TelnetConnection.OPENED_CLEANLY,tc.getOpenedStatus());

    }

    @Test
    void closingClient() {
        for (int i = 0; i < readCommandClient.getOutput().size() - 1 ; i++) {
            tc.addPacket(readCommandClient.getOutput().get(i).get(TcpPacket.class));
        }
        assertEquals(TelnetConnection.CLOSING, tc.getClosedStatus());
    }

    @Test
    void closedCleanlyClient() {
        for (int i = 0; i < readCommandClient.getOutput().size(); i++) {
            tc.addPacket(readCommandClient.getOutput().get(i).get(TcpPacket.class));
        }
        assertEquals(TelnetConnection.CLOSED_CLEANLY, tc.getClosedStatus());
    }
    //End Cliente

    //Begin Servidor
    @Test
    void addPacketPlus1Server() {

        int oldSize = tc.getPackets().size();
        tc.addPacket(readCommandServer.getOutput().get(0).get(TcpPacket.class));

        assertEquals(oldSize+1, tc.getPackets().size());

    }

    @Test
    void openingServer() {

        tc.addPacket(readCommandServer.getOutput().get(0).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING,tc.getOpenedStatus());
        tc.addPacket(readCommandServer.getOutput().get(1).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING,tc.getOpenedStatus());

    }

    @Test
    void openedCleanlyServer() {

        for (int i = 0; i < readCommandServer.getOutput().size() ; i++) {
            tc.addPacket(readCommandServer.getOutput().get(i).get(TcpPacket.class));
        }

        assertEquals(TelnetConnection.OPENED_CLEANLY,tc.getOpenedStatus());

    }

    @Test
    void closingServer() {
        for (int i = 0; i < readCommandServer.getOutput().size() - 1 ; i++) {
            tc.addPacket(readCommandServer.getOutput().get(i).get(TcpPacket.class));
        }
        assertEquals(TelnetConnection.CLOSING, tc.getClosedStatus());
    }

    @Test
    void closedCleanlyServer() {
        for (int i = 0; i < readCommandServer.getOutput().size(); i++) {
            tc.addPacket(readCommandServer.getOutput().get(i).get(TcpPacket.class));
        }
        assertEquals(TelnetConnection.CLOSED_CLEANLY, tc.getClosedStatus());
    }
    //End Servidor


}