package com.spec.asms.vo;

import com.spec.asms.common.Constants;

public class RccVO 
{
	private static int rccId;
	private static int maintainanceId;
	private static String isSandFilling = Constants.LABEL_BLANK;
	private static String isWorking = Constants.LABEL_BLANK;
	private static String msNail = Constants.LABEL_BLANK;
	private static String msNut = Constants.LABEL_BLANK;
	private static String rccGuard = Constants.LABEL_BLANK;
	private static String msNailChargable = Constants.LABEL_BLANK;
	private static String msNutChargable = Constants.LABEL_BLANK;
	private static String rccGuardChargable = Constants.LABEL_BLANK;
	private static String msNailSuppliedBy = Constants.LABEL_BLANK;
	private static String msNutSuppliedBy = Constants.LABEL_BLANK;
	private static String rccGuardSuppliedBy = Constants.LABEL_BLANK;
	
	public static String getIsSandFilling() {
		return isSandFilling;
	}
	public static void setIsSandFilling(String isSandFilling) {
		RccVO.isSandFilling = isSandFilling;
	}
	public static String getMsNailChargable() {
		return msNailChargable;
	}
	public static void setMsNailChargable(String msNailChargable) {
		RccVO.msNailChargable = msNailChargable;
	}
	public static String getMsNutChargable() {
		return msNutChargable;
	}
	public static void setMsNutChargable(String msNutChargable) {
		RccVO.msNutChargable = msNutChargable;
	}
	public static String getRccGuardChargable() {
		return rccGuardChargable;
	}
	public static void setRccGuardChargable(String rccGuardChargable) {
		RccVO.rccGuardChargable = rccGuardChargable;
	}
	public static String getMsNailSuppliedBy() {
		return msNailSuppliedBy;
	}
	public static void setMsNailSuppliedBy(String msNailSuppliedBy) {
		RccVO.msNailSuppliedBy = msNailSuppliedBy;
	}
	public static String getMsNutSuppliedBy() {
		return msNutSuppliedBy;
	}
	public static void setMsNutSuppliedBy(String msNutSuppliedBy) {
		RccVO.msNutSuppliedBy = msNutSuppliedBy;
	}
	public static String getRccGuardSuppliedBy() {
		return rccGuardSuppliedBy;
	}
	public static void setRccGuardSuppliedBy(String rccGuardSuppliedBy) {
		RccVO.rccGuardSuppliedBy = rccGuardSuppliedBy;
	}

	private static String coachScrew = Constants.LABEL_BLANK;
	private static int createdBy;
	private static String createDon;
	private static int updatedBy;
	private static String updateDon;
	private static int isSync;
	
	
	public static int getRccId() {
		return rccId;
	}
	public static void setRccId(int rccId) {
		RccVO.rccId = rccId;
	}
	public static int getMaintainanceId() {
		return maintainanceId;
	}
	public static void setMaintainanceId(int maintainanceId) {
		RccVO.maintainanceId = maintainanceId;
	}
	public static String getIsWorking() {
		return isWorking;
	}
	public static void setIsWorking(String isWorking) {
		RccVO.isWorking = isWorking;
	}
	public static String getMsNail() {
		return msNail;
	}
	public static void setMsNail(String msNail) {
		RccVO.msNail = msNail;
	}
	public static String getMsNut() {
		return msNut;
	}
	public static void setMsNut(String msNut) {
		RccVO.msNut = msNut;
	}
	public static String getCoachScrew() {
		return coachScrew;
	}
	public static void setCoachScrew(String coachScrew) {
		RccVO.coachScrew = coachScrew;
	}
	public static int getCreatedBy() {
		return createdBy;
	}
	public static void setCreatedBy(int createdBy) {
		RccVO.createdBy = createdBy;
	}
	public static String getCreateDon() {
		return createDon;
	}
	public static void setCreateDon(String createDon) {
		RccVO.createDon = createDon;
	}
	public static int getUpdatedBy() {
		return updatedBy;
	}
	public static void setUpdatedBy(int updatedBy) {
		RccVO.updatedBy = updatedBy;
	}
	public static String getUpdateDon() {
		return updateDon;
	}
	public static void setUpdateDon(String updateDon) {
		RccVO.updateDon = updateDon;
	}
	public static int getIsSync() {
		return isSync;
	}
	public static void setIsSync(int isSync) {
		RccVO.isSync = isSync;
	}
	public static String getRccGuard() {
		return rccGuard;
	}
	public static void setRccGuard(String rccGuard) {
		RccVO.rccGuard = rccGuard;
	}
	
	public static void cleanRCCVO()
	{
		
		   rccId = 0;
		   maintainanceId = 0;
		   coachScrew = Constants.LABEL_BLANK;
		   createdBy = 0;
		   createDon = Constants.LABEL_BLANK;
		   updatedBy = 0;
		   updateDon = Constants.LABEL_BLANK;
		   isSync = 0;
		  
		   isSandFilling = Constants.LABEL_BLANK;
		   isWorking = Constants.LABEL_BLANK;
		   msNail = Constants.LABEL_BLANK;
		   msNut = Constants.LABEL_BLANK;
		   rccGuard = Constants.LABEL_BLANK;
		   msNailChargable = Constants.LABEL_BLANK;
		   msNutChargable = Constants.LABEL_BLANK;
		   rccGuardChargable = Constants.LABEL_BLANK;
		   msNailSuppliedBy = Constants.LABEL_BLANK;
		   msNutSuppliedBy = Constants.LABEL_BLANK;
		   rccGuardSuppliedBy = Constants.LABEL_BLANK;
	}
}
