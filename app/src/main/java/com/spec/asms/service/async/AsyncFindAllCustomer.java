package com.spec.asms.service.async;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.CustomerMasterService;
import com.spec.asms.vo.CustomerMasterVO;

public class AsyncFindAllCustomer extends AsyncTask<Void,Void,ArrayList<CustomerMasterVO>>
{
	private OnResponseListenerFindCustomer responseListener;
	private Context context; 
	private ProgressDialog prDialog = null;
	private ArrayList<CustomerMasterVO> getAllCustLists = new ArrayList<CustomerMasterVO>();
	private CustomerMasterService custService;
	private String where;

	public AsyncFindAllCustomer(Context context, OnResponseListenerFindCustomer OnResponseListenerFind) {
		this.context = context;
		this.prDialog = new ProgressDialog(context);
		this.responseListener = OnResponseListenerFind;
		this.custService = new CustomerMasterService(context);
		
	}
	
	
	@Override
	protected void onPreExecute() {			
		super.onPreExecute();
		
		try{
			prDialog = new ProgressDialog(context);
			prDialog.setMessage(Constants.LABEL_BLANK+Constants.LABEL_PROGRESS_SEARCHING_CUSTOMER_LIST);
			prDialog.setCancelable(false);
			prDialog.show();
			prDialog.setProgress(10);
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncFindAllCustomer:"+ Utility.convertExceptionToString(e));
		}

	}
	
	@Override
	protected ArrayList<CustomerMasterVO> doInBackground(Void... arg0) {
		
		String whereCustbyUser = "isdeleted = 0";
		System.out.println("::::Where Cust By User Query :::"+whereCustbyUser);
		try {
			getAllCustLists = (ArrayList<CustomerMasterVO>) custService.getSelectedCustomers(Constants.TBL_MST_CUSTOMER,Constants.CUSTOMER_MASTER_FIELDS,whereCustbyUser);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncFindAllCustomer:"+ Utility.convertExceptionToString(e));
		}
		return getAllCustLists;
	}
	
	@Override
	protected void onPostExecute(ArrayList<CustomerMasterVO> result) {
		super.onPostExecute(result);
		try{
			prDialog.dismiss();
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncFindAllCustomer:"+ Utility.convertExceptionToString(e));
		}
		responseListener.onSuccess(result);
	}
	
	
	public interface OnResponseListenerFindCustomer {
		public void onSuccess(ArrayList<CustomerMasterVO> result);
		public void onFailure(String message);
	}

}
