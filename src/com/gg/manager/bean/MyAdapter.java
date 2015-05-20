package com.gg.manager.bean;

import java.util.List;

import com.gg.manager.R;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	
	int resId=0;
	List<Bean> mDatas;
	LayoutInflater mInflater;

	public MyAdapter(Context context, List<Bean> mDatas, int position,int resId) {
		// TODO Auto-generated constructor stub
		this.mDatas = mDatas;
		mInflater = LayoutInflater.from(context);
		this.resId = resId;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mDatas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder = new ViewHolder();
		if (arg1 == null) {
			arg1 = mInflater.inflate(resId, arg2, false);

			holder.app_icon = (ImageView) arg1.findViewById(R.id.icon);
			holder.app_name = (TextView) arg1.findViewById(R.id.appname);
			holder.running_time = (TextView) arg1
					.findViewById(R.id.running_time);
			holder.set_time = (TextView) arg1.findViewById(R.id.s_time);

			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}

		Bean bean = mDatas.get(arg0);
		
		Uri uri =Uri.parse("/data/data/com.gg.manager/files/"+bean.getApp_name());
		holder.app_icon.setImageURI(uri);
		holder.app_name.setText(bean.getApp_name());
		holder.running_time.setText(bean.getRunning_time());
		holder.set_time.setText(bean.getSet_time());

		return arg1;
	}

	private class ViewHolder {

		public ImageView app_icon;
		public TextView app_name;
		public TextView running_time;
		public TextView set_time;
	}

}
