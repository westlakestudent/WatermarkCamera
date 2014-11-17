package com.westlakestudent.camera.systemmgr;
import android.content.Context;
import android.util.Log;
import android.view.OrientationEventListener;
import android.widget.Toast;

public class MyOrientationListener extends OrientationEventListener {

	private Context mContext = null;
	public MyOrientationListener(Context context,int rate) {
		super(context,rate);
		mContext = context;
	}

	@Override
	public void onOrientationChanged(int orientation) {
		switch (orientation) {
		case 90:
			Toast.makeText(mContext, "onOrientationChanged"+orientation, 100).show();
			Log.d("orientation"+90+"======>", "orientation");
			break;
		case 180:
			Toast.makeText(mContext, "onOrientationChanged"+orientation, 100).show();		
			Log.d("orientation"+180+"======>", "orientation");
			break;
		case 270:
			Toast.makeText(mContext, "onOrientationChanged"+orientation, 100).show();
			Log.d("orientation"+270+"======>", "orientation");
			break;
		case 0:
			Toast.makeText(mContext, "onOrientationChanged"+orientation, 100).show();
			Log.d("orientation"+0+"======>", "orientation");
			break;
		case 359:
			Toast.makeText(mContext, "onOrientationChanged"+orientation, 100).show();	
			Log.d("orientation"+359+"======>", "orientation");
			break;
		
		}
		
		
	}


}
