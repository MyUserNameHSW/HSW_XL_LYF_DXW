package com.lyy.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lyy.bean.P_provide;
import com.lyy.project.R;
import com.lyy.util.MyApplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MySubmit_Adapter extends BaseAdapter {
	Context context;
	LayoutInflater mInflater;
	ViewHolder mHolder;
	List<P_provide> list = new ArrayList<P_provide>();

	public MySubmit_Adapter(Context context, List<P_provide> list) {
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
			arg1 = mInflater.inflate(R.layout.mysubmit_item, null);
			mHolder = new ViewHolder();
			mHolder.tv1 = (TextView) arg1.findViewById(R.id.ms_type);
			mHolder.tv2 = (TextView) arg1.findViewById(R.id.ms_endtime);
			mHolder.tv3 = (TextView) arg1.findViewById(R.id.ms_state);
			arg1.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) arg1.getTag();
		}
		mHolder.tv1.setText("代养类型："+list.get(arg0).getPlant_type());
		mHolder.tv2.setText("截止时间"+list.get(arg0).getEndTime());
		if (list.get(arg0).getState() == 1) {
			mHolder.tv3.setText("待发现");
		}else if (list.get(arg0).getState() == 2) {
			mHolder.tv3.setText("代养中");
		}else if (list.get(arg0).getState() == 3) {
			mHolder.tv3.setText("已结束");
		}
		
		return arg1;
	}

	class ViewHolder {
		TextView tv1, tv2, tv3;
	}
}
