package com.lyy.xl.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;
import com.lyy.xl.bean.ReceiveBean;
import com.lyy.xl.utils.BasicApplication;

public class MessageAddressActivity extends Activity implements OnClickListener {
	String url = "http://" + MyApplication.IP
			+ ":8080/MyProject/storeSevlet";
	ReceiveBean receiveBeans;
	ImageView image_back;
	EditText name_person, phone_person, address_person;
	Button address_ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_message_address);
		initView();

	}

	private void initDatas() {
		// TODO Auto-generated method stub
		String name = name_person.getText().toString();
		String phone = phone_person.getText().toString();
		String address = address_person.getText().toString();
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "8");
		params.addBodyParameter("u_id", MyMethod.GetU_id(getApplicationContext())+"");
		params.addBodyParameter("name", name);
		params.addBodyParameter("phone", phone);
		params.addBodyParameter("address", address);
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				Toast.makeText(getApplicationContext(), result,
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		image_back = (ImageView) findViewById(R.id.image_back);
		name_person = (EditText) findViewById(R.id.name_person);
		phone_person = (EditText) findViewById(R.id.phone_person);
		address_person = (EditText) findViewById(R.id.address_person);
		address_ok = (Button) findViewById(R.id.address_ok);
		image_back.setOnClickListener(this);
		address_ok.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		Intent intent;
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.image_back:
			finish();
			break;
		case R.id.address_ok:
			initDatas();
			finish();
			break;
		default:
			break;
		}
	}

}
