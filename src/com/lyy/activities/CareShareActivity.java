package com.lyy.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
import com.lyy.adapter.Care_Share_Adapter;
import com.lyy.bean.MyCare_Share;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

public class CareShareActivity extends Activity implements OnClickListener {
    List<MyCare_Share> list;
    ListView listView;
    Button button;
    ImageView back;
    TextView callCare;
    Care_Share_Adapter mAdapter;
    String url = "http://"+MyApplication.IP+":8080/MyProject/Link";
	private int pl_id,pp_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_care_share);
		initView();
		initData();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		mAdapter.notifyDataSetChanged();
		super.onResume();
	}
	private void initData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		pl_id = intent.getIntExtra("pl_id", 0);
		pp_id = intent.getIntExtra("pp_id", 0);
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
				mAdapter.notifyDataSetChanged();
			}
		});
		
	}
	private void initView() {
		// TODO Auto-generated method stub
		list = new ArrayList<MyCare_Share>();
		callCare = (TextView) findViewById(R.id.call_cares);
		back = (ImageView) findViewById(R.id.careshare_back);
		button = (Button) findViewById(R.id.confirm_getback);
		listView = (ListView) findViewById(R.id.care_share_lv);
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
		case R.id.careshare_back:
			this.finish();
			break;
		case R.id.confirm_getback:
			MyMethod.showToast(getApplicationContext(), "确认领回");
			Intent intent = new Intent(getApplicationContext(),MyCareActivity.class);
			deletePCare(pl_id);
			startActivity(intent);
			break;
		case R.id.call_cares:
			String phone = getIntent().getStringExtra("phone");
			Log.e("phone", phone+"--");
			intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ phone));
			this.startActivity(intent);
			break;
		default:
			break;
		}
	}
private void deletePCare(int pl_id){
	HttpUtils utils = new HttpUtils();
	RequestParams params = new RequestParams();
	params.addBodyParameter("flag","46");
	params.addBodyParameter("pl_id",pl_id+"");
	params.addBodyParameter("pp_id",pp_id+"");
	utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

		@Override
		public void onFailure(HttpException arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(ResponseInfo<String> arg0) {
			// TODO Auto-generated method stub
			MyMethod.showToast(getApplicationContext(), "领回成功");
		}
	});
}
}
