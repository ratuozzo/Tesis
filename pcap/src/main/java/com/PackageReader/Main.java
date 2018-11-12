package com.PackageReader;

import com.Common.Connection;
import com.Common.ConnectionHandler;
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


    public static void main(String[] args) throws PcapNativeException, NotOpenException {

        JFrame frame = new JFrame("packetList");
        frame.setContentPane(_list._panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        ConnectionHandler connectionHandler = new ConnectionHandler();
        connectionHandler.handlePcap(Registry.PCAPFILEPATH + "http.cap");
        connectionHandler.handlePcap(Registry.PCAPFILEPATH + "ftp.pcap");
        connectionHandler.handlePcap(Registry.PCAPFILEPATH + "telnet.pcap");
        connectionHandler.handlePcap(Registry.PCAPFILEPATH + "dump.pcap");
        connectionHandler.handlePcap(Registry.PCAPFILEPATH + "remix.pcap");


        for (int i = 0; i < connectionHandler.getConnections().size(); i++) {
            _list.addConnection(connectionHandler.getConnections().get(i));
        }
        
        

    }

}
