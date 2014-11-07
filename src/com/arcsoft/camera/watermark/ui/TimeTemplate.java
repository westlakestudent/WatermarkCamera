package com.arcsoft.camera.watermark.ui;


import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arcsoft.camera.systemmgr.UiCmdListener;
import com.arcsoft.camera.watermark.R;
import com.arcsoft.camera.watermark.utils.ScaleUtils;

public class TimeTemplate extends FrameLayout{

	private UiCmdListener mUiCmdListener = null;
	private Context mContext = null;
	private Time mTime = null;
	private Handler mHandler = null;
	private Runnable mRunnable = null;
	private TextView mYearFirstText = null;
	private TextView mYearSecondText = null;
	private TextView mYearThirdText = null;
	private TextView mYearFourthText = null;
	private TextView mLeftSinglePointText = null;
	private TextView mRightSinglePointText = null;
	private TextView mLeftMonthText = null;
	private TextView mRightMonthText = null;
	private TextView mLeftDayText = null;
	private TextView mRightDayText = null;
	private TextView mLeftHourText = null;
	private TextView mRightHourText = null;
	private TextView mLeftMinuteText = null;
	private TextView mRightMinuteText = null;
	private TextView mLeftSecondText = null;
	private TextView mRightSecondText = null;
	private TextView mLeftDoublePointtext = null;
	private TextView mRightDoublePointtext = null;
	private TextView mUserDefineText = null;
	private TextView mLocationText = null;
	private ImageView mLocationIamge = null;
	private final static int FIRST_YEAR = 0xc0000001, SECOND_YEAR = 0xc0000002, THIRD_YEAR = 0xc0000003, FOURTH_YEAR = 0xc0000004,
							 LEFT_SINGLE_POI = 0xc0000005, LEFT_DOUBLE_POI = 0xc0000006, LEFT_MONTH = 0xc0000007, RIGHT_MONTH = 0xc0000008,
							 LEFT_DAY = 0xc0000009, RIGHT_DAY = 0xc000000a, LEFT_HOUR = 0xc000000b, RIGHT_HOUR = 0xc000000c, LEFT_MINUTE = 0xc000000d,
							 RIGHT_MINUTE = 0xc000000e, LEFT_SECOND = 0xc000000f, RIGHT_SECONG = 0xc0000010, LOCA_TEXT = 0xc0000011,
							 LOCA_IMAGE = 0xc0000012 , RIGHT_SINGLE_POI = 0xc0000013, RIGHT_DOUBLE_POI = 0xc0000014 ,CONTAINER = 0xc0000015,
							 USER_DEFINE = 0xc0000016;
	
	private final int[] CLOCK_DATA_RES = new int[]{R.drawable.clock_0, R.drawable.clock_1, R.drawable.clock_2,
			R.drawable.clock_3, R.drawable.clock_4, R.drawable.clock_5, R.drawable.clock_6, R.drawable.clock_7,
			R.drawable.clock_8, R.drawable.clock_9};
	
	public TimeTemplate(Context context) {
		super(context);
		mContext = context;
		mHandler = new Handler();
		initView();
		initTime();
	}

	private void initView(){
		RelativeLayout.LayoutParams params = null;
		RelativeLayout mSmallContainer = new RelativeLayout(mContext);
		RelativeLayout mBigContainer  = new RelativeLayout(mContext);
		mSmallContainer.setId(CONTAINER);
		mSmallContainer.setBackgroundResource(R.drawable.clock_bg);
		params = new RelativeLayout.LayoutParams(ScaleUtils.scale(300), ScaleUtils.scale(120));
		params.leftMargin = ScaleUtils.scale(20);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		mBigContainer.addView(mSmallContainer,params);
		
		mYearFirstText = new TextView(mContext);
		mYearFirstText.setId(FIRST_YEAR);
		mYearFirstText.setBackgroundResource(CLOCK_DATA_RES[2]);
		params = new RelativeLayout.LayoutParams(ScaleUtils.scale(10), ScaleUtils.scale(26));
		params.leftMargin = ScaleUtils.scale(86);
		params.topMargin = ScaleUtils.scale(15);
		mSmallContainer.addView(mYearFirstText, params);
		
		mYearSecondText = new TextView(mContext);
		mYearSecondText.setId(SECOND_YEAR);
		mYearSecondText.setBackgroundResource(CLOCK_DATA_RES[0]);
		params = new RelativeLayout.LayoutParams(ScaleUtils.scale(10), ScaleUtils.scale(26));
		params.topMargin = ScaleUtils.scale(15);
		params.leftMargin = ScaleUtils.scale(3);
		params.addRule(RelativeLayout.RIGHT_OF, FIRST_YEAR);
		mSmallContainer.addView(mYearSecondText, params);
		
		mYearThirdText = new TextView(mContext);
		mYearThirdText.setId(THIRD_YEAR);
		mYearThirdText.setBackgroundResource(CLOCK_DATA_RES[1]);
		params = new RelativeLayout.LayoutParams(ScaleUtils.scale(10), ScaleUtils.scale(26));
		params.topMargin = ScaleUtils.scale(15);
		params.leftMargin = ScaleUtils.scale(3);
		params.addRule(RelativeLayout.RIGHT_OF, SECOND_YEAR);
		mSmallContainer.addView(mYearThirdText, params);
		
		mYearFourthText = new TextView(mContext);
		mYearFourthText.setId(FOURTH_YEAR);
		mYearFourthText.setBackgroundResource(CLOCK_DATA_RES[3]);
		params = new RelativeLayout.LayoutParams(ScaleUtils.scale(10), ScaleUtils.scale(26));
		params.topMargin = ScaleUtils.scale(15);
		params.leftMargin = ScaleUtils.scale(3);
		params.addRule(RelativeLayout.RIGHT_OF, THIRD_YEAR);
		mSmallContainer.addView(mYearFourthText, params);
		
		mLeftSinglePointText = new TextView(mContext);
		mLeftSinglePointText.setId(LEFT_SINGLE_POI);
		mLeftSinglePointText.setBackgroundResource(R.drawable.clock_point);
		params = new RelativeLayout.LayoutParams(ScaleUtils.scale(13), ScaleUtils.scale(46));
		params.topMargin = ScaleUtils.scale(3);
		params.leftMargin = ScaleUtils.scale(2);
		params.addRule(RelativeLayout.RIGHT_OF, FOURTH_YEAR);
		mSmallContainer.addView(mLeftSinglePointText, params);
		
		mLeftMonthText = new TextView(mContext);
		mLeftMonthText.setId(LEFT_MONTH);
		mLeftMonthText.setBackgroundResource(CLOCK_DATA_RES[0]);
		params = new RelativeLayout.LayoutParams(ScaleUtils.scale(10), ScaleUtils.scale(26));
		params.topMargin = ScaleUtils.scale(15);
		params.leftMargin = ScaleUtils.scale(3);
		params.addRule(RelativeLayout.RIGHT_OF, LEFT_SINGLE_POI);
		mSmallContainer.addView(mLeftMonthText, params);
		
		mRightMonthText = new TextView(mContext);
		mRightMonthText.setId(RIGHT_MONTH);
		mRightMonthText.setBackgroundResource(CLOCK_DATA_RES[9]);
		params = new RelativeLayout.LayoutParams(ScaleUtils.scale(10), ScaleUtils.scale(26));
		params.topMargin = ScaleUtils.scale(15);
		params.leftMargin = ScaleUtils.scale(3);
		params.addRule(RelativeLayout.RIGHT_OF, LEFT_MONTH);
		mSmallContainer.addView(mRightMonthText, params);
		
		mRightSinglePointText = new TextView(mContext);
		mRightSinglePointText.setId(RIGHT_SINGLE_POI);
		mRightSinglePointText.setBackgroundResource(R.drawable.clock_point);
		params = new RelativeLayout.LayoutParams(ScaleUtils.scale(13), ScaleUtils.scale(46));
		params.topMargin = ScaleUtils.scale(3);
		params.leftMargin = ScaleUtils.scale(2);
		params.addRule(RelativeLayout.RIGHT_OF, RIGHT_MONTH);
		mSmallContainer.addView(mRightSinglePointText, params);
		
		mLeftDayText =new TextView(mContext);
		mLeftDayText.setId(LEFT_DAY);
		mLeftDayText.setBackgroundResource(CLOCK_DATA_RES[0]);
		params = new RelativeLayout.LayoutParams(ScaleUtils.scale(10), ScaleUtils.scale(26));
		params.topMargin = ScaleUtils.scale(15);
		params.leftMargin = ScaleUtils.scale(195);
		mSmallContainer.addView(mLeftDayText, params);
		
		mRightDayText = new TextView(mContext);
		mRightDayText.setId(RIGHT_DAY);
		mRightDayText.setBackgroundResource(CLOCK_DATA_RES[2]);
		params = new RelativeLayout.LayoutParams(ScaleUtils.scale(10), ScaleUtils.scale(26));
		params.topMargin = ScaleUtils.scale(15);
		params.leftMargin = ScaleUtils.scale(3);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.addRule(RelativeLayout.RIGHT_OF, LEFT_DAY);
		mSmallContainer.addView(mRightDayText, params);
		
		mLeftHourText = new TextView(mContext);
		mLeftHourText.setId(LEFT_HOUR);
		mLeftHourText.setBackgroundResource(CLOCK_DATA_RES[1]);
		params = new RelativeLayout.LayoutParams(ScaleUtils.scale(30), ScaleUtils.scale(48));
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.bottomMargin = ScaleUtils.scale(19);
		params.leftMargin = ScaleUtils.scale(30);
		mSmallContainer.addView(mLeftHourText, params);
		
		mRightHourText = new TextView(mContext);
		mRightHourText.setId(RIGHT_HOUR);
		mRightHourText.setBackgroundResource(CLOCK_DATA_RES[3]);
		params = new RelativeLayout.LayoutParams(ScaleUtils.scale(30), ScaleUtils.scale(48));
		params.bottomMargin = ScaleUtils.scale(19);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.RIGHT_OF, LEFT_HOUR);
		mSmallContainer.addView(mRightHourText, params);
		
		mLeftDoublePointtext = new TextView(mContext);
		mLeftDoublePointtext.setId(LEFT_DOUBLE_POI);
		mLeftDoublePointtext.setBackgroundResource(R.drawable.clock_colon);
		params = new RelativeLayout.LayoutParams(ScaleUtils.scale(20), ScaleUtils.scale(48));
		params.bottomMargin = ScaleUtils.scale(19);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.RIGHT_OF, RIGHT_HOUR);
		mSmallContainer.addView(mLeftDoublePointtext, params);
		
		mLeftMinuteText = new TextView(mContext);
		mLeftMinuteText.setId(LEFT_MINUTE);
		mLeftMinuteText.setBackgroundResource(CLOCK_DATA_RES[0]);
		params = new RelativeLayout.LayoutParams(ScaleUtils.scale(30), ScaleUtils.scale(48));
		params.bottomMargin = ScaleUtils.scale(19);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.RIGHT_OF, LEFT_DOUBLE_POI);
		mSmallContainer.addView(mLeftMinuteText, params);
		
		mRightMinuteText = new TextView(mContext);
		mRightMinuteText.setId(RIGHT_MINUTE);
		mRightMinuteText.setBackgroundResource(CLOCK_DATA_RES[6]);
		params = new RelativeLayout.LayoutParams(ScaleUtils.scale(30), ScaleUtils.scale(48));
		params.bottomMargin = ScaleUtils.scale(19);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.RIGHT_OF, LEFT_MINUTE);
		mSmallContainer.addView(mRightMinuteText, params);
		
		mRightDoublePointtext = new TextView(mContext);
		mRightDoublePointtext.setId(RIGHT_DOUBLE_POI);
		mRightDoublePointtext.setBackgroundResource(R.drawable.clock_colon);
		params = new RelativeLayout.LayoutParams(ScaleUtils.scale(20), ScaleUtils.scale(48));
		params.bottomMargin = ScaleUtils.scale(19);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.RIGHT_OF, RIGHT_MINUTE);
		mSmallContainer.addView(mRightDoublePointtext, params);
		
		mLeftSecondText = new TextView(mContext);
		mLeftSecondText.setId(LEFT_SECOND);
		mLeftSecondText.setBackgroundResource(CLOCK_DATA_RES[0]);
		params = new RelativeLayout.LayoutParams(ScaleUtils.scale(30), ScaleUtils.scale(48));
		params.bottomMargin = ScaleUtils.scale(19);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.RIGHT_OF, RIGHT_DOUBLE_POI);
		mSmallContainer.addView(mLeftSecondText, params);
		
		mRightSecondText = new TextView(mContext);
		mRightSecondText.setId(RIGHT_SECONG);
		mRightSecondText.setBackgroundResource(CLOCK_DATA_RES[4]);
		params = new RelativeLayout.LayoutParams(ScaleUtils.scale(30), ScaleUtils.scale(48));
		params.bottomMargin = ScaleUtils.scale(19);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.RIGHT_OF, LEFT_SECOND);
		mSmallContainer.addView(mRightSecondText, params);
		
		
		
		mUserDefineText = new TextView(mContext);
		mUserDefineText.setId(USER_DEFINE);
		mUserDefineText.setText(R.string.ttt);
		mUserDefineText.setTextSize(16);
		mUserDefineText.setTextColor(Color.WHITE);
		params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.leftMargin = ScaleUtils.scale(40);
		params.addRule(RelativeLayout.BELOW,CONTAINER);
		mBigContainer.addView(mUserDefineText, params);
		mUserDefineText.setOnTouchListener(Listener);
		
		mLocationIamge = new ImageView(mContext);
		mLocationIamge.setId(LOCA_IMAGE);
		mLocationIamge.setBackgroundResource(R.drawable.likehate_location);
		params = new RelativeLayout.LayoutParams(ScaleUtils.scale(25), ScaleUtils.scale(40));
		params.leftMargin = ScaleUtils.scale(40);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.addRule(RelativeLayout.BELOW, USER_DEFINE);
		mBigContainer.addView(mLocationIamge, params);
		
		mLocationText = new TextView(mContext);
		mLocationText.setId(LOCA_TEXT);
		mLocationText.setText(R.string.sky);
		mLocationText.setTextSize(20);
		mLocationText.setTextColor(Color.WHITE);
		params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.BELOW,USER_DEFINE);
		params.addRule(RelativeLayout.RIGHT_OF, LOCA_IMAGE);
		mBigContainer.addView(mLocationText, params);
		addView(mBigContainer);
		
		
		
		
	}
	
	
	private void YearOperator(){
		Log.d("mTime.getYear()", ""+mTime.year);
		
		mYearFirstText.setBackgroundResource(CLOCK_DATA_RES[mTime.year/1000]);
		mYearSecondText.setBackgroundResource(CLOCK_DATA_RES[(mTime.year%1000)/100]);
		mYearThirdText.setBackgroundResource(CLOCK_DATA_RES[(mTime.year%100)/10]);
		mYearFourthText.setBackgroundResource(CLOCK_DATA_RES[mTime.year%10]);
	}
	
	
	private void MonthOperator(){
		Log.d("mTime.getMonth()%10", ""+mTime.month%10);
		
		mRightMonthText.setBackgroundResource(CLOCK_DATA_RES[mTime.month%10+1]);
		mLeftMonthText.setBackgroundResource(CLOCK_DATA_RES[mTime.month/10]);		
	}
	
	private void DayOperator(){
		mLeftDayText.setBackgroundResource(CLOCK_DATA_RES[mTime.monthDay/10]);
		mRightDayText.setBackgroundResource(CLOCK_DATA_RES[mTime.monthDay%10]);
	}
	
	private void HourOperator(){
		mLeftHourText.setBackgroundResource(CLOCK_DATA_RES[mTime.hour/10]);
		mRightHourText.setBackgroundResource(CLOCK_DATA_RES[mTime.hour%10]);
	}
	
	private void MinuteOperator(){
		mLeftMinuteText.setBackgroundResource(CLOCK_DATA_RES[mTime.minute/10]);
		mRightMinuteText.setBackgroundResource(CLOCK_DATA_RES[mTime.minute%10]);
	}
	
	private void SecondOperator(){
		mLeftSecondText.setBackgroundResource(CLOCK_DATA_RES[mTime.second/10]);
		mRightSecondText.setBackgroundResource(CLOCK_DATA_RES[mTime.second%10]);
	}
	

	private void initTime(){
		mRunnable = new Runnable(){

			@Override
			public void run() {
				mHandler.postDelayed(mRunnable, 1000);
				mTime = new Time();
				mTime.setToNow();
				YearOperator();
				MonthOperator();
				DayOperator();
				HourOperator();
				MinuteOperator();
				SecondOperator();
			}
			
		};
		mHandler.post(mRunnable);
	}
	
	public void setUiListener(UiCmdListener l){
		mUiCmdListener = l;
	}
	
	OnTouchListener Listener = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			mUiCmdListener.onUiCmd(999, null);
			EditView vv = new EditView(mContext); 
			//EditText mEditText = vv.getmEditText();
		//	InputMethodManager mManger = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
			//mManger.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			//mManger.showSoftInput(mEditText,0);
//			//mEditText.setBackgroundResource(R.drawable.edt_round);
//			mEditText.requestFocus();
			vv.requestWindowFeature(Window.FEATURE_NO_TITLE);
			vv.show();
			if(vv.getCurrentFocus() == null)
				vv.cancel();
//			InputMethodManager mManger = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//			//mManger.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//			mManger.showSoftInput(mEditText,0);
//			//mManger.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
			return true;
		}
	};
	
}
