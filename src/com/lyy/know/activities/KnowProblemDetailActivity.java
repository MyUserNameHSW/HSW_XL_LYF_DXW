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
import com.lyy.bean.EssayBean;
import com.lyy.know.adapter.KnowProblemAdapter;
import com.lyy.project.R;
import com.lyy.util.MyApplication;

public class KnowProblemDetailActivity extends Activity {
	ListView listView;
	List<EssayBean> list = new ArrayList<EssayBean>();
	KnowProblemAdapter adapter;
	HttpUtils httpUtils;
	private String url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_know_problem_detail);
		initViews();
		initDatas();
	}
	private void initViews() {
		listView=(ListView) findViewById(R.id.know_problem_detail_lv);
		adapter=new KnowProblemAdapter(KnowProblemDetailActivity.this, list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent=new Intent();
				intent.setClass(KnowProblemDetailActivity.this, KnowRenZhiProblemContentActivity.class);
				EssayBean essayBean=list.get(position);
				String name=essayBean.getName();
				String artical=essayBean.getArtical();
				String image=essayBean.getImage();
				int E_id = essayBean.getE_id();
				intent.putExtra("E_id", E_id);
				intent.putExtra("name", name);
				intent.putExtra("artical",artical);
				intent.putExtra("image", image);
				startActivity(intent);
				
			}
		});
	}
	private void initDatas() {
		httpUtils=new HttpUtils();
		url = "http://" + MyApplication.IP + ":8080/MyProject/BaikeServlet";
		RequestParams params=new RequestParams();
		params.addBodyParameter("key","3");
		Intent intent=getIntent();
		int Es2_id=intent.getIntExtra("es2_id",1);
		params.addBodyParameter("Es2_id",""+Es2_id);		
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result=responseInfo.result;
						
						JSONArray array;
						Log.e("problemdddd",result);
						try {
							array=new JSONArray(result);
							for (int i = 0; i < array.length(); i++) {
								JSONObject object = array.getJSONObject(i);
								int E_id = object.getInt("e_id");
								int Es2_id = object.getInt("es2_id");
								String name = object.getString("name");
								String articale = object.getString("article");
								int col_num = object.getInt("col_num");
								String image = object.getString("image");
								String simage = object.getString("simage");
								EssayBean essayBean = new EssayBean(E_id,
										Es2_id, name, articale, col_num, image,
										simage);
								//Log.e("problem", essayBean.toString());
								list.add(essayBean);
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
		KnowProblemDetailActivity.this.finish();
	}
}
