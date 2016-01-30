package com.lyy.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyy.activities.FormsActivity;
import com.lyy.activities.LoginActivity;
import com.lyy.activities.MainActivity;
import com.lyy.activities.MyCareActivity;
import com.lyy.activities.MyCartActivity;
import com.lyy.activities.MyCollectActivity;
import com.lyy.activities.MyFocusActivity;
import com.lyy.activities.SetActivity;
import com.lyy.activities.UserInfoActivity;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

public class MenuFragment extends Fragment implements OnClickListener {
	ImageView iv1;
	TextView tv1, tv2;
	View view;
	Intent intent;
	private TextView tv3;
	private TextView tv4;
	private TextView tv5;
	private TextView tv6;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_menu, null);
		initView();
		initData();
		return view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		int u_id = MyMethod.GetU_id(getActivity());
		String s = MyMethod.Get_UserName_ById(u_id);
		tv1.setText(u_id + "");

	}

	private void initView() {
		// TODO Auto-generated method stub
		iv1 = (ImageView) view.findViewById(R.id.one);
		tv1 = (TextView) view.findViewById(R.id.tv1);
		tv2 = (TextView) view.findViewById(R.id.ce_forms);
		tv3 = (TextView) view.findViewById(R.id.ce_mycare);
		tv4 = (TextView) view.findViewById(R.id.ce_myfocus);
		tv5 = (TextView) view.findViewById(R.id.ce_mycollect);
		tv6 = (TextView) view.findViewById(R.id.ce_set);
		iv1.setOnClickListener(this);
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tv3.setOnClickListener(this);
		tv4.setOnClickListener(this);
		tv5.setOnClickListener(this);
		tv6.setOnClickListener(this);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		int u_id = MyMethod.GetU_id(getActivity());
		tv1.setText(MyMethod.Get_UserName_ById(u_id));
		Log.e("onResume", MyMethod.Get_UserName_ById(u_id));
		MyApplication.bitmapUtils.display(iv1, MyMethod.headImg(u_id));
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (MyMethod.GetU_id(getActivity()) == 0) {
			Intent intent = new Intent(getActivity(), MainActivity.class);
			MyMethod.showToast(getActivity(), "请先登录");
			startActivity(intent);
//			return;
		}
		int id = view.getId();
		if (id == R.id.one) {
			if (MyMethod.GetU_id(getActivity()) == 0) {
				intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			} else {
				intent = new Intent(getActivity(), UserInfoActivity.class);
				startActivity(intent);
			}
		} else if (id == R.id.tv1) {
			if (MyMethod.GetU_id(getActivity()) == 0) {
				intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			} else {
				intent = new Intent(getActivity(), UserInfoActivity.class);
				startActivity(intent);
			}

		}
		else {
			if (MyMethod.GetU_id(getActivity()) == 0) {
				intent = new Intent(getActivity(), LoginActivity.class);
				MyMethod.showToast(getActivity(), "请先登录");
				startActivity(intent);
				}else {
					if (id == R.id.ce_forms) {
						intent = new Intent(getActivity(), FormsActivity.class);
						startActivity(intent);
					} else if (id == R.id.ce_mycare) {
						intent = new Intent(getActivity(), MyCareActivity.class);
						startActivity(intent);
					} else if (id == R.id.ce_myfocus) {
						intent = new Intent(getActivity(), MyFocusActivity.class);
						startActivity(intent);
					} else if (id == R.id.ce_mycollect) {
						intent = new Intent(getActivity(), MyCollectActivity.class);
						startActivity(intent);
					} else if (id == R.id.ce_set) {
						intent = new Intent(getActivity(), MyCartActivity.class);
						startActivity(intent);
					}
				}
		}/*else */
	}
}
