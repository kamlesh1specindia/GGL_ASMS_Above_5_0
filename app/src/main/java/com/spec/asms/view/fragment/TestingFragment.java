package com.spec.asms.view.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;




import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.LayoutParams;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Global;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.CustomerMasterService;
import com.spec.asms.service.MaintainanceDTOService;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.vo.CauseVO;
import com.spec.asms.vo.ConformanceMasterVO;
import com.spec.asms.vo.DamageVO;
import com.spec.asms.vo.GIFittingVO;
import com.spec.asms.vo.LeakageMasterVO;
import com.spec.asms.vo.LeakageVO;
import com.spec.asms.vo.MaintainanceVO;
import com.spec.asms.vo.TestingVO;

/**
 * A class is used to display Testing Form. It has methods to set values in
 * form's fields.
 *
 */
public class TestingFragment extends Fragment implements OnFocusChangeListener,OnClickListener,OnCheckedChangeListener{
	public View testing;

	public RelativeLayout rlGIFitting;
	public RelativeLayout relWhrLkg;

	public LinearLayout llWhrLkg;
	public LinearLayout llgasRectified;

	public LinearLayout llgifiting;
	public LinearLayout llgifitingDetail;
	public RelativeLayout llgasRectifiedDetail;

	public Button btnGiFittingWorkSetisfectory;
	public Button btnGasLeakageRectified;
	public Button btnNext;

	public static EditText edtInitialPressure;
	public static EditText edtFinalPressure;
	public static EditText edtDuration;
	public static EditText edtRemarkLkgDesc;

	public static CheckBox chkGiElbow;
	public static CheckBox chkGiTee;
	public static CheckBox chkGiHexNipple;
	public static CheckBox chkGiUnion;
	public static CheckBox chkGiPlug;
	public static CheckBox chkGiCap;
	public static CheckBox chkGiCouplling;

	public TextView lblLeakageRectified;
	public static TextView sugPressureDrop;
	public static TextView sugGasLeakageTestPass;
	public static TextView sugPressureType;
	public TextView lblTestingHeader;
	public TextView lblInitialPressure;
	public TextView lblFinalPressure;
	public TextView lblDuration;
	public TextView lblTestpass;
	public TextView lblPressureType;
	public TextView lblGasLeckageRectified;
	public TextView lblGasLeakageWhere;
	public TextView lblPressureDrop;
	public TextView lblElbow;
	public TextView lblGiFittingHeader;
	public TextView lblGiFittingWorkSetisfectory;
	public TextView lblGiTee;
	public TextView lblGiHexNipple;
	public TextView lblGiUnion;
	public TextView lblGiPlug;
	public TextView lblGiCap;
	public TextView lblGiCouppling;
	public TextView lblTstTestRemarkLkgDesc;
	public TextView errGiElbow;
	public TextView errGiTee;
	public TextView errGiHexNipple;
	public TextView errGiUnion;
	public TextView errGiPlug;
	public TextView errGiCap;
	public TextView errGiCoupling;
	public TextView errGiGasLickageSetisfectory;
	public TextView errInitialPressure;
	public TextView errTstInitPressurevalidate;
	public TextView errFinalPressure;
	public TextView errFinalPressurevalidate;
	public TextView errDuration;
	public TextView errIsRectified;
	public TextView wrrGiElbow;
	public TextView wrrGiTee;
	public TextView wrrGiHexNipple;
	public TextView wrrGiUnion;
	public TextView wrrGiPlug;
	public TextView wrrGiCap;
	public TextView wrrGiCoupling;

	public int id;
	public int isTesting = -1;
	public int IsGiWorkSetisfectory = -1;
	public int custStausId;
	public int selectedDamegeId = 0;
	public int selectedCauseId = 0;

	public static Boolean isGridEnabled = false;
	public static boolean decimalValidate;
	public static boolean GiCoupplingValidation;
	public static boolean isElbowBtnClicked = false;
	public static boolean isTeeBtnClicked = false;
	public static boolean isHexNippleBtnClicked = false;
	public static boolean isUnionBtnClicked = false;
	public static boolean isPlugBtnClicked = false;
	public static boolean isGICapBtnClicked = false;
	public static boolean isGICouplingBtnClicked = false;

	private GridView gridView;

	private String initialPressure = Constants.LABEL_BLANK;
	private String finalPressure = Constants.LABEL_BLANK;
	private String pressureDrop = Constants.LABEL_BLANK;
	private String strDamage = Constants.LABEL_BLANK;
	private String strCause = Constants.LABEL_BLANK;
	private String user = Constants.LABEL_BLANK;
	private String remarkLkgDescription = Constants.LABEL_BLANK;
	private String testingOption;
	private String IsGiSetisfectory;
	@SuppressWarnings("unused")
	private static String regExpression;
	public static final String format = "kk:mm:ss";
	public static String testingStatusCode;

	private FragmentManager fragmentManager;

	private String[] gasLeakage = new String[] {};
	private String[] gasTestCheckBox = new String[] {};

	public HashMap<String, Boolean> gasLeakageTest = new HashMap<String, Boolean>();

	@SuppressWarnings("unused")
	private List<String> dropdownValues;

	public static StringBuffer testingErrorMsg = new StringBuffer();

	public UserMasterService masterService;
	public CustomerMasterService custService;
	private LeakageListAdapter gridAdapter;

	private DamageVO damageVO;
	private CauseVO causeVO;

	public static LeakageMasterVO[] leakageArray = new LeakageMasterVO[] {};
	public static ArrayList<Boolean> itemCheckedLkg = new ArrayList<Boolean>();
	public static ArrayList<Boolean> leakageDtlChecked = new ArrayList<Boolean>();

	private Dialog dialog;
	private RadioGroup radGroupDamage;
	private RadioGroup radGroupCause;
	private RadioButton[] rbDamage;
	private RadioButton[] rbCause;

	private RadioGroup radGroupTestingLeakageRectified, radGroupGIFittingSatisfactory;
	private RadioButton radBtnYes,radBtnNo, radBtnGIFittingYes, radBtnGIFittingNo;

	protected PowerManager.WakeLock mWakeLock;
	private String contactNumber;

	/**
	 * A parameterized constructor
	 *
	 * @param  custStatusId : the id for which the profile is to be created
	 * @return TestingFragment object
	 */
	public static TestingFragment newInstance(int custStatusId, String contactNumber) {
		TestingFragment testingFragment = new TestingFragment();
		Bundle args = new Bundle();
		args.putInt("statusid", custStatusId);
		args.putString("contactNumber", contactNumber);
		testingFragment.setArguments(args);
		return testingFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		//super.onCreateView(inflater, container, savedInstanceState);



		System.out.println(" ON CREATE VIEW ");
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::TestingFragmentStarted:::");

		//testing = inflater.inflate(R.layout.form_testing, null);
		return inflater.inflate(R.layout.form_testing, container, false);

		//return testing;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		fragmentManager = getFragmentManager();
		custStausId = getArguments().getInt("statusid");
		contactNumber = getArguments().getString("contactNumber");
		user = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.DB_USERNAME, "tech");

		btnNext = (Button) view.findViewById(R.id.btnTestingNext);
		btnGasLeakageRectified = (Button) view.findViewById(R.id.btnTestingLeakageRectified);
		btnGiFittingWorkSetisfectory = (Button) view.findViewById(R.id.btnTstGFSatisfctry);

		sugGasLeakageTestPass = (TextView) view.findViewById(R.id.sugTstTestPass);
		sugPressureType = (TextView) view.findViewById(R.id.sugTstPressureType);
		sugPressureDrop = (TextView) view.findViewById(R.id.sugTstPressureDrop);
		lblDuration = (TextView) view.findViewById(R.id.lblTstDuration);
		lblFinalPressure = (TextView) view.findViewById(R.id.lblTstFinalPressure);
		lblGasLeckageRectified = (TextView) view.findViewById(R.id.lblTstLeakageRectified);
		lblGasLeakageWhere=(TextView)view.findViewById(R.id.lblTstTestPassReason);
		lblInitialPressure = (TextView) view.findViewById(R.id.lblTstInitPressure);
		lblPressureDrop = (TextView) view.findViewById(R.id.lblTstPressureDrop);
		lblPressureType = (TextView) view.findViewById(R.id.lblTstPressureType);
		lblTestingHeader = (TextView) view.findViewById(R.id.lblTstHeader);
		lblTestpass = (TextView) view.findViewById(R.id.lblTstTestPass);
		lblElbow = (TextView) view.findViewById(R.id.lblTstGFElbow);
		lblGiFittingHeader = (TextView) view.findViewById(R.id.lblTstGFHeadr);
		lblLeakageRectified = (TextView)view.findViewById(R.id.lblTestingLeakageRectified);
		lblGiTee = (TextView) view.findViewById(R.id.lblTstGFTee);
		lblGiCap = (TextView) view.findViewById(R.id.lblTstGFCap);
		lblGiCouppling = (TextView) view.findViewById(R.id.lblTstGFCouping);
		lblGiFittingWorkSetisfectory = (TextView) view.findViewById(R.id.lblTstGFSatisfactory);
		lblGiHexNipple = (TextView) view.findViewById(R.id.lblTstGFHexNipple);
		lblGiUnion = (TextView) view.findViewById(R.id.lblTstGFUnion);
		lblGiPlug = (TextView) view.findViewById(R.id.lblTstGFPlug);
		lblTstTestRemarkLkgDesc = (TextView)view.findViewById(R.id.lblTstTestRemarkLkgDesc);
		errDuration = (TextView) view.findViewById(R.id.errTstDuration);
		errFinalPressure = (TextView) view.findViewById(R.id.errTstFinalPressure);
		errFinalPressurevalidate = (TextView)view.findViewById(R.id.errTstFinalPressurevalidate);
		errInitialPressure = (TextView) view.findViewById(R.id.errTstInitPressure);
		errTstInitPressurevalidate = (TextView) view.findViewById(R.id.errTstInitPressurevalidate);
		errIsRectified = (TextView) view.findViewById(R.id.errTestingLeakageRectified);
		errGiCap = (TextView) view.findViewById(R.id.errTstGFCap);
		errGiCoupling = (TextView) view.findViewById(R.id.errTstGFCouping);
		errGiGasLickageSetisfectory = (TextView) view.findViewById(R.id.errTstGFSatisfctry);
		errGiElbow = (TextView) view.findViewById(R.id.errTstGFElbow);
		errGiHexNipple = (TextView) view.findViewById(R.id.errGFHexNipple);
		errGiPlug = (TextView) view.findViewById(R.id.errTstGFPlug);
		errGiTee = (TextView) view.findViewById(R.id.errTstGFTee);
		errGiUnion = (TextView) view.findViewById(R.id.errTstGFUnion);

		wrrGiCap = (TextView) view.findViewById(R.id.wrrTstGFCap);
		wrrGiCoupling = (TextView) view.findViewById(R.id.wrrTstGFCouping);
		wrrGiElbow = (TextView) view.findViewById(R.id.wrrTstGFElbow);
		wrrGiHexNipple = (TextView) view.findViewById(R.id.wrrGFHexNipple);
		wrrGiPlug = (TextView) view.findViewById(R.id.wrrTstGFPlug);
		wrrGiTee = (TextView) view.findViewById(R.id.wrrTstGFTee);
		wrrGiUnion = (TextView) view.findViewById(R.id.wrrTstGFUnion);


		edtInitialPressure = (EditText) view.findViewById(R.id.edtTstInitPressure);
		edtFinalPressure = (EditText) view.findViewById(R.id.edtTstFinalPressure);
		edtDuration = (EditText) view.findViewById(R.id.edtTstDuration);
		edtRemarkLkgDesc = (EditText) view.findViewById(R.id.edtTstTestRemarkLkgDesc);

		chkGiElbow = (CheckBox) view.findViewById(R.id.chkTstGFElbow);
		chkGiCap = (CheckBox) view.findViewById(R.id.chkTstGFCap);
		chkGiCouplling = (CheckBox) view.findViewById(R.id.chkTstGFCouping);
		chkGiHexNipple = (CheckBox) view.findViewById(R.id.chkTstGFHexNipple);
		chkGiPlug = (CheckBox) view.findViewById(R.id.chkTstGFPlug);
		chkGiTee = (CheckBox) view.findViewById(R.id.chkTstGFTee);
		chkGiUnion = (CheckBox) view.findViewById(R.id.chkTstGFUnion);

		chkGiCap.setOnClickListener(chkClick);
		chkGiCouplling.setOnClickListener(chkClick);
		chkGiElbow.setOnClickListener(chkClick);
		chkGiHexNipple.setOnClickListener(chkClick);
		chkGiPlug.setOnClickListener(chkClick);
		chkGiTee.setOnClickListener(chkClick);
		chkGiUnion.setOnClickListener(chkClick);


		rlGIFitting = (RelativeLayout) view.findViewById(R.id.rlTestingGiFitting);
		relWhrLkg  = (RelativeLayout) view.findViewById(R.id.relWhrLkg);
		llWhrLkg = (LinearLayout) view.findViewById(R.id.llWhrLkg);
		llgasRectified = (LinearLayout)view.findViewById(R.id.llgasRectified);
		llgasRectifiedDetail = (RelativeLayout)view.findViewById(R.id.llgasRectifiedDetail);

		llgifiting = (LinearLayout)view.findViewById(R.id.llGifiting);
		llgifitingDetail = (LinearLayout)view.findViewById(R.id.llgifitingDetail);

		gridView = (GridView) view.findViewById(R.id.grdViewTstGasLckge);

		radGroupTestingLeakageRectified = (RadioGroup)view.findViewById(R.id.radGroupTestingLeakageRectified);
		radBtnYes = (RadioButton)view.findViewById(R.id.radBtnYes);
		radBtnNo = (RadioButton)view.findViewById(R.id.radBtnNo);

		radGroupGIFittingSatisfactory = (RadioGroup)view.findViewById(R.id.radGroupGIFittingSatisfactory);
		radBtnGIFittingYes = (RadioButton)view.findViewById(R.id.radBtnGIFittingYes);
		radBtnGIFittingNo = (RadioButton)view.findViewById(R.id.radBtnGIFittingNo);

		dropdownValues = Arrays.asList(gasLeakage);

		gasLeakage = getActivity().getResources().getStringArray(R.array.arrayCheckBoolean);
		gasTestCheckBox = getActivity().getResources().getStringArray(R.array.array_gas_leakage_checkbox_list);

		edtInitialPressure.setOnFocusChangeListener(this);
		edtRemarkLkgDesc.setOnFocusChangeListener(this);
		edtFinalPressure.setOnFocusChangeListener(this);
		edtDuration.setOnFocusChangeListener(this);
		btnGasLeakageRectified.setOnFocusChangeListener(this);

		btnGasLeakageRectified.setOnClickListener(this);
		btnGiFittingWorkSetisfectory.setOnClickListener(this);
		radGroupTestingLeakageRectified.setOnCheckedChangeListener(this);
		radGroupGIFittingSatisfactory.setOnCheckedChangeListener(this);

		regExpression = (String) getResources().getText(R.string.regularexpression);

		masterService = new UserMasterService(getActivity());
		custService = new CustomerMasterService(getActivity());

		leakageArray = masterService.getLeakage().toArray(new LeakageMasterVO[0]);

		if (TestingVO.getGasLkgDetectionTest().toString().equals(Constants.LABLE_NO)) {
			isGridEnabled = true;
			llWhrLkg.setVisibility(View.VISIBLE);
			relWhrLkg.setVisibility(View.VISIBLE);
//			lblGasLeckageRectified.setVisibility(View.GONE);
//			radBtnNo.setVisibility(View.GONE);
//			radBtnYes.setVisibility(View.GONE);
			llgasRectified.setVisibility(View.GONE);
			llgasRectifiedDetail.setVisibility(View.GONE);
			//errIsRectified.setVisibility(View.GONE);
			//			btnTstGFCap.setClickable(true);
			//			btnTstGFCouping.setClickable(true);
			//			btnTstGFElbow.setClickable(true);
			//			btnTstGFHexNipple.setClickable(true);
			//			btnTstGFPlug.setClickable(true);
			//			btnTstGFTee.setClickable(true);
			//			btnTstGFUnion.setClickable(true);

		} else if (TestingVO.getGasLkgDetectionTest().toString().equals(Constants.LABLE_YES)) {
			isGridEnabled = false;
			llWhrLkg.setVisibility(View.GONE);
			relWhrLkg.setVisibility(View.GONE);

			llgasRectified.setVisibility(View.VISIBLE);
			llgasRectifiedDetail.setVisibility(View.VISIBLE);

//			lblGasLeckageRectified.setVisibility(View.VISIBLE);
//			radBtnNo.setVisibility(View.VISIBLE);
//			radBtnYes.setVisibility(View.VISIBLE);
//			errIsRectified.setVisibility(View.VISIBLE);
			//			btnTstGFCap.setClickable(false);
			//			btnTstGFCouping.setClickable(false);
			//			btnTstGFElbow.setClickable(false);
			//			btnTstGFHexNipple.setClickable(false);
			//			btnTstGFPlug.setClickable(false);
			//			btnTstGFTee.setClickable(false);
			//			btnTstGFUnion.setClickable(false);
		}


		setGasTestCheckBoxes(gasTestCheckBox);
		checkLanguage(id);

		if(Global.isCycleRunning)
			MaintanenceForm.btnHeaderProcess.setVisibility(View.VISIBLE);
		else
			MaintanenceForm.btnHeaderProcess.setVisibility(View.INVISIBLE);

		if ((custStausId != masterService.getStatusId("OP") && custStausId != masterService.getStatusId("HCL1"))  || user.equals("admin")) {
			isElbowBtnClicked = true;
			isTeeBtnClicked = true;
			isHexNippleBtnClicked = true;
			isUnionBtnClicked = true;
			isPlugBtnClicked = true;
			isGICapBtnClicked = true;
			isGICouplingBtnClicked = true;
			testingDisableViews();
		}

		btnNext.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				// String where =
				// "objectid = "+maintainanceService.getObjectIDFromGifitting(Constants.DB_REQ_GI_COUPLING);
				// DamageNCauseDetailVO damageNCauseDetailVO =
				// maintainanceService.getDetailDamageCauserByObjectNMorderID(where);

				//				if (!decimalValidation()) {
				//					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL,Constants.ERROR_ENTER_DECIMAL_VALUE);
				//				}

				// else if
				// (!GiCoupplingValidation(damageNCauseDetailVO.getDamageId(),damageNCauseDetailVO.getCauseId()))
				// {
				// Utility.alertDialog(
				// getActivity(),
				// Constants.ALERT_TITLE_GENERAL,
				// Constants.ERROR_SELECT_DAMAGE_AND_CAUSE
				// + Constants.LABEL_VALIDATE_GIFITTINGS_COUPLING);
				// if(checkForButtonsClicked()){
				//				else {
				//					informUser();
				Utility.changeSelectedForm(1, 0);
				Utility.changeList(1,false);
				ClampingFragment clampingFragment = ClampingFragment.newInstance(custStausId, contactNumber);
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.frmLayoutFragment, clampingFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
				//				}
			}
		});

		initializeValues();



	}

	private View.OnClickListener chkClick = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			CheckBox cb = (CheckBox) v;
			switch(v.getId()) {
				case R.id.chkTstGFCap:
					if (cb.isChecked()) {
						isGICapBtnClicked = true;
						showDialog(getActivity(),masterService.getGiFittingID(Constants.LABEL_VALIDATE_GIFITTINGS_CAP));
					} else if (!cb.isChecked()) {
						isGICapBtnClicked = false;
						String where = "objectid = " + masterService.getGiFittingID(Constants.LABEL_VALIDATE_GIFITTINGS_HEXNIPPLE)+ " and maintainanceorderid = '"+ MaintainanceVO.getMaintainanceOrderId()+ "'";
						masterService.deleteUser(Constants.TBL_DTL_DAMAGE_CAUSE, where);
					}
					break;
				case R.id.chkTstGFCouping:
					if(cb.isChecked()) {
						isGICouplingBtnClicked = true;
						showDialog(getActivity(),masterService.getGiFittingID(Constants.LABEL_VALIDATE_GIFITTINGS_COUPLING));
					}	else if(!cb.isChecked()){
						isGICouplingBtnClicked = false;
						String where = "objectid = " + masterService.getGiFittingID(Constants.LABEL_VALIDATE_GIFITTINGS_COUPLING)+ " and maintainanceorderid = '"+ MaintainanceVO.getMaintainanceOrderId()+ "'";
						masterService.deleteUser(Constants.TBL_DTL_DAMAGE_CAUSE, where);
					}
					break;
				case R.id.chkTstGFElbow:
					if(cb.isChecked()) {
						isElbowBtnClicked = true;
						showDialog(getActivity(),masterService.getGiFittingID(Constants.LABEL_VALIDATE_GIFITTINGS_ELBOW));
					} else if (!cb.isChecked()){
						isElbowBtnClicked = false;
						String where = "objectid = " + masterService.getGiFittingID(Constants.LABEL_VALIDATE_GIFITTINGS_ELBOW)+ " and maintainanceorderid = '"+ MaintainanceVO.getMaintainanceOrderId()+ "'";
						masterService.deleteUser(Constants.TBL_DTL_DAMAGE_CAUSE, where);
					}
					break;
				case R.id.chkTstGFHexNipple:
					if(cb.isChecked()) {
						isHexNippleBtnClicked = true;
						showDialog(getActivity(),masterService.getGiFittingID(Constants.LABEL_VALIDATE_GIFITTINGS_HEXNIPPLE));
					} else if(!cb.isChecked()){
						isHexNippleBtnClicked = false;
						String where = "objectid = " + masterService.getGiFittingID(Constants.LABEL_VALIDATE_GIFITTINGS_HEXNIPPLE)+ " and maintainanceorderid = '"+ MaintainanceVO.getMaintainanceOrderId()+ "'";
						masterService.deleteUser(Constants.TBL_DTL_DAMAGE_CAUSE, where);
					}
					break;
				case R.id.chkTstGFPlug:
					if(cb.isChecked()) {
						isPlugBtnClicked = true;
						showDialog(getActivity(),masterService.getGiFittingID(Constants.LABEL_VALIDATE_GIFITTINGS_PLUG));
					} else if(!cb.isChecked()){
						isPlugBtnClicked = false;
						String where = "objectid = " + masterService.getGiFittingID(Constants.LABEL_VALIDATE_GIFITTINGS_PLUG)+ " and maintainanceorderid = '"+ MaintainanceVO.getMaintainanceOrderId()+ "'";
						masterService.deleteUser(Constants.TBL_DTL_DAMAGE_CAUSE, where);
					}
					break;
				case R.id.chkTstGFTee:
					if(cb.isChecked()) {
						isTeeBtnClicked = true;
						showDialog(getActivity(),masterService.getGiFittingID(Constants.LABEL_VALIDATE_GIFITTINGS_TEE));
					} else if(!cb.isChecked()){
						isTeeBtnClicked = false;
						String where = "objectid = " + masterService.getGiFittingID(Constants.LABEL_VALIDATE_GIFITTINGS_TEE)+ " and maintainanceorderid = '"+ MaintainanceVO.getMaintainanceOrderId()+ "'";
						masterService.deleteUser(Constants.TBL_DTL_DAMAGE_CAUSE, where);
					}
					break;
				case R.id.chkTstGFUnion:
					if(cb.isChecked()) {
						isUnionBtnClicked = true;
						showDialog(getActivity(),masterService.getGiFittingID(Constants.LABEL_VALIDATE_GIFITTINGS_UNION));
					} else if(!cb.isChecked()){
						isUnionBtnClicked = false;
						String where = "objectid = " + masterService.getGiFittingID(Constants.LABEL_VALIDATE_GIFITTINGS_UNION)+ " and maintainanceorderid = '"+ MaintainanceVO.getMaintainanceOrderId()+ "'";
						masterService.deleteUser(Constants.TBL_DTL_DAMAGE_CAUSE, where);
					}
					break;
			}
		}
	};
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(group.getId()) {
			case R.id.radGroupGIFittingSatisfactory:
				switch(checkedId)
				{
					case R.id.radBtnGIFittingYes:
//					edtGiCap.setEnabled(false);
//					edtGiCouplling.setEnabled(false);
//					edtGiHexNipple.setEnabled(false);
//					edtGiPlug.setEnabled(false);
//					edtGiTee.setEnabled(false);
//					edtGiUnion.setEnabled(false);
						chkGiCap.setEnabled(false);
						chkGiCouplling.setEnabled(false);
						chkGiElbow.setEnabled(false);
						chkGiHexNipple.setEnabled(false);
						chkGiPlug.setEnabled(false);
						chkGiTee.setEnabled(false);
						chkGiUnion.setEnabled(false);
//					llgifiting.setVisibility(View.GONE);
//					llgifitingDetail.setVisibility(View.GONE);
						hideGIFittingLayout();
						//RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,100);
						//rlGIFitting.setLayoutParams(rel_btn);
//					edtGiCap.setText(Constants.LABEL_BLANK);
//					edtGiCouplling.setText(Constants.LABEL_BLANK);
//					edtGiElbow.setText(Constants.LABEL_BLANK);
//					edtGiHexNipple.setText(Constants.LABEL_BLANK);
//					edtGiPlug.setText(Constants.LABEL_BLANK);
//					edtGiTee.setText(Constants.LABEL_BLANK);
//					edtGiUnion.setText(Constants.LABEL_BLANK);
						chkGiCap.setChecked(false);
						chkGiCouplling.setChecked(false);
						chkGiElbow.setChecked(false);
						chkGiHexNipple.setChecked(false);
						chkGiPlug.setChecked(false);
						chkGiTee.setChecked(false);
						chkGiUnion.setChecked(false);

						GIFittingVO.setElbow("");
						GIFittingVO.setTee("");
						GIFittingVO.setHexNipple("");
						GIFittingVO.setUnion("");
						GIFittingVO.setPlug("");
						GIFittingVO.setGicap("");
						GIFittingVO.setGicoupling("");

						isGICapBtnClicked = false;
						isGICouplingBtnClicked = false;
						isElbowBtnClicked = false;
						isPlugBtnClicked = false;
						isUnionBtnClicked = false;
						isTeeBtnClicked = false;
						isHexNippleBtnClicked = false;
						//							isGridEnabled = true;

						errorMessageTesting();
						break;

					case R.id.radBtnGIFittingNo:
//					edtGiElbow.setEnabled(true);
//					edtGiCap.setEnabled(true);
//					edtGiCouplling.setEnabled(true);
//					edtGiHexNipple.setEnabled(true);
//					edtGiPlug.setEnabled(true);
//					edtGiTee.setEnabled(true);
//					edtGiUnion.setEnabled(true);
						chkGiElbow.setEnabled(true);
						chkGiCap.setEnabled(true);
						chkGiCouplling.setEnabled(true);
						chkGiHexNipple.setEnabled(true);
						chkGiPlug.setEnabled(true);
						chkGiTee.setEnabled(true);
						chkGiUnion.setEnabled(true);
//					llgifiting.setVisibility(View.VISIBLE);
//					llgifitingDetail.setVisibility(View.VISIBLE);
						showGIFittingLayout();
						//RelativeLayout.LayoutParams rel_btn1= new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
						//rlGIFitting.setLayoutParams(rel_btn1);

						//								isGridEnabled = false;
						break;
				}
//			GIFittingVO.setIsWorking(gasLeakage[whichButton]);			
				break;
			case R.id.radGroupTestingLeakageRectified:
				switch(checkedId)
				{
					case R.id.radBtnYes:
						Log.d("Testing Fragment","IsGasLeakageRectified ::: " + radBtnYes.getText());
						btnGasLeakageRectified.setText(radBtnYes.getText().toString());
						TestingVO.setGasLkgRectified(radBtnYes.getText().toString());
						break;

					case R.id.radBtnNo:
						Log.d("Testing Fragment","IsGasLeakageRectified ::: " + radBtnNo.getText());
						btnGasLeakageRectified.setText(radBtnNo.getText().toString());
						TestingVO.setGasLkgRectified(radBtnNo.getText().toString());
						break;
				}
				break;
		}
	}



	// public boolean checkForButtonsClicked(){
	// if(isElbowBtnClicked == true && isTeeBtnClicked == true &&
	// isHexNippleBtnClicked == true && isUnionBtnClicked == true &&
	// isPlugBtnClicked == true && isGICapBtnClicked == true &&
	// isGICouplingBtnClicked == true){
	// return true;
	// } else {
	// informUser();
	// return false;
	// }
	// }

	//	public void informUser() {
	//		if (!edtGiElbow.getText().toString().equals(Constants.LABEL_BLANK)
	//				&& btnGiFittingWorkSetisfectory.getText().toString()
	//						.equals("NO")) {
	//			if (!isElbowBtnClicked)
	//				errGiElbow.setText("Please click here");
	//			else
	//				errGiElbow.setText("");
	//		}
	//		if (!edtGiTee.getText().toString().equals(Constants.LABEL_BLANK)
	//				&& btnGiFittingWorkSetisfectory.getText().toString()
	//						.equals("NO")) {
	//			if (!isTeeBtnClicked)
	//				errGiTee.setText("Please click here");
	//			else
	//				errGiTee.setText("");
	//		}
	//		if (!edtGiHexNipple.getText().toString().equals(Constants.LABEL_BLANK)
	//				&& btnGiFittingWorkSetisfectory.getText().toString()
	//						.equals("NO")) {
	//			if (!isHexNippleBtnClicked)
	//				errGiHexNipple.setText("Please click here");
	//			else
	//				errGiHexNipple.setText("");
	//		}
	//		if (!edtGiUnion.getText().toString().equals(Constants.LABEL_BLANK)
	//				&& btnGiFittingWorkSetisfectory.getText().toString()
	//						.equals("NO")) {
	//			if (!isUnionBtnClicked)
	//				errGiUnion.setText("Please click here");
	//			else
	//				errGiUnion.setText("");
	//		}
	//		if (!edtGiPlug.getText().toString().equals(Constants.LABEL_BLANK)
	//				&& btnGiFittingWorkSetisfectory.getText().toString()
	//						.equals("NO")) {
	//			if (!isPlugBtnClicked)
	//				errGiPlug.setText("Please click here");
	//			else
	//				errGiPlug.setText("");
	//		}
	//		if (!edtGiCap.getText().toString().equals(Constants.LABEL_BLANK)
	//				&& btnGiFittingWorkSetisfectory.getText().toString()
	//						.equals("NO")) {
	//			if (!isGICapBtnClicked)
	//				errGiCap.setText("Please click here");
	//			else
	//				errGiCap.setText("");
	//		}
	//		if (!edtGiCouplling.getText().toString().equals(Constants.LABEL_BLANK)
	//				&& btnGiFittingWorkSetisfectory.getText().toString()
	//						.equals("NO")) {
	//			if (!isGICouplingBtnClicked)
	//				errGiCoupling.setText("Please click here");
	//			else
	//				errGiCoupling.setText("");
	//		}
	//
	//	}

	public void showDialog(Context context, final int position) {
		try {
			dialog = new Dialog(context);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.chk_dialog);

			radGroupDamage = (RadioGroup) dialog.findViewById(R.id.radGroupDamage);
			radGroupCause = (RadioGroup) dialog.findViewById(R.id.radGroupCause);

			List<DamageVO> listDamageVOs = masterService.getDamageInfo(position);
			List<CauseVO> listCauseVOs = masterService.getCauseInfo(position);

			// add radio buttons
			rbDamage = new RadioButton[listDamageVOs.size()];
			for (int i = 0; i < listDamageVOs.size(); i++) {

				damageVO = listDamageVOs.get(i);
				rbDamage[i] = new RadioButton(context);
				rbDamage[i].setLayoutParams(new RadioGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				rbDamage[i].setText(Constants.LABEL_BLANK+ damageVO.getFieldName());
				rbDamage[i].setTextColor(Color.BLACK);
				rbDamage[i].setSingleLine();
				rbDamage[i].setId(i);
				rbDamage[i].setTextSize(15);
				rbDamage[0].setChecked(true);
				radGroupDamage.addView(rbDamage[i]); // the RadioButtons are

				// added to the radioGroup instead of the layout
				strDamage = rbDamage[0].getText().toString();
			}

			// add radio buttons
			rbCause = new RadioButton[listCauseVOs.size()];
			for (int i = 0; i < listCauseVOs.size(); i++) {

				causeVO = listCauseVOs.get(i);
				rbCause[i] = new RadioButton(context);
				rbCause[i].setLayoutParams(new RadioGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				rbCause[i].setText(Constants.LABEL_BLANK+ causeVO.getFieldName());
				rbCause[i].setTextColor(Color.BLACK);
				rbCause[i].setSingleLine();
				rbCause[i].setId(i);
				rbCause[i].setTextSize(15);
				rbCause[0].setChecked(true);
				radGroupCause.addView(rbCause[i]); // the RadioButtons are added

				// to the radioGroup instead of the layout
				strCause = rbCause[0].getText().toString();
			}

			radGroupDamage.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				public void onCheckedChanged(RadioGroup group,int checkedId) {

					for (int i = 0; i < group.getChildCount(); i++) {
						RadioButton btn = (RadioButton) group.getChildAt(i);

						if (btn.getId() == checkedId) {
							strDamage = btn.getText().toString();
							Log.d("FIrstActivity", "Radio1 Text ::: "+ strDamage);
							Log.d("FIrstActivity","Radio1 Text btn.getId() ::: "+ btn.getId());

							// do something with text
							return;
						}
					}
				}
			});

			radGroupCause.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

				public void onCheckedChanged(RadioGroup rg,int checkedId) {
					for (int i = 0; i < rg.getChildCount(); i++) {
						RadioButton btn = (RadioButton) rg.getChildAt(i);

						if (btn.getId() == checkedId) {
							strCause = btn.getText().toString();
							Log.d("FIrstActivity", "Radio2 Text ::: "+ strCause);
							Log.d("FIrstActivity","Radio2 Text btn.getId() ::: "+ btn.getId());

							// do something with text
							return;
						}
					}
				}
			});

			Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
			btnOk.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					dialog.dismiss();
					Log.d("FIrstActivity","===========================================");
					Log.d("FIrstActivity", "Radio1 Text in ok ::: " + strDamage);
					Log.d("FIrstActivity", "Radio2 Text in ok ::: " + strCause);
					Log.d("FIrstActivity","===========================================");
					Log.d("FIrstActivity", "selected field id ::: " + position);

					selectedDamegeId = masterService.getDamageID(strDamage,position);
					selectedCauseId = masterService.getCauseID(strCause,position);

					Log.d("FIrstActivity","Radio1 Text damageVO.getDamageId() ::: "+ selectedDamegeId);
					Log.d("FIrstActivity","Radio2 Text causeVO.getCauseId() ::: "+ selectedCauseId);

					Log.d("FIrstActivity","===========================================");

					MaintainanceDTOService maintainanceDTOService = new MaintainanceDTOService(getActivity());
					List<Integer> giFittingDatas = maintainanceDTOService.getGIfittingMasterSize();

					Log.d("FIrstActivity","======================= giFittingDatas.size() ===================="+ giFittingDatas.size());
					Collections.sort(giFittingDatas);

					if(isElbowBtnClicked){
						//						errGiElbow.setText(Utility.giFittings(getActivity(), edtGiElbow.getText().toString(), Constants.ELBOW));
					}

					if(isGICapBtnClicked){
						//						errGiCap.setText(Utility.giFittings(getActivity(), edtGiCap.getText().toString(), -1));
					}

					if(isGICouplingBtnClicked){
						//						errGiCoupling.setText(Utility.giFittings(getActivity(),edtGiCouplling.getText().toString(), -1));
					}

					if(isHexNippleBtnClicked){
						//						errGiHexNipple.setText(Utility.giFittings(getActivity(),edtGiHexNipple.getText().toString(), Constants.HEXNIPPLE));
					}

					if(isPlugBtnClicked){
						//						errGiPlug.setText(Utility.giFittings(getActivity(), edtGiPlug.getText().toString(), Constants.PLUG));
					}

					if(isTeeBtnClicked){
						//						errGiTee.setText(Utility.giFittings(getActivity(), edtGiTee.getText().toString(), Constants.TEE));
					}

					if(isUnionBtnClicked){
						//						errGiUnion.setText(Utility.giFittings(getActivity(), edtGiUnion.getText().toString(), Constants.UNION));
					}

					if (Collections.binarySearch(giFittingDatas, position) >= 0) {
						String where = "objectid = " + position+ " and maintainanceorderid = '"+ MaintainanceVO.getMaintainanceOrderId() + "'";
						masterService.deleteUser(Constants.TBL_DTL_DAMAGE_CAUSE, where);
					}

					ContentValues cv = new ContentValues();
					cv.put(Constants.DB_OBJECT_ID, position);
					cv.put(Constants.DB_DAMAGE_ID, selectedDamegeId);
					cv.put(Constants.DB_CAUSE_ID, selectedCauseId);
					cv.put(Constants.DB_MAINTAINANCE_ORDER_ID,MaintainanceVO.getMaintainanceOrderId());

					if (masterService.insertUser(Constants.TBL_DTL_DAMAGE_CAUSE, cv) != -1) {
						//Log.e(Constants.TAG_LOGIN_ACTIVITY, "userInsert");
					}
					//	errorMessageTesting();
				}
			});
			dialog.show();

		} catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,"TestingFragment:"+ Utility.convertExceptionToString(e));
		} finally {
			selectedDamegeId = 0;
			selectedCauseId = 0;
			// strDamage = Constants.LABEL_BLANK;
			// strCause = Constants.LABEL_BLANK;
		}
	}

	/**
	 * Method to disable all controls when open form in edit mode.
	 *
	 * @return void
	 */
	public void testingDisableViews() {

		btnGasLeakageRectified.setEnabled(false);
		radGroupTestingLeakageRectified.setEnabled(false);
		radBtnYes.setEnabled(false);
		radBtnNo.setEnabled(false);
		edtInitialPressure.setEnabled(false);
		edtFinalPressure.setEnabled(false);
		edtDuration.setEnabled(false);
		edtRemarkLkgDesc.setEnabled(false);

		sugGasLeakageTestPass.setEnabled(false);
		sugPressureType.setEnabled(false);
		sugPressureDrop.setEnabled(false);
//		edtGiElbow.setEnabled(false);
//		edtGiCap.setEnabled(false);
//		edtGiCouplling.setEnabled(false);
//		edtGiHexNipple.setEnabled(false);
//		edtGiPlug.setEnabled(false);
//		edtGiTee.setEnabled(false);
//		edtGiUnion.setEnabled(false);
//		gridView.setEnabled(false);
		radBtnGIFittingNo.setEnabled(false);
		radBtnGIFittingYes.setEnabled(false);
		chkGiElbow.setEnabled(false);
		chkGiCap.setEnabled(false);
		chkGiCouplling.setEnabled(false);
		chkGiHexNipple.setEnabled(false);
		chkGiPlug.setEnabled(false);
		chkGiTee.setEnabled(false);
		chkGiUnion.setEnabled(false);
		gridView.setEnabled(false);
//		btnGiFittingWorkSetisfectory.setEnabled(false);

	}

	public void onClick(View v) {
		if (v == btnGasLeakageRectified) {
			try {
				new AlertDialog.Builder(getActivity()).setSingleChoiceItems(gasLeakage, isTesting,new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int whichButton) {

						btnGasLeakageRectified.setText(gasLeakage[whichButton]);
						isTesting = whichButton;
						System.out.println(" ---- IS GAS LEA ------ "+gasLeakage[whichButton]);
						TestingVO.setGasLkgRectified(gasLeakage[whichButton]);
						dialog.dismiss();
					}
				}).show();
			} catch (Exception e) {
				e.printStackTrace();
				Log.d(Constants.DEBUG,"TestingFragment:"+ Utility.convertExceptionToString(e));
			}

		} 
		/*
		else if (v == btnGiFittingWorkSetisfectory) {
			try {
				new AlertDialog.Builder(getActivity()).setSingleChoiceItems(gasLeakage, IsGiWorkSetisfectory,new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int whichButton) {

						btnGiFittingWorkSetisfectory.setText(gasLeakage[whichButton]);
						IsGiWorkSetisfectory = whichButton;

						if (gasLeakage[whichButton].equals("NO")) {

							edtGiElbow.setEnabled(true);
							edtGiCap.setEnabled(true);
							edtGiCouplling.setEnabled(true);
							edtGiHexNipple.setEnabled(true);
							edtGiPlug.setEnabled(true);
							edtGiTee.setEnabled(true);
							edtGiUnion.setEnabled(true);

							//								isGridEnabled = false;
							btnTstGFCap.setClickable(true);
							btnTstGFCouping.setClickable(true);
							btnTstGFElbow.setClickable(true);
							btnTstGFHexNipple.setClickable(true);
							btnTstGFPlug.setClickable(true);
							btnTstGFTee.setClickable(true);
							btnTstGFUnion.setClickable(true);

						} else {
							edtGiElbow.setEnabled(false);
							edtGiCap.setEnabled(false);
							edtGiCouplling.setEnabled(false);
							edtGiHexNipple.setEnabled(false);
							edtGiPlug.setEnabled(false);
							edtGiTee.setEnabled(false);
							edtGiUnion.setEnabled(false);

							edtGiCap.setText(Constants.LABEL_BLANK);
							edtGiCouplling.setText(Constants.LABEL_BLANK);
							edtGiElbow.setText(Constants.LABEL_BLANK);
							edtGiHexNipple.setText(Constants.LABEL_BLANK);
							edtGiPlug.setText(Constants.LABEL_BLANK);
							edtGiTee.setText(Constants.LABEL_BLANK);
							edtGiUnion.setText(Constants.LABEL_BLANK);



							//							isGridEnabled = true;
							btnTstGFCap.setClickable(false);
							btnTstGFCouping.setClickable(false);
							btnTstGFElbow.setClickable(false);
							btnTstGFHexNipple.setClickable(false);
							btnTstGFPlug.setClickable(false);
							btnTstGFTee.setClickable(false);
							btnTstGFUnion.setClickable(false);
												errorMessageTesting();
							
						}
						GIFittingVO.setIsWorking(gasLeakage[whichButton]);						
						dialog.dismiss();
					}
				}).show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		*/
	}

	/**
	 * Method used to set values for field and controls.
	 *
	 * @return void
	 *
	 */
	private void initializeValues() {

		testingOption = TestingVO.getGasLkgRectified();
		if (testingOption.equals(Constants.LABEL_BLANK)) {
			testingOption = getActivity().getResources().getString(R.string.title_select_option);
			btnGasLeakageRectified.setText(testingOption);
			llgasRectified.setVisibility(View.GONE);
			llgasRectifiedDetail.setVisibility(View.GONE);
			radBtnYes.setChecked(false);
			radBtnNo.setChecked(false);
//			radBtnNo.setEnabled(false);
//			radBtnYes.setEnabled(false);
			//	errIsRectified.setVisibility(View.VISIBLE);

		} else {
			btnGasLeakageRectified.setText(testingOption);
			if("YES".equals(testingOption))
			{
				isTesting = 0;
				llgasRectified.setVisibility(View.GONE);
				llgasRectifiedDetail.setVisibility(View.GONE);
//				radBtnNo.setEnabled(true);
//				radBtnYes.setEnabled(true);
				radBtnYes.setChecked(true);
				radBtnNo.setChecked(false);
				lblLeakageRectified.setText("YES");

			}
			else if("NO".equals(testingOption))
			{
				isTesting = 1;
				llgasRectified.setVisibility(View.VISIBLE);
				llgasRectifiedDetail.setVisibility(View.VISIBLE);
//				radBtnNo.setEnabled(true);
//				radBtnYes.setEnabled(true);
				radBtnNo.setChecked(true);
				radBtnYes.setChecked(false);
				lblLeakageRectified.setText("NO");
			}


		}

		IsGiSetisfectory = GIFittingVO.getIsWorking();

		if (IsGiSetisfectory.equals(Constants.LABEL_BLANK)) {
			IsGiSetisfectory = getActivity().getResources().getString(R.string.title_select_option);
			btnGiFittingWorkSetisfectory.setText(IsGiSetisfectory);
			radBtnGIFittingNo.setChecked(false);
			radBtnGIFittingYes.setChecked(false);
			errGiGasLickageSetisfectory.setText(Constants.ERROR_MARK);

		} else {
			btnGiFittingWorkSetisfectory.setText(IsGiSetisfectory);
			errGiGasLickageSetisfectory.setText(Constants.LABEL_BLANK);

			if (IsGiSetisfectory.equals("NO")) {
				radBtnGIFittingNo.setChecked(true);
				radBtnGIFittingYes.setChecked(false);
//				edtGiElbow.setEnabled(true);
//				edtGiCap.setEnabled(true);
//				edtGiCouplling.setEnabled(true);
//				edtGiHexNipple.setEnabled(true);
//				edtGiPlug.setEnabled(true);
//				edtGiTee.setEnabled(true);
//				edtGiUnion.setEnabled(true);

				chkGiElbow.setEnabled(true);
				chkGiCap.setEnabled(true);
				chkGiCouplling.setEnabled(true);
				chkGiHexNipple.setEnabled(true);
				chkGiPlug.setEnabled(true);
				chkGiTee.setEnabled(true);
				chkGiUnion.setEnabled(true);

			} else if (IsGiSetisfectory.equals("YES")) {
				radBtnGIFittingNo.setChecked(false);
				radBtnGIFittingYes.setChecked(true);
//				edtGiElbow.setEnabled(false);
//				edtGiCap.setEnabled(false);
//				edtGiCouplling.setEnabled(false);
//				edtGiHexNipple.setEnabled(false);
//				edtGiPlug.setEnabled(false);
//				edtGiTee.setEnabled(false);
//				edtGiUnion.setEnabled(false);
				chkGiElbow.setEnabled(false);
				chkGiCap.setEnabled(false);
				chkGiCouplling.setEnabled(false);
				chkGiHexNipple.setEnabled(false);
				chkGiPlug.setEnabled(false);
				chkGiTee.setEnabled(false);
				chkGiUnion.setEnabled(false);

			}
			if (custStausId != masterService.getStatusId("OP") && custStausId != masterService.getStatusId("HCL1")) {
//				edtGiElbow.setEnabled(false);
//				edtGiCap.setEnabled(false);
//				edtGiCouplling.setEnabled(false);
//				edtGiHexNipple.setEnabled(false);
//				edtGiPlug.setEnabled(false);
//				edtGiTee.setEnabled(false);
//				edtGiUnion.setEnabled(false);
				chkGiElbow.setEnabled(false);
				chkGiCap.setEnabled(false);
				chkGiCouplling.setEnabled(false);
				chkGiHexNipple.setEnabled(false);
				chkGiPlug.setEnabled(false);
				chkGiTee.setEnabled(false);
				chkGiUnion.setEnabled(false);
			}
		}
		edtInitialPressure.setText(TestingVO.getInitialPressure());
		edtFinalPressure.setText(TestingVO.getFinalPressure());
		sugPressureDrop.setText(TestingVO.getPressureDrop());
		edtDuration.setText(TestingVO.getDuration());
		edtRemarkLkgDesc.setText(TestingVO.getRemarkLkgDescription());
		sugGasLeakageTestPass.setText(TestingVO.getGasLkgDetectionTest());
		sugPressureType.setText(TestingVO.getPressureType());
//		edtGiCap.setText(GIFittingVO.getGicap());
//		edtGiCouplling.setText(GIFittingVO.getGicoupling());
//		edtGiElbow.setText(GIFittingVO.getElbow());
//		edtGiHexNipple.setText(GIFittingVO.getHexNipple());
//		edtGiPlug.setText(GIFittingVO.getPlug());
//		edtGiTee.setText(GIFittingVO.getTee());
//		edtGiUnion.setText(GIFittingVO.getUnion());
		chkGiCap.setChecked((!(GIFittingVO.getGicap() == ""))? (Integer.parseInt(GIFittingVO.getGicap()) == 0  ? false : true) : false);
		chkGiCouplling.setChecked((!(GIFittingVO.getGicoupling() == "")) ? (Integer.parseInt(GIFittingVO.getGicoupling()) == 0 ? false : true) : false);
		chkGiElbow.setChecked((!(GIFittingVO.getElbow() == "")) ? (Integer.parseInt(GIFittingVO.getElbow()) == 0 ? false : true) : false);
		chkGiHexNipple.setChecked((!(GIFittingVO.getHexNipple() == "")) ? (Integer.parseInt(GIFittingVO.getHexNipple()) == 0 ? false : true) : false);
		chkGiPlug.setChecked((!(GIFittingVO.getPlug() == "")) ? (Integer.parseInt(GIFittingVO.getPlug()) == 0 ? false : true) : false);
		chkGiTee.setChecked((!(GIFittingVO.getTee() == "")) ? (Integer.parseInt(GIFittingVO.getTee()) == 0  ? false : true) : false);
		chkGiUnion.setChecked((!(GIFittingVO.getUnion() == "")) ? (Integer.parseInt(GIFittingVO.getUnion()) == 0 ? false : true) : false);


		if(isGridEnabled){
			lblGasLeakageWhere.setVisibility(View.VISIBLE);
			llWhrLkg.setVisibility(View.VISIBLE);
			relWhrLkg.setVisibility(View.VISIBLE);

			llgasRectified.setVisibility(View.VISIBLE);
			llgasRectifiedDetail.setVisibility(View.VISIBLE);
//			radBtnNo.setEnabled(true);
//			radBtnYes.setEnabled(true);

//			lblGasLeckageRectified.setVisibility(View.GONE);
//			radBtnNo.setVisibility(View.GONE);
//			radBtnYes.setVisibility(View.GONE);
//			errIsRectified.setVisibility(View.GONE);

		}else{
			llWhrLkg.setVisibility(View.GONE);
			relWhrLkg.setVisibility(View.GONE);
			llgasRectified.setVisibility(View.GONE);
			llgasRectifiedDetail.setVisibility(View.GONE);
//			radBtnNo.setEnabled(true);
//			radBtnYes.setEnabled(true);
//			lblGasLeckageRectified.setVisibility(View.VISIBLE);
//			radBtnNo.setVisibility(View.VISIBLE);
//			radBtnYes.setVisibility(View.VISIBLE);
//			errIsRectified.setVisibility(View.VISIBLE);
			lblGasLeakageWhere.setVisibility(View.INVISIBLE);
		}

		ArrayList<LeakageVO> voList = new ArrayList<LeakageVO>();

		for (int i = 0; i < leakageArray.length; i++) {
			LeakageVO lvo = new LeakageVO();
			lvo.setServiceLkgId((leakageArray[i].getLeakageid()));
			lvo.setLeakageName(leakageArray[i].getLeakagename());
			voList.add(lvo);
		}

		if (custStausId == masterService.getStatusId("OP") || custStausId == masterService.getStatusId("HCL1")) { // 6
			leakageDtlChecked.clear();
		}

		for (int i = 0; i < leakageArray.length; i++) {
			if (itemCheckedLkg.size() > 0) {
				leakageDtlChecked.add(i, itemCheckedLkg.get(i));
			} else {
				leakageDtlChecked.add(i, false);
			}
		}

		if (custStausId != masterService.getStatusId("OP") && custStausId != masterService.getStatusId("HCL1")) {
			gridAdapter = new LeakageListAdapter(getActivity(), voList,rlGIFitting, leakageDtlChecked, true, this);
		} else {
			gridAdapter = new LeakageListAdapter(getActivity(), voList,rlGIFitting, leakageDtlChecked,false, this);
		}


		gridView.setAdapter(gridAdapter);
		gridView.setEnabled(false);
		System.err.println(" ----- " + leakageDtlChecked.indexOf(true));

		if (leakageDtlChecked.size() > 2) {
			if (leakageDtlChecked.get(1)) { // chk for GIFITTING
				rlGIFitting.setVisibility(View.VISIBLE);
//				RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,100);
//				rlGIFitting.setLayoutParams(rel_btn);
//				llgifiting.setVisibility(View.GONE);
//				llgifitingDetail.setVisibility(View.GONE);
				int id = radGroupGIFittingSatisfactory.getCheckedRadioButtonId();
//				(id == R.id.radBtnGIFittingYes)? (hideGIFittingLayout()) : (((id == R.id.radBtnGIFittingNo) ? showGIFittingLayout() : hideGIFittingLayout()));
				if(id == R.id.radBtnGIFittingYes)
					hideGIFittingLayout();
				else {
					if(id == R.id.radBtnGIFittingNo)
						showGIFittingLayout();
					else
						hideGIFittingLayout();
				}

				if(radBtnGIFittingYes.isChecked())
					hideGIFittingLayout();
				Global.form_GiFitting = true;
			} else {
				rlGIFitting.setVisibility(View.GONE);
//				llgifiting.setVisibility(View.GONE);
				hideGIFittingLayout();
//				llgifitingDetail.setVisibility(View.GONE);
				Global.form_GiFitting = false;
			}
		} else {
			rlGIFitting.setVisibility(View.GONE);
			Global.form_GiFitting = false;
		}

		displayWarnings();
		errorMessageTesting();
	}

	private void hideGIFittingLayout(){
		llgifiting.setVisibility(View.GONE);
		llgifitingDetail.setVisibility(View.GONE);
		RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,110);
		rel_btn.bottomMargin = 15;
		rel_btn.topMargin = 15;
		rel_btn.rightMargin = 15;
		rel_btn.leftMargin = 15;
		rel_btn.addRule(RelativeLayout.BELOW, R.id.llrightpanel);
		rlGIFitting.setLayoutParams(rel_btn);
	}

	private void showGIFittingLayout(){
		llgifiting.setVisibility(View.VISIBLE);
		llgifitingDetail.setVisibility(View.VISIBLE);
		RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		rel_btn.bottomMargin = 15;
		rel_btn.topMargin = 15;
		rel_btn.rightMargin = 15;
		rel_btn.leftMargin = 15;
		rel_btn.addRule(RelativeLayout.BELOW, R.id.llrightpanel);
		rlGIFitting.setLayoutParams(rel_btn);

	}
	/**
	 * Method to set Error messages for Testing form
	 *
	 * @return void
	 *
	 */
	public void errorMessageTesting() {
		ArrayList<Boolean> errorList = new ArrayList<Boolean>();
		try{
//			if(edtInitialPressure != null && edtFinalPressure != null && edtDuration != null &&  btnGasLeakageRectified != null && btnGiFittingWorkSetisfectory != null)
			if(edtInitialPressure != null && edtFinalPressure != null && edtDuration != null ) //&&  btnGasLeakageRectified != null
			{
				if (edtInitialPressure.getText().toString().equals(Constants.LABEL_BLANK)) {
					errorList.add(true);
					if(errInitialPressure != null){
						errInitialPressure.setText(Constants.ERROR_MARK);
					}

				} else {
					errorList.add(false);
					if(errInitialPressure != null){
						errInitialPressure.setText(Constants.ERROR_MARK);
					}

				}

				if (edtFinalPressure.getText().toString().equals(Constants.LABEL_BLANK)) {
					if(errFinalPressure != null) {
						errorList.add(true);
						errFinalPressure.setText(Constants.ERROR_MARK);
					}
				} else {
					if(errFinalPressure != null) {
						errorList.add(false);

						errFinalPressure.setText(Constants.ERROR_MARK);
					}
					//			errFinalPressure.setText(Constants.LABEL_BLANK);
				}

				if (edtDuration.getText().toString().equals(Constants.LABEL_BLANK)) {
					if(errDuration != null) {
						errDuration.setText(Constants.ERROR_MARK);
						errorList.add(true);
					}
				} else {
					if(errDuration != null) {
						errDuration.setText(Constants.ERROR_MARK);
						//			errDuration.setText(Constants.LABEL_BLANK);
						errorList.add(false);
					}
				}

//				if(!sugGasLeakageTestPass.getText().equals(Constants.LABLE_NO))
//				{
//					if (btnGasLeakageRectified.getText().toString().equals(Constants.DROPDOWN_MSG)) {
//						errorList.add(true);
//						errIsRectified.setText(Constants.ERROR_MARK);
//					} else {
//						//			errIsRectified.setText(Constants.LABEL_BLANK);
//						errIsRectified.setText(Constants.ERROR_MARK);
//						errorList.add(false);
//					}
//				}

				if (Global.form_GiFitting == true) {
					if (btnGiFittingWorkSetisfectory.getText().toString().equals(Constants.DROPDOWN_MSG)) {
						errorList.add(true);
						errGiGasLickageSetisfectory.setText(Constants.ERROR_MARK);
					} else {
						//				errGiGasLickageSetisfectory.setText(Constants.LABEL_BLANK);
						errGiGasLickageSetisfectory.setText(Constants.ERROR_MARK);
						errorList.add(false);
					}
				} else{
					errorList.add(false);
					errGiGasLickageSetisfectory.setText(Constants.ERROR_MARK);
				}

//				if (Global.form_GiFitting == true) 
//				{
//					if (!isElbowBtnClicked 	&& radGroupGIFittingSatisfactory.getCheckedRadioButtonId() == R.id.radBtnGIFittingNo)
//						errGiElbow.setText(Constants.ERROR_CLICK_GI_BUTTON);
//					else
//						errGiElbow.setText(Constants.LABEL_BLANK);
//
//					if (errGiElbow.getText().toString().equals(Constants.LABEL_BLANK))
//						errorList.add(false);
//					else
//						errorList.add(true);
//				} 
//				else
//				{
//					errorList.add(false);
//					if(errGiElbow != null)
//						errGiElbow.setText(Constants.LABEL_BLANK);
//				}
//
//				if (Global.form_GiFitting == true) 
//				{
//					if (!isHexNippleBtnClicked && radGroupGIFittingSatisfactory.getCheckedRadioButtonId() == R.id.radBtnGIFittingNo) 
//					{
//						errGiHexNipple.setText(Constants.ERROR_CLICK_GI_BUTTON);
//					}else{
//						errGiHexNipple.setText(Constants.LABEL_BLANK);	
//					}
//					if (errGiHexNipple.getText().toString().equals(Constants.LABEL_BLANK))
//						errorList.add(false);
//					else
//						errorList.add(true);
//				} 
//				else
//				{
//					errorList.add(false);
//					if(errGiHexNipple != null)
//						errGiHexNipple.setText(Constants.LABEL_BLANK);
//				}
//
//				if (Global.form_GiFitting == true){
//
//					if (!isPlugBtnClicked && radGroupGIFittingSatisfactory.getCheckedRadioButtonId() == R.id.radBtnGIFittingNo) 
//					{
//						errGiPlug.setText(Constants.ERROR_CLICK_GI_BUTTON);	
//					}else{
//						errGiPlug.setText(Constants.LABEL_BLANK);	
//					}
//					if (errGiPlug.getText().toString().equals(Constants.LABEL_BLANK))
//						errorList.add(false);
//					else
//						errorList.add(true);
//				} 
//				else
//				{
//					errorList.add(false);
//					if(errGiPlug != null)
//						errGiPlug.setText(Constants.LABEL_BLANK);
//				}
//
//				if (Global.form_GiFitting == true){
//					if (!isTeeBtnClicked && radGroupGIFittingSatisfactory.getCheckedRadioButtonId() == R.id.radBtnGIFittingNo) 
//					{
//						errGiTee.setText(Constants.ERROR_CLICK_GI_BUTTON);	
//					}else{
//						errGiTee.setText(Constants.LABEL_BLANK);
//					}
//
//					if (errGiTee.getText().toString().equals(Constants.LABEL_BLANK))
//						errorList.add(false);
//					else
//						errorList.add(true);
//				} else
//				{
//					errorList.add(false);
//					if(errGiTee != null)
//						errGiTee.setText(Constants.LABEL_BLANK);
//				}
//
//				if (Global.form_GiFitting == true){
//					if (!isGICapBtnClicked && radGroupGIFittingSatisfactory.getCheckedRadioButtonId() == R.id.radBtnGIFittingNo) 
//					{
//						errGiCap.setText(Constants.ERROR_CLICK_GI_BUTTON);
//
//					}else{
//						errGiCap.setText(Constants.LABEL_BLANK);
//					}
//
//					if (errGiCap.getText().toString().equals(Constants.LABEL_BLANK))
//						errorList.add(false);
//					else
//						errorList.add(true);
//				} 
//				else
//				{
//					errorList.add(false);
//					if(errGiCap != null)
//						errGiCap.setText(Constants.LABEL_BLANK);
//				}
//
//				if (Global.form_GiFitting == true){
//					if (!isGICouplingBtnClicked && radGroupGIFittingSatisfactory.getCheckedRadioButtonId() == R.id.radBtnGIFittingNo) 
//					{
//						errGiCoupling.setText(Constants.ERROR_CLICK_GI_BUTTON);
//					}else{
//						errGiCoupling.setText(Constants.LABEL_BLANK);
//					}
//
//					if (errGiCoupling.getText().toString().equals(Constants.LABEL_BLANK))
//						errorList.add(false);
//					else
//						errorList.add(true);
//				} 
//				else
//				{
//					errorList.add(false);
//					if(errGiCoupling != null)
//						errGiCoupling.setText(Constants.LABEL_BLANK);
//				}
//
//				if (Global.form_GiFitting == true){
//					if (!isUnionBtnClicked && radGroupGIFittingSatisfactory.getCheckedRadioButtonId() == R.id.radBtnGIFittingNo) 
//					{
//						errGiUnion.setText(Constants.ERROR_CLICK_GI_BUTTON);
//
//					}else{
//						errGiUnion.setText(Constants.LABEL_BLANK);
//					}
//
//					if (errGiUnion.getText().toString().equals(Constants.LABEL_BLANK))
//						errorList.add(false);
//					else
//						errorList.add(true);
//				} 
//				else
//				{
//					errorList.add(false);
//					if(errGiUnion != null)
//						errGiUnion.setText(Constants.LABEL_BLANK);
//				}

				Collections.sort(errorList);
				System.err.println(" Correction Error --- "	+ Collections.binarySearch(errorList, true));
				if (Collections.binarySearch(errorList, true) >= 0)
					Utility.changeList(0, true);
				else
					Utility.changeList(0, false);
			}else
				Utility.changeList(0, false);
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,"TestingFragment:"+ Utility.convertExceptionToString(e));
		}
	}

	@SuppressWarnings("unused")
	public static void clearValuesTest(TextView textView1, EditText... editTexts){

		if(editTexts != null){
			for(EditText et: editTexts){

			}
		}

	}

	public static void clearValues() {
		System.out.println(" ON CLEAR VALUES ");
		try{
			if(edtFinalPressure != null && edtInitialPressure != null && edtDuration != null && sugGasLeakageTestPass != null && sugPressureDrop != null && edtRemarkLkgDesc != null)
			{
				edtFinalPressure.setText(Constants.LABEL_BLANK);
				edtInitialPressure.setText(Constants.LABEL_BLANK);
				edtDuration.setText(Constants.LABEL_BLANK);
				edtRemarkLkgDesc.setText(Constants.LABEL_BLANK);

				sugGasLeakageTestPass.setText(Constants.LABEL_BLANK);
				sugPressureDrop.setText(Constants.LABEL_BLANK);
				sugPressureType.setText(Constants.LABEL_BLANK);

				if(chkGiCap != null && chkGiCouplling != null && chkGiElbow != null && chkGiHexNipple != null && chkGiPlug != null && chkGiTee != null && chkGiUnion != null )
				{
//					edtGiCap.setText(Constants.LABEL_BLANK);
//					edtGiCouplling.setText(Constants.LABEL_BLANK);
//					edtGiElbow.setText(Constants.LABEL_BLANK);
//					edtGiHexNipple.setText(Constants.LABEL_BLANK);
//					edtGiPlug.setText(Constants.LABEL_BLANK);
//					edtGiTee.setText(Constants.LABEL_BLANK);
//					edtGiUnion.setText(Constants.LABEL_BLANK);
//					chkGiCap.setChecked(false);
//					chkGiCouplling.setChecked(false);
//					chkGiElbow.setChecked(false);
//					chkGiHexNipple.setChecked(false);
//					chkGiPlug.setChecked(false);
//					chkGiTee.setChecked(false);
//					chkGiUnion.setChecked(false);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,"TestingFragment:"+ Utility.convertExceptionToString(e));
		}
	}

	public void displayWarnings()
	{
		wrrGiElbow.setText(Utility.giFittings(getActivity(),"0",Constants.ELBOW));
		wrrGiTee.setText(Utility.giFittings(getActivity(),"0",Constants.TEE));
		wrrGiHexNipple.setText(Utility.giFittings(getActivity(),"0",Constants.HEXNIPPLE));
		wrrGiUnion.setText(Utility.giFittings(getActivity(),"0",Constants.UNION));
		wrrGiPlug.setText(Utility.giFittings(getActivity(),"0",Constants.PLUG));
		wrrGiCap.setText(Utility.giFittings(getActivity(),"0",Constants.GICAP));
		wrrGiCoupling.setText(Utility.giFittings(getActivity(),"0",Constants.GICOUPLING));
	}

	public void clearGIFitingValues()
	{
		IsGiSetisfectory = getActivity().getResources().getString(R.string.title_select_option);
		btnGiFittingWorkSetisfectory.setText(IsGiSetisfectory);
		radBtnGIFittingNo.setChecked(false);
		radBtnGIFittingYes.setChecked(false);
		chkGiCap.setChecked(false);
		chkGiCouplling.setChecked(false);
		chkGiElbow.setChecked(false);
		chkGiHexNipple.setChecked(false);
		chkGiPlug.setChecked(false);
		chkGiTee.setChecked(false);
		chkGiUnion.setChecked(false);
	}

	/**
	 * Method to set values to vo for testing and gifitting.
	 *
	 * @return void
	 *
	 */
	public void setValues() {
//		if(edtInitialPressure != null && edtFinalPressure != null && edtDuration != null &&  btnGasLeakageRectified != null && btnGiFittingWorkSetisfectory != null)
		if(edtInitialPressure != null && edtFinalPressure != null && edtDuration != null  && edtRemarkLkgDesc != null) //&&  btnGasLeakageRectified != null
		{
			TestingVO.setFinalPressure(edtFinalPressure.getText().toString().trim());
			TestingVO.setInitialPressure(edtInitialPressure.getText().toString().trim());
			TestingVO.setDuration(edtDuration.getText().toString().trim());
			TestingVO.setGasLkgDetectionTest(sugGasLeakageTestPass.getText().toString().trim());
			TestingVO.setPressureDrop(sugPressureDrop.getText().toString().trim());
			TestingVO.setPressureType(sugPressureType.getText().toString().trim());
			TestingVO.setRemarkLkgDescription(edtRemarkLkgDesc.getText().toString().trim());

			System.out.println(" ---- IS GAS LEA SET VALUES ****** ------ "+btnGasLeakageRectified.getText().toString());
			if(btnGasLeakageRectified.getText().equals(Constants.DROPDOWN_MSG))
				TestingVO.setGasLkgRectified(Constants.LABEL_BLANK);
			else
			{
				TestingVO.setGasLkgRectified(btnGasLeakageRectified.getText().toString());
			}
			int id = radGroupGIFittingSatisfactory.getCheckedRadioButtonId();
			GIFittingVO.setIsWorking(id == R.id.radBtnGIFittingYes? "YES" : (id == R.id.radBtnGIFittingNo ? "NO" : ""));
			GIFittingVO.setGicap(chkGiCap.isChecked()? "1" : "0" );
			GIFittingVO.setGicoupling(chkGiCouplling.isChecked()? "1" : "0");
			GIFittingVO.setElbow(chkGiElbow.isChecked() ? "1" : "0");
			GIFittingVO.setHexNipple(chkGiHexNipple.isChecked() ? "1" : "0");
			GIFittingVO.setPlug(chkGiPlug.isChecked() ? "1" : "0");
			GIFittingVO.setTee(chkGiTee.isChecked() ? "1" : "0");
			GIFittingVO.setUnion(chkGiUnion.isChecked() ? "1" : "0");

//			GIFittingVO.setGicap(isGICapBtnClicked? "1" : "0" );
//			GIFittingVO.setGicoupling(isGICouplingBtnClicked ? "1" : "0");
//			GIFittingVO.setElbow(isElbowBtnClicked ? "1" : "0");
//			GIFittingVO.setHexNipple(isHexNippleBtnClicked ? "1" : "0");
//			GIFittingVO.setPlug(isPlugBtnClicked? "1" : "0");
//			GIFittingVO.setTee(isTeeBtnClicked ? "1" : "0");
//			GIFittingVO.setUnion(isUnionBtnClicked ? "1" : "0");
			// if (custStausId != masterService.getStatusId("OP")) { // 6
			leakageDtlChecked.clear();
			// }

			for (int i = 0; i < leakageArray.length; i++) {
				if (itemCheckedLkg.size() > 0) {
					leakageDtlChecked.add(itemCheckedLkg.get(i));
				} else {
					leakageDtlChecked.add(i, false);
				}
			}

			// for(int i = 0 ; i < leakageArray.length ; i++ ){
			// if(itemCheckedLkg.size()>0){
			// leakageDtlChecked.add(itemCheckedLkg.get(i));
			// }else{
			// leakageDtlChecked.add(i,false);
			// }
			// }

			//			clearValues(); // clear value once they are set
		}
	}

	public void getSpinnerValue(final Button btnValue, final String[] status) {
		try {
			new AlertDialog.Builder(getActivity()).setSingleChoiceItems(status,-1, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int whichButton) {
					btnValue.setText(status[whichButton]);
					dialog.dismiss();
				}
			}).show();
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,"TestingFragment:"+ Utility.convertExceptionToString(e));
		}
	}


	public static boolean GiCoupplingValidation(int DamegeId, int CauseId) {
//
//		if (edtGiCouplling.getText().toString().equals(Constants.LABEL_BLANK)) {
//			GiCoupplingValidation = true;
//
//		} else {
		if (DamegeId > 0 && CauseId > 0) {
			GiCoupplingValidation = true;
		} else {
			GiCoupplingValidation = false;
		}
//		}
		return GiCoupplingValidation;
	}

	/*@Override
	public void onResume() {
		//		if (!(custStausId != masterService.getStatusId("OP") || user.equals("admin")))
		//			//			informUser();
		//			errorMessageTesting();
		
		super.onResume();
	}
*/
	@Override
	public void onPause() {
		super.onPause();
		System.out.println("Testing ON PAUSE ");

		System.out.println(" ++  STAT CODE -- "+ testingStatusCode);
		if("OP".equals(testingStatusCode) || "HCL1".equals(TestingFragment.testingStatusCode))
		{
			System.out.println(" --- OP ---");
			errorMessageTesting(); // first chk for errors 
			setValues();			// the set value followed by clear views
		}
		super.onPause();
	}


	@SuppressWarnings("unused")
	public void setGasTestCheckBoxes(String[] checkBoxNamelist) {
		for (int i = 0; i < checkBoxNamelist.length; i++) {
			gasLeakageTest.put(checkBoxNamelist[i], false);
			boolean key = gasLeakageTest.get(checkBoxNamelist[i]);
		}
	}

	/**
	 * This method use to set Pressure Drop on base of initialPressure and
	 * finalPressure.
	 *
	 */
	public void setPressureDrop() {
		if ((!TestingVO.getInitialPressure().equals(Constants.BLANK)) && (!finalPressure.equals(Constants.BLANK))) {
			Utility.checkPressureDrop(TestingVO.getInitialPressure(), finalPressure);
			sugPressureDrop.setText(TestingVO.getPressureDrop());
		} else {
			TestingVO.setPressureDrop(Constants.BLANK);
			sugPressureDrop.setText(Constants.BLANK);
		}
	}

	/**
	 * This method use to set Pressure type on base of Pressure Drop.
	 *
	 */
	public void setPressureType() {

		//pressureDrop = TestingVO.getPressureDrop();

		initialPressure = TestingVO.getInitialPressure();
		if ((!initialPressure.equals(Constants.BLANK))) {
			Utility.checkPressureType(getActivity(), initialPressure);
			sugPressureType.setText(TestingVO.getPressureType());
		} else {
			sugPressureType.setText(Constants.BLANK);
		}
	}

	/**
	 * @author bhavikg This method use to set Gas Leakage Test pass or not It
	 *         calls isGasLeakageTestPass method with @param
	 *         initialPressure,finalPressure
	 */
	public void setGasLeakageTestPass() {
		try{
			if ((!TestingVO.getInitialPressure().equals(Constants.BLANK)) && (!finalPressure.equals(Constants.BLANK))) {
				//if (Utility.isGasLeakageTestPass(TestingVO.getInitialPressure(), finalPressure)) {
				pressureDrop = TestingVO.getPressureDrop();
				if(!Utility.isGesLeakageTestPassNew(pressureDrop)){
					//TestingVO.setGasLkgDetectionTest(Constants.LABLE_YES);
					TestingVO.setGasLkgDetectionTest(Constants.LABLE_NO); //Show Grid

					sugGasLeakageTestPass.setText(TestingVO.getGasLkgDetectionTest());
					lblGasLeakageWhere.setVisibility(View.VISIBLE);
					isGridEnabled = true;
					btnGasLeakageRectified.setText("YES");
					TestingVO.setGasLkgRectified("YES");
					llWhrLkg.setVisibility(View.VISIBLE);
					relWhrLkg.setVisibility(View.VISIBLE);

//					lblGasLeckageRectified.setVisibility(View.GONE);
//					radBtnNo.setVisibility(View.GONE);
//					radBtnYes.setVisibility(View.GONE);
//					errIsRectified.setVisibility(View.GONE);

					llgasRectified.setVisibility(View.VISIBLE);
					llgasRectifiedDetail.setVisibility(View.VISIBLE);
					lblLeakageRectified.setText("YES");
//					radBtnNo.setEnabled(true);
//					radBtnYes.setEnabled(true);
					radBtnNo.setChecked(false);
					radBtnYes.setChecked(true);
				} else {
					TestingVO.setGasLkgDetectionTest(Constants.LABLE_YES); //Dont show grid
					sugGasLeakageTestPass.setText(TestingVO.getGasLkgDetectionTest());
					lblGasLeakageWhere.setVisibility(View.INVISIBLE);

					isGridEnabled = false;

					btnGasLeakageRectified.setText("NO");
					llWhrLkg.setVisibility(View.GONE);
					relWhrLkg.setVisibility(View.GONE);
					lblLeakageRectified.setText("NO");
					llgasRectified.setVisibility(View.GONE);
					llgasRectifiedDetail.setVisibility(View.GONE);

//					lblGasLeckageRectified.setVisibility(View.VISIBLE);
//					radBtnNo.setVisibility(View.VISIBLE);
//					radBtnYes.setVisibility(View.VISIBLE);
//					radBtnNo.setEnabled(true);
//					radBtnYes.setEnabled(true);
					radBtnNo.setChecked(true);
					radBtnYes.setChecked(false);
//					errIsRectified.setVisibility(View.VISIBLE);
					//				for (int i = 0; i < leakageArray.length; i++) {
					//						leakageDtlChecked.remove(i);
					//						leakageDtlChecked.add(i, false);
					//				}
					for (int i = 0; i < leakageArray.length; i++) {
						itemCheckedLkg.add(i, false);
						String where = "maintainanceorderid = '"+ MaintainanceVO.getMaintainanceOrderId() +"' and objectid = "+leakageArray[i].getLeakageid();
						if(masterService.deleteUser(Constants.TBL_DTL_DAMAGE_CAUSE, where) != -1)
						{
						}
					}
					rlGIFitting.setVisibility(View.GONE);
					Global.form_GiFitting = false;
					clearGIFitingValues();
					errorMessageTesting();
				}
				gridAdapter.notifyDataSetChanged();
			} else {
				TestingVO.setGasLkgDetectionTest(Constants.BLANK);
				sugGasLeakageTestPass.setText(TestingVO.getGasLkgDetectionTest());
			}
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,"TestingFragment:"+ Utility.convertExceptionToString(e));
		}
	}

	public void onFocusChange(View view, boolean hasFocus) {
		try {
			if (view == edtInitialPressure) {
				initialPressure = edtInitialPressure.getText().toString();

				if (!hasFocus) {
					if (!initialPressure.equals(Constants.LABEL_BLANK) && Integer.parseInt(initialPressure) > 0) {
						Global.initialPressure = true;
						//						if (initialPressure.matches(regExpression)) {
						//							Global.initialPressure = true;
						//
						//						} else {
						//							Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL,Constants.ERROR_ENTER_DECIMAL_VALUE);
						//							Global.initialPressure = false;
						//						}
					}
				}else{
					Global.initialPressure=false;
					sugPressureType.setText(Constants.LABEL_BLANK);
				}
				TestingVO.setInitialPressure(initialPressure);
				TestingVO.setInitialPressure(edtInitialPressure.getText().toString());
				setPressureType();

				if (Global.initialPressure == true && Global.finalPressure == true) {
					setPressureDrop();
					setPressureType();
					setGasLeakageTestPass();

				}

			} else if (view == edtFinalPressure) {
				finalPressure = edtFinalPressure.getText().toString();

				if (Global.initialPressure == false) {
					errTstInitPressurevalidate.setText(Constants.ERROR_INITIAL_PRESSURE_ZERO_VALUE);
					edtInitialPressure.requestFocus();
					edtFinalPressure.setCursorVisible(false);
				}
				else {
					errTstInitPressurevalidate.setText(Constants.LABEL_BLANK);
					edtFinalPressure.setCursorVisible(true);
				}

				if (!hasFocus) {
					if (!finalPressure.equals(Constants.LABEL_BLANK) && Integer.parseInt(finalPressure) > 0 ) {

						initialPressure = edtInitialPressure.getText().toString();
						if(Integer.parseInt(finalPressure) <= Integer.parseInt(initialPressure))
						{
							Global.finalPressure = true;
							edtDuration.setCursorVisible(true);
							errFinalPressurevalidate.setText(Constants.LABEL_BLANK);

						}else {

							errFinalPressurevalidate.setText(Constants.ERROR_FINAL_PRESSURE_VALUE);

							Global.finalPressure=false;

							//edtFinalPressure.setText(Constants.LABEL_BLANK);
//							edtFinalPressure.requestFocus();
//							edtFinalPressure.setFocusableInTouchMode(true);
							edtFinalPressure.setFocusable(true);
							edtFinalPressure.setCursorVisible(true);
							edtDuration.setCursorVisible(false);


							//Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL,Constants.ERROR_ENTER_DECIMAL_VALUE);
						}

						//						if (finalPressure.matches(regExpression)) {
						//							Global.finalPressure = true;
						//
						//						} else {
						//							Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL,Constants.ERROR_ENTER_DECIMAL_VALUE);
						//							Global.finalPressure = false;
						//						}
					}else{
						Global.finalPressure=false;
					}
				}

				TestingVO.setFinalPressure(finalPressure);
				TestingVO.setInitialPressure(edtInitialPressure.getText().toString());
				@SuppressWarnings("unused")
				String initValue=edtInitialPressure.getText().toString();
				setGasLeakageTestPass();

				if (Global.initialPressure == true && Global.finalPressure == true) {
					setPressureDrop();
					setPressureType();
					setGasLeakageTestPass();

				} else  {
					sugPressureDrop.setText(Constants.LABEL_BLANK);
					//sugPressureType.setText(Constants.LABEL_BLANK);
				}

			}else if(view == edtDuration){
				if (Global.finalPressure == false) {
					if(Integer.parseInt(finalPressure) > 0){
						edtFinalPressure.setText(Constants.LABEL_BLANK);
						errFinalPressurevalidate.setText(Constants.ERROR_FINAL_PRESSURE_VALUE);
					}else{
						errFinalPressurevalidate.setText(Constants.ERROR_FINAL_PRESSURE_ZERO_VALUE);
					}
					edtFinalPressure.requestFocus();
					edtFinalPressure.setCursorVisible(true);
				}else {
					edtDuration.setCursorVisible(true);

				}
				//TestingVO.setDuration(edtDuration.getText().toString());
			} else if(view == edtRemarkLkgDesc) {
				if(!hasFocus) {
					remarkLkgDescription = edtRemarkLkgDesc.getText().toString().trim();
					TestingVO.setRemarkLkgDescription(remarkLkgDescription);
				}
			}
//			else if (view == edtGiElbow) {
//				if (!hasFocus) {
//					if (!edtGiElbow.getText().toString().equals(Constants.LABEL_BLANK)) {
//						System.out.println("Value of selectedDamegaId"+ selectedDamegeId);
//						System.out.println("Value of selectedCauseId"+ selectedCauseId);
//
//						if (selectedDamegeId > 0 && selectedCauseId > 0) {
//							Global.elbo = true;
//
//						} else {
//							Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL,Constants.ERROR_SELECT_DAMAGE_AND_CAUSE+ Constants.LABEL_VALIDATE_GIFITTINGS_ELBOW);
//							Global.elbo = false;
//						}
//					}
//				}
//
//			} else if (view == edtGiTee) {
//				if (Global.elbo == false) {
//					edtGiElbow.requestFocus();
//					edtGiTee.setCursorVisible(false);
//
//				} else {
//					edtGiTee.setCursorVisible(true);
//				}
//
//				if (!hasFocus) {
//					if (!edtGiTee.getText().toString().equals(Constants.LABEL_BLANK)) {
//						if (selectedDamegeId > 0 && selectedCauseId > 0) {
//							Global.tee = true;
//						} else {
//							Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL,Constants.ERROR_SELECT_DAMAGE_AND_CAUSE+ Constants.LABEL_VALIDATE_GIFITTINGS_TEE);
//							Global.tee = false;
//						}
//					}
//				}
//
//			} else if (view == edtGiHexNipple) {
//				if (Global.tee == false) {
//					edtGiTee.requestFocus();
//					edtGiHexNipple.setCursorVisible(false);
//
//				} else {
//					edtGiHexNipple.setCursorVisible(true);
//				}
//
//				if (!hasFocus) {
//					if (!edtGiHexNipple.getText().toString().equals(Constants.LABEL_BLANK)) {
//						if (selectedDamegeId > 0 && selectedCauseId > 0) {
//							Global.hexNipple = true;
//						} else {
//							Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL,Constants.ERROR_SELECT_DAMAGE_AND_CAUSE+ Constants.LABEL_VALIDATE_GIFITTINGS_HEXNIPPLE);
//							Global.hexNipple = false;
//						}
//					}
//				}
//
//			} else if (view == edtGiUnion) {
//				if (Global.hexNipple == false) {
//					edtGiHexNipple.requestFocus();
//					edtGiUnion.setCursorVisible(false);
//
//				} else {
//					edtGiUnion.setCursorVisible(true);
//				}
//
//				if (!hasFocus) {
//					if (!edtGiUnion.getText().toString().equals(Constants.LABEL_BLANK)) {
//						if (selectedDamegeId > 0 && selectedCauseId > 0) {
//							Global.union = true;
//						} else {
//							Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL,Constants.ERROR_SELECT_DAMAGE_AND_CAUSE+ Constants.LABEL_VALIDATE_GIFITTINGS_UNION);
//							Global.union = false;
//						}
//					}
//				}
//
//			} else if (view == edtGiPlug) {
//				if (Global.union == false) {
//					edtGiUnion.requestFocus();
//					edtGiPlug.setCursorVisible(false);
//
//				} else {
//					edtGiPlug.setCursorVisible(true);
//				}
//
//				if (!hasFocus) {
//					if (!edtGiPlug.getText().toString().equals(Constants.LABEL_BLANK)) {
//						if (selectedDamegeId > 0 && selectedCauseId > 0) {
//							Global.plug = true;
//						} else {
//							Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL,Constants.ERROR_SELECT_DAMAGE_AND_CAUSE+ Constants.LABEL_VALIDATE_GIFITTINGS_PLUG);
//							Global.plug = false;
//						}
//					}
//				}
//
//			} else if (view == edtGiCap) {
//				if (Global.plug == false) {
//					edtGiPlug.requestFocus();
//					edtGiCap.setCursorVisible(false);
//
//				} else {
//					edtGiCap.setCursorVisible(true);
//				}
//
//				if (!hasFocus) {
//					if (!edtGiCap.getText().toString().equals(Constants.LABEL_BLANK)) {
//						if (selectedDamegeId > 0 && selectedCauseId > 0) {
//							Global.cap = true;
//						} else {
//							Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL,Constants.ERROR_SELECT_DAMAGE_AND_CAUSE+ Constants.LABEL_VALIDATE_GIFITTINGS_CAP);
//							Global.cap = false;
//						}
//					}
//				}
//
//			} else if (view == edtGiCouplling) {
//				if (Global.cap == false) {
//					edtGiCap.requestFocus();
//					edtGiCouplling.setCursorVisible(false);
//
//				} else {
//					edtGiCouplling.setCursorVisible(true);
//				}
//
//				if (!hasFocus) {
//					if (!edtGiCouplling.getText().toString().equals(Constants.LABEL_BLANK)) {
//						if (selectedDamegeId > 0 && selectedCauseId > 0) {
//							Global.coupling = true;
//						} else {
//							Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL,Constants.ERROR_SELECT_DAMAGE_AND_CAUSE+ Constants.LABEL_VALIDATE_GIFITTINGS_COUPLING);
//							Global.coupling = false;
//						}
//					}
//				}
//
//			} else if (view == btnGasLeakageRectified) {
//				if (Global.coupling == false) {
//					edtGiCouplling.requestFocus();
//					btnGasLeakageRectified.setCursorVisible(false);
//
//				} else {
//					btnGasLeakageRectified.setCursorVisible(true);
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,"TestingFragment:"+ Utility.convertExceptionToString(e));
		} finally {
			selectedCauseId = 0;
			selectedDamegeId = 0;
		}
	}

	/**
	 * Method used to change label values as per parameter to English and
	 * Gujarati.
	 *
	 * @param id
	 *            : 1 - English , 2 - Gujarati
	 */
	public void checkLanguage(int id) {

		id = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.PREF_LANGUAGE, Constants.LANGUAGE_ENGLISH);

		switch (id) {
			case Constants.LANGUAGE_ENGLISH:
				lblDuration.setText(getResources().getText(R.string.title_testing_duration_Eng));
				lblFinalPressure.setText(getResources().getText(R.string.title_testing_final_pressure_Eng));
				lblGasLeckageRectified.setText(getResources().getText(R.string.title_testing_leakage_rectified_Eng));
				lblInitialPressure.setText(getResources().getText(R.string.title_testing_initial_pressure_Eng));
				lblPressureDrop.setText(getResources().getText(R.string.title_testing_pressure_drop_Eng));
				lblPressureType.setText(getResources().getText(R.string.title_testing_pressure_type_Eng));
				lblTestingHeader.setText(getResources().getText(R.string.header_testing_Eng));
				lblTestpass.setText(getResources().getText(R.string.title_testing_test_pass_Eng));
				lblElbow.setText(getResources().getText(R.string.title_testing_gi_elbow_Eng));
				lblGiCap.setText(getResources().getText(R.string.title_testing_gi_gi_cap_Eng));
				lblGiFittingHeader.setText(getResources().getText(R.string.title_testing_gi_fitting_Eng));
				lblGiFittingWorkSetisfectory.setText(getResources().getText(R.string.title_testing_gi_statisfactory_Eng));
				lblGiTee.setText(getResources().getText(R.string.title_testing_gi_tee_Eng));
				lblGiHexNipple.setText(getResources().getText(R.string.title_testing_gi_hex_nipple_Eng));
				lblGiUnion.setText(getResources().getText(R.string.title_testing_gi_union_Eng));
				lblGiPlug.setText(getResources().getText(R.string.title_testing_gi_plug_Eng));
				lblGiCouppling.setText(getResources().getText(R.string.title_testing_gi_gi_coupling_Eng));
				lblGasLeakageWhere.setText(getResources().getText(R.string.title_testing_test_pass_reason_Eng));
				lblTstTestRemarkLkgDesc.setText(getResources().getText(R.string.title_testing_test_pass_remarkforlkg_Eng));

				break;

			case Constants.LANGUAGE_GUJRATI:

				lblDuration.setTypeface(Global.typeface_Guj);
				lblFinalPressure.setTypeface(Global.typeface_Guj);
				lblGasLeckageRectified.setTypeface(Global.typeface_Guj);
				lblInitialPressure.setTypeface(Global.typeface_Guj);
				lblPressureDrop.setTypeface(Global.typeface_Guj);
				lblPressureType.setTypeface(Global.typeface_Guj);
				lblTestingHeader.setTypeface(Global.typeface_Guj);
				lblTestpass.setTypeface(Global.typeface_Guj);
				lblElbow.setTypeface(Global.typeface_Guj);
				lblGiCap.setTypeface(Global.typeface_Guj);
				lblGiFittingHeader.setTypeface(Global.typeface_Guj);
				lblGiFittingWorkSetisfectory.setTypeface(Global.typeface_Guj);
				lblGiTee.setTypeface(Global.typeface_Guj);
				lblGiHexNipple.setTypeface(Global.typeface_Guj);
				lblGiUnion.setTypeface(Global.typeface_Guj);
				lblGiPlug.setTypeface(Global.typeface_Guj);
				lblGiCouppling.setTypeface(Global.typeface_Guj);
				lblGasLeakageWhere.setTypeface(Global.typeface_Guj);
				lblTstTestRemarkLkgDesc.setTypeface(Global.typeface_Guj);
				lblElbow.setText(getResources().getText(R.string.title_testing_gi_elbow_Guj));
				lblGiCap.setText(getResources().getText(R.string.title_testing_gi_gi_cap_Guj));
				lblGiFittingHeader.setText(getResources().getText(R.string.title_testing_gi_fitting_Guj));
				lblGiFittingWorkSetisfectory.setText(getResources().getText(R.string.title_testing_gi_statisfactory_Guj));
				lblGiTee.setText(getResources().getText(R.string.title_testing_gi_tee_Guj));
				lblGiHexNipple.setText(getResources().getText(R.string.title_testing_gi_hex_nipple_Guj));
				lblGiUnion.setText(getResources().getText(R.string.title_testing_gi_union_Guj));
				lblGiPlug.setText(getResources().getText(R.string.title_testing_gi_plug_Guj));
				lblGiCouppling.setText(getResources().getText(R.string.title_testing_gi_gi_coupling_Guj));
				lblDuration.setText(getResources().getText(R.string.title_testing_duration_Guj));
				lblFinalPressure.setText(getResources().getText(R.string.title_testing_final_pressure_Guj));
				lblGasLeckageRectified.setText(getResources().getText(R.string.title_testing_leakage_rectified_Guj));
				lblInitialPressure.setText(getResources().getText(R.string.title_testing_initial_pressure_Guj));
				lblPressureDrop.setText(getResources().getText(R.string.title_testing_pressure_drop_Guj));
				lblPressureType.setText(getResources().getText(R.string.title_testing_pressure_type_Guj));
				lblTestingHeader.setText(getResources().getText(R.string.header_testing_Guj));
				lblTestpass.setText(getResources().getText(R.string.title_testing_test_pass_Guj));
				lblGasLeakageWhere.setText(getResources().getText(R.string.title_testing_test_pass_reason_Guj));
				lblTstTestRemarkLkgDesc.setText(getResources().getText(R.string.title_testing_test_pass_remarkforlkg_Guj));


				break;

			default:
				break;
		}
	}

	/**
	 * An adapter for set Leakage value.
	 *
	 */
	private static class LeakageListAdapter extends BaseAdapter {

		static class ViewHolder {
			private CheckBox leakageName;
		}

		private Context context;
		private LayoutInflater mLayoutInflator;
		private ArrayList<LeakageVO> arrayList;
		private RelativeLayout rl;
		private boolean flag;


		private Dialog dialog;
		private RadioGroup radGroupDamage;
		private RadioGroup radGroupCause;
		private RadioButton[] rbDamage;
		private RadioButton[] rbCause;
		private String strDamage, strCause;
		private UserMasterService userMasterService;
		private ViewHolder holder;

		private DamageVO damageVO;
		private CauseVO causeVO;

		private TestingFragment testingFragment;

		public LeakageListAdapter(Context context, ArrayList<LeakageVO> list,RelativeLayout rlGI, ArrayList<Boolean> checkedItemLkg, boolean flag, TestingFragment testingFrag) {

			mLayoutInflator = LayoutInflater.from(context);
			this.context = context;
			rl = rlGI;
			this.arrayList = list;
			this.flag = flag;
			itemCheckedLkg.clear();
			for (int i = 0; i < list.size(); i++) {
				itemCheckedLkg.add(i, checkedItemLkg.get(i)); // initializes all
				// items value
				// with false
			}
			userMasterService = new UserMasterService(context);
			testingFragment = testingFrag;
		}

		public int getCount() {
			return arrayList.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int index) {
			return index;
		}

		public View getView(final int position, View convertView,final ViewGroup parent) {

			if (convertView == null) {
				convertView = mLayoutInflator.inflate(R.layout.testing_leakage_list, null);
				holder = new ViewHolder();
				holder.leakageName = (CheckBox) convertView.findViewById(R.id.checkBox1);

				convertView.setTag(holder);
			} else
				holder = (ViewHolder) convertView.getTag();

			holder.leakageName.setText(arrayList.get(position).getLeakageName());
			// holder.leakageName.setEnabled(true);

			//			if(!isGridEnabled){
			//				holder.leakageName.setChecked(false);
			//			}
			if (isGridEnabled && flag) {
				convertView.setEnabled(false);
				convertView.setClickable(false);
				// holder.leakageName.setFocusable(false);
				System.out.println("isGridEnabled : "+isGridEnabled + "  flag : "+flag);

			} else if (!isGridEnabled && flag) {
				convertView.setEnabled(false);
				convertView.setClickable(false);
				// holder.leakageName.setFocusable(true);
				System.out.println("isGridEnabled : "+isGridEnabled + "  flag : "+flag);
			} else if (!isGridEnabled && !flag) {
				convertView.setEnabled(false);
				convertView.setClickable(false);
				holder.leakageName.setChecked(false);

			} else {
				convertView.setEnabled(true);
				convertView.setClickable(true);
				System.out.println("isGridEnabled : "+isGridEnabled + "  flag : "+flag);
			}
			holder.leakageName.setChecked(itemCheckedLkg.get(position));

			convertView.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					CheckBox chk = (CheckBox) v;
					System.out.println(" CHECKBOX " + chk.getText());

					if (chk.isChecked()) {
						itemCheckedLkg.set(position, true);

						if (chk.getText().toString().equalsIgnoreCase("Gi Fitting")) {
							rl.setVisibility(View.VISIBLE);
							testingFragment.hideGIFittingLayout();
							testingFragment.radGroupGIFittingSatisfactory.setEnabled(true);
							testingFragment.radBtnGIFittingNo.setEnabled(true);
							testingFragment.radBtnGIFittingNo.setChecked(true);
							testingFragment.radBtnGIFittingYes.setEnabled(true);

							Global.form_GiFitting = true;
							testingFragment.btnGiFittingWorkSetisfectory.setText("NO");
						} else {
							showDialog(context, position);
						}

					} else if (!chk.isChecked()) {
						itemCheckedLkg.set(position, false);

						if (chk.getText().toString().equalsIgnoreCase("Gi Fitting")) {
							rl.setVisibility(View.GONE);
							Global.form_GiFitting = false;
							testingFragment.radGroupGIFittingSatisfactory.clearCheck();
							isGICapBtnClicked = false;
							isGICouplingBtnClicked = false;
							isElbowBtnClicked = false;
							isPlugBtnClicked = false;
							isUnionBtnClicked = false;
							isTeeBtnClicked = false;
							isHexNippleBtnClicked = false;
							testingFragment.clearGIFitingValues();
							testingFragment.errorMessageTesting();
						}
						String where = "maintainanceorderid = '"+ MaintainanceVO.getMaintainanceOrderId() +"' and objectid = "+arrayList.get(position).getServiceLkgId();
						if(userMasterService.deleteUser(Constants.TBL_DTL_DAMAGE_CAUSE, where) != -1)
						{
							notifyDataSetInvalidated();
						}
					}
				}
			});
			return convertView;
		}

		public void showDialog(final Context context, final int position) {
			try {
				dialog = new Dialog(context);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.chk_dialog);

				radGroupDamage = (RadioGroup) dialog.findViewById(R.id.radGroupDamage);
				radGroupCause = (RadioGroup) dialog.findViewById(R.id.radGroupCause);

				List<DamageVO> listDamageVOs = userMasterService.getDamageInfo(arrayList.get(position).getServiceLkgId());
				List<CauseVO> listCauseVOs = userMasterService.getCauseInfo(arrayList.get(position).getServiceLkgId());

				// add radio buttons
				rbDamage = new RadioButton[listDamageVOs.size()];
				for (int i = 0; i < listDamageVOs.size(); i++) {
					damageVO = listDamageVOs.get(i);
					rbDamage[i] = new RadioButton(context);
					rbDamage[i].setLayoutParams(new RadioGroup.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
					rbDamage[i].setText(Constants.LABEL_BLANK+ damageVO.getFieldName());
					rbDamage[i].setTextColor(Color.BLACK);
					rbDamage[i].setTextSize(15);
					rbDamage[i].setSingleLine();
					rbDamage[i].setId(i);
					rbDamage[0].setChecked(true);
					radGroupDamage.addView(rbDamage[i]); // the RadioButtons are
					// added to the
					// radioGroup
					// instead of the
					// layout
					strDamage = rbDamage[0].getText().toString();
				}

				// add radio buttons
				rbCause = new RadioButton[listCauseVOs.size()];
				for (int i = 0; i < listCauseVOs.size(); i++) {
					causeVO = listCauseVOs.get(i);
					rbCause[i] = new RadioButton(context);
					rbCause[i].setLayoutParams(new RadioGroup.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
					rbCause[i].setText(Constants.LABEL_BLANK+ causeVO.getFieldName());
					rbCause[i].setTextColor(Color.BLACK);
					rbCause[i].setSingleLine();
					rbCause[i].setTextSize(15);
					rbCause[i].setId(i);
					rbCause[0].setChecked(true);
					radGroupCause.addView(rbCause[i]); // the RadioButtons are
					// added to the
					// radioGroup instead of
					// the layout
					strCause = rbCause[0].getText().toString();
				}

				radGroupDamage
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							public void onCheckedChanged(RadioGroup group,
														 int checkedId) {
								for (int i = 0; i < group.getChildCount(); i++) {
									RadioButton btn = (RadioButton) group.getChildAt(i);
									if (btn.getId() == checkedId) {
										strDamage = btn.getText().toString();
										Log.d("FIrstActivity","Radio1 Text ::: " + strDamage);
										Log.d("FIrstActivity","Radio1 Text btn.getId() ::: "+ btn.getId());

										// do something with text
										return;
									}
								}
							}
						});

				radGroupCause
						.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
							public void onCheckedChanged(RadioGroup rg,int checkedId) {
								for (int i = 0; i < rg.getChildCount(); i++) {
									RadioButton btn = (RadioButton) rg.getChildAt(i);
									if (btn.getId() == checkedId) {
										strCause = btn.getText().toString();
										Log.d("FIrstActivity","Radio2 Text ::: " + strCause);
										Log.d("FIrstActivity","Radio2 Text btn.getId() ::: "+ btn.getId());

										// do something with text
										return;
									}
								}
							}
						});

				Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
				btnOk.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						dialog.dismiss();
						Log.d("FIrstActivity","===========================================");
						Log.d("FIrstActivity", "Radio1 Text in ok ::: "+ strDamage);
						Log.d("FIrstActivity", "Radio2 Text in ok ::: "+ strCause);
						Log.d("FIrstActivity","===========================================");
						Log.d("FIrstActivity", "selected field id ::: "+ arrayList.get(position).getServiceLkgId());

						Log.d("FIrstActivity","Radio1 Text damageVO.getDamageId() ::: "+ userMasterService.getDamageID(strDamage,arrayList.get(position).getServiceLkgId()));
						Log.d("FIrstActivity","Radio2 Text causeVO.getCauseId() ::: "+ userMasterService.getCauseID(strCause,arrayList.get(position).getServiceLkgId()));
						Log.d("FIrstActivity","Radio1 Text damageVO.getDamageId() ::: "+ userMasterService.getDamageID(strDamage,arrayList.get(position).getServiceLkgId()));
						Log.d("FIrstActivity","Radio2 Text causeVO.getCauseId() ::: "+ userMasterService.getCauseID(strCause,arrayList.get(position).getServiceLkgId()));

						Log.d("FIrstActivity","===========================================");

						MaintainanceDTOService maintainanceDTOService = new MaintainanceDTOService(context);
						List<Integer> giFittingDatas = maintainanceDTOService.getGIfittingMasterSize();

						Log.d("FIrstActivity","======================= giFittingDatas.size() ===================="+ giFittingDatas.size());
						Collections.sort(giFittingDatas);

						if (Collections.binarySearch(giFittingDatas, position) >= 0) {
							String where = "objectid = " + position+ " and maintainanceorderid = '"+ MaintainanceVO.getMaintainanceOrderId()+ "'";
							userMasterService.deleteUser(Constants.TBL_DTL_DAMAGE_CAUSE, where);
						}

						ContentValues cv = new ContentValues();
						cv.put(Constants.DB_OBJECT_ID, arrayList.get(position).getServiceLkgId());
						cv.put(Constants.DB_DAMAGE_ID, userMasterService.getDamageID(strDamage, arrayList.get(position).getServiceLkgId()));
						cv.put(Constants.DB_CAUSE_ID, userMasterService.getCauseID(strCause, arrayList.get(position).getServiceLkgId()));
						cv.put(Constants.DB_MAINTAINANCE_ORDER_ID,MaintainanceVO.getMaintainanceOrderId());

						if (userMasterService.insertUser(Constants.TBL_DTL_DAMAGE_CAUSE, cv) != -1) {
							//Log.e(Constants.TAG_LOGIN_ACTIVITY, "userInsert");
						}
					}
				});
				dialog.show();

			} catch (Exception e) {
				e.printStackTrace();
				Log.d(Constants.DEBUG,"TestingFragment:"+ Utility.convertExceptionToString(e));
			} finally {
				// strDamage = Constants.LABEL_BLANK;
				// strCause = Constants.LABEL_BLANK;
			}
		}
	}
}