package com.Common.Entity.Connections.Telnet;

import com.Common.Entity.ConnectionTree;
import com.Common.Entity.Connections.TelnetConnection;
import com.Common.Registry;
import com.DomainLogicLayer.CommandFactory;
import com.DomainLogicLayer.ReadPcap;
import com.scalified.tree.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pcap4j.packet.TcpPacket;

import static org.junit.jupiter.api.Assertions.*;

class TelnetConnectionTest {

    TelnetConnection tc;


    @BeforeEach
    void setUp() {

        tc = new TelnetConnection();

    }

    @Test
    void testTree() {

        assertTrue(tc.getTree().isRoot());
        assertEquals(10,tc.getTree().size());
        assertEquals(6,tc.getTree().height());


    }

}