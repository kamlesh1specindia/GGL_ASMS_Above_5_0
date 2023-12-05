package com.spec.asms.dto;

import com.spec.asms.common.Constants;
import com.spec.asms.vo.PaintingVO;

public class PaintingDTO 
{
	private int paintingId;
	private String isPaintingWork = Constants.LABEL_BLANK;
	private int paint;
	private String isOringWork = Constants.LABEL_BLANK;
	private int orMeter;
	private int orDomRegulator;
	private int orAudcoGland;
	private int maintainanceId;
	private int createdBy;
	private String createDon;
	private int updatedBy;
	private String updateDon;
	private int isSync;
	
	public PaintingDTO() {
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


	public int getPaintingId() {
		return paintingId;
	}
	public void setPaintingId(int paintingId) {
		this.paintingId = paintingId;
	}
	public String getIsPaintingWork() {
		return isPaintingWork;
	}
	public void setIsPaintingWork(String isPaintingWork) {
		this.isPaintingWork = isPaintingWork;
	}
	public int getPaint() {
		return paint;
	}
	public void setPaint(int paint) {
		this.paint = paint;
	}
	public String getIsOringWork() {
		return isOringWork;
	}
	public void setIsOringWork(String isOringWork) {
		this.isOringWork = isOringWork;
	}
	public int getOrMeter() {
		return orMeter;
	}
	public void setOrMeter(int orMeter) {
		this.orMeter = orMeter;
	}
	public int getOrDomRegulator() {
		return orDomRegulator;
	}
	public void setOrDomRegulator(int orDomRegulator) {
		this.orDomRegulator = orDomRegulator;
	}
	public int getOrAudcoGland() {
		return orAudcoGland;
	}
	public void setOrAudcoGland(int orAudcoGland) {
		this.orAudcoGland = orAudcoGland;
	}
}