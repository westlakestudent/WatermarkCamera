package com.westlakestudent.camera.watermark.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.westlakestudent.camera.watermark.R;
import com.westlakestudent.camera.watermark.utils.DensityUtil;

public class WeekTemplate extends RelativeLayout{

	private Context mContext = null;
	private int mBaseHeight = 960;
	private float mScale = 1.0f;
	
	private Handler mHandler = null;
	private Runnable mRunnable = null;
	private TextView mDayText = null;
	private TextView mMonthText = null;
	private TextView mTimeText = null;
	private TextView mLocationtext = null;
	private TextView mMonText = null;
	private TextView mTueText = null;
	private TextView mWenText = null;
	private TextView mThuText = null;
	private TextView mFriText = null;
	private TextView mSatText = null;
	private TextView mSunText = null;
	private TextView [] WEEKDAY_SET = null;
	
	private final static String[] MONTHS= {"January","February","March","April","May","June","July","August","September","October","November","December"};
	private final static int DAY = 0xeee00001, MONTH = 0xeee00002, TIME = 0xeee00003, SUNDAY = 0xeee00004, MONDAY = 0xeee00005,
							 TUESDAY = 0xeee00006, WENSDAY = 0xeee00007, THURSADY = 0xeee00008, FRIDAY = 0xeee00009, SATURDAY = 0xeee0000a, LOCATION = 0xeee0000b,
							 WEEKIMAGE = 0xeee0000c, IMAGE_LINE = 0xeee0000d;
	
	
	public WeekTemplate(Context context, int height) {
		super(context);
		mContext = context;
		mHandler = new Handler();
		mScale = (height * 1.0f) / mBaseHeight;
		initView();
		initTime();
	}
	
	
	private void initView(){
		LayoutParams Params = null;
		mDayText = new TextView(mContext);
		mDayText.setTextSize(35);
		mDayText.setTextColor(Color.WHITE);
		mDayText.setId(DAY);
		mDayText.setText("05");
		Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		Params.leftMargin = DensityUtil.dip2px(mContext, 20);
		Params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		Params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		addView(mDayText, Params);
		
		ImageView mImageLine = new ImageView(mContext);
		mImageLine.setBackgroundResource(R.drawable.weekwhiteline);
		mImageLine.setId(IMAGE_LINE);
		Params = new LayoutParams(DensityUtil.dip2px(mContext, 10), DensityUtil.dip2px(mContext, 45));
		Params.leftMargin = DensityUtil.dip2px(mContext, 2);
		Params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		Params.addRule(RelativeLayout.RIGHT_OF,DAY);
		addView(mImageLine, Params);
		
		mMonthText = new TextView(mContext);
		mMonthText.setTextSize(35);
		mMonthText.setTextColor(Color.WHITE);
		mMonthText.setId(MONTH);
		mMonthText.setText(MONTHS[8]);
		Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		Params.addRule(RelativeLayout.RIGHT_OF, IMAGE_LINE);
		Params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		addView(mMonthText, Params);
		
		mTimeText = new TextView(mContext);
		mTimeText.setTextSize(12);
		mTimeText.setId(TIME);
		mTimeText.setText("14:39");
		mTimeText.setTextColor(Color.WHITE);
		Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		Params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		Params.addRule(RelativeLayout.RIGHT_OF, MONTH);
		Params.topMargin = DensityUtil.dip2px(mContext, 10);
		Params.leftMargin = DensityUtil.dip2px(mContext, 5);
		addView(mTimeText, Params);
		
		mLocationtext = new TextView(mContext);
		mLocationtext.setId(LOCATION);
		mLocationtext.setTextSize(12);
		mLocationtext.setText("º¼ÖÝ");
		mLocationtext.setTextColor(Color.WHITE);
		Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		Params.addRule(RelativeLayout.ALIGN_LEFT, TIME);
		Params.addRule(RelativeLayout.BELOW, TIME);
		addView(mLocationtext, Params);
		
		ImageView mWeekImage = new ImageView(mContext);
		mWeekImage.setId(WEEKIMAGE);
		mWeekImage.setBackgroundResource(R.drawable.sunsat);
		Params = new LayoutParams(DensityUtil.dip2px(mContext, 270), DensityUtil.dip2px(mContext, 30));
		Params.addRule(RelativeLayout.ALIGN_LEFT, DAY);
		Params.addRule(RelativeLayout.BELOW, DAY);
		addView(mWeekImage, Params);
		
		mSunText = new TextView(mContext);
		mSunText.setId(SUNDAY);
		mSunText.setText("7");
		mSunText.setTextSize(20);
		mSunText.setTextColor(Color.WHITE);
		mSunText.setGravity(Gravity.CENTER);
		//mSunText.setBackgroundResource(R.drawable.redcircle);
		Params = new LayoutParams(DensityUtil.dip2px(mContext, 30), DensityUtil.dip2px(mContext, 30));
		Params.addRule(RelativeLayout.ALIGN_LEFT, DAY);
		Params.addRule(RelativeLayout.BELOW, WEEKIMAGE);
		//Params.leftMargin = DensityUtil.dip2px(mContext, 7);
		addView(mSunText, Params);
		
		mMonText = new TextView(mContext);
		mMonText.setId(MONDAY);
		mMonText.setText("1");
		mMonText.setTextSize(20);
		mMonText.setTextColor(Color.WHITE);
		mMonText.setGravity(Gravity.CENTER);
		//mMonText.setBackgroundResource(R.drawable.redcircle);
		Params = new LayoutParams(DensityUtil.dip2px(mContext, 30), DensityUtil.dip2px(mContext, 30));
		Params.addRule(RelativeLayout.RIGHT_OF, SUNDAY);
		Params.addRule(RelativeLayout.BELOW, WEEKIMAGE);
		Params.leftMargin = DensityUtil.dip2px(mContext, 11);
		addView(mMonText, Params);
		
		mTueText = new TextView(mContext);
		mTueText.setId(TUESDAY);
		mTueText.setText("2");
		mTueText.setTextSize(20);
		mTueText.setTextColor(Color.WHITE);
		mTueText.setGravity(Gravity.CENTER);
		//mTueText.setBackgroundResource(R.drawable.redcircle);
		Params = new LayoutParams(DensityUtil.dip2px(mContext, 30), DensityUtil.dip2px(mContext, 30));
		Params.addRule(RelativeLayout.RIGHT_OF, MONDAY);
		Params.addRule(RelativeLayout.BELOW, WEEKIMAGE);
		Params.leftMargin = DensityUtil.dip2px(mContext, 11);
		addView(mTueText, Params);
		
		mWenText = new TextView(mContext);
		mWenText.setId(WENSDAY);
		mWenText.setText("3");
		mWenText.setTextSize(20);
		mWenText.setTextColor(Color.WHITE);
		mWenText.setGravity(Gravity.CENTER);
		//mWenText.setBackgroundResource(R.drawable.redcircle);
		Params = new LayoutParams(DensityUtil.dip2px(mContext, 30), DensityUtil.dip2px(mContext, 30));
		Params.addRule(RelativeLayout.RIGHT_OF, TUESDAY);
		Params.addRule(RelativeLayout.BELOW, WEEKIMAGE);
		Params.leftMargin = DensityUtil.dip2px(mContext, 11);
		addView(mWenText, Params);
		
		mThuText = new TextView(mContext);
		mThuText.setId(THURSADY);
		mThuText.setText("4");
		mThuText.setTextSize(20);
		mThuText.setTextColor(Color.WHITE);
		mThuText.setGravity(Gravity.CENTER);
		//mThuText.setBackgroundResource(R.drawable.redcircle);
		Params = new LayoutParams(DensityUtil.dip2px(mContext, 30), DensityUtil.dip2px(mContext, 30));
		Params.addRule(RelativeLayout.RIGHT_OF, WENSDAY);
		Params.addRule(RelativeLayout.BELOW, WEEKIMAGE);
		Params.leftMargin = DensityUtil.dip2px(mContext, 11);
		addView(mThuText, Params);
		
		mSatText = new TextView(mContext);
		mSatText.setId(SATURDAY);
		mSatText.setText("6");
		mSatText.setTextSize(20);
		mSatText.setTextColor(Color.WHITE);
		mSatText.setGravity(Gravity.CENTER);
		//mSatText.setBackgroundResource(R.drawable.redcircle);
		Params = new LayoutParams(DensityUtil.dip2px(mContext, 30), DensityUtil.dip2px(mContext, 30));
		Params.addRule(RelativeLayout.ALIGN_RIGHT, WEEKIMAGE);
		Params.addRule(RelativeLayout.BELOW, WEEKIMAGE);
		addView(mSatText, Params);
		
		mFriText = new TextView(mContext);
		mFriText.setId(FRIDAY);
		mFriText.setGravity(Gravity.CENTER);
		mFriText.setText("5");
		mFriText.setTextSize(20);
		mFriText.setTextColor(Color.WHITE);
		//mFriText.setBackgroundResource(R.drawable.redcircle);
		Params = new LayoutParams(DensityUtil.dip2px(mContext, 30), DensityUtil.dip2px(mContext, 30));
		Params.addRule(RelativeLayout.LEFT_OF, SATURDAY);
		Params.addRule(RelativeLayout.BELOW, WEEKIMAGE);
		Params.rightMargin = DensityUtil.dip2px(mContext, 10);
		addView(mFriText, Params);
		
		WEEKDAY_SET = new TextView [] {mSunText,mMonText,mTueText,mWenText,mThuText,mFriText,mSatText};
	}
	
	@SuppressWarnings("deprecation")
	private void MonthOperator(){
		mMonthText.setText(MONTHS[new Date().getMonth()]);
		Log.d("MONTHS[mTime.month]", ""+MONTHS[new Date().getMonth()]);
	}
	
	private void DayOperator(){
		mDayText.setText(new SimpleDateFormat("dd").format(new Date()));
		Log.d("SimpleDateFormat.format(mTime.monthDay)", ""+new SimpleDateFormat("dd").format(new Date()));
	}
	
	private void TimeOperator(){
		mTimeText.setText(new SimpleDateFormat("HH:mm").format(new Date()));
		Log.d("mTimeText", ""+new SimpleDateFormat("HH:mm").format(new Date()));
	}
	
	private void WeekOperator(){
		Time mTime = new Time();
		mTime.setToNow();
		int sum = 0,i = mTime.weekDay, Day = mTime.monthDay;
		Log.d("week----------->", mTime.weekDay+"");
		for(int j = 0; j<7 ;j++)
			WEEKDAY_SET[j].setBackground(null);
		WEEKDAY_SET[mTime.weekDay].setBackgroundResource(R.drawable.redcircle);
		while(sum<7){
			while(i >= 0){
				WEEKDAY_SET[i].setText(Day+"");
				Log.d("day1----------->", Day+"");
				i--;
				Day--;
				sum++;
			}
			i = mTime.weekDay+1;
			Day = mTime.monthDay+1;
			while(i <7){
				WEEKDAY_SET[i].setText(Day+"");
				Log.d("day2----------->", Day+"");
				i++;
				Day++;
				sum++;
			}
		}
		
		
		
	}
	
	private void initTime(){
		mRunnable = new Runnable(){

			@Override
			public void run() {
				mHandler.postDelayed(mRunnable, 1000);
				MonthOperator();
				DayOperator();
				TimeOperator();
				WeekOperator();
			}
			
		};
		mHandler.post(mRunnable);
	}


	public TextView getmLocationtext() {
		return mLocationtext;
	}


	public void setmLocationtext(TextView mLocationtext) {
		this.mLocationtext = mLocationtext;
	}
	
	
	

}
