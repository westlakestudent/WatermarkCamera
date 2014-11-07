package com.arcsoft.camera.watermark.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arcsoft.camera.systemmgr.UiCmdListener;
import com.arcsoft.camera.watermark.R;
import com.arcsoft.camera.watermark.utils.DensityUtil;

public class LikeorHateTemplate extends RelativeLayout implements UiCmdListener{

	private Context mContext = null;
	private UiCmdListener mUiCmdListener = null;
	private LikeorHateImage mLikeorHateImage = null;
	private TextView mLeftTextView = null;
	private TextView mRightTextView = null;
	private TextView mLocationTextView = null;
	private ImageView mCenterImageView = null;
	private ImageView mLocationImageView = null;
	public final static int mt_id1 = 0xf200001,mt_id2 = 0xf200002, mt_id3 = 0xf200003, mimg_id1 = 0xf200004, mimg_id2 = 0xf200005 ;
	public final static int CRATE_LIKE_OR_HATE_IMAGE = 6 ,KILL_LIKE_OR_HATE_IMAGE = 7;
	public LikeorHateTemplate(Context context) {
		super(context);
		mContext = context;
		initView();
	}
	public TextView getmLocationTextView() {
		return mLocationTextView;
	}
	
	private void initView(){
		mLeftTextView = new TextView(mContext);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.addRule(ALIGN_PARENT_LEFT);
		params.topMargin = DensityUtil.dip2px(mContext, 10);
		params.leftMargin = DensityUtil.dip2px(mContext, 10);
		mLeftTextView.setText(R.string.me);
		mLeftTextView.setId(mt_id1);
		mLeftTextView.setTextColor(Color.WHITE);
		mLeftTextView.setTextSize(45);
		addView(mLeftTextView, params);
		
		mCenterImageView = new ImageView(mContext);
		params = new LayoutParams(DensityUtil.dip2px(mContext, 80),DensityUtil.dip2px(mContext, 80));
		params.addRule(RelativeLayout.ALIGN_TOP, mt_id1);
		params.topMargin = DensityUtil.dip2px(mContext, -9);
		params.addRule(RelativeLayout.RIGHT_OF, mt_id1);
		mCenterImageView.setBackgroundResource(R.drawable.likehate_favorite);
		mCenterImageView.setId(mimg_id1);
		addView(mCenterImageView, params);
		
		mRightTextView = new TextView(mContext);
		params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.RIGHT_OF, mimg_id1);
		params.topMargin = DensityUtil.dip2px(mContext, 10);
		mRightTextView.setTextColor(Color.WHITE);
		mRightTextView.setTextSize(45);
		mRightTextView.setText(R.string.you);
		mRightTextView.setId(mt_id2);
		addView(mRightTextView, params);
		
		mLocationImageView = new ImageView(mContext);
		params = new LayoutParams(DensityUtil.dip2px(mContext, 20),DensityUtil.dip2px(mContext, 30));
		params.addRule(ALIGN_PARENT_LEFT);
		params.leftMargin = DensityUtil.dip2px(mContext, 10);
		params.addRule(RelativeLayout.BELOW, mt_id1);
		mLocationImageView.setBackgroundResource(R.drawable.likehate_location);
		mLocationImageView.setId(mimg_id2);
		addView(mLocationImageView, params);
		
		mLocationTextView = new TextView(mContext);
		params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.BELOW, mt_id1);
		params.addRule(RelativeLayout.RIGHT_OF, mimg_id2);
		mLocationTextView.setText(R.string.weizhi);
		mLocationTextView.setTextSize(20);
		mLocationTextView.setTextColor(Color.WHITE);
		mLocationTextView.setId(mt_id3);
		addView(mLocationTextView, params);
		
		mCenterImageView.setOnClickListener(lisetener);	
	}
	
	OnClickListener lisetener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			mLikeorHateImage =new LikeorHateImage(mContext);
			mLikeorHateImage.setmUiCmdListener(LikeorHateTemplate.this);
			if(mUiCmdListener != null)
				mUiCmdListener.onUiCmd(CRATE_LIKE_OR_HATE_IMAGE, mLikeorHateImage);
			mLikeorHateImage = null;
			
		}
	};

	
	public void setMuCmdListener(UiCmdListener listener) {
		mUiCmdListener = listener;
	}

	@Override
	public int onUiCmd(int key, Object obj) {

		switch (key) {
		case 0:
			mCenterImageView.setBackgroundResource(R.drawable.likehate_bless);			
			break;
		case 1:
			mCenterImageView.setBackgroundResource(R.drawable.likehate_contempt);	
			break;
		case 2:
			mCenterImageView.setBackgroundResource(R.drawable.likehate_donotwant);	
			break;
		case 3:
			mCenterImageView.setBackgroundResource(R.drawable.likehate_favorite);	
			break;	
		case 4:
			mCenterImageView.setBackgroundResource(R.drawable.likehate_hate);	
			break;	
		case 5:
			mCenterImageView.setBackgroundResource(R.drawable.likehate_kiss);	
			break;
		case 6:
			mCenterImageView.setBackgroundResource(R.drawable.likehate_praise);	
			break;
		case 7:
			mCenterImageView.setBackgroundResource(R.drawable.likehate_thanks);	
			break;
		}
		if(mUiCmdListener != null)
			mUiCmdListener.onUiCmd(KILL_LIKE_OR_HATE_IMAGE, null);
		return key;
	}
	

}
