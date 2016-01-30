package com.lyy.xl.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
import com.lyy.bean.MyFocus;
import com.lyy.bean.MyReceive;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;
import com.lyy.xl.bean.BasicBean;
import com.lyy.xl.bean.ReceiveBean;
import com.lyy.xl.utils.BasicApplication;

public class OrderDetailActivity extends Activity implements OnClickListener {
	String url = "http://" + MyApplication.IP + ":8080/MyProject/storeSevlet";
	String url2 = MyMethod.url;
	ReceiveBean receiveBean;
	BasicBean basicBeans;
	MyReceive receive;
	TextView receiver_name, receiver_phone, receiver_address;
	TextView order_number, goods_name_order, goods_number_order, goods_price,
			postage_goods, goods_num_order;
	ImageView order_back;
	Button order_detail_ok;
	List<ReceiveBean> list;
	List<MyReceive> list2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_order_detail);
		initDatas();
		initMyAdd();

	}

	private void initMyAdd() {
		// TODO Auto-generated method stub
		r_id = getIntent().getIntExtra("r_id", 0);
		Log.e("r_id", r_id + "");
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "45");
		params.addBodyParameter("r_id", r_id + "");
		utils.send(HttpMethod.POST, url2, params,
				new RequestCallBack<String>() {

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
						Type typeOfT = new TypeToken<List<MyReceive>>() {
						}.getType();
						List<MyReceive> resList = gson
								.fromJson(result, typeOfT);
						// list2.addAll(resList);
						receive = resList.get(0);
						Log.e("receiveaa", receive.toString());
						initView();
					}
				});

	}

	private String goods_number;
	private double good_price2;
	private String sjnumber1;
	private int r_id;

	private void initDatas() {
		// TODO Auto-generated method stub
		list2 = new ArrayList<MyReceive>();
		order_number = (TextView) findViewById(R.id.order_number);
		sjnumber1 = getIntent().getStringExtra("order_number1");
		order_number.setText(sjnumber1);
		goods_name_order = (TextView) findViewById(R.id.goods_name_order);
		String goods_name = getIntent().getStringExtra("goods_name1");
		goods_name_order.setText(goods_name);
		goods_number_order = (TextView) findViewById(R.id.goods_number_order);
		goods_number = getIntent().getStringExtra("goods_number1");
		goods_number_order.setText(goods_number);
		goods_price = (TextView) findViewById(R.id.goods_prices);
		good_price2 = getIntent().getDoubleExtra("goods_price1", 0);
		Log.e("good_price2", good_price2 + "");
		goods_price.setText(good_price2 + "  元");
		postage_goods = (TextView) findViewById(R.id.postage_order);
		double postage = getIntent().getDoubleExtra("postage1", 0);
		postage_goods.setText(postage + " 元");
		goods_num_order = (TextView) findViewById(R.id.goods_num_order);
		double goods_num = getIntent().getDoubleExtra("account_goods_num", 0);
		goods_num_order.setText(goods_num + " 元");
	}

	private void initView() {
		// TODO Auto-generated method stub

		receiver_name = (TextView) findViewById(R.id.receiver_name1);
		String receive_name = getIntent().getStringExtra("name");
		receiver_name.setText("收货人     "+receive.getName());
		receiver_phone = (TextView) findViewById(R.id.receiver_phone1);
		String receive_phone = getIntent().getStringExtra("phone");
		receiver_phone.setText("手机号      "+receive.getPhone());
		receiver_address = (TextView) findViewById(R.id.receiver_address1);
		String receive_address = getIntent().getStringExtra("address");
		receiver_address.setText("收货地址        "+receive.getAddress());
		order_back = (ImageView) findViewById(R.id.order_back);
		order_detail_ok = (Button) findViewById(R.id.order_detail_ok);
		order_back.setOnClickListener(this);
		order_detail_ok.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.order_back:
			finish();
			break;
		case R.id.order_detail_ok:
			final EditText inputServer = new EditText(this);
			inputServer.setBackgroundColor(R.drawable.two_radius);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("输入支付密码")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setView(inputServer).setNegativeButton("取消", null);
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(
									OrderDetailActivity.this, PayActivity.class);
							changePayState();
							startActivity(intent);
						}
					});
			builder.show();

			break;
		default:
			break;
		}
	}

	private void changePayState() {
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("ordernumber", sjnumber1);
		params.addBodyParameter("state", 2 + "");
		params.addBodyParameter("flag", "47");
		utils.send(HttpMethod.POST, url2, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						MyMethod.showToast(getApplicationContext(), "付款成功");
					}
				});
	}
}
