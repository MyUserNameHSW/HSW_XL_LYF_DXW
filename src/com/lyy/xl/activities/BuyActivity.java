package com.lyy.xl.activities;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;
import com.lyy.xl.bean.BasicBean;
import com.lyy.xl.bean.ReceiveBean;
import com.lyy.xl.bean.ReceiveMsgBean;
import com.lyy.xl.utils.BasicApplication;

public class BuyActivity extends Activity implements OnClickListener {
	String url = "http://" + MyApplication.IP
			+ ":8080/MyProject/storeSevlet";
	public static String NOWTIME;
	String order_bianhao;
	String resultName ="";
	String resultAddress;
	String resultPhone;
	int r_id;
	BasicBean basicBeans;
	ReceiveBean receiveBeans;
	TextView shouhuo_msg;
	ReceiveMsgBean receiveMsgBeans;
	LinearLayout receive_message, receive_msg;
	TextView goods_buy_ok, goods_name, goods_depict, goods_price, pop_reduce1,
			pop_num1, pop_add1, postage_buy, num_goods, account_goods,
			account_goods1, name, address, phone;
	ImageView goods_list_pic, image_back2;
	Intent intent;
	ImageView xx;
	int p_id;
	private final int ADDORREDUCE = 1;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				addData();
				break;
			default:
				break;
			}
		}

	};
	private double price ;
	private String popnum1;
	private String time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_buy_immediately);

		initViews();
		initData();

	}

	private void addData() {
		// TODO Auto-generated method stub
		MyApplication.bitmapUtils.display(goods_list_pic, "http://"
				+ MyApplication.IP + ":8080" + basicBeans.getImg());
		goods_name.setText(basicBeans.getName());
		goods_depict.setText(basicBeans.getDepict());
		goods_price.setText(basicBeans.getPrice() + "元");
		account_goods.setText(basicBeans.getPrice() + "");
		postage_buy.setText(basicBeans.getPostage() + "  元");
		account_goods1.setText(basicBeans.getPrice() + basicBeans.getPostage()
				+ "");
		num_goods.setText(1 + "");
	}

	private void initViews() {
		// TODO Auto-generated method stub
		intent = getIntent();
		p_id = intent.getIntExtra("p_id", 0);
		goods_buy_ok = (TextView) findViewById(R.id.goods_buy_ok);
		pop_reduce1 = (TextView) findViewById(R.id.pop_reduce1);
		pop_add1 = (TextView) findViewById(R.id.pop_add1);
		goods_list_pic = (ImageView) findViewById(R.id.goods_list_pic);
		image_back2 = (ImageView) findViewById(R.id.image_back2);
		goods_name = (TextView) findViewById(R.id.goods_name);
		goods_depict = (TextView) findViewById(R.id.goods_depict);
		pop_num1 = (TextView) findViewById(R.id.pop_num1);
		postage_buy = (TextView) findViewById(R.id.postage_buy);
		num_goods = (TextView) findViewById(R.id.num_goods);
		account_goods = (TextView) findViewById(R.id.account_goods);
		account_goods1 = (TextView) findViewById(R.id.account_goods1);
		goods_price = (TextView) findViewById(R.id.goods_price);
		shouhuo_msg = (TextView) findViewById(R.id.shouhuo_msg);
		receive_msg = (LinearLayout) findViewById(R.id.receiver_msg);

		name = (TextView) findViewById(R.id.receiver_name);
		phone = (TextView) findViewById(R.id.receiver_phone);
		address = (TextView) findViewById(R.id.receiver_address);
		goods_buy_ok.setOnClickListener(this);
		pop_reduce1.setOnClickListener(this);
		pop_add1.setOnClickListener(this);
		image_back2.setOnClickListener(this);
		xx = (ImageView) findViewById(R.id.xx);
		xx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent(BuyActivity.this,
						ShouhuoAddressActivity.class);
				startActivityForResult(intent2, 111);

			}
		});
	}

	@Override
	public void onClick(View v) {
		String num_reduce = null;
		String num_add = null;
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.goods_buy_ok:
			if (resultName == null || resultName.equals("")) {
				MyMethod.showToast(getApplicationContext(), "请选择收货地址");
				return;
			}
			price = basicBeans.getPrice();
			int number = Integer
					.parseInt(num_goods.getText().toString().trim());
			double postage = basicBeans.getPostage();
			intent = new Intent(BuyActivity.this, OrderDetailActivity.class);
			intent.putExtra("name", resultName);
			intent.putExtra("phone", resultPhone);
			intent.putExtra("address", resultAddress);
			intent.putExtra("goods_name1", basicBeans.getName());
			intent.putExtra("r_id", r_id);
			popnum1 = num_goods.getText().toString().trim();
			intent.putExtra("goods_number1", num_goods.getText().toString()
					.trim());
			intent.putExtra("goods_price1", basicBeans.getPrice());
			intent.putExtra("postage1", basicBeans.getPostage());
			intent.putExtra("account_goods_num", price * number + postage);
			int sjnumber = (int) (Math.random() * 9000 + 1000);
			order_bianhao = getNowTime() + sjnumber;
			intent.putExtra("order_number1", order_bianhao);
			initViewsa();
			startActivity(intent);
			break;
		case R.id.pop_reduce1:
			if (!pop_num1.getText().toString().equals("1")) {
				num_reduce = Integer.valueOf(pop_num1.getText().toString())
						- ADDORREDUCE + "";
				pop_num1.setText(num_reduce);
				num_goods.setText(num_reduce);
				account_goods.setText(Integer.parseInt(num_reduce)
						* basicBeans.getPrice() + "");
				account_goods1
						.setText((Integer.parseInt(num_reduce) * basicBeans
								.getPrice()) + basicBeans.getPostage() + "");
			} else {
				Toast.makeText(BuyActivity.this, "购买数量不能低于1件",
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.pop_add1:
			if (!pop_num1.getText().toString().equals("750")) {
				num_add = Integer.valueOf(pop_num1.getText().toString())
						+ ADDORREDUCE + "";
				pop_num1.setText(num_add);
			} else {
				Toast.makeText(BuyActivity.this, "不能超过最大产品数量",
						Toast.LENGTH_SHORT).show();
			}
			num_goods.setText(num_add);
			account_goods.setText(Integer.parseInt(num_add)
					* basicBeans.getPrice() + "");
			account_goods1.setText((Integer.parseInt(num_add) * basicBeans
					.getPrice()) + basicBeans.getPostage() + "");
			break;
		case R.id.receive_message:
			intent = new Intent(BuyActivity.this, ShouhuoAddressActivity.class);
			startActivity(intent);

			break;
		case R.id.image_back2:
			finish();

			break;
		default:
			break;
		}
	}

	private void initData() {
        p_id = getIntent().getIntExtra("p_id", 1);
		// TODO Auto-generated method stub
		HttpUtils utils = new HttpUtils();
		time = BasicApplication.getNowTime();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "4");
		params.addBodyParameter("p_id", p_id+"");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e("xu", "hate");
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				Log.e("ssss", "aaaa");
				Gson gson = new Gson();
				Type typeOfT = new TypeToken<List<BasicBean>>() {
				}.getType();

				List<BasicBean> basicBean = gson.fromJson(result, typeOfT);
				basicBeans = basicBean.get(0);
				Log.e("xllll", basicBeans.toString());
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
				Log.e("dddd", basicBeans.toString());
			}
		});

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
		Toast.makeText(this, "" + resultName, 0).show();
		name.setText(resultName);
		address.setText(resultAddress);
		phone.setText(resultPhone);
		if (!resultName.equals("")) {
			shouhuo_msg.setVisibility(View.GONE);
			receive_msg.setVisibility(View.VISIBLE);
		}
	}

	public static String getNowTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
		NOWTIME = sdf.format(new Date());
		Log.e("time", NOWTIME);
		return NOWTIME;
	}

	private void initViewsa() {
		// TODO Auto-generated method stub
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "7");
		params.addBodyParameter("p_id", p_id+"");
		params.addBodyParameter("u_id", MyMethod.GetU_id(getApplicationContext())+"");
		params.addBodyParameter("state", "1");
		params.addBodyParameter("r_id", r_id+"");
		Log.e("prices", price+"");
		params.addBodyParameter("price", price + "");
		params.addBodyParameter("account", popnum1 + "");
		params.addBodyParameter("submittime", time);
		params.addBodyParameter("ordernumber", order_bianhao);
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
                   Log.e("you", "failed");
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				if (result.equals("success")) {
					Toast.makeText(getApplicationContext(), result,
							Toast.LENGTH_SHORT).show();
				} else {

				}
			}
		});
	}
}
