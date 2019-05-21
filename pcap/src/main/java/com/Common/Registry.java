package com.Common;

public class Registry {

    //private final static String _basePath = "C:\\Users\\andres\\Documents\\Developing\\Projects\\Universidad";
    private final static String _basePath = "D:\\Repos";
    public final static String PCAPFILEPATHW = _basePath +"\\Tesis\\Tesis\\pcap\\pcaps\\generated\\";
    public final static String PCAPFILEPATHL = "/home/andres/Documents/Developing/Projects/Universidad/Tesis/Tesis/pcap/pcaps/Generated/";
    public final static String CSVFILEPATHW = _basePath +"\\Tesis\\Tesis\\pcap\\src\\main\\resources\\NetData\\";
    public final static String CSVFILEPATHL = "/home/andres/Documents/Developing/Projects/Universidad/Tesis/Tesis/pcap/src/main/resources/NetData/";
    public final static String RESOURCEFILEPATHW = _basePath +"\\Tesis\\Tesis\\pcap\\src\\main\\resources\\";
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
