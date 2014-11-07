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

public class CartoonTemplate extends RelativeLayout implements UiCmdListener{

	private Context mContext = null;
	private UiCmdListener mUiCmdListener = null;
	private CartoonImage mCartoonImage = null;
	private ImageView mUpImageView = null;
	private ImageView mLocationImageView = null;
	private TextView mUpTextView = null;
	private TextView mLocationTextView = null;
	public TextView getmLocationTextView() {
		return mLocationTextView;
	}

	private final static int mImg_id1 = 0xf03,mImg_id2 = 0xf04, mText_id1 = 0xf05, mText_id2 = 0xf06;
	public final static int CRATE_CARTOON_IMAGE = 0xf07 ,KILL_CARTOON_IMAGE = 0xf08;
	
	public CartoonTemplate(Context context) {
		super(context);
		mContext = context;
		initView();
	}
	
	
	private void initView(){
		RelativeLayout.LayoutParams Params = null;
		mUpImageView = new ImageView(mContext);
		Params = new RelativeLayout.LayoutParams(DensityUtil.dip2px(mContext, 100),DensityUtil.dip2px(mContext, 80));
		Params.topMargin = DensityUtil.dip2px(mContext, 10);
		Params.leftMargin = DensityUtil.dip2px(mContext, 10);
		mUpImageView.setBackgroundResource(R.drawable.cartoonmood_0);
		mUpImageView.setId(mImg_id1);
		addView(mUpImageView, Params);
		
		mUpTextView = new TextView(mContext);
		Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		Params.addRule(RelativeLayout.RIGHT_OF, mImg_id1);
		Params.topMargin = DensityUtil.dip2px(mContext, 25);
		mUpTextView.setText(R.string.c_0);
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
			mCartoonImage = new CartoonImage(mContext);
			mCartoonImage.setmUiCmdListener(CartoonTemplate.this);
			if(mUiCmdListener != null)
				mUiCmdListener.onUiCmd(CRATE_CARTOON_IMAGE, mCartoonImage);
			mCartoonImage = null;
			
		}
	};
	public void setMuCmdListener(UiCmdListener mUiCmdListener) {
		this.mUiCmdListener = mUiCmdListener;
	}
	
	@Override
	public int onUiCmd(int key, Object obj) {
		switch (key) {
		case 0:
			mUpImageView.setBackgroundResource(R.drawable.cartoonmood_0);
			mUpTextView.setText(R.string.c_0);
			break;
		case 1:
			mUpImageView.setBackgroundResource(R.drawable.cartoonmood_1);
			mUpTextView.setText(R.string.c_1);
			break;
		case 2:
			mUpImageView.setBackgroundResource(R.drawable.cartoonmood_2);
			mUpTextView.setText(R.string.c_2);
			break;
		case 3:
			mUpImageView.setBackgroundResource(R.drawable.cartoonmood_3);
			mUpTextView.setText(R.string.c_3);
			break;
		case 4:
			mUpImageView.setBackgroundResource(R.drawable.cartoonmood_4);
			mUpTextView.setText(R.string.c_4);
			break;
		case 5:
			mUpImageView.setBackgroundResource(R.drawable.cartoonmood_5);
			mUpTextView.setText(R.string.c_5);
			break;
		case 6:
			mUpImageView.setBackgroundResource(R.drawable.cartoonmood_6);
			mUpTextView.setText(R.string.c_6);
			break;
		case 7:
			mUpImageView.setBackgroundResource(R.drawable.cartoonmood_7);
			mUpTextView.setText(R.string.c_7);
			break;
		case 8:
			mUpImageView.setBackgroundResource(R.drawable.cartoonmood_8);
			mUpTextView.setText(R.string.c_8);
		}
		if(mUiCmdListener != null)
			mUiCmdListener.onUiCmd(KILL_CARTOON_IMAGE, null);
			return key;
	}

}
