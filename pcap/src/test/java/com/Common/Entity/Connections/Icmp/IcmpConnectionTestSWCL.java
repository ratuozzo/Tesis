package com.Common.Entity.Connections.Icmp;

import com.Common.Entity.Connections.IcmpConnection;
import com.Common.Registry;
import com.DomainLogicLayer.Commands.CommandFactory;
import com.DomainLogicLayer.Commands.ReadPcap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pcap4j.packet.IcmpV4CommonPacket;
import org.pcap4j.packet.IpV4Packet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IcmpConnectionTestSWCL {

    String _filePath;

    ReadPcap readCommandClient;
    ReadPcap readCommandServer;

    IcmpConnection ic;


    @BeforeEach
    void setUp() {

        _filePath = Registry.getPCAPFILEPATH() +"ping/ping-swcl-c.pcap";
        readCommandClient = (ReadPcap) CommandFactory.instantiateReadPcap(_filePath);
        readCommandClient.execute();

        _filePath = Registry.getPCAPFILEPATH() +"ping/ping-swcl-s.pcap";
        readCommandServer = (ReadPcap) CommandFactory.instantiateReadPcap(_filePath);
        readCommandServer.execute();



        ic = new IcmpConnection(readCommandClient.getOutput().get(0).get(IpV4Packet.class).getHeader().getSrcAddr().toString(),
                readCommandClient.getOutput().get(0).get(IpV4Packet.class).getHeader().getDstAddr().toString());

    }

    //Begin Client
    @Test
    void addPacketPlus1Client() {

        int oldSize = ic.getPackets().size();
        ic.addPacket(readCommandClient.getOutput().get(0).get(IcmpV4CommonPacket.class));

        assertEquals(oldSize+1, ic.getPackets().size());

    }

    @Test
    void openedCleanlyClient() {
        for (int i = 0; i < readCommandClient.getOutput().size() ; i++) {
            ic.addPacket(readCommandClient.getOutput().get(i).get(IcmpV4CommonPacket.class));
        }
        assertEquals(IcmpConnection.OPENED_CLEANLY, ic.getOpenedStatus());
    }

    @Test
    void closedCleanlyClient() {
        for (int i = 0; i < readCommandClient.getOutput().size(); i++) {
            ic.addPacket(readCommandClient.getOutput().get(i).get(IcmpV4CommonPacket.class));
        }
        assertEquals(IcmpConnection.CLOSED_CLEANLY, ic.getClosedStatus());
    }

    @Test
    void notClosedClient() {
        for (int i = 0; i < readCommandClient.getOutput().size() - 1; i++) {
            ic.addPacket(readCommandClient.getOutput().get(i).get(IcmpV4CommonPacket.class));
        }
        assertEquals(IcmpConnection.NOT_CLOSED, ic.getClosedStatus());
    }
    //End Client

    //Begin Server
    @Test
    void addPacketPlus1Server() {

        int oldSize = ic.getPackets().size();
        ic.addPacket(readCommandServer.getOutput().get(0).get(IcmpV4CommonPacket.class));

        assertEquals(oldSize+1, ic.getPackets().size());
    }

    @Test
    void openedCleanlyServer() {
        for (int i = 0; i < readCommandServer.getOutput().size() ; i++) {
            ic.addPacket(readCommandServer.getOutput().get(i).get(IcmpV4CommonPacket.class));
        }
        assertEquals(IcmpConnection.OPENED_CLEANLY, ic.getOpenedStatus());
    }

    @Test
    void closedCleanlyServer() {
        for (int i = 0; i < readCommandServer.getOutput().size(); i++) {
            ic.addPacket(readCommandServer.getOutput().get(i).get(IcmpV4CommonPacket.class));
        }
        assertEquals(IcmpConnection.CLOSED_CLEANLY, ic.getClosedStatus());
    }

    @Test
    void notClosedServer() {
        for (int i = 0; i < readCommandServer.getOutput().size() - 1; i++) {
            ic.addPacket(readCommandServer.getOutput().get(i).get(IcmpV4CommonPacket.class));
        }
        assertEquals(IcmpConnection.NOT_CLOSED, ic.getClosedStatus());
    }
    //End Server


}