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

                TcpPacket aux = packet.get(TcpPacket.class);

               /* System.out.println("SYN: " + aux.getHeader().getSyn() + " ACK: "+aux.getHeader().getAck() );
                System.out.println("SEQNUM: " + aux.getHeader().getSequenceNumber()+ " ACKNUM: "+aux.getHeader().getAcknowledgmentNumber() );
                System.out.println("FIN: " + aux.getHeader().getFin() );*/

                if(aux.getHeader().getSyn() && !aux.getHeader().getAck()){
                    _connections.add(new Connection(packet.get(IpV4Packet.class)));
                }else{

                    int index = getIndexIfExists(packet);
                    if( index == -1) {
                        _connections.add(new Connection(packet.get(IpV4Packet.class)));
                    } else {
                        if(aux.getHeader().getFin() && !_connections.get(index).getStatus().equals("Closing")){
                            _connections.get(index).addPacket(packet);
                            _connections.get(index).prepareToClose(packet);
                        }else{
                            _connections.get(index).addPacket(packet);
                        }

                    }


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

    private int getIndexIfExists(Packet packet) {

        Connection aux = new Connection(packet.get(IpV4Packet.class));

        for (int i = 0; i < _connections.size(); i++) {
            if(_connections.get(i).equals(aux)){
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Connection> getConnections() {
        return _connections;
    }
}
