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
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.vo.CauseVO;
import com.spec.asms.vo.CleanupVO;
import com.spec.asms.vo.DamageVO;
import com.spec.asms.vo.FAQMasterVO;
import com.spec.asms.vo.GeyserVO;
import com.spec.asms.vo.LeakageMasterVO;
import com.spec.asms.vo.MakeVO;
import com.spec.asms.vo.StatusVO;
import com.spec.asms.vo.UserLockVO;
import com.spec.asms.vo.UserMasterVO;
import com.spec.asms.vo.ValidationVO;

public class UserMasterService {

	private DBHelper dbHelper;
	public UserMasterService(Context context){
		try{
			dbHelper = new DBHelper(context);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public long insertUser(String tableName,ContentValues values){
		return dbHelper.insertToTable(tableName, values);
	}

	public long updateUserByID(String tableName,ContentValues values,String where){
		return dbHelper.updateToTable(tableName, values, where,null);
	}

	public long deleteUser(String tableName, String where){
		return dbHelper.delete(tableName,where,null);
	}

	public void deleteAllMaster(){
		dbHelper.delete(Constants.TBL_MST_USER,null,null);
		dbHelper.delete(Constants.TBL_MST_STATUS,null,null);
		dbHelper.delete(Constants.TBL_MST_CONFORMANCE,null,null);
		dbHelper.delete(Constants.TBL_MST_FAQ,null,null);
		dbHelper.delete(Constants.TBL_MST_VALIDATION_RULE,null,null);
		dbHelper.delete(Constants.TBL_MST_GEYSER_TYPE,null,null);
		dbHelper.delete(Constants.TBL_MST_MAKE,null,null);
		dbHelper.delete(Constants.TBL_MST_LEAKAGE,null,null);
		dbHelper.delete(Constants.TBL_MST_DAMAGES,null,null);
		dbHelper.delete(Constants.TBL_MST_CAUSES,null,null);
		dbHelper.delete(Constants.TBL_DTL_CLEANUP,null,null);
		dbHelper.delete(Constants.TBL_MST_CUSTOME_PARAMETER,null,null);		
	}
	public List<UserMasterVO> getUsers(String tableName){
		List<UserMasterVO> userList=new ArrayList<UserMasterVO>();
		String []fieldList={Constants.USER_MASTER_FIELDS[2]};
		Cursor cursor = dbHelper.cursorSelectAll(tableName, fieldList);
		if(cursor.moveToFirst()){
			do{
				UserMasterVO res=new UserMasterVO();
				res.setUserName(cursor.getString(0));
				userList.add(res);
			}while(cursor.moveToNext());
		}
		if(cursor != null && !cursor.isClosed()){
			cursor.close();
		}
		dbHelper.closeDB();
		return userList;
	}

	public List<UserMasterVO> getAllUsers(String tableName,String[] fieldList){
		List<UserMasterVO> userList=new ArrayList<UserMasterVO>();

		Cursor cursor = dbHelper.cursorSelectAll(tableName, fieldList);
		if(cursor.moveToFirst()){
			do{
				UserMasterVO res=new UserMasterVO();
				res.setUserID(((Integer.parseInt(cursor.getString(0)))));
				res.setRoleID(((Integer.parseInt(cursor.getString(1)))));
				res.setUserName(cursor.getString(2));
				res.setPassword(cursor.getString(3));
				res.setLastLogintime(cursor.getString(4));
				res.setIssync(((Integer.parseInt(cursor.getString(5)))));
				res.setCreatedBy(((Integer.parseInt(cursor.getString(6)))));
				res.setCreatedOn(cursor.getString(7));
				res.setUpdatedBy(((Integer.parseInt(cursor.getString(8)))));
				res.setUpdatedOn(cursor.getString(9));
				//res.setIslock(Integer.parseInt(cursor.getString(10)));
				userList.add(res);
			}while(cursor.moveToNext());
		}
		if(cursor != null && !cursor.isClosed()){
			cursor.close();
		}
		dbHelper.closeDB();
		return userList;
	}
	
	
	public List<UserLockVO> getLockUsers(String tableName,String username){
		List<UserLockVO> userList = new ArrayList<UserLockVO>();
		try{
			String[] fieldList = new String[]{Constants.USER_LOCK_FIELDS[4],Constants.USER_LOCK_FIELDS[5]};
			String where = "username = '"+username+"'";
			Cursor cursor = dbHelper.cursorSelectByWhere(tableName, fieldList,where);
			if(cursor.moveToFirst()){
				do{
					UserLockVO res = new UserLockVO();
					res.setPasswordUpdateDate(cursor.getString(0));
					res.setIsLock(cursor.getString(1));		
					userList.add(res);
				}while(cursor.moveToNext());
			}
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
			}
			dbHelper.closeDB();
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":UserMaseterService:"+ Utility.convertExceptionToString(e));
		}
		return userList;
	}
	
	
	public ArrayList<UserMasterVO> getLockedUsers()
	{
		ArrayList<UserMasterVO>userList=new ArrayList<UserMasterVO>();
		String where="islock = "+1;
		String[] fieldList = new String[]{Constants.USER_MASTER_FIELDS[0], Constants.USER_MASTER_FIELDS[2], Constants.USER_MASTER_FIELDS[10]};
		Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_USER, fieldList,where);
		if(cursor.moveToFirst()){
			do{
				UserMasterVO userVO=new UserMasterVO();
				userVO.setUserID(Integer.parseInt(cursor.getString(0)));
				userVO.setUserName(cursor.getString(1));
				userList.add(userVO);
			}while(cursor.moveToNext());
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		dbHelper.closeDB();
		return userList;
	}

	public UserLockVO getLockedStatus(String username)
	{
		UserLockVO userLockVO = new UserLockVO();
		try{
			String where = "username = '"+username+"'";

			String[] fieldList = new String[]{Constants.USER_LOCK_FIELDS[4],Constants.USER_LOCK_FIELDS[5]};
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_MST_USER_LOCK, fieldList,where);
			if(cursor.moveToFirst()){
				do{	
//					userLockVO.setPasswordUpdateDate(SecurityManager.decrypt(cursor.getString(0),Constants.SALT));
//					userLockVO.setIsLock(SecurityManager.decrypt(cursor.getString(1),Constants.SALT));
					userLockVO.setPasswordUpdateDate(cursor.getString(0));
					userLockVO.setIsLock(cursor.getString(1));	
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
			dbHelper.closeDB();
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":UserMaseterService:"+ Utility.convertExceptionToString(e));
		}
		return userLockVO;
	}

	public List<StatusVO> getStatus(){
		List<StatusVO> statusList = new ArrayList<StatusVO>();
		String[] fieldList = {Constants.DB_STATUS_ID,Constants.DB_STATUS_CODE,Constants.DB_STATUS};
		Cursor cursor = dbHelper.cursorSelectAll(Constants.TBL_MST_STATUS, fieldList);

		if(cursor.moveToFirst()){
			do{
				StatusVO res = new StatusVO();
				res.setId(((Integer.parseInt(cursor.getString(0)))));
				res.setStatusCode(cursor.getString(1));
				res.setStatusName(cursor.getString(2));
				statusList.add(res);
			}while(cursor.moveToNext());
		}
		if(cursor != null && !cursor.isClosed()){
			cursor.close();
		}
		dbHelper.closeDB();
		return statusList;
	}

	public List<FAQMasterVO> getFAQ(String[] fieldList){
		List<FAQMasterVO> faqList = new ArrayList<FAQMasterVO>();

		Cursor cursor = dbHelper.cursorSelectAll(Constants.TBL_MST_FAQ, fieldList);
		if(cursor.moveToFirst()){
			do{
				FAQMasterVO res = new FAQMasterVO();
				res.setFaqID((Integer.parseInt(cursor.getString(0))));
				res.setQuestion((cursor.getString(1)));
				res.setAnswer(cursor.getString(2));
				faqList.add(res);
			}while(cursor.moveToNext());
		}
		if(cursor != null && !cursor.isClosed()){
			cursor.close();
		}
		dbHelper.closeDB();
		return faqList;
	}


	public List<MakeVO> getMake(){
		List<MakeVO> makeList = new ArrayList<MakeVO>();
		String[] fieldList = new String[]{Constants.DB_MAKE_ID,Constants.DB_MAKE_TEXT};
		Cursor cursor = dbHelper.cursorSelectAll(Constants.TBL_MST_MAKE, fieldList);
		if(cursor.moveToFirst()){
			do{
				MakeVO res = new MakeVO();
				res.setId(((Integer.parseInt(cursor.getString(0)))));
				res.setText(cursor.getString(1));
				makeList.add(res);
			}while(cursor.moveToNext());
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
		}
		dbHelper.closeDB();
		return makeList;
	}

	public List<GeyserVO> getGeysers(){
		List<GeyserVO> geyserList = new ArrayList<GeyserVO>();
		String[] fieldList = new String[]{"geysertypeid","text"};
		Cursor cursor = dbHelper.cursorSelectAll("mstgeysertype", fieldList);
		if(cursor.moveToFirst()){
			do{
				GeyserVO res = new GeyserVO();
				res.setId(((Integer.parseInt(cursor.getString(0)))));
				res.setText(cursor.getString(1));
				geyserList.add(res);
			}while(cursor.moveToNext());
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		dbHelper.closeDB();
		return geyserList;
	}

	public List<ValidationVO> getValidation(String formname){

		List<ValidationVO> validationList = new ArrayList<ValidationVO>();

		String where="formname = '"+formname+"'";
		String[] fieldList = new String[]{ Constants.DB_VALIDATION_RULE_ID,Constants.DB_FORMNAME,Constants.DB_FIELDNAME,Constants.DB_MINVALUE,Constants.DB_MAXVALUE};
		Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_VALIDATION_RULE, fieldList,where);
		if(cursor.moveToFirst()){
			do{
				ValidationVO res=new ValidationVO();
				res.setId(((Integer.parseInt(cursor.getString(0)))));
				res.setFormName(cursor.getString(1));
				res.setFieldName(cursor.getString(2));
				res.setMinValue(cursor.getString(3));
				res.setMaxValue(cursor.getString(4));
				validationList.add(res);
			}while(cursor.moveToNext());
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		dbHelper.closeDB();
		return validationList;
	}

	public ValidationVO getValidationMinMax(String formname,String fieldname){

		ValidationVO res = null;

		String where = "formname = '"+formname+"' and fieldname = '"+fieldname+"'";
		String[] fieldList = new String[]{ Constants.DB_VALIDATION_RULE_ID,Constants.DB_FORMNAME,Constants.DB_FIELDNAME,Constants.DB_MINVALUE,Constants.DB_MAXVALUE};
		Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_MST_VALIDATION_RULE, fieldList,where);
		System.out.println(" CURSOR SIZE -- "+cursor.getCount());
		if(cursor.moveToFirst()){
			do{
				res = new ValidationVO();
				res.setId(((Integer.parseInt(cursor.getString(0)))));
				res.setFormName(cursor.getString(1));
				res.setFieldName(cursor.getString(2));
				res.setMinValue(cursor.getString(3));
				res.setMaxValue(cursor.getString(4));

			}while(cursor.moveToNext());
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		dbHelper.closeDB();
		return res;
	}

	public List<CleanupVO> getCleanup(String[] fieldList){
		List<CleanupVO> cleanupList=new ArrayList<CleanupVO>();

		Cursor cursor=dbHelper.cursorSelectAll(Constants.TBL_DTL_CLEANUP, fieldList);
		if(cursor.moveToFirst())
		{
			do
			{
				CleanupVO res=new CleanupVO();
				res.setId(((Integer.parseInt(cursor.getString(0)))));
				res.setLastCleanupDate(Long.valueOf(cursor.getString(1)));
				res.setUserId(Integer.parseInt(cursor.getString(2)));
				res.setCleanUpPeriod(cursor.getString(3));
				cleanupList.add(res);
			}
			while(cursor.moveToNext());
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		dbHelper.closeDB();
		return cleanupList;
	}

	public CleanupVO getCleanupPeriod(){
		CleanupVO res = new CleanupVO();
		try{
			String[] fieldList = {Constants.DB_CLEANUP_ID,Constants.DB_LAST_CLEANUP_DATE,Constants.DB_CLEANUP_PEROID,Constants.DB_USER_ID};
			Cursor cursor = dbHelper.cursorSelectAll(Constants.TBL_DTL_CLEANUP, fieldList);
			if(cursor.moveToFirst())
			{
				do
				{
					res.setId(((Integer.parseInt(cursor.getString(0)))));
					res.setLastCleanupDate(Long.valueOf(cursor.getString(1)));
					res.setCleanUpPeriod(cursor.getString(2));
					res.setUserId(Integer.parseInt(cursor.getString(3)));
				}
				while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":UserMaseterService:"+ Utility.convertExceptionToString(e));
		}
		dbHelper.closeDB();
		return res;
	}

	public List<LeakageMasterVO> getLeakage(){
		List<LeakageMasterVO> lkgList=new ArrayList<LeakageMasterVO>();
		String[] fieldList = {Constants.DB_LEAKAGE_ID,Constants.DB_TEXT};		
		Cursor cursor=dbHelper.cursorSelectAll(Constants.TBL_MST_LEAKAGE, fieldList);

		if(cursor.moveToFirst()){
			do{
				LeakageMasterVO res=new LeakageMasterVO();
				res.setLeakageid(((Integer.parseInt(cursor.getString(0)))));
				res.setLeakagename((cursor.getString(1)));

				lkgList.add(res);
			}while(cursor.moveToNext());
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		dbHelper.closeDB();
		return lkgList;
	}

	public UserMasterVO getUserInfo(String tableName,String[] fieldList,String where){

		UserMasterVO res = new UserMasterVO();
		Cursor cursor=dbHelper.cursorSelectByWhere(tableName,fieldList,where);
		try{
			if(cursor.moveToFirst()){
				do{
					//					userVO.setUserID(((Integer.parseInt(cursor.getString(0)))));
					//					userVO.setRoleID(3);
					//					userVO.setUserName(cursor.getString(2));
					//					userVO.setPassword(cursor.getString(3));
					//					userVO.setLastLogintime(cursor.getString(4));
					//					userVO.setIssync(((Integer.parseInt(cursor.getString(5)))));
					//					userVO.setCreatedBy(((Integer.parseInt(cursor.getString(6)))));
					//					userVO.setCreatedOn(cursor.getString(7));
					//					userVO.setUpdatedBy(((Integer.parseInt(cursor.getString(8)))));
					//					userVO.setUpdatedOn(cursor.getString(9));
					//					userVO.setIslock(Integer.parseInt(cursor.getString(10)));


					res.setUserID(((Integer.parseInt(cursor.getString(0)))));
					if(cursor.getString(1)!=null){
						res.setRoleID(((Integer.parseInt(cursor.getString(1)))));
					}
					res.setUserName(cursor.getString(2));
					res.setPassword(cursor.getString(3));
					res.setLastLogintime(cursor.getString(4));
					res.setIssync(((Integer.parseInt(cursor.getString(5)))));
					res.setCreatedBy(((Integer.parseInt(cursor.getString(6)))));
					res.setCreatedOn(cursor.getString(7));
					res.setUpdatedBy(((Integer.parseInt(cursor.getString(8)))));
					res.setUpdatedOn(cursor.getString(9));
					if(cursor.getString(10)!=null){
						res.setIslock(Integer.parseInt(cursor.getString(10)));
					}else {
						res.setIslock(0);
					}

				}while(cursor.moveToNext());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":UserMaseterService:"+ Utility.convertExceptionToString(ex));
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		dbHelper.closeDB();
		return res;
	}

	public String getCurrentpasswordbyuserID(int ID){

		String password = Constants.LABEL_BLANK;
		String where = "userID = " +ID;
		String fieldList[] = {"password"};
		try{
			Cursor cursor = dbHelper.cursorSelectByWhere(Constants.TBL_MST_USER,fieldList, where);
			System.out.println(" CURSOR "+cursor.getCount());
			if(cursor.moveToFirst()){
				do{
					password = cursor.getString(0); //SecurityManager.decrypt(cursor.getString(0),Constants.SALT);
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			Log.d(Constants.DEBUG,":UserMaseterService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return password;
	}

	public String getIssync(String uname){

		String issync =  Constants.LABEL_BLANK;
		String where = "username = " +uname;
		String fieldList[] = {"issync"};

		try{
			Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_USER,fieldList, where);
			if(cursor.moveToFirst()){
				do{
					issync=SecurityManager.decrypt(cursor.getString(5),Constants.SALT);
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			Log.d(Constants.DEBUG,":UserMaseterService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return issync;
	}

	public int getStatusId(String code){
		int statusId = 0;
		String where = "statuscode = '"+code.trim()+"'";
		String fieldList[] = {"statusid"};

		try{
			Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_STATUS,fieldList,where);
			if(cursor.moveToFirst()){
				do{
					statusId=Integer.parseInt(cursor.getString(0));
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			Log.d(Constants.DEBUG,":UserMaseterService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return statusId;
	}
	
	public int getStatusIdFromName(String statusName){

		int statusId = 0;
		String where = "status = '"+statusName +"'";
		String fieldList[] = {"statusid"};

		try{
			Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_STATUS,fieldList,where);
			if(cursor.moveToFirst()){
				do{
					statusId=Integer.parseInt(cursor.getString(0));
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			Log.d(Constants.DEBUG,":UserMaseterService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return statusId;
	}
	
	//new function
	public String getStatus(String code){

		String status=Constants.LABEL_BLANK;
		String where = "statuscode = '"+code +"'";
		String fieldList[] = {"status"};

		try{
			Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_STATUS,fieldList,where);
			if(cursor.moveToFirst()){
				do{
					status=cursor.getString(0);
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			Log.d(Constants.DEBUG,":UserMaseterService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return status;
	}

	public String getStatusCode(int statusId){
		String statusCode =  Constants.LABEL_BLANK;
		String where = "statusid = " +statusId;
		String fieldList[] = {"statuscode"};

		try{
			Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_STATUS,fieldList,where);
			if(cursor.moveToFirst()){
				do{
					statusCode=cursor.getString(0);
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			Log.d(Constants.DEBUG,":UserMaseterService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return statusCode;
	}

	public String getMakeValue(int makeId){

		String makeValue = Constants.LABEL_BLANK;
		String where = "makeid = " +makeId;
		String fieldList[] = {Constants.DB_MAKE_TEXT};

		try{
			Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_MAKE,fieldList,where);
			if(cursor.moveToFirst()){
				do{
					makeValue=cursor.getString(0);
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			Log.d(Constants.DEBUG,":UserMaseterService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return makeValue;
	}

	public String getGeyserValue(int geyserId){

		String geyserValue = Constants.LABEL_BLANK;
		String where = "geysertypeid = " +geyserId;
		String fieldList[] = {Constants.DB_TEXT};

		try{
			Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_GEYSER_TYPE,fieldList,where);
			if(cursor.moveToFirst()){
				do{
					geyserValue=cursor.getString(0);
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			Log.d(Constants.DEBUG,":UserMaseterService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return geyserValue;
	}

	public String getCreatedDate(Context context){
		String createdDate =  Constants.LABEL_BLANK;
		String where = "userid = "+SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USER_ID, null);
		String fieldList[] = {"createdon"};
		try{
			Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_USER,fieldList, where);
			if(cursor.moveToFirst()){
				do{
					createdDate=cursor.getString(0);
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			Log.d(Constants.DEBUG,":UserMaseterService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return createdDate;
	}

	public String getUpdatedDate(Context context){
		String updatedDate =  Constants.LABEL_BLANK;
		String where = "userid = "+SharedPrefrenceUtil.getPrefrence(context,
				Constants.DB_USER_ID, null);
		String fieldList[] = {"updatedon"};

		try{
			Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_USER,fieldList, where);
			if(cursor.moveToFirst()){
				do{
					updatedDate=cursor.getString(0);
				}while(cursor.moveToNext());
			}
			if(cursor !=null && !cursor.isClosed()){
				cursor.close();
				dbHelper.closeDB();
			}
		}catch(Exception ex){
			Log.d(Constants.DEBUG,":UserMaseterService:"+ Utility.convertExceptionToString(ex));
		}
		dbHelper.closeDB();
		return updatedDate;
	}

	public String getEncVal(String tableName,String[] fieldList){

		String password = Constants.LABEL_BLANK;
		Cursor cursor=dbHelper.cursorSelectAll(tableName, fieldList);
		if(cursor.moveToFirst()){
			do{
				password=cursor.getString(0);
			}while(cursor.moveToNext());
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		dbHelper.closeDB();
		return password;
	}

	public int getGiFittingID(String gifittingText){

		int gifittingId = 0;

		String[] fieldList = {Constants.DB_MST_GIFITTING_ID};
		String where = "text = "+"'"+gifittingText+"'";

		Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_GIFITTING,fieldList,where);
		try{
			if(cursor.moveToFirst()){
				do{
					gifittingId = cursor.getInt(0);
				}while(cursor.moveToNext());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":UserMaseterService:"+ Utility.convertExceptionToString(ex));
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		dbHelper.closeDB();
		return gifittingId;
	}

	public List<DamageVO> getDamageInfo(int objectId){

		List<DamageVO> damageList=new ArrayList<DamageVO>();

		String[] fieldList = {Constants.DB_DAMAGE_ID,Constants.DB_OBJECT_ID,Constants.DB_DAMAGE_CODE,Constants.DB_FIELD_NAME_GUJ,Constants.DB_FIELDNAME};
		String where = "objectid = "+objectId;

		Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_DAMAGES,fieldList,where);
		try{
			if(cursor.moveToFirst()){
				do{
					DamageVO damageVO = new DamageVO();
					damageVO.setDamageId(cursor.getInt(0));
					damageVO.setObjectId(cursor.getInt(1));
					damageVO.setDamageCode(cursor.getString(2));
					damageVO.setFieldNameGuj(cursor.getString(3));
					damageVO.setFieldName(cursor.getString(4));
					damageList.add(damageVO);
				}while(cursor.moveToNext());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":UserMaseterService:"+ Utility.convertExceptionToString(ex));
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		dbHelper.closeDB();
		return damageList;
	}

	public int getDamageID(String damageName, int objectId){

		int damageId = 0;

		String[] fieldList = {Constants.DB_DAMAGE_ID};
		String where = "fieldname = "+"'"+damageName+"' and objectid = "+objectId;

		Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_DAMAGES,fieldList,where);
		try{
			if(cursor.moveToFirst()){
				do{
					damageId = cursor.getInt(0);
				}while(cursor.moveToNext());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":UserMaseterService:"+ Utility.convertExceptionToString(ex));
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		dbHelper.closeDB();
		return damageId;
	}

	public String getDamageName(int damageId){

		String damageNm = Constants.LABEL_BLANK;

		String[] fieldList = {Constants.DB_FIELDNAME};
		String where = "damageid = "+damageId;

		Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_DAMAGES,fieldList,where);
		try{
			if(cursor.moveToFirst()){
				do{
					damageNm = cursor.getString(0);
				}while(cursor.moveToNext());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":UserMaseterService:"+ Utility.convertExceptionToString(ex));
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		dbHelper.closeDB();
		return damageNm;
	}

	public int getCauseID(String causeName, int objectId){

		int causeId = 0;

		String[] fieldList = {Constants.DB_CAUSE_ID};
		String where = "fieldname = "+"'"+causeName+"' and objectid = "+objectId;

		Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_CAUSES,fieldList,where);
		try{
			if(cursor.moveToFirst()){
				do{
					causeId = cursor.getInt(0);
				}while(cursor.moveToNext());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":UserMaseterService:"+ Utility.convertExceptionToString(ex));
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		dbHelper.closeDB();
		return causeId;
	}

	public List<CauseVO> getCauseInfo(int objectId){

		List<CauseVO> causeList=new ArrayList<CauseVO>();

		String[] fieldList = {Constants.DB_CAUSE_ID,Constants.DB_OBJECT_ID,Constants.DB_CAUSE_CODE,Constants.DB_FIELDNAME,Constants.DB_FIELD_NAME_GUJ};
		String where = "objectid = "+objectId;

		Cursor cursor=dbHelper.cursorSelectByWhere(Constants.TBL_MST_CAUSES,fieldList,where);
		try{
			if(cursor.moveToFirst()){
				do{
					CauseVO causeVO = new CauseVO();
					causeVO.setCauseId(cursor.getInt(0));
					causeVO.setObjectId(cursor.getInt(1));
					causeVO.setCauseCode(cursor.getString(2));
					causeVO.setFieldName(cursor.getString(3));
					causeVO.setFieldNameGuj(cursor.getString(4));

					causeList.add(causeVO);
				}while(cursor.moveToNext());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":UserMaseterService:"+ Utility.convertExceptionToString(ex));
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		dbHelper.closeDB();
		return causeList;
	}

	public String getAdminURL(){

		String url = Constants.LABEL_BLANK;
		String[] fieldList = {Constants.DB_URL};
		Cursor cursor = dbHelper.cursorSelectAll(Constants.TBL_DTL_ADMIN,fieldList);
		if(cursor.moveToFirst()){
			do{
				url=cursor.getString(0);
			}while(cursor.moveToNext());
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		dbHelper.closeDB();
		return url;
	}


}