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
import com.spec.asms.vo.FAQMasterVO;

public class FAQMasterService {

	private DBHelper dbHelper;
	public FAQMasterService(Context context)
	{
		try{
			dbHelper = new DBHelper(context);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public long insertFAQ(String tableName,ContentValues values){
		return dbHelper.insertToTable(tableName, values);
	}

	public long deleteFAQ(String where){
		return dbHelper.delete(Constants.TBL_MST_FAQ,where,null);
	}

	public List<FAQMasterVO> getAllFAQ(String tableName,String[] fieldList){
		List<FAQMasterVO> faqList = new ArrayList<FAQMasterVO>();

		Cursor cursor = dbHelper.cursorSelectAll("mstfaq", new String[]{"faqid","question","answer"});
		if(cursor.moveToFirst()){
			do{
				FAQMasterVO faq = new FAQMasterVO();
				faq.setFaqID(((Integer.parseInt(cursor.getString(0)))));
				faq.setQuestion((cursor.getString(1)));
				faq.setAnswer(cursor.getString(2));

				faqList.add(faq);
			}while(cursor.moveToNext());

		}
		if(cursor !=null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		return faqList;
	}

	public FAQMasterVO getFAQInfo(String tableName,String[] fieldList,String where){

		FAQMasterVO faqVO = new FAQMasterVO();
		Cursor cursor = dbHelper.cursorSelectByWhere(tableName,fieldList,where);
		try{
			if(cursor.moveToFirst()){
				do{
					faqVO.setFaqID(((Integer.parseInt(cursor.getString(0)))));
					faqVO.setQuestion(SecurityManager.decrypt(cursor.getString(1),Constants.SALT));
					faqVO.setAnswer(SecurityManager.decrypt(cursor.getString(2),Constants.SALT));
				}while(cursor.moveToNext());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":FAQMasterService:"+ Utility.convertExceptionToString(ex));
		}
		if(cursor != null && !cursor.isClosed()){
			cursor.close();
			dbHelper.closeDB();
		}
		return faqVO;
	}
}