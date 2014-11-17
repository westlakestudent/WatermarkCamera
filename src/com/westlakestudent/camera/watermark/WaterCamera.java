package com.westlakestudent.camera.watermark;

import java.io.IOException;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.westlakestudent.camera.systemmgr.UiCmdListener;
import com.westlakestudent.camera.watermark.location.LocationInfo;
import com.westlakestudent.camera.watermark.service.LocationInfoProvider;
import com.westlakestudent.camera.watermark.service.ServiceProvider;
import com.westlakestudent.camera.watermark.service.Weather;
import com.westlakestudent.camera.watermark.ui.BeerImage;
import com.westlakestudent.camera.watermark.ui.BeerTemplate;
import com.westlakestudent.camera.watermark.ui.BubbleImage;
import com.westlakestudent.camera.watermark.ui.BubbleTemplate;
import com.westlakestudent.camera.watermark.ui.CameraBar;
import com.westlakestudent.camera.watermark.ui.CartoonImage;
import com.westlakestudent.camera.watermark.ui.CartoonTemplate;
import com.westlakestudent.camera.watermark.ui.GuideViewHelper;
import com.westlakestudent.camera.watermark.ui.LikeorHateImage;
import com.westlakestudent.camera.watermark.ui.LikeorHateTemplate;
import com.westlakestudent.camera.watermark.ui.PoiTemplate;
import com.westlakestudent.camera.watermark.ui.TopBar;
import com.westlakestudent.camera.watermark.ui.ViewContainer;
import com.westlakestudent.camera.watermark.ui.WaterFlipper;
import com.westlakestudent.camera.watermark.ui.WaterTemplateBar;
import com.westlakestudent.camera.watermark.utils.BitmapOperator;
import com.westlakestudent.camera.watermark.utils.ScaleUtils;


public class WaterCamera extends Activity implements PictureCallback,
		ShutterCallback, AutoFocusCallback, Callback, UiCmdListener{

	private Camera.Parameters parameters = null;
	private RelativeLayout.LayoutParams Params = null;
	private FrameLayout.LayoutParams mFLayoutParams = null;
	
	//private LocationService mLocationService = null;
	//private WeatherService mWeatherService = null;
	private  Weather mW = null;
	private Handler mHandler = null;
	private Map<String, String> mCityToID = null;
	private String mLocation = null;
	private String mCity = null;
	private String mTemp = null;
	private String mWeather = null;
	
	private Bitmap mTempBitmap = null;
	private Bitmap mPicBitmap = null;
	
	private FrameLayout mSurfaceViewrCantainer = null;
	private ViewContainer mViewContainer = null;
	private SurfaceView mSurfaceView = null;
	private SurfaceHolder mHolder = null;
	private Camera mCamera = null;
	private TopBar mTopBar = null;
	private LikeorHateImage mLikeorHateImage = null;
	private CartoonImage mCartoonImage = null;
	private BubbleImage mBubbleImage = null;
	private BeerImage mBeerImage = null;
	private WaterTemplateBar mWaterTemplateBar = null;
	private WaterFlipper mWaterFlipper = null;
	private GuideViewHelper mHelper = null;
	private CameraBar mCameraBar = null;
	private ImageView mPictureView = null;
	
	private TextView mLikeoHateLocationText = null;
	private TextView mBubbleLocationText = null;
	private TextView mCartoonLocationText = null;
	private TextView mPoiLocationText = null;
	private TextView mFreeGoLocationtext = null;
	private LocationInfoProvider mProvider = null;
	
	private static boolean IS_FRONT = false ,LIKE_IMAGE_IS_FRONT = false ,CARTOON_IMAGE_IS_FRONT = false, 
						   BUBBLE_IMAGE_IS_FRONT = false,BEER_IMAGE_IS_FRONT = false;
	private boolean mIsPreview = false;
	private static int FRONT = 1 ,BACK = 0 ;
	private int mInitLocationTextMood = 0;
	private int mWhichWaterButton = 0;
	private int mWhichCamera = 0;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);	
		initView();
//		LocationInit();
		mProvider = new LocationInfoProvider(this);
		mProvider.initWireless();
//		provider.startService();
		initLocation();
		
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		mProvider.startLocationManager();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (null != mProvider)
			mProvider.stopLocationManager();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mProvider.stopLocationManager();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int arg1, int arg2,
			int arg3) {
		if (holder.getSurface() == null) {
			return;
		}
		try {
			mCamera.stopPreview();
		} catch (Exception e) {
		}
		parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
		parameters.setPictureSize(1600, 1200);
		parameters.setPreviewSize(640, 480);
//		mCamera.setParameters(parameters);
		try {
			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();
		} catch (Exception e) {
			Log.d("error=========>",
					"Error starting camera preview: " + e.getMessage());
		}

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mCamera = Camera.open(mWhichCamera);
			if (mWhichCamera == BACK) {
				mCamera.setPreviewDisplay(holder);
				mCamera.setDisplayOrientation(90);
				parameters = mCamera.getParameters();
				Log.d("params", parameters.flatten());
				parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
//				parameters.setPictureSize(1600, 1200);
//				parameters.setPreviewSize(800, 480);
//				mCamera.setParameters(parameters);

			}
			mCamera.startPreview();
			mIsPreview = true;
		} catch (IOException e) {
			e.printStackTrace();
			mCamera.release();
			mCamera = null;
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		if (mCamera != null) {
			if (mIsPreview)
				mCamera.stopPreview();
			mCamera.release();
		}

	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		if (success) 
			mCamera.takePicture(this, null, null, this);
		else
			mCamera.takePicture(this, null, null, this);
	}

	@Override
	public void onShutter() {

	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		mPicBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		System.out.println("mPicBitmap------------->"+mPicBitmap.getWidth()+"--------------->"+mPicBitmap.getHeight());
		long dateTaken = System.currentTimeMillis();
		String date = DateFormat.format("yyyyMMddkkmmss", dateTaken).toString();
		ContentResolver contentResolver = getContentResolver();
		String path = Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera/" + date + ".png";
		/****************************************** 旋转bitmap90度 **************************/
		Matrix matrix = new Matrix();
		matrix.reset();
		if (mWhichCamera == BACK)
			matrix.setRotate(90);
		if (mWhichCamera == FRONT)
			matrix.setRotate(270);
		Bitmap bmp2 = Bitmap.createBitmap(mPicBitmap, 0, 0, mPicBitmap.getWidth(),
				mPicBitmap.getHeight(), matrix, true);
		mPicBitmap.recycle();
		mPicBitmap = bmp2;
		if(mWaterFlipper.ismIsFlipperShown()){
			mPictureView = mWaterFlipper.getmPicView();
			android.view.ViewGroup.LayoutParams Param = mPictureView.getLayoutParams();
			Param.width = 1200;
			Param.height = 1600;
			mPictureView.setLayoutParams(Param);
			mPictureView.setImageBitmap(mPicBitmap);
			Log.w("warning----","----------------->>"+mWaterFlipper.getmFlipper().getHeight()+"--------------->>"+mWaterFlipper.getmFlipper().getWidth());
			mTempBitmap = BitmapOperator.convertViewToBitmap(mWaterFlipper);
			mPictureView.setImageBitmap(null);
			//mTempBitmap = BitmapOperator.resizeBitmap(mTempBitmap, 1200, 1600);
			BitmapOperator.saveBitmapToFile(mTempBitmap, path, 90);
			BitmapOperator.addToDb(path, mTempBitmap.getWidth(), mTempBitmap.getHeight(), contentResolver);
			mTempBitmap.recycle();
		}
		else{
			BitmapOperator.saveBitmapToFile(mPicBitmap, path, 90);
			BitmapOperator.addToDb(path, mPicBitmap.getWidth(), mPicBitmap.getHeight(), contentResolver);
			mPicBitmap.recycle();
		}
		
		mCamera.startPreview();
		
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && !IS_FRONT) {
			new AlertDialog.Builder(WaterCamera.this)
					.setMessage("确认退出吗?")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									WaterCamera.this.finish();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							}).show();
			return true;
		}
		else if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && LIKE_IMAGE_IS_FRONT)
		{
			mViewContainer.removeView(mLikeorHateImage);
			mLikeorHateImage = null;
			LIKE_IMAGE_IS_FRONT = false;
			IS_FRONT = false;
		}
		else if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && CARTOON_IMAGE_IS_FRONT)
		{
			mViewContainer.removeView(mCartoonImage);
			mCartoonImage = null;
			CARTOON_IMAGE_IS_FRONT = false;
			IS_FRONT = false;
		}
		else if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && BUBBLE_IMAGE_IS_FRONT)
		{
			mViewContainer.removeView(mBubbleImage);
			mBubbleImage = null;
			BUBBLE_IMAGE_IS_FRONT = false;
			IS_FRONT = false;
		}
		else if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && BEER_IMAGE_IS_FRONT)
		{
			mViewContainer.removeView(mBeerImage);
			mBeerImage = null;
			BEER_IMAGE_IS_FRONT = false;
			IS_FRONT = false;
		}
		return true;
	}

	
//	private void LocationInit() {
//		if (!ServiceProvider.isWirelessEnabled(WaterCamera.this)) {
//			new AlertDialog.Builder(WaterCamera.this)
//					.setTitle("当前网络不可用")
//					.setMessage("您尚未开启网络，部分水印可能无法完整展示。")
//					.setCancelable(false)
//					.setPositiveButton("打开网络",
//							new DialogInterface.OnClickListener() {
//								public void onClick(DialogInterface dialog,
//										int which) {
//									ServiceProvider
//											.openWirelessSetting(WaterCamera.this);
//								}
//							})
//					.setNegativeButton("继续使用",
//							new DialogInterface.OnClickListener() {
//								public void onClick(DialogInterface dialog,
//										int which) {
//									Toast.makeText(WaterCamera.this,
//											"未开启网络，部分水印可能无法完整展示!",
//											Toast.LENGTH_SHORT).show();
//								}
//							}).setCancelable(false).show()
//					.setOnDismissListener(new OnDismissListener() {
//
//						@Override
//						public void onDismiss(DialogInterface dialog) {
//							initGPS();
//						}
//					});
//		} else {
//			initGPS();
//		}
//	}

//	private void initGPS() {
//		if (!ServiceProvider.isGPSProviderEnabled(WaterCamera.this)) {
//			new AlertDialog.Builder(WaterCamera.this)
//					.setTitle("当前GPS定位服务不可用")
//					.setMessage("您尚未开启GPS定位服务，部分水印可能无法完整展示。")
//					.setCancelable(false)
//					.setPositiveButton("打开GPS",
//							new DialogInterface.OnClickListener() {
//								public void onClick(DialogInterface dialog,
//										int which) {
//									ServiceProvider
//											.openLocationSetting(WaterCamera.this);
//								}
//							})
//					.setNegativeButton("继续使用",
//							new DialogInterface.OnClickListener() {
//								public void onClick(DialogInterface dialog,
//										int which) {
//									Toast.makeText(WaterCamera.this,
//											"未开启GPS定位服务，定位服务!",
//											Toast.LENGTH_SHORT).show();
//								}
//							}).setCancelable(false).show()
//					.setOnDismissListener(new OnDismissListener() {
//
//						@Override
//						public void onDismiss(DialogInterface dialog) {
//							initLocation();
//						}
//					});
//		} else {
//			initLocation();
//		}
//	}

	private void initLocation() {
		mCityToID = ServiceProvider.loadMappingTable(this);
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case LocationInfoProvider.LOACTION_INFO_REFRESHED:
					mCity = mLocation = ((LocationInfo) msg.obj).getCity()
					.split("市")[0];
					Log.d("mLocation@@&&&&&&&&&&&&&&&&&&&&&&------>", mLocation+"");
					break;
				case Weather.TEMP_INFO:
					mTemp = (String) msg.obj;
					Log.d("mTemp@@@@@@@@@@@@------>", mTemp+"");
					break;
				case Weather.WEATHER_INFO:
					mWeather = (String) msg.obj;
					Log.d("mWeather@@@@@@@@@@@@------>", mWeather+"");
					break;
				case Weather.LOCATION_INFO:
//					mLocation = mCity = (String)msg.obj;
//					Log.d("mLocation@@@@@@@@@@@@------>", mLocation+"");
					break;
				case Weather.WIND_INFO:
					break;
				}
				
				if(mInitLocationTextMood != 0){
					switch (mInitLocationTextMood) {
					case WaterTemplateBar.MoodBtn_Id:
						setMoodLocationText(mLocation);
						break;
					case WaterTemplateBar.LocationBtn_Id:
						setLocationText(mLocation);
						break;
					case WaterTemplateBar.DefaultBtn_Id:
						break;
					
						}
				   }
			}
		};
		mW = new Weather(this, mHandler);
		mW.startWeatherThread();
		
//		//mWeatherService = new WeatherService(mHandler, null);
//		mLocationService = new LocationService(this, mHandler);
//		mLocationService.startService();
		mProvider.setHandler(mHandler);
		mProvider.startLocationManager();
	}

	public void handleLocationResult(Intent intent) {
		if (null == intent)
			return;
		Bundle data = intent.getExtras();
		mCity = data.getString("city");
		mLocation = data.getString("location");
		Log.d("location", "*************>"+mLocation);
		Log.d("mCity", "*************>"+mCity);
		if(mInitLocationTextMood != 0){
			switch (mInitLocationTextMood) {
			case WaterTemplateBar.MoodBtn_Id:
				setMoodLocationText(mLocation);
				break;
			case WaterTemplateBar.LocationBtn_Id:
				setLocationText(mLocation);
				break;
			case WaterTemplateBar.DefaultBtn_Id:
				break;
			
			}
		}

		Toast.makeText(WaterCamera.this, "******-------*******城市：" + mCity + " 位置：" + mLocation,
				Toast.LENGTH_SHORT).show();
		Log.i("Location", "########## *****++++****城市：" + mCity + " 位置：" + mLocation);

		//mWeatherService.startService(data.getString("cityID"));
	}

	private void initView(){
	mViewContainer = new ViewContainer(this);
	
	ScaleUtils.scaleInit(this, 888, 540, 240);
		
		Params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		Params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		mCameraBar = new CameraBar(this, WaterTemplateBar.DefaultBtn_Id);
		mCameraBar.setUiCmdListener(this);
		mViewContainer.addView(mCameraBar,Params);
		
		mSurfaceViewrCantainer = new FrameLayout(this);
		mSurfaceView = new SurfaceView(this);
		mFLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
		Params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		Params.addRule(RelativeLayout.ABOVE, CameraBar.CAMERA_BAR);
		mHolder = mSurfaceView.getHolder();
		mHolder.addCallback(WaterCamera.this);
		mSurfaceViewrCantainer.addView(mSurfaceView, mFLayoutParams);
		mViewContainer.addView(mSurfaceViewrCantainer, Params);
		
		Params = new RelativeLayout.LayoutParams(1200,1600);	
		Params.addRule(RelativeLayout.ABOVE, CameraBar.CAMERA_BAR);
		mWaterFlipper = new WaterFlipper(this,this);
		mViewContainer.addView(mWaterFlipper,Params);
		
		mTopBar = new TopBar(this);	
		mTopBar.setUiCmdListener(this);	
		Params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);	
		Params.topMargin = ScaleUtils.scale(10);
		Params.leftMargin = ScaleUtils.scale(370);
		mViewContainer.addView(mTopBar,Params);
		mCameraBar.bringToFront();	
		
		mHelper = new GuideViewHelper(this);
		mViewContainer.addView(mHelper);	
		setContentView(mViewContainer);
	}

	@Override
	public int onUiCmd(int key, Object obj) {
		switch (key) {
		case TopBar.FLASH_AUTO:
			parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
			mCamera.setParameters(parameters);
			break;
		case TopBar.FLASH_ON:
			parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
			mCamera.setParameters(parameters);
			break;
		case TopBar.FLASH_OFF:
			parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
			mCamera.setParameters(parameters);
			break;
		case TopBar.BACK_CAMERA:
			Change2BackCamera();
			break;
		case TopBar.FRONT_CAMERA:
			Change2FrontCamera();
			break;
		case LikeorHateTemplate.CRATE_LIKE_OR_HATE_IMAGE:
			Params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			mLikeorHateImage = (LikeorHateImage) obj;
			Toast.makeText(this, "实现", 300).show();
			LIKE_IMAGE_IS_FRONT = true ;
			IS_FRONT = true;
			mViewContainer.addView(mLikeorHateImage,Params);
			mWaterFlipper.setEnabled(false);
			break;
		case LikeorHateTemplate.KILL_LIKE_OR_HATE_IMAGE:
			mViewContainer.removeView(mLikeorHateImage);
			mLikeorHateImage = null;
			LIKE_IMAGE_IS_FRONT = false;
			IS_FRONT = false;
			break;
		case CartoonTemplate.CRATE_CARTOON_IMAGE:
			Params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			mCartoonImage = (CartoonImage) obj;
			mViewContainer.addView(mCartoonImage,Params);			
			CARTOON_IMAGE_IS_FRONT = true;
			IS_FRONT = true;
			break;
		case CartoonTemplate.KILL_CARTOON_IMAGE:
			mViewContainer.removeView(mCartoonImage);
			mCartoonImage = null;
			CARTOON_IMAGE_IS_FRONT = false;
			IS_FRONT = false;
			break;
		case BubbleTemplate.CRATE_BUBBLE_IMAGE:
			 Params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			 mBubbleImage = (BubbleImage) obj;
			 mViewContainer.addView(mBubbleImage, Params);
			 BUBBLE_IMAGE_IS_FRONT = true;
			 IS_FRONT = true;
			 break;
		case BubbleTemplate.KILL_BUBBLE_IMAGE:
			 mViewContainer.removeView(mBubbleImage);
			 mBubbleImage = null;
			 BUBBLE_IMAGE_IS_FRONT = false;
			 IS_FRONT = false;
			 break; 
		case BeerTemplate.CRATE_BEER_IMAGE:
		     Params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			 mBeerImage = (BeerImage) obj;
			 mViewContainer.addView(mBeerImage, Params);
			 BEER_IMAGE_IS_FRONT = true;
			 IS_FRONT = true;
			 break;
		case BeerTemplate.KILL_BEER_IMAGE:
			 mViewContainer.removeView(mBeerImage);
			 mBeerImage = null;
			 BEER_IMAGE_IS_FRONT = false;
			 IS_FRONT = false;
			 break; 
		case PoiTemplate.CLICK:
			Intent mIntent = new Intent(WaterCamera.this, Location.class);
			startActivityForResult(mIntent, 111);
			break;
		case CameraBar.BUTTON_ALBUM_CLICK:
			Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, 55);
			break;
		case CameraBar.BUTTON_LOCATION_CLICK:
			intent = new Intent(WaterCamera.this, Location.class);
			startActivityForResult(intent, 111);
			break;
		case CameraBar.BUTTON_CAPTURE_CLICK:
			 CaptureButtonClick();
			break;
		case CameraBar.BUTTON_WATERMARK_CLICK:
			 WaterButtonClick();
			break;
		case WaterTemplateBar.LocationBtn_Id:
			initLocationBtn();
			break;
		case WaterTemplateBar.TimeBtn_Id:
			initTimeBtn();
			break;
		case WaterTemplateBar.FoodBtn_Id:
			initFoodBtn();
			break;
		case WaterTemplateBar.WeatherBtn_Id:
			initWeatherBtn();
			break;
		case WaterTemplateBar.MoodBtn_Id:
			initMoodBtn();
			break;
		case WaterTemplateBar.DefaultBtn_Id:
			initDefaultBtn();
			break;	
		case 999:
//			EditView v = new EditView(this); 
//			RelativeLayout.LayoutParams rparams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);	
//			rparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//			mViewContainer.addView(v, rparams);
//			InputMethodManager mManger = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
//			//mManger.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//			mCameraBar.setFocusable(false);
//			mManger.showSoftInput(v,0);
//			break;
		}
		return key;
	}
	
	private void initMoodLocationText(int id){
		mLikeoHateLocationText = mWaterFlipper.getmLikeorHateTemplate().getmLocationTextView();
		mBubbleLocationText = mWaterFlipper.getmBubbleTemplate().getmLocationTextView();
		mCartoonLocationText = mWaterFlipper.getmCartoonTemplate().getmLocationTextView();
		mInitLocationTextMood = id ;
	}
	
	private void setMoodLocationText(String s){
		if(s != null){
			mLikeoHateLocationText.setText(s);
			mBubbleLocationText.setText(s);
			mCartoonLocationText.setText(s);
			Log.d("string======", ""+s);
		}
		else{ 
			mLikeoHateLocationText.setText(R.string.updatelocation);
			mBubbleLocationText.setText(R.string.updatelocation);
			mCartoonLocationText.setText(R.string.updatelocation);
		}
	}
	
	private void initLocationText(int id){
		mFreeGoLocationtext = mWaterFlipper.getmFreeGoTemplate().getmLocationText();
		mPoiLocationText = mWaterFlipper.getmPoiTemplate().getmLocationText();
		mInitLocationTextMood = id ;
	}
	
	private void setLocationText(String s){
		if(s != null){
			mFreeGoLocationtext.setText(s);
			mPoiLocationText.setText(s);
			Log.d("string======", ""+s);
		}
		else{
			mFreeGoLocationtext.setText(R.string.updatelocation);
			mPoiLocationText.setText(R.string.updatelocation);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 111 && resultCode == RESULT_OK) 			
			handleLocationResult(data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void Change2BackCamera(){
		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
			mWhichCamera = BACK;
		}
		mCamera = Camera.open(BACK);
		try {
			mCamera.setPreviewDisplay(mHolder);
			mCamera.setDisplayOrientation(90);
			parameters = mCamera.getParameters();
			parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
			parameters.setPictureSize(1600, 1200);
			parameters.setPreviewSize(640, 480);
			mCamera.setParameters(parameters);
			mCamera.startPreview();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void Change2FrontCamera(){
		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
			mWhichCamera = FRONT;
		}
		mCamera = Camera.open(FRONT);
		try {
			mCamera.setPreviewDisplay(mHolder);
//			parameters = mCamera.getParameters();
//			parameters.setPictureSize(1600, 1200);
//			parameters.setPreviewSize(640, 480);
//			mCamera.setParameters(parameters);
			mCamera.setDisplayOrientation(90);
			mCamera.startPreview();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	private void CaptureButtonClick(){
		if (mCamera != null && mWhichCamera == BACK)
			mCamera.autoFocus(WaterCamera.this);			
		if (mCamera != null && mWhichCamera == FRONT)
			mCamera.takePicture(WaterCamera.this, null, null, WaterCamera.this);
	}
	
	private void WaterButtonClick(){
		if (mWaterTemplateBar == null ) {
			mWaterTemplateBar = new WaterTemplateBar(this);
			Log.d("you", "----->u"+mWaterTemplateBar);
			RelativeLayout.LayoutParams mWaterTemplateParams = new RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT, android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
			mWaterTemplateBar.setmUiCmdListener(this);
			mWaterTemplateBar.onUiCmd(mWhichWaterButton, null);
			mWaterTemplateParams.addRule(RelativeLayout.ABOVE, CameraBar.CAMERA_BAR);
			mViewContainer.addView(mWaterTemplateBar, mWaterTemplateParams);
		} else{
			mViewContainer.removeView(mWaterTemplateBar);
			mWaterTemplateBar.setmUiCmdListener(null);
			mWaterTemplateBar = null;
		}
	}
	
	private void initLocationBtn(){
		mWaterFlipper.ChangeChildView(WaterTemplateBar.LocationBtn_Id);
		mCameraBar.setWatermarkButtonIcon(WaterTemplateBar.LocationBtn_Id);
		mWhichWaterButton = WaterTemplateBar.LocationBtn_Id;
		initLocationText(WaterTemplateBar.LocationBtn_Id);
		setLocationText(mLocation);
		mWaterTemplateBar = null;
	}
	
	private void initTimeBtn(){
		mWaterFlipper.ChangeChildView(WaterTemplateBar.TimeBtn_Id);
		mCameraBar.setWatermarkButtonIcon(WaterTemplateBar.TimeBtn_Id);
		mWhichWaterButton = WaterTemplateBar.TimeBtn_Id;
		mWaterTemplateBar = null;
	}
	
	private void initFoodBtn(){
		mWaterFlipper.ChangeChildView(WaterTemplateBar.FoodBtn_Id);
		mCameraBar.setWatermarkButtonIcon(WaterTemplateBar.FoodBtn_Id);
		mWhichWaterButton = WaterTemplateBar.FoodBtn_Id;
		mWaterTemplateBar = null;
	}
	
	private void initWeatherBtn(){
		mWaterFlipper.ChangeChildView(WaterTemplateBar.WeatherBtn_Id);
		mCameraBar.setWatermarkButtonIcon(WaterTemplateBar.WeatherBtn_Id);
		mWhichWaterButton = WaterTemplateBar.WeatherBtn_Id;
		mWaterTemplateBar = null;
	}
	
	private void initMoodBtn(){
		mWaterFlipper.ChangeChildView(WaterTemplateBar.MoodBtn_Id);
		mCameraBar.setWatermarkButtonIcon(WaterTemplateBar.MoodBtn_Id);
		mWhichWaterButton = WaterTemplateBar.MoodBtn_Id;
		initMoodLocationText(WaterTemplateBar.MoodBtn_Id);
		setMoodLocationText(mLocation);
		mWaterTemplateBar = null;
	}
	
	private void initDefaultBtn(){
		mWaterFlipper.ChangeChildView(WaterTemplateBar.DefaultBtn_Id);
		mCameraBar.setWatermarkButtonIcon(WaterTemplateBar.DefaultBtn_Id);
		mWhichWaterButton = WaterTemplateBar.DefaultBtn_Id;
		mWaterTemplateBar = null;
	}
}