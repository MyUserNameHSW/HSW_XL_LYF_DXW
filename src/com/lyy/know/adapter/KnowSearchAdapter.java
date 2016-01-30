package com.lyy.know.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lyy.bean.EssayBean;
import com.lyy.project.R;

public class KnowSearchAdapter extends BaseAdapter{
	Context context;
	List<EssayBean> list;
	LayoutInflater mInflater;
	ViewHolder mHolder;
	
	
	public KnowSearchAdapter(Context context, List<EssayBean> list) {
		super();
		this.context = context;
		this.list = list;
		mInflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(list.size()==0){
			Toast.makeText(context, "对不起你搜索的内容不存在", Toast.LENGTH_SHORT).show();
		}
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
		TextView nameTextView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView==null){
			convertView=mInflater.inflate(R.layout.know_search_textview, null);
			mHolder=new ViewHolder();
			mHolder.nameTextView=(TextView) convertView.findViewById(R.id.bk_search_tv);
			convertView.setTag(mHolder);
		}else {
			mHolder=(ViewHolder) convertView.getTag();
		}
		mHolder.nameTextView.setText(list.get(position).getName());
		return convertView;
	}

}
