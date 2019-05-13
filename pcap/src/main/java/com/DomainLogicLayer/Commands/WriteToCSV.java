package com.DomainLogicLayer.Commands;

import com.Common.Entity.Connections.Connection;
import com.Common.Entity.Socket;
import com.Common.Registry;
import com.opencsv.CSVWriter;
import org.pcap4j.packet.*;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class WriteToCSV extends Command {

    private ArrayList<Connection> _connections;
    private String _name;

    public WriteToCSV(ArrayList<Connection> connections, String name) {
        _connections = connections;
        _name = name;
    }

    @Override
    public void execute() {

        CSVWriter csvWriter = null;

        doShuffle();

        try {

            Writer writer = Files.newBufferedWriter(Paths.get(Registry.getCSVFILEPATH() + _name));

            csvWriter = new CSVWriter(writer,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            for (Connection connection: _connections) {
                for (int i = 0; i < connection.getUniquePackets().size(); i++) {
                    csvWriter.writeNext(extractConnectionData(connection.getUniquePackets().get(i)));
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

    private void doShuffle() {
        Collections.shuffle(_connections, new Random());
        for (Connection connection:_connections) {
            Collections.shuffle(connection.getPackets(), new Random());
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
            /*aux.add(Byte.toString(ipV4Packet.getHeader().getTtl()));
            aux.add(Byte.toString(ipV4Packet.getHeader().getTos().value()));
            aux.add(Integer.toString(ipV4Packet.getPayload().length()));
            aux.add(Integer.toString(input.get(TcpPacket.class).getHeader().getWindowAsInt()));*/

            return scaleData(aux);

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
            /*aux.add(Byte.toString(ipV4Packet.getHeader().getTtl()));
            aux.add(Byte.toString(ipV4Packet.getHeader().getTos().value()));
            aux.add(Integer.toString(ipV4Packet.getPayload().length()));
            aux.add("0");*/

            return scaleData(aux);
            //Hora Inicio
            //Hora Fin
            //Duracion
            //Tiempo entre paquetes (promedio)
            //Tamano de paquetes (promedio)
        }
    }

    private String[] scaleData(ArrayList<String> aux) {
        String[] output = new String[10];
        for (int i = 0; i < aux.size(); i++) {
            float number = Float.parseFloat(aux.get(i)) / 1000;
            output[i] = String.valueOf(number);
        }
        return output;
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
