package com.lyy.activities;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.project.R;
import com.lyy.project.R.layout;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UpdateNameActivity extends Activity implements OnClickListener {
	EditText username;
    ImageView uname;
    Button upUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_update_name);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		username = (EditText) findViewById(R.id.new_username);
		uname = (ImageView) findViewById(R.id.uname_back);
		upUser = (Button) findViewById(R.id.up_usernames);
		uname.setOnClickListener(this);
		upUser.setOnClickListener(this);
	}

	public void editUsername() {
		String url = "http://"+MyApplication.IP+":8080/MyProject/Link";
		int u_id = MyMethod.GetU_id(getApplicationContext());
		String newUsername = username.getText().toString().trim();
		if (newUsername == null || newUsername.equals("")) {
			MyMethod.showToast(getApplicationContext(), "用户名不能为空");
			return;
		}
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "19");
		params.addBodyParameter("u_id", u_id+"");
		params.addBodyParameter("username", newUsername);
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String reString = arg0.result;
				if (reString.equals("success")) {
					MyMethod.showToast(getApplicationContext(), "修改成功");
					UpdateNameActivity.this.finish();
				}
			}
		});
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.uname_back:
			UpdateNameActivity.this.finish();
			break;
		case R.id.up_usernames:
			Log.e("今天--------", "9999999999999999");
			editUsername();
			break;
		default:
			break;
		}
	}
}
