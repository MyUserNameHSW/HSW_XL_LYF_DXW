package com.lyy.xl.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.lyy.project.R;
import com.lyy.xl.activities.ShouhuoAddressActivity;
import com.lyy.xl.bean.ReceiveBean;

public class ReceiveAddressAdapter extends BaseAdapter {
	List<ReceiveBean> list;
	Context context;
	LayoutInflater mInflater;
	ViewHolder mHolder;
	Button button;

	public ReceiveAddressAdapter(List<ReceiveBean> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		mInflater = LayoutInflater.from(context);
		//ShouhuoAddressActivity shouhuoAddressActivity=(ShouhuoAddressActivity) context;
		//button=shouhuoAddressActivity.getButton();
	}

	

	


	public List<ReceiveBean> getList() {
		return list;
	}

	public void setList(List<ReceiveBean> list) {
		this.list = list;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ReceiveBean receiveBean = list.get(position);
		if (convertView == null) {
			mHolder = new ViewHolder();
			convertView = mInflater
					.inflate(R.layout.receive_address_item, null);
			mHolder.receive_name = (TextView) convertView
					.findViewById(R.id.receive_name);
			mHolder.receive_phone = (TextView) convertView
					.findViewById(R.id.receive_phone);
			mHolder.receive_address = (TextView) convertView
					.findViewById(R.id.receive_address);
			mHolder.button =(Button) convertView.
					findViewById(R.id.receive_address_ok);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}

		mHolder.receive_name.setText(list.get(position).getName());
		mHolder.receive_phone.setText(list.get(position).getPhone());
		mHolder.receive_address.setText("地址："+list.get(position).getAddress());
		mHolder.button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("name",receiveBean.getName());
				intent.putExtra("phone", receiveBean.getPhone());
				intent.putExtra("address", receiveBean.getAddress());
				intent.putExtra("r_id", receiveBean.getR_id());
				ShouhuoAddressActivity shouhuoAddressActivity=(ShouhuoAddressActivity)context;
				shouhuoAddressActivity.setResult(Activity.RESULT_OK, intent);
				shouhuoAddressActivity.finish();
			}
		});
		return convertView;
	}

 class ViewHolder {
		TextView receive_name, receive_phone, receive_address;
		Button button;
		
	}

}
