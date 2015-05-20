package com.gg.manager;

import com.gg.manager.sevices.Reconder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Limit extends Activity {

	ListView limitlist;
	SimpleAdapter adapter;
	Reconder reconder;
	TextView view1, view;
	EditText view2;
	ImageView imgv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.limit);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setTitle("设置限时");
		reconder = new Reconder(getApplicationContext());
		imgv=(ImageView) findViewById(R.id.app_icon);
	
		view1 = (TextView) findViewById(R.id.appname_li);
		view2 = (EditText) findViewById(R.id.time_set_li);
		view = (TextView) findViewById(R.id.running_li);
		final Intent intent = getIntent();
		
		Uri uri = Uri.parse("/data/data/com.gg.manager/files/"+intent.getStringExtra("appname"));
		imgv.setImageURI(uri);
		view1.setText(intent.getStringExtra("appname"));
		view.setText(intent.getStringExtra("runningtime") + "秒");
		findViewById(R.id.okand).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					int u = Integer.parseInt(view2.getText().toString());
					reconder.update(intent.getStringExtra("appname"), "set_time",u);
					Limit.this.finish();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "输入不够规范",
							Toast.LENGTH_LONG).show();
					return;
				}
			}
		});
	}
}
