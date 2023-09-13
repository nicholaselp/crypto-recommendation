package com.cryptorecommendation.controller.command;

import com.crypto.recommendation.generated.dto.NormalizedRangeDto;
import com.cryptorecommendation.exceptions.ValidationException;
import com.cryptorecommendation.stubs.TestConfigApi;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GetHighestNormalizedCommandTest {

    TestConfigApi testConfigApi = new TestConfigApi();
    GetHighestNormalizedCommand command = testConfigApi.getHighestNormalizedCommand();
    @Test
    public void fail_incorrect_date_format(){
        assertThatThrownBy(() -> command.execute("aaa"))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Incorrect date format, Please provide date with YYYY-MM-DD format");
    }

    @Test
    public void fail_no_data_found_for_date(){
        assertThatThrownBy(() -> command.execute("2022-01-02"))
                .isInstanceOf(ValidationException.class)
                .hasMessage("No data found for: 2022-01-02");
    }

    @Test
    public void get_highest_normalized_range(){
        var expected = new NormalizedRangeDto();
        expected.setNormalizedRange(BigDecimal.valueOf(0.0192));
        expected.setSymbol("XRP");

        assertThat(command.execute("2022-01-01")).isEqualTo(expected);
    }

}
