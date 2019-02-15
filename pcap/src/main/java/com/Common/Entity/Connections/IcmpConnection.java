package com.Common.Entity.Connections;

import org.pcap4j.packet.IcmpV4CommonPacket;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;

import java.util.ArrayList;

public class IcmpConnection extends Connection{

    private String _src;
    private String _dst;
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

    public IcmpConnection(Packet packet) {
        _src = packet.get(IpV4Packet.class).getHeader().getSrcAddr().toString();
        _dst = packet.get(IpV4Packet.class).getHeader().getDstAddr().toString();
        _packets = new ArrayList<>();
        _openedStatus = OPENED_CLEANLY;
        _closedStatus = NOT_CLOSED;
    }

    public void addPacket(Packet packet) {
        _packets.add(packet);
        if (packet.get(IcmpV4CommonPacket.class).getHeader().getType().name().equals("Echo")) {
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

    @Override
    public boolean shouldAdd(Packet packet) {

        String pSrc = packet.get(IpV4Packet.class).getHeader().getSrcAddr().toString();
        String pDst = packet.get(IpV4Packet.class).getHeader().getDstAddr().toString();

        if (((pSrc.equals(_src) && pDst.equals(_dst)) || (pSrc.equals(_dst) && pDst.equals(_src)))) {
            return true;
        }
        return false;
    }
}
