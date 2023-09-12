package com.cryptorecommendation.exceptions;

public class CryptoCurrencyNotFoundException extends RuntimeException {

    public CryptoCurrencyNotFoundException(String message){
        super(message);
    }
}
