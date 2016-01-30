package com.lyy.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

public class CareAssessActivity extends Activity {
	ImageView back;
	EditText text;
	Button button;
	String url = MyMethod.url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_care_assess);
		initView();
		
	}

	private void initData() {
		// TODO Auto-generated method stub
		int u_id = MyMethod.GetU_id(getApplicationContext());
		int pp_id = getIntent().getIntExtra("pp_id", 0);
		String texts = text.getText().toString().trim();
		String time = MyMethod.getNowTime();
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("pp_id", pp_id + "");
		params.addBodyParameter("text", texts);
		params.addBodyParameter("time", time);
		params.addBodyParameter("flag", "41");
		params.addBodyParameter("u_id", u_id + "");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				if (arg0.result.equals("success")) {
					MyMethod.showToast(getApplicationContext(), "评价成功");
					finish();
				}
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.care_assess_back);
		text = (EditText) findViewById(R.id.care_assess_text);
		button = (Button) findViewById(R.id.cass_submit);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				initData();
			}
		});
	}

}
