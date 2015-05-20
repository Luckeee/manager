package com.gg.manager;

import com.gg.manager.sevices.Reconder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Setting extends Activity {

	CheckBox item1, item2, item3;
	Reconder reconder;
	Boolean istangkuang, issound, isverbin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_layout);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setTitle("…Ë÷√");
		reconder = new Reconder(getApplicationContext());
		final SharedPreferences setting = getSharedPreferences("setting",
				Context.MODE_PRIVATE);
		istangkuang = setting.getBoolean("istankuang", false);
		issound = setting.getBoolean("issound", false);
		isverbin = setting.getBoolean("isverbin", false);
		item1 = (CheckBox) findViewById(R.id.istankuang);
		item2 = (CheckBox) findViewById(R.id.issound);
		item3 = (CheckBox) findViewById(R.id.isVerbin);
		item1.setChecked(istangkuang);
		item1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub

				if (istangkuang) {
					Editor editor = setting.edit();
					editor.putBoolean("istankuang", false);
					editor.commit();
				} else {
					Editor editor = setting.edit();
					editor.putBoolean("istankuang", true);
					editor.commit();
				}
			}
		});
		item2.setChecked(issound);
		item2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (issound) {
					Editor editor = setting.edit();
					editor.putBoolean("issound", false);
					editor.commit();
				} else {
					Editor editor = setting.edit();
					editor.putBoolean("issound", true);
					editor.commit();
				}
			}
		});
		item3.setChecked(isverbin);
		item3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (isverbin) {
					Editor editor = setting.edit();
					editor.putBoolean("isverbin", false);
					editor.commit();
				} else {
					Editor editor = setting.edit();
					editor.putBoolean("isverbin", true);
					editor.commit();
				}
			}
		});
		findViewById(R.id.getallinit).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				reconder.initrecond();
			}
		});
		findViewById(R.id.whitelist).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new  Intent(Setting.this,SetWhiteList.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.feedback).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_SENDTO);
				Uri uri = Uri.parse("mailto:1191481036@qq.com");
				intent.setData(uri);
				Setting.this.startActivity(intent);
			}
		});

	}
}
