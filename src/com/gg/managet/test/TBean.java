package com.gg.managet.test;

public class TBean {

	private String name;
	private boolean isWhite;

	public TBean() {

	}

	public TBean(String name,boolean isWhite) {
		super();
		this.name = name;
		this.isWhite = isWhite;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isWhite() {
		return isWhite;
	}

	public void setWhite(boolean isWhite) {
		this.isWhite = isWhite;
	};

}
