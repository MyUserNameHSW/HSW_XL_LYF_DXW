package com.lyy.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lyy.activities.FriendsActivity;
import com.lyy.bean.PC_assess;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PC_assess_Adapter extends BaseAdapter {
	Activity context;
	LayoutInflater mInflater;
	List<PC_assess> list = new ArrayList<PC_assess>();
	ViewHolder mHolder;

	public PC_assess_Adapter(Activity context, List<PC_assess> list) {
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
			arg1 = mInflater.inflate(R.layout.pcare_assess_item, null);
			mHolder = new ViewHolder();
			mHolder.tv1 = (TextView) arg1.findViewById(R.id.passess_name);
			mHolder.tv2 = (TextView) arg1.findViewById(R.id.passess_text);
			mHolder.tv3 = (TextView) arg1.findViewById(R.id.passess_time);
			mHolder.head = (ImageView) arg1.findViewById(R.id.passess_head);
			arg1.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) arg1.getTag();
		}
		mHolder.tv1.setText(list.get(arg0).getU_name());
		mHolder.tv2.setText(list.get(arg0).getText());
		mHolder.tv3.setText(list.get(arg0).getTime());
		MyApplication.bitmapUtils.display(mHolder.head, "http://"
				+ MyApplication.IP + ":8080/upload/ic_head.png");
		mHolder.head.setOnClickListener(new MyClickListener(arg0));
		return arg1;
	}

	class ViewHolder {
		ImageView head;
		TextView tv1, tv2, tv3;
	}

	class MyClickListener implements OnClickListener {
		int index;

		public MyClickListener(int index) {
			super();
			this.index = index;
		}

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context, FriendsActivity.class);
			intent.putExtra("fu_id", list.get(index).getU_id());
			context.startActivity(intent);
		}
	}
}
