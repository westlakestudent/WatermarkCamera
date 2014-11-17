package com.westlakestudent.camera.watermark.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.westlakestudent.camera.systemmgr.UiCmdListener;
import com.westlakestudent.camera.watermark.R;
import com.westlakestudent.camera.watermark.utils.DensityUtil;

public class BubbleTemplate extends RelativeLayout implements UiCmdListener{

	private Context mContext = null;
	private UiCmdListener mUiCmdListener = null;
	private BubbleImage mBubbleImage = null;
	private ImageView mBgImageView = null;
	private ImageView mFrontImageView = null;
	private ImageView mLocationImageView = null;
	private TextView mLocationTextView = null;
	public TextView getmLocationTextView() {
		return mLocationTextView;
	}


	private final static int mImg_id1 = 0xf11, mImg_id2 = 0xf12, mImg_id3 = 0xf13, mText_id = 0xf14;
	public final static int CRATE_BUBBLE_IMAGE = 0xf15 ,KILL_BUBBLE_IMAGE = 0xf16;
	
	public BubbleTemplate(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	private void initView(){
		RelativeLayout.LayoutParams Params = null;
		mBgImageView = new ImageView(mContext);
		mBgImageView.setBackgroundResource(R.drawable.bubble);
		mBgImageView.setId(mImg_id1);
		Params = new RelativeLayout.LayoutParams(DensityUtil.dip2px(mContext, 200),DensityUtil.dip2px(mContext, 60));
		Params.topMargin = DensityUtil.dip2px(mContext, 10);
		Params.leftMargin = DensityUtil.dip2px(mContext, 10);
		addView(mBgImageView, Params);
		
		mFrontImageView = new ImageView(mContext);
		mFrontImageView.setBackgroundResource(R.drawable.angry);
		mFrontImageView.setId(mImg_id2);
		Params = new RelativeLayout.LayoutParams(DensityUtil.dip2px(mContext, 150),DensityUtil.dip2px(mContext, 30));
		Params.topMargin = DensityUtil.dip2px(mContext, 25);
		Params.leftMargin = DensityUtil.dip2px(mContext, 25);
		addView(mFrontImageView, Params);
		
		mLocationImageView = new ImageView(mContext);
		mLocationImageView.setBackgroundResource(R.drawable.likehate_location);
		mLocationImageView.setId(mImg_id3);
		Params = new RelativeLayout.LayoutParams(DensityUtil.dip2px(mContext, 20),DensityUtil.dip2px(mContext, 30));
		Params.addRule(RelativeLayout.BELOW, mImg_id1);
		Params.addRule(RelativeLayout.ALIGN_LEFT, mImg_id1);
		Params.leftMargin = DensityUtil.dip2px(mContext, 10);
		addView(mLocationImageView, Params);
		
		mLocationTextView = new TextView(mContext);
		mLocationTextView.setText(R.string.weizhi);
		mLocationTextView.setTextSize(20);
		mLocationTextView.setTextColor(Color.WHITE);
		mLocationTextView.setId(mText_id);
		Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		Params.addRule(RelativeLayout.BELOW, mImg_id1);
		Params.addRule(RelativeLayout.RIGHT_OF, mImg_id3);
		Params.addRule(RelativeLayout.ALIGN_BOTTOM, mImg_id3);
		addView(mLocationTextView, Params);
		
		mFrontImageView.setOnClickListener(listener);
	}
	
	
	OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			mBubbleImage = new BubbleImage(mContext);
			mBubbleImage.setmUiCmdListener(BubbleTemplate.this);
			if(mUiCmdListener != null)
				mUiCmdListener.onUiCmd(CRATE_BUBBLE_IMAGE, mBubbleImage);
			mBubbleImage = null;			
		}
	};
	
	
	
	public void setmUiCmdListener(UiCmdListener mUiCmdListener) {
		this.mUiCmdListener = mUiCmdListener;
	}

	@Override
	public int onUiCmd(int key, Object obj) {
	switch (key) {
		case 0:
			mFrontImageView.setBackgroundResource(R.drawable.hehe);
			break;
		case 1:
			mFrontImageView.setBackgroundResource(R.drawable.heng);
			break;
		case 2:
			mFrontImageView.setBackgroundResource(R.drawable.dese);
			break;
		case 3:
			mFrontImageView.setBackgroundResource(R.drawable.kiddingyourfather);
			break;
		case 4:
			mFrontImageView.setBackgroundResource(R.drawable.gotodie);
			break;
		case 5:
			mFrontImageView.setBackgroundResource(R.drawable.woqu);
			break;
		case 6:
			mFrontImageView.setBackgroundResource(R.drawable.angry);
			break;
		case 7:
			mFrontImageView.setBackgroundResource(R.drawable.hate);
			break;
		case 8:
			mFrontImageView.setBackgroundResource(R.drawable.sad);
			break;
		case 9:
			mFrontImageView.setBackgroundResource(R.drawable.dizzy);
			break;
		case 10:
			mFrontImageView.setBackgroundResource(R.drawable.zzz);
			break;
		case 11:
			mFrontImageView.setBackgroundResource(R.drawable.orz);
			break;
		case 12:
			mFrontImageView.setBackgroundResource(R.drawable.cyte);
			break;
		case 13:
			mFrontImageView.setBackgroundResource(R.drawable.happy);
			break;
		case 14:
			mFrontImageView.setBackgroundResource(R.drawable.excite);
			break;
		case 15:
			mFrontImageView.setBackgroundResource(R.drawable.kawayi);
			break;
		case 16:
			mFrontImageView.setBackgroundResource(R.drawable.surprised);
			break;
	}
	if(mUiCmdListener != null)
		mUiCmdListener.onUiCmd(KILL_BUBBLE_IMAGE, null);

		return key;
	}

}
