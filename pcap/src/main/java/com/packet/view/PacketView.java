package com.packet.view;

import com.Common.Connection;
import org.pcap4j.packet.IpV4Packet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class PacketView {
    public JPanel _panelMain;
    private JTable _packetTable;
    private DefaultTableModel _tableModel;
    private Connection[] _connections = new Connection[1000000];


    public PacketView() {
        Object[] columnNames = {"Number", "Source Ip", "Source Port", "Destination Ip", "Destination Port", "Protocol", "Packet Count", "Opened", "Closed", "Status"};
        _tableModel = new DefaultTableModel(columnNames,0);

    }

    public void addConnection(Connection connection) {
        for (int i = 0; i < _connections.length; i++) {
            if (_connections[i] == null) {
                _connections[i] = connection;
                _tableModel.addRow(connectionToArray(_connections[i], i));
                break;
            }
        }
        _packetTable.setModel(_tableModel);
    }

    public Object[] connectionToArray(Connection connection, int number) {

        String srcAddr = connection.getSource().getIp();
        int srcPort = connection.getSource().getPort();
        String dstAddr = connection.getDestination().getIp();
        int dstPort = connection.getDestination().getPort();
        String protocol = connection.getProtocol();
        int packetCount = connection.getPackets().size();
        String opened = connection.getOpened();
        String closed = connection.getClosed();
        String status = connection.getStatus();

        Object[] rowData = new Object[]{number, srcAddr, srcPort, dstAddr, dstPort, protocol, packetCount,opened,closed,status};
        return rowData;
    }

}
