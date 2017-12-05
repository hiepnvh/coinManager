package com.xxx.coinman.service;

public interface BittrexService {
    Double getPrice(String coinCode, String refCode) throws Exception;
    String buy(String coinCode, String refCode, String apiKey, double vol, double rate) throws Exception;
    String sell(String coinCode, String refCode, String apiKey, double vol, double rate) throws Exception;
}
