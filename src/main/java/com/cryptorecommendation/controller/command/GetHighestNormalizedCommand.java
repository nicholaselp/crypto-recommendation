package com.cryptorecommendation.controller.command;

import com.crypto.recommendation.generated.dto.NormalizedRangeDto;
import com.cryptorecommendation.exceptions.ValidationException;
import com.cryptorecommendation.model.CryptoCurrency;
import com.cryptorecommendation.service.CSVReaderService;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;

import static java.util.Objects.requireNonNull;

public class GetHighestNormalizedCommand implements Command<String, NormalizedRangeDto>{

    private final CSVReaderService csvReaderService;

    public GetHighestNormalizedCommand(CSVReaderService csvReaderService){
        this.csvReaderService = requireNonNull(csvReaderService, "CSVReaderService is missing");
    }

    @Override
    public NormalizedRangeDto execute(String date) {
        Date transformedDate = transformDate(date);
        return csvReaderService.getCryptoCurrencyList().stream()
                .map(crypto -> {
                    BigDecimal normalizedRange = crypto.getNormalizedRange(transformedDate);
                    NormalizedRangeDto dto = new NormalizedRangeDto();
                    dto.setSymbol(crypto.getSymbol());
                    dto.setNormalizedRange(normalizedRange);
                    return dto;
                })
                .max(Comparator.comparing(NormalizedRangeDto::getNormalizedRange))
                .orElse(null);
    }

    private Date transformDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (Exception exception){
            throw new ValidationException("Incorrect date format, Please provide date with YYYY-MM-DD format");
        }
    }
}

