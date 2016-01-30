package com.lyy.know.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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

public class KnowRenZhiProblemContentActivity extends Activity implements
		OnClickListener {
	TextView nameTextView, contentTextView;
	ImageView imageView;
	int E_id;
	ImageButton imageButton;
	int id, m, flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_know_ren_zhi_content);
		initViews();
		initDatas();
	}

	private void initViews() {
		imageButton = (ImageButton) findViewById(R.id.imageB);
		nameTextView = (TextView) findViewById(R.id.bk_renzhi_content_name_tv);
		imageView = (ImageView) findViewById(R.id.bk_renzhi_image);
		contentTextView = (TextView) findViewById(R.id.bk_renzhi_content_detail);
		imageButton.setOnClickListener(this);
	}

	private void initDatas() {
		Intent intent = getIntent();
		E_id = intent.getIntExtra("E_id", 0);
		String name = intent.getStringExtra("name");
		String image = intent.getStringExtra("image");
		String artical = intent.getStringExtra("artical");
		nameTextView.setText(name);
		String url = "http://" + MyApplication.IP+ ":8080" + image;
		MyApplication.bitmapUtils.display(imageView, url);
		contentTextView.setText(artical);
		getCollecttionFromServer();
	}

	private void getCollecttionFromServer() {
		HttpUtils httpUtils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("E_id", E_id+"");
		params.addBodyParameter("U_id", "1");
		params.addBodyParameter("key", "7");

		String url = "http://" + MyApplication.IP
				+ ":8080/MyProject/BaikeServlet";
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						m = Integer.parseInt(responseInfo.result);
						Log.e("点击成功", "success");
						if (m == 2) {
							imageButton.setImageResource(R.drawable.bc2);
							flag = 1;// 表示已经收藏
						}
						if (m == -2) {
							imageButton.setImageResource(R.drawable.bc1);
							flag = -1;// 表示没收藏
						}
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageB:
			Log.e("点击成功", "success");
			if (flag == 1) {
				flag = -1;
			} else {
				flag = 1;
			}
			getCollection(flag);
			break;

		default:
			break;
		}

	}

	private void getCollection(int flag2) {
		HttpUtils httpUtils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("E_id", E_id+"");
		params.addBodyParameter("U_id", MyMethod.GetU_id(getApplicationContext())+"");
		params.addBodyParameter("flag", "" + flag2);
		params.addBodyParameter("key", "6");
		String url = "http://" + MyApplication.IP
				+ ":8080/MyProject/BaikeServlet";
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = responseInfo.result;
						if (result.equals("true")) {
							imageButton.setImageResource(R.drawable.bc2);
							Toast.makeText(
									KnowRenZhiProblemContentActivity.this,
									"收藏成功", Toast.LENGTH_SHORT).show();
						} else {
							imageButton.setImageResource(R.drawable.bc1);
							Toast.makeText(
									KnowRenZhiProblemContentActivity.this,
									"取消收藏", Toast.LENGTH_SHORT).show();
						}
					}
				});
	}
}
