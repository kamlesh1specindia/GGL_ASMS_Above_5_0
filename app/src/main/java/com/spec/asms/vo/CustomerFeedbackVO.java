package com.spec.asms.vo;

import com.spec.asms.common.Constants;

public class CustomerFeedbackVO {
	
	private static int customerFeedbackID;
	private static int maintainanceID;
	private static String comments = Constants.LABEL_BLANK;
	private static String signature = Constants.LABEL_BLANK;
	private static String receiptNo = Constants.LABEL_BLANK;
	private static String noticeNo = Constants.LABEL_BLANK;
	private static int createdBy;
	private static String createdOn = Constants.LABEL_BLANK;
	private static int updatedBy;
	private static String updatedOn = Constants.LABEL_BLANK;
	private static int issync;
	
	public static int getCustomerFeedbackID() {
		return customerFeedbackID;
	}
	public static void setCustomerFeedbackID(int customerFeedbackID) {
		CustomerFeedbackVO.customerFeedbackID = customerFeedbackID;
	}
	public static int getMaintainanceID() {
		return maintainanceID;
	}
	public static void setMaintainanceID(int maintainanceID) {
		CustomerFeedbackVO.maintainanceID = maintainanceID;
	}
	public static String getComments() {
		return comments;
	}
	public static void setComments(String comments) {
		CustomerFeedbackVO.comments = comments;
	}
	public static String getSignature() {
		return signature;
	}
	public static void setSignature(String signature) {
		CustomerFeedbackVO.signature = signature;
	}
	public static String getReceiptNo() {
		return receiptNo;
	}
	public static void setReceiptNo(String receiptNo) {
		CustomerFeedbackVO.receiptNo = receiptNo;
	}
	public static String getNoticeNo() {
		return noticeNo;
	}
	public static void setNoticeNo(String noticeNo) {
		CustomerFeedbackVO.noticeNo = noticeNo;
	}
	public static int getCreatedBy() {
		return createdBy;
	}
	public static void setCreatedBy(int createdBy) {
		CustomerFeedbackVO.createdBy = createdBy;
	}
	public static String getCreatedOn() {
		return createdOn;
	}
	public static void setCreatedOn(String createdOn) {
		CustomerFeedbackVO.createdOn = createdOn;
	}
	public static int getUpdatedBy() {
		return updatedBy;
	}
	public static void setUpdatedBy(int updatedBy) {
		CustomerFeedbackVO.updatedBy = updatedBy;
	}
	public static String getUpdatedOn() {
		return updatedOn;
	}
	public static void setUpdatedOn(String updatedOn) {
		CustomerFeedbackVO.updatedOn = updatedOn;
	}
	public static int getIssync() {
		return issync;
	}
	public static void setIssync(int issync) {
		CustomerFeedbackVO.issync = issync;
	}
	
	
	public static void cleanomerFeedbackCustVO()
	{
		 customerFeedbackID = 0;
		 maintainanceID = 0;
		 comments = Constants.LABEL_BLANK;
		 signature = Constants.LABEL_BLANK;
		 receiptNo = Constants.LABEL_BLANK;
		 noticeNo = Constants.LABEL_BLANK;
		 createdBy = 0;
		 createdOn = Constants.LABEL_BLANK;
		 updatedBy = 0;
		 updatedOn = Constants.LABEL_BLANK;
		 issync = 0;
	}
	
	
}
