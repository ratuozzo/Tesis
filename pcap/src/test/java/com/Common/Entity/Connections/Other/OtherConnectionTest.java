package com.Common.Entity.Connections.Other;

import com.Common.Entity.Connections.FtpDataConnection;
import com.Common.Entity.Connections.OtherConnection;
import com.Common.Entity.Socket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OtherConnectionTest {

    OtherConnection fc;


    @BeforeEach
    void setUp() {

        Socket src = new Socket("192.168.1.112",5554);
        Socket dst = new Socket("192.168.1.112",5554);
        fc = new OtherConnection(src, dst);

    }

    @Test
    void testTree() {

        assertTrue(fc.getModelTree().isRoot());
        assertEquals(9, fc.getModelTree().size());
        assertEquals(6, fc.getModelTree().height());

    }


}