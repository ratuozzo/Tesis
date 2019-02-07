package comOld.packet.view;

import comOld.Common.Connection;
import org.pcap4j.packet.Packet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class PacketView {
    public JPanel _panelMain;
    private JTable _packetTable;
    private DefaultTableModel _tableModel;
    private Connection[] _connections = new Connection[1000000];
    private PacketDetail _packetDetail = new PacketDetail();
    private ArrayList<Packet> _packets = new ArrayList<Packet>();


    public PacketView() {
        Object[] columnNames = {"Number", "Source Ip", "Source Port", "Destination Ip", "Destination Port", "Protocol", "Packet Count", "Opened", "Closed", "Status"};
        _tableModel = new DefaultTableModel(columnNames,0);

        _packetTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int index = +_packetTable.getSelectedRow();
                System.out.println(_connections[index]);
                for (int i = 0;i < _connections[index].getPackets().size(); i++) {
                    System.out.println(_connections[index].getPackets().get(i));
                    _packets = (_connections[index].getPackets());
                }
                _packetDetail.addPackets(_packets);
            }
        });
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
