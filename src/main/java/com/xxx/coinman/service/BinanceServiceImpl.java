package com.xxx.coinman.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.webcerebrium.binance.api.BinanceApi;
import com.webcerebrium.binance.api.BinanceApiException;
import com.webcerebrium.binance.datatype.BinanceOrder;
import com.webcerebrium.binance.datatype.BinanceOrderPlacement;
import com.webcerebrium.binance.datatype.BinanceOrderSide;
import com.webcerebrium.binance.datatype.BinanceSymbol;

@Service
public class BinanceServiceImpl implements BinanceService {
	
	@Override
	public Double getPrice(String coinCode, String refCode) throws Exception{
		
		BinanceApi binanceApi = new BinanceApi();
		String symbol = coinCode.toUpperCase() + refCode.toUpperCase();
		
		BinanceSymbol bSymbol;
		try {
			bSymbol = new BinanceSymbol(symbol);
//			binanceApi.balances();
			Double lastPrice = binanceApi.ticker24hr(bSymbol).get("lastPrice").getAsDouble() ;
			System.out.println(lastPrice);
			return lastPrice;
		} catch (BinanceApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0.0;
		}
		
	}
	
	@Override
	public Double getAccBalance(String coinCode, String apiKey, String secretKey) throws Exception{
		try {
			BinanceApi binanceApi = new BinanceApi(apiKey, secretKey);
			JsonArray balanceArr = binanceApi.balances();
			for(int i = 0 ; i < balanceArr.size(); i++){
				JsonObject bal = balanceArr.get(i).getAsJsonObject();
				if(bal.get("asset").getAsString().equalsIgnoreCase(coinCode))
					return bal.get("free").getAsDouble();
			}
			return 0.0;
		} catch (BinanceApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0.0;
		}
		
	}
	
    
	@Override
	public String buy(String coinCode, String refCode, String apiKey, String secretKey, double vol, double price) throws Exception{
		
		try {
			BinanceApi binanceApi = new BinanceApi(apiKey, secretKey);
			String symbol = coinCode.toUpperCase() + refCode.toUpperCase();
			
			BinanceSymbol bSymbol = new BinanceSymbol(symbol);
			//buy
			BinanceOrderSide side = BinanceOrderSide.BUY;
			BinanceOrderPlacement orderPlacement = new  BinanceOrderPlacement(bSymbol, side);
			orderPlacement.setPrice(BigDecimal.valueOf(price));
			orderPlacement.setQuantity(BigDecimal.valueOf(vol));
//			binanceApi.createOrder(orderPlacement);
			JsonObject res = binanceApi.createOrder(orderPlacement);
			return res.get("status").getAsString();
		} catch (BinanceApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
    
	@Override
	public String sell(String coinCode, String refCode, String apiKey, String secretKey, double vol, double price) throws Exception{
		try {
			BinanceApi binanceApi = new BinanceApi(apiKey, secretKey);
			String symbol = coinCode.toUpperCase() + refCode.toUpperCase();
			
			BinanceSymbol bSymbol = new BinanceSymbol(symbol);
			//buy
			BinanceOrderSide side = BinanceOrderSide.SELL;
			BinanceOrderPlacement orderPlacement = new  BinanceOrderPlacement(bSymbol, side);
			orderPlacement.setPrice(BigDecimal.valueOf(price));
			orderPlacement.setQuantity(BigDecimal.valueOf(vol));
//			binanceApi.createOrder(orderPlacement);
			JsonObject res = binanceApi.createOrder(orderPlacement);
			return res.get("status").getAsString();
		} catch (BinanceApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	@Override
	public String getOpenOrder(String coinCode, String refCode, String apiKey, String secretKey) throws Exception{
		try {
			BinanceApi binanceApi = new BinanceApi(apiKey, secretKey);
			String symbol = coinCode.toUpperCase() + refCode.toUpperCase();
			
			BinanceSymbol bSymbol = new BinanceSymbol(symbol);
			//check if order completed
			List<BinanceOrder> openOrders = binanceApi.openOrders(bSymbol);
			if(openOrders.size() == 0)
				return "Filled";
			else
				return "Ordering";
		} catch (BinanceApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "exception";
		}
	}
}
