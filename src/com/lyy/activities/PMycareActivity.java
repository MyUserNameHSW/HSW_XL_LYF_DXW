package com.lyy.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.lyy.adapter.PMcare_Adapter;
import com.lyy.bean.MyCare;
import com.lyy.bean.P_provide;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

public class PMycareActivity extends Activity {
	String url = MyMethod.url;
	ImageView pmycare_back;
	ListView pmycare_lv;
	PMcare_Adapter mAdapter;
	List<MyCare> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication) this.getApplication();
		appState.addActivity(this);
		setContentView(R.layout.activity_pmycare);
		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		int u_id = MyMethod.GetU_id(getApplicationContext());
		int state = 2;
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "40");
		params.addBodyParameter("u_id", u_id + "");
		params.addBodyParameter("state", state + "");
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
				mAdapter.notifyDataSetChanged();
			}
		});

	}

	private void initView() {
		// TODO Auto-generated method stub
		list = new ArrayList<MyCare>();
		mAdapter = new PMcare_Adapter(list, getApplicationContext());
		pmycare_back = (ImageView) findViewById(R.id.pmycare_back);
		pmycare_lv = (ListView) findViewById(R.id.pmy_listview);
		pmycare_lv.setAdapter(mAdapter);
		pmycare_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		pmycare_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						ShareCareActivity.class);
				intent.putExtra("pl_id", list.get(arg2).getPl_id());
				startActivity(intent);
			}
		});
	}

}
