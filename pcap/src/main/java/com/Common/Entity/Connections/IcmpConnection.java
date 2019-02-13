package com.Common.Entity.Connections;

import org.pcap4j.packet.IcmpV4CommonPacket;

import java.util.ArrayList;
import java.util.BitSet;

public class IcmpConnection {

    private String _src;
    private String _dst;
    private ArrayList<IcmpV4CommonPacket> _packets;
    protected String _openedStatus;
    protected String _closedStatus;


    public static final String OPENED_CLEANLY = "Opened Cleanly";

    public static final String NOT_CLOSED = "Not Closed";
    public static final String CLOSED_CLEANLY = "Closed Cleanly";

    public IcmpConnection(String src, String dst) {
        _src = src;
        _dst = dst;
        _packets = new ArrayList<>();
        _openedStatus = OPENED_CLEANLY;
        _closedStatus = NOT_CLOSED;
    }


    public ArrayList<IcmpV4CommonPacket> getPackets() {
        return _packets;
    }

    public void addPacket(IcmpV4CommonPacket packet) {
        _packets.add(packet);
        if (packet.getHeader().getType().name().equals("Echo")) {
            _closedStatus = NOT_CLOSED;
        } else {
            _closedStatus = CLOSED_CLEANLY;
        }
    }

    public String getOpenedStatus() {
        return _openedStatus;
    }

    public String getClosedStatus() {
        return _closedStatus;
    }
}
