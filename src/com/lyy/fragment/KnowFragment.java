package com.lyy.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.bean.Esort1Bean;
import com.lyy.know.activities.KnowProblemActivity;
import com.lyy.know.activities.KnowRenZhiActivity;
import com.lyy.know.activities.KnowSearchActivity;
import com.lyy.know.adapter.KnowEsort1_2Adapter;
import com.lyy.project.R;
import com.lyy.util.MyApplication;

public class KnowFragment extends Fragment {
	EditText editText;
	Button button;
	ListView listView1, listView2;
	List<Esort1Bean> list = new ArrayList<Esort1Bean>();
	List<Esort1Bean> list1 = new ArrayList<Esort1Bean>();
	KnowEsort1_2Adapter adapter, adapter1;
	HttpUtils httpUtils, httpUtils1;
	private String url, url1;
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.konw_middle, null);
		initViews();
		initDatas();
		return view;
	}

	private void initDatas() {

		httpUtils = new HttpUtils();
		url = "http://" + MyApplication.IP
				+ ":8080/MyProject/BaikeServlet?key=4";
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				try {
					JSONArray array = new JSONArray(result);
					for (int i = 0; i < array.length(); i++) {
						JSONObject object = array.getJSONObject(i);
						int id = object.getInt("es1_id");
						String name = object.getString("es1_name");
						int type = object.getInt("type");
						Esort1Bean esort1Bean = new Esort1Bean(id, name, type);
						list.add(esort1Bean);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				adapter.notifyDataSetChanged();
			}
		});
		httpUtils1 = new HttpUtils();
		url1 = "http://" + MyApplication.IP
				+ ":8080/MyProject/BaikeServlet?key=5";
		httpUtils1.send(HttpMethod.GET, url1, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				try {
					JSONArray array = new JSONArray(result);
					for (int i = 0; i < array.length(); i++) {
						JSONObject object = array.getJSONObject(i);
						int id = object.getInt("es1_id");
						String name = object.getString("es1_name");
						int type = object.getInt("type");
						Esort1Bean esort1Bean = new Esort1Bean(id, name, type);
						list1.add(esort1Bean);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				adapter1.notifyDataSetChanged();
			}
		});
	}

	private void initViews() {
		listView1 = (ListView) view.findViewById(R.id.bk_listview1);
		listView2 = (ListView) view.findViewById(R.id.bk_listview2);
		// 搜索
		editText = (EditText) view.findViewById(R.id.bk_searchText);
		button = (Button) view.findViewById(R.id.bk_button);

		adapter = new KnowEsort1_2Adapter(getActivity(), list);
		adapter1 = new KnowEsort1_2Adapter(getActivity(), list1);
		listView1.setAdapter(adapter);
		listView2.setAdapter(adapter1);
		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), KnowRenZhiActivity.class);
				Esort1Bean esort1Bean = list.get(position);
				int Es1_id = esort1Bean.getEs1_id();
				intent.putExtra("es1_id", Es1_id);
				startActivity(intent);
			}
		});
		listView2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), KnowProblemActivity.class);
				Esort1Bean esort1Bean = list.get(position);
				int Es1_id = esort1Bean.getEs1_id();
				intent.putExtra("es1_id", (Es1_id + 3));
				startActivity(intent);
			}
		});
		// 搜索
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				String searchtext = editText.getText().toString().trim();
				Log.e("seachText", searchtext);
				if (searchtext.equals("")) {
					Toast.makeText(getActivity(), "请输入你要搜索的内容",
							Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent();
					intent.setClass(getActivity(), KnowSearchActivity.class);
					intent.putExtra("name", searchtext);
					startActivity(intent);
				}
			}
		});
	}
}
