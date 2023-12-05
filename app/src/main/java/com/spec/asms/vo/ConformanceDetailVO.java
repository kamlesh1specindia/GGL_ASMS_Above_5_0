package com.spec.asms.vo;

import com.spec.asms.common.Constants;

/**
 * 
 * @author jenisha 
 * Value object for Conformance Detail
 * 
 */
public class ConformanceDetailVO {
	
	private static int conformancedetailId;
	private static int mantainanceId;
	private static int conformanceId;
	private static String text = Constants.LABEL_BLANK;
	private static int createdBy;
	private static String createdOn =  Constants.LABEL_BLANK;
	private static int updatedBy;
	private static String updatedOn = Constants.LABEL_BLANK;
	private static int issync;
	private int serviceConformanceID;
	private static String isNC = Constants.LABEL_BLANK;
	
	
	public static int getConformancedetailId() {
		return conformancedetailId;
	}
	public static void setConformancedetailId(int conformancedetailId) {
		ConformanceDetailVO.conformancedetailId = conformancedetailId;
	}
	public static int getMantainanceId() {
		return mantainanceId;
	}
	public static void setMantainanceId(int mantainanceId) {
		ConformanceDetailVO.mantainanceId = mantainanceId;
	}
	public static int getConformanceId() {
		return conformanceId;
	}
	public static void setConformanceId(int conformanceId) {
		ConformanceDetailVO.conformanceId = conformanceId;
	}
	public static String getText() {
		return text;
	}
	public static void setText(String text) {
		ConformanceDetailVO.text = text;
	}
	public static int getCreatedBy() {
		return createdBy;
	}
	public static void setCreatedBy(int createdBy) {
		ConformanceDetailVO.createdBy = createdBy;
	}
	public static String getCreatedOn() {
		return createdOn;
	}
	public static void setCreatedOn(String createdOn) {
		ConformanceDetailVO.createdOn = createdOn;
	}
	public static int getUpdatedBy() {
		return updatedBy;
	}
	public static void setUpdatedBy(int updatedBy) {
		ConformanceDetailVO.updatedBy = updatedBy;
	}
	public static String getUpdatedOn() {
		return updatedOn;
	}
	public static void setUpdatedOn(String updatedOn) {
		ConformanceDetailVO.updatedOn = updatedOn;
	}
	public static int getIssync() {
		return issync;
	}
	public static void setIssync(int issync) {
		ConformanceDetailVO.issync = issync;
	}
	
	
	public static void cleanConformanceVO()
	{
		 conformancedetailId = 0;
		 mantainanceId = 0;
		 conformanceId = 0;
		 text = Constants.LABEL_BLANK;
		 createdBy = 0;
		 createdOn =  Constants.LABEL_BLANK;
		 updatedBy = 0;
		 updatedOn = Constants.LABEL_BLANK;
		 issync = 0;
		 isNC = Constants.LABEL_BLANK;
	}
	public int getServiceConformanceID() {
		return serviceConformanceID;
	}
	public void setServiceConformanceID(int serviceConformanceID) {
		this.serviceConformanceID = serviceConformanceID;
	}
	public static String getIsNC() {
		return isNC;
	}
	public static void setIsNC(String isNC) {
		ConformanceDetailVO.isNC = isNC;
	}
	

}
