package com.cryptorecommendation.controller.command;

import com.crypto.recommendation.generated.dto.CryptocurrencyInfoDto;
import com.crypto.recommendation.generated.dto.MarketStatusDto;
import com.cryptorecommendation.converter.CryptoCurrencyMapper;
import com.cryptorecommendation.converter.MarketStatusConverter;
import com.cryptorecommendation.exceptions.CryptoCurrencyNotFoundException;
import com.cryptorecommendation.service.CSVReaderService;

import static java.util.Objects.requireNonNull;

public class GetByMarketStatusCommand implements Command<MarketStatusDto, CryptocurrencyInfoDto> {

    private final CSVReaderService csvReaderService;
    private final CryptoCurrencyMapper cryptoCurrencyMapper;

    public GetByMarketStatusCommand(CSVReaderService csvReaderService, CryptoCurrencyMapper cryptoCurrencyMapper){
        this.csvReaderService = requireNonNull(csvReaderService, "CSVReaderService is missing");
        this.cryptoCurrencyMapper = requireNonNull(cryptoCurrencyMapper, "CryptoCurrencyMapper is missing");
    }

    @Override
    public CryptocurrencyInfoDto execute(MarketStatusDto marketStatusDto) {
        return csvReaderService.getCryptoCurrencyList().stream()
                .filter(cryptoCurrency -> cryptoCurrency.getMarketStatus() == MarketStatusConverter.toMarketStatus(marketStatusDto))
                .findFirst()
                .map(cryptoCurrencyMapper::toCryptoCurrencyInfoDto)
                .orElseThrow(() -> new CryptoCurrencyNotFoundException("No Cryptocurrency found with market status: " + marketStatusDto.getValue()));
    }
}