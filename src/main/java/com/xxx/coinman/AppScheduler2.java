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
import com.xxx.coinman.service.BinanceService;
import com.xxx.coinman.service.UserService;

@Component
public class AppScheduler2 {

	@Autowired
	private BinanceService binanceService;
	
	@Autowired
	private CoinBotRepository coinBotRepo;
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private MailSender mailSender;
	
	Logger LOGGER = Logger.getLogger(AppScheduler2.class);
	
	String BUY = "Buy";
	String SELL = "Sell";
	
	//run every 5 mins
	@Scheduled(fixedRate = 1*60*1000)
	public void getAndTrade() throws Exception{
		double feePercent = .1/100; //0.1% =  binance fee
		List<CoinBot> coinBots = coinBotRepo.findByActive(true);
		for(CoinBot cb : coinBots){
			Double lastPrice = cb.getLastPrice();//Gia mua vao lan gan day nhat
			Double lastPriceGot = cb.getLastPriceGot();//Gia vua get duoc lan truoc do
			Double maxLost = (double) (cb.getMaxLost()*0.01);
			Double minProfit = (double) (cb.getMinProfit()*0.01);
			Double currVolume = cb.getVolume();
			String coinCode = cb.getCoinCode();
			String refCode = cb.getRefCode();
			String apiKey = cb.getApiKey();
			String secretKey = cb.getSecretKey();
			boolean isBought = cb.getIsBought();
			
			//check if this coin is ordering
			String isOrdering = binanceService.getOpenOrder(coinCode, refCode, apiKey, secretKey);
			if(!isOrdering.equalsIgnoreCase("Filled"))
				continue; //do nothing, next to other coins

			//get
			Double currPrice = binanceService.getPrice(coinCode, refCode);
			
			//Logger
			LOGGER.info(coinCode + " maxlost: " + maxLost + " minprofit: " + minProfit + " currPrice " + currPrice + " lastprice " + lastPrice + " lastpricegot " + lastPriceGot);
			
			if(currPrice == 0)
				continue;
			
			////////////////////buy
			//check if currPrice > lastPriceGot  && currPrice > lastPrice + limitBuy && is sold
			//if firstPrice null --> buy instantly if currPrice > lastPriceGot
			//first time
			if(cb.getFirstPrice() == 0){
				//buy then save new lastprice/firstprice...
				cb.setLastPriceGot(currPrice);
				
				//place order to bittrex
				Double orderPrice = 0.0;
				if(lastPrice > 0)
					orderPrice = lastPrice;
				else
					orderPrice = currPrice;
				String tradeRes = binanceService.buy(coinCode, refCode, apiKey, secretKey, currVolume, orderPrice);
				if(tradeRes != ""){
					//success
					cb.setFirstPrice(orderPrice);
					cb.setFirstVolume(currVolume);
					cb.setLastPrice(orderPrice);
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
				Double buyableVol = round(currMoney/currPrice, cb.getRoundTo());
				Double volAfterFee = buyableVol*(1 - feePercent);
				
				//place order to bittrex
				String tradeRes = binanceService.buy(coinCode, refCode, apiKey, secretKey, buyableVol, currPrice);
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
				String tradeRes = binanceService.sell(coinCode, refCode, apiKey, secretKey, currVolume, currPrice);
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
				User user = cb.getUser();
				
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
		
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    double tmp = Math.floor(value);
	    return (double) tmp / factor;
	}

}
