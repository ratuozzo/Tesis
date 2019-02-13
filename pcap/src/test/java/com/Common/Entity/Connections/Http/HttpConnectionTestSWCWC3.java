package com.Common.Entity.Connections.Http;

import com.Common.Entity.Connections.HttpConnection;
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

class HttpConnectionTestSWCWC3 {

    HttpConnection hc;

    String _filePath;

    ReadPcap readCommandClient;
    ReadPcap readCommandServer;

    @BeforeEach
    void setUp() {
        _filePath = Registry.PCAPFILEPATH+"http/http-swcw-c-c3.pcap";
        readCommandClient = (ReadPcap) CommandFactory.instantiateReadPcap(_filePath);
        readCommandClient.execute();

        _filePath = Registry.PCAPFILEPATH+"http/http-swcw-s-c3.pcap";
        readCommandServer = (ReadPcap) CommandFactory.instantiateReadPcap(_filePath);
        readCommandServer.execute();

        ArrayList<Socket> sockets = Socket.packetToSockets(readCommandClient.getOutput().get(0));

        hc = new HttpConnection(sockets.get(0), sockets.get(1));

    }

    //Begin Cliente
    @Test
    void addPacketPlus1Client() {

        int oldSize = hc.getPackets().size();
        hc.addPacket(readCommandClient.getOutput().get(0).get(TcpPacket.class));

        assertEquals(oldSize+1, hc.getPackets().size());

    }

    @Test
    void openingClient() {

        hc.addPacket(readCommandClient.getOutput().get(0).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING, hc.getOpenedStatus());
        hc.addPacket(readCommandClient.getOutput().get(1).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING, hc.getOpenedStatus());

    }

    @Test
    void openedCleanlyClient() {

        for (int i = 0; i < readCommandClient.getOutput().size() ; i++) {
            hc.addPacket(readCommandClient.getOutput().get(i).get(TcpPacket.class));
        }

        assertEquals(TelnetConnection.OPENED_CLEANLY, hc.getOpenedStatus());

    }

    @Test
    void closingClient() {
        for (int i = 0; i < readCommandClient.getOutput().size() - 1; i++) {
            hc.addPacket(readCommandClient.getOutput().get(i).get(TcpPacket.class));
        }
        assertEquals(TelnetConnection.CLOSING, hc.getClosedStatus());
    }

    @Test
    void closedCleanlyClient() {
        for (int i = 0; i < readCommandClient.getOutput().size(); i++) {
            hc.addPacket(readCommandClient.getOutput().get(i).get(TcpPacket.class));
        }
        assertEquals(TelnetConnection.CLOSED_CLEANLY, hc.getClosedStatus());
    }
    //End Cliente

    //Begin Servidor
    @Test
    void addPacketPlus1Server() {

        int oldSize = hc.getPackets().size();
        hc.addPacket(readCommandServer.getOutput().get(0).get(TcpPacket.class));

        assertEquals(oldSize+1, hc.getPackets().size());

    }

    @Test
    void openingServer() {

        hc.addPacket(readCommandServer.getOutput().get(0).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING, hc.getOpenedStatus());
        hc.addPacket(readCommandServer.getOutput().get(1).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING, hc.getOpenedStatus());

    }

    @Test
    void openedCleanlyServer() {

        for (int i = 0; i < readCommandServer.getOutput().size() ; i++) {
            hc.addPacket(readCommandServer.getOutput().get(i).get(TcpPacket.class));
        }

        assertEquals(TelnetConnection.OPENED_CLEANLY, hc.getOpenedStatus());

    }

    @Test
    void closingServer() {
        for (int i = 0; i < readCommandServer.getOutput().size() - 1; i++) {
            hc.addPacket(readCommandServer.getOutput().get(i).get(TcpPacket.class));
        }
        assertEquals(TelnetConnection.CLOSING, hc.getClosedStatus());
    }

    @Test
    void closedCleanlyServer() {
        for (int i = 0; i < readCommandServer.getOutput().size(); i++) {
            hc.addPacket(readCommandServer.getOutput().get(i).get(TcpPacket.class));
        }
        assertEquals(TelnetConnection.CLOSED_CLEANLY, hc.getClosedStatus());
    }
    //End Servidor


}