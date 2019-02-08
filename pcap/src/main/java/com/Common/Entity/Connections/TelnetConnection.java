package com.Common.Entity.Connections;

import com.Common.Entity.ConnectionTree;
import com.scalified.tree.TreeNode;
import org.pcap4j.packet.TcpPacket;

import java.util.Iterator;

public class TelnetConnection extends TcpConnection {


    public TelnetConnection(){
        super();

        ConnectionTree aux = null;
        for (TreeNode<Integer> node : _tree) {
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

    @Override
    public void addPacket(TcpPacket input) {

        _packets.add(input);

        packetToInteger(input);

      /*  //Begin Open
        if (_openedStatus.equals(NOT_OPENED) && syn && !ack) {
            _openedStatus = OPENING;
        }

        if (_openedStatus.equals(OPENING) && syn && ack) {
            _openedStatus = OPENING;
        }

        if ( _openedStatus.equals(OPENING) && !syn && ack) {
            _openedStatus = OPENED_CLEANLY;
        }
        //End Open

        //Begin Close
        if (_closedStatus.equals(NOT_CLOSED) && fin && ack) {
            _closedStatus = CLOSING;
        }

        if (_closedStatus.equals(CLOSING) && fin && ack) {
            _closedStatus = CLOSING;
        }

        if (_closedStatus.equals(CLOSING) && !fin && ack) {
            _closedStatus = CLOSED_CLEANLY;
        }
        //End Close
*/

    }
}
