package com.cryptorecommendation.controller.command;

import com.crypto.recommendation.generated.dto.NormalizedRangeDto;
import com.cryptorecommendation.stubs.TestConfigApi;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetAllCryptosCommandTest {

    private final TestConfigApi testConfigApi = new TestConfigApi();
    private final GetAllCryptosCommand command = testConfigApi.getAllCryptosCommand();

    @Test
    public void get_all_cryptos(){
        NormalizedRangeDto bitcoinDto = new NormalizedRangeDto();
        bitcoinDto.setSymbol("BTC");
        bitcoinDto.setNormalizedRange(BigDecimal.valueOf(0.0058));

        NormalizedRangeDto xrpDto = new NormalizedRangeDto();
        xrpDto.setSymbol("XRP");
        xrpDto.setNormalizedRange(BigDecimal.valueOf(0.0193));

        assertThat(command.execute(null)).isNotEmpty().hasSize(2)
                .containsExactlyInAnyOrderElementsOf(List.of(bitcoinDto, xrpDto));
    }
}
