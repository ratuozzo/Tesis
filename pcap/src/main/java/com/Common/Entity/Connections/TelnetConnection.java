package com.Common.Entity.Connections;

import com.Common.Entity.ConnectionTree;
import com.Common.Entity.Socket;
import com.scalified.tree.TreeNode;
import org.pcap4j.packet.Packet;

import java.util.ArrayList;

public class TelnetConnection extends TcpConnection {

    public TelnetConnection(Socket src, Socket dst) {
        super(src, dst);
    }

    public TelnetConnection(Packet packet) {
        super(packet);
    }

    protected void instantiateTree() {
        ConnectionTree aux = null;
        for (TreeNode<Integer> node : _modelTree) {
            if (node.isLeaf()) {
                aux = (ConnectionTree) node;
            }

        }

        ConnectionTree baseTree = new ConnectionTree(ConnectionTree.FIN_ACK);


        ConnectionTree il1 = new ConnectionTree(ConnectionTree.ACK);
        il1.addFinAck().addEndingAck();
        il1.addEndingRst();

        ConnectionTree ir1 = new ConnectionTree(ConnectionTree.FIN_ACK);
        ir1.addEndingAck();

        baseTree.add(il1);
        baseTree.add(ir1);

        aux.add(baseTree);
    }

    @Override
    public boolean shouldAdd(Packet packet) {

        try {

            ArrayList<Socket> sockets = new ArrayList<>(Socket.packetToSockets(packet));
            sockets.add(_src);
            sockets.add(_dst);

            return Socket.dualEquals(sockets) && checkSeqAck(packet);

        } catch (NullPointerException ex) {
            //ex.printStackTrace();
            return false;
        }
    }
}
