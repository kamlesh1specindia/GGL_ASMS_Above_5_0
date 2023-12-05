package com.spec.asms.service.async;


import java.util.List;

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
import com.spec.asms.dto.MaintainanceDTO;
import com.spec.asms.service.CommanService;
import com.spec.asms.service.LogMasterService;


public class AsyncBatchCreation extends AsyncTask<Void,Void,JSONArray> {

	
	private OnResponseListenerBatchCreation responseListener;
	private Context context; 
	private ProgressDialog prDialog = null;
	private String from;
	private int startIndex = 0;
	private int lastIndex = 0;
	private List<MaintainanceDTO> arryMaintainance;
	private LogMasterService logMasterService;
	
	public AsyncBatchCreation(Context context, OnResponseListenerBatchCreation OnResponseListenerBatchCreation,String from,List<MaintainanceDTO> arryMaintainance,int startIndex,int lastIndex) {
		this.context = context;
		this.from = from;
		if(!from.equals("Service"))
			this.prDialog = new ProgressDialog(context);
		this.responseListener = OnResponseListenerBatchCreation;
		this.startIndex = startIndex;
		this.lastIndex = lastIndex;
		this.arryMaintainance = arryMaintainance;
		Global.isManualSubmitRunning = true;
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
			Log.d(Constants.DEBUG,":AsyncBacthCreation:"+ Utility.convertExceptionToString(e));
			Global.isManualSubmitRunning = false;
			insertExceptionLog(e.getMessage());
		}

	}
	
	@Override
	protected JSONArray doInBackground(Void... arg0) {
		JSONArray batchRequest = null;
		try {
//			batchRequest = CommanService.batchSubmit(context,offset);
			batchRequest = CommanService.prepareBatchSubmit(context, arryMaintainance, startIndex, lastIndex);
//			Log.e("AutoSubmit Task", ": Auto Submit Start :#2" +batchRequest.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncBacthCreation:"+ Utility.convertExceptionToString(e));
			Global.isManualSubmitRunning = false;
			insertExceptionLog(e.getMessage());
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
			e.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncBacthCreation:"+ Utility.convertExceptionToString(e));
			Global.isManualSubmitRunning = false;
			insertExceptionLog(e.getMessage());
		}
		responseListener.onSuccess(batchRequest);
	}

	public interface OnResponseListenerBatchCreation {
		public void onSuccess(JSONArray batchDetailRequest);
		public void onFailure(String message);
	}
	
	private void insertStartingLog(){
		logMasterService = new LogMasterService(context);
		Global.currentDateTime = System.currentTimeMillis();
		ContentValues values = new ContentValues();
		values.put(Constants.LOG_FIELDS[0], Global.currentDateTime);
		values.put(Constants.LOG_FIELDS[1],context.getResources().getString(R.string.status_object_being_created));
		logMasterService.insertLog(values);
	}
	
	private void insertExceptionLog(String msgException){
		if(msgException != null){
			logMasterService = new LogMasterService(context);
			ContentValues values = new ContentValues();
			values.put(Constants.LOG_FIELDS[8],msgException);
			String where = Constants.LOG_FIELDS[0] + "= " + Global.currentDateTime;
			logMasterService.updateLog(values,where);
		}
	}
}
