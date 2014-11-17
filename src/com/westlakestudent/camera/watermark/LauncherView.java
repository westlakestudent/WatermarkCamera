package com.westlakestudent.camera.watermark;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class LauncherView extends Activity{
	private Handler mHandler = null;
	private Runnable mRunnable =null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    LinearLayout l = new LinearLayout(this);
	    l.setBackgroundResource(R.drawable.splash);
		setContentView(l);	
		mHandler = new Handler();
		mRunnable = new Runnable(){

			@Override
			public void run() {
				Intent mIntent = new Intent(LauncherView.this,WaterCamera.class);
				startActivity(mIntent);
				finish();
			}};
			mHandler.postDelayed(mRunnable, 1000);	
		}
		
	

}
