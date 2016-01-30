package com.lyy.forums.activities;

//帖子分享activity

import java.util.ArrayList;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.activities.MainActivity;
import com.lyy.forums.adapter.Forums_GridViewAdapter;
import com.lyy.forums.util.DateUtil;
import com.lyy.project.R;
import com.lyy.util.MyApplication;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.text.TextPaint;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("CutPasteId")
public class ShareActivity extends Activity implements OnClickListener {
	TextView zfcanceltTextView, zfarticlTextView, zfthemeTextView;
	Button sendButton;
	EditText pointEditText;
	ImageView zfimageImageView;
	String username, articl, theme, f_time, path;
	ArrayList<String> list = new ArrayList<String>();
	HttpUtils httpUtils;
	GridView gridView;
	Forums_GridViewAdapter gridViewAdapter;
	private int width;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forum_share);
		// 获取转发内容
		Intent intent2 = getIntent();
		username = intent2.getStringExtra("username");
		articl = intent2.getStringExtra("articl");
		theme = intent2.getStringExtra("theme");
		list = intent2.getStringArrayListExtra("listpicture");
		// 获取手机分辨率，这类写法只能放在activity中，其他地方搜索另外的方法
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		initviews();
	}

	private void initviews() {
		gridView = (GridView) findViewById(R.id.sharegridView);
		gridViewAdapter = new Forums_GridViewAdapter(ShareActivity.this,
				gridView, width, list);
		gridView.setAdapter(gridViewAdapter);
		zfcanceltTextView = (TextView) findViewById(R.id.zfcancel);
		zfthemeTextView = (TextView) findViewById(R.id.zftheme);
		// 设置字体为粗体
		TextPaint tp = zfthemeTextView.getPaint();
		tp.setFakeBoldText(true);
		zfarticlTextView = (TextView) findViewById(R.id.zfarticl);
		pointEditText = (EditText) findViewById(R.id.newpoint);
		sendButton = (Button) findViewById(R.id.zfsend);

		// 设置单击事件
		zfcanceltTextView.setOnClickListener(this);
		sendButton.setOnClickListener(this);

		// 设置值
		Intent intent = getIntent();
		zfthemeTextView.setText(intent.getStringExtra("theme"));
		zfarticlTextView.setText(intent.getStringExtra("articl"));

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.zfcancel:
			finish();
			break;
		case R.id.zfsend:
			share();
			finish();
			break;

		default:
			break;
		}

	}

	private void share() {
		// 获取当前转发时间
		DateUtil dateUtil = new DateUtil();
		String f_time = dateUtil.getTime();
		httpUtils = new HttpUtils();
		// 当前帖子木有图片
		String url1 = "http://" + MyApplication.IP
				+ ":8080/MyProject/Check?action=17";
		// 当前帖子有图片
		String url2 = "http://" + MyApplication.IP
				+ ":8080/MyProject/Check?action=18";
		// 获取转发观点
		String point = pointEditText.getEditableText().toString().trim();
		Log.e("point", point);

		RequestParams params = new RequestParams();
		// 当前用户id
		int U_id = 1;
		params.addBodyParameter("u_id", "" + U_id);
		params.addBodyParameter("f_time", f_time);
		params.addBodyParameter("articl", articl);
		params.addBodyParameter("username", username);
		params.addBodyParameter("theme", theme);
		params.addBodyParameter("point", point);
		for (int i = 0; i < list.size(); i++) {
			params.addBodyParameter("" + i, list.get(i));
		}
		if (list.size() == 0) {
			httpUtils.send(HttpMethod.POST, url1, params,
					new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							Toast.makeText(ShareActivity.this, "网络连接超时......",
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
							// 帖子发表成功，跳到帖子列表并刷新
							Toast.makeText(ShareActivity.this, "转发成功",
									Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(ShareActivity.this,
									MainActivity.class);

							startActivity(intent);

						}
					});
		} else {
			httpUtils.send(HttpMethod.POST, url2, params,
					new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							Toast.makeText(ShareActivity.this, "网络连接超时......",
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
							// 帖子发表成功，跳到帖子列表并刷新
							Toast.makeText(ShareActivity.this, "转发成功",
									Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(ShareActivity.this,
									MainActivity.class);

							startActivity(intent);

						}
					});
		}

	}

}
