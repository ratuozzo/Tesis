package com.DomainLogicLayer;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;

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

            Packet aux;
            while ((aux = handle.getNextPacket()) != null) {
                _output.add(aux);
            }

        } catch (PcapNativeException e) {
            e.printStackTrace();
        } catch (NotOpenException e) {
            e.printStackTrace();
        } finally {
            handle.close();
        }

    }

    public ArrayList<Packet> getOutput() {
        return _output;
    }
}
