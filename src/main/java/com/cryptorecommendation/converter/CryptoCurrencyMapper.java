package com.cryptorecommendation.converter;

import com.crypto.recommendation.generated.dto.CryptocurrencyInfoDto;
import com.cryptorecommendation.model.CryptoCurrency;

public class CryptoCurrencyMapper {

    public CryptocurrencyInfoDto toCryptoCurrencyInfoDto(CryptoCurrency cryptoCurrency) {
        CryptocurrencyInfoDto dto = new CryptocurrencyInfoDto();

        dto.setSymbol(cryptoCurrency.getSymbol());
        dto.setNewestPrice(cryptoCurrency.getNewestPrice());
        dto.setOldestPrice(cryptoCurrency.getOldestPrice());
        dto.setMaxPrice(cryptoCurrency.getMaxPrice());
        dto.setMinPrice(cryptoCurrency.getMinPrice());
        dto.setMarketStatus(MarketStatusConverter.toMarketStatusDto(cryptoCurrency.getMarketStatus()));

        return dto;
    }
}