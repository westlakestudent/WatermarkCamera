package com.arcsoft.camera.watermark.service;

import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class Weather {
	public final static int WEATHER_INFO = 0xee000001,TEMP_INFO = 0xee000002, WIND_INFO = 0xee000003,LOCATION_INFO = 0xee000004,
							WARNING_INFO = 0xee000005;
	private final static String KEY_RELCITY = "relCity";
	private final static String KEY_TEMPERATURE_NOW = "temperature_now";
	private final static String KEY_DESCRIPTION = "description_1";
	private final static String KEY_MAX_TEMPERATURE = "maxTemperature_1";
	private final static String KEY_MIN_TEMPERATURE = "minTemperature_1";
	private final static String KEY_WIND_NOW = "wind_now";
	private final static String KEY_WARNING = "warning";
	private final static String WEATHER_URI = "content://com.metek.zqWeather.DataContentProvider/weathers";
	private final static String WEATHER_THREAD = "WEATHER_THREAD";
	private String mNow_Temperature = null;
	private String mMax_temperature = null;
	private String mMin_Temperature = null;
	private String mWarning = null;
	private String mWeather = null;
	private String mWind = null;
	private String mLocation = null;
	private Context mContext = null;
	private Handler mHandler = null;
	
	public Weather(Context context,Handler handler){
		mContext = context;
		mHandler = handler;
	}


	public void startWeatherThread(){
		TimerTask mTask = new TimerTask(){

			@Override
			public void run() {
				new WeatherThread(WEATHER_THREAD).start();
			}
			
		};
		new Timer().schedule(mTask, 1000,30000);
	}
	
	private void updateWeather(String weather){
		Message msg = new Message();
		msg.what = WEATHER_INFO;
		msg.obj = weather;
		mHandler.sendMessage(msg);
	} 
	

	private void updateWind(String wind){
		Message msg = new Message();
		msg.what = WIND_INFO;
		msg.obj = wind;
		mHandler.sendMessage(msg);
	}
	
	private void updateLocation(String loca){
		Message msg = new Message();
		msg.what = LOCATION_INFO;
		msg.obj = loca;
		mHandler.sendMessage(msg);
	}
	
	private void updateTemperature(String temperature){
		Message msg = new Message();
		msg.what = TEMP_INFO;
		msg.obj = temperature;
		mHandler.sendMessage(msg);
	}

	private void updateWarning(String warning){
		Message msg = new Message();
		msg.what = WARNING_INFO;
		msg.obj = warning;
		mHandler.sendMessage(msg);
	}

	private class WeatherThread extends Thread{
		
		public WeatherThread(String threadname){
			super(threadname);
		}

		public void run() {
			Uri CONTENT_URI =Uri.parse(WEATHER_URI);
			Log.e("weather-------------->", "!!!!!!");
			if(CONTENT_URI != null){
				Cursor c = mContext.getContentResolver().query(CONTENT_URI,null, null, null, null);
				if(c != null && c.moveToFirst()){
					mLocation = c.getString(c.getColumnIndex(KEY_RELCITY));
					mWeather = c.getString(c.getColumnIndex(KEY_DESCRIPTION));
					mWind = c.getString(c.getColumnIndex(KEY_WIND_NOW));
				    mMax_temperature = c.getString(c.getColumnIndex(KEY_MAX_TEMPERATURE));
					mMin_Temperature = c.getString(c.getColumnIndex(KEY_MIN_TEMPERATURE));
					mNow_Temperature = c.getString(c.getColumnIndex(KEY_TEMPERATURE_NOW));
					mWarning = c.getString(c.getColumnIndex(KEY_WARNING));
					updateLocation(mLocation);
					updateTemperature(mNow_Temperature);
					updateWeather(mWeather);
					updateWind(mWind);
					updateWarning(mWarning);
					c.close();
				}
			}
		}
		
		
		
	}

	public String getmNow_Temperature() {
		return mNow_Temperature;
	}


	public String getmMax_temperature() {
		return mMax_temperature;
	}


	public String getmMin_Temperature() {
		return mMin_Temperature;
	}


	public String getmWarning() {
		return mWarning;
	}


	public String getmWeather() {
		return mWeather;
	}


	public String getmWind() {
		return mWind;
	}


	public String getmLocation() {
		return mLocation;
	}
	
	
	
	
}
