package com.lyy.activities;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.layout.SendMsgDialogLayout;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

public class EditPProvideActivity extends Activity {
	EditText endTime, address, types, phone, discipt, eepp;
	String endTimeInput = "", addressInput = "", typesInput = "",
			phoneInput = "", disciptInput = "", eeppInput = "";
	Button script, inview;
	String url = "http://" + MyApplication.IP + ":8080/MyProject/Link";
	boolean flag = false;
	int i = 30;
	private Spinner provinceSpinner = null; // 省级（省、直辖市）
	private Spinner citySpinner = null; // 地级市
	private Spinner countySpinner = null; // 县级（区、县、县级市）
	ArrayAdapter<String> provinceAdapter = null; // 省级适配器
	ArrayAdapter<String> cityAdapter = null; // 地级适配器
	ArrayAdapter<String> countyAdapter = null; // 县级适配器
	static int provincePosition = 1;
	private String[] province = new String[] { "江苏", "北京", "上海" };
	private String[][] city = new String[][] {
			{ "苏州", "南京", "无锡" },
			{ "东城区", "西城区", "崇文区", "宣武区", "朝阳区", "海淀区", "丰台区", "石景山区", "门头沟区",
					"房山区", "通州区", "顺义区", "大兴区", "昌平区", "平谷区", "怀柔区", "密云县",
					"延庆县" }, { "长宁区", "静安区", "普陀区", "闸北区", "虹口区" } };
	private String[][][] county = new String[][][] { {// 江苏
			{// 苏州
					"姑苏区", "相城区", "吴中区", "虎丘区", "吴江区" }, {// 南京
					"玄武区", "秦淮区", "鼓楼区", "建邺区", "雨花台区", "浦口区", "六合区", "栖霞区",
							"江宁区", "溧水区", "高淳区" }, {// 无锡
					"崇安区", "南长区", "北塘区", "锡山区", "惠山区", "滨湖区" } }, {// 北京市
			{ "无" }, { "无" }, { "无" }, { "无" }, { "无" }, { "无" }, { "无" },
					{ "无" }, { "无" }, { "无" }, { "无" }, { "无" }, { "无" },
					{ "无" }, { "无" }, { "无" }, { "无" }, { "无" } }, {// 上海市
			{ "无" }, { "无" }, { "无" }, { "无" }, { "无" } } };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication) this.getApplication();
		appState.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit_pprovide);
		setSpinner();
		initSDKdata();
		initView();
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == -9) {
				inview.setText("重新发送(" + i + ")");
				inview.setClickable(false);
			} else if (msg.what == -8) {
				inview.setText("重新发送");
				inview.setClickable(true);
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
						submitEdit();
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

	public void submits(View view) {
		switch (view.getId()) {
		case R.id.epp_submit:
			checkEdit();
			SMSSDK.submitVerificationCode("86", phoneInput, eepp.getText()
					.toString().trim());
			break;

		default:
			break;
		}
	}

	private void initSDKdata() {
		// TODO Auto-generated method stub
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
		endTime = (EditText) findViewById(R.id.limitedDate);
		address = (EditText) findViewById(R.id.epp_address);
		types = (EditText) findViewById(R.id.epp_type);
		eepp = (EditText) findViewById(R.id.eepp_yanzheng);
		types.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(EditPProvideActivity.this,
						CareTypeActivity.class);
				startActivity(intent);
			}
		});
		phone = (EditText) findViewById(R.id.epp_phone);
		inview = (Button) findViewById(R.id.epp_yanzheng);
		inview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				phoneInput = phone.getText().toString().trim();
				if (!MyMethod.judgePhoneNums(phoneInput,
						getApplicationContext())) {
					return;
				}
				SendMsg();
			}
		});
		discipt = (EditText) findViewById(R.id.add_content);
		script = (Button) findViewById(R.id.epp_submit);
	}

	private void SendMsg() {
		SMSSDK.getVerificationCode("86", phoneInput);

		// 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
		inview.setClickable(false);
		inview.setText("重新发送(" + i + ")");
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

	private void checkEdit() {
		// TODO Auto-generated method stub
		eeppInput = eepp.getText().toString().trim();
		endTimeInput = endTime.getText().toString().trim();
		addressInput = address.getText().toString().trim();
		typesInput = types.getText().toString().trim();
		phoneInput = phone.getText().toString().trim();
		disciptInput = discipt.getText().toString().trim();
		Log.e("----------", eeppInput + "555");
		MyMethod.showToast(getApplicationContext(), typesInput);
		if (endTimeInput.equals("")) {
			MyMethod.showToast(getApplicationContext(), "日期为空");
			return;
		} else if (addressInput.equals("")) {
			MyMethod.showToast(getApplicationContext(), "地址为空");
			return;
		} else if (typesInput.equals("")) {
			MyMethod.showToast(getApplicationContext(), "类型为空");
			return;
		} else if (phoneInput.equals("")) {
			MyMethod.showToast(getApplicationContext(), "电话为空");
			return;
		} else if (eeppInput.equals("")) {
			MyMethod.showToast(getApplicationContext(), "描述为空");
			return;
		} else if (disciptInput.equals("")) {
			MyMethod.showToast(getApplicationContext(), "描述为空");
			return;
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		int ps2_id = intent.getIntExtra("ps2_id", 0);
		Log.e("activity", ps2_id + "onResume");
		if (ps2_id != 0) {
			types.setText(ps2_id + "");
		}
		super.onResume();
	}

	private void setSpinner() {
		provinceSpinner = (Spinner) findViewById(R.id.province);
		citySpinner = (Spinner) findViewById(R.id.cities);
		countySpinner = (Spinner) findViewById(R.id.county);

		// 绑定适配器和值
		provinceAdapter = new ArrayAdapter<String>(EditPProvideActivity.this,
				android.R.layout.simple_spinner_item, province);
		provinceSpinner.setAdapter(provinceAdapter);
		provinceSpinner.setSelection(2, true); // 设置默认选中项，此处为默认选中第4个值

		cityAdapter = new ArrayAdapter<String>(EditPProvideActivity.this,
				android.R.layout.simple_spinner_item, city[2]);
		citySpinner.setAdapter(cityAdapter);
		citySpinner.setSelection(0, true); // 默认选中第0个

		countyAdapter = new ArrayAdapter<String>(EditPProvideActivity.this,
				android.R.layout.simple_spinner_item, county[2][0]);
		countySpinner.setAdapter(countyAdapter);
		countySpinner.setSelection(0, true);

		// 省级下拉框监听
		provinceSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					// 表示选项被改变的时候触发此方法，主要实现办法：动态改变地级适配器的绑定值
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// position为当前省级选中的值的序号

						// 将地级适配器的值改变为city[position]中的值
						cityAdapter = new ArrayAdapter<String>(
								EditPProvideActivity.this,
								android.R.layout.simple_spinner_item,
								city[position]);
						// 设置二级下拉列表的选项内容适配器
						citySpinner.setAdapter(cityAdapter);
						provincePosition = position; // 记录当前省级序号，留给下面修改县级适配器时用
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}

				});

		// 地级下拉监听
		citySpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						countyAdapter = new ArrayAdapter<String>(
								EditPProvideActivity.this,
								android.R.layout.simple_spinner_item,
								county[provincePosition][position]);
						countySpinner.setAdapter(countyAdapter);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
	}

	private void submitEdit() {
		int u_id = MyMethod.GetU_id(getApplicationContext());
		MyMethod.showToast(getApplicationContext(), "成功");
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "36");
		params.addBodyParameter("time", endTimeInput);
		params.addBodyParameter("phone", phoneInput);
		params.addBodyParameter("address", citySpinner.getSelectedItem()
				.toString() + addressInput);
		params.addBodyParameter("type", typesInput + "");
		Log.e("my_type", typesInput);
		MyMethod.showToast(getApplicationContext(), "类型"+typesInput);
		params.addBodyParameter("discipt", disciptInput);
		params.addBodyParameter("u_id", u_id + "");
		params.addBodyParameter("state", "1");
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
					MyMethod.showToast(getApplicationContext(), "提交成功");
				}
			}
		});
	}
}
