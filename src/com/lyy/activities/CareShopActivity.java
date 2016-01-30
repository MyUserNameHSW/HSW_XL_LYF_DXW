package com.lyy.activities;

import java.lang.reflect.Type;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.lyy.bean.S_provide;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

public class CareShopActivity extends Activity implements OnClickListener {

	String url = "http://" + MyApplication.IP + ":8080/MyProject/Link";
	TextView tv1, tv2, tv3, tv4;
	ImageView iv1, iv2, iv3,iv4;
	private int sp_id,s_id;
	Intent intent = null;
	S_provide provide;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication) this.getApplication();
		appState.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_care_shop);
		initExtra();
		initData();
		initView();
	}

	private void initExtra() {
		// TODO Auto-generated method stub
		intent = getIntent();
		sp_id = intent.getIntExtra("sp_id", 0);
		s_id = intent.getIntExtra("s_id", 0);
				}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				addData();
				break;

			default:
				break;
			}
		};
	};

	private void addData() {
		 MyApplication.bitmapUtils.display(iv1,
		 "http://"+MyApplication.IP+":8080"+provide.getImg());
		tv1.setText("店铺名     "+provide.getShop_name());
		tv2.setText("店铺地址 :  "+ provide.getShop_address());
		tv3.setText("店铺简介 : "+provide.getSctipt());
	}

	private void initData() {
		// TODO Auto-generated method stub
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "33");
		params.addBodyParameter("pageNum", "1");
		params.addBodyParameter("rec", "0");
		params.addBodyParameter("sp_id", sp_id + "");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				Log.e("result", result);
				Gson gson = new Gson();
				Type typeofType = new TypeToken<List<S_provide>>() {
				}.getType();
				List<S_provide> resultList = gson.fromJson(result, typeofType);
				provide = resultList.get(0);
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		});

	}

	private void initView() {
		// TODO Auto-generated method stub
		tv1 = (TextView) findViewById(R.id.care_shop_name);
		tv2 = (TextView) findViewById(R.id.care_shop_address);
		tv3 = (TextView) findViewById(R.id.care_shop_script);
		tv4 = (TextView) findViewById(R.id.care_to_shop);
		iv1 = (ImageView) findViewById(R.id.care_shop_top_img);
		iv2 = (ImageView) findViewById(R.id.call_phone);
		iv3 = (ImageView) findViewById(R.id.call_message);
		iv4 = (ImageView) findViewById(R.id.care_shops_back);
		tv4.setOnClickListener(this);
		iv2.setOnClickListener(this);
		iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.care_to_shop:
            MyMethod.showToast(getApplicationContext(), "店铺id-->"+s_id);
			break;
		case R.id.call_phone:
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ "15737954793"));
			this.startActivity(intent);
			break;
		case R.id.call_message:
			Uri uri = Uri.parse("smsto://15737954793");
			Intent intent2 = new Intent(Intent.ACTION_SENDTO, uri);
			this.startActivity(intent2);
			break;
		case R.id.care_shops_back:
			finish();
			break;
		default:
			break;
		}
	}
}
