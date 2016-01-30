package com.lyy.activities;

import org.apache.http.Header;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.lidroid.xutils.HttpUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lyy.project.R;
import com.lyy.project.R.layout;
import com.lyy.project.R.menu;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {
	EditText user, code, phoneNum, validate, register_yanzheng;
	Button registerBtn, validateBtn;
	ImageView back;
	TextView login;
	String username = "", password = "", phone = "";
	String IP = MyApplication.IP;
	boolean isRegister;
	boolean isPhone = false;
	boolean flag = false;
	int i = 30;
	String url = "http://" + IP + ":8080/MyProject/Link";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		// 启动短信验证sdk
		SMSSDK.initSDK(this, MyApplication.SMSSDK_APPKEY,
				MyApplication.SMSSDK_SECRET);
		EventHandler eventHandler = new EventHandler() {
			@Override
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		};
		SMSSDK.registerEventHandler(eventHandler);
	}

	private void initView() {
		// TODO Auto-generated method stub
		user = (EditText) findViewById(R.id.register_user);
		code = (EditText) findViewById(R.id.register_code);
		phoneNum = (EditText) findViewById(R.id.register_phone);
		register_yanzheng = (EditText) findViewById(R.id.register_yanzheng);
		validateBtn = (Button) findViewById(R.id.yanzheng);
		registerBtn = (Button) findViewById(R.id.regisiter_btn);
		login = (TextView) findViewById(R.id.register_link1);
		back = (ImageView) findViewById(R.id.register_back);

		validateBtn.setOnClickListener(this);
		registerBtn.setOnClickListener(this);
		login.setOnClickListener(this);
		back.setOnClickListener(this);
		user.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				username = user.getText().toString().trim();
				checkUsername();
			}
		});
	}

	private void checkUsername() {
		// TODO Auto-generated method stub
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("flag", "11");
		params.put("username", username);
		client.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				final String results = new String(arg2);
				runOnUiThread(new Runnable() {
					public void run() {
						if (results.equals("success")) {
//							MyMethod.showToast(getApplicationContext(), "用户名可用");
						} else if (results.equals("failed")) {
							MyMethod.showToast(getApplicationContext(),
									"用户名已经被注册");
							user.setText("");
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

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {

		case R.id.yanzheng:
			phone = phoneNum.getText().toString().trim();
			if (!MyMethod.judgePhoneNums(phone, getApplicationContext())) {
				return;
			}
			checkPhone();
			Log.e("isphone", isPhone + "");
			// 2. 通过sdk发送短信验证

			break;
		case R.id.regisiter_btn:
			SMSSDK.submitVerificationCode("86", phone, register_yanzheng
					.getText().toString().trim());
			createProgressBar();
			break;
		case R.id.register_link1:
			Intent intent = new Intent(RegisterActivity.this,
					LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.register_back:
			RegisterActivity.this.finish();
		default:
			break;
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == -9) {
				validateBtn.setText("重新发送(" + i + ")");
				validateBtn.setClickable(false);
			} else if (msg.what == -8) {
				validateBtn.setText("重新发送");
				validateBtn.setClickable(true);
				i = 30;
			} else {
				int event = msg.arg1;
				int result = msg.arg2;
				Object data = msg.obj;
				Log.e("event", "event=" + event);
				if (result == SMSSDK.RESULT_COMPLETE) {

					// 短信注册成功后，返回MainActivity,然后提示
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
						flag = true;
						Log.e("jieguo", flag + "");
						checkRegister();
						Toast.makeText(getApplicationContext(), "提交验证码成功",
								Toast.LENGTH_SHORT).show();
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {

						Toast.makeText(getApplicationContext(), "验证码已经发送",
								Toast.LENGTH_SHORT).show();
					} else {
						((Throwable) data).printStackTrace();
					}
				}
			}
		}
	};

	private void checkRegister() {
		// TODO Auto-generated method stub
		username = user.getText().toString().trim();
		password = code.getText().toString().trim();
		phone = phoneNum.getText().toString().trim();

		if (username.equals("")) {
			Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT)
					.show();
		} else if (password.equals("")) {
			Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT)
					.show();
		} else if (phone.equals("")) {
			Toast.makeText(RegisterActivity.this, "手机号不能为空", Toast.LENGTH_SHORT)
					.show();
		} else {
			Log.e("-------------------", username + "sssssss");
			AsyncHttpClient client = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			params.put("flag", 7);
			params.put("phone", phone);
			params.put("username", username);
			params.put("password", password);
			client.post(url, params, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					// TODO Auto-generated method stub
					final String result = new String(arg2);
					runOnUiThread(new Runnable() {
						public void run() {
							if (result.equals("success")) {
								Toast.makeText(RegisterActivity.this, "注册成功",
										Toast.LENGTH_SHORT).show();
								Intent intent = new Intent(
										RegisterActivity.this,
										LoginActivity.class);
								startActivity(intent);
							} else {
								Toast.makeText(RegisterActivity.this, "注册失败",
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

	private void checkPhone() {
		// TODO Auto-generated method stub

		AsyncHttpClient client = new AsyncHttpClient();
		// String url = "http://" + IP + ":8080/MyProject/Link";
		RequestParams params = new RequestParams();
		params.put("flag", 6);
		params.put("phone", phone);
		client.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				final String result = new String(arg2);
				runOnUiThread(new Runnable() {
					public void run() {
						if (result.equals("success")) {
							Toast.makeText(RegisterActivity.this, "手机号可用",
									Toast.LENGTH_SHORT).show();
							SMSSDK.getVerificationCode("86", phone);

							// 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
							validateBtn.setClickable(false);
							validateBtn.setText("重新发送(" + i + ")");
							new Thread(new Runnable() {
								@Override
								public void run() {
									for (; i > 0; i--) {
										handler.sendEmptyMessage(-9);
										if (i <= 0) {
											break;
										}
										try {
											Thread.sleep(1000);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
									handler.sendEmptyMessage(-8);
								}
							}).start();
						} else if (result.equals("success")) {

							Toast.makeText(RegisterActivity.this, "手机号已经注册",
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

	/**
	 * progressbar
	 */
	private void createProgressBar() {
		FrameLayout layout = (FrameLayout) findViewById(android.R.id.content);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER;
		ProgressBar mProBar = new ProgressBar(this);
		mProBar.setLayoutParams(layoutParams);
		mProBar.setVisibility(View.VISIBLE);
		layout.addView(mProBar);
	}

	@Override
	protected void onDestroy() {
		SMSSDK.unregisterAllEventHandler();
		super.onDestroy();
	}
}
