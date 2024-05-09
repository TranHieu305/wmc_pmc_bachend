package com.wms.wms.util;

public class StringHelper {

    public static String preProcess(String value) {
        value = trimString(value);
        return value;
    }

    public static String trimString(String value) {
        return value.trim();
    }


}
