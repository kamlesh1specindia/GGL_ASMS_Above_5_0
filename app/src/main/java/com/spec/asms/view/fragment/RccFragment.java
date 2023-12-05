package com.spec.asms.view.fragment;

import java.util.ArrayList;
import java.util.Collections;

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
import com.spec.asms.vo.RccVO;

/**
 * A class is used to display RCC form.  It has methods to set values in form's fields.
 *
 */
public class RccFragment extends Fragment {

	private View rccView;

	private Button btnRccWorkSetisfectory;
	private Button btnNext;
	private Button btnPrev ;

	private int isRccWorkSetisfectory = -1;
	private int id;
	private int custStatusID;

	private EditText edtMsNail;
	private EditText edtMsNut;
	private EditText edtRccGuard;

	private EditText edtMsNailChargable;
	private EditText edtMsNutChargable;
	private EditText edtRccGuardChargable;

	private String rccoption;

	private TextView lblIsWorkSetisfectoy;
	private TextView lblSandFilling;
	private TextView lblMsNail;
	private TextView lblMsNut;
	private TextView lblRccGuard;
	private TextView errMsNail;
	private TextView errMsNut;
	private TextView errRccGuard;
	private TextView errTxtRccstifctry;
	private TextView lblRccHeader;

	public static StringBuffer errorMsgRcc = new StringBuffer();
	private FragmentManager fragmentManager;
	public UserMasterService masterService;
	private String user = Constants.LABEL_BLANK;

	private RadioGroup radGroupRccWorkSetisfactory;
	private RadioButton radBtnYes,radBtnNo;
	private LinearLayout llRcc,llRccDetail;

	private RadioGroup radGroupRccSandFilling;
	private RadioButton radBtnSandFillYes, radBtnSandFillNo;

	private RadioGroup radGroupRccMsNail;
	private RadioButton radRccMsNailByCompany, radRccMsNailByVendor;

	private RadioGroup radGroupRccMsNut;
	private RadioButton radRccMsNutByCompany, radRccMsNutByVendor;

	private RadioGroup radGroupRccGuard;
	private RadioButton radRccGuardByCompany, radRccGuardByVendor;

	private TextView txtVendorSupplied;
	private TextView txtCompanySupplied;
	private TextView txtNoChargableQty;
	private TextView txtChargableQty;
	private String contactNumber;
	private android.widget.CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new android.widget.CompoundButton.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			checkSuppliedStatus();
		}
	};
	/**
	 * A parameterized constructor
	 * @param customer Status id: the id for which the profile is to be created
	 * @return RCC Fragment object
	 */
	public static RccFragment newInstance(int custStatusId, String contactNumber) {
		RccFragment rccFragment = new RccFragment();
		Bundle args = new Bundle();
		args.putInt("statusid", custStatusId);
		args.putString("contactNumber", contactNumber);
		rccFragment.setArguments(args);
		return rccFragment;
	}

	public void checkSuppliedStatus() {
		if(radRccMsNailByCompany.isChecked() || radRccMsNailByVendor.isChecked()) {
			Log.e(getClass().getName(), "******************Change Listener called: Half Clamp");
			edtMsNail.setEnabled(true);
			edtMsNailChargable.setEnabled(true);
		} else {
			edtMsNail.setEnabled(false);
			edtMsNailChargable.setEnabled(false);
		}

		if(radRccMsNutByCompany.isChecked() || radRccMsNutByVendor.isChecked()) {
			edtMsNut.setEnabled(true);
			edtMsNutChargable.setEnabled(true);
		} else {
			edtMsNut.setEnabled(false);
			edtMsNutChargable.setEnabled(false);
		}

		if(radRccGuardByCompany.isChecked() || radRccGuardByVendor.isChecked()) {
			edtRccGuard.setEnabled(true);
			edtRccGuardChargable.setEnabled(true);
		} else {
			edtRccGuard.setEnabled(false);
			edtRccGuardChargable.setEnabled(false);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		rccView = inflater.inflate(R.layout.form_rcc,null);
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::RccFragmentStarted:::");
		fragmentManager = getFragmentManager();
		custStatusID = getArguments().getInt("statusid");
		contactNumber = getArguments().getString("contactNumber");
		masterService = new UserMasterService(getActivity());
		user = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.DB_USERNAME, "tech");
		final String[] dropdownOptions = getResources().getStringArray(R.array.arrayCheckBoolean);
		lblIsWorkSetisfectoy = (TextView)rccView.findViewById(R.id.lblRccRccWorkSetisfactory);
		lblSandFilling = (TextView) rccView.findViewById(R.id.lblSandFilling);
		lblMsNail = (TextView)rccView.findViewById(R.id.lblRccMsNail);
		lblMsNut = (TextView)rccView.findViewById(R.id.lblRssM_SNut);
		lblRccGuard = (TextView)rccView.findViewById(R.id.lblRccRccGuard);
		lblRccHeader = (TextView)rccView.findViewById(R.id.lblRccHeadr);
		btnRccWorkSetisfectory = (Button)rccView.findViewById(R.id.btnRccRccWorkSetisfactory);
		edtMsNail = (EditText)rccView.findViewById(R.id.edtRccMsNail);
		edtMsNut = (EditText)rccView.findViewById(R.id.edtRssMSNut);
		edtRccGuard = (EditText)rccView.findViewById(R.id.edtRccRccGuard);
		// -Start- Added By Pankit Mistry
		edtMsNailChargable = (EditText) rccView.findViewById(R.id.edtRccMsNailChargeable);
		edtMsNutChargable = (EditText) rccView.findViewById(R.id.edtRccMsNutChargeable);
		edtRccGuardChargable = (EditText) rccView.findViewById(R.id.edtRccGuardChargeable);
		// -End-
		btnNext = (Button)rccView.findViewById(R.id.btnRccNext);
		btnPrev = (Button)rccView.findViewById(R.id.btnRccPrev);
		errMsNail = (TextView)rccView.findViewById(R.id.errRccMsNail);
		errMsNut = (TextView)rccView.findViewById(R.id.errRssMSNut);
		errRccGuard = (TextView)rccView.findViewById(R.id.errRccRccGuard);
		errTxtRccstifctry = (TextView) rccView.findViewById(R.id.errRccWorkSetisfactory);
		//
		radGroupRccWorkSetisfactory = (RadioGroup) rccView.findViewById(R.id.radGroupRccWorkSetisfactory);
		radBtnNo = (RadioButton) rccView.findViewById(R.id.radBtnNo);
		radBtnYes = (RadioButton) rccView.findViewById(R.id.radBtnYes);
		// -Start- Added By Pankit Mistry
		radGroupRccSandFilling = (RadioGroup) rccView.findViewById(R.id.radGroupSandFilling);
		radBtnSandFillYes = (RadioButton) rccView.findViewById(R.id.radBtnSandFillingYes);
		radBtnSandFillNo = (RadioButton) rccView.findViewById(R.id.radBtnSandFillingNo);

		radGroupRccMsNail = (RadioGroup) rccView.findViewById(R.id.radGroupRccMsNail);
		radRccMsNailByCompany = (RadioButton) rccView.findViewById(R.id.stubeRccMsNailByCompany);
		radRccMsNailByVendor = (RadioButton) rccView.findViewById(R.id.stubeRccMsNailByVendor);

		radGroupRccMsNut = (RadioGroup) rccView.findViewById(R.id.radGroupRccMsNut);
		radRccMsNutByCompany = (RadioButton) rccView.findViewById(R.id.stubeRccMsNutByCompany);
		radRccMsNutByVendor = (RadioButton) rccView.findViewById(R.id.stubeRccMsNutByVendor);

		radGroupRccGuard = (RadioGroup) rccView.findViewById(R.id.radGroupRccGuard);
		radRccGuardByCompany = (RadioButton) rccView.findViewById(R.id.stubeRccGuardByCompany);
		radRccGuardByVendor = (RadioButton) rccView.findViewById(R.id.stubeRccGuardByVendor);
		// -End-

		txtVendorSupplied = (TextView) rccView.findViewById(R.id.txtVendorSupplied);
		txtCompanySupplied = (TextView) rccView.findViewById(R.id.txtCompanySupplied);
		txtNoChargableQty = (TextView) rccView.findViewById(R.id.txtNoChargableQty);
		txtChargableQty = (TextView) rccView.findViewById(R.id.txtChargableQty);

		radRccGuardByCompany.setOnCheckedChangeListener(onCheckedChangeListener);
		radRccGuardByVendor.setOnCheckedChangeListener(onCheckedChangeListener);
		radRccMsNailByCompany.setOnCheckedChangeListener(onCheckedChangeListener);
		radRccMsNailByVendor.setOnCheckedChangeListener(onCheckedChangeListener);
		radRccMsNutByCompany.setOnCheckedChangeListener(onCheckedChangeListener);
		radRccMsNutByVendor.setOnCheckedChangeListener(onCheckedChangeListener);
		llRcc = (LinearLayout) rccView.findViewById(R.id.llRcc);
		llRccDetail = (LinearLayout) rccView.findViewById(R.id.llRccDetail);

		changeLanguage(id);

		if((custStatusID != masterService.getStatusId("OP") && custStatusID != masterService.getStatusId("HCL1")) || user.equals("admin")){
			rccDisableViews();
		}

		if(Global.isCycleRunning)
			MaintanenceForm.btnHeaderProcess.setVisibility(View.VISIBLE);
		else
			MaintanenceForm.btnHeaderProcess.setVisibility(View.INVISIBLE);

		btnNext.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Utility.changeSelectedForm(4, 3);
				Utility.changeList(4,false);
				KitchenSurakshaTubeFragment surakshaTubeFragment = KitchenSurakshaTubeFragment.newInstance(custStatusID, contactNumber);
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.frmLayoutFragment, surakshaTubeFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		});


		btnPrev.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Utility.changeSelectedForm(2, 3);
				Utility.changeList(2,false);
				PaintingFragment paintingFragment = PaintingFragment.newInstance(custStatusID, contactNumber);
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.frmLayoutFragment, paintingFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		});

		radGroupRccWorkSetisfactory.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {

					case R.id.radBtnYes:
						btnRccWorkSetisfectory.setText(radBtnYes.getText().toString());
						RccVO.setIsWorking(radBtnYes.getText().toString());

						edtMsNail.setEnabled(false);
						edtMsNut.setEnabled(false);
						edtRccGuard.setEnabled(false);
						// -Start- Added By Pankit Mistry
						edtMsNailChargable.setEnabled(false);
						edtMsNutChargable.setEnabled(false);
						edtRccGuardChargable.setEnabled(false);

						radRccMsNailByCompany.setEnabled(false);
						radRccMsNailByVendor.setEnabled(false);
						radRccMsNutByCompany.setEnabled(false);
						radRccMsNutByVendor.setEnabled(false);
						radRccGuardByCompany.setEnabled(false);
						radRccGuardByVendor.setEnabled(false);
						// -End-
						edtRccGuard.setText(Constants.LABEL_BLANK);
						edtMsNail.setText(Constants.LABEL_BLANK);
						edtMsNut.setText(Constants.LABEL_BLANK);
						// -Start- Added By Pankit Mistry
						edtRccGuardChargable.setText(Constants.LABEL_BLANK);
						edtMsNailChargable.setText(Constants.LABEL_BLANK);
						edtMsNutChargable.setText(Constants.LABEL_BLANK);

						radRccMsNailByCompany.setChecked(false);
						radRccMsNailByVendor.setChecked(false);
						radRccMsNutByCompany.setChecked(false);
						radRccMsNutByVendor.setChecked(false);
						radRccGuardByCompany.setChecked(false);
						radRccGuardByVendor.setChecked(false);
						// -End-

						llRcc.setVisibility(View.GONE);
						llRccDetail.setVisibility(View.GONE);
						break;

					case R.id.radBtnNo:
						btnRccWorkSetisfectory.setText(radBtnNo.getText().toString());
						RccVO.setIsWorking(radBtnNo.getText().toString());

						edtMsNail.setEnabled(true);
						edtMsNut.setEnabled(true);
						edtRccGuard.setEnabled(true);
						// -Start- Added By Pankit Mistry
						edtMsNailChargable.setEnabled(true);
						edtMsNutChargable.setEnabled(true);
						edtRccGuardChargable.setEnabled(true);

						radRccMsNailByCompany.setEnabled(true);
						radRccMsNailByVendor.setEnabled(true);
						radRccMsNutByCompany.setEnabled(true);
						radRccMsNutByVendor.setEnabled(true);
						radRccGuardByCompany.setEnabled(true);
						radRccGuardByVendor.setEnabled(true);
						// -End-
						llRcc.setVisibility(View.VISIBLE);
						llRccDetail.setVisibility(View.VISIBLE);
						break;
				}

				validateButton();
				checkSuppliedStatus();

			}
		});

		radGroupRccSandFilling.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId) {
					case R.id.radBtnSandFillingYes:
						break;
					case R.id.radBtnSandFillingNo:
						break;
				}

			}
		});
		initializeValuesRcc();
		btnRccWorkSetisfectory.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				try{
					new AlertDialog.Builder(getActivity()).setSingleChoiceItems(dropdownOptions,isRccWorkSetisfectory, new DialogInterface.OnClickListener(){

						public void onClick(DialogInterface dialog, int whichButton){

							btnRccWorkSetisfectory.setText(dropdownOptions[whichButton]);
							isRccWorkSetisfectory = whichButton;

							if(dropdownOptions[whichButton].equals("NO")){
								llRcc.setVisibility(View.VISIBLE);
								llRccDetail.setVisibility(View.VISIBLE);
								edtMsNail.setEnabled(true);
								edtMsNut.setEnabled(true);
								edtRccGuard.setEnabled(true);
								// -Start- Added By Pankit Mistry
								edtMsNailChargable.setEnabled(true);
								edtMsNutChargable.setEnabled(true);
								edtRccGuardChargable.setEnabled(true);

								radRccMsNailByCompany.setEnabled(true);
								radRccMsNailByVendor.setEnabled(true);
								radRccMsNutByCompany.setEnabled(true);
								radRccMsNutByVendor.setEnabled(true);
								radRccGuardByCompany.setEnabled(true);
								radRccGuardByVendor.setEnabled(true);
								// -End-
							}else{

								llRcc.setVisibility(View.GONE);
								llRccDetail.setVisibility(View.GONE);
								edtMsNail.setEnabled(false);
								edtMsNut.setEnabled(false);
								edtRccGuard.setEnabled(false);
								// -Start- Added By Pankit Mistry
								edtMsNailChargable.setEnabled(false);
								edtMsNutChargable.setEnabled(false);
								edtRccGuardChargable.setEnabled(false);

								radRccMsNailByCompany.setEnabled(false);
								radRccMsNailByVendor.setEnabled(false);
								radRccMsNutByCompany.setEnabled(false);
								radRccMsNutByVendor.setEnabled(false);
								radRccGuardByCompany.setEnabled(false);
								radRccGuardByVendor.setEnabled(false);
								// -End-
								edtRccGuard.setText(Constants.LABEL_BLANK);
								edtMsNail.setText(Constants.LABEL_BLANK);
								edtMsNut.setText(Constants.LABEL_BLANK);
								// -Start- Added By Pankit Mistry
								edtRccGuardChargable.setText(Constants.LABEL_BLANK);
								edtMsNailChargable.setText(Constants.LABEL_BLANK);
								edtMsNutChargable.setText(Constants.LABEL_BLANK);

								radRccMsNailByCompany.setChecked(false);
								radRccMsNailByVendor.setChecked(false);
								radRccMsNutByCompany.setChecked(false);
								radRccMsNutByVendor.setChecked(false);
								radRccGuardByCompany.setChecked(false);
								radRccGuardByVendor.setChecked(false);
								// -End-

							}

							RccVO.setIsWorking(dropdownOptions[whichButton]);
							validateButton();
							dialog.dismiss();
						}
					})
							.show();

				}catch (Exception e){
					e.printStackTrace();
				}
			}
		});
		checkSuppliedStatus();
		return rccView;
	}

	public void clearValues() {
		if(radBtnNo != null)
			radBtnNo.setChecked(false);
		if(radBtnYes != null)
			radBtnYes.setChecked(false);

		if(radBtnSandFillNo != null)
			radBtnSandFillNo.setChecked(false);
		if(radBtnSandFillYes != null)
			radBtnSandFillYes.setChecked(false);

		if(radRccGuardByCompany != null)
			radRccGuardByCompany.setChecked(false);
		if(radRccGuardByVendor != null)
			radRccGuardByVendor.setChecked(false);

		if(radRccMsNailByCompany != null)
			radRccMsNailByCompany.setChecked(false);
		if(radRccMsNailByVendor != null)
			radRccMsNailByVendor.setChecked(false);

		if(radRccMsNutByCompany != null)
			radRccMsNutByCompany.setChecked(false);

		if(radRccMsNutByVendor != null)
			radRccMsNutByVendor.setChecked(false);

		if(edtMsNail != null)
			edtMsNail.setText(Constants.BLANK);

		if(edtMsNailChargable != null)
			edtMsNailChargable.setText(Constants.BLANK);

		if(edtMsNut != null)
			edtMsNut.setText(Constants.BLANK);
		if(edtMsNutChargable != null)
			edtMsNutChargable.setText(Constants.BLANK);

		if(edtRccGuard != null)
			edtRccGuard.setText(Constants.BLANK);
		if(edtRccGuardChargable != null)
			edtRccGuardChargable.setText(Constants.BLANK);
	}
	/**
	 * Validation method to validate control. If any error ,it will display on error text field.
	 * @return void.
	 */
	public void validateButton(){
		ArrayList<Boolean> errorList = new ArrayList<Boolean>();

		try {
			if(radBtnSandFillNo.isChecked())
				RccVO.setIsSandFilling("NO");
			else if(radBtnSandFillYes.isChecked())
				RccVO.setIsSandFilling("YES");
			else
				RccVO.setIsSandFilling(Constants.LABEL_BLANK);
//		errMsNail.setText(Utility.rcc(Constants.MS_NAIL,edtMsNail.getText().toString().trim(),getActivity()));
			RccVO.setMsNail(edtMsNail.getText().toString());
//		if(errMsNail.getText().toString().equals(Constants.LABEL_BLANK))
//			errorList.add(false);
//		else
//			errorList.add(true);
//		
//		errMsNut.setText(Utility.rcc(Constants.MS_NUT,edtMsNut.getText().toString(),getActivity()));
			RccVO.setMsNut(edtMsNut.getText().toString());
//		if(errMsNail.getText().toString().equals(Constants.LABEL_BLANK))
//			errorList.add(false);
//		else
//			errorList.add(true);
//		
//		errRccGuard.setText(Utility.rcc(Constants.RCC_GUARD,edtRccGuard.getText().toString().trim(),getActivity()));
			RccVO.setRccGuard(edtRccGuard.getText().toString());
//		if(errRccGuard.getText().toString().equals(Constants.LABEL_BLANK))
//			errorList.add(false);
//		else
//			errorList.add(true);

			// -Start- Added By Pankit Mistry
			RccVO.setMsNailChargable(edtMsNailChargable.getText().toString());
			RccVO.setMsNutChargable(edtMsNutChargable.getText().toString());
			RccVO.setRccGuardChargable(edtRccGuardChargable.getText().toString());

			if(radRccGuardByCompany.isChecked())
				RccVO.setRccGuardSuppliedBy(Constants.SUPPLY_COMPANY);
			else if(radRccGuardByVendor.isChecked())
				RccVO.setRccGuardSuppliedBy(Constants.SUPPLY_VENDOR);
			else
				RccVO.setRccGuardSuppliedBy(Constants.LABEL_BLANK);

			if(radRccMsNailByCompany.isChecked())
				RccVO.setMsNailSuppliedBy(Constants.SUPPLY_COMPANY);
			else if(radRccMsNailByVendor.isChecked())
				RccVO.setMsNailSuppliedBy(Constants.SUPPLY_VENDOR);
			else
				RccVO.setMsNailSuppliedBy(Constants.LABEL_BLANK);

			if(radRccMsNutByCompany.isChecked())
				RccVO.setMsNutSuppliedBy(Constants.SUPPLY_COMPANY);
			else if(radRccMsNutByVendor.isChecked())
				RccVO.setMsNutSuppliedBy(Constants.SUPPLY_VENDOR);
			else
				RccVO.setMsNutSuppliedBy(Constants.LABEL_BLANK);


			// -End-
			if(btnRccWorkSetisfectory.getText().toString().equals(getResources().getString(R.string.title_select_option))){
				errorList.add(true);
				errTxtRccstifctry.setText(Constants.ERROR_MARK);
			}else{
				if(btnRccWorkSetisfectory.getText().toString().equalsIgnoreCase("yes")) {
					errorList.add(false);
					errTxtRccstifctry.setText(Constants.ERROR_MARK);
				} else {

					if(radRccMsNailByCompany.isChecked() || radRccMsNailByVendor.isChecked()) {
						if(edtMsNail.getText().toString().equals(Constants.BLANK) && edtMsNailChargable.getText().toString().equals(Constants.BLANK)) {
							errorList.add(true);
						} else {
							errorList.add(false);
						}
					} else {
						errorList.add(false);
					}
					if(radRccMsNutByCompany.isChecked() || radRccMsNutByVendor.isChecked()) {
						if(edtMsNut.getText().toString().equals(Constants.BLANK) && edtMsNutChargable.getText().toString().equals(Constants.BLANK)) {
							errorList.add(true);
						} else {
							errorList.add(false);
						}
					} else {
						errorList.add(false);
					}
					if(radRccGuardByCompany.isChecked() || radRccGuardByVendor.isChecked()) {
						if(edtRccGuard.getText().toString().equals(Constants.BLANK) && edtRccGuardChargable.getText().toString().equals(Constants.BLANK)) {
							errorList.add(true);
						} else {
							errorList.add(false);
						}
					} else {
						errorList.add(false);
					}
				/*if(edtMsNail.getText().toString().equals(Constants.BLANK) ||
						edtMsNailChargable.getText().toString().equals(Constants.BLANK) ||
						edtMsNut.getText().toString().equals(Constants.BLANK) || 
						edtMsNutChargable.getText().toString().equals(Constants.BLANK)) {
					errorList.add(true);
					errTxtRccstifctry.setText(Constants.ERROR_MARK);
				} else {
					errorList.add(false);
					errTxtRccstifctry.setText(Constants.ERROR_MARK);
				}*/
				}

//			errTxtRccstifctry.setText(Constants.LABEL_BLANK);
			}



			Collections.sort(errorList);
			System.err.println(" Correction Error --- "+ Collections.binarySearch(errorList,true));
			if(Collections.binarySearch(errorList,true) >= 0)
				Utility.changeList(3,true);
			else
				Utility.changeList(3,false);
		} catch(IllegalStateException e) {

		}
	}

	/**
	 * Method to disable all controls when open form in edit mode.
	 * @return void
	 */

	public void rccDisableViews(){
		btnRccWorkSetisfectory.setEnabled(false);
		edtMsNail.setEnabled(false);
		edtMsNut.setEnabled(false);
		edtRccGuard.setEnabled(false);
		radBtnNo.setEnabled(false);
		radBtnYes.setEnabled(false);
		llRcc.setVisibility(View.GONE);
		llRccDetail.setVisibility(View.GONE);
		// -Start- Added By Pankit Mistry
		radBtnSandFillNo.setEnabled(false);
		radBtnSandFillYes.setEnabled(false);
		edtMsNailChargable.setEnabled(false);
		edtMsNutChargable.setEnabled(false);
		edtRccGuardChargable.setEnabled(false);

		radRccMsNailByCompany.setEnabled(false);
		radRccMsNailByVendor.setEnabled(false);
		radRccMsNutByCompany.setEnabled(false);
		radRccMsNutByVendor.setEnabled(false);
		radRccGuardByCompany.setEnabled(false);
		radRccGuardByVendor.setEnabled(false);
		// -End-
	}
	/**
	 * Method used to set values for field and controls.
	 * @return void
	 *
	 */

	private void initializeValuesRcc(){

		errMsNail.setText(Utility.rcc(Constants.MS_NAIL,edtMsNail.getText().toString().trim(),getActivity()));
		errMsNut.setText(Utility.rcc(Constants.MS_NUT,edtMsNut.getText().toString(),getActivity()));
		errRccGuard.setText(Utility.rcc(Constants.RCC_GUARD,edtRccGuard.getText().toString().trim(),getActivity()));

		rccoption = RccVO.getIsWorking();
		// -Start- Added By Pankit Mistry
		String isSandFilling = RccVO.getIsSandFilling();
		if(isSandFilling.equals(Constants.LABEL_BLANK)) {
			radBtnSandFillNo.setChecked(false);
			radBtnSandFillYes.setChecked(false);
		} else {
			if("YES".equals(RccVO.getIsSandFilling())) {
				radBtnSandFillNo.setChecked(false);
				radBtnSandFillYes.setChecked(true);
			} else if("NO".equals(RccVO.getIsSandFilling())) {
				radBtnSandFillNo.setChecked(true);
				radBtnSandFillYes.setChecked(false);
			}
		}
		// -End-
		edtMsNail.setText(RccVO.getMsNail());
		edtMsNut.setText(RccVO.getMsNut());
		edtRccGuard.setText(RccVO.getRccGuard());

		// -Start- Added By Pankit Mistry
		edtMsNailChargable.setText(RccVO.getMsNailChargable());
		edtMsNutChargable.setText(RccVO.getMsNutChargable());
		edtRccGuardChargable.setText(RccVO.getRccGuardChargable());

		if(RccVO.getMsNailSuppliedBy().equals(Constants.SUPPLY_COMPANY)) {
			radRccMsNailByCompany.setChecked(true);
			radRccMsNailByVendor.setChecked(false);
		}else if(RccVO.getMsNailSuppliedBy().equals(Constants.SUPPLY_VENDOR)) {
			radRccMsNailByVendor.setChecked(true);
			radRccMsNailByCompany.setChecked(false);
		} else {
			radRccMsNailByVendor.setChecked(false);
			radRccMsNailByCompany.setChecked(false);
		}

		if(RccVO.getMsNutSuppliedBy().equals(Constants.SUPPLY_COMPANY)) {
			radRccMsNutByCompany.setChecked(true);
			radRccMsNutByVendor.setChecked(false);
		}else if(RccVO.getMsNutSuppliedBy().equals(Constants.SUPPLY_VENDOR)) {
			radRccMsNutByCompany.setChecked(false);
			radRccMsNutByVendor.setChecked(true);
		} else {
			radRccMsNutByCompany.setChecked(false);
			radRccMsNutByVendor.setChecked(false);
		}

		if(RccVO.getRccGuardSuppliedBy().equals(Constants.SUPPLY_COMPANY)) {
			radRccGuardByCompany.setChecked(true);
			radRccGuardByVendor.setChecked(false);
		}else if(RccVO.getMsNailSuppliedBy().equals(Constants.SUPPLY_VENDOR)) {
			radRccGuardByCompany.setChecked(false);
			radRccGuardByVendor.setChecked(true);
		} else {
			radRccGuardByCompany.setChecked(false);
			radRccGuardByVendor.setChecked(false);
		}
		// -End-
		if(rccoption.equals(Constants.LABEL_BLANK)){
			rccoption = getResources().getString(R.string.title_select_option);
			btnRccWorkSetisfectory.setText(rccoption);
			radBtnNo.setChecked(false);
			radBtnYes.setChecked(false);
		}else{
			btnRccWorkSetisfectory.setText(RccVO.getIsWorking());
			if("YES".equals(RccVO.getIsWorking()))
			{
				isRccWorkSetisfectory = 0;
				radBtnNo.setChecked(false);
				radBtnYes.setChecked(true);
				llRcc.setVisibility(View.GONE);
				llRccDetail.setVisibility(View.GONE);

			}
			else if("NO".equals(RccVO.getIsWorking()))
			{
				isRccWorkSetisfectory = 1;
				radBtnNo.setChecked(true);
				radBtnYes.setChecked(false);
				llRcc.setVisibility(View.VISIBLE);
				llRccDetail.setVisibility(View.VISIBLE);
			}
		}

		if(rccoption.equals("NO")){

			if(custStatusID != masterService.getStatusId("OP")){
				edtMsNail.setEnabled(false);
				edtMsNut.setEnabled(false);
				edtRccGuard.setEnabled(false);
				// -Start- Added By Pankit Mistry
				edtMsNailChargable.setEnabled(false);
				edtMsNutChargable.setEnabled(false);
				edtRccGuardChargable.setEnabled(false);

				radRccMsNailByCompany.setEnabled(false);
				radRccMsNailByVendor.setEnabled(false);
				radRccGuardByCompany.setEnabled(false);
				radRccGuardByVendor.setEnabled(false);
				radRccMsNutByCompany.setEnabled(false);
				radRccMsNutByVendor.setEnabled(false);
				// -End-
			}else{
				edtMsNail.setEnabled(true);
				edtMsNut.setEnabled(true);
				edtRccGuard.setEnabled(true);
				// -Start- Added By Pankit Mistry
				edtMsNailChargable.setEnabled(true);
				edtMsNutChargable.setEnabled(true);
				edtRccGuardChargable.setEnabled(true);

				radRccMsNailByCompany.setEnabled(true);
				radRccMsNailByVendor.setEnabled(true);
				radRccGuardByCompany.setEnabled(true);
				radRccGuardByVendor.setEnabled(true);
				radRccMsNutByCompany.setEnabled(true);
				radRccMsNutByVendor.setEnabled(true);
				// -End-
			}
		}else if(rccoption.equals("YES")){

			edtMsNail.setEnabled(false);
			edtMsNut.setEnabled(false);
			edtRccGuard.setEnabled(false);
			// -Start- Added By Pankit Mistry
			edtMsNailChargable.setEnabled(false);
			edtMsNutChargable.setEnabled(false);
			edtRccGuardChargable.setEnabled(false);

			radRccMsNailByCompany.setEnabled(false);
			radRccMsNailByVendor.setEnabled(false);
			radRccGuardByCompany.setEnabled(false);
			radRccGuardByVendor.setEnabled(false);
			radRccMsNutByCompany.setEnabled(false);
			radRccMsNutByVendor.setEnabled(false);
			// -End-
		}




		validateButton();
		checkSuppliedStatus();
	}
	/**
	 * Method used to change label values as per parameter to English and Gujarati.
	 * @param id : 1 - English , 2 - Gujarati
	 */
	public void changeLanguage(int id){

		id = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.PREF_LANGUAGE,Constants.LANGUAGE_ENGLISH);

		switch (id){
			case Constants.LANGUAGE_ENGLISH:
				lblIsWorkSetisfectoy.setText(getResources().getText(R.string.title_rcc_R_c_c_work_Setisfectory_Eng));
				lblSandFilling.setText(getResources().getText(R.string.title_rcc_sand_filling_Eng));
				lblMsNail.setText(getResources().getText(R.string.title_rcc_Rcc_Ms_Nail_Eng));
				lblMsNut.setText(getResources().getText(R.string.title_rcc_M_S_Nut_Eng));
				lblRccGuard.setText(getResources().getText(R.string.title_rcc_RccGuard_Eng));
				lblRccHeader.setText(getResources().getText(R.string.header_rcc_Eng));
				txtVendorSupplied.setText(getResources().getText(R.string.lbl_by_supply_vendor_Eng));
				txtCompanySupplied.setText(getResources().getText(R.string.lbl_by_supply_company_Eng));
				txtNoChargableQty.setText(getResources().getText(R.string.lbl_non_chargable_qty_Eng));
				txtChargableQty.setText(getResources().getText(R.string.lbl_chargable_qty_Eng));
				break;

			case Constants.LANGUAGE_GUJRATI:
				lblIsWorkSetisfectoy.setTypeface(Global.typeface_Guj);
				lblMsNail.setTypeface(Global.typeface_Guj);
				lblMsNut.setTypeface(Global.typeface_Guj);
				lblRccGuard.setTypeface(Global.typeface_Guj);
				lblRccHeader.setTypeface(Global.typeface_Guj);
				lblSandFilling.setTypeface(Global.typeface_Guj);
				txtVendorSupplied.setTypeface(Global.typeface_Guj);
				txtCompanySupplied.setTypeface(Global.typeface_Guj);
				txtNoChargableQty.setTypeface(Global.typeface_Guj);
				txtChargableQty.setTypeface(Global.typeface_Guj);
				lblIsWorkSetisfectoy.setText(getResources().getText(R.string.title_rcc_R_c_c_work_Setisfectory_Guj));
				lblSandFilling.setText(getResources().getText(R.string.title_rcc_sand_filling_Guj));
				lblMsNail.setText(getResources().getText(R.string.title_rcc_Rcc_Ms_Nail_Guj));
				lblMsNut.setText(getResources().getText(R.string.title_rcc_M_S_Nut_Guj));
				lblRccGuard.setText(getResources().getText(R.string.title_rcc_RccGuard_Guj));
				lblRccHeader.setText(getResources().getText(R.string.header_rcc_Guj));
				txtVendorSupplied.setText(getResources().getText(R.string.lbl_by_supply_vendor_Guj));
				txtCompanySupplied.setText(getResources().getText(R.string.lbl_by_supply_company_Guj));
				txtNoChargableQty.setText(getResources().getText(R.string.lbl_non_chargable_qty_Guj));
				txtChargableQty.setText(getResources().getText(R.string.lbl_chargable_qty_Guj));

				break;

			default:
				break;
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		validateButton();
	}
}