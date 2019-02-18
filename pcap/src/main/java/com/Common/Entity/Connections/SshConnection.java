package com.Common.Entity.Connections;

import com.Common.Entity.ConnectionTree;
import com.Common.Entity.Socket;
import com.scalified.tree.TreeNode;
import org.pcap4j.packet.Packet;

import java.util.ArrayList;

public class SshConnection extends TcpConnection {

    public SshConnection(Socket src, Socket dst) {
        super(src, dst);
    }

    public SshConnection(Packet packet) {
        super(packet);
    }

    protected void instantiateTree() {

        ConnectionTree aux = null;
        for (TreeNode<Integer> node : _modelTree) {
            if (node.isLeaf()) {
                aux = (ConnectionTree) node;
            }

        }

        ConnectionTree l1 = new ConnectionTree(ConnectionTree.ACK);
        l1.addFinAck().addEndingAck();
        l1.addEndingRstAck();

        ConnectionTree l11 = new ConnectionTree(ConnectionTree.FIN_ACK);
        l11.add(l1);

        aux.add(l11);
        aux.addEndingRstAck().addEndingRstAck();
    }


    @Override
    public boolean shouldAdd(Packet packet) {

        try {
            ArrayList<Socket> sockets = new ArrayList<>(Socket.packetToSockets(packet));

            if (((_src.equals(sockets.get(0)) && _dst.equals(sockets.get(1))) ||
                    (_src.equals(sockets.get(1)) && _dst.equals(sockets.get(0)))) &&
                    !_closedStatus.equals(CLOSED_CLEANLY)) {
                return true;
            }
        } catch (NullPointerException ex) {
            //ex.printStackTrace();
            return false;
        }
        return false;
    }
}
