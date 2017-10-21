package com.xxx.coinman.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class BittrexServiceImpl implements BittrexService {
	@Override
	public Double getPrice(String coinCode, String refCode) throws Exception{
		String url = "https://bittrex.com/api/v1.1/public/getmarketsummary?market=" + refCode + "-" + coinCode;
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		Double average = 0.0;

		JSONObject jObj = new JSONObject(response.toString());
		if(jObj.get("success").toString().equals("true")){
			JSONArray resArr = (JSONArray) jObj.get("result");
			JSONObject res = (JSONObject) resArr.get(0);
			String high = res.getString("High");
			String low = res.getString("Low");
			average = (Double.parseDouble(low) + Double.parseDouble(high))/2;
		}
		
		return average;
	}
    
	@Override
	public String buy(String coinCode, String refCode){
		return "";
	}
    
	@Override
	public String sell(String coinCode, String refCode){
		return "";
	}
}
