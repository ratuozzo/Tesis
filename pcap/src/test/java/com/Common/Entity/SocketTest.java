package com.Common.Entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SocketTest {

    static Socket s1;
    static Socket s2;

    @BeforeAll
    static void setUp() {
        s1 = new Socket("192.168.1.115",23);
        s2 = new Socket("192.168.1.115",23);
    }

    @Test
    void equals() {
        assertTrue(s1.equals(s2));
    }
}