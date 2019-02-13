package com.DomainLogicLayer;

import com.Common.Entity.Connections.IcmpConnection;
import com.Common.Entity.Connections.TcpConnection;
import com.Common.Entity.Connections.TelnetConnection;
import com.Common.Entity.Socket;
import org.pcap4j.packet.IcmpV4CommonPacket;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import java.util.ArrayList;
import java.util.Iterator;

public class OrchestrateCommand extends Command {


    private ArrayList<TcpConnection> _tcpConnections;
    private ArrayList<IcmpConnection> _icmpConnections;
    private ArrayList<Packet> _packets;

    public OrchestrateCommand(ArrayList<Packet> packets) {
        _packets = packets;
        _tcpConnections = new ArrayList<>();
        _icmpConnections = new ArrayList<>();
    }

    @Override
    public void execute() {

        Iterator iterator = _packets.iterator();
        while ( iterator.hasNext() ) {
            Packet packet = (Packet) iterator.next();
            distributePacket(packet);
        }


    }

    public ArrayList<TcpConnection> getTcpConnections() {
        return _tcpConnections;
    }

    public ArrayList<IcmpConnection> getIcmpConnections() {
        return _icmpConnections;
    }

    private void distributePacket(Packet packet) {

        TcpPacket tcpP = packet.get(TcpPacket.class);
        String pName = getPacketProtocol(packet);
        switch (pName) {
            case "Telnet":
                telnetHandler(packet);
                break;
        }
    }

    private String getPacketProtocol(Packet packet) {

        try {
            TcpPacket tcpP = packet.get(TcpPacket.class);
            if (tcpP.getHeader().getDstPort().valueAsInt() < tcpP.getHeader().getSrcPort().valueAsInt()) {
                return tcpP.getHeader().getDstPort().name();
            } else {
                return tcpP.getHeader().getSrcPort().name();
            }
        } catch (NullPointerException e) {
            return "Icmp";
        }

    }

    private ArrayList<TcpConnection> findTcpConnections(Packet packet) {

        ArrayList<TcpConnection> output = new ArrayList<>();

        Iterator iterator = _tcpConnections.iterator();
        while ( iterator.hasNext() ) {
            TcpConnection tcpConn = (TcpConnection) iterator.next();
            if (getPacketProtocol(tcpConn.getPackets().get(0)) == getPacketProtocol(packet)) {
                output.add(tcpConn);
            }
        }
        return output;
    }

    private void telnetHandler(Packet packet) {

        TcpPacket tcpP = packet.get(TcpPacket.class);

        if (tcpP.getHeader().getSyn() && !tcpP.getHeader().getAck()) {
            ArrayList<Socket> sockets = new ArrayList<>(Socket.packetToSockets(packet));
            TelnetConnection tConnection = new TelnetConnection(sockets.get(0), sockets.get(1));
            tConnection.addPacket(tcpP);
            _tcpConnections.add(tConnection);
        }else {
            ArrayList<TcpConnection> telnetConnections = findTcpConnections(packet);
            Iterator iterator = telnetConnections.iterator();
            while ( iterator.hasNext() ) {
                TcpConnection tcpConn = (TcpConnection) iterator.next();
                if (tcpConn.belongsTo(packet) && !tcpConn.getClosedStatus().equals(TcpConnection.CLOSED_CLEANLY)) {
                    tcpConn.addPacket(tcpP);
                }
            }


        }

    }
}
