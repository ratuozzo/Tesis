package com.Common.Entity.Connections;

import com.Common.Entity.ConnectionTree;
import com.Common.Entity.Socket;
import com.scalified.tree.TreeNode;
import org.pcap4j.packet.Packet;

public class HttpConnection extends TcpConnection {

    public HttpConnection(Socket src, Socket dst) {
        super(src, dst);

        ConnectionTree aux = null;
        for (TreeNode<Integer> node : _modelTree) {
            if (node.isLeaf()) {
                aux = (ConnectionTree) node;
            }

        }

        ConnectionTree l1 = new ConnectionTree(ConnectionTree.ACK);
        l1.addEndingRstAck();
        l1.addFinAck().addEndingAck();

        ConnectionTree l11 = new ConnectionTree(ConnectionTree.FIN_ACK);
        l11.add(l1);
        l11.addFinAck().addEndingAck();

        aux.add(l11);

    }

    @Override
    public boolean shouldAdd(Packet packet) {
        return false;
    }
}
