package com.spec.asms.service.async;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.LogMasterService;
import com.spec.asms.vo.LogVO;

public class AsyncViewLog extends  AsyncTask<Void,Void,List<LogVO>>{

	private Context context; 
	private ProgressDialog prDialog = null;
	private LogMasterService logMasterService;
	private OnResponseListenerViewLog onResponseListenerViewLog;
	private List<LogVO> logVOs;
	private String searchDate;
	private boolean isSelected;
	
	public AsyncViewLog(Context context,OnResponseListenerViewLog onResponseListenerViewLog,String date,boolean isSelected){
		this.context = context;
		this.prDialog = new ProgressDialog(context);
		this.onResponseListenerViewLog = onResponseListenerViewLog;
		this.searchDate = date;
		this.isSelected = isSelected;
		logMasterService = new LogMasterService(context);
		logVOs = new ArrayList<LogVO>(0);
		
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
			Log.d(Constants.DEBUG,":AsyncViewLog:"+ Utility.convertExceptionToString(e));
		}
	}
	
	
	@Override
	protected List<LogVO> doInBackground(Void... params) {

		try{
			 logVOs = logMasterService.getAllLogDetail(context,searchDate,isSelected);
			
		}catch(Exception e){
			e.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncViewLog:"+ Utility.convertExceptionToString(e));
		}
		return logVOs;
	}
	
	
	@Override
	protected void onPostExecute(List<LogVO> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		try{
			prDialog.dismiss();
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncViewLog:"+ Utility.convertExceptionToString(e));
		}
		onResponseListenerViewLog.onSuccess(result);
	}

	
	public interface OnResponseListenerViewLog {
		public void onSuccess(List<LogVO> result);
		public void onFailure(String message);
	}

}
