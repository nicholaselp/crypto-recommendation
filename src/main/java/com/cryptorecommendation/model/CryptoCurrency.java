package com.cryptorecommendation.model;

import com.cryptorecommendation.exceptions.ValidationException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
        return getMaxPrice().subtract(getMinPrice()).divide(getMinPrice(), 4, RoundingMode.HALF_UP);
    }

    public BigDecimal getNormalizedRange(Date date){
        var calendar = createCalendar(date);

        if(!containsDate(calendar)){
                throw new ValidationException("No data found for: " +
                        new SimpleDateFormat("yyyy-MM-dd").format(date));

        }

        var maxPrice = getMaxPriceSpecificDate(calendar);
        var minPrice = getMinPriceSpecificDate(calendar);
        return maxPrice.subtract(minPrice).divide(minPrice, 4, RoundingMode.HALF_UP);
    }

    private boolean containsDate(Calendar calendar) {
        return priceMap.keySet().stream()
                .map(this::createCalendar)
                .anyMatch(entrycalendar -> entrycalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                        && entrycalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
                        && entrycalendar.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH));
    }

    private BigDecimal getMaxPriceSpecificDate(Calendar calendar){
        return getSpecificDate(calendar)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal getMinPriceSpecificDate(Calendar calendar){
        return getSpecificDate(calendar)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    private Stream<BigDecimal> getSpecificDate(Calendar calendar){
        return priceMap.entrySet().stream()
                .filter(entry -> {
                    var entryCalendar = createCalendar(entry.getKey());
                    return entryCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                            && entryCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
                            && entryCalendar.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH);
                })
                .map(Map.Entry::getValue);
    }

    private Calendar createCalendar(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CryptoCurrency that = (CryptoCurrency) o;
        return Objects.equals(symbol, that.symbol) &&
                Objects.equals(priceMap, that.priceMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, priceMap);
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
