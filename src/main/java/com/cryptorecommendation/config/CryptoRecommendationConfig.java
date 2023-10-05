package com.cryptorecommendation.config;

import com.cryptorecommendation.controller.command.GetAllCryptosCommand;
import com.cryptorecommendation.controller.command.GetByMarketStatusCommand;
import com.cryptorecommendation.controller.command.GetCryptoInfoCommand;
import com.cryptorecommendation.controller.command.GetHighestNormalizedCommand;
import com.cryptorecommendation.controller.delegate.CryptoRecommendationApiControllerDelegate;
import com.cryptorecommendation.converter.CryptoCurrencyMapper;
import com.cryptorecommendation.service.CSVReaderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        WebConf.class
})
public class CryptoRecommendationConfig {

    @Bean
    CryptoRecommendationApiControllerDelegate cryptoRecommendationApiControllerDelegate(GetAllCryptosCommand getAllCryptosCommand,
                                                                                        GetCryptoInfoCommand getCryptoInfoCommand,
                                                                                        GetHighestNormalizedCommand getHighestNormalizedCommand,
                                                                                        GetByMarketStatusCommand getByMarketStatusCommand){
        return new CryptoRecommendationApiControllerDelegate(getAllCryptosCommand, getCryptoInfoCommand, getHighestNormalizedCommand,
                getByMarketStatusCommand);
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
    GetCryptoInfoCommand getCryptoInfoCommand(CSVReaderService csvReaderService, CryptoCurrencyMapper cryptoCurrencyMapper){
        return new GetCryptoInfoCommand(csvReaderService, cryptoCurrencyMapper);
    }

    @Bean
    CryptoCurrencyMapper cryptoCurrencyMapper(){
        return new CryptoCurrencyMapper();
    }

    @Bean
    CSVReaderService csvReaderService(@Value("${crypto-recommendation.csv-path}") String csvPath){
        return new CSVReaderService(csvPath);
    }
}
