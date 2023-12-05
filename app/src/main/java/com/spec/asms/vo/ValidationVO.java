package com.spec.asms.vo;

import com.spec.asms.common.Constants;

public class ValidationVO {
	
	private int id;
	private String formName = Constants.LABEL_BLANK;
	private String fieldName = Constants.LABEL_BLANK;
	private String minValue = Constants.LABEL_BLANK;
	private String maxValue = Constants.LABEL_BLANK;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getMinValue() {
		return minValue;
	}
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	public String getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	
		
}
