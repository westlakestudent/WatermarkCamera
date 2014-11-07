package com.arcsoft.camera.watermark.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.arcsoft.camera.systemmgr.UiCmdListener;
import com.arcsoft.camera.watermark.R;
import com.arcsoft.camera.watermark.utils.DensityUtil;

public class FreeGoTemplate extends LinearLayout implements OnClickListener, UiCmdListener  {
	
	private Context mContext;
	private UiCmdListener mUiCmdListener;
	private ImageView mBackgroundImage;
	private TextView mDateText;
	private TextView mLocationText;
	public TextView getmLocationText() {
		return mLocationText;
	}

	public static final int ON_CLICK = 0x1002;
	
	public FreeGoTemplate(Context context) {
		super(context);
		mContext = context;
		init();
	}
	
	private void init() {
		LayoutParams params = null;
		
		setOrientation(LinearLayout.VERTICAL);
		setPadding(DensityUtil.dip2px(mContext, 20), 0, DensityUtil.dip2px(mContext, 20), 0);
		
		mBackgroundImage = new ImageView(mContext);
		mBackgroundImage.setBackgroundResource(R.drawable.freego);
		params = new LayoutParams(DensityUtil.dip2px(mContext, 200), DensityUtil.dip2px(mContext, 50));
		addView(mBackgroundImage, params);
		
		LinearLayout textContainer = new LinearLayout(mContext);
		textContainer.setOrientation(LinearLayout.HORIZONTAL);
		params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.topMargin = DensityUtil.dip2px(mContext, 5);
		addView(textContainer, params);
		
		mDateText = new TextView(mContext);
		mDateText.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		mDateText.setTextColor(Color.parseColor("#ffffff"));
		mDateText.setTextSize(15);
		params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.rightMargin = DensityUtil.dip2px(mContext, 20);
		textContainer.addView(mDateText, params);
		
		mLocationText = new TextView(mContext);
		mLocationText.setText("Œª÷√");
		mLocationText.setTextColor(Color.parseColor("#ffffff"));
		mLocationText.setTextSize(20);
		params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);		
		textContainer.addView(mLocationText, params);
	}


	@Override
	public int onUiCmd(int key, Object obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onClick(View v) {
		if (mUiCmdListener != null)
			mUiCmdListener.onUiCmd(ON_CLICK, null);		
	}
	
	public void setUiCmdListener(UiCmdListener l) {
		mUiCmdListener = l;
	}
	
	public void setLocationText(CharSequence text) {
		mLocationText.setText(text);
	}
	
	public void setDateText(CharSequence text) {
		mDateText.setText(text);
	}
	
	public void setBackgroundImage(int resid) {
		mBackgroundImage.setBackgroundResource(resid);
	}
}
