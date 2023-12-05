package com.spec.asms.view.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Global;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.vo.OtherKitchenSurakshaTubeVO;

public class OtherSurakshaTubeFragment extends Fragment {

	private TextView lblsurakshaTubeReplaced;
	private TextView lblSurakshaTube75mm1Meter;
	private TextView lblSurakshaTube79mm15Meter;
	private TextView lblSurakshaTube125mm15Meter;
	private TextView lblSurakshaTubeClampHose8mmPipe;
	private TextView lblSurakshaTubeClampHose20mmpipe;

	private EditText edtSurakshaTube75mm1Meter;
	private EditText edtSurakshaTube75mm1MeterChargeable;
	private EditText edtSurakshaTube79mm15Meter;
	private EditText edtSurakshaTube79mm15MeterChargeable;
	private EditText edtSurakshaTube125mm15Meter;
	private EditText edtSurakshaTube125mm15MeterChargeable;
	private EditText edtSurakshaTubeClampHose8mmPipe;
	private EditText edtSurakshaTubeClampHose8mmPipeChargeable;
	private EditText edtSurakshaTubeClampHose20mmPipe;
	private EditText edtSurakshaTubeClampHose20mmPipeChargeable;

	private RadioGroup radGroupSurakshaTube75mm1Meter;
	private RadioGroup radGroupSurakshaTube79mm15Meter;
	private RadioGroup radGroupSurakshaTube125mm15Meter;
	private RadioGroup radGroupSurakshaTubeClampHose8mmPipe;
	private RadioGroup radGroupSurakshaTube20mmReplaced;

	private RadioButton radStube75ByCompany;
	private RadioButton radStube75ByVendor;
	private RadioButton radStube7915ByCompany;
	private RadioButton radStube7915ByVendor;
	private RadioButton radSTube12515ByCompany;
	private RadioButton radSTube12515ByVendor;
	private RadioButton radHose8mmPipeByCompany;
	private RadioButton radHose8mmPipeByVendor;
	private RadioButton radHose20mmPipeByCompany;
	private RadioButton radHose20mmPipeByVendor;

	private View srkshaTbView;
	private Button btnSaveandNext;
	private Button btnPrev;
	private Button btnIsReplaced;
	/*private EditText edtSize1;
	private EditText edtSize2;	
	private EditText edtSize3;
	private EditText edtSize4;
	private EditText edtSize5;*/

	private TextView txtVendorSupplied;
	private TextView txtCompanySupplied;
	private TextView txtNoChargableQty;
	private TextView txtChargableQty;

	private TextView txtVendorSupplied2;
	private TextView txtCompanySupplied2;
	private TextView txtNoChargableQty2;
	private TextView txtChargableQty2;

	private TextView errSurakshaTube75mm1Meter;
	private TextView errSurakshaTube79mm15Meter;
	private TextView errSurakshaTube125mm15Meter;
	private TextView errSurakshaTubeClampHose8mmPipe;
	private TextView errSurakshaTubeClampHose20mmPipe;

	private TextView errTxtReplaced;
/*	private TextView errSize1;
	private TextView errSize2;
	private TextView errSize3;
	private TextView errSize4;
	private TextView errSize5;*/

	private TextView lblSurakshaHeader;
	private int isSurakshaTubeReplaced=-1;
	private int id;

	private int custStatusId;
	private String surakshaTubeoption;
	private String surakshaTube75mm1MeterOption;
	private String surakshaTube79mm15MeterOption;
	private String surakshaTube125mm15MeterOption;
	private String surakshaTubeClampHose8mmPipeOption;
	private String surakshaTubeClampHose20mmPipeOption;
	private SurakshaTubeDateDialogFragment dateFragment;
	private Calendar cal;

	@SuppressWarnings("unused")
	private String isReplaced;
	public static StringBuffer errorMsgSuraksha = new StringBuffer();
	private FragmentManager fragmentManager;
	private UserMasterService userMasterService;
	private String user = Constants.LABEL_BLANK;

	private RadioGroup radGroupSurakshaTubeReplaced;
	private RadioButton radBtnYes,radBtnNo;
	private LinearLayout llSurkshaTube,llSurkshaTubeDetail;
	private RelativeLayout rltHeader2;
	private LinearLayout lnHeader2;
	private String contactNumber;
	/**
	 * A parameterized constructor
	 * @param customer Status id: the id for which the profile is to be created
	 * @return SurkshTube Fragment object
	 */
	public static OtherSurakshaTubeFragment newInstance(int custStatusId, String contactNumber){
		OtherSurakshaTubeFragment otherSurakshaTubeFragment = new OtherSurakshaTubeFragment();
		Bundle args = new Bundle();
		args.putInt("statusid", custStatusId);
		args.putString("contactNumber", contactNumber);
		otherSurakshaTubeFragment.setArguments(args);
		return otherSurakshaTubeFragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		srkshaTbView = inflater.inflate(R.layout.form_surakshatube,null);
		fragmentManager = getFragmentManager();
		custStatusId = getArguments().getInt("statusid");
		contactNumber = getArguments().getString("contactNumber");
		userMasterService = new UserMasterService(getActivity());


		lblsurakshaTubeReplaced = (TextView) srkshaTbView.findViewById(R.id.lblsurakshaTubeReplaced);
		lblSurakshaTube75mm1Meter = (TextView) srkshaTbView.findViewById(R.id.lblSurakshaTube75mm1Meter);
		lblSurakshaTube79mm15Meter = (TextView) srkshaTbView.findViewById(R.id.lblSurakshaTube79mm15Meter);
		lblSurakshaTube125mm15Meter = (TextView) srkshaTbView.findViewById(R.id.lblSurakshaTube125mm15Meter);
		lblSurakshaTubeClampHose8mmPipe = (TextView) srkshaTbView.findViewById(R.id.lblSurakshaTubeClampHose8mmPipe);
		lblSurakshaTubeClampHose20mmpipe = (TextView) srkshaTbView.findViewById(R.id.lblSurakshaTubeClampHose20mmpipe);

		edtSurakshaTube75mm1Meter = (EditText) srkshaTbView.findViewById(R.id.edtSurakshaTube75mm1Meter);
		edtSurakshaTube75mm1MeterChargeable = (EditText) srkshaTbView.findViewById(R.id.edtSurakshaTube75mm1MeterChargeable);
		edtSurakshaTube79mm15Meter = (EditText) srkshaTbView.findViewById(R.id.edtSurakshaTube79mm15Meter);
		edtSurakshaTube79mm15MeterChargeable = (EditText) srkshaTbView.findViewById(R.id.edtSurakshaTube79mm15MeterChargeable);
		edtSurakshaTube125mm15Meter = (EditText) srkshaTbView.findViewById(R.id.edtSurakshaTube125mm15Meter);
		edtSurakshaTube125mm15MeterChargeable = (EditText) srkshaTbView.findViewById(R.id.edtSurakshaTube125mm15MeterChargeable);
		edtSurakshaTubeClampHose8mmPipe = (EditText) srkshaTbView.findViewById(R.id.edtSurakshaTubeClampHose8mmPipe);
		edtSurakshaTubeClampHose8mmPipeChargeable = (EditText) srkshaTbView.findViewById(R.id.edtSurakshaTubeClampHose8mmPipeChargeable);
		edtSurakshaTubeClampHose20mmPipe = (EditText) srkshaTbView.findViewById(R.id.edtSurakshaTubeClampHose20mmPipe);
		edtSurakshaTubeClampHose20mmPipeChargeable = (EditText) srkshaTbView.findViewById(R.id.edtSurakshaTubeClampHose20mmPipeChargeable);

		radGroupSurakshaTube75mm1Meter = (RadioGroup) srkshaTbView.findViewById(R.id.radGroupSurakshaTube75mm1Meter);
		radGroupSurakshaTube79mm15Meter = (RadioGroup) srkshaTbView.findViewById(R.id.radGroupSurakshaTube79mm15Meter);
		radGroupSurakshaTube125mm15Meter = (RadioGroup) srkshaTbView.findViewById(R.id.radGroupSurakshaTube125mm15Meter);
		radGroupSurakshaTubeClampHose8mmPipe = (RadioGroup) srkshaTbView.findViewById(R.id.radGroupSurakshaTubeClampHose8mmPipe);
		radGroupSurakshaTube20mmReplaced = (RadioGroup) srkshaTbView.findViewById(R.id.radGroupSurakshaTube20mmReplaced);

		radStube75ByCompany = (RadioButton) srkshaTbView.findViewById(R.id.radStube75ByCompany);
		radStube75ByVendor = (RadioButton) srkshaTbView.findViewById(R.id.radStube75ByVendor);
		radStube7915ByCompany = (RadioButton) srkshaTbView.findViewById(R.id.radStube7915ByCompany);
		radStube7915ByVendor = (RadioButton) srkshaTbView.findViewById(R.id.radStube7915ByVendor);
		radSTube12515ByCompany = (RadioButton) srkshaTbView.findViewById(R.id.radSTube12515ByCompany);
		radSTube12515ByVendor = (RadioButton) srkshaTbView.findViewById(R.id.radSTube12515ByVendor);
		radHose8mmPipeByCompany = (RadioButton) srkshaTbView.findViewById(R.id.radHose8mmPipeByCompany);
		radHose8mmPipeByVendor = (RadioButton) srkshaTbView.findViewById(R.id.radHose8mmPipeByVendor);
		radHose20mmPipeByCompany = (RadioButton) srkshaTbView.findViewById(R.id.radHose20mmPipeByCompany);
		radHose20mmPipeByVendor = (RadioButton) srkshaTbView.findViewById(R.id.radHose20mmPipeByVendor);

		errTxtReplaced = (TextView)srkshaTbView.findViewById(R.id.errSurakshaTubeReplaced);
		errSurakshaTube75mm1Meter = (TextView) srkshaTbView.findViewById(R.id.errSurakshaTube75mm1Meter);
		errSurakshaTube79mm15Meter = (TextView) srkshaTbView.findViewById(R.id.errSurakshaTube79mm15Meter);
		errSurakshaTube125mm15Meter = (TextView) srkshaTbView.findViewById(R.id.errSurakshaTube125mm15Meter);
		errSurakshaTubeClampHose8mmPipe = (TextView) srkshaTbView.findViewById(R.id.errSurakshaTubeClampHose8mmPipe);
		errSurakshaTubeClampHose20mmPipe = (TextView) srkshaTbView.findViewById(R.id.errSurakshaTubeClampHose20mmPipe);

		rltHeader2 = (RelativeLayout) srkshaTbView.findViewById(R.id.rltHeader2);
		lnHeader2 = (LinearLayout) srkshaTbView.findViewById(R.id.lnHeader2);


		radGroupSurakshaTubeReplaced = (RadioGroup) srkshaTbView.findViewById(R.id.radGroupSurakshaTubeReplaced);
		radBtnNo = (RadioButton) srkshaTbView.findViewById(R.id.radBtnNo);
		radBtnYes = (RadioButton) srkshaTbView.findViewById(R.id.radBtnYes);

		txtVendorSupplied = (TextView) srkshaTbView.findViewById(R.id.txtVendorSupplied);
		txtCompanySupplied = (TextView) srkshaTbView.findViewById(R.id.txtCompanySupplied);
		txtNoChargableQty = (TextView) srkshaTbView.findViewById(R.id.txtNoChargableQty);
		txtChargableQty = (TextView) srkshaTbView.findViewById(R.id.txtChargableQty);

		txtVendorSupplied2 = (TextView) srkshaTbView.findViewById(R.id.txtVendorSupplied2);
		txtCompanySupplied2 = (TextView) srkshaTbView.findViewById(R.id.txtCompanySupplied2);
		txtNoChargableQty2 = (TextView) srkshaTbView.findViewById(R.id.txtNoChargableQty2);
		txtChargableQty2 = (TextView) srkshaTbView.findViewById(R.id.txtChargableQty2);


		llSurkshaTube = (LinearLayout) srkshaTbView.findViewById(R.id.llSurkshaTube);
		llSurkshaTubeDetail = (LinearLayout) srkshaTbView.findViewById(R.id.llSurkshaTubeDetail);


		btnSaveandNext = (Button)srkshaTbView.findViewById(R.id.btnSurakshaSaveandNext);
		btnPrev  = (Button)srkshaTbView.findViewById(R.id.btnSurakshaPrev);

		btnIsReplaced = (Button)srkshaTbView.findViewById(R.id.btnSurakshaTubeReplaced);
		lblSurakshaHeader = (TextView)srkshaTbView.findViewById(R.id.lblSurakshaTubeHeader);

		user = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.DB_USERNAME, "tech");


		radGroupSurakshaTubeReplaced.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {

					case R.id.radBtnYes:
						btnIsReplaced.setText(radBtnYes.getText().toString());
					/*SurakshaTubeVO.setIsReplaced(radBtnYes.getText().toString());

					edtSize1.setEnabled(true);
					edtSize2.setEnabled(true);
					edtSize3.setEnabled(true);*/

						OtherKitchenSurakshaTubeVO.setIsReplaced(radBtnYes.getText().toString());
						surakshaTubeoption = radBtnYes.getText().toString();
						edtSurakshaTube75mm1Meter.setEnabled(true);
						edtSurakshaTube75mm1MeterChargeable.setEnabled(true);
						edtSurakshaTube79mm15Meter.setEnabled(true);
						edtSurakshaTube79mm15MeterChargeable.setEnabled(true);
						edtSurakshaTube125mm15Meter.setEnabled(true);
						edtSurakshaTube125mm15MeterChargeable.setEnabled(true);

						llSurkshaTube.setVisibility(View.VISIBLE);
						llSurkshaTubeDetail.setVisibility(View.VISIBLE);
						rltHeader2.setVisibility(View.GONE);
						lnHeader2.setVisibility(View.GONE);

						break;

					case R.id.radBtnNo:
						btnIsReplaced.setText(radBtnNo.getText().toString());
					/*SurakshaTubeVO.setIsReplaced(radBtnNo.getText().toString());

					edtSize1.setEnabled(false);
					edtSize2.setEnabled(false);
					edtSize3.setEnabled(false);
					edtSize1.setText(Constants.LABEL_BLANK);
					edtSize2.setText(Constants.LABEL_BLANK);
					edtSize3.setText(Constants.LABEL_BLANK);*/
						surakshaTubeoption = radBtnNo.getText().toString();
						OtherKitchenSurakshaTubeVO.setIsReplaced(radBtnNo.getText().toString());
						edtSurakshaTube75mm1Meter.setEnabled(false);
						edtSurakshaTube75mm1MeterChargeable.setEnabled(false);
						edtSurakshaTube79mm15Meter.setEnabled(false);
						edtSurakshaTube79mm15MeterChargeable.setEnabled(false);
						edtSurakshaTube125mm15Meter.setEnabled(false);
						edtSurakshaTube125mm15MeterChargeable.setEnabled(false);

						llSurkshaTube.setVisibility(View.GONE);
						llSurkshaTubeDetail.setVisibility(View.GONE);
						rltHeader2.setVisibility(View.VISIBLE);
						lnHeader2.setVisibility(View.VISIBLE);
						break;
				}

				checkSuppliedStatus();
			}
		});


		cal = Calendar.getInstance();

		isReplaced = btnIsReplaced.getText().toString();
		final String[] dropdownOptions = getResources().getStringArray(R.array.arrayCheckBoolean);
		changeLanguage(id);

		if((custStatusId != userMasterService.getStatusId("OP") && custStatusId != userMasterService.getStatusId("HCL1")) || user.equals("admin")){
			surkshatubeDisableViews();
		}

		if(Global.isCycleRunning)
			MaintanenceForm.btnHeaderProcess.setVisibility(View.VISIBLE);
		else
			MaintanenceForm.btnHeaderProcess.setVisibility(View.INVISIBLE);

		initializeValuesSuraksha();



		radGroupSurakshaTube75mm1Meter.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
					case R.id.radStube75ByCompany:
						radStube75ByCompany.setChecked(true);
						radStube75ByVendor.setChecked(false);
						OtherKitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.SUPPLY_COMPANY);
						surakshaTube75mm1MeterOption = Constants.SUPPLY_COMPANY;
						break;
					case R.id.radStube75ByVendor:
						radStube75ByCompany.setChecked(false);
						radStube75ByVendor.setChecked(true);
						OtherKitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.SUPPLY_VENDOR);
						surakshaTube75mm1MeterOption = Constants.SUPPLY_VENDOR;
						break;
				}
				checkSuppliedStatus();
			}
		});

		radGroupSurakshaTube79mm15Meter.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
					case R.id.radStube7915ByCompany:
						radStube7915ByCompany.setChecked(true);
						radStube75ByVendor.setChecked(false);
						OtherKitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.SUPPLY_COMPANY);
						surakshaTube79mm15MeterOption = Constants.SUPPLY_COMPANY;
						break;
					case R.id.radStube7915ByVendor:
						radStube7915ByCompany.setChecked(false);
						radStube75ByVendor.setChecked(true);
						OtherKitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.SUPPLY_VENDOR);
						surakshaTube79mm15MeterOption = Constants.SUPPLY_VENDOR;
						break;
				}
				checkSuppliedStatus();
			}
		});


		radGroupSurakshaTube125mm15Meter.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
					case R.id.radSTube12515ByCompany:
						radSTube12515ByCompany.setChecked(true);
						radSTube12515ByVendor.setChecked(false);
						OtherKitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.SUPPLY_COMPANY);
						surakshaTube125mm15MeterOption = Constants.SUPPLY_COMPANY;
						break;
					case R.id.radSTube12515ByVendor:
						radSTube12515ByCompany.setChecked(false);
						radSTube12515ByVendor.setChecked(true);
						OtherKitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.SUPPLY_VENDOR);
						surakshaTube125mm15MeterOption = Constants.SUPPLY_VENDOR;
						break;
				}
				checkSuppliedStatus();
			}
		});

		radGroupSurakshaTubeClampHose8mmPipe.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
					case R.id.radHose8mmPipeByCompany:
						radHose8mmPipeByCompany.setChecked(true);
						radHose8mmPipeByVendor.setChecked(false);
						OtherKitchenSurakshaTubeVO.setKSplrClampHose8mmPipe(Constants.SUPPLY_COMPANY);
						surakshaTubeClampHose8mmPipeOption = Constants.SUPPLY_COMPANY;
						break;
					case R.id.radHose8mmPipeByVendor:
						radHose8mmPipeByCompany.setChecked(false);
						radHose8mmPipeByVendor.setChecked(true);
						OtherKitchenSurakshaTubeVO.setKSplrClampHose8mmPipe(Constants.SUPPLY_VENDOR);
						surakshaTubeClampHose8mmPipeOption = Constants.SUPPLY_VENDOR;
						break;
				}
				checkSuppliedStatus();
			}
		});

		radGroupSurakshaTube20mmReplaced.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
					case R.id.radHose20mmPipeByCompany:
						radHose20mmPipeByCompany.setChecked(true);
						radHose20mmPipeByVendor.setChecked(false);
						OtherKitchenSurakshaTubeVO.setKSplrClampHose20mmPipe(Constants.SUPPLY_COMPANY);
						surakshaTubeClampHose20mmPipeOption = Constants.SUPPLY_COMPANY;
						break;
					case R.id.radHose20mmPipeByVendor:
						radHose20mmPipeByCompany.setChecked(false);
						radHose20mmPipeByVendor.setChecked(true);
						OtherKitchenSurakshaTubeVO.setKSplrClampHose20mmPipe(Constants.SUPPLY_VENDOR);
						surakshaTubeClampHose20mmPipeOption = Constants.SUPPLY_VENDOR;
						break;
				}
				checkSuppliedStatus();
			}
		});
/*		btnIsReplaced.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				try{
					new AlertDialog.Builder(getActivity()).setSingleChoiceItems(dropdownOptions,isSurakshaTubeReplaced, new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog, int whichButton){

							btnIsReplaced.setText(dropdownOptions[whichButton]);
							isSurakshaTubeReplaced = whichButton;
							if(dropdownOptions[whichButton].equals("YES")){
								edtSize1.setEnabled(true);
								edtSize2.setEnabled(true);
								edtSize3.setEnabled(true);

							}else{
								edtSize1.setEnabled(false);
								edtSize2.setEnabled(false);
								edtSize3.setEnabled(false);
								edtSize1.setText(Constants.LABEL_BLANK);
								edtSize2.setText(Constants.LABEL_BLANK);
								edtSize3.setText(Constants.LABEL_BLANK);
							}
							OtherKitchenSurakshaTubeVO.setIsReplaced(dropdownOptions[whichButton]);
							validateButton();
							dialog.dismiss();
						}
					})
					.show();
				}
				catch (Exception e){
					e.printStackTrace();
					Log.d(Constants.DEBUG,"SurakshaTubeFragment:"+ Utility.convertExceptionToString(e));
				}
			}
		});
*/
		btnSaveandNext.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Utility.changeSelectedForm(6, 5);
				Utility.changeList(6,false);
				MakeNGeyserFragment mkNgyFragment = MakeNGeyserFragment.newInstance(custStatusId, contactNumber);
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.frmLayoutFragment, mkNgyFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		});


		btnPrev.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Utility.changeSelectedForm(4, 5);
				Utility.changeList(4,false);
				KitchenSurakshaTubeFragment kitchenSurakshaTubeFragment = KitchenSurakshaTubeFragment.newInstance(custStatusId, contactNumber);
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.frmLayoutFragment, kitchenSurakshaTubeFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		});

		return srkshaTbView;
	}

	public void checkSuppliedStatus() {
		if(radStube75ByCompany.isChecked() || radStube75ByVendor.isChecked()) {
			Log.e(getClass().getName(), "******************Change Listener called: Half Clamp");
			edtSurakshaTube75mm1Meter.setEnabled(true);
			edtSurakshaTube75mm1MeterChargeable.setEnabled(true);
		} else {
			edtSurakshaTube75mm1Meter.setEnabled(false);
			edtSurakshaTube75mm1MeterChargeable.setEnabled(false);
		}

		if(radStube7915ByCompany.isChecked() || radStube7915ByVendor.isChecked()) {
			edtSurakshaTube79mm15Meter.setEnabled(true);
			edtSurakshaTube79mm15MeterChargeable.setEnabled(true);
		} else {
			edtSurakshaTube79mm15Meter.setEnabled(false);
			edtSurakshaTube79mm15MeterChargeable.setEnabled(false);
		}

		if(radSTube12515ByCompany.isChecked() || radSTube12515ByVendor.isChecked()) {
			edtSurakshaTube125mm15Meter.setEnabled(true);
			edtSurakshaTube125mm15MeterChargeable.setEnabled(true);
		} else {
			edtSurakshaTube125mm15Meter.setEnabled(false);
			edtSurakshaTube125mm15MeterChargeable.setEnabled(false);
		}

		if(radHose20mmPipeByCompany.isChecked() || radHose20mmPipeByVendor.isChecked()) {
			edtSurakshaTubeClampHose20mmPipe.setEnabled(true);
			edtSurakshaTubeClampHose20mmPipeChargeable.setEnabled(true);
		} else  {
			edtSurakshaTubeClampHose20mmPipe.setEnabled(false);
			edtSurakshaTubeClampHose20mmPipeChargeable.setEnabled(false);
		}

		if(radHose8mmPipeByCompany.isChecked() || radHose8mmPipeByVendor.isChecked()) {
			edtSurakshaTubeClampHose8mmPipe.setEnabled(true);
			edtSurakshaTubeClampHose8mmPipeChargeable.setEnabled(true);
		} else {
			edtSurakshaTubeClampHose8mmPipe.setEnabled(false);
			edtSurakshaTubeClampHose8mmPipeChargeable.setEnabled(false);
		}
	}

	/**
	 * Method to disable all controls when open form in edit mode.
	 * @return void
	 */
	public void surkshatubeDisableViews(){
		btnIsReplaced.setEnabled(false);
		edtSurakshaTube75mm1Meter.setEnabled(false);
		edtSurakshaTube75mm1MeterChargeable.setEnabled(false);
		edtSurakshaTube79mm15Meter.setEnabled(false);
		edtSurakshaTube79mm15MeterChargeable.setEnabled(false);
		edtSurakshaTube125mm15Meter.setEnabled(false);
		edtSurakshaTube125mm15MeterChargeable.setEnabled(false);
		edtSurakshaTubeClampHose8mmPipe.setEnabled(false);
		edtSurakshaTubeClampHose8mmPipeChargeable.setEnabled(false);
		edtSurakshaTubeClampHose20mmPipe.setEnabled(false);
		edtSurakshaTubeClampHose20mmPipeChargeable.setEnabled(false);
		llSurkshaTube.setVisibility(View.GONE);
		llSurkshaTubeDetail.setVisibility(View.GONE);

	}

	/**
	 * Validation method to validate control. If any error ,it will display on error text field.
	 * @return void.
	 */
	private void validateButton(){

		ArrayList<Boolean> errorList = new ArrayList<Boolean>();

		//errSize1.setText(Utility.surakshaTube(Constants.SIZE1,edtSize1.getText().toString(),getActivity()));
//		if(errSize1.getText().toString().equals(Constants.LABEL_BLANK))
//			errorList.add(false);
//		else
//			errorList.add(true);
//
//		//errSize2.setText(Utility.surakshaTube(Constants.SIZE2,edtSize2.getText().toString(),getActivity()));
//		if(errSize2.getText().toString().equals(Constants.LABEL_BLANK))
//			errorList.add(false);
//		else
//			errorList.add(true);
//
//		//errSize3.setText(Utility.surakshaTube(Constants.SIZE3,edtSize3.getText().toString(),getActivity()));
//		if(errSize3.getText().toString().equals(Constants.LABEL_BLANK))
//			errorList.add(false);
//		else
//			errorList.add(true);
//
//		//errSize4.setText(Utility.surakshaTube(Constants.SIZE4,edtSize4.getText().toString(),getActivity()));
//		if(errSize4.getText().toString().equals(Constants.LABEL_BLANK))
//			errorList.add(false);
//		else
//			errorList.add(true);
//
//		errSize5.setText(Utility.surakshaTube(Constants.SIZE5,edtSize5.getText().toString(),getActivity()));
//		if(errSize5.getText().toString().equals(Constants.LABEL_BLANK))
//			errorList.add(false);
//		else
//			errorList.add(true);


		if(btnIsReplaced.getText().toString().equals(getResources().getString(R.string.title_select_option))){
			errorList.add(true);
			errTxtReplaced.setText(Constants.ERROR_MARK);
		}else{
			if(btnIsReplaced.getText().toString().equalsIgnoreCase("yes")) {
				boolean is75Checked = false;
				boolean is79Checked = false;
				boolean is125Checked = false;
				if(radStube75ByCompany.isChecked() || radStube75ByVendor.isChecked()) {
					is75Checked = true;
					if(edtSurakshaTube75mm1Meter.getText().toString().equals(Constants.BLANK) && edtSurakshaTube75mm1MeterChargeable.getText().toString().equals(Constants.BLANK)) {
						errorList.add(true);
					} else {
						errorList.add(false);
					}
				} else {
					is75Checked = false;
					errorList.add(false);
				}

				if(radStube7915ByCompany.isChecked() || radStube7915ByVendor.isChecked()) {
					is79Checked = true;
					if(edtSurakshaTube79mm15Meter.getText().toString().equals(Constants.BLANK) && edtSurakshaTube79mm15MeterChargeable.getText().toString().equals(Constants.BLANK)) {
						errorList.add(true);
					} else {
						errorList.add(false);
					}
				} else {
					is79Checked = false;
					errorList.add(false);
				}

				if(radSTube12515ByCompany.isChecked() || radSTube12515ByVendor.isChecked()) {
					is125Checked = true;
					if(edtSurakshaTube125mm15Meter.getText().toString().equals(Constants.BLANK) && edtSurakshaTube125mm15MeterChargeable.getText().toString().equals(Constants.BLANK)) {
						errorList.add(true);
					} else {
						errorList.add(false);
					}
				} else {
					is125Checked = false;
					errorList.add(false);
				}

				if(is125Checked || is75Checked || is79Checked) {
					errorList.add(false);
				} else {
					errorList.add(true);
				}


				if(radHose8mmPipeByCompany.isChecked() || radHose8mmPipeByVendor.isChecked()) {
					if(edtSurakshaTubeClampHose8mmPipe.getText().toString().equals(Constants.BLANK) && edtSurakshaTubeClampHose8mmPipeChargeable.getText().toString().equals(Constants.BLANK)) {
						errorList.add(true);
					} else {
						errorList.add(false);
					}
				} else {
					errorList.add(false);
				}

				if(radHose20mmPipeByCompany.isChecked() || radHose20mmPipeByVendor.isChecked()) {
					if(edtSurakshaTubeClampHose20mmPipe.getText().toString().equals(Constants.BLANK) && edtSurakshaTubeClampHose20mmPipeChargeable.getText().toString().equals(Constants.BLANK)) {
						errorList.add(true);
					} else {
						errorList.add(false);
					}
				} else {
					errorList.add(false);
				}
				
				/*if(edtSurakshaTube75mm1Meter.getText().toString().equals(Constants.BLANK) ||
						edtSurakshaTube75mm1MeterChargeable.getText().toString().equals(Constants.BLANK) || 
						edtSurakshaTube79mm15Meter.getText().toString().equals(Constants.BLANK) || 
						edtSurakshaTube79mm15MeterChargeable.getText().toString().equals(Constants.BLANK) ||
						edtSurakshaTube125mm15Meter.getText().toString().equals(Constants.BLANK) || 
						edtSurakshaTube125mm15MeterChargeable.getText().toString().equals(Constants.BLANK) ||
						edtSurakshaTubeClampHose8mmPipe.getText().toString().equals(Constants.BLANK) || 
						edtSurakshaTubeClampHose8mmPipeChargeable.getText().toString().equals(Constants.BLANK) ||
						edtSurakshaTubeClampHose20mmPipe.getText().toString().equals(Constants.BLANK) ||
						edtSurakshaTubeClampHose20mmPipeChargeable.getText().toString().equals(Constants.BLANK)) {
					errorList.add(true);
					errTxtReplaced.setText(Constants.ERROR_MARK);
				}  else {
					errorList.add(false);
					errTxtReplaced.setText(Constants.ERROR_MARK);
				}*/
			} else if(btnIsReplaced.getText().toString().equalsIgnoreCase("no")) {
				if(radHose8mmPipeByCompany.isChecked() || radHose8mmPipeByVendor.isChecked()) {
					if(edtSurakshaTubeClampHose8mmPipe.getText().toString().equals(Constants.BLANK) && edtSurakshaTubeClampHose8mmPipeChargeable.getText().toString().equals(Constants.BLANK)) {
						errorList.add(true);
					} else {
						errorList.add(false);
					}
				} else {
					errorList.add(false);
				}

				if(radHose20mmPipeByCompany.isChecked() || radHose20mmPipeByVendor.isChecked()) {
					if(edtSurakshaTubeClampHose20mmPipe.getText().toString().equals(Constants.BLANK) && edtSurakshaTubeClampHose20mmPipeChargeable.getText().toString().equals(Constants.BLANK)) {
						errorList.add(true);
					} else {
						errorList.add(false);
					}
				} else {
					errorList.add(false);
				}
				/*if(edtSurakshaTubeClampHose8mmPipe.getText().toString().equals(Constants.BLANK) || 
						edtSurakshaTubeClampHose8mmPipeChargeable.getText().toString().equals(Constants.BLANK) ||
						edtSurakshaTubeClampHose20mmPipe.getText().toString().equals(Constants.BLANK) ||
						edtSurakshaTubeClampHose20mmPipeChargeable.getText().toString().equals(Constants.BLANK)) {
					errorList.add(true);
					errTxtReplaced.setText(Constants.ERROR_MARK);
				} else {
					errorList.add(false);
					errTxtReplaced.setText(Constants.ERROR_MARK);
				}*/
			}
		/*	errorList.add(false);
			errTxtReplaced.setText(Constants.ERROR_MARK);*/
//			errTxtReplaced.setText(Constants.LABEL_BLANK);
		}

		Collections.sort(errorList);
		System.err.println(" Correction Error --- "+ Collections.binarySearch(errorList,true));
		if(Collections.binarySearch(errorList,true) >= 0)
			//Utility.changeList(5,false);
			Utility.changeList(5,true);
		else
			Utility.changeList(5,false);
	}

	/**
	 * Method used to change label values as per parameter to English and Gujarati.
	 * @param id : 1 - English , 2 - Gujarati
	 */
	public void changeLanguage(int id){

		id = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.PREF_LANGUAGE,Constants.LANGUAGE_ENGLISH);

		switch (id){
			case Constants.LANGUAGE_ENGLISH:

				lblsurakshaTubeReplaced.setText(getResources().getString(R.string.title_surakshaTube_Replaced_Eng));
				lblSurakshaTube75mm1Meter.setText(getResources().getString(R.string.title_surakshaTube_79mm_1_meter_Eng));
				lblSurakshaTube79mm15Meter.setText(getResources().getString(R.string.title_surakshaTube_79mm_15_Meter_Eng));
				lblSurakshaTube125mm15Meter.setText(getResources().getString(R.string.title_surakshaTube_125mm_15_Meter_Eng));
				lblSurakshaTubeClampHose8mmPipe.setText(getResources().getString(R.string.title_surakshaTube_ClampHose_8mm_Pipe_Eng));
				lblSurakshaTubeClampHose20mmpipe.setText(getResources().getString(R.string.title_surakshaTube_ClampHose_20mm_pipe_Eng));
				lblSurakshaHeader.setText(getResources().getText(R.string.header_other_suraksha_tube_Eng));
				txtVendorSupplied.setText(getResources().getText(R.string.lbl_by_supply_vendor_Eng));
				txtCompanySupplied.setText(getResources().getText(R.string.lbl_by_supply_company_Eng));
				txtNoChargableQty.setText(getResources().getText(R.string.lbl_non_chargable_qty_Eng));
				txtChargableQty.setText(getResources().getText(R.string.lbl_chargable_qty_Eng));

				txtVendorSupplied2.setText(getResources().getText(R.string.lbl_by_supply_vendor_Eng));
				txtCompanySupplied2.setText(getResources().getText(R.string.lbl_by_supply_company_Eng));
				txtNoChargableQty2.setText(getResources().getText(R.string.lbl_non_chargable_qty_Eng));
				txtChargableQty2.setText(getResources().getText(R.string.lbl_chargable_qty_Eng));
				break;

			case Constants.LANGUAGE_GUJRATI:
				lblSurakshaHeader.setTypeface(Global.typeface_Guj);

				lblSurakshaTube75mm1Meter.setTypeface(Global.typeface_Guj);
				lblSurakshaTube79mm15Meter.setTypeface(Global.typeface_Guj);
				lblSurakshaTube125mm15Meter.setTypeface(Global.typeface_Guj);
				lblSurakshaTubeClampHose8mmPipe.setTypeface(Global.typeface_Guj);
				lblSurakshaTubeClampHose20mmpipe.setTypeface(Global.typeface_Guj);
				lblsurakshaTubeReplaced.setTypeface(Global.typeface_Guj);
				txtVendorSupplied.setTypeface(Global.typeface_Guj);
				txtCompanySupplied.setTypeface(Global.typeface_Guj);
				txtNoChargableQty.setTypeface(Global.typeface_Guj);
				txtChargableQty.setTypeface(Global.typeface_Guj);
				txtVendorSupplied2.setTypeface(Global.typeface_Guj);
				txtCompanySupplied2.setTypeface(Global.typeface_Guj);
				txtNoChargableQty2.setTypeface(Global.typeface_Guj);
				txtChargableQty2.setTypeface(Global.typeface_Guj);
				lblsurakshaTubeReplaced.setText(getResources().getString(R.string.title_surakshaTube_Replaced_Guj));
				lblSurakshaTube75mm1Meter.setText(getResources().getString(R.string.title_surakshaTube_79mm_1_meter_Guj));
				lblSurakshaTube79mm15Meter.setText(getResources().getString(R.string.title_surakshaTube_79mm_15_Meter_Guj));
				lblSurakshaTube125mm15Meter.setText(getResources().getString(R.string.title_surakshaTube_125mm_15_Meter_Guj));
				lblSurakshaTubeClampHose8mmPipe.setText(getResources().getString(R.string.title_surakshaTube_ClampHose_8mm_Pipe_Guj));
				lblSurakshaTubeClampHose20mmpipe.setText(getResources().getString(R.string.title_surakshaTube_ClampHose_20mm_pipe_Guj));
				lblSurakshaHeader.setText(getResources().getText(R.string.header_other_suraksha_tube_Guj));
				txtVendorSupplied.setText(getResources().getText(R.string.lbl_by_supply_vendor_Guj));
				txtCompanySupplied.setText(getResources().getText(R.string.lbl_by_supply_company_Guj));
				txtNoChargableQty.setText(getResources().getText(R.string.lbl_non_chargable_qty_Guj));
				txtChargableQty.setText(getResources().getText(R.string.lbl_chargable_qty_Guj));
				txtVendorSupplied2.setText(getResources().getText(R.string.lbl_by_supply_vendor_Guj));
				txtCompanySupplied2.setText(getResources().getText(R.string.lbl_by_supply_company_Guj));
				txtNoChargableQty2.setText(getResources().getText(R.string.lbl_non_chargable_qty_Guj));
				txtChargableQty2.setText(getResources().getText(R.string.lbl_chargable_qty_Guj));


				break;

			default:
				break;
		}
	}

	/**
	 * Method used to set values for field and controls.
	 * @return void
	 *
	 */
	private void initializeValuesSuraksha(){

		errSurakshaTube75mm1Meter.setText(Utility.surakshaTube(Constants.SIZE1,edtSurakshaTube75mm1MeterChargeable.getText().toString(),getActivity()));
		errSurakshaTube79mm15Meter.setText(Utility.surakshaTube(Constants.SIZE2,edtSurakshaTube79mm15MeterChargeable.getText().toString(),getActivity()));
		errSurakshaTube125mm15Meter.setText(Utility.surakshaTube(Constants.SIZE3,edtSurakshaTube125mm15MeterChargeable.getText().toString(),getActivity()));
		errSurakshaTubeClampHose8mmPipe.setText(Utility.surakshaTube(Constants.SIZE4,edtSurakshaTubeClampHose8mmPipeChargeable.getText().toString(),getActivity()));
		errSurakshaTubeClampHose20mmPipe.setText(Utility.surakshaTube(Constants.SIZE5,edtSurakshaTubeClampHose20mmPipeChargeable.getText().toString(),getActivity()));

		surakshaTubeoption = OtherKitchenSurakshaTubeVO.getIsReplaced();
		surakshaTube75mm1MeterOption = OtherKitchenSurakshaTubeVO.getKSplr1Meter75mm();
		surakshaTube79mm15MeterOption = OtherKitchenSurakshaTubeVO.getKSplr15Meter79mm();
		surakshaTube125mm15MeterOption = OtherKitchenSurakshaTubeVO.getKSplrChrg15Meter125mm();
		surakshaTubeClampHose8mmPipeOption = OtherKitchenSurakshaTubeVO.getKSplrClampHose8mmPipe();
		surakshaTubeClampHose20mmPipeOption = OtherKitchenSurakshaTubeVO.getKSplrClampHose20mmPipe();

		if(surakshaTube75mm1MeterOption.equals(Constants.LABEL_BLANK)){
			radStube75ByCompany.setChecked(false);
			radStube75ByVendor.setChecked(false);

		}else if(surakshaTube75mm1MeterOption.equals(Constants.SUPPLY_COMPANY)){
			radStube75ByCompany.setChecked(true);
			radStube75ByVendor.setChecked(false);
		}else if(surakshaTube75mm1MeterOption.equals(Constants.SUPPLY_VENDOR)){
			radStube75ByCompany.setChecked(false);
			radStube75ByVendor.setChecked(true);
		} else {
			radStube75ByCompany.setChecked(false);
			radStube75ByVendor.setChecked(false);
		}

		if(surakshaTube79mm15MeterOption.equals(Constants.LABEL_BLANK)){
			radStube7915ByCompany.setChecked(false);
			radStube7915ByVendor.setChecked(false);
		}else if(surakshaTube79mm15MeterOption.equals(Constants.SUPPLY_COMPANY)){
			radStube7915ByCompany.setChecked(true);
			radStube7915ByVendor.setChecked(false);
		}else if(surakshaTube79mm15MeterOption.equals(Constants.SUPPLY_VENDOR)){
			radStube7915ByCompany.setChecked(false);
			radStube7915ByVendor.setChecked(true);
		} else {
			radStube7915ByCompany.setChecked(false);
			radStube7915ByVendor.setChecked(false);
		}

		if(surakshaTube125mm15MeterOption.equals(Constants.LABEL_BLANK)){
			radSTube12515ByCompany.setChecked(false);
			radSTube12515ByVendor.setChecked(false);
		}else if(surakshaTube125mm15MeterOption.equals(Constants.SUPPLY_COMPANY)){
			radSTube12515ByCompany.setChecked(true);
			radSTube12515ByVendor.setChecked(false);
		}else if(surakshaTube125mm15MeterOption.equals(Constants.SUPPLY_VENDOR)){
			radSTube12515ByCompany.setChecked(false);
			radSTube12515ByVendor.setChecked(true);
		} else {
			radSTube12515ByCompany.setChecked(false);
			radSTube12515ByVendor.setChecked(false);
		}



		if(surakshaTubeClampHose8mmPipeOption.equals(Constants.LABEL_BLANK)){
			radHose8mmPipeByCompany.setChecked(false);
			radHose8mmPipeByVendor.setChecked(false);
		}else if(surakshaTubeClampHose8mmPipeOption.equals(Constants.SUPPLY_COMPANY)){
			radHose8mmPipeByCompany.setChecked(true);
			radHose8mmPipeByVendor.setChecked(false);
		}else if(surakshaTubeClampHose8mmPipeOption.equals(Constants.SUPPLY_VENDOR)){
			radHose8mmPipeByCompany.setChecked(false);
			radHose8mmPipeByVendor.setChecked(true);
		} else {
			radHose8mmPipeByCompany.setChecked(false);
			radHose8mmPipeByVendor.setChecked(false);
		}

		if(surakshaTubeClampHose20mmPipeOption.equals(Constants.LABEL_BLANK)){
			radHose20mmPipeByCompany.setChecked(false);
			radHose20mmPipeByVendor.setChecked(false);
		}else if(surakshaTubeClampHose20mmPipeOption.equals(Constants.SUPPLY_COMPANY)){
			radHose20mmPipeByCompany.setChecked(true);
			radHose20mmPipeByVendor.setChecked(false);
		}else if(surakshaTubeClampHose20mmPipeOption.equals(Constants.SUPPLY_VENDOR)){
			radHose20mmPipeByCompany.setChecked(false);
			radHose20mmPipeByVendor.setChecked(true);
		} else {
			radHose20mmPipeByCompany.setChecked(false);
			radHose20mmPipeByVendor.setChecked(false);
		}


		if(surakshaTubeoption.equals(Constants.LABEL_BLANK)){
			surakshaTubeoption = getResources().getString(R.string.title_select_option);
			btnIsReplaced.setText(surakshaTubeoption);
			radBtnNo.setChecked(false);
			radBtnYes.setChecked(false);
			llSurkshaTube.setVisibility(View.GONE);
			llSurkshaTubeDetail.setVisibility(View.GONE);
		}else{
			btnIsReplaced.setText(surakshaTubeoption);
			if("YES".equals(surakshaTubeoption))
			{
				isSurakshaTubeReplaced = 0;
				radBtnNo.setChecked(false);
				radBtnYes.setChecked(true);
				llSurkshaTube.setVisibility(View.VISIBLE);
				llSurkshaTubeDetail.setVisibility(View.VISIBLE);
			}
			else if("NO".equals(surakshaTubeoption))
			{
				isSurakshaTubeReplaced = 1;
				radBtnNo.setChecked(true);
				radBtnYes.setChecked(false);
				llSurkshaTube.setVisibility(View.GONE);
				llSurkshaTubeDetail.setVisibility(View.GONE);
			}
		}

		if(surakshaTubeoption.equals("YES")){
			if(custStatusId != userMasterService.getStatusId("OP"))
			{
				edtSurakshaTube125mm15Meter.setEnabled(false);
				edtSurakshaTube125mm15MeterChargeable.setEnabled(false);
				edtSurakshaTube75mm1Meter.setEnabled(false);
				edtSurakshaTube75mm1MeterChargeable.setEnabled(false);
				edtSurakshaTube79mm15Meter.setEnabled(false);
				edtSurakshaTube79mm15MeterChargeable.setEnabled(false);
			}else{
				edtSurakshaTube125mm15Meter.setEnabled(true);
				edtSurakshaTube125mm15MeterChargeable.setEnabled(true);
				edtSurakshaTube75mm1Meter.setEnabled(true);
				edtSurakshaTube75mm1MeterChargeable.setEnabled(true);
				edtSurakshaTube79mm15Meter.setEnabled(true);
				edtSurakshaTube79mm15MeterChargeable.setEnabled(true);
			}
		}else if(surakshaTubeoption.equals("NO")){
			edtSurakshaTube125mm15Meter.setEnabled(false);
			edtSurakshaTube125mm15MeterChargeable.setEnabled(false);
			edtSurakshaTube75mm1Meter.setEnabled(false);
			edtSurakshaTube75mm1MeterChargeable.setEnabled(false);
			edtSurakshaTube79mm15Meter.setEnabled(false);
			edtSurakshaTube79mm15MeterChargeable.setEnabled(false);
		}



		edtSurakshaTube75mm1Meter.setText(OtherKitchenSurakshaTubeVO.getSize751nc());
		edtSurakshaTube75mm1MeterChargeable.setText(OtherKitchenSurakshaTubeVO.getSize751c());
		edtSurakshaTube79mm15Meter.setText(OtherKitchenSurakshaTubeVO.getSize7915nc());
		edtSurakshaTube79mm15MeterChargeable.setText(OtherKitchenSurakshaTubeVO.getSize7915c());
		edtSurakshaTube125mm15Meter.setText(OtherKitchenSurakshaTubeVO.getSize12515nc());
		edtSurakshaTube125mm15MeterChargeable.setText(OtherKitchenSurakshaTubeVO.getSize12515c());
		edtSurakshaTubeClampHose8mmPipe.setText(OtherKitchenSurakshaTubeVO.getClampsize8nc());
		edtSurakshaTubeClampHose8mmPipeChargeable.setText(OtherKitchenSurakshaTubeVO.getClampsize8c());
		edtSurakshaTubeClampHose20mmPipe.setText(OtherKitchenSurakshaTubeVO.getClampsize20nc());
		edtSurakshaTubeClampHose20mmPipeChargeable.setText(OtherKitchenSurakshaTubeVO.getClampsize20c());


//		edtSize1.setText(SurakshaTubeVO.getSize1());
//		edtSize2.setText(SurakshaTubeVO.getSize2());		    
//		edtSize3.setText(SurakshaTubeVO.getSize3());  
//		edtSize4.setText(SurakshaTubeVO.getClampSize1());
//		edtSize5.setText(SurakshaTubeVO.getClamSize2());

		validateButton();
		checkSuppliedStatus();

	}




	/**
	 * Interface is a listener between the Date Dialog fragment and the activity
	 * to update the buttons date
	 *
	 */
	public interface DateDialogFragmentListeners {

		public void updateChangedDate(int year, int month, int day);
	}

	@Override
	public void onPause() {
		super.onPause();
		try{
			if("OP".equals(TestingFragment.testingStatusCode) || "HCL1".equals(TestingFragment.testingStatusCode))
			{
				validateButton();
				setValues();
			}
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,"SurakshaTubeFragment:"+ Utility.convertExceptionToString(e));
		}
	}

	public void clearValues()
	{
		try{
			if(edtSurakshaTube75mm1Meter != null && edtSurakshaTube75mm1MeterChargeable != null
					&& edtSurakshaTube79mm15Meter != null && edtSurakshaTube79mm15MeterChargeable !=null
					&& edtSurakshaTube125mm15Meter != null && edtSurakshaTube125mm15MeterChargeable != null
					&& edtSurakshaTubeClampHose8mmPipe != null && edtSurakshaTubeClampHose8mmPipeChargeable != null
					&& edtSurakshaTubeClampHose20mmPipe != null &&edtSurakshaTubeClampHose20mmPipeChargeable != null ){

				edtSurakshaTube75mm1Meter.setText(Constants.LABEL_BLANK);
				edtSurakshaTube75mm1MeterChargeable.setText(Constants.LABEL_BLANK);
				edtSurakshaTube79mm15Meter.setText(Constants.LABEL_BLANK);
				edtSurakshaTube79mm15MeterChargeable.setText(Constants.LABEL_BLANK);
				edtSurakshaTube125mm15Meter.setText(Constants.LABEL_BLANK);
				edtSurakshaTube125mm15MeterChargeable.setText(Constants.LABEL_BLANK);
				edtSurakshaTubeClampHose8mmPipe.setText(Constants.LABEL_BLANK);
				edtSurakshaTubeClampHose8mmPipeChargeable.setText(Constants.LABEL_BLANK);
				edtSurakshaTubeClampHose20mmPipe.setText(Constants.LABEL_BLANK);
				edtSurakshaTubeClampHose20mmPipeChargeable.setText(Constants.LABEL_BLANK);
			}

			if(radBtnNo != null)
				radBtnNo.setChecked(false);

			if(radBtnYes != null)
				radBtnYes.setChecked(false);

			if(radHose20mmPipeByCompany != null)
				radHose20mmPipeByCompany.setChecked(false);

			if(radHose20mmPipeByVendor != null)
				radHose20mmPipeByVendor.setChecked(false);

			if(radHose8mmPipeByCompany != null)
				radHose8mmPipeByCompany.setChecked(false);

			if(radHose8mmPipeByVendor != null)
				radHose8mmPipeByVendor.setChecked(false);

			if(radSTube12515ByCompany != null)
				radSTube12515ByCompany.setChecked(false);

			if(radSTube12515ByVendor != null)
				radStube7915ByVendor.setChecked(false);

			if(radStube75ByCompany != null)
				radStube75ByCompany.setChecked(false);

			if(radStube75ByVendor != null)
				radStube75ByVendor.setChecked(false);

			if(radStube7915ByCompany != null)
				radStube7915ByCompany.setChecked(false);

			if(radStube7915ByVendor != null)
				radStube7915ByVendor.setChecked(false);
			surakshaTube75mm1MeterOption = Constants.LABEL_BLANK;
			surakshaTube79mm15MeterOption = Constants.LABEL_BLANK;
			surakshaTube125mm15MeterOption = Constants.LABEL_BLANK;
			surakshaTubeClampHose8mmPipeOption = Constants.LABEL_BLANK;
			surakshaTubeClampHose20mmPipeOption =Constants.LABEL_BLANK;

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setValues()
	{
		try{

			OtherKitchenSurakshaTubeVO.setSize751c(edtSurakshaTube75mm1MeterChargeable.getText().toString());
			OtherKitchenSurakshaTubeVO.setSize751nc(edtSurakshaTube75mm1Meter.getText().toString());
			OtherKitchenSurakshaTubeVO.setSize7915c(edtSurakshaTube79mm15MeterChargeable.getText().toString());
			OtherKitchenSurakshaTubeVO.setSize7915nc(edtSurakshaTube79mm15Meter.getText().toString());
			OtherKitchenSurakshaTubeVO.setSize12515c(edtSurakshaTube125mm15MeterChargeable.getText().toString());
			OtherKitchenSurakshaTubeVO.setSize12515nc(edtSurakshaTube125mm15Meter.getText().toString());
			OtherKitchenSurakshaTubeVO.setClampsize8c(edtSurakshaTubeClampHose8mmPipeChargeable.getText().toString());
			OtherKitchenSurakshaTubeVO.setClampsize8nc(edtSurakshaTubeClampHose8mmPipe.getText().toString());
			OtherKitchenSurakshaTubeVO.setClampsize20c(edtSurakshaTubeClampHose20mmPipeChargeable.getText().toString());
			OtherKitchenSurakshaTubeVO.setClampsize20nc(edtSurakshaTubeClampHose20mmPipe.getText().toString());

			OtherKitchenSurakshaTubeVO.setKSplr1Meter75mm(surakshaTube75mm1MeterOption);
			OtherKitchenSurakshaTubeVO.setKSplr15Meter79mm(surakshaTube79mm15MeterOption);
			OtherKitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(surakshaTube125mm15MeterOption);
			OtherKitchenSurakshaTubeVO.setKSplrClampHose8mmPipe(surakshaTubeClampHose8mmPipeOption);
			OtherKitchenSurakshaTubeVO.setKSplrClampHose20mmPipe(surakshaTubeClampHose20mmPipeOption);

			clearValues();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}



}
