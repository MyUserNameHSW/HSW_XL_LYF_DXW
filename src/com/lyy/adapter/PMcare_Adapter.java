package com.lyy.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lyy.bean.MyCare;
import com.lyy.bean.P_provide;
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

public class PMcare_Adapter extends BaseAdapter {
	List<MyCare> list = new ArrayList<MyCare>();
	Context context;
	LayoutInflater mInflater;
	ViewHolder mHolder;

	public PMcare_Adapter(List<MyCare> list, Context context) {
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
			arg1 = mInflater.inflate(R.layout.pmy_item, null);
			mHolder = new ViewHolder();
			mHolder.imageView = (ImageView) arg1.findViewById(R.id.pmy_head);
			mHolder.name = (TextView) arg1.findViewById(R.id.pmy_other);
			mHolder.time = (TextView) arg1.findViewById(R.id.pmy_overtime);
			arg1.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) arg1.getTag();
		}
		MyApplication.bitmapUtils.display(mHolder.imageView, "http://"
				+ MyApplication.IP + ":8080/upload/ic_head.png");
		String username = list.get(arg0).getUsername();
		mHolder.name.setText(username);
		mHolder.time.setText(list.get(arg0).getOver_time());
		return arg1;
	}

	class ViewHolder {
		ImageView imageView;
		TextView name, time;
	}
}
