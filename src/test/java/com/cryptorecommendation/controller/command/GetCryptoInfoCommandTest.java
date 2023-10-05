package com.cryptorecommendation.controller.command;

import com.crypto.recommendation.generated.dto.CryptocurrencyInfoDto;
import com.crypto.recommendation.generated.dto.MarketStatusDto;
import com.cryptorecommendation.exceptions.CryptoCurrencyNotFoundException;
import com.cryptorecommendation.stubs.TestConfigApi;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class GetCryptoInfoCommandTest {

    TestConfigApi testConfigApi = new TestConfigApi();
    GetCryptoInfoCommand command = testConfigApi.getCryptoInfoCommand();

    @Test
    public void fail_invalid_symbol(){
        assertThatThrownBy(() -> command.execute("unknown"))
                .isInstanceOf(CryptoCurrencyNotFoundException.class)
                .hasMessage("Symbol does not exist");
    }

    @Test
    public void success_get_crypto_info(){
        var expectedInfo = new CryptocurrencyInfoDto();
        expectedInfo.setSymbol("BTC");
        expectedInfo.setMinPrice(BigDecimal.valueOf(46871.09));
        expectedInfo.setMaxPrice(BigDecimal.valueOf(47143.98));
        expectedInfo.setOldestPrice(BigDecimal.valueOf(46979.61));
        expectedInfo.setNewestPrice(BigDecimal.valueOf(46871.09));
        expectedInfo.setMarketStatus(MarketStatusDto.ACTIVE);

        assertThat(command.execute("BTC")).isEqualTo(expectedInfo);
    }
}
