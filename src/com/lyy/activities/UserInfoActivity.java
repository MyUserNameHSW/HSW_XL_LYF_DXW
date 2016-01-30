package com.lyy.activities;

import java.io.File;

import android.app.Activity;
import android.app.Dialog;
//import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

public class UserInfoActivity extends Activity implements OnClickListener {
	ImageView info_back, headImp;
	TextView editUsername, editPhone, editPassword, info_username;
	Button button;
	int IMAGE_CAMERA = 0;
	int IMAGE_PHONE = 1;
	Bitmap bitmap;
	Intent intent;
	Dialog dialog;
	View view;
	/* 请求码 */
	public static final int REQUEST_CODE_OPENPICS = 1;
	public static final int REQUEST_CODE_CAMERA = 2;
	public static final int REQUEST_CODE_ZOOM = 3;

	private int u_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication) this.getApplication();
		appState.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_info);
		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		u_id = MyMethod.GetU_id(getApplicationContext());
		Log.e("creatView", "--->" + u_id);
		String usernameString = MyMethod.Get_UserName_ById(u_id);
		info_username.setText(usernameString);
		MyApplication.bitmapUtils.display(headImp, MyMethod.headImg(u_id));
	}

	private void initView() {
		// TODO Auto-generated method stub
		info_username = (TextView) findViewById(R.id.info_username);
		info_back = (ImageView) findViewById(R.id.info_back);
		headImp = (ImageView) findViewById(R.id.one);
		editUsername = (TextView) findViewById(R.id.edit_username);
		editPhone = (TextView) findViewById(R.id.edit_user_phone);
		editPassword = (TextView) findViewById(R.id.edit_user_code);
		button = (Button) findViewById(R.id.exitLogin);
		button.setOnClickListener(this);
		info_back.setOnClickListener(this);
		headImp.setOnClickListener(this);
		editUsername.setOnClickListener(this);
		editPhone.setOnClickListener(this);
		editPassword.setOnClickListener(this);
		headImp.setOnClickListener(this);
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.info_back:
			UserInfoActivity.this.finish();
			break;
		case R.id.one:
			// 选择相册或者相机
			showDialog();
			break;
		case R.id.edit_username:
			intent = new Intent(getApplicationContext(),
					UpdateNameActivity.class);
			startActivity(intent);
			break;
		case R.id.edit_user_phone:
			intent = new Intent(getApplicationContext(),
					UpdatePhoneActivity.class);
			startActivity(intent);
			break;
		case R.id.edit_user_code:
			intent = new Intent(getApplicationContext(),
					UpdateCodeActivity.class);
			startActivity(intent);
			break;
		case R.id.exitLogin:
			MyMethod.cleanData(getApplicationContext());
			intent = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	private void showDialog() {
		view = getLayoutInflater().inflate(R.layout.photo_choose_dialog,
				null);
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
	}
    
	public void on_click(View v) {
		switch (v.getId()) {
		case R.id.openCamera:
			openCamera();
			break;
		case R.id.openPhones:
			openPhones();
			break;
		case R.id.cancel:
			dialog.cancel();
			break;
		default:
			break;
		}
	}

	private void openPhones() {
		dialog.dismiss();
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, REQUEST_CODE_OPENPICS);
	}

	private void openCamera() {
		dialog.dismiss();
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
				UserInfoActivity.this.getExternalCacheDir(), "temp_photo.png")));
		startActivityForResult(intent, REQUEST_CODE_CAMERA);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("resultCode:" + resultCode);
		switch (requestCode) {
		// 如果是直接从相册获取
		case REQUEST_CODE_OPENPICS:
			if (data != null)
				startPhotoZoom(data.getData());
			break;
		// 如果是调用相机拍照时
		case REQUEST_CODE_CAMERA:
			System.out.println("----" + data);

			if (resultCode == RESULT_OK) {
				File file = new File(this.getExternalCacheDir(),
						"temp_photo.png");
				startPhotoZoom(Uri.fromFile(file));
			}
			break;
		// 取得裁剪后的图片
		case REQUEST_CODE_ZOOM:
			if (resultCode == RESULT_OK) {
				if (data != null) {
					// dataIntent = data;
					setPicToView(data);
					uploadPhoto();
				}
			}
			break;
		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 50);
		intent.putExtra("outputY", 50);
		intent.putExtra("return-data", true);
		intent.putExtra("output",
				Uri.fromFile(new File(this.getExternalCacheDir(), "temp.png")));
		startActivityForResult(intent, REQUEST_CODE_ZOOM);
	}

	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			headImp.setImageBitmap(photo);
			
		}
	}

	private void uploadPhoto() {
//		String url = "http://10.204.1.41:8080/GreenGarden/Check?action=10";

//		String url = "http://10.204.1.45:8080/GreenGarden1/uploadpic";
		String url = "http://"+MyApplication.IP+":8080/MyProject/uploadpic";
		Log.e("url", url);
		RequestParams params = new RequestParams();
		params.addBodyParameter("msg", "headimg");
		params.addBodyParameter("u_id", u_id + "");
		params.addBodyParameter("file", new File(this.getExternalCacheDir(),
				"temp.png"));
		HttpUtils httpUtils = new HttpUtils();
		
		httpUtils.send(HttpRequest.HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						Toast.makeText(getApplicationContext(), "正在上传",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// 上传成功，这里面的返回值，就是服务器返回的数据
						// 使用 String result = responseInfo.result 获取返回值
						Toast.makeText(getApplicationContext(), "上传成功",
								Toast.LENGTH_SHORT).show();
						Log.e("------>", "成功");
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// 上传失败
						Log.e("ssssewewew", "failed");
					}
				});
	}
}
