package com.Common.Entity.Connections;

import com.Common.Entity.ConnectionTree;
import com.Common.Entity.Socket;
import com.scalified.tree.TreeNode;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class TcpConnection extends Connection{

    protected Socket _src;
    protected Socket _dst;
    protected ConnectionTree _modelTree;
    protected ConnectionTree _packetTree;

    public TcpConnection(Socket src, Socket dst) {
        _packets = new ArrayList<>();
        _openedStatus = NOT_OPENED;
        _closedStatus = NOT_CLOSED;
        _modelTree = new ConnectionTree();
        _src = src;
        _dst = dst;
        instantiateTree();
    }

    public TcpConnection(Packet packet) {

        ArrayList<Socket> sockets = Socket.packetToSockets(packet);

        _packets = new ArrayList<>();
        _openedStatus = NOT_OPENED;
        _closedStatus = NOT_CLOSED;
        _modelTree = new ConnectionTree();
        _src = sockets.get(0);
        _dst = sockets.get(1);
        instantiateTree();
    }

    protected abstract void instantiateTree();

    public void addPacket(Packet inputPacket) {


        TcpPacket input = inputPacket.get(TcpPacket.class);
        _packets.add(input);

        if (((_dst.getPort() == input.getHeader().getSrcPort().valueAsInt()) &&
            (_src.getPort() == input.getHeader().getDstPort().valueAsInt())) ||
            ((_src.getPort() == input.getHeader().getSrcPort().valueAsInt()) &&
            (_dst.getPort() == input.getHeader().getDstPort().valueAsInt()))) {
            buildTree(packetToInteger(input));
        }

        checkStatus();

    }

    protected void buildTree(int packetInt) {

        if (_packetTree == null) {

            _packetTree = new ConnectionTree(packetInt);


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
        return _modelTree.checkEndingNode(_packetTree);
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

    @Override
    public boolean shouldAdd(Packet packet) {

        try {

            ArrayList<Socket> sockets = new ArrayList<>(Socket.packetToSockets(packet));
            sockets.add(_src);
            sockets.add(_dst);

            return Socket.dualEquals(sockets);

        } catch (NullPointerException ex) {
            //ex.printStackTrace();
            return false;
        }
    }

    public ConnectionTree getModelTree() {
        return _modelTree;
    }

}
