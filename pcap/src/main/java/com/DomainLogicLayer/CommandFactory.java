package com.DomainLogicLayer;

public class CommandFactory {

    public static Object instantiateReadPcap(String filePath) {
        return new ReadPcap(filePath);
    }
}