package com.gg.managet.test;

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
import android.widget.ToggleButton;

public class TAdapter extends BaseAdapter {
	
	
	List<TBean> mdata;
	LayoutInflater inflater;
	
	public	TAdapter(Context context,List<TBean> allDatas,int position){
		this.mdata = allDatas;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mdata.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mdata.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder=new ViewHolder();
		if (arg1==null) {
			arg1 =inflater.inflate(R.layout.whilte_list_item, arg2, false);
			
			holder.app_icon = (ImageView) arg1.findViewById(R.id.appicon);
			holder.app_name= (TextView) arg1.findViewById(R.id.appname);
			holder.is_white = (ToggleButton) arg1.findViewById(R.id.is_white);
			
			arg1.setTag(holder);
		}else {
			holder = (ViewHolder) arg1.getTag();
		}
		
		TBean bean = mdata.get(arg0);
		Uri uri = Uri.parse("/data/data/com.gg.manager/files/"+bean.getName());
		
		holder.app_icon.setImageURI(uri);
		holder.app_name.setText(bean.getName());
		holder.is_white.setChecked(bean.isWhite());

		return arg1;
	}

	private class ViewHolder{
		public ImageView app_icon;
		public TextView app_name;
		public ToggleButton is_white;
	}
}
