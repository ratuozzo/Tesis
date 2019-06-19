package com.Common.Entity;

public class Rule {

    private String _srcIp;
    private String _dstIp;
    private int _srcPort;
    private int _dstPort;

    public Rule(String srcIp, int srcPort, String dstIp, int dstPort) {
        _srcIp = srcIp;
        _dstIp = dstIp;
        _srcPort = srcPort;
        _dstPort = dstPort;
    }


    public String getSrcIp() {
        return _srcIp;
    }

    public void setSrcIp(String _srcIp) {
        this._srcIp = _srcIp;
    }

    public String getDstIp() {
        return _dstIp;
    }

    public void setDstIp(String _dstIp) {
        this._dstIp = _dstIp;
    }

    public int getSrcPort() {
        return _srcPort;
    }

    public void setSrcPort(int _srcPort) {
        this._srcPort = _srcPort;
    }

    public int getDstPort() {
        return _dstPort;
    }

    public void setDstPort(int _dstPort) {
        this._dstPort = _dstPort;
    }
}
