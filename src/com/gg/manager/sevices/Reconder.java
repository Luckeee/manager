package com.gg.manager.sevices;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Reconder extends SQLiteOpenHelper {

	final private static String TABLE_NAME = "recond";
	SQLiteDatabase db;
	Context context;

	public Reconder(Context context) {
		super(context, TABLE_NAME+".db", null, 1);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL("create table "
				+ TABLE_NAME
				+ "(app_name text not null UNIQUE,running_time integer,set_time integer,is_white integer  )");

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		arg0.execSQL("drop table if exits recond");
		onCreate(arg0);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);

	}

	public void initrecond() {
		db = this.getWritableDatabase();
		List<PackageInfo> packages = context.getPackageManager()
				.getInstalledPackages(0);
		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageInfo = packages.get(i);
			String app_name = packageInfo.applicationInfo.loadLabel(
					context.getPackageManager()).toString();
			newrecond(app_name);
			writeIcon(context, packageInfo, app_name);
		}

	}

	public void writeIcon(Context context2, PackageInfo packageInfo,
			String app_name) {
		// TODO Auto-generated method stub
		try {
			FileOutputStream fout = context.openFileOutput(app_name,
					Context.MODE_PRIVATE);
			Drawable icon = packageInfo.applicationInfo.loadIcon(context
					.getPackageManager());
			Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
			byte[] bytes = os.toByteArray();
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void newrecond(String app_name) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put("app_name", app_name);
		cv.put("running_time", 0);
		cv.put("set_time", 0);
		cv.put("is_white", 0);
		db.insert("recond", null, cv);
	}

	public void delectRecond(String app_name) {
		String sql = "delect from recond where app_name = " + app_name;
		db.execSQL(sql);
	}

	public int get(String app_name, String whitchItem) {
		db = this.getWritableDatabase();
		String sql = "select " + whitchItem + " from recond where app_name='"
				+ app_name + "'";
		int time = 0;
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			time = cursor.getInt(0);
		}
				
				
		return time;
	}

	public void update(String app_name, String whitchItem, int time) {
		db = this.getWritableDatabase();
		String sql = "update recond set " + whitchItem + "=" + time
				+ " where app_name='" + app_name + "'";
		db.execSQL(sql);
	}

	public ArrayList<HashMap<String, Object>> getRecond() {
		db = this.getWritableDatabase();
		String sql = "select *from recond order by running_time desc";
		Cursor cursor = db.rawQuery(sql, null);
		int columnsSize = cursor.getColumnCount();
		ArrayList<HashMap<String, Object>> listData = new ArrayList<HashMap<String, Object>>();
		while (cursor.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < columnsSize; i++) {
				map.put("app_name", cursor.getString(0));
				map.put("running_time", cursor.getString(1));
				map.put("set_time", cursor.getString(2));
				map.put("is_white", cursor.getString(3));
			}
			listData.add(map);
		}
		return listData;
	}

	public int size() {
		// TODO Auto-generated method stub
		db = this.getWritableDatabase();
		int columnsSize = 0;
		Cursor c = db.rawQuery("select * from recond ", null);
		while (c.moveToNext()) {
			columnsSize ++;
		}
		return columnsSize;
	}
}

//
