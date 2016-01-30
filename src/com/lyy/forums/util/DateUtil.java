package com.lyy.forums.util;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat") 
public class DateUtil {
	public String getTime() {
		// 获取当前时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		Date curDate = new Date(System.currentTimeMillis());
		String time = formatter.format(curDate);
		return time;

	}

}
