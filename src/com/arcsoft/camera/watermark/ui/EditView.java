package com.arcsoft.camera.watermark.ui;

import java.util.Timer;
import java.util.TimerTask;

import com.arcsoft.camera.watermark.utils.ScaleUtils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class EditView extends Dialog implements OnClickListener{

	private Context mContext = null;
	private Button mCancelButton = null;
	private Button mConfirmButton = null;
	private Button mClearButton = null;
	private EditText mEditText = null;
	private final static int CANCEL = 0xeee00001, CONFIRM = 0xeee00002, EDIT = 0xeee00003;
	
	public EditView(Context context) {
		super(context);
		mContext = context;
		
	}

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}



	private void initView(){
		RelativeLayout mMainLayout = new RelativeLayout(mContext);
		RelativeLayout.LayoutParams Params = null;
		
		mCancelButton = new Button(mContext);
		mCancelButton.setId(CANCEL);
		mCancelButton.setText("取消");
		Params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		Params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		Params.addRule(RelativeLayout.CENTER_VERTICAL);
		mMainLayout.addView(mCancelButton, Params);
		
		mEditText = new EditText(mContext);
		mEditText.setId(EDIT);
		mEditText.requestFocus();
		Params = new RelativeLayout.LayoutParams(300,LayoutParams.WRAP_CONTENT);
		Params.addRule(RelativeLayout.RIGHT_OF, CANCEL);
		Params.addRule(RelativeLayout.CENTER_VERTICAL);
		mMainLayout.addView(mEditText, Params);
		
		mConfirmButton = new Button(mContext);
		mConfirmButton.setId(CONFIRM);
		mConfirmButton.setText("确定");
		Params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		Params.addRule(RelativeLayout.RIGHT_OF, EDIT);
		Params.addRule(RelativeLayout.CENTER_VERTICAL);
		mMainLayout.addView(mConfirmButton, Params);
		setContentView(mMainLayout);
		
		Window mWindow = getWindow();
		mWindow.setGravity(Gravity.BOTTOM);
		
		WindowManager.LayoutParams wParams=getWindow().getAttributes();   
		wParams.alpha=1.0f; 
		wParams.height = ScaleUtils.scale(100);
		wParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(wParams);  
		
		Timer timer = new Timer();
		   timer.schedule(new TimerTask() {
		    @Override
		    public void run() {
		     ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE))
		       .toggleSoftInput(0,
		         InputMethodManager.HIDE_NOT_ALWAYS);
		    }
		   }, 1000);
	}
	
	
	
	public EditText getmEditText() {
		return mEditText;
	}



	@Override
	public void onClick(View v) {
		
		
	}

}
