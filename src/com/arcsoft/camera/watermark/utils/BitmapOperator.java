package com.arcsoft.camera.watermark.utils;

import java.io.File;
import java.io.FileOutputStream;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

public class BitmapOperator {

	public static void saveBitmapToFile(Bitmap bmp, String savePath, int quality) {
		FileOutputStream fos = null;
		File file = new File(savePath);
		file.getParentFile().mkdirs();
		try {
			fos = new FileOutputStream(savePath);
			if (fos != null) {
				bmp.compress(Bitmap.CompressFormat.JPEG, quality, fos);
			}
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addToDb(String filepath, int width, int height,ContentResolver contentResolver){
		long date = System.currentTimeMillis();

        // Insert into MediaStore.
        ContentValues values = new ContentValues(4);
        values.put(ImageColumns.DATE_TAKEN, date);
        values.put(ImageColumns.DATA, filepath);
        values.put(ImageColumns.WIDTH, width);
        values.put(ImageColumns.HEIGHT, height);

        try {
            contentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI, values);
            Log.d("test", "Add to datebase!");
        } catch (Throwable th)  {
        	Log.e("test", "Failed to new image" + th);
        }
	}
	
	
	public static Bitmap convertViewToBitmap(View view) {
//		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		Bitmap bitmap = Bitmap.createBitmap(1200,1600,  
	            Bitmap.Config.ARGB_8888);  
	        Canvas canvas = new Canvas(bitmap);  
	        canvas.drawColor(Color.TRANSPARENT);  
	        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG  
	            | Paint.FILTER_BITMAP_FLAG));  
	        view.draw(canvas);
		return bitmap;
	}


	public static Bitmap resizeBitmap(Bitmap bm, int w, int h) {
		Bitmap BitmapOrg = bm;
		int width = BitmapOrg.getWidth();
		int height = BitmapOrg.getHeight();
		float scaleWidth = ((float) w) / width;
		float scaleHeight = ((float) h) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		return Bitmap
				.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);
	}
}
