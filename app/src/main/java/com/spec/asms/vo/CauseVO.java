package com.spec.asms.vo;

import com.spec.asms.common.Constants;

/**
 * value object for damage
 * @author mansiv
 *
 */
public class CauseVO {

	private int causeId;
	private int objectId;
	private String causeCode = Constants.LABEL_BLANK;
	private String fieldNameGuj = Constants.LABEL_BLANK;
	private String fieldName = Constants.LABEL_BLANK;


	public int getCauseId() {
		return causeId;
	}
	public void setCauseId(int causeId) {
		this.causeId = causeId;
	}

	public int getObjectId() {
		return objectId;
	}
	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public String getCauseCode() {
		return causeCode;
	}
	public void setCauseCode(String causeCode) {
		this.causeCode = causeCode;
	}

	public String getFieldNameGuj() {
		return fieldNameGuj;
	}
	public void setFieldNameGuj(String fieldNameGuj) {
		this.fieldNameGuj = fieldNameGuj;
	}

	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void cleanDamageVO(){
		causeId = 0;
		objectId = 0;
		causeCode = Constants.LABEL_BLANK;
		fieldNameGuj = Constants.LABEL_BLANK;
		fieldName = Constants.LABEL_BLANK;
	}
}