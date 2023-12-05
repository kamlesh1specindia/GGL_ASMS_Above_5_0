package com.spec.asms.dto;

import com.spec.asms.common.Constants;

/**
 * @author bhavikg
 * value object for Clamping
 *
 */
public class ClampingDTO {

	private int clampingId;	
	private String ClampingWorkSatisfactory = Constants.LABEL_BLANK;
	
	private String pipelineProtectionClamp = Constants.LABEL_BLANK;
	private String ChrgClamp1by2 = Constants.LABEL_BLANK;
	private String Clamp1by2 = Constants.LABEL_BLANK;
	private String ChrgClamp1 = Constants.LABEL_BLANK;
	private String Clamp1 = Constants.LABEL_BLANK;
	private String ChrgCheeseHeadScrew = Constants.LABEL_BLANK;
	private String CheeseHeadScrew = Constants.LABEL_BLANK;
	private String ChrgWoodScrew = Constants.LABEL_BLANK;
	private String WoodScrew = Constants.LABEL_BLANK;
	
	private String ChrgRoulPlug = Constants.LABEL_BLANK;
	private String RoulPlug = Constants.LABEL_BLANK;
	
	private String SplrClamp1by2 = Constants.LABEL_BLANK;
	private String SplrClamp1 = Constants.LABEL_BLANK;
	private String SplrCheeseHeadScrew = Constants.LABEL_BLANK;
	private String SplrWoodScrew = Constants.LABEL_BLANK;
	private String SplrRoulPlug = Constants.LABEL_BLANK;
	
	private int maintainanceId;
	private int createdBy;
	private String createdOn;
	private int updatedBy;
	private String updatedOn;
	private int isSync;
	
	public ClampingDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public String getPipelineProtectionClamp() {
		return pipelineProtectionClamp;
	}



	public void setPipelineProtectionClamp(String pipelineProtectionClamp) {
		this.pipelineProtectionClamp = pipelineProtectionClamp;
	}



	public String getChrgClamp1by2() {
		return ChrgClamp1by2;
	}



	public void setChrgClamp1by2(String chrgClamp1by2) {
		ChrgClamp1by2 = chrgClamp1by2;
	}



	public String getClamp1by2() {
		return Clamp1by2;
	}



	public void setClamp1by2(String clamp1by2) {
		Clamp1by2 = clamp1by2;
	}



	public String getChrgClamp1() {
		return ChrgClamp1;
	}



	public void setChrgClamp1(String chrgClamp1) {
		ChrgClamp1 = chrgClamp1;
	}



	public String getClamp1() {
		return Clamp1;
	}



	public void setClamp1(String clamp1) {
		Clamp1 = clamp1;
	}



	public String getChrgCheeseHeadScrew() {
		return ChrgCheeseHeadScrew;
	}



	public void setChrgCheeseHeadScrew(String chrgCheeseHeadScrew) {
		ChrgCheeseHeadScrew = chrgCheeseHeadScrew;
	}



	public String getCheeseHeadScrew() {
		return CheeseHeadScrew;
	}



	public void setCheeseHeadScrew(String cheeseHeadScrew) {
		CheeseHeadScrew = cheeseHeadScrew;
	}



	public String getChrgWoodScrew() {
		return ChrgWoodScrew;
	}



	public void setChrgWoodScrew(String chrgWoodScrew) {
		ChrgWoodScrew = chrgWoodScrew;
	}



	public String getWoodScrew() {
		return WoodScrew;
	}



	public void setWoodScrew(String woodScrew) {
		WoodScrew = woodScrew;
	}



	public String getChrgRoulPlug() {
		return ChrgRoulPlug;
	}



	public void setChrgRoulPlug(String chrgRoulPlug) {
		ChrgRoulPlug = chrgRoulPlug;
	}



	public String getRoulPlug() {
		return RoulPlug;
	}



	public void setRoulPlug(String roulPlug) {
		RoulPlug = roulPlug;
	}



	public String getSplrClamp1by2() {
		return SplrClamp1by2;
	}



	public void setSplrClamp1by2(String splrClamp1by2) {
		SplrClamp1by2 = splrClamp1by2;
	}



	public String getSplrClamp1() {
		return SplrClamp1;
	}



	public void setSplrClamp1(String splrClamp1) {
		SplrClamp1 = splrClamp1;
	}



	public String getSplrCheeseHeadScrew() {
		return SplrCheeseHeadScrew;
	}



	public void setSplrCheeseHeadScrew(String splrCheeseHeadScrew) {
		SplrCheeseHeadScrew = splrCheeseHeadScrew;
	}



	public String getSplrWoodScrew() {
		return SplrWoodScrew;
	}



	public void setSplrWoodScrew(String splrWoodScrew) {
		SplrWoodScrew = splrWoodScrew;
	}



	public String getSplrRoulPlug() {
		return SplrRoulPlug;
	}



	public void setSplrRoulPlug(String splrRoulPlug) {
		SplrRoulPlug = splrRoulPlug;
	}
	
	public int getMaintainanceId() {
		return maintainanceId;
	}



	public void setMaintainanceId(int maintainanceId) {
		this.maintainanceId = maintainanceId;
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



	public int getIsSync() {
		return isSync;
	}



	public void setIsSync(int isSync) {
		this.isSync = isSync;
	}

	public int getClampingId() {
		return clampingId;
	}

	public void setClampingId(int clampingId) {
		this.clampingId = clampingId;
	}

	public String getClampingWorkSatisfactory() {
		return ClampingWorkSatisfactory;
	}

	public void setClampingWorkSatisfactory(String clampingWorkSatisfactory) {
		ClampingWorkSatisfactory = clampingWorkSatisfactory;
	}

	
}