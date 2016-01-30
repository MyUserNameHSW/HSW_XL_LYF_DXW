package com.lyy.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lyy.bean.MyCare_Share;
import com.lyy.project.R;
import com.lyy.util.MyApplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Care_Share_Adapter extends BaseAdapter {
	Context context;
	LayoutInflater mInflater;
	List<MyCare_Share> list = new ArrayList<MyCare_Share>();
	ViewHolder holder;

	public Care_Share_Adapter(Context context, List<MyCare_Share> list) {
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
			arg1 = mInflater.inflate(R.layout.care_share_item, null);
			holder = new ViewHolder();
			holder.iv1 = (ImageView) arg1.findViewById(R.id.tcare_img);
			holder.iv2 = (ImageView) arg1.findViewById(R.id.mcare_img);
			holder.iv3 = (ImageView) arg1.findViewById(R.id.bcare_img);
			holder.tv1 = (TextView) arg1.findViewById(R.id.share_time);
			holder.tv2 = (TextView) arg1.findViewById(R.id.share_script);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		MyApplication.bitmapUtils.display(holder.iv1, "http://"
				+ MyApplication.IP + ":8080/upload/ic_head.png");
		MyApplication.bitmapUtils.display(holder.iv2, "http://"
				+ MyApplication.IP + ":8080/upload/ic_head.png");
		MyApplication.bitmapUtils.display(holder.iv3, "http://"
				+ MyApplication.IP + ":8080/upload/ic_head.png");
		holder.tv1.setText("分享时间"+list.get(arg0).getShare_time());
		holder.tv2.setText(list.get(arg0).getScript());
		return arg1;
	}

	class ViewHolder {
		ImageView iv1, iv2, iv3;
		TextView tv1, tv2;
	}
}
