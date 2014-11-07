package com.arcsoft.camera.watermark.location;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;


public class LocationInfo {

	private int mStatus = -1;	
	private double mLongitude;
	private double mLatitude;
	private String mProvince;
	private String mCity;
	private String mDistrict;
	private String mStreet;		
	private String mStreetNumber;			
	private List<POI> mPoiList = new ArrayList<POI>();		
	
	public int getStatus() {
		return mStatus;
	}

	public double getLongitude() {
		return mLongitude;
	}
	
	public double getLatitude() {
		return mLatitude;
	}
	
	public String getProvince() {
		return mProvince;
	}
	
	public String getCity() {
		return mCity;
	}
	
	public String getDistrict() {
		return mDistrict;
	}
	
	public String getStreet() {
		return mStreet;
	}
	
	public String getStreetNumber() {
		return mStreetNumber;
	}
	
	public void addPOI(POI poi) {
		mPoiList.add(poi);
	}
	
	public List<POI> getPOIs() {
		return mPoiList;
	}
		
	public void setStatus(int s) {
		mStatus = s;
	}
	
	public void setPoint(JSONObject jsObj) {			
		try {
			mLongitude = jsObj.getDouble("lng");
			mLatitude = jsObj.getDouble("lat");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void setAddressComponent(JSONObject jsObj) {
		try {
			mProvince = jsObj.getString("province");
			mCity = jsObj.getString("city");
			mDistrict = jsObj.getString("district");
			mStreet = jsObj.getString("street");
			mStreetNumber = jsObj.getString("street_number");				
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder loc = new StringBuilder();
		loc.append("longitude: " + mLongitude + "\n");
		loc.append("latitude: " + mLatitude + "\n");
		loc.append("province: " + mProvince + "\n");
		loc.append("city: " + mCity + "\n");
		loc.append("district: " + mDistrict + "\n");
		loc.append("street: " + mStreet + "\n");
		loc.append("streetNumber: " + mStreetNumber);			
		
		return loc.toString();
	}
}
