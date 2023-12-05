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
import com.spec.asms.vo.RoleMasterVO;


public class RoleMasterService {

	private DBHelper dbHelper;
	public RoleMasterService(Context context){
		try{
			dbHelper = new DBHelper(context);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public long insertRole(String tableName,ContentValues values){
		return dbHelper.insertToTable(tableName, values);
	}

	public long updateRoleByID(String tableName,ContentValues values,String where){
		return dbHelper.updateToTable(tableName, values, where,null);
	}

	public long deleteRole(String where){
		return dbHelper.delete(Constants.TBL_MST_ROLE,where,null);
	}

	public List<RoleMasterVO> getAllRoles(String tableName,String[] fieldList){
		List<RoleMasterVO> roleList=new ArrayList<RoleMasterVO>();

		Cursor cursor=dbHelper.cursorSelectAll(tableName, fieldList);
		if(cursor.moveToFirst()){
			do{
				RoleMasterVO res=new RoleMasterVO();
				res.setRoleID(((Integer.parseInt(cursor.getString(0)))));
				res.setRoleName((cursor.getString(1)));
				res.setCreatedBy(((Integer.parseInt(cursor.getString(2)))));
				res.setCreatedOn(cursor.getString(3));
				res.setUpdatedBy(((Integer.parseInt(cursor.getString(4)))));
				res.setUpdatedOn(cursor.getString(5));

				roleList.add(res);
			}while(cursor.moveToNext());
		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		return roleList;
	}
}