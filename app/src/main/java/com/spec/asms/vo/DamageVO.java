package com.spec.asms.vo;

import com.spec.asms.common.Constants;

/**
 * value object for damage
 * @author mansiv
 *
 */
public class DamageVO {

	private int damageId;
	private int objectId;
	private String damageCode = Constants.LABEL_BLANK;
	private String fieldNameGuj = Constants.LABEL_BLANK;
	private String fieldName = Constants.LABEL_BLANK;

	public int getDamageId() {
		return damageId;
	}
	public void setDamageId(int damageId) {
		this.damageId = damageId;
	}

	public int getObjectId() {
		return objectId;
	}
	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public String getDamageCode() {
		return damageCode;
	}
	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
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
		damageId = 0;
		objectId = 0;
		damageCode = Constants.LABEL_BLANK;
		fieldNameGuj = Constants.LABEL_BLANK;
		fieldName = Constants.LABEL_BLANK;
	}
}