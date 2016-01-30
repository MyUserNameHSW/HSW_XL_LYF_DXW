package com.lyy.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import cn.smssdk.gui.layout.ProgressDialogLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.adapter.SCare_Adapter;
import com.lyy.bean.S_provide;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

public class YouCareList2Activity extends Activity {
	ImageView back;
	ListView pListView;
	List<S_provide> list;
	SCare_Adapter mAdapter;
	String url = "http://" + MyApplication.IP + ":8080/MyProject/Link";
	private View view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication) this.getApplication();
		appState.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_you_care_list2);
		initView();
		initData();
	}
    Handler handler = new Handler(){
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
    private void addData(){
    	pListView.setAdapter(mAdapter);	
    }
	private void initData() {
		// TODO Auto-generated method stub
		view.setVisibility(View.VISIBLE);
		pListView.setVisibility(View.GONE);
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "33");
		params.addBodyParameter("pageNum", "1");
		params.addBodyParameter("rec", "0");
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
				Type typeofType = new TypeToken<List<S_provide>>() {
				}.getType();
				List<S_provide> resultList = gson.fromJson(result, typeofType);
				list.addAll(resultList);
				pListView.setVisibility(View.VISIBLE);
				view.setVisibility(View.GONE);
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		});
		mAdapter.notifyDataSetChanged();
	}

	private void initView() {
		// TODO Auto-generated method stub
		list = new ArrayList<S_provide>();
		view = findViewById(R.id.anim3);
		mAdapter = new SCare_Adapter(getApplicationContext(), list);
		back = (ImageView) findViewById(R.id.scarelist_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				YouCareList2Activity.this.finish();
			}
		});
		pListView = (ListView) findViewById(R.id.scare_listview);
		/*LayoutInflater mInflater = LayoutInflater.from(getApplicationContext());
		View view = mInflater.inflate(R.layout.loading_anim, null);
		pListView.setEmptyView(view);*/
		pListView.setAdapter(mAdapter);
		pListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				MyMethod.showToast(getApplicationContext(), list.get(arg2)
						.getSp_id() + "");
				Intent intent = new Intent(YouCareList2Activity.this,
						CareShopActivity.class);
				intent.putExtra("sp_id", list.get(arg2).getSp_id());
				intent.putExtra("s_id", list.get(arg2).getS_id());
				startActivity(intent);
			}
		});
	}

}
