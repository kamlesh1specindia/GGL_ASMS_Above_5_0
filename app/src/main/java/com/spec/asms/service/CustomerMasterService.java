package com.spec.asms.service;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.DBHelper;
import com.spec.asms.common.utils.SecurityManager;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.vo.CustomerMasterVO;

/**
 * CustomerMaster Service is used to do operation of CustomerMaster table.
 * 
 * @author jenisha
 *
 */
public class CustomerMasterService {

	private DBHelper dbHelper;
	public CustomerMasterService(Context context){
		try{
			dbHelper = new DBHelper(context);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public long insertCustomer(String tableName,ContentValues values){
		return dbHelper.insertToTable(tableName, values);
	}

	public long updateCustomerByID(String tableName,ContentValues values,String where){
		return dbHelper.updateToTable(tableName, values, where,null);
	}

	public long deleteCustomer(String where){
		return dbHelper.delete(Constants.TBL_MST_CUSTOMER,where,null);
	}

	public List<CustomerMasterVO> getAllCustomers(String tableName,String[] fieldList) throws Exception{
		List<CustomerMasterVO> custList = new ArrayList<CustomerMasterVO>();

		Cursor cursor=dbHelper.cursorSelectAll(tableName, fieldList);
		if(cursor.moveToFirst()){
			do{
				CustomerMasterVO res = new CustomerMasterVO();
				res.setCustomerID(cursor.getString(0));
				res.setCustomername(SecurityManager.decrypt(cursor.getString(1),Constants.SALT));
				//System.out.println("Customer Name"+SecurityManager.decrypt(cursor.getString(1),Constants.SALT));
				res.setCustomeradd(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));
				res.setStatusId(((Integer.parseInt(cursor.getString(3)))));
				res.setWalksequence(SecurityManager.decrypt(cursor.getString(4),Constants.SALT));
				res.setIssync(((Integer.parseInt(cursor.getString(5)))));
				res.setCreatedBy(((Integer.parseInt(cursor.getString(6)))));
				res.setCreatedOn(SecurityManager.decrypt(cursor.getString(7),Constants.SALT));
				res.setUpdatedBy(((Integer.parseInt(cursor.getString(8)))));
				res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(9),Constants.SALT));
				res.setMaintainanceOrderID(cursor.getString(10));
				res.setOrderID(cursor.getString(11));
				res.setIsDeleted(((Integer.parseInt(cursor.getString(12)))));
				res.setMrunit(cursor.getString(13));
				res.setSocietyName(cursor.getString(14));
				res.setPhoneNumber(cursor.getString(15));
				res.setCustomerStatus(cursor.getString(16));
				res.setExpiryDate(cursor.getString(17));
				if(cursor.getString(18) != null ){
					res.setMeterNumber(cursor.getString(18));
				}	
				
				if(!cursor.isNull(15)){
					res.setPhoneNumber(cursor.getString(15));
					System.out.println("::::::::GetAllCustomers::::::::");
					System.out.println("Get Contact Number : "+ cursor.getString(15));
				}
				custList.add(res);
			}while(cursor.moveToNext());

		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
		}
		dbHelper.closeDB();
		return custList;
	}

	public int getCustomerStatus(int id){
		int statusid = 0 ;
		String where = "customerid = "+id;
		Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_CUSTOMER,new String[]{"statusid"}, where);
		try
		{
			if(cursor.moveToFirst()){
				do{
					statusid = cursor.getInt(0);
					
				}while(cursor.moveToNext());
			}
		}catch(Exception ex){
			Log.d(Constants.DEBUG,":CustomerMasterService:"+ Utility.convertExceptionToString(ex));
		}

		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
		}
		dbHelper.closeDB();
		return statusid;
	}
		
	public CustomerMasterVO getCustomerInfo(String tableName,String[] fieldList,String where){

		CustomerMasterVO customerVO = new CustomerMasterVO();
		Cursor cursor=dbHelper.cursorSelectByWhere(tableName, fieldList, where);
		try
		{
			if(cursor.moveToFirst()){
				do{
					customerVO.setCustomerID(cursor.getString(0));
					customerVO.setCustomername(SecurityManager.decrypt(cursor.getString(1),Constants.SALT));
					customerVO.setCustomeradd(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));
					customerVO.setStatusId(((Integer.parseInt(cursor.getString(3)))));
					customerVO.setWalksequence(SecurityManager.decrypt(cursor.getString(4),Constants.SALT));
					customerVO.setCreatedBy(((Integer.parseInt(cursor.getString(5)))));
					customerVO.setIssync(((Integer.parseInt(cursor.getString(6)))));
					customerVO.setCreatedOn(SecurityManager.decrypt(cursor.getString(7),Constants.SALT));
					customerVO.setUpdatedBy(((Integer.parseInt(cursor.getString(8)))));
					customerVO.setUpdatedOn(SecurityManager.decrypt(cursor.getString(9),Constants.SALT));
					customerVO.setMaintainanceOrderID(SecurityManager.decrypt(cursor.getString(10),Constants.SALT));
					customerVO.setOrderID(SecurityManager.decrypt(cursor.getString(11),Constants.SALT));
					customerVO.setIsDeleted(((Integer.parseInt(cursor.getString(12)))));
					customerVO.setMrunit(cursor.getString(13));
					customerVO.setSocietyName(cursor.getString(14));
					customerVO.setPhoneNumber(cursor.getString(15));
					customerVO.setCustomerStatus(cursor.getString(16));
					customerVO.setExpiryDate(cursor.getString(17));
					if(cursor.getString(18) != null ){
						customerVO.setMeterNumber(cursor.getString(18));
					}	
					if(!cursor.isNull(15)){
						System.out.println("::::::::getCustomerInfo::::::::");
						System.out.println("Get Contact Number : "+ cursor.getString(15));
						customerVO.setPhoneNumber(cursor.getString(15));
					}
					
				}while(cursor.moveToNext());
			}
		}catch(Exception ex){
			Log.d(Constants.DEBUG,":CustomerMasterService:"+ Utility.convertExceptionToString(ex));
		}

		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
		}
		dbHelper.closeDB();
		return customerVO;
	}

	public List<CustomerMasterVO> getSelectedCustomers(String tableName,String[] fieldList,String where) throws Exception{
		List<CustomerMasterVO> custList = new ArrayList<CustomerMasterVO>();

		Cursor cursor=dbHelper.cursorSelectByWhere(tableName, fieldList,where);
		try
		{
			if(cursor.moveToFirst()){
				do{
					CustomerMasterVO res = new CustomerMasterVO();
					res.setCustomerID(cursor.getString(0));
					res.setCustomername(SecurityManager.decrypt(cursor.getString(1),Constants.SALT));
					res.setCustomeradd(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));
					res.setStatusId(((Integer.parseInt(cursor.getString(3)))));
					res.setWalksequence(SecurityManager.decrypt(cursor.getString(4),Constants.SALT));
					res.setIssync(((Integer.parseInt(cursor.getString(5)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(6)))));
					res.setCreatedOn(SecurityManager.decrypt(cursor.getString(7),Constants.SALT));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(8)))));
					res.setUpdatedOn(SecurityManager.decrypt(cursor.getString(9),Constants.SALT));
					res.setMaintainanceOrderID(cursor.getString(10));
					res.setOrderID(cursor.getString(11));
					res.setIsDeleted(((Integer.parseInt(cursor.getString(12)))));
					res.setMrunit(cursor.getString(13));
					res.setSocietyName(cursor.getString(14));
					res.setPhoneNumber(cursor.getString(15));
					res.setCustomerStatus(cursor.getString(16));
					res.setExpiryDate(SecurityManager.decrypt(cursor.getString(17),Constants.SALT));
					if(cursor.getString(18) != null ){
						res.setMeterNumber(cursor.getString(18));
					}	
					if(!cursor.isNull(15)){
						System.out.println("::::::::getSelectedCustomers::::::::");
						System.out.println("Get Contact Number : "+ cursor.getString(15));
						res.setPhoneNumber(cursor.getString(15));
					}
					
					custList.add(res);
				}while(cursor.moveToNext());

			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":CustomerMasterService:"+ Utility.convertExceptionToString(ex));
		}
		
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
		}
		dbHelper.closeDB();
		return custList;
	}

	public int getTotalCustomers(){
		int count=0;
		count = dbHelper.countTotalRows(Constants.TBL_MST_CUSTOMER,"");
		return count;
	}
	
	public String getCustNTeamNames(String whereParam){
		String name = Constants.LABEL_BLANK;
		try{
		String where = "key = '"+whereParam+"'";
		String []fieldList={Constants.CUSTOMER_PARAMETER_FIELDS[2]};
		Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_CUSTOME_PARAMETER,fieldList,where);
	
			if(cursor != null)
			{
				if(cursor.moveToFirst()){
					do{
						name = cursor.getString(0);
					}while(cursor.moveToNext());
				}
			}
		
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
		}
		dbHelper.closeDB();
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":CustomerMasterService:"+ Utility.convertExceptionToString(ex));
		}
		return name;
	}

	public List<String> getMRUnitList(){
		List<String> mrUnitList = null;
		try{
			String []fieldList={Constants.CUSTOMER_MASTER_FIELDS[13]};
			Cursor cursor=dbHelper.cursorAllSelectDistinct(Constants.TBL_MST_CUSTOMER, fieldList);
			if(cursor != null)
			{
				mrUnitList = new ArrayList<String>(0);
				mrUnitList.add("Select");
				if(cursor.moveToFirst()){
					do{
						mrUnitList.add(cursor.getString(0));
					}while(cursor.moveToNext());
				}
			}
			
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
			}
		    dbHelper.closeDB();
		
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":CustomerMasterService:"+ Utility.convertExceptionToString(ex));
		}
		return mrUnitList;
	}
	
	public List<String> getSocietyNameList(String mrunit){
		List<String> societyNameList = null;
		String where = Constants.CUSTOMER_MASTER_FIELDS[13] + "=" + "'"+mrunit+"'";
		try{
			String [] fieldList={Constants.CUSTOMER_MASTER_FIELDS[14]};
			Cursor cursor=dbHelper.cursorAllSelectDistinct(Constants.TBL_MST_CUSTOMER, fieldList,where);
			if(cursor != null)
			{
				societyNameList = new ArrayList<String>();
				if(cursor.moveToFirst()){
					do{
						societyNameList.add(cursor.getString(0));
					}while(cursor.moveToNext());
				}
			}
			
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
			}
		    dbHelper.closeDB();
		
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":CustomerMasterService:"+ Utility.convertExceptionToString(ex));
		}
		return societyNameList;
	}
}