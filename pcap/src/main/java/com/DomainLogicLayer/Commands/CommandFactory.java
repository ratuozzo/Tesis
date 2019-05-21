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

    public static Object instantiateTrainAndEvaluateData(String filePathTrain, String filePathEvaluate,
                                                         Double learningRate, int batchSize, int epochs) {
        return new TrainAndEvaluateData(filePathTrain,filePathEvaluate,learningRate,batchSize,epochs);
    }
    public static Object instantiateCreateMasonryView(ArrayList<Connection> connections) {
        return new CreateMasonryView(connections);
    }

}