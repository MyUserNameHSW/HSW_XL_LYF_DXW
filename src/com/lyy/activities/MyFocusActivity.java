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
import com.lyy.adapter.MyCare1Adapter;
import com.lyy.adapter.MyFcous_Adapter;
import com.lyy.bean.Essay_Collections;
import com.lyy.bean.MyFocus;
import com.lyy.project.R;
import com.lyy.project.R.layout;
import com.lyy.project.R.menu;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class MyFocusActivity extends Activity implements OnClickListener {
	ImageView myfocus_back;
	ListView myfocus_lv;
	MyFcous_Adapter myFcous_Adapter;
	List<MyFocus> focus = new ArrayList<MyFocus>();
	String url = "http://" + MyApplication.IP + ":8080/MyProject/Link";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication) this.getApplication();
		appState.addActivity(this);
		setContentView(R.layout.activity_my_focus);
		
		initView();
		initData();
	}
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				myFcous_Adapter = new MyFcous_Adapter(focus, MyFocusActivity.this);
				myfocus_lv.setAdapter(myFcous_Adapter);
				break;
			default:
				break;
			}
		};
	};

	private void initData() {
		// TODO Auto-generated method stub
		int u_id = MyMethod.GetU_id(getApplicationContext());
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "29");
		params.addBodyParameter("u_id", u_id + "");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				Gson gson = new Gson();
				Type typeOfT = new TypeToken<List<MyFocus>>() {
				}.getType();
				List<MyFocus> resList = gson.fromJson(result, typeOfT);
				focus.addAll(resList);
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		myfocus_back = (ImageView) findViewById(R.id.myfocus_back);
		myfocus_lv = (ListView) findViewById(R.id.myfocus_lv);
		myfocus_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.myfocus_back:
			MyFocusActivity.this.finish();
			break;
		default:
			break;
		}
	}
}
