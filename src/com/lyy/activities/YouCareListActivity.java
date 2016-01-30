package com.lyy.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
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
import com.lyy.adapter.PCare_Adapter;
import com.lyy.bean.P_provide;
import com.lyy.forums.adapter.Forums_GridViewAdapter.viewHolder;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

public class YouCareListActivity extends Activity {
	ImageView back;
	ListView pListView;
	List<P_provide> list;
	PCare_Adapter mAdapter;
	private View view; 
	String url = "http://" + MyApplication.IP + ":8080/MyProject/Link";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication) this.getApplication();
		appState.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_you_care_list);
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
	    	/*mAdapter.notifyDataSetChanged();
	    	pListView.setAdapter(mAdapter);*/
	    	
	    }
	private void initData() {
		// TODO Auto-generated method stub
		view.setVisibility(View.VISIBLE);
		pListView.setVisibility(View.GONE);
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "34");
		params.addBodyParameter("pageNum", "1");
		params.addBodyParameter("rec", "0");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException  arg0, String arg1) {
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
				pListView.setVisibility(View.VISIBLE);
				view.setVisibility(View.GONE);
/*				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);*/
				for (int i = 0; i < resultList.size(); i++) {
					Log.e("shhhshshd---", resultList.get(i).toString());
				}
				mAdapter.notifyDataSetChanged();
				
			}
		});
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		list = new ArrayList<P_provide>();
		view = findViewById(R.id.anim2); 
		mAdapter = new PCare_Adapter(getApplicationContext(), list);
		pListView = (ListView) findViewById(R.id.pcare_listview);
		/*LayoutInflater mInflater = LayoutInflater.from(getApplicationContext());
		View view = mInflater.inflate(R.layout.loading_anim, null);
		pListView.setEmptyView(view);*/
		pListView.setAdapter(mAdapter);
		back = (ImageView) findViewById(R.id.pcarelist_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		pListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				MyMethod.showToast(getApplicationContext(), list.get(arg2)
						.getPp_id() + "");
				Intent intent = new Intent(YouCareListActivity.this,
						PersonCareActivity.class);
				intent.putExtra("pp_id", list.get(arg2).getPp_id());
				startActivity(intent);
			}
		});
	}

}
