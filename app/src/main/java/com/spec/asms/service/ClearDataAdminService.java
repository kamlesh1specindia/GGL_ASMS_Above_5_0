package com.spec.asms.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.dto.MaintainanceDTO;
import com.spec.asms.vo.CleanupVO;
import com.spec.asms.vo.UserMasterVO;

public class ClearDataAdminService extends AsyncTask<Void,Void,Boolean>{

	private List<MaintainanceDTO> listMaintainance;
	private Context context; 
	private ProgressDialog prDialog = null;
	private int UserID;
	public int days = 0;
	public String msg;
	private UserMasterService userService;
	private MaintainanceService maintainanceService;
	SimpleDateFormat simpleFormatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);

	public ClearDataAdminService(Context c) {
		context = c;
		UserID = Integer.parseInt(SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USER_ID, null));
		userService = new UserMasterService(c);
		maintainanceService = new MaintainanceService(c);

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		try {
			prDialog = new ProgressDialog(context);
			prDialog.setMessage(Constants.LABEL_PROGRESS_CLEARING);
			prDialog.setCancelable(false);
			prDialog.show();
			prDialog.setProgress(10);
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":ClearDataAdminService:"+ Utility.convertExceptionToString(ex));
		}
	}

	@Override
	protected Boolean doInBackground(Void... params) {

		boolean cleared = false;
		try{

			userService.deleteUser(Constants.TBL_DTL_ADMIN,null);
			userService.deleteUser(Constants.TBL_DTL_CLAMPING,null);
			userService.deleteUser(Constants.TBL_DTL_CLEANUP,null);
			
			maintainanceService.deleteMaintainance(Constants.TBL_DTL_MAINTAINANCE, null);
			maintainanceService.deleteMaintainance(Constants.TBL_DTL_DAMAGE_CAUSE, null);

			maintainanceService.deleteMaintainance(Constants.TBL_DTL_TESTING, null);
			maintainanceService.deleteMaintainance(Constants.TBL_DTL_LEAKAGE, null);
			maintainanceService.deleteMaintainance(Constants.TBL_DTL_GIFITTING, null);

			maintainanceService.deleteMaintainance(Constants.TBL_DTL_CLAMPING, null);
			maintainanceService.deleteMaintainance(Constants.TBL_DTL_PAINTINGORING, null);
			maintainanceService.deleteMaintainance(Constants.TBL_DTL_RCC, null);
			maintainanceService.deleteMaintainance(Constants.TBL_DTL_SURAKSHA_TUBE, null);
			maintainanceService.deleteMaintainance(Constants.TBL_DTL_MAKE_AND_GEYSER, null);
			maintainanceService.deleteMaintainance(Constants.TBL_DTL_OTHER_CHECKS, null);
			maintainanceService.deleteMaintainance(Constants.TBL_DTL_CONFORMANCE, null);
			maintainanceService.deleteMaintainance(Constants.TBL_DTL_CUSTOMER_FEEDBACK, null);
			
			userService.deleteUser(Constants.TBL_MST_USER,null);
			userService.deleteUser(Constants.TBL_MST_USER_LOCK,null);
			userService.deleteUser(Constants.TBL_MST_STATUS,null);
			userService.deleteUser(Constants.TBL_MST_CONFORMANCE,null);
			userService.deleteUser(Constants.TBL_MST_FAQ,null);
			userService.deleteUser(Constants.TBL_MST_VALIDATION_RULE,null);
			userService.deleteUser(Constants.TBL_MST_GEYSER_TYPE,null);
			userService.deleteUser(Constants.TBL_MST_MAKE,null);
			userService.deleteUser(Constants.TBL_MST_LEAKAGE,null);
			userService.deleteUser(Constants.TBL_MST_DAMAGES,null);
			userService.deleteUser(Constants.TBL_MST_CAUSES,null);
			userService.deleteUser(Constants.TBL_MST_CUSTOME_PARAMETER,null);
			userService.deleteUser(Constants.TBL_MST_CUSTOMER, null);
			userService.deleteUser(Constants.TBL_MST_GIFITTING, null);
			
			cleared = true;
				
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":ClearDataAdminService:"+ Utility.convertExceptionToString(e));
		}
		return cleared;
	}

	@Override
	protected void onPostExecute(Boolean result) {		
		super.onPostExecute(result);

		prDialog.dismiss();
		if(result)
		{
			//Utility.alertDialog(context, Constants.ALERT_TITLE_GENERAL_INFO,Constants.LABEL_ALERT_CLEAR_SUCCESS);
			AlertDialog.Builder builder = new AlertDialog.Builder(context);  
			builder.setCancelable(true);		
			builder.setTitle(Constants.ALERT_TITLE_GENERAL_INFO);  
			builder.setMessage(Constants.LABEL_ALERT_CLEAR_SUCCESS);
			builder.setPositiveButton(Constants.LABLE_OK,new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			AlertDialog alert = builder.create();  
			alert.show(); 
		}
		else{
			msg ="Cleaning Failed!"; 
			//Utility.alertDialog(context, Constants.ALERT_TITLE_GENERAL_INFO,msg);
			AlertDialog.Builder builder = new AlertDialog.Builder(context);  
			builder.setCancelable(true);		
			builder.setTitle(Constants.ALERT_TITLE_GENERAL_INFO);  
			builder.setMessage(msg);
			builder.setPositiveButton(Constants.LABLE_OK,new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			AlertDialog alert = builder.create();  
			alert.show(); 
		}
	}

}
