package com.spec.asms.vo;

import com.spec.asms.common.Constants;

/**
 * @author bhavikg
 * value object for GI Fitting
 */
public class GIFittingVO {
	private static int giFittingId;
	private static int testingId;
	private static String isWorking = Constants.LABEL_BLANK;
	private static String elbow = Constants.LABEL_BLANK;
	private static String tee = Constants.LABEL_BLANK;
	private static String hexNipple= Constants.LABEL_BLANK;
	private static String union= Constants.LABEL_BLANK;
	private static String plug = Constants.LABEL_BLANK;
	private static int createdBy;
	private static String createdOn;
	private static int updatedBy;
	private static String updatedOn;
	private static String gicap= Constants.LABEL_BLANK;
	private static String gicoupling = Constants.LABEL_BLANK;
	private static int isSync;
	
	public static String getGicap() {
		return gicap;
	}
	public static void setGicap(String gicap) {
		GIFittingVO.gicap = gicap;
	}
	public static String getGicoupling() {
		return gicoupling;
	}
	public static void setGicoupling(String gicoupling) {
		GIFittingVO.gicoupling = gicoupling;
	}
	
	public static int getGiFittingId() {
		return giFittingId;
	}
	public static void setGiFittingId(int giFittingId) {
		GIFittingVO.giFittingId = giFittingId;
	}
	
	public static String getIsWorking() {
		return isWorking;
	}
	public static void setIsWorking(String isWorking) {
		GIFittingVO.isWorking = isWorking;
	}
	public static String getElbow() {
		return elbow;
	}
	public static void setElbow(String elbow) {
		GIFittingVO.elbow = elbow;
	}
	public static String getTee() {
		return tee;
	}
	public static void setTee(String tee) {
		GIFittingVO.tee = tee;
	}
	public static String getHexNipple() {
		return hexNipple;
	}
	public static void setHexNipple(String hexNipple) {
		GIFittingVO.hexNipple = hexNipple;
	}
	public static String getUnion() {
		return union;
	}
	public static void setUnion(String union) {
		GIFittingVO.union = union;
	}
	public static String getPlug() {
		return plug;
	}
	public static void setPlug(String plug) {
		GIFittingVO.plug = plug;
	}
	public static int getCreatedBy() {
		return createdBy;
	}
	public static void setCreatedBy(int createdBy) {
		GIFittingVO.createdBy = createdBy;
	}
	public static String getCreatedOn() {
		return createdOn;
	}
	public static void setCreatedOn(String createdOn) {
		GIFittingVO.createdOn = createdOn;
	}
	public static int getUpdatedBy() {
		return updatedBy;
	}
	public static void setUpdatedBy(int updatedBy) {
		GIFittingVO.updatedBy = updatedBy;
	}
	public static String getUpdatedOn() {
		return updatedOn;
	}
	public static void setUpdatedOn(String updatedOn) {
		GIFittingVO.updatedOn = updatedOn;
	}
	public static int getIsSync() {
		return isSync;
	}
	public static void setIsSync(int isSync) {
		GIFittingVO.isSync = isSync;
	}
	
	public static int getTestingId() {
		return testingId;
	}
	public static void setTestingId(int testingId) {
		GIFittingVO.testingId = testingId;
	}
	
	public static void cleanGiFitting()
	{
		 giFittingId = 0;
		 testingId = 0;
		 isWorking = Constants.LABEL_BLANK;
		 elbow = Constants.LABEL_BLANK;
		 tee = Constants.LABEL_BLANK;
		 hexNipple= Constants.LABEL_BLANK;
		 union= Constants.LABEL_BLANK;
		 plug = Constants.LABEL_BLANK;
		 createdBy = 0;
		 createdOn = Constants.LABEL_BLANK;
		 updatedBy = 0;
		 updatedOn = Constants.LABEL_BLANK;
		 gicap= Constants.LABEL_BLANK;
		 gicoupling = Constants.LABEL_BLANK;
		 isSync = 0;
	}
}
