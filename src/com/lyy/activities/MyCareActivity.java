package com.lyy.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.adapter.MyCare1Adapter;
import com.lyy.bean.Collections;
import com.lyy.bean.MyCare;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

public class MyCareActivity extends Activity implements OnClickListener {
	ImageView mycare_back;
	GridView mycare_lv;
	MyCare1Adapter mAdapter;
	List<MyCare> list;
	String IP = MyApplication.IP;
	String url = "http://" + IP + ":8080/MyProject/Link";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication) this.getApplication();
		appState.addActivity(this);
		setContentView(R.layout.activity_my_care);
		initView();
		initData();
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				mAdapter = new MyCare1Adapter(getApplicationContext(), list);
				mycare_lv.setAdapter(mAdapter);
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
		params.addBodyParameter("flag", "31");
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
				Type typeofType = new TypeToken<List<MyCare>>() {
				}.getType();
				List<MyCare> myCares = gson.fromJson(result, typeofType);
				list.addAll(myCares);
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		list = new ArrayList<MyCare>();
		mycare_back = (ImageView) findViewById(R.id.mycare_back);
		mycare_lv = (GridView) findViewById(R.id.mycare_gv);
		mycare_back.setOnClickListener(this);
		mycare_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						CareShareActivity.class);
				intent.putExtra("pl_id", list.get(arg2).getPl_id());
				intent.putExtra("pp_id", list.get(arg2).getPp_id());
				intent.putExtra("phone", list.get(arg2).getPhoneNum());
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.mycare_back:
			MyCareActivity.this.finish();
			break;

		default:
			break;
		}
	}
}
