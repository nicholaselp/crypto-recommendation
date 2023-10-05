package com.cryptorecommendation.controller.command;

import com.crypto.recommendation.generated.dto.CryptocurrencyInfoDto;
import com.cryptorecommendation.converter.CryptoCurrencyMapper;
import com.cryptorecommendation.exceptions.CryptoCurrencyNotFoundException;
import com.cryptorecommendation.service.CSVReaderService;

import static java.util.Objects.requireNonNull;

public class GetCryptoInfoCommand implements Command<String, CryptocurrencyInfoDto> {

    private final CSVReaderService csvReaderService;
    private final CryptoCurrencyMapper cryptoCurrencyMapper;

    public GetCryptoInfoCommand(CSVReaderService csvReaderService, CryptoCurrencyMapper cryptoCurrencyMapper){
        this.csvReaderService = requireNonNull(csvReaderService, "CSVReaderService is missing");
        this.cryptoCurrencyMapper = requireNonNull(cryptoCurrencyMapper, "cryptoCurrencyMapper is missing");
    }

    @Override
    public CryptocurrencyInfoDto execute(String symbol) {
        return csvReaderService.getCryptoCurrencyList().stream()
                .filter(cryptoCurrency -> cryptoCurrency.getSymbol().equals(symbol))
                .findFirst()
                .map(cryptoCurrencyMapper::toCryptoCurrencyInfoDto)
                .orElseThrow(() -> new CryptoCurrencyNotFoundException("Symbol does not exist"));
    }
}
