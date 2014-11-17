package com.westlakestudent.camera.watermark.ui;


import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.westlakestudent.camera.systemmgr.UiCmdListener;
import com.westlakestudent.camera.watermark.R;


public class WaterFlipper extends FrameLayout implements OnGestureListener{

	
	private Context mContext = null;
	private GestureDetector mDetector = null;
	private UiCmdListener mUiCmdListener = null;
	private LikeorHateTemplate mLikeorHateTemplate = null;
	private CartoonTemplate mCartoonTemplate = null;
	private BubbleTemplate mBubbleTemplate = null;
	private BeerTemplate mBeerTemplate = null;
	private PoiTemplate mPoiTemplate = null;
	private FreeGoTemplate mFreeGoTemplate = null;
	private TimeTemplate mTimeTemplate = null;
	private WeekTemplate mWeekTemplate = null;
	private ViewFlipper mFlipper = null;
	private ImageView mPicView = null;
	private boolean mIsFlipperShown = false;
	@SuppressWarnings("deprecation")
	public WaterFlipper(Context context,UiCmdListener l) {
		super(context);
		mContext = context;
		mUiCmdListener = l;
		mFlipper = new ViewFlipper(mContext);
		mDetector = new GestureDetector(this);
		DefaultView();
	}
	@SuppressWarnings("deprecation")
	public WaterFlipper(Context context){
		super(context);
		mContext = context;
		mFlipper = new ViewFlipper(mContext);
		mDetector = new GestureDetector(this);
		DefaultView();
	}
	
	private void DefaultView(){
			mPicView = new ImageView(mContext);
			FrameLayout.LayoutParams Params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
			addView(mPicView, Params);
			Log.d("mPicView------------------------->", mPicView+"add");
	}
	
	
	
	public void setmUiCmdListener(UiCmdListener mUiCmdListener) {
		this.mUiCmdListener = mUiCmdListener;
	}

	@Override
	public boolean onDown(MotionEvent e) {

		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
	
		if (e2.getX() - e1.getX() > 0) {
			mFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.rightin));
			mFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.rightout));
			mFlipper.showNext();
			
	
			return true;
		} else if (e1.getX() - e2.getX() > 0) {
			mFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.leftin));
			mFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.leftout));
			mFlipper.showPrevious();
			

			return true;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
	
		return false;
	}

	
	public boolean onTouchEvent(MotionEvent event) {
		return mDetector.onTouchEvent(event);
		
	}

public void ChangeChildView(int key){
	LayoutParams Params = null;
	switch (key) {
	case WaterTemplateBar.LocationBtn_Id:
		removeView(mFlipper);
		mFlipper.removeAllViews();
		Params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		Params.gravity = Gravity.BOTTOM;
		mPoiTemplate = new PoiTemplate(mContext);
		mFlipper.addView(mPoiTemplate, 0,Params);
		mFreeGoTemplate = new FreeGoTemplate(mContext);
		mFlipper.addView(mFreeGoTemplate, 1,Params);		
		mWeekTemplate = new WeekTemplate(mContext, 0);
		mFlipper.addView(mWeekTemplate, 2,Params);
		Params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		Params.gravity = Gravity.BOTTOM;
		mTimeTemplate = new TimeTemplate(mContext);
		mFlipper.addView(mTimeTemplate, 3, Params);
		addView(mFlipper);
		if(mUiCmdListener != null){				
			mPoiTemplate.setUiCmdListener(mUiCmdListener);		
			mFreeGoTemplate.setUiCmdListener(mUiCmdListener);
			mTimeTemplate.setUiListener(mUiCmdListener);
		}
		mIsFlipperShown = true;
		break;
	case WaterTemplateBar.TimeBtn_Id:
		break;
	case WaterTemplateBar.FoodBtn_Id:
		
		removeView(mFlipper);
		mFlipper.removeAllViews();
		Params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		Params.gravity = Gravity.BOTTOM;
		mBeerTemplate = new BeerTemplate(mContext);
		mFlipper.addView(mBeerTemplate, 0,Params);
		mCartoonTemplate = new CartoonTemplate(mContext);
		mFlipper.addView(mCartoonTemplate,1,Params);
		addView(mFlipper);
		if(mUiCmdListener != null){				
			mCartoonTemplate.setMuCmdListener(mUiCmdListener);			
			mBeerTemplate.setmUiCmdListener(mUiCmdListener);
		}
		mIsFlipperShown = true;
		break;
	case WaterTemplateBar.WeatherBtn_Id:
		break;
	case WaterTemplateBar.MoodBtn_Id:
		removeView(mFlipper);
		mFlipper.removeAllViews();
		mLikeorHateTemplate = new LikeorHateTemplate(mContext);
		Params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		Params.gravity = Gravity.BOTTOM;
		mFlipper.addView(mLikeorHateTemplate,0,Params);
		mCartoonTemplate = new CartoonTemplate(mContext);
		mFlipper.addView(mCartoonTemplate,1,Params);
		mBubbleTemplate = new BubbleTemplate(mContext);
		mFlipper.addView(mBubbleTemplate, 2, Params);
		addView(mFlipper);
		if(mUiCmdListener != null){
			mLikeorHateTemplate.setMuCmdListener(mUiCmdListener);
			mCartoonTemplate.setMuCmdListener(mUiCmdListener);
			mBubbleTemplate.setmUiCmdListener(mUiCmdListener);
		}
		mIsFlipperShown = true;
		break;
	case WaterTemplateBar.DefaultBtn_Id:
		removeView(mFlipper);
		mFlipper.removeAllViews();
		mIsFlipperShown = false;
		break;		
	}
}

public ImageView getmPicView() {
	return mPicView;
}
public LikeorHateTemplate getmLikeorHateTemplate() {
	return mLikeorHateTemplate;
}
public CartoonTemplate getmCartoonTemplate() {
	return mCartoonTemplate;
}
public BubbleTemplate getmBubbleTemplate() {
	return mBubbleTemplate;
}
public PoiTemplate getmPoiTemplate() {
	return mPoiTemplate;
}
public FreeGoTemplate getmFreeGoTemplate() {
	return mFreeGoTemplate;
}
public ViewFlipper getmFlipper() {
	return mFlipper;
}
public boolean ismIsFlipperShown() {
	return mIsFlipperShown;
}	
	

}
