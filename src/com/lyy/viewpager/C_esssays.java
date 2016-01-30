package com.lyy.viewpager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.adapter.Ecol_Adapter;
import com.lyy.adapter.Gcol_Adapter;
import com.lyy.adapter.Scol_Adapter;
import com.lyy.bean.Collections;
import com.lyy.bean.Essay_Collections;
import com.lyy.bean.Shop_Collections;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

public class C_esssays extends Fragment {
	View view;
	int nowItem;
	ListView listView;
	List<Essay_Collections> arrayList;
	CheckBox cb;
	Ecol_Adapter mAdapter;
	Dialog dialog;
	String IP = MyApplication.IP;
	String url = "http://" + IP + ":8080/MyProject/Link";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup p = (ViewGroup) view.getParent();
		if (p != null) {
			p.removeAllViewsInLayout();
		}
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.collect_essay,
				(ViewGroup) getActivity().findViewById(R.id.vPager), false);
		initView();
		initData();

	}

	private void initData() {
		// TODO Auto-generated method stub
		arrayList = new ArrayList<Essay_Collections>();
		String url = MyMethod.url;
		int u_id = MyMethod.GetU_id(getActivity());
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "25");
		params.addBodyParameter("u_id", u_id + "");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				Log.e("MyResult", result);
				Gson gson = new Gson();
				Type typeOfT = new TypeToken<List<Essay_Collections>>() {
				}.getType();
				List<Essay_Collections> resultList = gson.fromJson(result,
						typeOfT);
				arrayList.addAll(resultList);
				dataChanged();
			}
		});
		mAdapter = new Ecol_Adapter(arrayList, getActivity());
		listView.setAdapter(mAdapter);
		dataChanged();
	}

	private void initView() {
		// TODO Auto-generated method stub
		listView = (ListView) view.findViewById(R.id.ecol_listview);
	}

	private void dataChanged() {
		// 通知listView刷新
		mAdapter.notifyDataSetChanged();
		// TextView显示最新的选中数目
		// tv_show.setText("已选中" + checkNum + "项");
	}

	
}