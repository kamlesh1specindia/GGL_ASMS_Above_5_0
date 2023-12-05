package com.spec.asms.vo;

public class LogVO {
	
	private long datetime;
	private String objectstatus;
	private String submitstatus;
	private String responsestatus;
	private int batchsize;
	private int totalsubmitted;
	private long responsetime;
	private String exception;
	
	public long getDatetime() {
		return datetime;
	}
	public void setDatetime(long datetime) {
		this.datetime = datetime;
	}
	public String getObjectstatus() {
		return objectstatus;
	}
	public void setObjectstatus(String objectstatus) {
		this.objectstatus = objectstatus;
	}
	public String getSubmitstatus() {
		return submitstatus;
	}
	public void setSubmitstatus(String submitstatus) {
		this.submitstatus = submitstatus;
	}
	public String getResponsestatus() {
		return responsestatus;
	}
	public void setResponsestatus(String responsestatus) {
		this.responsestatus = responsestatus;
	}
	public int getBatchsize() {
		return batchsize;
	}
	public void setBatchsize(int batchsize) {
		this.batchsize = batchsize;
	}
	public int getTotalsubmitted() {
		return totalsubmitted;
	}
	public void setTotalsubmitted(int totalsubmitted) {
		this.totalsubmitted = totalsubmitted;
	}
	public long getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(long responsetime) {
		this.responsetime = responsetime;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	

}
