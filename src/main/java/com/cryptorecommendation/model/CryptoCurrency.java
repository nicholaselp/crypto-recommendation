package com.cryptorecommendation.model;

import com.cryptorecommendation.exceptions.ValidationException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return priceMap.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getValue)
                .orElse(BigDecimal.ZERO);
    }
    public BigDecimal getMinPrice(){
        return priceMap.entrySet().stream().min(Map.Entry.comparingByValue()).map(Map.Entry::getValue)
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
        return getMaxPrice().subtract(getMinPrice()).divide(getMinPrice(), 4, RoundingMode.FLOOR);
    }

    public BigDecimal getNormalizedRange(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if(!containsDate(calendar)){
            throw new ValidationException("No Data found for: " + date);
        }

        var maxPrice = getMaxPriceSpecificDate(calendar);
        var minPrice = getMinPriceSpecificDate(calendar);
        return maxPrice.subtract(minPrice).divide(minPrice, 4, RoundingMode.FLOOR);
    }

    private boolean containsDate(Calendar calendar) {
        return priceMap.keySet().stream()
                .map(timestamp -> {
                    Calendar entryCalendar = Calendar.getInstance();
                    entryCalendar.setTime(timestamp);
                    return entryCalendar;
                })
                .anyMatch(entrycalendar -> entrycalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                        && entrycalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
                        && entrycalendar.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH));
    }

    private BigDecimal getMaxPriceSpecificDate(Calendar calendar){
        var x = getSpecificDate(calendar).toList();
        return getSpecificDate(calendar)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal getMinPriceSpecificDate(Calendar calendar){
        return getSpecificDate(calendar)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO); //TODO: dont like this..
    }

    private Stream<BigDecimal> getSpecificDate(Calendar calendar){
        return priceMap.entrySet().stream()
                .filter(entry -> {
                    Calendar entryCalendar = Calendar.getInstance();
                    entryCalendar.setTime(entry.getKey());
                    return entryCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                            && entryCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
                            && entryCalendar.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH);
                })
                .map(Map.Entry::getValue);
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
