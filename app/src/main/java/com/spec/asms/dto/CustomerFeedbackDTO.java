package com.spec.asms.dto;

import com.spec.asms.common.Constants;
import com.spec.asms.vo.CustomerFeedbackVO;

public class CustomerFeedbackDTO {
	
	private int customerFeedbackID;
	private String comments = Constants.LABEL_BLANK;
	private String signature = Constants.LABEL_BLANK;
	private String receiptNo = Constants.LABEL_BLANK;
	private String noticeNo = Constants.LABEL_BLANK;
	
	public CustomerFeedbackDTO() {
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("static-access")
	public CustomerFeedbackDTO(CustomerFeedbackVO customerFeedbackVO){
		
		setComments(customerFeedbackVO.getComments());
		setSignature(customerFeedbackVO.getSignature());
		setReceiptNo(customerFeedbackVO.getReceiptNo());
		setNoticeNo(customerFeedbackVO.getNoticeNo());
		setCustomerFeedbackID(customerFeedbackVO.getCustomerFeedbackID());
	}
	
	public int getCustomerFeedbackID() {
		return customerFeedbackID;
	}
	public void setCustomerFeedbackID(int customerFeedbackID) {
		this.customerFeedbackID = customerFeedbackID;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getNoticeNo() {
		return noticeNo;
	}
	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}
	
	
	
}
