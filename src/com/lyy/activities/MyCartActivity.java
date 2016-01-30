
package com.lyy.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.adapter.MyAdapter;
import com.lyy.bean.CartBean;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

public class MyCartActivity extends Activity {
	private CheckBox checkBox;
	private ListView listView;
	private TextView popTotalPrice; 
	private TextView popDelete; 
	ImageView back;
	private TextView popRecycle; 
	private TextView popCheckOut; 
	private LinearLayout layout; 
	private MyAdapter adapter; 
	private List<CartBean> list = new ArrayList<CartBean>(); 
    
	private boolean flag = true;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_mycart);
		initViews();
		initData();

	}

	private void initData() {
		url = "http://" + MyApplication.IP + ":8080/MyProject/Link";
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "51");
		params.addBodyParameter("u_id", MyMethod.GetU_id(getApplicationContext())+"");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e("shibai", "shibai");
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				Gson gson = new Gson();
				Type typeofType = new TypeToken<List<CartBean>>() {
				}.getType();
				List<CartBean> resultList = gson.fromJson(result, typeofType);
				MyApplication.list.addAll(resultList);
				// ShoppingCanst.list = list;
				adapter = new MyAdapter(handler, getApplicationContext(), MyApplication.list);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
		});
	}


	private void initViews() {
		MyApplication.list = new ArrayList<CartBean>();
		checkBox = (CheckBox) findViewById(R.id.all_check);
		listView = (ListView) findViewById(R.id.main_listView);
		popTotalPrice = (TextView) findViewById(R.id.shopTotalPrice);
		popDelete = (TextView) findViewById(R.id.delete);
		popRecycle = (TextView) findViewById(R.id.collection);
		popCheckOut = (TextView) findViewById(R.id.checkOut);
		layout = (LinearLayout) findViewById(R.id.price_relative);
		back = (ImageView) findViewById(R.id.mycarti_back);
        back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		ClickListener cl = new ClickListener();
		checkBox.setOnClickListener(cl);
		popDelete.setOnClickListener(cl);
		popCheckOut.setOnClickListener(cl);
		popRecycle.setOnClickListener(cl);
	}

	// �¼����������
	private final class ClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.all_check: // ȫѡ
				selectedAll();
				break;
			case R.id.delete: // ɾ��
				String shopIndex = deleteOrCheckOutShop();
				showDialogDelete(shopIndex);
				break;
			case R.id.checkOut: // ����
				goCheckOut();
				break;
			}
		}
	}

	// ����
	private void goCheckOut() {
		String shopIndex = deleteOrCheckOutShop();
		Intent checkOutIntent = new Intent(getApplicationContext(),
				CartPayActivity.class);
		checkOutIntent.putExtra("shopIndex", shopIndex);
		startActivity(checkOutIntent);
	}

	// ȫѡ��ȫȡ��
	private void selectedAll() {
		for (int i = 0; i < MyApplication.list.size(); i++) {
			MyAdapter.getIsSelected().put(i, flag);
		}
		adapter.notifyDataSetChanged();
	}

	// ɾ���������Ʒ
	private String deleteOrCheckOutShop() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < MyApplication.list.size(); i++) {
			if (MyAdapter.getIsSelected().get(i)) {
				sb.append(i);
				sb.append(",");
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	private void showDialogDelete(String str) {
		final String[] delShopIndex = str.split(",");
		Log.e("index", delShopIndex.length + "(--)");
		new AlertDialog.Builder(MyCartActivity.this)
				.setMessage("您确定删除这" + delShopIndex.length + "商品吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {

						for (int i = delShopIndex.length; i > 0; i--) {
							int index = Integer.valueOf(delShopIndex[i - 1]);
							Log.e("ii", index + "++");
							//
							deleteCart(index);
						}
						flag = false;
						selectedAll();
						flag = true;
					}
				}).setNegativeButton("取消", null).create().show();
	}

	private void deleteCart(int pos) {
		final int fy = pos;
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "53");
		Log.e("tttt", MyApplication.list.get(pos).getC_id() + "{}");
		params.addBodyParameter("c_id", MyApplication.list.get(pos).getC_id() + "");
		Log.e("c_id", MyApplication.list.get(pos).getC_id() + "=====");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				MyApplication.list.remove(fy);
				MyMethod.showToast(getApplicationContext(), "删除成功");
				adapter.notifyDataSetChanged();
			}
		});
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 10) {
				float price = (Float) msg.obj;
				if (price > 0) {
					popTotalPrice.setText("￥" + price);
					layout.setVisibility(View.VISIBLE);
				} else {
					layout.setVisibility(View.GONE);
				}
			} else if (msg.what == 11) {
				flag = !(Boolean) msg.obj;
				checkBox.setChecked((Boolean) msg.obj);
			}
		}
	};
}
