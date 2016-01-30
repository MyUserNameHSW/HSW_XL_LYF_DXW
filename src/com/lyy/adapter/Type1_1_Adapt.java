package com.lyy.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.lyy.bean.Product;
import com.lyy.project.R;

public class Type1_1_Adapt extends BaseAdapter {
	Context context;
	List<Product> list = new ArrayList<Product>();
	LayoutInflater mInflater;
	ViewHolder mhHolder;

	public Type1_1_Adapt(Context context, List<Product> list) {
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
		Log.e("arg0", arg0+"");
		if (arg1 == null) {
			arg1 = mInflater.inflate(R.layout.type1_1_item, null);
			mhHolder = new ViewHolder();
			mhHolder.smallImageView = (SmartImageView) arg1
					.findViewById(R.id.type1_1_img);
			mhHolder.nameTextView = (TextView) arg1
					.findViewById(R.id.type1_1_name);
			mhHolder.tv2 = (TextView) arg1.findViewById(R.id.type1_1_price);
			mhHolder.tv3 = (TextView) arg1.findViewById(R.id.type1_1_buynum);

			arg1.setTag(mhHolder);
		} else {
			mhHolder = (ViewHolder) arg1.getTag();
		}
		mhHolder.nameTextView.setText(list.get(arg0).getName());
		mhHolder.tv2.setText("￥ "+list.get(arg0).getPrice()+"元");
		mhHolder.tv3.setText("已售"+list.get(arg0).getBuy_num()+"笔");
		String url = list.get(arg0).getImg();
		mhHolder.smallImageView.setImageUrl(url, R.drawable.smallimage_fail,
				R.drawable.smallimage_load);
		return arg1;
	}

	class ViewHolder {
		SmartImageView smallImageView;
		TextView nameTextView,tv2,tv3;
	}
}
