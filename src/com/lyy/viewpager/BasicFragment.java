package com.lyy.viewpager;

import java.lang.reflect.Type;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lyy.project.R;
import com.lyy.util.MyApplication;
import com.lyy.xl.bean.BasicBean;
import com.lyy.xl.utils.BasicApplication;

public class BasicFragment extends Fragment {
	// List<BasicBean> list=new ArrayList<BasicBean>();
	String url="http://"+MyApplication.IP+":8080/MyProject/storeSevlet";
	BasicBean basicBeans ;
		View view;
	ImageView image_basic,detail_fenxiang;
	TextView goods_msg, price_goods, collection_num, postage, buy_num;
	private int p_id;
   Handler handler=new Handler(){
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
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// 初始化SDK
		ShareSDK.initSDK(getActivity());
		
		view = inflater.inflate(R.layout.basic_msg, null);
		p_id = getArguments().getInt("p_id");
		detail_fenxiang=(ImageView) view.findViewById(R.id.detail_fenxiang);
		detail_fenxiang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showshare();
			}

		});
		
		initView();
		initData();
		return view;

	}

	private void showshare() {
		// TODO Auto-generated method stub
		OnekeyShare oks = new OnekeyShare(); // 关闭sso授权
		oks.disableSSOWhenAuthorize();
		getString((R.string.app_name)); // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share)); // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://sharesdk.cn"); // text是分享文本，所有平台都需要这个字段
		oks.setText("我为绿意苑代言"); // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImageUrl("http://10.203.1.71:8080/happycat/img/lb_sj.jpg");// 确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		 oks.setUrl("http://sharesdk.cn"); // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		 oks.setComment("我是绿意苑代言人"); // site是分享此内容的网站名称，仅在QQ空间使用
		 oks.setSite(getString(R.string.app_name)); //
		 //siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://sharesdk.cn");
		oks.show(getActivity());
	}
	

	private void addData() {
		// TODO Auto-generated method stub
	MyApplication.bitmapUtils.display(image_basic,"http://"+MyApplication.IP+":8080"+ basicBeans.getPhone());
	goods_msg.setText(basicBeans.getName());
	price_goods.setText(basicBeans.getPrice()+"元");
	collection_num.setText(basicBeans.getCol_num()+"  件");
	postage.setText(basicBeans.getPostage()+"元");
	buy_num.setText(basicBeans.getBuy_num()+" 件");
	};
	   
	
	private void initData() {

		// TODO Auto-generated method stub
		HttpUtils utils = new HttpUtils();
		
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "4");
		params.addBodyParameter("p_id", p_id+"");
		utils.send(HttpMethod.POST,url , 
				params,new RequestCallBack<String>() {

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
						Message msg=new Message();
		                msg.what=1;
		                handler.sendMessage(msg);
		                Log.e("dddd", basicBeans.toString());
					}
				});
		
		

	}

	private void initView() {
		// TODO Auto-generated method stub
		image_basic = (ImageView) view.findViewById(R.id.image_basic);
		goods_msg = (TextView) view.findViewById(R.id.goods_msg);
		price_goods = (TextView) view.findViewById(R.id.price_goods);
		collection_num = (TextView) view.findViewById(R.id.collection_num);
		postage = (TextView) view.findViewById(R.id.postage);
		buy_num = (TextView) view.findViewById(R.id.buy_num);
 
	}

}
