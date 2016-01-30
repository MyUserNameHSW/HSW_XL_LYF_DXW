package com.lyy.fragment;

import com.lyy.project.R;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class Care_TopFragment extends Fragment implements OnClickListener {
	TextView textView1, textView2;
	View view;
	Fragment fragment;
	FragmentManager manager;
	FragmentTransaction transaction;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.care_topbar, null);
		textView1 = (TextView) view.findViewById(R.id.my_text);
		textView2 = (TextView) view.findViewById(R.id.you_text);
		textView1.setOnClickListener(this);
		textView2.setOnClickListener(this);
		initData();
		return view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		manager = getFragmentManager();
		transaction = manager.beginTransaction();
		fragment = new YouCareFragment();
		transaction.replace(R.id.care_change, fragment);
		transaction.commit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.my_text:
            reset();
            textView1.setTextColor(this.getResources().getColor(R.color.themeColor));
			textView1.setBackgroundResource(R.drawable.baike_btn_pink_left_f_96);
			manager = getFragmentManager();
			transaction = manager.beginTransaction();
			fragment = new YouCareFragment();
			break;
		case R.id.you_text:
			reset();
            textView2.setTextColor(this.getResources().getColor(R.color.themeColor));
			textView2.setBackgroundResource(R.drawable.baike_btn_pink_right_f_96);
			manager = getFragmentManager();
			transaction = manager.beginTransaction();
			fragment = new MyCareFragment();
		default:
			break;
		}
		transaction.replace(R.id.care_change, fragment);
		transaction.commit();
	}
	private void reset() {
		textView1.setBackgroundResource(R.drawable.baike_btn_trans_left_f_96);
		textView2.setBackgroundResource(R.drawable.baike_btn_trans_right_f_96);
		textView1.setTextColor(this.getResources().getColor(R.color.white));
		textView2.setTextColor(this.getResources().getColor(R.color.white));
	}
}
