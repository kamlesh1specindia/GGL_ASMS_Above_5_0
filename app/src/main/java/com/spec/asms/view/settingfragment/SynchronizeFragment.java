package com.spec.asms.view.settingfragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Global;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.SecurityManager;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.dto.MaintainanceDTO;
import com.spec.asms.service.CommanService;
import com.spec.asms.service.LogMasterService;
import com.spec.asms.service.MaintainanceService;
import com.spec.asms.service.async.AsyncBatchCreation;
import com.spec.asms.service.async.AsyncBatchCreation.OnResponseListenerBatchCreation;
import com.spec.asms.service.async.AsyncBatchSubmit;
import com.spec.asms.service.async.AsyncBatchSubmit.OnResponseListener;
import com.spec.asms.vo.BatchSubmitVO;
/**
 * A Class for sync data with server means Batch Submit.
 *
 */
public class SynchronizeFragment extends Fragment {

	private View syncView;
	private Button btnSync,btnAutoSub;
	private TextView texSync;
	private TextView txtAutoSubmission;
	private int id;
	private MaintainanceService maintainanceService;
	private JSONArray successMFormID;
	//	public static boolean isCycleRunning;
	public int totalRecords = 0 ,sucessfulRecords = 0,unsucessfulRecords = 0;
	public static int totalSuccesfulRecords = 0,totalUnSuccessfulRecords = 0,
			totalCompletedRecords= 0,totalSyncRecords= 0,totalAllCompleteRecords = 0;
	private TextView lblLstSubDt,lblTotRec,lblSuccRec,lblUnsuccRec;
	private TextView txtLstSubDt,txtTotRec,txtSuccRec,txtUnsuccRec;
	private TextView txtTotalCompletedRecords,txtTotalSyncRecord;
	private TextView lblTotalCompletedRec,lblTotalSyncRec;
	public BatchSubmitVO lastBatchVO ;
	public int completedRecords = 0;
	//	public static int offset = 0;
	private List<MaintainanceDTO> arryMaintainance;

	private static int totalMaintainaceRecords = 0;
	private static int startIndex =0;
	private static int lastIndex = 0;
	private static int batchsize = 0;


	/**
	 * A parameterized constructor 
	 * @return SynchronizeFragment object
	 */
	public static SynchronizeFragment newInstance() {
		SynchronizeFragment faqFragment = new SynchronizeFragment();
		return faqFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);

		maintainanceService = new MaintainanceService(getActivity());

		syncView = inflater.inflate(R.layout.form_sync,null);
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::SynchronizeFragment:::");

		btnSync = (Button)syncView.findViewById(R.id.btnSync);
		btnAutoSub = (Button)syncView.findViewById(R.id.btnAutoSub);
		texSync = (TextView)syncView.findViewById(R.id.txtSync);
		txtAutoSubmission = (TextView)syncView.findViewById(R.id.txtAutoSubMsg);
		txtLstSubDt = (TextView)syncView.findViewById(R.id.txtLstSubDt);
		txtTotRec = (TextView)syncView.findViewById(R.id.txtTotRec);
		txtSuccRec = (TextView)syncView.findViewById(R.id.txtSuccRec);
		txtUnsuccRec = (TextView)syncView.findViewById(R.id.txtUnsuccRec);

		lblLstSubDt = (TextView)syncView.findViewById(R.id.lblLstSubDt);
		lblTotRec = (TextView)syncView.findViewById(R.id.lblTotRec);
		lblSuccRec = (TextView)syncView.findViewById(R.id.lblSuccRec);
		lblUnsuccRec = (TextView)syncView.findViewById(R.id.lblUnsuccRec);
		lblTotalCompletedRec = (TextView) syncView.findViewById(R.id.lblTotalCompletedRec);
		lblTotalSyncRec = (TextView) syncView.findViewById(R.id.lblTotalSyncRec);
		txtTotalCompletedRecords = (TextView) syncView.findViewById(R.id.txtTotalCompletedRecords);
		txtTotalSyncRecord = (TextView) syncView.findViewById(R.id.txtTotalSyncRecord);


		changeLanguage(id);

		if(Global.isCycleRunning)
		{
			btnSync.setEnabled(false);
			btnSync.setVisibility(View.GONE);
			btnAutoSub.setVisibility(View.VISIBLE);
			txtAutoSubmission.setVisibility(View.VISIBLE);
			Global.isManualSubmitRunning = false;
		}else{
			btnSync.setEnabled(true);
			btnSync.setVisibility(View.VISIBLE);
			btnAutoSub.setVisibility(View.INVISIBLE);
			txtAutoSubmission.setVisibility(View.INVISIBLE);
		}


//		maintainanceService = new MaintainanceService(getActivity());
		batchsize = maintainanceService.getBatchSize();
		lastIndex = batchsize;

		totalCompletedRecords = maintainanceService.getCompletedRecordCount();
		totalAllCompleteRecords = maintainanceService.getTotalCompletedRecordCount();
		totalSyncRecords = maintainanceService.getTotalSyncRecordCount();

		txtTotalCompletedRecords.setText(String.valueOf(totalAllCompleteRecords));
		txtTotalSyncRecord.setText(String.valueOf(totalSyncRecords));

		lastBatchVO = maintainanceService.getLastBatchSubmit();

		if(lastBatchVO.getBatchsubmitid() > 0)
		{
			txtLstSubDt.setText(Utility.getConvertedDateTime(getActivity(),lastBatchVO.getCreatedOn()));
			txtTotRec.setText(lastBatchVO.getTotRecords()+"");
			txtSuccRec.setText(lastBatchVO.getSuccessfulRecords()+"");
			txtUnsuccRec.setText(lastBatchVO.getUnsuccessfulRecords()+"");
			txtTotalSyncRecord.setText(String.valueOf(lastBatchVO.getSuccessfulRecords()));
		}
		btnSync.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			public void onClick(View v) {

				if(Utility.isNetAvailable(getActivity())){
					try{

						if(Global.isCycleRunning)
						{
							btnSync.setEnabled(false);
							btnSync.setVisibility(View.GONE);
							btnAutoSub.setVisibility(View.VISIBLE);
							txtAutoSubmission.setVisibility(View.VISIBLE);

						}else{
							arryMaintainance = new ArrayList<MaintainanceDTO>();
							try {
								arryMaintainance = CommanService.getMaintainanceList(getActivity().getApplicationContext());
								totalMaintainaceRecords = arryMaintainance.size();
							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								Log.d(Constants.DEBUG,":Synchronize:btnSync:onClick:"+ Utility.convertExceptionToString(e1));
							}
							btnSync.setEnabled(false);
							//JSONArray batchRequest = CommanService.batchSubmit(getActivity());
							AsyncBatchCreation asyncBatchCreation = new AsyncBatchCreation(getActivity(), onResponseBatchCreation,"Fragment",arryMaintainance,startIndex,lastIndex);
							asyncBatchCreation.execute(null,null);
						}

					}catch (Exception e) {
						e.printStackTrace();
						Global.isManualSubmitRunning = false;
						insertExceptionLog(e.getMessage());
						Log.d(Constants.DEBUG,":Synchronize:btnSync:onClick:"+ Utility.convertExceptionToString(e));
					}
				}else{
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
				}
			}
		});
		return syncView;
	}

	protected OnResponseListenerBatchCreation onResponseBatchCreation = new OnResponseListenerBatchCreation() {

		public void onSuccess(JSONArray batchDetailRequest) {

			Log.d("Response", "******"+batchDetailRequest);
			LogMasterService logMasterService = new LogMasterService(getActivity());

			JSONArray batchRequest = batchDetailRequest;
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(Constants.JSON_LOGIN_USERNAME, SecurityManager.encrypt(SharedPrefrenceUtil.getPrefrence(getActivity(), Constants.DB_USERNAME, Constants.LABEL_BLANK),Constants.SALT));
			param.put(Constants.JSON_LOGIN_PASSWORD, SecurityManager.encrypt(SharedPrefrenceUtil.getPrefrence(getActivity(), Constants.DB_PASSWORD, Constants.LABEL_BLANK),Constants.SALT));
			param.put(Constants.JSON_LOGIN_DEVICEID, SecurityManager.encrypt(Constants.LABLE_DEVICE_ID,Constants.SALT));//Utility.getDeviceId(getActivity()));
			param.put(Constants.JSON_MAINTENANCE_FORM_DATA,SecurityManager.encrypt(batchRequest.toString(),Constants.SALT));
			totalRecords = batchRequest.length();


			ContentValues contentValues = new ContentValues();
			contentValues.put(Constants.LOG_FIELDS[4], totalRecords);
			contentValues.put(Constants.LOG_FIELDS[1], getResources().getString(R.string.status_object_being_created));
			String where = Constants.LOG_FIELDS[0] + "=" + Global.currentDateTime;
			logMasterService.updateLog(contentValues, where);

//			Toast.makeText(getActivity().getApplicationContext(),"Batch Total" + totalRecords,Toast.LENGTH_LONG).show();
//			int norecords = maintainanceService.getCompletedRecordCount();
			if(Utility.isNetAvailable(getActivity())){
				try{

					if(batchRequest.length() > 0 && !Global.isCycleRunning)
					{
						AsyncBatchSubmit asyncBatchSubmit= new AsyncBatchSubmit(getActivity(),onResponseListener,"Fragment");
						asyncBatchSubmit.execute(param);
					}else{
						totalCompletedRecords = 0;
						totalSuccesfulRecords = 0;
						totalUnSuccessfulRecords = 0;
						startIndex = 0;
						lastIndex = batchsize;
						Global.isManualSubmitRunning = false;
						btnSync.setEnabled(true);
						Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_GENERAL_INFO,Constants.NO_BATCH_SUBMIT);
					}

//					else if(norecords > 0 && offset < totalCompletedRecords){
//						Toast.makeText(getActivity().getApplicationContext(),"Batch record not found",Toast.LENGTH_LONG).show();
//						btnSync.setEnabled(false);
//						Global.isManualSubmitRunning = true;
//						offset+=50;
//						//JSONArray batchRequest = CommanService.batchSubmit(getActivity());
//						AsyncBatchCreation asyncBatchCreation = new AsyncBatchCreation(getActivity(), onResponseBatchCreation,"Fragment",offset);
//						asyncBatchCreation.execute(null,null);
//				    }
				}catch (Exception e) {
					e.printStackTrace();
					Global.isManualSubmitRunning = false;
					insertExceptionLog(e.getMessage());
					btnSync.setEnabled(true);
					Log.d(Constants.DEBUG,":onResponseBatchCreation:"+ Utility.convertExceptionToString(e));
				}
			}else{
				Global.isManualSubmitRunning = false;
				Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
			}

		}
		public void onFailure(String message) {
			Global.isCycleRunning = false;
		}
	};

	protected OnResponseListener onResponseListener = new OnResponseListener() {

		public void onSuccess(JSONObject response) {
			try{

				Log.d("Response", "******"+response);
				String rsp = null;

				if(response.has(Constants.JSON_FAILED)){
					rsp = response.getString(Constants.JSON_FAILED);
					Log.d("AsyncConfirmationSyncData", "JSON Responce :::: Authentication :::"+response.getString(Constants.METHOD_AUTHENTICATE));
					Global.isManualSubmitRunning = false;
				}


//				if(response.getString(Constants.JSON_FAILED).equals(Constants.JSON_FAILED)){
//					//createOfflineDialog(LoginActivity.this,LoginActivity.this);
//					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR, Constants.ERROR_JSON_FAILED);
				if(rsp != null && rsp.equals(Constants.JSON_FAILED)){
					//createOfflineDialog(LoginActivity.this,LoginActivity.this);
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR, Constants.ERROR_JSON_FAILED);
					logException();
				}else if(response.has(Constants.NETWORK_EXCEPTION_METHOD)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.NETWORK_EXCEPTION_METHOD);
					logException();
					Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
				}else if(response.has(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.CONNECTION_REFUSED_EXCEPTION_METHOD);
					logException();
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(response.has(Constants.SOCKET_EXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.SOCKET_EXCEPTION);
					logException();
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(response.has(Constants.IOEXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.IOEXCEPTION);
					logException();
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(response.has(Constants.XMLPULLPARSEREXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.XMLPULLPARSEREXCEPTION);
					logException();
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(response.has(Constants.EXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.EXCEPTION);
					logException();
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else{

					successMFormID = response.getJSONArray("SuccessMFormID");
					if(successMFormID != null){
						Log.e("JSON",  "DateTime:-"+Utility.getcurrentTimeStamp()+":::::::::Sync Fragment successMFormID ::: "+successMFormID);
						Log.d("JSON",  "DateTime:-"+Utility.getcurrentTimeStamp()+"::::::::: successMFormID.length ::: "+successMFormID.length());
						Log.d("JSON",  "DateTime:-"+Utility.getcurrentTimeStamp()+"::::::::: successMFormID.toString ::: "+successMFormID.toString());
						sucessfulRecords = successMFormID.length();
						unsucessfulRecords = totalRecords - sucessfulRecords;
						totalSuccesfulRecords += sucessfulRecords;
						totalUnSuccessfulRecords += unsucessfulRecords;

						ContentValues cvSub=new ContentValues();
						cvSub.put(Constants.DB_TOT_RECORDS,totalCompletedRecords);
						cvSub.put(Constants.DB_SUCCESS_RECORDS,totalSuccesfulRecords);
						cvSub.put(Constants.DB_UNSUCCESS_RECORDS,totalUnSuccessfulRecords);
						cvSub.put(Constants.DB_CREATEDBY,SharedPrefrenceUtil.getPrefrence(getActivity(), Constants.DB_USER_ID, Constants.LABEL_BLANK));
						cvSub.put(Constants.DB_CREATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));


						LogMasterService logMasterService = new LogMasterService(getActivity());
						ContentValues contentValues = new ContentValues();
						contentValues.put(Constants.LOG_FIELDS[5], sucessfulRecords);
						contentValues.put(Constants.LOG_FIELDS[2], getResources().getString(R.string.status_submit_response));
						String where = Constants.LOG_FIELDS[0] + " = " + Global.currentDateTime;
						logMasterService.updateLog(contentValues, where);



						if(maintainanceService.insertMaintainance(Constants.TBL_MST_BATCH_SUBMIT, cvSub) != -1)
							Log.e("SYNC FRAGMENT", "DateTime:-"+Utility.getcurrentTimeStamp()+"BatchSubmit Done ,When Data Successful");

						lastBatchVO = maintainanceService.getLastBatchSubmit();

						if(lastBatchVO.getBatchsubmitid() > 0)
						{
							txtLstSubDt.setText(Utility.getConvertedDateTime(getActivity(),lastBatchVO.getCreatedOn()));
							txtTotRec.setText(lastBatchVO.getTotRecords()+"");
							txtSuccRec.setText(lastBatchVO.getSuccessfulRecords()+"");
							txtUnsuccRec.setText(lastBatchVO.getUnsuccessfulRecords()+"");
						}

						if(maintainanceService != null){
							totalAllCompleteRecords = maintainanceService.getTotalCompletedRecordCount();
							totalSyncRecords = maintainanceService.getTotalSyncRecordCount();

							txtTotalCompletedRecords.setText(String.valueOf(totalAllCompleteRecords));
							txtTotalSyncRecord.setText(String.valueOf(totalSuccesfulRecords));
						}
//						Toast.makeText(getActivity(), "Success Full Records :"+ sucessfulRecords,Toast.LENGTH_LONG).show();
//						Toast.makeText(getActivity(), "Unuccess Full Records :"+ unsucessfulRecords,Toast.LENGTH_LONG).show();
//
//						Toast.makeText(getActivity(), "Total Success Full Records :"+ totalSuccesfulRecords,Toast.LENGTH_LONG).show();
//						Toast.makeText(getActivity(), "Total Unuccess Full Records :"+ totalUnSuccessfulRecords,Toast.LENGTH_LONG).show();

						if((response.getString(Constants.METHOD_AUTHENTICATE).equalsIgnoreCase("True"))&& successMFormID.length()>0){
							//					Log.e("SyncFragment ", "##########################################"+CommanService.batchSubmit(getActivity()));

							int maintainanceId = 0;
							int testingID = 0;
							if(maintainanceService != null){

								for(int i=0;i<successMFormID.length();i++){


									maintainanceId =  maintainanceService.getMaintainanceId(successMFormID.getInt(i));
									if(!SecurityManager.decrypt(maintainanceService.getMaintainanceStatus(successMFormID.getInt(i)), Constants.SALT).equalsIgnoreCase("HCL1")) {
										System.out.println("MaintainanceOrderId :::::::::::" + successMFormID.getInt(i));
										System.out.println("MaintainanceId ::::::::::" + maintainanceId);


										String deleteCust = "maintainanceorderid = " + successMFormID.getInt(i);
										String deleteWhere = "maintainanceid = " + maintainanceId;
										//long resutl = maintainanceService.deleteMaintainance(Constants.TBL_DTL_CLAMPING,deleteWhere);
										testingID = maintainanceService.getTestingId(deleteWhere);
										String delete = "testingid = " + testingID;
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_MAINTAINANCE, deleteWhere);

										maintainanceService.deleteMaintainance(Constants.TBL_DTL_TESTING, deleteWhere);
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_LEAKAGE, delete);
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_GIFITTING, delete);

										maintainanceService.deleteMaintainance(Constants.TBL_DTL_CLAMPING, deleteWhere);
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_PAINTINGORING, deleteWhere);
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_RCC, deleteWhere);
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_SURAKSHA_TUBE, deleteWhere);
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_MAKE_AND_GEYSER, deleteWhere);
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_OTHER_CHECKS, deleteWhere);
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_CONFORMANCE, deleteWhere);
										maintainanceService.deleteMaintainance(Constants.TBL_DTL_CUSTOMER_FEEDBACK, deleteWhere);
										maintainanceService.deleteMaintainance(Constants.TBL_MST_CUSTOMER, deleteCust);
									} else {
										String deleteWhere = "maintainanceid = " + maintainanceId;
										ContentValues cv = new ContentValues();
										cv.put(Constants.MAINTAINANCE_FIELDS[6], 1);
										maintainanceService.updateMaintainance(Constants.TBL_DTL_MAINTAINANCE, cv, deleteWhere);
									}
//								String msg = null;
//								System.out.println(msg);
//								String wheres = "maintainanceorderid = "+successMFormID.getInt(i);
//								ContentValues cv=new ContentValues();
//								cv.put(Constants.MAINTAINANCE_FIELDS[6],"1");
//								cv.put(Constants.MAINTAINANCE_FIELDS[9],SharedPrefrenceUtil.getPrefrence(getActivity(), Constants.DB_USER_ID, Constants.LABEL_BLANK));
//								cv.put(Constants.MAINTAINANCE_FIELDS[10], SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
//
//								if(maintainanceService.updateMaintainance(Constants.TBL_DTL_MAINTAINANCE,cv, wheres) != -1)
//								{
//									Log.e("SYNC FRAG", "DateTime:-"+Utility.getcurrentTimeStamp()+ "::::::::: Upadte issync = 1 for successMFormID ::: "+successMFormID.getInt(i));
//
//									ContentValues cvCust=new ContentValues();
//									cvCust.put(Constants.CUSTOMER_MASTER_FIELDS[5],"1");
//									cvCust.put(Constants.CUSTOMER_MASTER_FIELDS[8],SharedPrefrenceUtil.getPrefrence(getActivity(), Constants.DB_USER_ID, Constants.LABEL_BLANK));
//									cvCust.put(Constants.CUSTOMER_MASTER_FIELDS[9],SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
//
//									if(maintainanceService.updateMaintainance(Constants.TBL_MST_CUSTOMER,cvCust, wheres) != -1)
//									{
//										Log.e("SYNC FRAG Cust Sync", "DateTime:-"+Utility.getcurrentTimeStamp()+ "::::::::: Upadte issync = 1 for Customer table successMFormID ::: "+successMFormID.getInt(i));
//									}
//								}
								}

							}
//							completedRecords = maintainanceService.getCompletedRecordCount();
							if(lastIndex < totalMaintainaceRecords){
//								Toast.makeText(getActivity(), "Remaining Records  to submit:"+ completedRecords,Toast.LENGTH_LONG).show();
								if(Utility.isNetAvailable(getActivity())){
									try{

										if(Global.isCycleRunning)
										{
											btnSync.setEnabled(false);
											btnSync.setVisibility(View.GONE);
											btnAutoSub.setVisibility(View.VISIBLE);
											txtAutoSubmission.setVisibility(View.VISIBLE);
											Global.isManualSubmitRunning = false;
										}else{
											btnSync.setEnabled(false);
											//JSONArray batchRequest = CommanService.batchSubmit(getActivity());
											startIndex = lastIndex;
											lastIndex += batchsize;
											Toast.makeText(getActivity().getApplicationContext(),"Customer Remaining to submit :" + (totalMaintainaceRecords - startIndex), Toast.LENGTH_LONG).show();
											AsyncBatchCreation asyncBatchCreation = new AsyncBatchCreation(getActivity(), onResponseBatchCreation,"Fragment",arryMaintainance,startIndex,lastIndex);
											asyncBatchCreation.execute(null,null);
										}

									}catch (Exception e) {
										e.printStackTrace();
										Global.isManualSubmitRunning = false;
										btnSync.setEnabled(true);
										Log.d(Constants.DEBUG,":SynchronizeFragment:onResponseLinstner:"+ Utility.convertExceptionToString(e));
									}
								}else{
									btnSync.setEnabled(true);
									startIndex = 0;
									lastIndex = batchsize;
									Global.isManualSubmitRunning = false;
									totalCompletedRecords = 0;
									totalSuccesfulRecords = 0;
									totalUnSuccessfulRecords = 0;
									Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);

								}
							}else{
								//Insert to Batch Submit Table
								btnSync.setEnabled(true);
								Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_GENERAL_INFO,Constants.BATCH_SUBMIT_SUCESSFUL);

								if(maintainanceService != null){
									totalAllCompleteRecords = maintainanceService.getTotalCompletedRecordCount();
									totalSyncRecords = maintainanceService.getTotalSyncRecordCount();

									txtTotalCompletedRecords.setText(String.valueOf(totalAllCompleteRecords));
									txtTotalSyncRecord.setText(String.valueOf(totalSuccesfulRecords));
								}
								Global.isManualSubmitRunning = false;

								totalCompletedRecords = 0;
								totalSuccesfulRecords = 0;
								totalUnSuccessfulRecords = 0;
								startIndex = 0;
								lastIndex = batchsize;
							}

						}else{

							if(lastIndex < totalMaintainaceRecords){
//								Toast.makeText(getActivity(), "Remaining Records  to submit:"+ completedRecords,Toast.LENGTH_LONG).show();
								if(Utility.isNetAvailable(getActivity())){
									try{

										if(Global.isCycleRunning)
										{
											btnSync.setEnabled(false);
											btnSync.setVisibility(View.GONE);
											btnAutoSub.setVisibility(View.VISIBLE);
											txtAutoSubmission.setVisibility(View.VISIBLE);
											Global.isManualSubmitRunning = false;
										}else{
											btnSync.setEnabled(false);
											//JSONArray batchRequest = CommanService.batchSubmit(getActivity());
											startIndex = lastIndex;
											lastIndex += batchsize;
											Toast.makeText(getActivity().getApplicationContext(),"Customer Remaining to submit :" + (totalMaintainaceRecords - startIndex), Toast.LENGTH_LONG).show();
											AsyncBatchCreation asyncBatchCreation = new AsyncBatchCreation(getActivity(), onResponseBatchCreation,"Fragment",arryMaintainance,startIndex,lastIndex);
											asyncBatchCreation.execute(null,null);
										}

									}catch (Exception e) {
										e.printStackTrace();
										Global.isManualSubmitRunning = false;
										btnSync.setEnabled(true);
										Log.d(Constants.DEBUG,":SynchronizeFragment:onResponseLinstner:"+ Utility.convertExceptionToString(e));
									}
								}else{
									startIndex = 0;
									lastIndex = batchsize;
									Global.isManualSubmitRunning = false;
									totalCompletedRecords = 0;
									totalSuccesfulRecords = 0;
									totalUnSuccessfulRecords = 0;
									btnSync.setEnabled(true);
									Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
								}
							}else{
								//Insert to Batch Submit Table
//								Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_GENERAL_INFO,Constants.BATCH_SUBMIT_SUCESSFUL);
								Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_GENERAL_INFO,Constants.BATCH_SUBMIT_UNSUCESSFUL);
								btnSync.setEnabled(true);
								Global.isManualSubmitRunning = false;

								totalCompletedRecords = 0;
								totalSuccesfulRecords = 0;
								totalUnSuccessfulRecords = 0;
								startIndex = 0;
								lastIndex = batchsize;
							}






//							Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_GENERAL_INFO,Constants.BATCH_SUBMIT_UNSUCESSFUL);
//							
//							Global.isManualSubmitRunning = false;
//							totalCompletedRecords = 0;
//							totalSuccesfulRecords = 0;
//							totalUnSuccessfulRecords = 0;
//							startIndex = 0;
//							lastIndex = batchsize;
						}


					}else{

						LogMasterService logMasterService = new LogMasterService(getActivity());
						ContentValues contentValues = new ContentValues();
						contentValues.put(Constants.LOG_FIELDS[5],0);
						contentValues.put(Constants.LOG_FIELDS[6],0);
						contentValues.put(Constants.LOG_FIELDS[2], getResources().getString(R.string.status_submit_failed));
						String where = Constants.LOG_FIELDS[0] + " = " + Global.currentDateTime;
						logMasterService.updateLog(contentValues, where);



					}

				}
			}catch (Exception e) {
				totalCompletedRecords = 0;
				totalSuccesfulRecords = 0;
				totalUnSuccessfulRecords = 0;
				startIndex = 0;
				lastIndex = batchsize;
				Global.isManualSubmitRunning = false;
				Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_GENERAL_INFO,Constants.ERROR_IN_SUBMISSION);
				insertExceptionLog(e.getMessage());
				btnSync.setEnabled(true);
				Log.d(Constants.DEBUG,":SynchronizeFragment:onResponseLinstner:"+ Utility.convertExceptionToString(e));
			}
		}

		public void onFailure(String message) {
			Global.isManualSubmitRunning = false;
			totalCompletedRecords = 0;
			totalSuccesfulRecords = 0;
			totalUnSuccessfulRecords = 0;
			startIndex = 0;
			lastIndex = batchsize;
			Global.isManualSubmitRunning = false;
			Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_GENERAL_INFO,Constants.ERROR_IN_SUBMISSION);
			insertExceptionLog(message);
			btnSync.setEnabled(true);

		}
	};
	public void changeLanguage(int id)
	{
		id = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.PREF_LANGUAGE,Constants.LANGUAGE_ENGLISH);
		switch (id)
		{
			case Constants.LANGUAGE_ENGLISH:
				texSync.setText(getResources().getText(R.string.synchro_text_Eng));
				lblLstSubDt.setText(getResources().getText(R.string.sync_auto_submssion_lstdt_Eng));
				lblTotRec.setText(getResources().getText(R.string.sync_auto_submssion_totrec_Eng));
				lblSuccRec.setText(getResources().getText(R.string.sync_auto_submssion_sucessrec_Eng));
				lblUnsuccRec.setText(getResources().getText(R.string.sync_auto_submssion_unsucessrec_Eng));
				lblTotalCompletedRec.setText(getResources().getText(R.string.sync_auto_submssion_total_record_Eng));
				lblTotalSyncRec.setText(getResources().getText(R.string.sync_auto_submssion_total_sync_Eng));
				break;
			case Constants.LANGUAGE_GUJRATI:
				texSync.setTypeface(Global.typeface_Guj);
				lblLstSubDt.setTypeface(Global.typeface_Guj);
				lblTotRec.setTypeface(Global.typeface_Guj);
				lblSuccRec.setTypeface(Global.typeface_Guj);
				lblUnsuccRec.setTypeface(Global.typeface_Guj);
				lblTotalCompletedRec.setTypeface(Global.typeface_Guj);
				lblTotalSyncRec.setTypeface(Global.typeface_Guj);
				texSync.setText(getResources().getText(R.string.synchro_text_Guj));
				lblLstSubDt.setText(getResources().getText(R.string.sync_auto_submssion_lstdt_Guj));
				lblTotRec.setText(getResources().getText(R.string.sync_auto_submssion_totrec_Guj));
				lblSuccRec.setText(getResources().getText(R.string.sync_auto_submssion_sucessrec_Guj));
				lblUnsuccRec.setText(getResources().getText(R.string.sync_auto_submssion_unsucessrec_Guj));
				lblTotalCompletedRec.setText(getResources().getText(R.string.sync_auto_submssion_total_record_Guj));
				lblTotalSyncRec.setText(getResources().getText(R.string.sync_auto_submssion_total_sync_Guj));
				break;
			default:
				break;
		}
	}

	private void insertExceptionLog(String msgException){
		if(msgException != null){
			LogMasterService logMasterService = new LogMasterService(getActivity());
			ContentValues values = new ContentValues();
			values.put(Constants.LOG_FIELDS[8],msgException);
			String where = Constants.LOG_FIELDS[0] + "= " + Global.currentDateTime;
			logMasterService.updateLog(values,where);
		}
	}

/*	protected OnResponseListenerConfirmation onResponseListenerConfirmation = new OnResponseListenerConfirmation() {

		public void onSuccess(JSONObject responce) {
			try{
				if(responce.has(Constants.METHOD_AUTHENTICATE) && responce.has("Synchronization")){
					if(responce.getString(Constants.METHOD_AUTHENTICATE).equalsIgnoreCase("True") && responce.getString("Synchronization").equalsIgnoreCase("True")){

					}
				}

			}catch (Exception e) {
			}
		}

		public void onFailure(String message) {
		}
	};*/

	private void logException(){
		LogMasterService logMasterService = new LogMasterService(getActivity());
		ContentValues contentValues = new ContentValues();
		contentValues.put(Constants.LOG_FIELDS[5], 0);
		contentValues.put(Constants.LOG_FIELDS[2], getResources().getString(R.string.status_submit_failed));
		String where = Constants.LOG_FIELDS[0] + " = " + Global.currentDateTime;
		logMasterService.updateLog(contentValues, where);
		btnSync.setEnabled(true);
		totalCompletedRecords = 0;
		totalSuccesfulRecords = 0;
		totalUnSuccessfulRecords = 0;
		startIndex = 0;
		lastIndex = batchsize;
		Global.isManualSubmitRunning = false;
	}
}
