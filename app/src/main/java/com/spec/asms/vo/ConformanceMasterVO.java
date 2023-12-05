package com.spec.asms.vo;

import com.spec.asms.common.Constants;

/**
 * Value object for Conformance Master
 * @author jenisha
 *
 */
public class ConformanceMasterVO {
	
	private int conformanceId;
	private String reason = Constants.LABEL_BLANK;
	
	
	public  int getConformanceID() {
		return conformanceId;
	}
	public  void setConformanceID(int conformanceID) {
		this.conformanceId = conformanceID;
	}
	public   String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	

}
