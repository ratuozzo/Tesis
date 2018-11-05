package com.PackageReader;

import com.packet.view.PacketView;
import org.pcap4j.core.*;
import org.pcap4j.packet.IllegalRawDataException;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import javax.swing.*;
import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    private static PacketView _list = new PacketView();
    private static ArrayList<Session> foundSessions = new ArrayList<Session>();
    private static ArrayList<Session> rawData = new ArrayList<Session>();
    private static int readPackets = 0;


    public static void main(String[] args) throws PcapNativeException, NotOpenException, IllegalRawDataException {

        JFrame frame = new JFrame("packetList");
        frame.setContentPane(_list._panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        final PcapHandle handle;

        handle = Pcaps.openOffline(Registry.PCAPFILEPATH);

        PacketListener listener = new PacketListener() {
            @Override
            public void gotPacket(Packet packet) {

                IpV4Packet ipV4Packet = packet.get(IpV4Packet.class);
                //System.out.println(handle.getTimestamp());
                //System.out.println("IPV4 Packet -----------------------------------------------------------------");
                //System.out.println(ipV4Packet);
                Session session = new Session(
                        ipV4Packet.getHeader().getSrcAddr().toString(),
                        packet.get(TcpPacket.class).getHeader().getSrcPort().valueAsInt(),
                        ipV4Packet.getHeader().getDstAddr().toString(),
                        packet.get(TcpPacket.class).getHeader().getDstPort().valueAsInt(),
                        packet.get(TcpPacket.class).getHeader().getDstPort().valueAsString()
                        );
                rawData.add(session);
                readPackets++;
                _list.addPacket(ipV4Packet);
            }
        };

        try {
            handle.loop(Registry.PACKAGECOUNT, listener);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        handle.close();

        foundSessions.add(rawData.get(0));

        ArrayList<Integer> dstPorts = new ArrayList<Integer>();

        for (int i = 0; i < rawData.size(); i++) {
            dstPorts.add(rawData.get(i).getDestinationPort());
        }

        for (int i = 1; i < rawData.size(); i++){
            boolean exists = false;

            for (int j = 0; j < foundSessions.size(); j++) {
                if(
                        (foundSessions.get(j).getDestinationIp().equals(rawData.get(i).getDestinationIp()) &&
                        foundSessions.get(j).getSourceIp().equals(rawData.get(i).getSourceIp()) &&
                        foundSessions.get(j).getSourcePort() == rawData.get(i).getSourcePort() &&
                        foundSessions.get(j).getDestinationPort() == rawData.get(i).getDestinationPort()) ||

                        (foundSessions.get(j).getSourceIp().equals(rawData.get(i).getDestinationIp()) &&
                        foundSessions.get(j).getDestinationIp().equals(rawData.get(i).getSourceIp()) &&
                        foundSessions.get(j).getDestinationPort() == rawData.get(i).getSourcePort() &&
                        foundSessions.get(j).getSourcePort() == rawData.get(i).getDestinationPort()) ) {
                    exists = true;
                    break;
                }
            }
            if(!exists) {
                foundSessions.add(rawData.get(i));
            }

            int ocurrences = Collections.frequency(dstPorts, rawData.get(i).getDestinationPort());
            System.out.println("PORT: " + rawData.get(i).getDestinationPort() + ", OCURRENCES: " + ocurrences);

        }

        System.out.println("COMUNICACIONES " + foundSessions.size());

    }

}
