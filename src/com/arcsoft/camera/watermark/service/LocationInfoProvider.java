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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class LocationInfoProvider {
	public final String TAG = this.getClass().getName();
	private Context mContext = null;
	private Handler mHandler = null;
	private LocationInfo mLocationInfo = null;
	private LocationManager mLocationManager;
    private LocationListener mlocationListener;
    private JSONObject mJsonObj = null;
    private Location mLocation = null;
    private boolean mbLocated = false;
    private boolean mbRefresh = false;
	private String mProvider = null;
	private String mCity = null;
	private String mLastAddress = null;  
	public final static int LOACTION_INFO_REFRESHED = 0xee000006;
	
	public LocationInfoProvider(Context context){
		mContext = context;
		mLocationInfo = new LocationInfo();
		mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);   
		getBestProvider(); 
		mlocationListener = new LocationListener(){

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
			public void onStatusChanged(String provider, int status, Bundle b) {
				
			}};
	}
	
	
	private String getBestProvider() { 
    	Criteria criteria = new Criteria();        
    	criteria.setAccuracy(Criteria.ACCURACY_FINE);
    	criteria.setCostAllowed(true);
    	criteria.setPowerRequirement(Criteria.POWER_MEDIUM);        
        mProvider = mLocationManager.getBestProvider(criteria, true);   
        Log.d(TAG, "getBestProvider: " + mProvider);
        return mProvider;
    }

	 public void stopLocationManager() {
	        mLocationManager.removeUpdates(mlocationListener);
	    }
	 
	 public void startLocationManager() {
	    	Log.d(TAG, "start==========");
	        mLocationManager.requestLocationUpdates(mProvider , 1000, 0, mlocationListener );
	        updateLocation(null);
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
	  
	  
	  public boolean isLoacted() {
	    	return mbLocated;
	    } 
	  
	  public void setHandler(Handler handler){
		  mHandler = handler;
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
	
	private JSONObject parseGeocode() {		
        new ResolveLocationThread("ResolveLocationThread").start();
        return mJsonObj;
    }    
	
	
	
	public void initWireless(){
		if (!isWirelessEnabled(mContext)) {
			new AlertDialog.Builder(mContext)
					.setTitle("当前网络不可用")
					.setMessage("您尚未开启网络，部分水印可能无法完整展示。")
					.setCancelable(false)
					.setPositiveButton("打开网络",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									openWirelessSetting(mContext);
								}
							})
					.setNegativeButton("继续使用",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									Toast.makeText(mContext,
											"未开启网络，部分水印可能无法完整展示!",
											Toast.LENGTH_SHORT).show();
								}
							}).setCancelable(false).show()
					.setOnDismissListener(new OnDismissListener() {

						@Override
						public void onDismiss(DialogInterface dialog) {
							initGPS();
						}
					});
		} else {
			initGPS();
		}
	}
	
	
	private void initGPS(){
		if (!isGPSProviderEnabled(mContext)) {
			new AlertDialog.Builder(mContext)
					.setTitle("当前GPS定位服务不可用")
					.setMessage("您尚未开启GPS定位服务，部分水印可能无法完整展示。")
					.setCancelable(false)
					.setPositiveButton("打开GPS",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									openLocationSetting(mContext);
								}
							})
					.setNegativeButton("继续使用",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									Toast.makeText(mContext,
											"未开启GPS定位服务，定位服务!",
											Toast.LENGTH_SHORT).show();
								}
							}).setCancelable(false).show();
		} 
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
	
	
	private void locationUpdated(LocationInfo lInfo) {	
		Log.e(TAG, "--------->"+lInfo.toString());
		if (!mbRefresh) 
			return;
		Message msg = new Message();
		msg.what = LOACTION_INFO_REFRESHED;
		msg.obj = lInfo;
		mHandler.sendMessage(msg);
	}	
		
	
	private boolean isGPSProviderEnabled(Context context) {
    	ContentResolver cr = context.getContentResolver();
    	String providersAllowed = Settings.Secure.getString(cr, Settings.Secure.LOCATION_PROVIDERS_ALLOWED);  
    	Log.i("GPSService", "providersAllowed:" + providersAllowed);
    	return providersAllowed.contains("gps");    	
    }
    
  
    private boolean isWirelessEnabled(Context context) {  
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
    
    private void openLocationSetting(Context context) {
		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		((Activity) context).startActivity(intent);
	}
    
   
    
    private void openWirelessSetting(Context context) {
		Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
		((Activity) context).startActivity(intent);
	}
    
   
    
    
	private class ResolveLocationThread extends Thread {
		
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
				httpConn.disconnect(); // 关闭http连接				
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
    
}
