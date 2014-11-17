package com.westlakestudent.camera.watermark.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

public class ServiceProvider {
	public static final int LOACTION_INFO_REFRESHED = 0x12000001;
	public static final int TEMP_INFO_REFRESHED = 0x12000002;
	public static final int WEATHER_INFO_REFRESHED = 0x12000003;
	public static final int LOACTION_INFO_REFRESH_FAILED = 0x12000004;
	
	public static boolean isGPSProviderEnabled(Context context) {
    	ContentResolver cr = context.getContentResolver();
    	String providersAllowed = Settings.Secure.getString(cr, Settings.Secure.LOCATION_PROVIDERS_ALLOWED);  
    	Log.i("GPSService", "providersAllowed:" + providersAllowed);
    	return providersAllowed.contains("gps");    	
    }
    
    public static boolean isNetworkProviderEnabled(Context context) {
    	ContentResolver cr = context.getContentResolver();
    	String providersAllowed = Settings.Secure.getString(cr, Settings.Secure.LOCATION_PROVIDERS_ALLOWED);  
    	Log.i("GPSService", "providersAllowed:" + providersAllowed);
    	return providersAllowed.contains("network");    	
    }
    
    public static boolean isWirelessEnabled(Context context) {  
		ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);  
	    
		if (manager == null) {  
			return false; 
		}  
	  
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();  
	   
		if (networkinfo == null || !networkinfo.isAvailable()) {  
			return false;  
		}  
	   
		return true;
	} 
    
    public static void openLocationSetting(Context context) {
		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		((Activity) context).startActivity(intent);
	}
    
    public static void openLocationSetting(Context context, int requestCode) {
		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		((Activity) context).startActivityForResult(intent, requestCode);
	}
    
    public static void openWirelessSetting(Context context) {
		Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
		((Activity) context).startActivity(intent);
	}
    
    public static void openWirelessSetting(Context context, int requestCode) {
		Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
		((Activity) context).startActivityForResult(intent, requestCode);
	}
    
    public static Map<String, String> loadMappingTable(Context context) {    	
    	AssetManager am = context.getAssets();
    	Map<String, String> cityToID = new HashMap<String, String>();
		
		try {
			InputStream in = am.open("city_to_id.txt");				
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "Unicode"));
			
			String strLine = null;		            
            while ((strLine = reader.readLine()) != null) {
            	String[] strIDtoCity = strLine.split("\t");
            	if (strIDtoCity.length != 2) 
            		continue;
            	cityToID.put(strIDtoCity[1], strIDtoCity[0]);				    
			}   
            in.close();			
		  } catch (IOException e) {
			  Log.e("", "city_to_id.txt exception");
			  e.printStackTrace();
		  }	    
		
		return cityToID;
    }
}
