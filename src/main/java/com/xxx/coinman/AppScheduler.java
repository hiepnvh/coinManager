package com.xxx.coinman;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.xxx.coinman.model.CoinBot;
import com.xxx.coinman.model.User;
import com.xxx.coinman.repository.CoinBotRepository;
import com.xxx.coinman.service.BittrexService;
import com.xxx.coinman.service.UserService;

@Component
public class AppScheduler {

	@Autowired
	private BittrexService bittrexService;
	
	@Autowired
	private CoinBotRepository coinBotRepo;
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private MailSender mailSender;
	
	Logger LOGGER = Logger.getLogger(AppScheduler.class);
	
	String BUY = "Buy";
	String SELL = "Sell";
	
	//run every 30 mins
//	@Scheduled(fixedRate = 30*60*1000)
	public void getAndTrade() throws Exception{
		String refCode = "USDT"; //chi quy doi ra usd de tinh
		double feePercent = .25/100; //0.25% =  bittrex fee
		List<CoinBot> coinBots = coinBotRepo.findByActive(true);
		for(CoinBot cb : coinBots){
			Double lastPrice = cb.getLastPrice();//Gia mua vao lan gan day nhat
			Double lastPriceGot = cb.getLastPriceGot();//Gia vua get duoc lan truoc do
			Double maxLost = (double) (cb.getMaxLost()/100);
			Double minProfit = (double) (cb.getMinProfit()/100);
			Double currVolume = cb.getVolume();
			String coinCode = cb.getCoinCode();
			String apiKey = cb.getApiKey();
			
			boolean isBought = cb.getIsBought();

			//get
			Double currPrice = bittrexService.getPrice(coinCode, refCode);
			
			//Logger
			LOGGER.info(coinCode + " maxlost: " + maxLost + " minprofit: " + minProfit + " currPrice " + currPrice + " lastprice " + lastPrice + " lastpricegot " + lastPriceGot);
			
			if(currPrice == 0)
				continue;
			
			////////////////////buy
			//check if currPrice > lastPriceGot  && currPrice > lastPrice + limitBuy && is sold
			//if firstPrice null --> buy instantly if currPrice > lastPriceGot
			//first time
			if(cb.getFirstPrice() == null){
				//buy then save new lastprice/firstprice...
				cb.setLastPriceGot(currPrice);
				
				//place order to bittrex
				String tradeRes = bittrexService.buy(coinCode, refCode, apiKey, currVolume, currPrice);
				if(tradeRes != ""){
					//success
					cb.setFirstPrice(currPrice);
					cb.setFirstVolume(currVolume);
					cb.setLastPrice(currPrice);
					cb.setIsBought(true);
					cb.setAction(BUY);
					//update your money
					double yourMoney = 0;
					cb.setYourMoney(yourMoney);
				}
				
			}else if(currPrice > lastPriceGot //else, buy when begin raising
//				&& currPrice*(1 + limitBuy) < lastPrice 
				&& !isBought){
				//buy then save new lastPrice
				Double currMoney = cb.getYourMoney();
				Double buyableVol = round(currMoney/currPrice, 5);
				Double volAfterFee = buyableVol*(1 - feePercent);
				
				//place order to bittrex
				String tradeRes = bittrexService.buy(coinCode, refCode, apiKey, currVolume, currPrice);
				if(tradeRes != ""){
					//success
					cb.setVolume(volAfterFee);//cap nhat khoi luong sau khi da mua/ban ( da tru fee)
					cb.setLastPrice(currPrice);
					
					cb.setIsBought(true);
					cb.setAction(BUY);
					//update your money
					double yourMoney = currMoney - buyableVol*currPrice;
					cb.setYourMoney(yourMoney);
				}
				
				cb.setLastPriceGot(currPrice);
				
			}else if(currPrice < lastPriceGot //losing 
					&& (currPrice*(1 + maxLost) < lastPrice || currPrice*(1 - minProfit) > lastPrice) //losing more than limitsell or you have benefit
					&& isBought){
				//sell then save new lastprrice
				Double fee = currVolume*currPrice*feePercent;
				Double currMoney = cb.getYourMoney();
				Double yourMoney = currMoney + (currVolume*currPrice - fee);
				
				//place order to bittrex
				String tradeRes = bittrexService.sell(coinCode, refCode, apiKey, currVolume, currPrice);
				if(tradeRes != ""){
					//success
					cb.setLastPrice(currPrice);
					cb.setYourMoney(yourMoney);
//					cb.setVolume(round(currVolume*(1 - fee)*lastPrice/currPrice, 2));//cap nhat khoi luong sau khi da mua/ban
					cb.setIsBought(false);
					cb.setAction(SELL);
				}
				cb.setLastPriceGot(currPrice);
				
			}else{
				//save last got price
				cb.setLastPriceGot(currPrice);
				cb.setAction(null);
			}
			
			if(currPrice*(1 + maxLost) < lastPrice && isBought){ //losing more than limitsell
				//send mail to subscriber
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				User user = userService.findByUsername(auth.getName());
				
				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setFrom("hiepnvh@gmail.com");
				msg.setTo(user.getEmail());
				msg.setText("Sold " + cb.getCoinCode() + " cause of over lost.");
				msg.setSubject("Trading msg");
				
				LOGGER.info("Sending email to " +user.getEmail());
				
				try {
		            mailSender.send(msg);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			}
			
			//save to db
			coinBotRepo.save(cb);
			
		}
		
		System.out.println("run abc def");
	}
	
	//just test, not trade actually
	//run every 30 mins
	@Scheduled(fixedRate = 30*60*1000)
	public void getAndTradeTest() throws Exception{
		String refCode = "USDT"; //chi quy doi ra usd de tinh
		double feePercent = .25/100; //0.25% =  bittrex fee
		List<CoinBot> coinBots = coinBotRepo.findByActive(true);
		for(CoinBot cb : coinBots){
			Double lastPrice = cb.getLastPrice();//Gia mua vao lan gan day nhat
			Double lastPriceGot = cb.getLastPriceGot();//Gia vua get duoc lan truoc do
			Double maxLost = (double) (cb.getMaxLost()*0.01);
			Double minProfit = (double) (cb.getMinProfit()*0.01);
			Double currVolume = cb.getVolume();
			String coinCode = cb.getCoinCode();
//			String apiKey = cb.getApiKey();
			
			boolean isBought = cb.getIsBought();

			//get
			Double currPrice = bittrexService.getPrice(coinCode, refCode);
			
			//Logger
			LOGGER.info(coinCode + " maxlost: " + maxLost + " minprofit: " + minProfit + " currPrice " + currPrice + " lastprice " + lastPrice + " lastpricegot " + lastPriceGot);
			
			if(currPrice == 0)
				continue;
			
			////////////////////buy
			//check if currPrice > lastPriceGot  && currPrice > lastPrice + limitBuy && is sold
			//if firstPrice null --> buy instantly if currPrice > lastPriceGot
			//first time
			if(cb.getFirstPrice() == null){
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
			
			if(currPrice*(1 + maxLost) < lastPrice && isBought){ //losing more than limitsell
				//send mail to subscriber
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				User user = userService.findByUsername(auth.getName());
				
				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setFrom("hiepnvh@gmail.com");
				msg.setTo(user.getEmail());
				msg.setText("Sold " + cb.getCoinCode() + " cause of over lost.");
				msg.setSubject("Trading msg");
				
				LOGGER.info("Sending email to " +user.getEmail());
				
				try {
		            mailSender.send(msg);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
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
	    double tmp = Math.floor(value);
	    return (double) tmp / factor;
	}

}
