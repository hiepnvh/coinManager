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
		Double bidPrice = 0.0;

		JSONObject jObj = new JSONObject(response.toString());
		if(jObj.get("success").toString().equals("true")){
			JSONArray resArr = (JSONArray) jObj.get("result");
			JSONObject res = (JSONObject) resArr.get(0);
//			Double high = res.getDouble("High");
//			Double low = res.getDouble("Low");
			bidPrice = res.getDouble("Bid");
		}
		
		return bidPrice;
	}
    
	@Override
	public String buy(String coinCode, String refCode, String apiKey, double vol, double rate) throws Exception{
		
		String url = "https://bittrex.com/api/v1.1/market/buylimit?apikey=" + apiKey + "&market=" + refCode + "-" + coinCode + "&quantity=" + vol + "&rate=" + rate;
		
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

		JSONObject jObj = new JSONObject(response.toString());
		String uuid = "";
		if(jObj.get("success").toString().equals("true")){
			JSONArray resArr = (JSONArray) jObj.get("result");
			JSONObject res = (JSONObject) resArr.get(0);
			uuid = res.getString("uuid");
		}
		
		return uuid;
	}
    
	@Override
	public String sell(String coinCode, String refCode, String apiKey, double vol, double rate) throws Exception{
String url = "https://bittrex.com/api/v1.1/market/selllimit?apikey=" + apiKey + "&market=" + refCode + "-" + coinCode + "&quantity=" + vol + "&rate=" + rate;
		
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

		JSONObject jObj = new JSONObject(response.toString());
		String uuid = "";
		if(jObj.get("success").toString().equals("true")){
			JSONArray resArr = (JSONArray) jObj.get("result");
			JSONObject res = (JSONObject) resArr.get(0);
			uuid = res.getString("uuid");
		}
		
		return uuid;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
}