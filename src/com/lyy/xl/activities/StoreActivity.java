package com.lyy.xl.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.project.R;
import com.lyy.project.R.id;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;
import com.lyy.xl.adapter.StoreAdapter;
import com.lyy.xl.bean.ProductBean;
import com.lyy.xl.bean.S_ColBean;
import com.lyy.xl.bean.StoreBean;

public class StoreActivity extends Activity implements OnClickListener {
	String url = "http://" + MyApplication.IP + ":8080/MyProject/storeSevlet";
	StoreBean storeBean;
	List<S_ColBean> list = new ArrayList<S_ColBean>();;
	List<ProductBean> listpb;
	ListView store_listview;
	StoreAdapter adapter;
	int u_id;
	ImageView store_back, store_collection,mystore_img;
	TextView store_name, store_jianjie,mystore_name,mystore_jianjie,mystore_address,mystore_phone;
	boolean flag = true;
    
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				addData();
				break;
			case 2:
				addData1();
				break;
			default:
				break;
			}

		}

	};
	private int s_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_store1);
		u_id = MyMethod.GetU_id(getApplicationContext());
		s_id = Integer.parseInt(getIntent().getStringExtra("s_id"));
		Log.e("s_id", s_id + "");
		initView();
		initViews();
		initDatas();
		initData();
		initData1();
	}

	private void addData1() {
		MyApplication.bitmapUtils.display(mystore_img, "http://"+MyApplication.IP+":8080"+storeBean.getImg());
		mystore_name.setText(storeBean.getName());
		mystore_jianjie.setText(storeBean.getDepict());
		mystore_address.setText("店铺地址："+storeBean.getAddress());
		mystore_phone.setText("客服电话： "+storeBean.getPhone());
	}

	private void initData1() {
		// TODO Auto-generated method stub
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "1");
		params.addBodyParameter("s_id", s_id + "");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
        
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				Log.e("ssss", result);
				Gson gson = new Gson();
				Type typeOfT = new TypeToken<List<StoreBean>>() {
				}.getType();
				List<StoreBean> sBeans = gson.fromJson(result, typeOfT);
				storeBean = sBeans.get(0);
				Message msg = new Message();
				msg.what = 2;
				handler.sendMessage(msg);

			}
		});
	}

	private void addData() {
		// TODO Auto-generated method stub
		//store_name.setText(storeBean.getName());
	}

	private void initDatas() {
		// TODO Auto-generated method stub

		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "1");
		params.addBodyParameter("s_id", s_id + "");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				Log.e("ssss", "aaaa");
				Gson gson = new Gson();
				Type typeOfT = new TypeToken<List<StoreBean>>() {
				}.getType();
				List<StoreBean> sBeans = gson.fromJson(result, typeOfT);
				storeBean = sBeans.get(0);
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		});
	}

	private void initData() {
		// TODO Auto-generated method stub
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "2");
		params.addBodyParameter("s_id", s_id+"");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e("大坏蛋", "讨厌你");
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				// System.out.println(result);
				Log.e("好人", "you 赢了");
				Gson gson = new Gson();
				Type typeOfT = new TypeToken<List<ProductBean>>() {
				}.getType();
				List<ProductBean> resultList = gson.fromJson(result, typeOfT);
				listpb.addAll(resultList);
				Log.e("xu", listpb.toString());
				adapter.notifyDataSetChanged();
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		mystore_img = (ImageView) findViewById(R.id.mystore_img);
		mystore_name = (TextView) findViewById(R.id.mystore_name);
		mystore_jianjie = (TextView) findViewById(R.id.mystore_jianjie);
		mystore_address = (TextView) findViewById(R.id.mystore_address);
		mystore_phone = (TextView) findViewById(R.id.mystore_phone);
		
		
		store_back = (ImageView) findViewById(R.id.store_back2);
		store_collection = (ImageView) findViewById(R.id.store_collection);
		store_back.setOnClickListener(this);
		store_collection.setOnClickListener(this);
		getpanduan();
	}

	private void getpanduan() {
		// TODO Auto-generated method stub
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "15");
		params.addBodyParameter("s_id", s_id+"");
		params.addBodyParameter("u_id", u_id+"");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e("你", "网络时代");
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				Gson gson = new Gson();
				Type typeOfT = new TypeToken<List<S_ColBean>>() {
				}.getType();
				List<S_ColBean> resultList = gson.fromJson(result, typeOfT);

				list.addAll(resultList);
				if (list.size() != 0) {
					flag = true;
					store_collection
							.setImageResource(R.drawable.lyy_already_colloection);
					
				} else {
					flag = false;
					store_collection
							.setImageResource(R.drawable.lyy_collection);
				}

			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		Intent intent;
		switch (v.getId()) {
		case R.id.store_back2:
			finish();
			break;
		/*case R.id.store_jianjie:
			intent = new Intent(StoreActivity.this, StorejianjieActivity.class);
			startActivity(intent);
			break;*/
		case R.id.store_collection:
			if (!flag) {

				store_collection.setImageResource(R.drawable.lyy_collection);
				getcollect();
				flag = false;
			} else {
				store_collection
						.setImageResource(R.drawable.lyy_already_colloection);
				removecollect();
				flag = true;
			}
			flag = !flag;
			break;

		default:
			break;
		}
	}

	private void getcollect() {
		// TODO Auto-generated method stub
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "13");
		params.addBodyParameter("s_id", s_id+"");
		params.addBodyParameter("u_id", u_id+"");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e("你失败了", "网络时代");
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				Toast.makeText(StoreActivity.this, result, 0).show();
				store_collection
						.setImageResource(R.drawable.lyy_already_colloection);

			}
		});
	}

	private void removecollect() {
		// TODO Auto-generated method stub
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "14");
		params.addBodyParameter("s_id", s_id+"");
		params.addBodyParameter("u_id", u_id+"");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				Toast.makeText(StoreActivity.this, result, 0).show();
				store_collection.setImageResource(R.drawable.lyy_collection);

			}
		});
	}

	private void initViews() {
		// TODO Auto-generated method stub
		listpb = new ArrayList<ProductBean>();
		store_listview = (ListView) findViewById(R.id.store_listview);
		store_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				 Intent intent=new
				 Intent(StoreActivity.this,DetailActivity.class);
				 bundle.putInt("p_id", listpb.get(position).getP_id());
//				 intent.putExtra("xulu", listpb.get(position).getP_id());
				 Log.e("p_id", listpb.get(position).getP_id()+"++++");
				 intent.putExtras(bundle);
				 startActivity(intent);
			}
		});
		adapter = new StoreAdapter(listpb, getApplicationContext());
		store_listview.setAdapter(adapter);
	}

}
