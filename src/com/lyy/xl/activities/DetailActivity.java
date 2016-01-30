package com.lyy.xl.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.k;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.activities.LoginActivity;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;
import com.lyy.viewpager.BasicFragment;
import com.lyy.viewpager.DetailFragment;
import com.lyy.viewpager.EvaluationFragment;
import com.lyy.xl.bean.BasicBean;
import com.lyy.xl.bean.P_ColBean;
import com.lyy.xl.bean.ProductBean;
import com.lyy.xl.utils.BasicApplication;

public class DetailActivity extends FragmentActivity implements OnClickListener {
	String url = "http://" + MyApplication.IP + ":8080/MyProject/storeSevlet";
	String url2 = "http://" + MyApplication.IP + ":8080/MyProject/Link";
	ProductBean productBean;
	BasicBean basicBeans;
	List<BasicBean> list2;
	List<P_ColBean> list = new ArrayList<P_ColBean>();
	P_ColBean p_ColBean;
	private ViewPager mPager;// 页卡内容
	private List<Fragment> listViews; // Tab页面列表
	private ImageView cursor, back;// 动画图片
	private TextView t1, t2, t3;// 页卡头标
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	ImageView storeView, collectView;
	Button cartButton, buybButton;
	boolean flag = true;
	Dialog dialog;
	private final int ADDORREDUCE = 1;
	View view;
	int p_id;
	int u_id;
	private TextView basin, nobasin, pop_reduce, pop_add, pop_ok, pop_num;
	private ImageView pop_del;
	ImageView pop_picture;
	TextView pop_name, pop_price;
	// int p_id = DetailActivity.this.getIntent().getIntExtra("p_id", 1);
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				addData();
				break;

			default:
				break;
			}

		}

	};
	private String s_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xl_activity_main);
		u_id = MyMethod.GetU_id(getApplicationContext());
		initData();
		InitTextView();
		InitImageView();
		InitViewPager();
	}

	
private void storeId(int id){
	HttpUtils utils = new HttpUtils();
	RequestParams params = new RequestParams();
	params.addBodyParameter("flag", "50");
	Log.e("p_id", id+"id");
	params.addBodyParameter("p_id", id + "");
	utils.send(HttpMethod.POST, url2, params, new RequestCallBack<String>() {
		@Override
		public void onFailure(HttpException arg0, String arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ResponseInfo<String> arg0) {
			// TODO Auto-generated method stub
			String result = arg0.result;
			s_id = result;
			Log.e("activity11", s_id + "sssss");
		}
	});
}

	private void initData() {

		// 判断collection是否被收藏过，如果收藏过，显示
		Bundle bundle = new Bundle();
		Intent intent = getIntent();
		bundle = intent.getExtras();
		p_id = bundle.getInt("p_id");
		Log.e("activity", p_id + "ppppp");
		storeId(p_id);
		
		storeView = (ImageView) findViewById(R.id.store);
		collectView = (ImageView) findViewById(R.id.collect);
		cartButton = (Button) findViewById(R.id.cart);
		buybButton = (Button) findViewById(R.id.buy_immediately);
		getpanduan();
		storeView.setOnClickListener(this);
		collectView.setOnClickListener(this);
		cartButton.setOnClickListener(this);
		buybButton.setOnClickListener(this);

	}

	private void getpanduan() {

		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "12");
		params.addBodyParameter("p_id", p_id + "");
		Log.e("你", "网络时代1"+p_id);
		params.addBodyParameter("u_id", u_id + "");
		Log.e("你", "网络时代2"+u_id);
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e("你", "网络时代");
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				
				String result = arg0.result;
				Gson gson = new Gson();
				Type typeOfT = new TypeToken<List<P_ColBean>>() {
				}.getType();
				List<P_ColBean> resultList = gson.fromJson(result, typeOfT);
				Log.e("list", resultList.toString());
				list.addAll(resultList);
				if (list.size() != 0) {
					flag = true;
					collectView
							.setImageResource(R.drawable.lyy_already_colloection);
				} else {
					flag = false;
					collectView.setImageResource(R.drawable.lyy_collection);
				}

			}
		});
		// TODO Auto-generated method stub

	}

	private void InitTextView() {
		t1 = (TextView) findViewById(R.id.basic);
		t2 = (TextView) findViewById(R.id.detail);
		t3 = (TextView) findViewById(R.id.evaluation);
		t1.setTextColor(getApplicationContext().getResources()
				.getColor(R.color.themeColor));
		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		t3.setOnClickListener(new MyOnClickListener(2));
	}

	class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View arg0) {
			mPager.setCurrentItem(index);
		}
	}

	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		listViews = new ArrayList<Fragment>();
		BasicFragment basicFragment = new BasicFragment();
		DetailFragment detailFragment = new DetailFragment();
		EvaluationFragment evaluationFragment = new EvaluationFragment();
		listViews.add(basicFragment);
		listViews.add(detailFragment);
		listViews.add(evaluationFragment);
		Bundle bundle = new Bundle();
		bundle.putInt("p_id", p_id);
		for (int i = 0; i < listViews.size(); i++) {
			listViews.get(i).setArguments(bundle);
		}
		mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub

		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return listViews.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listViews.size();
		}

	}

	private void InitImageView() {
		back = (ImageView) findViewById(R.id.gdetail_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.lyy_little_line).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// 设置动画初始位置
	}

	class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				t1.setTextColor(getApplicationContext().getResources()
						.getColor(R.color.themeColor));
				t2.setTextColor(getApplicationContext().getResources()
						.getColor(R.color.black));
				t3.setTextColor(getApplicationContext().getResources()
						.getColor(R.color.black));
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				}
				break;
			case 1:
				t1.setTextColor(getApplicationContext().getResources()
						.getColor(R.color.black));
				t2.setTextColor(getApplicationContext().getResources()
						.getColor(R.color.themeColor));
				t3.setTextColor(getApplicationContext().getResources()
						.getColor(R.color.black));
				if (currIndex == 0) {

					animation = new TranslateAnimation(offset, one, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				}
				break;
			case 2:
				t1.setTextColor(getApplicationContext().getResources()
						.getColor(R.color.black));
				t2.setTextColor(getApplicationContext().getResources()
						.getColor(R.color.black));
				t3.setTextColor(getApplicationContext().getResources()
						.getColor(R.color.themeColor));
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	@Override
	public void onClick(View arg0) {

		Intent intent;

		switch (arg0.getId()) {
		case R.id.store:
			intent = new Intent(DetailActivity.this, StoreActivity.class);
			intent.putExtra("s_id", s_id);
			startActivity(intent);

			break;
		case R.id.collect:
			if (!flag) {
				collectView.setImageResource(R.drawable.lyy_collection);
				getcollect();
				flag = false;
			} else {

				collectView
						.setImageResource(R.drawable.lyy_already_colloection);
				removecollect();
				flag = true;
			}
			flag = !flag;
			break;
		case R.id.cart:
			showDialog();
			break;
		case R.id.buy_immediately:
			// showDialog();
			if (MyMethod.GetU_id(getApplicationContext()) == 0) {
				intent = new Intent(getApplicationContext(),
						LoginActivity.class);
				MyMethod.showToast(getApplicationContext(), "请先登录");
			} else {
				intent = new Intent(getApplicationContext(), BuyActivity.class);
				intent.putExtra("p_id", p_id);
			}
			startActivity(intent);
			break;
		case R.id.pop_reduce:
			if (!pop_num.getText().toString().equals("1")) {
				String num_reduce = Integer.valueOf(pop_num.getText()
						.toString()) - ADDORREDUCE + "";
				pop_num.setText(num_reduce);
			} else {
				Toast.makeText(getApplicationContext(), "购买数量不能低于1件",
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.pop_add:
			if (!pop_num.getText().toString().equals("750")) {
				String num_add = Integer.valueOf(pop_num.getText().toString())
						+ ADDORREDUCE + "";
				pop_num.setText(num_add);
			} else {
				Toast.makeText(getApplicationContext(), "不能超过最大产品数量",
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.pop_ok:
			
			String num = pop_num.getText().toString().trim();
			double prices = basicBeans.getPrice();
			
		    insertCart(prices,num);
			dialog.dismiss();
			break;
		case R.id.pop_del:
			dialog.dismiss();
			break;

		}
	}
    private void  insertCart(double MyPrice,String account){
    	HttpUtils utils = new HttpUtils();
    	RequestParams params = new RequestParams();
    	params.addBodyParameter("u_id",u_id+"");
    	params.addBodyParameter("p_id",p_id+"");
    	params.addBodyParameter("price",MyPrice+"");
    	params.addBodyParameter("account",account);
    	params.addBodyParameter("flag","52");
    	utils.send(HttpMethod.POST,url2, params	, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "加入到购物车成功",
						Toast.LENGTH_SHORT).show();
			}
		});
    }
	private void getcollect() {
		// TODO Auto-generated method stub

		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "10");
		params.addBodyParameter("p_id", p_id + "");
		params.addBodyParameter("u_id", u_id + "");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e("你失败了", "网络时代");
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;

				Toast.makeText(DetailActivity.this, result, 0).show();
				collectView
						.setImageResource(R.drawable.lyy_already_colloection);
				// flag=false;
			}
		});
	}

	private void removecollect() {
		// TODO Auto-generated method stub
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();

		params.addBodyParameter("flag", "11");
		params.addBodyParameter("p_id", p_id + "");
		params.addBodyParameter("u_id", u_id + "");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				Toast.makeText(DetailActivity.this, result, 0).show();
				collectView.setImageResource(R.drawable.lyy_collection);
				// flag=true;
			}
		});
	}

	private void showDialog() {
		view = getLayoutInflater().inflate(R.layout.popwindow, null);
		dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
		dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		Window window = dialog.getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = getWindowManager().getDefaultDisplay().getHeight();
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

		// 设置显示位置
		dialog.onWindowAttributesChanged(wl);
		// 设置点击外围解散
		dialog.setCanceledOnTouchOutside(true);

		dialog.show();

		initViews();
		initDatas();
	}

	private void addData() {
		// TODO Auto-generated method stub
		MyApplication.bitmapUtils.display(pop_picture, "http://"
				+ MyApplication.IP + ":8080" + basicBeans.getImg());
		pop_name.setText(basicBeans.getName());
		pop_price.setText(basicBeans.getPrice() + " 元");
	}

	private void initDatas() {
		// TODO Auto-generated method stub
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "4");
		params.addBodyParameter("p_id", p_id + "");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				Log.e("ssss", "aaaa");
				Gson gson = new Gson();
				Type typeOfT = new TypeToken<List<BasicBean>>() {
				}.getType();

				List<BasicBean> basicBean = gson.fromJson(result, typeOfT);
				basicBeans = basicBean.get(0);
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
				Log.e("dddd", basicBeans.toString());
			}
		});
	}

	private void initViews() {
		// TODO Auto-generated method stub
		pop_reduce = (TextView) view.findViewById(R.id.pop_reduce);
		pop_add = (TextView) view.findViewById(R.id.pop_add);
		pop_ok = (TextView) view.findViewById(R.id.pop_ok);
		pop_del = (ImageView) view.findViewById(R.id.pop_del);
		pop_num = (TextView) view.findViewById(R.id.pop_num);
		pop_picture = (ImageView) view.findViewById(R.id.pop_picture);
		pop_name = (TextView) view.findViewById(R.id.pop_name);
		pop_price = (TextView) view.findViewById(R.id.pop_price);

		//basin.setOnClickListener(this);
		//nobasin.setOnClickListener(this);
		pop_reduce.setOnClickListener(this);
		pop_add.setOnClickListener(this);
		pop_ok.setOnClickListener(this);
		pop_del.setOnClickListener(this);
		pop_num.setOnClickListener(this);

	}

}
