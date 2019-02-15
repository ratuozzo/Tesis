package com.DomainLogicLayer;

import com.Common.Entity.Connections.Connection;
import com.Common.Entity.Connections.IcmpConnection;
import com.Common.Entity.Connections.TelnetConnection;
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

        try {
            while (iterator.hasNext()) {
                Connection aux = (Connection) iterator.next();
                if (aux.shouldAdd(packet)) {
                    aux.addPacket(packet);
                    return false;
                }
            }
        } catch (NullPointerException ex) {
            return true;
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
