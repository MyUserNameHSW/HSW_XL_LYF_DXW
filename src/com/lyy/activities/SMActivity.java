package com.lyy.activities;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

public class SMActivity extends Activity implements OnClickListener {
	ImageView back, chooses;
	TextView type, time;
	Button submit;
	EditText user, name, getTime;
	String userInp, nameInp, getTimeInp;
	String types;
	int pp_id, u_id;
	String endTime, url = MyMethod.url;
	boolean flag = true;
	LinearLayout linearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication) this.getApplication();
		appState.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sm);
		initView();
		initData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		linearLayout = (LinearLayout) findViewById(R.id.show_sub);
		back = (ImageView) findViewById(R.id.sub_back);
		chooses = (ImageView) findViewById(R.id.sub_choose);
		type = (TextView) findViewById(R.id.sub_type);
		time = (TextView) findViewById(R.id.sub_time);
		submit = (Button) findViewById(R.id.sm_submit);
		user = (EditText) findViewById(R.id.sm_user_phones);
		name = (EditText) findViewById(R.id.sm_plantname);
		getTime = (EditText) findViewById(R.id.user_limitedDate);
		chooses.setOnClickListener(this);
		back.setOnClickListener(this);
		submit.setOnClickListener(this);
	}

	private void initData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		types = intent.getStringExtra("type");
		endTime = intent.getStringExtra("endTime");
		pp_id = intent.getIntExtra("pp_id", 0);
		type.setText(types);
		time.setText(endTime);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.sub_back:
            finish();
			break;
		case R.id.sub_choose:
			if (flag) {
				chooses.setImageResource(R.drawable.btn_toggle_button_cache_on);
				linearLayout.setVisibility(View.VISIBLE);
			} else {
				chooses.setImageResource(R.drawable.btn_toggle_button_cache_off);
				linearLayout.setVisibility(View.INVISIBLE);
			}
			flag = !flag;
			break;
		case R.id.sm_submit:
			userInp = user.getText().toString().trim();
			u_id = MyMethod.byPhoneUser(userInp);
			nameInp = name.getText().toString().trim();
			getTimeInp = getTime.getText().toString().trim();
			insertInfo();
			break;
		default:
			break;
		}
	}

	private void insertInfo() {
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("state",2+"");
		params.addBodyParameter("flag", "39");
		params.addBodyParameter("name", nameInp);
		params.addBodyParameter("pp_id", pp_id + "");
		params.addBodyParameter("u_id", u_id + "");
		params.addBodyParameter("overtime", getTimeInp);
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				if (arg0.result.equals("success")) {
					MyMethod.showToast(getApplicationContext(), "提交成功");
					SMActivity.this.finish();
				}
			}
		});
	}
}
