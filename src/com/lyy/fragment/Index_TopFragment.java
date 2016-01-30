package com.lyy.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lyy.activities.PSearchActivity;
import com.lyy.project.R;

public class Index_TopFragment extends Fragment {
	View view;
	ImageView searchSort;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.index_topbar, null);
		searchSort = (ImageView) view.findViewById(R.id.ItopBar_search_img);
		searchSort.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), PSearchActivity.class);
				startActivity(intent);
			}
		});
		return view;
	}
}
