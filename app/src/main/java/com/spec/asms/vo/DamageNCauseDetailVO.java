package com.spec.asms.vo;

import com.spec.asms.common.Constants;

/**
 * 
 * @author jenisha
 *
 */
public class DamageNCauseDetailVO {
	
	private int dtldamagecauseId;
	private int damageId;
	private int objectId;
	private int causeId;
	private String maintainanceOrderId = Constants.LABEL_BLANK;
	
	public int getDtldamagecauseId() {
		return dtldamagecauseId;
	}
	public void setDtldamagecauseId(int dtldamagecauseId) {
		this.dtldamagecauseId = dtldamagecauseId;
	}
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
	public int getCauseId() {
		return causeId;
	}
	public void setCauseId(int causeId) {
		this.causeId = causeId;
	}
	public String getMaintainanceOrderId() {
		return maintainanceOrderId;
	}
	public void setMaintainanceOrderId(String maintainanceOrderId) {
		this.maintainanceOrderId = maintainanceOrderId;
	}
	
	

}
