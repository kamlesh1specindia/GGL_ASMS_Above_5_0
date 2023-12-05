package com.spec.asms.vo;

import com.spec.asms.common.Constants;

/**
 * Value object for Maintenance
 * @author jenisha
 *
 */
public class MaintainanceVO {

	private static int maintainanceId;
	private static int maintainanceOrderId;
	private static String customerId = Constants.LABEL_BLANK;
	private static String date = Constants.LABEL_BLANK;
	private static String time = Constants.LABEL_BLANK;
	private static String statusCode;
	private static int statusId;
	private static int isSync;
	private static int createdBy;
	private static String createdOn = Constants.LABEL_BLANK;
	private static int updatedBy;
	private static String updatedOn = Constants.LABEL_BLANK;
	private static String startTime = Constants.LABEL_BLANK;
	private static String endTime   = Constants.LABEL_BLANK;
	private static String contactNumber = Constants.LABEL_BLANK;
	private static String altContactNumber = Constants.LABEL_BLANK;

	public static String getContactNumber(){return contactNumber;}
	public static String getAltContactNumber() { return altContactNumber;}
	public static void setContactNumber(String contactNumber) {MaintainanceVO.contactNumber = contactNumber;}
	public static void setAltContactNumber(String altContactNumber) {MaintainanceVO.altContactNumber = altContactNumber;}


	public static int getMaintainanceId() {
		return maintainanceId;
	}
	public static void setMaintainanceId(int maintainanceId) {
		MaintainanceVO.maintainanceId = maintainanceId;
	}
	public static int getMaintainanceOrderId() {
		return maintainanceOrderId;
	}
	public static void setMaintainanceOrderId(int maintainanceOrderId) {
		MaintainanceVO.maintainanceOrderId = maintainanceOrderId;
	}
	public static String getCustomerId() {
		return customerId;
	}
	public static void setCustomerId(String customerId) {
		MaintainanceVO.customerId = customerId;
	}
	public static String getDate() {
		return date;
	}
	public static void setDate(String date) {
		MaintainanceVO.date = date;
	}
	public static String getTime() {
		return time;
	}
	public static void setTime(String time) {
		MaintainanceVO.time = time;
	}
	public static String getStatusCode() {
		return statusCode;
	}
	public static void setStatusCode(String statusCode) {
		MaintainanceVO.statusCode = statusCode;
	}
	public static int getIsSync() {
		return isSync;
	}
	public static void setIsSync(int isSync) {
		MaintainanceVO.isSync = isSync;
	}
	public static int getCreatedBy() {
		return createdBy;
	}
	public static void setCreatedBy(int createdBy) {
		MaintainanceVO.createdBy = createdBy;
	}
	public static String getCreatedOn() {
		return createdOn;
	}
	public static void setCreatedOn(String createdOn) {
		MaintainanceVO.createdOn = createdOn;
	}
	public static int getUpdatedBy() {
		return updatedBy;
	}
	public static void setUpdatedBy(int updatedBy) {
		MaintainanceVO.updatedBy = updatedBy;
	}
	public static String getUpdatedOn() {
		return updatedOn;
	}
	public static void setUpdatedOn(String updatedOn) {
		MaintainanceVO.updatedOn = updatedOn;
	}
	public static int getStatusId() {
		return statusId;
	}
	public static void setStatusId(int statusId) {
		MaintainanceVO.statusId = statusId;
	}
	public static String getEndTime() {
		return endTime;
	}
	public static void setEndTime(String endTime) {
		MaintainanceVO.endTime = endTime;
	}
	public static String getStartTime() {
		return startTime;
	}
	public static void setStartTime(String startTime) {
		MaintainanceVO.startTime = startTime;
	}




}
