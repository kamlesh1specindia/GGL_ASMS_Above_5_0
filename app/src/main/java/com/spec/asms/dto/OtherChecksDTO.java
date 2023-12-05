package com.spec.asms.dto;

import com.spec.asms.common.Constants;
import com.spec.asms.vo.OtherChecksVO;


public class OtherChecksDTO 
{
	private int OtherChechsId;
	//private int supplyBush;
	private int rubberCapChargable;
	private int brassBallValueChargable;
	private int brassBallCockChargable;
	private int chrgSupply3by4into1by2Bush;
	private int supply3by4into1by2Bush;
	private String rubberCapSuppliedBy = Constants.LABEL_BLANK;
	private String brassBallSuppliedBy = Constants.LABEL_BLANK;
	private String brassBallCockSuppliedBy = Constants.LABEL_BLANK;
	private String splrSupply3by4into1by2BushBy = Constants.LABEL_BLANK;
	private String pvcSleeve = Constants.LABEL_BLANK;
	private int RubberCap;
    private int BrassBallValve;
    private String MeterPerformance = Constants.LABEL_BLANK;
    private String MeterReadable = Constants.LABEL_BLANK;
    private String GIPipeInsideBasement = Constants.LABEL_BLANK;
    private int brassBallCock ;
	/*public int getSupplyBush() {
		return supplyBush;
	}

	public void setSupplyBush(int supplyBush) {
		this.supplyBush = supplyBush;
	}*/
    
    public int getChrgSupply3by4into1by2Bush() {
		return chrgSupply3by4into1by2Bush;
	}

	public void setChrgSupply3by4into1by2Bush(int chrgSupply3by4into1by2Bush) {
		this.chrgSupply3by4into1by2Bush = chrgSupply3by4into1by2Bush;
	}

	public int getSupply3by4into1by2Bush() {
		return supply3by4into1by2Bush;
	}

	public void setSupply3by4into1by2Bush(int supply3by4into1by2Bush) {
		this.supply3by4into1by2Bush = supply3by4into1by2Bush;
	}

	public String getSplrSupply3by4into1by2BushBy() {
		return splrSupply3by4into1by2BushBy;
	}

	public void setSplrSupply3by4into1by2BushBy(String splrSupply3by4into1by2BushBy) {
		this.splrSupply3by4into1by2BushBy = splrSupply3by4into1by2BushBy;
	}

	public int getRubberCapChargable() {
		return rubberCapChargable;
	}

	public void setRubberCapChargable(int rubberCapChargable) {
		this.rubberCapChargable = rubberCapChargable;
	}

	public int getBrassBallValueChargable() {
		return brassBallValueChargable;
	}

	public void setBrassBallValueChargable(int brassBallValueChargable) {
		this.brassBallValueChargable = brassBallValueChargable;
	}

	public int getBrassBallCockChargable() {
		return brassBallCockChargable;
	}

	public void setBrassBallCockChargable(int brassBallCockChargable) {
		this.brassBallCockChargable = brassBallCockChargable;
	}

	/*public int getIsolationBallChargable() {
		return isolationBallChargable;
	}

	public void setIsolationBallChargable(int isolationBallChargable) {
		this.isolationBallChargable = isolationBallChargable;
	}
*/
	public String getRubberCapSuppliedBy() {
		return rubberCapSuppliedBy;
	}

	public void setRubberCapSuppliedBy(String rubberCapSuppliedBy) {
		this.rubberCapSuppliedBy = rubberCapSuppliedBy;
	}

	/*public int getIsolationBall() {
		return isolationBall;
	}

	public void setIsolationBall(int isolationBall) {
		this.isolationBall = isolationBall;
	}*/

	public String getBrassBallSuppliedBy() {
		return brassBallSuppliedBy;
	}

	public void setBrassBallSuppliedBy(String brassBallSuppliedBy) {
		this.brassBallSuppliedBy = brassBallSuppliedBy;
	}

	public String getBrassBallCockSuppliedBy() {
		return brassBallCockSuppliedBy;
	}

	public void setBrassBallCockSuppliedBy(String brassBallCockSuppliedBy) {
		this.brassBallCockSuppliedBy = brassBallCockSuppliedBy;
	}

	/*public String getIsolationBallSuppliedBy() {
		return isolationBallSuppliedBy;
	}

	public void setIsolationBallSuppliedBy(String isolationBallSuppliedBy) {
		this.isolationBallSuppliedBy = isolationBallSuppliedBy;
	}
*/
	public String getPvcSleeve() {
		return pvcSleeve;
	}

	public void setPvcSleeve(String pvcSleeve) {
		this.pvcSleeve = pvcSleeve;
	}

	private String isMeter = Constants.LABEL_BLANK;
	private int meterReading ;
    
    public OtherChecksDTO() {
		// TODO Auto-generated constructor stub
	}
    
    @SuppressWarnings("static-access")
	public OtherChecksDTO(OtherChecksVO otherChecksVO){
    	// -Start- Added By Pankit Mistry
    	/*if(otherChecksVO.getSupplyBush().equals(Constants.LABEL_BLANK)) 
    		setSupplyBush(0);
    	else
    		setSupplyBush(Integer.parseInt(otherChecksVO.getSupplyBush()));*/
    	// -End-
    	if(otherChecksVO.getRubberCap().equals(Constants.LABEL_BLANK))
			setRubberCap(0);
		else
			setRubberCap(Integer.parseInt(otherChecksVO.getRubberCap()));
		
		if(otherChecksVO.getBrassBallValue().equals(Constants.LABEL_BLANK))
			setBrassBallValve(0);
		else
			setBrassBallValve(Integer.parseInt(otherChecksVO.getBrassBallValue()));
		
		if(otherChecksVO.getBrassBallCock().equals(Constants.LABEL_BLANK))
			setBrassBallCock(0);
		else
			setBrassBallCock(Integer.parseInt(otherChecksVO.getBrassBallCock()));
		
		setIsMeter(OtherChecksVO.getIsMeter());
		if(otherChecksVO.getMeterReading().equals(Constants.LABEL_BLANK))
			setMeterReading(0);
		else
			setMeterReading(Integer.parseInt(otherChecksVO.getMeterReading()));
		
		// -Start- Added By Pankit Mistry
		if(otherChecksVO.getRubberCapChargable().equals(Constants.LABEL_BLANK))
			setRubberCapChargable(0);
		else 
			setRubberCapChargable(Integer.parseInt(otherChecksVO.getRubberCapChargable()));
		
		if(otherChecksVO.getBrassBallCockChargable().equals(Constants.LABEL_BLANK))
			setBrassBallCockChargable(0);
		else 
			setBrassBallCockChargable(Integer.parseInt(otherChecksVO.getBrassBallCockChargable()));
		
		if(otherChecksVO.getBrassBallValueChargable().equals(Constants.LABEL_BLANK))
			setBrassBallValueChargable(0);
		else
			setBrassBallValueChargable(Integer.parseInt(otherChecksVO.getBrassBallValueChargable()));
		
		if(otherChecksVO.getSupply3by4into1by2Bush().equals(Constants.LABEL_BLANK))
			setSupply3by4into1by2Bush(0);
		else
			setSupply3by4into1by2Bush(Integer.parseInt(otherChecksVO.getSupply3by4into1by2Bush()));
		
		if(otherChecksVO.getChrgSupply3by4into1by2Bush().equals(Constants.LABEL_BLANK))
			setChrgSupply3by4into1by2Bush(0);
		else
			setChrgSupply3by4into1by2Bush(Integer.parseInt(otherChecksVO.getChrgSupply3by4into1by2Bush()));
		
		setRubberCapSuppliedBy(otherChecksVO.getRubberCapSuppliedBy());
		setBrassBallCockSuppliedBy(otherChecksVO.getBrassBallCockSuppliedBy());
		setBrassBallSuppliedBy(otherChecksVO.getBrassBallSuppliedBy());
		setSplrSupply3by4into1by2BushBy(otherChecksVO.getsplrSupply3by4into1by2BushBy());
		setPvcSleeve(otherChecksVO.getPvcSleeve());
		// -End-
		setMeterPerformance(otherChecksVO.getMtrPerformance());
		setMeterReadable(otherChecksVO.getMtrReadable());
		setGIPipeInsideBasement(otherChecksVO.getGiPipeInsidebm());
		setOtherChechsId(otherChecksVO.getOtherChechsId());
    }

	public int getOtherChechsId() {
		return OtherChechsId;
	}

	public void setOtherChechsId(int otherChechsId) {
		OtherChechsId = otherChechsId;
	}

	public int getRubberCap() {
		return RubberCap;
	}

	public void setRubberCap(int rubberCap) {
		RubberCap = rubberCap;
	}

	public int getBrassBallValve() {
		return BrassBallValve;
	}

	public void setBrassBallValve(int brassBallValve) {
		BrassBallValve = brassBallValve;
	}

	public String getMeterPerformance() {
		return MeterPerformance;
	}

	public void setMeterPerformance(String meterPerformance) {
		MeterPerformance = meterPerformance;
	}

	public String getMeterReadable() {
		return MeterReadable;
	}

	public void setMeterReadable(String meterReadable) {
		MeterReadable = meterReadable;
	}

	public String getGIPipeInsideBasement() {
		return GIPipeInsideBasement;
	}

	public void setGIPipeInsideBasement(String gIPipeInsideBasement) {
		GIPipeInsideBasement = gIPipeInsideBasement;
	}


	public String getIsMeter() {
		return isMeter;
	}

	public void setIsMeter(String isMeter) {
		this.isMeter = isMeter;
	}


	public int getBrassBallCock() {
		return brassBallCock;
	}

	public void setBrassBallCock(int brassBallCock) {
		this.brassBallCock = brassBallCock;
	}

	public int getMeterReading() {
		return meterReading;
	}

	public void setMeterReading(int meterReading) {
		this.meterReading = meterReading;
	}
}