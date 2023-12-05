package com.spec.asms.vo;

import com.spec.asms.common.Constants;

public class PaintingVO 
{
	private static int paintingId;
	private static int maintainanceId;
	private static String isPaintingWork = Constants.LABEL_BLANK;
	private static String paint = Constants.LABEL_BLANK;
	private static String isOringWork = Constants.LABEL_BLANK;
	private static String orMeter = Constants.LABEL_BLANK;
	private static String orDomRegulator = Constants.LABEL_BLANK;
	private static String orAudcoGland = Constants.LABEL_BLANK;
	private static int createdBy;
	private static String createDon;
	private static int updatedBy;
	private static String updateDon;
	private static int isSync;
	
	
	public static String getIsPaintingWork() {
		return isPaintingWork;
	}
	public static void setIsPaintingWork(String isPaintingWork) {
		PaintingVO.isPaintingWork = isPaintingWork;
	}	
	public static String getIsOringWork() {
		return isOringWork;
	}
	public static void setIsOringWork(String oRingSetisfectory) {
		PaintingVO.isOringWork = oRingSetisfectory;
	}
	public static String getOrMeter() {
		return orMeter;
	}
	public static void setOrMeter(String orMeter) {
		PaintingVO.orMeter = orMeter;
	}
	public static String getOrDomRegulator() {
		return orDomRegulator;
	}
	public static void setOrDomRegulator(String orDomRegulator) {
		PaintingVO.orDomRegulator = orDomRegulator;
	}
	public static String getOrAudcoGland() {
		return orAudcoGland;
	}
	public static void setOrAudcoGland(String orAudcoGland) {
		PaintingVO.orAudcoGland = orAudcoGland;
	}
	
	public static String getCreateDon() {
		return createDon;
	}
	public static void setCreateDon(String createDon) {
		PaintingVO.createDon = createDon;
	}
	
	public static void setUpdateDon(String updateDon) {
		PaintingVO.updateDon = updateDon;
	}
	
	public static String getPaint() {
		return paint;
	}
	public static void setPaint(String paint) {
		PaintingVO.paint = paint;
	}
	public static int getPaintingId() {
		return paintingId;
	}
	public static void setPaintingId(int paintingId) {
		PaintingVO.paintingId = paintingId;
	}
	public static int getMaintainanceId() {
		return maintainanceId;
	}
	public static void setMaintainanceId(int maintainanceId) {
		PaintingVO.maintainanceId = maintainanceId;
	}
	public static int getCreatedBy() {
		return createdBy;
	}
	public static void setCreatedBy(int createdBy) {
		PaintingVO.createdBy = createdBy;
	}
	public static int getUpdatedBy() {
		return updatedBy;
	}
	public static void setUpdatedBy(int updatedBy) {
		PaintingVO.updatedBy = updatedBy;
	}
	public static int getIsSync() {
		return isSync;
	}
	public static void setIsSync(int isSync) {
		PaintingVO.isSync = isSync;
	}
	public static String getUpdateDon() {
		return updateDon;
	}
	
	public static void cleanPaintingVO()
	{		
		  setPaintingId(0);
		  setMaintainanceId(0);
		  setIsPaintingWork(Constants.LABEL_BLANK);
		  setPaint(Constants.LABEL_BLANK);
		  setIsOringWork(Constants.LABEL_BLANK);
		  setOrMeter(Constants.LABEL_BLANK);
		  setOrDomRegulator(Constants.LABEL_BLANK);
		  setOrAudcoGland(Constants.LABEL_BLANK);
		  createdBy = 0;
		  createDon = Constants.LABEL_BLANK;
		  updatedBy = 0;
		  updateDon = Constants.LABEL_BLANK;
		  isSync = 0;
	}
}
