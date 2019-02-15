package com.DomainLogicLayer;

import com.Common.Entity.Connections.TcpConnection;
import org.pcap4j.packet.Packet;

import java.util.ArrayList;

public class CommandFactory {

    public static Object instantiateReadPcap(String filePath) {
        return new ReadPcap(filePath);
    }

    public static Object instantiateReadMultiplePcaps(ArrayList<String> filePaths) {
        return new ReadMultiplePcaps(filePaths);
    }

    public static Object instantiateOrchestrateCommand(ArrayList<Packet> packets) {
        return new OrchestrateCommand(packets);
    }
}