package com.lyy.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.image.SmartImageView;
import com.lyy.activities.ProductActivity;
import com.lyy.activities.Type1_1Activity;
import com.lyy.bean.GoodsImgs;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.xl.activities.DetailActivity;

public class IndexFragment extends Fragment implements OnClickListener {
	String IP = MyApplication.IP;
	View view, view2;
	List<GoodsImgs> list1, list2;
	List<SmartImageView> list3, list4;
	ImageView Imiddle_more1, Imiddle_more2, Imiddle_more3, Imiddle_more4,
			Imiddle_img1, Imiddle_img2, Imiddle_img3, Imiddle_img4,searchSort;
	SmartImageView watch_big, watch_small1, watch_small2, duorou_big,
			duorou_small1, duorou_small2, smallp_big, smallp_small1,
			smallp_small2, tools_big, tools_small1, tools_small2;
	Bundle bundle;
	Intent intent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.index_middle, null);
		view2 = inflater.inflate(R.layout.layout_slideshow, null);
		initData();
		downLoadBigImg();
		downLoadSmallImg();
		return view;
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				insertSmallImg();
				break;
			case 2:
				insertBigImg();
				break;
			default:
				break;
			}
		}

		private void insertBigImg() {
			// TODO Auto-generated method stub
			for (int i = 0; i < list2.size(); i++) {
				final int p_id = list2.get(i).getP_id();
				list4.get(i).setImageUrl(list2.get(i).getImgUrl(),
						R.drawable.smallimage_fail, R.drawable.smallimage_load);
				list4.get(i).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						bundle = new Bundle();
						intent = new Intent(getActivity(),
								DetailActivity.class);
						bundle.putInt("p_id", p_id);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});
			}
		}

		private void insertSmallImg() {
			// TODO Auto-generated method stub
			for (int i = 0; i < list1.size(); i++) {
				final int p_id = list1.get(i).getP_id();
				list3.get(i).setImageUrl(list1.get(i).getImgUrl(),
						R.drawable.smallimage_fail, R.drawable.smallimage_load);
				list3.get(i).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						bundle = new Bundle();
						intent = new Intent(getActivity(),
								DetailActivity.class);
						bundle.putInt("p_id", p_id);
						Log.e("jejjej", p_id+"");
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});
			}
		};
	};

	private void initData() {
		// TODO Auto-generated method stub
		list1 = new ArrayList<GoodsImgs>();
		list2 = new ArrayList<GoodsImgs>();
		list3 = new ArrayList<SmartImageView>();
		list4 = new ArrayList<SmartImageView>();
		Imiddle_img1 = (ImageView) view.findViewById(R.id.Imiddle_img1);
		Imiddle_img2 = (ImageView) view.findViewById(R.id.Imiddle_img2);
		Imiddle_img3 = (ImageView) view.findViewById(R.id.Imiddle_img3);
		Imiddle_img4 = (ImageView) view.findViewById(R.id.Imiddle_img4);
		Imiddle_img1.setOnClickListener(this);
		Imiddle_img2.setOnClickListener(this);
		Imiddle_img3.setOnClickListener(this);
		Imiddle_img4.setOnClickListener(this);

		watch_big = (SmartImageView) view.findViewById(R.id.watch_big);
		watch_small1 = (SmartImageView) view.findViewById(R.id.watch_small1);
		watch_small2 = (SmartImageView) view.findViewById(R.id.watch_small2);

		smallp_big = (SmartImageView) view.findViewById(R.id.smallp_big);
		smallp_small1 = (SmartImageView) view.findViewById(R.id.smallp_small1);
		smallp_small2 = (SmartImageView) view.findViewById(R.id.smallp_small2);

		duorou_big = (SmartImageView) view.findViewById(R.id.duorou_big);
		duorou_small1 = (SmartImageView) view.findViewById(R.id.duorou_small1);
		duorou_small2 = (SmartImageView) view.findViewById(R.id.duorou_small2);

		tools_big = (SmartImageView) view.findViewById(R.id.tools_big);
		tools_small1 = (SmartImageView) view.findViewById(R.id.tools_small1);
		tools_small2 = (SmartImageView) view.findViewById(R.id.tools_small2);
		list3.add(watch_small1);
		list3.add(watch_small2);
		list3.add(duorou_small1);
		list3.add(duorou_small2);
		list3.add(smallp_small1);
		list3.add(smallp_small2);
		list3.add(tools_small1);
		list3.add(tools_small2);
		list4.add(watch_big);
		list4.add(duorou_big);
		list4.add(smallp_big);
		list4.add(tools_big);
	}

	private void downLoadSmallImg() {
		// TODO Auto-generated method stub
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + IP + ":8080/MyProject/Link";
		RequestParams params = new RequestParams();
		params.put("flag", 5);
		client.post(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);

				for (int i = 0; i < response.length(); i++) {
					try {
						int p_id = response.getJSONObject(i).getInt("p_id");
						String img = "http://" + IP + ":8080"
								+ response.getJSONObject(i).getString("img");
						GoodsImgs imgs = new GoodsImgs(p_id, img);
						list1.add(imgs);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse) {
				// TODO Auto-generated method stub
				Log.e("shiba", "shibai");
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
	}

	private void downLoadBigImg() {
		// TODO Auto-generated method stub
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + IP + ":8080/MyProject/Link";
		RequestParams params = new RequestParams();
		params.put("flag", 4);
		client.post(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				for (int i = 0; i < response.length(); i++) {
					try {
						int p_id = response.getJSONObject(i).getInt("p_id");
						String img = "http://" + IP + ":8080"
								+ response.getJSONObject(i).getString("img");
						GoodsImgs imgs = new GoodsImgs(p_id, img);
						list2.add(imgs);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Message msg = new Message();
				msg.what = 2;
				handler.sendMessage(msg);

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		bundle = new Bundle();
		intent = new Intent(getActivity(), Type1_1Activity.class);
		switch (id) {
		case R.id.Imiddle_img1:
			bundle.putInt("params", 2);
			bundle.putString("types", "热门推荐");
			break;
		case R.id.Imiddle_img2:
			bundle.putInt("params", 2);
			bundle.putString("types", "节日送礼");
			break;
		case R.id.Imiddle_img3:
			bundle.putInt("params", 2);
			bundle.putString("types", "办公养眼");
			break;
		case R.id.Imiddle_img4:
			bundle.putInt("params", 2);
			bundle.putString("types", "种植工具");
			break;
		default:
			break;
		}
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
