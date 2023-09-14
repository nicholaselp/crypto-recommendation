package com.cryptorecommendation.controller;

import com.cryptorecommendation.config.CryptoRecommendationConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(CryptoRecommendationConfig.class)
public class SpringTestConfig {
}
