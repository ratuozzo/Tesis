package com.Common.Entity.Connections;

import com.Common.Entity.ConnectionTree;
import com.Common.Entity.Socket;
import com.scalified.tree.TreeNode;
import org.pcap4j.packet.Packet;

public class FtpDataConnection extends TcpConnection {


    public FtpDataConnection(Socket src, Socket dst) {
        super(src, dst);
    }

    public FtpDataConnection(Packet packet) {
        super(packet);
    }

    @Override
    protected void instantiateTree() {
        ConnectionTree aux = null;
        for (TreeNode<Integer> node : _modelTree) {
            if (node.isLeaf()) {
                aux = (ConnectionTree) node;
            }

        }

        ConnectionTree l1 = new ConnectionTree(ConnectionTree.FIN_ACK);
        l1.addFinAck().addEndingAck();

        l1.addAck().addFinAck().addEndingAck();

        aux.add(l1);
    }


}
