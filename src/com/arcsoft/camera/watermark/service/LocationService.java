package com.arcsoft.camera.watermark.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.arcsoft.camera.watermark.location.LocationInfo;
import com.arcsoft.camera.watermark.location.POI;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class LocationService{
	public final String TAG = this.getClass().getName();
	
	private boolean mbLocated = false;
	private String mProvider;
	private Context mContext;	
    private Handler mHandler;
    private Location mLocation;
    private LocationInfo mLocationInfo = new LocationInfo();
    private LocationManager mLocationManager;
    private LocationListener mlocationListener;
    private JSONObject mJsonObj;
    private String mCity;
    private String mLastAddress;   
    private boolean mbRefresh;
    
    public LocationService(Context context, Handler handler) {
        mContext = context;
        mHandler = handler;
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);           
        getBestProvider();           
        mlocationListener = new LocationListener() {
        	
            @Override
            public void onLocationChanged(Location location) {
            	Log.d(TAG, "########## onLocationChanged");
                updateLocation(location);                
            }
 
            @Override
            public void onProviderDisabled(String provider) {
            	Log.d(TAG, "########## onProviderDisabled");
            	getBestProvider();  
            }
 
            @Override
            public void onProviderEnabled(String provider) {
            	Log.d(TAG, "########## onProviderEnabled");
            	getBestProvider();  
            }  

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				Log.d(TAG, "########## onStatusChanged");
			}
        };
    }
    
//    public void setLocationProvider(String provider) {    	   
//        mProvider = provider;   
//        Log.d(TAG, "current provider: " + mProvider);        
//    }
    
    public String getBestProvider() {
    	Criteria criteria = new Criteria();        
    	criteria.setAccuracy(Criteria.ACCURACY_FINE);
    	criteria.setCostAllowed(true);
    	criteria.setPowerRequirement(Criteria.POWER_MEDIUM);        
        mProvider = mLocationManager.getBestProvider(criteria, true);   
//        mProvider = "gps";
        Log.d(TAG, "getBestProvider: " + mProvider);
        
        return mProvider;
    }
    
    public void stopService() {
        mLocationManager.removeUpdates(mlocationListener);
    }
 
    public void startService() {
    	Log.d(TAG, "startService <==========");
    	
        mLocationManager.requestLocationUpdates(mProvider , 1000, 0, mlocationListener );
        updateLocation(null);
        
        Log.d(TAG, "startService ==========>");
    }
 
    private void updateLocation(Location newLocation) {
        if (newLocation == null) {
        	mLocation = mLocationManager.getLastKnownLocation(mProvider);
        	if (null == mLocation)
        		return;
        	Log.i(TAG, "LastLatitude:" + mLocation.getLatitude() + "  LastLongitude:" + mLocation.getLongitude());
        } else {
            mLocation = newLocation;
            mbLocated = true;
            Log.i(TAG, "Latitude:" + mLocation.getLatitude() + "  Longitude:" + mLocation.getLongitude());
        }
        
        parseGeocode();
    }
 
//    public Location getLocation() {
//    	return mLocation;
//    }
    
    public boolean isLoacted() {
    	return mbLocated;
    }   
    
    private JSONObject parseGeocode() {		
        new ResolveLocationThread("ResolveLocationThread").start();
        return mJsonObj;
    }    
	
    private void parseJsonObject() {
		
		try {
			if (0 != mJsonObj.getInt("status")) 
				return;
			mLocationInfo.setStatus(0);
			
			JSONObject result = mJsonObj.getJSONObject("result");
			if (mLastAddress != null && mLastAddress.equals(result.getString("formatted_address"))) 
				mbRefresh = false;			
			else
				mbRefresh = true;
			mLastAddress = result.getString("formatted_address");
			
			mLocationInfo.setPoint(result.getJSONObject("location"));			
			mLocationInfo.setAddressComponent(result.getJSONObject("addressComponent"));
			Log.i(TAG, mLocationInfo.toString());
			
			JSONArray pois = result.getJSONArray("pois");
			for (int i = 0; i < pois.length(); i++) {				
				POI poi = new POI(pois.getJSONObject(i));
				mLocationInfo.addPOI(poi);
				Log.i(TAG, poi.toString());
			}
			
			locationUpdated(mLocationInfo);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
    
    public String getCity() {
    	String city = mLocationInfo.getCity();
    	if (null == city) 
    		return null;
    		
    	city = city.subSequence(0, city.length()-1).toString();
    	return city;
    }
    
    public LocationInfo getLocationInfo() {
    	return mLocationInfo;
    }
    
	class ResolveLocationThread extends Thread {
		
		public ResolveLocationThread(String threadName) {
			super(threadName);
		}
		
		public void run() {
//			String strUrl = "http://api.map.baidu.com/geocoder?output=json&location="+mLocation.getLatitude()+","+mLocation.getLongitude()+"&key=E75C1477680B72C100DBFBAC105B00542C174F54";
			String strUrl = "http://api.map.baidu.com/geocoder/v2/?output=json&location="+mLocation.getLatitude()+","+mLocation.getLongitude()+"&pois=1&ak=8419b6b1b7a08f3e70a1172bb992c3dc";
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
				httpConn.disconnect(); // ¹Ø±ÕhttpÁ¬½Ó				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
					
	        try {    
	        	mJsonObj = new JSONObject(strJsonObject.toString());  	        		        	
	            Log.i(TAG, mJsonObj.toString()); 
	            Log.i(TAG, "current city:" + mCity); 

	            parseJsonObject();
	        } catch (JSONException e) {     
	            e.printStackTrace();    
	        }  
		}
	}
	
	public void locationUpdated(LocationInfo lInfo) {	
		if (!mbRefresh) 
			return;
		
		Message msg = new Message();
		msg.what = ServiceProvider.LOACTION_INFO_REFRESHED;
		msg.obj = lInfo;
		mHandler.sendMessage(msg);
	}	
}
