package com.lyy.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.bean.Shop_Collections;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;
import com.lyy.xl.activities.StoreActivity;

public class Scol_Adapter extends BaseAdapter {
	List<Shop_Collections> list = new ArrayList<Shop_Collections>();
	Context mcontext;
	LayoutInflater mInflater;
	ViewHolder holder = null;
	String url = MyMethod.url;

	public Scol_Adapter(List<Shop_Collections> list, Context mcontext) {
		super();
		this.list = list;
		this.mcontext = mcontext;
		mInflater = LayoutInflater.from(mcontext);
	}

	class ViewHolder {
		TextView tv1;
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
			convertView = mInflater.inflate(R.layout.scol_item, null);
			holder.tv1 = (TextView) convertView
					.findViewById(R.id.scol_item_gname);
			holder.button = (Button) convertView
					.findViewById(R.id.scol_item_cancle);
			holder.img = (ImageView) convertView
					.findViewById(R.id.scol_item_img);
			// 为view设置标签
			convertView.setTag(holder);
		} else {
			// 取出holder
			holder = (ViewHolder) convertView.getTag();
		}
		holder.button.setOnClickListener(new MyOnClick(position));
		holder.tv1.setOnClickListener(new MyOnClick(position));
		holder.img.setOnClickListener(new MyOnClick(position));
		holder.tv1.setText(list.get(position).getName());
		MyApplication.bitmapUtils.display(holder.img,
				"http://"+MyApplication.IP+":8080"+list.get(position).getImg());
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
			case R.id.scol_item_cancle:
				HttpUtils utils = new HttpUtils();
				RequestParams params = new RequestParams();
				params.addBodyParameter("sc_id", list.get(index).getPc_id() + "");
				Log.e("idspc", list.get(index).getPc_id()+"++");
				params.addBodyParameter("flag", "24");
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
           case R.id.scol_item_gname:
        	   case R.id.scol_item_img:
        		   Intent intent = new Intent(mcontext,StoreActivity.class);
        		   intent.putExtra("s_id", list.get(index).getS_id()+"");
        		   mcontext.startActivity(intent);
        		   break;
			default:
				break;
			}
			

		}

	}
}
