package com.cryptorecommendation.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

public class CryptoCurrency {
    private final String symbol;
    private Map<Timestamp, BigDecimal> priceMap;

    private CryptoCurrency(String symbol, Map<Timestamp, BigDecimal> priceMap){
        this.symbol = requireNonNull(symbol, "symbol is missing");
        this.priceMap = priceMap;
    }

    public String getSymbol(){ return symbol; }

    public BigDecimal getMaxPrice(){
        return priceMap.entrySet().stream().max(Map.Entry.comparingByKey()).map(Map.Entry::getValue)
                .orElse(BigDecimal.ZERO);
    }
    public BigDecimal getMinPrice(){
        return priceMap.entrySet().stream().min(Map.Entry.comparingByKey()).map(Map.Entry::getValue)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal getNewestPrice(){
        return priceMap.entrySet().stream().max(Map.Entry.comparingByKey()).map(Map.Entry::getValue)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal getOldestPrice(){
        return priceMap.entrySet().stream().min(Map.Entry.comparingByKey()).map(Map.Entry::getValue)
                .orElse(BigDecimal.ZERO);
    }


    public BigDecimal getNormalizedRange(){
        //TODO: is Rounding.FLOOR ok? also scale 2 is ok?
        return getMaxPrice().subtract(getMinPrice()).divide(getMaxPrice(), 2, RoundingMode.FLOOR);
    }

    public BigDecimal getNormalizedRange(DateTime dateTime){
        return getMaxPriceSpecificDate(dateTime).subtract(getMinPriceSpecificDate(dateTime))
                .divide(getMaxPriceSpecificDate(dateTime), 2, RoundingMode.FLOOR);
    }

    private BigDecimal getMaxPriceSpecificDate(DateTime dateTime){
        return priceMap.entrySet().stream()
                .filter(entry -> entry.getKey().equals(new Timestamp(dateTime.getMillis())))
                .map(Map.Entry::getValue)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal getMinPriceSpecificDate(DateTime dateTime){
        return priceMap.entrySet().stream()
                .filter(entry -> entry.getKey().equals(new Timestamp(dateTime.getMillis())))
                .map(Map.Entry::getValue)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    public static Builder builder(String symbol){ return new Builder(symbol); }

    public static class Builder {
        private final String symbol;
        private Map<Timestamp, BigDecimal> priceMap;

        private Builder(String symbol){
            this.symbol = requireNonNull(symbol, "symbol is missing");
        }

        public Builder addPrice(Timestamp timestamp, BigDecimal price){
            if(isNull(priceMap)){
                priceMap = new HashMap<>();
            }

            priceMap.put(timestamp, price);
            return this;
        }

        public CryptoCurrency build(){
            return new CryptoCurrency(symbol, priceMap);
        }


    }
}
