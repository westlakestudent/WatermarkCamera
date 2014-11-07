package com.arcsoft.camera.watermark.ui;

import com.arcsoft.camera.systemmgr.UiCmdListener;
import com.arcsoft.camera.watermark.R;
import com.arcsoft.camera.watermark.utils.DensityUtil;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class LocationSearchBar extends FrameLayout implements OnClickListener, TextWatcher {
	
	private Context mContext;
	private EditText mSearchbox;
	private ImageButton mCancelInputButton;
	private UiCmdListener mUiCmdListener;	
	
	private final int SEARCHBOX_HEIGHT = 30;
	private final String SEARCHBOX_HINT = "À—À˜ªÚ ‰»ÎŒª÷√";
	
	public static final int SEARCHBOX_CHANGED = 0x10000101;		
	
	public LocationSearchBar(Context context) {
		super(context);
		mContext = context;
		init();
	}

	private void init() {
		LayoutParams param = null;
		
		setPadding(DensityUtil.dip2px(mContext, 15), DensityUtil.dip2px(mContext, 10), DensityUtil.dip2px(mContext, 15), DensityUtil.dip2px(mContext, 10));
		param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		setLayoutParams(param);
		
		mSearchbox = new EditText(mContext);
		mSearchbox.setBackgroundResource(R.drawable.edt_round);
		mSearchbox.setHint(SEARCHBOX_HINT);		
		mSearchbox.setSingleLine(true);
		mSearchbox.setTextSize(16);	    
	    param = new LayoutParams(LayoutParams.MATCH_PARENT, DensityUtil.dip2px(mContext, SEARCHBOX_HEIGHT));
	    mSearchbox.addTextChangedListener(this);
	    addView(mSearchbox, param);
	    
	    mCancelInputButton = new ImageButton(mContext);
	    mCancelInputButton.setBackgroundResource(R.drawable.btn_edit_text_clear);
	    param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	    param.rightMargin = DensityUtil.dip2px(mContext, 10);
	    param.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
	    mCancelInputButton.setOnClickListener(this);
	    mCancelInputButton.setVisibility(View.GONE);
	    addView(mCancelInputButton, param);
	}
	
	public void clearSearchbox() {
		mSearchbox.setText("");
	}
	
	public void setUiCmdListener(UiCmdListener l) {
		mUiCmdListener = l;
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (mUiCmdListener != null) 
			mUiCmdListener.onUiCmd(SEARCHBOX_CHANGED, s);
		
		if (s.equals("") || s.length() == 0) 
			mCancelInputButton.setVisibility(View.GONE);
		else
			mCancelInputButton.setVisibility(View.VISIBLE);
	}

	@Override
	public void onClick(View v) {
		if (v == mCancelInputButton)
			clearSearchbox();			
	}
}
