package com.cryptorecommendation.stubs;

import com.cryptorecommendation.controller.command.GetAllCryptosCommand;
import com.cryptorecommendation.controller.command.GetCryptoInfoCommand;
import com.cryptorecommendation.controller.command.GetHighestNormalizedCommand;
import com.cryptorecommendation.service.CSVReaderService;

public class TestConfigApi {

    private final CSVReaderService csvReaderService;
    private final GetAllCryptosCommand getAllCryptosCommand;
    private final GetCryptoInfoCommand getCryptoInfoCommand;
    private final GetHighestNormalizedCommand getHighestNormalizedCommand;

    public TestConfigApi(){
        this.csvReaderService = new CSVReaderService("src/test/resources/csv-upload");
        csvReaderService.readCsvFiles();

        this.getAllCryptosCommand = new GetAllCryptosCommand(csvReaderService);
        this.getCryptoInfoCommand = new GetCryptoInfoCommand(csvReaderService);
        this.getHighestNormalizedCommand = new GetHighestNormalizedCommand(csvReaderService);
    }

    public CSVReaderService getCsvReaderService(){ return csvReaderService; }
    public GetAllCryptosCommand getAllCryptosCommand(){ return getAllCryptosCommand; }
    public GetCryptoInfoCommand getCryptoInfoCommand(){ return getCryptoInfoCommand; }
    public GetHighestNormalizedCommand getHighestNormalizedCommand(){ return getHighestNormalizedCommand; }
}
