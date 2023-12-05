package com.spec.asms.service.async;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Global;
import com.spec.asms.common.utils.SecurityManager;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.CustomerMasterService;
import com.spec.asms.service.MaintainanceService;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.view.ViewAssignmentActivity;
import com.spec.asms.view.fragment.ConformanceFragment;
import com.spec.asms.view.fragment.MaintanenceForm;
import com.spec.asms.view.fragment.TestingFragment;
import com.spec.asms.vo.ClampingVO;
import com.spec.asms.vo.ConformanceDetailVO;
import com.spec.asms.vo.CustomerFeedbackVO;
import com.spec.asms.vo.GIFittingVO;
import com.spec.asms.vo.KitchenSurakshaTubeVO;
import com.spec.asms.vo.LeakageMasterVO;
import com.spec.asms.vo.LeakageVO;
import com.spec.asms.vo.MaintainanceVO;
import com.spec.asms.vo.MakeGeyserVO;
import com.spec.asms.vo.OtherChecksVO;
import com.spec.asms.vo.OtherKitchenSurakshaTubeVO;
import com.spec.asms.vo.PaintingVO;
import com.spec.asms.vo.RccVO;
import com.spec.asms.vo.TestingVO;

public class AsyncFormSubmit extends AsyncTask<Void,Void,Boolean>{

	private Context context;
	private ProgressDialog prDialog = null;
	public int insertMaintainanceID,insertTestingID;
	public boolean isFromUpdate;
	public static boolean isInsert = false;
	public CustomerMasterService custService;
	public MaintainanceService maintainService;
	public UserMasterService userService;
	private FragmentManager fragmentManager;
	public int userID;
	public SharedPreferences pref;

	public AsyncFormSubmit(Context context,FragmentManager fragManager) {
		this.context = context;
		this.prDialog = new ProgressDialog(context);
		this.fragmentManager = fragManager;
		maintainService = new MaintainanceService(context);
		userService = new UserMasterService(context);
		custService = new CustomerMasterService(context);
		userID = Integer.parseInt(SharedPrefrenceUtil.getPrefrence(context,Constants.DB_USER_ID,Constants.LABEL_BLANK));
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		try {
			prDialog = new ProgressDialog(context);
			prDialog.setMessage(context.getResources().getString(R.string.progress_submit));
			prDialog.setCancelable(false);
			prDialog.show();
			prDialog.setProgress(10);
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncFormSubmit:"+ Utility.convertExceptionToString(ex));
		}


	}

	@Override
	protected Boolean doInBackground(Void... arg0) {

//		ArrayList<Boolean> tempValidatelist = new ArrayList<Boolean>(MaintanenceForm.validateList);
//		Collections.copy(tempValidatelist,MaintanenceForm.validateList);

//		Collections.sort(MaintanenceForm.validateList);
//		Log.e("abc",Collections.binarySearch(MaintanenceForm.validateList, true)+"");

		boolean result = false;
		if(MaintainanceVO.getStatusCode().equals("COMP")){ //CT

//			Collections.sort(tempValidatelist);
//			if(Collections.binarySearch(tempValidatelist, true) >= -1){
//				//unsort the list
//				result = false;
//			}else{
			SaveAllDetailsToDB();
			result = isInsert;
//			}	
		}else if(MaintainanceVO.getStatusCode().equals("HCL1") || MaintainanceVO.getStatusCode().equals("HCL2") || MaintainanceVO.getStatusCode().equals("TEMP") || MaintainanceVO.getStatusCode().equals("NOPR") || MaintainanceVO.getStatusCode().equals("REFL") ){
			setDefaultValuesOfVO();
			SaveAllDetailsToDB();
			result = isInsert;
		}else{
			SaveAllDetailsToDB();
			result = isInsert;
		}

		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {

		super.onPostExecute(result);

		prDialog.dismiss();

		if(result){
			// Update By Pankit Mistry
			String where = "(statusid = "+userService.getStatusId("OP") +  " or statusid = "+userService.getStatusId("HCL1") +")   and customerid = '" +MaintainanceVO.getCustomerId()+"'";
			ContentValues cv = new ContentValues();

			Log.e("FORM SUBMIT", "DateTime:-"+Utility.getcurrentTimeStamp()+"STATUS ID UPDATE"+MaintainanceVO.getStatusId());
			cv.put(Constants.CUSTOMER_MASTER_FIELDS[3],MaintainanceVO.getStatusId());
			cv.put(Constants.CUSTOMER_MASTER_FIELDS[16],MaintainanceVO.getStatusCode());

			cv.put(Constants.DB_UPDATEDBY,SharedPrefrenceUtil.getPrefrence(context,Constants.DB_USER_ID, null));
			cv.put(Constants.DB_UPDATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));

			if(custService.updateCustomerByID(Constants.TBL_MST_CUSTOMER,cv, where) != -1)
			{
				Log.e("Form Submit:>>>>", "DateTime:-"+Utility.getcurrentTimeStamp()+"Customer"+where +" Update with status id "+MaintainanceVO.getStatusId());
			}

			TestingVO.cleanTestingVO();
			GIFittingVO.cleanGiFitting();
			ClampingVO.cleanClampingVO();
			PaintingVO.cleanPaintingVO();
			LeakageVO.cleanLeakage();
			RccVO.cleanRCCVO();
			MakeGeyserVO.cleanMakeGeyser();
			KitchenSurakshaTubeVO.cleanKitchenSurakshaTubeVO();
			OtherKitchenSurakshaTubeVO.cleanOtherKitchenSurakshaTubeVO();
			OtherChecksVO.cleanOtherChecks();
			ConformanceDetailVO.cleanConformanceVO();
			CustomerFeedbackVO.cleanomerFeedbackCustVO();

			Global.isFormSubmit = true;
			ViewAssignmentActivity newFragment = new ViewAssignmentActivity();
			FragmentManager fragManager = fragmentManager;
			FragmentTransaction ft = fragManager.beginTransaction();
			ft.replace(R.id.mainFrg, newFragment);
			ft.commit();

		}else
		{
			Utility.alertDialog(context,"Submit - Error","There are few errors on forms!");
		}
		MaintanenceForm.sideListAdapter.notifyDataSetChanged();
	}

	/**
	 * Save all currentVO to Database
	 *
	 * @author jenisha
	 */
	private void SaveAllDetailsToDB(){
		try{

			ContentValues maintananceCV = new ContentValues();
			maintananceCV.put(Constants.MAINTAINANCE_FIELDS[1],MaintainanceVO.getMaintainanceOrderId());
			maintananceCV.put(Constants.MAINTAINANCE_FIELDS[2], SecurityManager.encrypt(MaintainanceVO.getCustomerId(),Constants.SALT));
			System.out.println("INSERT DATE" + MaintainanceVO.getDate());
			maintananceCV.put(Constants.MAINTAINANCE_FIELDS[3],SecurityManager.encrypt(MaintainanceVO.getDate(),Constants.SALT));
			maintananceCV.put(Constants.MAINTAINANCE_FIELDS[4],SecurityManager.encrypt(MaintainanceVO.getTime(),Constants.SALT));
			maintananceCV.put(Constants.MAINTAINANCE_FIELDS[5],SecurityManager.encrypt(MaintainanceVO.getStatusCode(),Constants.SALT));
			maintananceCV.put(Constants.MAINTAINANCE_FIELDS[6],0);
			maintananceCV.put(Constants.MAINTAINANCE_FIELDS[7],userID);
			maintananceCV.put(Constants.MAINTAINANCE_FIELDS[8],SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
			maintananceCV.put(Constants.MAINTAINANCE_FIELDS[9],userID);
			maintananceCV.put(Constants.MAINTAINANCE_FIELDS[10],SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
			maintananceCV.put(Constants.MAINTAINANCE_FIELDS[11],SecurityManager.encrypt(String.valueOf(SharedPrefrenceUtil.getPrefrence(context, Constants.PREF_START_TIME,Long.valueOf(0))),Constants.SALT));
			maintananceCV.put(Constants.MAINTAINANCE_FIELDS[12],SecurityManager.encrypt(String.valueOf(SharedPrefrenceUtil.getPrefrence(context, Constants.PREF_END_TIME,Long.valueOf(0))),Constants.SALT));

			// Added by Pankit Mistry
			maintananceCV.put(Constants.MAINTAINANCE_FIELDS[13],
					SecurityManager.encrypt(SharedPrefrenceUtil.getPrefrence(context, MaintainanceVO.getCustomerId()+"c", ""), Constants.SALT));
			maintananceCV.put(Constants.MAINTAINANCE_FIELDS[14],
					SecurityManager.encrypt(SharedPrefrenceUtil.getPrefrence(context, MaintainanceVO.getCustomerId()+"a", ""), Constants.SALT));
			String v0 = SecurityManager.decrypt(maintainService.getMaintainanceStatus(MaintainanceVO.getMaintainanceOrderId()), Constants.SALT);
			if(v0 != null && v0.equalsIgnoreCase("HCL1")) {
			/*if(MaintainanceVO.getStatusCode().equalsIgnoreCase("HCL2")) {*/
				isFromUpdate = true;
				String updateWhere = "maintainanceorderid =" + MaintainanceVO.getMaintainanceOrderId();
				maintainService.updateMaintainance(Constants.TBL_DTL_MAINTAINANCE, maintananceCV, updateWhere);
				insertMaintainanceID = maintainService.getMaintainanceId(MaintainanceVO.getMaintainanceOrderId());
			} else {
				insertMaintainanceID = (int) maintainService.insertMaintainance(Constants.TBL_DTL_MAINTAINANCE, maintananceCV);
			}
			if(insertMaintainanceID != -1){

				Log.e("Maitainance ID=", "DateTime:-"+Utility.getcurrentTimeStamp()+"----DB Maintainance id insert---"+insertMaintainanceID);
				insertTestingToDB();
				insertClampingToDB();
				insertPaintingOringToDB();
				insertRCCTODB();
				insertKitchenSurkshaTubeTODB();
				insertOtherSurkshaTubeTODB();
				insertMakeandGeyserTODB();
				insertOtherChecksTODB();
				insertConfromanceDetailTODB();
				insertCustomerFeedbackTODB();
			}
		}catch(Exception e){
			e.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncFormSubmit:"+ Utility.convertExceptionToString(e));
		}
	}

	private void insertTestingToDB(){

		ContentValues testingCV = new ContentValues();
		testingCV.put(Constants.DB_MAINTAINANCE_ID,insertMaintainanceID);
		testingCV.put(Constants.DB_INITIAL_PRESSURE,SecurityManager.encrypt(TestingVO.getInitialPressure(),Constants.SALT));
		testingCV.put(Constants.DB_FINAL_PRESSURE,SecurityManager.encrypt(TestingVO.getFinalPressure(),Constants.SALT));
		testingCV.put(Constants.DB_PRESSURE_DROP,SecurityManager.encrypt(TestingVO.getPressureDrop(),Constants.SALT));
		testingCV.put(Constants.DB_DURATION,SecurityManager.encrypt(TestingVO.getDuration(),Constants.SALT));
		testingCV.put(Constants.DB_GAS_LKG_DETECTION_TEST,SecurityManager.encrypt(TestingVO.getGasLkgDetectionTest(),Constants.SALT));
		testingCV.put(Constants.DB_PRESSURE_TYPE,SecurityManager.encrypt(TestingVO.getPressureType(),Constants.SALT));
		System.out.println(" ********** LEAKAGE RECTIFIED *********** "+TestingVO.getGasLkgRectified());
		testingCV.put(Constants.DB_GAS_LKG_RECTIFIED,SecurityManager.encrypt(TestingVO.getGasLkgRectified(),Constants.SALT));
		testingCV.put(Constants.DB_ISSYNC,0);
		testingCV.put(Constants.DB_CREATEDBY,userID);
		testingCV.put(Constants.DB_CREATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
		testingCV.put(Constants.DB_UPDATEDBY,userID);
		testingCV.put(Constants.DB_UPDATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
		if(isFromUpdate) {
		maintainService.updateMaintainance(Constants.TBL_DTL_TESTING, testingCV, "maintainanceid = " + insertMaintainanceID);
		insertTestingID = maintainService.getTestingId("maintainanceid = "+insertMaintainanceID);
		} else {
			insertTestingID = (int) maintainService.insertMaintainance(Constants.TBL_DTL_TESTING, testingCV);
		}
		System.out.print("testingid ="+insertTestingID);
		if(insertTestingID != -1){

			System.out.print("out testING ="+TestingVO.getGasLkgDetectionTest());
			if(TestingVO.getGasLkgDetectionTest().equals(Constants.LABLE_NO))
			{
				//gas lkg table
				System.out.print("iN testING ="+TestingVO.getGasLkgDetectionTest());
				for(int i=0;i<TestingFragment.leakageArray.length;i++){
					if(TestingFragment.leakageDtlChecked.get(i)){

						LeakageMasterVO lkgVO = TestingFragment.leakageArray[i];

						ContentValues lkgCV = new ContentValues();
						lkgCV.put(Constants.DB_TESTING_ID,insertTestingID);
						System.out.print("\nLeakage id="+lkgVO.getLeakageid());
						lkgCV.put(Constants.DB_LEAKAGE_ID,lkgVO.getLeakageid());
						System.out.print("LeakageID="+lkgVO.getLeakageid());
						if(TestingFragment.leakageArray[i].getLeakagename().equalsIgnoreCase("GI FITTING"))//GIFITTING
						{
							//gifitting table
							ContentValues gifittingCV = new ContentValues();
							gifittingCV.put(Constants.DB_TESTING_ID,insertTestingID);
							System.out.print("Gifiting elbow="+GIFittingVO.getElbow());
							System.out.print("Gifiting tee="+GIFittingVO.getTee());
							System.out.print("Gifiting hex="+GIFittingVO.getHexNipple());
							System.out.print("Gifiting plug="+GIFittingVO.getPlug());
							System.out.print("Gifiting isworking="+GIFittingVO.getIsWorking());
							System.out.print("Gifiting cap="+GIFittingVO.getGicap());
							System.out.print("Gifiting coupling="+GIFittingVO.getGicoupling());
							System.out.print("Gifiting unioun="+GIFittingVO.getUnion());
							gifittingCV.put(Constants.DB_ELBOW,SecurityManager.encrypt(GIFittingVO.getElbow(),Constants.SALT));
							gifittingCV.put(Constants.DB_TEE,SecurityManager.encrypt(GIFittingVO.getTee(),Constants.SALT));
							gifittingCV.put(Constants.DB_HAXNIPPLE,SecurityManager.encrypt(GIFittingVO.getHexNipple(),Constants.SALT));
							gifittingCV.put(Constants.DB_GI_UNION,SecurityManager.encrypt(GIFittingVO.getUnion(),Constants.SALT));
							gifittingCV.put(Constants.DB_PLUG,SecurityManager.encrypt(GIFittingVO.getPlug(),Constants.SALT));
							gifittingCV.put(Constants.DB_GI_CAP,SecurityManager.encrypt(GIFittingVO.getGicap(),Constants.SALT));
							gifittingCV.put(Constants.DB_GI_COUPLING,SecurityManager.encrypt(GIFittingVO.getGicoupling(),Constants.SALT));
							gifittingCV.put(Constants.DB_ISSYNC,0);
							gifittingCV.put(Constants.DB_CREATEDBY,userID);
							gifittingCV.put(Constants.DB_CREATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
							gifittingCV.put(Constants.DB_UPDATEDBY,userID);
							gifittingCV.put(Constants.DB_UPDATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
							gifittingCV.put(Constants.DB_VALUE,SecurityManager.encrypt(GIFittingVO.getIsWorking(),Constants.SALT));
							System.out.print("Gifiting added="+i);
							if(isFromUpdate) {
								if (maintainService.updateMaintainance(Constants.TBL_DTL_GIFITTING, gifittingCV, "testingid = " + insertTestingID) != -1) {
									isInsert = true;
								}

							} else {
								if (maintainService.insertMaintainance(Constants.TBL_DTL_GIFITTING, gifittingCV) != -1) {
									isInsert = true;
								}
							}

						}

						lkgCV.put(Constants.DB_ISSYNC,0);
						lkgCV.put(Constants.DB_CREATEDBY,userID);
						lkgCV.put(Constants.DB_CREATEDON, SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
						lkgCV.put(Constants.DB_UPDATEDBY,userID);
						lkgCV.put(Constants.DB_UPDATEDON, SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
						if(isFromUpdate) {
							if (maintainService.updateMaintainance(Constants.TBL_DTL_LEAKAGE, lkgCV, "testingid = " + insertTestingID) != -1) {
								isInsert = true;
							}

						} else {
							if (maintainService.insertMaintainance(Constants.TBL_DTL_LEAKAGE, lkgCV) != -1) {
								isInsert = true;
							}
						}
					}
				}
			}
		}
	}

	private void insertClampingToDB() {

		ContentValues clampingCV = new ContentValues();
		clampingCV.put(Constants.DB_MAINTAINANCE_ID,insertMaintainanceID);
		clampingCV.put(Constants.DB_ISWORKING,SecurityManager.encrypt(ClampingVO.getIsWorking(),Constants.SALT));
		clampingCV.put(Constants.DB_PIPELINE_PROTECTION_CLAMP,SecurityManager.encrypt(ClampingVO.getPipelineProtectionClamp(),Constants.SALT));
		clampingCV.put(Constants.DB_CLAMP_HALF_C,SecurityManager.encrypt(ClampingVO.getChrgClamp1by2(),Constants.SALT));
		clampingCV.put(Constants.DB_CLAMP_HALF_NC, SecurityManager.encrypt(ClampingVO.getClamp1by2(),Constants.SALT));
		clampingCV.put(Constants.DB_CLAMP_ONE_C,SecurityManager.encrypt(ClampingVO.getChrgClamp1(),Constants.SALT));
		clampingCV.put(Constants.DB_CLAMP_ONE_NC,SecurityManager.encrypt(ClampingVO.getClamp1(),Constants.SALT));
		clampingCV.put(Constants.DB_CHEESEHEADSCREW_C,SecurityManager.encrypt(ClampingVO.getChrgCheeseHeadScrew(),Constants.SALT));
		clampingCV.put(Constants.DB_CHEESEHEADSCREW_NC,SecurityManager.encrypt(ClampingVO.getCheeseHeadScrew(),Constants.SALT));
		clampingCV.put(Constants.DB_WOODSCREW_C,SecurityManager.encrypt(ClampingVO.getChrgWoodScrew(),Constants.SALT));
		clampingCV.put(Constants.DB_WOODSCREW_NC, SecurityManager.encrypt(ClampingVO.getWoodScrew(),Constants.SALT));
		clampingCV.put(Constants.DB_ROUL_PLUG_C,SecurityManager.encrypt(ClampingVO.getChrgRoulPlug(),Constants.SALT));
		clampingCV.put(Constants.DB_ROUL_PLUG_NC,SecurityManager.encrypt(ClampingVO.getRoulPlug(),Constants.SALT));
		clampingCV.put(Constants.DB_SPLR_HALF, SecurityManager.encrypt(ClampingVO.getSplrClamp1by2(),Constants.SALT));
		clampingCV.put(Constants.DB_SPLR_ONE,SecurityManager.encrypt(ClampingVO.getSplrClamp1(),Constants.SALT));
		clampingCV.put(Constants.DB_SPLR_CHEESE_HEAD_SCREW,SecurityManager.encrypt(ClampingVO.getSplrCheeseHeadScrew(),Constants.SALT));
		clampingCV.put(Constants.DB_SPLR_WOOKD_SCREW, SecurityManager.encrypt(ClampingVO.getSplrWoodScrew(),Constants.SALT));
		clampingCV.put(Constants.DB_SPLR_ROUL_PLUG,SecurityManager.encrypt(ClampingVO.getSplrRoulPlug(),Constants.SALT));
		clampingCV.put(Constants.DB_ISSYNC,0);
		clampingCV.put(Constants.DB_CREATEDBY,userID);
		clampingCV.put(Constants.DB_CREATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
		clampingCV.put(Constants.DB_UPDATEDBY,userID);
		clampingCV.put(Constants.DB_UPDATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
		if(isFromUpdate) {
			if (maintainService.updateMaintainance(Constants.TBL_DTL_CLAMPING, clampingCV, "maintainanceid = " + insertMaintainanceID) != -1) {
				isInsert = true;
			}

		} else {
			if (maintainService.insertMaintainance(Constants.TBL_DTL_CLAMPING, clampingCV) != -1) {
				isInsert = true;
			}
		}
	}

	private void insertPaintingOringToDB(){

		ContentValues paintingCV = new ContentValues();
		paintingCV.put(Constants.DB_MAINTAINANCE_ID,insertMaintainanceID);
		paintingCV.put(Constants.DB_ISPAINTING_WORK,SecurityManager.encrypt(PaintingVO.getIsPaintingWork(),Constants.SALT));
		paintingCV.put(Constants.DB_PAINT,SecurityManager.encrypt(PaintingVO.getPaint(),Constants.SALT));

		paintingCV.put(Constants.DB_ISORING_WORK,SecurityManager.encrypt(PaintingVO.getIsOringWork(),Constants.SALT));
		paintingCV.put(Constants.DB_ORMETER,SecurityManager.encrypt(PaintingVO.getOrMeter(),Constants.SALT));
		paintingCV.put(Constants.DB_ORDOM_REGULATOR,SecurityManager.encrypt(PaintingVO.getOrDomRegulator(),Constants.SALT));
		paintingCV.put(Constants.DB_ORAUDCOGLAND,SecurityManager.encrypt(PaintingVO.getOrAudcoGland(),Constants.SALT));
		paintingCV.put(Constants.DB_ISSYNC,0);
		paintingCV.put(Constants.DB_CREATEDBY,userID);
		paintingCV.put(Constants.DB_CREATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
		paintingCV.put(Constants.DB_UPDATEDBY,userID);
		paintingCV.put(Constants.DB_UPDATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
		if(isFromUpdate) {
			if (maintainService.updateMaintainance(Constants.TBL_DTL_PAINTINGORING, paintingCV, "maintainanceid = " + insertMaintainanceID) != -1) {
				isInsert = true;
			}

		} else {
		if(maintainService.insertMaintainance(Constants.TBL_DTL_PAINTINGORING,paintingCV) != -1){
			isInsert=true;
		}
		}
	}



	private void insertRCCTODB(){

		ContentValues rccCV = new ContentValues();
		rccCV.put(Constants.DB_MAINTAINANCE_ID,insertMaintainanceID);
		rccCV.put(Constants.DB_IS_SAND_FILLING, SecurityManager.encrypt(RccVO.getIsSandFilling(),Constants.SALT));
		rccCV.put(Constants.DB_ISWORKING,SecurityManager.encrypt(RccVO.getIsWorking(),Constants.SALT));
		Log.d("tag"," RCC NAIL ::::::: - "+RccVO.getMsNail());
		rccCV.put(Constants.DB_MSNAIL,SecurityManager.encrypt(RccVO.getMsNail(),Constants.SALT));
		Log.d("tag"," RCC NUT ::::::: - "+RccVO.getMsNut());
		rccCV.put(Constants.DB_MSNUT,SecurityManager.encrypt(RccVO.getMsNut(),Constants.SALT));
		// -Start- Added By Pankit Mistry
		Log.d("tag", " RCC NAIL CHARGABLE :::::: - "+RccVO.getMsNailChargable());
		rccCV.put(Constants.DB_MSNAIL_CHARGABLE, SecurityManager.encrypt(RccVO.getMsNailChargable(),Constants.SALT));
		Log.d("tag", " RCC NUT CHARGABLE :::::: - "+RccVO.getMsNutChargable());
		rccCV.put(Constants.DB_MSNUT_CHARGABLE, SecurityManager.encrypt(RccVO.getMsNutChargable(),Constants.SALT));
		Log.d("tag"," RCC COACH SCREW ::::::: - "+RccVO.getCoachScrew());
		rccCV.put(Constants.DB_MSNAIL_SUPPLIEDBY, SecurityManager.encrypt(RccVO.getMsNailSuppliedBy(),Constants.SALT));
		rccCV.put(Constants.DB_MSNUT_SUPPLIEDBY, SecurityManager.encrypt(RccVO.getMsNutSuppliedBy(),Constants.SALT));
		// -End-
		rccCV.put(Constants.DB_COACH_SCREW,SecurityManager.encrypt(RccVO.getCoachScrew(),Constants.SALT));
		Log.d("tag"," RCC GAURD ::::::: - "+RccVO.getRccGuard());
		rccCV.put(Constants.DB_RCC_GUARD, SecurityManager.encrypt(RccVO.getRccGuard(),Constants.SALT));
		// -Start- Added By Pankit Mistry
		Log.d("tag", " RCC GUARD CHARGABLE :::::: - "+RccVO.getRccGuardChargable());
		rccCV.put(Constants.DB_RCC_GUARD_CHARGABLE, SecurityManager.encrypt(RccVO.getRccGuardChargable(),Constants.SALT));
		rccCV.put(Constants.DB_RCC_GUARD_SUPPLIEDBY, SecurityManager.encrypt(RccVO.getRccGuardSuppliedBy(),Constants.SALT));
		// -End-

		rccCV.put(Constants.DB_ISSYNC,0);
		rccCV.put(Constants.DB_CREATEDBY,userID);
		rccCV.put(Constants.DB_CREATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
		rccCV.put(Constants.DB_UPDATEDBY,userID);
		rccCV.put(Constants.DB_UPDATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
		if(isFromUpdate) {
			if (maintainService.updateMaintainance(Constants.TBL_DTL_RCC, rccCV, "maintainanceid = " + insertMaintainanceID) != -1) {
				isInsert = true;
			}

		} else {
			if (maintainService.insertMaintainance(Constants.TBL_DTL_RCC, rccCV) != -1) {
				isInsert = true;
			}
		}
	}
	
	/*private void insertRCCTODB(){

		ContentValues rccCV = new ContentValues();
		rccCV.put(Constants.DB_MAINTAINANCE_ID,insertMaintainanceID);
		rccCV.put(Constants.DB_ISWORKING,SecurityManager.encrypt(RccVO.getIsWorking(),Constants.SALT));
		Log.d("tag"," RCC NAIL ::::::: - "+RccVO.getMsNail());
		rccCV.put(Constants.DB_MSNAIL,SecurityManager.encrypt(RccVO.getMsNail(),Constants.SALT));
		Log.d("tag"," RCC NUT ::::::: - "+RccVO.getMsNut());
		rccCV.put(Constants.DB_MSNUT,SecurityManager.encrypt(RccVO.getMsNut(),Constants.SALT));
		Log.d("tag"," RCC COACH SCREW ::::::: - "+RccVO.getCoachScrew());
		rccCV.put(Constants.DB_COACH_SCREW,SecurityManager.encrypt(RccVO.getCoachScrew(),Constants.SALT));
		Log.d("tag"," RCC GAURD ::::::: - "+RccVO.getRccGuard());
		rccCV.put(Constants.DB_RCC_GUARD, SecurityManager.encrypt(RccVO.getRccGuard(),Constants.SALT));
		
		rccCV.put(Constants.DB_ISSYNC,0);
		rccCV.put(Constants.DB_CREATEDBY,userID);
		rccCV.put(Constants.DB_CREATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
		rccCV.put(Constants.DB_UPDATEDBY,userID);
		rccCV.put(Constants.DB_UPDATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));

		if(maintainService.insertMaintainance(Constants.TBL_DTL_RCC,rccCV) != -1){
			isInsert=true;
		}
	}*/

	private void insertKitchenSurkshaTubeTODB(){

		ContentValues surkshaCV = new ContentValues();
		surkshaCV.put(Constants.DB_MAINTAINANCE_ID,insertMaintainanceID);
		surkshaCV.put(Constants.DB_IS_REPLACED,SecurityManager.encrypt(KitchenSurakshaTubeVO.getIsReplaced(),Constants.SALT));

		Log.d("tag",Constants.DB_SIZE_751C +"  ::::::: - "+KitchenSurakshaTubeVO.getSize751c());
		surkshaCV.put(Constants.DB_SIZE_751C,SecurityManager.encrypt(KitchenSurakshaTubeVO.getSize751c(),Constants.SALT));

		Log.d("tag", Constants.DB_SIZE_751NC +" ::::::: - "+KitchenSurakshaTubeVO.getSize751nc());
		surkshaCV.put(Constants.DB_SIZE_751NC,SecurityManager.encrypt(KitchenSurakshaTubeVO.getSize751nc(),Constants.SALT));


		Log.d("tag",Constants.DB_SIZE_7915C +"  ::::::: - "+KitchenSurakshaTubeVO.getSize7915c());
		surkshaCV.put(Constants.DB_SIZE_7915C,SecurityManager.encrypt(KitchenSurakshaTubeVO.getSize7915c(),Constants.SALT));

		Log.d("tag", Constants.DB_SIZE_7915NC +" ::::::: - "+KitchenSurakshaTubeVO.getSize7915nc());
		surkshaCV.put(Constants.DB_SIZE_7915NC,SecurityManager.encrypt(KitchenSurakshaTubeVO.getSize7915nc(),Constants.SALT));

		Log.d("tag",Constants.DB_SIZE_12515C +"  ::::::: - "+KitchenSurakshaTubeVO.getSize12515c());
		surkshaCV.put(Constants.DB_SIZE_12515C,SecurityManager.encrypt(KitchenSurakshaTubeVO.getSize12515c(),Constants.SALT));

		Log.d("tag", Constants.DB_SIZE_12515NC +" ::::::: - "+KitchenSurakshaTubeVO.getSize12515nc());
		surkshaCV.put(Constants.DB_SIZE_12515NC,SecurityManager.encrypt(KitchenSurakshaTubeVO.getSize12515nc(),Constants.SALT));

		Log.d("tag",Constants.DB_SIZE_CLAMP_8C +"  ::::::: - "+KitchenSurakshaTubeVO.getClampsize8c());
		surkshaCV.put(Constants.DB_SIZE_CLAMP_8C,SecurityManager.encrypt(KitchenSurakshaTubeVO.getClampsize8c(),Constants.SALT));

		Log.d("tag", Constants.DB_SIZE_CLAMP_8NC +" ::::::: - "+KitchenSurakshaTubeVO.getClampsize8nc());
		surkshaCV.put(Constants.DB_SIZE_CLAMP_8NC,SecurityManager.encrypt(KitchenSurakshaTubeVO.getClampsize8nc(),Constants.SALT));

		Log.d("tag",Constants.DB_SIZE_CLAMP_20C +"  ::::::: - "+KitchenSurakshaTubeVO.getClampsize20c());
		surkshaCV.put(Constants.DB_SIZE_CLAMP_20C,SecurityManager.encrypt(KitchenSurakshaTubeVO.getClampsize20c(),Constants.SALT));

		Log.d("tag", Constants.DB_SIZE_CLAMP_20C +" ::::::: - "+KitchenSurakshaTubeVO.getClampsize20nc());
		surkshaCV.put(Constants.DB_SIZE_CLAMP_20NC,SecurityManager.encrypt(KitchenSurakshaTubeVO.getClampsize20nc(),Constants.SALT));

		surkshaCV.put(Constants.DB_KSPL_751,SecurityManager.encrypt(KitchenSurakshaTubeVO.getKSplr1Meter75mm(),Constants.SALT));
		surkshaCV.put(Constants.DB_KSPL_7915,SecurityManager.encrypt(KitchenSurakshaTubeVO.getKSplr15Meter79mm(),Constants.SALT));
		surkshaCV.put(Constants.DB_KSPL_12515,SecurityManager.encrypt(KitchenSurakshaTubeVO.getKSplrChrg15Meter125mm(),Constants.SALT));
		surkshaCV.put(Constants.DB_KSPL_8,SecurityManager.encrypt(KitchenSurakshaTubeVO.getKSplrClampHose8mmPipe(),Constants.SALT));
		surkshaCV.put(Constants.DB_KSPL_20,SecurityManager.encrypt(KitchenSurakshaTubeVO.getKSplrClampHose20mmPipe(),Constants.SALT));

		surkshaCV.put(Constants.DB_REPLACE_EXPIRY_DATE,SecurityManager.encrypt(KitchenSurakshaTubeVO.getReplaceexpirydate(),Constants.SALT));
		surkshaCV.put(Constants.DB_EXPIRY_DATE,SecurityManager.encrypt(KitchenSurakshaTubeVO.getExpirydate(),Constants.SALT));

		surkshaCV.put(Constants.DB_ISSYNC,0);



		surkshaCV.put(Constants.DB_CREATEDBY,userID);
		surkshaCV.put(Constants.DB_CREATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
		surkshaCV.put(Constants.DB_UPDATEDBY,userID);
		surkshaCV.put(Constants.DB_UPDATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
		if(isFromUpdate) {
			if (maintainService.updateMaintainance(Constants.TBL_DTL_SURAKSHA_TUBE, surkshaCV, "maintainanceid = " + insertMaintainanceID) != -1) {
				isInsert = true;
			}

		} else {
			if (maintainService.insertMaintainance(Constants.TBL_DTL_SURAKSHA_TUBE, surkshaCV) != -1) {
				isInsert = true;
			}
		}
	}


	private void insertOtherSurkshaTubeTODB(){

		ContentValues surkshaCV = new ContentValues();
		surkshaCV.put(Constants.DB_MAINTAINANCE_ID,insertMaintainanceID);
		surkshaCV.put(Constants.DB_IS_REPLACED,SecurityManager.encrypt(OtherKitchenSurakshaTubeVO.getIsReplaced(),Constants.SALT));

		Log.d("tag",Constants.DB_SIZE_751C +"  ::::::: - "+OtherKitchenSurakshaTubeVO.getSize751c());
		surkshaCV.put(Constants.DB_SIZE_751C,SecurityManager.encrypt(OtherKitchenSurakshaTubeVO.getSize751c(),Constants.SALT));

		Log.d("tag", Constants.DB_SIZE_751NC +" ::::::: - "+OtherKitchenSurakshaTubeVO.getSize751nc());
		surkshaCV.put(Constants.DB_SIZE_751NC,SecurityManager.encrypt(OtherKitchenSurakshaTubeVO.getSize751nc(),Constants.SALT));


		Log.d("tag",Constants.DB_SIZE_7915C +"  ::::::: - "+OtherKitchenSurakshaTubeVO.getSize7915c());
		surkshaCV.put(Constants.DB_SIZE_7915C,SecurityManager.encrypt(OtherKitchenSurakshaTubeVO.getSize7915c(),Constants.SALT));

		Log.d("tag", Constants.DB_SIZE_7915NC +" ::::::: - "+OtherKitchenSurakshaTubeVO.getSize7915nc());
		surkshaCV.put(Constants.DB_SIZE_7915NC,SecurityManager.encrypt(OtherKitchenSurakshaTubeVO.getSize7915nc(),Constants.SALT));

		Log.d("tag",Constants.DB_SIZE_12515C +"  ::::::: - "+OtherKitchenSurakshaTubeVO.getSize12515c());
		surkshaCV.put(Constants.DB_SIZE_12515C,SecurityManager.encrypt(OtherKitchenSurakshaTubeVO.getSize12515c(),Constants.SALT));

		Log.d("tag", Constants.DB_SIZE_12515NC +" ::::::: - "+OtherKitchenSurakshaTubeVO.getSize12515nc());
		surkshaCV.put(Constants.DB_SIZE_12515NC,SecurityManager.encrypt(OtherKitchenSurakshaTubeVO.getSize12515nc(),Constants.SALT));

		Log.d("tag",Constants.DB_SIZE_CLAMP_8C +"  ::::::: - "+OtherKitchenSurakshaTubeVO.getClampsize8c());
		surkshaCV.put(Constants.DB_SIZE_CLAMP_8C,SecurityManager.encrypt(OtherKitchenSurakshaTubeVO.getClampsize8c(),Constants.SALT));

		Log.d("tag", Constants.DB_SIZE_CLAMP_8NC +" ::::::: - "+OtherKitchenSurakshaTubeVO.getClampsize8nc());
		surkshaCV.put(Constants.DB_SIZE_CLAMP_8NC,SecurityManager.encrypt(OtherKitchenSurakshaTubeVO.getClampsize8nc(),Constants.SALT));

		Log.d("tag",Constants.DB_SIZE_CLAMP_20C +"  ::::::: - "+OtherKitchenSurakshaTubeVO.getClampsize20c());
		surkshaCV.put(Constants.DB_SIZE_CLAMP_20C,SecurityManager.encrypt(OtherKitchenSurakshaTubeVO.getClampsize20c(),Constants.SALT));

		Log.d("tag", Constants.DB_SIZE_CLAMP_20C +" ::::::: - "+OtherKitchenSurakshaTubeVO.getClampsize20nc());
		surkshaCV.put(Constants.DB_SIZE_CLAMP_20NC,SecurityManager.encrypt(OtherKitchenSurakshaTubeVO.getClampsize20nc(),Constants.SALT));

		surkshaCV.put(Constants.DB_KSPL_751,SecurityManager.encrypt(OtherKitchenSurakshaTubeVO.getKSplr1Meter75mm(),Constants.SALT));
		surkshaCV.put(Constants.DB_KSPL_7915,SecurityManager.encrypt(OtherKitchenSurakshaTubeVO.getKSplr15Meter79mm(),Constants.SALT));
		surkshaCV.put(Constants.DB_KSPL_12515,SecurityManager.encrypt(OtherKitchenSurakshaTubeVO.getKSplrChrg15Meter125mm(),Constants.SALT));
		surkshaCV.put(Constants.DB_KSPL_8,SecurityManager.encrypt(OtherKitchenSurakshaTubeVO.getKSplrClampHose8mmPipe(),Constants.SALT));
		surkshaCV.put(Constants.DB_KSPL_20,SecurityManager.encrypt(OtherKitchenSurakshaTubeVO.getKSplrClampHose20mmPipe(),Constants.SALT));

		surkshaCV.put(Constants.DB_ISSYNC,0);

		surkshaCV.put(Constants.DB_CREATEDBY,userID);
		surkshaCV.put(Constants.DB_CREATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
		surkshaCV.put(Constants.DB_UPDATEDBY,userID);
		surkshaCV.put(Constants.DB_UPDATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
		if(isFromUpdate) {
			if (maintainService.updateMaintainance(Constants.TBL_DTL_OTHER_SURAKSHA_TUBE, surkshaCV, "maintainanceid = " + insertMaintainanceID) != -1) {
				isInsert = true;
			}

		} else {
			if (maintainService.insertMaintainance(Constants.TBL_DTL_OTHER_SURAKSHA_TUBE, surkshaCV) != -1) {
				isInsert = true;
			}
		}
	}


	private void insertMakeandGeyserTODB(){

		ContentValues makegeyserCV = new ContentValues();
		makegeyserCV.put(Constants.DB_MAINTAINANCE_ID,insertMaintainanceID);
		makegeyserCV.put(Constants.DB_IS_GEYSER_AVAILABLE,SecurityManager.encrypt(MakeGeyserVO.getIsGeyserAvailable(),Constants.SALT));
		if(MakeGeyserVO.getIsGeyserAvailable().equals("YES"))
		{
			makegeyserCV.put(Constants.DB_MAKEID,MakeGeyserVO.getMakeId());
			makegeyserCV.put(Constants.DB_MAKE_OTHER_TEXT,SecurityManager.encrypt(MakeGeyserVO.getMakeotherText(),Constants.SALT));
			//Log.e("GEYSER FROM SUBMIT:-",""+MakeGeyserVO.getGeyserTypeId());
			//Log.e("MAKE oTHER TEXT FROM SUBMIT:-",""+MakeGeyserVO.getMakeotherText());
			makegeyserCV.put(Constants.DB_GEYSER_TYPE_ID,MakeGeyserVO.getGeyserTypeId());
			makegeyserCV.put(Constants.DB_ISINSIDE_BATHROOM,SecurityManager.encrypt(MakeGeyserVO.getIsInsideBathroom(),Constants.SALT));
		}
		makegeyserCV.put(Constants.DB_ISSYNC,0);
		makegeyserCV.put(Constants.DB_CREATEDBY,userID);
		makegeyserCV.put(Constants.DB_CREATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
		makegeyserCV.put(Constants.DB_UPDATEDBY,userID);
		makegeyserCV.put(Constants.DB_UPDATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
		if(isFromUpdate) {
			if (maintainService.updateMaintainance(Constants.TBL_DTL_MAKE_AND_GEYSER, makegeyserCV, "maintainanceid = " + insertMaintainanceID) != -1) {
				isInsert = true;
			}

		} else {
			if (maintainService.insertMaintainance(Constants.TBL_DTL_MAKE_AND_GEYSER, makegeyserCV) != -1) {
				isInsert = true;
			}
		}
	}


	private void insertOtherChecksTODB(){

		ContentValues otherchecksCV = new ContentValues();
		otherchecksCV.put(Constants.DB_MAINTAINANCE_ID,insertMaintainanceID);
		//otherchecksCV.put(Constants.DB_SUPPLY_BUSH, SecurityManager.encrypt(OtherChecksVO.getSupplyBush(), Constants.SALT));
		otherchecksCV.put(Constants.DB_RUBBERCAP,SecurityManager.encrypt(OtherChecksVO.getRubberCap(),Constants.SALT));
		otherchecksCV.put(Constants.DB_RUBBER_CAP_CHARGABLE, SecurityManager.encrypt(OtherChecksVO.getRubberCapChargable(), Constants.SALT));
		otherchecksCV.put(Constants.DB_RUBBER_CAP_SUPPLIED_BY, SecurityManager.encrypt(OtherChecksVO.getRubberCapSuppliedBy(), Constants.SALT));
		otherchecksCV.put(Constants.DB_BRASSBALL_VALVE,SecurityManager.encrypt(OtherChecksVO.getBrassBallValue(),Constants.SALT));
		otherchecksCV.put(Constants.DB_BRASSBALL_VALUE_CHARGABLE, SecurityManager.encrypt(OtherChecksVO.getBrassBallValueChargable(), Constants.SALT));
		otherchecksCV.put(Constants.DB_BRASS_BALL_VALUE_SUPPLIED_BY, SecurityManager.encrypt(OtherChecksVO.getBrassBallSuppliedBy(), Constants.SALT));
		otherchecksCV.put(Constants.DB_BRASSBALL_COCK,SecurityManager.encrypt(OtherChecksVO.getBrassBallCock(),Constants.SALT));
		otherchecksCV.put(Constants.DB_BRASSBALL_COCK_CHARGABLE, SecurityManager.encrypt(OtherChecksVO.getBrassBallCockChargable(), Constants.SALT));
		otherchecksCV.put(Constants.DB_BRASSBALL_COCK_SUPPLIED_BY, SecurityManager.encrypt(OtherChecksVO.getBrassBallCockSuppliedBy(), Constants.SALT));
		otherchecksCV.put(Constants.DB_SUPPLY_BUSH_VALUE, SecurityManager.encrypt(OtherChecksVO.getSupply3by4into1by2Bush(), Constants.SALT));
		otherchecksCV.put(Constants.DB_SUPPLY_BUSH_VALUE_CHARGABLE, SecurityManager.encrypt(OtherChecksVO.getChrgSupply3by4into1by2Bush(), Constants.SALT));
		otherchecksCV.put(Constants.DB_SUPPLY_BUSH_SUPPLIED_BY, SecurityManager.encrypt(OtherChecksVO.getsplrSupply3by4into1by2BushBy(), Constants.SALT));
		otherchecksCV.put(Constants.DB_IS_METER_OK,SecurityManager.encrypt(OtherChecksVO.getIsMeter(),Constants.SALT));
		otherchecksCV.put(Constants.DB_METER_READING,SecurityManager.encrypt(OtherChecksVO.getMeterReading(),Constants.SALT));

		if(OtherChecksVO.getMtrPerformance().toString().equalsIgnoreCase("Select option from dropdown"))
			OtherChecksVO.setMtrPerformance(Constants.LABEL_BLANK);
		otherchecksCV.put(Constants.DB_MTR_PERFORMANCE,SecurityManager.encrypt(OtherChecksVO.getMtrPerformance(),Constants.SALT));
		if(OtherChecksVO.getMtrReadable().toString().equalsIgnoreCase("Select option from dropdown"))
			OtherChecksVO.setMtrReadable(Constants.LABEL_BLANK);
		otherchecksCV.put(Constants.DB_MTR_READABLE,SecurityManager.encrypt(OtherChecksVO.getMtrReadable(),Constants.SALT));
		if(OtherChecksVO.getGiPipeInsidebm().toString().equalsIgnoreCase("Select option from dropdown"))
			OtherChecksVO.setGiPipeInsidebm(Constants.LABEL_BLANK);
		otherchecksCV.put(Constants.DB_GIPIPE_INSIDE_BM,SecurityManager.encrypt(OtherChecksVO.getGiPipeInsidebm(),Constants.SALT));
		if(OtherChecksVO.getPvcSleeve().toString().equalsIgnoreCase("Select option from dropdown"))
			OtherChecksVO.setPvcSleeve(Constants.LABEL_BLANK);
		otherchecksCV.put(Constants.DB_PVC_SLEEVE, SecurityManager.encrypt(OtherChecksVO.getPvcSleeve(), Constants.SALT));
		otherchecksCV.put(Constants.DB_ISSYNC,0);
		otherchecksCV.put(Constants.DB_CREATEDBY,userID);
		otherchecksCV.put(Constants.DB_CREATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
		otherchecksCV.put(Constants.DB_UPDATEDBY,userID);
		otherchecksCV.put(Constants.DB_UPDATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
		if(isFromUpdate) {
			if (maintainService.updateMaintainance(Constants.TBL_DTL_OTHER_CHECKS, otherchecksCV, "maintainanceid = " + insertMaintainanceID) != -1) {
				isInsert = true;
			}

		} else {
			if (maintainService.insertMaintainance(Constants.TBL_DTL_OTHER_CHECKS, otherchecksCV) != -1) {
				isInsert = true;
			}
		}
	}
	
	

	/*private void insertOtherChecksTODB(){

		ContentValues otherchecksCV = new ContentValues();
		otherchecksCV.put(Constants.DB_MAINTAINANCE_ID,insertMaintainanceID);
		otherchecksCV.put(Constants.DB_RUBBERCAP,SecurityManager.encrypt(OtherChecksVO.getRubberCap(),Constants.SALT));
		otherchecksCV.put(Constants.DB_BRASSBALL_VALVE,SecurityManager.encrypt(OtherChecksVO.getBrassBallValue(),Constants.SALT));
		otherchecksCV.put(Constants.DB_BRASSBALL_COCK,SecurityManager.encrypt(OtherChecksVO.getBrassBallCock(),Constants.SALT));
		otherchecksCV.put(Constants.DB_IS_METER_OK,SecurityManager.encrypt(OtherChecksVO.getIsMeter(),Constants.SALT));
		otherchecksCV.put(Constants.DB_METER_READING,SecurityManager.encrypt(OtherChecksVO.getMeterReading(),Constants.SALT));
		
		if(OtherChecksVO.getMtrPerformance().toString().equalsIgnoreCase("Select option from dropdown"))
			OtherChecksVO.setMtrPerformance(Constants.LABEL_BLANK);
		otherchecksCV.put(Constants.DB_MTR_PERFORMANCE,SecurityManager.encrypt(OtherChecksVO.getMtrPerformance(),Constants.SALT));
		if(OtherChecksVO.getMtrReadable().toString().equalsIgnoreCase("Select option from dropdown"))
			OtherChecksVO.setMtrReadable(Constants.LABEL_BLANK);
		otherchecksCV.put(Constants.DB_MTR_READABLE,SecurityManager.encrypt(OtherChecksVO.getMtrReadable(),Constants.SALT));
		if(OtherChecksVO.getGiPipeInsidebm().toString().equalsIgnoreCase("Select option from dropdown"))
			OtherChecksVO.setGiPipeInsidebm(Constants.LABEL_BLANK);
		otherchecksCV.put(Constants.DB_GIPIPE_INSIDE_BM,SecurityManager.encrypt(OtherChecksVO.getGiPipeInsidebm(),Constants.SALT));
		otherchecksCV.put(Constants.DB_ISSYNC,0);
		otherchecksCV.put(Constants.DB_CREATEDBY,userID);
		otherchecksCV.put(Constants.DB_CREATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
		otherchecksCV.put(Constants.DB_UPDATEDBY,userID);
		otherchecksCV.put(Constants.DB_UPDATEDON,SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));

		if(maintainService.insertMaintainance(Constants.TBL_DTL_OTHER_CHECKS,otherchecksCV) != -1){
			isInsert=true;
		}
	}*/

	private void insertConfromanceDetailTODB(){
		if(ConformanceFragment.nonConformanceReasonLst != null){
			for(int i=0;i<ConformanceFragment.nonConformanceReasonLst.size();i++){
				System.out.println(" SIZE LIST NAME ------- "+ ConformanceFragment.nonConformanceReasonLst.size() );
				System.out.println(" SIZE CHECKED LIST ------- "+ ConformanceFragment.reasonChecked.size() );
				if(ConformanceFragment.reasonChecked.size()>0){
					if(ConformanceFragment.reasonChecked.get(i)){
						ContentValues ConformanceCV = new ContentValues();
						ConformanceCV.put(Constants.CONFORMANCE_DETAIL_FIELDS[1], insertMaintainanceID);

						System.out.println(" CONFORMANCE ID --------- "+ConformanceFragment.nonConformanceReasonLst.get(i).getConformanceID());

						ConformanceCV.put(Constants.CONFORMANCE_DETAIL_FIELDS[2],ConformanceFragment.nonConformanceReasonLst.get(i).getConformanceID());

						if(ConformanceFragment.nonConformanceReasonLst.get(i).getReason().equalsIgnoreCase("OTHERS")){
							//Log.e("COnformance Other Text Insert:--","--------"+ConformanceDetailVO.getText());
							ConformanceCV.put(Constants.CONFORMANCE_DETAIL_FIELDS[3], SecurityManager.encrypt(ConformanceDetailVO.getText(),Constants.SALT));
						}

						ConformanceCV.put(Constants.CONFORMANCE_DETAIL_FIELDS[4], 0);
						ConformanceCV.put(Constants.CONFORMANCE_DETAIL_FIELDS[5], userID);
						ConformanceCV.put(Constants.CONFORMANCE_DETAIL_FIELDS[6], SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
						ConformanceCV.put(Constants.CONFORMANCE_DETAIL_FIELDS[7], userID);
						ConformanceCV.put(Constants.CONFORMANCE_DETAIL_FIELDS[8], SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
						ConformanceCV.put(Constants.CONFORMANCE_DETAIL_FIELDS[9], SecurityManager.encrypt(ConformanceDetailVO.getIsNC(),Constants.SALT));
						/*if(isFromUpdate) {
							if (maintainService.updateConformanceDetail(Constants.TBL_DTL_CONFORMANCE, ConformanceCV, "maintainanceid = " + insertMaintainanceID) != -1) {
								isInsert = true;
							}

						} else {*/
							if (maintainService.insertConformanceDetail(Constants.TBL_DTL_CONFORMANCE, ConformanceCV) != -1) {
								isInsert = true;
							}
						/*}*/
					}else{
						ContentValues ConformanceCV1 = new ContentValues();
						ConformanceCV1.put(Constants.CONFORMANCE_DETAIL_FIELDS[1], insertMaintainanceID);
						ConformanceCV1.put(Constants.CONFORMANCE_DETAIL_FIELDS[2],0);
						ConformanceCV1.put(Constants.CONFORMANCE_DETAIL_FIELDS[3], SecurityManager.encrypt(ConformanceDetailVO.getText(),Constants.SALT));
						ConformanceCV1.put(Constants.CONFORMANCE_DETAIL_FIELDS[4], 0);
						ConformanceCV1.put(Constants.CONFORMANCE_DETAIL_FIELDS[5], userID);
						ConformanceCV1.put(Constants.CONFORMANCE_DETAIL_FIELDS[6], SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
						ConformanceCV1.put(Constants.CONFORMANCE_DETAIL_FIELDS[7], userID);
						ConformanceCV1.put(Constants.CONFORMANCE_DETAIL_FIELDS[8], SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
						ConformanceCV1.put(Constants.CONFORMANCE_DETAIL_FIELDS[9], SecurityManager.encrypt(ConformanceDetailVO.getIsNC(),Constants.SALT));
						/*if(isFromUpdate) {
							if (maintainService.updateConformanceDetail(Constants.TBL_DTL_CONFORMANCE, ConformanceCV1, "maintainanceid = " + insertMaintainanceID) != -1) {
								isInsert = true;
							}

						} else {*/
							if (maintainService.insertConformanceDetail(Constants.TBL_DTL_CONFORMANCE, ConformanceCV1) != -1) {
								isInsert = true;
							}
						/*}*/
					}

				}
			}
		}
	}

	private void insertCustomerFeedbackTODB(){

		ContentValues CV = new ContentValues();
		CV.put(Constants.CUST_FEED_FIELDS[1],insertMaintainanceID);
		CV.put(Constants.CUST_FEED_FIELDS[2],SecurityManager.encrypt(TestingVO.getRemarkLkgDescription()+"\n"+CustomerFeedbackVO.getComments(),Constants.SALT));
		CV.put(Constants.CUST_FEED_FIELDS[3],SecurityManager.encrypt(CustomerFeedbackVO.getSignature(),Constants.SALT));
		CV.put(Constants.CUST_FEED_FIELDS[4],SecurityManager.encrypt(CustomerFeedbackVO.getReceiptNo(),Constants.SALT));
		CV.put(Constants.CUST_FEED_FIELDS[5],SecurityManager.encrypt(CustomerFeedbackVO.getNoticeNo(),Constants.SALT));
		CV.put(Constants.CUST_FEED_FIELDS[6],0);
		CV.put(Constants.CUST_FEED_FIELDS[7],userID);
		CV.put(Constants.CUST_FEED_FIELDS[8],SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
		CV.put(Constants.CUST_FEED_FIELDS[9],userID);
		CV.put(Constants.CUST_FEED_FIELDS[10],SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));
		if(isFromUpdate) {
			if (maintainService.updateFeedbackByID(Constants.TBL_DTL_CUSTOMER_FEEDBACK, CV, "maintainanceid = " + insertMaintainanceID) != -1) {
				isInsert = true;
			}

		} else {
			if (maintainService.insertFeedback(Constants.TBL_DTL_CUSTOMER_FEEDBACK, CV) != -1) {
				isInsert = true;
			}
		}
	}

	private void setDefaultValuesOfVO(){

		TestingVO.setInitialPressure(Constants.LABEL_BLANK);
		TestingVO.setFinalPressure(Constants.LABEL_BLANK);
		TestingVO.setPressureDrop(Constants.LABEL_BLANK);
		TestingVO.setDuration(Constants.LABEL_BLANK);
		TestingVO.setGasLkgDetectionTest(Constants.LABEL_BLANK);
		TestingVO.setPressureType(Constants.LABEL_BLANK);
		TestingVO.setGasLkgRectified(Constants.LABEL_BLANK);

		ClampingVO.setIsWorking(Constants.LABEL_BLANK);
		ClampingVO.setPipelineProtectionClamp(Constants.LABEL_BLANK);
		ClampingVO.setChrgClamp1by2(Constants.LABEL_BLANK);
		ClampingVO.setClamp1by2(Constants.LABEL_BLANK);
		ClampingVO.setChrgClamp1(Constants.LABEL_BLANK);
		ClampingVO.setClamp1(Constants.LABEL_BLANK);
		ClampingVO.setChrgCheeseHeadScrew(Constants.LABEL_BLANK);
		ClampingVO.setCheeseHeadScrew(Constants.LABEL_BLANK);
		ClampingVO.setChrgWoodScrew(Constants.LABEL_BLANK);
		ClampingVO.setWoodScrew(Constants.LABEL_BLANK);
		ClampingVO.setChrgRoulPlug(Constants.LABEL_BLANK);
		ClampingVO.setRoulPlug(Constants.LABEL_BLANK);
		ClampingVO.setSplrClamp1by2(Constants.LABEL_BLANK);
		ClampingVO.setSplrClamp1(Constants.LABEL_BLANK);
		ClampingVO.setSplrCheeseHeadScrew(Constants.LABEL_BLANK);
		ClampingVO.setSplrWoodScrew(Constants.LABEL_BLANK);
		ClampingVO.setSplrRoulPlug(Constants.LABEL_BLANK);



		PaintingVO.setIsPaintingWork(Constants.LABEL_BLANK);
		PaintingVO.setPaint(Constants.LABEL_BLANK);
		PaintingVO.setIsOringWork(Constants.LABEL_BLANK);
		PaintingVO.setOrMeter(Constants.LABEL_BLANK);
		PaintingVO.setOrDomRegulator(Constants.LABEL_BLANK);
		PaintingVO.setOrAudcoGland(Constants.LABEL_BLANK);

		RccVO.setIsWorking(Constants.LABEL_BLANK);
		RccVO.setIsSandFilling(Constants.LABEL_BLANK);
		RccVO.setMsNailChargable(Constants.LABEL_BLANK);
		RccVO.setMsNail(Constants.LABEL_BLANK);
		RccVO.setMsNailSuppliedBy(Constants.LABEL_BLANK);
		RccVO.setMsNutChargable(Constants.LABEL_BLANK);
		RccVO.setMsNut(Constants.LABEL_BLANK);
		RccVO.setMsNutSuppliedBy(Constants.LABEL_BLANK);
		RccVO.setCoachScrew(Constants.LABEL_BLANK);
		RccVO.setRccGuard(Constants.LABEL_BLANK);
		RccVO.setRccGuardChargable(Constants.LABEL_BLANK);
		RccVO.setRccGuardSuppliedBy(Constants.LABEL_BLANK);

		KitchenSurakshaTubeVO.setIsReplaced(Constants.LABEL_BLANK);
		KitchenSurakshaTubeVO.setExpirydate(Constants.LABEL_BLANK);
		KitchenSurakshaTubeVO.setReplaceexpirydate(Constants.LABEL_BLANK);
		KitchenSurakshaTubeVO.setSize751c(Constants.LABEL_BLANK);
		KitchenSurakshaTubeVO.setSize751nc(Constants.LABEL_BLANK);
		KitchenSurakshaTubeVO.setSize7915c(Constants.LABEL_BLANK);
		KitchenSurakshaTubeVO.setSize7915nc(Constants.LABEL_BLANK);
		KitchenSurakshaTubeVO.setSize12515c(Constants.LABEL_BLANK);
		KitchenSurakshaTubeVO.setSize12515nc(Constants.LABEL_BLANK);
		KitchenSurakshaTubeVO.setClampsize8c(Constants.LABEL_BLANK);
		KitchenSurakshaTubeVO.setClampsize8nc(Constants.LABEL_BLANK);
		KitchenSurakshaTubeVO.setClampsize20c(Constants.LABEL_BLANK);
		KitchenSurakshaTubeVO.setClampsize20nc(Constants.LABEL_BLANK);
		KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.LABEL_BLANK);
		KitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.LABEL_BLANK);
		KitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.LABEL_BLANK);
		KitchenSurakshaTubeVO.setKSplrClampHose20mmPipe(Constants.LABEL_BLANK);
		KitchenSurakshaTubeVO.setKSplrClampHose8mmPipe(Constants.LABEL_BLANK);


		OtherKitchenSurakshaTubeVO.setSize751c(Constants.LABEL_BLANK);
		OtherKitchenSurakshaTubeVO.setSize751nc(Constants.LABEL_BLANK);
		OtherKitchenSurakshaTubeVO.setSize7915c(Constants.LABEL_BLANK);
		OtherKitchenSurakshaTubeVO.setSize7915nc(Constants.LABEL_BLANK);
		OtherKitchenSurakshaTubeVO.setSize12515c(Constants.LABEL_BLANK);
		OtherKitchenSurakshaTubeVO.setSize12515nc(Constants.LABEL_BLANK);
		OtherKitchenSurakshaTubeVO.setClampsize8c(Constants.LABEL_BLANK);
		OtherKitchenSurakshaTubeVO.setClampsize8nc(Constants.LABEL_BLANK);
		OtherKitchenSurakshaTubeVO.setClampsize20c(Constants.LABEL_BLANK);
		OtherKitchenSurakshaTubeVO.setClampsize20nc(Constants.LABEL_BLANK);
		OtherKitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.LABEL_BLANK);
		OtherKitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.LABEL_BLANK);
		OtherKitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.LABEL_BLANK);
		OtherKitchenSurakshaTubeVO.setKSplrClampHose20mmPipe(Constants.LABEL_BLANK);
		OtherKitchenSurakshaTubeVO.setKSplrClampHose8mmPipe(Constants.LABEL_BLANK);


		MakeGeyserVO.setIsGeyserAvailable(Constants.LABEL_BLANK);
		MakeGeyserVO.setMakeId(0);
		MakeGeyserVO.setMakeotherText(Constants.LABEL_BLANK);
		MakeGeyserVO.setGeyserTypeId(0);
		MakeGeyserVO.setIsInsideBathroom(Constants.LABEL_BLANK);

		OtherChecksVO.setRubberCap(Constants.LABEL_BLANK);
		OtherChecksVO.setBrassBallCock(Constants.LABEL_BLANK);
		OtherChecksVO.setBrassBallCockChargable(Constants.LABEL_BLANK);
		OtherChecksVO.setBrassBallCockSuppliedBy(Constants.LABEL_BLANK);
		OtherChecksVO.setBrassBallSuppliedBy(Constants.LABEL_BLANK);
		OtherChecksVO.setBrassBallValue(Constants.LABEL_BLANK);
		OtherChecksVO.setBrassBallValueChargable(Constants.LABEL_BLANK);
		OtherChecksVO.setGiPipeInsidebm(Constants.LABEL_BLANK);
		OtherChecksVO.setIsMeter(Constants.LABEL_BLANK);
		OtherChecksVO.setSupply3by4into1by2Bush(Constants.LABEL_BLANK);
		OtherChecksVO.setChrgSupply3by4into1by2Bush(Constants.LABEL_BLANK);
		OtherChecksVO.setsplrSupply3by4into1by2BushBy(Constants.LABEL_BLANK);
		OtherChecksVO.setMeterReading(Constants.LABEL_BLANK);
		OtherChecksVO.setMtrPerformance(Constants.LABEL_BLANK);
		OtherChecksVO.setMtrReadable(Constants.LABEL_BLANK);
		OtherChecksVO.setPvcSleeve(Constants.LABEL_BLANK);
		OtherChecksVO.setRubberCap(Constants.LABEL_BLANK);
		OtherChecksVO.setRubberCapChargable(Constants.LABEL_BLANK);
		OtherChecksVO.setRubberCapSuppliedBy(Constants.LABEL_BLANK);
		//OtherChecksVO.setSupplyBush(Constants.LABEL_BLANK);


		ConformanceDetailVO.setText(Constants.LABEL_BLANK);
		ConformanceDetailVO.setIsNC(Constants.LABEL_BLANK);

		TestingVO.setRemarkLkgDescription(Constants.LABEL_BLANK);
		CustomerFeedbackVO.setSignature(Constants.LABEL_BLANK);
		CustomerFeedbackVO.setReceiptNo(Constants.LABEL_BLANK);
		CustomerFeedbackVO.setNoticeNo(Constants.LABEL_BLANK);

	}


}