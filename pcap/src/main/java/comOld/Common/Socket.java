package comOld.Common;

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
