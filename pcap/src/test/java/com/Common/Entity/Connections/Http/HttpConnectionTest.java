package com.Common.Entity.Connections.Http;

import com.Common.Entity.Connections.HttpConnection;
import com.Common.Entity.Socket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpConnectionTest {

    HttpConnection hc;


    @BeforeEach
    void setUp() {

        Socket src = new Socket("192.168.1.112",5554);
        Socket dst = new Socket("192.168.1.112",5554);
        hc = new HttpConnection(src, dst);

    }

    @Test
    void testTree() {

        assertTrue(hc.getModelTree().isRoot());
        assertEquals(10, hc.getModelTree().size());
        assertEquals(6, hc.getModelTree().height());

    }


}