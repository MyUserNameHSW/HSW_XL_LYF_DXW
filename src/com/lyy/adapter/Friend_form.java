package com.lyy.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lyy.bean.Forum;
import com.lyy.bean.MyForum;
import com.lyy.bean.MyForums;
import com.lyy.project.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Friend_form extends BaseAdapter{
   List<MyForums> list = new ArrayList<MyForums>();
   Context context;
   LayoutInflater mInflater;
   ViewHolder mHolder;
   
   public Friend_form(List<MyForums> list, Context context) {
	super();
	this.list = list;
	this.context = context;
	mInflater = LayoutInflater.from(context);
}

class ViewHolder{
	   TextView tv1,tv2;
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
			arg1 = mInflater.inflate(R.layout.friend_forum_item, null);
			mHolder = new ViewHolder();
			mHolder.tv1 = (TextView) arg1.findViewById(R.id.fforums_theme);
			mHolder.tv2 = (TextView) arg1.findViewById(R.id.fforums_time);
			arg1.setTag(mHolder);
		}else {
			mHolder = (ViewHolder) arg1.getTag();
		}
		mHolder.tv1.setText(list.get(arg0).getTheme());
		mHolder.tv2.setText(list.get(arg0).getF_time());
		return arg1;
	}

}
