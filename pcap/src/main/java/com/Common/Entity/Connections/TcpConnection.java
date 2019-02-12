package com.Common.Entity.Connections;

import com.Common.Entity.ConnectionTree;
import com.Common.Entity.Socket;
import com.scalified.tree.TreeNode;
import org.pcap4j.packet.TcpPacket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class TcpConnection {

    protected Socket _dst;
    protected Socket _src;
    protected ArrayList<TcpPacket> _packets;
    protected String _openedStatus;
    protected String _closedStatus;
    protected ConnectionTree _modelTree;
    protected ConnectionTree _packetTree;

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
        _modelTree = new ConnectionTree();
    }

    public void addPacket(TcpPacket input) {

        _packets.add(input);

        buildTree(packetToInteger(input));

        checkStatus();

    }

    protected void buildTree(int packetInt) {

        if (_packetTree==null && packetInt == _modelTree.data()) {

            _packetTree = new ConnectionTree(ConnectionTree.SYN);


        }else {

            ArrayList<ConnectionTree> collectionAuxModel = _modelTree.getNodesByLevel(_packetTree.height()+1);

            Collection<? extends ConnectionTree> nodesToDelete = null;
            for (int i = 0; i < collectionAuxModel.size(); i++) {
                if (packetInt == collectionAuxModel.get(i).data()) {
                    addToPacketTree(packetInt);
                    nodesToDelete = (Collection<? extends ConnectionTree>) collectionAuxModel.get(i).siblings();
                }

            }

            if(nodesToDelete!=null) {
                Iterator iterator = nodesToDelete.iterator();
                while (iterator.hasNext()) {
                    ConnectionTree aux = (ConnectionTree) iterator.next();
                    _modelTree.remove(aux);
                }
            }
        }
    }

    private void addToPacketTree(int packetInt) {
        for (TreeNode<Integer> node : _packetTree) {
            if (node.isLeaf()) {
                ConnectionTree aux = (ConnectionTree) node;
                aux.add(new ConnectionTree(packetInt));
            }
        }
    }


    protected void checkStatus(){

        if (_packetTree.size() == 1) {
            _openedStatus = OPENING;
        } else if (_packetTree.size() == 2) {
            _openedStatus = OPENING;
        } else if (_packetTree.size() == 3) {
            _openedStatus = OPENED_CLEANLY;
        } else if (_packetTree.size() > 3 && !checkClosedCleanly()) {
            _closedStatus = CLOSING;
        }else {
            _closedStatus = CLOSED_CLEANLY;
        }

    }

    private boolean checkClosedCleanly() {
        return _modelTree.checkDimensions(_packetTree);
    }




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

    public ConnectionTree getModelTree() {
        return _modelTree;
    }
}
