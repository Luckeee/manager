package com.gg.managet.test;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Reconder extends SQLiteOpenHelper {

	final private static String TABLE_NAME="in-and-out";
	SQLiteDatabase db;
	Context context;
	public Reconder(Context context) {
		super(context,TABLE_NAME , null, 1);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL("create table "+TABLE_NAME+"(app_name text not null UNIQUE,runnng_time integer,set_time integer,is_white integer )");
		initrecond();
	}

	private void initrecond() {
		List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageInfo = packages.get(i);
			String appName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
			newrecond(appName,null,  null, null);
			writeIcon(context, packageInfo, appName);
		}
		
	}

	private void newrecond(String app_name, Object object,Object object1,Object object2) {
		// TODO Auto-generated method stub
		db=this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("app_name", app_name);
		cv.putNull("runnng_time");
		cv.putNull("set_time");
		cv.putNull("is_white");
		db.insert("recond", null, cv);
		db.close();
	}

	private void writeIcon(Context context2, PackageInfo packageInfo,
			String appName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
