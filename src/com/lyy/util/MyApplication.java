package com.lyy.util;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.BitmapUtils;
import com.lyy.bean.CartBean;
import com.lyy.project.R;

import android.app.Activity;
import android.app.Application;

public class MyApplication extends Application {
	public static List<CartBean> list;
//	public static final String IP = "10.204.1.21";//何帅伟
	public static final String IP = "115.28.111.135";//何帅伟
	
	public static final String SMSSDK_APPKEY = "b2584c745a6c";
	public static final String SMSSDK_SECRET = "554e6686be3d5c1db3a1aa838ef99e05";
	private List<Activity> mainActivity = new ArrayList<Activity>();
	public static BitmapUtils bitmapUtils;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		bitmapUtils = new BitmapUtils(getApplicationContext());
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.smallimage_fail);
		bitmapUtils.configDefaultLoadingImage(R.drawable.smallimage_load);
		super.onCreate();
	}

	public List<Activity> MainActivity() {
		return mainActivity;
	}

	public void addActivity(Activity act) {
		mainActivity.add(act);
	}

	public void finishAll() {
		for (Activity act : mainActivity) {
			if (!act.isFinishing()) {
				act.finish();
			}
		}
		mainActivity = null;
	}
}
