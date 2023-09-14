package com.cryptorecommendation.controller;

import com.crypto.recommendation.generated.api.CryptoRecommendationApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CryptoRecommendationApi.class)
@ContextConfiguration(classes = SpringTestConfig.class)
public class CryptoRecommendationSpringTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void get_crypto_info() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/XRP/info"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"symbol\":\"XRP\",\"oldestPrice\":0.8298,\"newestPrice\":0.5867,\"minPrice\":0.5616,\"maxPrice\":0.8458}"));
    }

    @Test
    public void get_all_sorted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/all-sorted"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json("[{\"symbol\":\"BTC\",\"normalizedRange\":0.4341},{\"symbol\":\"LTC\",\"normalizedRange\":0.4652},{\"symbol\":\"DOGE\",\"normalizedRange\":0.5047},{\"symbol\":\"XRP\",\"normalizedRange\":0.5061},{\"symbol\":\"ETH\",\"normalizedRange\":0.6384}]"));
    }

    @Test
    public void get_highest_normalized_range() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/highest-range")
                        .param("date", "2022-01-07"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"symbol\":\"DOGE\",\"normalizedRange\":0.0369}"));
    }

}
