package com.xxx.coinman;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.xxx.coinman.service.BittrexService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestService {
	
	@Autowired
	private BittrexService bitService;

	@Test
	public void main() throws Exception {
		// TODO Auto-generated method stub
		Double d = bitService.getPrice("LTC", "BTC");
		assertNotEquals(0.0, d);
	}

}
