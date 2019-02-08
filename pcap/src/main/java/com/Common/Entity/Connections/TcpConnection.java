package com.Common.Entity.Connections;

import com.Common.Entity.Socket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import java.util.ArrayList;

public abstract class TcpConnection {

    protected Socket _dst;
    protected Socket _src;
    protected ArrayList<TcpPacket> _packets;
    protected String _openedStatus;
    protected String _closedStatus;



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
    }


    public abstract void addPacket(TcpPacket input);


    public ArrayList<TcpPacket> getPackets() {
        return _packets;
    }

    public String getOpenedStatus() {
        return _openedStatus;
    }

    public String getClosedStatus() {
        return _closedStatus;
    }
}
