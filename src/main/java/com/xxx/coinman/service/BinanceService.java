package com.xxx.coinman.service;

public interface BinanceService {
    Double getPrice(String coinCode, String refCode) throws Exception;
    Double getAccBalance(String coinCode, String apiKey, String secretKey) throws Exception;
    String getOpenOrder(String coinCode, String refCode, String apiKey, String secretKey) throws Exception;
    String buy(String coinCode, String refCode, String apiKey, String secretKey, double vol, double rate) throws Exception;
    String sell(String coinCode, String refCode, String apiKey, String secretKey, double vol, double rate) throws Exception;
}