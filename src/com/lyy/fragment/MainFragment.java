package com.lyy.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.lyy.project.R;

public class MainFragment extends Fragment implements OnClickListener {
	FragmentManager manager;
	FragmentTransaction transaction;
	ImageView iv1, iv2, iv3, iv4;
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.activity_main, null);
		initData();
		initTopBar();
		initBottom();
		initMiddle();
		return view;
	}

	private void initTopBar() {
		// TODO Auto-generated method stub
		transaction = manager.beginTransaction();
		Index_TopFragment fragment = new Index_TopFragment();
		transaction.replace(R.id.topBar, fragment);
		transaction.commit();
	}

	private void initMiddle() {
		// TODO Auto-generated method stub
		transaction = manager.beginTransaction();
		IndexFragment fragment = new IndexFragment();
		Bundle bundle = new Bundle();
		bundle.putString("title", "首页");
		fragment.setArguments(bundle);
		transaction.replace(R.id.middle_view, fragment);
		transaction.commit();
	}

	private void initBottom() {
		// TODO Auto-generated method stub
		iv1.setImageResource(R.drawable.lyy_icon_index_slt);
		iv2.setImageResource(R.drawable.lyy_icon_book);
		iv3.setImageResource(R.drawable.lyy_icon_care);
		iv4.setImageResource(R.drawable.lyy_icon_forum);
	}

	private void initData() {
		// TODO Auto-generated method stub
		manager = getFragmentManager();
		iv1 = (ImageView) view.findViewById(R.id.ic_bottom_index);
		iv2 = (ImageView) view.findViewById(R.id.ic_bottom_know);
		iv3 = (ImageView) view.findViewById(R.id.ic_bottom_care);
		iv4 = (ImageView) view.findViewById(R.id.ic_bottom_forum);

		iv1.setOnClickListener(this);
		iv2.setOnClickListener(this);
		iv3.setOnClickListener(this);
		iv4.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		transaction = manager.beginTransaction();
		switch (v.getId()) {
		case R.id.ic_bottom_index:
			replaceTopFragment(transaction, 1);
			replaceFragment(transaction, "首页", 1);
			reset();
			iv1.setImageResource(R.drawable.lyy_icon_index_slt);
			break;
		case R.id.ic_bottom_know:
			replaceTopFragment(transaction, 2);
			replaceFragment(transaction, "百科", 2);
			reset();
			iv2.setImageResource(R.drawable.lyy_icon_book_slt);
			break;
		case R.id.ic_bottom_care:
			replaceTopFragment(transaction, 3);
			replaceFragment(transaction, "代养", 3);
			reset();
			iv3.setImageResource(R.drawable.lyy_icon_care_slt);
			break;
		case R.id.ic_bottom_forum:
			replaceTopFragment(transaction, 4);
			replaceFragment(transaction, "论坛", 4);
			reset();
			iv4.setImageResource(R.drawable.lyy_icon_forum_slt);
			break;

		default:
			break;
		}
		transaction.commit();
	}

	private void reset() {
		iv1.setImageResource(R.drawable.lyy_icon_index);
		iv2.setImageResource(R.drawable.lyy_icon_book);
		iv3.setImageResource(R.drawable.lyy_icon_care);
		iv4.setImageResource(R.drawable.lyy_icon_forum);
	}

	private void replaceFragment(FragmentTransaction transaction, String title,
			int flag) {
		android.app.Fragment fragment = null;
		Bundle bundle;
		switch (flag) {
		case 1:
			fragment = new IndexFragment();
			break;
		case 2:
			fragment = new KnowFragment();
			break;
		case 3:
			fragment = new CareFragment();
			break;
		case 4:
			fragment = new ForumFragment();
			break;
		default:
			break;
		}
		bundle = new Bundle();
		bundle.putString("title", title);
		fragment.setArguments(bundle);
		transaction.replace(R.id.middle_view, fragment);
	}

	private void replaceTopFragment(FragmentTransaction transaction, int flag) {
		android.app.Fragment fragment = null;
		switch (flag) {
		case 1:
			fragment = new Index_TopFragment();
			break;
		case 2:
			fragment = new Know_TopFragment();
			break;
		case 3:
			fragment = new Care_TopFragment();
			break;
		case 4:
			fragment = new Forum_TopFragment();
			break;
		default:
			break;
		}
		transaction.replace(R.id.topBar, fragment);
	}
}
