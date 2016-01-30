package com.lyy.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyy.bean.P_provide;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

public class PCare_Adapter extends BaseAdapter {
	Context context;
	LayoutInflater mInflater;
	
	List<P_provide> list;

	public PCare_Adapter(Context context, List<P_provide> list) {
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
		ViewHolder mHolder = null;
//		Log.e("size", list.size()+"");
//		Log.e("arg0", arg0+"");
		if (arg1 == null) {
			arg1 = mInflater.inflate(R.layout.pcare_item, null);
			mHolder = new ViewHolder();
			mHolder.imageView = (ImageView) arg1.findViewById(R.id.p_care_img);
			mHolder.tv1 = (TextView) arg1.findViewById(R.id.p_care_name);
			mHolder.tv2 = (TextView) arg1.findViewById(R.id.p_care_script);
			mHolder.tv3 = (TextView) arg1.findViewById(R.id.pp_num);
			mHolder.tv4 = (TextView) arg1.findViewById(R.id.pfocus_num);
			arg1.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) arg1.getTag();
			
		}
		MyApplication.bitmapUtils.display(mHolder.imageView, "http://"
				+ MyApplication.IP + ":8080"+list.get(arg0).getHead());
		mHolder.tv1.setText(list.get(arg0).getName());
		mHolder.tv2.setText(list.get(arg0).getExprience());
		mHolder.tv3.setText("代养次数  "+list.get(arg0).getPp_num()+"");
		mHolder.tv4.setText("关注次数  "+list.get(arg0).getFocus_num()+"");
		//resetViewHolder(mHolder);
		notifyDataSetChanged();
		return arg1;
	}
    class ViewHolder {
		ImageView imageView;
		TextView tv1, tv2, tv3, tv4;
	}
    
}
