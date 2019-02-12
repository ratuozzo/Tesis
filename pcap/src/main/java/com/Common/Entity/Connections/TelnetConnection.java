package com.Common.Entity.Connections;

import com.Common.Entity.ConnectionTree;
import com.Common.Entity.Socket;
import com.scalified.tree.TreeNode;

public class TelnetConnection extends TcpConnection {

    public TelnetConnection(Socket src, Socket dst) {
        super(src, dst);

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
}
