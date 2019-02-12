package com.Common.Entity.Connections.Ssh;

import com.Common.Entity.Connections.SshConnection;
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

class SshConnectionTestSWCL {

    SshConnection sc;

    String _filePath;

    ReadPcap readCommandClient;
    ReadPcap readCommandServer;

    @BeforeEach
    void setUp() {
        _filePath = Registry.PCAPFILEPATH+"ssh/ssh-swcl-c.pcap";
        readCommandClient = (ReadPcap) CommandFactory.instantiateReadPcap(_filePath);
        readCommandClient.execute();

        _filePath = Registry.PCAPFILEPATH+"ssh/ssh-swcl-s.pcap";
        readCommandServer = (ReadPcap) CommandFactory.instantiateReadPcap(_filePath);
        readCommandServer.execute();

        ArrayList<Socket> sockets = Socket.packetToSockets(readCommandClient._output.get(0));

        sc = new SshConnection(sockets.get(0), sockets.get(1));

    }

    //Begin Cliente
    @Test
    void addPacketPlus1Client() {

        int oldSize = sc.getPackets().size();
        sc.addPacket(readCommandClient._output.get(0).get(TcpPacket.class));

        assertEquals(oldSize+1, sc.getPackets().size());

    }

    @Test
    void openingClient() {

        sc.addPacket(readCommandClient._output.get(0).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING, sc.getOpenedStatus());
        sc.addPacket(readCommandClient._output.get(1).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING, sc.getOpenedStatus());

    }

    @Test
    void openedCleanlyClient() {

        for (int i = 0; i < readCommandClient._output.size() ; i++) {
            sc.addPacket(readCommandClient._output.get(i).get(TcpPacket.class));
        }

        assertEquals(TelnetConnection.OPENED_CLEANLY, sc.getOpenedStatus());

    }

    @Test
    void closedCleanlyClient() {
        for (int i = 0; i < readCommandClient._output.size(); i++) {
            sc.addPacket(readCommandClient._output.get(i).get(TcpPacket.class));
        }
        assertEquals(TelnetConnection.CLOSED_CLEANLY, sc.getClosedStatus());
    }
    //End Cliente

    //Begin Servidor
    @Test
    void addPacketPlus1Server() {

        int oldSize = sc.getPackets().size();
        sc.addPacket(readCommandServer._output.get(0).get(TcpPacket.class));

        assertEquals(oldSize+1, sc.getPackets().size());

    }

    @Test
    void openingServer() {

        sc.addPacket(readCommandServer._output.get(0).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING, sc.getOpenedStatus());
        sc.addPacket(readCommandServer._output.get(1).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING, sc.getOpenedStatus());

    }

    @Test
    void openedCleanlyServer() {

        for (int i = 0; i < readCommandServer._output.size() ; i++) {
            sc.addPacket(readCommandServer._output.get(i).get(TcpPacket.class));
        }

        assertEquals(TelnetConnection.OPENED_CLEANLY, sc.getOpenedStatus());

    }

    @Test
    void closedCleanlyServer() {
        for (int i = 0; i < readCommandServer._output.size(); i++) {
            sc.addPacket(readCommandServer._output.get(i).get(TcpPacket.class));
        }
        assertEquals(TelnetConnection.CLOSED_CLEANLY, sc.getClosedStatus());
    }
    //End Servidor


}