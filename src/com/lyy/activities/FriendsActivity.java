package com.lyy.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.adapter.Friend_form;
import com.lyy.bean.MyForums;
import com.lyy.forums.activities.ForumDetailActivity;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

public class FriendsActivity extends Activity {
	ImageView fri_head, fri_back;
	TextView fri_name;
	ListView ff_lv;
	private int fu_id;
	private String fu_name;
	List<MyForums> list;
	Friend_form form;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_friends);
		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub

		Intent intent = getIntent();
		fu_id = intent.getIntExtra("fu_id", 0);
		fu_name = intent.getStringExtra("fu_name");
		fri_name.setText(fu_name);
		String imgurl = MyMethod.headImg(fu_id);
		MyApplication.bitmapUtils.display(fri_head, imgurl);
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "49");
		Log.e("fu_id", fu_id+"");
		params.addBodyParameter("fu_id", fu_id + "");
		utils.send(HttpMethod.POST, MyMethod.url, params,
				new RequestCallBack<String>() {

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
						Type typeOfT = new TypeToken<List<MyForums>>() {
						}.getType();
						List<MyForums> resultList = gson.fromJson(result, typeOfT);
						Log.e("ewew", resultList.get(0).toString());
						list.addAll(resultList);
						form.notifyDataSetChanged();
					}
				});
	}

	private void initView() {
		// TODO Auto-generated method stub
		list = new ArrayList<MyForums>();
		fri_back = (ImageView) findViewById(R.id.finfo_back);
		fri_head = (ImageView) findViewById(R.id.fone);
		fri_name = (TextView) findViewById(R.id.finfo_username);
		ff_lv = (ListView) findViewById(R.id.fforums);
//		ff_lv.setEmptyView(emptyView);
		fri_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		form = new Friend_form(list, getApplicationContext());
		ff_lv.setAdapter(form);
		ff_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),ForumDetailActivity.class);
				int f_id = list.get(arg2).getF_id();
				String username = list.get(arg2).getUsername();
				String userimg = list.get(arg2).getUserimg();
				int u_id = list.get(arg2).getU_id();
				String f_time = list.get(arg2).getF_time();
				String articl = list.get(arg2).getArticl();
				String theme = list.get(arg2).getTheme();
				intent.putExtra("f_id", f_id);
				intent.putExtra("u_id", u_id);
				intent.putExtra("username", username);
				intent.putExtra("userimg", userimg);
				intent.putExtra("f_time", f_time);
				intent.putExtra("articl", articl);
				intent.putExtra("theme", theme);
				startActivity(intent);
			}
		});
	}

}
