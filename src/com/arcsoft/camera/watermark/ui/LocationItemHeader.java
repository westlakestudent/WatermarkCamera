package com.arcsoft.camera.watermark.ui;

import com.arcsoft.camera.watermark.R;
import com.arcsoft.camera.watermark.utils.DensityUtil;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LocationItemHeader extends LinearLayout {

	private Context mContext;
	private ImageView mHeadIcon;
	private ProgressBar mProgressBar;
	private RotateAnimation mAnimArrow;
	private RotateAnimation mAnimReverseArrow;
	private TextView mHeadText;
	private TextView mHeadTips;
	
	private final String LOCATION_ITEM_HEAD_PULL_TO_REFRESH = "下拉刷新";
	private final String LOCATION_ITEM_HEAD_RELEASH_TO_REFRESH = "释放立即刷新";
	private final String LOCATION_ITEM_HEAD_REFRESHING = "刷新中……";
	
	public LocationItemHeader(Context context) {
		super(context);
		mContext = context;
		init();
	}

	private void init() {
		LayoutParams lparam = null;			
		
		RelativeLayout panel = new RelativeLayout(mContext);
		panel.setBackgroundColor(Color.parseColor("#cccccc"));
		panel.setPadding(DensityUtil.dip2px(mContext, 60), DensityUtil.dip2px(mContext, 5), DensityUtil.dip2px(mContext, 60), DensityUtil.dip2px(mContext, 5));
		lparam = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);	    
		lparam.gravity = Gravity.CENTER_VERTICAL;
		addView(panel, lparam);
		
		FrameLayout iconView = new FrameLayout(mContext);
		RelativeLayout.LayoutParams rparam = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rparam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		rparam.addRule(RelativeLayout.CENTER_VERTICAL);
	    panel.addView(iconView, rparam);
		
		mHeadIcon = new ImageView(mContext);
		mHeadIcon.setBackgroundResource(R.drawable.ic_list_view_pull_arrow);
		mHeadIcon.setMinimumWidth(DensityUtil.dip2px(mContext, 20));
		mHeadIcon.setMinimumHeight(DensityUtil.dip2px(mContext, 30));
		FrameLayout.LayoutParams fparam = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		fparam.gravity = Gravity.CENTER;
		iconView.addView(mHeadIcon, fparam);
		
		mProgressBar = new ProgressBar(mContext);	
		fparam = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		fparam.gravity = Gravity.CENTER;	
		fparam.width = DensityUtil.dip2px(mContext, 20);
		fparam.height = DensityUtil.dip2px(mContext, 20);
		iconView.addView(mProgressBar, fparam);
		
		LinearLayout headLabel = new LinearLayout(mContext);
		headLabel.setOrientation(LinearLayout.VERTICAL);
	    headLabel.setPadding(DensityUtil.dip2px(mContext, 15), DensityUtil.dip2px(mContext, 10), DensityUtil.dip2px(mContext, 10), DensityUtil.dip2px(mContext, 10));
	    rparam = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	    rparam.addRule(RelativeLayout.CENTER_IN_PARENT);
	    panel.addView(headLabel, rparam);
	    
	    mHeadText = new TextView(mContext);	    
	    mHeadText.setText(LOCATION_ITEM_HEAD_PULL_TO_REFRESH);	    
	    mHeadText.setTextSize(20);
	    lparam = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);	    
	    headLabel.addView(mHeadText, lparam);
	    
	    mHeadTips = new TextView(mContext);	    
    	mHeadTips.setText(LOCATION_ITEM_HEAD_RELEASH_TO_REFRESH);
    	mHeadTips.setTextColor(Color.parseColor("#cc6600"));
    	mHeadTips.setTextSize(10);
    	lparam = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);	
	    headLabel.addView(mHeadTips, lparam);	
	    
	    mAnimArrow = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mAnimArrow.setInterpolator(new LinearInterpolator());
		mAnimArrow.setDuration(250);
		mAnimArrow.setFillAfter(true);
		mAnimReverseArrow = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mAnimReverseArrow.setInterpolator(new LinearInterpolator());
		mAnimReverseArrow.setDuration(200);
	}
	
	public void setProgressBarVisibility(int visibility) {
		mProgressBar.setVisibility(visibility);
	}
	
	public void setIconVisibility(int visibility) {
		mHeadIcon.setVisibility(visibility);
	}
	
	public void clearIconAnimation() {
		mHeadIcon.clearAnimation();
	}
	
	public void startIconAnimation() {
		clearIconAnimation();
		mHeadIcon.startAnimation(mAnimArrow); 
	}
	
	public void startIconReverseAnimation() {
		clearIconAnimation();
		mHeadIcon.startAnimation(mAnimReverseArrow); 
	}
	
	public void setHeadText(CharSequence text) {
		mHeadText.setText(text);
	}
	
	public void setHeadTips(CharSequence text) {
		mHeadTips.setText(text);
	}
}
