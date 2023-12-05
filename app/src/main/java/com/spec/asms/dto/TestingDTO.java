package com.spec.asms.dto;

import com.spec.asms.common.Constants;
import com.spec.asms.vo.TestingVO;

/**
 * value object for Testing
 */
public class TestingDTO {
	
	private int testingId;	
	private int maintainanceId;
	private double initialPressure;
	private double finalPressure;
	private double pressureDrop;
	private double duration;
	private String gasLkgDetectionTest = Constants.LABEL_BLANK;
	private String pressureType = Constants.LABEL_BLANK;
	private String gasLkgRectified = Constants.LABEL_BLANK;
	private int createdBy;
	private String createdOn;
	private int updatedBy;
	private String updatedOn;
	private int isSync;

	public TestingDTO() {
	
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


	public double getInitialPressure() {
		return initialPressure;
	}

	public void setInitialPressure(double initialPressure) {
		this.initialPressure = initialPressure;
	}

	public double getFinalPressure() {
		return finalPressure;
	}

	public void setFinalPressure(double finalPressure) {
		this.finalPressure = finalPressure;
	}

	public double getPressureDrop() {
		return pressureDrop;
	}

	public void setPressureDrop(double pressureDrop) {
		this.pressureDrop = pressureDrop;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public String getGasLkgDetectionTest() {
		return gasLkgDetectionTest;
	}

	public void setGasLkgDetectionTest(String gasLkgDetectionTest) {
		this.gasLkgDetectionTest = gasLkgDetectionTest;
	}

	public String getPressureType() {
		return pressureType;
	}

	public void setPressureType(String pressureType) {
		this.pressureType = pressureType;
	}

	public String getGasLkgRectified() {
		return gasLkgRectified;
	}

	public void setGasLkgRectified(String gasLkgRectified) {
		this.gasLkgRectified = gasLkgRectified;
	}

	public int getTestingId() {
		return testingId;
	}

	public void setTestingId(int testingId) {
		this.testingId = testingId;
	}	
}