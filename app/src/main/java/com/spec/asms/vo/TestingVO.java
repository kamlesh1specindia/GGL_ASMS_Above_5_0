package com.spec.asms.vo;

import com.spec.asms.common.Constants;

/**
 * @author bhavikg
 * value object for Testing
 */
public class TestingVO {

	private static int testingId;
	private static int maintainanceId;
	private static String initialPressure = Constants.LABEL_BLANK;
	private static String finalPressure = Constants.LABEL_BLANK;
	private static String pressureDrop = Constants.LABEL_BLANK;
	private static String duration = Constants.LABEL_BLANK;
	private static String gasLkgDetectionTest = Constants.LABEL_BLANK;
	private static String pressureType = Constants.LABEL_BLANK;
	private static String gasLkgRectified = Constants.LABEL_BLANK;
	private static String remarkLkgDescription = Constants.LABEL_BLANK;
	private static int createdBy;
	private static String createdOn;
	private static int updatedBy;
	private static String updatedOn;
	private static int isSync;


	public static int getTestingId() {
		return testingId;
	}
	public static void setTestingId(int testingId) {
		TestingVO.testingId = testingId;
	}
	public static int getMaintainanceId() {
		return maintainanceId;
	}
	public static void setMaintainanceId(int maintainanceId) {
		TestingVO.maintainanceId = maintainanceId;
	}
	public static String getInitialPressure() {
		System.err.println(" -- GET -- "+initialPressure);
		return initialPressure;
	}
	public static void setInitialPressure(String initialPressure) {
		System.err.println(" -- GET -- "+initialPressure);
		TestingVO.initialPressure = initialPressure;
	}
	public static String getFinalPressure() {
		return finalPressure;
	}
	public static void setFinalPressure(String finalPressure) {
		TestingVO.finalPressure = finalPressure;
	}
	public static String getPressureDrop() {
		return pressureDrop;
	}
	public static void setPressureDrop(String pressureDrop) {
		TestingVO.pressureDrop = pressureDrop;
	}
	public static String getDuration() {
		return duration;
	}
	public static void setDuration(String duration) {
		TestingVO.duration = duration;
	}
	public static String getGasLkgDetectionTest() {
		return gasLkgDetectionTest;
	}
	public static void setGasLkgDetectionTest(String gasLkgDetectionTest) {
		TestingVO.gasLkgDetectionTest = gasLkgDetectionTest;
	}
	public static String getPressureType() {
		return pressureType;
	}
	public static void setPressureType(String pressureType) {
		TestingVO.pressureType = pressureType;
	}
	public static String getGasLkgRectified() {
		return gasLkgRectified;
	}
	public static void setGasLkgRectified(String gasLkgRectified) {
		TestingVO.gasLkgRectified = gasLkgRectified;
	}
	public static int getCreatedBy() {
		return createdBy;
	}
	public static void setCreatedBy(int createdBy) {
		TestingVO.createdBy = createdBy;
	}
	public static String getCreatedOn() {
		return createdOn;
	}
	public static void setCreatedOn(String createdOn) {
		TestingVO.createdOn = createdOn;
	}
	public static int getUpdatedBy() {
		return updatedBy;
	}
	public static void setUpdatedBy(int updatedBy) {
		TestingVO.updatedBy = updatedBy;
	}
	public static String getUpdatedOn() {
		return updatedOn;
	}
	public static void setUpdatedOn(String updatedOn) {
		TestingVO.updatedOn = updatedOn;
	}
	public static int getIsSync() {
		return isSync;
	}
	public static void setIsSync(int isSync) {
		TestingVO.isSync = isSync;
	}

	public static void cleanTestingVO()
	{
		setTestingId(0);
		setMaintainanceId(0);
		setInitialPressure(Constants.LABEL_BLANK);
		setFinalPressure(Constants.LABEL_BLANK);
		setPressureDrop(Constants.LABEL_BLANK);
		setDuration(Constants.LABEL_BLANK);
		setGasLkgDetectionTest(Constants.LABEL_BLANK);
		setPressureType(Constants.LABEL_BLANK);
		setGasLkgRectified(Constants.LABEL_BLANK);
		setRemarkLkgDescription(Constants.LABEL_BLANK);
		setCreatedBy(0);
		setCreatedOn(Constants.LABEL_BLANK);
		setUpdatedBy(0);
		setUpdatedOn(Constants.LABEL_BLANK);
		setIsSync(0);
	}
	public static String getRemarkLkgDescription() {
		return remarkLkgDescription;
	}
	public static void setRemarkLkgDescription(String remarkLkgDescription) {
		TestingVO.remarkLkgDescription = remarkLkgDescription;
	}
}