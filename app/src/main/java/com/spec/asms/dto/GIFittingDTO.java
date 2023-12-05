package com.spec.asms.dto;

import com.spec.asms.common.Constants;
import com.spec.asms.vo.GIFittingVO;


/**
 * @author bhavikg
 * value object for GI Fitting
 */
public class GIFittingDTO {
	
	private int giFittingId;
	private String isWorking = Constants.LABEL_BLANK;
	private int Elbow;
	private int Tee;
	private int HexNipple;
	private int GIUnion;
	private int Plug;
	private int GICap;
	private int GICoupling;
	private int createdBy;
	private String createdOn;
	private int updatedBy;
	private String updatedOn;	
	private int isSync;
	
	public GIFittingDTO() {
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



	public int getElbow() {
		return Elbow;
	}

	public void setElbow(int elbow) {
		Elbow = elbow;
	}

	public int getTee() {
		return Tee;
	}

	public void setTee(int tee) {
		Tee = tee;
	}

	public int getHexNipple() {
		return HexNipple;
	}

	public void setHexNipple(int hexNipple) {
		HexNipple = hexNipple;
	}

	public int getGIUnion() {
		return GIUnion;
	}

	public void setGIUnion(int gIUnion) {
		GIUnion = gIUnion;
	}

	public int getPlug() {
		return Plug;
	}

	public void setPlug(int plug) {
		Plug = plug;
	}

	public int getGICap() {
		return GICap;
	}

	public void setGICap(int gICap) {
		GICap = gICap;
	}

	public int getGICoupling() {
		return GICoupling;
	}

	public void setGICoupling(int gICoupling) {
		GICoupling = gICoupling;
	}

	public int getGiFittingId() {
		return giFittingId;
	}

	public void setGiFittingId(int giFittingId) {
		this.giFittingId = giFittingId;
	}

	public String getIsWorking() {
		return isWorking;
	}

	public void setIsWorking(String isWorking) {
		this.isWorking = isWorking;
	}
	
}
