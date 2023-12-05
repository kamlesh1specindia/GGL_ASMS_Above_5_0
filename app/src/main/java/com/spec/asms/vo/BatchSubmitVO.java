package com.spec.asms.vo;

/**
 * Batch Submit Vo for Batch Submission information.
 * @author jenisha
 *
 */
public class BatchSubmitVO {
	
	private int batchsubmitid;
	private int totRecords;
	private int successfulRecords;
	private int unsuccessfulRecords;
	private int createdBy;
	private String createdOn;
	
	
	public int getBatchsubmitid() {
		return batchsubmitid;
	}
	public void setBatchsubmitid(int batchsubmitid) {
		this.batchsubmitid = batchsubmitid;
	}
	public int getTotRecords() {
		return totRecords;
	}
	public void setTotRecords(int totRecords) {
		this.totRecords = totRecords;
	}
	public int getSuccessfulRecords() {
		return successfulRecords;
	}
	public void setSuccessfulRecords(int successfulRecords) {
		this.successfulRecords = successfulRecords;
	}
	public int getUnsuccessfulRecords() {
		return unsuccessfulRecords;
	}
	public void setUnsuccessfulRecords(int unsuccessfulRecords) {
		this.unsuccessfulRecords = unsuccessfulRecords;
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


}
