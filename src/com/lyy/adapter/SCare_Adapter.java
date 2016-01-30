package com.lyy.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lyy.bean.P_provide;
import com.lyy.bean.S_provide;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SCare_Adapter extends BaseAdapter {
	Context context;
	LayoutInflater mInflater;
	ViewHolder mHolder;
	List<S_provide> list = new ArrayList<S_provide>();

	public SCare_Adapter(Context context, List<S_provide> list) {
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
		if (arg1 == null) {
			arg1 = mInflater.inflate(R.layout.scare_item, null);
			mHolder = new ViewHolder();
			mHolder.imageView = (ImageView) arg1.findViewById(R.id.s_care_img);
			mHolder.tv1 = (TextView) arg1.findViewById(R.id.s_care_name);
			mHolder.tv2 = (TextView) arg1.findViewById(R.id.s_care_script);
			mHolder.tv3 = (TextView) arg1.findViewById(R.id.p_address);
			arg1.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) arg1.getTag();
		}
		MyApplication.bitmapUtils.display(mHolder.imageView, "http://"
				+ MyApplication.IP + ":8080"+list.get(arg0).getImg());
		mHolder.tv1.setText(list.get(arg0).getShop_name());
		mHolder.tv2.setText(list.get(arg0).getSctipt());
		mHolder.tv3.setText(list.get(arg0).getShop_address());
		return arg1;
	}

	class ViewHolder {
		ImageView imageView;
		TextView tv1, tv2, tv3, tv4;
	}
}
