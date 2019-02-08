package com.Common.Entity;


import com.scalified.tree.TreeNode;
import com.scalified.tree.multinode.ArrayMultiTreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionTreeTest {

    ConnectionTree tree;
    @BeforeEach
    void setUp() {
        tree = new ConnectionTree();
    }

    @Test
    void TestConstructor() {
        assertTrue(tree.isRoot());
        assertEquals(3,tree.size());
        assertEquals(2,tree.height());
    }

    @Test
    void TestSize() {

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