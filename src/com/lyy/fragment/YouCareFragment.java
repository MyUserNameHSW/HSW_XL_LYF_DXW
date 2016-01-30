package com.lyy.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.activities.CareShopActivity;
import com.lyy.activities.PersonCareActivity;
import com.lyy.activities.YouCareList2Activity;
import com.lyy.activities.YouCareListActivity;
import com.lyy.bean.P_provide;
import com.lyy.bean.S_provide;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

public class YouCareFragment extends Fragment implements OnClickListener {
	ImageView s_iv1, s_iv2, s_iv3, p_iv1, p_iv2, p_iv3, shopCare, personCare;
	TextView s_tv1_1, s_tv1_2, s_tv2_1, s_tv2_2, s_tv3_1, s_tv3_2, p_tv1_1,
			p_tv1_2, p_tv2_1, p_tv2_2, p_tv3_1, p_tv3_2;
	LinearLayout lin1,lin2,lin3,lin4,lin5,lin6;
	List<ImageView> ivList = new ArrayList<ImageView>();
	List<TextView> tvList = new ArrayList<TextView>();;
	List<S_provide> sList;
	List<P_provide> pList;
	private View view;
	String url = "http://" + MyApplication.IP + ":8080/MyProject/Link";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.you_care, null);
		initView();
		initPData();
		initSData();
		
		return view;
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				addSData();
				break;
			case 1:
				addPData();
				break;
			default:
				break;
			}
		};
	};

	private void initSData() {
		// TODO Auto-generated method stub
		sList = new ArrayList<S_provide>();
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "33");
		params.addBodyParameter("rec", "1");
		params.addBodyParameter("pageNum", "1");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				
				Gson gson = new Gson();
				Type typeofType = new TypeToken<List<S_provide>>() {
				}.getType();
				List<S_provide> resultList = gson.fromJson(result, typeofType);
				sList.addAll(resultList);
				Message msg = new Message();
				msg.what = 0;
				handler.sendMessage(msg);
			}
		});
	}

	private void initPData() {
		// TODO Auto-generated method stub
		pList = new ArrayList<P_provide>();
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "34");
		params.addBodyParameter("rec", "1");
		params.addBodyParameter("pageNum", "1");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
			} 

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				Log.e("sss", result+"+++");
				Gson gson = new Gson();
				Type typeofType = new TypeToken<List<P_provide>>() {
				}.getType();
				List<P_provide> resultList = gson.fromJson(result, typeofType);
				pList.addAll(resultList);
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		s_iv1 = (ImageView) view.findViewById(R.id.shop_care_img1);
		s_iv2 = (ImageView) view.findViewById(R.id.shop_care_img2);
		s_iv3 = (ImageView) view.findViewById(R.id.shop_care_img3);
		p_iv1 = (ImageView) view.findViewById(R.id.person_care_img1);
		p_iv2 = (ImageView) view.findViewById(R.id.person_care_img2);
		p_iv3 = (ImageView) view.findViewById(R.id.person_care_img3);
		s_tv1_1 = (TextView) view.findViewById(R.id.shop_care_name1);
		s_tv2_1 = (TextView) view.findViewById(R.id.shop_care_name2);
		s_tv3_1 = (TextView) view.findViewById(R.id.shop_care_name3);
		s_tv1_2 = (TextView) view.findViewById(R.id.shop_care_script1);
		s_tv2_2 = (TextView) view.findViewById(R.id.shop_care_script2);
		s_tv3_2 = (TextView) view.findViewById(R.id.shop_care_script3);
		p_tv1_1 = (TextView) view.findViewById(R.id.person_care_name1);
		p_tv2_1 = (TextView) view.findViewById(R.id.person_care_name2);
		p_tv3_1 = (TextView) view.findViewById(R.id.person_care_name3);
		p_tv1_2 = (TextView) view.findViewById(R.id.person_care_script1);
		p_tv2_2 = (TextView) view.findViewById(R.id.person_care_script2);
		p_tv3_2 = (TextView) view.findViewById(R.id.person_care_script3);

		ivList.add(s_iv1);
		ivList.add(s_iv2);
		ivList.add(s_iv3);
		ivList.add(p_iv1);
		ivList.add(p_iv2);
		ivList.add(p_iv3);

		tvList.add(s_tv1_1);
		tvList.add(s_tv2_1);
		tvList.add(s_tv3_1);
		tvList.add(s_tv1_2);
		tvList.add(s_tv2_2);
		tvList.add(s_tv3_2);
		tvList.add(p_tv1_1);
		tvList.add(p_tv2_1);
		tvList.add(p_tv3_1);
		tvList.add(p_tv1_2);
		tvList.add(p_tv2_2);
		tvList.add(p_tv3_2);
		personCare = (ImageView) view.findViewById(R.id.personcare);
		shopCare = (ImageView) view.findViewById(R.id.shopcare);
		personCare.setOnClickListener(this);
		shopCare.setOnClickListener(this);
		lin1 = (LinearLayout) view.findViewById(R.id.lin1);
		lin2 = (LinearLayout) view.findViewById(R.id.lin2);
		lin3 = (LinearLayout) view.findViewById(R.id.lin3);
		lin4 = (LinearLayout) view.findViewById(R.id.lin4);
		lin5 = (LinearLayout) view.findViewById(R.id.lin5);
		lin6 = (LinearLayout) view.findViewById(R.id.lin6);
		lin1.setOnClickListener(this);
		lin2.setOnClickListener(this);
		lin3.setOnClickListener(this);
		lin4.setOnClickListener(this);
		lin5.setOnClickListener(this);
		lin6.setOnClickListener(this);
	}

	private void addSData() {
		for (int i = 0; i < sList.size(); i++) {
			MyApplication.bitmapUtils.display(ivList.get(i), "http://"
					+ MyApplication.IP + ":8080"+sList.get(i).getLogo());
			tvList.get(i).setText(sList.get(i).getName());
			tvList.get(i + 3).setText(sList.get(i).getSctipt());
		}
	}

	private void addPData() {

		for (int i = 0; i < pList.size(); i++) {
			MyApplication.bitmapUtils.display(ivList.get(i + 3), "http://"
					+ MyApplication.IP + ":8080"+pList.get(i).getHead());
			tvList.get(i + 6).setText(pList.get(i).getName());
			tvList.get(i + 9).setText(pList.get(i).getExprience());
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (arg0.getId()) {
		case R.id.personcare:
			intent = new Intent(getActivity(), YouCareListActivity.class);
			startActivity(intent);
			break;
		case R.id.shopcare:
			intent = new Intent(getActivity(), YouCareList2Activity.class);
			startActivity(intent);
			break;
		case R.id.lin1:
		    intent = new Intent(getActivity(), CareShopActivity.class);
			intent.putExtra("sp_id", sList.get(0).getSp_id());
			startActivity(intent);
			break;
		case R.id.lin2:
		    intent = new Intent(getActivity(), CareShopActivity.class);
			intent.putExtra("sp_id", sList.get(1).getSp_id());
			startActivity(intent);
			break;
		case R.id.lin3:
		    intent = new Intent(getActivity(), CareShopActivity.class);
			intent.putExtra("sp_id", sList.get(2).getSp_id());
			MyMethod.showToast(getActivity(), sList.get(2).getSp_id()+"");
			startActivity(intent);
			break;
		case R.id.lin4:
		    intent = new Intent(getActivity(), PersonCareActivity.class);
			intent.putExtra("pp_id", pList.get(0).getPp_id());
			startActivity(intent);
			break;
		case R.id.lin5:
		    intent = new Intent(getActivity(), PersonCareActivity.class);
			intent.putExtra("pp_id", pList.get(1).getPp_id());
			startActivity(intent);
			break;
		case R.id.lin6:
		    intent = new Intent(getActivity(), PersonCareActivity.class);
			intent.putExtra("pp_id", pList.get(2).getPp_id());
			startActivity(intent);
			break;
		default:
			break;
		}
		
	}
}
