package com.wms.wms.util;

import org.mapstruct.Named;

public class StringHelper {

    @Named("preProcessString")
    public static String preProcess(String value) {
        value = trimString(value);
        return value;
    }

    public static String trimString(String value) {
        if (value == null) {
            return null;
        }
        return value.trim();
    }
}
