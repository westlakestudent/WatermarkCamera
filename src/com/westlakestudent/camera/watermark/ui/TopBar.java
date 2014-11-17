package com.westlakestudent.camera.watermark.ui;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.westlakestudent.camera.systemmgr.UiCmdListener;
import com.westlakestudent.camera.watermark.R;
import com.westlakestudent.camera.watermark.utils.ScaleUtils;

public class TopBar extends RelativeLayout{

	private UiCmdListener mUiCmdListener =null;
	private Context mContext = null;
	private final static int TopBar_left_bt_id = 1000 , TopBar_right_bt_id = 2000 , TopBar_center_bt_id = 3000;
	private Button mLeftButton =null ;
	private Button mRightButton = null;
	private Button mCenterButton = null; 
	private int PARAM_WIDTH = 70 , PARAM_HEIGHT = 45 ,TOP_MARGIN = 10 ,LEFT_MARGIN = 35 ;
	public final static int FLASH_AUTO = 1 , FLASH_ON = 2 , FLASH_OFF = 3 ,BACK_CAMERA = 4 ,FRONT_CAMERA = 5;
	private int FLASH = 0 ;
	
	public TopBar(Context context) {
		super(context);
		mContext = context;
		initView();
		Log.d("PARAM_WIDTH", "---->"+PARAM_WIDTH);
		Log.d("PARAM_HEIGHT", "---->"+PARAM_HEIGHT);
		Log.d("TOP_MARGIN", "---->"+TOP_MARGIN);
		
		
	}
	
	public void setUiCmdListener(UiCmdListener l){
		mUiCmdListener = l;
	}
	
	private void initView(){
		RelativeLayout.LayoutParams Params = null;
		mLeftButton = new Button(mContext);
		mLeftButton.setId(TopBar_left_bt_id);				
		Params = new RelativeLayout.LayoutParams(ScaleUtils.scale(PARAM_WIDTH),ScaleUtils.scale(PARAM_HEIGHT));
		addView(mLeftButton,Params);
		
		mRightButton = new Button(mContext);
		mRightButton.setId(TopBar_right_bt_id);
		Params = new RelativeLayout.LayoutParams(ScaleUtils.scale(PARAM_WIDTH),ScaleUtils.scale(PARAM_HEIGHT));
		Params.addRule(RelativeLayout.RIGHT_OF, TopBar_left_bt_id);
		addView(mRightButton,Params);
		
		mCenterButton = new Button(mContext);
		mCenterButton.setId(TopBar_center_bt_id);
		Params = new RelativeLayout.LayoutParams(ScaleUtils.scale(PARAM_WIDTH),ScaleUtils.scale(PARAM_HEIGHT));
		Params.addRule(ALIGN_PARENT_LEFT);
		Params.leftMargin = ScaleUtils.scale(LEFT_MARGIN);
		addView(mCenterButton,Params);
		
		mLeftButton.setOnClickListener(leftListener);
		mRightButton.setOnClickListener(rightListener);
		mCenterButton.setOnClickListener(centerListener);
		DefaultButtonBackground(mLeftButton, mRightButton,mCenterButton);	
	
	}

	private void DefaultButtonBackground(Button l,Button r,Button c){
		l.setBackgroundResource(R.drawable.ic_flash_full_auto);
		r.setBackgroundResource(R.drawable.ic_switch_cam);
		c.setBackgroundResource(R.drawable.cam_single_normal);
		c.setVisibility(GONE);
		FLASH = FLASH_AUTO;
		
	}
	
	OnClickListener leftListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (FLASH) {
			case FLASH_AUTO:
				mLeftButton.setBackgroundResource(R.drawable.ic_flash_full_on);
				FLASH = FLASH_ON ;
				if (null != mUiCmdListener) 
					mUiCmdListener.onUiCmd(FLASH, null);		
				break;
			case FLASH_ON:
				mLeftButton.setBackgroundResource(R.drawable.ic_flash_full_off);
				FLASH = FLASH_OFF ;
				if (null != mUiCmdListener) 
					mUiCmdListener.onUiCmd(FLASH, null);				
				break;
			case FLASH_OFF:
				mLeftButton.setBackgroundResource(R.drawable.ic_flash_full_auto);
				FLASH = FLASH_AUTO ;
				if (null != mUiCmdListener) 
					mUiCmdListener.onUiCmd(FLASH, null);				
				break;
			}
			
		}
	};
	
	OnClickListener rightListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			mCenterButton.setVisibility(VISIBLE);
			mLeftButton.setVisibility(GONE);
			mRightButton.setVisibility(GONE);
			if (null != mUiCmdListener) 
				mUiCmdListener.onUiCmd(FRONT_CAMERA, null);
		}
	};
	
	OnClickListener centerListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			mCenterButton.setVisibility(GONE);
			mLeftButton.setVisibility(VISIBLE);
			mRightButton.setVisibility(VISIBLE);
			if (null != mUiCmdListener) 
				mUiCmdListener.onUiCmd(BACK_CAMERA, null);
			
		}
	};
	
}
