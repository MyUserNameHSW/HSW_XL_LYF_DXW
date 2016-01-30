package com.lyy.activities;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.project.R;
import com.lyy.project.R.layout;
import com.lyy.project.R.menu;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class PayMoneyActivity extends Activity {

	ImageView back;
	EditText editText;
	Button button;
	private int p_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_pay_money);
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		p_id = getIntent().getIntExtra("p_id", 0);
		back = (ImageView) findViewById(R.id.pmoney_back);
		editText = (EditText) findViewById(R.id.product_assess_text);
		button = (Button) findViewById(R.id.cassss_submit);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				insertAssess();
				deleteForms();
			}
		});
	}
    private void deleteForms(){
    	int fr_id = getIntent().getIntExtra("fr_id", 0);
    	HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "28");
		params.addBodyParameter("fr_id", fr_id
				+ "");
		utils.send(HttpMethod.POST, MyMethod.url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0,
							String arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
//						String string = arg0.result;
					}
				});
    }
	private void insertAssess() {
		String assess = editText.getText().toString();
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "48");
		params.addBodyParameter("p_id", p_id + "");
		params.addBodyParameter("assess", assess);
		params.addBodyParameter("u_id",MyMethod.GetU_id(getApplicationContext())+"");
		utils.send(HttpMethod.POST, MyMethod.url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						MyMethod.showToast(getApplicationContext(), "评价成功");
						Intent intent = new Intent(getApplicationContext(),
								MainActivity.class);
						startActivity(intent);
					}
				});
	}
}
