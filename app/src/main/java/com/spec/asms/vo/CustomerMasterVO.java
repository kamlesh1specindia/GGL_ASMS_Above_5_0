package com.spec.asms.vo;

import com.spec.asms.common.Constants;

/**
 * Value object for Customers
 * @author jenisha
 *
 */
public class CustomerMasterVO {

	private String customerID = Constants.LABEL_BLANK;
	private String customerName = Constants.LABEL_BLANK;
	private String meterNumber = Constants.LABEL_BLANK;
	private String customerAdd = Constants.LABEL_BLANK;
	private int statusId;
	private String walksequence = Constants.LABEL_BLANK;
	private int createdBy;
	private String createdOn = Constants.LABEL_BLANK;
	private int updatedBy;
	private String updatedOn = Constants.LABEL_BLANK;
	private int issync;
	private String maintainanceOrderID = Constants.LABEL_BLANK;
	private String orderID = Constants.LABEL_BLANK;
	private int isDeleted;
	private String mrunit;
	private String societyName;
	private String phoneNumber;
	private String customerStatus;
	private String expiryDate;
	
	public String getCustomerStatus() {
		return customerStatus;
	}
	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public String getCustomername() {
		return customerName;
	}
	public void setCustomername(String customername) {
		this.customerName = customername;
	}
	public String getCustomeradd() {
		return customerAdd;
	}
	public void setCustomeradd(String customeradd) {
		this.customerAdd = customeradd;
	}
		public String getWalksequence() {
		return walksequence;
	}
	public void setWalksequence(String walksequence) {
		this.walksequence = walksequence;
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
	public int getIssync() {
		return issync;
	}
	public void setIssync(int issync) {
		this.issync = issync;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getMaintainanceOrderID() {
		return maintainanceOrderID;
	}
	public void setMaintainanceOrderID(String maintainanceOrderID) {
		this.maintainanceOrderID = maintainanceOrderID;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getMrunit() {
		return mrunit;
	}
	public void setMrunit(String mrunit) {
		this.mrunit = mrunit;
	}
	public String getSocietyName() {
		return societyName;
	}
	public void setSocietyName(String societyName) {
		this.societyName = societyName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getMeterNumber() {
		return meterNumber;
	}
	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}
}
