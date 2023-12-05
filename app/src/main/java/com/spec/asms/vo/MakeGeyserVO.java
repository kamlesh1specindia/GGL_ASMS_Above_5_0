	package com.spec.asms.vo;

import com.spec.asms.common.Constants;

public class MakeGeyserVO 
{
	private static int makeGeyserId;
	private static int maintainanceId;
	private static String isGeyserAvailable = Constants.LABEL_BLANK;
	private static int makeId;
	private static String makeValue = Constants.LABEL_BLANK;
	private static String makeotherText = Constants.LABEL_BLANK;
	private static String isInsideBathroom=Constants.LABEL_BLANK;
	private static int geyserTypeId;
	private static String geyserTypeValue = Constants.LABEL_BLANK;
	private static int isSync;
	private static int createdBy;
	private static String createdOn;
	private static int updatedBy;
	private static String updatedOn;
	
	
	public static String getGeyserTypeValue() {
		return geyserTypeValue;
	}
	public static void setGeyserTypeValue(String geyserTypeValue) {
		MakeGeyserVO.geyserTypeValue = geyserTypeValue;
	}
	public static String getMakeValue() {
		return makeValue;
	}
	public static void setMakeValue(String makeValue) {
		MakeGeyserVO.makeValue = makeValue;
	}
	public static int getMakeGeyserId() {
		return makeGeyserId;
	}
	public static void setMakeGeyserId(int makeGeyserId) {
		MakeGeyserVO.makeGeyserId = makeGeyserId;
	}
	public static int getMaintainanceId() {
		return maintainanceId;
	}
	public static void setMaintainanceId(int maintainanceId) {
		MakeGeyserVO.maintainanceId = maintainanceId;
	}
	public static int getMakeId() {
		return makeId;
	}
	public static void setMakeId(int makeId) {
		MakeGeyserVO.makeId = makeId;
	}
	public static String getMakeotherText() {
		return makeotherText;
	}
	public static void setMakeotherText(String makeotherText) {
		MakeGeyserVO.makeotherText = makeotherText;
	}
	public static String getIsInsideBathroom() {
		return isInsideBathroom;
	}
	public static void setIsInsideBathroom(String isInsideBathroom) {
		MakeGeyserVO.isInsideBathroom = isInsideBathroom;
	}
	public static int getGeyserTypeId() {
		return geyserTypeId;
	}
	public static void setGeyserTypeId(int geyserTypeId) {
		MakeGeyserVO.geyserTypeId = geyserTypeId;
	}
	public static int getCreatedBy() {
		return createdBy;
	}
	public static void setCreatedBy(int createdBy) {
		MakeGeyserVO.createdBy = createdBy;
	}
	public static String getCreatedOn() {
		return createdOn;
	}
	public static void setCreatedOn(String createdOn) {
		MakeGeyserVO.createdOn = createdOn;
	}
	public static int getUpdatedBy() {
		return updatedBy;
	}
	public static void setUpdatedBy(int updatedBy) {
		MakeGeyserVO.updatedBy = updatedBy;
	}
	public static String getUpdatedOn() {
		return updatedOn;
	}
	public static void setUpdatedOn(String updatedOn) {
		MakeGeyserVO.updatedOn = updatedOn;
	}
	public static int getIsSync() {
		return isSync;
	}
	public static void setIsSync(int isSync) {
		MakeGeyserVO.isSync = isSync;
	}
	
	public static void cleanMakeGeyser()
	{
		 makeGeyserId = 0 ;
		 maintainanceId = 0;
		 isGeyserAvailable = Constants.LABEL_BLANK;
		 makeId = 0;
		 makeValue = Constants.LABEL_BLANK;
		 makeotherText = Constants.LABEL_BLANK;
		 isInsideBathroom=Constants.LABEL_BLANK;
		 geyserTypeId = 0;
		 geyserTypeValue = Constants.LABEL_BLANK;
		 isSync = 0;
		 createdBy = 0;
		 createdOn = Constants.LABEL_BLANK;
		 updatedBy = 0;
		 updatedOn = Constants.LABEL_BLANK;
	}
	public static String getIsGeyserAvailable() {
		return isGeyserAvailable;
	}
	public static void setIsGeyserAvailable(String isGeyserAvailable) {
		MakeGeyserVO.isGeyserAvailable = isGeyserAvailable;
	}
}