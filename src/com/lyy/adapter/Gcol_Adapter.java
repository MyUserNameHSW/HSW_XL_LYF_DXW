package com.lyy.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.bean.Collections;
import com.lyy.forums.adapter.Forums_GridViewAdapter.viewHolder;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;
import com.lyy.xl.activities.DetailActivity;

public class Gcol_Adapter extends BaseAdapter {
	List<Collections> list = new ArrayList<Collections>();
	Context mcontext;
	LayoutInflater mInflater;
	ViewHolder holder = null;
	String url = MyMethod.url;

	public Gcol_Adapter(List<Collections> list, Context mcontext) {
		super();
		this.list = list;
		this.mcontext = mcontext;
		mInflater = LayoutInflater.from(mcontext);
	}

	class ViewHolder {
		TextView tv1, tv2;
		ImageView img;
		Button button;
		RelativeLayout r1;
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
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			// 获得ViewHolder对象
			holder = new ViewHolder();
			// 导入布局并赋值给convertview
			convertView = mInflater.inflate(R.layout.gcol_item, null);
			holder.tv1 = (TextView) convertView
					.findViewById(R.id.gcol_item_gname);
			holder.tv2 = (TextView) convertView
					.findViewById(R.id.gcol_item_gprice);
			holder.img = (ImageView) convertView
					.findViewById(R.id.gcol_item_img);
			holder.button = (Button) convertView
					.findViewById(R.id.gcol_item_cancle);
			holder.r1 = (RelativeLayout) convertView.findViewById(R.id.gcol_r1);
			// 为view设置标签
			convertView.setTag(holder);
		} else {
			// 取出holder
			holder = (ViewHolder) convertView.getTag();
		}
		Log.e("p_name", list.get(position).toString());
		holder.tv1.setText(list.get(position).getP_name());
		holder.tv2.setText("单价" + list.get(position).getP_price() + "");
		MyApplication.bitmapUtils.display(holder.img,
				"http://10.204.1.21:8080/"+list.get(position).getImg());
		holder.button.setOnClickListener(new MyOnClick(position));
		holder.r1.setOnClickListener(new MyOnClick(position));
		holder.img.setOnClickListener(new MyOnClick(position));
		return convertView;
	}

	private class MyOnClick implements OnClickListener {
		int index;

		public MyOnClick(int index) {
			super();
			this.index = index;
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.gcol_item_cancle:
				HttpUtils utils = new HttpUtils();
				RequestParams params = new RequestParams();
				params.addBodyParameter("pc_id", list.get(index).getPc_id()
						+ "");
				Log.e("idspc", list.get(index).getPc_id() + "++");
				params.addBodyParameter("flag", "22");
				utils.send(HttpMethod.POST, url, params,
						new RequestCallBack<String>() {

							@Override
							public void onFailure(HttpException arg0,
									String arg1) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(ResponseInfo<String> arg0) {
								// TODO Auto-generated method stub
								String reString = arg0.result;
								if (reString.equals("success")) {
									MyMethod.showToast(mcontext, "删除成功");
									list.remove(index);
									notifyDataSetChanged();
								}
							}
						});

				break;
			case R.id.gcol_r1:
			case R.id.gcol_item_img:
				Bundle bundle = new Bundle();
				Intent intent2 = new Intent(mcontext, DetailActivity.class);
				bundle.putInt("p_id", list.get(index).getP_id());
				intent2.putExtras(bundle);
				mcontext.startActivity(intent2);
				break;
			default:
				break;
			}

		}

	}
}
