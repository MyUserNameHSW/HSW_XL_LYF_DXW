package com.lyy.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.adapter.CartPay_Adapter;
import com.lyy.bean.CartBean;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;
import com.lyy.xl.activities.ShouhuoAddressActivity;

public class CartPayActivity extends Activity implements OnClickListener {
	ImageView back, cxx;
	TextView cshouhuo_msg, creceiver_name, creceiver_phone, creceiver_address;
	LinearLayout lin1;
	private String resultName = "";
	private String resultAddress;
	private String resultPhone;
	private int r_id;
	private Button sureCheckOut;
	private TextView checkOutAllPrice;
	ListView listView;
	List<CartBean> list;
	List<String> list2;
	double allPrice;
	CartPay_Adapter adapter;
	HttpUtils utils;
	RequestParams params;
	String url = "http://" + MyApplication.IP + ":8080/MyProject/storeSevlet";
	String url2 = "http://" + MyApplication.IP + ":8080/MyProject/Link";
	int u_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_cart_pay);
		u_id = MyMethod.GetU_id(getApplicationContext());
		initView();
		init();
	}

	private void init() {
		list2 = new ArrayList<String>();
		list = new ArrayList<CartBean>();
		String shopIndex = getIntent().getStringExtra("shopIndex");
		String[] shopIndexs = shopIndex.split(",");
		for (String s : shopIndexs) {
			int position = Integer.valueOf(s);
			CartBean bean = MyApplication.list.get(position);
			allPrice += bean.getAccount() * bean.getPrice();
			list.add(bean);
		}
		checkOutAllPrice.setText("总共有" + list.size() + "个商品       总价￥"
				+ allPrice);
		adapter = new CartPay_Adapter(list, getApplicationContext());
		listView.setAdapter(adapter);
	}

	private void initView() {
		Log.e("list", MyApplication.list.get(0).toString());
		back = (ImageView) findViewById(R.id.cimage_back2);
		cxx = (ImageView) findViewById(R.id.cxx);
		listView = (ListView) findViewById(R.id.clistview);
		cshouhuo_msg = (TextView) findViewById(R.id.cshouhuo_msg);
		creceiver_name = (TextView) findViewById(R.id.creceiver_name);
		creceiver_phone = (TextView) findViewById(R.id.creceiver_phone);
		creceiver_address = (TextView) findViewById(R.id.creceiver_address);
		lin1 = (LinearLayout) findViewById(R.id.creceiver_msg);
		sureCheckOut = (Button) findViewById(R.id.sureCheckOut);
		checkOutAllPrice = (TextView) findViewById(R.id.checkOutAllPrice);
		back.setOnClickListener(this);
		cxx.setOnClickListener(this);
		sureCheckOut.setOnClickListener(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 111:
				UpdateUI(data);
				break;

			default:
				break;
			}
		}
	}

	private void UpdateUI(Intent data) {
		resultName = data.getStringExtra("name");
		resultAddress = data.getStringExtra("address");
		resultPhone = data.getStringExtra("phone");
		r_id = data.getIntExtra("r_id", 0);
		// Toast.makeText(this, "" + resultName, 0).show();
		creceiver_name.setText(resultName);
		creceiver_address.setText(resultAddress);
		creceiver_phone.setText(resultPhone);
		if (!resultName.equals("")) {
			cshouhuo_msg.setVisibility(View.GONE);
			lin1.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.cimage_back2:
			finish();
			break;
		case R.id.cxx:
			Intent intent2 = new Intent(getApplicationContext(),
					ShouhuoAddressActivity.class);
			startActivityForResult(intent2, 111);
			break;
		case R.id.sureCheckOut:
			if (resultName.equals("")) {
				MyMethod.showToast(getApplicationContext(), "请选择收货地址");
				return;
			}
			insertNoPay();
			deleteCarts();
			showDialogs();
			break;
		default:
			break;
		}
	}

	private void showDialogs() {
		final EditText inputServer = new EditText(CartPayActivity.this);
		inputServer.setBackgroundColor(R.drawable.two_radius);
		AlertDialog.Builder builder = new AlertDialog.Builder(
				CartPayActivity.this);
		builder.setTitle("输入支付密码").setIcon(android.R.drawable.ic_dialog_info)
				.setView(inputServer).setNegativeButton("取消", null);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				ChangeState();
			}
		});
		builder.show();
	}

	private void deleteCarts() {
		for (int i = 0; i < list.size(); i++) {
			utils = new HttpUtils();
			params = new RequestParams();
			params.addBodyParameter("flag", "53");
			params.addBodyParameter("c_id", list.get(i).getC_id() + "");
			utils.send(HttpMethod.POST, url2, params,
					new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
							// TODO Auto-generated method stub
							Log.e("--", "删除加入订单后的购物车数据成功");
						}
					});
		}
	}

	private void ChangeState() {
		for (int i = 0; i < list.size(); i++) {
			utils = new HttpUtils();
			params = new RequestParams();
			params.addBodyParameter("flag", 47 + "");
			params.addBodyParameter("state", "2");
			params.addBodyParameter("ordernumber", list2.get(i));
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

	private void insertNoPay() {

		for (int i = 0; i < list.size(); i++) {
			utils = new HttpUtils();
			params = new RequestParams();
			params.addBodyParameter("flag", "7");
			params.addBodyParameter("p_id", list.get(i).getP_id() + "");
			params.addBodyParameter("u_id", u_id + "");
			params.addBodyParameter("state", "1");
			params.addBodyParameter("r_id", r_id + "");
			params.addBodyParameter("price", list.get(i).getPrice() + "");
			params.addBodyParameter("account", list.get(i).getAccount() + "");
			params.addBodyParameter("submittime", getNowTime());
			int sjnumber = (int) (Math.random() * 9000 + 1000);
			list2.add(getNowTime() + sjnumber);
			params.addBodyParameter("ordernumber", getNowTime() + sjnumber);
			utils.send(HttpMethod.POST, url, params,
					new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
							// TODO Auto-generated method stub
							Log.e("--", "加入未完成订单成功");
						}
					});
		}
	}

	public static String getNowTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
		String NOWTIME = sdf.format(new Date());
		return NOWTIME;
	}
}
