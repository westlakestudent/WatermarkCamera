package com.westlakestudent.camera.watermark.ui;

import com.westlakestudent.camera.watermark.R;
import com.westlakestudent.camera.watermark.utils.DensityUtil;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


public class BottomBar extends RelativeLayout{

	private Context mContext = null;
	private Button mAlbumButton = null;
	private Button mLocationButton = null;
	private Button mTakePictureButton = null;
	private Button mWaterTemplateBarButton = null;
	private final static int btn_id1 = 0xf29, btn_id2 = 0xf30, btn_id3 = 0xf31,btn_id4 = 0xf32;
	
	public BottomBar(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	private void initView(){
		RelativeLayout.LayoutParams Param = null;
		Param = new RelativeLayout.LayoutParams(DensityUtil.dip2px(mContext, 30),DensityUtil.dip2px(mContext, 30));
		
		mAlbumButton = new Button(mContext);
		mAlbumButton.setBackgroundResource(R.drawable.top_album);
		mAlbumButton.setId(btn_id1);
		Param.addRule(ALIGN_PARENT_LEFT);
		Param.leftMargin = DensityUtil.dip2px(mContext, 15);
		Param.topMargin = DensityUtil.dip2px(mContext, 20);
		addView(mAlbumButton, Param);
		
		Param = new RelativeLayout.LayoutParams(DensityUtil.dip2px(mContext, 30),DensityUtil.dip2px(mContext, 30));
		mLocationButton = new Button(mContext);
		mLocationButton.setBackgroundResource(R.drawable.ic_gps);
		mLocationButton.setId(btn_id2);
		Param.addRule(RelativeLayout.RIGHT_OF, btn_id1);
		Param.leftMargin = DensityUtil.dip2px(mContext, 30);
		Param.topMargin = DensityUtil.dip2px(mContext, 20);
		addView(mLocationButton, Param);
		
		Param = new RelativeLayout.LayoutParams(DensityUtil.dip2px(mContext, 70),DensityUtil.dip2px(mContext, 70));
		mTakePictureButton = new Button(mContext);
		mTakePictureButton.setId(btn_id3);
		mTakePictureButton.setBackgroundResource(R.drawable.takephoto_btn);
		Param.addRule(RelativeLayout.RIGHT_OF, btn_id2);
		Param.topMargin = DensityUtil.dip2px(mContext, 20);
		Param.leftMargin = DensityUtil.dip2px(mContext, 40);
		addView(mTakePictureButton, Param);
		
		Param = new RelativeLayout.LayoutParams(DensityUtil.dip2px(mContext, 60),DensityUtil.dip2px(mContext, 60));
		mWaterTemplateBarButton = new Button(mContext);
		mWaterTemplateBarButton.setBackgroundResource(R.drawable.watermark_library_bg);
		mWaterTemplateBarButton.setId(btn_id4);
		Param.addRule(RelativeLayout.RIGHT_OF, btn_id3);
		Param.topMargin = DensityUtil.dip2px(mContext, 20);
		Param.leftMargin = DensityUtil.dip2px(mContext, 20);
		addView(mWaterTemplateBarButton, Param);
		
		setBackgroundResource(R.drawable.category_bg_normal);
	}
	
	OnClickListener AlbumListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			
		}
	};
	
	OnClickListener LocationListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		
			
		}
	};
	
	OnClickListener TakePictureLiatener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			
		}
	};
	
	OnClickListener WatertemplateListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			
		}
	};
	
	
	
}
