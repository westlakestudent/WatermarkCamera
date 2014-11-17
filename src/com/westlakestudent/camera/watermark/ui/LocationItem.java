package com.westlakestudent.camera.watermark.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.westlakestudent.camera.systemmgr.UiCmdListener;
import com.westlakestudent.camera.watermark.R;
import com.westlakestudent.camera.watermark.location.POIType;
import com.westlakestudent.camera.watermark.utils.DensityUtil;

public class LocationItem extends LinearLayout implements OnClickListener {

	private Context mContext;
	private POIType mPoiType;
	private ImageView mItemIcon;
	private TextView mItemText;	
	private UiCmdListener mUiCmdListener;	
	
	private final int LOCATION_ITEM_MIN_HEIGHT = 60;
	private final int LOCATION_ITEM_TEXT_SIZE = 16;
	private final int LOCATION_ITEM_TIPS_SIZE = 13;
	private final String LOCATION_ITEM_TIPS_TEXT = "击使用输入文字作为地点发布";
	
	public static final int LOCATION_ITEM_CLICK = 0x10000301;
	
	public LocationItem(Context context, String itemText) {
		super(context);
		mContext = context;
		init(itemText);
	}
	
	public LocationItem(Context context, String itemText, POIType poiType) {
		super(context);
		mContext = context;
		mPoiType = poiType;
		init(itemText);
	}
	
	private void init(String itemText) {
		LayoutParams param = null;		
		
		setMinimumHeight(DensityUtil.dip2px(mContext, LOCATION_ITEM_MIN_HEIGHT));		

	    mItemIcon = new ImageView(mContext);	
	    if (mPoiType == null) 
	    	mItemIcon.setBackgroundResource(R.drawable.ic_loc_add_normal);
	    else
	    	setLocationIcon(mPoiType);	    
	    param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	    param.gravity = Gravity.CENTER_VERTICAL;
	    param.leftMargin = DensityUtil.dip2px(mContext, 15);
	    addView(mItemIcon, param);	
	    
	    LinearLayout itemLabel = new LinearLayout(mContext);
	    itemLabel.setOrientation(LinearLayout.VERTICAL);
	    itemLabel.setPadding(DensityUtil.dip2px(mContext, 15), DensityUtil.dip2px(mContext, 10), DensityUtil.dip2px(mContext, 10), DensityUtil.dip2px(mContext, 10));
	    param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    param.gravity = Gravity.CENTER_VERTICAL;
	    addView(itemLabel, param);	
	    
	    mItemText = new TextView(mContext);	    
	    mItemText.setText(itemText);
	    mItemText.setTextSize(LOCATION_ITEM_TEXT_SIZE);
	    param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);	    
	    itemLabel.addView(mItemText, param);
	    
	    if (mPoiType == null) {
	    	setOnClickListener(this);
	    	
	    	TextView itemTips = new TextView(mContext);	    
	    	itemTips.setText(LOCATION_ITEM_TIPS_TEXT);
	    	itemTips.setTextSize(LOCATION_ITEM_TIPS_SIZE);
		    param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);	
		    itemLabel.addView(itemTips, param);			    
	    }	
	}
	
	public void setUiCmdListener(UiCmdListener l) {
		mUiCmdListener = l;
	}
	
	public void setLocationIcon(POIType poiType) {
		if (mItemIcon == null)
			return;
		
		switch (poiType) {
		case AGENCY:
			mItemIcon.setBackgroundResource(R.drawable.ic_loc_group_normal);
			break;
		case EDUCATION:
			mItemIcon.setBackgroundResource(R.drawable.ic_loc_edu_normal);
			break;
		case ENTERTAINMENT:
			mItemIcon.setBackgroundResource(R.drawable.ic_loc_entertainment_normal);
			break;
		case FOOD:
			mItemIcon.setBackgroundResource(R.drawable.ic_loc_food_normal);
			break;
		case HOSPITAL:
			mItemIcon.setBackgroundResource(R.drawable.ic_loc_hospital_normal);
			break;
		case HOTEL:
			mItemIcon.setBackgroundResource(R.drawable.ic_loc_hotel_normal);
			break;			
		case LIFE:
			mItemIcon.setBackgroundResource(R.drawable.ic_loc_life_service_normal);
			break;
		case OFFICE:
			mItemIcon.setBackgroundResource(R.drawable.ic_loc_office_normal);
			break;
		case SHOP:
			mItemIcon.setBackgroundResource(R.drawable.ic_loc_shopping_normal);
			break;
		case TRAFFIC:
			mItemIcon.setBackgroundResource(R.drawable.ic_loc_traffic_normal);
			break;
		case TRAVEL:
			mItemIcon.setBackgroundResource(R.drawable.ic_loc_travel_normal);
			break;		
		case PLACE:
			mItemIcon.setBackgroundResource(R.drawable.ic_loc_place_normal);
			break;
		default:
			mItemIcon.setBackgroundResource(R.drawable.ic_loc_starred_normal);
		}
	}
	
	public void setLocationText(CharSequence text) {
		mItemText.setText(text);
	}
		
	@Override
	public void onClick(View v) {
		if (v == LocationItem.this) {
			if (mUiCmdListener != null) 
				mUiCmdListener.onUiCmd(LOCATION_ITEM_CLICK, mItemText.getText());			
		}			
	}
}
