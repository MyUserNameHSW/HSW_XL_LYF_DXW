package com.lyy.forums.activities;

//帖子详情列表activity
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.activities.MainActivity;
import com.lyy.bean.F_assess;
import com.lyy.forums.adapter.F_assessAdapter;
import com.lyy.forums.adapter.Forums_GridViewAdapter;
import com.lyy.forums.util.DateUtil;
import com.lyy.forums.util.F_assessListViewHeight;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.text.TextPaint;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("InflateParams")
public class ForumDetailActivity extends Activity implements OnClickListener {
	LinearLayout linearLayout1, linearLayout2, linearLayout3, layout2;
	ImageView favourImageView, backImageView, xialadetailImageView,
			userimagedetailImageView, sendImageView;
	TextView usernamedetailTextView, timedetailTextView, themedetailTextView,
			articldetailTextView, pointdetailTextView, contentTextView;
	EditText editText;
	ListView listView;
	F_assessAdapter myDetailAdapter;
	List<F_assess> list = new ArrayList<F_assess>();
	LayoutInflater mInflater;
	HttpUtils httpUtils = new HttpUtils();
	RequestParams params = new RequestParams();
	View view;
	PopupWindow pop;
	LayoutInflater layout;
	int item, lastItem, f_id, u_id;
	String username, articl, theme, f_time, userimg, point;
	GridView gridView;
	ArrayList<String> listpicture = new ArrayList<String>();
	Forums_GridViewAdapter gridViewAdapter;
	private int width;
	String result, result2;

	// 当前用户id
	int U_id ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forum_detail);

		mInflater = LayoutInflater.from(ForumDetailActivity.this);
         U_id = MyMethod.GetU_id(getApplicationContext());
		// 从上一个获取数据
		Intent intent = getIntent();
		f_id = intent.getIntExtra("f_id", 1);
		u_id = intent.getIntExtra("u_id", 2);
		username = intent.getStringExtra("username");
		point = intent.getStringExtra("point");
		articl = intent.getStringExtra("articl");
		theme = intent.getStringExtra("theme");
		f_time = intent.getStringExtra("f_time");
		userimg = intent.getStringExtra("userimg");
		// 获取手机分辨率，这类写法只能放在activity中，其他地方搜索另外的方法
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		// 初始化
		initViews();
		// 获取帖子的图片
		getPictureFromServlet();
		// 获取评论数据
		getDataFromServlet();

	}

	@SuppressWarnings("static-access")
	private void initViews() {
		// 初始化控件
		linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
		linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
		linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
		layout2 = (LinearLayout) findViewById(R.id.input);
		// 隐藏输入框
		layout2.setVisibility(View.GONE);
		editText = (EditText) findViewById(R.id.inputtext);
		sendImageView = (ImageView) findViewById(R.id.send2);
		backImageView = (ImageView) findViewById(R.id.back);
		favourImageView = (ImageView) findViewById(R.id.favour2);
		// 详情列表中的控件
		xialadetailImageView = (ImageView) findViewById(R.id.focus);
		userimagedetailImageView = (ImageView) findViewById(R.id.userimagedetail);
		usernamedetailTextView = (TextView) findViewById(R.id.usernamedetail);
		timedetailTextView = (TextView) findViewById(R.id.timedetail);
		themedetailTextView = (TextView) findViewById(R.id.themedetail);
		//设置字体为粗体
		TextPaint tp = themedetailTextView.getPaint(); 
		tp.setFakeBoldText(true); 

		articldetailTextView = (TextView) findViewById(R.id.articldetail);
		pointdetailTextView = (TextView) findViewById(R.id.point2);
		listView = (ListView) findViewById(R.id.mydetaillistView);
		gridView = (GridView) findViewById(R.id.detailgridView);
		// 初始化适配器
		myDetailAdapter = new F_assessAdapter(list, ForumDetailActivity.this);
		gridViewAdapter = new Forums_GridViewAdapter(ForumDetailActivity.this,
				gridView, width, listpicture);
		gridView.setAdapter(gridViewAdapter);

		view = mInflater.inflate(R.layout.forum_pop, null);
		pop = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		// 单击事件
		linearLayout1.setOnClickListener(this);
		linearLayout2.setOnClickListener(this);
		linearLayout3.setOnClickListener(this);
		backImageView.setOnClickListener(this);
		sendImageView.setOnClickListener(this);
		xialadetailImageView.setOnClickListener(this);
		// 设置值
		Intent intent = getIntent();
		usernamedetailTextView.setText(intent.getStringExtra("username"));
		timedetailTextView.setText(intent.getStringExtra("f_time"));
		themedetailTextView.setText(intent.getStringExtra("theme"));
		articldetailTextView.setText(intent.getStringExtra("articl"));

		if (point == null) {
			pointdetailTextView.setVisibility(view.GONE);
		} else {
			pointdetailTextView.setText(intent.getStringExtra("point"));
		}

		MyApplication.bitmapUtils.display(
				userimagedetailImageView,
				"http://" + MyApplication.IP + ":8080"
						+ intent.getStringExtra("userimg"));
		// 查询关注列表,获取数据
		getFocusFromServlet();
		getPraiseFromServlet();

	}

	private void getPictureFromServlet() {
		params.addBodyParameter("action", "" + 16);
		params.addBodyParameter("f_time", f_time);

		String url = "http://" + MyApplication.IP
				+ ":8080/MyProject/Check";
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {

					}

					@SuppressWarnings("static-access")
					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String result = arg0.result;
						if (result == null) {
							gridView.setVisibility(view.GONE);
						} else {

							try {
								JSONArray array = new JSONArray(result);
								// 把json数组转换为集合,没有直接的方法
								for (int i = 0; i < array.length(); i++) {
									// 取出json数组中的每个json对象
									JSONObject object = array.getJSONObject(i);
									// 取出jsonobject中的对应的值
									String p_img = object.getString("p_img");
									listpicture.add(p_img);
								}
								// 刷新界面
								gridViewAdapter.notifyDataSetChanged();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
	}

	private void getDataFromServlet() {
		params.addBodyParameter("f_id", "" + f_id);
		String url = "http://" + MyApplication.IP
				+ ":8080/MyProject/Check?action=9";
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String result = arg0.result;
						// 使用GSon框架进行json解析
						Gson gson = new Gson();
						Type typeOfT = new TypeToken<List<F_assess>>() {
						}.getType();
						List<F_assess> list2 = gson.fromJson(result, typeOfT);
						list.addAll(list2);
						listView.setAdapter(myDetailAdapter);
						F_assessListViewHeight
								.setListViewHeightBasedOnChildren(listView);
						myDetailAdapter.notifyDataSetChanged();
					}
				});

	}

	private void getFocusFromServlet() {
		// 用户的id params.addBodyParameter("f_id", "" + f_id);
		// 发表贴的用户的id,想要关注的人的id
		params.addBodyParameter("u_id", "" + U_id);
		params.addBodyParameter("fOCU_id", "" + u_id);
		String url = "http://" + MyApplication.IP
				+ ":8080/MyProject/Check?action=19";
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						result = arg0.result;

						if (result.equals("没")) {
							xialadetailImageView
									.setImageResource(R.drawable.tm_btn_pin);

						} else {
							xialadetailImageView
									.setImageResource(R.drawable.tm_fun_btn_pinned);
						}

					}
				});

	}

	private void getPraiseFromServlet() {
		// 用户的id params.addBodyParameter("", "");
		params.addBodyParameter("u_id", "" + U_id);
		// 要点赞的帖子的id
		params.addBodyParameter("f_id", "" + f_id);
		Log.e("f_id", f_id+"");
		Log.e("u_id", U_id+"");
		String url = "http://" + MyApplication.IP
				+ ":8080/MyProject/Check?action=20";
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						result2 = arg0.result;

						if (result2.equals("没")) {
							favourImageView
									.setImageResource(R.drawable.af_ugc_icon_favour);

						} else {
							favourImageView
									.setImageResource(R.drawable.af_ugc_icon_favour_slt);
						}

					}
				});
	}

	public void onClick(View v) {
		switch (v.getId()) {
		// 点击评论
		case R.id.linearLayout2:
			layout2.setVisibility(View.VISIBLE);
			break;
		// 点击发送按钮
		case R.id.send2:
			send();
			layout2.setVisibility(View.GONE);
			InputMethodManager imm = (InputMethodManager) this
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			editText.setCursorVisible(false);// 失去光标
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			((EditText) findViewById(R.id.inputtext)).setText("");
			list.clear();
			getDataFromServlet();
			listView.setAdapter(myDetailAdapter);
			myDetailAdapter.notifyDataSetChanged();
			break;
		// 点击分享转发
		case R.id.linearLayout1:
			Intent intent2 = new Intent(ForumDetailActivity.this,
					ShareActivity.class);
			// 传给转发页的数据
			intent2.putExtra("username", username);
			intent2.putExtra("articl", articl);
			intent2.putExtra("theme", theme);
			intent2.putExtra("f_time", f_time);
			intent2.putStringArrayListExtra("listpicture", listpicture);
			startActivity(intent2);
			break;
		// 点赞
		case R.id.linearLayout3:
			if (result2.equals("没")) {
				// 数据库中点赞数+1
				dianZan();
				result2 = "有";
			} else {
				// 数据库中点赞数-1
				cancleDianZan();
				result2 = "没";
			}
			break;
		// 关注
		case R.id.focus:
			if (result.equals("没")) {
				// 加关注
				attention();
				xialadetailImageView
						.setImageResource(R.drawable.tm_fun_btn_pinned);
				result = "有";
			} else {
				// 取消关注
				cancelattention();
				xialadetailImageView.setImageResource(R.drawable.tm_btn_pin);
				result = "没";
			}
			break;
		// 返回上一界面
		case R.id.back:
			/*Intent intent3 = new Intent(ForumDetailActivity.this,
					MainActivity.class);
			startActivity(intent3);*/
			finish();
			break;

		// 举报
		case R.id.report:
			Toast.makeText(this, "...........", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}

	}

	public void send() {
		httpUtils = new HttpUtils();
		// 获取帖子的id
		Intent intent2 = getIntent();
		int f_id = intent2.getIntExtra("f_id", 1);
		// 获得评论内容
		String content = editText.getEditableText().toString().trim();
		// 获取当前评论时间
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getTime();
		String url = "http://" + MyApplication.IP
				+ ":8080/MyProject/Check?action=11";
		RequestParams params = new RequestParams();
		params.addBodyParameter("u_id", "" + U_id);
		params.addBodyParameter("f_id", "" + f_id);
		params.addBodyParameter("time", time);
		params.addBodyParameter("content", content);
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// 评论发表成功
						Toast.makeText(ForumDetailActivity.this, "发表评论成功！",
								Toast.LENGTH_SHORT).show();

					}
				});
	}

	private void dianZan() {
		favourImageView.setImageResource(R.drawable.af_ugc_icon_favour_slt);
		// 用户的idparams.addBodyParameter("f_id", "" + f_id);
		params.addBodyParameter("u_id", "" + U_id);
		params.addBodyParameter("f_id", "" + f_id);
		String url = "http://" + MyApplication.IP
				+ ":8080/MyProject/Check?action=14";
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						Toast.makeText(ForumDetailActivity.this, "已点赞",
								Toast.LENGTH_SHORT).show();
					}
				});

	}

	private void cancleDianZan() {
		favourImageView.setImageResource(R.drawable.af_ugc_icon_favour);
		params.addBodyParameter("u_id", "" + U_id);
		params.addBodyParameter("f_id", "" + f_id);
		String url = "http://" + MyApplication.IP
				+ ":8080/MyProject/Check?action=15";
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						Toast.makeText(ForumDetailActivity.this, "已取消",
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (pop != null) {
			pop.dismiss();
		}
		return super.onTouchEvent(event);
	}

	private void attention() {
		// 当前用户的id
		params.addBodyParameter("u_id", "" + U_id);
		params.addBodyParameter("fOCU_id", "" + u_id);
		String url = "http://" + MyApplication.IP
				+ ":8080/MyProject/Check?action=12";
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						Toast.makeText(ForumDetailActivity.this, "已关注",
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	private void cancelattention() {
		// 当前用户的id
		params.addBodyParameter("u_id", "" + U_id);
		params.addBodyParameter("fOCU_id", "" + u_id);
		String url = "http://" + MyApplication.IP
				+ ":8080/MyProject/Check?action=13";
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						Toast.makeText(ForumDetailActivity.this, "已取消",
								Toast.LENGTH_SHORT).show();
					}
				});
	}

}
