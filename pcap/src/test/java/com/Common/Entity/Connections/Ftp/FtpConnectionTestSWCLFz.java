package com.Common.Entity.Connections.Ftp;

import com.Common.Entity.Connections.FtpConnection;
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

class FtpConnectionTestSWCLFz {

    FtpConnection fc;

    String _filePath;

    ReadPcap readCommandClient;
    ReadPcap readCommandServer;

    @BeforeEach
    void setUp() {
        _filePath = Registry.PCAPFILEPATH+"ftp/ftp-swcl-c-fz.pcap";
        readCommandClient = (ReadPcap) CommandFactory.instantiateReadPcap(_filePath);
        readCommandClient.execute();

        _filePath = Registry.PCAPFILEPATH+"ftp/ftp-swcl-s-fz.pcap";
        readCommandServer = (ReadPcap) CommandFactory.instantiateReadPcap(_filePath);
        readCommandServer.execute();

        ArrayList<Socket> sockets = Socket.packetToSockets(readCommandClient._output.get(0));

        fc = new FtpConnection(sockets.get(0), sockets.get(1));

    }

    //Begin Cliente
    @Test
    void addPacketPlus1Client() {

        int oldSize = fc.getPackets().size();
        fc.addPacket(readCommandClient._output.get(0).get(TcpPacket.class));

        assertEquals(oldSize+1, fc.getPackets().size());

    }

    @Test
    void openingClient() {

        fc.addPacket(readCommandClient._output.get(0).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING, fc.getOpenedStatus());
        fc.addPacket(readCommandClient._output.get(1).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING, fc.getOpenedStatus());

    }

    @Test
    void openedCleanlyClient() {

        for (int i = 0; i < readCommandClient._output.size() ; i++) {
            fc.addPacket(readCommandClient._output.get(i).get(TcpPacket.class));
        }

        assertEquals(TelnetConnection.OPENED_CLEANLY, fc.getOpenedStatus());

    }

    @Test
    void closingClient() {
        for (int i = 0; i < readCommandClient._output.size() - 1; i++) {
            fc.addPacket(readCommandClient._output.get(i).get(TcpPacket.class));
        }
        assertEquals(TelnetConnection.CLOSING, fc.getClosedStatus());
    }

    @Test
    void closedCleanlyClient() {
        for (int i = 0; i < readCommandClient._output.size(); i++) {
            fc.addPacket(readCommandClient._output.get(i).get(TcpPacket.class));
        }
        assertEquals(TelnetConnection.CLOSED_CLEANLY, fc.getClosedStatus());
    }
    //End Cliente

    //Begin Servidor
    @Test
    void addPacketPlus1Server() {

        int oldSize = fc.getPackets().size();
        fc.addPacket(readCommandServer._output.get(0).get(TcpPacket.class));

        assertEquals(oldSize+1, fc.getPackets().size());

    }

    @Test
    void openingServer() {

        fc.addPacket(readCommandServer._output.get(0).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING, fc.getOpenedStatus());
        fc.addPacket(readCommandServer._output.get(1).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING, fc.getOpenedStatus());

    }

    @Test
    void openedCleanlyServer() {

        for (int i = 0; i < readCommandServer._output.size() ; i++) {
            fc.addPacket(readCommandServer._output.get(i).get(TcpPacket.class));
        }

        assertEquals(TelnetConnection.OPENED_CLEANLY, fc.getOpenedStatus());

    }

    @Test
    void closingServer() {
        for (int i = 0; i < readCommandServer._output.size() - 1; i++) {
            fc.addPacket(readCommandServer._output.get(i).get(TcpPacket.class));
        }
        assertEquals(TelnetConnection.CLOSING, fc.getClosedStatus());
    }

    @Test
    void closedCleanlyServer() {
        for (int i = 0; i < readCommandServer._output.size(); i++) {
            fc.addPacket(readCommandServer._output.get(i).get(TcpPacket.class));
        }
        assertEquals(TelnetConnection.CLOSED_CLEANLY, fc.getClosedStatus());
    }
    //End Servidor


}