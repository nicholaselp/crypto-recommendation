package com.cryptorecommendation.service;

import com.cryptorecommendation.model.CryptoCurrency;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class CSVReaderService {
    private static final Logger logger = LoggerFactory.getLogger(CSVReaderService.class);
    private final String csvPath;

    //TODO: os this the best object to use for my purpose? research it
    private List<CryptoCurrency> cryptoCurrencyList = new ArrayList<>();

    public CSVReaderService(String csvPath){
        this.csvPath = requireNonNull(csvPath, "csv path is missing");
    }

    public List<CryptoCurrency> getCryptoCurrencyList(){ return cryptoCurrencyList; }

    //TODO: handle if list is final empty]
    //TODO: if no excels are found?
    //TODO: if an excel is empty..
    public void readCsvFiles(){
        getCsvFiles().forEach(csvFile -> {
            logger.info("CSV File ---> " + csvFile.toString());
            cryptoCurrencyList.add(readFromCsv(csvFile));
        });
        logger.info("Processing excel files completed");
    }

    private List<Path> getCsvFiles() {
        try(Stream<Path> files = Files.list(Paths.get(csvPath))){
            return files.filter(Files::isRegularFile)
                    .filter(path -> path.toString().toLowerCase().endsWith(".csv"))
                    .collect(Collectors.toList());
        } catch (IOException exception){
            logger.error(exception.toString());
            throw new RuntimeException("Error while reading excel files from path: " + csvPath);
        }
    }

    private CryptoCurrency readFromCsv(Path csvFile) {
        try (Reader reader = Files.newBufferedReader(csvFile, StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReader(reader)) {
            List<String[]> records = csvReader.readAll();

            if(!records.isEmpty()){
                var cryptoBuilder = CryptoCurrency.builder(records.get(1)[1]);

                records.stream().skip(1) //Skip the first record (header)
                        .forEach(record -> {
                            cryptoBuilder.addPrice(new Timestamp(Long.parseLong(record[0])), new BigDecimal(record[2]));
                        });
                return cryptoBuilder.build();
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("CSV is empty..");
    }
}
