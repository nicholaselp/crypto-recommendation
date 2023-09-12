package com.cryptorecommendation.controller.command;

import com.crypto.recommendation.generated.dto.CryptocurrencyDto;
import com.cryptorecommendation.service.CSVReaderService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class GetAllCryptosCommand implements Command<Void, List<CryptocurrencyDto>>{

    private final CSVReaderService csvReaderService;

    public GetAllCryptosCommand(CSVReaderService csvReaderService){
        this.csvReaderService = requireNonNull(csvReaderService, "CSVReaderService is missing");
    }

    //TODO: dont like this void request..
    @Override
    public List<CryptocurrencyDto> execute(Void request) {
        return csvReaderService.getCryptoCurrencyList()
                .stream().map(crypto -> {
                    CryptocurrencyDto dto = new CryptocurrencyDto();
                    dto.setSymbol(crypto.getSymbol());
                    dto.setNormalizedRange(crypto.getNormalizedRange());
                    return dto;
                })
                .sorted(Comparator.comparing(CryptocurrencyDto::getNormalizedRange))
                .collect(Collectors.toList());
    }
}
