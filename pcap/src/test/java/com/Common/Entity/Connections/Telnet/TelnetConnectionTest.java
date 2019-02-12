package com.Common.Entity.Connections.Telnet;

import com.Common.Entity.Connections.TelnetConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TelnetConnectionTest {

    TelnetConnection tc;


    @BeforeEach
    void setUp() {

        tc = new TelnetConnection();

    }

    @Test
    void testTree() {

        assertTrue(tc.getModelTree().isRoot());
        assertEquals(10,tc.getModelTree().size());
        assertEquals(6,tc.getModelTree().height());

    }


}