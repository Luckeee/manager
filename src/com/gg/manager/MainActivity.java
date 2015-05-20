package com.gg.manager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gg.manager.bean.Bean;
import com.gg.manager.bean.MyAdapter;
import com.gg.manager.sevices.AppInformation;
import com.gg.manager.sevices.Reconder;
import com.gg.manager.sevices.backwatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Reconder reconder;
	ListView listview;
	Button item1;
	MyAdapter mAdapter;
	List<Bean> mDatas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setOverflowButtonAways();
		getActionBar().setDisplayShowHomeEnabled(false);
		initreconder();

		initlistview();
	}

	public void initreconder() {
		reconder = new Reconder(getApplicationContext());
		SharedPreferences setting = getSharedPreferences("setting",
				Context.MODE_PRIVATE);
		Boolean user_first = setting.getBoolean("isfirst", true);
		if (user_first) {
			reconder.initrecond();
			Editor editor = setting.edit();
			editor.putBoolean("isfirst", false);
			editor.commit();
		}
	}

	private void initlistview() {

		setListview();

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TextView view1, view2;
				String string1, t;
				view1 = (TextView) arg1.findViewById(R.id.appname);
				view2 = (TextView) arg1.findViewById(R.id.running_time);
				string1 = (String) view1.getText();
				t = (String) view2.getText();
				Intent intent = new Intent(MainActivity.this, Limit.class);
				intent.putExtra("appname", string1);
				intent.putExtra("runningtime", t);
				MainActivity.this.startActivity(intent);
			}
		});

		listview.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				return false;
			}
		});

	}

	private void setListview() {

		reconder = new Reconder(this);
		listview = (ListView) findViewById(R.id.istview);
		mDatas = new ArrayList<Bean>();
		Bean bean = new Bean();
		ArrayList<HashMap<String, Object>> alldata = reconder.getRecond();
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < reconder.size(); i++) {
			map = alldata.get(i);
			bean = new Bean(map.get("app_name").toString(), map.get("running_time").toString(), map.get("set_time").toString());
			mDatas.add(bean);
		}
		mAdapter = new MyAdapter(this, mDatas, 0, R.layout.list_view2);
		listview.setAdapter(mAdapter);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		listview.removeAllViewsInLayout();
		initlistview();
	}

	private void setOverflowButtonAways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKey = ViewConfiguration.class.getDeclaredField("ahasPermanentMenuKey");
			menuKey.setAccessible(true);
			menuKey.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent i = new Intent(MainActivity.this, backwatch.class);
		switch (item.getItemId()) {
		case R.id.StartService:

			try {
				AppInformation appInformation = new AppInformation(
						getApplicationContext());
				boolean iswork = appInformation.isServiceWork(this,
						"com.gg.manager.sevices.backwatch");

				if (!iswork) {
					startService(i);
					Toast.makeText(MainActivity.this, "已开启服务",
							Toast.LENGTH_LONG).show();
					SharedPreferences setting = getSharedPreferences("setting",
							Context.MODE_PRIVATE);
					Editor editor = setting.edit();
					editor.putBoolean("iswork", true);
					editor.commit();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.stopser:
			stopService(i);
			SharedPreferences setting = getSharedPreferences("setting",
					Context.MODE_PRIVATE);
			Editor editor = setting.edit();
			editor.putBoolean("iswork",false);
			editor.commit();
			break;
		case R.id.setting:
			Intent f = new Intent(MainActivity.this, Setting.class);
			startActivity(f);
			break;
		case R.id.refresh:
			listview.removeAllViewsInLayout();
			initlistview();
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return super.onMenuOpened(featureId, menu);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
