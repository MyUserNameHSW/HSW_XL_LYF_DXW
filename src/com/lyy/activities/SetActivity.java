package com.lyy.activities;

import com.lyy.project.R;
import com.lyy.project.R.layout;
import com.lyy.project.R.menu;
import com.lyy.util.MyApplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class SetActivity extends Activity implements OnClickListener {
    ImageView set_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_set);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		set_back = (ImageView) findViewById(R.id.set_back);
		set_back.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.set_back:
			SetActivity.this.finish();
			break;

		default:
			break;
		}
	}
}
