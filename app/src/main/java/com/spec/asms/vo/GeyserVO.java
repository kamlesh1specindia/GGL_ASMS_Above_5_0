package com.spec.asms.vo;

import com.spec.asms.common.Constants;

public class GeyserVO {
	
	private int id;
	private String text = Constants.LABEL_BLANK;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
