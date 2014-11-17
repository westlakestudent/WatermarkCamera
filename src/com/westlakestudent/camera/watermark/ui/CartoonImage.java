package com.westlakestudent.camera.watermark.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.westlakestudent.camera.systemmgr.UiCmdListener;
import com.westlakestudent.camera.watermark.R;
import com.westlakestudent.camera.watermark.utils.DensityUtil;

public class CartoonImage extends RelativeLayout{

	private GridView mGridView = null;
	private Context mContext = null; 
	private UiCmdListener mUiCmdListener =null;
	private int[] imageRes = { R.drawable.cartoonmood_0, R.drawable.cartoonmood_1,R.drawable.cartoonmood_2, 
               R.drawable.cartoonmood_3, R.drawable.cartoonmood_4, R.drawable.cartoonmood_5,
               R.drawable.cartoonmood_6, R.drawable.cartoonmood_7,R.drawable.cartoonmood_8};
	private String[] itemName = {"À¨Õ·Õ·","∂∫±∆","√¿◊Ã◊Ã","”∆œ–","ø’µ¥","”Ù√∆","áÂ¥Û¡À","¡˜∫π","Œ“‘Œ"};
	
	public CartoonImage(Context context) {
		super(context);
		mContext = context;
		initView();
	}
	
	private void initView(){	
		mGridView = new GridView(mContext);
		GridAdapter d = new GridAdapter(mContext,itemName,imageRes);
		mGridView.setColumnWidth(DensityUtil.dip2px(mContext, 90));
		mGridView.setNumColumns(GridView.AUTO_FIT);
		mGridView.setAdapter(d);	
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,long arg3) {
				if(mUiCmdListener != null)
					mUiCmdListener.onUiCmd(position, null);					
				
			}
		});
	    addView(mGridView);
		setBackgroundColor(Color.WHITE);
		
		
	}
	
	public void setmUiCmdListener(UiCmdListener mUiCmdListener) {
		this.mUiCmdListener = mUiCmdListener;
	}

	private class GridAdapter extends BaseAdapter{

		private String[] name = null;
	    private int[] iconarray = null;
	    
	    public GridAdapter(Context context, String[] name, int[] iconarray ) {	        
	        this.name = name;
	        this.iconarray = iconarray;

	    }
	    
		@Override
		public int getCount() {
			
			return name.length;
		}

		@Override
		public Object getItem(int position) {
			
			return position;
		}

		@Override
		public long getItemId(int position) {
			
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			AdapterItem item = new AdapterItem(mContext);
			item.setResource(name[position], iconarray[position]);				
			return item;
		}
		
	}

}
