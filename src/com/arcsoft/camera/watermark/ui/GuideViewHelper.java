package com.arcsoft.camera.watermark.ui;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.arcsoft.camera.watermark.R;
import com.arcsoft.camera.watermark.utils.ScaleUtils;

public class GuideViewHelper extends RelativeLayout{

	private Context mContext = null;
	private PageAdapter mAdapter = null;
	private ViewPager mPageView = null;
	private ArrayList<View> mViewArray = null;
	private LinearLayout mLinearLayout = null;
	
	
	public GuideViewHelper(Context context) {
		super(context);
		initView(context);
	}

	public GuideViewHelper(Context context,AttributeSet attrs) {
		super(context,attrs);
		initView(context);

	}
	
	private void initView(Context context){
		mContext = context;
		mViewArray = new ArrayList<View>();	
		LinearLayout.LayoutParams Params = null;
		mPageView = new ViewPager(context);
		mLinearLayout = new LinearLayout(mContext);
		Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
		mLinearLayout.setLayoutParams(Params);
		mLinearLayout.setBackgroundResource(R.drawable.guide_1);
		mViewArray.add(mLinearLayout);
		
		mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.setBackgroundResource(R.drawable.guide_2);
		mViewArray.add(mLinearLayout);
		mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.setBackgroundResource(R.drawable.guide_3);
		Button mEnterButton = new Button(mContext);
		Params = new LinearLayout.LayoutParams(ScaleUtils.scale(170), ScaleUtils.scale(70));
		Params.gravity = Gravity.BOTTOM;
		Params.bottomMargin = ScaleUtils.scale(30);
		Params.leftMargin = ScaleUtils.scale(180);
		mEnterButton.setBackgroundResource(R.drawable.btn_use);
		mEnterButton.setOnClickListener(mClickListener);
		mLinearLayout.addView(mEnterButton,Params);
		
		mViewArray.add(mLinearLayout);
		Log.d("mViewArray=", mViewArray.size()+"");
		mAdapter = new PageAdapter(mViewArray);
		Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
		mPageView.setLayoutParams(Params);
		mPageView.setAdapter(mAdapter);
		mPageView.setOnPageChangeListener(mPageChangeListener);
		
		addView(mPageView);
	}
	
	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener(){

		@Override
		public void onPageScrollStateChanged(int arg0) {

			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		
			
		}

		@Override
		public void onPageSelected(int arg0) {
		
			
		}
		
		
	};
	
	private OnClickListener mClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			GuideViewHelper.this.removeAllViews();
			
		}
	};
		
	private class PageAdapter extends PagerAdapter{
		
		private ArrayList<View> views;
		
		public PageAdapter(ArrayList<View> v){
			views = v;
		}
		@Override
		public int getCount() {
			if(null != views)
				return views.size();
			return 0;
		}

		
		 @Override
	    public Object instantiateItem(View view, int position) {
		       
		        ((ViewPager) view).addView(views.get(position), 0);
		       
		        return views.get(position);
		    }
		 
		@Override
		public boolean isViewFromObject(View view, Object arg1) {

			return (view == arg1);
		}
		
		 @Override
		public void destroyItem(View view, int position, Object arg2) {
		        ((ViewPager) view).removeView(views.get(position));       
		}
		
		
		
	}
	
}
