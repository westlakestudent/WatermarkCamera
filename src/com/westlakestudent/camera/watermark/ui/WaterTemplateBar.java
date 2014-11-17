package com.westlakestudent.camera.watermark.ui;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.westlakestudent.camera.systemmgr.UiCmdListener;
import com.westlakestudent.camera.watermark.R;

public class WaterTemplateBar extends HorizontalScrollView implements UiCmdListener{

	private Context mContext = null;
	private UiCmdListener mUiCmdListener =null;
	public final static int LocationBtn_Id = 0xf1000001, TimeBtn_Id = 0xf1000002, FoodBtn_Id = 0xf1000003,WeatherBtn_Id = 0xf1000004, MoodBtn_Id = 0xf1000005 ,DefaultBtn_Id = 0xf1000006;
	private LinearLayout mChildLinearLayout = null;
	private Button mMoodButton = null;
	private Button mTimeButton = null;
	private Button mWeatherButton = null;
	private Button mDefaultButton = null;
	private Button mFoodButton = null;
	private Button mLocationButton = null;
	
	public Button getmMoodButton() {
		return mMoodButton;
	}

	public Button getmTimeButton() {
		return mTimeButton;
	}

	public Button getmWeatherButton() {
		return mWeatherButton;
	}

	public Button getmDefaultButton() {
		return mDefaultButton;
	}

	public Button getmFoodButton() {
		return mFoodButton;
	}

	public Button getmLocationButton() {
		return mLocationButton;
	}
	
	public WaterTemplateBar(Context context) {
		super(context);
		mContext = context;
		initView();
		
	}
	
	public void setmUiCmdListener(UiCmdListener mUiCmdListener) {
		this.mUiCmdListener = mUiCmdListener;
	}

	public void initView(){
		LayoutParams ChlidParams = null;
		LinearLayout.LayoutParams Params = null;
		mChildLinearLayout = new LinearLayout(mContext);
		ChlidParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		mChildLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		addView(mChildLinearLayout, ChlidParams);
		
		mLocationButton = new Button(mContext);
		mLocationButton.setId(LocationBtn_Id);
		Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		mLocationButton.setBackgroundResource(R.drawable.watertemplate_location_bg);
		mLocationButton.setOnClickListener(mLocationListener);
		mChildLinearLayout.addView(mLocationButton, Params);
		
		mTimeButton = new Button(mContext);
		mTimeButton.setId(TimeBtn_Id);
		Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		mTimeButton.setBackgroundResource(R.drawable.watertemplate_time_bg);
		mTimeButton.setOnClickListener(mTimeListener);
		mChildLinearLayout.addView(mTimeButton, Params);
		
		mFoodButton= new Button(mContext);
		mFoodButton.setId(FoodBtn_Id);
		mFoodButton.setBackgroundResource(R.drawable.watertemplate_food_bg);
		Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		mFoodButton.setOnClickListener(mFoodListener);
		mChildLinearLayout.addView(mFoodButton,Params);
		
		mWeatherButton = new Button(mContext);
		mWeatherButton.setId(WeatherBtn_Id);
		mWeatherButton.setBackgroundResource(R.drawable.watertemplate_weather_bg);
		Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		mWeatherButton.setOnClickListener(mWeatherListener);
		mChildLinearLayout.addView(mWeatherButton, Params);
		
		mMoodButton = new Button(mContext);
		mMoodButton.setId(MoodBtn_Id);
		mMoodButton.setBackgroundResource(R.drawable.watertemplate_mood_bg);
		Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		mMoodButton.setOnClickListener(mMoodListener);
		mChildLinearLayout.addView(mMoodButton, Params);
		
		mDefaultButton = new Button(mContext);
		mDefaultButton.setId(DefaultBtn_Id);
		mDefaultButton.setBackgroundResource(R.drawable.watertemplate_default_bg);
		Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		mDefaultButton.setOnClickListener(mDefaultListener);
		mChildLinearLayout.addView(mDefaultButton, Params);
		
	
		
	}
	
	@Override
	public int onUiCmd(int key, Object obj) {
		 switch (key) {
		case LocationBtn_Id:
			mLocationButton.setBackgroundResource(R.drawable.didian1);
			break;
		case TimeBtn_Id:
			mTimeButton.setBackgroundResource(R.drawable.shijian1);
			break;
		case FoodBtn_Id:
			mFoodButton.setBackgroundResource(R.drawable.shiwu1);
			break;
		case WeatherBtn_Id:
			mWeatherButton.setBackgroundResource(R.drawable.tianqi1);
			break;
		case MoodBtn_Id:
			mMoodButton.setBackgroundResource(R.drawable.xinqing1);
			break;
		case DefaultBtn_Id:
			mDefaultButton.setBackgroundResource(R.drawable.shuiyin1);
			break;
		
		
		}
		return key;
	}
	
	OnClickListener mLocationListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (null != mUiCmdListener){
				mUiCmdListener.onUiCmd(LocationBtn_Id, null);
				setButton2Null();
			}
			
		}
	};
	
	OnClickListener mTimeListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (null != mUiCmdListener){
				mUiCmdListener.onUiCmd(TimeBtn_Id, null);
				setButton2Null();
			}
			
		}
	};
	
	OnClickListener mFoodListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (null != mUiCmdListener){
				mUiCmdListener.onUiCmd(FoodBtn_Id, null);
				setButton2Null();
			}
			
		}
	};
	
	OnClickListener mWeatherListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (null != mUiCmdListener){
				mUiCmdListener.onUiCmd(WeatherBtn_Id, null);
				setButton2Null();
			}
			
		}
	};
	
	OnClickListener mMoodListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (null != mUiCmdListener){
				mUiCmdListener.onUiCmd(MoodBtn_Id, null);
				setButton2Null();
			}
			
		}
	};
	
	OnClickListener mDefaultListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (null != mUiCmdListener){
				mUiCmdListener.onUiCmd(DefaultBtn_Id, null);				
				setButton2Null();
			}
		}
	};
	
	private void setButton2Null(){
		removeAllViews();
		mLocationButton = mTimeButton = mFoodButton = mWeatherButton = mMoodButton = mDefaultButton = null;
		mChildLinearLayout = null;
	
		
	}

	

}
