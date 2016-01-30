package com.lyy.activities;

import org.apache.http.Header;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

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
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;

public class ForgetCodeActivity extends Activity implements OnClickListener {
	EditText fcode_phone, fcode_code;
	ImageView fcode_back;
	Button validate, fcode_btn;
	String fphone = "", fcode = "";
	String IP = MyApplication.IP;
	String url = "http://" + IP + ":8080/MyProject/Link";
	boolean flag = false;
	int i = 30;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_forget_code);
		initView();
		initData();
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == -9) {
				validate.setText("重新发送(" + i + ")");
				validate.setClickable(false);
			} else if (msg.what == -8) {
				validate.setText("重新发送");
				validate.setClickable(true);
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

						Intent intent = new Intent(getApplicationContext(),
								UpdateCodeActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("phone", fphone);
						intent.putExtras(bundle);
						startActivity(intent);
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
		fcode_back = (ImageView) findViewById(R.id.fcode_back);
		fcode_phone = (EditText) findViewById(R.id.fcode_phone);
		fcode_code = (EditText) findViewById(R.id.fcode_yanzheng);
		validate = (Button) findViewById(R.id.yanzheng);
		fcode_btn = (Button) findViewById(R.id.fcode_btn);
		validate.setOnClickListener(this);
		fcode_btn.setOnClickListener(this);
		fcode_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		int id = view.getId();
		switch (id) {
		case R.id.yanzheng:
			fphone = fcode_phone.getText().toString().trim();
			if (!MyMethod.judgePhoneNums(fphone, getApplicationContext())) {
				return;
			}
			checkPhone();
			break;
		case R.id.fcode_btn:
			SMSSDK.submitVerificationCode("86", fphone, fcode_code.getText()
					.toString().trim());
			break;
		case R.id.fcode_back:
			ForgetCodeActivity.this.finish();
			break;
		default:
			break;
		}
	}

	private void checkPhone() {
		// TODO Auto-generated method stub
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("flag", 6);
		params.put("phone", fphone);
		client.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				final String result = new String(arg2);
				runOnUiThread(new Runnable() {
					public void run() {
						if (result.equals("success")) {
							Toast.makeText(ForgetCodeActivity.this, "此手机号未注册",
									Toast.LENGTH_SHORT).show();
						} else if (result.equals("failed")) {
							Toast.makeText(ForgetCodeActivity.this, "存在此手机号",
									Toast.LENGTH_SHORT).show();
							SMSSDK.getVerificationCode("86", fphone);

							// 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
							validate.setClickable(false);
							validate.setText("重新发送(" + i + ")");
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
	protected void onDestroy() {
		SMSSDK.unregisterAllEventHandler();
		super.onDestroy();
	}
}
