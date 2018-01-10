package com.xxx.coinman;

import static org.junit.Assert.assertNotEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.xxx.coinman.service.BinanceService;
import com.xxx.coinman.service.BittrexService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestService {
	
	@Autowired
	private BittrexService bitService;
	
	@Autowired
	private BinanceService binanceService;

	String API_KEY = "aHKRiqeXJmm40nOqGh03evV1S6rB9Ut7M8A4OA2KCviTZcspKh0Z1ytQeRWjxS0S";
	String SECRET_KEY = "Q2kzxvoNmtRdXG5qED3v7xlFkYkFc0FpvfDRi6Wg02QHBd58myBdqnaNY1Uo2PO9";
	
	@Test
	@Ignore
	public void testBitrex() throws Exception {
		// TODO Auto-generated method stub
		Double d = bitService.getPrice("LTC", "BTC");
		assertNotEquals(0.0, d);
	}
	
	@Test
	public void testBinace() throws Exception {
		// TODO Auto-generated method stub
		
//		Double c = binanceService.getAccBalance("USDT");
//		Double d = binanceService.getPrice("LTC", "BTC");
//		String s = binanceService.getOpenOrder("BTC", "USDT", API_KEY, SECRET_KEY);
//		assertNotEquals(0.0, d);
		binanceService.buy("BCC", "USDT", API_KEY, SECRET_KEY, 0.01, 2500);
	}

}
