package com.cryptorecommendation.controller.command;

import com.crypto.recommendation.generated.dto.NormalizedRangeDto;
import com.cryptorecommendation.service.CSVReaderService;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class GetAllCryptosCommand implements Command<Void, List<NormalizedRangeDto>>{

    private final CSVReaderService csvReaderService;

    public GetAllCryptosCommand(CSVReaderService csvReaderService){
        this.csvReaderService = requireNonNull(csvReaderService, "CSVReaderService is missing");
    }
    @Override
    public List<NormalizedRangeDto> execute(@Nullable Void request) {
        return csvReaderService.getCryptoCurrencyList()
                .stream().map(crypto -> {
                    NormalizedRangeDto dto = new NormalizedRangeDto();
                    dto.setSymbol(crypto.getSymbol());
                    dto.setNormalizedRange(crypto.getNormalizedRange());
                    return dto;
                })
                .sorted(Comparator.comparing(NormalizedRangeDto::getNormalizedRange))
                .collect(Collectors.toList());
    }
}
