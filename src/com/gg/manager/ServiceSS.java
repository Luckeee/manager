package com.gg.manager;

import com.gg.manager.sevices.backwatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ServiceSS extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("dfasdf");
		Intent intent2 = new Intent(this, backwatch.class);
		this.stopService(intent2);
	}
}
