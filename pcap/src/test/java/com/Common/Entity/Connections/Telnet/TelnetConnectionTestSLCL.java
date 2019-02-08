package com.Common.Entity.Connections.Telnet;

import static org.junit.jupiter.api.Assertions.*;

import com.Common.Entity.Connections.TelnetConnection;
import com.Common.Registry;
import com.DomainLogicLayer.CommandFactory;
import com.DomainLogicLayer.ReadPcap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pcap4j.packet.TcpPacket;

class TelnetConnectionTestSLCL {

    TelnetConnection tc;

    String _filePath;

    ReadPcap readCommandClient;
    ReadPcap readCommandServer;

    @BeforeEach
    void setUp() {
        _filePath = Registry.PCAPFILEPATH+"telnet/telnet-slcl-c.pcap";
        readCommandClient = (ReadPcap) CommandFactory.instantiateReadPcap(_filePath);
        readCommandClient.execute();

        _filePath = Registry.PCAPFILEPATH+"telnet/telnet-slcl-s.pcap";
        readCommandServer = (ReadPcap) CommandFactory.instantiateReadPcap(_filePath);
        readCommandServer.execute();

        tc = new TelnetConnection();

    }

    //Begin Cliente
    @Test
    void addPacketPlus1Client() {

        int oldSize = tc.getPackets().size();
        tc.addPacket(readCommandClient._output.get(0).get(TcpPacket.class));

        assertEquals(oldSize+1, tc.getPackets().size());

    }

    @Test
    void openingClient() {

        tc.addPacket(readCommandClient._output.get(0).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING,tc.getOpenedStatus());
        tc.addPacket(readCommandClient._output.get(1).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING,tc.getOpenedStatus());

    }

    @Test
    void openedCleanlyClient() {

        for (int i = 0; i < readCommandClient._output.size() ; i++) {
            tc.addPacket(readCommandClient._output.get(i).get(TcpPacket.class));
        }

        assertEquals(TelnetConnection.OPENED_CLEANLY,tc.getOpenedStatus());

    }

    @Test
    void closingClient() {
        for (int i = 0; i < readCommandClient._output.size() - 1 ; i++) {
            tc.addPacket(readCommandClient._output.get(i).get(TcpPacket.class));
        }
        assertEquals(TelnetConnection.CLOSING, tc.getClosedStatus());
    }

    @Test
    void closedCleanlyClient() {
        for (int i = 0; i < readCommandClient._output.size(); i++) {
            tc.addPacket(readCommandClient._output.get(i).get(TcpPacket.class));
        }
        assertEquals(TelnetConnection.CLOSED_CLEANLY, tc.getClosedStatus());
    }
    //End Cliente

    //Begin Servidor
    @Test
    void addPacketPlus1Server() {

        int oldSize = tc.getPackets().size();
        tc.addPacket(readCommandServer._output.get(0).get(TcpPacket.class));

        assertEquals(oldSize+1, tc.getPackets().size());

    }

    @Test
    void openingServer() {

        tc.addPacket(readCommandServer._output.get(0).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING,tc.getOpenedStatus());
        tc.addPacket(readCommandServer._output.get(1).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING,tc.getOpenedStatus());

    }

    @Test
    void openedCleanlyServer() {

        for (int i = 0; i < readCommandServer._output.size() ; i++) {
            tc.addPacket(readCommandServer._output.get(i).get(TcpPacket.class));
        }

        assertEquals(TelnetConnection.OPENED_CLEANLY,tc.getOpenedStatus());

    }

    @Test
    void closingServer() {
        for (int i = 0; i < readCommandServer._output.size() - 1 ; i++) {
            tc.addPacket(readCommandServer._output.get(i).get(TcpPacket.class));
        }
        assertEquals(TelnetConnection.CLOSING, tc.getClosedStatus());
    }

    @Test
    void closedCleanlyServer() {
        for (int i = 0; i < readCommandServer._output.size(); i++) {
            tc.addPacket(readCommandServer._output.get(i).get(TcpPacket.class));
        }
        assertEquals(TelnetConnection.CLOSED_CLEANLY, tc.getClosedStatus());
    }
    //End Servidor


}