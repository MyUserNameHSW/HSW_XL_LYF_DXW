package com.lyy.activities;

import org.apache.http.Header;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

public class LoginActivity extends Activity implements OnClickListener {
	EditText user, code;
	Button loginButton;
	ImageView back;
	TextView forgetCode, register;
	String username, password;
	String IP = MyApplication.IP;
	private ProgressDialog mDialog;
	boolean isCheck;
	String url = "http://" + IP + ":8080/MyProject/Link";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		user = (EditText) findViewById(R.id.login_user);
		code = (EditText) findViewById(R.id.login_code);
		loginButton = (Button) findViewById(R.id.login_btn);
		back = (ImageView) findViewById(R.id.login_back);

		forgetCode = (TextView) findViewById(R.id.forget_code);
		register = (TextView) findViewById(R.id.login_link1);
		back.setOnClickListener(this);
		loginButton.setOnClickListener(this);
		forgetCode.setOnClickListener(this);
		register.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.login_btn:
			mDialog = new ProgressDialog(LoginActivity.this);
			mDialog.setTitle("请稍等");
			mDialog.setMessage("正在登录...");
			mDialog.show();
			Login();
			break;
		case R.id.login_back:
            LoginActivity.this.finish();
			break;
		case R.id.forget_code:
			Intent intent1 = new Intent(LoginActivity.this,
					ForgetCodeActivity.class);
			startActivity(intent1);
			break;
		case R.id.login_link1:
			Intent intent2 = new Intent(LoginActivity.this,
					RegisterActivity.class);
			startActivity(intent2);
			break;
		default:
			
			break;
		}
	}

	private void Login() {
		// TODO Auto-generated method stub
		username = user.getText().toString().trim();
		password = code.getText().toString().trim();
		Log.e("u", username);
		Log.e("p", password);
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("flag", 1);
		params.put("username", username);
		params.put("password", password);
		client.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				final String result = new String(arg2);
				Log.e("result", result);
				runOnUiThread(new Runnable() {
					public void run() {
						if (result.equals("success")) {
							mDialog.cancel();
							MyMethod.WriteUserInfo(getApplicationContext(), username);
							Toast.makeText(LoginActivity.this, "登陆成功",
									Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(LoginActivity.this,
									MainActivity.class);
							startActivity(intent);
						} else {
							mDialog.cancel();
							Toast.makeText(LoginActivity.this, "账号或密码错误",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub

			}
		});
	}

}
