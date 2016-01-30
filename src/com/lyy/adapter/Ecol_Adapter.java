package com.lyy.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.bean.Essay_Collections;
import com.lyy.know.activities.KnowRenZhiProblemContentActivity;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

public class Ecol_Adapter extends BaseAdapter {
	List<Essay_Collections> list = new ArrayList<Essay_Collections>();
	Context mcontext;
	LayoutInflater mInflater;
	String url = MyMethod.url;
	ViewHolder holder = null;

	public Ecol_Adapter(List<Essay_Collections> list, Context mcontext) {
		super();
		this.list = list;
		this.mcontext = mcontext;
		mInflater = LayoutInflater.from(mcontext);
	}

	class ViewHolder {
		TextView tv1, tv2;
		ImageView img;
		Button button;
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
			convertView = mInflater.inflate(R.layout.ecol_item, null);
			holder.tv1 = (TextView) convertView
					.findViewById(R.id.ecol_item_gname);
			holder.button = (Button) convertView
					.findViewById(R.id.ecol_item_cancle);
			holder.img = (ImageView) convertView
					.findViewById(R.id.ecol_item_img);
			// 为view设置标签
			convertView.setTag(holder);
		} else {
			// 取出holder
			holder = (ViewHolder) convertView.getTag();
		}
		holder.img.setOnClickListener(new MyOnClick(position));
		holder.tv1.setOnClickListener(new MyOnClick(position));
		holder.button.setOnClickListener(new MyOnClick(position));
		holder.tv1.setText(list.get(position).getName());
		MyApplication.bitmapUtils.display(holder.img,
				"http://10.204.1.21:8080"+list.get(position).getSimg());
		return convertView;
	}
	class MyOnClick implements OnClickListener {
		int index;

		public MyOnClick(int index) {
			super();
			this.index = index;
		}
         
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.ecol_item_cancle:
				HttpUtils utils = new HttpUtils();
				RequestParams params = new RequestParams();
				params.addBodyParameter("kc_id", list.get(index).getCe_id() + "");
				params.addBodyParameter("flag", "26");
				utils.send(HttpMethod.POST, url, params,
						new RequestCallBack<String>() {

							@Override
							public void onFailure(HttpException arg0, String arg1) {
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
			case R.id.ecol_item_img:
			case R.id.ecol_item_gname:
				Intent intent = new Intent(mcontext,KnowRenZhiProblemContentActivity.class);
				int e_id = list.get(index).getE_id();
				String name = list.get(index).getName();
				String articl = list.get(index).getAricl();
				String image = list.get(index).getImg();
				intent.putExtra("E_id", e_id);
				intent.putExtra("name", name);
				intent.putExtra("artical", articl);
				intent.putExtra("image", image);
				mcontext.startActivity(intent);
				break;
			default:
				break;
			}
			
		}

	}
}
