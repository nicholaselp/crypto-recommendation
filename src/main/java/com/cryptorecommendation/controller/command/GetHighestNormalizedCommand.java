package com.cryptorecommendation.controller.command;

import com.crypto.recommendation.generated.dto.NormalizedRangeDto;
import com.cryptorecommendation.model.CryptoCurrency;
import com.cryptorecommendation.service.CSVReaderService;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Comparator;

import static java.util.Objects.requireNonNull;

public class GetHighestNormalizedCommand implements Command<DateTime, NormalizedRangeDto>{

    private final CSVReaderService csvReaderService;

    public GetHighestNormalizedCommand(CSVReaderService csvReaderService){
        this.csvReaderService = requireNonNull(csvReaderService, "CSVReaderService is missing");
    }

    @Override
    public NormalizedRangeDto execute(DateTime date) {
        return csvReaderService.getCryptoCurrencyList().stream()
                .map(crypto -> {
                    BigDecimal normalizedRange = crypto.getNormalizedRange(date);
                    NormalizedRangeDto dto = new NormalizedRangeDto();
                    dto.setSymbol(crypto.getSymbol());
                    dto.setHighestNormRange(normalizedRange);
                    return dto;
                })
                .max(Comparator.comparing(NormalizedRangeDto::getHighestNormRange))
                .orElse(null);
    }
}

