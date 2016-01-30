package com.lyy.forums.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lyy.bean.F_assess;
import com.lyy.project.R;
import com.lyy.util.MyApplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

@SuppressLint("InflateParams") 
public class F_assessAdapter extends BaseAdapter {
	List<F_assess> list = new ArrayList<F_assess>();
	Context context;
	LayoutInflater mInflater;
	ViewHolder holder;


	public F_assessAdapter(List<F_assess> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {

		return list.size();
	}

	public Object getItem(int position) {

		return list.get(position);
	}

	public long getItemId(int position) {

		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.forum_item2, null);
			holder = new ViewHolder();
			// 评论人的名称，头像，评论时间以及内容
			holder.usernameitemTextView = (TextView) convertView
					.findViewById(R.id.usernameitem);
			holder.userimageitemView = (ImageView) convertView
					.findViewById(R.id.userimageitem);
			holder.timeitemTextView = (TextView) convertView
					.findViewById(R.id.timeitem);
			holder.contentTextView = (TextView) convertView
					.findViewById(R.id.content);
			
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.usernameitemTextView.setText(list.get(position).getUsername());
		holder.contentTextView.setText(list.get(position).getContent());
		holder.timeitemTextView.setText(list.get(position).getTime());
		MyApplication.bitmapUtils.display(holder.userimageitemView,"http://" + MyApplication.IP+ ":8080"+ list.get(position).getUserimg());
		return convertView;
	}

	class ViewHolder {
		TextView usernameitemTextView, contentTextView, timeitemTextView,
				zhuanfa1TextView, pinglunt1TextView, dianzan1tTextView;
		ImageView userimageitemView;
		PopupWindow mPopupWindow;
	}

}
