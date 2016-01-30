package com.lyy.xl.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.xl.bean.ProductBean;

public class StoreAdapter extends BaseAdapter {
	List<ProductBean> list = new ArrayList<ProductBean>();
	Context context;
	LayoutInflater mInflater;
	ViewHolder mHolder;

	public List<ProductBean> getList() {
		return list;
	}

	public void setList(List<ProductBean> list) {
		this.list = list;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public StoreAdapter(List<ProductBean> list, Context context) {
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
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			mHolder = new ViewHolder();
			convertView = mInflater
					.inflate(R.layout.activity_store1_item, null);
			mHolder.image_store1 = (ImageView) convertView
					.findViewById(R.id.image_store1);
			mHolder.name_goods1 = (TextView) convertView
					.findViewById(R.id.name_goods1);
			mHolder.depict_goods1 = (TextView) convertView
					.findViewById(R.id.depict_goods1);
			mHolder.price_goods1 = (TextView) convertView
					.findViewById(R.id.price_goods1);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		MyApplication.bitmapUtils.display(mHolder.image_store1, "http://"+MyApplication.IP+":8080"+list.get(position).getImg());
		mHolder.name_goods1.setText(list.get(position).getName());
		mHolder.depict_goods1.setText("价格     "+list.get(position).getPrice()+" 元");
		mHolder.price_goods1.setText("邮费      "+list.get(position).getPostage()+ "  元");
		return convertView;
	}

	class ViewHolder {
		ImageView image_store1;
		TextView name_goods1, depict_goods1, price_goods1;
	}

}
