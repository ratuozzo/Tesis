package com.Common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerOps {

    private static Logger logger = LoggerFactory.getLogger( LoggerOps.class );


    public static void debug(String out){

        logger.debug( "Debug: " + out );

    }

    public static void error(String out){

        logger.error( "Error: " + out );

    }

}