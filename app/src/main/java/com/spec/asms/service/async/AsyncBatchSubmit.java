package com.spec.asms.service.async;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.SocketException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Global;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.LogMasterService;
import com.spec.asms.service.SOAPCaller;

public class AsyncBatchSubmit extends AsyncTask<Map<String, Object>, Void, JSONObject>{
	private ProgressDialog prDialog = null;
	private JSONObject response = null;
	private OnResponseListener responseListener;
	private Context context;
	private String from;
	private boolean isSuccess = true;
	private String responseStatusCode = "";
	private LogMasterService logMasterService;

	public AsyncBatchSubmit(Context context, OnResponseListener responder,String from) {
		this.context = context;
		this.from = from;
		if(!this.from.equals("Service"))
		  this.prDialog = new ProgressDialog(context);
		this.responseListener = responder;
		
		
	}

	@Override
	protected void onPreExecute() {			
		super.onPreExecute();
		responseStatusCode = Constants.BLANK;
		try {
			if(!from.equals("Service")){
			prDialog = new ProgressDialog(context);
			prDialog.setMessage(Constants.LABEL_BLANK+ Constants.LABEL_PROGRESS_SYNC);
			prDialog.setCancelable(false);
			prDialog.show();
			prDialog.setProgress(10);}
			insertSubmissionStartLog(context);
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncBatchSubmit:"+ Utility.convertExceptionToString(ex));
			isSuccess = false;
			Global.isCycleRunning = false;
			Global.isManualSubmitRunning = false;
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String errorMessage = sw.toString(); 
			insertExceptionLog(errorMessage);
			
		}
		
	}
	@Override
	protected JSONObject doInBackground(Map<String, Object>... params) {
		SOAPCaller soapCaller = SOAPCaller.getInstance();

		Map<String, Object> param = params[0];
		Log.e("AsyncBatchSubmit", "DateTime:-"+Utility.getcurrentTimeStamp()+ "param.size() :::::::::::::::: "+param.size());
		try {
			response =(JSONObject) soapCaller.getResponse(Constants.METHOD_BATCH_SUBMIT, param)[0];	
			responseStatusCode = (String) soapCaller.getResponseCode();
			System.out.println("Response Code :::" + responseStatusCode);
			//Log.e("Response", "******"+response);
			//				Log.d("JSON", "::::::::: "+response.getString(Constants.JSON_MASTER_DATA));
			//				Log.d("JSON", "::::::::: "+response.getJSONArray(Constants.JSON_TECHNICIANS));

		}catch(JSONException jse){
			Log.d(Constants.DEBUG,":AsyncBatchSubmit:"+ Utility.convertExceptionToString(jse));
			jse.printStackTrace();
			isSuccess = false;
			Global.isCycleRunning = false;
			Global.isManualSubmitRunning = false;
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			jse.printStackTrace(pw);
			String errorMessage = sw.toString(); 
			insertExceptionLog(errorMessage);
			insertResponseLog();
/*			StackTraceElement stackTrace = jse.getStackTrace()[0];
			String method = stackTrace.getMethodName();*/
			response = new JSONObject();
			try{
				response.put(Constants.JSON_FAILED,Constants.JSON_FAILED);
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch(ConnectException ce){
			Log.d(Constants.DEBUG,":AsyncBatchSubmit:"+ Utility.convertExceptionToString(ce));
			Log.e("Error While caling doInBackground:", "*******"+ce.getMessage());
			isSuccess = false;
			StackTraceElement stackTrace = ce.getStackTrace()[0];
			String method = stackTrace.getMethodName();
			response = new JSONObject();
			try{
				if(method != null && method.equalsIgnoreCase(Constants.NETWORK_EXCEPTION_METHOD)){
					response.put(Constants.NETWORK_EXCEPTION_METHOD,Constants.NETWORK_EXCEPTION_METHOD);
				}else if(method != null && method.equalsIgnoreCase(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD)){
					response.put(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD,Constants.CONNECTION_REFUSED_EXCEPTION_METHOD);
				}else{
					response.put(Constants.JSON_FAILED,Constants.JSON_FAILED);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch(SocketException se){
			Log.e("Error While caling doInBackground:", "*******"+se.getMessage());
			Log.d(Constants.DEBUG,":AsyncBatchSubmit:"+ Utility.convertExceptionToString(se));
			isSuccess = false;
			Global.isCycleRunning = false;
			Global.isManualSubmitRunning = false;
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			se.printStackTrace(pw);
			String errorMessage = sw.toString(); 
			insertExceptionLog(errorMessage);
			insertResponseLog();
			response = new JSONObject();
			try{
				response.put(Constants.SOCKET_EXCEPTION,Constants.SOCKET_EXCEPTION);
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch(IOException ioe){
			Log.e("Error While caling doInBackground:", "*******"+ioe.getMessage());
			Log.d(Constants.DEBUG,":AsyncBatchSubmit:"+ Utility.convertExceptionToString(ioe));
			isSuccess = false;
			Global.isCycleRunning = false;
			Global.isManualSubmitRunning = false;
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ioe.printStackTrace(pw);
			String errorMessage = sw.toString(); 
			insertExceptionLog(errorMessage);
			insertResponseLog();
			response = new JSONObject();
			try{
				response.put(Constants.IOEXCEPTION,Constants.IOEXCEPTION);
			}catch(Exception e){
				e.printStackTrace();
				Log.d(Constants.DEBUG,":AsyncBatchSubmit:"+ Utility.convertExceptionToString(e));
			}
		}catch(XmlPullParserException xpe){
			Log.e("Error While caling doInBackground:", "*******"+xpe.getMessage());
			Log.d(Constants.DEBUG,":AsyncBatchSubmit:"+ Utility.convertExceptionToString(xpe));
			isSuccess = false;
			Global.isCycleRunning = false;
			Global.isManualSubmitRunning = false;
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			xpe.printStackTrace(pw);
			String errorMessage = sw.toString(); 
			insertExceptionLog(errorMessage);
			insertResponseLog();
			response = new JSONObject();
			try{
				response.put(Constants.XMLPULLPARSEREXCEPTION,Constants.XMLPULLPARSEREXCEPTION);
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncBatchSubmit:"+ Utility.convertExceptionToString(e));
			isSuccess = false;
			Global.isCycleRunning = false;
			Global.isManualSubmitRunning = false;
			System.out.println("Error In Submission ::::" + e.getMessage());
			Log.e("Error While caling doInBackground:", "*******"+e.getMessage());
			//Utility.alertDialog(context, Constants.ALERT_TITLE_GENERAL_INFO, Constants.ERROR_SESSION_TIMEOUT);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String errorMessage = sw.toString(); 
			insertExceptionLog(errorMessage);
			insertResponseLog();
			response = new JSONObject();
			try{
				response.put(Constants.EXCEPTION,Constants.EXCEPTION);
			}catch(Exception ex){
				ex.printStackTrace();
				Log.d(Constants.DEBUG,":AsyncBatchSubmit:"+ Utility.convertExceptionToString(ex));
			}
		}
		return response;
	}
	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);
		insertResponseLog();
		if(!from.equals("Service"))
			prDialog.dismiss();
		if(isSuccess){
			responseListener.onSuccess(result);
		}else{
			responseListener.onFailure("false");
		}
		
	}

	public interface OnResponseListener {
		public void onSuccess(JSONObject responce);
		public void onFailure(String message);
	}
	
	public void insertSubmissionStartLog(Context context){
		LogMasterService logMasterService = new LogMasterService(context);
		ContentValues contentValues = new ContentValues();
		contentValues.put(Constants.LOG_FIELDS[2],context.getString(R.string.status_submit_request));
		String where = Constants.LOG_FIELDS[0] + "= " + Global.currentDateTime;
		logMasterService.updateLog(contentValues, where);
	}
	
	private void insertExceptionLog(String msgException){
		if(msgException != null){
			logMasterService = new LogMasterService(context);
			ContentValues values = new ContentValues();
			values.put(Constants.LOG_FIELDS[8],responseStatusCode + msgException);
			String where = Constants.LOG_FIELDS[0] + "= " + Global.currentDateTime;
			logMasterService.updateLog(values,where);
		}
	}
	private void insertResponseLog(){
		logMasterService = new LogMasterService(context);
		ContentValues values = new ContentValues();
		values.put(Constants.LOG_FIELDS[3],responseStatusCode);
		values.put(Constants.LOG_FIELDS[7],System.currentTimeMillis());
		String where = Constants.LOG_FIELDS[0] + "=" + Global.currentDateTime;
		logMasterService.updateLog(values,where);
	}
}