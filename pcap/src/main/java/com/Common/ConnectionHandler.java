package com.Common;

import com.PackageReader.Registry;
import org.pcap4j.core.*;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import java.util.ArrayList;
import java.util.Collection;

public class ConnectionHandler {

    private static int readPackets = 0;
    private static int ipV4ReadPackets = 0;

    private ArrayList<Connection> _connections = new ArrayList<Connection>();

    public void handlePcap(String filePath) throws PcapNativeException, NotOpenException {

        final PcapHandle handle;

        handle = Pcaps.openOffline(filePath);

        PacketListener listener = new PacketListener() {
            @Override
            public void gotPacket(Packet packet) {
                readPackets++;
                Connection aux = new Connection(packet);

                TcpPacket tcp = packet.get(TcpPacket.class);

                System.out.println("SYN: " + tcp.getHeader().getSyn() + " ACK: "+tcp.getHeader().getAck() );
                System.out.println("SEQNUM: " + tcp.getHeader().getSequenceNumber()+ " ACKNUM: "+tcp.getHeader().getAcknowledgmentNumber() );
                System.out.println("FIN: " + tcp.getHeader().getFin() );

                int index = checkIfExists(aux);
                if( index == -1) {
                    _connections.add(aux);
                } else {
                    _connections.get(index).getPackets().add(packet);
                }
                ipV4ReadPackets++;
            }
        };

        try {
            handle.loop(Registry.PACKAGECOUNT, listener);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        handle.close();
        System.out.println("READ PACKETS: " + readPackets);
        System.out.println("IPV4 READ PACKETS: " + ipV4ReadPackets);
    }

    private int checkIfExists(Connection connection) {
        for (int i = 0; i < _connections.size(); i++) {
            if(_connections.get(i).equals(connection)){
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Connection> getConnections() {
        return _connections;
    }
}
