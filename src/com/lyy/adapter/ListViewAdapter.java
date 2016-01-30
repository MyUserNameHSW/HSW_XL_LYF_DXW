package com.lyy.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.lyy.bean.Psort_1;
import com.lyy.bean.Psort_2;
import com.lyy.project.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
	private ArrayList<Psort_1> mList;
	private Context mContext;

	public ListViewAdapter(ArrayList<Psort_1> mList,
			Context mContext) {
		super();
		this.mList = mList;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		if (mList == null) {
			return 0;
		} else {
			return this.mList.size();
		}
	}

	@Override
	public Object getItem(int position) {
		if (mList == null) {
			return null;
		} else {
			return this.mList.get(position);
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(this.mContext).inflate(
					R.layout.listview_item, null, false);
			holder.textView = (TextView) convertView
					.findViewById(R.id.listview_item_textview);
			holder.gridView = (GridView) convertView
					.findViewById(R.id.listview_item_gridview);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (this.mList != null) {
			if (holder.textView != null) {
				String bType = this.mList.get(position).getPs1_name();
				holder.textView.setText(bType);
			}
			if (holder.gridView != null) {
				GridViewAdapter gridViewAdapter = new GridViewAdapter(mContext,
						mList.get(position).getSmalllist());
				holder.gridView.setAdapter(gridViewAdapter);
				gridViewAdapter.notifyDataSetChanged();
			}

		}

		return convertView;

	}
	
	
	public ArrayList<Psort_1> getmList() {
		return mList;
	}

	public void setmList(ArrayList<Psort_1> mList) {
		this.mList = mList;
	}


	private class ViewHolder {
		TextView textView;
		GridView gridView;
	}

}