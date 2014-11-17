package com.westlakestudent.camera.watermark.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class WeatherService {
	public final String TAG = this.getClass().getName();
	
	private Handler mHandler; 
	private String mCityID;
	private String mTemp;
	private String mWeather;
	
	public WeatherService(Handler handler, String cityID) {
		super();
		mHandler = handler;
		mCityID = cityID;
	}	
	
	public void setCityID(String cityID) {
		mCityID = cityID;
	}
	
	public void startService() {		
		new GetWeatherThread("GetWeatherThread").start();
	}
	
	public void startService(String cityID) {
		mCityID = cityID;
		new GetWeatherThread("GetWeatherThread").start();
	}	
	
	private void parseTemp(JSONObject jsonObj) {
		if (jsonObj == null) 
			mTemp = null;
		
		try {
			mTemp = jsonObj.getJSONObject("weatherinfo").getString("temp").toString();
			Log.i(TAG, "current temperature:" + mTemp);	
			updateTemp(mTemp);				
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "JSONException: parseTemp");	
			e.printStackTrace();
			mTemp = null;
		}
	}
	
	private void parseWeather(JSONObject jsonObj) {
		if (jsonObj == null) 
			mWeather = null;
		
		try {
			mWeather = jsonObj.getJSONObject("weatherinfo").getString("weather").toString();
			updateWeather(mWeather);			
			Log.i(TAG, "current weather:" + mWeather);			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "JSONException: parseWeather");	
			e.printStackTrace();
			mWeather = null;
		}
	}
	
	public String getTemp() {
		return mTemp;
	}
	
	public String getWeather() {
		return mWeather;
	}
	
	class GetWeatherThread extends Thread {		
		
		public GetWeatherThread(String threadName) {
			super(threadName);
			
		}
		
		public void run() {
			String strUrlTemp = "http://www.weather.com.cn/data/sk/" + mCityID + ".html";
			String strUrlWeather = "http://www.weather.com.cn/data/cityinfo/" + mCityID + ".html";

			getJsonObject(strUrlTemp, 1);
			getJsonObject(strUrlWeather, 2);
		}
		
		private JSONObject getJsonObject(String strUrl, int flag) {
			StringBuilder strJsonObject = new StringBuilder();
			
			try {
				URL url = new URL(strUrl);
				URLConnection urlConn = url.openConnection();
				HttpURLConnection httpConn = (HttpURLConnection) urlConn;			
				InputStream urlStream = httpConn.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlStream));

				String strCurrentLine = null;
				while ((strCurrentLine = bufferedReader.readLine()) != null) {
					strJsonObject.append(strCurrentLine);
				}
				bufferedReader.close();
				httpConn.disconnect(); // πÿ±’http¡¨Ω”				
	            try {	
	            	JSONObject jsonObj = new JSONObject(strJsonObject.toString()); 
	            	if (flag == 1)
            			parseTemp(jsonObj);
            		else if (flag == 2)
            			parseWeather(jsonObj);

					Log.i(TAG, jsonObj.toString());		
					return jsonObj;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			
			return null;
		}
	}
	
	private void updateTemp(String temp) {	
		Message msg = new Message();
		msg.what = ServiceProvider.TEMP_INFO_REFRESHED;
		msg.obj = temp;
		mHandler.sendMessage(msg);
	}
	
	private void updateWeather(String weather) {	
		Message msg = new Message();
		msg.what = ServiceProvider.WEATHER_INFO_REFRESHED;
		msg.obj = weather;
		mHandler.sendMessage(msg);
	}
}
