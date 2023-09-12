package com.cryptorecommendation.controller.delegate;

import com.crypto.recommendation.generated.api.CryptoRecommendationApiDelegate;
import com.crypto.recommendation.generated.dto.CryptocurrencyInfoDto;
import com.crypto.recommendation.generated.dto.NormalizedRangeDto;
import com.cryptorecommendation.controller.MainController;
import com.cryptorecommendation.controller.command.GetAllCryptosCommand;
import com.cryptorecommendation.controller.command.GetCryptoInfoCommand;
import com.cryptorecommendation.controller.command.GetHighestNormalizedCommand;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class CryptoRecommendationApiControllerDelegate extends MainController implements CryptoRecommendationApiDelegate {

    private final GetAllCryptosCommand getAllCryptosCommand;
    private final GetCryptoInfoCommand getCryptoInfoCommand;
    private final GetHighestNormalizedCommand getHighestNormalizedCommand;

    public CryptoRecommendationApiControllerDelegate(GetAllCryptosCommand getAllCryptosCommand, GetCryptoInfoCommand getCryptoInfoCommand,
                                                     GetHighestNormalizedCommand getHighestNormalizedCommand){
        this.getAllCryptosCommand = requireNonNull(getAllCryptosCommand, "GetAllCryptosCommand is missing");
        this.getCryptoInfoCommand = requireNonNull(getCryptoInfoCommand, "GetCryptoInfoCommand is missing");
        this.getHighestNormalizedCommand = requireNonNull(getHighestNormalizedCommand, "GetHighestNormalizedCommand is missing");
    }

    @Override
    public ResponseEntity<List<NormalizedRangeDto>> getAllCryptosSorted() {
        return (ResponseEntity<List<NormalizedRangeDto>>) execute(getAllCryptosCommand, null);
    }

    @Override
    public ResponseEntity<CryptocurrencyInfoDto> getCryptoInfo(String cryptoSymbol){
        return (ResponseEntity<CryptocurrencyInfoDto>) execute(getCryptoInfoCommand, cryptoSymbol);
    }

    @Override
    public ResponseEntity<NormalizedRangeDto> getHighestNormalizedByDay(String date){
        return (ResponseEntity<NormalizedRangeDto>) execute(getHighestNormalizedCommand, date);
    }
}
