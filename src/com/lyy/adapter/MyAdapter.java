package com.lyy.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lyy.bean.CartBean;
import com.lyy.project.R;
import com.lyy.util.MyApplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class MyAdapter extends BaseAdapter {
	private Handler mHandler;
	ViewHolder holder = null;
	List<TextView> tList = new ArrayList<TextView>();
	private Context context; // ���������
	private List<CartBean> list = new ArrayList<CartBean>(); // ���ݼ���List
	private LayoutInflater inflater; // ���������
	private static HashMap<Integer, Boolean> isSelected;

	@SuppressLint("UseSparseArrays") 
	public MyAdapter(Handler mHandler, Context context, List<CartBean> list) {
		super();
		this.mHandler = mHandler;
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		isSelected = new HashMap<Integer, Boolean>();
		initDate();
	}

	// ��ʼ��isSelected������
	private void initDate() {
		for (int i = 0; i < list.size(); i++) {
			getIsSelected().put(i, false);
		}
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		MyAdapter.isSelected = isSelected;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		CartBean bean = list.get(arg0);
		
		if (arg1 == null) {
			arg1 = inflater.inflate(R.layout.mycart_item, null);
			holder = new ViewHolder();
			holder.shop_photo = (ImageView) arg1.findViewById(R.id.shop_photo);
			holder.shop_name = (TextView) arg1.findViewById(R.id.shop_name);
			holder.shop_price = (TextView) arg1.findViewById(R.id.shop_price);
			holder.shop_number = (TextView) arg1.findViewById(R.id.shop_number);
			holder.shop_check = (CheckBox) arg1.findViewById(R.id.shop_check);
			holder.btn1 = (Button) arg1.findViewById(R.id.add);
			holder.btn2 = (Button) arg1.findViewById(R.id.reduce);
			
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		tList.add(holder.shop_number);
		MyApplication.bitmapUtils.display(holder.shop_photo, "http://"
				+ MyApplication.IP + ":8080" + bean.getPic());
		holder.shop_name.setText(bean.getPname());
		holder.shop_price.setText("￥" + bean.getPrice());
		holder.shop_number.setTag(arg0);
		holder.shop_number.setText(String.valueOf(bean.getAccount()));
		//holder.shop_number.setOnClickListener(new ShopNumberClickListener());
		holder.shop_check.setTag(arg0);
		holder.shop_check.setChecked(isSelected.get(arg0));
		holder.shop_check
				.setOnCheckedChangeListener(new CheckBoxChangedListener());
		holder.btn1.setOnClickListener(new MyNum(arg0));
		holder.btn2.setOnClickListener(new MyNum(arg0));
		return arg1;
	}
    
	private class MyNum implements OnClickListener{
        int index;
       
		public MyNum(int index) {
			super();
			this.index = index;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			 TextView textView = tList.get(index);
		     int num=Integer.parseInt(textView.getText().toString());
			switch (arg0.getId()) {
			case R.id.reduce:
				num++;
				number = num;
				list.get(index).setAccount(number);
				handler.sendMessage(handler.obtainMessage(1, textView));
				break;
			case R.id.add:
				if (num >1) {
					num--;
					number = num;
					list.get(index).setAccount(number);
					handler.sendMessage(handler.obtainMessage(1, textView));
				}else {
					Toast.makeText(context, "数量不能少于1", 0).show();
				}
				break;
			default:
				break;
			}
		}
		
	}
	
	// ����TextView���������
	/*private final class ShopNumberClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// ��ȡ��Ʒ������
			String str = ((TextView) v).getText().toString();
			int shopNum = Integer.valueOf(str);
			//showDialog(shopNum, (TextView) v);
		}
	}*/

	private int number = 0; // ��¼�Ի����е�����
	private EditText editText; // �Ի����������༭��

	/**
	 * �����Ի��������Ʒ������
	 * 
	 * @param shopNum
	 *            ��Ʒԭ��������
	 * @param textNum
	 *            Item����ʾ��Ʒ�����Ŀؼ�
	 */
	/*private void showDialog(int shopNum, final TextView textNum) {
		View view = inflater.inflate(R.layout.number_update, null);
		Button btnSub = (Button) view.findViewById(R.id.numSub);
		Button btnAdd = (Button) view.findViewById(R.id.numAdd);
		editText = (EditText) view.findViewById(R.id.edt);
		editText.setText(String.valueOf(shopNum));
		btnSub.setOnClickListener(new ButtonClickListener());
		btnAdd.setOnClickListener(new ButtonClickListener());
		number = shopNum;
		new AlertDialog.Builder(context).setView(view)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// ���û����ĵ���Ʒ�������µ�������
						int position = (Integer) textNum.getTag();
						list.get(position).setAccount(number);
						handler.sendMessage(handler.obtainMessage(1, textNum));
					}
				}).setNegativeButton("ȡ��", null).create().show();
	}*/

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) { // ������Ʒ����
				((TextView) msg.obj).setText(number+"");
				// ������Ʒ������֪ͨActivity������Ҫ���ѵ��ܽ��
				mHandler.sendMessage(mHandler
						.obtainMessage(10, getTotalPrice()));
			} else if (msg.what == 2) {// ���ĶԻ����е�����
				editText.setText(String.valueOf(number));
			}
		}
	};

	// CheckBoxѡ��ı������
	private final class CheckBoxChangedListener implements
			OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(CompoundButton cb, boolean flag) {
			int position = (Integer) cb.getTag();
			getIsSelected().put(position, flag);
			CartBean bean = list.get(position);
			bean.setChoosed(flag);
			mHandler.sendMessage(mHandler.obtainMessage(10, getTotalPrice()));
			// ������е���Ʒȫ����ѡ�У���ȫѡ��ťҲĬ�ϱ�ѡ��
			mHandler.sendMessage(mHandler.obtainMessage(11, isAllSelected()));
		}
	}

	/**
	 * ����ѡ����Ʒ�Ľ��
	 * 
	 * @return ������Ҫ���ѵ��ܽ��
	 */
	private float getTotalPrice() {
		CartBean bean = null;
		float totalPrice = 0;
		for (int i = 0; i < list.size(); i++) {
			bean = list.get(i);
			if (bean.isChoosed()) {
				totalPrice += bean.getAccount() * bean.getPrice();
			}
		}
		return totalPrice;
	}

	/**
	 * �ж��Ƿ��ﳵ�����е���Ʒȫ����ѡ��
	 * 
	 * @return true������Ŀȫ����ѡ�� false������Ŀû�б�ѡ��
	 */
	private boolean isAllSelected() {
		boolean flag = true;
		for (int i = 0; i < list.size(); i++) {
			if (!getIsSelected().get(i)) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	private final class ViewHolder {
		public ImageView shop_photo; // ��ƷͼƬ
		public TextView shop_name; // ��Ʒ����
		public TextView shop_price; // ��Ʒ�۸�
		public TextView shop_number; // ��Ʒ����
		public CheckBox shop_check; // ��Ʒѡ��ť
		public Button btn1,btn2;
	}
}
