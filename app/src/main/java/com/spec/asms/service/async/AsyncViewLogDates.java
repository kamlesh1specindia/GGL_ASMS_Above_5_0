package com.spec.asms.service.async;

import java.util.ArrayList;
import java.util.List;

import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.LogMasterService;
import com.spec.asms.service.async.AsyncViewLog.OnResponseListenerViewLog;
import com.spec.asms.vo.LogVO;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncViewLogDates  extends  AsyncTask<Void,Void,List<String>>{

	private Context context; 
	private ProgressDialog prDialog = null;
	private LogMasterService logMasterService;
	private OnResponseListenerViewLogDates onResponseListenerViewLogDates;
	private List<String> dateVOs;
	
	public AsyncViewLogDates(Context context,OnResponseListenerViewLogDates onResponseListenerViewLogDates){
		this.context = context;
		this.prDialog = new ProgressDialog(context);
		this.onResponseListenerViewLogDates = onResponseListenerViewLogDates;
		logMasterService = new LogMasterService(context);
		dateVOs = new ArrayList<String>(0);
	}
	
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		try{
			prDialog = new ProgressDialog(context);
			prDialog.setMessage(Constants.LABEL_BLANK+Constants.LABEL_PROGRESS);
			prDialog.setCancelable(false);
			prDialog.show();
			prDialog.setProgress(10);
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncViewLogDates:"+ Utility.convertExceptionToString(e));
		}
	}
	
	
	@Override
	protected List<String> doInBackground(Void... params) {

		try{
			dateVOs = logMasterService.getLogDates(context);
			
		}catch(Exception e){
			e.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncViewLogDates:"+ Utility.convertExceptionToString(e));
		}
		return dateVOs;
	}
	
	
	@Override
	protected void onPostExecute(List<String> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		try{
			prDialog.dismiss();
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncViewLogDates:"+ Utility.convertExceptionToString(e));
		}
		onResponseListenerViewLogDates.onSuccess(result);
	}

	
	public interface OnResponseListenerViewLogDates {
		public void onSuccess(List<String> result);
		public void onFailure(String message);
	}

}
