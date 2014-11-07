package com.arcsoft.camera.watermark.location;

import org.json.JSONException;
import org.json.JSONObject;


public class POI {
	
	private double mX;
	private double mY;
	private double mDistance;
	private String address;	
	private String mName;
	private String mPoiType;
	private String mTel;		
	private String mUid;		
	private String mZip;
	private POIType mType;	
	
	public POI(JSONObject jsObj) {
		try {
			mX = jsObj.getJSONObject("point").getDouble("x");
			mY = jsObj.getJSONObject("point").getDouble("y");
			address = jsObj.getString("addr");			
			mDistance = jsObj.getDouble("distance");
			mName = jsObj.getString("name");
			mPoiType = jsObj.getString("poiType");
			mTel = jsObj.getString("tel");
			mUid = jsObj.getString("uid");
			mZip = jsObj.getString("zip");
			setType();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	private void setType() {
		String[] types = mPoiType.split(",");		
		mType = POIType.getType(types[types.length - 1]);
	}
	
	public POIType getType() {
		return mType;
	}
	
	public double getX() {
		return mX;
	}
	
	public double getY() {
		return mY;
	}
	
	public String getAddress() {
		return address;
	}

	public double getDistance() {
		return mDistance;
	}
	
	public String getName() {
		return mName;
	}
	
	public String getPoiType() {
		return mPoiType;
	}
	
	public String getTel() {
		return mTel;
	}
	
	public String getUid() {
		return mUid;
	}
	
	public String getZip() {
		return mZip;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder poi = new StringBuilder();
		poi.append("address: " + address + "\n");
		poi.append("mX: " + mX + "\n");
		poi.append("mY: " + mY + "\n");
		poi.append("mDistance: " + mDistance + "\n");
		poi.append("mName: " + mName + "\n");
		poi.append("mPoiType: " + mPoiType + "\n");
		poi.append("mTel: " + mTel + "\n");
		poi.append("mUid: " + mUid);
		poi.append("mZip: " + mZip);
		
		return poi.toString();
	}	

}
