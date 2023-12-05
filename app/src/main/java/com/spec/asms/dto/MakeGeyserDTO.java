	package com.spec.asms.dto;

import com.spec.asms.common.Constants;
import com.spec.asms.vo.MakeGeyserVO;

public class MakeGeyserDTO 
{
	private int makeGeyserId;
	private int makeId;
	private String isGeyserAvailable = Constants.LABEL_BLANK;
	private String makeotherText = Constants.LABEL_BLANK;
	private String isInsideBathroom = Constants.LABEL_BLANK;
	private int geyserTypeId;
	
	public MakeGeyserDTO() {
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("static-access")
	public MakeGeyserDTO(MakeGeyserVO makeGeyserVO) {
		
		setMakeGeyserId(makeGeyserVO.getMakeGeyserId());
		setMakeId(makeGeyserVO.getMakeId());
		setMakeotherText(makeGeyserVO.getMakeotherText());
		setIsInsideBathroom(makeGeyserVO.getIsInsideBathroom());
		setGeyserTypeId(makeGeyserVO.getGeyserTypeId());
		setIsGeyserAvailable(makeGeyserVO.getIsGeyserAvailable());
	}
	
	public int getMakeGeyserId() {
		return makeGeyserId;
	}
	public void setMakeGeyserId(int makeGeyserId) {
		this.makeGeyserId = makeGeyserId;
	}
	public int getMakeId() {
		return makeId;
	}
	public void setMakeId(int makeId) {
		this.makeId = makeId;
	}
	public String getMakeotherText() {
		return makeotherText;
	}
	public void setMakeotherText(String makeotherText) {
		this.makeotherText = makeotherText;
	}
	public String getIsInsideBathroom() {
		return isInsideBathroom;
	}
	public void setIsInsideBathroom(String isInsideBathroom) {
		this.isInsideBathroom = isInsideBathroom;
	}
	public int getGeyserTypeId() {
		System.err.println(" -- GEYSER TYPE ID "+geyserTypeId);
		return geyserTypeId;
	}
	public void setGeyserTypeId(int geyserTypeId) {
		System.err.println(" ###### GEYSER TYPE ID "+geyserTypeId);
		
		this.geyserTypeId = geyserTypeId;
		
		System.err.println(" *** GEYSER TYPE ID "+ this.geyserTypeId);
		
	}

	public String getIsGeyserAvailable() {
		return isGeyserAvailable;
	}

	public void setIsGeyserAvailable(String isGeyserAvailable) {
		this.isGeyserAvailable = isGeyserAvailable;
	}
}