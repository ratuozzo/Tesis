package com.DomainLogicLayer;

import com.Common.Entity.Connections.Connection;
import com.DomainLogicLayer.Filters.FilterByProtocol;
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
        return new Orchestrate(packets);
    }

    public static Object instantiateFilterByProtocol(ArrayList<Connection> connections) {
        return new FilterByProtocol(connections);
    }

    public static Object instantiateWriteToCSV(ArrayList<Connection> connections) {
        return new WriteToCSV(connections);
    }

}