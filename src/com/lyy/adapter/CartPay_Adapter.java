package com.lyy.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lyy.adapter.Friend_form.ViewHolder;
import com.lyy.bean.CartBean;
import com.lyy.bean.MyForums;
import com.lyy.project.R;
import com.lyy.util.MyApplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CartPay_Adapter extends BaseAdapter {
	List<CartBean> list = new ArrayList<CartBean>();
	Context context;
	LayoutInflater mInflater;
	ViewHolder mHolder;

	public CartPay_Adapter(List<CartBean> list, Context context) {
		super();
		this.list = list;
		this.context = context;
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
		if (arg1 == null) {
			arg1 = mInflater.inflate(R.layout.cartpay_item, null);
			mHolder = new ViewHolder();
			mHolder.tv1 = (TextView) arg1.findViewById(R.id.cp_name);
			mHolder.tv2 = (TextView) arg1.findViewById(R.id.cp_price);
			mHolder.tv3 = (TextView) arg1.findViewById(R.id.cp_num);
			mHolder.iv1 = (ImageView) arg1.findViewById(R.id.cpay_img);
			arg1.setTag(mHolder);
		}else {
			mHolder = (ViewHolder) arg1.getTag();
		}
		mHolder.tv1.setText(list.get(arg0).getPname());
		mHolder.tv2.setText(list.get(arg0).getPrice()+"");
		mHolder.tv3.setText(list.get(arg0).getAccount()+"件");
		MyApplication.bitmapUtils.display(mHolder.iv1, "http://"+MyApplication.IP+":8080"+list.get(arg0).getPic());
		return arg1;
	}

	class ViewHolder {
		TextView tv1, tv2, tv3;
		ImageView iv1;
	}
}
