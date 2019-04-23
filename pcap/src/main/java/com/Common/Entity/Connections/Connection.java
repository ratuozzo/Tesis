package com.Common.Entity.Connections;

import org.pcap4j.packet.Packet;

import java.util.ArrayList;

public abstract class Connection {

    protected ArrayList<Packet> _packets;
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


    public ArrayList<Packet> getPackets() {
        return _packets;
    }

    public String getOpenedStatus() {
        return _openedStatus;
    }

    public String getClosedStatus() {
        return _closedStatus;
    }

    public abstract boolean shouldAdd(Packet packet);

    public abstract void addPacket(Packet packet);

    public abstract ArrayList<Packet> getUniquePackets();
}
