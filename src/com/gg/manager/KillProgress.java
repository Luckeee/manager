package com.gg.manager;


import com.gg.manager.sevices.AppInformation;
import com.gg.manager.sevices.Reconder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class KillProgress extends Activity implements OnClickListener {

	String progName, appname;
	Button delayBtn, stopiyBtn;
	ImageView appicon;
	TextView appnameView, detailsView;
	AppInformation appmanager;
	Reconder reconder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setTitle("manager");
		setContentView(R.layout.activity_kill_progress);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	public void init() {
		reconder = new Reconder(getApplicationContext());
		Intent intent = getIntent();
		appname = intent.getStringExtra("appname");
		progName = intent.getStringExtra("progname");
		Log.e("killprocess", progName + appname);
		appnameView = (TextView) findViewById(R.id.appname);
		detailsView = (TextView) findViewById(R.id.details);
		appicon = (ImageView) findViewById(R.id.appicon);
		delayBtn = (Button) findViewById(R.id.delayAlter);
		delayBtn.setOnClickListener(this);
		stopiyBtn = (Button) findViewById(R.id.stopit);
		stopiyBtn.setOnClickListener(this);
		appmanager = new AppInformation(this);
		Uri uri = Uri.parse("/data/data/com.gg.manager/files/"+appname);
		appicon.setImageURI(uri);
		appnameView.setText(appname);
		
		String detailstring = "运行时间:" + reconder.get(appname,"running_time")+ "   限制时间:" + reconder.get(appname,"set_time");
		detailsView.setText(detailstring);

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.delayAlter:
			delay();
			this.finish();
			break;
		case R.id.stopit:
			dialog();
			break;
		default:
			break;
		}
	}

	private void delay() {
		// TODO Auto-generated method stub
		int set_time=0,dellayo=10000;
		set_time = reconder.get(appname,"set_time")+dellayo;
		reconder.update(appname, "set_time",set_time);
	}

	private void dialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
				.setTitle("").setNegativeButton("取消", null)
				.setPositiveButton("停止", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						appmanager.killProcessByName(progName);
					}
				});
		builder.create().show();
	}

}
