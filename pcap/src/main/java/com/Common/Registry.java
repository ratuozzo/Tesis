package com.Common;

public class Registry {

    public final static String PCAPFILEPATHW = "D:\\Repos\\Tesis\\Tesis\\pcap\\pcaps\\generated\\";
    public final static String PCAPFILEPATHL = "/home/andres/Documents/Developing/Projects/Universidad/Tesis/Tesis/pcap/pcaps/Generated/";
    public final static String CSVFILEPATHW = "D:\\Repos\\Tesis\\Tesis\\pcap\\src\\main\\resources\\NetData\\";
    public final static String CSVFILEPATHL = "/home/andres/Documents/Developing/Projects/Universidad/Tesis/Tesis/pcap/src/main/resources/NetData/";
    public final static String RESOURCEFILEPATHW = "D:\\Repos\\Tesis\\Tesis\\pcap\\src\\main\\resources\\";
    public final static String RESOURCEFILEPATHL = "/home/andres/Documents/Developing/Projects/Universidad/Tesis/Tesis/pcap/src/main/resources/";

    public final static int PACKAGECOUNT = 1000000;


    public static String getPCAPFILEPATH() {
        if (System.getProperty("os.name").startsWith("Windows")) {
            return PCAPFILEPATHW;
        }
        return PCAPFILEPATHL;
    }

    public static String getCSVFILEPATH() {
        if (System.getProperty("os.name").startsWith("Windows")) {
            return CSVFILEPATHW;
        }
        return CSVFILEPATHL;
    }

    public static String getRESOURCEFILEPATH() {
        if (System.getProperty("os.name").startsWith("Windows")) {
            return RESOURCEFILEPATHW;
        }
        return RESOURCEFILEPATHL;
    }
}
