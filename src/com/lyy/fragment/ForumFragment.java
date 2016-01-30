package com.lyy.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.lyy.bean.Forum;
import com.lyy.forums.activities.ForumDetailActivity;
import com.lyy.forums.adapter.ForumAdapter;
import com.lyy.forums.util.ForumListView;
import com.lyy.forums.util.ForumListView.IXListViewListener;
import com.lyy.project.R;
import com.lyy.util.MyApplication;

public class ForumFragment extends Fragment implements IXListViewListener {
	private final String TAG = "XListViewActivity";
	private Handler mHandler;
	private ForumListView listView;
	private List<Forum> list = new ArrayList<Forum>();
	private ForumAdapter adapter;
	private MyApplication application;
	String url1 = "http://" + MyApplication.IP
			+ ":8080/MyProject/Check?action=8&pageNow=1";
	private HttpUtils httpUtils;
	private int number = 1;
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.forum_middle, null);
		initView();
		// 从服务器端下载数据
		
		downloadData(url1);
		return view;
	}

	@SuppressWarnings("static-access")
	private void downloadData(String url) {
		httpUtils = new HttpUtils();
		HttpMethod method = HttpMethod.GET;
		RequestParams params = new RequestParams();
		httpUtils.send(method, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// 获取服务器端返回的数据结果
				String result = arg0.result;
				// 使用GSon框架进行json解析
				Gson gson = new Gson();
				Type typeOfT = new TypeToken<List<Forum>>() {
				}.getType();

				List<Forum> list2 = gson.fromJson(result, typeOfT);
				list.addAll(list2);
				// 刷新界面
				adapter.notifyDataSetChanged();

			}
		});

	}

	private void initView() {
		listView = (ForumListView) view.findViewById(R.id.listView);
		listView.setPullLoadEnable(true);
		adapter = new ForumAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		listView.setXListViewListener(this);
		mHandler = new Handler();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@SuppressWarnings("unused")
			private int item;
			@SuppressWarnings("unused")
			private TextView articlTextView;
			@SuppressWarnings("unused")
			private ImageView favourImageView, xialaImageView;

			// 单击事件，单击每个item跳转
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {

				Intent intent = new Intent(getActivity(),
						ForumDetailActivity.class);
				// 获取单击的帖子的id，发帖人的名称，头像以及内容等
				Forum forum = list.get(arg2 - 1);
				int f_id = forum.getF_id();
				// 为intent绑定数据传递给ForumItemActivity
				intent.putExtra("f_id", f_id);

				String username = forum.getUsername();
				String userimg = forum.getUserimg();
				int U_id = forum.getU_id();
				String f_time = forum.getF_time();
				String articl = forum.getArticl();
				String theme = forum.getTheme();
				int praise = forum.getPraise();

				intent.putExtra("u_id", U_id);
				intent.putExtra("username", username);
				intent.putExtra("userimg", userimg);
				intent.putExtra("f_time", f_time);
				intent.putExtra("articl", articl);
				intent.putExtra("theme", theme);
				intent.putExtra("praise", praise);

				startActivity(intent);

			}
		});

	}

	public void onRefresh() {
		Log.i(TAG, "刷新最新");
		mHandler.postDelayed(new Runnable() {
			public void run() {
				list.clear();
				downloadData(url1);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);

	}

	public void onLoadMore() {
		Log.i(TAG, "加载更多");
		mHandler.postDelayed(new Runnable() {
			public void run() {
				number++;
				// 加载数据
				String url2 = "http://" + MyApplication.IP
						+ ":8080/MyProject/Check?action=8&pageNow=" + number;
				downloadData(url2);
				adapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime("刚刚");
	}

}
