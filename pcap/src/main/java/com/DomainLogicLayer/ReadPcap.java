package com.DomainLogicLayer;

import org.pcap4j.core.*;
import org.pcap4j.packet.IcmpV4CommonPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import java.util.ArrayList;

public class ReadPcap extends Command{

    private String _filePath;
    private ArrayList<Packet> _output;

    public ReadPcap(String filePath) {
        _filePath = filePath;
        _output = new ArrayList<Packet>();
    }
    @Override
    public void execute() {

        PcapHandle handle = null;

        try {
            handle = Pcaps.openOffline(_filePath);
        } catch (PcapNativeException e) {
            e.printStackTrace();
        }

        while (true) {
            try {

                Packet aux = handle.getNextPacket();
                if (aux == null) {
                    break;
                }
                if (aux.get(TcpPacket.class) != null || aux.get(IcmpV4CommonPacket.class) != null) {
                    _output.add(aux);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        handle.close();

    }

    public ArrayList<Packet> getOutput() {
        return _output;
    }
}
