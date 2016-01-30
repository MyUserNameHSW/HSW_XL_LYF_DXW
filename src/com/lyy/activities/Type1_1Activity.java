package com.lyy.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lyy.adapter.Type1_1_Adapt;
import com.lyy.bean.Product;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;
import com.lyy.xl.activities.DetailActivity;

@SuppressLint("ResourceAsColor") 
public class Type1_1Activity extends Activity implements OnItemClickListener,
		OnClickListener {

	ListView listView;
	TextView tView1;
	ImageView type_back;
	Type1_1_Adapt myAdapt;
	Button price, saleNum; 
	List<Product> list = new ArrayList<Product>();
	String IP = MyApplication.IP;
	int flag;
	String types, search_text;
	int ps2_id;
	private RequestParams params;
	String url = "http://" + IP + ":8080/MyProject/Link";
	boolean flag1, flag2;
	static final int SALENUM_ASC = 1; // 按销量从小到大
	static final int SALENUM_DESC = -1; // 按销量从大到小
	static final int PRICE_ASC = 2; // 按价格从小到大
	static final int PRICE_DESC = -2; // 按价格从大到小
	private int sortWay = SALENUM_DESC;
    boolean colorFlag = true;
	private View view; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication) this.getApplication();
		appState.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_type_1);
//		LayoutInflater mInflater = LayoutInflater.from(getApplicationContext());
//		view = mInflater.inflate(R.layout.loading_anim, null);
		initData();
		initView();
		downLoadData(sortWay);
	}

	private void initData() {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		Intent intent = this.getIntent();
		bundle = intent.getExtras();
		flag = bundle.getInt("params");
		types = bundle.getString("types");
		ps2_id = intent.getIntExtra("ps2_id", 0);
		search_text = intent.getStringExtra("search_text");
	}

	private void downLoadData(int sortWay) {
		// TODO Auto-generated method stub
		view.setVisibility(View.VISIBLE);
		listView.setVisibility(View.GONE);
//		final ProgressDialog dialog = new ProgressDialog(this);
//		dialog.setTitle("正在加载");
//		dialog.setMessage("请稍后...");
		//dialog.setContentView(view);
		//dialog.setContentView(R.layout.loading_anim);
//		dialog.show();
//		Dialog dialog = new Dialog(getApplicationContext());
//		dialog.setContentView(view, new LinearLayout.LayoutParams(
//				LinearLayout.LayoutParams.WRAP_CONTENT,
//				LinearLayout.LayoutParams.WRAP_CONTENT));
		//dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
//		dialog.show();
		list = new ArrayList<Product>();
		if (types != null && !types.equals("")) {
			params = new RequestParams();
			params.put("flag", flag);
			params.put("types", types);
			// params.put("list", value);
		} else if (ps2_id != 0) {
			params = new RequestParams();
			params.put("flag", 16);
			params.put("ps2_id", ps2_id);
		} else{
			params = new RequestParams();
			params.put("flag", 17);
			params.put("search_text", search_text);
//			MyMethod.showToast(getApplicationContext(), "无查询结果");
		}
		// if (search_text != null && !search_text.equals("")) 
		params.put("sort", sortWay);
		params.put("page", 1);
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				for (int i = 0; i < response.length(); i++) {
					JSONObject object;
					try {
						object = response.getJSONObject(i);
						int p_id = object.getInt("p_id");
						String name = object.getString("name");
						String img = "http://" + IP + ":8080"
								+ object.getString("img");
						int price = object.getInt("price");
						int buy_num = object.getInt("buy_num");
						Product product = new Product(p_id, name, img,price,buy_num);
						list.add(product);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					myAdapt = new Type1_1_Adapt(Type1_1Activity.this, list);
					myAdapt.notifyDataSetChanged();
					listView.setAdapter(myAdapt);
//					dialog.dismiss();
					view.setVisibility(View.GONE);
					listView.setVisibility(View.VISIBLE);
				}
			/*	runOnUiThread(new Runnable() {
					public void run() {
						
					}
				});*/
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);
			}
		});
	}

	@SuppressLint("ResourceAsColor") 
	private void initView() {
		// TODO Auto-generated method stub
        view = findViewById(R.id.anim1); 
		type_back = (ImageView) findViewById(R.id.type1_back);
		tView1 = (TextView) findViewById(R.id.topBar1_text);
		listView = (ListView) findViewById(R.id.type1_1_listview);
		saleNum = (Button) findViewById(R.id.saleNum_sort);
		price = (Button) findViewById(R.id.price_sort);
		tView1.setText(types);
		type_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Type1_1Activity.this.finish();
			}
		});
		listView.setOnItemClickListener(this);
		saleNum.setOnClickListener(this);
		price.setOnClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		Intent intent = new Intent(Type1_1Activity.this, DetailActivity.class);
		int p_id = list.get(arg2).getP_id();
		bundle.putInt("p_id", p_id);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public void back(View v) {
		Intent intent = new Intent(Type1_1Activity.this, MainActivity.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.saleNum_sort:
//			price.setBackgroundColor(R.color.white);
//			saleNum.setTextColor(R.color.gray);
//			saleNum.setBackgroundColor(color);
			if (flag1) {
				saleNum.setText("销量最高");
//				saleNum.setTextColor(R.color.themeColor);
//				price.setTextColor(R.color.black);
				sortWay = SALENUM_DESC;
			} else {
				saleNum.setText("销量最低");
//				saleNum.setTextColor(R.color.themeColor);
//				price.setTextColor(R.color.black);
				sortWay = SALENUM_ASC;
			}
            flag1 = !flag1;
			break;
		case R.id.price_sort:
//			price.setBackgroundColor(R.color.gray);
//			saleNum.setTextColor(R.color.white);
			if (flag2) {
				price.setText("价格最低");

				sortWay = PRICE_ASC;
			} else {
				price.setText("价格最高");
				// price.setTextColor(R.color.themeColor);
				// saleNum.setTextColor(R.color.black);
				sortWay = PRICE_DESC;
			}
			flag2 = !flag2;
			break;
		default:
			break;
		}
		downLoadData(sortWay);
	}
}
