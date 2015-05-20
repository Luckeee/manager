package com.gg.manager.bean;

public class Bean {

	private String app_name;
	private String running_time;
	private String set_time;
	

	public Bean() {

	}

	public Bean( String app_name, String running_time,
			String set_time) {
		super();
		this.app_name = app_name;
		this.running_time = running_time;
		this.set_time = set_time;
	}

	public String getApp_name() {
		return app_name;
	}

	public Bean setApp_name(String app_name) {
		this.app_name = app_name;
		return this;
	}

	public String getRunning_time() {
		return running_time;
	}

	public Bean setRunning_time(String running_time) {
		this.running_time = running_time;
		return this;
	}

	public String getSet_time() {
		return set_time;
	}

	public Bean setSet_time(String set_time) {
		this.set_time = set_time;
		return this;
	}
	
}

	