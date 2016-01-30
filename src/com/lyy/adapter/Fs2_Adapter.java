package com.lyy.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.activities.PayMoneyActivity;
import com.lyy.bean.MyForms;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;
import com.lyy.xl.activities.DetailActivity;

public class Fs2_Adapter extends BaseAdapter {
	Context context;
	LayoutInflater mInflater;
	List<MyForms> list = new ArrayList<MyForms>();
	ViewHolder mHolder;
	int index;
	String url = "http://" + MyApplication.IP + ":8080/MyProject/Link";

	public Fs2_Adapter(Context context, List<MyForms> list) {
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
			arg1 = mInflater.inflate(R.layout.fs2_item, null);
			mHolder = new ViewHolder();
			mHolder.lin = (RelativeLayout) arg1.findViewById(R.id.fs2_r1);
			mHolder.name = (TextView) arg1.findViewById(R.id.fs2_name);
			mHolder.price = (TextView) arg1.findViewById(R.id.fs2_price);
			mHolder.num = (TextView) arg1.findViewById(R.id.fs2_buy_number);
			mHolder.account = (TextView) arg1.findViewById(R.id.fs2_account);
			mHolder.img = (ImageView) arg1.findViewById(R.id.fs2_img);
			mHolder.del = (Button) arg1.findViewById(R.id.fs2_delete);
			mHolder.pays = (Button) arg1.findViewById(R.id.fs2_pay);
			mHolder.ordernum = (TextView) arg1.findViewById(R.id.ordernumber2);
			arg1.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) arg1.getTag();
		}
		mHolder.name.setText(list.get(arg0).getP_name());
		mHolder.price.setText(list.get(arg0).getPrice() + "元");
		mHolder.num.setText(list.get(arg0).getAccount() + "件");
		mHolder.account.setText(list.get(arg0).getAccount()
				* list.get(arg0).getPrice() + "元");
		mHolder.ordernum.setText(list.get(arg0).getOrderNumber());
		MyApplication.bitmapUtils.display(mHolder.img,
				"http://10.204.1.21:8080"+list.get(arg0).getImg());
		mHolder.del.setOnClickListener(new MyOnClickListener(arg0));
		mHolder.lin.setOnClickListener(new MyOnClickListener(arg0));
		mHolder.pays.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MyMethod.showToast(context, "查看物流");
			}
		});
		return arg1;
	}

	private void deleteFs1(int position) {
		MyMethod.showToast(context, "确认收货");
		final int aaa = position;
		final EditText inputServer = new EditText(context);
		inputServer.setBackgroundColor(R.drawable.two_radius);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("输入支付密码").setIcon(android.R.drawable.ic_dialog_info)
				.setView(inputServer).setNegativeButton("取消", null);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				ChangeState(aaa);
			}
		});
		builder.show();
	}

	private void ChangeState(int sss) {
		final int bbb = sss;
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("ordernumber", list.get(sss).getOrderNumber());
		params.addBodyParameter("state", 3 + "");
		params.addBodyParameter("flag", "47");
		utils.send(HttpMethod.POST, MyMethod.url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						MyMethod.showToast(context, "付款成功");
						Intent intent = new Intent(context,
								PayMoneyActivity.class);
						intent.putExtra("p_id", list.get(bbb).getP_id());
						context.startActivity(intent);
					}
				});
	}

	public class MyOnClickListener implements OnClickListener {
		int indexs;

		public MyOnClickListener(int indexs) {
			this.indexs = indexs;
		}

		@Override
		public void onClick(View view) {
			if (view.getId() == R.id.fs2_delete) {
				deleteFs1(indexs);
			} else if (view.getId() == R.id.fs2_r1) {
				Bundle bundle = new Bundle();
				Intent intent = new Intent(context, DetailActivity.class);
				bundle.putInt("p_id", list.get(indexs).getP_id());
				intent.putExtras(bundle);
				context.startActivity(intent);
			}

		}
	}

	class ViewHolder {
		TextView name, price, num, account, ordernum;
		ImageView img;
		Button del, pays;
		RelativeLayout lin;
	}
}
