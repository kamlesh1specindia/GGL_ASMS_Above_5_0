package com.spec.asms.dto;

import com.spec.asms.common.Constants;


/**
 * @author bhavikg
 * value object for GI Fitting
 */
public class GIFittingMSTDTO{
	
	private int giFittingId;
	private String text = Constants.LABEL_BLANK;
	private String codeGroup;
	private String objectCode;
	private int objectId;
	
	public int getGiFittingId() {
		return giFittingId;
	}
	public void setGiFittingId(int giFittingId) {
		this.giFittingId = giFittingId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCodeGroup() {
		return codeGroup;
	}
	public void setCodeGroup(String codeGroup) {
		this.codeGroup = codeGroup;
	}
	public String getObjectCode() {
		return objectCode;
	}
	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}
	public int getObjectId() {
		return objectId;
	}
	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}
}
