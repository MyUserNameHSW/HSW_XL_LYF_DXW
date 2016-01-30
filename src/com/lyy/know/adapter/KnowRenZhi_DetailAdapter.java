package com.lyy.know.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyy.bean.EssayBean;
import com.lyy.project.R;
import com.lyy.util.MyApplication;

public class KnowRenZhi_DetailAdapter extends BaseAdapter{
	Context context;
	List<EssayBean> list;
	LayoutInflater mInflater;
	ViewHolder mHolder;
	public KnowRenZhi_DetailAdapter(Context context, List<EssayBean> list) {
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
		ImageView imageView;
		TextView textView;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.know_ren_zhi_detail_item, null);
			mHolder=new ViewHolder();
			mHolder.imageView=(ImageView) convertView.findViewById(R.id.bk_ren_zhi_image);
			mHolder.textView=(TextView) convertView.findViewById(R.id.bk_ren_zhi_detail_tv);
			convertView.setTag(mHolder);
		}else {
			mHolder=(ViewHolder) convertView.getTag();
		}
		String url="http://"+MyApplication.IP+":8080"+list.get(position).getSimage();
		MyApplication.bitmapUtils.display(mHolder.imageView, url);		
		mHolder.textView.setText(list.get(position).getName());
		return convertView;
	}

}
