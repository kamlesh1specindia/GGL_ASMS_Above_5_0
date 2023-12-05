package com.spec.asms.service.async;


import java.io.PrintWriter;
import java.io.StringWriter;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Global;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.CommanService;
import com.spec.asms.service.LogMasterService;


public class AsyncAutoBatchCreation extends AsyncTask<Void,Void,JSONArray> {

	
	private OnResponseListenerAutoBatchCreation responseListener;
	private Context context; 
	private ProgressDialog prDialog = null;
	private String from;
	private boolean isSuccess = true;

	public AsyncAutoBatchCreation(Context context, OnResponseListenerAutoBatchCreation OnResponseListenerBatchCreation,String from) {
		this.context = context;
		this.from = from;
		if(!from.equals("Service"))
			this.prDialog = new ProgressDialog(context);
		this.responseListener = OnResponseListenerBatchCreation;
		Global.isCycleRunning = true;
	}
	
	
	@Override
	protected void onPreExecute() {			
		super.onPreExecute();
		
		try{
			if(!from.equals("Service")){
				prDialog = new ProgressDialog(context);
				prDialog.setMessage(Constants.LABEL_BLANK+Constants.LABEL_PROGRESS_SYNC);
				prDialog.setCancelable(false);
				prDialog.show();
				prDialog.setProgress(10); }
				insertStartingLog();
		}catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
			Global.isCycleRunning = false;
			Log.d(Constants.DEBUG,":AsyncAutoBacthCreation:"+ Utility.convertExceptionToString(e));
		}

	}
	
	@Override
	protected JSONArray doInBackground(Void... arg0) {
		JSONArray batchRequest = null;
		try {
			batchRequest = CommanService.batchSubmit(context);
//			batchRequest = CommanService.prepareBatchSubmit(context, arryMaintainance, startIndex, lastIndex);
//			Log.e("AutoSubmit Task", ": Auto Submit Start :#2" +batchRequest.toString());
			
		} catch (JSONException e) {
			Log.d(Constants.DEBUG,":AsyncAutoBacthCreation:"+ Utility.convertExceptionToString(e));
			e.printStackTrace();
			isSuccess = false;
			Global.isCycleRunning = false;
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String errorMessage = sw.toString(); 
			insertExceptionLog(errorMessage);
		}catch(Exception ex){
			Log.d(Constants.DEBUG,":AsyncAutoBacthCreation:"+ Utility.convertExceptionToString(ex));
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String errorMessage = sw.toString(); 
			insertExceptionLog(errorMessage);
		}
		
		return batchRequest;
	}
	
	@Override
	protected void onPostExecute(JSONArray batchRequest) {
		super.onPostExecute(batchRequest);
		try{
			if(!from.equals("Service"))
				prDialog.dismiss();
		}catch (Exception e) {
			Log.d(Constants.DEBUG,":AsyncAutoBacthCreation:"+ Utility.convertExceptionToString(e));
			e.printStackTrace();
			Global.isCycleRunning = false;
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String errorMessage = sw.toString(); 
			insertExceptionLog(errorMessage);
		}
		if(isSuccess){
			responseListener.onSuccess(batchRequest);
		}else{
			responseListener.onFailure("false");
		}
		
	}

	public interface OnResponseListenerAutoBatchCreation {
		public void onSuccess(JSONArray batchDetailRequest);
		public void onFailure(String message);
	}
	
	private void insertStartingLog(){
		LogMasterService logMasterService = new LogMasterService(context);
		Global.currentDateTime = System.currentTimeMillis();
		ContentValues values = new ContentValues();
		values.put(Constants.LOG_FIELDS[0], Global.currentDateTime);
		values.put(Constants.LOG_FIELDS[1],context.getResources().getString(R.string.status_object_being_created));
		logMasterService.insertLog(values);
	}
	
	private void insertExceptionLog(String msgException){
		if(msgException != null){
			LogMasterService logMasterService = new LogMasterService(context);
			ContentValues values = new ContentValues();
			values.put(Constants.LOG_FIELDS[8],msgException);
			String where = Constants.LOG_FIELDS[0] + "= " + Global.currentDateTime;
			logMasterService.updateLog(values,where);
		}
	}
}
