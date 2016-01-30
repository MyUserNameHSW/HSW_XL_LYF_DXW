package com.lyy.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lyy.activities.LoginActivity;
import com.lyy.activities.MainActivity;
import com.lyy.forums.activities.Forum_MessageActivity;
import com.lyy.project.R;
import com.lyy.util.MyMethod;

public class Forum_TopFragment extends Fragment{
	
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	View view = inflater.inflate(R.layout.forum_topbar, null);
	ImageView imageView = (ImageView) view.findViewById(R.id.topBar_write_img);
	imageView.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (MyMethod.GetU_id(getActivity()) == 0) {
				Intent intent = new Intent(getActivity(),LoginActivity.class);
				MyMethod.showToast(getActivity(), "请先登录");
				startActivity(intent);
				
			}else {
				Intent intent = new Intent(getActivity(),Forum_MessageActivity.class);
				startActivity(intent);
			}
			
		}
	});
	return view;
}
}
