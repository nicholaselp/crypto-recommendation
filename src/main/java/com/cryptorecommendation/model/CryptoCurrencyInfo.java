package com.cryptorecommendation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

public class CryptoCurrencyInfo {
    private final String symbol;
    private final BigDecimal oldestPrice;
    private final BigDecimal newestPrice;
    private final BigDecimal minPrice;
    private final BigDecimal maxPrice;

    public CryptoCurrencyInfo(String symbol, BigDecimal oldestPrice, BigDecimal newestPrice,
                              BigDecimal minPrice, BigDecimal maxPrice){
        this.symbol = requireNonNull(symbol, "Symbol is missing");
        this.oldestPrice = requireNonNull(oldestPrice, "oldestPrice is missing");
        this.newestPrice = requireNonNull(newestPrice, "newestPrice is missing");
        this.minPrice = requireNonNull(minPrice, "minPrice is missing");
        this.maxPrice = requireNonNull(maxPrice, "maxPrice is missing");
    }

    public String getSymbol() { return symbol; }
    public BigDecimal getOldestPrice() { return oldestPrice; }
    public BigDecimal getNewestPrice() { return newestPrice; }
    public BigDecimal getMinPrice() { return minPrice; }
    public BigDecimal getMaxPrice() { return maxPrice; }

    public static CryptoCurrencyInfo.Builder builder(String symbol){ return new CryptoCurrencyInfo.Builder(symbol); }

    public static class Builder {
        private final String symbol;
        private BigDecimal oldestPrice;
        private BigDecimal newestPrice;
        private BigDecimal minPrice;
        private BigDecimal maxPrice;
        private Builder(String symbol){
            this.symbol = requireNonNull(symbol, "symbol is missing");
        }

        public Builder withOldestPrice(BigDecimal oldestPrice){
            this.oldestPrice = oldestPrice;
            return this;
        }

        public Builder withNewestPrice(BigDecimal newestPrice){
            this.newestPrice = newestPrice;
            return this;
        }

        public Builder withMinPrice(BigDecimal minPrice){
            this.minPrice = minPrice;
            return this;
        }

        public Builder withMaxPrice(BigDecimal maxPrice){
            this.maxPrice = maxPrice;
            return this;
        }

        public CryptoCurrencyInfo build(){
            return new CryptoCurrencyInfo(symbol, oldestPrice, newestPrice, minPrice, maxPrice);
        }


    }




}
