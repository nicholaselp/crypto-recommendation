package com.cryptorecommendation.model;

import com.cryptorecommendation.utility.EnumStringValue;

public enum MarketStatus implements EnumStringValue {
    ACTIVE("active"),
    INACTIVE("inactive"),
    SUSPENDED("suspended");

    private final String value;

    MarketStatus(String value){
        this.value = value;
    }
    @Override
    public String getValue() { return value; }
}
