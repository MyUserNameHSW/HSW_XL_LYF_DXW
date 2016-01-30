package com.lyy.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.adapter.Fs2_Adapter.MyOnClickListener;
import com.lyy.bean.MyForms;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;
import com.lyy.viewpager.F_noPay;
import com.lyy.xl.activities.DetailActivity;
import com.lyy.xl.activities.OrderDetailActivity;

public class Fs1_Adapter extends BaseAdapter {
	Context context;
	LayoutInflater mInflater;
	List<MyForms> list = new ArrayList<MyForms>();
	ViewHolder mHolder;
	int index;
	String url = "http://" + MyApplication.IP + ":8080/MyProject/Link";

	public Fs1_Adapter(Context context, List<MyForms> list) {
		super();
		this.context = context;
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		index = arg0;
		if (arg1 == null) {
			arg1 = mInflater.inflate(R.layout.fs1_item, null);
			mHolder = new ViewHolder();
			mHolder.lin = (RelativeLayout) arg1.findViewById(R.id.fs1_r1);
			mHolder.name = (TextView) arg1.findViewById(R.id.fs1_name);
			mHolder.price = (TextView) arg1.findViewById(R.id.fs1_price);
			mHolder.num = (TextView) arg1.findViewById(R.id.fs1_buy_number);
			mHolder.account = (TextView) arg1.findViewById(R.id.fs1_account);
			mHolder.img = (ImageView) arg1.findViewById(R.id.fs1_img);
			mHolder.del = (Button) arg1.findViewById(R.id.fs1_delete);
			mHolder.pays = (Button) arg1.findViewById(R.id.fs1_pay);
			mHolder.ordernum = (TextView) arg1.findViewById(R.id.ordernumber);
			arg1.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) arg1.getTag();
		}
		mHolder.name.setText(list.get(arg0).getP_name());
		mHolder.price.setText("单价 " + list.get(arg0).getPrice() + "元");
		mHolder.num.setText("数量 " + list.get(arg0).getAccount() + "件");
		mHolder.account.setText("总价 " + list.get(arg0).getAccount()
				* list.get(arg0).getPrice() + "元");
		mHolder.ordernum.setText(list.get(arg0).getOrderNumber());
		MyApplication.bitmapUtils.display(mHolder.img,
				"http://10.204.1.21:8080"+list.get(arg0).getImg());
		mHolder.del.setOnClickListener(new MyOnClickListener(arg0));
		mHolder.lin.setOnClickListener(new MyOnClickListener(arg0));
		mHolder.pays.setOnClickListener(new MyOnClickListener(arg0));
		return arg1;
	}

	public class MyOnClickListener implements OnClickListener {
		int indexs;

		public MyOnClickListener(int indexs) {
			this.indexs = indexs;
		}

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			HttpUtils utils = new HttpUtils();
			RequestParams params = new RequestParams();
			switch (view.getId()) {
			case R.id.fs1_pay:
               Intent intent = new Intent(context,OrderDetailActivity.class);
               intent.putExtra("r_id", list.get(indexs).getR_id());
               Log.e("sssss", list.get(indexs).getR_id()+"ee");
               Log.e("bean", list.get(indexs).toString());
               intent.putExtra("order_number1", list.get(indexs).getOrderNumber());
               intent.putExtra("goods_name1", list.get(indexs).getP_name());
               intent.putExtra("goods_number1",list.get(indexs).getAccount()+"");
               intent.putExtra("goods_price1", list.get(indexs).getPrice());
               intent.putExtra("account_goods_num", list.get(indexs).getAccount() * list.get(indexs).getPrice());
               context.startActivity(intent);
				break;
			case R.id.fs1_delete:
				params.addBodyParameter("flag", "28");
				params.addBodyParameter("fr_id", list.get(indexs).getFr_id()
						+ "");
				utils.send(HttpMethod.POST, url, params,
						new RequestCallBack<String>() {

							@Override
							public void onFailure(HttpException arg0,
									String arg1) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(ResponseInfo<String> arg0) {
								// TODO Auto-generated method stub
								String string = arg0.result;
								if (string.equals("success")) {
									MyMethod.showToast(context, "删除成功");
								}
							}
						});
				list.remove(indexs);
				notifyDataSetChanged();
				break;
				case R.id.fs1_r1:
					Bundle bundle = new Bundle();
					Intent intent2 = new Intent(context, DetailActivity.class);
					bundle.putInt("p_id", list.get(indexs).getP_id());
					intent2.putExtras(bundle);
					context.startActivity(intent2);
					break;
			default:
				break;
			}

		}

	}

	class ViewHolder {
		TextView name, price, num, account,ordernum;
		ImageView img;
		Button del, pays;
		RelativeLayout lin;
	}
}
