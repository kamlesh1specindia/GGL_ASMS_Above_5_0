package com.spec.asms.service.async;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.CustomerMasterService;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.service.async.AsyncUpdateCustomerStatus.OnResponseUpdateCustomerStatusId;
import com.spec.asms.vo.CustomerMasterVO;
import com.spec.asms.vo.MaintainanceVO;

public class AsyncUpdateCustomerStatus extends AsyncTask<Void,Void,String>
{
	private OnResponseUpdateCustomerStatusId responseListener;
	private Context context; 
	private ProgressDialog prDialog = null;
	private ArrayList<CustomerMasterVO> getAllCustLists = new ArrayList<CustomerMasterVO>();
	private CustomerMasterService custService;
	private UserMasterService masterService;
	private CustomerMasterService customerMasterService;

	public AsyncUpdateCustomerStatus(Context context, OnResponseUpdateCustomerStatusId OnResponseListenerFind) {
		this.context = context;
		this.prDialog = new ProgressDialog(context);
		this.responseListener = OnResponseListenerFind;
		this.custService = new CustomerMasterService(context);
		masterService = new UserMasterService(context);
		customerMasterService = new CustomerMasterService(context);
		
	}
	
	
	@Override
	protected void onPreExecute() {			
		super.onPreExecute();
		
		try{
			prDialog = new ProgressDialog(context);
			prDialog.setMessage(Constants.LABEL_BLANK+Constants.LABEL_UPDATING_CUSTOMER_STATUS);
			prDialog.setCancelable(false);
			prDialog.show();
			prDialog.setProgress(10);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	protected String doInBackground(Void... arg0) {
		
		String whereCustbyUser = "isdeleted = 0";
		System.out.println("::::Where Cust By User Query :::"+whereCustbyUser);
		try {
			getAllCustLists = (ArrayList<CustomerMasterVO>) custService.getSelectedCustomers(Constants.TBL_MST_CUSTOMER,Constants.CUSTOMER_MASTER_FIELDS,whereCustbyUser);
			String statusCode;
			int statusId;
			String where;
			for(CustomerMasterVO customerMasterVO: getAllCustLists){
				statusCode = customerMasterVO.getCustomerStatus();
				statusId = masterService.getStatusId(statusCode);
				where = "customerid = '" +customerMasterVO.getCustomerID()+"'";
				if(customerMasterService != null){
					ContentValues cv = new ContentValues();
					cv.put(Constants.CUSTOMER_MASTER_FIELDS[3],statusId);
					customerMasterService.updateCustomerByID(Constants.TBL_MST_CUSTOMER, cv, where);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		try{
			prDialog.dismiss();
		}catch (Exception e) {
			e.printStackTrace();
		}
		responseListener.onSuccess(result);
	}
	
	
	public interface OnResponseUpdateCustomerStatusId {
		public void onSuccess(String result);
		public void onFailure(String message);
	}

}
