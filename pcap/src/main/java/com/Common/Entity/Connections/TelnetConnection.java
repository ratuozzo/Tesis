package com.Common.Entity.Connections;

import com.Common.Entity.ConnectionTree;
import com.scalified.tree.TreeNode;

public class TelnetConnection extends TcpConnection {

    public TelnetConnection() {
        super();

        ConnectionTree aux = null;
        for (TreeNode<Integer> node : _modelTree) {
            if (node.isLeaf()) {
                aux = (ConnectionTree) node;
            }

        }

        ConnectionTree telnetTree = new ConnectionTree(ConnectionTree.FIN_ACK);


        ConnectionTree il1 = new ConnectionTree(ConnectionTree.ACK);
        il1.addFinAck().addAck();
        il1.addRst();

        ConnectionTree ir1 = new ConnectionTree(ConnectionTree.FIN_ACK);
        ir1.addAck();

        telnetTree.add(il1);
        telnetTree.add(ir1);

        aux.add(telnetTree);

    }




}
