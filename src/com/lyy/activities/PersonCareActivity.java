package com.lyy.activities;

import java.lang.reflect.Type;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.bean.P_provide;
import com.lyy.project.R;
import com.lyy.util.MyApplication;

public class PersonCareActivity extends Activity implements OnClickListener {
	String url = "http://" + MyApplication.IP + ":8080/MyProject/Link";
	TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
	ImageView iv1, iv2, iv3, back;
	P_provide provide;
	Intent intent;
	int pp_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_person_care);
		initExtra();
		initView();
		initData();
	}

	private void initExtra() {
		// TODO Auto-generated method stub
		intent = getIntent();
		pp_id = intent.getIntExtra("pp_id", 0);
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				addData();
				break;
			case 2:
				break;
			default:
				break;
			}
		};
	};

	private void addData() {
		tv1.setText("代养人 ：  " + provide.getName());
		tv2.setText("代养经验 ： " + provide.getExprience());
		tv3.setText("TA的地址  ： " + provide.getAddress());
		tv4.setText("TA代养的类型：     " + provide.getPlant_type());
		tv5.setText("接收截止时间 ：   " + provide.getEndTime());
		tv6.setText("代养次数 ：   " + provide.getPp_num() + "");
		tv7.setText("关注次数 ：   " + provide.getFocus_num() + "");
		MyApplication.bitmapUtils.display(iv1, "http://" + MyApplication.IP
				+ ":8080/"+provide.getHead());
	}

	private void initData() {
		// TODO Auto-generated method stub
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "34");
		params.addBodyParameter("pageNum", "1");
		params.addBodyParameter("rec", "0");
		params.addBodyParameter("pp_id", pp_id + "");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				Log.e("result", result);
				Gson gson = new Gson();
				Type typeofType = new TypeToken<List<P_provide>>() {
				}.getType();
				List<P_provide> resultList = gson.fromJson(result, typeofType);
				provide = resultList.get(0);
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		});

	}

	private void initView() {
		// TODO Auto-generated method stub

		tv1 = (TextView) findViewById(R.id.pc_username);
		tv2 = (TextView) findViewById(R.id.pc_exprience);
		tv3 = (TextView) findViewById(R.id.pc_address);
		tv4 = (TextView) findViewById(R.id.pc_type);
		tv5 = (TextView) findViewById(R.id.pc_overtime);
		tv6 = (TextView) findViewById(R.id.pc_carenum);
		tv7 = (TextView) findViewById(R.id.pc_focusnum);
		iv1 = (ImageView) findViewById(R.id.pc_headimg);
		iv2 = (ImageView) findViewById(R.id.pc_call_phone);
		iv3 = (ImageView) findViewById(R.id.pc_call_message);
		back = (ImageView) findViewById(R.id.pcareyy_back);
		back.setOnClickListener(this);
		iv1.setOnClickListener(this);
		iv2.setOnClickListener(this);
		iv3.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (arg0.getId()) {
		case R.id.pc_headimg:
			intent = new Intent(getApplicationContext(), FriendsActivity.class);
			intent.putExtra("fu_id", provide.getU_id());
			break;
		case R.id.pc_call_phone:
			intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ "15737954793"));
			break;
		case R.id.pc_call_message:
			Uri uri = Uri.parse("smsto://15737954793");
			intent = new Intent(Intent.ACTION_SENDTO, uri);
			break;
		case R.id.pcareyy_back:
			this.finish();
			return;
		default:
			break;
		}
		this.startActivity(intent);
	}

}
