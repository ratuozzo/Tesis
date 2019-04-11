package com.DomainLogicLayer.Commands;

import com.Common.Entity.Connections.*;
import org.pcap4j.packet.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Orchestrate extends Command {

    private ArrayList<Connection>[] _connections;
    private ArrayList<Packet> _packets;
    private ArrayList<Packet> _dumpedPackets;

    public Orchestrate(ArrayList<Packet> packets) {
        _packets = packets;
        _dumpedPackets = new ArrayList<>();
        _connections = new ArrayList [1000];
        for (int i = 0; i < 1000 ; i++) {
            _connections[i] = new ArrayList<>();
        }
    }

    @Override
    public void execute() {

        Iterator iterator = _packets.iterator();
        while ( iterator.hasNext() ) {
            Packet packet = (Packet) iterator.next();

            if (distributePacket(packet)) {
                instantiateConnection(packet);
            }
        }
    }

    private boolean distributePacket(Packet packet) {

        for (int i = 0; i < _connections[packetToHash(packet)].size(); i++) {

            Connection aux = _connections[packetToHash(packet)].get(i);

            if (aux.shouldAdd(packet)) {
                aux.addPacket(packet);
                return false;
            }
        }

        return true;
    }

    private void instantiateConnection(Packet packet) {

        switch (getPacketProtocol(packet)) {
            case "Telnet":
                TelnetConnection tc = new TelnetConnection(packet);
                tc.addPacket(packet);
                _connections[packetToHash(packet)].add(tc);
                break;

            case "Icmp":
                IcmpConnection ic = new IcmpConnection(packet);
                ic.addPacket(packet);
                _connections[packetToHash(packet)].add(ic);
                break;

            case "SSH":
                SshConnection sc = new SshConnection(packet);
                sc.addPacket(packet);
                _connections[packetToHash(packet)].add(sc);
                break;

            case "File Transfer [Control]":
                FtpCommandConnection fc = new FtpCommandConnection(packet);
                fc.addPacket(packet);
                _connections[packetToHash(packet)].add(fc);
                break;

            case "File Transfer [Default Data]":
                FtpDataConnection trc = new FtpDataConnection(packet);
                trc.addPacket(packet);
                _connections[packetToHash(packet)].add(trc);
                break;

            case "HTTP":
                HttpConnection hc = new HttpConnection(packet);
                hc.addPacket(packet);
                _connections[packetToHash(packet)].add(hc);
                break;

            default:

                if (packet.get(TcpPacket.class).getHeader().getSyn() && !packet.get(TcpPacket.class).getHeader().getAck()) {
                    OtherConnection oc = new OtherConnection(packet);
                    oc.addPacket(packet);
                    _connections[packetToHash(packet)].add(oc);
                } else {
                    _dumpedPackets.add(packet);
                }
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

    private int packetToHash(Packet packet) {

        int hash=0;

        IpV4Packet ipv4 = packet.get(IpV4Packet.class);

        hash += ipv4.getHeader().getSrcAddr().hashCode();
        hash += ipv4.getHeader().getDstAddr().hashCode();

        try {
            TcpPacket tcpP = packet.get(TcpPacket.class);
            hash += tcpP.getHeader().getSrcPort().valueAsInt();
            hash += tcpP.getHeader().getDstPort().valueAsInt();
        } catch (NullPointerException e) {

        }

        return Math.abs(hash)%1000;
    }

    public ArrayList<Connection> getClosedConnections(){

        ArrayList<Connection> output = new ArrayList<>();

        for (int i = 0; i < 1000 ; i++) {
            for (int j = 0; j < _connections[i].size(); j++) {
                if (_connections[i].get(j).getClosedStatus().equals(TcpConnection.CLOSED_CLEANLY)) {
                    output.add(_connections[i].get(j));
                }
            }
        }

        return output;
    }
}
