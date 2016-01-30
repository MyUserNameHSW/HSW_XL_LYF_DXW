package com.lyy.know.adapter;

import java.util.List;


import com.lyy.bean.Esort1Bean;
import com.lyy.project.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class KnowEsort1_1Adapter extends BaseAdapter{
	Context context;
	List<Esort1Bean> list;
	LayoutInflater mInflater;
	ViewHolder mHolder;
	


	public KnowEsort1_1Adapter(Context context, List<Esort1Bean> list) {
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
		TextView esortTextView;
		//TextView know1TextView;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			//第一次进入应用，初始化listView每一行布局
			convertView=mInflater.inflate(R.layout.know_problem_item, null);
			mHolder=new ViewHolder();
			mHolder.esortTextView=(TextView) convertView.findViewById(R.id.bk_problem_textView);
			convertView.setTag(mHolder);
		}else {
			mHolder=(ViewHolder) convertView.getTag();
		}
		mHolder.esortTextView.setText(list.get(position).getEs1_name());
		return convertView;
	}

}
