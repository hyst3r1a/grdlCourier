package com.mishon.couriers.Services;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Service {
    final static Logger logger = LogManager.getLogger("com.zetcode");

    public static String getXSSSecureParameter(String s) {
        return s.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;").
                replaceAll("'", "&apos;").replaceAll("&", "&amp;");
    }
}
