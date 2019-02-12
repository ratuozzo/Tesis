package com.Common.Entity.Connections.Telnet;

import com.Common.Entity.Connections.TelnetConnection;
import com.Common.Entity.Socket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TelnetConnectionTest {

    TelnetConnection tc;


    @BeforeEach
    void setUp() {

        Socket src = new Socket("192.168.1.112",5554);
        Socket dst = new Socket("192.168.1.112",5554);
        tc = new TelnetConnection(src, dst);

    }

    @Test
    void testTree() {

        assertTrue(tc.getModelTree().isRoot());
        assertEquals(10,tc.getModelTree().size());
        assertEquals(6,tc.getModelTree().height());

    }


}