package com.Common;

public class Registry {

    public final static String PCAPFILEPATHW = "D:\\Repos\\Tesis\\Tesis\\pcap\\pcaps\\generated\\";
    public final static String PCAPFILEPATHL = "/home/andres/Documents/Developing/Projects/Universidad/Tesis/Tesis/pcap/pcaps/Generated/";

    public final static int PACKAGECOUNT = 1000000;


    public static String getPCAPFILEPATH() {
        if (System.getProperty("os.name").startsWith("Windows")) {
            return PCAPFILEPATHW;
        }
        return PCAPFILEPATHL;
    }
}
