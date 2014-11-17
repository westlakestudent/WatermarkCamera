package com.westlakestudent.camera.watermark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.westlakestudent.camera.systemmgr.UiCmdListener;
import com.westlakestudent.camera.watermark.location.LocationInfo;
import com.westlakestudent.camera.watermark.location.POI;
import com.westlakestudent.camera.watermark.location.POIType;
import com.westlakestudent.camera.watermark.service.LocationService;
import com.westlakestudent.camera.watermark.service.ServiceProvider;
import com.westlakestudent.camera.watermark.service.WeatherService;
import com.westlakestudent.camera.watermark.ui.LocationItem;
import com.westlakestudent.camera.watermark.ui.LocationListView;
import com.westlakestudent.camera.watermark.ui.LocationSearchBar;
import com.westlakestudent.camera.watermark.ui.LocationTitleBar;
import com.westlakestudent.camera.watermark.utils.DensityUtil;

public class Location extends Activity implements UiCmdListener {
	private final String TAG = this.getClass().getName();
	
	private Handler mHandler;
	private MAdapter mAdapter;
	private Map<String, POIType> mLocationType;	
	private Map<String, String> mCityToID;
	private List<String> mCollectLocationItemList;
	private LocationTitleBar mTitleBar;
	private LocationInfo mLocationInfo;
	private LocationItem mNewLocationItem;
	private LocationListView mLocationListView;
	private LocationSearchBar mSearchBar;
	private LocationService mLocationService;
	private SharedPreferences mPreferences;
	private SharedPreferences.Editor mEditor;
	private String mLocation;
	private TextView mNullNetworkTips;
	private WeatherService mWeatherService;	
	
	private final int CONTEXT_MENU_ITEM_DELETE = 0x11000001;
	private final int CONTEXT_MENU_ITEM_CANCEL = 0x11000002;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);	
	    
	    init();		
	    
	    registerForContextMenu(mLocationListView);
	    
	    if (!ServiceProvider.isWirelessEnabled(this))
	    	mNullNetworkTips.setVisibility(View.VISIBLE);
	}	
	
	private void init() {
		mCollectLocationItemList = new ArrayList<String>();
	    mLocationType = new HashMap<String, POIType>();	
	    mPreferences = getSharedPreferences("waterCam", MODE_PRIVATE);
		mEditor = mPreferences.edit();
		mAdapter = new MAdapter(this);	
		mAdapter.add(decodeLocationItems());		
		mCityToID = ServiceProvider.loadMappingTable(this);	
		
		LinearLayout locationPanel = new LinearLayout(this);	    
	    locationPanel.setOrientation(LinearLayout.VERTICAL);
	    setContentView(locationPanel);
		
	    mTitleBar = new LocationTitleBar(this);	    	    
	    locationPanel.addView(mTitleBar);  
	    
	    mSearchBar = new LocationSearchBar(this);
	    mSearchBar.setUiCmdListener(this);
	    locationPanel.addView(mSearchBar);
	    
	    mNullNetworkTips = new TextView(this);
	    mNullNetworkTips.setBackgroundColor(Color.parseColor("#fffbfcd4"));
	    mNullNetworkTips.setGravity(Gravity.CENTER);
	    mNullNetworkTips.setMinHeight(DensityUtil.dip2px(this, 60));
	    mNullNetworkTips.setText("获取位置失败，请检查网络连接是否正常");	    
	    mNullNetworkTips.setTextColor(Color.parseColor("#ff72583a"));
	    mNullNetworkTips.setTextSize(15);
	    mNullNetworkTips.setVisibility(View.GONE);
	    locationPanel.addView(mNullNetworkTips, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    		    
	    mNewLocationItem = new LocationItem(this, null);
	    mNewLocationItem.setUiCmdListener(this);
	    mNewLocationItem.setVisibility(View.GONE);		
	    locationPanel.addView(mNewLocationItem);   
	    	    	    
		mLocationListView = new LocationListView(this);			
		mLocationListView.setAdapter(mAdapter);
		mLocationListView.setUiCmdListener(this);
		locationPanel.addView(mLocationListView); 		
		
		mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case ServiceProvider.LOACTION_INFO_REFRESHED:
					mLocationInfo = (LocationInfo) msg.obj;
					mLocation = mLocationService.getCity();
					mLocationType.put(mLocation, POIType.PLACE);
					mAdapter.add(0, mLocation);	
					mAdapter.addPOIs(mLocationInfo.getPOIs());	
					mLocationListView.refreshCompleted();						
					Log.i(TAG, "城市：" + mLocation);	
					Toast.makeText(Location.this, "城市：" + mLocation, Toast.LENGTH_SHORT).show();						
//					mWeatherService.startService(mCityToID.get(mLocation));	
					break;
				case ServiceProvider.TEMP_INFO_REFRESHED:
					Log.i(TAG, "城市：" + mLocation + "， 当前温度：" + (String) msg.obj);
					break;
				case ServiceProvider.WEATHER_INFO_REFRESHED:
					Log.i(TAG, "城市：" + mLocation + "， 当前温度：" + mWeatherService.getTemp() + "， 当前天气：" + (String) msg.obj);
					Toast.makeText(Location.this, "城市：" + mLocation + "， 当前温度：" + mWeatherService.getTemp() + "， 当前天气：" + (String) msg.obj, Toast.LENGTH_SHORT).show();
					break;
				case ServiceProvider.LOACTION_INFO_REFRESH_FAILED:
					mLocationListView.refreshCompleted();
				}
			}
		};
		
		mLocationService = new LocationService(this, mHandler);	
		mWeatherService = new WeatherService(mHandler, null);		
		mLocationService.startService();
	}
	
	public void onCancel() {
//		Intent intent = new Intent(Location.this, WaterCamera.class);
//		setResult(RESULT_OK, intent);	
		finish();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		mLocationService.startService();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		LocationFinished();
	}
	
	private String[] decodeLocationItems() {		
		if (null == mPreferences.getString("locatinItems", null)) 
			return new String[]{};
		
		String[] locationItems = mPreferences.getString("locatinItems", null).split("\t");			
		for (String loc : locationItems) {
			mCollectLocationItemList.add(loc);
			mLocationType.put(loc, POIType.COLLECT);
		}			
		mCollectLocationItemList.toArray(locationItems);
		
		return locationItems;
	}
	
	private String encodeLocationItems() {
		if (mCollectLocationItemList.size() == 0) 
			return null;
		
		StringBuilder locationItems = new StringBuilder();
		for (String loc : mCollectLocationItemList) {
			locationItems.append(loc + "\t");
		}
		return locationItems.toString();
	}
	
	private void addLocationItems(String location) {
		int index;
		if (-1 != (index = mCollectLocationItemList.indexOf(location)))
			mCollectLocationItemList.remove(index);		
		mCollectLocationItemList.add(0, location);				
	}	
	
	private void removeLocationItems(String location) {
		mCollectLocationItemList.remove(location);
	}
	
	class MAdapter extends BaseAdapter {			
		private List<String> txtList;
		
		public MAdapter(Context context) {
			super();			
			txtList = new ArrayList<String>();
		}
		
		@Override
		public int getCount() {
			return txtList.size();
		}

		@Override
		public Object getItem(int position) {
			return txtList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			String location = txtList.get(position);			
			LocationItem locationItem = new LocationItem(Location.this, location, mLocationType.get(location));
			locationItem.setLocationText(location);
			return locationItem;			
		}
		
		public void add(String text){
			int idx;
			if (-1 != (idx = txtList.indexOf(text))) {
				txtList.remove(idx);
			}
			txtList.add(text);			
			notifyDataSetChanged();
		}
		
		public void add(String[] texts){
			for(String text: texts) {
				add(text);				
			}			
			notifyDataSetChanged();
		}
		
		public void add(int index, String text){
			int idx;
			if (-1 != (idx = txtList.indexOf(text))) {
				txtList.remove(idx);
			}
			txtList.add(index, text);			
			notifyDataSetChanged();
		}
		
		public void addPOIs(List<POI> poiList) {
			for (POI poi : poiList) {
				String location = poi.getName();
				mLocationType.put(location, poi.getType());
				add(location);
			}
		}
		
		public void remove(int index) {
			if (index < 0)
				return;			
			txtList.remove(index);	
			notifyDataSetChanged();
		}
		
		public boolean remove(String location) {	
			boolean removed = txtList.remove(location);			
			notifyDataSetChanged();
			return removed;
		}
		
		public void removeAll(){
			txtList.clear();			
			notifyDataSetChanged();
		}
	}
	
	private void LocationFinished() {
		mLocationService.stopService();
		mEditor.putString("locatinItems", encodeLocationItems());
		mEditor.commit();	
		
		Bundle data = new Bundle();		
		String cityID = mCityToID.get(mLocation);			
		if (null != cityID) {
			mWeatherService.startService(cityID);	
			data.putString("city", mLocation);
			data.putString("cityID", cityID);
		} else {							
			data.putString("city", mLocationService.getCity());
			data.putString("cityID", mCityToID.get(mLocationService.getCity()));
		}			
		data.putString("location", mLocation);

		Intent intent = new Intent(Location.this, WaterCamera.class);
		intent.putExtras(data);
		setResult(RESULT_OK, intent);	
		finish();
	}

	@Override
	public int onUiCmd(int key, Object obj) {		
		switch (key) {
		case LocationSearchBar.SEARCHBOX_CHANGED:
			CharSequence text = (CharSequence) obj;
			mNewLocationItem.setLocationText(text);			
			if (text.equals("") || text.length() == 0) 
				mNewLocationItem.setVisibility(View.GONE);	
			else
				mNewLocationItem.setVisibility(View.VISIBLE);		
			break;
		case LocationItem.LOCATION_ITEM_CLICK:
			mLocation = obj.toString();					
			mAdapter.add(0, mLocation);	
			mLocationType.put(mLocation, POIType.COLLECT);
			addLocationItems(mLocation);	
			
			mSearchBar.clearSearchbox();
			mNewLocationItem.setVisibility(View.GONE);				
			LocationFinished();
			break;
		case LocationListView.LOCATION_LIST_VIEW_ITEM_CLICK:
			mLocation = mAdapter.getItem(Integer.parseInt(obj.toString())).toString();
			mAdapter.add(0, mLocation);		
			addLocationItems(mLocation);	
			Toast.makeText(Location.this, "位置：" + mLocation, Toast.LENGTH_SHORT).show();
//			String cityID = mCityToID.get(mLocation);
//			if (null != cityID)			
//				mWeatherService.startService(cityID);				
			LocationFinished();
			break;
		case LocationListView.LOCATION_REFRESH:
			TimerTask task = new TimerTask() {

				@Override
				public void run() {
					mHandler.sendEmptyMessage(ServiceProvider.LOACTION_INFO_REFRESH_FAILED);
				}
				
			};
			new Timer().schedule(task, 10000);
			
			if (ServiceProvider.isWirelessEnabled(this)) {
				mNullNetworkTips.setVisibility(View.GONE);
				mLocationService.stopService();
				mLocationService.startService();
			} else {
				mNullNetworkTips.setVisibility(View.VISIBLE);
				mLocationListView.refreshCompleted();
			}							
		}
		
		return 0;
	}	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == CONTEXT_MENU_ITEM_DELETE) {
			AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo(); 				
			String location = mAdapter.getItem((int) info.id).toString();
			
			mAdapter.remove(location);			
			if (mCollectLocationItemList.contains(location)) 
				removeLocationItems(location);
			
			Toast.makeText(Location.this, location + " 已删除", Toast.LENGTH_SHORT).show();
		}		
		
		return super.onContextItemSelected(item); 
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
//		mLocationListView.onCreateContextMenu(menu, v, menuInfo);
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(Menu.NONE, CONTEXT_MENU_ITEM_DELETE, Menu.NONE, "删除"); 
		menu.add(Menu.NONE, CONTEXT_MENU_ITEM_CANCEL, Menu.NONE, "取消"); 
	}
	
}