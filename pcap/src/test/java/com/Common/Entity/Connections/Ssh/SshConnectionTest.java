package com.Common.Entity.Connections.Ssh;

import com.Common.Entity.Connections.SshConnection;
import com.Common.Entity.Connections.TelnetConnection;
import com.Common.Entity.Socket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SshConnectionTest {

    SshConnection sc;


    @BeforeEach
    void setUp() {

        Socket src = new Socket("192.168.1.112",5554);
        Socket dst = new Socket("192.168.1.112",5554);
        sc = new SshConnection(src, dst);

    }

    @Test
    void testTree() {

        assertTrue(sc.getModelTree().isRoot());
        assertEquals(10,sc.getModelTree().size());
        assertEquals(6,sc.getModelTree().height());

    }


}