package com.lyy.xl.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lyy.project.R;
import com.lyy.xl.bean.AssessBean;

public class AssessAdapter extends BaseAdapter{
 List<AssessBean> listab=new ArrayList<AssessBean>();
 Context context;
 LayoutInflater mInflater;
 ViewHolder mHolder;
 
 
 
 public AssessAdapter(List<AssessBean> listab, Context context) {
	super();
	this.listab = listab;
	this.context = context;
	mInflater=LayoutInflater.from(context);
}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listab.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listab.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (arg1==null) {
			mHolder=new ViewHolder();
			arg1=mInflater.inflate(R.layout.pj_item, null);
			mHolder.pj_content=(TextView) arg1.findViewById(R.id.pj_content);
			mHolder.pj_username=(TextView) arg1.findViewById(R.id.pj_username);
			mHolder.pj_time=(TextView) arg1.findViewById(R.id.pj_time);
			arg1.setTag(mHolder);
		}else {
			mHolder=(ViewHolder) arg1.getTag();
		}
		mHolder.pj_content.setText(listab.get(arg0).getText());
		mHolder.pj_username.setText(listab.get(arg0).getU_id()+"");
		mHolder.pj_time.setText(listab.get(arg0).getTime()+"");
		
		return arg1;
	}
	class ViewHolder{
		 TextView pj_content,pj_username,pj_time;
		 
		 
	 }
}
