package com.westlakestudent.camera.watermark.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.westlakestudent.camera.watermark.Location;
import com.westlakestudent.camera.watermark.R;
import com.westlakestudent.camera.watermark.utils.DensityUtil;

public class LocationTitleBar extends RelativeLayout implements OnClickListener, OnTouchListener {
	
	private Context mContext;
	private Button mCancelButton;
	
	private final int LOCATION_TITLE_BAR_MIN_HEIGHT = 60;
	private final int LOCATION_TITLE_BAR_TEXT_SIZE = 22;
	private final int LOCATION_TITLE_BAR_BUTTON_CANCEL_TEXT_SIZE = 12;
	
	private final String LOCATION_TITLE_BAR_TEXT = "Î»ÖÃ";
	
	public LocationTitleBar(Context context) {
		super(context);
		mContext = context;
		init();
	}

	private void init() {
		LayoutParams param = null;		
		
	    setBackgroundColor(Color.parseColor("#505050"));	  
	    setMinimumHeight(DensityUtil.dip2px(mContext, LOCATION_TITLE_BAR_MIN_HEIGHT));
	    param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    
	    TextView headText = new TextView(mContext);	    
	    headText.setText(LOCATION_TITLE_BAR_TEXT);
	    headText.setTextColor(Color.parseColor("#ffffffff"));
	    headText.setTextSize(LOCATION_TITLE_BAR_TEXT_SIZE);
	    param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	    param.addRule(RelativeLayout.CENTER_IN_PARENT);
	    addView(headText, param);	    
	    
	    mCancelButton = new Button(mContext);
	    mCancelButton.setBackgroundResource(R.drawable.btn_watermarkcamera_cancel);	   
	    mCancelButton.setText("È¡Ïû");
	    mCancelButton.setTextColor(Color.parseColor("#ffffffff"));
	    mCancelButton.setTextSize(LOCATION_TITLE_BAR_BUTTON_CANCEL_TEXT_SIZE);    
	    param = new LayoutParams(DensityUtil.dip2px(mContext, 50), DensityUtil.dip2px(mContext, 33));
	    param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	    param.addRule(RelativeLayout.CENTER_VERTICAL);
	    param.topMargin = DensityUtil.dip2px(mContext, 15);
	    param.rightMargin = DensityUtil.dip2px(mContext, 15);
	    param.bottomMargin = DensityUtil.dip2px(mContext, 15);
	    mCancelButton.setOnClickListener(this);
	    mCancelButton.setOnTouchListener(this);
	    addView(mCancelButton, param);    	    
	}
		
	@Override
	public void onClick(View v) {
		if (v == mCancelButton)
			((Location) mContext).onCancel();				
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (v == mCancelButton) {
			if (event.getAction() == MotionEvent.ACTION_DOWN)
				mCancelButton.setBackgroundResource(R.drawable.btn_watermarkcamera_cancel_click);			
			if (event.getAction() == MotionEvent.ACTION_UP)
				mCancelButton.setBackgroundResource(R.drawable.btn_watermarkcamera_cancel);
			
		}
		return false;
	}
}
