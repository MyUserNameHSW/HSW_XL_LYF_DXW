package com.lyy.xl.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
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
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;
import com.lyy.xl.adapter.ReceiveAddressAdapter;
import com.lyy.xl.bean.ReceiveBean;

public class ShouhuoAddressActivity extends Activity implements OnClickListener{
	String url = "http://"+MyApplication.IP+":8080/MyProject/storeSevlet";
	ListView addresslistview;
	Button button;
	public Button getButton() {
		return button;
	}

	ReceiveAddressAdapter myAdapter;
	List<ReceiveBean> list;
	ImageView image_back;
	ListView listView;
	TextView address_textView;
	TextView receive_name,receive_phone,receive_address;
	Intent intent;
	String name1,address1,phone1;
	//List<ReceiveMsgBean>  listbean;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.receive_address);
		
		initViews();
		initView();
		initData();
	}

	
	private void initViews() {
		// TODO Auto-generated method stub
		image_back = (ImageView) findViewById(R.id.image_back);
		address_textView = (TextView) findViewById(R.id.address_textview);
		listView=(ListView) findViewById(R.id.address_listview);
		//单击事件
		button=(Button) findViewById(R.id.receive_address_ok);
		
		 
		
		
		image_back.setOnClickListener(this);
		address_textView.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.image_back:
		finish();
		break;
	case R.id.address_textview:
		intent = new Intent(ShouhuoAddressActivity.this,
				MessageAddressActivity.class);
		startActivity(intent);

	default:
		break;
	}
	
	}
	private void initData() {
		// TODO Auto-generated method stub
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "9");
		params.addBodyParameter("u_id", MyMethod.GetU_id(getApplicationContext())+"");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e("I am so happy", arg1.toString());
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				Log.e("result", result);
				Gson gson = new Gson();
				Type typeOfT = new TypeToken<List<ReceiveBean>>() {
				}.getType();
				List<ReceiveBean> resultList = gson.fromJson(result, typeOfT);
				list.addAll(resultList);
				Log.e("lulu", list.toString());
				myAdapter.notifyDataSetChanged();
			}
		});

	}

	private void initView() {
		list = new ArrayList<ReceiveBean>();
		addresslistview = (ListView) findViewById(R.id.address_listview);
		receive_name=(TextView) findViewById(R.id.receive_name);
		receive_phone=(TextView) findViewById(R.id.receive_phone);
		receive_address=(TextView) findViewById(R.id.receive_address);
		myAdapter=new ReceiveAddressAdapter(list, this);
		addresslistview.setAdapter(myAdapter);	
	}
}
