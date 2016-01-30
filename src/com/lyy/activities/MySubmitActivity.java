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
import com.lyy.adapter.MySubmit_Adapter;
import com.lyy.bean.PC_assess;
import com.lyy.bean.P_provide;
import com.lyy.project.R;
import com.lyy.project.R.layout;
import com.lyy.project.R.menu;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class MySubmitActivity extends Activity {
    ListView listView;
    ImageView back;
    String url = MyMethod.url;
    List<P_provide> list;
    MySubmit_Adapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_submit);
		initView();
		initData();
	}
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				mAdapter = new MySubmit_Adapter(getApplicationContext(), list);
				mAdapter.notifyDataSetChanged();
				listView.setAdapter(mAdapter);
				break;
			default:
				break;
			}
		};
	};
	private void initData() {
		// TODO Auto-generated method stub
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("u_id", MyMethod.GetU_id(getApplicationContext()) + "");
		params.addBodyParameter("flag", "37");
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
				Type typeofType = new TypeToken<List<P_provide>>() {
				}.getType();
				List<P_provide> resultList = gson.fromJson(result, typeofType);
				list.addAll(resultList);
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		});
	}
	private void initView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.ms_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		list = new ArrayList<P_provide>();
		listView = (ListView) findViewById(R.id.ms_listview);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (list.get(arg2).getState() == 1) {
					Intent intent = new Intent(getApplicationContext(),SMActivity.class);
					intent.putExtra("type", list.get(arg2).getPlant_type());
					intent.putExtra("endTime", list.get(arg2).getEndTime());
					intent.putExtra("pp_id", list.get(arg2).getPp_id());
					startActivity(intent);
				}else if (list.get(arg2).getState() == 2) {
					Intent intent = new Intent(getApplicationContext(),CaringActivity.class);
					intent.putExtra("type", list.get(arg2).getPlant_type());
					intent.putExtra("endTime", list.get(arg2).getEndTime());
					intent.putExtra("pp_id", list.get(arg2).getPp_id());
					startActivity(intent); 
				}
			}
		});
	}
    
}
