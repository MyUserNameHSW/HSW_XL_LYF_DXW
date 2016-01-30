package com.lyy.xl.activities;

import java.lang.reflect.Type;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.xl.bean.StoreBean;
import com.lyy.xl.utils.BasicApplication;

public class StorejianjieActivity extends Activity {
	String url = "http://" + MyApplication.IP + ":8080/MyProject/storeSevlet";
	StoreBean storeBean;
	ImageView store_back, store_picture;
	TextView store_name1, store_address, store_whentime, store_level,
			store_phone;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				addData();
				break;

			default:
				break;
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_storejianjie);
		initView();
		initData();
	}

	private void addData() {
		// TODO Auto-generated method stub
		MyApplication.bitmapUtils.display(store_picture, "http://"
				+ MyApplication.IP + ":8080" + storeBean.getImg());
		Log.e("dddd",
				"http://" + MyApplication.IP + ":8080" + storeBean.getImg());
		store_name1.setText(storeBean.getName());
		store_address.setText(storeBean.getAddress());
		store_level.setText(storeBean.getLevel() + "");
		store_whentime.setText(storeBean.getDepict());
		store_phone.setText(storeBean.getPhone());
	};

	private void initData() {
		// TODO Auto-generated method stub
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "1");
		params.addBodyParameter("s_id", "1");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				Log.e("ssss", "aaaa");
				Gson gson = new Gson();
				Type typeOfT = new TypeToken<List<StoreBean>>() {
				}.getType();
				List<StoreBean> sBeans = gson.fromJson(result, typeOfT);
				storeBean = sBeans.get(0);
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);

			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		store_back = (ImageView) findViewById(R.id.store_back);
		store_picture = (ImageView) findViewById(R.id.store_picture);
		store_name1 = (TextView) findViewById(R.id.store_name1);
		store_address = (TextView) findViewById(R.id.store_address);
		store_whentime = (TextView) findViewById(R.id.store_whentime);
		store_level = (TextView) findViewById(R.id.store_level);
		store_phone = (TextView) findViewById(R.id.store_phone);
	}

}
