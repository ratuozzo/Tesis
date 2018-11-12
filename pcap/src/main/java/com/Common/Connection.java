package com.Common;

import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import java.util.ArrayList;

public class Connection {

    private Socket _source;
    private Socket _destination;
    private String _protocol;
    private ArrayList<Packet> _packets = new ArrayList<Packet>();


    public Connection(Packet packet) {

        IpV4Packet aux = (IpV4Packet) packet;

        _source = new Socket(aux.getHeader().getSrcAddr().toString(),
                packet.get(TcpPacket.class).getHeader().getSrcPort().valueAsInt());
        _destination= new Socket(aux.getHeader().getDstAddr().toString(),
                packet.get(TcpPacket.class).getHeader().getDstPort().valueAsInt());

        setProtocol();

        _packets.add(packet);

    }

    private void setProtocol() {
        if(checkSSH()){
            _protocol = "SSH";
        }else if(checkFTP()){
            _protocol = "FTP";
        }else if(checkHTTP()){
            _protocol = "HTTP";
        }else if(checkTELNET()){
            _protocol = "TELNET";
        }else if(checkNMAP()){
            _protocol = "NMAP";
        }else if(checkPING()){
            _protocol = "PING";
        }else{
            _protocol = "Other";
        }

    }

    private boolean checkPING() {

        return false;
    }

    private boolean checkNMAP() {

        return false;
    }

    private boolean checkTELNET() {

        if(checkPorts(new int[] {23})){
            return true;
        }else {
            return false;
        }
    }

    private boolean checkHTTP() {


        if(checkPorts(new int[] {80,8080,8090,9090,9080,443})){
            return true;
        }else {
            return false;
        }
    }

    private boolean checkFTP() {

        if(checkPorts(new int[] {20,21})){
            return true;
        }else {
            return false;
        }
    }

    private boolean checkSSH() {

        if(checkPorts(new int[] {22})){
            return true;
        }else {
            return false;
        }
    }

    private boolean checkPorts(int[] possiblePorts){

        for (int i = 0; i < possiblePorts.length ; i++) {
            if(_source.getPort() == possiblePorts[i] ||
               _destination.getPort() == possiblePorts[i]){

                return true;
            }
        }

        return false;

    }


    public Socket getDestination() {

        return _destination;
    }

    public Socket getSource() {

        return _source;
    }
}
