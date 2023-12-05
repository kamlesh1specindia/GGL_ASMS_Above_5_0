package com.spec.asms.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Global;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.SecurityManager;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.async.AsyncAutoBatchCreation;
import com.spec.asms.service.async.AsyncAutoBatchCreation.OnResponseListenerAutoBatchCreation;
import com.spec.asms.service.async.AsyncBatchSubmit;
import com.spec.asms.service.async.AsyncBatchSubmit.OnResponseListener;

public class BatchSubmitService extends Service{

	public static Timer timer;
	private Context ctx;
	private MaintainanceService maintainanceService;
	private JSONArray successMFormID;
	private int autoUpdateInterval = 0;
	public int totalRecords, sucessfulRecords, unsucessfulRecords;
	public int userID;

	public void onCreate()
	{
		super.onCreate();
		ctx = getApplicationContext();
		maintainanceService = new MaintainanceService(ctx);
		autoUpdateInterval = maintainanceService.getAutoSubmissionInterval();
		userID = Integer.parseInt(SharedPrefrenceUtil.getPrefrence(ctx, Constants.DB_USER_ID,"0"));
		ApplicationLog.writeToFile(ctx, Constants.APP_NAME,Constants.TAG_SERVICE);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(timer != null){
			timer.cancel();
			Log.e(Constants.TAG_SERVICE, "Timer Canceled!!!");
		}
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.e(Constants.TAG_SERVICE,"DateTime:-"+Utility.getcurrentTimeStamp()+"****---- from on Start Service......");
		autoUpdateInterval = maintainanceService.getAutoSubmissionInterval();

		Log.e(Constants.TAG_SERVICE,"DateTime:-"+Utility.getcurrentTimeStamp()+"***---AutoUpdatationInterval:-"+autoUpdateInterval*60000);


		if(userID > 0)
		{
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {

					ApplicationLog.writeToFile(ctx, Constants.APP_NAME, Constants.TAG_SERVICE);
					userID = Integer.parseInt(SharedPrefrenceUtil.getPrefrence(ctx, Constants.DB_USER_ID, "0"));
					Log.e(Constants.TAG_SERVICE, "DateTime:-" + Utility.getcurrentTimeStamp() + "Batch Submit Service start");
					Log.e(Constants.TAG_SERVICE, "DateTime:-" + Utility.getcurrentTimeStamp() + "Is Auto Submission : true");

					if (Utility.isNetAvailable(ctx)) {
						try {
							if (userID > 0 && !Global.isManualSubmitRunning && !Global.isCycleRunning) {
								Log.e(Constants.TAG_SERVICE, "Auto submit start #1 "+"UserID :#"+userID);
								printToast.sendEmptyMessage(0);
								AsyncAutoBatchCreation asyncAutoBatchCreation = new AsyncAutoBatchCreation(ctx, onResponseBatchCreation, "Service");
								asyncAutoBatchCreation.execute(null,null);
//								AsyncBatchCreation asyncBatchCreation = new AsyncBatchCreation(ctx, onResponseBatchCreation, "Service",0);
//								asyncBatchCreation.execute(null, null);
							}
						} catch (Exception e) {
							e.printStackTrace();
							Log.d(Constants.DEBUG,":BatchSubmitService:"+ Utility.convertExceptionToString(e));
							makeCycle(false);
						} finally {
//							makeCycle(false);
						}
					} else {
						Log.e(Constants.TAG_SERVICE, Constants.ERROR_INTERNET_CONNECTION);
						makeCycle(false);
					}
				}
			}, autoUpdateInterval * 60000, autoUpdateInterval * 60000); //For one Hour = 3600000 (3600000,autoUpdateInterval*60000);
		}
		super.onStart(intent, startId);
	}

	final Handler printToast = new Handler() {
		public void handleMessage(Message msg) {
			Toast.makeText(getBaseContext(),Constants.AUTO_SUBMIT_START,Toast.LENGTH_LONG).show();
		}
	};

	final Handler printBatchToast = new Handler() {
		public void handleMessage(Message msg) {
			Toast.makeText(getBaseContext(),Constants.BATCH_SUBMIT_START,Toast.LENGTH_LONG).show();
		}
	};

	final Handler printBatchResponseToast = new Handler() {
		public void handleMessage(Message msg) {
			Toast.makeText(getBaseContext(),Constants.BATCH_RESPONSE,Toast.LENGTH_LONG).show();
		}
	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	protected OnResponseListenerAutoBatchCreation onResponseBatchCreation = new OnResponseListenerAutoBatchCreation() {

		public void onSuccess(JSONArray batchDetailRequest) {

			JSONArray batchRequest = batchDetailRequest;
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(Constants.JSON_LOGIN_USERNAME, SecurityManager.encrypt(SharedPrefrenceUtil.getPrefrence(ctx, Constants.DB_USERNAME, Constants.LABEL_BLANK),Constants.SALT));
			param.put(Constants.JSON_LOGIN_PASSWORD, SecurityManager.encrypt(SharedPrefrenceUtil.getPrefrence(ctx, Constants.DB_PASSWORD, Constants.LABEL_BLANK),Constants.SALT));
			param.put(Constants.JSON_LOGIN_DEVICEID, SecurityManager.encrypt(Constants.LABLE_DEVICE_ID,Constants.SALT));//Utility.getDeviceId(getActivity()));
			param.put(Constants.JSON_MAINTENANCE_FORM_DATA,SecurityManager.encrypt(batchRequest.toString(),Constants.SALT));

			LogMasterService logMasterService = new LogMasterService(ctx);
			ContentValues contentValues = new ContentValues();
			totalRecords = batchRequest.length();
			contentValues.put(Constants.LOG_FIELDS[4], totalRecords);
			contentValues.put(Constants.LOG_FIELDS[1], getResources().getString(R.string.status_object_being_created));
			String where = Constants.LOG_FIELDS[0] + "=" + Global.currentDateTime;
			logMasterService.updateLog(contentValues, where);

			if(Utility.isNetAvailable(ctx)){
				try{

					Log.e(Constants.TAG_SERVICE, "Auto submit start #2 "+"Length :#"+batchRequest.length());
					if(batchRequest.length() > 0 && !Global.isManualSubmitRunning && Global.isCycleRunning)
					{

						totalRecords = batchRequest.length();
						printBatchToast.sendEmptyMessage(0);
						AsyncBatchSubmit asyncBatchSubmit= new AsyncBatchSubmit(ctx,onResponseListener,"Service");
						asyncBatchSubmit.execute(param);
					}else{
						makeCycle(false);
						Log.e(Constants.TAG_SERVICE,"DateTime:-"+Utility.getcurrentTimeStamp()+"Is Auto Submission : false");
						Log.e(Constants.TAG_SERVICE,"DateTime:-"+Utility.getcurrentTimeStamp()+Constants.NO_BATCH_SUBMIT);
						printBatchResponseToast.sendEmptyMessage(0);
					}
				}catch (Exception e) {
					Log.e(Constants.TAG_SERVICE,"DateTime:-"+Utility.getcurrentTimeStamp()+"Error:-"+e.getMessage());
					Log.d(Constants.DEBUG,":BatchSubmitService:"+ Utility.convertExceptionToString(e));
					makeCycle(false);
				}finally{
					//makeCycle(false);
				}
			}else{
				Log.e(Constants.TAG_SERVICE, Constants.ERROR_INTERNET_CONNECTION);
				makeCycle(false);
			}
		}
		public void onFailure(String message) {
			makeCycle(false);
		}
	};

	protected OnResponseListener onResponseListener = new OnResponseListener() {

		public void onSuccess(JSONObject response) {
			try{

				Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+"Response******"+response);
				String rsp = null;

				Log.e(Constants.TAG_SERVICE, "Auto submit start #3 ");
				if(response.has(Constants.JSON_FAILED)){
					rsp = response.getString(Constants.JSON_FAILED);
					Log.e(Constants.TAG_SERVICE,"DateTime:-"+Utility.getcurrentTimeStamp()+"AsyncConfirmationSyncData  :: JSON Responce :::: Authentication :::"+response.getString(Constants.METHOD_AUTHENTICATE));
				}

				if(rsp != null && rsp.equals(Constants.JSON_FAILED)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.JSON_FAILED);
					Utility.alertDialog(getApplicationContext(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_JSON_FAILED);
					logException();
				}else if(response.has(Constants.NETWORK_EXCEPTION_METHOD)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.NETWORK_EXCEPTION_METHOD);
					logException();
					Utility.alertDialog(getApplicationContext(), Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
				}else if(response.has(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.CONNECTION_REFUSED_EXCEPTION_METHOD);
					logException();
					Utility.alertDialog(getApplicationContext(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(response.has(Constants.SOCKET_EXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.SOCKET_EXCEPTION);
					logException();
					Utility.alertDialog(getApplicationContext(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(response.has(Constants.IOEXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.IOEXCEPTION);
					logException();
					Utility.alertDialog(getApplicationContext(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(response.has(Constants.XMLPULLPARSEREXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.XMLPULLPARSEREXCEPTION);
					logException();
					Utility.alertDialog(getApplicationContext(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(response.has(Constants.EXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.EXCEPTION);
					logException();
					Utility.alertDialog(getApplicationContext(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else{
					successMFormID = response.getJSONArray("SuccessMFormID");
					if(successMFormID != null){
						Log.e("JSON","DateTime:-"+Utility.getcurrentTimeStamp()+ "::::::::: successMFormID ::: "+successMFormID);
						Log.e("JSON", "DateTime:-"+Utility.getcurrentTimeStamp()+"::::::::: successMFormID.length ::: "+successMFormID.length());
						Log.e("JSON", "DateTime:-"+Utility.getcurrentTimeStamp()+"::::::::: successMFormID.toString ::: "+successMFormID.toString());
						sucessfulRecords = successMFormID.length();
						unsucessfulRecords = totalRecords - sucessfulRecords;


						LogMasterService logMasterService = new LogMasterService(ctx);
						ContentValues contentValues = new ContentValues();
						contentValues.put(Constants.LOG_FIELDS[5], sucessfulRecords);
						contentValues.put(Constants.LOG_FIELDS[2], getResources().getString(R.string.status_submit_response));
						String where = Constants.LOG_FIELDS[0] + " = " + Global.currentDateTime;
						logMasterService.updateLog(contentValues, where);


//						Toast.makeText(ctx, "Success Full Records :"+ sucessfulRecords,Toast.LENGTH_LONG).show();
//						Toast.makeText(ctx, "Unuccess Full Records :"+ sucessfulRecords,Toast.LENGTH_LONG).show();

						if((response.getString(Constants.METHOD_AUTHENTICATE).equalsIgnoreCase("True"))&& successMFormID.length()>0){

							int maintainanceId = 0;
							int testingID = 0;
							if(maintainanceService != null){
								for(int i=0;i<successMFormID.length();i++){

									maintainanceId =  maintainanceService.getMaintainanceId(successMFormID.getInt(i));

									if(!SecurityManager.decrypt(maintainanceService.getMaintainanceStatus(successMFormID.getInt(i)), Constants.SALT).equalsIgnoreCase("HCL1")) {
										System.out.println("MaintainanceOrderId :::::::::::" + successMFormID.getInt(i));
										System.out.println("MaintainanceId ::::::::::" + maintainanceId);

										String deleteCust = "maintainanceorderid = " + successMFormID.getInt(i);
										String deleteWhere = "maintainanceid =" + maintainanceId;
										//long resutl = maintainanceService.deleteMaintainance(Constants.TBL_DTL_CLAMPING,deleteWhere);
										testingID = maintainanceService.getTestingId(deleteWhere);
										String delete = "testingid =" + testingID;
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_MAINTAINANCE, deleteWhere);

										maintainanceService.deleteMaintainance(Constants.TBL_DTL_TESTING, deleteWhere);
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_LEAKAGE, delete);
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_GIFITTING, delete);

										maintainanceService.deleteMaintainance(Constants.TBL_DTL_CLAMPING, deleteWhere);
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_PAINTINGORING, deleteWhere);
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_RCC, deleteWhere);
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_SURAKSHA_TUBE, deleteWhere);
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_OTHER_SURAKSHA_TUBE, deleteWhere);
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_MAKE_AND_GEYSER, deleteWhere);
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_OTHER_CHECKS, deleteWhere);
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_CONFORMANCE, deleteWhere);
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_CUSTOMER_FEEDBACK, deleteWhere);
										maintainanceService.deleteMaintainance(Constants.TBL_MST_CUSTOMER, deleteCust);
									} else {
										String deleteWhere = "maintainanceid = " + maintainanceId;
										ContentValues cv = new ContentValues();
										cv.put(Constants.MAINTAINANCE_FIELDS[6], 1);
										maintainanceService.updateMaintainance(Constants.TBL_DTL_MAINTAINANCE, cv, deleteWhere);
									}


//									String wheres = "maintainanceorderid = "+successMFormID.getInt(i);
//									ContentValues cv=new ContentValues();
//									cv.put(Constants.MAINTAINANCE_FIELDS[6],"1");
//									cv.put(Constants.MAINTAINANCE_FIELDS[9],SharedPrefrenceUtil.getPrefrence(ctx, Constants.DB_USER_ID, Constants.LABEL_BLANK));
//									cv.put(Constants.MAINTAINANCE_FIELDS[10], SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
//
//									if(maintainanceService.updateMaintainance(Constants.TBL_DTL_MAINTAINANCE,cv, wheres) != -1)
//									{
//										Log.e(Constants.TAG_SERVICE,"DateTime:-"+Utility.getcurrentTimeStamp()+"Service  ::::::::: Upadte issync = 1 for successMFormID ::: "+successMFormID.getInt(i));
//
//										ContentValues cvCust=new ContentValues();
//										cvCust.put(Constants.CUSTOMER_MASTER_FIELDS[5],"1");
//										cvCust.put(Constants.CUSTOMER_MASTER_FIELDS[8],SharedPrefrenceUtil.getPrefrence(ctx, Constants.DB_USER_ID, Constants.LABEL_BLANK));
//										cvCust.put(Constants.CUSTOMER_MASTER_FIELDS[9],SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
//
//										if(maintainanceService.updateMaintainance(Constants.TBL_MST_CUSTOMER,cvCust, wheres) != -1)
//										{
//											Log.e(Constants.TAG_SERVICE,"DateTime:-"+Utility.getcurrentTimeStamp()+"Service Cust Sync ::::::::: Upadte issync = 1 for Customer table successMFormID ::: "+successMFormID.getInt(i));
//										}
//									}
								}
							}

							Log.e(Constants.TAG_SERVICE,"DateTime:-"+Utility.getcurrentTimeStamp()+Constants.BATCH_SUBMIT_SUCESSFUL);
						}else{
//							makeCycle(false);
							Log.e(Constants.TAG_SERVICE,"DateTime:-"+Utility.getcurrentTimeStamp()+"Is Auto Submission : true");
							Log.e(Constants.TAG_SERVICE,"DateTime:-"+Utility.getcurrentTimeStamp()+Constants.BATCH_SUBMIT_UNSUCESSFUL);
						}
						ContentValues cvSub=new ContentValues();
						cvSub.put(Constants.DB_TOT_RECORDS,totalRecords);
						cvSub.put(Constants.DB_SUCCESS_RECORDS,sucessfulRecords);
						cvSub.put(Constants.DB_UNSUCCESS_RECORDS,unsucessfulRecords);
						cvSub.put(Constants.DB_CREATEDBY,SharedPrefrenceUtil.getPrefrence(ctx, Constants.DB_USER_ID, Constants.LABEL_BLANK));
						cvSub.put(Constants.DB_CREATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));

						if(maintainanceService.insertMaintainance(Constants.TBL_MST_BATCH_SUBMIT, cvSub) != -1)
							Log.e(Constants.TAG_SERVICE,"DateTime:-"+Utility.getcurrentTimeStamp()+"Data Successfully submitted in batch");
						makeCycle(false);
						printBatchResponseToast.sendEmptyMessage(0);
					}

				}
			}catch (Exception e) {
				Log.e(Constants.TAG_SERVICE,"DateTime:-"+Utility.getcurrentTimeStamp()+Constants.ERROR_IN_SUBMISSION);
				Log.d(Constants.DEBUG,":BatchSubmitService:"+ Utility.convertExceptionToString(e));
				makeCycle(false);
				insertExceptionLog(e.getMessage());
			}
		}

		public void onFailure(String message) {
			try{
				makeCycle(false);
				Utility.alertDialog(ctx, Constants.ALERT_TITLE_GENERAL_INFO,Constants.ERROR_IN_SUBMISSION);
				insertExceptionLog(message);
			}catch(Exception e){
				e.printStackTrace();
			}

		}
	};

	public void makeCycle(boolean var)
	{
		Global.isCycleRunning = var;
//		MainTabActivity.isCycleRunning = var;
//		SynchronizeFragment.isCycleRunning = var;
//		ViewAssignmentActivity.isCycleRunning = var;
//		MaintanenceForm.isCycleRunning = var;
	}

	private void insertExceptionLog(String msgException){
		if(msgException != null){
			LogMasterService logMasterService = new LogMasterService(ctx);
			if(logMasterService != null){
				ContentValues values = new ContentValues();
				values.put(Constants.LOG_FIELDS[8],msgException);
				String where = Constants.LOG_FIELDS[0] + "= " + Global.currentDateTime;
				logMasterService.updateLog(values,where);
			}
		}
	}

	private void logException(){
		makeCycle(false);
		LogMasterService logMasterService = new LogMasterService(ctx);
		ContentValues contentValues = new ContentValues();
		contentValues.put(Constants.LOG_FIELDS[5], 0);
		contentValues.put(Constants.LOG_FIELDS[2], getResources().getString(R.string.status_submit_failed));
		String where = Constants.LOG_FIELDS[0] + " = " + Global.currentDateTime;
		logMasterService.updateLog(contentValues, where);
	}
}