package com.lyy.forums.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lyy.bean.Forum;
import com.lyy.project.R;
import com.lyy.util.MyApplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class ForumAdapter extends BaseAdapter {
	Context context;
	List<Forum> list = new ArrayList<Forum>();
	LayoutInflater mInflater;
	ViewHolder holder;
	View view;
	PopupWindow pop;

	public ForumAdapter() {
		super();
	}

	public ForumAdapter(Context context, List<Forum> list) {
		super();
		this.context = context;
		this.list = list;
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

	@SuppressWarnings("static-access")
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.forum_item1, null);
			holder = new ViewHolder();
			holder.usernameTextView = (TextView) convertView
					.findViewById(R.id.username);
			holder.userimageImageView = (ImageView) convertView
					.findViewById(R.id.userimage);
			holder.timeTextView = (TextView) convertView
					.findViewById(R.id.time);
			holder.pointTextView = (TextView) convertView
					.findViewById(R.id.point);
			holder.articltTextView = (TextView) convertView
					.findViewById(R.id.articl);
			holder.themeTextView = (TextView) convertView
					.findViewById(R.id.theme);
			TextPaint tp = holder.themeTextView.getPaint(); 
			tp.setFakeBoldText(true); 

			holder.imageImageView = (ImageView) convertView
					.findViewById(R.id.image);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.usernameTextView.setText(list.get(position).getUsername());
		holder.timeTextView.setText(list.get(position).getF_time());
		if (list.get(position).getPoint()==null) {
			holder.pointTextView.setVisibility(view.GONE);
		}else {
			holder.pointTextView.setText(list.get(position).getPoint());
		}
		holder.articltTextView.setText(list.get(position).getArticl());
		holder.themeTextView.setText(list.get(position).getTheme());
		MyApplication.bitmapUtils.display(holder.userimageImageView, "http://"
				+ MyApplication.IP + ":8080"
				+ list.get(position).getUserimg());
		Log.e("userimg", list.get(position).getUserimg());
		//String url="http://"+ MyApplication.applicationID + ":8080"+list.get(position).getImg();
				if (list.get(position).getImg().size()==0) {
					holder.imageImageView.setVisibility(view.GONE);
				} else {
					String url2="http://"+ MyApplication.IP + ":8080/uploads/"+list.get(position).getImg().get(0).getP_img();
					MyApplication.bitmapUtils.display(holder.imageImageView,url2);
				}
		return convertView;
	}

	@SuppressLint("InflateParams")
	@SuppressWarnings({ "deprecation" })
	private void initPopupWindow() {
		view = mInflater.inflate(R.layout.forum_pop, null);
		pop = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		pop.setOutsideTouchable(true);
		view.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
			}
		});
	}

	public class ViewHolder {
		TextView usernameTextView, timeTextView,pointTextView, articltTextView,
				themeTextView;
		ImageView imageImageView, userimageImageView;
	}
}
