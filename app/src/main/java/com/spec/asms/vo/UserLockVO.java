package com.spec.asms.vo;

import com.spec.asms.common.Constants;

public class UserLockVO {
	
	private String isLock = Constants.LABLE_DEFAULT_UNLOCK;
	private String passwordUpdateDate = Constants.LABEL_BLANK;
	
	public String getIsLock() {
		return isLock;
	}
	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}
	public String getPasswordUpdateDate() {
		return passwordUpdateDate;
	}
	public void setPasswordUpdateDate(String passwordUpdateDate) {
		this.passwordUpdateDate = passwordUpdateDate;
	}	
}
