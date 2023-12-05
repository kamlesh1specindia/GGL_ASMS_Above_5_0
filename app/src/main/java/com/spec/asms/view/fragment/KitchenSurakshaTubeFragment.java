package com.spec.asms.view.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.spec.asms.view.fragment.MaintanenceForm.DateDialogFragmentListener;
import com.spec.asms.vo.KitchenSurakshaTubeVO;

import org.w3c.dom.Text;

public class KitchenSurakshaTubeFragment extends Fragment implements OnClickListener{



	private TextView lblExpiryDate;
	private TextView lblExpiryReplacedDate;
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
	private ImageButton imgBtnReplacedExpiryDate;
	private ImageButton imgBtnExpiryDate;
	private TextView txtExpiryDate;
	private TextView txtRelpacedExpiryDate;


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

	private TextView txtVendorSupplied;
	private TextView txtCompanySupplied;
	private TextView txtNoChargableQty;
	private TextView txtChargableQty;

	private TextView txtVendorSupplied2;
	private TextView txtCompanySupplied2;
	private TextView txtNoChargableQty2;
	private TextView txtChargableQty2;

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
	private String selectedDate;
	private TextView errExpiryDate;
	private String contactNumber;
	private android.widget.CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new android.widget.CompoundButton.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			checkSuppliedStatus(buttonView);
		}
	};
	/**
	 * A parameterized constructor
	 * @param  Status id: the id for which the profile is to be created
	 * @return SurkshTube Fragment object
	 */
	public static KitchenSurakshaTubeFragment newInstance(int custStatusId, String contactNumber){
		KitchenSurakshaTubeFragment kitchenSurakshaTubeFragment = new KitchenSurakshaTubeFragment();
		Bundle args = new Bundle();
		args.putInt("statusid", custStatusId);
		args.putString("contactNumber", contactNumber);
		kitchenSurakshaTubeFragment.setArguments(args);
		return kitchenSurakshaTubeFragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		srkshaTbView = inflater.inflate(R.layout.form_kitchen_suraksha_tube,null);
		fragmentManager = getFragmentManager();
		custStatusId = getArguments().getInt("statusid");
		contactNumber = getArguments().getString("contactNumber");
		userMasterService = new UserMasterService(getActivity());

		lblExpiryDate = (TextView) srkshaTbView.findViewById(R.id.lblExpiryDate);
		errExpiryDate = (TextView) srkshaTbView.findViewById(R.id.errExpiryDate);
		lblExpiryReplacedDate = (TextView) srkshaTbView.findViewById(R.id.lblExpiryReplacedDate);
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

		edtSurakshaTube125mm15Meter.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.toString().length() > 0) {
					edtSurakshaTube125mm15MeterChargeable.setText(Constants.BLANK);
				}
			}
		});
		edtSurakshaTube125mm15MeterChargeable.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.toString().length() > 0) {
					edtSurakshaTube125mm15Meter.setText(Constants.BLANK);
				}
			}
		});

		edtSurakshaTube75mm1Meter.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.toString().length() > 0) {
					edtSurakshaTube75mm1MeterChargeable.setText(Constants.BLANK);
				}
			}
		});

		edtSurakshaTube75mm1MeterChargeable.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.toString().length() > 0) {
					edtSurakshaTube75mm1Meter.setText(Constants.BLANK);
				}
			}
		});

		edtSurakshaTube79mm15Meter.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.toString().length() > 0) {
					edtSurakshaTube79mm15MeterChargeable.setText(Constants.BLANK);
				}
			}
		});

		edtSurakshaTube79mm15MeterChargeable.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.toString().length() > 0) {
					edtSurakshaTube79mm15Meter.setText(Constants.BLANK);
				}
			}
		});
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

		txtExpiryDate = (TextView) srkshaTbView.findViewById(R.id.txtExpiryDate);
		txtRelpacedExpiryDate = (TextView) srkshaTbView.findViewById(R.id.txtRelpacedExpiryDate);

		imgBtnReplacedExpiryDate = (ImageButton) srkshaTbView.findViewById(R.id.imgBtnReplacedExpiryDate);
		imgBtnExpiryDate = (ImageButton) srkshaTbView.findViewById(R.id.imgBtnExpiryDate);

		lblExpiryDate = (TextView) srkshaTbView.findViewById(R.id.lblExpiryDate);
		lblExpiryReplacedDate = (TextView) srkshaTbView.findViewById(R.id.lblExpiryReplacedDate);

		llSurkshaTube = (LinearLayout) srkshaTbView.findViewById(R.id.llSurkshaTube);
		llSurkshaTubeDetail = (LinearLayout) srkshaTbView.findViewById(R.id.llSurkshaTubeDetail);


		btnSaveandNext = (Button)srkshaTbView.findViewById(R.id.btnSurakshaSaveandNext);
		btnPrev  = (Button)srkshaTbView.findViewById(R.id.btnSurakshaPrev);

		btnIsReplaced = (Button)srkshaTbView.findViewById(R.id.btnSurakshaTubeReplaced);
		lblSurakshaHeader = (TextView)srkshaTbView.findViewById(R.id.lblSurakshaTubeHeader);

		txtVendorSupplied = (TextView) srkshaTbView.findViewById(R.id.txtVendorSupplied);
		txtCompanySupplied = (TextView) srkshaTbView.findViewById(R.id.txtCompanySupplied);
		txtNoChargableQty = (TextView) srkshaTbView.findViewById(R.id.txtNoChargableQty);
		txtChargableQty = (TextView) srkshaTbView.findViewById(R.id.txtChargableQty);

		txtVendorSupplied2 = (TextView) srkshaTbView.findViewById(R.id.txtVendorSupplied2);
		txtCompanySupplied2 = (TextView) srkshaTbView.findViewById(R.id.txtCompanySupplied2);
		txtNoChargableQty2 = (TextView) srkshaTbView.findViewById(R.id.txtNoChargableQty2);
		txtChargableQty2 = (TextView) srkshaTbView.findViewById(R.id.txtChargableQty2);

		user = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.DB_USERNAME, "tech");

		radStube75ByCompany.setOnClickListener(this);
		radStube75ByVendor.setOnClickListener(this);
		radStube7915ByCompany.setOnClickListener(this);
		radStube7915ByVendor.setOnClickListener(this);
		radSTube12515ByCompany.setOnClickListener(this);
		radSTube12515ByVendor.setOnClickListener(this);


		radGroupSurakshaTubeReplaced.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {

					case R.id.radBtnYes:
						btnIsReplaced.setText(radBtnYes.getText().toString());
				/*	SurakshaTubeVO.setIsReplaced(radBtnYes.getText().toString());

					edtSize1.setEnabled(true);
					edtSize2.setEnabled(true);
					edtSize3.setEnabled(true);*/

						KitchenSurakshaTubeVO.setIsReplaced(radBtnYes.getText().toString());
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
						btnIsReplaced.setText("Yes");
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
						KitchenSurakshaTubeVO.setIsReplaced(radBtnNo.getText().toString());
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
						btnIsReplaced.setText("No");
						break;
				}
				validateButton();
				checkSuppliedStatus(null);
			}
		});


		cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		String date;
		if(month <10){
			date = Constants.LABEL_BLANK + "0"+  month + year;
		}else{
			date = Constants.LABEL_BLANK +  month + year;
		}
		txtRelpacedExpiryDate.setText(date);

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



		imgBtnExpiryDate.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				showExpiryDateDialog();
			}
		});

		imgBtnReplacedExpiryDate.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				showReplaceExpiryDateDialog();
			}
		});
		/*
		radGroupSurakshaTube75mm1Meter.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.radStube75ByCompany:
					//if(radStube75ByCompany.isChecked()) {

					radStube75ByCompany.setChecked(true);
					radStube75ByVendor.setChecked(false);

					radStube7915ByCompany.setChecked(false);
					radStube7915ByVendor.setChecked(false);

					radSTube12515ByCompany.setChecked(false);
					radSTube12515ByVendor.setChecked(false);

					edtSurakshaTube125mm15Meter.setText(Constants.BLANK);
					edtSurakshaTube125mm15MeterChargeable.setText(Constants.BLANK);
					edtSurakshaTube79mm15Meter.setText(Constants.BLANK);
					edtSurakshaTube79mm15MeterChargeable.setText(Constants.BLANK);
					KitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.SUPPLY_COMPANY);
					KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.BLANK);
					KitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.BLANK);
					surakshaTube75mm1MeterOption = Constants.SUPPLY_COMPANY;
					surakshaTube79mm15MeterOption = Constants.BLANK;
					surakshaTube125mm15MeterOption = Constants.BLANK;
					checkSuppliedStatus(null);
					//}
					break;
				case R.id.radStube75ByVendor:
					//if(radStube75ByVendor.isChecked()) {

					radStube75ByVendor.setChecked(true);
					radStube75ByCompany.setChecked(false);

					radStube7915ByCompany.setChecked(false);
					radStube7915ByVendor.setChecked(false);

					radSTube12515ByCompany.setChecked(false);
					radSTube12515ByVendor.setChecked(false);

					edtSurakshaTube125mm15Meter.setText(Constants.BLANK);
					edtSurakshaTube125mm15MeterChargeable.setText(Constants.BLANK);
					edtSurakshaTube79mm15Meter.setText(Constants.BLANK);
					edtSurakshaTube79mm15MeterChargeable.setText(Constants.BLANK);
					KitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.SUPPLY_VENDOR);
					KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.BLANK);
					KitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.BLANK);
					surakshaTube75mm1MeterOption = Constants.SUPPLY_VENDOR;
					surakshaTube79mm15MeterOption = Constants.BLANK;
					surakshaTube125mm15MeterOption = Constants.BLANK;
					checkSuppliedStatus(null);
					//}
					break;
				}

			}
		});

		radGroupSurakshaTube79mm15Meter.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.radStube7915ByCompany:
							//if(radStube7915ByCompany.isChecked()) {


							radStube7915ByCompany.setChecked(true);
							radStube7915ByVendor.setChecked(false);

							radStube75ByCompany.setChecked(false);
							radStube75ByVendor.setChecked(false);
							radSTube12515ByCompany.setChecked(false);
							radSTube12515ByVendor.setChecked(false);

							edtSurakshaTube125mm15Meter.setText(Constants.BLANK);
							edtSurakshaTube125mm15MeterChargeable.setText(Constants.BLANK);
							edtSurakshaTube75mm1Meter.setText(Constants.BLANK);
							edtSurakshaTube75mm1MeterChargeable.setText(Constants.BLANK);
							KitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.SUPPLY_COMPANY);
							KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.BLANK);
							KitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.BLANK);
							surakshaTube79mm15MeterOption = Constants.SUPPLY_COMPANY;
							surakshaTube75mm1MeterOption = Constants.BLANK;
							surakshaTube125mm15MeterOption = Constants.BLANK;
							checkSuppliedStatus(null);
							//}
							break;
						case R.id.radStube7915ByVendor:
							//if(radStube7915ByVendor.isChecked()) {

							radStube7915ByVendor.setChecked(true);
							radStube7915ByCompany.setChecked(false);

							radStube75ByCompany.setChecked(false);
							radStube75ByVendor.setChecked(false);
							radSTube12515ByCompany.setChecked(false);
							radSTube12515ByVendor.setChecked(false);

							edtSurakshaTube125mm15Meter.setText(Constants.BLANK);
							edtSurakshaTube125mm15MeterChargeable.setText(Constants.BLANK);
							edtSurakshaTube75mm1Meter.setText(Constants.BLANK);
							edtSurakshaTube75mm1MeterChargeable.setText(Constants.BLANK);
							KitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.SUPPLY_VENDOR);
							KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.BLANK);
							KitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.BLANK);
							surakshaTube79mm15MeterOption = Constants.SUPPLY_VENDOR;
							surakshaTube75mm1MeterOption = Constants.BLANK;
							surakshaTube125mm15MeterOption = Constants.BLANK;
							checkSuppliedStatus(null);
							//}
							break;
						}

					}
	    });


		radGroupSurakshaTube125mm15Meter.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radSTube12515ByCompany:
					//if(radSTube12515ByCompany.isChecked()) {

					radSTube12515ByCompany.setChecked(true);
					radSTube12515ByVendor.setChecked(false);

					radStube75ByCompany.setChecked(false);
					radStube75ByVendor.setChecked(false);
					radStube7915ByCompany.setChecked(false);
					radStube7915ByVendor.setChecked(false);

					edtSurakshaTube75mm1Meter.setText(Constants.BLANK);
					edtSurakshaTube75mm1MeterChargeable.setText(Constants.BLANK);
					edtSurakshaTube79mm15Meter.setText(Constants.BLANK);
					edtSurakshaTube79mm15MeterChargeable.setText(Constants.BLANK);
					KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.SUPPLY_COMPANY);
					KitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.BLANK);
					KitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.BLANK);
					surakshaTube125mm15MeterOption = Constants.SUPPLY_COMPANY;
					surakshaTube75mm1MeterOption = Constants.BLANK;
					surakshaTube79mm15MeterOption = Constants.BLANK;
					checkSuppliedStatus(null);
					//}

					break;
				case R.id.radSTube12515ByVendor:
				//	if(radSTube12515ByVendor.isChecked()) {

					radSTube12515ByVendor.setChecked(true);
					radSTube12515ByCompany.setChecked(false);

					radStube7915ByCompany.setChecked(false);
					radStube7915ByVendor.setChecked(false);
					radStube75ByCompany.setChecked(false);
					radStube75ByVendor.setChecked(false);

					edtSurakshaTube75mm1Meter.setText(Constants.BLANK);
					edtSurakshaTube75mm1MeterChargeable.setText(Constants.BLANK);
					edtSurakshaTube79mm15Meter.setText(Constants.BLANK);
					edtSurakshaTube79mm15MeterChargeable.setText(Constants.BLANK);
					KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.SUPPLY_VENDOR);
					KitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.BLANK);
					KitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.BLANK);
					surakshaTube125mm15MeterOption = Constants.SUPPLY_VENDOR;
					surakshaTube75mm1MeterOption = Constants.BLANK;
					surakshaTube79mm15MeterOption = Constants.BLANK;
					checkSuppliedStatus(null);
					//}
					break;
				}

			}
		});*/


 /*radGroupSurakshaTube75mm1Meter.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.radStube75ByCompany:

					radStube75ByCompany.setChecked(true);
					radStube75ByVendor.setChecked(false);
					if(radStube75ByCompany.isChecked()) {



					if(radStube75ByVendor.isChecked()){

					}
					if(radStube7915ByCompany.isChecked()){
						radStube7915ByCompany.setChecked(false);
					}

					if(radStube7915ByVendor.isChecked()){
						radStube7915ByVendor.setChecked(false);
					}
					if(radStube7915ByVendor.isChecked()){
						radStube7915ByVendor.setChecked(false);
					}
					if(radSTube12515ByVendor.isChecked()){
						radSTube12515ByVendor.setChecked(false);
					}
					if(radSTube12515ByCompany.isChecked()){
						radSTube12515ByCompany.setChecked(false);
					}




					edtSurakshaTube125mm15Meter.setText(Constants.BLANK);
					edtSurakshaTube125mm15MeterChargeable.setText(Constants.BLANK);
					edtSurakshaTube79mm15Meter.setText(Constants.BLANK);
					edtSurakshaTube79mm15MeterChargeable.setText(Constants.BLANK);
					KitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.SUPPLY_COMPANY);
					KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.BLANK);
					KitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.BLANK);
					surakshaTube75mm1MeterOption = Constants.SUPPLY_COMPANY;
					surakshaTube79mm15MeterOption = Constants.BLANK;
					surakshaTube125mm15MeterOption = Constants.BLANK;
					checkSuppliedStatus(null);
					}
					break;
				case R.id.radStube75ByVendor:
					radStube75ByVendor.setChecked(true);
					radStube75ByCompany.setChecked(false);
					if(radStube75ByVendor.isChecked()) {



					if(radStube75ByCompany.isChecked()){

					}
					if(radStube7915ByCompany.isChecked()){
						radStube7915ByCompany.setChecked(false);
					}
					if(radStube7915ByVendor.isChecked()){
						radStube7915ByVendor.setChecked(false);
					}
					if(radSTube12515ByCompany.isChecked()){
						radSTube12515ByCompany.setChecked(false);
					}
					if(radSTube12515ByVendor.isChecked()){
						radSTube12515ByVendor.setChecked(false);
					}

					edtSurakshaTube125mm15Meter.setText(Constants.BLANK);
					edtSurakshaTube125mm15MeterChargeable.setText(Constants.BLANK);
					edtSurakshaTube79mm15Meter.setText(Constants.BLANK);
					edtSurakshaTube79mm15MeterChargeable.setText(Constants.BLANK);
					KitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.SUPPLY_VENDOR);
					KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.BLANK);
					KitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.BLANK);
					surakshaTube75mm1MeterOption = Constants.SUPPLY_VENDOR;
					surakshaTube79mm15MeterOption = Constants.BLANK;
					surakshaTube125mm15MeterOption = Constants.BLANK;
					checkSuppliedStatus(null);
					}
					break;
				}

			}
		});

		radGroupSurakshaTube79mm15Meter.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.radStube7915ByCompany:
							radStube7915ByCompany.setChecked(true);
							radStube7915ByVendor.setChecked(false);
							if(radStube7915ByCompany.isChecked()) {

							if(radStube7915ByVendor.isChecked()){
								radStube7915ByVendor.setChecked(false);
							}
							if(radStube75ByCompany.isChecked()){
								radStube75ByCompany.setChecked(false);
							}
							if(radStube75ByVendor.isChecked()){
								radStube75ByVendor.setChecked(false);
							}

							if(radSTube12515ByCompany.isChecked()){
								radSTube12515ByCompany.setChecked(false);
							}

							if(radSTube12515ByVendor.isChecked()){
								radSTube12515ByVendor.setChecked(false);
							}

							edtSurakshaTube125mm15Meter.setText(Constants.BLANK);
							edtSurakshaTube125mm15MeterChargeable.setText(Constants.BLANK);
							edtSurakshaTube75mm1Meter.setText(Constants.BLANK);
							edtSurakshaTube75mm1MeterChargeable.setText(Constants.BLANK);
							KitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.SUPPLY_COMPANY);
							KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.BLANK);
							KitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.BLANK);
							surakshaTube79mm15MeterOption = Constants.SUPPLY_COMPANY;
							surakshaTube75mm1MeterOption = Constants.BLANK;
							surakshaTube125mm15MeterOption = Constants.BLANK;
							checkSuppliedStatus(null);
							}
							break;
						case R.id.radStube7915ByVendor:
							radStube7915ByVendor.setChecked(true);
							radStube7915ByCompany.setChecked(false);
							if(radStube7915ByVendor.isChecked()) {


							if(radStube7915ByCompany.isChecked()){

							}
							if(radStube75ByCompany.isChecked()){
								radStube75ByCompany.setChecked(false);
							}
							if(radStube75ByVendor.isChecked()){
								radStube75ByVendor.setChecked(false);
							}
							if(radSTube12515ByCompany.isChecked()){
								radSTube12515ByCompany.setChecked(false);
							}
							if(radSTube12515ByVendor.isChecked()){
								radSTube12515ByVendor.setChecked(false);
							}

							edtSurakshaTube125mm15Meter.setText(Constants.BLANK);
							edtSurakshaTube125mm15MeterChargeable.setText(Constants.BLANK);
							edtSurakshaTube75mm1Meter.setText(Constants.BLANK);
							edtSurakshaTube75mm1MeterChargeable.setText(Constants.BLANK);
							KitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.SUPPLY_VENDOR);
							KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.BLANK);
							KitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.BLANK);
							surakshaTube79mm15MeterOption = Constants.SUPPLY_VENDOR;
							surakshaTube75mm1MeterOption = Constants.BLANK;
							surakshaTube125mm15MeterOption = Constants.BLANK;
							checkSuppliedStatus(null);
							}
							break;
						}

					}
	    });




		radGroupSurakshaTube125mm15Meter.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radSTube12515ByCompany:
					radSTube12515ByCompany.setChecked(true);
					radSTube12515ByVendor.setChecked(false);

					if(radSTube12515ByCompany.isChecked()) {

					if(radSTube12515ByVendor.isChecked()){

					}
					if(radStube75ByCompany.isChecked()){
						radStube75ByCompany.setChecked(false);
					}
					if(radStube75ByVendor.isChecked()){
						radStube75ByVendor.setChecked(false);
					}
					if(radStube7915ByCompany.isChecked()){
						radStube7915ByCompany.setChecked(false);
					}
					if(radStube7915ByVendor.isChecked()){
						radStube7915ByVendor.setChecked(false);
					}

					edtSurakshaTube75mm1Meter.setText(Constants.BLANK);
					edtSurakshaTube75mm1MeterChargeable.setText(Constants.BLANK);
					edtSurakshaTube79mm15Meter.setText(Constants.BLANK);
					edtSurakshaTube79mm15MeterChargeable.setText(Constants.BLANK);
					KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.SUPPLY_COMPANY);
					KitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.BLANK);
					KitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.BLANK);
					surakshaTube125mm15MeterOption = Constants.SUPPLY_COMPANY;
					surakshaTube75mm1MeterOption = Constants.BLANK;
					surakshaTube79mm15MeterOption = Constants.BLANK;
					checkSuppliedStatus(null);
					}

					break;
				case R.id.radSTube12515ByVendor:
					radSTube12515ByVendor.setChecked(true);
					radSTube12515ByCompany.setChecked(false);
					if(radSTube12515ByVendor.isChecked()) {


					if(radSTube12515ByCompany.isChecked()){

					}

					if(radStube75ByCompany.isChecked()){
						radStube75ByCompany.setChecked(false);
					}

					if(radStube75ByVendor.isChecked()){
						radStube75ByVendor.setChecked(false);
					}

					if(radStube7915ByCompany.isChecked()){
						radStube7915ByCompany.setChecked(false);
					}

					if(radStube7915ByVendor.isChecked()){
						radStube7915ByVendor.setChecked(false);
					}


					edtSurakshaTube75mm1Meter.setText(Constants.BLANK);
					edtSurakshaTube75mm1MeterChargeable.setText(Constants.BLANK);
					edtSurakshaTube79mm15Meter.setText(Constants.BLANK);
					edtSurakshaTube79mm15MeterChargeable.setText(Constants.BLANK);
					KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.SUPPLY_VENDOR);
					KitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.BLANK);
					KitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.BLANK);
					surakshaTube125mm15MeterOption = Constants.SUPPLY_VENDOR;
					surakshaTube75mm1MeterOption = Constants.BLANK;
					surakshaTube79mm15MeterOption = Constants.BLANK;
					checkSuppliedStatus(null);
					}
					break;
				}

			}
		});*/



		radGroupSurakshaTubeClampHose8mmPipe.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
					case R.id.radHose8mmPipeByCompany:
						radHose8mmPipeByCompany.setChecked(true);
						radHose8mmPipeByVendor.setChecked(false);
						KitchenSurakshaTubeVO.setKSplrClampHose8mmPipe(Constants.SUPPLY_COMPANY);
						surakshaTubeClampHose8mmPipeOption = Constants.SUPPLY_COMPANY;
						break;
					case R.id.radHose8mmPipeByVendor:
						radHose8mmPipeByCompany.setChecked(false);
						radHose8mmPipeByVendor.setChecked(true);
						KitchenSurakshaTubeVO.setKSplrClampHose8mmPipe(Constants.SUPPLY_VENDOR);
						surakshaTubeClampHose8mmPipeOption = Constants.SUPPLY_VENDOR;
						break;
				}
				checkSuppliedStatus(null);
			}
		});

		radGroupSurakshaTube20mmReplaced.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
					case R.id.radHose20mmPipeByCompany:
						radHose20mmPipeByCompany.setChecked(true);
						radHose20mmPipeByVendor.setChecked(false);
						KitchenSurakshaTubeVO.setKSplrClampHose20mmPipe(Constants.SUPPLY_COMPANY);
						surakshaTubeClampHose20mmPipeOption = Constants.SUPPLY_COMPANY;
						break;
					case R.id.radHose20mmPipeByVendor:
						radHose20mmPipeByCompany.setChecked(false);
						radHose20mmPipeByVendor.setChecked(true);
						KitchenSurakshaTubeVO.setKSplrClampHose20mmPipe(Constants.SUPPLY_VENDOR);
						surakshaTubeClampHose20mmPipeOption = Constants.SUPPLY_VENDOR;
						break;
				}
				checkSuppliedStatus(null);
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
							KitchenSurakshaTubeVO.setIsReplaced(dropdownOptions[whichButton]);
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

				Utility.changeSelectedForm(5, 4);
				Utility.changeList(5,false);
				OtherSurakshaTubeFragment otherSurakshaTubeFragment = OtherSurakshaTubeFragment.newInstance(custStatusId, contactNumber);
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.frmLayoutFragment, otherSurakshaTubeFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		});


		btnPrev.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Utility.changeSelectedForm(3, 4);
				Utility.changeList(3,false);
				RccFragment rccFragment = RccFragment.newInstance(custStatusId, contactNumber);
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.frmLayoutFragment, rccFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		});
		checkSuppliedStatus(null);
		return srkshaTbView;
	}

	@Override
	public void onResume() {
		super.onResume();
		initializeValuesSuraksha();
	}

	private void setSelectedMeterOption(RadioButton radioButton){
		if(radioButton == radStube75ByCompany){
			radStube75ByCompany.setChecked(true);
			radGroupSurakshaTube125mm15Meter.clearCheck();
			radGroupSurakshaTube79mm15Meter.clearCheck();

			edtSurakshaTube125mm15Meter.setText(Constants.BLANK);
			edtSurakshaTube125mm15MeterChargeable.setText(Constants.BLANK);
			edtSurakshaTube79mm15Meter.setText(Constants.BLANK);
			edtSurakshaTube79mm15MeterChargeable.setText(Constants.BLANK);
			KitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.SUPPLY_COMPANY);
			KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.BLANK);
			KitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.BLANK);
			surakshaTube75mm1MeterOption = Constants.SUPPLY_COMPANY;
			surakshaTube79mm15MeterOption = Constants.BLANK;
			surakshaTube125mm15MeterOption = Constants.BLANK;
			checkSuppliedStatus(null);

		}else if(radioButton == radStube75ByVendor){
			radStube75ByVendor.setChecked(true);

			radGroupSurakshaTube125mm15Meter.clearCheck();
			radGroupSurakshaTube79mm15Meter.clearCheck();

			edtSurakshaTube125mm15Meter.setText(Constants.BLANK);
			edtSurakshaTube125mm15MeterChargeable.setText(Constants.BLANK);
			edtSurakshaTube79mm15Meter.setText(Constants.BLANK);
			edtSurakshaTube79mm15MeterChargeable.setText(Constants.BLANK);
			KitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.SUPPLY_VENDOR);
			KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.BLANK);
			KitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.BLANK);
			surakshaTube75mm1MeterOption = Constants.SUPPLY_VENDOR;
			surakshaTube79mm15MeterOption = Constants.BLANK;
			surakshaTube125mm15MeterOption = Constants.BLANK;
			checkSuppliedStatus(null);

		}else if(radioButton == radStube7915ByCompany){
			radStube7915ByCompany.setChecked(true);

			radGroupSurakshaTube125mm15Meter.clearCheck();
			radGroupSurakshaTube75mm1Meter.clearCheck();

			edtSurakshaTube125mm15Meter.setText(Constants.BLANK);
			edtSurakshaTube125mm15MeterChargeable.setText(Constants.BLANK);
			edtSurakshaTube75mm1Meter.setText(Constants.BLANK);
			edtSurakshaTube75mm1MeterChargeable.setText(Constants.BLANK);
			KitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.SUPPLY_COMPANY);
			KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.BLANK);
			KitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.BLANK);
			surakshaTube79mm15MeterOption = Constants.SUPPLY_COMPANY;
			surakshaTube75mm1MeterOption = Constants.BLANK;
			surakshaTube125mm15MeterOption = Constants.BLANK;
			checkSuppliedStatus(null);

		}else if(radioButton == radStube7915ByVendor){
			radStube7915ByVendor.setChecked(true);

			radGroupSurakshaTube125mm15Meter.clearCheck();
			radGroupSurakshaTube75mm1Meter.clearCheck();

			edtSurakshaTube125mm15Meter.setText(Constants.BLANK);
			edtSurakshaTube125mm15MeterChargeable.setText(Constants.BLANK);
			edtSurakshaTube75mm1Meter.setText(Constants.BLANK);
			edtSurakshaTube75mm1MeterChargeable.setText(Constants.BLANK);
			KitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.SUPPLY_VENDOR);
			KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.BLANK);
			KitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.BLANK);
			surakshaTube79mm15MeterOption = Constants.SUPPLY_VENDOR;
			surakshaTube75mm1MeterOption = Constants.BLANK;
			surakshaTube125mm15MeterOption = Constants.BLANK;
			checkSuppliedStatus(null);

		}else if(radioButton == radSTube12515ByCompany){

			radSTube12515ByCompany.setChecked(true);

			radGroupSurakshaTube79mm15Meter.clearCheck();
			radGroupSurakshaTube75mm1Meter.clearCheck();

			edtSurakshaTube75mm1Meter.setText(Constants.BLANK);
			edtSurakshaTube75mm1MeterChargeable.setText(Constants.BLANK);
			edtSurakshaTube79mm15Meter.setText(Constants.BLANK);
			edtSurakshaTube79mm15MeterChargeable.setText(Constants.BLANK);
			KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.SUPPLY_COMPANY);
			KitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.BLANK);
			KitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.BLANK);
			surakshaTube125mm15MeterOption = Constants.SUPPLY_COMPANY;
			surakshaTube75mm1MeterOption = Constants.BLANK;
			surakshaTube79mm15MeterOption = Constants.BLANK;
			checkSuppliedStatus(null);
		}else if(radioButton == radSTube12515ByVendor){

			radSTube12515ByVendor.setChecked(true);

			radGroupSurakshaTube79mm15Meter.clearCheck();
			radGroupSurakshaTube75mm1Meter.clearCheck();

			edtSurakshaTube75mm1Meter.setText(Constants.BLANK);
			edtSurakshaTube75mm1MeterChargeable.setText(Constants.BLANK);
			edtSurakshaTube79mm15Meter.setText(Constants.BLANK);
			edtSurakshaTube79mm15MeterChargeable.setText(Constants.BLANK);
			KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(Constants.SUPPLY_VENDOR);
			KitchenSurakshaTubeVO.setKSplr15Meter79mm(Constants.BLANK);
			KitchenSurakshaTubeVO.setKSplr1Meter75mm(Constants.BLANK);
			surakshaTube125mm15MeterOption = Constants.SUPPLY_VENDOR;
			surakshaTube75mm1MeterOption = Constants.BLANK;
			surakshaTube79mm15MeterOption = Constants.BLANK;
			checkSuppliedStatus(null);
		}
	}

	public void checkSuppliedStatus(CompoundButton buttonView) {
		if(buttonView != null) {
			if(buttonView.isChecked()) {

				switch (buttonView.getId()) {
					case R.id.radStube75ByCompany:
					case R.id.radStube75ByVendor:

						edtSurakshaTube75mm1Meter.setEnabled(true);
						edtSurakshaTube75mm1MeterChargeable.setEnabled(true);
						edtSurakshaTube79mm15Meter.setEnabled(false);
						edtSurakshaTube79mm15MeterChargeable.setEnabled(false);
						edtSurakshaTube125mm15Meter.setEnabled(false);
						edtSurakshaTube125mm15MeterChargeable.setEnabled(false);

						radStube7915ByCompany.setChecked(false);
						radStube7915ByVendor.setChecked(false);
						radSTube12515ByCompany.setChecked(false);
						radSTube12515ByVendor.setChecked(false);
						edtSurakshaTube125mm15Meter.setText(Constants.BLANK);
						edtSurakshaTube125mm15MeterChargeable.setText(Constants.BLANK);
						edtSurakshaTube79mm15Meter.setText(Constants.BLANK);
						edtSurakshaTube79mm15MeterChargeable.setText(Constants.BLANK);

						break;


					case R.id.radStube7915ByCompany:
					case R.id.radStube7915ByVendor:

						edtSurakshaTube79mm15Meter.setEnabled(true);
						edtSurakshaTube79mm15MeterChargeable.setEnabled(true);
						edtSurakshaTube125mm15Meter.setEnabled(false);
						edtSurakshaTube125mm15MeterChargeable.setEnabled(false);
						edtSurakshaTube75mm1Meter.setEnabled(false);
						edtSurakshaTube75mm1MeterChargeable.setEnabled(false);


						radStube75ByCompany.setChecked(false);
						radStube75ByVendor.setChecked(false);
						radSTube12515ByCompany.setChecked(false);
						radSTube12515ByVendor.setChecked(false);
						edtSurakshaTube75mm1Meter.setText(Constants.BLANK);
						edtSurakshaTube75mm1MeterChargeable.setText(Constants.BLANK);
						edtSurakshaTube125mm15Meter.setText(Constants.BLANK);
						edtSurakshaTube125mm15MeterChargeable.setText(Constants.BLANK);
						break;


					case R.id.radSTube12515ByCompany:
					case R.id.radSTube12515ByVendor:
						edtSurakshaTube125mm15Meter.setEnabled(true);
						edtSurakshaTube125mm15MeterChargeable.setEnabled(true);
						edtSurakshaTube75mm1Meter.setEnabled(false);
						edtSurakshaTube75mm1MeterChargeable.setEnabled(false);
						edtSurakshaTube79mm15Meter.setEnabled(false);
						edtSurakshaTube79mm15MeterChargeable.setEnabled(false);


						radStube75ByCompany.setChecked(false);
						radStube75ByVendor.setChecked(false);
						radStube7915ByCompany.setChecked(false);
						radStube7915ByVendor.setChecked(false);
						edtSurakshaTube75mm1Meter.setText(Constants.BLANK);
						edtSurakshaTube75mm1MeterChargeable.setText(Constants.BLANK);
						edtSurakshaTube79mm15Meter.setText(Constants.BLANK);
						edtSurakshaTube79mm15MeterChargeable.setText(Constants.BLANK);
						break;


					default:
						break;
				}
			}
		} else {
			if(radStube75ByCompany.isChecked() || radStube75ByVendor.isChecked()) {
				Log.e(getClass().getName(), "******************Change Listener called: Half Clamp");
				edtSurakshaTube75mm1Meter.setEnabled(true);
				edtSurakshaTube75mm1MeterChargeable.setEnabled(true);
				edtSurakshaTube75mm1Meter.setFocusable(true);
				edtSurakshaTube75mm1MeterChargeable.setFocusable(true);
			/*radStube7915ByCompany.setChecked(false);
			radStube7915ByVendor.setChecked(false);
			radSTube12515ByCompany.setChecked(false);
			radSTube12515ByVendor.setChecked(false);
			edtSurakshaTube125mm15Meter.setText(Constants.BLANK);
			edtSurakshaTube125mm15MeterChargeable.setText(Constants.BLANK);
			edtSurakshaTube79mm15Meter.setText(Constants.BLANK);
			edtSurakshaTube79mm15MeterChargeable.setText(Constants.BLANK);*/
			} else {
				edtSurakshaTube75mm1Meter.setEnabled(false);
				edtSurakshaTube75mm1MeterChargeable.setEnabled(false);
			/*edtSurakshaTube75mm1Meter.setText(Constants.BLANK);
			edtSurakshaTube75mm1MeterChargeable.setText(Constants.BLANK);*/
			}

			if(radStube7915ByCompany.isChecked() || radStube7915ByVendor.isChecked()) {
				edtSurakshaTube79mm15Meter.setEnabled(true);
				edtSurakshaTube79mm15MeterChargeable.setEnabled(true);
				edtSurakshaTube79mm15Meter.setFocusable(true);
				edtSurakshaTube79mm15MeterChargeable.setFocusable(true);

			/*radStube75ByCompany.setChecked(false);
			radStube75ByVendor.setChecked(false);
			radSTube12515ByCompany.setChecked(false);
			radSTube12515ByVendor.setChecked(false);
			edtSurakshaTube75mm1Meter.setText(Constants.BLANK);
			edtSurakshaTube75mm1MeterChargeable.setText(Constants.BLANK);
			edtSurakshaTube125mm15Meter.setText(Constants.BLANK);
			edtSurakshaTube125mm15MeterChargeable.setText(Constants.BLANK);*/
			} else {
				edtSurakshaTube79mm15Meter.setEnabled(false);
				edtSurakshaTube79mm15MeterChargeable.setEnabled(false);

			/*edtSurakshaTube79mm15Meter.setText(Constants.BLANK);
			edtSurakshaTube79mm15MeterChargeable.setText(Constants.BLANK);*/
			}

			if(radSTube12515ByCompany.isChecked() || radSTube12515ByVendor.isChecked()) {
				edtSurakshaTube125mm15Meter.setEnabled(true);
				edtSurakshaTube125mm15MeterChargeable.setEnabled(true);
				edtSurakshaTube125mm15Meter.setFocusable(true);
				edtSurakshaTube125mm15MeterChargeable.setFocusable(true);

		/*	radStube75ByCompany.setChecked(false);
			radStube75ByVendor.setChecked(false);
			radStube7915ByCompany.setChecked(false);
			radStube7915ByVendor.setChecked(false);
			edtSurakshaTube75mm1Meter.setText(Constants.BLANK);
			edtSurakshaTube75mm1MeterChargeable.setText(Constants.BLANK);
			edtSurakshaTube79mm15Meter.setText(Constants.BLANK);
			edtSurakshaTube79mm15MeterChargeable.setText(Constants.BLANK);*/
			} else {
				edtSurakshaTube125mm15Meter.setEnabled(false);
				edtSurakshaTube125mm15MeterChargeable.setEnabled(false);

			/*edtSurakshaTube125mm15Meter.setText(Constants.BLANK);
			edtSurakshaTube125mm15MeterChargeable.setText(Constants.BLANK);*/
			}

			if(radHose20mmPipeByCompany.isChecked() || radHose20mmPipeByVendor.isChecked()) {
				edtSurakshaTubeClampHose20mmPipe.setEnabled(true);
				edtSurakshaTubeClampHose20mmPipeChargeable.setEnabled(true);
				edtSurakshaTubeClampHose20mmPipe.setFocusable(true);
				edtSurakshaTubeClampHose20mmPipeChargeable.setFocusable(true);
			} else  {
				edtSurakshaTubeClampHose20mmPipe.setEnabled(false);
				edtSurakshaTubeClampHose20mmPipeChargeable.setEnabled(false);
			}

			if(radHose8mmPipeByCompany.isChecked() || radHose8mmPipeByVendor.isChecked()) {
				edtSurakshaTubeClampHose8mmPipe.setEnabled(true);
				edtSurakshaTubeClampHose8mmPipeChargeable.setEnabled(true);
				edtSurakshaTubeClampHose8mmPipe.setFocusable(true);
				edtSurakshaTubeClampHose8mmPipeChargeable.setFocusable(true);
			} else {
				edtSurakshaTubeClampHose8mmPipe.setEnabled(false);
				edtSurakshaTubeClampHose8mmPipeChargeable.setEnabled(false);
			}
		}
	}
	/**
	 * Method to disable all controls when open form in edit mode.
	 * @return void
	 */
	public void surkshatubeDisableViews(){
		imgBtnExpiryDate.setEnabled(false);
		imgBtnReplacedExpiryDate.setEnabled(false);
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

		if(txtExpiryDate.getText().toString().equals(Constants.BLANK)) {
			errorList.add(true);
		} else {
			errorList.add(false);
		}

		if(btnIsReplaced.getText().toString().equals(getResources().getString(R.string.title_select_option))){
			errorList.add(true);
			errTxtReplaced.setText(Constants.ERROR_MARK);
		} else {
			errorList.add(false);
			errTxtReplaced.setText(Constants.ERROR_MARK);
//			errTxtReplaced.setText(Constants.LABEL_BLANK);
		}

		if(btnIsReplaced.getText().toString().equalsIgnoreCase("yes")) {
			errExpiryDate.setText("*");
			if(radStube75ByCompany.isChecked() || radStube75ByVendor.isChecked()) {
				if(edtSurakshaTube75mm1Meter.getText().toString().equals(Constants.BLANK) && edtSurakshaTube75mm1MeterChargeable.getText().toString().equals(Constants.BLANK)) {
					errorList.add(true);
				} else {
					errorList.add(false);
				}
			} else {
				errorList.add(false);
			}

			if(radStube7915ByCompany.isChecked() || radStube7915ByVendor.isChecked()) {
				if(edtSurakshaTube79mm15Meter.getText().toString().equals(Constants.BLANK) && edtSurakshaTube79mm15MeterChargeable.getText().toString().equals(Constants.BLANK)) {
					errorList.add(true);
				} else {
					errorList.add(false);
				}
			} else {
				errorList.add(false);
			}

			if(radSTube12515ByCompany.isChecked() || radSTube12515ByVendor.isChecked()) {
				if(edtSurakshaTube125mm15Meter.getText().toString().equals(Constants.BLANK) && edtSurakshaTube125mm15MeterChargeable.getText().toString().equals(Constants.BLANK)) {
					errorList.add(true);
				} else {
					errorList.add(false);
				}
			} else {
				errorList.add(false);
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

			if(txtRelpacedExpiryDate.getText().toString().equals(Constants.BLANK)) {
				errorList.add(true);
			} else {
				errorList.add(false);
			}
			/*if(edtSurakshaTube75mm1Meter.getText().toString().equals(Constants.BLANK) && 
					edtSurakshaTube75mm1MeterChargeable.getText().toString().equals(Constants.BLANK) &&
					edtSurakshaTube125mm15Meter.getText().toString().equals(Constants.BLANK) &&
					edtSurakshaTube125mm15MeterChargeable.getText().toString().equals(Constants.BLANK) &&
					edtSurakshaTube79mm15Meter.getText().toString().equals(Constants.BLANK) &&
					edtSurakshaTube79mm15MeterChargeable.getText().toString().equals(Constants.BLANK)) {
				errorList.add(true);
			} else {
				errorList.add(false);
			}
			
			if(edtSurakshaTubeClampHose8mmPipe.getText().toString().equals(Constants.BLANK) || 
					edtSurakshaTubeClampHose8mmPipeChargeable.getText().toString().equals(Constants.BLANK) ||
					edtSurakshaTubeClampHose20mmPipe.getText().toString().equals(Constants.BLANK) ||
					edtSurakshaTubeClampHose20mmPipeChargeable.getText().toString().equals(Constants.BLANK)) {
				errorList.add(true);
			}  else {
				errorList.add(false);
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
			}  else {
				errorList.add(false);
			}*/
			if(selectedDate != null && !selectedDate.equals(Constants.BLANK)) {
				txtExpiryDate.setText(selectedDate);
				if (isPastDate(selectedDate)) {
					errExpiryDate.setText("Past date is not allowed");
					errorList.add(true);
				} else {
					errExpiryDate.setText("*");
					errorList.add(false);
				}
			} else {
				errExpiryDate.setText("*");
				errorList.add(false);
			}
		}

		Collections.sort(errorList);
		System.err.println(" Correction Error --- "+ Collections.binarySearch(errorList,true));
		if(Collections.binarySearch(errorList,true) >= 0)
			//Utility.changeList(4,false);
			Utility.changeList(4,true);
		else
			Utility.changeList(4,false);
	}

	public boolean isPastDate(String dateString) {

		SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, Integer.parseInt(dateString.substring(0, 2)) - 1);
		calendar.set(Calendar.YEAR, Integer.parseInt(dateString.substring(2, dateString.length())));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 30);

		/*if(calendar.get(Calendar.MONTH) == Calendar.FEBRUARY) {
			calendar.set(Calendar.DAY_OF_MONTH, 28);
		} else {
			calendar.set(Calendar.DAY_OF_MONTH, 30);
		}*/
		Date date = calendar.getTime();
		Calendar currentCal = Calendar.getInstance();
		currentCal.set(Calendar.HOUR_OF_DAY, 0);
		currentCal.set(Calendar.MINUTE, 0);
		currentCal.set(Calendar.SECOND, 0);
		currentCal.set(Calendar.MILLISECOND, 0);
		currentCal.set(Calendar.DAY_OF_MONTH, 30);
		Date currentDate = currentCal.getTime();
		if(date.before(currentDate)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Method used to change label values as per parameter to English and Gujarati.
	 * @param id : 1 - English , 2 - Gujarati
	 */
	public void changeLanguage(int id){

		id = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.PREF_LANGUAGE,Constants.LANGUAGE_ENGLISH);

		switch (id){
			case Constants.LANGUAGE_ENGLISH:
				lblExpiryDate.setText(getResources().getString(R.string.title_expiry_date_Eng));
				lblExpiryReplacedDate.setText(getResources().getString(R.string.title_expiry_date_of_replaced_rubber_tube_Eng));
				lblsurakshaTubeReplaced.setText(getResources().getString(R.string.title_surakshaTube_Replaced_Eng));
				lblSurakshaTube75mm1Meter.setText(getResources().getString(R.string.title_surakshaTube_79mm_1_meter_Eng));
				lblSurakshaTube79mm15Meter.setText(getResources().getString(R.string.title_surakshaTube_79mm_15_Meter_Eng));
				lblSurakshaTube125mm15Meter.setText(getResources().getString(R.string.title_surakshaTube_125mm_15_Meter_Eng));
				lblSurakshaTubeClampHose8mmPipe.setText(getResources().getString(R.string.title_surakshaTube_ClampHose_8mm_Pipe_Eng));
				lblSurakshaTubeClampHose20mmpipe.setText(getResources().getString(R.string.title_surakshaTube_ClampHose_20mm_pipe_Eng));
				lblSurakshaHeader.setText(getResources().getText(R.string.header_kitchen_suraksha_tube_Eng));
				txtVendorSupplied.setText(getResources().getText(R.string.lbl_by_supply_vendor_Eng));
				txtCompanySupplied.setText(getResources().getText(R.string.lbl_by_supply_company_Eng));
				txtNoChargableQty.setText(getResources().getText(R.string.lbl_non_chargable_qty_Eng));
				txtChargableQty.setText(getResources().getText(R.string.lbl_chargable_qty_Eng));
				break;

			case Constants.LANGUAGE_GUJRATI:
				lblSurakshaHeader.setTypeface(Global.typeface_Guj);
				lblExpiryDate.setTypeface(Global.typeface_Guj);
				lblExpiryReplacedDate.setTypeface(Global.typeface_Guj);
				lblsurakshaTubeReplaced.setTypeface(Global.typeface_Guj);
				lblSurakshaTube75mm1Meter.setTypeface(Global.typeface_Guj);
				lblSurakshaTube79mm15Meter.setTypeface(Global.typeface_Guj);
				lblSurakshaTube125mm15Meter.setTypeface(Global.typeface_Guj);
				lblSurakshaTubeClampHose8mmPipe.setTypeface(Global.typeface_Guj);
				lblSurakshaTubeClampHose20mmpipe.setTypeface(Global.typeface_Guj);

				txtVendorSupplied.setTypeface(Global.typeface_Guj);
				txtCompanySupplied.setTypeface(Global.typeface_Guj);
				txtNoChargableQty.setTypeface(Global.typeface_Guj);
				txtChargableQty.setTypeface(Global.typeface_Guj);

				txtVendorSupplied2.setTypeface(Global.typeface_Guj);
				txtCompanySupplied2.setTypeface(Global.typeface_Guj);
				txtNoChargableQty2.setTypeface(Global.typeface_Guj);
				txtChargableQty2.setTypeface(Global.typeface_Guj);

				lblExpiryDate.setText(getResources().getString(R.string.title_expiry_date_Guj));
				lblExpiryReplacedDate.setText(getResources().getString(R.string.title_expiry_date_of_replaced_rubber_tube_Guj));
				lblsurakshaTubeReplaced.setText(getResources().getString(R.string.title_surakshaTube_Replaced_Guj));
				lblSurakshaTube75mm1Meter.setText(getResources().getString(R.string.title_surakshaTube_79mm_1_meter_Guj));
				lblSurakshaTube79mm15Meter.setText(getResources().getString(R.string.title_surakshaTube_79mm_15_Meter_Guj));
				lblSurakshaTube125mm15Meter.setText(getResources().getString(R.string.title_surakshaTube_125mm_15_Meter_Guj));
				lblSurakshaTubeClampHose8mmPipe.setText(getResources().getString(R.string.title_surakshaTube_ClampHose_8mm_Pipe_Guj));
				lblSurakshaTubeClampHose20mmpipe.setText(getResources().getString(R.string.title_surakshaTube_ClampHose_20mm_pipe_Guj));
				lblSurakshaHeader.setText(getResources().getText(R.string.header_kitchen_suraksha_tube_Guj));

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

		KitchenSurakshaTubeVO.setExpirydate(KitchenSurakshaTubeVO.getExpirydate().equals("")?Global.expiryDate:KitchenSurakshaTubeVO.getExpirydate());

		errSurakshaTube75mm1Meter.setText(Utility.surakshaTube(Constants.SIZE1,edtSurakshaTube75mm1MeterChargeable.getText().toString(),getActivity()));
		errSurakshaTube79mm15Meter.setText(Utility.surakshaTube(Constants.SIZE2,edtSurakshaTube79mm15MeterChargeable.getText().toString(),getActivity()));
		errSurakshaTube125mm15Meter.setText(Utility.surakshaTube(Constants.SIZE3,edtSurakshaTube125mm15MeterChargeable.getText().toString(),getActivity()));
		errSurakshaTubeClampHose8mmPipe.setText(Utility.surakshaTube(Constants.SIZE4,edtSurakshaTubeClampHose8mmPipeChargeable.getText().toString(),getActivity()));
		errSurakshaTubeClampHose20mmPipe.setText(Utility.surakshaTube(Constants.SIZE5,edtSurakshaTubeClampHose20mmPipeChargeable.getText().toString(),getActivity()));

		surakshaTubeoption = KitchenSurakshaTubeVO.getIsReplaced();
		surakshaTube75mm1MeterOption = KitchenSurakshaTubeVO.getKSplr1Meter75mm();
		surakshaTube79mm15MeterOption = KitchenSurakshaTubeVO.getKSplr15Meter79mm();
		surakshaTube125mm15MeterOption = KitchenSurakshaTubeVO.getKSplrChrg15Meter125mm();
		surakshaTubeClampHose8mmPipeOption = KitchenSurakshaTubeVO.getKSplrClampHose8mmPipe();
		surakshaTubeClampHose20mmPipeOption = KitchenSurakshaTubeVO.getKSplrClampHose20mmPipe();

		if(surakshaTube75mm1MeterOption.equals(Constants.LABEL_BLANK)){
			radStube75ByCompany.setChecked(false);
			radStube75ByVendor.setChecked(false);

		}else if(surakshaTube75mm1MeterOption.equals(Constants.SUPPLY_COMPANY)){
			radStube75ByCompany.setChecked(true);
			radStube75ByVendor.setChecked(false);
		}else if(surakshaTube75mm1MeterOption.equals(Constants.SUPPLY_VENDOR)){
			radStube75ByCompany.setChecked(false);
			radStube75ByVendor.setChecked(true);
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

		txtExpiryDate.setText(KitchenSurakshaTubeVO.getExpirydate().equals("0000")?Constants.BLANK:KitchenSurakshaTubeVO.getExpirydate());
		selectedDate = txtExpiryDate.getText().toString();
		txtRelpacedExpiryDate.setText(KitchenSurakshaTubeVO.getReplaceexpirydate());

		edtSurakshaTube75mm1Meter.setText(KitchenSurakshaTubeVO.getSize751nc());
		edtSurakshaTube75mm1MeterChargeable.setText(KitchenSurakshaTubeVO.getSize751c());
		edtSurakshaTube79mm15Meter.setText(KitchenSurakshaTubeVO.getSize7915nc());
		edtSurakshaTube79mm15MeterChargeable.setText(KitchenSurakshaTubeVO.getSize7915c());
		edtSurakshaTube125mm15Meter.setText(KitchenSurakshaTubeVO.getSize12515nc());
		edtSurakshaTube125mm15MeterChargeable.setText(KitchenSurakshaTubeVO.getSize12515c());
		edtSurakshaTubeClampHose8mmPipe.setText(KitchenSurakshaTubeVO.getClampsize8nc());
		edtSurakshaTubeClampHose8mmPipeChargeable.setText(KitchenSurakshaTubeVO.getClampsize8c());
		edtSurakshaTubeClampHose20mmPipe.setText(KitchenSurakshaTubeVO.getClampsize20nc());
		edtSurakshaTubeClampHose20mmPipeChargeable.setText(KitchenSurakshaTubeVO.getClampsize20c());


//		edtSize1.setText(SurakshaTubeVO.getSize1());
//		edtSize2.setText(SurakshaTubeVO.getSize2());		    
//		edtSize3.setText(SurakshaTubeVO.getSize3());  
//		edtSize4.setText(SurakshaTubeVO.getClampSize1());
//		edtSize5.setText(SurakshaTubeVO.getClamSize2());

		validateButton();
		checkSuppliedStatus(null);

	}

	/**
	 * Method is used to show dialog for displaying date.
	 *
	 *
	 */
	public void showExpiryDateDialog() {

		FragmentTransaction fragTransaction = getFragmentManager().beginTransaction(); // get the fragment
		dateFragment = SurakshaTubeDateDialogFragment.newInstance(getActivity(),new DateDialogFragmentListener() {

			public void updateChangedDate(int year, int month, int day) { //change month+1

				int tempMonth = month + 1;
				String months = Constants.LABEL_BLANK;
				if(tempMonth <10){
					months = "0"+tempMonth;
				}else{
					months = String.valueOf(tempMonth);
				}
				Global.expiryDate = Constants.LABEL_BLANK + months + Constants.LABEL_BLANK + year;
				txtExpiryDate.setText(Global.expiryDate);
				selectedDate = Global.expiryDate;
				KitchenSurakshaTubeVO.setExpirydate(Global.expiryDate);
				validateButton();
				cal.set(year, month, day);
			}
		}, cal);
		dateFragment.show(fragTransaction, Constants.DATE_DIALOG_FRAGMENT);
	}

	/**
	 * Method is used to show dialog for displaying date.
	 *
	 *
	 */
	public void showReplaceExpiryDateDialog() {

		FragmentTransaction fragTransaction = getFragmentManager().beginTransaction(); // get the fragment
		dateFragment = SurakshaTubeDateDialogFragment.newInstance(getActivity(),new DateDialogFragmentListener() {

			public void updateChangedDate(int year, int month, int day) { //change month+1
				int tempMonth = month + 1;
				String months = Constants.LABEL_BLANK;
				if(tempMonth < 10){
					months = "0"+ tempMonth;
				}else{
					months = String.valueOf(tempMonth);
				}
				txtRelpacedExpiryDate.setText(""+months+""+year);
				validateButton();
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
	public interface DateDialogFragmentListeners {

		public void updateChangedDate(int year, int month, int day);
	}

	@Override
	public void onPause() {
		super.onPause();
		try{
			if("OP".equals(TestingFragment.testingStatusCode)  || "HCL1".equals(TestingFragment.testingStatusCode))
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
			txtExpiryDate.setText(Constants.LABEL_BLANK);
			txtRelpacedExpiryDate.setText(Constants.LABEL_BLANK);

			surakshaTube75mm1MeterOption = Constants.LABEL_BLANK;
			surakshaTube79mm15MeterOption = Constants.LABEL_BLANK;
			surakshaTube125mm15MeterOption = Constants.LABEL_BLANK;
			surakshaTubeClampHose8mmPipeOption = Constants.LABEL_BLANK;
			surakshaTubeClampHose20mmPipeOption =Constants.LABEL_BLANK;
			
			/*if(radBtnNo != null)
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
				radSTube12515ByVendor.setChecked(false);
			
			if(radStube75ByCompany != null)
				radStube75ByCompany.setChecked(false);
			if(radStube75ByVendor != null)
				radStube75ByVendor.setChecked(false);
			
			if(radStube7915ByCompany != null)
				radStube7915ByCompany.setChecked(false);
			if(radStube7915ByVendor != null)
				radStube7915ByVendor.setChecked(false);*/
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setValues()
	{
		try{
			KitchenSurakshaTubeVO.setExpirydate(txtExpiryDate.getText().toString());
			KitchenSurakshaTubeVO.setReplaceexpirydate(txtRelpacedExpiryDate.getText().toString());
			KitchenSurakshaTubeVO.setSize751c(edtSurakshaTube75mm1MeterChargeable.getText().toString());
			KitchenSurakshaTubeVO.setSize751nc(edtSurakshaTube75mm1Meter.getText().toString());
			KitchenSurakshaTubeVO.setSize7915c(edtSurakshaTube79mm15MeterChargeable.getText().toString());
			KitchenSurakshaTubeVO.setSize7915nc(edtSurakshaTube79mm15Meter.getText().toString());
			KitchenSurakshaTubeVO.setSize12515c(edtSurakshaTube125mm15MeterChargeable.getText().toString());
			KitchenSurakshaTubeVO.setSize12515nc(edtSurakshaTube125mm15Meter.getText().toString());
			KitchenSurakshaTubeVO.setClampsize8c(edtSurakshaTubeClampHose8mmPipeChargeable.getText().toString());
			KitchenSurakshaTubeVO.setClampsize8nc(edtSurakshaTubeClampHose8mmPipe.getText().toString());
			KitchenSurakshaTubeVO.setClampsize20c(edtSurakshaTubeClampHose20mmPipeChargeable.getText().toString());
			KitchenSurakshaTubeVO.setClampsize20nc(edtSurakshaTubeClampHose20mmPipe.getText().toString());

			KitchenSurakshaTubeVO.setKSplr1Meter75mm(surakshaTube75mm1MeterOption);
			KitchenSurakshaTubeVO.setKSplr15Meter79mm(surakshaTube79mm15MeterOption);
			KitchenSurakshaTubeVO.setKSplrChrg15Meter125mm(surakshaTube125mm15MeterOption);
			KitchenSurakshaTubeVO.setKSplrClampHose8mmPipe(surakshaTubeClampHose8mmPipeOption);
			KitchenSurakshaTubeVO.setKSplrClampHose20mmPipe(surakshaTubeClampHose20mmPipeOption);

			clearValues();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == radStube75ByCompany){
			setSelectedMeterOption(radStube75ByCompany);
		}else if(v == radStube75ByVendor){
			setSelectedMeterOption(radStube75ByVendor);
		}else if(v == radStube7915ByCompany){
			setSelectedMeterOption(radStube7915ByCompany);
		}else if(v == radStube7915ByVendor){
			setSelectedMeterOption(radStube7915ByVendor);
		}else if(v == radSTube12515ByCompany){
			setSelectedMeterOption(radSTube12515ByCompany);
		}else if(v == radSTube12515ByVendor){
			setSelectedMeterOption(radSTube12515ByVendor);
		}
		setClickable();
	}

	private void setClickable(){
		radStube75ByVendor.setClickable(true);
		radStube7915ByCompany.setClickable(true);
		radStube7915ByVendor.setClickable(true);
		radSTube12515ByCompany.setClickable(true);
		radSTube12515ByVendor.setClickable(true);
	}
}
