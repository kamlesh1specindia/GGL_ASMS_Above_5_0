package com.spec.asms.service;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.DBHelper;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.vo.LogVO;

public class LogMasterService {

	private DBHelper dbHelper;
	public LogMasterService(Context context){
		try{
			dbHelper = new DBHelper(context);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public long insertLog(ContentValues values){
		return dbHelper.insertToTable(Constants.TBL_MST_LOG, values);
	}

	public long updateLog(ContentValues values,String where){
		return dbHelper.updateToTable(Constants.TBL_MST_LOG, values, where,null);
	}

	public List<LogVO> getAllLogDetail(Context context,String searchDate,boolean isSelected){
		ArrayList<LogVO> logDetails = new ArrayList<LogVO>(0);
		
		Cursor cursor = dbHelper.cursorSelectAll(Constants.TBL_MST_LOG,Constants.LOG_FIELDS);
		try
		{
			if(cursor.moveToFirst()){
				do{
					String date = Utility.getConvertedDate(context,Utility.getConvertedLongToDateTime(context, cursor.getLong(0)));
					int size =cursor.getInt(4);
					if(isSelected){
						if(date.equals(searchDate) && size >= 0){
							LogVO logVO = new LogVO();
							logVO.setDatetime(cursor.getLong(0));
							logVO.setObjectstatus(cursor.getString(1));
							logVO.setSubmitstatus(cursor.getString(2));
							logVO.setResponsestatus(cursor.getString(3));
							logVO.setBatchsize(cursor.getInt(4));
							logVO.setTotalsubmitted(cursor.getInt(5));
							logVO.setResponsetime(cursor.getLong(7));
							logVO.setException(cursor.getString(8));
							logDetails.add(logVO);
						}
					}else{
						if(date.equals(searchDate)  && size != 0){
							LogVO logVO = new LogVO();
							logVO.setDatetime(cursor.getLong(0));
							logVO.setObjectstatus(cursor.getString(1));
							logVO.setSubmitstatus(cursor.getString(2));
							logVO.setResponsestatus(cursor.getString(3));
							logVO.setBatchsize(cursor.getInt(4));
							logVO.setTotalsubmitted(cursor.getInt(5));
							logVO.setResponsetime(cursor.getLong(7));
							logVO.setException(cursor.getString(8));
							logDetails.add(logVO);
						}
					}
					
					
				}while(cursor.moveToNext());
				
				}
		}catch(Exception e){
			e.printStackTrace();
			Log.d(Constants.DEBUG,":LogMasterService:"+ Utility.convertExceptionToString(e));
		}
		
		return logDetails;
	}

	
	public List<String> getLogDates(Context context){
		ArrayList<String> logDates = new ArrayList<String>(0);
		
		Cursor cursor = dbHelper.cursorSelectAll(Constants.TBL_MST_LOG,Constants.LOG_FIELDS);
		try
		{
			if(cursor.moveToFirst()){
				do{
					
					String date = Utility.getConvertedDate(context,Utility.getConvertedLongToDateTime(context, cursor.getLong(0)));
					if(!logDates.contains(date)){
						logDates.add(date);
					}
				}while(cursor.moveToNext());
				
				}
		}catch(Exception e){
			e.printStackTrace();
			Log.d(Constants.DEBUG,":LogMasterService:"+ Utility.convertExceptionToString(e));
		}finally{
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
				cursor = null;
			}
		}
		
		return logDates;
	}
	
	
	public long deleteLog(String tableName,String where){
		return dbHelper.delete(tableName, where, null);
	}
}
