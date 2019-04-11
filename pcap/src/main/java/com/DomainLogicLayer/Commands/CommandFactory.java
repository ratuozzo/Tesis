package com.DomainLogicLayer.Commands;

import com.Common.Entity.Connections.Connection;
import org.pcap4j.packet.Packet;

import java.util.ArrayList;

public class CommandFactory {

    public static Object instantiateReadPcap(String filePath) {
        return new ReadPcap(filePath);
    }

    public static Object instantiateReadMultiplePcaps(ArrayList<String> filePaths) {
        return new ReadMultiplePcaps(filePaths);
    }

    public static Object instantiateOrchestrate(ArrayList<Packet> packets) {
        return new Orchestrate(packets);
    }

    public static Object instantiateWriteToCSV(ArrayList<Connection> connections, String name) {
        return new WriteToCSV(connections,name);
    }

    public static Object instantiateTrainNeuralNet() {
        return new TrainNeuralNet();
    }

    public static Object instantiateEvaluateData(org.deeplearning4j.nn.layers.variational.VariationalAutoencoder vae) {
        return new EvaluateData(vae);
    }

}