package com.gg.manager.sevices;

import java.util.Timer;
import java.util.TimerTask;
import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;
import com.gg.manager.KillProgress;

public class backwatch extends Service {
	public static boolean isstop = false;
	private Reconder reconder;
	private AppInformation appinfo;
	private int runningTime = 0, settime = 0;
	private String topTask = "manager";
	private Notification notification;
	private NotificationManager notificationManager;
	stopthis thiss;
	Timer timer;
	PackageManager packageManager = null;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		reconder = new Reconder(getApplicationContext());
		appinfo = new AppInformation(getApplicationContext());
		this.packageManager = getPackageManager();

		IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_USER_PRESENT);

		thiss = new stopthis();
		registerReceiver(thiss, filter);

		super.onCreate();

		String service = NOTIFICATION_SERVICE;
		notificationManager = (NotificationManager) getSystemService(service);

		timer = new Timer();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				String string;
				try {
					if (!isstop) {
						string = appinfo.getFirstRunningTaskInfo();
						String progname = appinfo.getFirstRunningProgrecssName(0);
						String appname = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(string, 0));
						runningTime += 3;
						reconder.update(topTask,"running_time", runningTime);
						runningTime = reconder.get(appname,"running_time");
						settime = reconder.get(appname,"set_time");
						topTask = appname;
						int isignore = reconder.get(appname,"is_white");
						if (((settime < runningTime )&& (settime != 0))&& (isignore != 1)) {
							nitifyc(appname, progname);
						}
						System.out.println(string + " " + appname + "     "
								+ runningTime);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0, 3000);
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(thiss);
		timer.cancel();
		super.onDestroy();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	public class stopthis extends BroadcastReceiver {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			if (arg1.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				isstop = true;
			}
			if (arg1.getAction().equals(Intent.ACTION_USER_PRESENT)) {
				isstop = false;
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void nitifyc(String appname, String prog) {

		SharedPreferences setting = getSharedPreferences("setting",
				Context.MODE_PRIVATE);
		boolean istangkuang = setting.getBoolean("istankuang", false);
		boolean issound = setting.getBoolean("issound", false);
		boolean isverbin = setting.getBoolean("isverbin", false);
		notification = new Notification();

		Intent it = new Intent(this, KillProgress.class);
		it.putExtra("progname", prog);
		it.putExtra("appname", appname);
		Log.e(prog, appname);
		PendingIntent pendintent = PendingIntent.getActivity(this, 0, it,
				PendingIntent.FLAG_UPDATE_CURRENT);

		if (isverbin) {
			notification.defaults = Notification.DEFAULT_VIBRATE;
			long[] vibrate = { 0, 100 };
			notification.vibrate = vibrate;
		}
		if (issound) {
			notification.defaults = Notification.DEFAULT_SOUND;
		}
		if (istangkuang) {
			notification.icon = R.drawable.btn_plus;
			notification.setLatestEventInfo(getApplicationContext(), "警告",
					appname + "应用使用时长超时", pendintent);
		}
		notificationManager.notify(1, notification);
	}
}
