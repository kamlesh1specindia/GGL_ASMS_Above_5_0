package com.spec.asms.vo;

import com.spec.asms.common.Constants;

public class StatusVO {
	
	private int id;
	private String statusCode = Constants.LABEL_BLANK;
	private String statusName = Constants.LABEL_BLANK;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	
	
}
