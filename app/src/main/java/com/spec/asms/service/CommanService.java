package com.spec.asms.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.DBHelper;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.dto.ClampingDTO;
import com.spec.asms.dto.ConformanceDetailDTO;
import com.spec.asms.dto.CustomerFeedbackDTO;
import com.spec.asms.dto.GIFittingDTO;
import com.spec.asms.dto.KitchenSurakshaTubeDTO;
import com.spec.asms.dto.MaintainanceDTO;
import com.spec.asms.dto.MakeGeyserDTO;
import com.spec.asms.dto.OtherChecksDTO;
import com.spec.asms.dto.OtherKitchenSurakshaTubeDTO;
import com.spec.asms.dto.PaintingDTO;
import com.spec.asms.dto.RccDTO;
import com.spec.asms.dto.TestingDTO;
import com.spec.asms.vo.DamageNCauseDetailVO;
import com.spec.asms.vo.LeakageVO;

public class CommanService {

	private static DBHelper dbHelper;

	public static void retriveSavedData(Context context , int itemId)
	{
		try{
			dbHelper = new DBHelper(context);
			Cursor cursor = dbHelper.cursorAllSelectByWhere(Constants.TBL_MST_CONFORMANCE,"rowid = "+itemId);
			System.out.println(" cursor size -- "+cursor.getCount());

			JSONObject outboundJson = new JSONObject();
			JSONArray orderJson = new JSONArray();
			JSONObject addressJson = new JSONObject();

			addressJson.put(Constants.JSON_LOGIN_USERNAME,"npras");
			addressJson.put(Constants.JSON_LOGIN_PASSWORD,"abcDef123");
			addressJson.put(Constants.JSON_LOGIN_DEVICEID,"29989878677562435");
			orderJson.put(addressJson);
			outboundJson.put(Constants.JSON_LOGIN,orderJson);

			System.out.println(" JSON : "+outboundJson.toString());

		}catch(Exception ex){
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":CommanService"+ Utility.convertExceptionToString(ex));
		}
	}

	public static List<MaintainanceDTO> getMaintainanceList(Context context) throws JSONException{
		List<MaintainanceDTO> arryMaintainance = new ArrayList<MaintainanceDTO>();
		MaintainanceService maintainanceService = new MaintainanceService(context);
		arryMaintainance = maintainanceService.getMaintainaceList(Constants.TBL_DTL_MAINTAINANCE, Constants.MAINTAINANCE_FIELDS);
		return arryMaintainance;
	}

	public static JSONArray prepareBatchSubmit(Context context,List<MaintainanceDTO> arryMaintainance,int startIndex,int lastIndex) throws JSONException{

		JSONArray mainArray = new JSONArray();
		MaintainanceService maintainanceService = new MaintainanceService(context);
		MaintainanceDTOService mainDtoService = new MaintainanceDTOService(context);


		for(int i= startIndex; i< lastIndex && i< arryMaintainance.size();i++){

			JSONArray nonConformanceArray = new JSONArray();
			JSONArray leakageArray = new JSONArray();
			JSONObject maintenanceFormData = new JSONObject();

			JSONObject maintenanceForm = new JSONObject();
			JSONObject maintenanceFormTesting = new JSONObject();
			JSONObject maintenanceFormClamping = new JSONObject();
			JSONObject maintenanceFormPainting = new JSONObject();
			JSONObject maintenanceFormORing = new JSONObject();

			JSONObject maintenanceFormTestingLeakageGIFitting = new JSONObject();
			JSONObject maintenanceFormRCC = new JSONObject();
			JSONObject maintenanceFormKitchenSurakshaTube = new JSONObject();
			JSONObject maintenanceFormOtherSurakshaTube = new JSONObject();
			JSONObject maintenanceFormMakeAndGeyser = new JSONObject();
			JSONObject maintenanceFormOthers = new JSONObject();

			JSONObject maintenanceFormSignature = new JSONObject();

			MaintainanceDTO maintainanceDTO = arryMaintainance.get(i);
//			Log.d("batchSubmit", "maintainanceVO.getMaintainanceId()::: "+maintainanceDTO.getMaintainanceId());

			TestingDTO testingDTO = mainDtoService.getTestingDetailByMaintainanceID(maintainanceDTO.getMaintainanceId());

			ClampingDTO clampingDTO = mainDtoService.getClampingByMaintainanceID(maintainanceDTO.getMaintainanceId());

			PaintingDTO paintingDTO = mainDtoService.getPaintingOringByMaintainanceID(maintainanceDTO.getMaintainanceId());

			GIFittingDTO giFittingDTO = mainDtoService.getGIfittingDetailByTestingID(testingDTO.getTestingId());

			List<LeakageVO> arrayLeakage = maintainanceService.getLeakageLstByTestingID(testingDTO.getTestingId());

			RccDTO rccDTO = mainDtoService.getRCCDetailByMaintainanceID(maintainanceDTO.getMaintainanceId());

			///SurakshaTubeDTO surakshaTubeDTO = mainDtoService.getSurkshaTubeByMaintainanceID(maintainanceDTO.getMaintainanceId());

			KitchenSurakshaTubeDTO kitchenSurakshaTubeDTO = mainDtoService.getKitchenSurkshaTubeByMaintainanceID(maintainanceDTO.getMaintainanceId());

			OtherKitchenSurakshaTubeDTO otherKitchenSurakshaTubeDTO = mainDtoService.getOtherSurkshaTubeByMaintainanceID(maintainanceDTO.getMaintainanceId());

			MakeGeyserDTO makeGeyserDTO = mainDtoService.getMakeGeyserByMaitainanceId(maintainanceDTO.getMaintainanceId());

			OtherChecksDTO otherChecksDTO = mainDtoService.getOtherchecksByMaintainanceID(maintainanceDTO.getMaintainanceId());

			List<ConformanceDetailDTO> arrayConformanceDetail = maintainanceService.getAllConformanceDTOListByMaitainanceID(maintainanceDTO.getMaintainanceId());

			CustomerFeedbackDTO customerFeedbackDTO = mainDtoService.getCustomerFeedbackByMaintainaceID(maintainanceDTO.getMaintainanceId());


			//maintenanceForm -----
			maintenanceForm.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
			maintenanceForm.put(Constants.JSON_CUSTOMER_ID,maintainanceDTO.getCustomerId());
			maintenanceForm.put(Constants.JSON_STATUS_CODE_ID,maintainanceDTO.getStatusCode());
			maintenanceForm.put(Constants.JSON_DATETIME_RECORD,maintainanceDTO.getDate());
			maintenanceForm.put(Constants.JSON_COMMENTS,customerFeedbackDTO.getComments());
			maintenanceForm.put(Constants.JSON_RECEIPT_NO,customerFeedbackDTO.getReceiptNo());
			maintenanceForm.put(Constants.JSON_NOTICE_NO,customerFeedbackDTO.getNoticeNo());

			// Added by Pankit Mistry - To support Contact number and Alternate contact number
			maintenanceForm.put("ContactNo", maintainanceDTO.getContactNo());
			maintenanceForm.put("AltContactNo", maintainanceDTO.getAltContactNo());

//			MAINTENANCEFORM.PUT(CONSTANTS.JSON_START_TIME_STAMP,SHAREDPREFRENCEUTIL.GETPREFRENCE(CONTEXT, CONSTANTS.PREF_START_TIME,LONG.VALUEOF(0)));
//			MAINTENANCEFORM.PUT(CONSTANTS.JSON_END_TIME_STAMP,SHAREDPREFRENCEUTIL.GETPREFRENCE(CONTEXT,CONSTANTS.PREF_END_TIME,LONG.VALUEOF(0)));

//			Log.e("START TIME---",""+maintainanceDTO.getStartTime());
//			Log.e("END TIME---",""+maintainanceDTO.getEndTime());

			maintenanceForm.put(Constants.JSON_START_TIME_STAMP,maintainanceDTO.getStartTime());
			maintenanceForm.put(Constants.JSON_END_TIME_STAMP,maintainanceDTO.getEndTime());

			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM, maintenanceForm);

			//maintenanceForm -----
//			Log.d("batchSubmit", "::: JSON_MAINTENANCE_FORM_ID"+maintainanceDTO.getMaintainanceOrderId());
			//maintenanceFormTesting -----
			maintenanceFormTesting.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
			maintenanceFormTesting.put(Constants.JSON_INITIAL_PRESSURE, testingDTO.getInitialPressure());
			maintenanceFormTesting.put(Constants.JSON_FINAL_PRESSURE, testingDTO.getFinalPressure());
			maintenanceFormTesting.put(Constants.JSON_PRESSURE_DROP,testingDTO.getPressureDrop());
			maintenanceFormTesting.put(Constants.JSON_DURATION, testingDTO.getDuration());
			maintenanceFormTesting.put(Constants.JSON_GAS_LKG_TEST_PASS, testingDTO.getGasLkgDetectionTest());
			maintenanceFormTesting.put(Constants.JSON_PRESSURE_HLN, testingDTO.getPressureType());
			System.out.println(" ********** SERVER ^^^^^^^^ LEAKAGE RECTIFIED *********** "+testingDTO.getGasLkgRectified());
			maintenanceFormTesting.put(Constants.JSON_GAS_LKG_RECTIFIED, testingDTO.getGasLkgRectified());

			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_TESTING, maintenanceFormTesting);
			//maintenanceFormTesting -----

			//maintenanceFormClamping -----
			maintenanceFormClamping.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
			maintenanceFormClamping.put(Constants.JSON_CLAMPPING_WORK_SATISFACTORY,clampingDTO.getClampingWorkSatisfactory());

			maintenanceFormClamping.put(Constants.JSON_PIPELINE_PROTECTION_CLAMP,clampingDTO.getPipelineProtectionClamp());
			maintenanceFormClamping.put(Constants.JSON_CLAMP_1BY2_C,clampingDTO.getChrgClamp1by2());
			maintenanceFormClamping.put(Constants.JSON_CLAMP_1BY2_NC,clampingDTO.getClamp1by2());
			maintenanceFormClamping.put(Constants.JSON_CLAMP1_C,clampingDTO.getChrgClamp1());
			maintenanceFormClamping.put(Constants.JSON_CLAMP1_NC,clampingDTO.getClamp1());
			maintenanceFormClamping.put(Constants.JSON_CHEESE_HEAD_SCREW_C,clampingDTO.getChrgCheeseHeadScrew());
			maintenanceFormClamping.put(Constants.JSON_CHEESE_HEAD_SCREW_NC,clampingDTO.getCheeseHeadScrew());
			maintenanceFormClamping.put(Constants.JSON_WOOD_SCREW_C,clampingDTO.getChrgWoodScrew());
			maintenanceFormClamping.put(Constants.JSON_WOOD_SCREW_NC,clampingDTO.getWoodScrew());
			maintenanceFormClamping.put(Constants.JSON_ROUL_PLUG_C, clampingDTO.getChrgRoulPlug());
			maintenanceFormClamping.put(Constants.JSON_ROUL_PLUG_NC,clampingDTO.getRoulPlug());
			maintenanceFormClamping.put(Constants.JSON_CLMAP_SPLR_1BY2,clampingDTO.getSplrClamp1by2());
			maintenanceFormClamping.put(Constants.JSON_CLMAP_SPLR_1,clampingDTO.getSplrClamp1());
			maintenanceFormClamping.put(Constants.JSON_CLMAP_SPLR_CHEESE_HEAD_SCREW,clampingDTO.getSplrCheeseHeadScrew());
			maintenanceFormClamping.put(Constants.JSON_CLMAP_SPLR_WOOD_SCREW,clampingDTO.getSplrWoodScrew());
			maintenanceFormClamping.put(Constants.JSON_CLMAP_SPLR_ROUL_PLUG,clampingDTO.getSplrRoulPlug());

			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_CLAMPING, maintenanceFormClamping);
			//maintenanceFormClamping -----


			//maintenanceFormPainting -----
			maintenanceFormPainting.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
			maintenanceFormPainting.put(Constants.JSON_PAINTING_WORK_SATISFACTORY,paintingDTO.getIsPaintingWork());
			maintenanceFormPainting.put(Constants.JSON_PAINT, paintingDTO.getPaint());

			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_PAINTING, maintenanceFormPainting);
			//maintenanceFormPainting -----

			//maintenanceFormORing -----
			maintenanceFormORing.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
			maintenanceFormORing.put(Constants.JSON_ORING_SATISFACTORY,paintingDTO.getIsOringWork());
//			Log.d("tag","JSON PAINT 1 ::::::: - "+paintingDTO.getOrMeter());
			maintenanceFormORing.put(Constants.JSON_FOR_METER, paintingDTO.getOrMeter());
//			Log.d("tag","JSON PAINT 2 ::::::: - "+paintingDTO.getOrDomRegulator());
			maintenanceFormORing.put(Constants.JSON_FOR_DOM_REGULARTOR,paintingDTO.getOrDomRegulator());
//			Log.d("tag","JSON PAINT 3 ::::::: - "+paintingDTO.getOrAudcoGland());
			maintenanceFormORing.put(Constants.JSON_FOR_AUDCO_GLAND, paintingDTO.getOrAudcoGland());

			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_ORING, maintenanceFormORing);
			//maintenanceFormORing -----


			//maintenanceFormTestingLeakage -----
			for(int k=0;k<arrayLeakage.size();k++){
				LeakageVO leakageVO = arrayLeakage.get(k);
				if(leakageVO!=null){
					JSONObject maintenanceFormTestingLeakage = new JSONObject();
					maintenanceFormTestingLeakage.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
					maintenanceFormTestingLeakage.put(Constants.JSON_LKG_ID,leakageVO.getServiceLkgId());
					maintenanceFormTestingLeakage.put(Constants.JSON_LKG_TEXT, maintainanceService.getLeakageName(leakageVO.getServiceLkgId()));
					String where = "maintainanceorderid = '"+maintainanceDTO.getMaintainanceOrderId()+"' and objectid ="+leakageVO.getServiceLkgId();
					DamageNCauseDetailVO damageVo = maintainanceService.getDetailDamageCauserByObjectNMorderID(where);
					maintenanceFormTestingLeakage.put(Constants.JSON_DAMAGE_ID,damageVo.getDamageId());
					maintenanceFormTestingLeakage.put(Constants.JSON_CAUSE_ID,damageVo.getCauseId());

					leakageArray.put(k,maintenanceFormTestingLeakage);
				}
			}
			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_TESTING_LKG, leakageArray);
			//maintenanceFormTestingLeakage -----

			//maintenanceFormTestingLeakageGIFitting -----
			if(giFittingDTO!=null){
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_WORK_ON_GAS,giFittingDTO.getIsWorking());

//				//Log.e("COMMMON SERVICE:-","ELBOW VALUE:-"+giFittingDTO.getElbow());
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_ELBOW, giFittingDTO.getElbow());
				String where = "maintainanceorderid = '"+maintainanceDTO.getMaintainanceOrderId()+"' and objectid ="+maintainanceService.getObjectIDFromGifitting(Constants.DB_REQ_ELBOW);
				DamageNCauseDetailVO damageVoElbow = maintainanceService.getDetailDamageCauserByObjectNMorderID(where);
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_ELBOW+Constants.JSON_DAMAGE_ID,damageVoElbow.getDamageId());
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_ELBOW+Constants.JSON_CAUSE_ID,damageVoElbow.getCauseId());

				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_TEE,giFittingDTO.getTee());
				String whereTee = "maintainanceorderid = '"+maintainanceDTO.getMaintainanceOrderId()+"' and objectid ="+maintainanceService.getObjectIDFromGifitting(Constants.DB_REQ_TEE);
				DamageNCauseDetailVO damageVoTee = maintainanceService.getDetailDamageCauserByObjectNMorderID(whereTee);
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_TEE+Constants.JSON_DAMAGE_ID,damageVoTee.getDamageId());
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_TEE+Constants.JSON_CAUSE_ID,damageVoTee.getCauseId());

				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_HEX_NIPPLE,giFittingDTO.getHexNipple());
				String wherehex = "maintainanceorderid = '"+maintainanceDTO.getMaintainanceOrderId()+"' and objectid ="+maintainanceService.getObjectIDFromGifitting(Constants.DB_REQ_HEX_NIPPLE);
				DamageNCauseDetailVO damageVoHex = maintainanceService.getDetailDamageCauserByObjectNMorderID(wherehex);
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_HEX_NIPPLE+Constants.JSON_DAMAGE_ID,damageVoHex.getDamageId());
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_HEX_NIPPLE+Constants.JSON_CAUSE_ID,damageVoHex.getCauseId());

				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_GI_UNION,giFittingDTO.getGIUnion());
				String whereUnion = "maintainanceorderid = '"+maintainanceDTO.getMaintainanceOrderId()+"' and objectid ="+maintainanceService.getObjectIDFromGifitting(Constants.DB_REQ_UNION);
				DamageNCauseDetailVO damageVoUnion = maintainanceService.getDetailDamageCauserByObjectNMorderID(whereUnion);
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_GI_UNION+Constants.JSON_DAMAGE_ID,damageVoUnion.getDamageId());
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_GI_UNION+Constants.JSON_CAUSE_ID,damageVoUnion.getCauseId());

				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_PLUG,giFittingDTO.getPlug());
				String wherePlug = "maintainanceorderid = '"+maintainanceDTO.getMaintainanceOrderId()+"' and objectid ="+maintainanceService.getObjectIDFromGifitting(Constants.DB_REQ_PLUG);
				DamageNCauseDetailVO damageVoPlug = maintainanceService.getDetailDamageCauserByObjectNMorderID(wherePlug);
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_PLUG+Constants.JSON_DAMAGE_ID,damageVoPlug.getDamageId());
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_PLUG+Constants.JSON_CAUSE_ID,damageVoPlug.getCauseId());

				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_GI_CAP,giFittingDTO.getGICap());
				String whereCap = "maintainanceorderid = '"+maintainanceDTO.getMaintainanceOrderId()+"' and objectid ="+maintainanceService.getObjectIDFromGifitting(Constants.DB_REQ_GI_CAP);
				DamageNCauseDetailVO damageVoCap = maintainanceService.getDetailDamageCauserByObjectNMorderID(whereCap);
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_GI_CAP+Constants.JSON_DAMAGE_ID,damageVoCap.getDamageId());
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_GI_CAP+Constants.JSON_CAUSE_ID,damageVoCap.getCauseId());

				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_GI_COUPLING,giFittingDTO.getGICoupling());
				String whereCoupling = "maintainanceorderid = '"+maintainanceDTO.getMaintainanceOrderId()+"' and objectid ="+maintainanceService.getObjectIDFromGifitting(Constants.DB_REQ_GI_COUPLING);
				DamageNCauseDetailVO damageVocoupling = maintainanceService.getDetailDamageCauserByObjectNMorderID(whereCoupling);
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_GI_COUPLING+Constants.JSON_DAMAGE_ID,damageVocoupling.getDamageId());
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_GI_COUPLING+Constants.JSON_CAUSE_ID,damageVocoupling.getCauseId());

				//				JSONArray attribute = new JSONArray();
				//			//	for(int l = 0;l<mainDtoService.getGIfittingMasterDataSize().size(); l++){
				//					GIFittingMSTDTO dto = mainDtoService.getGIfittingMasterDataSize().get(l);
				//
				//					JSONObject object = new JSONObject();
				//
				//					object.put("Key", dto.getText());
				//					object.put("Value", "Value");
				//					object.put(Constants.JSON_TEE, giFittingDTO.getTee());
				//					object.put(Constants.JSON_HEX_NIPPLE, giFittingDTO.getHexNipple());
				//					object.put(Constants.JSON_GI_UNION, giFittingDTO.getGIUnion());
				//					object.put(Constants.JSON_PLUG, giFittingDTO.getPlug());
				//					object.put(Constants.JSON_GI_CAP, giFittingDTO.getGICap());
				//					object.put(Constants.JSON_GI_COUPLING, giFittingDTO.getGICoupling());
				//
				//					attribute.put(l,object);
				//				}
				//	maintenanceFormTestingLeakageGIFitting.put("Attributes", attribute);

				maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_TESTING_LKG_GIFITTING, maintenanceFormTestingLeakageGIFitting);

			}
			//maintenanceFormTestingLeakageGIFitting -----

			//maintenanceFormRCC -----
			maintenanceFormRCC.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
			maintenanceFormRCC.put(Constants.JSON_RCC_WORK_SATISFACTORY,rccDTO.getIsWorking());
			maintenanceFormRCC.put(Constants.JSON_RCC_SANDFILLING_CEMENTING,rccDTO.getIsSandFilling());
			maintenanceFormRCC.put(Constants.JSON_MSN_NAILDIA_1BY4_C, rccDTO.getMsNailChargable());
			maintenanceFormRCC.put(Constants.JSON_MSN_NAILDIA_1BY4_NC, rccDTO.getMsNail());
			maintenanceFormRCC.put(Constants.JSON_MSNUT_5BY16_C, rccDTO.getMsNutChargable());
			maintenanceFormRCC.put(Constants.JSON_MSNUT_5BY16_NC,rccDTO.getMsNut());
			maintenanceFormRCC.put(Constants.JSON_RCC_GUARD_C, rccDTO.getRccGuardChargable());
			maintenanceFormRCC.put(Constants.JSON_RCC_GUARD_NC, rccDTO.getRccGuard());

			maintenanceFormRCC.put(Constants.JSON_RCC_SPL_MSN_NAILDIA_1BY4, rccDTO.getMsNailSuppliedBy());
			maintenanceFormRCC.put(Constants.JSON_RCC_SPLMSNUT_5BY16, rccDTO.getMsNutSuppliedBy());
			maintenanceFormRCC.put(Constants.JSON_RCC_SPL_GUARD, rccDTO.getRccGuardSuppliedBy());

/*//			Log.d("tag","JSON RCC NAIL ::::::: - "+rccDTO.getMsNail());
			maintenanceFormRCC.put(Constants.JSON_MSN_NAILDIA_1BY4, rccDTO.getMsNail());
//			Log.d("tag","JSON RCC NUT ::::::: - "+rccDTO.getMsNut());
			maintenanceFormRCC.put(Constants.JSON_MSNUT_5BY16,rccDTO.getMsNut());
//			Log.d("tag","JSON RCC GAURD ::::::: - "+rccDTO.getRccGuard());
			maintenanceFormRCC.put(Constants.JSON_RCC_GUARD, rccDTO.getRccGuard());*/

			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_RCC, maintenanceFormRCC);
			//maintenanceFormRCC -----

			//maintenanceFormSurakshaTube -----
/*			maintenanceFormSurakshaTube.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
			maintenanceFormSurakshaTube.put(Constants.JSON_SURAKSHA_TUBE_REPLACED,surakshaTubeDTO.getSurakshaTubeReplaced());
//			Log.d("tag","JSON surakshaTubeDTO.getMeter1mm75() ::::::: - "+surakshaTubeDTO.getMeter1mm75());
			maintenanceFormSurakshaTube.put(Constants.JSON_METER_1MM75, surakshaTubeDTO.getMeter1mm75());
//			Log.d("tag","JSON surakshaTubeDTO.getMeter15mm79() ::::::: - "+surakshaTubeDTO.getMeter15mm79());
			maintenanceFormSurakshaTube.put(Constants.JSON_METER_15MM79,surakshaTubeDTO.getMeter15mm79());
//			Log.d("tag","JSON surakshaTubeDTO.getClampHose8mmPipe():::::: - "+surakshaTubeDTO.getClampHose8mmPipe());
			maintenanceFormSurakshaTube.put(Constants.JSON_CLAMP_HOSE_8MM_PIPE, surakshaTubeDTO.getClampHose8mmPipe());
//			Log.d("tag","JSON surakshaTubeDTO.getMeter15mm125() ::::::: - "+surakshaTubeDTO.getMeter15mm125());
			maintenanceFormSurakshaTube.put(Constants.JSON_METER_15MM125,surakshaTubeDTO.getMeter15mm125());
//			Log.d("tag","JSON RCC NAIL ::::::: - "+surakshaTubeDTO.getClampHose20mmPipe());
			maintenanceFormSurakshaTube.put(Constants.JSON_METER_1MM125,surakshaTubeDTO.getMeter1mm125());
//			Log.d("tag","JSON RCC NAIL ::::::: - "+surakshaTubeDTO.getClampHose20mmPipe());

			maintenanceFormSurakshaTube.put(Constants.JSON_CLAMP_HOSE_20MM_PIPE, surakshaTubeDTO.getClampHose20mmPipe());*/

			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_MAIN_MAINTENANCE_FORM_ID, maintainanceDTO.getMaintainanceOrderId());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_RUBBER_TUBE_EXPIRY_DATE,kitchenSurakshaTubeDTO.getExpiryDate());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_EXPIRY_DATE_OF_REPLACE_RUBBER_TUBE, kitchenSurakshaTubeDTO.getReplaceExpiryDate());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_SURAKSHA_TUBE_REPLACED,kitchenSurakshaTubeDTO.getSurakshaTubeReplaced());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_KC_SIZE_751,kitchenSurakshaTubeDTO.getSize751c());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_K_SIZE_751,kitchenSurakshaTubeDTO.getSize751nc());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_KC_SIZE_7915,kitchenSurakshaTubeDTO.getSize7915c());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_K_SIZE_7915,kitchenSurakshaTubeDTO.getSize7915nc());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_KC_SIZE_12515,kitchenSurakshaTubeDTO.getSize12515c());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_K_SIZE_12515,kitchenSurakshaTubeDTO.getSize12515nc());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_KC_SIZE_8,kitchenSurakshaTubeDTO.getClampsize8c());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_K_SIZE_8,kitchenSurakshaTubeDTO.getClampsize8nc());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_KC_SIZE_20,kitchenSurakshaTubeDTO.getClampsize20c());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_K_SIZE_20,kitchenSurakshaTubeDTO.getClampsize20nc());

			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_KSPL_751,kitchenSurakshaTubeDTO.getKSplr1Meter75mm());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_KSPL_7915,kitchenSurakshaTubeDTO.getKSplr15Meter79mm());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_KSPL_12515,kitchenSurakshaTubeDTO.getKSplrChrg15Meter125mm());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_KSPL_8,kitchenSurakshaTubeDTO.getKSplrClampHose8mmPipe());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_KSPL_20,kitchenSurakshaTubeDTO.getKSplrClampHose20mmPipe());
			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_SURAKSHA_TUBE_KITCHEN, maintenanceFormKitchenSurakshaTube);
			//maintenanceFormSurakshaTube -----

			//maintenanceFormOtherSurakshaTube -----
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_MAIN_MAINTENANCE_FORM_ID, maintainanceDTO.getMaintainanceOrderId());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_SURAKSHA_TUBE_REPLACED,otherKitchenSurakshaTubeDTO.getSurakshaTubeReplaced());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_C_SIZE_751,otherKitchenSurakshaTubeDTO.getSize751c());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_NC_SIZE_751,otherKitchenSurakshaTubeDTO.getSize751nc());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_C_SIZE_7915,otherKitchenSurakshaTubeDTO.getSize7915c());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_NC_SIZE_7915,otherKitchenSurakshaTubeDTO.getSize7915nc());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_C_SIZE_12515,otherKitchenSurakshaTubeDTO.getSize12515c());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_NC_SIZE_12515,otherKitchenSurakshaTubeDTO.getSize12515nc());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_C_SIZE_8,otherKitchenSurakshaTubeDTO.getClampsize8c());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_NC_SIZE_8,otherKitchenSurakshaTubeDTO.getClampsize8nc());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_C_SIZE_20,otherKitchenSurakshaTubeDTO.getClampsize20c());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_NC_SIZE_20,otherKitchenSurakshaTubeDTO.getClampsize20nc());

			maintenanceFormOtherSurakshaTube.put(Constants.JSON_OSPL_751,otherKitchenSurakshaTubeDTO.getKSplr1Meter75mm());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_OSPL_7915,otherKitchenSurakshaTubeDTO.getKSplr15Meter79mm());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_OSPL_12515,otherKitchenSurakshaTubeDTO.getKSplrChrg15Meter125mm());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_OSPL_8,otherKitchenSurakshaTubeDTO.getKSplrClampHose8mmPipe());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_OSPL_20,otherKitchenSurakshaTubeDTO.getKSplrClampHose20mmPipe());

			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_SURAKSHA_TUBE, maintenanceFormOtherSurakshaTube);
			//maintenanceFormOtherSurakshaTube -----

			//maintenanceFormMakeAndGeyser -----
			maintenanceFormMakeAndGeyser.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
			maintenanceFormMakeAndGeyser.put(Constants.JSON_MAKE_ID,makeGeyserDTO.getMakeId());

//			maintenanceFormMakeAndGeyser.put(Constants.JSON_MAKE_ID,makeGeyserDTO.getIsGeyserAvailable());

			maintenanceFormMakeAndGeyser.put(Constants.JSON_MAKE_OTHERS, makeGeyserDTO.getMakeotherText());
			maintenanceFormMakeAndGeyser.put(Constants.JSON_GEYSER_TYPE_ID,makeGeyserDTO.getGeyserTypeId());
			maintenanceFormMakeAndGeyser.put(Constants.JSON_GEYSER_INSIDE, makeGeyserDTO.getIsInsideBathroom());
//			Log.e("GEYSER From CommanService",""+makeGeyserDTO.getGeyserTypeId());
//			Log.e("GEYSER From CommanService",""+makeGeyserDTO.getMakeId());
//			Log.e("GEYSER From CommanService",""+makeGeyserDTO.getIsInsideBathroom());
			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_MAKE_GEYSER, maintenanceFormMakeAndGeyser);
			//maintenanceFormMakeAndGeyser -----

			//maintenanceFormOthers -----
			maintenanceFormOthers.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
			//maintenanceFormOthers.put(Constants.JSON_SUPPLIED_BUSH,otherChecksDTO.getSupplyBush());
			maintenanceFormOthers.put(Constants.JSON_RUBBER_CAP_C,otherChecksDTO.getRubberCapChargable());
			maintenanceFormOthers.put(Constants.JSON_RUBBER_CAP_NC,otherChecksDTO.getRubberCap());
			maintenanceFormOthers.put(Constants.JSON_BRASS_BALL_VALVE_C,otherChecksDTO.getBrassBallValueChargable());
			maintenanceFormOthers.put(Constants.JSON_BRASS_BALL_VALVE_NC,otherChecksDTO.getBrassBallValve());
			maintenanceFormOthers.put(Constants.JSON_BRASS_BALL_COCK_C, otherChecksDTO.getBrassBallCockChargable());
			maintenanceFormOthers.put(Constants.JSON_BRASS_BALL_COCK_NC,otherChecksDTO.getBrassBallCock());
			maintenanceFormOthers.put(Constants.JSON_SUPPLY_BUSH_C,otherChecksDTO.getChrgSupply3by4into1by2Bush());
			maintenanceFormOthers.put(Constants.JSON_SUPPLY_BUSH_NC,otherChecksDTO.getSupply3by4into1by2Bush());

			maintenanceFormOthers.put(Constants.JSON_SPLR_RUBBER_CAP,otherChecksDTO.getRubberCapSuppliedBy());
			maintenanceFormOthers.put(Constants.JSON_SPLR_BRASS_BALL_VALVE,otherChecksDTO.getBrassBallSuppliedBy());
			maintenanceFormOthers.put(Constants.JSON_SPLR_BRASS_BALL_COCK,otherChecksDTO.getBrassBallCockSuppliedBy());
			maintenanceFormOthers.put(Constants.JSON_SPLR_SUPPLY_BUSH_VALVE,otherChecksDTO.getSplrSupply3by4into1by2BushBy());
			maintenanceFormOthers.put(Constants.JSON_INSTALLATION_OF_PVC_SLEEVE, otherChecksDTO.getPvcSleeve());

			maintenanceFormOthers.put(Constants.JSON_METER_READING, otherChecksDTO.getMeterReading());

			maintenanceFormOthers.put(Constants.JSON_METER_PERFORMANCE,otherChecksDTO.getMeterPerformance());
			maintenanceFormOthers.put(Constants.JSON_METER_READABLE, otherChecksDTO.getMeterReadable());
			maintenanceFormOthers.put(Constants.JSON_GI_PIPE_INSIDE_BASEMENT, otherChecksDTO.getGIPipeInsideBasement());

			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_OTHERS, maintenanceFormOthers);
			//maintenanceFormOthers -----

			//maintenanceFormNonConformance -----
			for(int j=0;j<arrayConformanceDetail.size();j++){
				ConformanceDetailDTO conformanceDetailDTO = arrayConformanceDetail.get(j);

				JSONObject maintenanceFormNonConformance = new JSONObject();
				System.out.println(" DTO >>>>>>>>> ID --- "+ conformanceDetailDTO.getConformanceId());
				maintenanceFormNonConformance.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
				maintenanceFormNonConformance.put(Constants.JSON_NON_CONFORMANCE_ID,conformanceDetailDTO.getConformanceId());
				maintenanceFormNonConformance.put(Constants.JSON_IS_APPLICABLE, "Asd");
				maintenanceFormNonConformance.put(Constants.JSON_OTHERS,conformanceDetailDTO.getText());
				String where = "maintainanceorderid = '"+maintainanceDTO.getMaintainanceOrderId()+"' and objectid ="+conformanceDetailDTO.getConformanceId();
				DamageNCauseDetailVO damageVo = maintainanceService.getDetailDamageCauserByObjectNMorderID(where);
				maintenanceFormNonConformance.put(Constants.JSON_DAMAGE_ID,damageVo.getDamageId());
				maintenanceFormNonConformance.put(Constants.JSON_CAUSE_ID,damageVo.getCauseId());

				if(conformanceDetailDTO.getIsNc().equalsIgnoreCase("YES") && (conformanceDetailDTO.getConformanceId() >= 1))
				{
					nonConformanceArray.put(maintenanceFormNonConformance);
				}
			}
			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_NON_CONFORMANCE, nonConformanceArray);
			//maintenanceFormNonConformance -----

			//maintenanceFormSignature -----
			maintenanceFormSignature.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
			maintenanceFormSignature.put(Constants.JSON_SIGNATURE,customerFeedbackDTO.getSignature());

			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_SIGNATURE, maintenanceFormSignature);
			//maintenanceFormSignature -----

			mainArray.put(maintenanceFormData);
		}
		Utility.WriteTextFile("file4.txt","{::"+mainArray.toString()+"::}");
		//		Log.v("CommanService", "BatchSubmit JSON :::: "+mainArray);

		return mainArray;

	}

	public static JSONArray batchSubmit(Context context) throws JSONException{
		List<MaintainanceDTO> arryMaintainance = new ArrayList<MaintainanceDTO>();

		JSONArray mainArray = new JSONArray();
		int batchSize = 0;

		MaintainanceService maintainanceService = new MaintainanceService(context);

		MaintainanceDTOService mainDtoService = new MaintainanceDTOService(context);

		batchSize = maintainanceService.getBatchSize();

		arryMaintainance = maintainanceService.getBatchMaintainaceList(Constants.TBL_DTL_MAINTAINANCE, Constants.MAINTAINANCE_FIELDS,batchSize);

		for(int i=0;i<arryMaintainance.size();i++){

			JSONArray nonConformanceArray = new JSONArray();
			JSONArray leakageArray = new JSONArray();
			JSONObject maintenanceFormData = new JSONObject();

			JSONObject maintenanceForm = new JSONObject();
			JSONObject maintenanceFormTesting = new JSONObject();
			JSONObject maintenanceFormClamping = new JSONObject();
			JSONObject maintenanceFormPainting = new JSONObject();
			JSONObject maintenanceFormORing = new JSONObject();

			JSONObject maintenanceFormTestingLeakageGIFitting = new JSONObject();
			JSONObject maintenanceFormRCC = new JSONObject();
			JSONObject maintenanceFormKitchenSurakshaTube = new JSONObject();
			JSONObject maintenanceFormOtherSurakshaTube = new JSONObject();
			JSONObject maintenanceFormMakeAndGeyser = new JSONObject();
			JSONObject maintenanceFormOthers = new JSONObject();

			JSONObject maintenanceFormSignature = new JSONObject();

			MaintainanceDTO maintainanceDTO = arryMaintainance.get(i);
//			Log.d("batchSubmit", "maintainanceVO.getMaintainanceId()::: "+maintainanceDTO.getMaintainanceId());

			TestingDTO testingDTO = mainDtoService.getTestingDetailByMaintainanceID(maintainanceDTO.getMaintainanceId());

			ClampingDTO clampingDTO = mainDtoService.getClampingByMaintainanceID(maintainanceDTO.getMaintainanceId());

			PaintingDTO paintingDTO = mainDtoService.getPaintingOringByMaintainanceID(maintainanceDTO.getMaintainanceId());

			GIFittingDTO giFittingDTO = mainDtoService.getGIfittingDetailByTestingID(testingDTO.getTestingId());

			List<LeakageVO> arrayLeakage = maintainanceService.getLeakageLstByTestingID(testingDTO.getTestingId());

			RccDTO rccDTO = mainDtoService.getRCCDetailByMaintainanceID(maintainanceDTO.getMaintainanceId());

			//SurakshaTubeDTO surakshaTubeDTO = mainDtoService.getSurkshaTubeByMaintainanceID(maintainanceDTO.getMaintainanceId());
			KitchenSurakshaTubeDTO kitchenSurakshaTubeDTO = mainDtoService.getKitchenSurkshaTubeByMaintainanceID(maintainanceDTO.getMaintainanceId());

			OtherKitchenSurakshaTubeDTO otherKitchenSurakshaTubeDTO = mainDtoService.getOtherSurkshaTubeByMaintainanceID(maintainanceDTO.getMaintainanceId());

			MakeGeyserDTO makeGeyserDTO = mainDtoService.getMakeGeyserByMaitainanceId(maintainanceDTO.getMaintainanceId());

			OtherChecksDTO otherChecksDTO = mainDtoService.getOtherchecksByMaintainanceID(maintainanceDTO.getMaintainanceId());

			List<ConformanceDetailDTO> arrayConformanceDetail = maintainanceService.getAllConformanceDTOListByMaitainanceID(maintainanceDTO.getMaintainanceId());

			CustomerFeedbackDTO customerFeedbackDTO = mainDtoService.getCustomerFeedbackByMaintainaceID(maintainanceDTO.getMaintainanceId());


			//maintenanceForm -----
			maintenanceForm.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
			maintenanceForm.put(Constants.JSON_CUSTOMER_ID,maintainanceDTO.getCustomerId());
			maintenanceForm.put(Constants.JSON_STATUS_CODE_ID,maintainanceDTO.getStatusCode());
			maintenanceForm.put(Constants.JSON_DATETIME_RECORD,maintainanceDTO.getDate());
			maintenanceForm.put(Constants.JSON_COMMENTS,customerFeedbackDTO.getComments());
			maintenanceForm.put(Constants.JSON_RECEIPT_NO,customerFeedbackDTO.getReceiptNo());
			maintenanceForm.put(Constants.JSON_NOTICE_NO,customerFeedbackDTO.getNoticeNo());

			// Added by Pankit Mistry - To support Contact number and Alternate contact number
			maintenanceForm.put("ContactNo", maintainanceDTO.getContactNo());
			maintenanceForm.put("AltContactNo", maintainanceDTO.getAltContactNo());

//			MAINTENANCEFORM.PUT(CONSTANTS.JSON_START_TIME_STAMP,SHAREDPREFRENCEUTIL.GETPREFRENCE(CONTEXT, CONSTANTS.PREF_START_TIME,LONG.VALUEOF(0)));
//			MAINTENANCEFORM.PUT(CONSTANTS.JSON_END_TIME_STAMP,SHAREDPREFRENCEUTIL.GETPREFRENCE(CONTEXT,CONSTANTS.PREF_END_TIME,LONG.VALUEOF(0)));

//			Log.e("START TIME---",""+maintainanceDTO.getStartTime());
//			Log.e("END TIME---",""+maintainanceDTO.getEndTime());

			maintenanceForm.put(Constants.JSON_START_TIME_STAMP,maintainanceDTO.getStartTime());
			maintenanceForm.put(Constants.JSON_END_TIME_STAMP,maintainanceDTO.getEndTime());

			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM, maintenanceForm);

			//maintenanceForm -----
			Log.d("batchSubmit", "::: JSON_MAINTENANCE_FORM_ID"+maintainanceDTO.getMaintainanceOrderId());
			//maintenanceFormTesting -----
			maintenanceFormTesting.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
			maintenanceFormTesting.put(Constants.JSON_INITIAL_PRESSURE, testingDTO.getInitialPressure());
			maintenanceFormTesting.put(Constants.JSON_FINAL_PRESSURE, testingDTO.getFinalPressure());
			maintenanceFormTesting.put(Constants.JSON_PRESSURE_DROP,testingDTO.getPressureDrop());
			maintenanceFormTesting.put(Constants.JSON_DURATION, testingDTO.getDuration());
			maintenanceFormTesting.put(Constants.JSON_GAS_LKG_TEST_PASS, testingDTO.getGasLkgDetectionTest());
			maintenanceFormTesting.put(Constants.JSON_PRESSURE_HLN, testingDTO.getPressureType());
			System.out.println(" ********** SERVER ^^^^^^^^ LEAKAGE RECTIFIED *********** "+testingDTO.getGasLkgRectified());
			maintenanceFormTesting.put(Constants.JSON_GAS_LKG_RECTIFIED, testingDTO.getGasLkgRectified());

			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_TESTING, maintenanceFormTesting);
			//maintenanceFormTesting -----

			//maintenanceFormClamping -----
			maintenanceFormClamping.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
			maintenanceFormClamping.put(Constants.JSON_CLAMPPING_WORK_SATISFACTORY,clampingDTO.getClampingWorkSatisfactory());

			maintenanceFormClamping.put(Constants.JSON_PIPELINE_PROTECTION_CLAMP,clampingDTO.getPipelineProtectionClamp());
			maintenanceFormClamping.put(Constants.JSON_CLAMP_1BY2_C,clampingDTO.getChrgClamp1by2());
			maintenanceFormClamping.put(Constants.JSON_CLAMP_1BY2_NC,clampingDTO.getClamp1by2());
			maintenanceFormClamping.put(Constants.JSON_CLAMP1_C,clampingDTO.getChrgClamp1());
			maintenanceFormClamping.put(Constants.JSON_CLAMP1_NC,clampingDTO.getClamp1());
			maintenanceFormClamping.put(Constants.JSON_CHEESE_HEAD_SCREW_C,clampingDTO.getChrgCheeseHeadScrew());
			maintenanceFormClamping.put(Constants.JSON_CHEESE_HEAD_SCREW_NC,clampingDTO.getCheeseHeadScrew());
			maintenanceFormClamping.put(Constants.JSON_WOOD_SCREW_C,clampingDTO.getChrgWoodScrew());
			maintenanceFormClamping.put(Constants.JSON_WOOD_SCREW_NC,clampingDTO.getWoodScrew());
			maintenanceFormClamping.put(Constants.JSON_ROUL_PLUG_C, clampingDTO.getChrgRoulPlug());
			maintenanceFormClamping.put(Constants.JSON_ROUL_PLUG_NC,clampingDTO.getRoulPlug());
			maintenanceFormClamping.put(Constants.JSON_CLMAP_SPLR_1BY2,clampingDTO.getSplrClamp1by2());
			maintenanceFormClamping.put(Constants.JSON_CLMAP_SPLR_1,clampingDTO.getSplrClamp1());
			maintenanceFormClamping.put(Constants.JSON_CLMAP_SPLR_CHEESE_HEAD_SCREW,clampingDTO.getSplrCheeseHeadScrew());
			maintenanceFormClamping.put(Constants.JSON_CLMAP_SPLR_WOOD_SCREW,clampingDTO.getSplrWoodScrew());
			maintenanceFormClamping.put(Constants.JSON_CLMAP_SPLR_ROUL_PLUG,clampingDTO.getSplrRoulPlug());
			
			/*maintenanceFormClamping.put(Constants.JSON_CLAMP_1BY2, clampingDTO.getClamp1by2());
			maintenanceFormClamping.put(Constants.JSON_CLAMP1,clampingDTO.getClamp1());
			maintenanceFormClamping.put(Constants.JSON_CHEESE_HEAD_SCREW,clampingDTO.getCheeseHeadScrew());
			maintenanceFormClamping.put(Constants.JSON_WOOD_SCREW,clampingDTO.getWoodScrew());
			maintenanceFormClamping.put(Constants.JSON_ROUL_PLUG,clampingDTO.getRoulPlug());*/

			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_CLAMPING, maintenanceFormClamping);
			//maintenanceFormClamping -----


			//maintenanceFormPainting -----
			maintenanceFormPainting.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
			maintenanceFormPainting.put(Constants.JSON_PAINTING_WORK_SATISFACTORY,paintingDTO.getIsPaintingWork());
			maintenanceFormPainting.put(Constants.JSON_PAINT, paintingDTO.getPaint());

			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_PAINTING, maintenanceFormPainting);
			//maintenanceFormPainting -----

			//maintenanceFormORing -----
			maintenanceFormORing.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
			maintenanceFormORing.put(Constants.JSON_ORING_SATISFACTORY,paintingDTO.getIsOringWork());
//			Log.d("tag","JSON PAINT 1 ::::::: - "+paintingDTO.getOrMeter());
			maintenanceFormORing.put(Constants.JSON_FOR_METER, paintingDTO.getOrMeter());
//			Log.d("tag","JSON PAINT 2 ::::::: - "+paintingDTO.getOrDomRegulator());
			maintenanceFormORing.put(Constants.JSON_FOR_DOM_REGULARTOR,paintingDTO.getOrDomRegulator());
//			Log.d("tag","JSON PAINT 3 ::::::: - "+paintingDTO.getOrAudcoGland());
			maintenanceFormORing.put(Constants.JSON_FOR_AUDCO_GLAND, paintingDTO.getOrAudcoGland());

			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_ORING, maintenanceFormORing);
			//maintenanceFormORing -----

			//maintenanceFormTestingLeakage -----
			for(int k=0;k<arrayLeakage.size();k++){
				LeakageVO leakageVO = arrayLeakage.get(k);
				if(leakageVO!=null){
					JSONObject maintenanceFormTestingLeakage = new JSONObject();
					maintenanceFormTestingLeakage.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
					maintenanceFormTestingLeakage.put(Constants.JSON_LKG_ID,leakageVO.getServiceLkgId());
					maintenanceFormTestingLeakage.put(Constants.JSON_LKG_TEXT, maintainanceService.getLeakageName(leakageVO.getServiceLkgId()));
					String where = "maintainanceorderid = '"+maintainanceDTO.getMaintainanceOrderId()+"' and objectid ="+leakageVO.getServiceLkgId();
					DamageNCauseDetailVO damageVo = maintainanceService.getDetailDamageCauserByObjectNMorderID(where);
					maintenanceFormTestingLeakage.put(Constants.JSON_DAMAGE_ID,damageVo.getDamageId());
					maintenanceFormTestingLeakage.put(Constants.JSON_CAUSE_ID,damageVo.getCauseId());

					leakageArray.put(k,maintenanceFormTestingLeakage);
				}
			}
			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_TESTING_LKG, leakageArray);
			//maintenanceFormTestingLeakage -----

			//maintenanceFormTestingLeakageGIFitting -----
			if(giFittingDTO!=null){
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_WORK_ON_GAS,giFittingDTO.getIsWorking());

//				//Log.e("COMMMON SERVICE:-","ELBOW VALUE:-"+giFittingDTO.getElbow());
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_ELBOW, giFittingDTO.getElbow());
				String where = "maintainanceorderid = '"+maintainanceDTO.getMaintainanceOrderId()+"' and objectid ="+maintainanceService.getObjectIDFromGifitting(Constants.DB_REQ_ELBOW);
				DamageNCauseDetailVO damageVoElbow = maintainanceService.getDetailDamageCauserByObjectNMorderID(where);
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_ELBOW+Constants.JSON_DAMAGE_ID,damageVoElbow.getDamageId());
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_ELBOW+Constants.JSON_CAUSE_ID,damageVoElbow.getCauseId());

				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_TEE,giFittingDTO.getTee());
				String whereTee = "maintainanceorderid = '"+maintainanceDTO.getMaintainanceOrderId()+"' and objectid ="+maintainanceService.getObjectIDFromGifitting(Constants.DB_REQ_TEE);
				DamageNCauseDetailVO damageVoTee = maintainanceService.getDetailDamageCauserByObjectNMorderID(whereTee);
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_TEE+Constants.JSON_DAMAGE_ID,damageVoTee.getDamageId());
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_TEE+Constants.JSON_CAUSE_ID,damageVoTee.getCauseId());

				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_HEX_NIPPLE,giFittingDTO.getHexNipple());
				String wherehex = "maintainanceorderid = '"+maintainanceDTO.getMaintainanceOrderId()+"' and objectid ="+maintainanceService.getObjectIDFromGifitting(Constants.DB_REQ_HEX_NIPPLE);
				DamageNCauseDetailVO damageVoHex = maintainanceService.getDetailDamageCauserByObjectNMorderID(wherehex);
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_HEX_NIPPLE+Constants.JSON_DAMAGE_ID,damageVoHex.getDamageId());
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_HEX_NIPPLE+Constants.JSON_CAUSE_ID,damageVoHex.getCauseId());

				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_GI_UNION,giFittingDTO.getGIUnion());
				String whereUnion = "maintainanceorderid = '"+maintainanceDTO.getMaintainanceOrderId()+"' and objectid ="+maintainanceService.getObjectIDFromGifitting(Constants.DB_REQ_UNION);
				DamageNCauseDetailVO damageVoUnion = maintainanceService.getDetailDamageCauserByObjectNMorderID(whereUnion);
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_GI_UNION+Constants.JSON_DAMAGE_ID,damageVoUnion.getDamageId());
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_GI_UNION+Constants.JSON_CAUSE_ID,damageVoUnion.getCauseId());

				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_PLUG,giFittingDTO.getPlug());
				String wherePlug = "maintainanceorderid = '"+maintainanceDTO.getMaintainanceOrderId()+"' and objectid ="+maintainanceService.getObjectIDFromGifitting(Constants.DB_REQ_PLUG);
				DamageNCauseDetailVO damageVoPlug = maintainanceService.getDetailDamageCauserByObjectNMorderID(wherePlug);
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_PLUG+Constants.JSON_DAMAGE_ID,damageVoPlug.getDamageId());
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_PLUG+Constants.JSON_CAUSE_ID,damageVoPlug.getCauseId());

				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_GI_CAP,giFittingDTO.getGICap());
				String whereCap = "maintainanceorderid = '"+maintainanceDTO.getMaintainanceOrderId()+"' and objectid ="+maintainanceService.getObjectIDFromGifitting(Constants.DB_REQ_GI_CAP);
				DamageNCauseDetailVO damageVoCap = maintainanceService.getDetailDamageCauserByObjectNMorderID(whereCap);
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_GI_CAP+Constants.JSON_DAMAGE_ID,damageVoCap.getDamageId());
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_GI_CAP+Constants.JSON_CAUSE_ID,damageVoCap.getCauseId());

				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_GI_COUPLING,giFittingDTO.getGICoupling());
				String whereCoupling = "maintainanceorderid = '"+maintainanceDTO.getMaintainanceOrderId()+"' and objectid ="+maintainanceService.getObjectIDFromGifitting(Constants.DB_REQ_GI_COUPLING);
				DamageNCauseDetailVO damageVocoupling = maintainanceService.getDetailDamageCauserByObjectNMorderID(whereCoupling);
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_GI_COUPLING+Constants.JSON_DAMAGE_ID,damageVocoupling.getDamageId());
				maintenanceFormTestingLeakageGIFitting.put(Constants.JSON_GI_COUPLING+Constants.JSON_CAUSE_ID,damageVocoupling.getCauseId());

				//				JSONArray attribute = new JSONArray();
				//			//	for(int l = 0;l<mainDtoService.getGIfittingMasterDataSize().size(); l++){
				//					GIFittingMSTDTO dto = mainDtoService.getGIfittingMasterDataSize().get(l);
				//					
				//					JSONObject object = new JSONObject();
				//					
				//					object.put("Key", dto.getText());
				//					object.put("Value", "Value");
				//					object.put(Constants.JSON_TEE, giFittingDTO.getTee());
				//					object.put(Constants.JSON_HEX_NIPPLE, giFittingDTO.getHexNipple());
				//					object.put(Constants.JSON_GI_UNION, giFittingDTO.getGIUnion());
				//					object.put(Constants.JSON_PLUG, giFittingDTO.getPlug());
				//					object.put(Constants.JSON_GI_CAP, giFittingDTO.getGICap());
				//					object.put(Constants.JSON_GI_COUPLING, giFittingDTO.getGICoupling());
				//					
				//					attribute.put(l,object);
				//				}
				//	maintenanceFormTestingLeakageGIFitting.put("Attributes", attribute);

				maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_TESTING_LKG_GIFITTING, maintenanceFormTestingLeakageGIFitting);

			}
			//maintenanceFormTestingLeakageGIFitting -----

			//maintenanceFormRCC -----
			maintenanceFormRCC.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
			maintenanceFormRCC.put(Constants.JSON_RCC_WORK_SATISFACTORY,rccDTO.getIsWorking());
			maintenanceFormRCC.put(Constants.JSON_RCC_SANDFILLING_CEMENTING,rccDTO.getIsSandFilling());
			maintenanceFormRCC.put(Constants.JSON_MSN_NAILDIA_1BY4_C, rccDTO.getMsNailChargable());
			maintenanceFormRCC.put(Constants.JSON_MSN_NAILDIA_1BY4_NC, rccDTO.getMsNail());
			maintenanceFormRCC.put(Constants.JSON_MSNUT_5BY16_C, rccDTO.getMsNutChargable());
			maintenanceFormRCC.put(Constants.JSON_MSNUT_5BY16_NC,rccDTO.getMsNut());
			maintenanceFormRCC.put(Constants.JSON_RCC_GUARD_C, rccDTO.getRccGuardChargable());
			maintenanceFormRCC.put(Constants.JSON_RCC_GUARD_NC, rccDTO.getRccGuard());

			maintenanceFormRCC.put(Constants.JSON_RCC_SPL_MSN_NAILDIA_1BY4, rccDTO.getMsNailSuppliedBy());
			maintenanceFormRCC.put(Constants.JSON_RCC_SPLMSNUT_5BY16, rccDTO.getMsNutSuppliedBy());
			maintenanceFormRCC.put(Constants.JSON_RCC_SPL_GUARD, rccDTO.getRccGuardSuppliedBy());

			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_RCC, maintenanceFormRCC);
			//maintenanceFormRCC -----

			//maintenanceFormSurakshaTube -----
/*			maintenanceFormSurakshaTube.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
			maintenanceFormSurakshaTube.put(Constants.JSON_SURAKSHA_TUBE_REPLACED,surakshaTubeDTO.getSurakshaTubeReplaced());
//			Log.d("tag","JSON surakshaTubeDTO.getMeter1mm75() ::::::: - "+surakshaTubeDTO.getMeter1mm75());
			maintenanceFormSurakshaTube.put(Constants.JSON_METER_1MM75, surakshaTubeDTO.getMeter1mm75());
//			Log.d("tag","JSON surakshaTubeDTO.getMeter15mm79() ::::::: - "+surakshaTubeDTO.getMeter15mm79());
			maintenanceFormSurakshaTube.put(Constants.JSON_METER_15MM79,surakshaTubeDTO.getMeter15mm79());
//			Log.d("tag","JSON surakshaTubeDTO.getClampHose8mmPipe():::::: - "+surakshaTubeDTO.getClampHose8mmPipe());
			maintenanceFormSurakshaTube.put(Constants.JSON_CLAMP_HOSE_8MM_PIPE, surakshaTubeDTO.getClampHose8mmPipe());
//			Log.d("tag","JSON surakshaTubeDTO.getMeter15mm125() ::::::: - "+surakshaTubeDTO.getMeter15mm125());
			maintenanceFormSurakshaTube.put(Constants.JSON_METER_15MM125,surakshaTubeDTO.getMeter15mm125());
//			Log.d("tag","JSON RCC NAIL ::::::: - "+surakshaTubeDTO.getMeter15mm125());
			maintenanceFormSurakshaTube.put(Constants.JSON_METER_1MM125,surakshaTubeDTO.getMeter1mm125());
//			Log.d("tag","JSON RCC NAIL ::::::: - "+surakshaTubeDTO.getClampHose20mmPipe());
			maintenanceFormSurakshaTube.put(Constants.JSON_CLAMP_HOSE_20MM_PIPE, surakshaTubeDTO.getClampHose20mmPipe());*/

			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_MAIN_MAINTENANCE_FORM_ID, maintainanceDTO.getMaintainanceOrderId());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_RUBBER_TUBE_EXPIRY_DATE,kitchenSurakshaTubeDTO.getExpiryDate());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_EXPIRY_DATE_OF_REPLACE_RUBBER_TUBE, kitchenSurakshaTubeDTO.getReplaceExpiryDate());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_SURAKSHA_TUBE_REPLACED,kitchenSurakshaTubeDTO.getSurakshaTubeReplaced());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_KC_SIZE_751,kitchenSurakshaTubeDTO.getSize751c());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_K_SIZE_751,kitchenSurakshaTubeDTO.getSize751nc());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_KC_SIZE_7915,kitchenSurakshaTubeDTO.getSize7915c());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_K_SIZE_7915,kitchenSurakshaTubeDTO.getSize7915nc());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_KC_SIZE_12515,kitchenSurakshaTubeDTO.getSize12515c());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_K_SIZE_12515,kitchenSurakshaTubeDTO.getSize12515nc());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_KC_SIZE_8,kitchenSurakshaTubeDTO.getClampsize8c());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_K_SIZE_8,kitchenSurakshaTubeDTO.getClampsize8nc());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_KC_SIZE_20,kitchenSurakshaTubeDTO.getClampsize20c());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_K_SIZE_20,kitchenSurakshaTubeDTO.getClampsize20nc());

			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_KSPL_751,kitchenSurakshaTubeDTO.getKSplr1Meter75mm());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_KSPL_7915,kitchenSurakshaTubeDTO.getKSplr15Meter79mm());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_KSPL_12515,kitchenSurakshaTubeDTO.getKSplrChrg15Meter125mm());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_KSPL_8,kitchenSurakshaTubeDTO.getKSplrClampHose8mmPipe());
			maintenanceFormKitchenSurakshaTube.put(Constants.JSON_KSPL_20,kitchenSurakshaTubeDTO.getKSplrClampHose20mmPipe());

			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_SURAKSHA_TUBE_KITCHEN, maintenanceFormKitchenSurakshaTube);
			//maintenanceFormSurakshaTube -----

			//maintenanceFormOtherSurakshaTube -----
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_MAIN_MAINTENANCE_FORM_ID, maintainanceDTO.getMaintainanceOrderId());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_SURAKSHA_TUBE_REPLACED,otherKitchenSurakshaTubeDTO.getSurakshaTubeReplaced());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_C_SIZE_751,otherKitchenSurakshaTubeDTO.getSize751c());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_NC_SIZE_751,otherKitchenSurakshaTubeDTO.getSize751nc());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_C_SIZE_7915,otherKitchenSurakshaTubeDTO.getSize7915c());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_NC_SIZE_7915,otherKitchenSurakshaTubeDTO.getSize7915nc());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_C_SIZE_12515,otherKitchenSurakshaTubeDTO.getSize12515c());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_NC_SIZE_12515,otherKitchenSurakshaTubeDTO.getSize12515nc());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_C_SIZE_8,otherKitchenSurakshaTubeDTO.getClampsize8c());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_NC_SIZE_8,otherKitchenSurakshaTubeDTO.getClampsize8nc());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_C_SIZE_20,otherKitchenSurakshaTubeDTO.getClampsize20c());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_NC_SIZE_20,otherKitchenSurakshaTubeDTO.getClampsize20nc());

			maintenanceFormOtherSurakshaTube.put(Constants.JSON_OSPL_751,otherKitchenSurakshaTubeDTO.getKSplr1Meter75mm());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_OSPL_7915,otherKitchenSurakshaTubeDTO.getKSplr15Meter79mm());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_OSPL_12515,kitchenSurakshaTubeDTO.getKSplrChrg15Meter125mm());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_OSPL_8,otherKitchenSurakshaTubeDTO.getKSplrClampHose8mmPipe());
			maintenanceFormOtherSurakshaTube.put(Constants.JSON_OSPL_20,otherKitchenSurakshaTubeDTO.getKSplrClampHose20mmPipe());

			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_SURAKSHA_TUBE, maintenanceFormOtherSurakshaTube);
			//maintenanceFormOtherSurakshaTube -----


			//maintenanceFormMakeAndGeyser -----
			maintenanceFormMakeAndGeyser.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
			maintenanceFormMakeAndGeyser.put(Constants.JSON_MAKE_ID,makeGeyserDTO.getMakeId());

//			maintenanceFormMakeAndGeyser.put(Constants.JSON_MAKE_ID,makeGeyserDTO.getIsGeyserAvailable());

			maintenanceFormMakeAndGeyser.put(Constants.JSON_MAKE_OTHERS, makeGeyserDTO.getMakeotherText());
			maintenanceFormMakeAndGeyser.put(Constants.JSON_GEYSER_TYPE_ID,makeGeyserDTO.getGeyserTypeId());
			maintenanceFormMakeAndGeyser.put(Constants.JSON_GEYSER_INSIDE, makeGeyserDTO.getIsInsideBathroom());
//			Log.e("GEYSER From CommanService",""+makeGeyserDTO.getGeyserTypeId());
//			Log.e("GEYSER From CommanService",""+makeGeyserDTO.getMakeId());
//			Log.e("GEYSER From CommanService",""+makeGeyserDTO.getIsInsideBathroom());
			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_MAKE_GEYSER, maintenanceFormMakeAndGeyser);
			//maintenanceFormMakeAndGeyser -----

			//maintenanceFormOthers -----
			maintenanceFormOthers.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
			//maintenanceFormOthers.put(Constants.JSON_SUPPLIED_BUSH,otherChecksDTO.getSupplyBush());
			maintenanceFormOthers.put(Constants.JSON_RUBBER_CAP_C,otherChecksDTO.getRubberCapChargable());
			maintenanceFormOthers.put(Constants.JSON_RUBBER_CAP_NC,otherChecksDTO.getRubberCap());
			maintenanceFormOthers.put(Constants.JSON_BRASS_BALL_VALVE_C,otherChecksDTO.getBrassBallValueChargable());
			maintenanceFormOthers.put(Constants.JSON_BRASS_BALL_VALVE_NC,otherChecksDTO.getBrassBallValve());
			maintenanceFormOthers.put(Constants.JSON_BRASS_BALL_COCK_C, otherChecksDTO.getBrassBallCockChargable());
			maintenanceFormOthers.put(Constants.JSON_BRASS_BALL_COCK_NC,otherChecksDTO.getBrassBallCock());
			maintenanceFormOthers.put(Constants.JSON_SUPPLY_BUSH_C,otherChecksDTO.getChrgSupply3by4into1by2Bush());
			maintenanceFormOthers.put(Constants.JSON_SUPPLY_BUSH_NC,otherChecksDTO.getSupply3by4into1by2Bush());

			maintenanceFormOthers.put(Constants.JSON_SPLR_RUBBER_CAP,otherChecksDTO.getRubberCapSuppliedBy());
			maintenanceFormOthers.put(Constants.JSON_SPLR_BRASS_BALL_VALVE,otherChecksDTO.getBrassBallSuppliedBy());
			maintenanceFormOthers.put(Constants.JSON_SPLR_BRASS_BALL_COCK,otherChecksDTO.getBrassBallCockSuppliedBy());
			maintenanceFormOthers.put(Constants.JSON_SPLR_SUPPLY_BUSH_VALVE,otherChecksDTO.getSplrSupply3by4into1by2BushBy());
			maintenanceFormOthers.put(Constants.JSON_INSTALLATION_OF_PVC_SLEEVE, otherChecksDTO.getPvcSleeve());

			maintenanceFormOthers.put(Constants.JSON_METER_READING, otherChecksDTO.getMeterReading());

			maintenanceFormOthers.put(Constants.JSON_METER_PERFORMANCE,otherChecksDTO.getMeterPerformance());
			maintenanceFormOthers.put(Constants.JSON_METER_READABLE, otherChecksDTO.getMeterReadable());
			maintenanceFormOthers.put(Constants.JSON_GI_PIPE_INSIDE_BASEMENT, otherChecksDTO.getGIPipeInsideBasement());

			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_OTHERS, maintenanceFormOthers);
			//maintenanceFormOthers -----

			//maintenanceFormNonConformance -----
			for(int j=0;j<arrayConformanceDetail.size();j++){
				ConformanceDetailDTO conformanceDetailDTO = arrayConformanceDetail.get(j);

				JSONObject maintenanceFormNonConformance = new JSONObject();
				System.out.println(" DTO >>>>>>>>> ID --- "+ conformanceDetailDTO.getConformanceId());
				maintenanceFormNonConformance.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
				maintenanceFormNonConformance.put(Constants.JSON_NON_CONFORMANCE_ID,conformanceDetailDTO.getConformanceId());
				maintenanceFormNonConformance.put(Constants.JSON_IS_APPLICABLE, "Asd");
				maintenanceFormNonConformance.put(Constants.JSON_OTHERS,conformanceDetailDTO.getText());
				String where = "maintainanceorderid = '"+maintainanceDTO.getMaintainanceOrderId()+"' and objectid ="+conformanceDetailDTO.getConformanceId();
				DamageNCauseDetailVO damageVo = maintainanceService.getDetailDamageCauserByObjectNMorderID(where);
				maintenanceFormNonConformance.put(Constants.JSON_DAMAGE_ID,damageVo.getDamageId());
				maintenanceFormNonConformance.put(Constants.JSON_CAUSE_ID,damageVo.getCauseId());

				if(conformanceDetailDTO.getIsNc().equalsIgnoreCase("YES") && (conformanceDetailDTO.getConformanceId() >= 1))
				{
					nonConformanceArray.put(maintenanceFormNonConformance);
				}
			}
			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_NON_CONFORMANCE, nonConformanceArray);
			//maintenanceFormNonConformance -----

			//maintenanceFormSignature -----
			maintenanceFormSignature.put(Constants.JSON_MAINTENANCE_FORM_ID,maintainanceDTO.getMaintainanceOrderId());
			maintenanceFormSignature.put(Constants.JSON_SIGNATURE,customerFeedbackDTO.getSignature());

			maintenanceFormData.put(Constants.JSON_MAINTENANCE_FORM_SIGNATURE, maintenanceFormSignature);
			//maintenanceFormSignature -----

			mainArray.put(maintenanceFormData);
		}
		Utility.WriteTextFile("file4.txt","{::"+mainArray.toString()+"::}");
		//		Log.v("CommanService", "BatchSubmit JSON :::: "+mainArray);

		return mainArray;
	}
}