package com.lyy.buying;

import com.lyy.project.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MyDialog extends Dialog implements OnClickListener {
	private TextView basin, nobasin, pop_reduce, pop_add, pop_ok;
	private ImageView pop_del;
	private MyDialogListener listener;

	public interface MyDialogListener {
		public void onClick(View view);

	}

	public MyDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popwindow);
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		basin = (TextView) findViewById(R.id.hanpenfou);
		nobasin =  (TextView) findViewById(R.id.nobasin);
		pop_add = (TextView) findViewById(R.id.pop_add);
		pop_reduce = (TextView) findViewById(R.id.basic);
		pop_ok = (TextView) findViewById(R.id.pop_ok);
		pop_del = (ImageView) findViewById(R.id.pop_del);

		basin.setOnClickListener(this);
		nobasin.setOnClickListener(this);
		pop_add.setOnClickListener(this);
		pop_reduce.setOnClickListener(this);
		pop_ok.setOnClickListener(this);
		pop_del.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		listener.onClick(arg0);
	}

}
