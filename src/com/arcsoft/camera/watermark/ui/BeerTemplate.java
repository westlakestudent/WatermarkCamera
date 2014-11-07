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

public class BeerTemplate extends RelativeLayout implements UiCmdListener{

	private Context mContext = null;
	private BeerImage mBeerImage = null;
	private UiCmdListener mUiCmdListener = null;
	private ImageView mUpImageView = null, mLocationImageView = null;
	private TextView mUpTextView = null, mLocationTextView = null;
	private final static int mImg_id1 = 0xf17,mImg_id2 = 0xf18, mText_id1 = 0xf19, mText_id2 = 0xf20;
	public final static int CRATE_BEER_IMAGE = 0xf21 ,KILL_BEER_IMAGE = 0xf22;
	public BeerTemplate(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	
	private void initView(){
		RelativeLayout.LayoutParams Params = null;
		
		mUpImageView = new ImageView(mContext);
		Params = new RelativeLayout.LayoutParams(DensityUtil.dip2px(mContext, 100),DensityUtil.dip2px(mContext, 100));
		Params.bottomMargin = DensityUtil.dip2px(mContext, 10);
		Params.leftMargin = DensityUtil.dip2px(mContext, 10);
		mUpImageView.setBackgroundResource(R.drawable.beermark_0);
		mUpImageView.setId(mImg_id1);
		addView(mUpImageView, Params);
		
		mUpTextView = new TextView(mContext);
		Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		Params.addRule(RelativeLayout.RIGHT_OF, mImg_id1);
		Params.topMargin = DensityUtil.dip2px(mContext, 15);
		mUpTextView.setText(R.string.a_0);
		mUpTextView.setTextSize(20);
		mUpTextView.setTextColor(Color.WHITE);
		mUpTextView.setId(mText_id1);
		addView(mUpTextView, Params);
		
		mLocationImageView = new ImageView(mContext);
		Params = new RelativeLayout.LayoutParams(DensityUtil.dip2px(mContext, 20),DensityUtil.dip2px(mContext, 30));
		Params.addRule(RelativeLayout.BELOW, mText_id1);
		Params.addRule(RelativeLayout.ALIGN_LEFT, mText_id1);
		mLocationImageView.setBackgroundResource(R.drawable.likehate_location);
		mLocationImageView.setId(mImg_id2);
		addView(mLocationImageView, Params);
		
		mLocationTextView = new TextView(mContext);
		Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		Params.addRule(RelativeLayout.RIGHT_OF, mImg_id2);
		Params.addRule(RelativeLayout.BELOW, mText_id1);
		mLocationTextView.setText(R.string.weizhi);
		mLocationTextView.setTextColor(Color.WHITE);
		mLocationTextView.setTextSize(20);
		mLocationTextView.setId(mText_id2);
		addView(mLocationTextView, Params);
		
		mUpImageView.setOnClickListener(listener);
	}
	
	OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			mBeerImage =  new BeerImage(mContext);
			mBeerImage.setmUiCmdListener(BeerTemplate.this);
			if(mUiCmdListener != null)
				mUiCmdListener.onUiCmd(CRATE_BEER_IMAGE, mBeerImage);
			mBeerImage = null;
			
		}
	};
	
	
	
	public void setmUiCmdListener(UiCmdListener mUiCmdListener) {
		this.mUiCmdListener = mUiCmdListener;
	}


	@Override
	public int onUiCmd(int key, Object obj) {
		switch (key) {
		case 0:
			mUpImageView.setBackgroundResource(R.drawable.beermark_0);
			mUpTextView.setText(R.string.a_0);
			break;
		case 1:
			mUpImageView.setBackgroundResource(R.drawable.beermark_1);
			mUpTextView.setText(R.string.a_1);
			break;
		case 2:
			mUpImageView.setBackgroundResource(R.drawable.beermark_2);
			mUpTextView.setText(R.string.a_2);
			break;
		case 3:
			mUpImageView.setBackgroundResource(R.drawable.beermark_3);
			mUpTextView.setText(R.string.a_3);
			break;
		case 4:
			mUpImageView.setBackgroundResource(R.drawable.beermark_4);
			mUpTextView.setText(R.string.a_4);
			break;
		case 5:
			mUpImageView.setBackgroundResource(R.drawable.beermark_5);
			mUpTextView.setText(R.string.a_5);
			break;

		}
		if(mUiCmdListener != null)
			mUiCmdListener.onUiCmd(KILL_BEER_IMAGE, null);
		return key;
	}

}
