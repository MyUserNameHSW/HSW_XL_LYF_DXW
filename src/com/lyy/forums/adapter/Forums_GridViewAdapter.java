package com.lyy.forums.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lyy.project.R;
import com.lyy.util.MyApplication;

public class Forums_GridViewAdapter extends BaseAdapter {
	// 上下文对象
	private Context context;
	View view;
	GridView gridView;
	// count表示图片总数，设置原则为如果只有一张图片，则图片宽度填满父布局，高度为手机宽一半高
	// 两张图片则宽度平分父布局宽度，高度为父布局三分之一，大于两张图片时每行最多显示三张图片，宽度平分
	int count, width;
	// 这里注意LayoutParams是属于AbsListView类的，因为本例中图片父布局是gridview
	LayoutParams params;
	private List<String> list = new ArrayList<String>();
	private LayoutInflater mInflater;
	BitmapUtils bitmapUtils;

	

	public Forums_GridViewAdapter(Context context, GridView gridView, int width,
			List<String> list) {
		super();
		this.context = context;
		this.gridView = gridView;
		this.width = width;
		this.list = list;
		mInflater = LayoutInflater.from(context);
		bitmapUtils = new BitmapUtils(context);
		// 加载中图片
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
	}

	public int getCount() {
		count = list.size();
		return count;
	}

	public Object getItem(int position) {

		return list.get(position);
	}

	public long getItemId(int position) {

		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		viewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.gridviewitem, null);
			holder = new viewHolder();
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.detailgridviewimage);

			if (count == 1) {
				// 设置gridview每行只显示一张图片
				gridView.setNumColumns(1);
				// 只有一张图片时，图片宽与手机屏幕分辨率宽一致，高为宽分辨率的一半
				params = new LayoutParams(width, width / 2);
				holder.imageView.setLayoutParams(params);
			} else if (count == 2) {
				// 设置gridview每行只显示2张图片
				gridView.setNumColumns(2);
				// 只有两张图片时，图片宽度几乎为手机分辨率宽的一半，因为gridview设置了
				// 两张图片间距为1dp，所以这里减去了一个值3，可以自行调节此值大小
				params = new LayoutParams(width / 2 - 3, width / 3);
				holder.imageView.setLayoutParams(params);
			} else {
				// 设置gridview每行只显示3张图片
				gridView.setNumColumns(3);
				params = new LayoutParams(width / 3 - 3, width / 4);
				holder.imageView.setLayoutParams(params);
			}

			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		if (list.size()==0) {
			gridView.setVisibility(view.GONE);
		}else {
			MyApplication.bitmapUtils.display(holder.imageView, "http://"
				+ MyApplication.IP + ":8080/uploads/"
				+ list.get(position));// 为ImageView设置图片资源
		}
		return convertView;
	}

	public class viewHolder {
		ImageView imageView;
	}

}
