package com.DomainLogicLayer;

import com.Common.Entity.Connections.*;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import java.util.ArrayList;
import java.util.Iterator;

public class OrchestrateCommand extends Command {


    private ArrayList<Connection> _connections;
    private ArrayList<Packet> _packets;

    public OrchestrateCommand(ArrayList<Packet> packets) {
        _packets = packets;
        _connections = new ArrayList<>();
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

    public ArrayList<Connection> getConnections() {
        return _connections;
    }

    private boolean distributePacket(Packet packet) {

        Iterator iterator = _connections.iterator();


            while (iterator.hasNext()) {
                Connection aux = (Connection) iterator.next();
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
                _connections.add(tc);
                break;

            case "Icmp":
                IcmpConnection ic = new IcmpConnection(packet);
                ic.addPacket(packet);
                _connections.add(ic);
                break;

            case "SSH":
                SshConnection sc = new SshConnection(packet);
                sc.addPacket(packet);
                _connections.add(sc);
                break;

            case "File Transfer [Control]":
                FtpCommandConnection fc = new FtpCommandConnection(packet);
                fc.addPacket(packet);
                _connections.add(fc);
                break;

            case "File Transfer [Default Data]":
                FtpDataConnection trc = new FtpDataConnection(packet);
                trc.addPacket(packet);
                _connections.add(trc);
                break;

            case "HTTP":
                HttpConnection hc = new HttpConnection(packet);
                hc.addPacket(packet);
                _connections.add(hc);
                break;

            default:
                OtherConnection oc = new OtherConnection(packet);
                oc.addPacket(packet);
                _connections.add(oc);
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
}
