package com.packets.App;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.packet.view.*;

import org.pcap4j.core.*;
import org.pcap4j.core.BpfProgram.BpfCompileMode;
import org.pcap4j.packet.*;

import javax.swing.*;

@SuppressWarnings("javadoc")
public class App {

    private static PacketView _list = new PacketView();

    private static final String COUNT_KEY
            = App.class.getName() + ".count";
    private static final int COUNT
            = Integer.getInteger(COUNT_KEY, 2);

    private static final String READ_TIMEOUT_KEY
            = App.class.getName() + ".readTimeout";
    private static final int READ_TIMEOUT
            = Integer.getInteger(READ_TIMEOUT_KEY, 10); // [ms]

    private static final String SNAPLEN_KEY
            = App.class.getName() + ".snaplen";
    private static final int SNAPLEN
            = Integer.getInteger(SNAPLEN_KEY, 65536); // [bytes]

    private App() {}

    public static void main(String[] args) throws PcapNativeException, NotOpenException, IllegalRawDataException {

        JFrame frame = new JFrame("packetList");
        frame.setContentPane(_list._panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        String filter = args.length != 0 ? args[0] : "";

        System.out.println(COUNT_KEY + ": " + COUNT);
        System.out.println(READ_TIMEOUT_KEY + ": " + READ_TIMEOUT);
        System.out.println(SNAPLEN_KEY + ": " + SNAPLEN);
        System.out.println("\n");

        String fileName = "";
        int packetcount = 0;
        int readPackets = 0;

        try {

            System.out.println("Introduzca el nombre del archivo");
            Scanner sc = new Scanner(System.in);
            fileName = sc.next();
            System.out.println("Introduzca cantidad de paquetes a leer");
            packetcount = sc.nextInt();
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec("D:\\Wireshark\\tshark.exe -i 3 -c "+
                    packetcount
                    + " -w D:\\Pcap\\"+
                    fileName
                    + ".pcap -F pcap");

            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            String line=null;

            while((line=input.readLine()) != null) {
                System.out.println(line);
            }

            int exitVal = pr.waitFor();
            System.out.println("Exited with error code "+exitVal);

        } catch(Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }



        /*
            PcapNetworkInterface nif;
            try {
                nif = new NifSelector().selectNetworkInterface();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            if (nif == null) {
                return;
            }

            System.out.println(nif.getName() + "(" + nif.getDescription() + ")");
        */


        final PcapHandle handle;
        //        = nif.openLive(SNAPLEN, PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);

        handle = Pcaps.openOffline("D:\\Pcap\\"+ fileName +".pcap");

        if (filter.length() != 0) {
            handle.setFilter(
                    filter,
                    BpfCompileMode.OPTIMIZE
            );
        }

        PacketListener listener
                = new PacketListener() {
            @Override
            public void gotPacket(Packet packet) {
                //EthernetPacket ethPacket = null;

                /*try {
                    ethPacket = EthernetPacket.newPacket(packet.getRawData(), 0, packet.getRawData().length);
                } catch (IllegalRawDataException e) {
                    e.printStackTrace();
                }*/

                IpV4Packet IpV4Packet = packet.get(IpV4Packet.class);
                //String protocol = IpV4Packet.getHeader().getProtocol().name();
                //Packet udp = ethPacket.getOuterOf(UdpPacket.class);
                //Packet tcp = ethPacket.getOuterOf(TcpPacket.class);
                //packet = PropertiesBasedPacketFactory.getInstance().newInstance(packet.getRawData(),0,packet.getRawData().length);
                System.out.println(handle.getTimestamp());
                System.out.println("Ethernet Packet ------------------------");
                System.out.println(IpV4Packet);

                _list.addPacket(IpV4Packet);
                //System.out.println("Packet ----------------------");
                //System.out.println(packet);
            }
        };
        try {
            handle.loop(packetcount, listener);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        /*PcapStat ps = handle.getStats();
        System.out.println("ps_recv: " + ps.getNumPacketsReceived());
        System.out.println("ps_drop: " + ps.getNumPacketsDropped());
        System.out.println("ps_ifdrop: " + ps.getNumPacketsDroppedByIf());
        if (Platform.isWindows()) {
            System.out.println("bs_capt: " + ps.getNumPacketsCaptured());
        }*/

        handle.close();
    }

}