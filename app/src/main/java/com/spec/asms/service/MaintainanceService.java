package com.spec.asms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.DBHelper;
import com.spec.asms.common.utils.SecurityManager;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.dto.ConformanceDetailDTO;
import com.spec.asms.dto.MaintainanceDTO;
import com.spec.asms.vo.BatchSubmitVO;
import com.spec.asms.vo.ClampingVO;
import com.spec.asms.vo.ConformanceDetailVO;
import com.spec.asms.vo.ConformanceMasterVO;
import com.spec.asms.vo.CustomerFeedbackVO;
import com.spec.asms.vo.DamageNCauseDetailVO;
import com.spec.asms.vo.GIFittingVO;
import com.spec.asms.vo.KitchenSurakshaTubeVO;
import com.spec.asms.vo.LeakageVO;
import com.spec.asms.vo.MaintainanceVO;
import com.spec.asms.vo.MakeGeyserVO;
import com.spec.asms.vo.OtherChecksVO;
import com.spec.asms.vo.OtherKitchenSurakshaTubeVO;
import com.spec.asms.vo.PaintingVO;
import com.spec.asms.vo.RccVO;
import com.spec.asms.vo.TestingVO;

public class MaintainanceService {

	private DBHelper dbHelper;
	private Context context;
	public MaintainanceService(Context context){
		this.context = context;
		try{
			dbHelper = new DBHelper(context);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	//common Insert For Maintainance : Testing,Leakage,Gifitting,Clamping,painting,Rcc,Surkshatube,make&geyser,otherchecks

	public long insertMaintainance(String tableName,ContentValues values){
		return dbHelper.insertToTable(tableName, values);
	}

	public long updateMaintainance(String tableName,ContentValues values,String where){
		return dbHelper.updateToTable(tableName, values, where,null);
	}

	public long deleteMaintainance(String tableName,String where){
		return dbHelper.delete(tableName, where, null);
	}


	@SuppressWarnings("static-access")
	public MaintainanceVO getMaintainacebyMOrderID(String tableName,String[] fieldList,int MorderID){
		MaintainanceVO res = new MaintainanceVO();

		try
		{
			String where ="maintainanceorderid = "+MorderID;
			Cursor cursor = dbHelper.cursorSelectByWhere(tableName, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setMaintainanceOrderId(((Integer.parseInt(cursor.getString(1)))));
					res.setCustomerId(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));
					res.setDate(SecurityManager.decrypt(cursor.getString(3),Constants.SALT));
					res.setTime(SecurityManager.decrypt(cursor.getString(4),Constants.SALT));
					res.setStatusCode(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
					res.setIsSync(((Integer.parseInt(cursor.getString(6)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(7)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(8),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(9)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(10),Constants.SALT));
					res.setStartTime(SecurityManager.decrypt(cursor.getString(11),Constants.SALT));
					res.setEndTime(SecurityManager.decrypt(cursor.getString(12),Constants.SALT));
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return res;
	}

	//MaintainanceVO
	@SuppressWarnings("static-access")
	public List<MaintainanceVO> getAllMaintainace(String tableName,String[] fieldList){
		List<MaintainanceVO> maintainanceLst = new ArrayList<MaintainanceVO>();

		try{
			Cursor cursor = dbHelper.cursorSelectAll(tableName, fieldList);
			if(cursor.moveToFirst()){
				do{
					MaintainanceVO res = new MaintainanceVO();
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setMaintainanceOrderId(((Integer.parseInt(cursor.getString(1)))));
					res.setCustomerId(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));
					res.setDate(SecurityManager.decrypt(cursor.getString(3),Constants.SALT));
					res.setTime(SecurityManager.decrypt(cursor.getString(4),Constants.SALT));
					res.setStatusCode(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
					res.setIsSync(((Integer.parseInt(cursor.getString(6)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(7)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(8),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(9)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(10),Constants.SALT));
					res.setStartTime(SecurityManager.decrypt(cursor.getString(11),Constants.SALT));
					res.setEndTime(SecurityManager.decrypt(cursor.getString(12),Constants.SALT));
					maintainanceLst.add(res);
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return maintainanceLst;
	}

	public List<MaintainanceDTO> getMaintainace(String tableName,String[] fieldList){
		List<MaintainanceDTO> maintainanceLst=new ArrayList<MaintainanceDTO>();

		try{
			Cursor cursor=dbHelper.cursorSelectAll(tableName, fieldList);
			if(cursor.moveToFirst()){
				do{
					MaintainanceDTO res=new MaintainanceDTO();
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setMaintainanceOrderId(((Integer.parseInt(cursor.getString(1)))));
					res.setCustomerId(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));
					Date date = new Date(SecurityManager.decrypt(cursor.getString(3),Constants.SALT));
					res.setDate(date.getTime());
					res.setStatusCode(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
					maintainanceLst.add(res);
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return maintainanceLst;
	}

	public int getCompletedRecordCount(){
		int totalRecords = 0;
		try{
			String where = "issync = " + 0;
			totalRecords = dbHelper.countTotalRows(Constants.TBL_DTL_MAINTAINANCE, where);
		}catch(Exception e){
			e.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(e));
		}

		return totalRecords;
	}

	public int getTotalCompletedRecordCount(){
		int totalRecords = 0;
		try{
			String where = "issync = 0";
			totalRecords = dbHelper.countTotalRows(Constants.TBL_DTL_MAINTAINANCE, where);
		}catch(Exception e){
			e.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(e));
		}

		return totalRecords;
	}

	public int getTotalSyncRecordCount(){
		int totalRecords = 0;
		try{
			String where = "issync = " + 1;
			totalRecords = dbHelper.countTotalRows(Constants.TBL_DTL_MAINTAINANCE, where);
		}catch(Exception e){
			e.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(e));
		}
		return totalRecords;
	}
	//NEW METHOD
	public List<MaintainanceDTO> getMaintainaceList(String tableName,String[] fieldList){
		List<MaintainanceDTO> maintainanceLst=new ArrayList<MaintainanceDTO>();
		String where = "issync = " + 0;
		try{
			Cursor cursor=dbHelper.cursorSelectByWhereData(tableName, fieldList, where);
			if(cursor.moveToFirst()){
				do{
					if(((Integer.parseInt(cursor.getString(6)))) == 0) {
						MaintainanceDTO res=new MaintainanceDTO();
						res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
						res.setMaintainanceOrderId(((Integer.parseInt(cursor.getString(1)))));
						res.setCustomerId(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));
						try {
							Date date = new Date(SecurityManager.decrypt(cursor.getString(3),Constants.SALT));
							res.setDate(date.getTime());
						} catch (IllegalArgumentException e) {
							res.setDate(new Date().getTime());
						}


						res.setStatusCode(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
						res.setIsSync(((Integer.parseInt(cursor.getString(6)))));
						res.setCreatedBy(((Integer.parseInt(cursor.getString(7)))));
						res.setCreatedOn(SecurityManager.decrypt(cursor.getString(8),Constants.SALT));
						res.setUpdatedBy(((Integer.parseInt(cursor.getString(9)))));
						res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(10),Constants.SALT));
						res.setStartTime(SecurityManager.decrypt(cursor.getString(11),Constants.SALT));
						res.setEndTime(SecurityManager.decrypt(cursor.getString(12),Constants.SALT));
						// Added by Pankit Mistry - To support Contact number and Alternate contact number
						if(cursor.getString(13) != null && !cursor.getString(13).equals(Constants.BLANK))
							res.setContactNo(SecurityManager.decrypt(cursor.getString(13),Constants.SALT));
						if(cursor.getString(14) != null && !cursor.getString(14).equals(Constants.BLANK))
							res.setAltContactNo(SecurityManager.decrypt(cursor.getString(14),Constants.SALT));
						maintainanceLst.add(res);
					}

				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return maintainanceLst;
	}


	public List<MaintainanceDTO> getBatchMaintainaceList(String tableName,String[] fieldList,int batchSize){
		List<MaintainanceDTO> maintainanceLst=new ArrayList<MaintainanceDTO>();
		String where = "issync = " + 0;
		try{
			Cursor cursor=dbHelper.cursorSelectByWhereLimit(tableName, fieldList, where,batchSize);
			if(cursor.moveToFirst()){
				do{
					if(((Integer.parseInt(cursor.getString(6)))) == 0) {
						MaintainanceDTO res = new MaintainanceDTO();
						res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
						res.setMaintainanceOrderId(((Integer.parseInt(cursor.getString(1)))));
						res.setCustomerId(SecurityManager.decrypt(cursor.getString(2), Constants.SALT));
						if(!cursor.getString(3).equals(Constants.BLANK)) {
							Date date = new Date(SecurityManager.decrypt(cursor.getString(3), Constants.SALT));
							res.setDate(date.getTime());
						} else {
							Date date = new Date();
							res.setDate(date.getTime());
						}
						res.setStatusCode(SecurityManager.decrypt(cursor.getString(5), Constants.SALT));
						res.setIsSync(((Integer.parseInt(cursor.getString(6)))));
						res.setCreatedBy(((Integer.parseInt(cursor.getString(7)))));
						res.setCreatedOn(SecurityManager.decrypt(cursor.getString(8), Constants.SALT));
						res.setUpdatedBy(((Integer.parseInt(cursor.getString(9)))));
						res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(10), Constants.SALT));
						res.setStartTime(SecurityManager.decrypt(cursor.getString(11), Constants.SALT));
						res.setEndTime(SecurityManager.decrypt(cursor.getString(12), Constants.SALT));
						// Added by Pankit Mistry - To support Contact number and Alternate contact number
						if (cursor.getString(13) != null && !cursor.getString(13).equals(Constants.BLANK))
							res.setContactNo(SecurityManager.decrypt(cursor.getString(13), Constants.SALT));
						if (cursor.getString(14) != null && !cursor.getString(14).equals(Constants.BLANK))
							res.setAltContactNo(SecurityManager.decrypt(cursor.getString(14), Constants.SALT));

						maintainanceLst.add(res);
					}
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return maintainanceLst;
	}


	public List<MaintainanceDTO> getMaintainaceDTO(String tableName,String[] fieldList,String where){
		List<MaintainanceDTO> maintainanceLst=new ArrayList<MaintainanceDTO>();

		try
		{
			Cursor cursor = dbHelper.cursorSelectByWhere(tableName, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					MaintainanceDTO res = new MaintainanceDTO();
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setMaintainanceOrderId(((Integer.parseInt(cursor.getString(1)))));
					res.setCustomerId(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));
					Date date = new Date(SecurityManager.decrypt(cursor.getString(3),Constants.SALT));
					res.setDate(date.getTime());
//						Date time = new Date(SecurityManager.decrypt(cursor.getString(4),Constants.SALT));
//						res.setTime(time.getTime());
					res.setStatusCode(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
					res.setIsSync(((Integer.parseInt(cursor.getString(6)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(7)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(8),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(9)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(10),Constants.SALT));
					res.setStartTime(SecurityManager.decrypt(cursor.getString(11),Constants.SALT));
					res.setEndTime(SecurityManager.decrypt(cursor.getString(12),Constants.SALT));
					maintainanceLst.add(res);
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return maintainanceLst;
	}

	@SuppressWarnings("static-access")
	public List<MaintainanceVO> getMaintainacebyIsSync(String tableName,String[] fieldList,String where){
		List<MaintainanceVO> maintainanceLst=new ArrayList<MaintainanceVO>();

		try
		{
			Cursor cursor = dbHelper.cursorSelectByWhere(tableName, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					MaintainanceVO res = new MaintainanceVO();
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setMaintainanceOrderId(((Integer.parseInt(cursor.getString(1)))));
					res.setCustomerId(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));
					res.setDate(SecurityManager.decrypt(cursor.getString(3),Constants.SALT));
					res.setTime(SecurityManager.decrypt(cursor.getString(4),Constants.SALT));
					res.setStatusCode(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
					res.setIsSync(((Integer.parseInt(cursor.getString(6)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(7)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(8),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(9)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(10),Constants.SALT));
					res.setStartTime(SecurityManager.decrypt(cursor.getString(11),Constants.SALT));
					res.setEndTime(SecurityManager.decrypt(cursor.getString(12),Constants.SALT));
					maintainanceLst.add(res);
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return maintainanceLst;
	}

	public long deleteManitanance(String where){
		return dbHelper.delete(Constants.TBL_DTL_MAINTAINANCE,where,null);
	}

	public int getTestingId(String where)
	{
		String[] fieldList ={Constants.DB_TESTING_ID};
		int testingID = 0;
		Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_TESTING, fieldList, where);

		try{
			if(cursor.moveToFirst()){
				do{
					testingID = cursor.getInt(0);
				}while(cursor.moveToNext());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		dbHelper.closeDB();
		return testingID;
	}

	public int getMaintainanceId(int maintainanceorderid)
	{
		String[] fieldList ={Constants.DB_MAINTAINANCE_ID};
		int maintainanceId = 0;
		String where = "maintainanceorderid = " + maintainanceorderid;
		Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_MAINTAINANCE, fieldList, where);

		try{
			if(cursor.moveToFirst()){
				do{
					maintainanceId = cursor.getInt(0);
				}while(cursor.moveToNext());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		dbHelper.closeDB();
		return maintainanceId;
	}

	public String getMaintainanceStatus(int maintainanceID)
	{
		String[] fieldList ={Constants.DB_STATUS_CODE};
		String statusCode = "";
		String where = "maintainanceorderid = " + maintainanceID;
		Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_MAINTAINANCE, fieldList, where);

		try{
			if(cursor.moveToFirst()){
				do{
					statusCode = cursor.getString(0);
				}while(cursor.moveToNext());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		dbHelper.closeDB();
		return statusCode;
	}


	//Testing
	@SuppressWarnings("static-access")
	public List<TestingVO> getAllTestingDetail(){
		List<TestingVO> testingList = new ArrayList<TestingVO>();

		try{
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID,Constants.DB_INITIAL_PRESSURE,Constants.DB_FINAL_PRESSURE,Constants.DB_PRESSURE_DROP,Constants.DB_DURATION,
					Constants.DB_GAS_LKG_DETECTION_TEST,Constants.DB_PRESSURE_TYPE,Constants.DB_GAS_LKG_RECTIFIED,Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,
					Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			Cursor cursor = dbHelper.cursorSelectAll(Constants.TBL_DTL_TESTING, fieldList);
			if(cursor.moveToFirst()){
				do{
					TestingVO res = new TestingVO();
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setInitialPressure(SecurityManager.decrypt(cursor.getString(1),Constants.SALT));
					res.setFinalPressure(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));
					res.setPressureDrop(SecurityManager.decrypt(cursor.getString(3),Constants.SALT));
					res.setDuration(SecurityManager.decrypt(cursor.getString(4),Constants.SALT));
					res.setGasLkgDetectionTest(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
					res.setPressureType(SecurityManager.decrypt(cursor.getString(6),Constants.SALT));
					res.setGasLkgRectified(SecurityManager.decrypt(cursor.getString(7),Constants.SALT));
					res.setIsSync(((Integer.parseInt(cursor.getString(8)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(9)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(10),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(11)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(12),Constants.SALT));
					testingList.add(res);
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return testingList;
	}

	@SuppressWarnings("static-access")
	public TestingVO getTestingDetailByMaintainanceID(int MaintainanceID){
		TestingVO res = new TestingVO();

		try{
			String where = "maintainanceid = " + MaintainanceID;
			String[] fieldList ={Constants.DB_TESTING_ID,Constants.DB_MAINTAINANCE_ID,Constants.DB_INITIAL_PRESSURE,Constants.DB_FINAL_PRESSURE,Constants.DB_PRESSURE_DROP,Constants.DB_DURATION,
					Constants.DB_GAS_LKG_DETECTION_TEST,Constants.DB_PRESSURE_TYPE,Constants.DB_GAS_LKG_RECTIFIED,Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,
					Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_TESTING, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(1)))));
					res.setInitialPressure(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));
					res.setFinalPressure(SecurityManager.decrypt(cursor.getString(3),Constants.SALT));
					res.setPressureDrop(SecurityManager.decrypt(cursor.getString(4),Constants.SALT));
					res.setDuration(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
					res.setGasLkgDetectionTest(SecurityManager.decrypt(cursor.getString(6),Constants.SALT));
					res.setPressureType(SecurityManager.decrypt(cursor.getString(7),Constants.SALT));
					res.setGasLkgRectified(SecurityManager.decrypt(cursor.getString(8),Constants.SALT));
					res.setIsSync(((Integer.parseInt(cursor.getString(9)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(10)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(11),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(12)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(13),Constants.SALT));
					res.setTestingId(Integer.parseInt(cursor.getString(0)));
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return res;
	}

	//Clamping
/*	@SuppressWarnings("static-access")
	public List<ClampingVO> getAllClampingDetail(){
		List<ClampingVO> clampingList = new ArrayList<ClampingVO>();

		try{
			String[] fieldList = {Constants.DB_MAINTAINANCE_ID,Constants.DB_ISWORKING,Constants.DB_CLAMP_HALF,Constants.DB_CLAMP_ONE,Constants.DB_CHEESEHEADSCREW,
					Constants.DB_WOODSCREW,Constants.DB_ROUL_PLUG,Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,
					Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			Cursor cursor = dbHelper.cursorSelectAll(Constants.TBL_DTL_CLAMPING, fieldList);
			if(cursor.moveToFirst()){
				do{
					ClampingVO res = new ClampingVO();
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setIsWorking(SecurityManager.decrypt(cursor.getString(1),Constants.SALT));
					res.setClampHalf(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));
					res.setClampOne(SecurityManager.decrypt(cursor.getString(3),Constants.SALT));
					res.setCheeseHeadScrew(SecurityManager.decrypt(cursor.getString(4),Constants.SALT));
					res.setWoodScrew(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
					res.setRoulPlug(SecurityManager.decrypt(cursor.getString(6),Constants.SALT));
					res.setIsSync(((Integer.parseInt(cursor.getString(7)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(8)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(9),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(10)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(11),Constants.SALT));
					clampingList.add(res);
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return clampingList;
	}*/

	@SuppressWarnings("static-access")
	public ClampingVO getClampingByMaintainanceID(int MainatainanceId){
		ClampingVO res = new ClampingVO();

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

			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_CLAMPING, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setIsWorking(SecurityManager.decrypt(cursor.getString(1),Constants.SALT));
					res.setPipelineProtectionClamp(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));

					if(res.getIsWorking().equals("NO") && res.getIsWorking()!= Constants.LABEL_BLANK){
						res.setChrgClamp1by2(SecurityManager.decrypt(cursor.getString(3),Constants.SALT));
						res.setClamp1by2(SecurityManager.decrypt(cursor.getString(4),Constants.SALT));
						res.setChrgClamp1(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
						res.setClamp1(SecurityManager.decrypt(cursor.getString(6),Constants.SALT));
					}

					res.setChrgCheeseHeadScrew(SecurityManager.decrypt(cursor.getString(7),Constants.SALT));
					res.setCheeseHeadScrew(SecurityManager.decrypt(cursor.getString(8),Constants.SALT));
					res.setChrgWoodScrew(SecurityManager.decrypt(cursor.getString(9),Constants.SALT));
					res.setWoodScrew(SecurityManager.decrypt(cursor.getString(10),Constants.SALT));
					res.setChrgRoulPlug(SecurityManager.decrypt(cursor.getString(11),Constants.SALT));
					res.setRoulPlug(SecurityManager.decrypt(cursor.getString(12),Constants.SALT));
					res.setSplrClamp1by2(SecurityManager.decrypt(cursor.getString(13),Constants.SALT));
					res.setSplrClamp1(SecurityManager.decrypt(cursor.getString(14),Constants.SALT));
					res.setSplrCheeseHeadScrew(SecurityManager.decrypt(cursor.getString(15),Constants.SALT));
					res.setSplrWoodScrew(SecurityManager.decrypt(cursor.getString(16),Constants.SALT));
					res.setSplrRoulPlug(SecurityManager.decrypt(cursor.getString(17),Constants.SALT));

					res.setIsSync(((Integer.parseInt(cursor.getString(18)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(19)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(20),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(21)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(22),Constants.SALT));
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return res;
	}

	public String getLeakageName(int id) {
		String leakageName =  Constants.LABEL_BLANK;
		String where = "leakageid = " +id;
		String fieldList[] = {"text"};

		try{
			Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_LEAKAGE,fieldList,where);
			if(cursor.moveToFirst()){
				do{
					leakageName=cursor.getString(0);
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return leakageName;
	}

	@SuppressWarnings("static-access")
	public LeakageVO getLeakageDetailByTestingID(int id){
		LeakageVO res = new LeakageVO();

		try{
			String[] fieldList ={Constants.DB_LEAKAGE_ID,Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,
					Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			String where = "testingid = "+id;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_LEAKAGE, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					res.setLkgId(((Integer.parseInt(cursor.getString(0)))));
					res.setIsSync(((Integer.parseInt(cursor.getString(1)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(2)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(3),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(4)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return res;
	}

	public List<LeakageVO> getLeakageLstByTestingID(int id){
		List<LeakageVO> lst = new ArrayList<LeakageVO>();

		try{
			String[] fieldList ={Constants.DB_LEAKAGE_ID,Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,
					Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			String where = "testingid = "+id;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_LEAKAGE, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					LeakageVO res = new LeakageVO();
					res.setServiceLkgId(((Integer.parseInt(cursor.getString(0)))));
					res.setServiceisSync(((Integer.parseInt(cursor.getString(1)))));
					res.setServicecreatedBy(((Integer.parseInt(cursor.getString(2)))));
					res.setServicecreatedOn((SecurityManager.decrypt(cursor.getString(3),Constants.SALT)));
					res.setServiceupdatedBy(((Integer.parseInt(cursor.getString(4)))));
					res.setServiceupdatedOn((SecurityManager.decrypt(cursor.getString(5),Constants.SALT)));
					lst.add(res);
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return lst;
	}

	@SuppressWarnings("static-access")
	public GIFittingVO getGIfittingDetailByTestingID(int id){
		GIFittingVO res = new GIFittingVO();

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
						res.setElbow(SecurityManager.decrypt(cursor.getString(1),Constants.SALT));
						res.setTee(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));
						res.setHexNipple(SecurityManager.decrypt(cursor.getString(3),Constants.SALT));
						res.setUnion(SecurityManager.decrypt(cursor.getString(4),Constants.SALT));
						res.setPlug(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
						res.setGicap(SecurityManager.decrypt(cursor.getString(6),Constants.SALT));
						res.setGicoupling(SecurityManager.decrypt(cursor.getString(7),Constants.SALT));
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
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return res;
	}

	@SuppressWarnings("static-access")
	public MaintainanceVO getMaintainaceByCustomerID(String tableName,String[] fieldList,String custid){
		MaintainanceVO res = new MaintainanceVO();

		try{
			String where ="customerid = '"+SecurityManager.encrypt(custid,Constants.SALT)+"'";
			Cursor cursor = dbHelper.cursorSelectByWhere(tableName, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setMaintainanceOrderId(((Integer.parseInt(cursor.getString(1)))));
					res.setCustomerId(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));
					res.setDate(SecurityManager.decrypt(cursor.getString(3),Constants.SALT));
					res.setTime(SecurityManager.decrypt(cursor.getString(4),Constants.SALT));
					res.setStatusCode(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
					res.setIsSync(((Integer.parseInt(cursor.getString(6)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(7)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(8),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(9)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(10),Constants.SALT));
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return res;
	}

	@SuppressWarnings("static-access")
	public String getLastSubmitDate(String tableName,String custid){
		String res = "";

		String[] fields = new String[1];
		fields[0] = "date";
		try{
			String where ="customerid = '"+SecurityManager.encrypt(custid,Constants.SALT)+"'";
			Cursor cursor = dbHelper.cursorSelectByWhere(tableName, fields,where);
			if(cursor.moveToFirst()){
				do{
					res = SecurityManager.decrypt(cursor.getString(0),Constants.SALT);
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return res;
	}

	@SuppressWarnings("static-access")
	public List<GIFittingVO> getGIfittingLstByTestingID(int id){

		List<GIFittingVO> lst = new ArrayList<GIFittingVO>();

		try{
			String[] fieldList ={Constants.DB_DTL_GIFITTING_ID,Constants.DB_ELBOW,Constants.DB_TEE,Constants.DB_HAXNIPPLE
					,Constants.DB_GI_UNION,Constants.DB_PLUG,Constants.DB_GI_CAP,Constants.DB_GI_COUPLING,Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,
					Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON,Constants.DB_VALUE};

			String where = "testingid = "+id;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_GIFITTING, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					GIFittingVO res = new GIFittingVO();
					res.setGiFittingId(((Integer.parseInt(cursor.getString(0)))));
					res.setElbow(SecurityManager.decrypt(cursor.getString(1),Constants.SALT));
					res.setTee(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));
					res.setHexNipple(SecurityManager.decrypt(cursor.getString(3),Constants.SALT));
					res.setUnion(SecurityManager.decrypt(cursor.getString(4),Constants.SALT));
					res.setPlug(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
					res.setGicap(SecurityManager.decrypt(cursor.getString(6),Constants.SALT));
					res.setGicoupling(SecurityManager.decrypt(cursor.getString(7),Constants.SALT));
					res.setIsSync(((Integer.parseInt(cursor.getString(8)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(9)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(10),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(11)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(12),Constants.SALT));
					res.setIsWorking(SecurityManager.decrypt(cursor.getString(13),Constants.SALT));
					lst.add(res);
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return lst;
	}

	@SuppressWarnings("static-access")
	public List<PaintingVO> getAllPaintingOringDetail(){
		List<PaintingVO> paintingList = new ArrayList<PaintingVO>();

		try{
			String[] fieldList ={
					Constants.DB_MAINTAINANCE_ID,Constants.DB_ISPAINTING_WORK,Constants.DB_PAINT,
					Constants.DB_ISORING_WORK,Constants.DB_ORMETER,Constants.DB_ORDOM_REGULATOR,
					Constants.DB_ORAUDCOGLAND,Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,
					Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			Cursor cursor = dbHelper.cursorSelectAll(Constants.TBL_DTL_PAINTINGORING, fieldList);
			if(cursor.moveToFirst()){
				do{
					PaintingVO res = new PaintingVO();
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setIsPaintingWork((SecurityManager.decrypt(cursor.getString(1),Constants.SALT)));
					res.setPaint(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));
					res.setIsOringWork(SecurityManager.decrypt(cursor.getString(3),Constants.SALT));
					res.setOrMeter(SecurityManager.decrypt(cursor.getString(4),Constants.SALT));
					res.setOrDomRegulator(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
					res.setOrAudcoGland(SecurityManager.decrypt(cursor.getString(6),Constants.SALT));
					res.setIsSync(((Integer.parseInt(cursor.getString(7)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(8)))));
					res.setCreateDon(SecurityManager.decrypt(cursor.getString(9),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(10)))));
					res.setUpdateDon(SecurityManager.decrypt(cursor.getString(11),Constants.SALT));
					paintingList.add(res);
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return paintingList;
	}

	@SuppressWarnings("static-access")
	public PaintingVO getPaintingOringByMaintainanceID(int MaintainanceId){
		PaintingVO res = new PaintingVO();

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
						res.setPaint(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));
					}

					res.setIsOringWork(SecurityManager.decrypt(cursor.getString(3),Constants.SALT));
					if(res.getIsOringWork().equals("YES") && res.getIsOringWork()!= Constants.LABEL_BLANK){
						res.setOrMeter(SecurityManager.decrypt(cursor.getString(4),Constants.SALT));
						res.setOrDomRegulator(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
						res.setOrAudcoGland(SecurityManager.decrypt(cursor.getString(6),Constants.SALT));
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
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return res;
	}

	/*@SuppressWarnings("static-access")
	public List<RccVO> getAllRCCDetail(){
		List<RccVO> rccList = new ArrayList<RccVO>();

		try{
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID,Constants.DB_ISWORKING,Constants.DB_MSNAIL,
					Constants.DB_MSNUT,Constants.DB_COACH_SCREW,Constants.DB_ISSYNC,
					Constants.DB_RCC_GUARD,Constants.DB_CREATEDBY,
					Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			Cursor cursor = dbHelper.cursorSelectAll(Constants.TBL_DTL_RCC, fieldList);
			if(cursor.moveToFirst()){
				do{
					RccVO res=new RccVO();
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setIsWorking((SecurityManager.decrypt(cursor.getString(1),Constants.SALT)));
					res.setMsNail(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));
					res.setMsNut(SecurityManager.decrypt(cursor.getString(3),Constants.SALT));
					res.setCoachScrew(SecurityManager.decrypt(cursor.getString(4),Constants.SALT));
					res.setIsSync(((Integer.parseInt(cursor.getString(5)))));
					res.setRccGuard(SecurityManager.decrypt(cursor.getString(6),Constants.SALT));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(7)))));
					res.setCreateDon(SecurityManager.decrypt(cursor.getString(8),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(9)))));
					res.setUpdateDon(SecurityManager.decrypt(cursor.getString(10),Constants.SALT));
					rccList.add(res);
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return rccList;
	}*/

	@SuppressWarnings("static-access")
	public RccVO getRCCDetailByMaintainanceID(int MaintainanceId){
		RccVO res = new RccVO();

		try{
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID, Constants.DB_IS_SAND_FILLING, Constants.DB_ISWORKING,Constants.DB_MSNAIL,
					Constants.DB_MSNAIL_CHARGABLE, Constants.DB_MSNAIL_SUPPLIEDBY,
					Constants.DB_MSNUT, Constants.DB_MSNUT_CHARGABLE, Constants.DB_MSNUT_SUPPLIEDBY, Constants.DB_COACH_SCREW,Constants.DB_ISSYNC,
					Constants.DB_RCC_GUARD, Constants.DB_RCC_GUARD_CHARGABLE, Constants.DB_RCC_GUARD_SUPPLIEDBY, Constants.DB_CREATEDBY,
					Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			String where ="maintainanceid =" +MaintainanceId;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_RCC, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setIsSandFilling((SecurityManager.decrypt(cursor.getString(1), Constants.SALT)));
					res.setIsWorking((SecurityManager.decrypt(cursor.getString(2),Constants.SALT)));
					if(res.getIsWorking().equals("NO") && res.getIsWorking()!= Constants.LABEL_BLANK){
						res.setMsNail(SecurityManager.decrypt(cursor.getString(3),Constants.SALT));
						res.setMsNailChargable(SecurityManager.decrypt(cursor.getString(4), Constants.SALT));
						res.setMsNailSuppliedBy(SecurityManager.decrypt(cursor.getString(5), Constants.SALT));
						res.setMsNut(SecurityManager.decrypt(cursor.getString(6),Constants.SALT));
						res.setMsNutChargable(SecurityManager.decrypt(cursor.getString(7), Constants.SALT));
						res.setMsNutSuppliedBy(SecurityManager.decrypt(cursor.getString(8), Constants.SALT));
						res.setCoachScrew(SecurityManager.decrypt(cursor.getString(9),Constants.SALT));
						res.setRccGuard(SecurityManager.decrypt(cursor.getString(11),Constants.SALT));
						res.setRccGuardChargable(SecurityManager.decrypt(cursor.getString(12), Constants.SALT));
						res.setRccGuardSuppliedBy(SecurityManager.decrypt(cursor.getString(13), Constants.SALT));
					}
					res.setIsSync(((Integer.parseInt(cursor.getString(10)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(14)))));
					res.setCreateDon(SecurityManager.decrypt(cursor.getString(15),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(16)))));
					res.setUpdateDon(SecurityManager.decrypt(cursor.getString(17),Constants.SALT));
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return res;
	}

	/*@SuppressWarnings("static-access")
	public List<SurakshaTubeVO> getAllSurkshaTubeDetail(){
		List<SurakshaTubeVO> surkshaList = new ArrayList<SurakshaTubeVO>();

		try{
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID,Constants.DB_IS_REPLACED,Constants.DB_SIZE1,
					Constants.DB_SIZE2,Constants.DB_SIZE3,Constants.DB_ISSYNC,
					Constants.DB_CLAMPSIZE1,Constants.DB_CLAMPSIZE2,Constants.DB_CREATEDBY,
					Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			Cursor cursor = dbHelper.cursorSelectAll(Constants.TBL_DTL_SURAKSHA_TUBE, fieldList);
			if(cursor.moveToFirst()){
				do{
					SurakshaTubeVO res = new SurakshaTubeVO();
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setIsReplaced(((SecurityManager.decrypt(cursor.getString(1),Constants.SALT))));
					res.setSize1((SecurityManager.decrypt(cursor.getString(2),Constants.SALT)));
					res.setSize2((SecurityManager.decrypt(cursor.getString(3),Constants.SALT)));
					res.setSize3((SecurityManager.decrypt(cursor.getString(4),Constants.SALT)));
					res.setIsSync(((Integer.parseInt(cursor.getString(5)))));
					res.setClampSize1((SecurityManager.decrypt(cursor.getString(6),Constants.SALT)));
					res.setClamSize2((SecurityManager.decrypt(cursor.getString(7),Constants.SALT)));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(8)))));
					res.setCreateDon(SecurityManager.decrypt(cursor.getString(9),Constants.SALT));
					res.setUpdateBy(((Integer.parseInt(cursor.getString(10)))));
					res.setUpdateDon(SecurityManager.decrypt(cursor.getString(11),Constants.SALT));
					surkshaList.add(res);
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return surkshaList;
	}*/

	@SuppressWarnings("static-access")
	public KitchenSurakshaTubeVO getSurkshaTubeByMaintainanceID(int MaintainanceId){
		KitchenSurakshaTubeVO res = new KitchenSurakshaTubeVO();

		try{

			String[] fieldList ={
					Constants.DB_MAINTAINANCE_ID,Constants.DB_IS_REPLACED,
					Constants.DB_SIZE_751C,Constants.DB_SIZE_751NC,
					Constants.DB_SIZE_7915C,Constants.DB_SIZE_7915NC,
					Constants.DB_SIZE_12515C,Constants.DB_SIZE_12515NC,
					Constants.DB_KSPL_751,Constants.DB_KSPL_7915,
					Constants.DB_KSPL_12515,
					Constants.DB_SIZE_CLAMP_8C,Constants.DB_SIZE_CLAMP_8NC,
					Constants.DB_SIZE_CLAMP_20C,Constants.DB_SIZE_CLAMP_20NC,
					Constants.DB_KSPL_8,Constants.DB_KSPL_20,
					Constants.DB_ISSYNC,Constants.DB_CREATEDBY,
					Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,
					Constants.DB_UPDATEDON,Constants.DB_EXPIRY_DATE,Constants.DB_REPLACE_EXPIRY_DATE};

			String where ="maintainanceid =" +MaintainanceId;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_SURAKSHA_TUBE, fieldList,where);
			if(cursor.moveToFirst()){
				do{

					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setIsReplaced(((SecurityManager.decrypt(cursor.getString(1),Constants.SALT))));
					if(res.getIsReplaced().equals("YES") && res.getIsReplaced()!= Constants.LABEL_BLANK){
						res.setSize751c((SecurityManager.decrypt(cursor.getString(2),Constants.SALT)));
						res.setSize751nc((SecurityManager.decrypt(cursor.getString(3),Constants.SALT)));
						res.setSize7915c((SecurityManager.decrypt(cursor.getString(4),Constants.SALT)));
						res.setSize7915nc((SecurityManager.decrypt(cursor.getString(5),Constants.SALT)));
						res.setSize12515c((SecurityManager.decrypt(cursor.getString(6),Constants.SALT)));
						res.setSize12515nc((SecurityManager.decrypt(cursor.getString(7),Constants.SALT)));
						res.setKSplr1Meter75mm((SecurityManager.decrypt(cursor.getString(8),Constants.SALT)));
						res.setKSplr15Meter79mm((SecurityManager.decrypt(cursor.getString(9),Constants.SALT)));
						res.setKSplrChrg15Meter125mm((SecurityManager.decrypt(cursor.getString(10),Constants.SALT)));
					}

					res.setClampsize8c((SecurityManager.decrypt(cursor.getString(11),Constants.SALT)));
					res.setClampsize8nc((SecurityManager.decrypt(cursor.getString(12),Constants.SALT)));
					res.setClampsize20c((SecurityManager.decrypt(cursor.getString(13),Constants.SALT)));
					res.setClampsize20nc((SecurityManager.decrypt(cursor.getString(14),Constants.SALT)));
					res.setKSplrClampHose8mmPipe((SecurityManager.decrypt(cursor.getString(15),Constants.SALT)));
					res.setKSplrClampHose20mmPipe((SecurityManager.decrypt(cursor.getString(16),Constants.SALT)));
					res.setIsSync(((Integer.parseInt(cursor.getString(17)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(18)))));
					res.setCreateDon(SecurityManager.decrypt(cursor.getString(19),Constants.SALT));
					res.setUpdateBy(((Integer.parseInt(cursor.getString(20)))));
					res.setUpdateDon(SecurityManager.decrypt(cursor.getString(21),Constants.SALT));
					res.setExpirydate(SecurityManager.decrypt(cursor.getString(22),Constants.SALT));
					res.setReplaceexpirydate(SecurityManager.decrypt(cursor.getString(23),Constants.SALT));

				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return res;
	}

	@SuppressWarnings("static-access")
	public OtherKitchenSurakshaTubeVO getOtherSurkshaTubeByMaintainanceID(int MaintainanceId){
		OtherKitchenSurakshaTubeVO res = new OtherKitchenSurakshaTubeVO();

		try{

			String[] fieldList ={
					Constants.DB_MAINTAINANCE_ID,Constants.DB_IS_REPLACED,
					Constants.DB_SIZE_751C,Constants.DB_SIZE_751NC,
					Constants.DB_SIZE_7915C,Constants.DB_SIZE_7915NC,
					Constants.DB_SIZE_12515C,Constants.DB_SIZE_12515NC,
					Constants.DB_KSPL_751,Constants.DB_KSPL_7915,
					Constants.DB_KSPL_12515,
					Constants.DB_SIZE_CLAMP_8C,Constants.DB_SIZE_CLAMP_8NC,
					Constants.DB_SIZE_CLAMP_20C,Constants.DB_SIZE_CLAMP_20NC,
					Constants.DB_KSPL_8,Constants.DB_KSPL_20,
					Constants.DB_ISSYNC,Constants.DB_CREATEDBY,
					Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,
					Constants.DB_UPDATEDON};

			String where ="maintainanceid =" +MaintainanceId;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_OTHER_SURAKSHA_TUBE, fieldList,where);
			if(cursor.moveToFirst()){
				do{

					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setIsReplaced(((SecurityManager.decrypt(cursor.getString(1),Constants.SALT))));
					if(res.getIsReplaced().equals("YES") && res.getIsReplaced()!= Constants.LABEL_BLANK){
						res.setSize751c((SecurityManager.decrypt(cursor.getString(2),Constants.SALT)));
						res.setSize751nc((SecurityManager.decrypt(cursor.getString(3),Constants.SALT)));
						res.setSize7915c((SecurityManager.decrypt(cursor.getString(4),Constants.SALT)));
						res.setSize7915nc((SecurityManager.decrypt(cursor.getString(5),Constants.SALT)));
						res.setSize12515c((SecurityManager.decrypt(cursor.getString(6),Constants.SALT)));
						res.setSize12515nc((SecurityManager.decrypt(cursor.getString(7),Constants.SALT)));
						res.setKSplr1Meter75mm((SecurityManager.decrypt(cursor.getString(8),Constants.SALT)));
						res.setKSplr15Meter79mm((SecurityManager.decrypt(cursor.getString(9),Constants.SALT)));
						res.setKSplrChrg15Meter125mm((SecurityManager.decrypt(cursor.getString(10),Constants.SALT)));
					}

					res.setClampsize8c((SecurityManager.decrypt(cursor.getString(11),Constants.SALT)));
					res.setClampsize8nc((SecurityManager.decrypt(cursor.getString(12),Constants.SALT)));
					res.setClampsize20c((SecurityManager.decrypt(cursor.getString(13),Constants.SALT)));
					res.setClampsize20nc((SecurityManager.decrypt(cursor.getString(14),Constants.SALT)));
					res.setKSplrClampHose8mmPipe((SecurityManager.decrypt(cursor.getString(15),Constants.SALT)));
					res.setKSplrClampHose20mmPipe((SecurityManager.decrypt(cursor.getString(16),Constants.SALT)));
					res.setIsSync(((Integer.parseInt(cursor.getString(17)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(18)))));
					res.setCreateDon(SecurityManager.decrypt(cursor.getString(19),Constants.SALT));
					res.setUpdateBy(((Integer.parseInt(cursor.getString(20)))));
					res.setUpdateDon(SecurityManager.decrypt(cursor.getString(21),Constants.SALT));

				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return res;
	}
	
/*	@SuppressWarnings("static-access")
	public SurakshaTubeVO getSurkshaTubeByMaintainanceID(int MaintainanceId){
		SurakshaTubeVO res = new SurakshaTubeVO();

		try{
			
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID,Constants.DB_IS_REPLACED,Constants.DB_SIZE1,
					Constants.DB_SIZE2,Constants.DB_SIZE3,Constants.DB_ISSYNC,
					Constants.DB_CLAMPSIZE1,Constants.DB_CLAMPSIZE2,Constants.DB_CREATEDBY,
					Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			String where ="maintainanceid =" +MaintainanceId;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_SURAKSHA_TUBE, fieldList,where);
			if(cursor.moveToFirst()){
				do{

					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setIsReplaced(((SecurityManager.decrypt(cursor.getString(1),Constants.SALT))));
					if(res.getIsReplaced().equals("YES") && res.getIsReplaced()!= Constants.LABEL_BLANK){
						res.setSize1((SecurityManager.decrypt(cursor.getString(2),Constants.SALT)));
						res.setSize2((SecurityManager.decrypt(cursor.getString(3),Constants.SALT)));
						res.setSize3((SecurityManager.decrypt(cursor.getString(4),Constants.SALT)));
					}					
					res.setIsSync(((Integer.parseInt(cursor.getString(5)))));
					res.setClampSize1((SecurityManager.decrypt(cursor.getString(6),Constants.SALT)));
					res.setClamSize2((SecurityManager.decrypt(cursor.getString(7),Constants.SALT)));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(8)))));
					res.setCreateDon(SecurityManager.decrypt(cursor.getString(9),Constants.SALT));
					res.setUpdateBy(((Integer.parseInt(cursor.getString(10)))));
					res.setUpdateDon(SecurityManager.decrypt(cursor.getString(11),Constants.SALT));
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return res;
	}*/


	@SuppressWarnings("static-access")
	public List<MakeGeyserVO> getAllMakeGeyserList(){
		List<MakeGeyserVO> makeGeyserList=new ArrayList<MakeGeyserVO>();

		try{
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID,Constants.DB_IS_GEYSER_AVAILABLE,Constants.DB_MAKE_ID,Constants.DB_MAKE_OTHER_TEXT,
					Constants.DB_GEYSER_TYPE_ID,Constants.DB_ISINSIDE_BATHROOM,Constants.DB_ISSYNC,
					Constants.DB_CREATEDBY,Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			Cursor cursor = dbHelper.cursorSelectAll(Constants.TBL_DTL_MAKE_AND_GEYSER, fieldList);
			if(cursor.moveToFirst()){
				do{
					MakeGeyserVO res = new MakeGeyserVO();
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setIsGeyserAvailable((SecurityManager.decrypt(cursor.getString(1),Constants.SALT)));
					res.setMakeId(((Integer.parseInt(cursor.getString(2)))));
					res.setMakeotherText((SecurityManager.decrypt(cursor.getString(3),Constants.SALT)));
					res.setGeyserTypeId(((Integer.parseInt(cursor.getString(4)))));
					res.setIsInsideBathroom(((SecurityManager.decrypt(cursor.getString(5),Constants.SALT))));
					res.setIsSync(((Integer.parseInt(cursor.getString(6)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(7)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(8),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(9)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(10),Constants.SALT));
					makeGeyserList.add(res);
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return makeGeyserList;
	}

	@SuppressWarnings("static-access")
	public MakeGeyserVO getMakeGeyserByMaitainanceId(int MaintainanceId){
		MakeGeyserVO res = new MakeGeyserVO();

		try{
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID,Constants.DB_IS_GEYSER_AVAILABLE,Constants.DB_MAKE_ID,Constants.DB_MAKE_OTHER_TEXT,
					Constants.DB_GEYSER_TYPE_ID,Constants.DB_ISINSIDE_BATHROOM,Constants.DB_ISSYNC,
					Constants.DB_CREATEDBY,Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			String where ="maintainanceid =" +MaintainanceId;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_MAKE_AND_GEYSER, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setIsGeyserAvailable((SecurityManager.decrypt(cursor.getString(1),Constants.SALT)));
					if(!cursor.isNull(2))
					{
						res.setMakeId(((Integer.parseInt(cursor.getString(2)))));
					}
					res.setMakeotherText((SecurityManager.decrypt(cursor.getString(3),Constants.SALT)));
					res.setGeyserTypeId(((Integer.parseInt(cursor.getString(4)))));
					res.setIsInsideBathroom(((SecurityManager.decrypt(cursor.getString(5),Constants.SALT))));
					res.setIsSync(((Integer.parseInt(cursor.getString(6)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(7)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(8),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(9)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(10),Constants.SALT));
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return res;
	}


/*	//Other Checks
	@SuppressWarnings("static-access")
	public List<OtherChecksVO> getAllOtherchecksList(){
		List<OtherChecksVO> otherchecksList = new ArrayList<OtherChecksVO>();

		try{
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID,Constants.DB_RUBBERCAP,Constants.DB_BRASSBALL_VALVE,Constants.DB_BRASSBALL_COCK,
					Constants.DB_IS_METER_OK,Constants.DB_METER_READING,
					Constants.DB_MTR_PERFORMANCE,Constants.DB_MTR_READABLE,Constants.DB_GIPIPE_INSIDE_BM,
					Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			Cursor cursor = dbHelper.cursorSelectAll(Constants.TBL_DTL_OTHER_CHECKS, fieldList);
			if(cursor.moveToFirst()){
				do{
					OtherChecksVO res = new OtherChecksVO();
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setRubberCap(((SecurityManager.decrypt(cursor.getString(1),Constants.SALT))));
					res.setBrassBallValue(((SecurityManager.decrypt(cursor.getString(2),Constants.SALT))));
					res.setBrassBallCock(((SecurityManager.decrypt(cursor.getString(3),Constants.SALT))));
					res.setIsMeter(((SecurityManager.decrypt(cursor.getString(4),Constants.SALT))));
					res.setMeterReading(((SecurityManager.decrypt(cursor.getString(5),Constants.SALT))));
					res.setMtrPerformance(((SecurityManager.decrypt(cursor.getString(6),Constants.SALT))));
					res.setMtrReadable((((SecurityManager.decrypt(cursor.getString(7),Constants.SALT)))));
					res.setGiPipeInsidebm((((SecurityManager.decrypt(cursor.getString(8),Constants.SALT)))));
					res.setIsSync(((Integer.parseInt(cursor.getString(9)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(10)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(11),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(12)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(13),Constants.SALT));
					otherchecksList.add(res);
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return otherchecksList;
	}*/


	@SuppressWarnings("static-access")
	public List<OtherChecksVO> getAllOtherchecksList(){
		List<OtherChecksVO> otherchecksList = new ArrayList<OtherChecksVO>();

		try{
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID, Constants.DB_RUBBERCAP, Constants.DB_RUBBER_CAP_CHARGABLE,
					Constants.DB_RUBBER_CAP_SUPPLIED_BY, Constants.DB_BRASSBALL_VALVE, Constants.DB_BRASSBALL_VALUE_CHARGABLE, Constants.DB_BRASS_BALL_VALUE_SUPPLIED_BY,
					Constants.DB_BRASSBALL_COCK, Constants.DB_BRASSBALL_COCK_CHARGABLE, Constants.DB_BRASSBALL_COCK_SUPPLIED_BY,
					Constants.DB_SUPPLY_BUSH_VALUE, Constants.DB_SUPPLY_BUSH_VALUE_CHARGABLE, Constants.DB_SUPPLY_BUSH_SUPPLIED_BY,
					Constants.DB_IS_METER_OK,Constants.DB_METER_READING,
					Constants.DB_MTR_PERFORMANCE,Constants.DB_MTR_READABLE,Constants.DB_GIPIPE_INSIDE_BM, Constants.DB_PVC_SLEEVE,
					Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			Cursor cursor = dbHelper.cursorSelectAll(Constants.TBL_DTL_OTHER_CHECKS, fieldList);
			if(cursor.moveToFirst()){
				do{
					OtherChecksVO res = new OtherChecksVO();
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					//res.setSupplyBush(((SecurityManager.decrypt(cursor.getString(1), Constants.SALT))));
					res.setRubberCap(((SecurityManager.decrypt(cursor.getString(1),Constants.SALT))));
					res.setRubberCapChargable(((SecurityManager.decrypt(cursor.getString(2), Constants.SALT))));
					res.setRubberCapSuppliedBy(((SecurityManager.decrypt(cursor.getString(3), Constants.SALT))));
					res.setBrassBallValue(((SecurityManager.decrypt(cursor.getString(4),Constants.SALT))));
					res.setBrassBallValueChargable((SecurityManager.decrypt(cursor.getString(5), Constants.SALT)));
					res.setBrassBallSuppliedBy(((SecurityManager.decrypt(cursor.getString(6), Constants.SALT))));
					res.setBrassBallCock(((SecurityManager.decrypt(cursor.getString(7),Constants.SALT))));
					res.setBrassBallCockChargable(((SecurityManager.decrypt(cursor.getString(8), Constants.SALT))));
					res.setBrassBallCockSuppliedBy(((SecurityManager.decrypt(cursor.getString(9), Constants.SALT))));
					res.setSupply3by4into1by2Bush(((SecurityManager.decrypt(cursor.getString(10), Constants.SALT))));
					res.setChrgSupply3by4into1by2Bush(((SecurityManager.decrypt(cursor.getString(11), Constants.SALT))));
					res.setsplrSupply3by4into1by2BushBy(((SecurityManager.decrypt(cursor.getString(12), Constants.SALT))));
					res.setIsMeter(((SecurityManager.decrypt(cursor.getString(13),Constants.SALT))));
					res.setMeterReading(((SecurityManager.decrypt(cursor.getString(14),Constants.SALT))));
					res.setMtrPerformance(((SecurityManager.decrypt(cursor.getString(15),Constants.SALT))));
					res.setMtrReadable((((SecurityManager.decrypt(cursor.getString(16),Constants.SALT)))));
					res.setGiPipeInsidebm((((SecurityManager.decrypt(cursor.getString(17),Constants.SALT)))));
					res.setPvcSleeve(((SecurityManager.decrypt(cursor.getString(18), Constants.SALT))));
					res.setIsSync(((Integer.parseInt(cursor.getString(19)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(20)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(21),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(22)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(23),Constants.SALT));
					otherchecksList.add(res);
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return otherchecksList;
	}


	@SuppressWarnings("static-access")
	public OtherChecksVO getOtherchecksByMaintainanceID(int MaintaiananceId){
		OtherChecksVO res = new OtherChecksVO();

		try{
			String[] fieldList ={
					Constants.DB_MAINTAINANCE_ID,
					Constants.DB_RUBBERCAP,
					Constants.DB_RUBBER_CAP_CHARGABLE,
					Constants.DB_RUBBER_CAP_SUPPLIED_BY,
					Constants.DB_BRASSBALL_VALVE,
					Constants.DB_BRASSBALL_VALUE_CHARGABLE,
					Constants.DB_BRASS_BALL_VALUE_SUPPLIED_BY,
					Constants.DB_BRASSBALL_COCK,
					Constants.DB_BRASSBALL_COCK_CHARGABLE,
					Constants.DB_BRASSBALL_COCK_SUPPLIED_BY,
					Constants.DB_SUPPLY_BUSH_VALUE,
					Constants.DB_SUPPLY_BUSH_VALUE_CHARGABLE,
					Constants.DB_SUPPLY_BUSH_SUPPLIED_BY,
					Constants.DB_IS_METER_OK,
					Constants.DB_METER_READING,
					Constants.DB_MTR_PERFORMANCE,
					Constants.DB_MTR_READABLE,
					Constants.DB_GIPIPE_INSIDE_BM,
					Constants.DB_PVC_SLEEVE,
					Constants.DB_ISSYNC,
					Constants.DB_CREATEDBY,
					Constants.DB_CREATEDON,
					Constants.DB_UPDATEDBY,
					Constants.DB_UPDATEDON};

			String where ="maintainanceid =" +MaintaiananceId;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_OTHER_CHECKS, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					//res.setSupplyBush(((SecurityManager.decrypt(cursor.getString(1), Constants.SALT))));
					res.setRubberCap(((SecurityManager.decrypt(cursor.getString(1),Constants.SALT))));
					res.setRubberCapChargable(((SecurityManager.decrypt(cursor.getString(2), Constants.SALT))));
					res.setRubberCapSuppliedBy(((SecurityManager.decrypt(cursor.getString(3), Constants.SALT))));
					res.setBrassBallValue(((SecurityManager.decrypt(cursor.getString(4),Constants.SALT))));
					res.setBrassBallValueChargable(((SecurityManager.decrypt(cursor.getString(5), Constants.SALT))));
					res.setBrassBallSuppliedBy(((SecurityManager.decrypt(cursor.getString(6), Constants.SALT))));
					res.setBrassBallCock(((SecurityManager.decrypt(cursor.getString(7),Constants.SALT))));
					res.setBrassBallCockChargable(((SecurityManager.decrypt(cursor.getString(8), Constants.SALT))));
					res.setBrassBallCockSuppliedBy(((SecurityManager.decrypt(cursor.getString(9), Constants.SALT))));
					res.setSupply3by4into1by2Bush(((SecurityManager.decrypt(cursor.getString(10), Constants.SALT))));
					res.setChrgSupply3by4into1by2Bush(((SecurityManager.decrypt(cursor.getString(11), Constants.SALT))));
					res.setsplrSupply3by4into1by2BushBy(((SecurityManager.decrypt(cursor.getString(12), Constants.SALT))));
					res.setIsMeter(((SecurityManager.decrypt(cursor.getString(13),Constants.SALT))));
					res.setMeterReading(((SecurityManager.decrypt(cursor.getString(14),Constants.SALT))));
					res.setMtrPerformance(((SecurityManager.decrypt(cursor.getString(15),Constants.SALT))));
					res.setMtrReadable((((SecurityManager.decrypt(cursor.getString(16),Constants.SALT)))));
					res.setGiPipeInsidebm((((SecurityManager.decrypt(cursor.getString(17),Constants.SALT)))));
					res.setPvcSleeve(((SecurityManager.decrypt(cursor.getString(18), Constants.SALT))));
					res.setIsSync(((Integer.parseInt(cursor.getString(19)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(20)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(21),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(22)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(23),Constants.SALT));
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return res;
	}

	/*@SuppressWarnings("static-access")
	public OtherChecksVO getOtherchecksByMaintainanceID(int MaintaiananceId){
		OtherChecksVO res = new OtherChecksVO();

		try{
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID,Constants.DB_RUBBERCAP,Constants.DB_BRASSBALL_VALVE,Constants.DB_BRASSBALL_COCK,
					Constants.DB_IS_METER_OK,Constants.DB_METER_READING,
					Constants.DB_MTR_PERFORMANCE,Constants.DB_MTR_READABLE,Constants.DB_GIPIPE_INSIDE_BM,
					Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			String where ="maintainanceid =" +MaintaiananceId;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_OTHER_CHECKS, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					res.setMaintainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setRubberCap(((SecurityManager.decrypt(cursor.getString(1),Constants.SALT))));
					res.setBrassBallValue(((SecurityManager.decrypt(cursor.getString(2),Constants.SALT))));
					res.setBrassBallCock(((SecurityManager.decrypt(cursor.getString(3),Constants.SALT))));
					res.setIsMeter(((SecurityManager.decrypt(cursor.getString(4),Constants.SALT))));
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
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return res;
	}
*/
	@SuppressWarnings("static-access")
	public List<ConformanceDetailVO> getAllConformanceListByMaitainanceID(int MaintaiananceId){
		List<ConformanceDetailVO> conformanceList=new ArrayList<ConformanceDetailVO>();

		try{
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID,Constants.DB_CONFORMANCE_ID,Constants.DB_TEXT,
					Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON,Constants.DB_IS_NC};
			String where ="maintainanceid =" +MaintaiananceId;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_CONFORMANCE, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					ConformanceDetailVO res=new ConformanceDetailVO();
					res.setMantainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setServiceConformanceID(((Integer.parseInt(cursor.getString(1)))));
					//					Log.e("***************************", "cursor.getString(2)::::::: "+cursor.getString(2));
					if(!cursor.isNull(2)){
						res.setText(((SecurityManager.decrypt(cursor.getString(2),Constants.SALT))));
					}
					res.setIssync(((Integer.parseInt(cursor.getString(3)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(4)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(6)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(7),Constants.SALT));
					res.setIsNC(SecurityManager.decrypt(cursor.getString(8),Constants.SALT));
					conformanceList.add(res);
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return conformanceList;
	}

	public List<ConformanceDetailDTO> getAllConformanceDTOListByMaitainanceID(int MaintaiananceId){
		List<ConformanceDetailDTO> conformanceList = new ArrayList<ConformanceDetailDTO>();

		try{
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID,Constants.DB_CONFORMANCE_ID,Constants.DB_TEXT,
					Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON,Constants.DB_IS_NC};
			String where ="maintainanceid =" +MaintaiananceId;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_CONFORMANCE, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					ConformanceDetailDTO res = new ConformanceDetailDTO();
					res.setMantainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setConformanceId(((Integer.parseInt(cursor.getString(1)))));
					Log.e("***************************", "cursor.getString(1)::::::: "+cursor.getString(1));
					if(!cursor.isNull(2)){
						res.setText(((SecurityManager.decrypt(cursor.getString(2),Constants.SALT))));
					}
					res.setIssync(((Integer.parseInt(cursor.getString(3)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(4)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(6)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(7),Constants.SALT));
					if(!cursor.isNull(8))
					{
						res.setIsNc(SecurityManager.decrypt(cursor.getString(8),Constants.SALT));
					}
					conformanceList.add(res);
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return conformanceList;
	}


	@SuppressWarnings("static-access")
	public ConformanceDetailVO getConformanceByMaintainanceID(int MaintainaceId){
		ConformanceDetailVO res = new ConformanceDetailVO();

		try{
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID,Constants.DB_CONFORMANCE_ID,Constants.DB_TEXT,
					Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON,Constants.DB_IS_NC};

			String where ="maintainanceid =" +MaintainaceId;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_CONFORMANCE, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					res.setMantainanceId(((Integer.parseInt(cursor.getString(0)))));
					res.setConformanceId(((Integer.parseInt(cursor.getString(1)))));
					res.setText(((SecurityManager.decrypt(cursor.getString(2),Constants.SALT))));
					Log.e("COnformance Other Text GET:--","--------"+(SecurityManager.decrypt(cursor.getString(2),Constants.SALT)));
					res.setIssync(((Integer.parseInt(cursor.getString(3)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(4)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(6)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(7),Constants.SALT));
					res.setIsNC(SecurityManager.decrypt(cursor.getString(8),Constants.SALT));
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return res;
	}

	@SuppressWarnings("static-access")
	public List<CustomerFeedbackVO> getAllCustomerFeedback(){
		List<CustomerFeedbackVO> feedbackList = new ArrayList<CustomerFeedbackVO>();

		try{
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID,Constants.DB_COMMENTS,Constants.DB_RECEIPT_NO,
					Constants.DB_NOTICE_NO,Constants.DB_SIGNATURE,
					Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			Cursor cursor = dbHelper.cursorSelectAll(Constants.TBL_DTL_CUSTOMER_FEEDBACK, fieldList);
			if(cursor.moveToFirst()){
				do{
					CustomerFeedbackVO res = new CustomerFeedbackVO();
					res.setMaintainanceID(((Integer.parseInt(cursor.getString(0)))));
					res.setComments(((SecurityManager.decrypt(cursor.getString(1),Constants.SALT))));
					res.setReceiptNo(((SecurityManager.decrypt(cursor.getString(2),Constants.SALT))));
					res.setNoticeNo(((SecurityManager.decrypt(cursor.getString(3),Constants.SALT))));
					res.setSignature(((SecurityManager.decrypt(cursor.getString(4),Constants.SALT))));
					res.setIssync(((Integer.parseInt(cursor.getString(5)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(6)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(7),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(8)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(9),Constants.SALT));
					feedbackList.add(res);
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return feedbackList;
	}

	@SuppressWarnings("static-access")
	public CustomerFeedbackVO getCustomerFeedbackByMaintainaceID(int MaintainanceId){
		CustomerFeedbackVO res = new CustomerFeedbackVO();

		try{
			String[] fieldList ={Constants.DB_MAINTAINANCE_ID,Constants.DB_COMMENTS,Constants.DB_RECEIPT_NO,
					Constants.DB_NOTICE_NO,Constants.DB_SIGNATURE,
					Constants.DB_ISSYNC,Constants.DB_CREATEDBY,Constants.DB_CREATEDON,Constants.DB_UPDATEDBY,Constants.DB_UPDATEDON};

			String where ="maintainanceid =" +MaintainanceId;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_CUSTOMER_FEEDBACK, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					res.setMaintainanceID(((Integer.parseInt(cursor.getString(0)))));
					res.setComments(((SecurityManager.decrypt(cursor.getString(1),Constants.SALT))));
					res.setReceiptNo(((SecurityManager.decrypt(cursor.getString(2),Constants.SALT))));
					res.setNoticeNo(((SecurityManager.decrypt(cursor.getString(3),Constants.SALT))));
					res.setSignature(((SecurityManager.decrypt(cursor.getString(4),Constants.SALT))));
					res.setIssync(((Integer.parseInt(cursor.getString(5)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(6)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(7),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(8)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(9),Constants.SALT));
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return res;
	}

	//Customer Feed Back
	public long insertFeedback(String tableName,ContentValues values){
		return dbHelper.insertToTable(tableName, values);
	}

	public long updateFeedbackByID(String tableName,ContentValues values,String where){
		return dbHelper.updateToTable(tableName, values, where,null);
	}

	public long deleteFeedback(String where){
		return dbHelper.delete(Constants.TBL_DTL_CUSTOMER_FEEDBACK,where,null);
	}

	//ConformanceDetail
	public long insertConformanceDetail(String tableName,ContentValues values){
		return dbHelper.insertToTable(tableName, values);
	}

	public long updateConformanceDetail(String tableName,ContentValues values,String where){
		return dbHelper.updateToTable(tableName, values, where,null);
	}

	public long deleteConformanceDetail(String where){
		return dbHelper.delete(Constants.TBL_DTL_CONFORMANCE,where,null);
	}

	//Get Conformance Master Detail
	public List<ConformanceMasterVO> getNonConformanceReasons(String tableName,String[] fieldList){
		List<ConformanceMasterVO> reasonList=new ArrayList<ConformanceMasterVO>();

		Cursor cursor = dbHelper.cursorSelectAll(tableName, fieldList);
		if(cursor.moveToFirst()){
			do{
				ConformanceMasterVO res = new ConformanceMasterVO();
				res.setConformanceID(((Integer.parseInt(cursor.getString(0)))));
				res.setReason(cursor.getString(1));
				reasonList.add(res);
			}while(cursor.moveToNext());

		}
		if(cursor != null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		dbHelper.closeDB();
		return reasonList;
	}


	public List<DamageNCauseDetailVO> getListDetailDamageCauserByObjectNMorderID(String where){
		List<DamageNCauseDetailVO> reasonList=new ArrayList<DamageNCauseDetailVO>();

		try
		{
			String[] fieldList = {Constants.DB_DTL_DAMAGE_CAUSE_ID,Constants.DB_OBJECT_ID,Constants.DB_DAMAGE_ID,Constants.DB_CAUSE_ID,Constants.DB_MAINTAINANCE_ORDER_ID};
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_DAMAGE_CAUSE, fieldList, where);
			if(cursor.moveToFirst()){
				do{
					DamageNCauseDetailVO res = new DamageNCauseDetailVO();
					res.setDtldamagecauseId(((Integer.parseInt(cursor.getString(0)))));
					res.setObjectId(((Integer.parseInt(cursor.getString(1)))));
					res.setDamageId(((Integer.parseInt(cursor.getString(2)))));
					res.setCauseId(((Integer.parseInt(cursor.getString(3)))));
					res.setMaintainanceOrderId(cursor.getString(4));
					reasonList.add(res);
				}while(cursor.moveToNext());

			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return reasonList;
	}

	public DamageNCauseDetailVO getDetailDamageCauserByObjectNMorderID(String where){
		DamageNCauseDetailVO res = new DamageNCauseDetailVO();
		try
		{
			String[] fieldList = {Constants.DB_DTL_DAMAGE_CAUSE_ID,Constants.DB_OBJECT_ID,Constants.DB_DAMAGE_ID,Constants.DB_CAUSE_ID,Constants.DB_MAINTAINANCE_ORDER_ID};
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_DTL_DAMAGE_CAUSE, fieldList, where);
			if(cursor.moveToFirst()){
				do{

					res.setDtldamagecauseId(((Integer.parseInt(cursor.getString(0)))));
					res.setObjectId(((Integer.parseInt(cursor.getString(1)))));
					res.setDamageId(((Integer.parseInt(cursor.getString(2)))));
					res.setCauseId(((Integer.parseInt(cursor.getString(3)))));
					res.setMaintainanceOrderId(cursor.getString(4));
				}while(cursor.moveToNext());

			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return res;
	}
	public int getObjectIDFromGifitting(String text){

		int objId = 0;

		String[] fieldList = {Constants.DB_MST_GIFITTING_ID};
		String where = "text = '"+text+"'";
		Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_GIFITTING,fieldList,where);
		try{
			if(cursor.moveToFirst()){
				do{
					objId = cursor.getInt(0);
				}while(cursor.moveToNext());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		dbHelper.closeDB();
		return objId;
	}

	public int  getAutoSubmissionInterval(){
		int minutes = 60;
		try{
			String where = "key = '"+Constants.JSON_AUTO_SUBMISSION_INTERVAL+"'";
			String []fieldList={Constants.CUSTOMER_PARAMETER_FIELDS[2]};
			Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_CUSTOME_PARAMETER,fieldList,where);

			if(cursor != null)
			{
				if(cursor.moveToFirst()){
					do{
						minutes = Integer.parseInt(cursor.getString(0));
						Log.e("AutoSubmissionInterval",minutes+"");
					}while(cursor.moveToNext());
				}
			}

			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
			}
			dbHelper.closeDB();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return minutes;
	}


	public int  getBatchSize(){
		int batchSize = 0;
		try{
			String where = "key = '"+Constants.JSON_BATCH_SIZE+"'";
			String []fieldList={Constants.CUSTOMER_PARAMETER_FIELDS[2]};
			Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_CUSTOME_PARAMETER,fieldList,where);

			if(cursor != null)
			{
				if(cursor.moveToFirst()){
					do{
						batchSize = Integer.parseInt(cursor.getString(0));
					}while(cursor.moveToNext());
				}
			}

			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
			}
			dbHelper.closeDB();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return batchSize;
	}

	public BatchSubmitVO getLastBatchSubmit()
	{
		BatchSubmitVO res = new BatchSubmitVO();
		try
		{
			String[] fieldList = {"MAX("+Constants.DB_BATCH_SUBMIT_ID+")",Constants.DB_TOT_RECORDS,
					Constants.DB_SUCCESS_RECORDS,Constants.DB_UNSUCCESS_RECORDS,Constants.DB_CREATEDBY,Constants.DB_CREATEDON};
			String where = null;
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_MST_BATCH_SUBMIT, fieldList, where);
			if(cursor.moveToFirst()){
				do{
					res.setBatchsubmitid(((Integer.parseInt(cursor.getString(0)))));
					res.setTotRecords(((Integer.parseInt(cursor.getString(1)))));
					res.setSuccessfulRecords(((Integer.parseInt(cursor.getString(2)))));
					res.setUnsuccessfulRecords(((Integer.parseInt(cursor.getString(3)))));
					res.setCreatedBy((Integer.parseInt(cursor.getString(4))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(5),Constants.SALT));
				}while(cursor.moveToNext());

			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":MaintainancService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return res;
	}
}