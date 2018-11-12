package com.Common;

import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;

import java.awt.font.ShapeGraphicAttribute;

public class Socket {

    private String _ip;
    private int _port;


    public Socket(String ip, int port) {

        _ip = ip;
        _port = port;

    }

    public int getPort() {
        return _port;
    }

    public String getIp() {

        return _ip;
    }

    public boolean equals(Socket aux){

        if (getIp().equals(aux.getIp()) && getPort() == aux.getPort()) {
            return true;
        }else {
            return false;
        }
    }
}
