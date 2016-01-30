package com.lyy.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyy.project.R;
import com.lyy.util.MyApplication;

public class ProductActivity extends Activity implements OnClickListener {
    TextView textView;
    ImageView product_back;
    int p_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_product);
		initView();
		Bundle bundle = new Bundle();
		Intent intent = this.getIntent();
		bundle = intent.getExtras();
		p_id = bundle.getInt("p_id");
		textView.setText("商品id---->"+p_id);
	}
	private void initView() {
		// TODO Auto-generated method stub
		textView = (TextView) findViewById(R.id.test_text);
		product_back = (ImageView) findViewById(R.id.product_back);
		product_back.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.product_back:
			ProductActivity.this.finish();
			break;

		default:
			break;
		}
	}

}
