package com.DomainLogicLayer;

import com.Common.Entity.Connections.Connection;
import com.Common.Entity.Connections.IcmpConnection;
import com.Common.Entity.Connections.TcpConnection;
import com.Common.Registry;
import com.opencsv.CSVWriter;
import org.pcap4j.packet.IcmpV4CommonPacket;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WriteToCSV extends Command {

    private ArrayList<Connection> _connections;

    public WriteToCSV(ArrayList<Connection> connections) {
        _connections = connections;
    }

    @Override
    public void execute() {

        CSVWriter csvWriter = null;

        try {

            Writer writer = Files.newBufferedWriter(Paths.get(Registry.getCSVFILEPATH() + "bigflows.csv"));

            csvWriter = new CSVWriter(writer,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            for (Connection connection: _connections) {
                for (int i = 0; i < connection.getPackets().size(); i++) {
                    csvWriter.writeNext(extractConnectionData(connection.getPackets().get(i)));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                csvWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String[] extractConnectionData(Packet input) {

        IpV4Packet ipV4Packet = input.get(IpV4Packet.class);

        try {

            //System.out.println(tcpConnection.getPackets().get(0).get(IpV4Packet.class).getHeader().getTtl());
            //System.out.println(connection.getPackets().get(0).get(Packet.class).get(PcapPacket.class).getTimestamp().toEpochMilli());

            //String hour = Integer.toString(((PcapPacket) connection.getPackets().get(0)).getTimestamp();

            ArrayList<String> aux = formatIp(ipV4Packet.getHeader().getSrcAddr().toString());
            aux.addAll(formatIp(ipV4Packet.getHeader().getDstAddr().toString()));
            aux.add(Integer.toString(input.get(TcpPacket.class).getHeader().getSrcPort().valueAsInt()));
            aux.add(Integer.toString(input.get(TcpPacket.class).getHeader().getDstPort().valueAsInt()));

            String[] output = new String[aux.size()];
            aux.toArray(output);
            return output;

            //Hora Inicio
            //Hora Fin
            //Duracion
            //Tiempo entre paquetes (promedio)
            //Tamano de paquetes (promedio)

        } catch (Exception e) {
            //e.printStackTrace();
            IcmpV4CommonPacket icmpPacket = input.get(IcmpV4CommonPacket.class);

            ArrayList<String> aux = formatIp(ipV4Packet.getHeader().getSrcAddr().toString());
            aux.addAll(formatIp(ipV4Packet.getHeader().getDstAddr().toString()));
            aux.add("0");
            aux.add("0");

            String[] output = new String[aux.size()];
            aux.toArray(output);
            return output;
            //Hora Inicio
            //Hora Fin
            //Duracion
            //Tiempo entre paquetes (promedio)
            //Tamano de paquetes (promedio)
        }
    }

    private ArrayList<String> formatIp(String ip) {

        String[] aux = ip.replace("/", "").split("\\.");
        ArrayList<String> output = new ArrayList<>();
        for (int i = 0; i < aux.length; i++) {
            output.add(aux[i]);
        }
        return output;
    }
}
