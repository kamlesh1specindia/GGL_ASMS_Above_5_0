package com.spec.asms.view.fragment;

import java.util.ArrayList;
import java.util.Collections;

import org.w3c.dom.Text;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import com.spec.asms.vo.OtherChecksVO;


/**
 * This Fragment contain Different fields related to OtherCheck
 * Fragment
 */
public class OtherChecksFragment extends Fragment
{
	private View ohrChkView;
	// -Start- Added By Pankit Mistry
	//private EditText edtSupplyBush;
	// -End-
	private EditText edtRubberCap;
	private EditText edtBrassBall;

	private Button btnSaveNnext;
	private Button btnPrev;
	private Button btnIsMeterOK;
	private Button btnMeterPerformance;
	private Button btnMeterRedable;
	private Button btnGiInsideBase;
	private Button btnPVCSleeve;

	private int meterPerformance = -1;
	private int meterRedable = -1;
	private int GiInsideBase = -1;

	private int id;
	private int custStatusID;
	private String contactNumber;
	private EditText edtBrassBallCock;
	private EditText edtMeterReading;

	private TextView lblRubberCap;
	private TextView lblBrassBall;
	private TextView lblBrassBallCock;
	private TextView lblMeterPerformance;
	private TextView lblMeterReadable;
	private TextView lblGiInsideBaseMent;
	private TextView lblotherCheckHeader;
	private TextView errTxtMtrPrformce;
	private TextView errTxtMtrRdble;
	private TextView errTxtMtrInside;
	private TextView errOtherChecksRubberCap;
	private TextView errOtherChecksBrassBall;
	private TextView errOtherChecksBrassBallCock;
	private TextView errOtherChecksIsolationBallValue;
	//private TextView errOtherCheckssupplybush;

	//private TextView lblotherchecksupplyofbush;
	private TextView lblOtherChecksIsolationBallValue;
	private TextView lblOtherChecksIsInstallationPVCSleeve;
	// -Start- Added By Pankit Mistry
	private TextView errTxtPVCSleeve;
	// -End-
	private TextView lblIsMeter;
	private TextView lblMeterReading;
	private String strMeterPerformance;
	private String strIsMeter;
	private String strMeterRedable;
	private String strGiInsideBase;
	private String strPVCSleeve;

	private TextView txtVendorSupplied;
	private TextView txtCompanySupplied;
	private TextView txtNoChargableQty;
	private TextView txtChargableQty;

	private FragmentManager fragmentManager;
	public static StringBuffer errorMsgOtherChecks = new StringBuffer();
	private String user = Constants.LABEL_BLANK;

	private RadioGroup radGrpIsMeter;
	private RadioButton radBtnIsMeterOk,radBtnIsMeterNotOk;

	private RadioGroup radGroupMeterPerformance;
	private RadioButton radBtnOk,radBtnNotOk;

	private RadioGroup radGroupOtherChecksMeterReadable;
	private RadioButton radBtnYes,radBtnNo;
	private RadioGroup radGroupOtherChecksGiPipeInsideBasement;
	private RadioButton radBtnYesInBm,radBtnNoInBm;

	// -Start- Added By Pankit Mistry
	private RadioGroup radGroupPVCSleeve;
	private RadioButton radBtnYesPVC, radBtnNoPVC;
	// -End-
	private LinearLayout llmeterReading,llmeterReadingDetail;

	// -Start- Added By Pankit Mistry
	private EditText edtRubberCapChargable;
	private EditText edtBrassBallChargable;
	private EditText edtBrassBallCockChargable;
	private EditText edtotherCheckssupplybushChargable;
	private EditText edtotherCheckssupplybush;
	// -End-

	// -Start- Added By Pankit Mistry
	private RadioGroup radGroupRubberCap;
	private RadioButton radRubberCapByCompany, radRubberCapByVendor;

	private RadioGroup radGroupBrassBall;
	private RadioButton radBrassBallByCompany, radBrassBallByVendor;

	private RadioGroup radGroupBrassBallCock;
	private RadioButton radBrassBallCockByCompany, radBrassBallCockByVendor;

	private RadioGroup radGroupIsolationBall;
	private RadioButton radSplrSupply3by4into1by2BushByVendor, radSplrSupply3by4into1by2BushByCompany;

	private android.widget.CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new android.widget.CompoundButton.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			checkSuppliedStatus();
		}
	};
	// -End-
	/**
	 * A parameterized constructor
	 * @param  custStatusId :
	 * @return Returns      : TestingFragment object
	 */
	public static OtherChecksFragment newInstance(int custStatusId, String contactNumber) {
		OtherChecksFragment otherChecksFragment = new OtherChecksFragment();
		Bundle args = new Bundle();
		args.putInt("statusid", custStatusId);
		args.putString("contactNumber", contactNumber);
		otherChecksFragment.setArguments(args);
		return otherChecksFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		ohrChkView = inflater.inflate(R.layout.form_otherchechks,null);
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::OtherCheckFragmentStarted:::");
		fragmentManager = getFragmentManager();
		UserMasterService userMasterService = new UserMasterService(getActivity());
		user = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.DB_USERNAME, "tech");
		custStatusID = getArguments().getInt("statusid");
		contactNumber = getArguments().getString("contactNumber");
		btnSaveNnext = (Button)ohrChkView.findViewById(R.id.btnOtherChecksSaveandNext);
		btnPrev = (Button)ohrChkView.findViewById(R.id.btnOtherChecksPrev);
		edtRubberCap = (EditText)ohrChkView.findViewById(R.id.edtotherChecksRubberCap);
		edtBrassBall = (EditText)ohrChkView.findViewById(R.id.edtOtherChecksBrassBall);
		// -Start- Added By Pankit Mistry
		//edtSupplyBush = (EditText) ohrChkView.findViewById(R.id.edtotherCheckssupplybush);
		edtRubberCapChargable = (EditText) ohrChkView.findViewById(R.id.edtotherChecksRubberCapChargable);
		edtBrassBallChargable = (EditText) ohrChkView.findViewById(R.id.edtotherChecksBrassBallChargable);
		edtBrassBallCockChargable = (EditText) ohrChkView.findViewById(R.id.edtotherChecksBrassBallCockChargable);
		edtotherCheckssupplybushChargable = (EditText) ohrChkView.findViewById(R.id.edtotherCheckssupplybushChargable);
		edtotherCheckssupplybush = (EditText) ohrChkView.findViewById(R.id.edtotherCheckssupplybush);
		// -End-
		lblIsMeter = (TextView)ohrChkView.findViewById(R.id.lblOtherCheckIsMeter);
		lblMeterReading = (TextView)ohrChkView.findViewById(R.id.lblOtherCheckMeterReading);
		btnIsMeterOK = (Button) ohrChkView.findViewById(R.id.btnIsMeterOK);
		btnMeterPerformance = (Button)ohrChkView.findViewById(R.id.btnOtherChecksMeterPerformance);
		btnMeterRedable = (Button)ohrChkView.findViewById(R.id.btnOtherChecksMeterReadable);
		btnGiInsideBase = (Button)ohrChkView.findViewById(R.id.btnOtherChecksGiPipeInsideBasement);
		btnPVCSleeve = (Button) ohrChkView.findViewById(R.id.btnOtherChecksIsInstallationPVCSleeve);
		// -Start- Added By Pankit Mistry
		btnPVCSleeve = (Button) ohrChkView.findViewById(R.id.btnOtherChecksIsInstallationPVCSleeve);
		// -End-
		edtBrassBallCock = (EditText)ohrChkView.findViewById(R.id.edtOtherChecksBrassBallCock);
		edtMeterReading = (EditText)ohrChkView.findViewById(R.id.edtOtherChecksMeterReading);
		lblotherCheckHeader = (TextView)ohrChkView.findViewById(R.id.lblheaderOtherChecks);
		lblBrassBall = (TextView)ohrChkView.findViewById(R.id.lblOtherChecksBrassBall);
		lblBrassBallCock = (TextView)ohrChkView.findViewById(R.id.lblOtherChecksBrassBallCock);
		lblGiInsideBaseMent = (TextView)ohrChkView.findViewById(R.id.lblOtherChecksGiPipeInsideBasement);
		lblMeterPerformance = (TextView)ohrChkView.findViewById(R.id.lblOtherChecksMeterPerformance);
		lblMeterReadable = (TextView)ohrChkView.findViewById(R.id.lblOtherChecksMeterReadable);
		lblRubberCap = (TextView)ohrChkView.findViewById(R.id.lblotherChecksRubberCap);
		lblOtherChecksIsInstallationPVCSleeve = (TextView) ohrChkView.findViewById(R.id.lblOtherChecksIsInstallationPVCSleeve);
		lblOtherChecksIsolationBallValue = (TextView) ohrChkView.findViewById(R.id.lblOtherChecksIsolationBallValue);
		//	lblotherchecksupplyofbush = (TextView) ohrChkView.findViewById(R.id.lblotherchecksupplyofbush);

		errTxtMtrInside = (TextView)ohrChkView.findViewById(R.id.errOthersPipeInside);
		errTxtMtrPrformce = (TextView)ohrChkView.findViewById(R.id.errOthersMtrPrformc);
		errTxtMtrRdble = (TextView)ohrChkView.findViewById(R.id.errOthersMtrRdable);
		// -Start- Added By Pankit Mistry
		errTxtPVCSleeve = (TextView) ohrChkView.findViewById(R.id.errOthersIsInstallationPVCSleeve);
		// -End-

		radGroupBrassBall = (RadioGroup) ohrChkView.findViewById(R.id.radGroupOtherChecksBrassBall);
		radGroupBrassBallCock = (RadioGroup) ohrChkView.findViewById(R.id.radGroupOtherChecksBrassBallCock);
		radGroupRubberCap = (RadioGroup) ohrChkView.findViewById(R.id.radGroupOtherChecksRubberCap);
		radGroupIsolationBall = (RadioGroup) ohrChkView.findViewById(R.id.radGroupOtherChecksIsolationBallValue);

		radBrassBallByCompany = (RadioButton) ohrChkView.findViewById(R.id.stubeOtherChecksBrassBallByCompany);
		radBrassBallByVendor = (RadioButton) ohrChkView.findViewById(R.id.stubeOtherChecksBrassBallByVendor);
		radBrassBallCockByCompany = (RadioButton) ohrChkView.findViewById(R.id.stubeOtherChecksBrassBallCockByCompany);
		radBrassBallCockByVendor = (RadioButton) ohrChkView.findViewById(R.id.stubeOtherChecksBrassBallCockByVendor);
		radRubberCapByCompany = (RadioButton) ohrChkView.findViewById(R.id.stubeOtherChecksRubberCapByCompany);
		radRubberCapByVendor = (RadioButton) ohrChkView.findViewById(R.id.stubeOtherChecksRubberCapByVendor);
		radSplrSupply3by4into1by2BushByVendor = (RadioButton) ohrChkView.findViewById(R.id.splrSupply3by4into1by2BushByVendor);
		radSplrSupply3by4into1by2BushByCompany = (RadioButton) ohrChkView.findViewById(R.id.splrSupply3by4into1by2BushByCompany);

		radRubberCapByCompany.setOnCheckedChangeListener(onCheckedChangeListener);
		radRubberCapByVendor.setOnCheckedChangeListener(onCheckedChangeListener);
		radBrassBallByCompany.setOnCheckedChangeListener(onCheckedChangeListener);
		radBrassBallByVendor.setOnCheckedChangeListener(onCheckedChangeListener);
		radBrassBallCockByCompany.setOnCheckedChangeListener(onCheckedChangeListener);
		radBrassBallCockByVendor.setOnCheckedChangeListener(onCheckedChangeListener);
		radSplrSupply3by4into1by2BushByCompany.setOnCheckedChangeListener(onCheckedChangeListener);
		radSplrSupply3by4into1by2BushByVendor.setOnCheckedChangeListener(onCheckedChangeListener);
		radGroupMeterPerformance = (RadioGroup)ohrChkView.findViewById(R.id.radGroupOtherChecksMeterPerformance);
		radBtnNotOk = (RadioButton)ohrChkView.findViewById(R.id.radBtnNotOk);
		radBtnOk = (RadioButton)ohrChkView.findViewById(R.id.radBtnOk);

		radGrpIsMeter = (RadioGroup)ohrChkView.findViewById(R.id.radGroupOtherChecksIsMeter);
		radBtnIsMeterOk = (RadioButton)ohrChkView.findViewById(R.id.radBtnIsMeterOk);
		radBtnIsMeterNotOk = (RadioButton)ohrChkView.findViewById(R.id.radBtnIsMeterNotOk);

		radGroupOtherChecksMeterReadable = (RadioGroup) ohrChkView.findViewById(R.id.radGroupOtherChecksMeterReadable);
		radBtnYes= (RadioButton)ohrChkView.findViewById(R.id.radBtnYes);
		radBtnNo = (RadioButton)ohrChkView.findViewById(R.id.radBtnNo);

		radGroupOtherChecksGiPipeInsideBasement = (RadioGroup) ohrChkView.findViewById(R.id.radGroupOtherChecksGiPipeInsideBasement);
		radBtnYesInBm= (RadioButton)ohrChkView.findViewById(R.id.radBtnYesInBM);
		radBtnNoInBm = (RadioButton)ohrChkView.findViewById(R.id.radBtnNoInBM);

		// -Start- Added By Pankit Mistry
		radGroupPVCSleeve = (RadioGroup) ohrChkView.findViewById(R.id.radGroupOtherChecksIsInstallationPVCSleeve);
		radBtnYesPVC = (RadioButton) ohrChkView.findViewById(R.id.radBtnYesInPVSSleeve);
		radBtnNoPVC = (RadioButton) ohrChkView.findViewById(R.id.radBtnNoInPVCSleeve);
		// -End-
		llmeterReading = (LinearLayout)ohrChkView.findViewById(R.id.llmeterReading);
		llmeterReadingDetail = (LinearLayout)ohrChkView.findViewById(R.id.llmeterReadingDetail);

		txtVendorSupplied = (TextView) ohrChkView.findViewById(R.id.txtVendorSupplied);
		txtCompanySupplied = (TextView) ohrChkView.findViewById(R.id.txtCompanySupplied);
		txtNoChargableQty = (TextView) ohrChkView.findViewById(R.id.txtNoChargableQty);
		txtChargableQty = (TextView) ohrChkView.findViewById(R.id.txtChargableQty);

		errOtherChecksRubberCap = (TextView) ohrChkView.findViewById(R.id.errOtherChecksRubberCap);
		errOtherChecksBrassBall = (TextView) ohrChkView.findViewById(R.id.errOtherChecksBrassBall);
		errOtherChecksBrassBallCock = (TextView) ohrChkView.findViewById(R.id.errOtherChecksBrassBallCock);
		errOtherChecksIsolationBallValue = (TextView) ohrChkView.findViewById(R.id.errOtherChecksIsolationBallValue);
		//errOtherCheckssupplybush = (TextView) ohrChkView.findViewById(R.id.errOtherCheckssupplybush);

		final String[] otionsMtrPrfrmce = getResources().getStringArray(R.array.other_mtr_values);
		final String[] optionsMtrReadable = getResources().getStringArray(R.array.arrayCheckBoolean);
		final String[] optionsMtrInside = getResources().getStringArray(R.array.arrayCheckBoolean);

		/**
		 * Calling the changeLanguage Function With Parameter Id
		 */
		changeLanguage(id);

		if((custStatusID != userMasterService.getStatusId("OP") && custStatusID != userMasterService.getStatusId("HCL1")) || user.equals("admin")){
			otherChecksDisableViews();
		}

		if(Global.isCycleRunning)
			MaintanenceForm.btnHeaderProcess.setVisibility(View.VISIBLE);
		else
			MaintanenceForm.btnHeaderProcess.setVisibility(View.INVISIBLE);

		btnSaveNnext.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Utility.changeSelectedForm(8, 7);
				Utility.changeList(8,false);
				SharedPreferences pref = getActivity().getSharedPreferences("com.spec.asms",Context.MODE_PRIVATE);
				int userID = pref.getInt("userID",0);
				ConformanceFragment conformanceFragment = ConformanceFragment.newInstance(userID,custStatusID, contactNumber);
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.frmLayoutFragment, conformanceFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		});

		btnPrev.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Utility.changeSelectedForm(6, 7);
				Utility.changeList(6,false);
				MakeNGeyserFragment mkNgyFragment = MakeNGeyserFragment.newInstance(custStatusID, contactNumber);
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.frmLayoutFragment, mkNgyFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		});

		/**
		 * calling the Function initializeValuesOtherChecks()
		 */
		initializeValuesOtherChecks();

		radGrpIsMeter.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {

					case R.id.radBtnIsMeterOk:
						btnIsMeterOK.setText(radBtnIsMeterOk.getText().toString());
						OtherChecksVO.setIsMeter(radBtnIsMeterOk.getText().toString());
						strIsMeter = radBtnIsMeterOk.getText().toString();
						llmeterReading.setVisibility(View.VISIBLE);
						llmeterReadingDetail.setVisibility(View.VISIBLE);
						break;
					case R.id.radBtnIsMeterNotOk:
						btnIsMeterOK.setText(radBtnIsMeterNotOk.getText().toString());
						OtherChecksVO.setIsMeter(radBtnIsMeterNotOk.getText().toString());
						strIsMeter = radBtnIsMeterNotOk.getText().toString();
						llmeterReading.setVisibility(View.GONE);
						llmeterReadingDetail.setVisibility(View.GONE);
						break;
					default:
						break;
				}
				validateButton();
			}
		});
		radGroupMeterPerformance.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {


				switch(checkedId)
				{
					case R.id.radBtnOk:
						btnMeterPerformance.setText(radBtnOk.getText().toString());
						OtherChecksVO.setMtrPerformance(radBtnOk.getText().toString());
						break;

					case R.id.radBtnNotOk:
						btnMeterPerformance.setText(radBtnNotOk.getText().toString());
						OtherChecksVO.setMtrPerformance(radBtnNotOk.getText().toString());
						break;
				}
				validateButton();
			}
		});



		btnMeterPerformance.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				try{
					new AlertDialog.Builder(getActivity()).setSingleChoiceItems(otionsMtrPrfrmce,meterPerformance, new DialogInterface.OnClickListener(){

						public void onClick(DialogInterface dialog, int whichButton){
							btnMeterPerformance.setText(otionsMtrPrfrmce[whichButton]);
							meterPerformance = whichButton;
							OtherChecksVO.setMtrPerformance(otionsMtrPrfrmce[whichButton]);
							validateButton();
							dialog.dismiss();
						}
					})
							.show();
				}catch (Exception e){
					e.printStackTrace();
					Log.d(Constants.DEBUG,"OtherChecksFragment:"+ Utility.convertExceptionToString(e));
				}
			}
		});

		radGroupOtherChecksMeterReadable.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch(checkedId)
				{
					case R.id.radBtnYes:
						btnMeterRedable.setText(radBtnYes.getText().toString());
						OtherChecksVO.setMtrReadable(radBtnYes.getText().toString());
						break;

					case R.id.radBtnNo:
						btnMeterRedable.setText(radBtnNo.getText().toString());
						OtherChecksVO.setMtrReadable(radBtnNo.getText().toString());
						break;
				}
				validateButton();

			}
		});

		btnMeterRedable.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				try{
					new AlertDialog.Builder(getActivity()).setSingleChoiceItems(optionsMtrReadable,meterRedable, new DialogInterface.OnClickListener(){

						public void onClick(DialogInterface dialog, int whichButton){
							btnMeterRedable.setText(optionsMtrReadable[whichButton]);
							meterRedable = whichButton;
							OtherChecksVO.setMtrReadable(optionsMtrReadable[whichButton]);
							validateButton();
							dialog.dismiss();
						}
					})
							.show();

				}catch (Exception e){
					e.printStackTrace();
					Log.d(Constants.DEBUG,"OtherChecksFragment:"+ Utility.convertExceptionToString(e));
				}
			}
		});

		radGroupOtherChecksGiPipeInsideBasement.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch(checkedId)
				{
					case R.id.radBtnYesInBM:
						btnGiInsideBase.setText(radBtnYesInBm.getText().toString());
						OtherChecksVO.setGiPipeInsidebm(radBtnYesInBm.getText().toString());
						break;

					case R.id.radBtnNoInBM:
						btnGiInsideBase.setText(radBtnNoInBm.getText().toString());
						OtherChecksVO.setGiPipeInsidebm(radBtnNoInBm.getText().toString());
						break;
				}
				validateButton();
			}
		});

		radGroupPVCSleeve.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch(checkedId) {
					case R.id.radBtnYesInPVSSleeve:
						btnPVCSleeve.setText(radBtnYesPVC.getText().toString());
						OtherChecksVO.setPvcSleeve(radBtnYesPVC.getText().toString());
						break;
					case R.id.radBtnNoInPVCSleeve:
						btnPVCSleeve.setText(radBtnNoPVC.getText().toString());
						OtherChecksVO.setPvcSleeve(radBtnNoPVC.getText().toString());
						break;
				}
			}
		});
		btnGiInsideBase.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				try{
					new AlertDialog.Builder(getActivity()).setSingleChoiceItems(optionsMtrInside,GiInsideBase, new DialogInterface.OnClickListener(){

						public void onClick(DialogInterface dialog, int whichButton) {
							btnGiInsideBase.setText(optionsMtrInside[whichButton]);
							GiInsideBase = whichButton;
							OtherChecksVO.setGiPipeInsidebm(optionsMtrInside[whichButton]);
							validateButton();
							dialog.dismiss();
						}
					})
							.show();
				}
				catch (Exception e) {
					e.printStackTrace();
					Log.d(Constants.DEBUG,"OtherChecksFragment:"+ Utility.convertExceptionToString(e));
				}
			}
		});
		checkSuppliedStatus();
		return ohrChkView;
	}

	public void checkSuppliedStatus() {
		if(radRubberCapByCompany.isChecked() || radRubberCapByVendor.isChecked()) {
			Log.e(getClass().getName(), "******************Change Listener called: Half Clamp");
			edtRubberCap.setEnabled(true);
			edtRubberCapChargable.setEnabled(true);
		} else {
			edtRubberCap.setEnabled(false);
			edtRubberCapChargable.setEnabled(false);
		}

		if(radBrassBallByCompany.isChecked() || radBrassBallByVendor.isChecked()) {
			edtBrassBall.setEnabled(true);
			edtBrassBallChargable.setEnabled(true);
		} else {
			edtBrassBall.setEnabled(false);
			edtBrassBallChargable.setEnabled(false);
		}

		if(radBrassBallCockByCompany.isChecked() || radBrassBallCockByVendor.isChecked()) {
			edtBrassBallCock.setEnabled(true);
			edtBrassBallCockChargable.setEnabled(true);
		} else {
			edtBrassBallCock.setEnabled(false);
			edtBrassBallCockChargable.setEnabled(false);
		}

		if(radSplrSupply3by4into1by2BushByCompany.isChecked() || radSplrSupply3by4into1by2BushByVendor.isChecked()) {
			edtotherCheckssupplybush.setEnabled(true);
			edtotherCheckssupplybushChargable.setEnabled(true);
		} else {
			edtotherCheckssupplybush.setEnabled(false);
			edtotherCheckssupplybushChargable.setEnabled(false);
		}
	}
	/**
	 * This Method Contain Button Validation if the Fields Value is not 
	 * Selected than this Method will Display Error_Mark. 
	 */
	public void validateButton(){

		ArrayList<Boolean> errorList = new ArrayList<Boolean>();
		
		/*if(edtSupplyBush.getText().toString().equals(Constants.BLANK)) {
			errorList.add(true);
			errTxtMtrPrformce.setText(Constants.ERROR_MARK);
		} else {
			errTxtMtrPrformce.setText(Constants.ERROR_MARK);
			errorList.add(false);
		}*/

		if(radRubberCapByCompany.isChecked() || radRubberCapByVendor.isChecked()) {
			if(edtRubberCap.getText().toString().equals(Constants.BLANK) && edtRubberCapChargable.getText().toString().equals(Constants.BLANK)) {
				errorList.add(true);
			} else {
				errorList.add(false);
			}
		} else {
			errorList.add(false);
		}

		if(radBrassBallByCompany.isChecked() || radBrassBallByVendor.isChecked()) {
			if(edtBrassBall.getText().toString().equals(Constants.BLANK) && edtBrassBallChargable.getText().toString().equals(Constants.BLANK)) {
				errorList.add(true);
			} else {
				errorList.add(false);
			}
		} else {
			errorList.add(false);
		}

		if(radBrassBallCockByCompany.isChecked() || radBrassBallCockByVendor.isChecked()) {
			if(edtBrassBallCock.getText().toString().equals(Constants.BLANK) && edtBrassBallCockChargable.getText().toString().equals(Constants.BLANK)) {
				errorList.add(true);
			} else {
				errorList.add(false);
			}
		} else {
			errorList.add(false);
		}

		if(radSplrSupply3by4into1by2BushByCompany.isChecked() || radSplrSupply3by4into1by2BushByVendor.isChecked()) {
			if(edtotherCheckssupplybush.getText().toString().equals(Constants.BLANK) && edtotherCheckssupplybushChargable.getText().toString().equals(Constants.BLANK)) {
				errorList.add(true);
			} else {
				errorList.add(false);
			}
		} else {
			errorList.add(false);
		}
		/*if(edtRubberCap.getText().toString().equals(Constants.BLANK) || 
				edtRubberCapChargable.getText().toString().equals(Constants.BLANK) ||
				edtBrassBall.getText().toString().equals(Constants.BLANK) ||
				edtBrassBallChargable.getText().toString().equals(Constants.BLANK) || 
				edtBrassBallCock.getText().toString().equals(Constants.BLANK) || 
				edtBrassBallCockChargable.getText().toString().equals(Constants.BLANK) ||
				edtIsolationBall.getText().toString().equals(Constants.BLANK) ||
				edtIsolationBallChargable.getText().toString().equals(Constants.BLANK)) {
			errorList.add(true);
			errTxtMtrPrformce.setText(Constants.ERROR_MARK);
		} else {
			errorList.add(false);
			errTxtMtrPrformce.setText(Constants.ERROR_MARK);
		}*/

		if(btnIsMeterOK.getText().toString().equals(getResources().getString(R.string.title_select_option))) {
			errorList.add(true);
			errTxtMtrPrformce.setText(Constants.ERROR_MARK);
		} else {
			if(btnIsMeterOK.getText().toString().equalsIgnoreCase("OK")) {
				if(edtMeterReading.getText().toString().equals(Constants.BLANK)) {
					errorList.add(true);
					errTxtMtrPrformce.setText(Constants.ERROR_MARK);
				} else {
					errorList.add(false);
					errTxtMtrPrformce.setText(Constants.ERROR_MARK);
				}
			}
			errorList.add(false);
			errTxtMtrPrformce.setText(Constants.ERROR_MARK);
		}
		if(btnMeterPerformance.getText().toString().equals(getResources().getString(R.string.title_select_option))){
			errorList.add(true);
			errTxtMtrPrformce.setText(Constants.ERROR_MARK);
		}else{
//			errTxtMtrPrformce.setText(Constants.LABEL_BLANK);
			errTxtMtrPrformce.setText(Constants.ERROR_MARK);
			errorList.add(false);
		}

		if(btnMeterRedable.getText().toString().equals(getResources().getString(R.string.title_select_option))){
			errorList.add(true);
			errTxtMtrRdble.setText(Constants.ERROR_MARK);
		}else{
//			errTxtMtrRdble.setText(Constants.LABEL_BLANK);
			errTxtMtrRdble.setText(Constants.ERROR_MARK);
			errorList.add(false);
		}
		if(btnGiInsideBase.getText().toString().equals(getResources().getString(R.string.title_select_option))){
			errorList.add(true);
			errTxtMtrInside.setText(Constants.ERROR_MARK);
		}else{
//			errTxtMtrInside.setText(Constants.LABEL_BLANK);
			errTxtMtrInside.setText(Constants.ERROR_MARK);
			errorList.add(false);
		}
		if(btnPVCSleeve.getText().toString().equals(getResources().getString(R.string.title_select_option))) {
			errorList.add(true);
			errTxtPVCSleeve.setText(Constants.ERROR_MARK);
		} else {
			errorList.add(false);
			errTxtPVCSleeve.setText(Constants.ERROR_MARK);
		}


		Collections.sort(errorList);
		System.err.println(" Correction Error --- "+ Collections.binarySearch(errorList,true));
		if(Collections.binarySearch(errorList,true) >= 0)
			Utility.changeList(7,true);
		else
			Utility.changeList(7,false);

	}

	public void setValue()
	{
		try{
			if(radRubberCapByCompany.isChecked())
				OtherChecksVO.setRubberCapSuppliedBy("C");
			else if(radRubberCapByVendor.isChecked())
				OtherChecksVO.setRubberCapSuppliedBy("V");
			else
				OtherChecksVO.setRubberCapSuppliedBy(Constants.LABEL_BLANK);

			if(radBrassBallByCompany.isChecked())
				OtherChecksVO.setBrassBallSuppliedBy("C");
			else if(radBrassBallByVendor.isChecked())
				OtherChecksVO.setBrassBallSuppliedBy("V");
			else
				OtherChecksVO.setBrassBallSuppliedBy(Constants.LABEL_BLANK);

			if(radBrassBallCockByCompany.isChecked())
				OtherChecksVO.setBrassBallCockSuppliedBy("C");
			else if(radBrassBallCockByVendor.isChecked())
				OtherChecksVO.setBrassBallCockSuppliedBy("V");
			else
				OtherChecksVO.setBrassBallCockSuppliedBy(Constants.LABEL_BLANK);

			if(radSplrSupply3by4into1by2BushByCompany.isChecked())
				OtherChecksVO.setsplrSupply3by4into1by2BushBy("C");
			else if(radSplrSupply3by4into1by2BushByVendor.isChecked())
				OtherChecksVO.setsplrSupply3by4into1by2BushBy("V");
			else
				OtherChecksVO.setsplrSupply3by4into1by2BushBy(Constants.LABEL_BLANK);

			if(radBtnIsMeterOk.isChecked())
				OtherChecksVO.setIsMeter("OK");
			else
				OtherChecksVO.setIsMeter("NOT OK");

			// -Start- Added By Pankit Mistry
			//OtherChecksVO.setSupplyBush(edtSupplyBush.getText().toString());
			OtherChecksVO.setRubberCapChargable(edtRubberCapChargable.getText().toString());
			OtherChecksVO.setBrassBallValueChargable(edtBrassBallChargable.getText().toString());
			OtherChecksVO.setBrassBallCockChargable(edtBrassBallCockChargable.getText().toString());
			OtherChecksVO.setChrgSupply3by4into1by2Bush(edtotherCheckssupplybushChargable.getText().toString());
			OtherChecksVO.setSupply3by4into1by2Bush(edtotherCheckssupplybush.getText().toString());
			OtherChecksVO.setPvcSleeve(btnPVCSleeve.getText().toString());
			// -End-
			OtherChecksVO.setRubberCap(edtRubberCap.getText().toString());
			OtherChecksVO.setBrassBallCock(edtBrassBallCock.getText().toString());
			OtherChecksVO.setMeterReading(edtMeterReading.getText().toString());
			OtherChecksVO.setBrassBallValue(edtBrassBall.getText().toString());
			OtherChecksVO.setMtrPerformance(btnMeterPerformance.getText().toString());
			OtherChecksVO.setMtrReadable(btnMeterRedable.getText().toString());
			OtherChecksVO.setGiPipeInsidebm(btnGiInsideBase.getText().toString());
			OtherChecksVO.setIsMeter(strIsMeter);

			clearValues();
		}catch (Exception e) {
			e.getStackTrace();
		}
	}

	public void clearValues()
	{
		try{
			// -Start- Added By Pankit Mistry
			//edtSupplyBush.setText(Constants.LABEL_BLANK);
			edtRubberCapChargable.setText(Constants.LABEL_BLANK);
			edtRubberCap.setText(Constants.BLANK);
			edtBrassBallChargable.setText(Constants.LABEL_BLANK);
			edtBrassBall.setText(Constants.BLANK);
			edtBrassBallCockChargable.setText(Constants.LABEL_BLANK);
			edtBrassBallCock.setText(Constants.BLANK);
			edtotherCheckssupplybush.setText(Constants.LABEL_BLANK);
			edtotherCheckssupplybushChargable.setText(Constants.LABEL_BLANK);
			btnPVCSleeve.setText(Constants.LABEL_BLANK);
			radRubberCapByCompany.setChecked(false);
			radRubberCapByVendor.setChecked(false);
			radBrassBallByCompany.setChecked(false);
			radBrassBallByVendor.setChecked(false);
			radBrassBallCockByCompany.setChecked(false);
			radBrassBallCockByVendor.setChecked(false);
			radSplrSupply3by4into1by2BushByCompany.setChecked(false);
			radSplrSupply3by4into1by2BushByVendor.setChecked(false);
			// -End-
			edtBrassBallCock.setText(Constants.LABEL_BLANK);
			edtMeterReading.setText(Constants.LABEL_BLANK);
			edtRubberCap.setText(Constants.LABEL_BLANK);
			edtBrassBall.setText(Constants.LABEL_BLANK);
			btnMeterPerformance.setText(Constants.LABEL_BLANK);
			btnMeterRedable.setText(Constants.LABEL_BLANK);//getResources().getString(R.string.title_select_option));
			btnGiInsideBase.setText(Constants.LABEL_BLANK);
			edtBrassBallCock.setText(Constants.LABEL_BLANK);
			strIsMeter = Constants.LABEL_BLANK;
			edtMeterReading.setText(Constants.LABEL_BLANK);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * On changing the Fragment or Switch over from 
	 * One Activity to another activity this method is call
	 */
	@Override
	public void onPause() {
		super.onPause();
		if("OP".equals(TestingFragment.testingStatusCode)  || "HCL1".equals(TestingFragment.testingStatusCode))
		{
			validateButton();
			setValue();
		}
	}

	public  void otherChecksDisableViews(){
		// -Start- Added By Pankit Mistry
		//edtSupplyBush.setEnabled(false);
		edtRubberCapChargable.setEnabled(false);
		edtBrassBallChargable.setEnabled(false);
		edtBrassBallCockChargable.setEnabled(false);
		edtotherCheckssupplybush.setEnabled(false);
		edtotherCheckssupplybushChargable.setEnabled(false);
		btnPVCSleeve.setEnabled(false);
		radBtnYesPVC.setEnabled(false);
		radBtnNoPVC.setEnabled(false);

		radRubberCapByCompany.setEnabled(false);
		radRubberCapByVendor.setEnabled(false);
		radBrassBallByCompany.setEnabled(false);
		radBrassBallByVendor.setEnabled(false);
		radBrassBallCockByCompany.setEnabled(false);
		radBrassBallCockByVendor.setEnabled(false);
		radSplrSupply3by4into1by2BushByCompany.setEnabled(false);
		radSplrSupply3by4into1by2BushByVendor.setEnabled(false);
		// -End-
		edtBrassBallCock.setEnabled(false);
		edtMeterReading.setEnabled(false);
		edtRubberCap.setEnabled(false);
		edtBrassBall.setEnabled(false);
		btnMeterPerformance.setEnabled(false);
		btnMeterRedable.setEnabled(false);
		btnGiInsideBase.setEnabled(false);
		radBtnIsMeterNotOk.setEnabled(false);
		radBtnIsMeterOk.setEnabled(false);
		radBtnNo.setEnabled(false);
		radBtnYes.setEnabled(false);
		radBtnNoInBm.setEnabled(false);
		radBtnYesInBm.setEnabled(false);
		radBtnNotOk.setEnabled(false);
		radBtnOk.setEnabled(false);
	}


	/**
	 * This Method is Used to Initialize Value of Fields.
	 * At Initial Level it will be initialize with Blank Label.
	 * and than Initialize with the value Enter by the user.
	 */
	private void initializeValuesOtherChecks(){

		// -Start- Added By Pankit Mistry

		//errOtherCheckssupplybush.setText(Utility.otherchecks(Constants.SUPPLY_BUSH,edtSupplyBush.getText().toString().trim(),getActivity()));
		errOtherChecksRubberCap.setText(Utility.otherchecks(Constants.RUBBER_CAP,edtRubberCapChargable.getText().toString().trim(),getActivity()));
		errOtherChecksBrassBall.setText(Utility.otherchecks(Constants.BRASS_BALL_VALVE,edtBrassBallChargable.getText().toString().trim(),getActivity()));
		errOtherChecksBrassBallCock.setText(Utility.otherchecks(Constants.BRASS_BALL_COCK,edtBrassBallCockChargable.getText().toString().trim(),getActivity()));
		errOtherChecksIsolationBallValue.setText(Utility.otherchecks(Constants.ISOLATION_BALL_VALE,edtotherCheckssupplybushChargable.getText().toString().trim(),getActivity()));

		//edtSupplyBush.setText(OtherChecksVO.getSupplyBush());
		edtRubberCapChargable.setText(OtherChecksVO.getRubberCapChargable());
		edtBrassBallChargable.setText(OtherChecksVO.getBrassBallValueChargable());
		edtBrassBallCockChargable.setText(OtherChecksVO.getBrassBallCockChargable());
		edtotherCheckssupplybush.setText(OtherChecksVO.getSupply3by4into1by2Bush());
		edtotherCheckssupplybushChargable.setText(OtherChecksVO.getChrgSupply3by4into1by2Bush());
		// -End-
		edtBrassBall.setText(OtherChecksVO.getBrassBallValue());
		edtBrassBallCock.setText(OtherChecksVO.getBrassBallCock());
		edtMeterReading.setText(OtherChecksVO.getMeterReading());
		edtRubberCap.setText(OtherChecksVO.getRubberCap());
		// -Start- Added By Pankit Mistry
		strPVCSleeve = OtherChecksVO.getPvcSleeve();
		if(strPVCSleeve.equals(Constants.LABEL_BLANK)) {
			strPVCSleeve = getResources().getString(R.string.title_select_option);
			btnPVCSleeve.setText(strPVCSleeve);
			radBtnYesPVC.setChecked(false);
			radBtnNoPVC.setChecked(false);
		} else {
			btnPVCSleeve.setText(OtherChecksVO.getPvcSleeve());
			if("YES".equals(OtherChecksVO.getPvcSleeve())) {
				radBtnYesPVC.setChecked(true);
				radBtnNoPVC.setChecked(false);
			} else if("NO".equals(OtherChecksVO.getPvcSleeve())) {
				radBtnYesPVC.setChecked(false);
				radBtnNoPVC.setChecked(true);
			}
		}
		// -End-

		// -Start- Added By Pankit Mistry
		if(OtherChecksVO.getRubberCapSuppliedBy().equals(Constants.SUPPLY_COMPANY)) {
			radRubberCapByCompany.setChecked(true);
			radRubberCapByVendor.setChecked(false);
		}else if(OtherChecksVO.getRubberCapSuppliedBy().equals(Constants.SUPPLY_VENDOR)) {
			radRubberCapByVendor.setChecked(true);
			radRubberCapByCompany.setChecked(false);
		} else {
			radRubberCapByCompany.setChecked(false);
			radRubberCapByVendor.setChecked(false);
		}

		if(OtherChecksVO.getBrassBallSuppliedBy().equals(Constants.SUPPLY_COMPANY)) {
			radBrassBallByCompany.setChecked(true);
			radBrassBallByVendor.setChecked(false);
		}else if(OtherChecksVO.getBrassBallSuppliedBy().equals(Constants.SUPPLY_VENDOR)) {
			radBrassBallByVendor.setChecked(true);
			radBrassBallByCompany.setChecked(false);
		} else {
			radBrassBallByCompany.setChecked(false);
			radBrassBallByVendor.setChecked(false);
		}

		if(OtherChecksVO.getBrassBallCockSuppliedBy().equals(Constants.SUPPLY_COMPANY)) {
			radBrassBallCockByCompany.setChecked(true);
			radBrassBallCockByVendor.setChecked(false);
		}else if(OtherChecksVO.getBrassBallCockSuppliedBy().equals(Constants.SUPPLY_VENDOR)) {
			radBrassBallCockByVendor.setChecked(true);
			radBrassBallCockByCompany.setChecked(false);
		} else {
			radBrassBallCockByCompany.setChecked(false);
			radBrassBallCockByVendor.setChecked(false);
		}

		if(OtherChecksVO.getsplrSupply3by4into1by2BushBy().equals(Constants.SUPPLY_COMPANY)) {
			radSplrSupply3by4into1by2BushByCompany.setChecked(true);
			radSplrSupply3by4into1by2BushByVendor.setChecked(false);
		}else if(OtherChecksVO.getsplrSupply3by4into1by2BushBy().equals(Constants.SUPPLY_VENDOR)) {
			radSplrSupply3by4into1by2BushByVendor.setChecked(true);
			radSplrSupply3by4into1by2BushByCompany.setChecked(false);
		} else {
			radSplrSupply3by4into1by2BushByCompany.setChecked(false);
			radSplrSupply3by4into1by2BushByVendor.setChecked(false);
		}


		strMeterPerformance = OtherChecksVO.getMtrPerformance();
		if(strMeterPerformance.equals(Constants.LABEL_BLANK)){
			strMeterPerformance = getResources().getString(R.string.title_select_option);
			btnMeterPerformance.setText(strMeterPerformance);
			meterPerformance = -1;
			radBtnNotOk.setChecked(false);
			radBtnOk.setChecked(false);

		}else{
			btnMeterPerformance.setText(OtherChecksVO.getMtrPerformance());
			if("OK".equals(OtherChecksVO.getMtrPerformance()))
			{
				//meterPerformance = 0;
				radBtnNotOk.setChecked(false);
				radBtnOk.setChecked(true);
			}
			else if("NOT OK".equals(OtherChecksVO.getMtrPerformance()))
			{
				radBtnNotOk.setChecked(true);
				radBtnOk.setChecked(false);
				//meterPerformance = 1;
			}
		}

		strMeterRedable = OtherChecksVO.getMtrReadable();
		if(strMeterRedable.equals(Constants.LABEL_BLANK)){
			strMeterRedable = getResources().getString(R.string.title_select_option);
			btnMeterRedable.setText(strMeterRedable);
			meterRedable = -1;
			radBtnYes.setChecked(false);
			radBtnNo.setChecked(false);

		}else{
			btnMeterRedable.setText(OtherChecksVO.getMtrReadable());
			if("YES".equals(OtherChecksVO.getMtrReadable()))
			{
//				meterRedable = 0;
				radBtnYes.setChecked(true);
				radBtnNo.setChecked(false);
			}
			else if("NO".equals(OtherChecksVO.getMtrReadable()))
			{
//				meterRedable = 1;
				radBtnYes.setChecked(false);
				radBtnNo.setChecked(true);
			}
		}

		strGiInsideBase = OtherChecksVO.getGiPipeInsidebm();
		if(strGiInsideBase.equals(Constants.LABEL_BLANK)){
			strGiInsideBase = getResources().getString(R.string.title_select_option);
			btnGiInsideBase.setText(strGiInsideBase);
			GiInsideBase = -1;
			radBtnYesInBm.setChecked(false);
			radBtnNoInBm.setChecked(false);

		}else{
			btnGiInsideBase.setText(OtherChecksVO.getGiPipeInsidebm());
			if("YES".equals(OtherChecksVO.getGiPipeInsidebm()))
			{
				GiInsideBase = 0;
				radBtnYesInBm.setChecked(true);
				radBtnNoInBm.setChecked(false);
			}
			else if("NO".equals(OtherChecksVO.getGiPipeInsidebm()))
			{
				GiInsideBase = 1;
				radBtnYesInBm.setChecked(false);
				radBtnNoInBm.setChecked(true);
			}
		}

		strIsMeter = OtherChecksVO.getIsMeter();
		if(strIsMeter.equals(Constants.LABEL_BLANK))
		{
			strIsMeter = getResources().getString(R.string.title_select_option);
			radBtnIsMeterOk.setChecked(false);
			radBtnIsMeterNotOk.setChecked(false);
		}
		else
		{
			if(OtherChecksVO.getIsMeter().equals("OK"))
			{
				radBtnIsMeterOk.setChecked(true);
				radBtnIsMeterNotOk.setChecked(false);
				llmeterReading.setVisibility(View.VISIBLE);
				llmeterReadingDetail.setVisibility(View.VISIBLE);

			}
			else if(OtherChecksVO.getIsMeter().equals("NOT OK"))
			{
				radBtnIsMeterNotOk.setChecked(true);
				radBtnIsMeterOk.setChecked(false);
				llmeterReading.setVisibility(View.GONE);
				llmeterReadingDetail.setVisibility(View.GONE);
			}
		}


		/**
		 * validateButton() Method Call here.
		 */
		validateButton();
	}


	/**
	 * This Method is used to change the Language of Fields
	 * @param id : Id is Passed as Parameter which is used to indicate which Language is selected 
	 *             to Display
	 *             if Id = 0 than select English Language and
	 *             if ID = 1 than select Gujrati Language.
	 */
	public void changeLanguage(int id){

		id = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.PREF_LANGUAGE,Constants.LANGUAGE_ENGLISH);

		switch (id){
			case Constants.LANGUAGE_ENGLISH:
				lblBrassBall.setText(getResources().getText(R.string.title_otherChecks_BrassBall_Eng));
				lblIsMeter.setText(getResources().getText(R.string.title_otherChecks_IsMeter_Eng));
				lblMeterReading.setText(getResources().getText(R.string.title_otherChecks_MeterReading_Eng));
				lblBrassBallCock.setText(getResources().getText(R.string.title_otherChecks_BrassBallCock_Eng));
				lblGiInsideBaseMent.setText(getResources().getText(R.string.title_otherChecks_GiPipeInsideBasement_Eng));
				lblMeterPerformance.setText(getResources().getText(R.string.title_otherChecks_MeterPerformance_Eng));
				lblMeterReadable.setText(getResources().getText(R.string.title_otherChecks_MeterReadable_Eng));
				lblotherCheckHeader.setText(getResources().getText(R.string.header_otherChecks_Eng));
				lblRubberCap.setText(getResources().getText(R.string.title_otherChecks_RubberCap_Eng));
				//lblotherchecksupplyofbush.setText(getResources().getText(R.string.title_otherSupplyOf_Bush_Eng));
				lblOtherChecksIsolationBallValue.setText(getResources().getText(R.string.title_otherSupplyOf_Bush_Eng));
				lblOtherChecksIsInstallationPVCSleeve.setText(getResources().getText(R.string.title_otherInstallation_PVCSleev_Eng));
				txtVendorSupplied.setText(getResources().getText(R.string.lbl_by_supply_vendor_Eng));
				txtCompanySupplied.setText(getResources().getText(R.string.lbl_by_supply_company_Eng));
				txtNoChargableQty.setText(getResources().getText(R.string.lbl_non_chargable_qty_Eng));
				txtChargableQty.setText(getResources().getText(R.string.lbl_chargable_qty_Eng));
				break;

			case Constants.LANGUAGE_GUJRATI:
				lblBrassBall.setTypeface(Global.typeface_Guj);
				lblBrassBallCock.setTypeface(Global.typeface_Guj);
				lblGiInsideBaseMent.setTypeface(Global.typeface_Guj);
				lblMeterPerformance.setTypeface(Global.typeface_Guj);
				lblMeterReadable.setTypeface(Global.typeface_Guj);
				lblotherCheckHeader.setTypeface(Global.typeface_Guj);
				lblRubberCap.setTypeface(Global.typeface_Guj);
				lblIsMeter.setTypeface(Global.typeface_Guj);
				lblMeterReading.setTypeface(Global.typeface_Guj);
				//lblotherchecksupplyofbush.setTypeface(Global.typeface_Guj);
				lblOtherChecksIsolationBallValue.setTypeface(Global.typeface_Guj);
				lblOtherChecksIsInstallationPVCSleeve.setTypeface(Global.typeface_Guj);

				lblBrassBall.setText(getResources().getText(R.string.title_otherChecks_BrassBall_Guj));
				lblIsMeter.setText(getResources().getText(R.string.title_otherChecks_IsMeter_Guj));
				lblMeterReading.setText(getResources().getText(R.string.title_otherChecks_MeterReading_Guj));
				lblBrassBallCock.setText(getResources().getText(R.string.title_otherChecks_BrassBallCock_Guj));
				lblGiInsideBaseMent.setText(getResources().getText(R.string.title_otherChecks_GiPipeInsideBasement_Guj));
				lblMeterPerformance.setText(getResources().getText(R.string.title_otherChecks_MeterPerformance_Guj));
				lblMeterReadable.setText(getResources().getText(R.string.title_otherChecks_MeterReadable_Guj));
				lblotherCheckHeader.setText(getResources().getText(R.string.header_otherChecks_Guj));
				lblRubberCap.setText(getResources().getText(R.string.title_otherChecks_RubberCap_Guj));
				//lblotherchecksupplyofbush.setText(getResources().getText(R.string.title_otherSupplyOf_Bush_Guj));
				lblOtherChecksIsolationBallValue.setText(getResources().getText(R.string.title_otherSupplyOf_Bush_Guj));
				lblOtherChecksIsInstallationPVCSleeve.setText(getResources().getText(R.string.title_otherInstallation_PVCSleev_Guj));
				txtVendorSupplied.setTypeface(Global.typeface_Guj);
				txtCompanySupplied.setTypeface(Global.typeface_Guj);
				txtNoChargableQty.setTypeface(Global.typeface_Guj);
				txtChargableQty.setTypeface(Global.typeface_Guj);
				txtVendorSupplied.setText(getResources().getText(R.string.lbl_by_supply_vendor_Guj));
				txtCompanySupplied.setText(getResources().getText(R.string.lbl_by_supply_company_Guj));
				txtNoChargableQty.setText(getResources().getText(R.string.lbl_non_chargable_qty_Guj));
				txtChargableQty.setText(getResources().getText(R.string.lbl_chargable_qty_Guj));
				break;

			default:
				break;
		}
	}
}