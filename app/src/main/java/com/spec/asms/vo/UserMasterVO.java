package com.spec.asms.vo;

/**
 * Value object for User Information.
 * @author jenisha
 *
 */
public class UserMasterVO {

	private int userID;
	private int roleID;
	private String userName;
	private String password;
	private int createdBy;
	private String createdOn;
	private int updatedBy;
	private String updatedOn;
	private String lastLogintime;
	private int issync;
	private int islock;
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getLastLogintime() {
		return lastLogintime;
	}
	public void setLastLogintime(String lastLogintime) {
		this.lastLogintime = lastLogintime;
	}
	public int getIssync() {
		return issync;
	}
	public void setIssync(int issync) {
		this.issync = issync;
	}
	public int getIslock() {
		return islock;
	} 
	public void setIslock(int islock) {
		this.islock = islock;
	}
	
}
