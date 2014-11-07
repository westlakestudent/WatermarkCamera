package com.arcsoft.camera.watermark.ui;

import com.arcsoft.camera.systemmgr.UiCmdListener;
import com.arcsoft.camera.watermark.R;
import com.arcsoft.camera.watermark.utils.DensityUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class CameraBar extends RelativeLayout implements OnClickListener, OnTouchListener {

	private Context mContext;
	private ImageButton mAlbumButton;	
	private ImageButton mLocationButton;
	private ImageButton mCaptureButton;
	private LinearLayout mWatermarkButton;
	private UiCmdListener mUiCmdListener;
	private ImageView mWatermarkIcon;
	private TextView mWatermarkText;
	private int mWatermarkId;
	private boolean mbCaptured;
	
	
	private final int BAR_PANEL_PADDING = 20;
	private final int BUTTON_WATERMARK_HEIGHT = 30;
	private final int BUTTON_ALBUM_TEXT_SIZE = 18;
	
	public static final int CAMERA_BAR = 0x1f000001;
	public static final int BUTTON_ALBUM = 0x1f000002;
	public static final int BUTTON_LOCATION = 0x1f000003;
	public static final int BUTTON_CAPTURE = 0x1f000004;
	public static final int BUTTON_WATERMARK = 0x1f000005;	
	
	public static final int BUTTON_ALBUM_CLICK = 0x1f000101;
	public static final int BUTTON_LOCATION_CLICK = 0x1f000102;
	public static final int BUTTON_CAPTURE_CLICK = 0x1f000103;
	public static final int BUTTON_WATERMARK_CLICK = 0x1f000104;
	
	public CameraBar(Context context, int  watermarkId) {
		super(context);
		mContext = context;
		mWatermarkId = watermarkId;
		mbCaptured = false;		
		init();	
	}	

	public CameraBar(Context context, int  watermarkId, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mWatermarkId = watermarkId;
		mbCaptured = false;		
		init();	
	}
	
	public CameraBar(Context context, int  watermarkId, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		mWatermarkId = watermarkId;
		mbCaptured = false;		
		init();	
	}

	private void init() {
		LayoutParams param = null;
		LinearLayout.LayoutParams lParam = null;
		
		setBackgroundResource(R.drawable.category_bg_normal);
		setId(CAMERA_BAR);
		setPadding(DensityUtil.dip2px(mContext, BAR_PANEL_PADDING), DensityUtil.dip2px(mContext, BAR_PANEL_PADDING), DensityUtil.dip2px(mContext, BAR_PANEL_PADDING), DensityUtil.dip2px(mContext, BAR_PANEL_PADDING));
		
		mAlbumButton = new ImageButton(mContext);
		mAlbumButton.setBackgroundResource(R.drawable.ic_album);
		mAlbumButton.setId(BUTTON_ALBUM);
		param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		param.addRule(RelativeLayout.CENTER_VERTICAL);
		param.addRule(RelativeLayout.LEFT_OF, BUTTON_LOCATION);		
		param.rightMargin = DensityUtil.dip2px(mContext, BUTTON_WATERMARK_HEIGHT);
		mAlbumButton.setOnClickListener(this);
		addView(mAlbumButton, param);
		
		mLocationButton = new ImageButton(mContext);
		mLocationButton.setBackgroundResource(R.drawable.ic_gps);
		mLocationButton.setId(BUTTON_LOCATION);
		param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		param.addRule(RelativeLayout.LEFT_OF, BUTTON_CAPTURE);
		param.addRule(RelativeLayout.CENTER_VERTICAL);
		param.rightMargin = DensityUtil.dip2px(mContext, BUTTON_WATERMARK_HEIGHT);
		mLocationButton.setOnClickListener(this);
		addView(mLocationButton, param);
		
		mCaptureButton = new ImageButton(mContext);
		mCaptureButton.setBackgroundResource(R.drawable.camera_normal);
		mCaptureButton.setId(BUTTON_CAPTURE);
		param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		param.addRule(RelativeLayout.CENTER_IN_PARENT);		
		param.rightMargin = DensityUtil.dip2px(mContext, BUTTON_WATERMARK_HEIGHT);
		mCaptureButton.setOnClickListener(this);
		mCaptureButton.setOnTouchListener(this);
		addView(mCaptureButton, param);

		mWatermarkButton = new LinearLayout(mContext);
		mWatermarkButton.setId(BUTTON_WATERMARK);
		param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		param.addRule(RelativeLayout.RIGHT_OF, BUTTON_CAPTURE);
		param.addRule(RelativeLayout.CENTER_VERTICAL);
		mWatermarkButton.setOnClickListener(this);
		addView(mWatermarkButton, param);	
		
		mWatermarkIcon = new ImageView(mContext);
		lParam = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lParam.gravity = Gravity.CENTER_VERTICAL;
		lParam.leftMargin = DensityUtil.dip2px(mContext, 5);
		mWatermarkButton.addView(mWatermarkIcon);
		
		mWatermarkText = new TextView(mContext);
		mWatermarkText.setTextColor(Color.WHITE);
		mWatermarkText.setTextSize(BUTTON_ALBUM_TEXT_SIZE);
		lParam = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lParam.gravity = Gravity.CENTER_VERTICAL;
		mWatermarkButton.addView(mWatermarkText, lParam);		
		setWatermarkButtonIcon(mWatermarkId);
	}

	@Override
	public void onClick(View v) {
		if (null == mUiCmdListener)
			return;

		switch (v.getId()) {
		case BUTTON_ALBUM:
			if (mbCaptured) {
				mAlbumButton.setBackgroundResource(R.drawable.ic_album);
				mCaptureButton.setBackgroundResource(R.drawable.camera_normal);
				mbCaptured = false;
			}				
			mUiCmdListener.onUiCmd(BUTTON_ALBUM_CLICK, mbCaptured);
			break;
		case BUTTON_LOCATION:
			mUiCmdListener.onUiCmd(BUTTON_LOCATION_CLICK, null);
			break;
		case BUTTON_CAPTURE:
			if (mbCaptured) {		
				mCaptureButton.setBackgroundResource(R.drawable.camera_normal);
				mAlbumButton.setBackgroundResource(R.drawable.ic_album);					
			} else {
				//mCaptureButton.setBackgroundResource(R.drawable.ic_done_normal);
				//mAlbumButton.setBackgroundResource(R.drawable.btn_cancel_normal);
			} 				
			mbCaptured = mbCaptured ? false : true;
			mUiCmdListener.onUiCmd(BUTTON_CAPTURE_CLICK, mbCaptured);
			break;		 
		case BUTTON_WATERMARK:
			mUiCmdListener.onUiCmd(BUTTON_WATERMARK_CLICK, mWatermarkId);
			break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {		
		if (event.getAction() == MotionEvent.ACTION_DOWN)
			if (v == mCaptureButton) 
				if (mbCaptured) 
					mCaptureButton.setBackgroundResource(R.drawable.camera_press);
//				else
//					mCaptureButton.setBackgroundResource(R.drawable.ic_done_press);
		
		if (event.getAction() == MotionEvent.ACTION_UP)
			if (v == mCaptureButton) 
				if (mbCaptured) 
					mCaptureButton.setBackgroundResource(R.drawable.camera_normal);
//				else
//					mCaptureButton.setBackgroundResource(R.drawable.ic_done_normal);
		
		return false;
	}
	
	public void setUiCmdListener(UiCmdListener l) {
		mUiCmdListener = l;
	}
	
	public void setAlbumButtonIcon(int resId) {
		mAlbumButton.setBackgroundResource(resId);
	}
	
	public void setCaptureButtonIcon(int resId) {
		mCaptureButton.setBackgroundResource(resId);
	}
	
	public void setWatermarkButtonIcon(int id) {
		mWatermarkId = id;
		
		switch (id) {
		case WaterTemplateBar.LocationBtn_Id:
			mWatermarkIcon.setBackgroundResource(R.drawable.place);
			mWatermarkText.setText("地点");
			break;
		case WaterTemplateBar.TimeBtn_Id:
			mWatermarkIcon.setBackgroundResource(R.drawable.time);
			mWatermarkText.setText("时间");
			break;
		case WaterTemplateBar.FoodBtn_Id:
			mWatermarkIcon.setBackgroundResource(R.drawable.food);
			mWatermarkText.setText("食物");
			break;
		case WaterTemplateBar.WeatherBtn_Id:
			mWatermarkIcon.setBackgroundResource(R.drawable.weather);
			mWatermarkText.setText("天气");
			break;
		case WaterTemplateBar.MoodBtn_Id:
			mWatermarkIcon.setBackgroundResource(R.drawable.expression);
			mWatermarkText.setText("心情");
			break;
		case WaterTemplateBar.DefaultBtn_Id:
			mWatermarkIcon.setBackgroundResource(R.drawable.watermark);
			mWatermarkText.setText("水印库");
			break;	
		default: 
			mWatermarkIcon.setBackgroundResource(R.drawable.watermark);
			mWatermarkText.setText("水印库");
		}
	}
	
}
