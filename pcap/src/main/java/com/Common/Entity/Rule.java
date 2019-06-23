package com.Common.Entity;

public class Rule {

    private String _srcIp;
    private String _dstIp;
    private String _srcPort;
    private String _dstPort;

    public Rule(String srcIp, String srcPort, String dstIp, String dstPort) {
        _srcIp = srcIp;
        _dstIp = dstIp;
        _srcPort = srcPort;
        _dstPort = dstPort;
    }

    public String get_srcIp() {
        return _srcIp;
    }

    public void set_srcIp(String _srcIp) {
        this._srcIp = _srcIp;
    }

    public String get_dstIp() {
        return _dstIp;
    }

    public void set_dstIp(String _dstIp) {
        this._dstIp = _dstIp;
    }

    public String get_srcPort() {
        return _srcPort;
    }

    public void set_srcPort(String _srcPort) {
        this._srcPort = _srcPort;
    }

    public String get_dstPort() {
        return _dstPort;
    }

    public void set_dstPort(String _dstPort) {
        this._dstPort = _dstPort;
    }
}
