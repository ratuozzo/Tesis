package com.Common.Entity;

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

        return getIp().equals(aux.getIp()) && getPort() == aux.getPort();
    }
}
