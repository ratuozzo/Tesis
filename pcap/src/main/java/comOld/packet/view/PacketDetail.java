package comOld.packet.view;

import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PacketDetail {

    public void addPackets(ArrayList<Packet> packets) {

        Object[] columnNames = {"Number", "Source Ip", "Source Port", "Destination Ip", "Destination Port"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);

        for (int i = 0; i < packets.size();i++)  {
            tableModel.addRow(packetToArray(packets.get(i),i));
        }
        JTable jtable = new JTable();
        jtable.setModel(tableModel);
        JFrame window = new JFrame("Packet Detail");
        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(jtable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JButton jButton = new JButton();
        jButton.setPreferredSize(new Dimension(200,40));
        jButton.setText("Evaluar Conexión");
        panel.add(scrollPane);
        panel.add(jButton);
        window.add(panel);
        window.setSize(900,500);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }

    public Object[] packetToArray(Packet packet, int index) {
        String srcAddr = packet.get(IpV4Packet.class).getHeader().getSrcAddr().toString().replace('/',' ');
        String dstAddr = packet.get(IpV4Packet.class).getHeader().getDstAddr().toString().replace('/',' ');
        String srcPort = packet.get(TcpPacket.class).getHeader().getSrcPort().valueAsString();
        String dstPort = packet.get(TcpPacket.class).getHeader().getDstPort().valueAsString();

        return new Object[]{index, srcAddr, srcPort, dstAddr, dstPort};
    }

}
