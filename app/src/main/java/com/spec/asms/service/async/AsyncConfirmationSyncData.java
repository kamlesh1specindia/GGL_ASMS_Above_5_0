package com.spec.asms.service.async;

import java.util.Map;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.SOAPCaller;

public class AsyncConfirmationSyncData extends AsyncTask<Map<String, Object>, Void, JSONObject>{
	private ProgressDialog prDialog = null;
	private JSONObject response = null;
//	private OnResponseListenerConfirmation responseListener;
	private Context context;

	public AsyncConfirmationSyncData(Context context){//, OnResponseListenerConfirmation responder) {
		this.context = context;
		this.prDialog = new ProgressDialog(context);
//		this.responseListener = responder;
	}

	@Override
	protected void onPreExecute() {			
		super.onPreExecute();

		try {
			prDialog = new ProgressDialog(context);
			prDialog.setMessage(Constants.LABEL_BLANK+ Constants.LABEL_PROGRESS_CONFIRMING_SYNC);
			prDialog.setCancelable(false);
			prDialog.show();
			prDialog.setProgress(10);
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncConfirmationSyncData:"+ Utility.convertExceptionToString(ex));
		}

	}
	@Override
	protected JSONObject doInBackground(Map<String, Object>... params) {
		SOAPCaller soapCaller = SOAPCaller.getInstance();

		Map<String, Object> param = params[0];
		Log.e("AsyncConfirmationSyncData", "param.size() :::::::::::::::: "+param.size());
		try {
			response = soapCaller.getJSON(Constants.METHOD_CONFIRMATION_SYNC_DATA, param);			
			Log.d("Response", "******"+response);
			Log.d("JSON", response.getString(Constants.METHOD_AUTHENTICATE));	
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncConfirmationSyncData:"+ Utility.convertExceptionToString(e));
		}
		return response;
	}
	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);		
		prDialog.dismiss();
		if(result.has(Constants.RESPONSE_ERROR)){
			Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.RESPONSE_ERROR);
		}else if(result != null && result.equals(Constants.JSON_FAILED)){
			Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.JSON_FAILED);
		}else if(result.has(Constants.NETWORK_EXCEPTION_METHOD)){
			Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.NETWORK_EXCEPTION_METHOD);
		}else if(result.has(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD)){
			Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.CONNECTION_REFUSED_EXCEPTION_METHOD);
		}else if(result.has(Constants.SOCKET_EXCEPTION)){
			Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.SOCKET_EXCEPTION);
		}else if(result.has(Constants.IOEXCEPTION)){
			Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.IOEXCEPTION);
		}else if(result.has(Constants.XMLPULLPARSEREXCEPTION)){
			Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.XMLPULLPARSEREXCEPTION);
		}else if(result.has(Constants.EXCEPTION)){
			Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.EXCEPTION);
		}
//		responseListener.onSuccess(result);
	}

	/*public interface OnResponseListenerConfirmation {
		public void onSuccess(JSONObject responce);
		public void onFailure(String message);
	}*/
}