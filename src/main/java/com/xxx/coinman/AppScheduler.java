package com.xxx.coinman;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xxx.coinman.model.CoinBot;
import com.xxx.coinman.repository.CoinBotRepository;
import com.xxx.coinman.service.BittrexService;

@Component
public class AppScheduler {

	@Autowired
	private BittrexService bittrexService;
	
	@Autowired
	private CoinBotRepository coinBotRepo;
	
	String BUY = "Buy";
	String SELL = "Sell";
	
	//run every 5 mins
	@Scheduled(fixedRate = 5*60*1000)
	public void getAndTrade() throws Exception{
		String refCode = "USDT"; //chi quy doi ra usd de tinh
		double feePercent = .25/100; //0.25% =  bittrex fee
		List<CoinBot> coinBots = coinBotRepo.findByActive(true);
		for(CoinBot cb : coinBots){
			Double lastPrice = cb.getLastPrice();//Gia mua vao lan gan day nhat
			Double lastPriceGot = cb.getLastPriceGot();//Gia vua get duoc lan truoc do
			Double limitBuy = (double) (cb.getBuyLimit()/100);
			Double limitSell = (double) (cb.getSellLimit()/100);
			Double currVolume = cb.getVolume();
			String coinCode = cb.getCoinCode();
			boolean isBought = cb.getIsBought();

			//get
			Double currPrice = bittrexService.getPrice(coinCode, refCode);
			
			////////////////////buy
			//check if currPrice > lastPriceGot  && currPrice > lastPrice + limitBuy && is sold
			//if firstPrice null --> buy instantly if currPrice > lastPriceGot
			//first time
			if(cb.getFirstPrice() == null){
				//buy then save new lastprice/firstprice...
				cb.setFirstPrice(currPrice);
				cb.setFirstVolume(currVolume);
				cb.setLastPrice(currPrice);
				cb.setLastPriceGot(currPrice);
				cb.setIsBought(true);
				cb.setAction(BUY);
				//update your money
				double yourMoney = 0;
				cb.setYourMoney(yourMoney);
			}else if(currPrice*(1 - limitBuy) > lastPriceGot //else, buy when begin raising
//				&& currPrice*(1 + limitBuy) < lastPrice 
				&& !isBought){
				//buy then save new lastPrice
				Double currMoney = cb.getYourMoney();
				Double buyableVol = round(currMoney/currPrice, 2);
				Double fee = buyableVol*currPrice*feePercent;
				cb.setVolume(round(buyableVol - fee/currPrice, 2));//cap nhat khoi luong sau khi da mua/ban ( da tru fee)
				cb.setLastPrice(currPrice);
				cb.setLastPriceGot(currPrice);
				cb.setIsBought(true);
				cb.setAction(BUY);
				//update your money
				double yourMoney = cb.getYourMoney() - round(buyableVol, 2)*currPrice;
				cb.setYourMoney(yourMoney);
				
			}else if(currPrice < lastPriceGot //losing 
					&& (lastPrice*(1 + limitSell) < currPrice || currPrice*(1 - feePercent) > lastPrice) //losing more than limitsell or you have benefit
					&& isBought){
				//sell then save new lastprrice
				cb.setLastPrice(currPrice);
				cb.setLastPriceGot(currPrice);
				Double fee = currVolume*currPrice*feePercent;
				Double currMoney = cb.getYourMoney();
				Double yourMoney = currMoney + (currVolume*currPrice - fee);
				cb.setYourMoney(yourMoney);
//				cb.setVolume(round(currVolume*(1 - fee)*lastPrice/currPrice, 2));//cap nhat khoi luong sau khi da mua/ban
				cb.setIsBought(false);
				cb.setAction(SELL);
			}else{
				//save last got price
				cb.setLastPriceGot(currPrice);
				cb.setAction(null);
			}
			
			//save to db
			coinBotRepo.save(cb);
			
		}
		
		System.out.println("test abc def");
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

}
