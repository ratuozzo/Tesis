package comOld.Common;

import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Connection {

    private Socket _source;
    private Socket _destination;
    private String _protocol;
    private ArrayList<Packet> _packets = new ArrayList<Packet>();
    private String _status;
    private String _openned;
    private String _closed;
    private int _seqToClose;
    private int _ackToClose;


    public Connection(Packet packet) {

        IpV4Packet aux = packet.get(IpV4Packet.class);

        _source = new Socket(aux.getHeader().getSrcAddr().toString(),
                packet.get(TcpPacket.class).getHeader().getSrcPort().valueAsInt());
        _destination= new Socket(aux.getHeader().getDstAddr().toString(),
                packet.get(TcpPacket.class).getHeader().getDstPort().valueAsInt());

        _protocol = setProtocol();

        _status = "Opened";
        if (packet.get(TcpPacket.class).getHeader().getSyn()){
            _openned = "Opened Cleanly";
        }else {
            _openned = "Opened Dirtily";
        }

        _packets.add(packet);

    }

    public String getProtocol() {
        return _protocol;
    }

    private String setProtocol() {
        if(checkSSH()){
            return  "SSH";
        }else if(checkFTP()){
            return  "FTP";
        }else if(checkHTTP()){
            return  "HTTP";
        }else if(checkTELNET()){
            return  "TELNET";
        }else if(checkNMAP()){
            return  "NMAP";
        }else if(checkPING()){
            return  "PING";
        }else{
            return  "Other";
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

    public ArrayList<Packet> getPackets() {
        return _packets;
    }

    public boolean equals(Connection connection) {
        if ((getSource().getIp().equals(connection.getSource().getIp()) && getDestination().equals(connection.getDestination())) ||
            (getSource().getIp().equals(connection.getDestination().getIp()) && getDestination().equals(connection.getSource()))) {
            return true;
        } else {
            return false;
        }
    }

    public void setStatus(String status) {
        _status = status;
    }

    public void prepareToClose(Packet packet) {

        _status = "Closing";

        TcpPacket aux = packet.get(TcpPacket.class);

        _seqToClose = abs(aux.getHeader().getSequenceNumber());
        _ackToClose = abs(aux.getHeader().getAcknowledgmentNumber());

    }

    public void addPacket(Packet packet) {

        if(!_status.equals("Closing")){
            _packets.add(packet);
        }else {

            TcpPacket aux = packet.get(TcpPacket.class);

                if(aux.getHeader().getAck() && !aux.getHeader().getFin() &&
               abs(aux.getHeader().getSequenceNumber())+1==_seqToClose &&
               abs(aux.getHeader().getAcknowledgmentNumber())+1==_ackToClose){

                _packets.add(packet);
                _status = "Closed";
                _closed = "Closed Cleanly";
            }else {

                _packets.add(packet);

            }
        }

    }

    public String getOpened() {
        return _openned;
    }

    public String getClosed() {
        return _closed;
    }

    public String getStatus() {
        return _status;
    }
}
