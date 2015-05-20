package com.gg.manager.sevices;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class MyBroadCast extends BroadcastReceiver {

	public MyBroadCast() {
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences  setting = context.getSharedPreferences("setting",Context.MODE_PRIVATE);
		Boolean iswork = setting.getBoolean("iswork", false);
		if (iswork) {
			Intent startService = new Intent();
			startService.setAction("com.gg.manager.services.BACK_WATCH");
			context.startService(startService);
			Log.e("¿ª»úÆô¶¯", "com.gg.manager.services.BACK_WATCH");
		}
	}
}