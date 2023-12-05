package com.spec.asms.dto;

import com.spec.asms.common.Constants;
import com.spec.asms.vo.ConformanceDetailVO;

/**
 * 
 * @author jenisha 
 * Value object for Conformance Detail
 * 
 */
public class ConformanceDetailDTO {
	
	private int conformanceId;
	private String text = Constants.LABEL_BLANK;
	private String IsApplicable = Constants.LABEL_BLANK;	
	private int conformancedetailId;
	private int mantainanceId;	
	private int createdBy;
	private String createdOn =  Constants.LABEL_BLANK;
	private int updatedBy;
	private String updatedOn = Constants.LABEL_BLANK;
	private int issync;
	private String isNc = Constants.LABEL_BLANK;
	
	public ConformanceDetailDTO() {
		// TODO Auto-generated constructor stub
	}
		
	public int getConformanceId() {
		return conformanceId;
	}
	public void setConformanceId(int conformanceId) {
		this.conformanceId = conformanceId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIsApplicable() {
		return IsApplicable;
	}
	public void setIsApplicable(String isApplicable) {
		IsApplicable = isApplicable;
	}

	public int getConformancedetailId() {
		return conformancedetailId;
	}

	public void setConformancedetailId(int conformancedetailId) {
		this.conformancedetailId = conformancedetailId;
	}

	public int getMantainanceId() {
		return mantainanceId;
	}

	public void setMantainanceId(int mantainanceId) {
		this.mantainanceId = mantainanceId;
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

	public String getIsNc() {
		return isNc;
	}

	public void setIsNc(String isNc) {
		this.isNc = isNc;
	}
	
	
}
