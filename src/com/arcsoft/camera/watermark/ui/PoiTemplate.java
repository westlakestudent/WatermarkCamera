package com.arcsoft.camera.watermark.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.arcsoft.camera.systemmgr.UiCmdListener;
import com.arcsoft.camera.watermark.R;
import com.arcsoft.camera.watermark.utils.DensityUtil;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PoiTemplate extends RelativeLayout implements OnClickListener, UiCmdListener {
	
	private Context mContext;
	private ImageView mPoiIcon;
	private TextView mLocationText;
	public TextView getmLocationText() {
		return mLocationText;
	}

	private TextView mTimeText;
	private UiCmdListener mUiCmdListener;
	private final int TEXT_VIEW_ID = 0x0001;
	public static final int CLICK = 0x1001;
	
	public PoiTemplate(Context context) {
		super(context);
		mContext = context;
		init();
	}
	
	private void init() {
		LayoutParams rParams = null;
		FrameLayout.LayoutParams fParams = null;
		LinearLayout.LayoutParams lParams = null;	
				
		setPadding(DensityUtil.dip2px(mContext, 20), 0, DensityUtil.dip2px(mContext, 20), 0);
		
		FrameLayout iconContainer = new FrameLayout(mContext);
		iconContainer.setId(TEXT_VIEW_ID);
		rParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rParams.addRule(RelativeLayout.ALIGN_LEFT);
		rParams.rightMargin = DensityUtil.dip2px(mContext, 10);
		iconContainer.setOnClickListener(this);
		addView(iconContainer, rParams);
		
		ImageView poiBackground = new ImageView(mContext);		
		poiBackground.setBackgroundResource(R.drawable.poi_background);
		fParams = new FrameLayout.LayoutParams(DensityUtil.dip2px(mContext, 80), DensityUtil.dip2px(mContext, 85));
		iconContainer.addView(poiBackground, fParams);
		
		mPoiIcon = new ImageView(mContext);
		mPoiIcon.setBackgroundResource(R.drawable.ic_loc_place_normal);
		fParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		fParams.gravity = Gravity.CENTER_HORIZONTAL;		
		fParams.topMargin = DensityUtil.dip2px(mContext, 20);;
		iconContainer.addView(mPoiIcon, fParams);
		
		LinearLayout textContainer = new LinearLayout(mContext);
		textContainer.setOrientation(LinearLayout.VERTICAL);
		rParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rParams.addRule(RelativeLayout.RIGHT_OF, TEXT_VIEW_ID);
		rParams.topMargin = DensityUtil.dip2px(mContext, 10);
		textContainer.setOnClickListener(this);
		addView(textContainer, rParams);
						
		mLocationText = new TextView(mContext);
		mLocationText.setText("Œª÷√");
		mLocationText.setTextColor(Color.parseColor("#ffffff"));
		mLocationText.setTextSize(20);
		lParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);		
		textContainer.addView(mLocationText, lParams);
		
		mTimeText = new TextView(mContext);
		mTimeText.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
		mTimeText.setTextColor(Color.parseColor("#ffffff"));
		mTimeText.setTextSize(15);
		lParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lParams.topMargin = DensityUtil.dip2px(mContext, 5);
		textContainer.addView(mTimeText, lParams);		
	}

	@Override
	public int onUiCmd(int key, Object obj) {
		return 0;
	}
	
	@Override
	public void onClick(View v) {
		if (mUiCmdListener != null)
			mUiCmdListener.onUiCmd(CLICK, null);
	}
	
	public void setUiCmdListener(UiCmdListener l) {
		mUiCmdListener = l;
	}
	
	public void setLocationText(CharSequence text) {
		mLocationText.setText(text);
	}
	
	public void setTimeText(CharSequence text) {
		mTimeText.setText(text);
	}
	
	public void setPoiIcon(int resid) {
		mPoiIcon.setBackgroundResource(resid);
	}
	
}
