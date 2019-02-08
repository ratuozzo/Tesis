package com.Common.Entity.Connections;

import org.pcap4j.packet.TcpPacket;

@SuppressWarnings("Duplicates")
public class TelnetConnection extends TcpConnection {


    @Override
    public void addPacket(TcpPacket input) {

        _packets.add(input);

        boolean syn = input.getHeader().getSyn();
        boolean fin = input.getHeader().getFin();
        boolean ack = input.getHeader().getAck();
        boolean rst = input.getHeader().getRst();

        //Begin Open
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


    }
}
