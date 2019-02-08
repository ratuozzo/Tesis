package com.Common.Entity;

import com.scalified.tree.multinode.ArrayMultiTreeNode;

public class ConnectionTree extends ArrayMultiTreeNode<Integer> {


    public static final int SYN = 8;
    public static final int SIN_ACK = 12;
    public static final int ACK = 4;
    public static final int FIN_ACK = 6;
    public static final int RST = 1;
    public static final int RST_ACK = 5;


    public ConnectionTree() {
        super(SYN);
        addSynAck().addAck();


    }

    public ConnectionTree(int i) {
        super(i);
    }


    public ConnectionTree addSyn() {
        ConnectionTree output = new ConnectionTree(SYN);
        add(output);
        return output;
    }

    public ConnectionTree addSynAck() {
        ConnectionTree output = new ConnectionTree(SIN_ACK);
        add(output);
        return output;
    }

    public ConnectionTree addAck() {
        ConnectionTree output = new ConnectionTree(ACK);
        add(output);
        return output;
    }

    public ConnectionTree addFinAck() {
        ConnectionTree output = new ConnectionTree(FIN_ACK);
        add(output);
        return output;
    }

    public ConnectionTree addRst() {
        ConnectionTree output = new ConnectionTree(RST);
        add(output);
        return output;
    }

    public ConnectionTree addRstAck() {
        ConnectionTree output = new ConnectionTree(RST_ACK);
        add(output);
        return output;
    }



}
