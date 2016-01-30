package com.lyy.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.forums.util.DateUtil;
import com.lyy.project.R;
import com.lyy.project.R.layout;
import com.lyy.project.R.menu;
import com.lyy.util.MyApplication;
import com.lyy.util.MyMethod;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddShareActivity extends Activity implements OnClickListener {
	ImageView back, share1, share2, share3;
	Button submit;
	EditText edt;
	String pathImage;
	List<String> list = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication appState = (MyApplication)this.getApplication();  
		appState.addActivity(this);
		setContentView(R.layout.activity_add_share);
		initView();
	}

	private void initView() {
		back = (ImageView) findViewById(R.id.as_back);
		share1 = (ImageView) findViewById(R.id.share_img1);
		share2 = (ImageView) findViewById(R.id.share_img2);
		share3 = (ImageView) findViewById(R.id.share_img3);
		edt = (EditText) findViewById(R.id.add_contents);
		submit = (Button) findViewById(R.id.add_submit);
		back.setOnClickListener(this);
		share1.setOnClickListener(this);
		share2.setOnClickListener(this);
		share3.setOnClickListener(this);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.as_back:
			finish();
			break;
		case R.id.share_img1:
			openCamera(1);
			break;
		case R.id.share_img2:
			openCamera(2);
			break;
		case R.id.share_img3:
			openCamera(3);
			break;
		case R.id.add_submit:
			send();
			break;
		default:
			break;
		}
	}

	private void send(){
		String script = edt.getText().toString();
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getTime();
		//String url = MyMethod.url;
		String IP = MyApplication.IP;
		String url ="http://" + IP + ":8080/MyProject/Link?flag=54";
		int pl_id = getIntent().getIntExtra("pl_id", 0);
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		//params.addBodyParameter("flag","54");
		params.addBodyParameter("pl_id",""+pl_id);
		params.addBodyParameter("time",time);
		params.addBodyParameter("script",script);
		Log.e("time-->", time);
		
		/*params.addBodyParameter("0",new File(list.get(0)));
		params.addBodyParameter("1",new File(list.get(1)));
		params.addBodyParameter("2",new File(list.get(2)));*/
//		Log.e("--->1", list.get(1));
		Log.e("--->1", list.toString());
		for (int i = 0; i < list.size(); i++) {
			params.addBodyParameter("" + i, new File(list.get(i)));
		}
		
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				MyMethod.showToast(getApplicationContext(), "提交成功");
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK&&requestCode !=0) {
			String sdState = Environment.getExternalStorageState();
			new DateFormat();
			String name = DateFormat.format("yyyyMMdd_hhmmss",
					Calendar.getInstance(Locale.CHINA))
					+ ".jpg";
			Bundle bundle = data.getExtras();
			// 获取相机返回的数据，并转换为图片格式
			Bitmap bitmap = (Bitmap) bundle.get("data");
			if (requestCode == 1) {
				share1.setImageBitmap(bitmap);
			}else if (requestCode == 2) {
				share2.setImageBitmap(bitmap);
			}
			else if (requestCode == 3) {
				share3.setImageBitmap(bitmap);
			}
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
		}
	}

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
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		intent.putExtra("output",
				Uri.fromFile(new File(this.getExternalCacheDir(), pathImage)));
		startActivityForResult(intent, 0);
	}

	private void openCamera(int a) {
		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(camera, a);
	}
}
