package com.lyy.xl.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Application;
import android.util.Log;

import com.lidroid.xutils.BitmapUtils;
import com.lyy.project.R;

public class BasicApplication extends Application {
	public static BitmapUtils bitmapUtils;
	public static String NOWTIME;
	public static final String IP = "10.204.1.45";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		bitmapUtils = new BitmapUtils(getApplicationContext());
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_launcher);
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
		super.onCreate();
	}

	public static String getNowTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		NOWTIME = sdf.format(new Date());
		Log.e("time", NOWTIME);
		return NOWTIME;
	}

}
