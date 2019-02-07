package comOld.PackageReader;

public class Session {

    private String _sourceIp;
    private String _destinationIp;
    private int _sourcePort;
    private int _destinationPort;
    private String _protocol;

    public Session (String sourceIp, int sourcePort, String destinationIp, int destinationPort) {
        _sourceIp = sourceIp;
        _sourcePort = sourcePort;
        _destinationIp = destinationIp;
        _destinationPort = destinationPort;
        setProtocol();
    }

    public Session (String sourceIp, int sourcePort, String destinationIp, int destinationPort, String protocol) {
        _sourceIp = sourceIp;
        _sourcePort = sourcePort;
        _destinationIp = destinationIp;
        _destinationPort = destinationPort;
        _protocol = protocol;
    }

    public String getSourceIp() {
        return _sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        _sourceIp = sourceIp;
    }

    public String getDestinationIp() {
        return _destinationIp;
    }

    public void setDestinationIp(String destinationIp) {
        _destinationIp = destinationIp;
    }

    public int getSourcePort() {
        return _sourcePort;
    }

    public void setSourcePort(int sourcePort) {
        _sourcePort = sourcePort;
    }

    public int getDestinationPort() {
        return _destinationPort;
    }

    public void setDestinationPort(int destinationPort) {
        _destinationPort = destinationPort;
    }

    public String getProtocol() {
        return _protocol;
    }

    private void setProtocol() {

        if(_destinationPort == 443) {
            _protocol = "HTTPS";
        } else if(_destinationPort == 80) {
            _protocol = "HTTP";
        }

    }
}
