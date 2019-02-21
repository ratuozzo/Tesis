package com.DomainLogicLayer;

import org.pcap4j.packet.Packet;

import java.util.ArrayList;

public class ReadMultiplePcaps extends Command {

    private ArrayList<Packet> _output;
    ArrayList<String> _filePaths;

    public ReadMultiplePcaps(ArrayList<String> filePaths) {
        _filePaths = filePaths;
        _output = new ArrayList<Packet>();
    }

    @Override
    public void execute() {

        for (int i = 0; i < _filePaths.size() ; i++) {
            ReadPcap command = (ReadPcap) CommandFactory.instantiateReadPcap(_filePaths.get(i));
            command.execute();
            for (int j = 0; j < command.getOutput().size() ; j++) {
                _output.add(command.getOutput().get(j));
            }
        }


    }

    public ArrayList<Packet> getPackets() {
        return _output;
    }
}
