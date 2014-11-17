package com.westlakestudent.camera.watermark.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.westlakestudent.camera.systemmgr.UiCmdListener;

public class BubbleImage extends RelativeLayout{

	private Context mContext = null;
	private ListView mListView = null;
	private List<String> mData = null;
	private UiCmdListener mUiCmdListener =null;
	public BubbleImage(Context context) {
		super(context);
		mContext = context;
		initView();
		
	}
	
	
	private void initView(){
		mListView = new ListView (mContext);
		mData = new ArrayList<String>();
		mData.add("呵呵");
		mData.add("哼");
		mData.add("得瑟");
		mData.add("坑爹");
		mData.add("去死");
		mData.add("我勒个去");
		mData.add("生气了");
		mData.add("好讨厌");
		mData.add("好伤心");
		mData.add("好晕");
		mData.add("好困");
		mData.add("跪");
		mData.add("好可爱");
		mData.add("好开心");
		mData.add("好激动");
		mData.add("卡哇伊");
		mData.add("惊讶");
		mListView.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,mData));
		RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
		addView(mListView,Params);
		setBackgroundColor(Color.WHITE);
		mListView.setOnItemClickListener(listener);
	}

	
	public void setmUiCmdListener(UiCmdListener mUiCmdListener) {
		this.mUiCmdListener = mUiCmdListener;
	}


	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position,long arg3) {
			if(mUiCmdListener != null)
				mUiCmdListener.onUiCmd(position, null);	
			
		}
	};
	
}
