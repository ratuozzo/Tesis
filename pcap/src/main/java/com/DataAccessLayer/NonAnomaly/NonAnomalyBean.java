package com.DataAccessLayer.NonAnomaly;

public class NonAnomalyBean {

    private int _id;
    private String _IpSource;
    private String _IpDestination;
    private int _PortSource;
    private int _PortDestination;

    public NonAnomalyBean(int id, String ipSource, String ipDestination, int portSource, int portDestination) {
        _id = id;
        _IpSource = ipSource;
        _IpDestination = ipDestination;
        _PortSource = portSource;
        _PortDestination = portDestination;
    }

    public NonAnomalyBean(String ipSource, String ipDestination, int portSource, int portDestination) {
        _IpSource = ipSource;
        _IpDestination = ipDestination;
        _PortSource = portSource;
        _PortDestination = portDestination;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public String getIpSource() {
        return _IpSource;
    }

    public void setIpSource(String ipSource) {
        _IpSource = ipSource;
    }

    public String getIpDestination() {
        return _IpDestination;
    }

    public void setIpDestination(String ipDestination) {
        _IpDestination = ipDestination;
    }

    public int getPortSource() {
        return _PortSource;
    }

    public void setPortSource(int portSource) {
        _PortSource = portSource;
    }

    public int getPortDestination() {
        return _PortDestination;
    }

    public void setPortDestination(int portDestination) {
        _PortDestination = portDestination;
    }
}
