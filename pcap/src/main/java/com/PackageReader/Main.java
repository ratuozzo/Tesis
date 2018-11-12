package com.PackageReader;

import com.Common.Connection;
import com.packet.view.PacketView;
import org.pcap4j.core.*;
import org.pcap4j.packet.IllegalRawDataException;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    private static PacketView _list = new PacketView();
    private static ArrayList<Connection> foundConnections = new ArrayList<Connection>();
    private static ArrayList<Connection> rawData= new ArrayList<Connection>();
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
                Connection aux= new Connection(packet);
                rawData.add(aux);
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

        foundConnections.add(rawData.get(0));

        ArrayList<Integer> dstPorts = new ArrayList<Integer>();

        for (int i = 0; i < rawData.size(); i++) {
            dstPorts.add(rawData.get(i).getDestination().getPort());
        }

        for (int i = 1; i < rawData.size(); i++){
            boolean exists = false;

            for (int j = 0; j < foundConnections.size(); j++) {
                if(
                        (foundConnections.get(j).getDestination().getIp().equals(rawData.get(i).getDestination().getIp()) &&
                        foundConnections.get(j).getSource().getIp().equals(rawData.get(i).getSource().getIp()) &&
                        foundConnections.get(j).getSource().getPort() == rawData.get(i).getSource().getPort() &&
                        foundConnections.get(j).getDestination().getPort() == rawData.get(i).getDestination().getPort()) ||

                        (foundConnections.get(j).getSource().getIp().equals(rawData.get(i).getDestination().getIp()) &&
                        foundConnections.get(j).getDestination().getIp().equals(rawData.get(i).getSource().getIp()) &&
                        foundConnections.get(j).getDestination().getPort() == rawData.get(i).getSource().getPort() &&
                        foundConnections.get(j).getSource().getPort() == rawData.get(i).getDestination().getPort()) ) {
                    exists = true;
                    break;
                }
            }
            if(!exists) {
                foundConnections.add(rawData.get(i));
            }

            int ocurrences = Collections.frequency(dstPorts, rawData.get(i).getDestination().getPort());
            System.out.println("PORT: " + rawData.get(i).getDestination().getPort() + ", OCURRENCES: " + ocurrences);

        }

        System.out.println("COMUNICACIONES " + foundConnections.size());

    }

}
