package com.spec.asms.dto;

import com.spec.asms.common.Constants;
import com.spec.asms.vo.RccVO;

public class RccDTO 
{
	private int rccId;
	private int maintainanceId;
	private String isSandFilling = Constants.LABEL_BLANK;
	private String isWorking = Constants.LABEL_BLANK;
	private int msNail;
	private int msNut;
	private int msNailChargable;
	private int msNutChargable;
	private String msNailSuppliedBy;
	private String msNutSuppliedBy;
	private int coachScrew;
	private int rccGuard;
	private int rccGuardChargable;
	private String rccGuardSuppliedBy;
	public String getMsNailSuppliedBy() {
		return msNailSuppliedBy;
	}


	public void setMsNailSuppliedBy(String msNailSuppliedBy) {
		this.msNailSuppliedBy = msNailSuppliedBy;
	}


	public String getMsNutSuppliedBy() {
		return msNutSuppliedBy;
	}


	public void setMsNutSuppliedBy(String msNutSuppliedBy) {
		this.msNutSuppliedBy = msNutSuppliedBy;
	}


	public String getRccGuardSuppliedBy() {
		return rccGuardSuppliedBy;
	}


	public void setRccGuardSuppliedBy(String rccGuardSuppliedBy) {
		this.rccGuardSuppliedBy = rccGuardSuppliedBy;
	}


	public String getIsSandFilling() {
		return isSandFilling;
	}


	public void setIsSandFilling(String isSandFilling) {
		this.isSandFilling = isSandFilling;
	}


	public int getMsNailChargable() {
		return msNailChargable;
	}


	public void setMsNailChargable(int msNailChargable) {
		this.msNailChargable = msNailChargable;
	}


	public int getMsNutChargable() {
		return msNutChargable;
	}


	public void setMsNutChargable(int msNutChargable) {
		this.msNutChargable = msNutChargable;
	}


	public int getRccGuardChargable() {
		return rccGuardChargable;
	}


	public void setRccGuardChargable(int rccGuardChargable) {
		this.rccGuardChargable = rccGuardChargable;
	}


	private int createdBy;
	private String createDon = Constants.LABEL_BLANK;
	private int updatedBy;
	private String updateDon = Constants.LABEL_BLANK;
	private int isSync;
	
	
	public RccDTO() {
		// TODO Auto-generated constructor stub
	}


	public int getRccId() {
		return rccId;
	}

	public void setRccId(int rccId) {
		this.rccId = rccId;
	}

	public String getIsWorking() {
		return isWorking;
	}

	public void setIsWorking(String isWorking) {
		this.isWorking = isWorking;
	}

	public int getMsNail() {
		return msNail;
	}

	public void setMsNail(int msNail) {
		this.msNail = msNail;
	}

	public int getMsNut() {
		return msNut;
	}

	public void setMsNut(int msNut) {
		this.msNut = msNut;
	}

	public int getCoachScrew() {
		return coachScrew;
	}

	public void setCoachScrew(int coachScrew) {
		this.coachScrew = coachScrew;
	}

	public int getRccGuard() {
		return rccGuard;
	}

	public void setRccGuard(int rccGuard) {
		this.rccGuard = rccGuard;
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


	public String getCreateDon() {
		return createDon;
	}


	public void setCreateDon(String createDon) {
		this.createDon = createDon;
	}


	public int getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}


	public String getUpdateDon() {
		return updateDon;
	}


	public void setUpdateDon(String updateDon) {
		this.updateDon = updateDon;
	}


	public int getIsSync() {
		return isSync;
	}


	public void setIsSync(int isSync) {
		this.isSync = isSync;
	}	
}