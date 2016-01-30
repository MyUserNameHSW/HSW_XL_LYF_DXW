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
import com.lyy.adapter.Care_Share_Adapter;
import com.lyy.bean.MyCare_Share;
import com.lyy.project.R;
import com.lyy.project.R.layout;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ShareCareActivity extends Activity implements OnClickListener {

	List<MyCare_Share> list;
	ListView listView;
	TextView callCare;
	Button button;
	ImageView back;
	Care_Share_Adapter mAdapter;
	String url = "http://" + MyApplication.IP + ":8080/MyProject/Link";
	private int pl_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_share_care);
		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		pl_id = intent.getIntExtra("pl_id", 0);
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "32");
		params.addBodyParameter("pl_id", pl_id + "");
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
				Type typeofType = new TypeToken<List<MyCare_Share>>() {
				}.getType();
				List<MyCare_Share> myCares = gson.fromJson(result, typeofType);
				list.addAll(myCares);
			}
		});
		mAdapter.notifyDataSetChanged();
	}

	private void initView() {
		// TODO Auto-generated method stub
		list = new ArrayList<MyCare_Share>();
		callCare = (TextView) findViewById(R.id.call_share);
		back = (ImageView) findViewById(R.id.sharecare_back);
		button = (Button) findViewById(R.id.add_share);
		listView = (ListView) findViewById(R.id.share_care_lv);
		mAdapter = new Care_Share_Adapter(getApplicationContext(), list);
		listView.setAdapter(mAdapter);
		back.setOnClickListener(this);
		button.setOnClickListener(this);
		callCare.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.sharecare_back:
			this.finish();
			break;
		case R.id.add_share:
			MyMethod.showToast(getApplicationContext(), "新增分享");
			Intent intent = new Intent(getApplicationContext(),
					AddShareActivity.class);
			intent.putExtra("pl_id", pl_id);
			startActivity(intent);
			break;
			case R.id.call_share:
				String phone = getIntent().getStringExtra("phone");
				Uri uri = Uri.parse("smsto://"+phone);
				Intent intent2 = new Intent(Intent.ACTION_SENDTO, uri);
				startActivity(intent2);
				break;
		default:
			break;
		}
	}

}
