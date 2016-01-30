package com.lyy.adapter;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
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
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.activities.FriendsActivity;
import com.lyy.bean.Collections;
import com.lyy.bean.Essay_Collections;
import com.lyy.bean.MyFocus;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

public class MyFcous_Adapter extends BaseAdapter {
	List<MyFocus> list = new ArrayList<MyFocus>();
	Activity mcontext;
	LayoutInflater mInflater;
	ViewHolder holder = null;
	int index;
	String url = "http://" + MyApplication.IP + ":8080/MyProject/Link";
    
	public MyFcous_Adapter(List<MyFocus> list, Activity mcontext) {
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
		index = position;
		if (convertView == null) {
			// 获得ViewHolder对象
			holder = new ViewHolder();
			// 导入布局并赋值给convertview
			convertView = mInflater.inflate(R.layout.my_focus_item, null);
			holder.tv1 = (TextView) convertView.findViewById(R.id.user_names);
			holder.img = (ImageView) convertView.findViewById(R.id.user_heads);
			holder.button = (Button) convertView.findViewById(R.id.focu_cancel);
			// 为view设置标签
			convertView.setTag(holder);
		} else {
			// 取出holder
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv1.setText(list.get(position).getUsername());
		MyApplication.bitmapUtils.display(holder.img,
				"http://10.204.1.21:8080/upload/ic_head.png");
		holder.tv1.setOnClickListener(new Myclick(position));
		holder.img.setOnClickListener(new Myclick(position));
		holder.button.setOnClickListener(new Myclick(position));
		return convertView;
	}

	private void deleteFocus(int num) {
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "30");
		params.addBodyParameter("fc_id", list.get(num).getFc_id() + "");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String string = arg0.result;
				if (string.equals("success")) {
					MyMethod.showToast(mcontext, "删除成功");
				}
			}
		});
		list.remove(num);
		notifyDataSetChanged();
	}

	class Myclick implements OnClickListener{
        int pos;
		public Myclick(int pos) {
			super();
			this.pos = pos;
		}
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.user_names:
			case R.id.user_heads:
	            Intent intent = new Intent(mcontext,FriendsActivity.class);
	            Log.e("fr_id", index+"");
	            intent.putExtra("fu_id", list.get(pos).getFocu_id());
	            intent.putExtra("fu_name", list.get(pos).getUsername());
	            mcontext.startActivity(intent);
				break;
			case R.id.focu_cancel:
				deleteFocus(pos);
				break;

			default:
				break;
			}
		}
		
	}
}
