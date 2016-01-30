package com.lyy.viewpager;

import java.lang.reflect.Type;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.xl.bean.BasicBean;
import com.lyy.xl.utils.BasicApplication;

public class DetailFragment extends Fragment {
	String url = "http://" + MyApplication.IP + ":8080/MyProject/storeSevlet";
	BasicBean bean;
	TextView pz, gn, zwlb;
	ImageView imageView, imageView2;
	View view;
	HttpUtils httpUtils;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				addData();
				break;

			default:
				break;
			}

		}

	};
	private int p_id;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.goods_detail, null);
		p_id = getArguments().getInt("p_id");
		Log.e("myP-Id", p_id+"");
		initView();
		initData();
		return view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		httpUtils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "4");
		params.addBodyParameter("p_id", p_id+"");
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						Log.e("error", "you faile");
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						String result = arg0.result;
						Gson gson = new Gson();
						Type typeOfT = new TypeToken<List<BasicBean>>() {
						}.getType();
						List<BasicBean> basicBean = gson.fromJson(result,
								typeOfT);
						bean = basicBean.get(0);
						Message msg = new Message();
						msg.what = 1;
						handler.sendMessage(msg);
						Log.e("dddd", bean.toString());

					}
				});
	};

	private void addData() {
		// TODO Auto-generated method stub
		pz.setText(bean.getName());
		gn.setText(bean.getDepict());
		zwlb.setText(bean.getType());
		MyApplication.bitmapUtils.display(imageView, "http://"
				+ MyApplication.IP + ":8080" + bean.getImg());
		MyApplication.bitmapUtils.display(imageView2, "http://"
				+ MyApplication.IP + ":8080" + bean.getPhone());
	}

	private void initView() {
		// TODO Auto-generated method stub
		pz = (TextView) view.findViewById(R.id.pz);
		gn = (TextView) view.findViewById(R.id.gn);
		zwlb = (TextView) view.findViewById(R.id.zwlb);
		imageView = (ImageView) view.findViewById(R.id.detail_image);
		imageView2 = (ImageView) view.findViewById(R.id.detail_image2);
	}
}
