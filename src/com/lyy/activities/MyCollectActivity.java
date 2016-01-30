package com.lyy.activities;

import java.util.ArrayList;
import java.util.List;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.viewpager.C_esssays;
import com.lyy.viewpager.C_goods;
import com.lyy.viewpager.C_shops;

public class MyCollectActivity extends FragmentActivity {
	private ViewPager mPager;// 页卡内容
	private List<Fragment> listViews; // Tab页面列表
	private ImageView cursor, collect_back;// 动画图片
	private TextView t1, t2, t3;// 页卡头标
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	LayoutInflater mInflater;
	View view1, view2, view3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication) this.getApplication();
		appState.addActivity(this);
		setContentView(R.layout.activity_my_collect);
		mInflater = LayoutInflater.from(getApplicationContext());
		InitTextView();
		InitImageView();
		InitViewPager();
	}

	private void InitTextView() {
		// TODO Auto-generated method stub
		t1 = (TextView) findViewById(R.id.text1);
		t1.setTextColor(getApplicationContext().getResources().getColor(R.color.themeColor));
		t2 = (TextView) findViewById(R.id.text2);
		t3 = (TextView) findViewById(R.id.text3);
		collect_back = (ImageView) findViewById(R.id.collect_back);

		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		t3.setOnClickListener(new MyOnClickListener(2));

		collect_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MyCollectActivity.this.finish();
			}
		});
	}

	class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			mPager.setCurrentItem(index);
		}
	}

	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		listViews = new ArrayList<Fragment>();
		C_goods goods = new C_goods();
		C_shops shops = new C_shops();
		C_esssays esssays = new C_esssays();
		listViews.add(goods);
		listViews.add(shops);
		listViews.add(esssays);
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
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.below_line).getWidth();// 获取图片宽度
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
				t1.setTextColor(getApplicationContext().getResources().getColor(R.color.themeColor));
				t2.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
				t3.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				}
				break;
			case 1:
				t1.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
				t2.setTextColor(getApplicationContext().getResources().getColor(R.color.themeColor));
				t3.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				}
				break;
			case 2:
				t1.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
				t2.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
				t3.setTextColor(getApplicationContext().getResources().getColor(R.color.themeColor));
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

	public interface Myclick {
		public abstract void col_click(View view);
	}
}
