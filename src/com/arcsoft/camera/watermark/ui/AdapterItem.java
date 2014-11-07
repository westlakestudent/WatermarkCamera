package com.arcsoft.camera.watermark.ui;

import com.arcsoft.camera.watermark.utils.DensityUtil;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdapterItem extends LinearLayout{

	private Context mContext = null;
	private ImageView mImageView = null;
	private TextView mTextView = null;
	public AdapterItem(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	private void initView(){
		LinearLayout.LayoutParams Params =null;
		setOrientation(LinearLayout.VERTICAL);
		mImageView = new ImageView(mContext);
		Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(mContext, 90));
		addView(mImageView, Params);
		
		mTextView = new TextView(mContext);
		Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		Params.gravity = Gravity.CENTER_HORIZONTAL;
		addView(mTextView, Params);
	}
	
	public void setResource(String s,int i){
		mImageView.setBackgroundResource(i);
		mTextView.setText(s);
		
	}
}
