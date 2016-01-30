package com.lyy.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
import com.lyy.adapter.ListViewAdapter;
import com.lyy.bean.Product;
import com.lyy.bean.Psort_1;
import com.lyy.bean.Psort_2;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

public class PSearchActivity extends Activity {
	private ListView mListView;
	private ImageView psearch_back;
	private ListViewAdapter mListViewAdapter;
	private ArrayList<Psort_1> bList;
	private Button psearch_btn;
	private EditText psearch_text;
	private String search_text;
	List<Product> list;
	HttpUtils httpUtils;
	String url = "http://" + MyApplication.IP + ":8080/MyProject/Link";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_psearch);
		init();
	}

	private void init() {
		list = new ArrayList<Product>();
		bList = new ArrayList<Psort_1>();
		mListView = (ListView) findViewById(R.id.listView);
		mListViewAdapter = new ListViewAdapter(bList, PSearchActivity.this);
		mListView.setAdapter(mListViewAdapter);
		initBigData();

		psearch_btn = (Button) findViewById(R.id.Psearch_btn);
		psearch_text = (EditText) findViewById(R.id.Psearch_et);
		psearch_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				search_text = psearch_text.getText().toString().trim();
				if (search_text == null || search_text.equals("")) {
					MyMethod.showToast(getApplicationContext(), "请输入查询内容");
					return;
				}
				Intent intent = new Intent(getApplicationContext(),
						Type1_1Activity.class);
				intent.putExtra("search_text", search_text);
				startActivity(intent);
			}
		});
		psearch_back = (ImageView) findViewById(R.id.psearch_back);
		psearch_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				PSearchActivity.this.finish();
			}
		});
	}

	public void psearch_back(View view) {
		PSearchActivity.this.finish();
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