package com.lyy.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lyy.bean.MyCare;
import com.lyy.project.R;
import com.lyy.util.MyApplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCare1Adapter extends BaseAdapter {
	Context context;
	LayoutInflater mInflater;
	List<MyCare> list = new ArrayList<MyCare>();
	ViewHolder mHolder;

	public MyCare1Adapter(Context context, List<MyCare> list) {
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
			arg1 = mInflater.inflate(R.layout.care_item1, null);
			mHolder = new ViewHolder();
			mHolder.imageView = (ImageView) arg1.findViewById(R.id.mc1_img);
			mHolder.name = (TextView) arg1.findViewById(R.id.mc1_name);
			mHolder.overTime = (TextView) arg1.findViewById(R.id.mc1_overtime);
			arg1.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) arg1.getTag();
		}
		MyApplication.bitmapUtils.display(mHolder.imageView, "http://"
				+ MyApplication.IP + ":8080/upload/ic_head.png");
		mHolder.name.setText(list.get(arg0).getName());
		mHolder.overTime.setText("领回时间"+list.get(arg0).getOver_time());
		return arg1;
	}

	class ViewHolder {
		ImageView imageView;
		TextView name, overTime;
	}
}
