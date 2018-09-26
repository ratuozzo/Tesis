package com.packet.view;

import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Iterator;
import java.util.List;


public class packetList {
    public JPanel _panelMain;
    private JTable _packetTable;
    private DefaultTableModel _tableModel;
    private IpV4Packet[] _packets = new IpV4Packet[5];


    public packetList() {
        Object[] columnNames = {"Type", "Protocol", "Destination", "Source", "Hex"};
        _tableModel = new DefaultTableModel(columnNames,0);

    }

    public void addPacket(IpV4Packet packet) {
        for (int i = 0; i < _packets.length; i++) {
            if (_packets[i] == null) {
                _packets[i] = packet;
                _tableModel.addRow(packetToArray(_packets[i]));
            }
        }
        _packetTable.setModel(_tableModel);
    }

    public Object[] packetToArray(IpV4Packet packet) {

        String type = packet.getHeader().getVersion().name();
        String dstAddr = packet.getHeader().getDstAddr().toString();
        String srcAddr = packet.getHeader().getSrcAddr().toString();
        String protocol = packet.getHeader().getProtocol().name();
        String hexData = packet.toHexString();
        Object[] rowData = new Object[]{type, protocol, dstAddr, srcAddr, hexData};
        return rowData;
    }

}
