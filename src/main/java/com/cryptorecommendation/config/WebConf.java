package com.cryptorecommendation.config;

import com.crypto.recommendation.generated.dto.MarketStatusDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

/**
 * Add custom converters to allow enum values to be part of query params in openAPI generated APIs
 * */
@Configuration
public class WebConf {

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("HEAD", "GET", "POSST", "PUT", "DELETE", "PATCH");
            }

            @Override
            public void configurePathMatch(PathMatchConfigurer configurer){
                final UrlPathHelper urlPathHelper = new UrlPathHelper();
                urlPathHelper.setRemoveSemicolonContent(false);
                configurer.setUrlPathHelper(urlPathHelper);
            }

            @Override
            public void addFormatters(FormatterRegistry registry){
                registry.addConverter(MarketStatusDto.Converter.INSTANCE);
            }
        };
    }
}
