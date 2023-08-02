package com.ems.employeemanagement.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Util {
    private static final Logger logger = LoggerFactory.getLogger(Util.class);
    public static String getTrackingId() {
        return UUID.randomUUID().toString().replace("-","");
    }
}
