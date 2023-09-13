package com.cryptorecommendation.model;

import com.cryptorecommendation.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CryptoCurrencyTest {

    CryptoCurrency xrp = CryptoCurrency.builder("XRP")
            .addPrice(new Timestamp(Long.parseLong("1640995200000")), BigDecimal.valueOf(0.8298)) //2022-01-01
            .addPrice(new Timestamp(Long.parseLong("1641016800000")), BigDecimal.valueOf(0.842)) //2022-01-01
            .addPrice(new Timestamp(Long.parseLong("1641070800000")), BigDecimal.valueOf(0.8458)) //2022-01-01
            .addPrice(new Timestamp(Long.parseLong("1641099600000")), BigDecimal.valueOf(0.8391)) //2022-01-02
            .addPrice(new Timestamp(Long.parseLong("1641132000000")), BigDecimal.valueOf(0.8448)) //2022-01-02
            .addPrice(new Timestamp(Long.parseLong("1641733200000")), BigDecimal.valueOf(0.7473)) //2022-01-09
            .build();

    @Test
    public void get_max_price(){
        assertThat(xrp.getMaxPrice()).isEqualTo(BigDecimal.valueOf(0.8458));
    }

    @Test
    public void get_min_price(){
        assertThat(xrp.getMinPrice()).isEqualTo(BigDecimal.valueOf(0.7473));
    }

    @Test
    public void get_newest_price(){
        assertThat(xrp.getNewestPrice()).isEqualTo(BigDecimal.valueOf(0.7473));
    }

    @Test
    public void get_oldest_price(){
        assertThat(xrp.getOldestPrice()).isEqualTo(BigDecimal.valueOf(0.8298));
    }

    @Test
    public void get_normalized_range(){
        //(max-min) / min --> ((0.8458-0.8298)/0.8298)
        assertThat(xrp.getNormalizedRange()).isEqualTo(BigDecimal.valueOf(0.1318));
    }

    @Test
    public void get_normalized_range_on_date() throws ParseException {
        String dateString = "2022-01-02";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        assertThat(xrp.getNormalizedRange(dateFormat.parse(dateString))).isEqualTo(BigDecimal.valueOf(0.0067));
    }

    @Test
    public void normalized_range_on_date_only_one() throws ParseException {
        String dateString = "2022-01-09";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        assertThat(xrp.getNormalizedRange(dateFormat.parse(dateString))).isEqualTo(BigDecimal.valueOf(0.0000).setScale(4));
    }

    @Test
    public void fail_get_normalized_range_on_date_not_found() throws ParseException {
        String dateString = "2028-01-02";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        assertThatThrownBy(() -> xrp.getNormalizedRange(dateFormat.parse(dateString)))
                .isInstanceOf(ValidationException.class)
                .hasMessage("No data found for: 2028-01-02");
    }

    @Test
    public void test_equals(){
        CryptoCurrency xrp1 = CryptoCurrency.builder("XRP")
                .addPrice(new Timestamp(Long.parseLong("1640995200000")), BigDecimal.valueOf(0.8298)) //2022-01-01
                .addPrice(new Timestamp(Long.parseLong("1641016800000")), BigDecimal.valueOf(0.842)) //2022-01-01
                .addPrice(new Timestamp(Long.parseLong("1641070800000")), BigDecimal.valueOf(0.8458)) //2022-01-01
                .addPrice(new Timestamp(Long.parseLong("1641099600000")), BigDecimal.valueOf(0.8391)) //2022-01-02
                .addPrice(new Timestamp(Long.parseLong("1641132000000")), BigDecimal.valueOf(0.8448)) //2022-01-02
                .addPrice(new Timestamp(Long.parseLong("1641733200000")), BigDecimal.valueOf(0.7473)) //2022-01-09
                .build();

        CryptoCurrency xrp2 = CryptoCurrency.builder("XRP")
                .addPrice(new Timestamp(Long.parseLong("1640995200000")), BigDecimal.valueOf(0.8298)) //2022-01-01
                .addPrice(new Timestamp(Long.parseLong("1641016800000")), BigDecimal.valueOf(0.842)) //2022-01-01
                .addPrice(new Timestamp(Long.parseLong("1641070800000")), BigDecimal.valueOf(0.8458)) //2022-01-01
                .addPrice(new Timestamp(Long.parseLong("1641099600000")), BigDecimal.valueOf(0.8391)) //2022-01-02
                .addPrice(new Timestamp(Long.parseLong("1641132000000")), BigDecimal.valueOf(0.8448)) //2022-01-02
                .addPrice(new Timestamp(Long.parseLong("1641733200000")), BigDecimal.valueOf(0.7473)) //2022-01-09
                .build();

        assertThat(xrp1.equals(xrp2)).isTrue();

    }
}
