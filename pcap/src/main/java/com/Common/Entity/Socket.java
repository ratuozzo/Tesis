package com.Common.Entity;

import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import java.util.ArrayList;

public class Socket {

    private String _ip;
    private int _port;


    public Socket(String ip, int port) {
        _ip = ip;
        _port = port;
    }

    public static ArrayList<Socket> packetToSockets(Packet input) {
        TcpPacket tcpAux = input.get(TcpPacket.class);
        IpV4Packet ipAux = input.get(IpV4Packet.class);
        Socket src = new Socket(ipAux.getHeader().getSrcAddr().toString(), tcpAux.getHeader().getSrcPort().valueAsInt());
        Socket dst = new Socket(ipAux.getHeader().getDstAddr().toString(), tcpAux.getHeader().getDstPort().valueAsInt());
        ArrayList<Socket> output = new ArrayList<>();
        output.add(src);
        output.add(dst);
        return output;
    }

    public int getPort() {
        return _port;
    }

    public String getIp() {
        return _ip;
    }

    public boolean equals(Socket aux){

        return getIp().equals(aux.getIp()) && getPort() == aux.getPort();
    }
}
