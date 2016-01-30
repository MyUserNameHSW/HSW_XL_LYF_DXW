package com.lyy.know.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.bean.Esort2Bean;
import com.lyy.know.adapter.KnowEsortRenZhiAdapter;
import com.lyy.project.R;
import com.lyy.util.MyApplication;

public class KnowRenZhiActivity extends Activity {
	ListView listView;
	List<Esort2Bean> list=new ArrayList<Esort2Bean>();
	KnowEsortRenZhiAdapter adapter;
	HttpUtils httpUtils;
	private String url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_know_ren_zhi);
		initViews();
		initDatas();
	}
	private void initViews() {
		listView=(ListView) findViewById(R.id.bk_rz_lv);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent=new Intent();
				intent.setClass(KnowRenZhiActivity.this, KnowRenZhiDetailActivity.class);
				Esort2Bean esort2Bean=list.get(position);
				int Es2_id=esort2Bean.getEs2_id();
				intent.putExtra("es2_id", Es2_id);
				startActivity(intent);
			}
		});
	}
	private void initDatas() {
		adapter=new KnowEsortRenZhiAdapter(KnowRenZhiActivity.this, list);
		listView.setAdapter(adapter);
		httpUtils=new HttpUtils();
		url="http://"+MyApplication.IP+":8080/MyProject/BaikeServlet";
		RequestParams params=new RequestParams();
		params.addBodyParameter("key","2");
		Intent intent=getIntent();
		int Es1_id=intent.getIntExtra("es1_id",1);
		params.addBodyParameter("Es1_id",""+Es1_id);
		
		httpUtils.send(HttpMethod.POST, url,params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result=responseInfo.result;
						JSONArray array;
						try {
							array=new JSONArray(result);
							for (int i = 0; i < array.length(); i++) {
								JSONObject object=array.getJSONObject(i);
								int Es2_id=object.getInt("es2_id");
								int Es1_id=object.getInt("es1_id");
								String Es2_name=object.getString("es2_name");
								Esort2Bean esort2Bean=new Esort2Bean(Es2_id,Es1_id,Es2_name);
								list.add(esort2Bean);
								Log.e("-------", list.get(0).toString());
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						adapter.notifyDataSetChanged();
					}
				});
		
	}
	public void ComeBack(View view){
		KnowRenZhiActivity.this.finish();
	}
}
