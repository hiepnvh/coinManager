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
	
	@Scheduled(fixedRate = 5*60*1000)
	//run every 2 mins
	public void getAndTrade() throws Exception{
		String refCode = "BTC"; //chi quy doi ra btc de tinh
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
				cb.setLastPrice(currPrice);
				cb.setLastPriceGot(currPrice);
				cb.setIsBought(true);
			}else if(currPrice*(1 - limitBuy) > lastPriceGot //raising
				&& currPrice*(1 + limitBuy) < lastPrice 
				&& !isBought	){
				//buy then save new lastPrice
				cb.setVolume(round(currVolume*lastPrice/currPrice, 2));//cap nhat khoi luong sau khi da mua/ban
				cb.setLastPrice(currPrice);
				cb.setLastPriceGot(currPrice);
				cb.setIsBought(true);
				
			}else if(currPrice*(1 + limitSell) < lastPriceGot //losing 
					&& currPrice*(1 + limitSell) < lastPrice 
					&& isBought){
				//sell then save new lastprrice
				cb.setLastPrice(currPrice);
				cb.setLastPriceGot(currPrice);
				cb.setVolume(round(currVolume*lastPrice/currPrice, 2));//cap nhat khoi luong sau khi da mua/ban
				cb.setIsBought(false);
			}else{
				//save last got price
				cb.setLastPriceGot(currPrice);
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
