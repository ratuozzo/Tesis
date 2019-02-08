package com.Common.Entity.Connections;

import com.Common.Entity.ConnectionTree;
import com.Common.Entity.Socket;
import com.scalified.tree.TreeNode;
import com.scalified.tree.multinode.ArrayMultiTreeNode;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import java.util.ArrayList;

public abstract class TcpConnection {

    protected Socket _dst;
    protected Socket _src;
    protected ArrayList<TcpPacket> _packets;
    protected String _openedStatus;
    protected String _closedStatus;
    protected ConnectionTree _tree;



    public static final String NOT_OPENED = "Not Opened";
    public static final String OPENING = "Opening";
    public static final String OPENED_CLEANLY = "Opened Cleanly";
    public static final String OPENED_DIRTILY = "Opened Dirtily";

    public static final String NOT_CLOSED = "Not Closed";
    public static final String CLOSING = "Closing";
    public static final String CLOSED_CLEANLY = "Closed Cleanly";
    public static final String CLOSED_DIRTILY = "Closed Dirtily";



    public TcpConnection() {
        _packets = new ArrayList<TcpPacket>();
        _openedStatus = NOT_OPENED;
        _closedStatus = NOT_CLOSED;
        _tree = new ConnectionTree();
    }


    public abstract void addPacket(TcpPacket input);

    public int packetToInteger(TcpPacket input) {

        int output = 0;

        boolean syn = input.getHeader().getSyn();
        boolean ack = input.getHeader().getAck();
        boolean fin = input.getHeader().getFin();
        boolean rst = input.getHeader().getRst();

        if (syn) {
            output += 8;
        }

        if (ack) {
            output += 4;
        }

        if (fin) {
            output += 2;
        }

        if (rst) {
            output += 1;
        }

        return output;
    }


    public ArrayList<TcpPacket> getPackets() {
        return _packets;
    }

    public String getOpenedStatus() {
        return _openedStatus;
    }

    public String getClosedStatus() {
        return _closedStatus;
    }

    public ConnectionTree getTree() {
        return _tree;
    }
}
