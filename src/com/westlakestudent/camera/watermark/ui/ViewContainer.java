package com.westlakestudent.camera.watermark.ui;

import android.content.Context;
import android.widget.RelativeLayout;

public class ViewContainer extends RelativeLayout{

	public ViewContainer(Context context) {
		super(context);
		LayoutParams Params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		setLayoutParams(Params);
	}
	
}
