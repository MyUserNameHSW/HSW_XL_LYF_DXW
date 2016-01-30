package com.lyy.viewpager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
import com.lyy.xl.adapter.AssessAdapter;
import com.lyy.xl.bean.AssessBean;

public class EvaluationFragment extends Fragment {
	String url = "http://"+MyApplication.IP+":8080/MyProject/storeSevlet";
	List<AssessBean> listab ;
	ListView listView;
	AssessAdapter adapter;
	View view;
    private int p_id;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.evaluation, null);
		p_id = getArguments().getInt("p_id");
		initview();
		initData();
		return view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "5");
		params.addBodyParameter("p_id", p_id+"");
		utils.send(HttpMethod.POST,
				url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						Log.e("you faile", "faile");
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						String result = arg0.result;
						// System.out.println(result);
						Log.e("success", "you success");
						Gson gson = new Gson();
						Type typeOfT = new TypeToken<List<AssessBean>>() {
						}.getType();
						List<AssessBean> resultList = gson.fromJson(result,
								typeOfT);
						listab.addAll(resultList);
						
						Log.e("xu", listab.toString());
						adapter.notifyDataSetChanged();
					}
				});

	}

	private void initview() {
		// TODO Auto-generated method stub
		listab=new ArrayList<AssessBean>();
		listView = (ListView) view.findViewById(R.id.list_evaluation);
		adapter=new AssessAdapter(listab, getActivity());
		listView.setAdapter(adapter);
	}

}
