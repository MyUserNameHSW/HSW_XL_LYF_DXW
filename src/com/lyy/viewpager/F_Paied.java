package com.lyy.viewpager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.adapter.Fs1_Adapter;
import com.lyy.adapter.Fs3_Adapter;
import com.lyy.bean.MyForms;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class F_Paied extends Fragment {
	View view;
	ListView fs1_lv;
	int u_id;
	String IP = MyApplication.IP;
	String url = "http://" + IP + ":8080/MyProject/Link";
	List<MyForms> list;
	Fs3_Adapter mAdapter;
	private View view1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ViewGroup p = (ViewGroup) view.getParent();
		if (p != null) {
			p.removeAllViewsInLayout();
		}
		initView();
		initData();
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.form_sort3, (ViewGroup) getActivity()
				.findViewById(R.id.vPager), false);

	}

	private void initView() {
		// TODO Auto-generated method stub
		list = new ArrayList<MyForms>();
		view1 = view.findViewById(R.id.anim5);
		mAdapter = new Fs3_Adapter(getActivity(), list);
		fs1_lv = (ListView) view.findViewById(R.id.fs3_lv);
		fs1_lv.setAdapter(mAdapter);

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		mAdapter.notifyDataSetChanged();
		super.onResume();
	}

	private void initData() {
		// TODO Auto-generated method stub
		view1.setVisibility(View.VISIBLE);
		fs1_lv.setVisibility(View.GONE);
		u_id = MyMethod.GetU_id(getActivity());
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("u_id", u_id + "");
		params.addBodyParameter("state", "3");
		params.addBodyParameter("flag", "27");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				Log.e("result", result);
				Gson gson = new Gson();
				Type typeofType = new TypeToken<List<MyForms>>() {
				}.getType();
				List<MyForms> resultList = gson.fromJson(result, typeofType);
				list.addAll(resultList);
				fs1_lv.setVisibility(View.VISIBLE);
				view1.setVisibility(View.GONE);
				mAdapter.notifyDataSetChanged();
			}
		});
		
	}
}
