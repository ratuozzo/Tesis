package com.DomainLogicLayer;

import java.util.ArrayList;

public class CommandFactory {

    public static Object instantiateReadPcap(String filePath) {
        return new ReadPcap(filePath);
    }

    public static Object instantiateReadMultiplePcaps(ArrayList<String> filePaths) {
        return new ReadMultiplePcaps(filePaths);
    }
}