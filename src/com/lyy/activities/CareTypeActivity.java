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
import com.lyy.adapter.CareTypeAdapter;
import com.lyy.adapter.ListViewAdapter;
import com.lyy.bean.Product;
import com.lyy.bean.Psort_1;
import com.lyy.bean.Psort_2;
import com.lyy.project.R;
import com.lyy.project.R.id;
import com.lyy.project.R.layout;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class CareTypeActivity extends Activity {

	private ListView mListView;
	private ImageView psearch_back;
	private CareTypeAdapter mListViewAdapter;
	private ArrayList<Psort_1> bList;
	private String search_text;
	List<Product> list;
	HttpUtils httpUtils;
	String url = "http://" + MyApplication.IP + ":8080/MyProject/Link";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_care_type);
		init();
	}

	private void init() {
		list = new ArrayList<Product>();
		bList = new ArrayList<Psort_1>();
		mListView = (ListView) findViewById(R.id.listView);
		mListViewAdapter = new CareTypeAdapter(bList, CareTypeActivity.this);
		mListView.setAdapter(mListViewAdapter);
		initBigData();

		psearch_back = (ImageView) findViewById(R.id.ctype_back);
		psearch_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				CareTypeActivity.this.finish();
			}
		});
	}

	public void psearch_back(View view) {
		CareTypeActivity.this.finish();
	}

	private void initBigData() {
		bList = new ArrayList<Psort_1>();
		httpUtils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "14");
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						String resule = arg0.result;
						Gson gson = new Gson();
						Type typeOfT = new TypeToken<List<Psort_1>>() {
						}.getType();
						List<Psort_1> resultList = gson.fromJson(resule,
								typeOfT);
						bList.addAll(resultList);
						initSmalldata();
					}
				});

	}

	private void initSmalldata() {
		new ArrayList<Psort_2>();
		RequestParams params;
		for (int i = 0; i < bList.size(); i++) {
			final int ii = i;
			params = new RequestParams();
			params.addBodyParameter("flag", "15");
			params.addBodyParameter("ps1_id", bList.get(i).getPs1_id() + "");
			httpUtils.send(HttpMethod.POST, url, params,
					new RequestCallBack<String>() {
						@Override
						public void onFailure(HttpException arg0, String arg1) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
							// TODO Auto-generated method stub
							String smallTypes = arg0.result;
							Gson gson = new Gson();
							Type typeOfT = new TypeToken<List<Psort_2>>() {
							}.getType();
							ArrayList<Psort_2> resultList = gson.fromJson(
									smallTypes, typeOfT);
							bList.get(ii).setSmalllist(resultList);
							mListViewAdapter.setmList(bList);
							mListViewAdapter.notifyDataSetChanged();
						}
					});
		}
	}
}
