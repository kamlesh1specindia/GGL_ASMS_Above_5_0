package com.spec.asms.view.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Global;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.vo.ClampingVO;

public class ClampingFragment extends Fragment implements OnClickListener{

	private View clampingView;
	private EditText edtClampingProtectionClamp;
	private EditText edtClampingClampHalfNonChargeable;
	private EditText edtClampingClampHalfChargeable;
	private EditText edtClampingClampOneNonChargeable;
	private EditText edtClampingClampOneChargeable;
	private EditText edtCheeseHeadeScrewNonChargeable;
	private EditText edtCheeseHeadeScrewChargeable;
	private EditText edtWoodScrewNonChargeable;
	private EditText edtWoodScrewChargeable;
	private EditText edtPlugTypeNonChargeable;
	private EditText edtPlugTypeChargeable;

	private TextView lblPipeLineProtectionClamps;
	private TextView lblClampSetisfectory;
	private TextView lblClampSize1;
	private TextView lblClampSize2;
	private TextView lblCheeseHeadScrew;
	private TextView lblWoodScrew;
	private TextView lblRoulPlug;
	private TextView lblClampingHeader;
	private TextView errTxtClamHalf;
	private TextView errTxtClamOne;
	private TextView errTxtHdScrew;
	private TextView errTxtWdScrew;
	private TextView errTxtPlug;
	private TextView errTxtIsWorking;
	private TextView errClampingProtectionClamp;
	private TextView txtVendorSupplied;
	private TextView txtCompanySupplied;
	private TextView txtNoChargableQty;
	private TextView txtChargableQty;

	private Button btnNext;
	private Button btnPrev;
	private Button btnClamping;

	private int isClamping =  -1;
	private int id;
	public int custStatuid;

	private FragmentManager fragmentManager;
	private String []clampWorking = new String []{};
	private List<String> dropdownValues ;
	public static StringBuffer clampingErrorMsg = new StringBuffer();

	public UserMasterService masterService;

	private String user = Constants.LABEL_BLANK;
	private String  clampingOption;

	private RadioGroup radGroupClampingIsWorking;
	private RadioButton radBtnYes,radBtnNo;

	private RadioButton radBtnClampHalfByCompany;
	private RadioButton radBtnClampHalfByVendor;
	private RadioButton radBtnClampingClampOneByCompany;
	private RadioButton radBtnClampingClampOneByVendor;
	private RadioButton radBtnCheeseHeadeScrewByCompany;
	private RadioButton radBtnCheeseHeadeScrewByVendor;
	private RadioButton radBtnWoodScrewByCompany;
	private RadioButton radBtnWoodScrewByVendor;
	private RadioButton radBtnPlugTypeByCompany;
	private RadioButton radBtnPlugTypeByVendor;
	private String contactNumber;
	private LinearLayout llClamping,llClampingDetail;
	private android.widget.CompoundButton.OnCheckedChangeListener clampHalfChangeListener = new android.widget.CompoundButton.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
									 boolean isChecked) {
			// TODO Auto-generated method stub
			checkSuppliedStatus();

		}

	};

	/**
	 * A constructor
	 * @return ClampingFragment object
	 */
	public static ClampingFragment newInstance(int custStatusId, String contactNumber){
		ClampingFragment clampingFragment = new ClampingFragment();
		Bundle args = new Bundle();
		args.putInt("statusid", custStatusId);
		args.putString("contactNumber", contactNumber);
		clampingFragment.setArguments(args);
		return clampingFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		clampingView = inflater.inflate(R.layout.form_clamping,null);
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::ClampingFragmentStarted:::");
		fragmentManager = getFragmentManager();
		custStatuid = getArguments().getInt("statusid");
		contactNumber = getArguments().getString("contactNumber");
		user = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.DB_USERNAME, "tech");
		edtClampingProtectionClamp = (EditText) clampingView.findViewById(R.id.edtClampingProtectionClamp);
		edtClampingClampHalfNonChargeable = (EditText) clampingView.findViewById(R.id.edtClampingClampHalfNonChargeable);
		edtClampingClampHalfChargeable  = (EditText) clampingView.findViewById(R.id.edtClampingClampHalfChargeable);
		edtClampingClampOneNonChargeable = (EditText) clampingView.findViewById(R.id.edtClampingClampOneNonChargeable);
		edtClampingClampOneChargeable = (EditText) clampingView.findViewById(R.id.edtClampingClampOneChargeable);
		edtCheeseHeadeScrewNonChargeable = (EditText) clampingView.findViewById(R.id.edtCheeseHeadeScrewNonChargeable);
		edtCheeseHeadeScrewChargeable = (EditText) clampingView.findViewById(R.id.edtCheeseHeadeScrewChargeable);
		edtWoodScrewNonChargeable = (EditText) clampingView.findViewById(R.id.edtWoodScrewNonChargeable);
		edtWoodScrewChargeable = (EditText) clampingView.findViewById(R.id.edtWoodScrewChargeable);
		edtPlugTypeNonChargeable = (EditText) clampingView.findViewById(R.id.edtPlugTypeNonChargeable);
		edtPlugTypeChargeable = (EditText) clampingView.findViewById(R.id.edtPlugTypeChargeable);

		radBtnClampHalfByCompany = (RadioButton) clampingView.findViewById(R.id.radBtnClampHalfByCompany);
		radBtnClampHalfByVendor = (RadioButton) clampingView.findViewById(R.id.radBtnClampHalfByVendor);
		radBtnClampingClampOneByCompany = (RadioButton) clampingView.findViewById(R.id.radBtnClampingClampOneByCompany);
		radBtnClampingClampOneByVendor = (RadioButton) clampingView.findViewById(R.id.radBtnClampingClampOneByVendor);
		radBtnCheeseHeadeScrewByCompany = (RadioButton) clampingView.findViewById(R.id.radBtnCheeseHeadeScrewByCompany);
		radBtnCheeseHeadeScrewByVendor = (RadioButton) clampingView.findViewById(R.id.radBtnCheeseHeadeScrewByVendor);
		radBtnWoodScrewByCompany = (RadioButton) clampingView.findViewById(R.id.radBtnWoodScrewByCompany);
		radBtnWoodScrewByVendor = (RadioButton) clampingView.findViewById(R.id.radBtnWoodScrewByVendor);
		radBtnPlugTypeByCompany = (RadioButton) clampingView.findViewById(R.id.radBtnPlugTypeByCompany);
		radBtnPlugTypeByVendor = (RadioButton) clampingView.findViewById(R.id.radBtnPlugTypeByVendor);

		radBtnClampHalfByCompany.setOnCheckedChangeListener(clampHalfChangeListener);
		radBtnClampHalfByVendor.setOnCheckedChangeListener(clampHalfChangeListener);
		radBtnClampingClampOneByCompany.setOnCheckedChangeListener(clampHalfChangeListener);
		radBtnClampingClampOneByVendor.setOnCheckedChangeListener(clampHalfChangeListener);
		radBtnCheeseHeadeScrewByCompany.setOnCheckedChangeListener(clampHalfChangeListener);
		radBtnCheeseHeadeScrewByVendor.setOnCheckedChangeListener(clampHalfChangeListener);
		radBtnWoodScrewByCompany.setOnCheckedChangeListener(clampHalfChangeListener);
		radBtnWoodScrewByVendor.setOnCheckedChangeListener(clampHalfChangeListener);
		radBtnPlugTypeByCompany.setOnCheckedChangeListener(clampHalfChangeListener);
		radBtnPlugTypeByVendor.setOnCheckedChangeListener(clampHalfChangeListener);

		txtVendorSupplied = (TextView) clampingView.findViewById(R.id.txtVendorSupplied);
		txtCompanySupplied = (TextView) clampingView.findViewById(R.id.txtCompanySupplied);
		txtNoChargableQty = (TextView) clampingView.findViewById(R.id.txtNoChargableQty);
		txtChargableQty = (TextView) clampingView.findViewById(R.id.txtChargableQty);


	/*	edtClampHalf = (EditText)clampingView.findViewById(R.id.edtClampingClampHalf);
		edtClampOne = (EditText) clampingView.findViewById(R.id.edtClampingClampOne);
		edtCheeseHeadScrew = (EditText)clampingView.findViewById(R.id.edtClampingCheeseHeadeScrew);
		edtWoodScrew = (EditText)clampingView.findViewById(R.id.edtClampingWoodScrew);
		edtPlugType = (EditText)clampingView.findViewById(R.id.edtClampingPlugType);*/

		lblPipeLineProtectionClamps = (TextView) clampingView.findViewById(R.id.lblPipeLineProtectionClamps);
		btnClamping = (Button)clampingView.findViewById(R.id.btnClampingIsWorking);
		lblCheeseHeadScrew = (TextView)clampingView.findViewById(R.id.lblClampingCheeseHeadScrew);
		lblClampSetisfectory = (TextView)clampingView.findViewById(R.id.lblClampingIsWorking);
		lblClampSize1 = (TextView)clampingView.findViewById(R.id.lblClampingClampHalf);
		lblClampSize2 = (TextView)clampingView.findViewById(R.id.lblClampingClampOne);
		lblRoulPlug = (TextView)clampingView.findViewById(R.id.lblClampingPlugType);
		lblWoodScrew = (TextView)clampingView.findViewById(R.id.lblClampingWoodScrew);
		lblClampingHeader = (TextView)clampingView.findViewById(R.id.lblClampingHeader);
		btnClamping.setOnClickListener(this);
		btnNext = (Button)clampingView.findViewById(R.id.btnClampingSaveandNext);
		btnPrev = (Button)clampingView.findViewById(R.id.btnClampingPrev);

		masterService = new UserMasterService(getActivity());
		clampWorking = getResources().getStringArray(R.array.arrayCheckBoolean);
		dropdownValues = Arrays.asList(clampWorking);

		errTxtClamHalf = (TextView)clampingView.findViewById(R.id.errClampingClampHalf);
		errTxtClamOne = (TextView)clampingView.findViewById(R.id.errClampingClampOne);
		errTxtHdScrew = (TextView)clampingView.findViewById(R.id.errClampingCheeseHeadeScrew);
		errTxtWdScrew = (TextView)clampingView.findViewById(R.id.errClampingWoodScrew);
		errTxtPlug = (TextView)clampingView.findViewById(R.id.errClampingPlugType);
		errTxtIsWorking = (TextView)clampingView.findViewById(R.id.errClampingIsWorking);
		errClampingProtectionClamp = (TextView) clampingView.findViewById(R.id.errClampingProtectionClamp);

		radGroupClampingIsWorking = (RadioGroup)clampingView.findViewById(R.id.radGroupClampingIsWorking);
		radBtnYes = (RadioButton)clampingView.findViewById(R.id.radBtnYes);
		radBtnNo = (RadioButton)clampingView.findViewById(R.id.radBtnNo);

		llClamping = (LinearLayout) clampingView.findViewById(R.id.llClamping);
		llClampingDetail = (LinearLayout) clampingView.findViewById(R.id.llClampingDetail);

		changeLanguage(id);

		if(Global.isCycleRunning)
			MaintanenceForm.btnHeaderProcess.setVisibility(View.VISIBLE);
		else
			MaintanenceForm.btnHeaderProcess.setVisibility(View.INVISIBLE);

		if((custStatuid != masterService.getStatusId("OP") && custStatuid != masterService.getStatusId("HCL1"))  ||user.equals("admin")){
			clampingDisableViews();
		}

		btnNext.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Utility.changeSelectedForm(2, 1);
				Utility.changeList(2,false);
				PaintingFragment paintingFragment = PaintingFragment.newInstance(custStatuid, contactNumber);
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.frmLayoutFragment, paintingFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		});

		btnPrev.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Utility.changeSelectedForm(0, 1);
				Utility.changeList(0,false);
				TestingFragment testingFragment = TestingFragment.newInstance(custStatuid, contactNumber);
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.frmLayoutFragment, testingFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		});

		radGroupClampingIsWorking.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {

					case R.id.radBtnYes:
						btnClamping.setText(radBtnYes.getText().toString());
						ClampingVO.setIsWorking(radBtnYes.getText().toString());

						edtClampingClampHalfNonChargeable.setEnabled(false);
						edtClampingClampHalfChargeable.setEnabled(false);
						edtClampingClampOneNonChargeable.setEnabled(false);
						edtClampingClampOneChargeable.setEnabled(false);

						edtClampingClampHalfNonChargeable.setText(Constants.BLANK);
						edtClampingClampHalfChargeable.setText(Constants.BLANK);
						edtClampingClampOneNonChargeable.setText(Constants.BLANK);
						edtClampingClampOneChargeable.setText(Constants.BLANK);
					 
					/*edtClampHalf.setText(Constants.BLANK);
					edtClampOne.setText(Constants.BLANK);
					edtClampHalf.setEnabled(false);
					edtClampOne.setEnabled(false);*/
						llClamping.setVisibility(View.GONE);
						llClampingDetail.setVisibility(View.GONE);
						break;

					case R.id.radBtnNo:
						btnClamping.setText(radBtnNo.getText().toString());
						ClampingVO.setIsWorking(radBtnNo.getText().toString());
						edtClampingClampHalfNonChargeable.setEnabled(true);
						edtClampingClampHalfChargeable.setEnabled(true);
						edtClampingClampOneNonChargeable.setEnabled(true);
						edtClampingClampOneChargeable.setEnabled(true);

						llClamping.setVisibility(View.VISIBLE);
						llClampingDetail.setVisibility(View.VISIBLE);
						break;
				}
				checkSuppliedStatus();
				validateButton();
			}
		});

		initializeValues();
		checkSuppliedStatus();

		return clampingView;
	}

	@Override
	public void onPause() {

		super.onPause();
		validateButton();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();

	}

	public void checkSuppliedStatus() {
		if(radBtnClampHalfByCompany.isChecked() || radBtnClampHalfByVendor.isChecked()) {
			Log.e(getClass().getName(), "******************Change Listener called: Half Clamp");
			edtClampingClampHalfChargeable.setEnabled(true);
			edtClampingClampHalfNonChargeable.setEnabled(true);
		} else {
			edtClampingClampHalfChargeable.setEnabled(false);
			edtClampingClampHalfNonChargeable.setEnabled(false);
		}

		if(radBtnClampingClampOneByCompany.isChecked() || radBtnClampingClampOneByVendor.isChecked()) {
			Log.e(getClass().getName(), "******************Change Listener called: One Clamp");
			edtClampingClampOneChargeable.setEnabled(true);
			edtClampingClampOneNonChargeable.setEnabled(true);
		} else {
			edtClampingClampOneChargeable.setEnabled(false);
			edtClampingClampOneNonChargeable.setEnabled(false);
		}

		if(radBtnCheeseHeadeScrewByCompany.isChecked() || radBtnCheeseHeadeScrewByVendor.isChecked()) {
			Log.e(getClass().getName(), "******************Change Listener called: Cheese Head Screw");
			edtCheeseHeadeScrewChargeable.setEnabled(true);
			edtCheeseHeadeScrewNonChargeable.setEnabled(true);
		} else {
			edtCheeseHeadeScrewChargeable.setEnabled(false);
			edtCheeseHeadeScrewNonChargeable.setEnabled(false);
		}

		if(radBtnWoodScrewByCompany.isChecked() || radBtnWoodScrewByVendor.isChecked()) {
			Log.e(getClass().getName(), "******************Change Listener called: Wood Screw");
			edtWoodScrewChargeable.setEnabled(true);
			edtWoodScrewNonChargeable.setEnabled(true);
		} else {
			edtWoodScrewChargeable.setEnabled(false);
			edtWoodScrewNonChargeable.setEnabled(false);
		}

		if(radBtnPlugTypeByCompany.isChecked() || radBtnPlugTypeByVendor.isChecked()) {
			Log.e(getClass().getName(), "******************Change Listener called: Plug Type");
			edtPlugTypeChargeable.setEnabled(true);
			edtPlugTypeNonChargeable.setEnabled(true);
		} else {
			edtPlugTypeChargeable.setEnabled(false);
			edtPlugTypeNonChargeable.setEnabled(false);
		}
	}

	//	@Override
//	public void onDestroy() {	
//		super.onDestroy();
//		if(edtClampHalf != null && edtClampOne != null && edtCheeseHeadScrew != null && edtWoodScrew != null && edtPlugType != null)
//		{
//			edtClampHalf.setText(Constants.LABEL_BLANK);
//			edtClampOne.setText(Constants.LABEL_BLANK);
//			edtCheeseHeadScrew.setText(Constants.LABEL_BLANK);
//			edtWoodScrew.setText(Constants.LABEL_BLANK);
//			edtPlugType.setText(Constants.LABEL_BLANK);
//		}
//	}
	public void clampingDisableViews(){

		edtClampingClampHalfNonChargeable.setEnabled(false);
		edtClampingClampHalfChargeable.setEnabled(false);
		edtClampingClampOneNonChargeable.setEnabled(false);
		edtClampingClampOneChargeable.setEnabled(false);
		edtCheeseHeadeScrewChargeable.setEnabled(false);
		edtCheeseHeadeScrewNonChargeable.setEnabled(false);
		edtClampingProtectionClamp.setEnabled(false);
		edtWoodScrewChargeable.setEnabled(false);
		edtWoodScrewNonChargeable.setEnabled(false);
		edtPlugTypeChargeable.setEnabled(false);
		edtPlugTypeNonChargeable.setEnabled(false);

		btnClamping.setEnabled(false);
		radBtnNo.setEnabled(false);
		radBtnYes.setEnabled(false);
		llClamping.setVisibility(View.GONE);
		llClampingDetail.setVisibility(View.GONE);
	}


	public void validateButton(){

		try {
			ArrayList<Boolean> errorList = new ArrayList<Boolean>();

			ClampingVO.setPipelineProtectionClamp(edtClampingProtectionClamp.getText().toString());

			ClampingVO.setChrgClamp1by2(edtClampingClampHalfChargeable.getText().toString());
			ClampingVO.setClamp1by2(edtClampingClampHalfNonChargeable.getText().toString());

			ClampingVO.setChrgClamp1(edtClampingClampOneChargeable.getText().toString());
			ClampingVO.setClamp1(edtClampingClampOneNonChargeable.getText().toString());

			ClampingVO.setChrgCheeseHeadScrew(edtCheeseHeadeScrewChargeable.getText().toString());
			ClampingVO.setCheeseHeadScrew(edtCheeseHeadeScrewNonChargeable.getText().toString());

			ClampingVO.setChrgWoodScrew(edtWoodScrewChargeable.getText().toString());
			ClampingVO.setWoodScrew(edtWoodScrewNonChargeable.getText().toString());

			ClampingVO.setChrgRoulPlug(edtPlugTypeChargeable.getText().toString());
			ClampingVO.setRoulPlug(edtPlugTypeNonChargeable.getText().toString());



			if(radBtnClampHalfByCompany.isChecked()){
				ClampingVO.setSplrClamp1by2(Constants.SUPPLY_COMPANY);
			}else if(radBtnClampHalfByVendor.isChecked()){
				ClampingVO.setSplrClamp1by2(Constants.SUPPLY_VENDOR);
			}else{
				ClampingVO.setSplrClamp1by2(Constants.LABEL_BLANK);
			}

			if(radBtnClampingClampOneByCompany.isChecked()){
				ClampingVO.setSplrClamp1(Constants.SUPPLY_COMPANY);
			}else if(radBtnClampingClampOneByVendor.isChecked()){
				ClampingVO.setSplrClamp1(Constants.SUPPLY_VENDOR);
			}else{
				ClampingVO.setSplrClamp1(Constants.LABEL_BLANK);
			}

			if(radBtnCheeseHeadeScrewByCompany.isChecked()){
				ClampingVO.setSplrCheeseHeadScrew(Constants.SUPPLY_COMPANY);
			}else if(radBtnCheeseHeadeScrewByVendor.isChecked()){
				ClampingVO.setSplrCheeseHeadScrew(Constants.SUPPLY_VENDOR);
			}else{
				ClampingVO.setSplrCheeseHeadScrew(Constants.LABEL_BLANK);
			}

			if(radBtnWoodScrewByCompany.isChecked()){
				ClampingVO.setSplrWoodScrew(Constants.SUPPLY_COMPANY);
			}else if(radBtnWoodScrewByVendor.isChecked()){
				ClampingVO.setSplrWoodScrew(Constants.SUPPLY_VENDOR);
			}else{
				ClampingVO.setSplrWoodScrew(Constants.LABEL_BLANK);
			}

			if(radBtnPlugTypeByCompany.isChecked()){
				ClampingVO.setSplrRoulPlug(Constants.SUPPLY_COMPANY);
			}else if(radBtnPlugTypeByVendor.isChecked()){
				ClampingVO.setSplrRoulPlug(Constants.SUPPLY_VENDOR);
			}else{
				ClampingVO.setSplrRoulPlug(Constants.LABEL_BLANK);
			}



//		errTxtClamHalf.setText(Utility.Clamping(getActivity(),Constants.CLAMP_HALF,edtClampHalf.getText().toString().trim()));
//		if(errTxtClamHalf.getText().toString().equals(Constants.LABEL_BLANK))
//			errorList.add(false);
//		else
//			errorList.add(true);
			//ClampingVO.setClampHalf(edtClampHalf.getText().toString());
//
//		errTxtClamOne.setText(Utility.Clamping(getActivity(),Constants.CLAMP_ONE,edtClampOne.getText().toString().trim()));		
			//ClampingVO.setClampOne(edtClampOne.getText().toString());
//		if(errTxtClamOne.getText().toString().equals(Constants.LABEL_BLANK))
//			errorList.add(false);
//		else
//			errorList.add(true);
//
//		errTxtHdScrew.setText(Utility.Clamping(getActivity(),Constants.CHEESE_HEAD_SCREW,edtCheeseHeadScrew.getText().toString().trim()));
			//	ClampingVO.setCheeseHeadScrew(edtCheeseHeadScrew.getText().toString());
//		if(errTxtHdScrew.getText().toString().equals(Constants.LABEL_BLANK))
//			errorList.add(false);
//		else
//			errorList.add(true);
//
//		errTxtWdScrew.setText( Utility.Clamping(getActivity(),Constants.WOOD_SCREW,edtWoodScrew.getText().toString().trim()));
			//ClampingVO.setWoodScrew(edtWoodScrew.getText().toString());
//		if(errTxtWdScrew.getText().toString().equals(Constants.LABEL_BLANK))
//			errorList.add(false);
//		else
//			errorList.add(true);
//
//		errTxtPlug.setText( Utility.Clamping(getActivity(),Constants.ROUL_PLUG,edtPlugType.getText().toString().trim()));
			//ClampingVO.setRoulPlug(edtPlugType.getText().toString());
//		if(errTxtPlug.getText().toString().equals(Constants.LABEL_BLANK))
//			errorList.add(false);
//		else
//			errorList.add(true);

			if(btnClamping.getText().toString().equals(getResources().getString(R.string.title_select_option)))
			{
				errorList.add(true);
				errTxtIsWorking.setText(Constants.ERROR_MARK);

			}else{
				//errorList.add(false);
				errTxtIsWorking.setText(Constants.ERROR_MARK);
//			errTxtIsWorking.setText(Constants.LABEL_BLANK);
			}
		
		/*if(edtClampingProtectionClamp.getText().toString().equals(Constants.BLANK)) {
			errorList.add(true);
		} else {
			errorList.add(false);
		}*/
			if(btnClamping.getText().toString().equalsIgnoreCase("yes")) {

				if(radBtnCheeseHeadeScrewByCompany.isChecked() || radBtnCheeseHeadeScrewByVendor.isChecked()) {
					if(edtCheeseHeadeScrewChargeable.getText().toString().equals(Constants.BLANK) && edtCheeseHeadeScrewNonChargeable.getText().toString().equals(Constants.BLANK)) {
						errorList.add(true);
					} else {
						errorList.add(false);
					}
				} else {
					errorList.add(false);
				}

				if(radBtnWoodScrewByCompany.isChecked() || radBtnWoodScrewByVendor.isChecked()) {
					if(edtWoodScrewChargeable.getText().toString().equals(Constants.BLANK) && edtWoodScrewNonChargeable.getText().toString().equals(Constants.BLANK)) {
						errorList.add(true);
					} else {
						errorList.add(false);
					}
				} else {
					errorList.add(false);
				}

				if(radBtnPlugTypeByCompany.isChecked() || radBtnPlugTypeByVendor.isChecked()) {
					if(edtPlugTypeChargeable.getText().toString().equals(Constants.BLANK) && edtPlugTypeNonChargeable.getText().toString().equals(Constants.BLANK)) {
						errorList.add(true);
					} else {
						errorList.add(false);
					}
				} else {
					errorList.add(false);
				}
			/*if(edtCheeseHeadeScrewChargeable.getText().toString().equals(Constants.BLANK) ||
					edtCheeseHeadeScrewNonChargeable.getText().toString().equalsIgnoreCase(Constants.BLANK) ||
					edtWoodScrewChargeable.getText().toString().equals(Constants.BLANK) ||
					edtWoodScrewNonChargeable.getText().toString().equals(Constants.BLANK) ||
					edtPlugTypeChargeable.getText().toString().equals(Constants.BLANK) ||
					edtPlugTypeNonChargeable.getText().toString().equals(Constants.BLANK)) {
				errorList.add(true);
				errTxtIsWorking.setText(Constants.ERROR_MARK);
			} else {
				errorList.add(false);
				errTxtIsWorking.setText(Constants.ERROR_MARK);
			}*/
			} else if(btnClamping.getText().toString().equalsIgnoreCase("no")){

				if(radBtnCheeseHeadeScrewByCompany.isChecked() || radBtnCheeseHeadeScrewByVendor.isChecked()) {
					if(edtCheeseHeadeScrewChargeable.getText().toString().equals(Constants.BLANK) && edtCheeseHeadeScrewNonChargeable.getText().toString().equals(Constants.BLANK)) {
						errorList.add(true);
					} else {
						errorList.add(false);
					}
				} else {
					errorList.add(false);
				}

				if(radBtnWoodScrewByCompany.isChecked() || radBtnWoodScrewByVendor.isChecked()) {
					if(edtWoodScrewChargeable.getText().toString().equals(Constants.BLANK) && edtWoodScrewNonChargeable.getText().toString().equals(Constants.BLANK)) {
						errorList.add(true);
					} else {
						errorList.add(false);
					}
				} else {
					errorList.add(false);
				}

				if(radBtnPlugTypeByCompany.isChecked() || radBtnPlugTypeByVendor.isChecked()) {
					if(edtPlugTypeChargeable.getText().toString().equals(Constants.BLANK) && edtPlugTypeNonChargeable.getText().toString().equals(Constants.BLANK)) {
						errorList.add(true);
					} else {
						errorList.add(false);
					}
				} else {
					errorList.add(false);
				}

				if(radBtnClampHalfByCompany.isChecked() || radBtnClampHalfByVendor.isChecked()) {
					if(edtClampingClampHalfChargeable.getText().toString().equals(Constants.BLANK) && edtClampingClampHalfNonChargeable.getText().toString().equals(Constants.BLANK)) {
						errorList.add(true);
					} else {
						errorList.add(false);
					}
				} else {
					errorList.add(false);
				}

				if(radBtnClampingClampOneByCompany.isChecked() || radBtnClampingClampOneByVendor.isChecked()) {
					if(edtClampingClampOneChargeable.getText().toString().equals(Constants.BLANK) && edtClampingClampOneNonChargeable.getText().toString().equals(Constants.BLANK)) {
						errorList.add(true);
					} else {
						errorList.add(false);
					}
				} else {
					errorList.add(false);
				}
			
			
			/*if(edtClampingClampOneChargeable.getText().toString().equals(Constants.BLANK) ||
					edtClampingClampOneNonChargeable.getText().toString().equals(Constants.BLANK) ||
					edtClampingClampHalfChargeable.getText().toString().equals(Constants.BLANK) ||
					edtClampingClampHalfNonChargeable.getText().toString().equals(Constants.BLANK) ||
					edtCheeseHeadeScrewChargeable.getText().toString().equals(Constants.BLANK) ||
					edtCheeseHeadeScrewNonChargeable.getText().toString().equalsIgnoreCase(Constants.BLANK) ||
					edtWoodScrewChargeable.getText().toString().equals(Constants.BLANK) ||
					edtWoodScrewNonChargeable.getText().toString().equals(Constants.BLANK) ||
					edtPlugTypeChargeable.getText().toString().equals(Constants.BLANK) ||
					edtPlugTypeNonChargeable.getText().toString().equals(Constants.BLANK)) {
				errorList.add(true);
				errTxtIsWorking.setText(Constants.ERROR_MARK);
			} else {
				errorList.add(false);
				errTxtIsWorking.setText(Constants.ERROR_MARK);
			}*/
			} else {
				errorList.add(false);
				errTxtIsWorking.setText(Constants.ERROR_MARK);
			}
			Collections.sort(errorList);
			System.err.println(" Correction Error --- "+ Collections.binarySearch(errorList,true));
			if(Collections.binarySearch(errorList,true) >= -1)
				Utility.changeList(1,true);
			else
				Utility.changeList(1,false);
		} catch(IllegalStateException e) {

		}
		/*Log.d("CLAMPING","validate btn :Half :-"+ClampingVO.getClampHalf());
		Log.d("CLAMPING","One :-"+ClampingVO.getClampOne());
		Log.d("CLAMPING","Cheese head screw :-"+ClampingVO.getCheeseHeadScrew());
		Log.d("CLAMPING","wood screw :-"+ClampingVO.getWoodScrew());
		Log.d("CLAMPING","plug :-"+ClampingVO.getRoulPlug());*/
	}



	public void onClick(View v) {

		if(v == btnClamping){
			try {
				new AlertDialog.Builder(getActivity()).setSingleChoiceItems(clampWorking,isClamping,new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int whichButton) {

						btnClamping.setText(clampWorking[whichButton]);
						isClamping = whichButton;
						if(clampWorking[whichButton].equals("NO")){

							edtClampingClampHalfNonChargeable.setEnabled(true);
							edtClampingClampHalfChargeable.setEnabled(true);
							edtClampingClampOneNonChargeable.setEnabled(true);
							edtClampingClampOneChargeable.setEnabled(true);
							
							/*edtClampHalf.setEnabled(true);
							edtClampOne.setEnabled(true);*/
//							edtCheeseHeadScrew.setEnabled(true);
//							edtWoodScrew.setEnabled(true);
//							edtPlugType.setEnabled(true);
							llClamping.setVisibility(View.VISIBLE);
							llClampingDetail.setVisibility(View.VISIBLE);
							ClampingVO.setIsWorking(clampWorking[whichButton]);
						}else{
							edtClampingClampHalfNonChargeable.setEnabled(false);
							edtClampingClampHalfChargeable.setEnabled(false);
							edtClampingClampOneNonChargeable.setEnabled(false);
							edtClampingClampOneChargeable.setEnabled(false);

							edtClampingClampHalfNonChargeable.setText(Constants.BLANK);
							edtClampingClampHalfChargeable.setText(Constants.BLANK);
							edtClampingClampOneNonChargeable.setText(Constants.BLANK);
							edtClampingClampOneChargeable.setText(Constants.BLANK);
							/*edtClampHalf.setEnabled(false);
							edtClampOne.setEnabled(false);*/
							llClamping.setVisibility(View.GONE);
							llClampingDetail.setVisibility(View.GONE);
//							edtCheeseHeadScrew.setEnabled(false);
//							edtWoodScrew.setEnabled(false);
//							edtPlugType.setEnabled(false);
							ClampingVO.setIsWorking(clampWorking[whichButton]);
						}

						validateButton();

						dialog.dismiss();
					}
				}).show();

			} catch (Exception e) {
				e.printStackTrace();
				Log.d(Constants.DEBUG,"ClampingFragment:onClick:"+ Utility.convertExceptionToString(e));
			}
		}
	}

	private void initializeValues(){

		errTxtClamHalf.setText(Utility.Clamping(getActivity(),Constants.CLAMP_HALF,edtClampingClampHalfChargeable.getText().toString().trim()));
		errTxtClamOne.setText(Utility.Clamping(getActivity(),Constants.CLAMP_ONE,edtClampingClampOneChargeable.getText().toString().trim()));
		errTxtHdScrew.setText(Utility.Clamping(getActivity(),Constants.CHEESE_HEAD_SCREW,edtCheeseHeadeScrewChargeable.getText().toString().trim()));
		errTxtWdScrew.setText( Utility.Clamping(getActivity(),Constants.WOOD_SCREW,edtWoodScrewChargeable.getText().toString().trim()));
		errTxtPlug.setText( Utility.Clamping(getActivity(),Constants.ROUL_PLUG,edtPlugTypeChargeable.getText().toString().trim()));
		errClampingProtectionClamp.setText( Utility.Clamping(getActivity(),Constants.PIPELINE_PROTECITON_CLAMPS,edtClampingProtectionClamp.getText().toString().trim()));

		clampingOption = ClampingVO.getIsWorking();
		/*Log.e("CLAMPING","Half :-"+ClampingVO.getClampHalf());
		Log.e("CLAMPING","One :-"+ClampingVO.getClampOne());
		Log.e("CLAMPING","Cheese head screw :-"+ClampingVO.getCheeseHeadScrew());
		Log.e("CLAMPING","wood screw :-"+ClampingVO.getWoodScrew());
		Log.e("CLAMPING","plug :-"+ClampingVO.getRoulPlug());*/

		edtClampingProtectionClamp.setText(ClampingVO.getPipelineProtectionClamp());
		edtClampingClampHalfNonChargeable.setText(ClampingVO.getClamp1by2());
		edtClampingClampHalfChargeable.setText(ClampingVO.getChrgClamp1by2());
		edtClampingClampOneNonChargeable.setText(ClampingVO.getClamp1());
		edtClampingClampOneChargeable.setText(ClampingVO.getChrgClamp1());
		edtCheeseHeadeScrewNonChargeable.setText(ClampingVO.getCheeseHeadScrew());
		edtCheeseHeadeScrewChargeable.setText(ClampingVO.getChrgCheeseHeadScrew());
		edtWoodScrewNonChargeable.setText(ClampingVO.getWoodScrew());
		edtWoodScrewChargeable.setText(ClampingVO.getChrgWoodScrew());
		edtPlugTypeNonChargeable.setText(ClampingVO.getRoulPlug());
		edtPlugTypeChargeable.setText(ClampingVO.getChrgRoulPlug());

		if(ClampingVO.getSplrClamp1by2().endsWith(Constants.SUPPLY_COMPANY)){
			radBtnClampHalfByCompany.setChecked(true);
			radBtnClampHalfByVendor.setChecked(false);
		}else if(ClampingVO.getSplrClamp1by2().endsWith(Constants.SUPPLY_VENDOR)){
			radBtnClampHalfByCompany.setChecked(false);
			radBtnClampHalfByVendor.setChecked(true);
		}else{
			radBtnClampHalfByCompany.setChecked(false);
			radBtnClampHalfByVendor.setChecked(false);
		}

		if(ClampingVO.getSplrClamp1().endsWith(Constants.SUPPLY_COMPANY)){
			radBtnClampingClampOneByCompany.setChecked(true);
			radBtnClampingClampOneByVendor.setChecked(false);
		}else if(ClampingVO.getSplrClamp1().endsWith(Constants.SUPPLY_VENDOR)){
			radBtnClampingClampOneByCompany.setChecked(false);
			radBtnClampingClampOneByVendor.setChecked(true);
		}else{
			radBtnClampingClampOneByCompany.setChecked(false);
			radBtnClampingClampOneByVendor.setChecked(false);
		}

		if(ClampingVO.getSplrCheeseHeadScrew().endsWith(Constants.SUPPLY_COMPANY)){
			radBtnCheeseHeadeScrewByCompany.setChecked(true);
			radBtnCheeseHeadeScrewByVendor.setChecked(false);
		}else if(ClampingVO.getSplrCheeseHeadScrew().endsWith(Constants.SUPPLY_VENDOR)){
			radBtnCheeseHeadeScrewByCompany.setChecked(false);
			radBtnCheeseHeadeScrewByVendor.setChecked(true);
		}else{
			radBtnCheeseHeadeScrewByCompany.setChecked(false);
			radBtnCheeseHeadeScrewByVendor.setChecked(false);
		}

		if(ClampingVO.getSplrWoodScrew().endsWith(Constants.SUPPLY_COMPANY)){
			radBtnWoodScrewByCompany.setChecked(true);
			radBtnWoodScrewByVendor.setChecked(false);
		}else if(ClampingVO.getSplrWoodScrew().endsWith(Constants.SUPPLY_VENDOR)){
			radBtnWoodScrewByCompany.setChecked(false);
			radBtnWoodScrewByVendor.setChecked(true);
		}else{
			radBtnWoodScrewByCompany.setChecked(false);
			radBtnWoodScrewByVendor.setChecked(false);
		}

		if(ClampingVO.getSplrRoulPlug().endsWith(Constants.SUPPLY_COMPANY)){
			radBtnPlugTypeByCompany.setChecked(true);
			radBtnPlugTypeByVendor.setChecked(false);
		}else if(ClampingVO.getSplrRoulPlug().endsWith(Constants.SUPPLY_VENDOR)){
			radBtnPlugTypeByCompany.setChecked(false);
			radBtnPlugTypeByVendor.setChecked(true);
		}else{
			radBtnPlugTypeByCompany.setChecked(false);
			radBtnPlugTypeByVendor.setChecked(false);
		}



		if(clampingOption.equals(Constants.LABEL_BLANK))
		{
			clampingOption = getResources().getString(R.string.title_select_option);
			radBtnNo.setChecked(false);
			radBtnYes.setChecked(false);
			llClamping.setVisibility(View.GONE);
			llClampingDetail.setVisibility(View.GONE);
		}
		isClamping = dropdownValues.indexOf(clampingOption);
		btnClamping.setText(clampingOption) ;

		if(clampingOption.equals("NO")){

			radBtnNo.setChecked(true);
			radBtnYes.setChecked(false);

			llClamping.setVisibility(View.VISIBLE);
			llClampingDetail.setVisibility(View.VISIBLE);
			if(custStatuid != masterService.getStatusId("OP"))
			{
				edtClampingClampHalfNonChargeable.setEnabled(false);
				edtClampingClampHalfChargeable.setEnabled(false);
				edtClampingClampOneNonChargeable.setEnabled(false);
				edtClampingClampOneChargeable.setEnabled(false);
				/*edtClampHalf.setEnabled(false);
				edtClampOne.setEnabled(false);*/
//				edtCheeseHeadScrew.setEnabled(false);
//				edtWoodScrew.setEnabled(false);
//				edtPlugType.setEnabled(false);
			}
			else
			{
				edtClampingClampHalfNonChargeable.setEnabled(true);
				edtClampingClampHalfChargeable.setEnabled(true);
				edtClampingClampOneNonChargeable.setEnabled(true);
				edtClampingClampOneChargeable.setEnabled(true);
				/*edtClampHalf.setEnabled(true);
				edtClampOne.setEnabled(true);*/
//				edtCheeseHeadScrew.setEnabled(true);
//				edtWoodScrew.setEnabled(true);
//				edtPlugType.setEnabled(true);			
			}
		}else if(clampingOption.equals("YES")){

			radBtnNo.setChecked(false);
			radBtnYes.setChecked(true);

			edtClampingClampHalfNonChargeable.setEnabled(false);
			edtClampingClampHalfChargeable.setEnabled(false);
			edtClampingClampOneNonChargeable.setEnabled(false);
			edtClampingClampOneChargeable.setEnabled(false);

			llClamping.setVisibility(View.GONE);
			llClampingDetail.setVisibility(View.GONE);
//				edtCheeseHeadScrew.setEnabled(false);
//				edtWoodScrew.setEnabled(false);
//				edtPlugType.setEnabled(false);			
		}



		validateButton();
	}

	public void changeLanguage(int id){

		id = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.PREF_LANGUAGE,Constants.LANGUAGE_ENGLISH);

		switch (id) {
			case Constants.LANGUAGE_ENGLISH:
				lblCheeseHeadScrew.setText(getResources().getText(R.string.title_clamping_cheese_head_screw_Eng));
				lblClampSetisfectory.setText(getResources().getText(R.string.title_clamping_is_clamping_work_Eng));
				lblClampSize1.setText(getResources().getText(R.string.title_clamping_clamp_half_Eng));
				lblClampSize2.setText(getResources().getText(R.string.title_clamping_clamp_one_Eng));
				lblRoulPlug.setText(getResources().getText(R.string.title_clamping_plug_type_Eng));
				lblWoodScrew.setText(getResources().getText(R.string.title_clamping_wood_screw_Eng));
				lblClampingHeader.setText(getResources().getText(R.string.header_clamping_Eng));
				lblPipeLineProtectionClamps.setText(getResources().getText(R.string.title_clamping_protection_clamp_Eng));
				txtVendorSupplied.setText(getResources().getText(R.string.lbl_by_supply_vendor_Eng));
				txtCompanySupplied.setText(getResources().getText(R.string.lbl_by_supply_company_Eng));
				txtNoChargableQty.setText(getResources().getText(R.string.lbl_non_chargable_qty_Eng));
				txtChargableQty.setText(getResources().getText(R.string.lbl_chargable_qty_Eng));
				break;
			case Constants.LANGUAGE_GUJRATI:
				lblCheeseHeadScrew.setTypeface(Global.typeface_Guj);
				lblClampSetisfectory.setTypeface(Global.typeface_Guj);
				lblClampSize1.setTypeface(Global.typeface_Guj);
				lblClampSize2.setTypeface(Global.typeface_Guj);
				lblRoulPlug.setTypeface(Global.typeface_Guj);
				lblWoodScrew.setTypeface(Global.typeface_Guj);
				lblClampingHeader.setTypeface(Global.typeface_Guj);
				lblPipeLineProtectionClamps.setTypeface(Global.typeface_Guj);
				txtVendorSupplied.setTypeface(Global.typeface_Guj);
				txtCompanySupplied.setTypeface(Global.typeface_Guj);
				txtNoChargableQty.setTypeface(Global.typeface_Guj);
				txtChargableQty.setTypeface(Global.typeface_Guj);
				lblClampingHeader.setText(getResources().getText(R.string.header_clamping_Guj));
				lblCheeseHeadScrew.setText(getResources().getText(R.string.title_clamping_cheese_head_screw_Guj));
				lblClampSetisfectory.setText(getResources().getText(R.string.title_clamping_is_clamping_work_Guj));
				lblClampSize1.setText(getResources().getText(R.string.title_clamping_clamp_half_Guj));
				lblClampSize2.setText(getResources().getText(R.string.title_clamping_clamp_one_Guj));
				lblRoulPlug.setText(getResources().getText(R.string.title_clamping_plug_type_Guj));
				lblWoodScrew.setText(getResources().getText(R.string.title_clamping_wood_screw_Guj));
				lblPipeLineProtectionClamps.setText(getResources().getText(R.string.title_clamping_protection_clamp_Guj));
				txtVendorSupplied.setText(getResources().getText(R.string.lbl_by_supply_vendor_Guj));
				txtCompanySupplied.setText(getResources().getText(R.string.lbl_by_supply_company_Guj));
				txtNoChargableQty.setText(getResources().getText(R.string.lbl_non_chargable_qty_Guj));
				txtChargableQty.setText(getResources().getText(R.string.lbl_chargable_qty_Guj));
				break;

			default:
				break;
		}
	}

	public void clearValues() {
		if(edtClampingProtectionClamp != null)
			edtClampingProtectionClamp.setText(Constants.BLANK);
		if(edtCheeseHeadeScrewChargeable != null)
			edtCheeseHeadeScrewChargeable.setText(Constants.BLANK);

		if(edtCheeseHeadeScrewNonChargeable != null)
			edtCheeseHeadeScrewNonChargeable.setText(Constants.BLANK);
		if(edtClampingClampHalfChargeable != null)
			edtClampingClampHalfChargeable.setText(Constants.BLANK);
		if(edtClampingClampHalfNonChargeable != null)
			edtClampingClampHalfNonChargeable.setText(Constants.BLANK);
		if(edtClampingClampOneChargeable != null)
			edtClampingClampOneChargeable.setText(Constants.BLANK);
		if(edtClampingClampOneNonChargeable != null)
			edtClampingClampOneNonChargeable.setText(Constants.BLANK);
		if(edtPlugTypeChargeable != null)
			edtPlugTypeChargeable.setText(Constants.BLANK);
		if(edtPlugTypeNonChargeable != null)
			edtPlugTypeNonChargeable.setText(Constants.BLANK);
		if(edtWoodScrewChargeable != null)
			edtWoodScrewChargeable.setText(Constants.BLANK);
		if(edtWoodScrewNonChargeable != null)
			edtWoodScrewNonChargeable.setText(Constants.BLANK);

		if(radBtnNo != null)
			radBtnNo.setChecked(false);
		if(radBtnYes != null)
			radBtnYes.setChecked(false);

		if(radBtnClampHalfByCompany != null)
			radBtnClampHalfByCompany.setChecked(false);
		if(radBtnClampHalfByVendor != null)
			radBtnClampHalfByVendor.setChecked(false);

		if(radBtnClampingClampOneByCompany != null)
			radBtnClampingClampOneByCompany.setChecked(false);

		if(radBtnClampingClampOneByVendor != null)
			radBtnClampingClampOneByVendor.setChecked(false);

		if(radBtnCheeseHeadeScrewByCompany != null)
			radBtnCheeseHeadeScrewByCompany.setChecked(false);
		if(radBtnCheeseHeadeScrewByVendor != null)
			radBtnCheeseHeadeScrewByVendor.setChecked(false);

		if(radBtnWoodScrewByCompany != null)
			radBtnWoodScrewByCompany.setChecked(false);
		if(radBtnWoodScrewByVendor != null)
			radBtnWoodScrewByVendor.setChecked(false);

		if(radBtnPlugTypeByCompany != null)
			radBtnPlugTypeByCompany.setChecked(false);

		if(radBtnPlugTypeByVendor != null)
			radBtnPlugTypeByVendor.setChecked(false);
	}
}