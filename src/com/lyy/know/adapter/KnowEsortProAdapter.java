package com.lyy.know.adapter;

import java.util.List;

import com.lyy.bean.Esort2Bean;
import com.lyy.project.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class KnowEsortProAdapter extends BaseAdapter{

	Context context;
	List<Esort2Bean> list;
	LayoutInflater mInflater;
	ViewHolder mHolder;
	
	public KnowEsortProAdapter(Context context, List<Esort2Bean> list) {
		super();
		this.context = context;
		this.list = list;
		mInflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	class ViewHolder{
		TextView esort2TextView;
		//TextView know1TextView;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent)  {
		if(convertView==null){
			//第一次进入应用，初始化listView每一行布局
			convertView=mInflater.inflate(R.layout.know_pro_item, null);
			mHolder=new ViewHolder();
			mHolder.esort2TextView=(TextView) convertView.findViewById(R.id.bk_pro_tv);
			convertView.setTag(mHolder);
		}else {
			mHolder=(ViewHolder) convertView.getTag();
		}
		mHolder.esort2TextView.setText(list.get(position).getEs2_name());
		return convertView;
	}

}
