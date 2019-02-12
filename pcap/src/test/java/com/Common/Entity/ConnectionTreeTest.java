package com.Common.Entity;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionTreeTest {

    ConnectionTree tree;
    ConnectionTree tree2;
    @BeforeEach
    void setUp() {
        tree = new ConnectionTree();
        tree2 = new ConnectionTree();
    }

    @Test
    void TestConstructor() {
        assertTrue(tree.isRoot());
        assertEquals(3,tree.size());
        assertEquals(2,tree.height());
    }

    @Test
    void TestSizeHeight() {

        tree.addSyn();
        tree.addSynAck();
        tree.addAck();
        tree.addFinAck().addSynAck().addSynAck().addSynAck().addSynAck().addSynAck();
        tree.addRst();
        tree.addRstAck();
        assertEquals(14,tree.size());
        assertEquals(6,tree.height());
    }

}