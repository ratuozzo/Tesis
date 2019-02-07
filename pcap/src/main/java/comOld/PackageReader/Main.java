package comOld.PackageReader;

import comOld.Common.ConnectionHandler;
import comOld.packet.view.PacketView;
import org.pcap4j.core.*;

import javax.swing.*;

public class Main {

    private static PacketView _list = new PacketView();


    public static void main(String[] args) throws PcapNativeException, NotOpenException {

        JFrame frame = new JFrame("packetList");
        frame.setContentPane(_list._panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        ConnectionHandler connectionHandler = new ConnectionHandler();

        //connectionHandler.handlePcap(Registry.PCAPFILEPATH + "clean.cap");
        //connectionHandler.handlePcap(Registry.PCAPFILEPATH + "http.cap");
        //connectionHandler.handlePcap(Registry.PCAPFILEPATH + "ftp.pcap");
        connectionHandler.handlePcap(Registry.PCAPFILEPATH + "telnet-slcw-c.pcap");
        //connectionHandler.handlePcap(Registry.PCAPFILEPATH + "bigFlows.pcap");


        for (int i = 0; i < connectionHandler.getConnections().size(); i++) {
            _list.addConnection(connectionHandler.getConnections().get(i));
        }



    }

}
