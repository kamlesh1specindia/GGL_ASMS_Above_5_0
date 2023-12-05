package com.spec.asms.service;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.DBHelper;
import com.spec.asms.common.utils.SecurityManager;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.dto.ClampingDTO;
import com.spec.asms.dto.CustomerFeedbackDTO;
import com.spec.asms.dto.GIFittingDTO;
import com.spec.asms.dto.GIFittingMSTDTO;
import com.spec.asms.dto.KitchenSurakshaTubeDTO;
import com.spec.asms.dto.MakeGeyserDTO;
import com.spec.asms.dto.OtherChecksDTO;
import com.spec.asms.dto.OtherKitchenSurakshaTubeDTO;
import com.spec.asms.dto.PaintingDTO;
import com.spec.asms.dto.RccDTO;
import com.spec.asms.dto.TestingDTO;
import com.spec.asms.vo.CustomerFeedbackVO;
import com.spec.asms.vo.MakeGeyserVO;
import com.spec.asms.vo.OtherChecksVO;

public class MaintainanceDTOService {

	private DBHelper dbHelper;

	public MaintainanceDTOService(Context context){

		try{
			dbHelper = new DBHelper(context);
		}catch(Exception ex){
			ex.printStackTrace(); 
		}
	}
	
	public TestingDTO getTestingDetailByMaintainanceID(int MaintainanceID){

		TestingDTO res = new TestingDTO();

		try{
			String where = "maintainanceid = " + MaintainanceID;
		   	String[] fieldList ={Constants.DB_TESTING_ID,Constants.DB_MAINTAINANCE_ID,Constants.DB_INITIAL_PRESSURE,Constants.DB_FINAL_PRESSURE,Constants.DB_PRESSURE_DROP,Constants.DB_DURATION,
				    	Constants.DB_GAS_LKG_DETECTION_TEST,Constants.DB_PRESSURE_TYPE,Constants.DB_GAS_LKG_RECTIFIED,Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,
					Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_TESTING, fieldList,where);
			if(cursor.moveToFirst()){
				do{					

					res.setMaintainanceId(((Integer.parseInt(cursor.getString(1)))));
					if(SecurityManager.decrypt(cursor.getString(2),Constants.SALT).equals(Constants.LABEL_BLANK))
						res.setInitialPressure(0);
					else
						res.setInitialPressure(Integer.parseInt(SecurityManager.decrypt(cursor.getString(2),Constants.SALT)));
					
					if(SecurityManager.decrypt(cursor.getString(3),Constants.SALT).equals(Constants.LABEL_BLANK))
						res.setFinalPressure(0);
					else
						res.setFinalPressure(Integer.parseInt(SecurityManager.decrypt(cursor.getString(3),Constants.SALT)));
					
					if(SecurityManager.decrypt(cursor.getString(4),Constants.SALT).equals(Constants.LABEL_BLANK))
						res.setPressureDrop(0);
					else
						res.setPressureDrop(Integer.parseInt(SecurityManager.decrypt(cursor.getString(4),Constants.SALT)));
					
					if(SecurityManager.decrypt(cursor.getString(5),Constants.SALT).equals(Constants.LABEL_BLANK))
						res.setDuration(0);
					else
						res.setDuration(Integer.parseInt(SecurityManager.decrypt(cursor.getString(5),Constants.SALT)));
					
					res.setGasLkgDetectionTest(SecurityManager.decrypt(cursor.getString(6),Constants.SALT));
					res.setPressureType(SecurityManager.decrypt(cursor.getString(7),Constants.SALT));
					res.setGasLkgRectified(SecurityManager.decrypt(cursor.getString(8),Constants.SALT));
					res.setIsSync(((Integer.parseInt(cursor.getString(9)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(10)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(11),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(12)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(13),Constants.SALT));
					res.setTestingId(Integer.parseInt(cursor.getString(0)));

					//					
					//					res.setInitialPressure(Double.parseDouble(SecurityManager.decrypt(cursor.getString(2),Constants.SALT)));					
					//					res.setFinalPressure(Double.parseDouble(SecurityManager.decrypt(cursor.getString(3),Constants.SALT)));					
					//					res.setPressureDrop(Double.parseDouble(SecurityManager.decrypt(cursor.getString(4),Constants.SALT)));
					//					res.setDuration(Integer.parseInt(SecurityManager.decrypt(cursor.getString(5),Constants.SALT)));
					//					res.setGasLkgDetectionTest(SecurityManager.decrypt(cursor.getString(6),Constants.SALT));
					//					res.setPressureType(SecurityManager.decrypt(cursor.getString(7),Constants.SALT));
					//					res.setGasLkgRectified(SecurityManager.decrypt(cursor.getString(8),Constants.SALT));					
					//					res.setTestingId(Integer.parseInt(cursor.getString(0)));

				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainanceDTOService:"+ Utility.convertExceptionToString(ex));
		}
		
		return res;
	}


	public ClampingDTO getClampingByMaintainanceID(int MainatainanceId){

		ClampingDTO res = new ClampingDTO();

		try{
			String where = "maintainanceid =" +MainatainanceId;
			
			String[] fieldList = {
					Constants.DB_MAINTAINANCE_ID,Constants.DB_ISWORKING,
					Constants.DB_PIPELINE_PROTECTION_CLAMP,
					Constants.DB_CLAMP_HALF_C,Constants.DB_CLAMP_HALF_NC,
					Constants.DB_CLAMP_ONE_C,Constants.DB_CLAMP_ONE_NC,
					Constants.DB_CHEESEHEADSCREW_C,Constants.DB_CHEESEHEADSCREW_NC,
					Constants.DB_WOODSCREW_C,Constants.DB_WOODSCREW_NC,
					Constants.DB_ROUL_PLUG_C,Constants.DB_ROUL_PLUG_NC,
					Constants.DB_SPLR_HALF,Constants.DB_SPLR_ONE,
					Constants.DB_SPLR_CHEESE_HEAD_SCREW,Constants.DB_SPLR_WOOKD_SCREW,
					Constants.DB_SPLR_ROUL_PLUG,Constants.DB_ISSYNC,
					Constants.DB_CREATEDBY,Constants.DB_CREATEDON,
					Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			
			/*String[] fieldList = {Constants.DB_MAINTAINANCE_ID,Constants.DB_ISWORKING,Constants.DB_CLAMP_HALF,Constants.DB_CLAMP_ONE,Constants.DB_CHEESEHEADSCREW,
					Constants.DB_WOODSCREW,Constants.DB_ROUL_PLUG,Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,
					Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};
*/
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_CLAMPING, fieldList,where);
			if(cursor.moveToFirst()){
				do{					
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setClampingWorkSatisfactory(SecurityManager.decrypt(cursor.getString(1),Constants.SALT));
					if(SecurityManager.decrypt(cursor.getString(2),Constants.SALT).equals(Constants.LABEL_BLANK)){
						  res.setPipelineProtectionClamp("0");
					}else{
						  res.setPipelineProtectionClamp(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));
					}
                  
					
					//if(res.getClampingWorkSatisfactory().equals("NO") && res.getClampingWorkSatisfactory()!= Constants.LABEL_BLANK){
						if(SecurityManager.decrypt(cursor.getString(3),Constants.SALT).equals(Constants.LABEL_BLANK)){
							res.setChrgClamp1by2(Constants.ZERO);
						}else{
							res.setChrgClamp1by2(SecurityManager.decrypt(cursor.getString(3),Constants.SALT));
						}
						if(SecurityManager.decrypt(cursor.getString(4),Constants.SALT).equals(Constants.LABEL_BLANK)){
							res.setClamp1by2(Constants.ZERO);
						}else{
							res.setClamp1by2(SecurityManager.decrypt(cursor.getString(4),Constants.SALT));
						}
						
						if(SecurityManager.decrypt(cursor.getString(5),Constants.SALT).equals(Constants.LABEL_BLANK)){
							res.setChrgClamp1(Constants.ZERO);
						}else{
							res.setChrgClamp1(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
						}
						if(SecurityManager.decrypt(cursor.getString(6),Constants.SALT).equals(Constants.LABEL_BLANK)){
							res.setClamp1(Constants.ZERO);
						}else{
							res.setClamp1(SecurityManager.decrypt(cursor.getString(6),Constants.SALT));
						}
						
				//}
					
					if(SecurityManager.decrypt(cursor.getString(7),Constants.SALT).equals(Constants.LABEL_BLANK)){
						res.setChrgCheeseHeadScrew(Constants.ZERO);
					}else{
						res.setChrgCheeseHeadScrew(SecurityManager.decrypt(cursor.getString(7),Constants.SALT));
					}
					if(SecurityManager.decrypt(cursor.getString(8),Constants.SALT).equals(Constants.LABEL_BLANK)){
						res.setCheeseHeadScrew(Constants.ZERO);
					}else{
						res.setCheeseHeadScrew(SecurityManager.decrypt(cursor.getString(8),Constants.SALT));	
					}
					
					if(SecurityManager.decrypt(cursor.getString(9),Constants.SALT).equals(Constants.LABEL_BLANK)){
						res.setChrgWoodScrew(Constants.ZERO);
					}else{
						res.setChrgWoodScrew(SecurityManager.decrypt(cursor.getString(9),Constants.SALT));
					}
					
					if(SecurityManager.decrypt(cursor.getString(10),Constants.SALT).equals(Constants.LABEL_BLANK)){
						res.setWoodScrew(Constants.ZERO);
					}else{
						res.setWoodScrew(SecurityManager.decrypt(cursor.getString(10),Constants.SALT));
					}
					
					if(SecurityManager.decrypt(cursor.getString(11),Constants.SALT).equals(Constants.LABEL_BLANK)){
						res.setChrgRoulPlug(Constants.ZERO);
					}else{
						res.setChrgRoulPlug(SecurityManager.decrypt(cursor.getString(11),Constants.SALT));
					}
					if(SecurityManager.decrypt(cursor.getString(12),Constants.SALT).equals(Constants.LABEL_BLANK)){
						res.setRoulPlug(Constants.ZERO);
					}else{
						res.setRoulPlug(SecurityManager.decrypt(cursor.getString(12),Constants.SALT));
					}
					
					if(SecurityManager.decrypt(cursor.getString(13),Constants.SALT).equals(Constants.LABEL_BLANK)){
						res.setSplrClamp1by2(Constants.ZERO);
					}else{
						res.setSplrClamp1by2(SecurityManager.decrypt(cursor.getString(13),Constants.SALT));	
					}
					
					//res.setSplrClamp1by2(SecurityManager.decrypt(cursor.getString(13),Constants.SALT));
					
					if(SecurityManager.decrypt(cursor.getString(14),Constants.SALT).equals(Constants.LABEL_BLANK)){
						res.setSplrClamp1(Constants.ZERO);
					}else{
						res.setSplrClamp1(SecurityManager.decrypt(cursor.getString(14),Constants.SALT));
					}
					
					//res.setSplrClamp1(SecurityManager.decrypt(cursor.getString(14),Constants.SALT));
					
					if(SecurityManager.decrypt(cursor.getString(15),Constants.SALT).equals(Constants.LABEL_BLANK)){
						res.setSplrCheeseHeadScrew(Constants.ZERO);
					}else{
						res.setSplrCheeseHeadScrew(SecurityManager.decrypt(cursor.getString(15),Constants.SALT));
					}
					
					//res.setSplrCheeseHeadScrew(SecurityManager.decrypt(cursor.getString(15),Constants.SALT));
					
					if(SecurityManager.decrypt(cursor.getString(16),Constants.SALT).equals(Constants.LABEL_BLANK)){
						res.setSplrWoodScrew(Constants.ZERO);
					}else{
						res.setSplrWoodScrew(SecurityManager.decrypt(cursor.getString(16),Constants.SALT));
					}
					
					//res.setSplrWoodScrew(SecurityManager.decrypt(cursor.getString(16),Constants.SALT));
					
					if(SecurityManager.decrypt(cursor.getString(17),Constants.SALT).equals(Constants.LABEL_BLANK)){
						res.setSplrRoulPlug(Constants.ZERO);
					}else{
						res.setSplrRoulPlug(SecurityManager.decrypt(cursor.getString(17),Constants.SALT));
					}
					
					//res.setSplrRoulPlug(SecurityManager.decrypt(cursor.getString(17),Constants.SALT));
					res.setIsSync(((Integer.parseInt(cursor.getString(18)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(19)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(20),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(21)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(22),Constants.SALT));
				}while(cursor.moveToNext());
			}else{
				res.setMaintainanceId(MainatainanceId);
				res.setClampingWorkSatisfactory(Constants.LABEL_BLANK);
				res.setPipelineProtectionClamp("0");
				res.setChrgClamp1by2(Constants.ZERO);
				res.setClamp1by2(Constants.ZERO);
				res.setChrgClamp1(Constants.ZERO);
				res.setClamp1(Constants.ZERO);
				res.setChrgCheeseHeadScrew(Constants.ZERO);	
				res.setCheeseHeadScrew(Constants.ZERO);
				res.setChrgWoodScrew(Constants.ZERO);
				res.setWoodScrew(Constants.ZERO);
				res.setChrgRoulPlug(Constants.ZERO);
				res.setRoulPlug(Constants.ZERO);
				res.setSplrClamp1by2(Constants.ZERO);
				res.setSplrClamp1(Constants.ZERO);
				res.setSplrCheeseHeadScrew(Constants.ZERO);
				res.setSplrWoodScrew(Constants.ZERO);
				res.setSplrRoulPlug(Constants.ZERO);
				res.setIsSync(0);
				res.setCreatedBy(0);
				res.setCreatedOn("");
				res.setUpdatedBy(0);
				res.setUpdatedOn("");
			}
			if(cursor != null && !cursor.isClosed()){	
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainanceDTOService:"+ Utility.convertExceptionToString(ex));
		}

		return res;
	}

	public PaintingDTO getPaintingOringByMaintainanceID(int MaintainanceId){

		PaintingDTO res = new PaintingDTO();

		try{
			String where ="maintainanceid =" +MaintainanceId;
			String[] fieldList ={
					Constants.DB_MAINTAINANCE_ID,Constants.DB_ISPAINTING_WORK,Constants.DB_PAINT,
					Constants.DB_ISORING_WORK,Constants.DB_ORMETER,Constants.DB_ORDOM_REGULATOR,
					Constants.DB_ORAUDCOGLAND,Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,
					Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_PAINTINGORING, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setIsPaintingWork((SecurityManager.decrypt(cursor.getString(1),Constants.SALT)));
					if(res.getIsPaintingWork().equals("NO") && res.getIsPaintingWork()!= Constants.LABEL_BLANK){
						if(SecurityManager.decrypt(cursor.getString(2),Constants.SALT).equals(Constants.LABEL_BLANK)){
							res.setPaint(0);
						}else{
							res.setPaint(Integer.parseInt(SecurityManager.decrypt(cursor.getString(2),Constants.SALT)));
						}
					}

					res.setIsOringWork(SecurityManager.decrypt(cursor.getString(3),Constants.SALT));
					if(res.getIsOringWork().equals("YES") && res.getIsOringWork()!= Constants.LABEL_BLANK){
						if(SecurityManager.decrypt(cursor.getString(4),Constants.SALT).equals(Constants.LABEL_BLANK)){
							res.setOrMeter(0);
						}else{
							res.setOrMeter(Integer.parseInt(SecurityManager.decrypt(cursor.getString(4),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(5),Constants.SALT).equals(Constants.LABEL_BLANK)){
							res.setOrDomRegulator(0);
						}else{
							res.setOrDomRegulator(Integer.parseInt(SecurityManager.decrypt(cursor.getString(5),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(6),Constants.SALT).equals(Constants.LABEL_BLANK)){
							res.setOrAudcoGland(0);
						}else{
							res.setOrAudcoGland(Integer.parseInt(SecurityManager.decrypt(cursor.getString(6),Constants.SALT)));
						}
					}
					res.setIsSync(((Integer.parseInt(cursor.getString(7)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(8)))));
					res.setCreateDon(SecurityManager.decrypt(cursor.getString(9),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(10)))));
					res.setUpdateDon(SecurityManager.decrypt(cursor.getString(11),Constants.SALT));				
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainanceDTOService:"+ Utility.convertExceptionToString(ex));
		}		
		return res;
	}


	public GIFittingDTO getGIfittingDetailByTestingID(int id){

		GIFittingDTO res = new GIFittingDTO();

		try{
			String[] fieldList ={Constants.DB_GIFITTING_ID,Constants.DB_ELBOW,Constants.DB_TEE,Constants.DB_HAXNIPPLE
					,Constants.DB_GI_UNION,Constants.DB_PLUG,Constants.DB_GI_CAP,Constants.DB_GI_COUPLING,Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,
					Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON,Constants.DB_VALUE};

			String where = "testingid = "+id;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_GIFITTING, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					res.setGiFittingId(((Integer.parseInt(cursor.getString(0)))));
					res.setIsWorking(SecurityManager.decrypt(cursor.getString(13),Constants.SALT));
					if(res.getIsWorking().equals("NO") && res.getIsWorking()!= Constants.LABEL_BLANK){
						if(SecurityManager.decrypt(cursor.getString(1),Constants.SALT).equals(Constants.LABEL_BLANK)){
							res.setElbow(0);
						}else{
							res.setElbow(Integer.parseInt(SecurityManager.decrypt(cursor.getString(1),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(2),Constants.SALT).equals(Constants.LABEL_BLANK)){
							res.setTee(0);
						}else{
							res.setTee(Integer.parseInt(SecurityManager.decrypt(cursor.getString(2),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(3),Constants.SALT).equals(Constants.LABEL_BLANK)){
							res.setHexNipple(0);
						}else{
							res.setHexNipple(Integer.parseInt(SecurityManager.decrypt(cursor.getString(3),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(4),Constants.SALT).equals(Constants.LABEL_BLANK)){
							res.setGIUnion(0);
						}else{
							res.setGIUnion(Integer.parseInt(SecurityManager.decrypt(cursor.getString(4),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(5),Constants.SALT).equals(Constants.LABEL_BLANK)){
							res.setPlug(0);
						}else{
							res.setPlug(Integer.parseInt(SecurityManager.decrypt(cursor.getString(5),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(6),Constants.SALT).equals(Constants.LABEL_BLANK)){
							res.setGICap(0);
						}else{
							res.setGICap(Integer.parseInt(SecurityManager.decrypt(cursor.getString(6),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(7),Constants.SALT).equals(Constants.LABEL_BLANK)){
							res.setGICoupling(0);
						}else{
							res.setGICoupling(Integer.parseInt(SecurityManager.decrypt(cursor.getString(7),Constants.SALT)));
						}
					}
					res.setIsSync(((Integer.parseInt(cursor.getString(8)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(9)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(10),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(11)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(12),Constants.SALT));		

				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainanceDTOService:"+ Utility.convertExceptionToString(ex));
		}

		return res;
	}

	
	public RccDTO getRCCDetailByMaintainanceID(int MaintainanceId){
		RccDTO rccDTO = new RccDTO();
		// Modified By Pankit Mistry (9/4/2016)
		// Added 
		// isSandFilling(Position:1)
		// msNailChargable(Position:5)
		// msNutChargable(Position:6)
		// rccGuardChargable(Position:12)
		// msNailSuppliedBy(Position:7)
		// msNutSuppliedBy(Position:8)
		// rccGuardSuppliedBy(Position:13)
		try{
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID, Constants.DB_IS_SAND_FILLING, Constants.DB_ISWORKING,Constants.DB_MSNAIL,
					Constants.DB_MSNUT, Constants.DB_MSNAIL_CHARGABLE, Constants.DB_MSNUT_CHARGABLE, Constants.DB_MSNAIL_SUPPLIEDBY,
					Constants.DB_MSNUT_SUPPLIEDBY, Constants.DB_COACH_SCREW,Constants.DB_ISSYNC,
					Constants.DB_RCC_GUARD, Constants.DB_RCC_GUARD_CHARGABLE, Constants.DB_RCC_GUARD_SUPPLIEDBY, Constants.DB_CREATEDBY,
					Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			String where ="maintainanceid =" +MaintainanceId;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_RCC, fieldList,where);
			if(cursor.moveToFirst()){
				do{					
					rccDTO.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					rccDTO.setIsSandFilling((SecurityManager.decrypt(cursor.getString(1),Constants.SALT)));
					rccDTO.setIsWorking((SecurityManager.decrypt(cursor.getString(2),Constants.SALT)));
					if(rccDTO.getIsWorking().equals("NO") && rccDTO.getIsWorking()!= Constants.LABEL_BLANK){
						if(SecurityManager.decrypt(cursor.getString(3),Constants.SALT).equals(Constants.LABEL_BLANK)){
							rccDTO.setMsNail(0);
						}else{
							rccDTO.setMsNail(Integer.parseInt(SecurityManager.decrypt(cursor.getString(3),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(4),Constants.SALT).equals(Constants.LABEL_BLANK)){
							rccDTO.setMsNut(0);
						}else{
							rccDTO.setMsNut(Integer.parseInt(SecurityManager.decrypt(cursor.getString(4),Constants.SALT)));
						}
						// -Start- Added By Pankit Mistry
						if(SecurityManager.decrypt(cursor.getString(5),Constants.SALT).equals(Constants.LABEL_BLANK)){
							rccDTO.setMsNailChargable(0);
						}else{
							rccDTO.setMsNailChargable(Integer.parseInt(SecurityManager.decrypt(cursor.getString(5),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(6),Constants.SALT).equals(Constants.LABEL_BLANK)){
							rccDTO.setMsNutChargable(0);
						}else{
							rccDTO.setMsNutChargable(Integer.parseInt(SecurityManager.decrypt(cursor.getString(6),Constants.SALT)));
						}
						
						if(SecurityManager.decrypt(cursor.getString(7),Constants.SALT).equals(Constants.LABEL_BLANK)){
							rccDTO.setMsNailSuppliedBy(Constants.LABEL_BLANK);
						}else{
							rccDTO.setMsNailSuppliedBy(SecurityManager.decrypt(cursor.getString(7),Constants.SALT));
						}
						
						if(SecurityManager.decrypt(cursor.getString(8),Constants.SALT).equals(Constants.LABEL_BLANK)){
							rccDTO.setMsNutSuppliedBy(Constants.LABEL_BLANK);
						}else{
							rccDTO.setMsNutSuppliedBy(SecurityManager.decrypt(cursor.getString(8),Constants.SALT));
						}
						// -End-
						
						//	rccDTO.setCoachScrew(Integer.parseInt(SecurityManager.decrypt(cursor.getString(4),Constants.SALT)));
						if(SecurityManager.decrypt(cursor.getString(11),Constants.SALT).equals(Constants.LABEL_BLANK)){
							rccDTO.setRccGuard(0);
						}else{
							rccDTO.setRccGuard(Integer.parseInt(SecurityManager.decrypt(cursor.getString(11),Constants.SALT)));
						}
						// -Start- Added By Pankit Mistry
						if(SecurityManager.decrypt(cursor.getString(12),Constants.SALT).equals(Constants.LABEL_BLANK)){
							rccDTO.setRccGuardChargable(0);
						}else{
							rccDTO.setRccGuardChargable(Integer.parseInt(SecurityManager.decrypt(cursor.getString(12),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(13),Constants.SALT).equals(Constants.LABEL_BLANK)){
							rccDTO.setRccGuardSuppliedBy(Constants.LABEL_BLANK);
						}else{
							rccDTO.setRccGuardSuppliedBy(SecurityManager.decrypt(cursor.getString(13),Constants.SALT));
						}
						// -End
					}
					rccDTO.setIsSync(((Integer.parseInt(cursor.getString(10)))));
					rccDTO.setCreatedBy(((Integer.parseInt(cursor.getString(14)))));
					rccDTO.setCreateDon(SecurityManager.decrypt(cursor.getString(15),Constants.SALT));
					rccDTO.setUpdatedBy(((Integer.parseInt(cursor.getString(16)))));
					rccDTO.setUpdateDon(SecurityManager.decrypt(cursor.getString(17),Constants.SALT));				
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainanceDTOService:"+ Utility.convertExceptionToString(ex));
		}

		return rccDTO;
	}

	/*public RccDTO getRCCDetailByMaintainanceID(int MaintainanceId){
		RccDTO rccDTO = new RccDTO();

		try{
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID,Constants.DB_ISWORKING,Constants.DB_MSNAIL,
					Constants.DB_MSNUT,Constants.DB_COACH_SCREW,Constants.DB_ISSYNC,
					Constants.DB_RCC_GUARD,Constants.DB_CREATEDBY,
					Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			String where ="maintainanceid =" +MaintainanceId;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_RCC, fieldList,where);
			if(cursor.moveToFirst()){
				do{					
					rccDTO.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					rccDTO.setIsWorking((SecurityManager.decrypt(cursor.getString(1),Constants.SALT)));
					if(rccDTO.getIsWorking().equals("NO") && rccDTO.getIsWorking()!= Constants.LABEL_BLANK){
						if(SecurityManager.decrypt(cursor.getString(2),Constants.SALT).equals(Constants.LABEL_BLANK)){
							rccDTO.setMsNail(0);
						}else{
							rccDTO.setMsNail(Integer.parseInt(SecurityManager.decrypt(cursor.getString(2),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(3),Constants.SALT).equals(Constants.LABEL_BLANK)){
							rccDTO.setMsNut(0);
						}else{
							rccDTO.setMsNut(Integer.parseInt(SecurityManager.decrypt(cursor.getString(3),Constants.SALT)));
						}
						//	rccDTO.setCoachScrew(Integer.parseInt(SecurityManager.decrypt(cursor.getString(4),Constants.SALT)));
						if(SecurityManager.decrypt(cursor.getString(6),Constants.SALT).equals(Constants.LABEL_BLANK)){
							rccDTO.setRccGuard(0);
						}else{
							rccDTO.setRccGuard(Integer.parseInt(SecurityManager.decrypt(cursor.getString(6),Constants.SALT)));
						}
					}
					rccDTO.setIsSync(((Integer.parseInt(cursor.getString(5)))));
					rccDTO.setCreatedBy(((Integer.parseInt(cursor.getString(7)))));
					rccDTO.setCreateDon(SecurityManager.decrypt(cursor.getString(8),Constants.SALT));
					rccDTO.setUpdatedBy(((Integer.parseInt(cursor.getString(9)))));
					rccDTO.setUpdateDon(SecurityManager.decrypt(cursor.getString(10),Constants.SALT));				
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainanceDTOService:"+ Utility.convertExceptionToString(ex));
		}

		return rccDTO;
	}*/



	public KitchenSurakshaTubeDTO getKitchenSurkshaTubeByMaintainanceID(int MaintainanceId){

		KitchenSurakshaTubeDTO surkshaDTO = new KitchenSurakshaTubeDTO();

		try{
			String[] fieldList ={
					Constants.DB_MAINTAINANCE_ID,Constants.DB_IS_REPLACED,
					Constants.DB_SIZE_751C,Constants.DB_SIZE_751NC,
					Constants.DB_SIZE_7915C,Constants.DB_SIZE_7915NC,
					Constants.DB_SIZE_12515C,Constants.DB_SIZE_12515NC,
					Constants.DB_SIZE_CLAMP_8C,Constants.DB_SIZE_CLAMP_8NC,
					Constants.DB_SIZE_CLAMP_20C,Constants.DB_SIZE_CLAMP_20NC,
					Constants.DB_KSPL_751,Constants.DB_KSPL_7915,
					Constants.DB_KSPL_12515,Constants.DB_KSPL_8,Constants.DB_KSPL_20,
					Constants.DB_ISSYNC,Constants.DB_CREATEDBY,
					Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,
					Constants.DB_UPDATEDON,Constants.DB_EXPIRY_DATE,Constants.DB_REPLACE_EXPIRY_DATE};

			String where ="maintainanceid =" +MaintainanceId;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_SURAKSHA_TUBE, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					surkshaDTO.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					//surkshaDTO.setSurakshaTubeReplaced((SecurityManager.decrypt(cursor.getString(1),Constants.SALT)));
					surkshaDTO.setSurakshaTubeReplaced((SecurityManager.decrypt(cursor.getString(1),Constants.SALT)));
					
					//if(surkshaDTO.getSurakshaTubeReplaced().equals("YES") && surkshaDTO.getSurakshaTubeReplaced()!= Constants.LABEL_BLANK){	
						
						if(SecurityManager.decrypt(cursor.getString(2),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setSize751c(0);
						}else{
							surkshaDTO.setSize751c(Integer.parseInt(SecurityManager.decrypt(cursor.getString(2),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(3),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setSize751nc(0);
						}else{
							surkshaDTO.setSize751nc(Integer.parseInt(SecurityManager.decrypt(cursor.getString(3),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(4),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setSize7915c(0);
						}else{
							surkshaDTO.setSize7915c(Integer.parseInt(SecurityManager.decrypt(cursor.getString(4),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(5),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setSize7915nc(0);
						}else{
							surkshaDTO.setSize7915nc(Integer.parseInt(SecurityManager.decrypt(cursor.getString(5),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(6),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setSize12515c(0);
						}else{
							surkshaDTO.setSize12515c(Integer.parseInt(SecurityManager.decrypt(cursor.getString(6),Constants.SALT)));
						}
						
						if(SecurityManager.decrypt(cursor.getString(7),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setSize12515nc(0);
						}else{
							surkshaDTO.setSize12515nc(Integer.parseInt(SecurityManager.decrypt(cursor.getString(7),Constants.SALT)));
						}
						
						if(SecurityManager.decrypt(cursor.getString(8),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setClampsize8c(0);
						}else{
							surkshaDTO.setClampsize8c(Integer.parseInt(SecurityManager.decrypt(cursor.getString(8),Constants.SALT)));
						}
						
						if(SecurityManager.decrypt(cursor.getString(9),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setClampsize8nc(0);
						}else{
							surkshaDTO.setClampsize8nc(Integer.parseInt(SecurityManager.decrypt(cursor.getString(9),Constants.SALT)));
						}
						
						if(SecurityManager.decrypt(cursor.getString(10),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setClampsize20c(0);
						}else{
							surkshaDTO.setClampsize20c(Integer.parseInt(SecurityManager.decrypt(cursor.getString(10),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(11),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setClampsize20nc(0);
						}else{
							surkshaDTO.setClampsize20nc(Integer.parseInt(SecurityManager.decrypt(cursor.getString(11),Constants.SALT)));
						}
						
						if(SecurityManager.decrypt(cursor.getString(12),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setKSplr1Meter75mm(Constants.LABEL_BLANK);
						}else{
							surkshaDTO.setKSplr1Meter75mm(SecurityManager.decrypt(cursor.getString(12),Constants.SALT));
						}
						
						if(SecurityManager.decrypt(cursor.getString(13),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setKSplr15Meter79mm(Constants.LABEL_BLANK);
						}else{
							surkshaDTO.setKSplr15Meter79mm(SecurityManager.decrypt(cursor.getString(13),Constants.SALT));
						}
						
						if(SecurityManager.decrypt(cursor.getString(14),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setKSplrChrg15Meter125mm(Constants.LABEL_BLANK);
						}else{
							surkshaDTO.setKSplrChrg15Meter125mm(SecurityManager.decrypt(cursor.getString(14),Constants.SALT));
						}
						
						if(SecurityManager.decrypt(cursor.getString(15),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setKSplrClampHose8mmPipe(Constants.LABEL_BLANK);
						}else{
							surkshaDTO.setKSplrClampHose8mmPipe(SecurityManager.decrypt(cursor.getString(15),Constants.SALT));
						}
						
						if(SecurityManager.decrypt(cursor.getString(16),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setKSplrClampHose20mmPipe(Constants.LABEL_BLANK);
						}else{
							surkshaDTO.setKSplrClampHose20mmPipe(SecurityManager.decrypt(cursor.getString(16),Constants.SALT));
						}
						
					//}					
					surkshaDTO.setIsSync(((Integer.parseInt(cursor.getString(17)))));
					surkshaDTO.setCreatedBy(((Integer.parseInt(cursor.getString(18)))));
					surkshaDTO.setCreateDon(SecurityManager.decrypt(cursor.getString(19),Constants.SALT));
					surkshaDTO.setUpdateBy(((Integer.parseInt(cursor.getString(20)))));
					surkshaDTO.setUpdateDon(SecurityManager.decrypt(cursor.getString(21),Constants.SALT));
					surkshaDTO.setExpiryDate(SecurityManager.decrypt(cursor.getString(22),Constants.SALT));
					surkshaDTO.setReplaceExpiryDate(SecurityManager.decrypt(cursor.getString(23),Constants.SALT));

				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainanceDTOService:"+ Utility.convertExceptionToString(ex));
		}		
		return surkshaDTO;
	}
	
	public OtherKitchenSurakshaTubeDTO getOtherSurkshaTubeByMaintainanceID(int MaintainanceId){

		OtherKitchenSurakshaTubeDTO surkshaDTO = new OtherKitchenSurakshaTubeDTO();

		try{
			String[] fieldList ={
					Constants.DB_MAINTAINANCE_ID,Constants.DB_IS_REPLACED,
					Constants.DB_SIZE_751C,Constants.DB_SIZE_751NC,
					Constants.DB_SIZE_7915C,Constants.DB_SIZE_7915NC,
					Constants.DB_SIZE_12515C,Constants.DB_SIZE_12515NC,
					Constants.DB_SIZE_CLAMP_8C,Constants.DB_SIZE_CLAMP_8NC,
					Constants.DB_SIZE_CLAMP_20C,Constants.DB_SIZE_CLAMP_20NC,
					Constants.DB_KSPL_751,Constants.DB_KSPL_7915,
					Constants.DB_KSPL_12515,Constants.DB_KSPL_8,Constants.DB_KSPL_20,
					Constants.DB_ISSYNC,Constants.DB_CREATEDBY,
					Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,
					Constants.DB_UPDATEDON};

			String where ="maintainanceid =" +MaintainanceId;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_OTHER_SURAKSHA_TUBE, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					surkshaDTO.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					//surkshaDTO.setSurakshaTubeReplaced((SecurityManager.decrypt(cursor.getString(1),Constants.SALT)));
					surkshaDTO.setSurakshaTubeReplaced((SecurityManager.decrypt(cursor.getString(1),Constants.SALT)));
					
					//if(surkshaDTO.getSurakshaTubeReplaced().equals("YES") && surkshaDTO.getSurakshaTubeReplaced()!= Constants.LABEL_BLANK){	
						
						if(SecurityManager.decrypt(cursor.getString(2),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setSize751c(0);
						}else{
							surkshaDTO.setSize751c(Integer.parseInt(SecurityManager.decrypt(cursor.getString(2),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(3),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setSize751nc(0);
						}else{
							surkshaDTO.setSize751nc(Integer.parseInt(SecurityManager.decrypt(cursor.getString(3),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(4),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setSize7915c(0);
						}else{
							surkshaDTO.setSize7915c(Integer.parseInt(SecurityManager.decrypt(cursor.getString(4),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(5),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setSize7915nc(0);
						}else{
							surkshaDTO.setSize7915nc(Integer.parseInt(SecurityManager.decrypt(cursor.getString(5),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(6),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setSize12515c(0);
						}else{
							surkshaDTO.setSize12515c(Integer.parseInt(SecurityManager.decrypt(cursor.getString(6),Constants.SALT)));
						}
						
						if(SecurityManager.decrypt(cursor.getString(7),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setSize12515nc(0);
						}else{
							surkshaDTO.setSize12515nc(Integer.parseInt(SecurityManager.decrypt(cursor.getString(7),Constants.SALT)));
						}
						
						if(SecurityManager.decrypt(cursor.getString(8),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setClampsize8c(0);
						}else{
							surkshaDTO.setClampsize8c(Integer.parseInt(SecurityManager.decrypt(cursor.getString(8),Constants.SALT)));
						}
						
						if(SecurityManager.decrypt(cursor.getString(9),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setClampsize8nc(0);
						}else{
							surkshaDTO.setClampsize8nc(Integer.parseInt(SecurityManager.decrypt(cursor.getString(9),Constants.SALT)));
						}
						
						if(SecurityManager.decrypt(cursor.getString(10),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setClampsize20c(0);
						}else{
							surkshaDTO.setClampsize20c(Integer.parseInt(SecurityManager.decrypt(cursor.getString(10),Constants.SALT)));
						}
						if(SecurityManager.decrypt(cursor.getString(11),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setClampsize20nc(0);
						}else{
							surkshaDTO.setClampsize20nc(Integer.parseInt(SecurityManager.decrypt(cursor.getString(11),Constants.SALT)));
						}
						
						if(SecurityManager.decrypt(cursor.getString(12),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setKSplr1Meter75mm(Constants.LABEL_BLANK);
						}else{
							surkshaDTO.setKSplr1Meter75mm(SecurityManager.decrypt(cursor.getString(12),Constants.SALT));
						}
						
						if(SecurityManager.decrypt(cursor.getString(13),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setKSplr15Meter79mm(Constants.LABEL_BLANK);
						}else{
							surkshaDTO.setKSplr15Meter79mm(SecurityManager.decrypt(cursor.getString(13),Constants.SALT));
						}
						
						if(SecurityManager.decrypt(cursor.getString(14),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setKSplrChrg15Meter125mm(Constants.LABEL_BLANK);
						}else{
							surkshaDTO.setKSplrChrg15Meter125mm(SecurityManager.decrypt(cursor.getString(14),Constants.SALT));
						}
						
						if(SecurityManager.decrypt(cursor.getString(15),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setKSplrClampHose8mmPipe(Constants.LABEL_BLANK);
						}else{
							surkshaDTO.setKSplrClampHose8mmPipe(SecurityManager.decrypt(cursor.getString(15),Constants.SALT));
						}
						
						if(SecurityManager.decrypt(cursor.getString(16),Constants.SALT).equals(Constants.LABEL_BLANK)){
							surkshaDTO.setKSplrClampHose20mmPipe(Constants.LABEL_BLANK);
						}else{
							surkshaDTO.setKSplrClampHose20mmPipe(SecurityManager.decrypt(cursor.getString(16),Constants.SALT));
						}
						
					//}					
					surkshaDTO.setIsSync(((Integer.parseInt(cursor.getString(17)))));
					surkshaDTO.setCreatedBy(((Integer.parseInt(cursor.getString(18)))));
					surkshaDTO.setCreateDon(SecurityManager.decrypt(cursor.getString(19),Constants.SALT));
					surkshaDTO.setUpdateBy(((Integer.parseInt(cursor.getString(20)))));
					surkshaDTO.setUpdateDon(SecurityManager.decrypt(cursor.getString(21),Constants.SALT));

				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainanceDTOService:"+ Utility.convertExceptionToString(ex));
		}		
		return surkshaDTO;
	}


	@SuppressWarnings("static-access")
	public MakeGeyserDTO getMakeGeyserByMaitainanceId(int MaintainanceId){

		//MakeGeyserVO res = new MakeGeyserVO();
		
		MakeGeyserDTO makeGeyserDTO = new MakeGeyserDTO();

		try{
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID,Constants.DB_IS_GEYSER_AVAILABLE,Constants.DB_MAKE_ID,Constants.DB_MAKE_OTHER_TEXT,
					Constants.DB_GEYSER_TYPE_ID,Constants.DB_ISINSIDE_BATHROOM,Constants.DB_ISSYNC,
					Constants.DB_CREATEDBY,Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			String where ="maintainanceid =" +MaintainanceId;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_MAKE_AND_GEYSER, fieldList,where);
			if(cursor.moveToFirst()){
				do{
				//	res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					//res.setIsGeyserAvailable((SecurityManager.decrypt(cursor.getString(1),Constants.SALT)));
					makeGeyserDTO.setIsGeyserAvailable((SecurityManager.decrypt(cursor.getString(1),Constants.SALT)));
					if(cursor.isNull(2))
					{
						makeGeyserDTO.setMakeId(0);
						//res.setMakeId(0);
					}else{
						//res.setMakeId(((Integer.parseInt(cursor.getString(2)))));
						makeGeyserDTO.setMakeId(((Integer.parseInt(cursor.getString(2)))));
					}
					if(!cursor.isNull(3))
					{	
						//res.setMakeotherText((SecurityManager.decrypt(cursor.getString(3),Constants.SALT)));
						makeGeyserDTO.setMakeotherText((SecurityManager.decrypt(cursor.getString(3),Constants.SALT)));
					}
					if(cursor.isNull(4))
					{
						//res.setMakeId(0);
						makeGeyserDTO.setGeyserTypeId(0);
					}else{
						//res.setGeyserTypeId(((Integer.parseInt(cursor.getString(4)))));
						makeGeyserDTO.setGeyserTypeId(((Integer.parseInt(cursor.getString(4)))));
					}
					if(!cursor.isNull(5))
					{
						//res.setIsInsideBathroom(((SecurityManager.decrypt(cursor.getString(5),Constants.SALT))));
						makeGeyserDTO.setIsInsideBathroom(((SecurityManager.decrypt(cursor.getString(5),Constants.SALT))));
					}
					/*res.setIsSync(((Integer.parseInt(cursor.getString(6)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(7)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(8),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(9)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(10),Constants.SALT));			*/
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainanceDTOService:"+ Utility.convertExceptionToString(ex));
		}
		//MakeGeyserDTO makeGeyserDTO = new MakeGeyserDTO(res);
		dbHelper.closeDB();
		return makeGeyserDTO;
	}
	
	
	@SuppressWarnings("static-access")
	public OtherChecksDTO getOtherchecksByMaintainanceID(int MaintaiananceId){

		//OtherChecksVO res = new OtherChecksVO();
		
		OtherChecksDTO otherChecksDTO = new OtherChecksDTO();

		try{
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID, Constants.DB_RUBBERCAP, Constants.DB_RUBBER_CAP_CHARGABLE
					, Constants.DB_RUBBER_CAP_SUPPLIED_BY, Constants.DB_BRASSBALL_VALVE, Constants.DB_BRASSBALL_VALUE_CHARGABLE, Constants.DB_BRASS_BALL_VALUE_SUPPLIED_BY,
					Constants.DB_BRASSBALL_COCK, Constants.DB_BRASSBALL_COCK_CHARGABLE, Constants.DB_BRASSBALL_COCK_SUPPLIED_BY,
					Constants.DB_SUPPLY_BUSH_VALUE, Constants.DB_SUPPLY_BUSH_VALUE_CHARGABLE, Constants.DB_SUPPLY_BUSH_SUPPLIED_BY,
					Constants.DB_IS_METER_OK,Constants.DB_METER_READING,
					Constants.DB_MTR_PERFORMANCE,Constants.DB_MTR_READABLE,Constants.DB_GIPIPE_INSIDE_BM, Constants.DB_PVC_SLEEVE,
					Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			String where ="maintainanceid =" +MaintaiananceId;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_OTHER_CHECKS, fieldList,where);
			if(cursor.moveToFirst()){
				do{	
					//res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					
					
					/*if(SecurityManager.decrypt(cursor.getString(1),Constants.SALT).equals(Constants.LABEL_BLANK)){
						res.setSupplyBush(Constants.ZERO);
					}else{
						res.setSupplyBush(((SecurityManager.decrypt(cursor.getString(1), Constants.SALT))));
					}*/
					
					if(SecurityManager.decrypt(cursor.getString(1),Constants.SALT).equals(Constants.LABEL_BLANK)){
						//res.setRubberCap(Constants.ZERO);
						otherChecksDTO.setRubberCap(0);
					}else{
						//res.setRubberCap(((SecurityManager.decrypt(cursor.getString(1),Constants.SALT))));
						otherChecksDTO.setRubberCap(Integer.parseInt(((SecurityManager.decrypt(cursor.getString(1),Constants.SALT)))));
					}
					if(SecurityManager.decrypt(cursor.getString(2),Constants.SALT).equals(Constants.LABEL_BLANK)){
						//res.setRubberCapChargable(Constants.ZERO);
						otherChecksDTO.setRubberCapChargable(0);
					}else{
						//res.setRubberCapChargable(((SecurityManager.decrypt(cursor.getString(2), Constants.SALT))));
						otherChecksDTO.setRubberCapChargable(Integer.parseInt(((SecurityManager.decrypt(cursor.getString(2), Constants.SALT)))));
					}
					
					//res.setRubberCapSuppliedBy(((SecurityManager.decrypt(cursor.getString(3), Constants.SALT))));
					otherChecksDTO.setRubberCapSuppliedBy(((SecurityManager.decrypt(cursor.getString(3), Constants.SALT))));
				
					if(SecurityManager.decrypt(cursor.getString(4),Constants.SALT).equals(Constants.LABEL_BLANK)){
						//res.setBrassBallValue(Constants.ZERO);
						otherChecksDTO.setBrassBallValve(0);
					}else{
						//res.setBrassBallValue(((SecurityManager.decrypt(cursor.getString(4),Constants.SALT))));
						otherChecksDTO.setBrassBallValve(Integer.parseInt(((SecurityManager.decrypt(cursor.getString(4),Constants.SALT)))));
					}
					
					if(SecurityManager.decrypt(cursor.getString(5),Constants.SALT).equals(Constants.LABEL_BLANK)){
						//res.setBrassBallValueChargable(Constants.ZERO);
						otherChecksDTO.setBrassBallValueChargable(0);
					}else{
						//res.setBrassBallValueChargable(((SecurityManager.decrypt(cursor.getString(5), Constants.SALT))));
						otherChecksDTO.setBrassBallValueChargable(Integer.parseInt(((SecurityManager.decrypt(cursor.getString(5), Constants.SALT)))));
					}
					
					//res.setBrassBallSuppliedBy(((SecurityManager.decrypt(cursor.getString(6), Constants.SALT))));
					otherChecksDTO.setBrassBallSuppliedBy(((SecurityManager.decrypt(cursor.getString(6), Constants.SALT))));
					
					if(SecurityManager.decrypt(cursor.getString(7),Constants.SALT).equals(Constants.LABEL_BLANK)){
						//res.setBrassBallCock(Constants.ZERO);
						otherChecksDTO.setBrassBallCock(0);
					}else{
						//res.setBrassBallCock(((SecurityManager.decrypt(cursor.getString(7),Constants.SALT))));
						otherChecksDTO.setBrassBallCock(Integer.parseInt(((SecurityManager.decrypt(cursor.getString(7),Constants.SALT)))));
					}
					
					if(SecurityManager.decrypt(cursor.getString(8),Constants.SALT).equals(Constants.LABEL_BLANK)){
						//res.setBrassBallCockChargable(Constants.ZERO);
						otherChecksDTO.setBrassBallCockChargable(0);
					}else{
						//res.setBrassBallCockChargable(((SecurityManager.decrypt(cursor.getString(8), Constants.SALT))));
						otherChecksDTO.setBrassBallCockChargable(Integer.parseInt(((SecurityManager.decrypt(cursor.getString(8), Constants.SALT)))));
					}
					
					//res.setBrassBallCockSuppliedBy(((SecurityManager.decrypt(cursor.getString(9), Constants.SALT))));
					otherChecksDTO.setBrassBallCockSuppliedBy(((SecurityManager.decrypt(cursor.getString(9), Constants.SALT))));
					
					if(SecurityManager.decrypt(cursor.getString(10),Constants.SALT).equals(Constants.LABEL_BLANK)){
						//res.setSupply3by4into1by2Bush(Constants.ZERO);
						otherChecksDTO.setSupply3by4into1by2Bush(0);
					}else{
						//res.setSupply3by4into1by2Bush(((SecurityManager.decrypt(cursor.getString(10), Constants.SALT))));
						otherChecksDTO.setSupply3by4into1by2Bush(Integer.parseInt(((SecurityManager.decrypt(cursor.getString(10), Constants.SALT)))));
					}
					if(SecurityManager.decrypt(cursor.getString(11),Constants.SALT).equals(Constants.LABEL_BLANK)){
						//res.setChrgSupply3by4into1by2Bush(Constants.ZERO);
						otherChecksDTO.setChrgSupply3by4into1by2Bush(0);
					}else{
						//res.setChrgSupply3by4into1by2Bush(((SecurityManager.decrypt(cursor.getString(11), Constants.SALT))));
						otherChecksDTO.setChrgSupply3by4into1by2Bush(Integer.parseInt(((SecurityManager.decrypt(cursor.getString(11), Constants.SALT)))));
					}
					
					//res.setsplrSupply3by4into1by2BushBy(((SecurityManager.decrypt(cursor.getString(12), Constants.SALT))));
					otherChecksDTO.setSplrSupply3by4into1by2BushBy(((SecurityManager.decrypt(cursor.getString(12), Constants.SALT))));
					
					otherChecksDTO.setIsMeter(((SecurityManager.decrypt(cursor.getString(13),Constants.SALT))));
					
					if(((SecurityManager.decrypt(cursor.getString(14),Constants.SALT))).equals(Constants.LABEL_BLANK)){
						otherChecksDTO.setMeterReading(0);
					}else{
						otherChecksDTO.setMeterReading(Integer.parseInt(((SecurityManager.decrypt(cursor.getString(14),Constants.SALT)))));
					}
					
					otherChecksDTO.setMeterPerformance(((SecurityManager.decrypt(cursor.getString(15),Constants.SALT))));
					otherChecksDTO.setMeterReadable((((SecurityManager.decrypt(cursor.getString(16),Constants.SALT)))));
					otherChecksDTO.setGIPipeInsideBasement((((SecurityManager.decrypt(cursor.getString(17),Constants.SALT)))));
					otherChecksDTO.setPvcSleeve(((SecurityManager.decrypt(cursor.getString(18), Constants.SALT))));
					
					
					//res.setIsMeter(((SecurityManager.decrypt(cursor.getString(13),Constants.SALT))));
					//res.setMeterReading(((SecurityManager.decrypt(cursor.getString(14),Constants.SALT))));
					//res.setMtrPerformance(((SecurityManager.decrypt(cursor.getString(15),Constants.SALT))));
					//res.setMtrReadable((((SecurityManager.decrypt(cursor.getString(16),Constants.SALT)))));
					//res.setGiPipeInsidebm((((SecurityManager.decrypt(cursor.getString(17),Constants.SALT)))));
					//res.setPvcSleeve(((SecurityManager.decrypt(cursor.getString(18), Constants.SALT))));
					//res.setIsSync(((Integer.parseInt(cursor.getString(19)))));
					//res.setCreatedBy(((Integer.parseInt(cursor.getString(20)))));
					//res.setCreatedOn(SecurityManager.decrypt(cursor.getString(21),Constants.SALT));
					//res.setUpdatedBy(((Integer.parseInt(cursor.getString(22)))));
					//res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(23),Constants.SALT));
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainanceDTOService:"+ Utility.convertExceptionToString(ex));
		}
		//OtherChecksDTO otherChkDTO = new OtherChecksDTO(res);
		dbHelper.closeDB();
	
		return otherChecksDTO;
	}

	/*@SuppressWarnings("static-access")
	public OtherChecksDTO getOtherchecksByMaintainanceID(int MaintaiananceId){

		OtherChecksVO res = new OtherChecksVO();

		try{
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID,Constants.DB_RUBBERCAP,Constants.DB_BRASSBALL_VALVE,
					Constants.DB_BRASSBALL_COCK,Constants.DB_IS_METER_OK,Constants.DB_METER_READING,
					Constants.DB_MTR_PERFORMANCE,Constants.DB_MTR_READABLE,Constants.DB_GIPIPE_INSIDE_BM,
					Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			String where ="maintainanceid =" +MaintaiananceId;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_OTHER_CHECKS, fieldList,where);
			if(cursor.moveToFirst()){
				do{	
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setRubberCap(((SecurityManager.decrypt(cursor.getString(1),Constants.SALT))));
					res.setBrassBallValue(((SecurityManager.decrypt(cursor.getString(2),Constants.SALT))));
					if(!cursor.isNull(3)){
						res.setBrassBallCock(((SecurityManager.decrypt(cursor.getString(3),Constants.SALT))));
					}
					if(!cursor.isNull(4))
					{
						res.setIsMeter(((SecurityManager.decrypt(cursor.getString(4),Constants.SALT))));
					}
					res.setMeterReading(((SecurityManager.decrypt(cursor.getString(5),Constants.SALT))));
					res.setMtrPerformance(((SecurityManager.decrypt(cursor.getString(6),Constants.SALT))));
					res.setMtrReadable((((SecurityManager.decrypt(cursor.getString(7),Constants.SALT)))));
					res.setGiPipeInsidebm((((SecurityManager.decrypt(cursor.getString(8),Constants.SALT)))));
					res.setIsSync(((Integer.parseInt(cursor.getString(9)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(10)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(11),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(12)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(13),Constants.SALT));
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainanceDTOService:"+ Utility.convertExceptionToString(ex));
		}
		OtherChecksDTO otherChkDTO = new OtherChecksDTO(res);
		dbHelper.closeDB();
		return otherChkDTO;
	}*/

	@SuppressWarnings("static-access")
	public CustomerFeedbackDTO getCustomerFeedbackByMaintainaceID(int MaintainanceId){

		//CustomerFeedbackVO res = new CustomerFeedbackVO();
		CustomerFeedbackDTO customerFeedbackDTO = new CustomerFeedbackDTO();
		try{
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID,Constants.DB_COMMENTS,Constants.DB_RECEIPT_NO,
					Constants.DB_NOTICE_NO,Constants.DB_SIGNATURE,
					Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			String where ="maintainanceid =" +MaintainanceId;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_CUSTOMER_FEEDBACK, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					customerFeedbackDTO.setComments(((SecurityManager.decrypt(cursor.getString(1),Constants.SALT))));
					customerFeedbackDTO.setReceiptNo(((SecurityManager.decrypt(cursor.getString(2),Constants.SALT))));
					customerFeedbackDTO.setNoticeNo(((SecurityManager.decrypt(cursor.getString(3),Constants.SALT))));
					customerFeedbackDTO.setSignature(((SecurityManager.decrypt(cursor.getString(4),Constants.SALT))));
					//res.setMaintainanceID(((Integer.parseInt(cursor.getString(0)))));
					//res.setComments(((SecurityManager.decrypt(cursor.getString(1),Constants.SALT))));
					//res.setReceiptNo(((SecurityManager.decrypt(cursor.getString(2),Constants.SALT))));
					//res.setNoticeNo(((SecurityManager.decrypt(cursor.getString(3),Constants.SALT))));
					//res.setSignature(((SecurityManager.decrypt(cursor.getString(4),Constants.SALT))));
					//res.setIssync(((Integer.parseInt(cursor.getString(5)))));
					//res.setCreatedBy(((Integer.parseInt(cursor.getString(6)))));
					//res.setCreatedOn(SecurityManager.decrypt(cursor.getString(7),Constants.SALT));
					//res.setUpdatedBy(((Integer.parseInt(cursor.getString(8)))));
					//res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(9),Constants.SALT));
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainanceDTOService:"+ Utility.convertExceptionToString(ex));
		}
		/*CustomerFeedbackDTO cusDTO = new CustomerFeedbackDTO(res);
		res.cleanomerFeedbackCustVO();*/
		return customerFeedbackDTO;
	}

	public ArrayList<GIFittingMSTDTO> getGIfittingMasterDataSize(){

		ArrayList<GIFittingMSTDTO> res = new ArrayList<GIFittingMSTDTO>();

		try{
			String[] fieldList ={Constants.DB_MST_GIFITTING_ID};

			Cursor cursor = dbHelper.cursorSelectAll(Constants.TBL_MST_GIFITTING, fieldList);
			if(cursor.moveToFirst()){
				do{
					GIFittingMSTDTO giFittingMSTDTO = new GIFittingMSTDTO();
					giFittingMSTDTO.setGiFittingId(cursor.getInt(0));
					res.add(giFittingMSTDTO);

				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainanceDTOService:"+ Utility.convertExceptionToString(ex));
		}
		return res;
	}

	public ArrayList<Integer> getGIfittingMasterSize(){

		ArrayList<Integer> res = new ArrayList<Integer>();

		try{
			String[] fieldList ={Constants.DB_MST_GIFITTING_ID};

			Cursor cursor = dbHelper.cursorSelectAll(Constants.TBL_MST_GIFITTING, fieldList);
			if(cursor.moveToFirst()){
				do{
					res.add(cursor.getInt(0));

				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainanceDTOService:"+ Utility.convertExceptionToString(ex));
		}
		return res;
	}
}