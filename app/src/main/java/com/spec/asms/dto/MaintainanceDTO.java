package com.spec.asms.dto;


import java.util.Date;


import android.util.Log;

import com.spec.asms.common.Constants;
import com.spec.asms.vo.MaintainanceVO;


public class MaintainanceDTO {

	private int maintainanceId;
	private int maintainanceOrderId;
	private String customerId =Constants.LABEL_BLANK;
	private long date;
	private long time;
	private String statusCode = Constants.LABEL_BLANK;
	private String startTime = Constants.LABEL_BLANK;
	private String endTime = Constants.LABEL_BLANK;
	private int isSync;
	private int createdBy;
	private String createdOn = Constants.LABEL_BLANK;
	private int updatedBy;
	private String updatedOn = Constants.LABEL_BLANK;

	public String getContactNo() {
		return contactNo;
	}

	public String getAltContactNo() {
		return AltContactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public void setAltContactNo(String altContactNo) {
		AltContactNo = altContactNo;
	}

	private String contactNo = Constants.LABEL_BLANK, AltContactNo = Constants.LABEL_BLANK;
	public MaintainanceDTO(){

	}

	@SuppressWarnings("static-access")
	public MaintainanceDTO(MaintainanceVO maintainanceVO){

		if(maintainanceVO.getDate().equals(Constants.LABEL_BLANK))
			setDate(0);
		else{
			Date date = new Date(maintainanceVO.getDate());
			setDate(date.getTime());
			Log.d("MaintainanceDTO", "Asli Date::: "+maintainanceVO.getDate());
			Log.d("MaintainanceDTO", "date.getDate()::: "+date.getDate());
			Log.d("MaintainanceDTO", "date.getTime()::: "+date.getTime());
		}
		Log.d("MaintainanceDTO", "maintainanceVO.getMaintainanceId()::: "+maintainanceVO.getMaintainanceId());
		setMaintainanceId(maintainanceVO.getMaintainanceId());
		setMaintainanceOrderId(maintainanceVO.getMaintainanceOrderId());
		setCustomerId(maintainanceVO.getCustomerId());
		setStatusCode(maintainanceVO.getStatusCode());
		setContactNo(maintainanceVO.getContactNumber());
		setAltContactNo(maintainanceVO.getAltContactNumber());
	}


	public int getIsSync() {
		return isSync;
	}

	public void setIsSync(int isSync) {
		this.isSync = isSync;
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

	public int getMaintainanceId() {
		return maintainanceId;
	}

	public void setMaintainanceId(int maintainanceId) {
		this.maintainanceId = maintainanceId;
	}

	public int getMaintainanceOrderId() {
		return maintainanceOrderId;
	}

	public void setMaintainanceOrderId(int maintainanceOrderId) {
		this.maintainanceOrderId = maintainanceOrderId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
