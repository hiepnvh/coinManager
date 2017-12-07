package com.xxx.coinman;

import com.xxx.coinman.model.CoinBot;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		getAndTradeTest();

	}
	

	public static void getAndTradeTest() throws Exception{
		
		String BUY = "Buy";
		String SELL = "Sell";
		double feePercent = .25/100; //0.25% =  bittrex fee
		
			CoinBot cb = new CoinBot();
		
			double lastPrice = 100;//Gia mua vao lan gan day nhat
			double lastPriceGot = 107;//Gia vua get duoc lan truoc do
			double maxLost = 5.0/100;
			double minProfit =(double) 5/100;
			double currVolume = 100;
//			String coinCode = "BTC";
//			String apiKey = cb.getApiKey();
			
			double firstPrice = 90;
			boolean isBought = true;
			double _currMoney = 10000;
			cb.setYourMoney(_currMoney);

			//get
			double currPrice = 106;
			
			
			
			////////////////////buy
			//check if currPrice > lastPriceGot  && currPrice > lastPrice + limitBuy && is sold
			//if firstPrice null --> buy instantly if currPrice > lastPriceGot
			//first time
			if(firstPrice == 0){
				//buy then save new lastprice/firstprice...
				cb.setLastPriceGot(currPrice);
				
				
				cb.setFirstPrice(currPrice);
				cb.setFirstVolume(currVolume);
				cb.setLastPrice(currPrice);
				cb.setIsBought(true);
				cb.setAction(BUY);
				//update your money
				double yourMoney = 0;
				cb.setYourMoney(yourMoney);
				
			}else if(currPrice > lastPriceGot //else, buy when begin raising
//				&& currPrice*(1 + limitBuy) < lastPrice 
				&& !isBought){
				//buy then save new lastPrice
				Double currMoney = cb.getYourMoney();
				Double buyableVol = round(currMoney/currPrice, 5);
				Double volAfterFee = buyableVol*(1 - feePercent);
				
				cb.setVolume(volAfterFee);//cap nhat khoi luong sau khi da mua/ban ( da tru fee)
				cb.setLastPrice(currPrice);
				
				cb.setIsBought(true);
				cb.setAction(BUY);
				//update your money
				double yourMoney = currMoney - buyableVol*currPrice;
				cb.setYourMoney(yourMoney);
				
				cb.setLastPriceGot(currPrice);
				
			}else if(currPrice < lastPriceGot //losing 
					&& (currPrice*(1 + maxLost) < lastPrice || currPrice*(1 - minProfit) > lastPrice) //losing more than limitsell or you have benefit
					&& isBought){
				//sell then save new lastprrice
				Double fee = currVolume*currPrice*feePercent;
				Double currMoney = cb.getYourMoney();
				Double yourMoney = currMoney + (currVolume*currPrice - fee);
				
				cb.setLastPrice(currPrice);
				cb.setYourMoney(yourMoney);
//					cb.setVolume(round(currVolume*(1 - fee)*lastPrice/currPrice, 2));//cap nhat khoi luong sau khi da mua/ban
				cb.setIsBought(false);
				cb.setAction(SELL);
				cb.setLastPriceGot(currPrice);
				
			}else{
				//save last got price
				cb.setLastPriceGot(currPrice);
				cb.setAction(null);
			}
			
		
	}
		
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    double tmp = Math.floor(value);
	    return (double) tmp / factor;
	}
	
}
