package com.cryptorecommendation.config;

import com.cryptorecommendation.controller.command.GetAllCryptosCommand;
import com.cryptorecommendation.controller.command.GetCryptoInfoCommand;
import com.cryptorecommendation.controller.command.GetHighestNormalizedCommand;
import com.cryptorecommendation.controller.delegate.CryptoRecommendationApiControllerDelegate;
import com.cryptorecommendation.service.CSVReaderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CryptoRecommendationConfig {

    @Bean
    CryptoRecommendationApiControllerDelegate cryptoRecommendationApiControllerDelegate(GetAllCryptosCommand getAllCryptosCommand,
                                                                                        GetCryptoInfoCommand getCryptoInfoCommand,
                                                                                        GetHighestNormalizedCommand getHighestNormalizedCommand){
        return new CryptoRecommendationApiControllerDelegate(getAllCryptosCommand, getCryptoInfoCommand, getHighestNormalizedCommand);
    }

    @Bean
    GetAllCryptosCommand getAllCryptosCommand(CSVReaderService csvReaderService){
        return new GetAllCryptosCommand(csvReaderService);
    }

    @Bean
    GetHighestNormalizedCommand getHighestNormalizedCommand(CSVReaderService csvReaderService){
        return new GetHighestNormalizedCommand(csvReaderService);
    }

    @Bean
    GetCryptoInfoCommand getCryptoInfoCommand(CSVReaderService csvReaderService){
        return new GetCryptoInfoCommand(csvReaderService);
    }

    @Bean
    CSVReaderService csvReaderService(@Value("${crypto-recommendation.csv-path}") String csvPath){
        return new CSVReaderService(csvPath);
    }
}
