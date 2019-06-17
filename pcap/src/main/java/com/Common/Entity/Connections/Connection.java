package com.Common.Entity.Connections;

import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import java.util.ArrayList;

public abstract class Connection {

    protected ArrayList<Packet> _packets;
    protected String _openedStatus;
    protected String _closedStatus;
    protected boolean _included;


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

    public String getSrcIp() {
        return _packets.get(0).get(IpV4Packet.class).getHeader().getSrcAddr().toString();
    }

    public String getDstIp() {
        return _packets.get(0).get(IpV4Packet.class).getHeader().getDstAddr().toString();
    }

    public int getSrcPort() {
        try {
            return _packets.get(0).get(TcpPacket.class).getHeader().getSrcPort().valueAsInt();
        } catch (Exception e) {
            return 0;
        }
    }

    public int getDstPort() {
        try {
            return _packets.get(0).get(TcpPacket.class).getHeader().getDstPort().valueAsInt();
        } catch (Exception e) {
            return 0;
        }
    }

    public void setIncluded(Boolean input) {
        _included = input;
    }

    public Boolean getIncluded() {
        return _included;
    }
}
