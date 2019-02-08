package com.Common.Entity.Connections;

import static org.junit.jupiter.api.Assertions.*;

import com.Common.Entity.Connections.TelnetConnection;
import com.Common.Registry;
import com.DomainLogicLayer.CommandFactory;
import com.DomainLogicLayer.ReadPcap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pcap4j.packet.TcpPacket;

class ConnectionTelnetTest {

    TelnetConnection tc;

    String _filePath;

    ReadPcap readCommand;

    @BeforeEach
    void setUp() {
        _filePath = Registry.PCAPFILEPATH+"telnet/telnet-slcl-c.pcap";

        readCommand = (ReadPcap) CommandFactory.instantiateReadPcap(_filePath);
        readCommand.execute();

        tc = new TelnetConnection();

    }

    @Test
    void addPacketPlus1() {

        int oldSize = tc.getPackets().size();
        tc.addPacket(readCommand._output.get(0).get(TcpPacket.class));

        assertEquals(oldSize+1, tc.getPackets().size());

    }

    @Test
    void opening() {

        tc.addPacket(readCommand._output.get(0).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING,tc._openedStatus);
        tc.addPacket(readCommand._output.get(1).get(TcpPacket.class));
        assertEquals(TelnetConnection.OPENING,tc._openedStatus);

    }



    @Test
    void openedCleanly() {

        for (int i = 0; i < readCommand._output.size() ; i++) {
            tc.addPacket(readCommand._output.get(i).get(TcpPacket.class));
        }

        assertEquals(TelnetConnection.OPENED_CLEANLY,tc.getOpenedStatus());

    }

    @Test
    void closing() {
        for (int i = 0; i < readCommand._output.size() - 1 ; i++) {
            tc.addPacket(readCommand._output.get(i).get(TcpPacket.class));
        }
        assertEquals(TelnetConnection.CLOSING, tc.getClosedStatus());
    }

    @Test
    void closedCleanly() {
        for (int i = 0; i < readCommand._output.size(); i++) {
            tc.addPacket(readCommand._output.get(i).get(TcpPacket.class));
        }
        assertEquals(TelnetConnection.CLOSED_CLEANLY, tc.getClosedStatus());
    }


}