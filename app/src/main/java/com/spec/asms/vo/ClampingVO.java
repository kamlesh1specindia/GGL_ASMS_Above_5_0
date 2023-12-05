package com.spec.asms.vo;

import com.spec.asms.common.Constants;

/**
 * @author bhavikg
 * value object for Clamping
 *
 */
public class ClampingVO {
	
	private static int clampingId;
	private static int maintainanceId;
	private static String isWorking = Constants.LABEL_BLANK;
	private static String pipelineProtectionClamp = Constants.LABEL_BLANK;
	private static String ChrgClamp1by2 = Constants.LABEL_BLANK;
	private static String Clamp1by2 = Constants.LABEL_BLANK;
	private static String ChrgClamp1 = Constants.LABEL_BLANK;
	private static String Clamp1 = Constants.LABEL_BLANK;
	private static String ChrgCheeseHeadScrew = Constants.LABEL_BLANK;
	private static String CheeseHeadScrew = Constants.LABEL_BLANK;
	private static String ChrgWoodScrew = Constants.LABEL_BLANK;
	private static String WoodScrew = Constants.LABEL_BLANK;
	
	private static String ChrgRoulPlug = Constants.LABEL_BLANK;
	private static String RoulPlug = Constants.LABEL_BLANK;
	
	private static String SplrClamp1by2 = Constants.LABEL_BLANK;
	private static String SplrClamp1 = Constants.LABEL_BLANK;
	private static String SplrCheeseHeadScrew = Constants.LABEL_BLANK;
	private static String SplrWoodScrew = Constants.LABEL_BLANK;
	private static String SplrRoulPlug = Constants.LABEL_BLANK;
	
	
	/*private static String clampHalf = Constants.LABEL_BLANK;
	private static String clampOne = Constants.LABEL_BLANK;
	private static String cheeseHeadScrew = Constants.LABEL_BLANK;
	private static String woodScrew = Constants.LABEL_BLANK;
	private static String roulPlug = Constants.LABEL_BLANK;*/
	
	private static int createdBy;
	private static String createdOn;
	private static int updatedBy;
	private static String updatedOn;
	private static int isSync;
	
	public static String getChrgClamp1by2() {
		return ChrgClamp1by2;
	}
	public static void setChrgClamp1by2(String chrgClamp1by2) {
		ChrgClamp1by2 = chrgClamp1by2;
	}
	public static String getClamp1by2() {
		return Clamp1by2;
	}
	public static void setClamp1by2(String clamp1by2) {
		Clamp1by2 = clamp1by2;
	}
	public static String getChrgClamp1() {
		return ChrgClamp1;
	}
	public static void setChrgClamp1(String chrgClamp1) {
		ChrgClamp1 = chrgClamp1;
	}
	public static String getClamp1() {
		return Clamp1;
	}
	public static void setClamp1(String clamp1) {
		Clamp1 = clamp1;
	}
	public static String getChrgCheeseHeadScrew() {
		return ChrgCheeseHeadScrew;
	}
	public static void setChrgCheeseHeadScrew(String chrgCheeseHeadScrew) {
		ChrgCheeseHeadScrew = chrgCheeseHeadScrew;
	}
	public static String getChrgWoodScrew() {
		return ChrgWoodScrew;
	}
	public static void setChrgWoodScrew(String chrgWoodScrew) {
		ChrgWoodScrew = chrgWoodScrew;
	}
	public static String getChrgRoulPlug() {
		return ChrgRoulPlug;
	}
	public static void setChrgRoulPlug(String chrgRoulPlug) {
		ChrgRoulPlug = chrgRoulPlug;
	}
	public static String getSplrClamp1by2() {
		return SplrClamp1by2;
	}
	public static void setSplrClamp1by2(String splrClamp1by2) {
		SplrClamp1by2 = splrClamp1by2;
	}
	public static String getSplrClamp1() {
		return SplrClamp1;
	}
	public static void setSplrClamp1(String splrClamp1) {
		SplrClamp1 = splrClamp1;
	}
	public static String getSplrCheeseHeadScrew() {
		return SplrCheeseHeadScrew;
	}
	public static void setSplrCheeseHeadScrew(String splrCheeseHeadScrew) {
		SplrCheeseHeadScrew = splrCheeseHeadScrew;
	}
	public static String getSplrWoodScrew() {
		return SplrWoodScrew;
	}
	public static void setSplrWoodScrew(String splrWoodScrew) {
		SplrWoodScrew = splrWoodScrew;
	}
	public static String getSplrRoulPlug() {
		return SplrRoulPlug;
	}
	public static void setSplrRoulPlug(String splrRoulPlug) {
		SplrRoulPlug = splrRoulPlug;
	}

	
	
	
	public static int getClampingId() {
		return clampingId;
	}
	public static void setClampingId(int clampingId) {
		ClampingVO.clampingId = clampingId;
	}
	public static int getMaintainanceId() {
		return maintainanceId;
	}
	public static void setMaintainanceId(int maintainanceId) {
		ClampingVO.maintainanceId = maintainanceId;
	}
	public static String getIsWorking() {
		return isWorking;
	}
	public static void setIsWorking(String isWorking) {
		ClampingVO.isWorking = isWorking;
	}
	
	/*public static String getClampHalf() {
		return clampHalf;
	}
	public static void setClampHalf(String clampHalf) {
		ClampingVO.clampHalf = clampHalf;
	}
	public static String getCheeseHeadScrew() {
		return cheeseHeadScrew;
	}
	public static void setCheeseHeadScrew(String cheeseHeadScrew) {
		ClampingVO.cheeseHeadScrew = cheeseHeadScrew;
	}
	public static String getWoodScrew() {
		return woodScrew;
	}
	public static void setWoodScrew(String woodScrew) {
		ClampingVO.woodScrew = woodScrew;
	}
	public static String getRoulPlug() {
		return roulPlug;
	}
	public static void setRoulPlug(String roulPlug) {
		ClampingVO.roulPlug = roulPlug;
	}
	*/
	public static int getCreatedBy() {
		return createdBy;
	}
	public static void setCreatedBy(int createdBy) {
		ClampingVO.createdBy = createdBy;
	}
	public static String getCreatedOn() {
		return createdOn;
	}
	public static void setCreatedOn(String createdOn) {
		ClampingVO.createdOn = createdOn;
	}
	public static int getUpdatedBy() {
		return updatedBy;
	}
	public static void setUpdatedBy(int updatedBy) {
		ClampingVO.updatedBy = updatedBy;
	}
	public static String getUpdatedOn() {
		return updatedOn;
	}
	public static void setUpdatedOn(String updatedOn) {
		ClampingVO.updatedOn = updatedOn;
	}
	public static int getIsSync() {
		return isSync;
	}
	public static void setIsSync(int isSync) {
		ClampingVO.isSync = isSync;
	}
	
/*	public static String getClampOne() {
		return clampOne;
	}
	public static void setClampOne(String clampOne) {
		ClampingVO.clampOne = clampOne;
	}*/
	
	public static void cleanClampingVO()
	{
		
		clampingId = 0;
		maintainanceId = 0;
		isWorking = Constants.LABEL_BLANK;
		/*
		 * clampHalf = Constants.LABEL_BLANK; cheeseHeadScrew =
		 * Constants.LABEL_BLANK; woodScrew = Constants.LABEL_BLANK; roulPlug =
		 * Constants.LABEL_BLANK;
		 */
	/*	pipelineProtectionClamp = Constants.LABEL_BLANK;
		ChrgClamp1by2 = Constants.LABEL_BLANK;
		Clamp1by2 = Constants.LABEL_BLANK;
		ChrgClamp1 = Constants.LABEL_BLANK;
		Clamp1 = Constants.LABEL_BLANK;
		ChrgCheeseHeadScrew = Constants.LABEL_BLANK;
		CheeseHeadScrew = Constants.LABEL_BLANK;
		ChrgWoodScrew = Constants.LABEL_BLANK;
		WoodScrew = Constants.LABEL_BLANK;

		ChrgRoulPlug = Constants.LABEL_BLANK;
		RoulPlug = Constants.LABEL_BLANK;

		SplrClamp1by2 = Constants.LABEL_BLANK;
		SplrClamp1 = Constants.LABEL_BLANK;
		SplrCheeseHeadScrew = Constants.LABEL_BLANK;
		SplrWoodScrew = Constants.LABEL_BLANK;
		SplrRoulPlug = Constants.LABEL_BLANK;*/

		isWorking = Constants.LABEL_BLANK;
		pipelineProtectionClamp = Constants.LABEL_BLANK;
		ChrgClamp1by2 = Constants.LABEL_BLANK;
		Clamp1by2 = Constants.LABEL_BLANK;
		ChrgClamp1 = Constants.LABEL_BLANK;
		Clamp1 = Constants.LABEL_BLANK;
		ChrgCheeseHeadScrew = Constants.LABEL_BLANK;
		CheeseHeadScrew = Constants.LABEL_BLANK;
		ChrgWoodScrew = Constants.LABEL_BLANK;
		WoodScrew = Constants.LABEL_BLANK;

		ChrgRoulPlug = Constants.LABEL_BLANK;
		RoulPlug = Constants.LABEL_BLANK;

		SplrClamp1by2 = Constants.LABEL_BLANK;
		SplrClamp1 = Constants.LABEL_BLANK;
		SplrCheeseHeadScrew = Constants.LABEL_BLANK;
		SplrWoodScrew = Constants.LABEL_BLANK;
		SplrRoulPlug = Constants.LABEL_BLANK;

		createdBy = 0;
		createdOn = Constants.LABEL_BLANK;
	
		updatedBy = 0;
		updatedOn = Constants.LABEL_BLANK;
		isSync = 0;
		 
		  
	}
	public static String getCheeseHeadScrew() {
		return CheeseHeadScrew;
	}
	public static void setCheeseHeadScrew(String cheeseHeadScrew) {
		CheeseHeadScrew = cheeseHeadScrew;
	}
	public static String getWoodScrew() {
		return WoodScrew;
	}
	public static void setWoodScrew(String woodScrew) {
		WoodScrew = woodScrew;
	}
	public static String getRoulPlug() {
		return RoulPlug;
	}
	public static void setRoulPlug(String roulPlug) {
		RoulPlug = roulPlug;
	}
	public static String getPipelineProtectionClamp() {
		return pipelineProtectionClamp;
	}
	public static void setPipelineProtectionClamp(
			String pipelineProtectionClamp1) {
		pipelineProtectionClamp = pipelineProtectionClamp1;
	}
}
