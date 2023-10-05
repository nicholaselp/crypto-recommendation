package com.cryptorecommendation.utility;

import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class StringUtility {

    public static String emptyIfBlank(String value){
        return isBlank(value) ? StringUtils.EMPTY : value;
    }
}