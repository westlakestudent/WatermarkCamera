package com.westlakestudent.camera.watermark.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.westlakestudent.camera.systemmgr.UiCmdListener;

public class LocationListView extends ListView implements OnItemClickListener {	
	private final String TAG = this.getClass().getName();
	
	private int mHeaderContentHeight;
	private int mLastRefreshState;	
	private int mRefreshState;		 
	private int mStartY;
	private int mCurrentY;
	private Context mContext;
	private LocationItemHeader mHeadView;
	private UiCmdListener mUiCmdListener;	
	
	private final static int RELEASE_TO_REFRESH = 0;
	private final static int PULL_TO_REFRESH = 1;
	private final static int REFRESHING = 2;
	private final static int DONE = 3;
	private final static int RATIO = 3;
	
	public static final int LOCATION_LIST_VIEW_ITEM_CLICK = 0x10000401;
	public static final int LOCATION_REFRESH = 0x10000403;
	
	public LocationListView(Context context) {
		super(context);
		mContext = context;
		init();
	}
	
	public LocationListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}
	
	private void init() {			
		mHeadView = new LocationItemHeader(mContext);
		measureView(mHeadView);
		mHeaderContentHeight = mHeadView.getMeasuredHeight();		
		mHeadView.setPadding(0, -1 * mHeaderContentHeight, 0, 0);
		mHeadView.invalidate();	 
		addHeaderView(mHeadView, null, false);
		setOnItemClickListener(this);	
		
		mRefreshState = DONE;		
	}
	
	public void setUiCmdListener(UiCmdListener l) {
		mUiCmdListener = l;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (mUiCmdListener == null) 
			return;	
		mUiCmdListener.onUiCmd(LOCATION_LIST_VIEW_ITEM_CLICK, arg2 - 1);		
	}

	@Override  
	public boolean onTouchEvent(MotionEvent event) {
		if (mRefreshState != REFRESHING)
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:				
				mStartY = (int) event.getY();   
				Log.v(TAG, "ACTION_DOWN" + " startY:" + mStartY);					
				break;
			case MotionEvent.ACTION_UP:
				if (mRefreshState == PULL_TO_REFRESH) 
					mRefreshState = DONE;				
				if (mRefreshState == RELEASE_TO_REFRESH) {
					mRefreshState = REFRESHING;				
					onRefresh();				
				}
				updateHeaderByState();
				break;
			case MotionEvent.ACTION_MOVE:
				mCurrentY = (int) event.getY();		
				Log.v(TAG, "ACTION_MOVE" + " currentY:" + mCurrentY);
				
				switch (mRefreshState) {
				case DONE:
					if (mCurrentY - mStartY > 0)
						mRefreshState = PULL_TO_REFRESH;	
					break;
				case PULL_TO_REFRESH: 	
					setSelection(0);
					if ((mCurrentY - mStartY) / RATIO >= mHeaderContentHeight)
						mRefreshState = RELEASE_TO_REFRESH;
					else if (mCurrentY - mStartY <= 0)
						mRefreshState = DONE;				
					break;
				case RELEASE_TO_REFRESH:
					setSelection(0);	
					if (((mCurrentY - mStartY) / RATIO < mHeaderContentHeight) && (mCurrentY - mStartY) > 0)
						mRefreshState = PULL_TO_REFRESH;	
					break;
				}			
				updateHeaderByState();
				mLastRefreshState = mRefreshState;
				break;
			}			
			
		return super.onTouchEvent(event);
	}
	
	private void updateHeaderByState() {		
		switch (mRefreshState) {
		case DONE:
			mHeadView.setPadding(0, -1 * mHeaderContentHeight, 0, 0);	
			mHeadView.setProgressBarVisibility(View.GONE);
			Log.v(TAG, "当前状态，DONE");
			break;
		case PULL_TO_REFRESH:
			mHeadView.setPadding(0, (mCurrentY - mStartY) / RATIO - mHeaderContentHeight, 0, 0);
			if (mLastRefreshState == mRefreshState) 
				return;
			mHeadView.setProgressBarVisibility(View.GONE);	
			mHeadView.setIconVisibility(View.VISIBLE);	
			mHeadView.startIconReverseAnimation();
			mHeadView.setHeadText("下拉刷新");			
			Log.v(TAG, "当前状态,PULL_TO_REFRESH");
			break;
		case RELEASE_TO_REFRESH:	
			mHeadView.setPadding(0, (mCurrentY - mStartY) / RATIO - mHeaderContentHeight, 0, 0);
			if (mLastRefreshState == mRefreshState) 
				return;
			mHeadView.setProgressBarVisibility(View.GONE);
			mHeadView.startIconAnimation();
			mHeadView.setHeadText("释放立即刷新");				
			Log.v(TAG, "当前状态,RELEASE_TO_REFRESH");
			break;		
		case REFRESHING:
			mHeadView.setPadding(0, 0, 0, 0);		
			mHeadView.setProgressBarVisibility(View.VISIBLE);
			mHeadView.clearIconAnimation();
			mHeadView.setIconVisibility(View.GONE);					
			mHeadView.setHeadText("正在刷新...");	
			Log.v(TAG, "当前状态,REFRESHING");
			break;		
		}
	}

	public void onRefresh() {
		if (mUiCmdListener == null) 
			return;	
		mUiCmdListener.onUiCmd(LOCATION_REFRESH, null);
	}	
	
	public void refreshCompleted() {
		mRefreshState = DONE;		
		mHeadView.setHeadTips("最近更新:" + new SimpleDateFormat("yyyy年MM月dd日  HH:mm").format(new Date()));	
		updateHeaderByState();
	}
	
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null)
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0)
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		else
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		child.measure(childWidthSpec, childHeightSpec);		
	}
		
	public void setAdapter(BaseAdapter adapter) {
		mHeadView.setHeadTips("最近更新:" + new SimpleDateFormat("yyyy年MM月dd日  HH:mm").format(new Date()));	
		super.setAdapter(adapter);
	}
	
}
