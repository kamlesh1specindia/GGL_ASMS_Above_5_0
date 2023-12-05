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
/**
 * 
 * @author shivani
 *
 */
public class AsyncTestConnection extends AsyncTask<Map<String, Object>, Void, JSONObject> {
	private OnResponseListenerTestConnection responseListener;
	private Context context; 
	private ProgressDialog prDialog = null;

	public AsyncTestConnection(Context context, OnResponseListenerTestConnection onResponseListenerTest) {
		this.context = context;
		this.prDialog = new ProgressDialog(context);
		this.responseListener = onResponseListenerTest;
	}

	@Override
	protected void onPreExecute() {			
		super.onPreExecute();

		try {
			prDialog = new ProgressDialog(context);
			prDialog.setMessage(Constants.LABEL_PROGRESS_TEST_CONNECTION);
			prDialog.setCancelable(false);
			prDialog.show();
			prDialog.setProgress(10);
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncTestConnection:"+ Utility.convertExceptionToString(ex));
		}

	}
	@Override
	protected JSONObject doInBackground(Map<String, Object>... params) {
		SOAPCaller soapCaller = SOAPCaller.getInstance();
		JSONObject response = null;

		Map<String, Object> param = params[0];

		try {
			response = soapCaller.getJSON(Constants.METHOD_TEST_CONNECTION, param);			
			Log.d("Response", "******"+response);
			Log.d("JSON", response.getString(Constants.JSON_TEST_STATUS));
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncTestConnection:"+ Utility.convertExceptionToString(e));
		}
		return response;
	}
	
	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);
		prDialog.dismiss();
		responseListener.onSuccess(result);
	}

	public interface OnResponseListenerTestConnection {
		public void onSuccess(JSONObject responce);
		public void onFailure(String message);
	}

}
