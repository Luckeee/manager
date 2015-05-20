package com.gg.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gg.manager.sevices.Reconder;
import com.gg.managet.test.TAdapter;
import com.gg.managet.test.TBean;

/**
 * @author i-a
 *
 */
public class SetWhiteList extends Activity {
	ListView whitelist;
	Reconder reconder;
	List<TBean> mDatas;
	TAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_white_list);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setTitle("ÉèÖÃ°×Ãûµ¥");
		reconder = new Reconder(this);
		setlist();

	}

	private void setlist() {
		reconder = new Reconder(this);
		whitelist = (ListView) findViewById(R.id.set_List_white);
		initList();
		whitelist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ToggleButton is = (ToggleButton) arg1.findViewById(R.id.is_white);
				TextView nameapp = (TextView) arg1.findViewById(R.id.appname);
				String appname = nameapp.getText().toString();
				boolean is_white = is.isChecked();
				is.setChecked(!is_white);
				if (is_white) {
					reconder.update(appname, "is_white", 0);
				}else {
					reconder.update(appname, "is_white", 1);
				}
				initList();
				Toast.makeText(getApplicationContext(), ""+appname+"", Toast.LENGTH_LONG).show();
			}
		});
		
	}	
	private void initList(){
		mDatas = new ArrayList<TBean>();
		TBean bean = new TBean();
		ArrayList<HashMap<String, Object>> alldata = reconder.getRecond();
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < reconder.size(); i++) {
			map = alldata.get(i);
			bean = new TBean(map.get("app_name").toString(),map.get("is_white").equals("1"));
			mDatas.add(bean);
		}
		mAdapter = new TAdapter(this, mDatas,0);
		whitelist.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
	}

}
