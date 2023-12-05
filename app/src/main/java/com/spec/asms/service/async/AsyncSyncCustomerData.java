package com.spec.asms.service.async;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.SecurityManager;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.CustomerMasterService;
import com.spec.asms.service.MaintainanceService;
import com.spec.asms.service.SOAPCaller;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.view.LoginActivity;
import com.spec.asms.view.MainTabActivity;
import com.spec.asms.vo.CustomerMasterVO;
import com.spec.asms.vo.KitchenSurakshaTubeVO;

public class AsyncSyncCustomerData extends AsyncTask<Map<String, Object>, Void, ArrayList<String>>{
	private ProgressDialog prDialog = null;
	private OnResponseListener responseListener;
	private Context context;
	private UserMasterService userService;
	public CustomerMasterService custService;
	public ArrayList<String> sucessfulCustList = new ArrayList<String>();
	public ArrayList<String> reAssignCustList = new ArrayList<String>();
	private JSONObject response = null;

	public AsyncSyncCustomerData(Context context, OnResponseListener responder) {
		this.context = context;
		this.prDialog = new ProgressDialog(context);
		this.responseListener = responder;
		System.out.println(":::::AsyncSyncCustomerData called:::::");
	}

	@Override
	protected void onPreExecute() {			
		super.onPreExecute();

		try {
			prDialog = new ProgressDialog(context);
			prDialog.setMessage(Constants.LABEL_BLANK + Constants.LABEL_PROGRESS_CUSTOMER);
			prDialog.setCancelable(false);
			prDialog.show();
			prDialog.setProgress(10);
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncSyncCustomerData:"+ Utility.convertExceptionToString(ex));
		}
	}


	@Override
	protected ArrayList<String> doInBackground(Map<String, Object>... params) {
		SOAPCaller soapCaller = SOAPCaller.getInstance();
		//JSONObject response = null;
		userService = new UserMasterService(context);
		custService = new CustomerMasterService(context);
		Map<String, Object> param = params[0];
		Log.e("AsyncSyncCustomerData", "DateTime:-"+Utility.getcurrentTimeStamp()+ "param.size() :::::::::::::::: "+param.size());
		try {
			response = soapCaller.getJSON(Constants.METHOD_SYNC_CUSTOMER_DATA, param);			
			Log.d("Response", "******"+response);
			
			if(response.has("ReAssigned"))
			{
				JSONArray reAssignData = response.getJSONArray("ReAssigned");
				Log.d("JSON", "::::::::: ReassignedCustData ::: "+reAssignData);
				Log.d("JSON", "::::::::: ReassignedCustData.length() ::: "+reAssignData.length());

			for(int i=0;i<reAssignData.length();i++){
				
				String where = "statusid = "+userService.getStatusId("OP")+" and maintainanceorderid ='"+reAssignData.getString(i) +"'";
				/*ContentValues cv = new ContentValues();
				cv.put(Constants.DB_ISDELETED,1);*/
				if(userService.deleteUser(Constants.TBL_MST_CUSTOMER,where) != -1){
					reAssignCustList.add(reAssignData.getString(i));
					Log.e(Constants.TAG_LOGIN_ACTIVITY,"ReAssignedcustListValue:- :: "+reAssignCustList.size());
					Log.e(Constants.TAG_LOGIN_ACTIVITY,"CustLogicallyDelete="+reAssignData.getString(i));
				}
			}

		}

			if(response.has("CustomerData"))
			{
				
				
				JSONArray customerData = response.getJSONArray("CustomerData");
				
				
				Log.d("JSON", "::::::::: customerData ::: "+customerData);
				Log.d("JSON", "::::::::: customerData.length() ::: "+customerData.length());

				for(int i=0;i<customerData.length();i++){
					JSONObject obj = customerData.getJSONObject(i);
					Log.d("JSON", "::::::::: customerData obj ::: "+obj);
					Log.d("JSON", "::::::::: customerData Phone ::: "+obj.getString("Phone"));
					Log.d("JSON", "::::::::: customerData MaintenanceOrderID ::: "+obj.getString("MaintenanceOrderID"));
					Log.d("JSON", "::::::::: customerData CustomerName ::: "+obj.getString("CustomerName"));
					Log.d("JSON", "::::::::: customerData OrderID ::: "+obj.getString("OrderID"));
					Log.d("JSON", "::::::::: customerData Address ::: "+obj.getString("Address"));
					Log.d("JSON", "::::::::: customerData CustomerID ::: "+obj.getString("CustomerID"));
					Log.d("JSON", "::::::::: customerData MRUnit ::: "+ obj.getString("MRUnit"));
					Log.d("JSON", "::::::::: customerData Society Name ::: "+ obj.getString("SocietyName"));
					Log.d("JSON", "::::::::: customerData Meter Number ::: " + obj.getString("MeterNumber"));

					Log.e("JSON",  "DateTime:-"+Utility.getcurrentTimeStamp()+"::::::::: customerData status ID from DB ::: "+userService.getStatusId("OP"));
					Log.e("JSON",  "DateTime:-"+Utility.getcurrentTimeStamp()+"::::::::: customerData createdon from DB ::: "+userService.getCreatedDate(context));
					Log.e("JSON",  "DateTime:-"+Utility.getcurrentTimeStamp()+"::::::::: customerData updated on from DB ::: "+userService.getUpdatedDate(context));

					//String where = "customerid ='"+obj.getString("CustomerID")+"' and statusid = 5";
					String where = "maintainanceorderid = '"+obj.getString("MaintenanceOrderID")+"' and  issync = 0";
					CustomerMasterVO customerVORes = custService.getCustomerInfo(Constants.TBL_MST_CUSTOMER,
							Constants.CUSTOMER_MASTER_FIELDS,where);
					sucessfulCustList.add(obj.getString("MaintenanceOrderID"));
					Log.e(Constants.TAG_LOGIN_ACTIVITY,"custListValue:- :: "+sucessfulCustList.size());
					if (customerVORes.getCustomerID().equals(Constants.LABEL_BLANK)) 
					{
						ContentValues cv = new ContentValues();
						cv.put(Constants.DB_MAINTAINANCE_ORDER_ID,obj.getString("MaintenanceOrderID"));
						cv.put(Constants.DB_CUSTOMER_NAME,SecurityManager.encrypt(obj.getString("CustomerName"),Constants.SALT));
						cv.put(Constants.DB_ORDER_ID,obj.getString("OrderID"));

						cv.put(Constants.DB_CUSTOMER_ADD,SecurityManager.encrypt(obj.getString("Address"),Constants.SALT));
						cv.put(Constants.DB_CUSTOMER_ID,obj.getString("CustomerID"));
						cv.put(Constants.DB_METER_NUMBER, obj.getString("MeterNumber"));
						
						cv.put(Constants.DB_STATUS_ID,userService.getStatusId(obj.getString("StatusCodeID")));
						cv.put(Constants.DB_ISSYNC,"0");
						cv.put(Constants.DB_WALK_SEQUENCE,SecurityManager.encrypt("Up",Constants.SALT));
						cv.put(Constants.DB_CREATEDBY,SharedPrefrenceUtil.getPrefrence(context,Constants.DB_USER_ID, null));
						cv.put(Constants.DB_UPDATEDBY,SharedPrefrenceUtil.getPrefrence(context,Constants.DB_USER_ID, null));
						cv.put(Constants.DB_CREATEDON,SecurityManager.encrypt(userService.getCreatedDate(context),Constants.SALT));
						cv.put(Constants.DB_UPDATEDON,SecurityManager.encrypt(userService.getUpdatedDate(context),Constants.SALT));
						cv.put(Constants.DB_ISDELETED,0);
						//cv.put(Constants.DB_DATE, 0);
						cv.put(Constants.DB_MRUNIT, obj.getString("MRUnit"));
						cv.put(Constants.DB_SOCIETYNAME,obj.getString("SocietyName"));
						cv.put(Constants.DB_PHONE,obj.getString("Phone"));
						cv.put(Constants.DB_CUSTOMER_STATUS,obj.getString("CustomerStatus"));
						//cv.put(Constants.DB_SURAKSHA_TUBE_EXPIRY_DATE, obj.getString("STubeExpiryDate"));
						cv.put(Constants.DB_EXPIRY_DATE,SecurityManager.encrypt(obj.getString("STubeExpiryDate"),Constants.SALT));
						System.out.println("Insert Phone :::::"+obj.getString("Phone"));

						if(userService.insertUser(Constants.TBL_MST_CUSTOMER,cv) != -1){
							
							Log.e(Constants.TAG_LOGIN_ACTIVITY, "DateTime:-"+Utility.getcurrentTimeStamp()+">>>>>>>Customer Insert<<<<<<<<");
						}

					}
				}
		
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncSyncCustomerData:"+ Utility.convertExceptionToString(e));
		}
		return sucessfulCustList;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onPostExecute(ArrayList<String> result) {	
		super.onPostExecute(result);
		if(prDialog != null && prDialog.isShowing()){
			prDialog.dismiss();
		}
		
		//		if(result.size() > 0)
		//		{

		Log.e("****************", "result.toString() ::::: "+result.toString());
		Log.e("****************", "result.toString() ::::: "+SecurityManager.encrypt(result.toString(),Constants.SALT));

		Log.e("****************", "result.toString() ::::: "+result.toString());
		 if(response.has(Constants.NETWORK_EXCEPTION_METHOD)){
				Utility.alertDialog(context, Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
		}else if(response.has(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD)){
				Utility.alertDialog(context,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
		}else{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(Constants.JSON_LOGIN_USERNAME, SecurityManager.encrypt(SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USERNAME, null),Constants.SALT));
			param.put(Constants.JSON_LOGIN_PASSWORD, SecurityManager.encrypt(SharedPrefrenceUtil.getPrefrence(context, Constants.DB_PASSWORD, null),Constants.SALT));
			param.put(Constants.JSON_LOGIN_DEVICEID, SecurityManager.encrypt(Constants.LABLE_DEVICE_ID,Constants.SALT));//Utility.getDeviceId(getActivity()));
			param.put(Constants.JSON_MAIN_MAINTENANCE_FORM_ID,SecurityManager.encrypt(result.toString(),Constants.SALT));
			param.put(Constants.JSON_REASSIGNMENT_ID,SecurityManager.encrypt(reAssignCustList.toString(),Constants.SALT));
			System.out.println("Maintainace Id:######"+result);
			System.out.println("ReAssign IDs :#####"+reAssignCustList);
	
			AsyncConfirmationSyncData asyncConfirmationSyncData= new AsyncConfirmationSyncData(context);
			asyncConfirmationSyncData.execute(param);
		}
		/*else{
			Utility.alertDialog(context,Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
		}*/
		responseListener.onSuccess();

	}

	public interface OnResponseListener {
		public void onSuccess();
		public void onFailure(String message);
	}

}