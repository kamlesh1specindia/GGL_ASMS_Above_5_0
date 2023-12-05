package com.spec.asms.view.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Global;
import com.spec.asms.common.Adapter.SideListAdapter;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.SecurityManager;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.FormListService;
import com.spec.asms.service.MaintainanceService;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.service.async.AsyncFormSubmit;
import com.spec.asms.service.async.AsyncGetHCL1Date;
import com.spec.asms.service.async.AsyncSyncCustomerData;
import com.spec.asms.view.MainTabActivity;
import com.spec.asms.view.ViewAssignmentActivity;
import com.spec.asms.vo.ClampingVO;
import com.spec.asms.vo.ConformanceDetailVO;
import com.spec.asms.vo.ConformanceMasterVO;
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

import org.json.JSONObject;

/**
 * A class is used to maintain different forms in Fragment.
 *
 *
 */
public class MaintanenceForm extends Fragment {

	private ProgressDialog progressDialog;

	public static ArrayList<Boolean> validateList = new ArrayList<Boolean>();
	public static ListView formListView;
	public static SideListAdapter sideListAdapter;
	//	public static Boolean isCycleRunning = false;
	private FragmentManager fragmentManager;

	private int userID;
	private int curYear;
	private int curMonth;
	private int curDay;
	private int isStatus = 1;
	private int custStatuid;
	private int id;
	private TextView customerId;
	private TextView lblCustomerID;
	private TextView lblCustomerName;
	private TextView txtContactNumber;
	private TextView lblDateNTime;
	private TextView lblStatus;
	private TextView customerName;
	private TextView lblContactNumber;
	private TextView sugTime;
	private EditText edtDate;
	private Button btnDate;
	private Button btnStatus;
	public  static Button btnHeaderProcess;
	private int[] statusid;
	private String[] status;
	private String[] statuscode;
	private String statusCode = null;
	private String customerID;
	private String customerNm;
	public static String contactNumber;
	private String mOrderid;
	private DateDialogFragment dateFragment;
	private Calendar cal;
	private MaintainanceService maintainanceService;
	private UserMasterService userMasterService;
	public View formView;
	private TestingFragment testingFragment;
	private ClampingFragment clampingFragment;
	private RccFragment rccFragment;
	private OtherSurakshaTubeFragment otherSurakshaTubeFragment;
	private KitchenSurakshaTubeFragment kitchenSurakshaTubeFragment;
	private PaintingFragment paintingFragment;
	private OtherChecksFragment otherChecksFragment;
	private ConformanceFragment conformanceFragment;
	private CustomerFeedbackFragment custFdbckFragment;
	private String user = Constants.LABEL_BLANK;
	private Button btnCustomerStatus;
	private String customerStatus;

/*	public static MaintanenceForm newInstance(String id, String name,
			int statusid, String mOrderid) {

		MainTabActivity.isBack = false;
		MaintanenceForm maintanenceForm = new MaintanenceForm();
		Bundle args = new Bundle();
		args.putString("id", id);
		args.putString("name", name);
		args.putInt("statusid", statusid);
		args.putString("morderid", mOrderid);
		maintanenceForm.setArguments(args);
		return maintanenceForm;
	}*/

	public static MaintanenceForm newInstance(String id, String name,
											  int statusid, String mOrderid,String contactNumber, String customerStatus) {

		MainTabActivity.isBack = false;
		MaintanenceForm maintanenceForm = new MaintanenceForm();
		Bundle args = new Bundle();
		args.putString("id", id);
		args.putString("name", name);
		args.putInt("statusid", statusid);
		args.putString("morderid", mOrderid);
		args.putString("contactNumber", contactNumber);
		args.putString("customerStatus", customerStatus);
		maintanenceForm.setArguments(args);
		return maintanenceForm;
	}

	@SuppressWarnings("static-access")
	public View onCreateView(android.view.LayoutInflater inflater,
							 android.view.ViewGroup container, Bundle savedInstanceState) {

		formView = inflater.inflate(R.layout.maintanenceform, null);
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::MaintanenceFragmentStarted:::");
		maintainanceService = new MaintainanceService(getActivity());
		userMasterService = new UserMasterService(getActivity());
		customerID = getArguments().getString("id");
		customerNm = getArguments().getString("name");
		custStatuid = getArguments().getInt("statusid");
		mOrderid = getArguments().getString("morderid");
		contactNumber = getArguments().getString("contactNumber");
		customerStatus = getArguments().getString("customerStatus");
		statusCode = userMasterService.getStatusCode(isStatus - 1);
		user = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.DB_USERNAME, "tech");
		System.out.println("Maintainace Form : Status Id :#####"+customerID);

		lblCustomerID = (TextView) formView.findViewById(R.id.lblHeaderCustId);
		lblCustomerName = (TextView) formView.findViewById(R.id.lblHeaderCustName);
		lblContactNumber = (TextView) formView.findViewById(R.id.lblHeaderContactNumber);
		lblDateNTime = (TextView) formView.findViewById(R.id.lblHeaderDateTime);
		lblStatus = (TextView) formView.findViewById(R.id.lblHeaderStatus);
		btnCustomerStatus = (Button) formView.findViewById(R.id.btnStatus);


		if(customerStatus != null && customerStatus.equalsIgnoreCase(Constants.STATUS_OPERATIONAL)){
			btnCustomerStatus.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.icon_operational));
		}else if(customerStatus != null && customerStatus.equalsIgnoreCase(Constants.STATUS_TEMPORARY_DISCONNECTED)){
			btnCustomerStatus.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.icon_temp_disconnect));
		}else if(customerStatus != null && customerStatus.equalsIgnoreCase(Constants.STATUS_FORCE_DISCONNECTED)){
			btnCustomerStatus.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.icon_force_disconnect ));
		}
		setHasOptionsMenu(true);
		TestingVO.cleanTestingVO();
		GIFittingVO.cleanGiFitting();
		ClampingVO.cleanClampingVO();
		PaintingVO.cleanPaintingVO();
		LeakageVO.cleanLeakage();
		RccVO.cleanRCCVO();
		MakeGeyserVO.cleanMakeGeyser();
		OtherKitchenSurakshaTubeVO.cleanOtherKitchenSurakshaTubeVO();
		KitchenSurakshaTubeVO.cleanKitchenSurakshaTubeVO();
		OtherChecksVO.cleanOtherChecks();
		ConformanceDetailVO.cleanConformanceVO();
		CustomerFeedbackVO.cleanomerFeedbackCustVO();
		TestingFragment.isElbowBtnClicked = false;
		TestingFragment.isTeeBtnClicked = false;
		TestingFragment.isHexNippleBtnClicked = false;
		TestingFragment.isUnionBtnClicked = false;
		TestingFragment.isPlugBtnClicked = false;
		TestingFragment.isGICapBtnClicked = false;
		TestingFragment.isGICouplingBtnClicked = false;
		TestingFragment.isGridEnabled = false;

		TestingFragment.itemCheckedLkg.clear();

		// TestingFragment.leakageDtlChecked.clear();

		ConformanceFragment.reasonChecked.clear();
		changeLanguage(id);

		/**
		 * If customer status is update it can allows to open controls in edit
		 * mode and set all vo's data
		 */
		if (custStatuid != userMasterService.getStatusId("OP")) {
			MaintainanceVO maintainanceVO = maintainanceService.getMaintainaceByCustomerID(Constants.TBL_DTL_MAINTAINANCE,Constants.MAINTAINANCE_FIELDS, customerID);

			System.out.println(" --- M SIZE " + maintainanceVO.getCustomerId());
			MaintainanceVO.setMaintainanceOrderId(maintainanceVO.getMaintainanceId());
			MaintainanceVO.setCustomerId(customerID);
			MaintainanceVO.setDate(maintainanceVO.getDate());
			MaintainanceVO.setTime(maintainanceVO.getTime());
			System.err.println(" DATE" + MaintainanceVO.getDate());
			System.err.println(" TIME " + MaintainanceVO.getTime());
			// MaintainanceVO.setStatusCode(custStatuid+"");
			MaintainanceVO.setStatusCode(userMasterService.getStatusCode(custStatuid));
			MaintainanceVO.setStatusId(custStatuid);
			isStatus = custStatuid;
			MaintainanceVO.setStartTime(MaintainanceVO.getStartTime());
			MaintainanceVO.setEndTime(MaintainanceVO.getEndTime());

			TestingVO testingVo = maintainanceService.getTestingDetailByMaintainanceID(maintainanceVO.getMaintainanceId());
			System.err.println(" ---- " + testingVo.getTestingId());
			TestingVO.setFinalPressure(testingVo.getFinalPressure());
			TestingVO.setInitialPressure(testingVo.getInitialPressure());
			TestingVO.setDuration(testingVo.getDuration());
			TestingVO.setGasLkgDetectionTest(testingVo.getGasLkgDetectionTest());
			TestingVO.setPressureDrop(testingVo.getPressureDrop());
			TestingVO.setPressureType(testingVo.getPressureType());

			ClampingVO clampingVO = maintainanceService.getClampingByMaintainanceID(maintainanceVO.getMaintainanceId());
			ClampingVO.setIsWorking(clampingVO.getIsWorking());
			ClampingVO.setPipelineProtectionClamp(clampingVO.getPipelineProtectionClamp());
			ClampingVO.setChrgClamp1by2(clampingVO.getChrgClamp1by2());
			ClampingVO.setClamp1by2(clampingVO.getClamp1by2());
			ClampingVO.setChrgClamp1(clampingVO.getChrgClamp1());
			ClampingVO.setClamp1(clampingVO.getClamp1());
			ClampingVO.setChrgCheeseHeadScrew(clampingVO.getChrgCheeseHeadScrew());
			ClampingVO.setCheeseHeadScrew(clampingVO.getCheeseHeadScrew());
			ClampingVO.setChrgWoodScrew(clampingVO.getChrgWoodScrew());
			ClampingVO.setWoodScrew(clampingVO.getWoodScrew());
			ClampingVO.setChrgRoulPlug(clampingVO.getChrgRoulPlug());
			ClampingVO.setRoulPlug(clampingVO.getRoulPlug());
			ClampingVO.setSplrClamp1by2(clampingVO.getSplrClamp1by2());
			ClampingVO.setSplrClamp1(clampingVO.getSplrClamp1());
			ClampingVO.setSplrCheeseHeadScrew(clampingVO.getSplrCheeseHeadScrew());
			ClampingVO.setSplrWoodScrew(clampingVO.getSplrWoodScrew());
			ClampingVO.setSplrRoulPlug(clampingVO.getSplrRoulPlug());


			/*ClampingVO.setClampHalf(clampingVO.getClampHalf());
			ClampingVO.setClampOne(clampingVO.getClampOne());
			ClampingVO.setCheeseHeadScrew(clampingVO.getCheeseHeadScrew());
			ClampingVO.setWoodScrew(clampingVO.getWoodScrew());
			ClampingVO.setRoulPlug(clampingVO.getRoulPlug());*/

			PaintingVO paintingVO = maintainanceService.getPaintingOringByMaintainanceID(maintainanceVO.getMaintainanceId());
			PaintingVO.setIsPaintingWork(paintingVO.getIsPaintingWork());
			PaintingVO.setPaint(paintingVO.getPaint());
			PaintingVO.setIsOringWork(paintingVO.getIsOringWork());
			PaintingVO.setOrMeter(paintingVO.getOrMeter());
			PaintingVO.setOrDomRegulator(paintingVO.getOrDomRegulator());
			PaintingVO.setOrAudcoGland(paintingVO.getOrAudcoGland());

			GIFittingVO giFittingVO = maintainanceService.getGIfittingDetailByTestingID(testingVo.getTestingId());
			GIFittingVO.setGicap(giFittingVO.getGicap());
			GIFittingVO.setGicoupling(giFittingVO.getGicap());
			GIFittingVO.setElbow(giFittingVO.getGicap());
			GIFittingVO.setHexNipple(giFittingVO.getGicap());
			GIFittingVO.setPlug(giFittingVO.getPlug());
			GIFittingVO.setTee(giFittingVO.getPlug());
			GIFittingVO.setUnion(giFittingVO.getPlug());
			GIFittingVO.setIsWorking(giFittingVO.getIsWorking());

			RccVO rccVO = maintainanceService.getRCCDetailByMaintainanceID(maintainanceVO.getMaintainanceId());
			RccVO.setIsWorking(rccVO.getIsWorking());
			RccVO.setIsSandFilling(rccVO.getIsSandFilling());
			RccVO.setMsNailChargable(rccVO.getMsNailChargable());
			RccVO.setMsNail(rccVO.getMsNail());
			RccVO.setMsNailSuppliedBy(rccVO.getMsNailSuppliedBy());
			RccVO.setMsNutChargable(rccVO.getMsNutChargable());
			RccVO.setMsNut(rccVO.getMsNut());
			RccVO.setMsNutSuppliedBy(rccVO.getMsNutSuppliedBy());
			RccVO.setRccGuard(rccVO.getRccGuard());
			RccVO.setRccGuardChargable(rccVO.getRccGuardChargable());
			RccVO.setRccGuardSuppliedBy(rccVO.getRccGuardSuppliedBy());

			/*SurakshaTubeVO surakshaTubeVO = maintainanceService.getSurkshaTubeByMaintainanceID(maintainanceVO.getMaintainanceId());
			SurakshaTubeVO.setIsReplaced(SurakshaTubeVO.getIsReplaced());
			SurakshaTubeVO.setSize1(surakshaTubeVO.getSize1());
			SurakshaTubeVO.setSize2(surakshaTubeVO.getSize2());
			SurakshaTubeVO.setSize3(surakshaTubeVO.getSize3());
			SurakshaTubeVO.setClampSize1(surakshaTubeVO.getClampSize1());
			SurakshaTubeVO.setClamSize2(surakshaTubeVO.getClamSize2());*/

			KitchenSurakshaTubeVO kitchenSurakshaTubeVO = maintainanceService.getSurkshaTubeByMaintainanceID(maintainanceVO.getMaintainanceId());
			KitchenSurakshaTubeVO.setExpirydate(kitchenSurakshaTubeVO.getExpirydate());
			KitchenSurakshaTubeVO.setReplaceexpirydate(kitchenSurakshaTubeVO.getReplaceexpirydate());
			KitchenSurakshaTubeVO.setIsReplaced(kitchenSurakshaTubeVO.getIsReplaced());
			KitchenSurakshaTubeVO.setSize751c(kitchenSurakshaTubeVO.getSize751c());
			KitchenSurakshaTubeVO.setSize751nc(kitchenSurakshaTubeVO.getSize751nc());
			KitchenSurakshaTubeVO.setSize7915c(kitchenSurakshaTubeVO.getSize7915c());
			KitchenSurakshaTubeVO.setSize7915nc(kitchenSurakshaTubeVO.getSize7915nc());
			KitchenSurakshaTubeVO.setSize12515c(kitchenSurakshaTubeVO.getSize12515c());
			KitchenSurakshaTubeVO.setSize12515nc(kitchenSurakshaTubeVO.getSize12515nc());
			KitchenSurakshaTubeVO.setClampsize8c(kitchenSurakshaTubeVO.getClampsize8c());
			KitchenSurakshaTubeVO.setClampsize8nc(kitchenSurakshaTubeVO.getClampsize8nc());
			KitchenSurakshaTubeVO.setClampsize20c(kitchenSurakshaTubeVO.getClampsize20c());
			KitchenSurakshaTubeVO.setClampsize20nc(kitchenSurakshaTubeVO.getClampsize20nc());
			KitchenSurakshaTubeVO.setKSplr1Meter75mm(kitchenSurakshaTubeVO.getKSplr1Meter75mm());
			KitchenSurakshaTubeVO.setKSplr15Meter79mm(kitchenSurakshaTubeVO.getKSplr15Meter79mm());
			KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(kitchenSurakshaTubeVO.getKSplrChrg15Meter125mm());
			KitchenSurakshaTubeVO.setKSplrClampHose8mmPipe(kitchenSurakshaTubeVO.getKSplrClampHose8mmPipe());
			KitchenSurakshaTubeVO.setKSplrClampHose20mmPipe(kitchenSurakshaTubeVO.getKSplrClampHose20mmPipe());


			OtherKitchenSurakshaTubeVO otherKitchenSurakshaTubeVO = maintainanceService.getOtherSurkshaTubeByMaintainanceID(maintainanceVO.getMaintainanceId());

			OtherKitchenSurakshaTubeVO.setIsReplaced(otherKitchenSurakshaTubeVO.getIsReplaced());
			OtherKitchenSurakshaTubeVO.setSize751c(otherKitchenSurakshaTubeVO.getSize751c());
			OtherKitchenSurakshaTubeVO.setSize751nc(otherKitchenSurakshaTubeVO.getSize751nc());
			OtherKitchenSurakshaTubeVO.setSize7915c(otherKitchenSurakshaTubeVO.getSize7915c());
			OtherKitchenSurakshaTubeVO.setSize7915nc(otherKitchenSurakshaTubeVO.getSize7915nc());
			OtherKitchenSurakshaTubeVO.setSize12515c(otherKitchenSurakshaTubeVO.getSize12515c());
			OtherKitchenSurakshaTubeVO.setSize12515nc(otherKitchenSurakshaTubeVO.getSize12515nc());
			OtherKitchenSurakshaTubeVO.setClampsize8c(otherKitchenSurakshaTubeVO.getClampsize8c());
			OtherKitchenSurakshaTubeVO.setClampsize8nc(otherKitchenSurakshaTubeVO.getClampsize8nc());
			OtherKitchenSurakshaTubeVO.setClampsize20c(otherKitchenSurakshaTubeVO.getClampsize20c());
			OtherKitchenSurakshaTubeVO.setClampsize20nc(otherKitchenSurakshaTubeVO.getClampsize20nc());
			OtherKitchenSurakshaTubeVO.setKSplr1Meter75mm(otherKitchenSurakshaTubeVO.getKSplr1Meter75mm());
			OtherKitchenSurakshaTubeVO.setKSplr15Meter79mm(otherKitchenSurakshaTubeVO.getKSplr15Meter79mm());
			OtherKitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(otherKitchenSurakshaTubeVO.getKSplrChrg15Meter125mm());
			OtherKitchenSurakshaTubeVO.setKSplrClampHose8mmPipe(otherKitchenSurakshaTubeVO.getKSplrClampHose8mmPipe());
			OtherKitchenSurakshaTubeVO.setKSplrClampHose20mmPipe(otherKitchenSurakshaTubeVO.getKSplrClampHose20mmPipe());


			MakeGeyserVO makeGeyserVO = maintainanceService.getMakeGeyserByMaitainanceId(maintainanceVO.getMaintainanceId());
			MakeGeyserVO.setIsGeyserAvailable(makeGeyserVO.getIsGeyserAvailable());
			MakeGeyserVO.setMakeValue(userMasterService.getMakeValue(makeGeyserVO.getMakeId()));
			MakeGeyserVO.setGeyserTypeValue(userMasterService.getGeyserValue(makeGeyserVO.getGeyserTypeId()));
			MakeGeyserVO.setMakeId(makeGeyserVO.getMakeId());
			MakeGeyserVO.setMakeGeyserId(makeGeyserVO.getMakeGeyserId());
			MakeGeyserVO.setMakeotherText(makeGeyserVO.getMakeotherText());
			MakeGeyserVO.setIsInsideBathroom(makeGeyserVO.getIsInsideBathroom());

			OtherChecksVO otherChecksVO = maintainanceService.getOtherchecksByMaintainanceID(maintainanceVO.getMaintainanceId());
			//OtherChecksVO.setSupplyBush(otherChecksVO.getSupplyBush());
			OtherChecksVO.setRubberCap(otherChecksVO.getRubberCap());
			OtherChecksVO.setRubberCapChargable(otherChecksVO.getRubberCapChargable());
			OtherChecksVO.setRubberCapSuppliedBy(otherChecksVO.getRubberCapSuppliedBy());
			OtherChecksVO.setBrassBallValue(otherChecksVO.getBrassBallValue());
			OtherChecksVO.setBrassBallValueChargable(otherChecksVO.getBrassBallValueChargable());
			OtherChecksVO.setBrassBallSuppliedBy(otherChecksVO.getBrassBallCockSuppliedBy());

			OtherChecksVO.setBrassBallCock(otherChecksVO.getBrassBallCock());
			OtherChecksVO.setBrassBallCockChargable(otherChecksVO.getBrassBallCockChargable());
			OtherChecksVO.setBrassBallCockSuppliedBy(otherChecksVO.getBrassBallCockSuppliedBy());

			OtherChecksVO.setSupply3by4into1by2Bush(otherChecksVO.getSupply3by4into1by2Bush());
			OtherChecksVO.setChrgSupply3by4into1by2Bush(otherChecksVO.getChrgSupply3by4into1by2Bush());
			OtherChecksVO.setsplrSupply3by4into1by2BushBy(otherChecksVO.getsplrSupply3by4into1by2BushBy());

			OtherChecksVO.setIsMeter(otherChecksVO.getIsMeter());
			OtherChecksVO.setMeterReading(otherChecksVO.getMeterReading());
			OtherChecksVO.setMtrPerformance(otherChecksVO.getMtrPerformance());
			OtherChecksVO.setMtrReadable(otherChecksVO.getMtrReadable());
			OtherChecksVO.setGiPipeInsidebm(otherChecksVO.getGiPipeInsidebm());
			OtherChecksVO.setPvcSleeve(otherChecksVO.getPvcSleeve());



			List<LeakageVO> arrayLeakage = maintainanceService.getLeakageLstByTestingID(testingVo.getTestingId());
			UserMasterService masterService = new UserMasterService(getActivity());
			LeakageMasterVO[] leakageArray = masterService.getLeakage().toArray(new LeakageMasterVO[0]);

			for (int i = 0; i < leakageArray.length; i++) {
				TestingFragment.itemCheckedLkg.add(i, false);
			}

			for (int i = 0; i < leakageArray.length; i++) {
				for (int j = 0; j < arrayLeakage.size(); j++) {
					if (leakageArray[i].getLeakageid() == arrayLeakage.get(j)
							.getServiceLkgId()) {
						TestingFragment.itemCheckedLkg.remove(i);
						TestingFragment.itemCheckedLkg.add(i, true);
					}
				}
			}

			ArrayList<ConformanceMasterVO> nonConformanceReasonLst =
					(ArrayList<ConformanceMasterVO>) maintainanceService.getNonConformanceReasons(Constants.TBL_MST_CONFORMANCE,Constants.CONFORMANCE_MASTER_FIELDS);

			List<ConformanceDetailVO> arrayConformanceDetail =
					maintainanceService.getAllConformanceListByMaitainanceID(maintainanceVO.getMaintainanceId());


			for (int i = 0; i < nonConformanceReasonLst.size(); i++) {
				ConformanceFragment.reasonChecked.add(i, false);
			}
			for (int i = 0; i < nonConformanceReasonLst.size(); i++) {
				for (int j = 0; j < arrayConformanceDetail.size(); j++) {
					ConformanceDetailVO.setIsNC(arrayConformanceDetail.get(j).getIsNC());
					if (nonConformanceReasonLst.get(i).getConformanceID() == arrayConformanceDetail.get(j).getServiceConformanceID()) {
						ConformanceFragment.reasonChecked.remove(i);
						ConformanceFragment.reasonChecked.add(i, true);

						if (arrayConformanceDetail.get(j).getServiceConformanceID() == 10) {
							ConformanceDetailVO.setText(arrayConformanceDetail.get(j).getText());
						}
					}
				}
			}

			CustomerFeedbackVO customerFeedbackVO = maintainanceService.getCustomerFeedbackByMaintainaceID(maintainanceVO.getMaintainanceId());

			String fullComment = CustomerFeedbackVO.getComments();
			String[] str = fullComment.split("\n");
			if(str.length > 0)
			{
				TestingVO.setRemarkLkgDescription(str[0]);
				if(str.length > 1)
				{
					CustomerFeedbackVO.setComments(str[1]);
				}else{
					CustomerFeedbackVO.setComments(Constants.LABEL_BLANK);
				}
			}
			CustomerFeedbackVO.setReceiptNo(customerFeedbackVO.getReceiptNo());
			CustomerFeedbackVO.setNoticeNo(customerFeedbackVO.getNoticeNo());
			CustomerFeedbackVO.setSignature(customerFeedbackVO.getSignature());
		}

		SharedPreferences pref = getActivity().getSharedPreferences("com.spec.asms", Context.MODE_PRIVATE);
		userID = pref.getInt("userID", 0);

		fragmentManager = getFragmentManager();
		formListView = (ListView) formView.findViewById(R.id.listMntnceForm);

		customerId = (TextView) formView.findViewById(R.id.sugHeaderCustId);
		customerName = (TextView) formView.findViewById(R.id.sugHeaderCustName);
		sugTime = (TextView) formView.findViewById(R.id.sugHeaderTime);
		txtContactNumber  = (TextView) formView.findViewById(R.id.sugHeaderContactNumber);


		customerId.setText(customerID);
		customerName.setText(customerNm);
		if(contactNumber != null && !contactNumber.equals(Constants.BLANK)){
			txtContactNumber.setText(contactNumber);
		}else{
			txtContactNumber.setText(getActivity().getResources().getString(R.string.not_available));
		}

		btnHeaderProcess = (Button)formView.findViewById(R.id.btnHeaderProcess);
		btnDate = (Button) formView.findViewById(R.id.btnHeaderDateTime);
		btnStatus = (Button) formView.findViewById(R.id.btnHeaderStatus);
		edtDate = (EditText) formView.findViewById(R.id.edtHeaderDateTime);


		LoadList loadList = new LoadList();
		loadList.execute();

		status = new String[userMasterService.getStatus().size()];
		statuscode = new String[userMasterService.getStatus().size()];
		statusid = new int[userMasterService.getStatus().size()];

		for (int i = 0; i < status.length; i++) {
			status[i] = userMasterService.getStatus().get(i).getStatusName();
			statusid[i] = userMasterService.getStatus().get(i).getId();
			statuscode[i] = userMasterService.getStatus().get(i)
					.getStatusCode();
		}

		if ((custStatuid != userMasterService.getStatusId("OP") && custStatuid != userMasterService.getStatusId("HCL1")) || user.equals("admin")) {
//			isStatus = 1;
			btnStatus.setText(status[isStatus - 1]);
			btnStatus.setEnabled(false);
			btnDate.setEnabled(false);

		} else {
			btnStatus.setText(status[isStatus - 1]);
			btnDate.setEnabled(true);
			/*
			 * Delete from Detail Damage and Cause when open in other than open mode.
			 */
			String where = "maintainanceorderid = '"+ mOrderid +"'";
			if(userMasterService.deleteUser(Constants.TBL_DTL_DAMAGE_CAUSE, where) != -1)
			{
				Log.e("MAINTAINANCE FORM", "DateTime:-"+Utility.getcurrentTimeStamp()+"DELETE FROM DETAIL DAMAGE N CAUSE");
			}
		}

		// call method to save start date and time in long format in shared
		// preference
		Utility.saveDateInSharedPref(getActivity(), Constants.PREF_START_DATE);
		Utility.saveTimeInSharedPref(getActivity(), Constants.PREF_START_TIME);

		// dropdownValues = Arrays.asList(status);
		cal = Calendar.getInstance();

		if(Global.isCycleRunning)
			btnHeaderProcess.setVisibility(View.VISIBLE);
		else
			btnHeaderProcess.setVisibility(View.INVISIBLE);


		if (custStatuid != userMasterService.getStatusId("OP")) {

			if(!MaintainanceVO.getTime().equals(Constants.BLANK)) {
				String time = MaintainanceVO.getTime();
				sugTime.setText(Utility.getConvertedTime(getActivity(), time));
			}

			if(!MaintainanceVO.getDate().equals(Constants.BLANK)) {
				Date date = new Date(MaintainanceVO.getDate());
				SimpleDateFormat simpleFormatter = new SimpleDateFormat(Constants.DATE_FORMAT);
				String strStartdt = simpleFormatter.format(date);
				System.out.println("Maintainace Form ::::: DATE From :#####" + strStartdt);
				edtDate.setText(strStartdt);
			}
		} else{
			setCurrentDate();
			sugTime.setText(Utility.getCurrentTime(getActivity()));
		}

		btnDate.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				showDialog();
			}
		});

		txtContactNumber.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				TelephonyManager tm= (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
				if(tm.getPhoneType()==TelephonyManager.PHONE_TYPE_NONE){
					Toast.makeText(getActivity(), getActivity().getString(R.string.msg_calling_not_supported),Toast.LENGTH_LONG).show();
				}else{
					Utility.makeCall(getActivity(), txtContactNumber.getText().toString());
				}

			}
		});

		btnStatus.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					new AlertDialog.Builder(getActivity()).setSingleChoiceItems(status, isStatus - 1,new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog,int whichButton) {
							btnStatus.setText(status[whichButton]);
							isStatus = statusid[whichButton];
							statusCode = userMasterService.getStatusCode(isStatus);
							MaintainanceVO.setStatusCode(statusCode);
							MaintainanceVO.setStatusId(isStatus);



							if(statusCode.equalsIgnoreCase("HCL2") && custStatuid == userMasterService.getStatusId("OP")) {
								AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
								builder.setCancelable(true);
								builder.setTitle("ASMS - Error");
								builder.setMessage("You can not select House Close Visit-2 directly. First complete House Close Visit-1.");
								builder.setPositiveButton(Constants.LABLE_OK,new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
										isStatus = userMasterService.getStatusId("COMP");
										btnStatus.setText(status[isStatus - 1]);
										statusCode = userMasterService.getStatusCode(isStatus);
										MaintainanceVO.setStatusCode(statusCode);
										MaintainanceVO.setStatusId(isStatus);
									}
								});

								AlertDialog alert = builder.create();
								alert.show();
							}else if(statusCode.equalsIgnoreCase("HCL2") && custStatuid == userMasterService.getStatusId("HCL1") ){
								String lastSubmitDateString = maintainanceService.getLastSubmitDate(Constants.TBL_DTL_MAINTAINANCE,customerID);

								if(lastSubmitDateString==null || lastSubmitDateString.isEmpty()){
									fetchLastHCLDate(mOrderid);
								}else {
									hclDateCheck(lastSubmitDateString);
								}

							} else if(statusCode.equalsIgnoreCase("HCL1") && custStatuid == userMasterService.getStatusId("HCL1")) {
								AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
								builder.setCancelable(true);
								builder.setTitle("ASMS - Error");
								builder.setMessage("House Close Visit-1 is already done for this customer.");
								builder.setPositiveButton(Constants.LABLE_OK,new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
										isStatus = userMasterService.getStatusId("COMP");
										btnStatus.setText(status[isStatus - 1]);
										statusCode = userMasterService.getStatusCode(isStatus);
										MaintainanceVO.setStatusCode(statusCode);
										MaintainanceVO.setStatusId(isStatus);
									}
								});

								AlertDialog alert = builder.create();
								alert.show();
							} else if(!(statusCode.equalsIgnoreCase("OP") || statusCode.equalsIgnoreCase("COMP")))
							{

								AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
								builder.setCancelable(true);
								builder.setTitle(Constants.LABLE_TITLE_CONFIRMATION);
								builder.setMessage("You have Selected '"+ userMasterService.getStatus(statusCode)+" '. Please Confirm.");
								builder.setPositiveButton(Constants.LABLE_OK,new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
										Utility.saveDateInSharedPref(getActivity(),Constants.PREF_END_DATE);
										Utility.saveTimeInSharedPref(getActivity(),Constants.PREF_END_TIME);
										if(contactNumber != null && !contactNumber.equals(Constants.BLANK))
											SharedPrefrenceUtil.setPrefrence(MaintanenceForm.this.getActivity(), MaintainanceVO.getCustomerId()+"c", contactNumber);

										//SharedPrefrenceUtil.setPrefrence(MaintanenceForm.this.getActivity(), MaintainanceVO.getCustomerId()+"a", edtAlternateNumber.getText().toString());
										AsyncFormSubmit asyFormSubmit = new AsyncFormSubmit(getActivity(), getFragmentManager());
										asyFormSubmit.execute();
									}
								});
								builder.setNegativeButton(Constants.LABLE_CANCEL,new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
										isStatus = userMasterService.getStatusId("COMP");
										btnStatus.setText(status[isStatus - 1]);
										statusCode = userMasterService.getStatusCode(isStatus);
										MaintainanceVO.setStatusCode(statusCode);
										MaintainanceVO.setStatusId(isStatus);

									}
								});
								AlertDialog alert = builder.create();
								alert.show();
							}

							dialog.dismiss();
						}
					}).show();

				} catch (Exception e) {
					e.printStackTrace();
					Log.d(Constants.DEBUG,"MaintanenceFormFragment:"+ Utility.convertExceptionToString(e));
				}
			}
		});

		initilizeValues();

		return formView;
	}

	private void hclDateCheck(String lastSubmitDateString){
		boolean is30DaysFromHCL1 = false;
		Date lastSubmitDate = new Date(lastSubmitDateString);
		Date todayDate =new Date();
		long difference = todayDate.getTime() - lastSubmitDate.getTime();
		long daysBetween = (int) (difference / (1000 * 60 * 60 * 24));
		if(daysBetween > 30){
			is30DaysFromHCL1 = true;
		}
		if(!is30DaysFromHCL1) {

			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setCancelable(true);
			builder.setTitle("ASMS - Error");
			builder.setMessage("You can not select House Close Visit-2 before 30 days from House Close Visit-1");
			builder.setPositiveButton(Constants.LABLE_OK, new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					isStatus = userMasterService.getStatusId("COMP");
					btnStatus.setText(status[isStatus - 1]);
					statusCode = userMasterService.getStatusCode(isStatus);
					MaintainanceVO.setStatusCode(statusCode);
					MaintainanceVO.setStatusId(isStatus);
				}
			});
			builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					isStatus = userMasterService.getStatusId("COMP");
					btnStatus.setText(status[isStatus - 1]);
					statusCode = userMasterService.getStatusCode(isStatus);
					MaintainanceVO.setStatusCode(statusCode);
					MaintainanceVO.setStatusId(isStatus);
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		}else{
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setCancelable(true);
			builder.setTitle(Constants.LABLE_TITLE_CONFIRMATION);
			builder.setMessage("You have Selected '"+ userMasterService.getStatus(statusCode)+" '. Please Confirm.");
			builder.setPositiveButton(Constants.LABLE_OK,new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					Utility.saveDateInSharedPref(getActivity(),Constants.PREF_END_DATE);
					Utility.saveTimeInSharedPref(getActivity(),Constants.PREF_END_TIME);
					if(contactNumber != null && !contactNumber.equals(Constants.BLANK))
						SharedPrefrenceUtil.setPrefrence(MaintanenceForm.this.getActivity(), MaintainanceVO.getCustomerId()+"c", contactNumber);

					//SharedPrefrenceUtil.setPrefrence(MaintanenceForm.this.getActivity(), MaintainanceVO.getCustomerId()+"a", edtAlternateNumber.getText().toString());
					AsyncFormSubmit asyFormSubmit = new AsyncFormSubmit(getActivity(), getFragmentManager());
					asyFormSubmit.execute();
				}
			});
			builder.setNegativeButton(Constants.LABLE_CANCEL,new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					isStatus = userMasterService.getStatusId("COMP");
					btnStatus.setText(status[isStatus - 1]);
					statusCode = userMasterService.getStatusCode(isStatus);
					MaintainanceVO.setStatusCode(statusCode);
					MaintainanceVO.setStatusId(isStatus);

				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		}
	}

	/*
	* Call api to check last hcl-1 date on server
	* */
	private void fetchLastHCLDate(String maintenanceOrderId){
		if(Utility.isNetAvailable(getActivity())){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(Constants.JSON_LOGIN_USERNAME, SecurityManager.encrypt(SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.DB_USERNAME, Constants.LABEL_BLANK),Constants.SALT));
			param.put(Constants.JSON_LOGIN_PASSWORD, SecurityManager.encrypt(SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.DB_PASSWORD, Constants.LABEL_BLANK),Constants.SALT));
			param.put(Constants.JSON_LOGIN_DEVICEID, SecurityManager.encrypt(Constants.LABLE_DEVICE_ID,Constants.SALT));
			param.put(Constants.JSON_MAINTENANCE_ORDER_ID,SecurityManager.encrypt(maintenanceOrderId,Constants.SALT));

			AsyncGetHCL1Date asyncGetHCL1Date = new AsyncGetHCL1Date(getActivity(),onResponseListener);
			asyncGetHCL1Date.execute(param);
		}else{
			Utility.alertDialog(
					getActivity(),
					Constants.ALERT_TITLE_NETWORK_ERROR,
					Constants.ERROR_INTERNET_CONNECTION
			);
			isStatus = userMasterService.getStatusId("COMP");
			btnStatus.setText(status[isStatus - 1]);
			statusCode = userMasterService.getStatusCode(isStatus);
			MaintainanceVO.setStatusCode(statusCode);
			MaintainanceVO.setStatusId(isStatus);
		}
	}

	protected AsyncGetHCL1Date.OnResponse onResponseListener = new AsyncGetHCL1Date.OnResponse(){
		public void onSuccess(JSONObject responce) {
			try {
				if(responce != null && responce.equals(Constants.JSON_FAILED)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.JSON_FAILED);
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_JSON_FAILED);
				}else if(responce.has(Constants.NETWORK_EXCEPTION_METHOD)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.NETWORK_EXCEPTION_METHOD);
					Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
				}else if(responce.has(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.CONNECTION_REFUSED_EXCEPTION_METHOD);
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.SOCKET_EXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.SOCKET_EXCEPTION);
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.IOEXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.IOEXCEPTION);
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.XMLPULLPARSEREXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.XMLPULLPARSEREXCEPTION);
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has("CustomerData")){
					JSONObject customerData = responce.getJSONObject("CustomerData");
					if(customerData.has("SubmittedDataTime")){
						String dateString = customerData.getString("SubmittedDataTime");
						if(dateString == null || dateString.isEmpty()) {
							Utility.alertDialog(getActivity(),Constants.ERROR_WEB_SERVICE_URL_NOT_SET ,Constants.ALERT_TITLE_DATE_NOT_FOUND);
						}else{
							hclDateCheck(dateString);
							return;
						}
					}else{
						Utility.alertDialog(getActivity(),Constants.ERROR_WEB_SERVICE_URL_NOT_SET ,Constants.ALERT_TITLE_DATE_NOT_FOUND);
					}
				}else {
					Utility.alertDialog(getActivity(),Constants.ERROR_WEB_SERVICE_URL_NOT_SET ,Constants.ALERT_TITLE_DATE_NOT_FOUND);
				}
			} catch (Exception e) {
				Log.d(Constants.DEBUG,":FileDownloader:"+ Utility.convertExceptionToString(e));
			}
			isStatus = userMasterService.getStatusId("COMP");
			btnStatus.setText(status[isStatus - 1]);
			statusCode = userMasterService.getStatusCode(isStatus);
			MaintainanceVO.setStatusCode(statusCode);
			MaintainanceVO.setStatusId(isStatus);
		}
		public void onFailure(String message) {
			Utility.alertDialog(getActivity(),Constants.ERROR_WEB_SERVICE_URL_NOT_SET ,Constants.ALERT_TITLE_DATE_NOT_FOUND);
			isStatus = userMasterService.getStatusId("COMP");
			btnStatus.setText(status[isStatus - 1]);
			statusCode = userMasterService.getStatusCode(isStatus);
			MaintainanceVO.setStatusCode(statusCode);
			MaintainanceVO.setStatusId(isStatus);
		}
	};

	/**
	 * Method is used to show dialog for displaying date.
	 *
	 *
	 */
	public void showDialog() {

		FragmentTransaction fragTransaction = getFragmentManager().beginTransaction(); // get the fragment
		dateFragment = DateDialogFragment.newInstance(getActivity(),new DateDialogFragmentListener() {

			public void updateChangedDate(int year, int month, int day) { //change month+1

				int tempMonth = month + 1;
				StringBuilder string = new StringBuilder();
//						month += 1;
				if((tempMonth) <= 9)
				{
					string.append("0"+tempMonth).append("/");
//							string.append("0"+month).append("/");
				}else{
					string.append(tempMonth).append("/");
				}

				if(day <= 9)
				{
					string.append("0"+day).append("/");
				}else{
					string.append(day).append("/");
				}
				string.append(year).append(" ");


				edtDate.setText(string.toString());
//						edtDate.setText(String.valueOf(month+1) + "/"
//								+ String.valueOf(day) + "/"
//								+ String.valueOf(year));
				System.out.println("CHANGED DATE :-"+edtDate.getText().toString());
				MaintainanceVO.setDate(edtDate.getText().toString());
				cal.set(year, month, day);
			}
		}, cal);
		dateFragment.show(fragTransaction, Constants.DATE_DIALOG_FRAGMENT);
	}

	/**
	 * Interface is a listener between the Date Dialog fragment and the activity
	 * to update the buttons date
	 *
	 */
	public interface DateDialogFragmentListener {

		public void updateChangedDate(int year, int month, int day);
	}

	/**
	 * Method used to set values for MaintainanceVO.
	 *
	 */
	private void initilizeValues() {

		MaintainanceVO.setMaintainanceOrderId(Integer.parseInt(mOrderid));
		MaintainanceVO.setCustomerId(customerID);
		MaintainanceVO.setDate(edtDate.getText().toString());
		MaintainanceVO.setTime(Utility.getcurrentTimeStamp());
		Log.e("Main**************", "DateTime:-"+Utility.getcurrentTimeStamp()+ "statusCode :::::::::: "
				+ userMasterService.getStatusCode(isStatus));
		MaintainanceVO.setStatusCode(userMasterService.getStatusCode(isStatus)+ "");
		MaintainanceVO.setStatusId(isStatus);
		MaintainanceVO.setIsSync(0);
	}

	/**
	 * Method used to set current date
	 */
	public void setCurrentDate() {

		Calendar curCal = Calendar.getInstance();
		curYear = curCal.get(Calendar.YEAR);
		curMonth = curCal.get(Calendar.MONTH);
		curDay = curCal.get(Calendar.DAY_OF_MONTH);
		curMonth += 1;
		StringBuilder string = new StringBuilder();
		if((curMonth) <= 9)
		{
			string.append("0"+curMonth).append("/");
		}else{
			string.append(curMonth).append("/");
		}

		if(curDay <= 9)
		{
			string.append("0"+curDay).append("/");
		}else{
			string.append(curDay).append("/");
		}
		string.append(curYear).append(" ");

//		StringBuilder string = new StringBuilder().append(curMonth + 1)
//				.append("/").append(curDay).append("/").append(curYear)
//				.append(" ");
		String curDate = string.toString();
		// set current date into textview
		edtDate.setText(curDate);
	}

	/**
	 * Method used to navigate between fragments on Maintenance form.
	 *
	 * @param index
	 *            : index to display specific fragment.
	 *
	 */
	private void showFragments(int index) {

		try {
			FragmentTransaction ft = fragmentManager.beginTransaction();
			switch (index) {
				case 0:
					testingFragment = TestingFragment.newInstance(custStatuid, contactNumber);
					ft.replace(R.id.frmLayoutFragment, testingFragment);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					break;
				case 1:
					clampingFragment = ClampingFragment.newInstance(custStatuid, contactNumber);
					ft.replace(R.id.frmLayoutFragment, clampingFragment);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					break;
				case 2:
					paintingFragment = PaintingFragment.newInstance(custStatuid, contactNumber);
					ft.replace(R.id.frmLayoutFragment, paintingFragment);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					break;
				case 3:
					rccFragment = RccFragment.newInstance(custStatuid, contactNumber);
					ft.replace(R.id.frmLayoutFragment, rccFragment);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					break;
				case 4:
					kitchenSurakshaTubeFragment = KitchenSurakshaTubeFragment.newInstance(custStatuid, contactNumber);
					ft.replace(R.id.frmLayoutFragment, kitchenSurakshaTubeFragment);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					break;
				case 5:
					otherSurakshaTubeFragment = OtherSurakshaTubeFragment.newInstance(custStatuid, contactNumber);
					ft.replace(R.id.frmLayoutFragment, otherSurakshaTubeFragment);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					break;
				case 6:
					MakeNGeyserFragment makeNGeyserFragment = MakeNGeyserFragment.newInstance(custStatuid, contactNumber);
					ft.replace(R.id.frmLayoutFragment, makeNGeyserFragment);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					break;
				case 7:
					otherChecksFragment = OtherChecksFragment.newInstance(custStatuid, contactNumber);
					ft.replace(R.id.frmLayoutFragment, otherChecksFragment);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					break;
				case 8:
					conformanceFragment = ConformanceFragment.newInstance(userID, custStatuid, contactNumber);
					ft.replace(R.id.frmLayoutFragment, conformanceFragment);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					break;
				case 9:
					custFdbckFragment = CustomerFeedbackFragment.newInstance(userID, custStatuid, contactNumber);
					ft.replace(R.id.frmLayoutFragment, custFdbckFragment);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					break;
				default:
					break;
			}
			if(testingFragment != null){
				testingFragment.errorMessageTesting();
			}
			if(conformanceFragment != null)
				conformanceFragment.validateConformanceDetail();


			ft.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,"MaintanenceFormFragment:"+ Utility.convertExceptionToString(e));
		}
	}

	/**
	 * A class is used to load side list in Maintainance Form.
	 *
	 *
	 */
	class LoadList extends AsyncTask<Void, Void, ArrayList<String>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(getActivity());
			progressDialog = ProgressDialog.show(getActivity(),Constants.LABEL_BLANK, Constants.LABEL_PROGRESS);
			progressDialog.show();
		}

		@Override
		protected ArrayList<String> doInBackground(Void... params) {

			ArrayList<String> arrayList = FormListService.LoadFormList(getActivity());
			if (arrayList.size() > 0) {
				return arrayList;

			} else {
				arrayList = null;
				return arrayList;
			}
		}

		@Override
		protected void onPostExecute(final ArrayList<String> result) {

			super.onPostExecute(result);
			progressDialog.dismiss();
			if (result != null) {
				sideListAdapter = new SideListAdapter(getActivity(), result);
				SideListAdapter.itemSelected.set(0, true);
				formListView.setAdapter(sideListAdapter);
				formListView.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> arg0, View arg1,
											int position, long arg3) {

//						String where = "objectid = "+maintainanceService.getObjectIDFromGifitting(Constants.DB_REQ_GI_COUPLING);
//						DamageNCauseDetailVO damageNCauseDetailVO = maintainanceService.getDetailDamageCauserByObjectNMorderID(where);
//						if (!TestingFragment.decimalValidation()) {
//							Utility.alertDialog(getActivity(),
//									Constants.ALERT_TITLE_GENERAL,
//									Constants.ERROR_ENTER_DECIMAL_VALUE);
//
//						} 
//						else if(!TestingFragment.GiCoupplingValidation(damageNCauseDetailVO.getDamageId(),damageNCauseDetailVO.getCauseId())) {
//							Utility.alertDialog(
//									getActivity(),
//									Constants.ALERT_TITLE_GENERAL,
//									Constants.ERROR_SELECT_DAMAGE_AND_CAUSE
//											+ Constants.LABEL_VALIDATE_GIFITTINGS_COUPLING);
//						}
//						else {

						sideListAdapter.notifyDataSetChanged();
						MaintanenceForm.validateList.remove(position);
						MaintanenceForm.validateList.add(position,false);


						for (int i = 0; i < arg0.getChildCount(); i++) {
							View v = arg0.getChildAt(i);
							if (i != position) {
								v.setBackgroundResource(R.drawable.left_pannel_button_normal);
								TextView tv = (TextView) v.findViewById(R.id.txtFormListRow);
								tv.setTextColor(getResources().getColor(R.color.form_blue));
							}
						}

						for (int i = 0; i < result.size(); i++) {
							SideListAdapter.itemSelected.set(i, false);
						}
						if(!Global.toValidate){
							SideListAdapter.itemSelected.set(position, true);
						}else{
							sideListAdapter.notifyDataSetChanged();
						}

						if(arg1!= null){
							arg1.setBackgroundDrawable(getResources().getDrawable(R.drawable.left_pannel_button_selected));
							TextView tv = (TextView) arg1.findViewById(R.id.txtFormListRow);
							tv.setTextColor(getResources().getColor(R.color.white));
							showFragments(position);

						}

					}
//					}
				});
				showFragments(0);


			} else {
				Utility.alertDialog(getActivity(),Constants.LABEL_DIALOG_ALERT,Constants.ERROR_LOADING_DATA);
			}
		}

	}

	private void clearFragment(){
		TestingVO.cleanTestingVO();
		GIFittingVO.cleanGiFitting();
		ClampingVO.cleanClampingVO();
		PaintingVO.cleanPaintingVO();
		RccVO.cleanRCCVO();
		LeakageVO.cleanLeakage();
		MakeGeyserVO.cleanMakeGeyser();
		KitchenSurakshaTubeVO.cleanKitchenSurakshaTubeVO();
		OtherKitchenSurakshaTubeVO.cleanOtherKitchenSurakshaTubeVO();
		OtherChecksVO.cleanOtherChecks();
		ConformanceDetailVO.cleanConformanceVO();
		CustomerFeedbackVO.cleanomerFeedbackCustVO();

		if(testingFragment != null)
			testingFragment.clearValues();
		if(clampingFragment != null)
			clampingFragment.clearValues();
		if(rccFragment != null)
			rccFragment.clearValues();
		if(paintingFragment != null)
			paintingFragment.clearValues();
		if(kitchenSurakshaTubeFragment != null)
			kitchenSurakshaTubeFragment.clearValues();
		if(otherSurakshaTubeFragment != null)
			otherSurakshaTubeFragment.clearValues();
		if(otherChecksFragment != null)
			otherChecksFragment.clearValues();
		if(custFdbckFragment != null)
			custFdbckFragment.clearValues();
	}

	public void changeLanguage(int id) {
		id = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.PREF_LANGUAGE, Constants.LANGUAGE_ENGLISH);
		switch (id) {
			case Constants.LANGUAGE_ENGLISH:
				lblCustomerID.setText(getResources().getText(R.string.title_header_customer_id_Eng));
				lblCustomerName.setText(getResources().getText(R.string.title_header_customer_name_Eng));
				lblDateNTime.setText(getResources().getText(R.string.title_header_date_time_Eng));
				lblStatus.setText(getResources().getText(R.string.title_header_status_Eng));
				lblContactNumber.setText(getResources().getText(R.string.title_header_contact_number_Eng));
				break;

			case Constants.LANGUAGE_GUJRATI:
				lblCustomerID.setTypeface(Global.typeface_Guj);
				lblCustomerName.setTypeface(Global.typeface_Guj);
				lblDateNTime.setTypeface(Global.typeface_Guj);
				lblStatus.setTypeface(Global.typeface_Guj);
				lblContactNumber.setTypeface(Global.typeface_Guj);
				lblCustomerID.setText(getResources().getText(R.string.title_header_customer_id_Guj));
				lblCustomerName.setText(getResources().getText(R.string.title_header_customer_name_Guj));
				lblDateNTime.setText(getResources().getText(R.string.title_header_date_time_Guj));
				lblStatus.setText(getResources().getText(R.string.title_header_status_Guj));
				lblContactNumber.setText(getResources().getText(R.string.title_header_contact_number_Guj));
				break;

			default:
				break;
		}
	}



	@SuppressWarnings("static-access")
	@Override
	public void onPause() {
		//MaintanenceForm.this.onPause();

//		if("OP".equals(userMasterService.getStatusCode(custStatuid)))					
//		{
//			try{
//				AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
//				alert.setTitle(Constants.LABEL_DIALOG_CONFIRM);
//				alert.setOnKeyListener(new OnKeyListener() {
//
//					public boolean onKey(DialogInterface dialog, int keyCode,
//							KeyEvent event) {
//						if (keyCode == KeyEvent.KEYCODE_BACK) {
//
//							return true;
//						}
//						return false;
//					}
//				});
//				alert.setMessage(getActivity().getResources()
//						.getString(R.string.info_backing));
//				alert.setPositiveButton(Constants.LABLE_YES,new DialogInterface.OnClickListener() {
//					
//					public void onClick(DialogInterface dialog, int which) {
//						
//						if(testingFragment != null)
//							testingFragment.clearValues();
//						if(paintingFragment != null)
//							paintingFragment.clearValues();
//						if(surakshaTubeFragment != null)
//							surakshaTubeFragment.clearValues();
//						if(otherChecksFragment != null)
//							otherChecksFragment.clearValues();
//						//MaintanenceForm.this.onStop();
//					}
//				});
//				alert.setNegativeButton(Constants.LABLE_NO,new DialogInterface.OnClickListener() {
//					
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//						
//					}
//				});
//				alert.show();
//				super.onPause();
//				}catch (Exception e) {
//					e.printStackTrace();
//				}
//		}
		super.onPause();
		System.out.println(" >>>>>>>> MAIN ON PAUSE <<<<<< ");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		clearFragment();
		System.out.println(" >>>>>>>> MAIN FORM ON DESTROY <<<<<< ");
	}

	@Override
	public void onStop() {
		super.onStop();
		System.out.println(" >>>>>>>> MAIN FORM ON STOP <<<<<< ");
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		MenuItem syncMenu = menu.findItem(R.id.toggleCleanup);
		if(syncMenu != null && syncMenu.isVisible())
			syncMenu.setEnabled(false);
	}
}