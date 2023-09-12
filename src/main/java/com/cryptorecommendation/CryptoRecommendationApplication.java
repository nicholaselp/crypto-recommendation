package com.cryptorecommendation;

import com.cryptorecommendation.service.CSVReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication(scanBasePackages ={
		//TODO: fix packages....
		"com.cryptorecommendation",
		"com.crypto.recommendation.generated.api"
})
public class CryptoRecommendationApplication {

	private static final Logger logger = LoggerFactory.getLogger(CryptoRecommendationApplication.class);

	@Autowired
	CSVReaderService csvReaderService;

	public static void main(String[] args) { SpringApplication.run(CryptoRecommendationApplication.class, args); }

	@EventListener
	public void onContextReady(ApplicationStartedEvent event){
		logger.info("Starting to read csv files");
		csvReaderService.readCsvFiles();
	}

}
