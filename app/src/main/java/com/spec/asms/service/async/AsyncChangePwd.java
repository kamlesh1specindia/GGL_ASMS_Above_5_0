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

public class AsyncChangePwd extends AsyncTask<Map<String, Object>, Void, JSONObject>{
	private OnResponseListenerChangePwd responseListener;
	private Context context; 
	private ProgressDialog prDialog = null;

	public AsyncChangePwd(Context context, OnResponseListenerChangePwd onResponseListenerPwd) {
		this.context = context;
		this.prDialog = new ProgressDialog(context);
		this.responseListener = onResponseListenerPwd;
	}

	@Override
	protected void onPreExecute() {			
		super.onPreExecute();

		try {
			prDialog = new ProgressDialog(context);
			prDialog.setMessage(Constants.LABEL_PROGRESS_CHANGE_PWD);
			prDialog.setCancelable(false);
			prDialog.show();
			prDialog.setProgress(10);
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncChangePwd:"+ Utility.convertExceptionToString(ex));
		}

	}

	@Override
	protected JSONObject doInBackground(Map<String, Object>... params) {
		SOAPCaller soapCaller = SOAPCaller.getInstance();
		JSONObject response = null;

		Map<String, Object> param = params[0];

		try {
			response = soapCaller.getJSON(Constants.METHOD_CHANGE_PASSWORD, param);			
			Log.d("Response", "******"+response);
//			Log.d("JSON", response.getString(Constants.METHOD_AUTHENTICATE));
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncChangePwd:"+ Utility.convertExceptionToString(e));
		}
		return response;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);
		prDialog.dismiss();
		responseListener.onSuccess(result);
	}

	public interface OnResponseListenerChangePwd {
		public void onSuccess(JSONObject responce);
		public void onFailure(String message);
	}
}