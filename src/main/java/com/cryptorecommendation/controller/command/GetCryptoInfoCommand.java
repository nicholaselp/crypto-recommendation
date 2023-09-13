package com.cryptorecommendation.controller.command;

import com.crypto.recommendation.generated.dto.CryptocurrencyInfoDto;
import com.cryptorecommendation.exceptions.CryptoCurrencyNotFoundException;
import com.cryptorecommendation.model.CryptoCurrency;
import com.cryptorecommendation.service.CSVReaderService;

import static java.util.Objects.requireNonNull;

public class GetCryptoInfoCommand implements Command<String, CryptocurrencyInfoDto> {

    private final CSVReaderService csvReaderService;

    public GetCryptoInfoCommand(CSVReaderService csvReaderService){
        this.csvReaderService = requireNonNull(csvReaderService, "CSVReaderService is missing");
    }

    @Override
    public CryptocurrencyInfoDto execute(String symbol) {
        return csvReaderService.getCryptoCurrencyList().stream()
                .filter(cryptoCurrency -> cryptoCurrency.getSymbol().equals(symbol))
                .findFirst()
                .map(this::buildResponse)
                .orElseThrow(() -> new CryptoCurrencyNotFoundException("Symbol does not exist"));
    }
    private CryptocurrencyInfoDto buildResponse(CryptoCurrency cryptoCurrency) {
        CryptocurrencyInfoDto dto = new CryptocurrencyInfoDto();

        dto.setSymbol(cryptoCurrency.getSymbol());
        dto.setNewestPrice(cryptoCurrency.getNewestPrice());
        dto.setOldestPrice(cryptoCurrency.getOldestPrice());
        dto.setMaxPrice(cryptoCurrency.getMaxPrice());
        dto.setMinPrice(cryptoCurrency.getMinPrice());

        return dto;
    }
}
