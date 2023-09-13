package com.cryptorecommendation.service;

import com.cryptorecommendation.exceptions.ValidationException;
import com.cryptorecommendation.model.CryptoCurrency;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CSVReaderServiceTest {
    private static final String CSV_FOLDER = "src/test/resources/";

    private CSVReaderService csvReaderService;

    @Test
    public void success(){
        createCSVReader("csv-upload").readCsvFiles();

        List<CryptoCurrency> cryptoCurrencyList = csvReaderService.getCryptoCurrencyList();

        var expectedBitcoin = CryptoCurrency.builder("BTC")
                .addPrice(new Timestamp(Long.parseLong("1641020400000")), BigDecimal.valueOf(46979.61))
                .addPrice(new Timestamp(Long.parseLong("1641031200000")), BigDecimal.valueOf(47143.98))
                .addPrice(new Timestamp(Long.parseLong("1641034800000")), BigDecimal.valueOf(46871.09))
                .build();

        var expectedXRP = CryptoCurrency.builder("XRP")
                .addPrice(new Timestamp(Long.parseLong("1640995200000")), BigDecimal.valueOf(0.8298))
                .addPrice(new Timestamp(Long.parseLong("1641016800000")), BigDecimal.valueOf(0.842))
                .addPrice(new Timestamp(Long.parseLong("1641070800000")), BigDecimal.valueOf(0.8458))
                .addPrice(new Timestamp(Long.parseLong("1641099600000")), BigDecimal.valueOf(0.8391))
                .addPrice(new Timestamp(Long.parseLong("1641132000000")), BigDecimal.valueOf(0.8448))
                .build();

        assertThat(cryptoCurrencyList).isNotEmpty().hasSize(2)
                .containsExactlyInAnyOrderElementsOf(List.of(expectedXRP, expectedBitcoin));
    }

    @Test
    public void fail_empty_csv(){
        assertThatThrownBy(() -> createCSVReader("csv-upload-empty-csv").readCsvFiles())
                .isInstanceOf(ValidationException.class)
                .hasMessage("CSV file 'empty.csv' is empty");
    }

    @Test
    public void fail_no_csv(){
        assertThatThrownBy(() -> createCSVReader("csv-upload-no-csvs").readCsvFiles())
                .isInstanceOf(ValidationException.class)
                .hasMessage("No CSV files found in path: src/test/resources/csv-upload-no-csvs");
    }

    @Test
    public void fail_missing_row(){
        assertThatThrownBy(() -> createCSVReader("csv-upload-missing-row").readCsvFiles())
                .hasMessage("Error while reading CSVs")
                .isInstanceOf(ValidationException.class);
    }

    @Test
    public void fail_to_read_csv(){
        assertThatThrownBy(() -> createCSVReader(null).readCsvFiles())
                .hasMessage("Error while reading excel files from path: src/test/resources/null")
                .isInstanceOf(ValidationException.class);
    }

    private CSVReaderService createCSVReader(String fileName){
        return csvReaderService = new CSVReaderService(CSV_FOLDER + fileName);
    }

}
