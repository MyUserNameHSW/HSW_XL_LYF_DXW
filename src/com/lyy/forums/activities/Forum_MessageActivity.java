package com.lyy.forums.activities;

//发表帖子activity
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.activities.MainActivity;
import com.lyy.forums.util.DateUtil;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;

@SuppressLint({ "SimpleDateFormat", "SdCardPath" })
public class Forum_MessageActivity extends Activity implements OnClickListener {
	TextView canceltextView;
	Button sendButton;
	EditText articlEditText, themeEditText;
	Forum_MessageActivity mContext;
	HttpUtils httpUtils;
	MyApplication myApplication;
	GridView gridView;
	@SuppressWarnings("unused")
	private final int IMAGE_OPEN = 1; // 打开图片标记
	private String pathImage; // 选择图片路径
	private Bitmap bmp; // 导入临时图片
	private ArrayList<HashMap<String, Object>> imageItem;
	private SimpleAdapter simpleAdapter; // 适配器
	List<String> list = new ArrayList<String>();// 图片路径集合
	private static final int CAMERA_WITH_DATA = 0; // 拍照
	private static final int PHOTO_PICKED_WITH_DATA = 1; // gallery

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forum_message);
		initView();

	}

	private void initView() {
		canceltextView = (TextView) findViewById(R.id.cancel);
		sendButton = (Button) findViewById(R.id.send);
		articlEditText = (EditText) findViewById(R.id.newarticl);
		themeEditText = (EditText) findViewById(R.id.newtheme);

		canceltextView.setOnClickListener(this);
		sendButton.setOnClickListener(this);
		// 获取控件对象
		gridView = (GridView) findViewById(R.id.newgridView);
		/*
		 * 载入默认图片添加图片加号 通过适配器实现 SimpleAdapter参数imageItem为数据源
		 * R.layout.griditem_addpic为布局
		 */
		bmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.gridview_addpic); // 加号
		imageItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("itemImage", bmp);
		imageItem.add(map);
		Log.e("imageItem", imageItem.toString());
		simpleAdapter = new SimpleAdapter(Forum_MessageActivity.this,
				imageItem, R.layout.griditem_addpic,
				new String[] { "itemImage" }, new int[] { R.id.gridviewimage });
		/*
		 * HashMap载入bmp图片在GridView中不显示,但是如果载入资源ID能显示 如 map.put("itemImage",
		 * R.drawable.img); 解决方法: 1.自定义继承BaseAdapter实现 2.ViewBinder()接口实现 参考
		 * http://blog.csdn.net/admin_/article/details/7257901
		 */
		simpleAdapter.setViewBinder(new ViewBinder() {
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				// TODO Auto-generated method stub
				if (view instanceof ImageView && data instanceof Bitmap) {
					ImageView i = (ImageView) view;
					i.setImageBitmap((Bitmap) data);
					return true;
				}
				return false;
			}
		});
		gridView.setAdapter(simpleAdapter);

		/*
		 * 监听GridView点击事件 报错:该函数必须抽象方法 故需要手动导入import android.view.View;
		 */
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				if (imageItem.size() == 10) { // 第一张为默认图片
					Toast.makeText(Forum_MessageActivity.this, "图片数9张已满",
							Toast.LENGTH_SHORT).show();
				} else if (position == 0) { // 点击图片位置为+ 0对应0张图片
					// 选择图片
					new AlertDialog.Builder(Forum_MessageActivity.this)
							.setTitle("选择相片")
							.setIcon(android.R.drawable.ic_dialog_info)
							.setItems(new String[] { "相机", "相册" },
									new DialogInterface.OnClickListener() {

										public void onClick(
												DialogInterface dialog,
												int which) {
											switch (which) {
											case CAMERA_WITH_DATA:
												// 调用系统相机
												Intent camera = new Intent(
														MediaStore.ACTION_IMAGE_CAPTURE);
												startActivityForResult(camera,
														0);
												break;
											case PHOTO_PICKED_WITH_DATA:
												// 调用图库
												Intent intent = new Intent(
														Intent.ACTION_PICK,
														android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
												startActivityForResult(intent,
														1);
												break;
											default:
												break;
											}
										}
									}).setNegativeButton("取消", null).show();
					// 通过onResume()刷新数据
				} else {
					dialog(position);
					Toast.makeText(Forum_MessageActivity.this,
							"点击第" + (position + 1) + " 号图片", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});

	}

	// 单击事件
	public void onClick(View v) {

		switch (v.getId()) {
		// 取消发帖
		case R.id.cancel:
			finish();
			break;
		// 发帖
		case R.id.send:
			send();
			finish();
			break;
		default:
			break;
		}

	}

	// 裁剪图片
	public void startPhotoZoom(String pathImage) {
		// 获得裁剪图片的路径文件
		File file = new File(pathImage);
		// 获得裁剪图片的名字
		String originName = file.getName();
		// 获得裁剪图片的uri
		Uri uri1 = Uri.fromFile(file);
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri1, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 800);
		intent.putExtra("outputY", 600);
		intent.putExtra("return-data", true);
		intent.putExtra("output",
				Uri.fromFile(new File(this.getExternalCacheDir(), pathImage)));
		startActivityForResult(intent, 2);
	}

	// 获取图片路径 响应startActivityForResult
	@SuppressWarnings("unused")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 打开图片
		if (resultCode == RESULT_OK && requestCode == 1) {

			Uri uri = data.getData();
			if (!TextUtils.isEmpty(uri.getAuthority())) {
				// 查询选择图片
				Cursor cursor = getContentResolver().query(uri,
						new String[] { MediaStore.Images.Media.DATA }, null,
						null, null);
				// 返回 没找到选择图片
				if (null == cursor) {
					return;
				}

				// 光标移动至开头 获取图片路径
				cursor.moveToFirst();
				pathImage = cursor.getString(cursor
						.getColumnIndex(MediaStore.Images.Media.DATA));
				startPhotoZoom(pathImage);
				list.add(pathImage);
				Log.e("list", list.toString());

			}
		} else if (resultCode == RESULT_OK && requestCode == 0) {
			String sdState = Environment.getExternalStorageState();
			new DateFormat();
			String name = DateFormat.format("yyyyMMdd_hhmmss",
					Calendar.getInstance(Locale.CHINA))
					+ ".jpg";
			Bundle bundle = data.getExtras();
			// 获取相机返回的数据，并转换为图片格式
			Bitmap bitmap = (Bitmap) bundle.get("data");
			FileOutputStream fout = null;
			File file = new File("/sdcard/pintu/");
			file.mkdirs();
			pathImage = file.getPath() + name;

			startPhotoZoom(pathImage);

			Log.e("filename", pathImage);
			list.add(pathImage);
			try {
				fout = new FileOutputStream(pathImage);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					fout.flush();
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else {
			
		}// end if 打开图片
	}

	public void send() {
		httpUtils = new HttpUtils();
		// 帖子不包含图片
		String url1 = "http://" + MyApplication.IP
				+ ":8080/MyProject/Check?action=17";
		// 帖子包含图片
		String url2 = "http://" + MyApplication.IP
				+ ":8080/MyProject/Check?action=10";
		// 获得帖子的主题
		String articl = articlEditText.getEditableText().toString().trim();
		// 获得帖子的内容
		String theme = themeEditText.getEditableText().toString().trim();
		DateUtil dateUtil = new DateUtil();
		String f_time = dateUtil.getTime();

		RequestParams params = new RequestParams();
		// 当前用户id
		int U_id = MyMethod.GetU_id(getApplicationContext());
		params.addBodyParameter("u_id", "" + U_id);
		params.addBodyParameter("articl", articl);
		params.addBodyParameter("f_time", f_time);
		params.addBodyParameter("theme", theme);
		// 获取图片的总数i 放到文件内，一并传到web端
		for (int i = 0; i < list.size(); i++) {
			params.addBodyParameter("" + i, new File(list.get(i)));
		}
		if (list.size() == 0) {
			httpUtils.send(HttpMethod.POST, url1, params,
					new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							Toast.makeText(Forum_MessageActivity.this,
									"网络连接超时......", Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
							// 帖子发表成功，跳到帖子列表并刷新
							Toast.makeText(Forum_MessageActivity.this, "发帖成功",
									Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(
									Forum_MessageActivity.this,
									MainActivity.class);

							startActivity(intent);

						}
					});
		} else {
			httpUtils.send(HttpMethod.POST, url2, params,
					new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							Toast.makeText(Forum_MessageActivity.this,
									"网络连接超时......", Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
							// 帖子发表成功，跳到帖子列表并刷新
							Toast.makeText(Forum_MessageActivity.this, "发帖成功",
									Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(
									Forum_MessageActivity.this,
									MainActivity.class);

							startActivity(intent);

						}
					});
		}

	}

	// 刷新图片
	@Override
	protected void onResume() {
		super.onResume();
		if (!TextUtils.isEmpty(pathImage)) {
			Bitmap addbmp = BitmapFactory.decodeFile(pathImage);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", addbmp);
			imageItem.add(map);
			simpleAdapter = new SimpleAdapter(this, imageItem,
					R.layout.griditem_addpic, new String[] { "itemImage" },
					new int[] { R.id.gridviewimage });
			simpleAdapter.setViewBinder(new ViewBinder() {
				public boolean setViewValue(View view, Object data,
						String textRepresentation) {
					// TODO Auto-generated method stub
					if (view instanceof ImageView && data instanceof Bitmap) {
						ImageView i = (ImageView) view;
						i.setImageBitmap((Bitmap) data);
						return true;
					}
					return false;
				}
			});
			gridView.setAdapter(simpleAdapter);
			simpleAdapter.notifyDataSetChanged();
			// 刷新后释放防止手机休眠后自动添加
			pathImage = null;
		}
	}

	protected void dialog(final int position) {
		AlertDialog.Builder builder = new Builder(Forum_MessageActivity.this);
		builder.setMessage("确认移除已添加图片吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				imageItem.remove(position);
				simpleAdapter.notifyDataSetChanged();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

}
