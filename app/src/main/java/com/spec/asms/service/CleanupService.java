package com.spec.asms.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.spec.asms.common.Constants;
import com.spec.asms.common.Global;
import com.spec.asms.common.utils.ICleanUpResponseListener;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.dto.MaintainanceDTO;
import com.spec.asms.vo.CleanupVO;
import com.spec.asms.vo.MaintainanceVO;
import com.spec.asms.vo.UserMasterVO;

public class CleanupService extends AsyncTask<Void,Void,Boolean>{

	private List<MaintainanceDTO> listMaintainance;
	private Context context; 
	private ProgressDialog prDialog = null;
	private int UserID;
	public int days = 0;
	public String msg;
	private UserMasterService userService;
	private MaintainanceService maintainanceService;
	SimpleDateFormat simpleFormatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
	private ICleanUpResponseListener iCleanUpResponseListener;
	public CleanupService(Context c,ICleanUpResponseListener iCleanUpResponseListener) {
		context = c;
		UserID = Integer.parseInt(SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USER_ID, null));
		userService = new UserMasterService(c);
		maintainanceService = new MaintainanceService(c);
		this.iCleanUpResponseListener = iCleanUpResponseListener;

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		try {
			prDialog = new ProgressDialog(context);
			prDialog.setMessage(Constants.LABEL_PROGRESS_CLEANUP);
			prDialog.setCancelable(false);
			prDialog.show();
			prDialog.setProgress(10);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	@Override
	protected Boolean doInBackground(Void... params) {

		boolean cleared = false;
		int testingID = 0;
		try{
			if(UserID > 0){

				UserMasterVO userVO = userService.getUserInfo(Constants.TBL_MST_USER,Constants.USER_MASTER_FIELDS,"userid ="+UserID);

//				String where = "issync = 1 and updatedby = "+UserID; // Remove udpdatedby after discussion 
				
				String where = "issync = 1";
				
				listMaintainance = maintainanceService.getMaintainaceDTO(Constants.TBL_DTL_MAINTAINANCE, Constants.MAINTAINANCE_FIELDS,where);

				if(listMaintainance != null && listMaintainance.size() > 0){
					for(int i = 0 ; i < listMaintainance.size() ; i++){
//						String d = listMaintainance.get(i).getUpdatedOn();
//						Date dt = simpleFormatter.parse(d);
						if(last30days(Long.parseLong(listMaintainance.get(i).getStartTime()))){ //userVO.getCreatedOn()

							cleared = true;
							Log.e("MORDERID FROM CLEANUP:-", "DateTime:-"+Utility.getcurrentTimeStamp()+""+listMaintainance.get(i).getMaintainanceId());
							String deleteCust = "issync = 1 and maintainanceorderid = "+listMaintainance.get(i).getMaintainanceOrderId();
							String deleteWhere = "maintainanceid ="+listMaintainance.get(i).getMaintainanceId();
							//long resutl = maintainanceService.deleteMaintainance(Constants.TBL_DTL_CLAMPING,deleteWhere);
							testingID = maintainanceService.getTestingId(deleteWhere);
							String delete = "testingid ="+testingID;
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

							maintainanceService.deleteMaintainance(Constants.TBL_MST_CUSTOMER,deleteCust);

							//if(resutl >= 1)
							//	cleared = true;
							//else
							//	cleared = false;

						}else{
							cleared = false;
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":CleanupService:"+ Utility.convertExceptionToString(e));
		}
		return cleared;
	}

	@Override
	protected void onPostExecute(Boolean result) {		
		super.onPostExecute(result);

		prDialog.dismiss();
		if(result){
			Utility.alertDialog(context, Constants.ALERT_TITLE_GENERAL_INFO,Constants.LABEL_ALERT_CLEAR_SUCCESS);
		    iCleanUpResponseListener.onSuccess();
		}else{
			if(days > 0)
			{
				 msg ="Cleaning Failed , There is no synced data or data is not "+ days +" days old!";
			}else{
				 msg ="Cleaning Failed , There is no synced data!"; }
			Utility.alertDialog(context, Constants.ALERT_TITLE_GENERAL_INFO,msg);
			Global.isFilterRequired = false;
		}
	}

	private boolean last30days(long createdOn){

		//Current date
		Date currentDate = new Date(System.currentTimeMillis());

		//Servertimestamp
		Date selectedDate = new Date(createdOn);

		CleanupVO cleanupVO = userService.getCleanupPeriod();

		if(!cleanupVO.getCleanUpPeriod().equals(Constants.LABEL_BLANK))
		{
			days = Integer.parseInt(cleanupVO.getCleanUpPeriod());
//			days = 0;
			//Last 30 days Date
			final Calendar cal = Calendar.getInstance();
			cal.setTime(selectedDate);
			cal.roll(Calendar.DAY_OF_YEAR, +days);
			Date daysAfterDate = cal.getTime();
			Log.d("CleanUpService", "cleanupPeriod :: "+days);
			Log.d("CleanUpService", "cleanupDate :: "+cleanupVO.getLastCleanupDate());
			Log.d("CleanUpService", "cleanupPeriod from Db :: "+cleanupVO.getCleanUpPeriod());
			Log.d("CleanUpService", "currentDate :: "+currentDate);
			Log.d("CleanUpService", "selectedDate :: "+selectedDate);
			Log.d("CleanUpService", "daysAfterDate :: "+daysAfterDate);

			if(currentDate.after(daysAfterDate))
				return true;
			else
				return false;
		}else{
			return false;
		}


		/*//Current date
		Date currentDate = new Date(System.currentTimeMillis());

		//Last 30 days Date
		final Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.roll(Calendar.DAY_OF_YEAR, -30);
		Date daysBeforeDate = cal.getTime();

		//Last 30 days Date
		final Calendar futureCal = Calendar.getInstance();
		futureCal.setTime(new Date());
		futureCal.roll(Calendar.DAY_OF_YEAR,+1);
		Date daysAfterDate = futureCal.getTime();

		//Servertimestamp
		Date selectedDate = new Date(updateOn);

		if(currentDate.compareTo(selectedDate) * selectedDate.compareTo(daysBeforeDate) > 0)
		{
			return true;
		}else
			return false;*/
	}
}