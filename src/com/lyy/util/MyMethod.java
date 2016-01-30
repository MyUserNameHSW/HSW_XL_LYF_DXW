package com.lyy.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.bean.UserInfo;

public class MyMethod {
	static String IP = MyApplication.IP;
	public final static String url = "http://" + IP + ":8080/MyProject/Link";
	private static SharedPreferences mySharedPreferences;
	static String username = "";
	static UserInfo userInfo;
	static Bitmap bitmap = null;
	static String imgUrl;
	static String NOWTIME;
	static int byPhoneUser_id;
	static String sname;
	static String saddress;

	/**
	 * 1.判断手机号码是否合理
	 * 
	 * @param phoneNums
	 */
	public static boolean judgePhoneNums(String phoneNums, Context context) {
		if (isMatchLength(phoneNums, 11) && isMobileNO(phoneNums)) {
			return true;
		}
		Toast.makeText(context, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
		return false;
	}

	/**
	 * 判断一个字符串的位数
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static boolean isMatchLength(String str, int length) {
		if (str.isEmpty()) {
			return false;
		} else {
			return str.length() == length ? true : false;
		}
	}

	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobileNums) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobileNums))
			return false;
		else
			return mobileNums.matches(telRegex);
	}

	/*
	 * 
	 * 2.Tosat方法
	 */
	public static void showToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/*
	 * 
	 * 3.存取用户id
	 */
	public static void WriteUserInfo(Context context, String username) {
		// 实例化SharedPreferences对象
		Log.e("username", username);
		mySharedPreferences = context.getSharedPreferences("user_name",
				Activity.MODE_PRIVATE);
		// 实例化SharedPreferences.Editor对象
		final SharedPreferences.Editor editor = mySharedPreferences.edit();
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "12");
		params.addBodyParameter("username", username);
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String ids = arg0.result;
				Log.e("ids", ids);
				editor.putInt("user_id", Integer.parseInt(ids));
				editor.commit();
			}
		});

	}

	/*
	 * 
	 * 4.读取用户id
	 */
	public static int GetU_id(Context context) {
		mySharedPreferences = context.getSharedPreferences("user_name",
				Activity.MODE_PRIVATE);
		// 使用getString方法获得value，注意第2个参数是value的默认值
		int u_id = mySharedPreferences.getInt("user_id", 0);
		return u_id;
	}

	public static String GetHead(Context context) {
		mySharedPreferences = context.getSharedPreferences("user_name",
				Activity.MODE_PRIVATE);
		// 使用getString方法获得value，注意第2个参数是value的默认值
		String head = mySharedPreferences.getString("head", "");
		return head;
	}

	/*
	 * 
	 * 5.清除用户id
	 */
	public static void cleanData(Context context) {
		mySharedPreferences = context.getSharedPreferences("user_name",
				Activity.MODE_PRIVATE);
		mySharedPreferences.edit().clear().commit();
	}

	/*
	 * 
	 * 5.通过用户id获得用户名
	 */
	public static String Get_UserName_ById(int u_id) {
		if (u_id == 0) {
			return "请登录";
		}
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "13");
		params.addBodyParameter("u_id", u_id + "");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				username = arg0.result;
			}
		});
		return username;
	}

	/*
	 * 
	 * 6.通过u_id获取headImg
	 */
	public static String headImg(int u_id) {

		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "18");
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
				imgUrl = "http://" + IP + ":8080" + result;
				Log.e("imgurl", imgUrl);
			}
		});
		return imgUrl;

	}

	/*
	 * 
	 * 7.获取当前时间
	 */
	public static String getNowTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		NOWTIME = sdf.format(new Date());
		Log.e("time", NOWTIME);
		return NOWTIME;
	}

	/*
	 * 
	 * 8.由手机号或用户名获取用户id
	 */
	public static int byPhoneUser(String username) {
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "38");
		params.addBodyParameter("username", username);
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				byPhoneUser_id = Integer.parseInt(result);
			}
		});
		return byPhoneUser_id;

	}

	/*
	 * 
	 * 9.通过s_id获取Sname
	 */
	public static String getSname1(int s_id) {

		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "42");
		params.addBodyParameter("s_id", s_id + "");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				sname = result;
			}
		});
		return sname;

	}

	/*
	 * 
	 * 9.通过s_id获取Sadress
	 */
	public static String getaddress(int s_id) {

		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "43");
		params.addBodyParameter("s_id", s_id + "");
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				saddress = result;
			}
		});
		return saddress;
	}

	// 由商品id获取店铺id
	public static String storeId(int p_id) {
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "50");
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
				saddress = result;
			}
		});
		return saddress;
	}

}
