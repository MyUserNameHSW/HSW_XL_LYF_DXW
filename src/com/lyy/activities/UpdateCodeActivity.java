package com.lyy.activities;

import android.app.Activity;
import android.content.Intent;
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

public class UpdateCodeActivity extends Activity implements OnClickListener {
	EditText newCode, confirmCode;
	Button editButton;
	ImageView ucode_back;
	String IP = MyApplication.IP;
	String code1 = "", code2 = "";
	private String phone = "";
    int u_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_update_code);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		Intent intent = this.getIntent();
		bundle = intent.getExtras();
		if (bundle != null) {
			phone = bundle.getString("phone");
		}
		u_id = MyMethod.GetU_id(getApplicationContext());
		// phone = "15737954793";

		newCode = (EditText) findViewById(R.id.newcode);
		confirmCode = (EditText) findViewById(R.id.confirm_code);
		editButton = (Button) findViewById(R.id.editcode_btn);
		ucode_back = (ImageView) findViewById(R.id.ucode_back);
		ucode_back.setOnClickListener(this);
		editButton.setOnClickListener(this);

	}

	private void checkNums() {
		// TODO Auto-generated method stub
		if (code1 == null) {
			MyMethod.showToast(getApplicationContext(), "新密码不能为空");
		} else {
			if (code2 == null) {
				MyMethod.showToast(getApplicationContext(), "请输入确认密码");
			} else {
				if (!code1.equals(code2)) {
					MyMethod.showToast(getApplicationContext(), "两次密码不一样");
				} else {
					insertData();
					Intent intent = new Intent(getApplicationContext(),
							LoginActivity.class);
					startActivity(intent);
				}
			}
		}
	}

	private void insertData() {
		// TODO Auto-generated method stub
		String url = "http://" + IP + ":8080/MyProject/Link";
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "10");
		params.addBodyParameter("u_id", u_id+"");
		params.addBodyParameter("phone", phone);
		params.addBodyParameter("password", code2);
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				if (result.equals("success")) {
					MyMethod.showToast(getApplicationContext(), "密码修改成功");
				} else {
					MyMethod.showToast(getApplicationContext(), "密码修改失败");
				}
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.editcode_btn:
			code1 = newCode.getText().toString().trim();
			code2 = confirmCode.getText().toString().trim();
			checkNums();
			break;
		case R.id.ucode_back:
			UpdateCodeActivity.this.finish();
		default:
			break;
		}
	}
}
