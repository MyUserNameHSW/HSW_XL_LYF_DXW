package com.lyy.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lyy.activities.CareTypeActivity;
import com.lyy.activities.EditPProvideActivity;
import com.lyy.activities.Type1_1Activity;
import com.lyy.bean.Psort_2;
import com.lyy.project.R;

public class CareGridViewAdapter extends BaseAdapter {
	private Activity mContext;
	private ArrayList<Psort_2> mList;

	public CareGridViewAdapter(Activity mContext,
			ArrayList<Psort_2> arrayListForEveryGridView) {
		super();
		this.mContext = mContext;
		this.mList = arrayListForEveryGridView;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(this.mContext).inflate(
					R.layout.gridview_item, null, false);
			holder.textView = (TextView) convertView
					.findViewById(R.id.gridview_item_button);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (this.mList != null) {
			String small = this.mList.get(position).getPs2_name();
			if (holder.textView != null) {
				holder.textView.setText(small);
				holder.textView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(mContext, "大类ID-->"+mList.get(position).getPs2_id()+",小类ID-->"+mList.get(position).getPs1_id(),
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(mContext,EditPProvideActivity.class);
						intent.putExtra("ps2_id", mList.get(position).getPs1_id());
						mContext.startActivity(intent);
						mContext.finish();
					}
				});
			}
		}
		return convertView;
	}

	private class ViewHolder {
		TextView textView;
	}

}