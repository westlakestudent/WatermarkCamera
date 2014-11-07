package com.arcsoft.camera.watermark.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arcsoft.camera.systemmgr.UiCmdListener;
import com.arcsoft.camera.watermark.R;
import com.arcsoft.camera.watermark.utils.DensityUtil;

public class LikeorHateImage extends RelativeLayout{

	private GridView mGridView = null;
	public GridView getmGridView() {
		return mGridView;
	}





	private Context mContext = null; 
	private UiCmdListener mUiCmdListener =null;
	
	private final int[] imageRes = { R.drawable.likehate_bless, R.drawable.likehate_contempt,R.drawable.likehate_donotwant, 
            R.drawable.likehate_favorite, R.drawable.likehate_hate, R.drawable.likehate_kiss,
            R.drawable.likehate_praise, R.drawable.likehate_thanks};
    private final String[] itemName = { "Æí¸£", "±ÉÊÓ", "²»Òª", "Ï²°®", "ÌÖÑá","ÎÇ", "ÔÞÉÍ", "¸Ð¶÷"};
    
	public LikeorHateImage(Context context) {
		super(context);
		mContext = context;
		Toast.makeText(mContext, "Í¨¹ý", 300).show();
		intiView();
	}
	
	private void intiView(){
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
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
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
