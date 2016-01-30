package com.lyy.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.bean.MyCare;
import com.lyy.bean.P_provide;
import com.lyy.project.R;
import com.lyy.project.R.layout;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class CaringActivity extends Activity {
    ImageView back,messages,phoneNum;
    TextView type,endTime;
    int pp_id;
    String url = MyMethod.url;
    MyCare myCare;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication) this.getApplication();
		appState.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_caring);
		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag","44");
		params.addBodyParameter("pp_id",pp_id+"");
		utils.send(HttpMethod.POST, url,params ,new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				Gson gson = new Gson();
				Type typeofType = new TypeToken<List<MyCare>>() {
				}.getType();
				List<MyCare> resultList = gson.fromJson(result, typeofType);
				myCare = resultList.get(0);
				type.setText(myCare.getName());
				endTime.setText(myCare.getOver_time());
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		pp_id = intent.getIntExtra("pp_id", 0);
		back = (ImageView) findViewById(R.id.caring_back);
		messages = (ImageView) findViewById(R.id.caring_call_message);
		phoneNum = (ImageView) findViewById(R.id.caring_call_phone);
		type = (TextView) findViewById(R.id.caring_type);
		endTime = (TextView) findViewById(R.id.caring_time);
		messages.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse("smsto://"+myCare.getPhoneNum());
				Intent intent2 = new Intent(Intent.ACTION_SENDTO, uri);
				startActivity(intent2);
			}
		});
		phoneNum.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ myCare.getPhoneNum()));
				startActivity(intent);
			}
		});
	}
    
  
}
