package com.lyy.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyy.activities.EditPProvideActivity;
import com.lyy.activities.LoginActivity;
import com.lyy.activities.MainActivity;
import com.lyy.activities.MySubmitActivity;
import com.lyy.activities.PMycareActivity;
import com.lyy.project.R;
import com.lyy.util.MyMethod;

public class MyCareFragment extends Fragment implements OnClickListener {
	View view;
	TextView tv1, tv2, tv3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.my_care, null);
		initView();
		return view;
	}

	private void initView() {
		// TODO Auto-generated method stub
		tv1 = (TextView) view.findViewById(R.id.mycare_tv1);
		tv2 = (TextView) view.findViewById(R.id.mycare_tv2);
		tv3 = (TextView) view.findViewById(R.id.mycare_tv3);
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tv3.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (MyMethod.GetU_id(getActivity()) == 0) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			MyMethod.showToast(getActivity(), "请先登录");
			startActivity(intent);
		} else {

			switch (arg0.getId()) {
			case R.id.mycare_tv1:
				MyMethod.showToast(getActivity(), "去提交");
				Intent intent = new Intent(getActivity(),
						EditPProvideActivity.class);
				startActivity(intent);
				break;
			case R.id.mycare_tv2:
				MyMethod.showToast(getActivity(), "已提交");
				Intent intent2 = new Intent(getActivity(),
						MySubmitActivity.class);
				startActivity(intent2);
				break;
			case R.id.mycare_tv3:
				MyMethod.showToast(getActivity(), "已代养");
				Intent intent3 = new Intent(getActivity(),
						PMycareActivity.class);
				startActivity(intent3);
				break;
			default:
				break;
			}

		}
	}
}
