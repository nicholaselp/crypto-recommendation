package com.cryptorecommendation.converter;

import com.crypto.recommendation.generated.dto.MarketStatusDto;
import com.cryptorecommendation.exceptions.UnsupportedEnumValueException;
import com.cryptorecommendation.model.MarketStatus;

public class MarketStatusConverter {

    public static MarketStatusDto toMarketStatusDto(MarketStatus marketStatus){
        return switch (marketStatus) {
            case ACTIVE -> MarketStatusDto.ACTIVE;
            case INACTIVE -> MarketStatusDto.INACTIVE;
            case SUSPENDED -> MarketStatusDto.SUSPENDED;
            default -> throw new UnsupportedEnumValueException(marketStatus.getValue());
        };
    }

    public static MarketStatus toMarketStatus(MarketStatusDto marketStatusDto){
        return switch (marketStatusDto) {
            case ACTIVE -> MarketStatus.ACTIVE;
            case INACTIVE -> MarketStatus.INACTIVE;
            case SUSPENDED -> MarketStatus.SUSPENDED;
            default -> throw new UnsupportedEnumValueException(marketStatusDto.getValue());
        };
    }

}
