package com.spec.asms.view.fragment;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
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
import android.widget.RadioGroup.LayoutParams;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Global;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.vo.MakeGeyserVO;

/**
 * A class is used to display Make and Geyser form.  It has methods to set values in form's fields.
 * @author jenisha
 *
 */
public class MakeNGeyserFragment extends Fragment
{
	private View mngView;

	private Button btnSaveandNext;
	private Button btnPrev;
	private Button btnMake;
	private Button btnGeyserType;
	private Button btnGeyserInBathroom;

	private int make = -1;
	private int geyserType = -1;
	@SuppressWarnings("unused")
	private int geyserInBathroom = -1;
	private int id;
	private int custStatusID;
	private String contactNumber;

	private String strMake = Constants.LABEL_BLANK;
	private String strGeysertype;
	private String strGyserInBathroom;

	private TextView lblMake;
	private TextView lblMakeNGeyserHeader;
	private TextView lblGeyserType;
	private TextView lblGeyserInBathroom;
	private TextView errTxtMake;
	private TextView errTxtType;
	private TextView errTxtInside;
	private TextView lblGeyserAvailable;
	private TextView errmessage;

	private EditText edtMGOthers;

	private FragmentManager fragmentManager;

	private String[] makeOptions ;
	private String[] geyserOptions ;
	private int[] makeOptionsIds;
	private int[] geyserOptionsIds;

	private RadioGroup radGroupMGeyserIsGeyserAvailable;
	private RadioButton radBtnGeyserAvailableYes,radBtnGeyserAvailableNo;

	private RadioGroup radGroupMGeyserInsideBathroom;
	private RadioButton radBtnGeyserInYes,radBtnGeyserInNo;

	private RadioGroup radGroupMake_geyser_Make;
	private RadioButton[] radBtnMake_geyser_Make;

	private RadioGroup radGroupMGeyserType;
	private RadioButton[] radBtnMGeyserType;

	private LinearLayout llgeyser,llgeyserDetail;


	private String user = Constants.LABEL_BLANK;
	private String strGeyserAvailable = Constants.LABEL_BLANK;

	/**
	 * A parameterized constructor
	 * @param customer Status id: the id for which the profile is to be created
	 * @return MakeNGeyserFragment object
	 */
	public static MakeNGeyserFragment newInstance(int custStatusId, String contactNumber){

		MakeNGeyserFragment makeNGeyserFragment = new MakeNGeyserFragment();
		Bundle args = new Bundle();
		args.putInt("statusid", custStatusId);
		args.putString("contactNumber", contactNumber);
		makeNGeyserFragment.setArguments(args);
		return makeNGeyserFragment;
	}

	@SuppressWarnings("unused")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		mngView = inflater.inflate(R.layout.form_make_gesyer,null);
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::MakeNGeyserFragmentStarted:::");
		UserMasterService userMasterService = new UserMasterService(getActivity());
		user = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.DB_USERNAME, "tech");
		fragmentManager = getFragmentManager();
		custStatusID = getArguments().getInt("statusid");
		contactNumber = getArguments().getString("contactNumber");
		btnSaveandNext = (Button)mngView.findViewById(R.id.btnMakeandGeyserSaveandNext);
		btnPrev = (Button)mngView.findViewById(R.id.btnMakeandGeyserPrev);
		btnMake = (Button)mngView.findViewById(R.id.btnMake_geyser_Make);
		btnGeyserType = (Button)mngView.findViewById(R.id.btnMGeyserType);
		btnGeyserInBathroom = (Button)mngView.findViewById(R.id.btnMGeyserInsideBathroom);
		lblGeyserInBathroom = (TextView)mngView.findViewById(R.id.lblmake_Geyser_GeyserInsideBathroom);
		lblGeyserType = (TextView)mngView.findViewById(R.id.lblmake_Geyser_GeyserType);
		lblMake = (TextView)mngView.findViewById(R.id.lblMake_geyser_Make);
		lblMakeNGeyserHeader = (TextView)mngView.findViewById(R.id.lblMakeGeyserMake);
		lblGeyserAvailable = (TextView)mngView.findViewById(R.id.lblmake_Geyser_isGeyserAvailable);
		edtMGOthers = (EditText) mngView.findViewById(R.id.edtMGOthers);
		errTxtMake = (TextView) mngView.findViewById(R.id.errMGOthers);
		errTxtType = (TextView) mngView.findViewById(R.id.errGeyserType);
		errTxtInside = (TextView) mngView.findViewById(R.id.errGeyserInsideBathroom);
		errmessage = (TextView) mngView.findViewById(R.id.errmessage);

		radGroupMGeyserIsGeyserAvailable = (RadioGroup) mngView.findViewById(R.id.radGroupMGeyserIsGeyserAvailable);
		radBtnGeyserAvailableYes = (RadioButton)mngView.findViewById(R.id.radBtnGeyserAvailableYes);
		radBtnGeyserAvailableNo = (RadioButton)mngView.findViewById(R.id.radBtnGeyserAvailableNo);

		radGroupMGeyserInsideBathroom = (RadioGroup) mngView.findViewById(R.id.radGroupMGeyserInsideBathroom);
		radBtnGeyserInYes = (RadioButton) mngView.findViewById(R.id.radBtnGeyserInYes);
		radBtnGeyserInNo = (RadioButton) mngView.findViewById(R.id.radBtnGeyserInNo);

		radGroupMake_geyser_Make = (RadioGroup) mngView.findViewById(R.id.radGroupMake_geyser_Make);
		radGroupMGeyserType = (RadioGroup) mngView.findViewById(R.id.radGroupMGeyserType);

		llgeyser = (LinearLayout)mngView.findViewById(R.id.llgeyser);
		llgeyserDetail = (LinearLayout)mngView.findViewById(R.id.llgeyserDetail);

		final String[] dropdownOptions = getResources().getStringArray(R.array.geyerInside);

		changeLanguage(id);

		if(Global.isCycleRunning)
			MaintanenceForm.btnHeaderProcess.setVisibility(View.VISIBLE);
		else
			MaintanenceForm.btnHeaderProcess.setVisibility(View.INVISIBLE);
		errmessage.setVisibility(View.INVISIBLE);

		makeOptions = new String[userMasterService.getMake().size()];
		makeOptionsIds = new int[userMasterService.getMake().size()];

		for (int i = 0; i < makeOptions.length; i++) {
			makeOptions[i] = userMasterService.getMake().get(i).getText();
			makeOptionsIds[i] = userMasterService.getMake().get(i).getId();
		}

		geyserOptions = new String[userMasterService.getGeysers().size()];
		geyserOptionsIds = new int[userMasterService.getGeysers().size()];

		for (int i = 0; i < geyserOptions.length; i++) {
			geyserOptions[i] = userMasterService.getGeysers().get(i).getText();
			geyserOptionsIds[i] = userMasterService.getGeysers().get(i).getId();
		}

		//IS Geyser Available or not.
		radGroupMGeyserIsGeyserAvailable.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch(checkedId)
				{
					case R.id.radBtnGeyserAvailableYes:
						llgeyser.setVisibility(View.VISIBLE);
						llgeyserDetail.setVisibility(View.VISIBLE);
						errmessage.setVisibility(View.VISIBLE);
						strGeyserAvailable =radBtnGeyserAvailableYes.getText().toString();
						MakeGeyserVO.setIsGeyserAvailable(radBtnGeyserAvailableYes.getText().toString());
						break;

					case R.id.radBtnGeyserAvailableNo:
						llgeyser.setVisibility(View.GONE);
						llgeyserDetail.setVisibility(View.GONE);
						errmessage.setVisibility(View.INVISIBLE);
						strGeyserAvailable =radBtnGeyserAvailableNo.getText().toString();
						MakeGeyserVO.setIsGeyserAvailable(radBtnGeyserAvailableNo.getText().toString());
						break;
				}
				validateButton();



			}
		});


		//For Make and geyser
		radBtnMake_geyser_Make = new RadioButton[makeOptions.length];
		for(int i = 0; i< makeOptions.length; i++)
		{
			radBtnMake_geyser_Make[i] = new RadioButton(getActivity());
			radBtnMake_geyser_Make[i].setLayoutParams(new RadioGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			radBtnMake_geyser_Make[i].setText(Constants.LABEL_BLANK+ makeOptions[i].toUpperCase());
			radBtnMake_geyser_Make[i].setTextColor(Color.BLACK);
			radBtnMake_geyser_Make[i].setId(makeOptionsIds[i]);
			Log.e("Make And Geyser", "DateTime:-"+Utility.getcurrentTimeStamp()+"Name:-"+radBtnMake_geyser_Make[i].getText()+"id :-"+radBtnMake_geyser_Make[i].getId());
			radBtnMake_geyser_Make[i].setTextSize(15);
			radGroupMake_geyser_Make.addView(radBtnMake_geyser_Make[i]);
		}
		radGroupMake_geyser_Make.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {

				btnMake.setText(makeOptions[checkedId-1]);
				make = checkedId-1;

				if(makeOptions[checkedId-1].equalsIgnoreCase("Other")){
					edtMGOthers.setVisibility(View.VISIBLE);
					errTxtMake.setVisibility(View.INVISIBLE);

				}else if(makeOptions[checkedId-1].equalsIgnoreCase("Both")){
					edtMGOthers.setVisibility(View.VISIBLE);
					errTxtMake.setVisibility(View.INVISIBLE);
				}else{
					edtMGOthers.setVisibility(View.INVISIBLE);
					errTxtMake.setVisibility(View.VISIBLE);
				}

				MakeGeyserVO.setMakeId(makeOptionsIds[checkedId-1]);
				MakeGeyserVO.setMakeValue(makeOptions[checkedId-1]);
				MakeGeyserVO.setMakeotherText(edtMGOthers.getText().toString());
				validateButton();

			}
		});


		//For Geyser Type
		radBtnMGeyserType = new RadioButton[geyserOptions.length];
		for(int i = 0; i< geyserOptions.length; i++)
		{
			radBtnMGeyserType[i] = new RadioButton(getActivity());
			radBtnMGeyserType[i].setLayoutParams(new RadioGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			radBtnMGeyserType[i].setText(Constants.LABEL_BLANK+ geyserOptions[i].toUpperCase());
			radBtnMGeyserType[i].setTextColor(Color.BLACK);
			radBtnMGeyserType[i].setId(geyserOptionsIds[i]);
			Log.e("Make And Geyser", "DateTime:-"+Utility.getcurrentTimeStamp()+"Name:-"+radBtnMGeyserType[i].getText()+"id :-"+radBtnMGeyserType[i].getId());
			radBtnMGeyserType[i].setTextSize(15);
			radGroupMGeyserType.addView(radBtnMGeyserType[i]);
		}
		radGroupMGeyserType.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {

				btnGeyserType.setText(geyserOptions[checkedId - 1]);
				geyserType = checkedId - 1;

				MakeGeyserVO.setGeyserTypeId(geyserOptionsIds[checkedId - 1]);
				MakeGeyserVO.setGeyserTypeValue(geyserOptions[checkedId - 1]);
				Log.e("GeyserTypeID:--", "DateTime:-"+Utility.getcurrentTimeStamp()+""+MakeGeyserVO.getGeyserTypeId()+"Name :-"+MakeGeyserVO.getGeyserTypeValue());
				validateButton();

			}
		});

		if((custStatusID != userMasterService.getStatusId("OP") && custStatusID != userMasterService.getStatusId("HCL1")) || user.equals("admin")){
			makeGeyerDisableViews();
		}
		initializeValuesMakeNgeyser();
//		btnMake.setOnClickListener(new OnClickListener(){
//			public void onClick(View v) {
//				try{
//					new AlertDialog.Builder(getActivity()).setSingleChoiceItems(makeOptions,make, new DialogInterface.OnClickListener(){
//						public void onClick(DialogInterface dialog, int whichButton){
//							btnMake.setText(makeOptions[whichButton]);
//							make = whichButton;
//
//							if(makeOptions[whichButton].equals("Other")){
//								edtMGOthers.setVisibility(View.VISIBLE);
//								errTxtMake.setVisibility(View.INVISIBLE);
//
//							}else if(makeOptions[whichButton].equals("Both")){
//								edtMGOthers.setVisibility(View.VISIBLE);
//								errTxtMake.setVisibility(View.INVISIBLE);
//							}else{
//								edtMGOthers.setVisibility(View.INVISIBLE);
//								errTxtMake.setVisibility(View.VISIBLE);
//							}
//
//							MakeGeyserVO.setMakeId(makeOptionsIds[whichButton]);
//							MakeGeyserVO.setMakeValue(makeOptions[whichButton]);
//							MakeGeyserVO.setMakeotherText(edtMGOthers.getText().toString());
//							dialog.dismiss();
//							validateButton();
//						}
//					})
//					.show();
//				}catch (Exception e){
//					e.printStackTrace();
//				}
//			}
//		});
//
//		btnGeyserType.setOnClickListener(new OnClickListener(){
//			public void onClick(View v) {
//				try{
//					new AlertDialog.Builder(getActivity()).setSingleChoiceItems(geyserOptions,geyserType, new DialogInterface.OnClickListener(){
//						public void onClick(DialogInterface dialog, int whichButton) {
//
//							btnGeyserType.setText(geyserOptions[whichButton]);
//							geyserType = whichButton;
//
//							MakeGeyserVO.setGeyserTypeId(geyserOptionsIds[whichButton]);
//							MakeGeyserVO.setGeyserTypeValue(geyserOptions[whichButton]);
//							Log.e("GeyserTypeID:--",""+MakeGeyserVO.getGeyserTypeId()+"Name :-"+MakeGeyserVO.getGeyserTypeValue());
//							validateButton();
//							dialog.dismiss();
//						}
//					})
//					.show();
//				}catch (Exception e){
//					e.printStackTrace();
//				}
//			}
//		});

		radGroupMGeyserInsideBathroom.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch(checkedId)
				{
					case R.id.radBtnGeyserInYes:
						btnGeyserInBathroom.setText(radBtnGeyserInYes.getText().toString());
						MakeGeyserVO.setIsInsideBathroom(radBtnGeyserInYes.getText().toString());
						break;

					case R.id.radBtnGeyserInNo:
						btnGeyserInBathroom.setText(radBtnGeyserInNo.getText().toString());
						MakeGeyserVO.setIsInsideBathroom(radBtnGeyserInNo.getText().toString());
						break;
				}
				validateButton();

			}
		});

//		btnGeyserInBathroom.setOnClickListener(new OnClickListener(){
//			public void onClick(View v) {
//				try{
//					new AlertDialog.Builder(getActivity()).setSingleChoiceItems(dropdownOptions,geyserInBathroom, new DialogInterface.OnClickListener(){
//						public void onClick(DialogInterface dialog, int whichButton) {
//
//							btnGeyserInBathroom.setText(dropdownOptions[whichButton]);
//							geyserInBathroom = whichButton;
//							MakeGeyserVO.setIsInsideBathroom(dropdownOptions[whichButton]);
//							validateButton();
//							dialog.dismiss();
//						}
//					})
//					.show();
//				}catch (Exception e){
//					e.printStackTrace();
//				}
//			}
//		});

		btnSaveandNext.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				validateButton();
				Utility.changeSelectedForm(7, 6);
				Utility.changeList(7,false);
				OtherChecksFragment otherFragment = OtherChecksFragment.newInstance(custStatusID, contactNumber);
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.frmLayoutFragment, otherFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		});

		btnPrev.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Utility.changeSelectedForm(5, 6);
				Utility.changeList(5,false);
				OtherSurakshaTubeFragment surakshaFragment = OtherSurakshaTubeFragment.newInstance(custStatusID, contactNumber);
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.frmLayoutFragment, surakshaFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		});
		return mngView;
	}


	/**
	 * Validation method to validate control. If any error ,it will display on error text field.
	 * @return void.
	 */
	public void validateButton(){

		ArrayList<Boolean> errorList = new ArrayList<Boolean>();

		Log.d("VALIDATE BTN:-","Other TExt:-"+edtMGOthers.getText().toString());

		strGeyserAvailable = MakeGeyserVO.getIsGeyserAvailable();

//		if(strGeyserAvailable.equals(getResources().getString(R.string.title_select_option)))
//		{
//			errorList.add(true);
//			errTxtMake.setText(Constants.ERROR_MARK);
//		}else{
//			errTxtMake.setText(Constants.ERROR_MARK);
//			errorList.add(false);
//		}

		if(strGeyserAvailable.equals("YES"))
		{

			if(!edtMGOthers.getText().toString().equals(""))
			{
				MakeGeyserVO.setMakeotherText(edtMGOthers.getText().toString());
			}else{
				MakeGeyserVO.setMakeotherText(MakeGeyserVO.getMakeotherText());
			}


			if(btnMake.getText().toString().equals(getResources().getString(R.string.title_select_option))){
				errorList.add(true);
				errTxtMake.setText(Constants.ERROR_MARK);
			}else{
				//			errTxtMake.setText(Constants.LABEL_BLANK);
				errTxtMake.setText(Constants.ERROR_MARK);
				errorList.add(false);
			}

			if(btnGeyserType.getText().toString().equals(getResources().getString(R.string.title_select_option))){
				errorList.add(true);
				errTxtType.setText(Constants.ERROR_MARK);
			}else{
				//			errTxtType.setText(Constants.LABEL_BLANK);
				errTxtType.setText(Constants.ERROR_MARK);
				errorList.add(false);
			}

			if(btnGeyserInBathroom.getText().toString().equals(getResources().getString(R.string.title_select_option))){
				errorList.add(true);
				errTxtInside.setText(Constants.ERROR_MARK);
			}else{
				//			errTxtInside.setText(Constants.LABEL_BLANK);
				errTxtInside.setText(Constants.ERROR_MARK);
				errorList.add(false);
			}
		} else {
			errorList.add(false);
		}
		MakeGeyserVO.setIsGeyserAvailable(MakeGeyserVO.getIsGeyserAvailable());
		MakeGeyserVO.setMakeValue(MakeGeyserVO.getMakeValue());
		MakeGeyserVO.setGeyserTypeValue(MakeGeyserVO.getGeyserTypeValue());
		MakeGeyserVO.setMakeId(MakeGeyserVO.getMakeId());//(btnMake.getText().toString());
		MakeGeyserVO.setGeyserTypeId(MakeGeyserVO.getGeyserTypeId());//(btnGeyserType.getText().toString());
		MakeGeyserVO.setIsInsideBathroom(MakeGeyserVO.getIsInsideBathroom());
		Log.d("VALIDATE BTN after:-","Make Other TExt:-"+MakeGeyserVO.getMakeotherText());
		MakeGeyserVO.setMakeotherText(MakeGeyserVO.getMakeotherText());

		Collections.sort(errorList);
		System.err.println(" Correction Error --- "+ Collections.binarySearch(errorList,true));
		if(Collections.binarySearch(errorList,true) >= 0)
			Utility.changeList(6,true);
		else
			Utility.changeList(6,false);

	}

	@Override
	public void onPause() {
		super.onPause();
		validateButton();
	}

	/**
	 * Method to disable all controls when open form in edit mode.
	 * @return void
	 */
	public void makeGeyerDisableViews(){
		btnMake.setEnabled(false);
		btnGeyserType.setEnabled(false);
		btnGeyserInBathroom.setEnabled(false);
		radBtnGeyserInNo.setEnabled(false);
		radBtnGeyserInYes.setEnabled(false);
		radBtnGeyserAvailableYes.setEnabled(false);
		radBtnGeyserAvailableNo.setEnabled(false);
		edtMGOthers.setEnabled(false);
		if(radBtnMake_geyser_Make.length > 0)
		{
			for(int i = 0; i< makeOptions.length; i++)
			{
				radBtnMake_geyser_Make[i].setEnabled(false);
			}
		}
		if(radBtnMGeyserType.length > 0)
		{
			for(int i = 0; i< geyserOptions.length; i++)
			{
				radBtnMGeyserType[i].setEnabled(false);
			}
		}
	}

	/**
	 * Method used to set values for field and controls.
	 * @return void
	 *
	 */
	private void initializeValuesMakeNgeyser(){

		strGeyserAvailable = MakeGeyserVO.getIsGeyserAvailable();
		if(strGeyserAvailable.equals(Constants.LABEL_BLANK))
		{
			strGeyserAvailable = getResources().getString(R.string.title_select_option);
			MakeGeyserVO.setIsGeyserAvailable(strGeyserAvailable);
			radBtnGeyserAvailableYes.setChecked(false);
			radBtnGeyserAvailableNo.setChecked(false);
		}else
		{
			if("YES".equals(MakeGeyserVO.getIsGeyserAvailable()))
			{
				radBtnGeyserAvailableYes.setChecked(true);
				radBtnGeyserAvailableNo.setChecked(false);
				llgeyser.setVisibility(View.VISIBLE);
				llgeyserDetail.setVisibility(View.VISIBLE);
				errmessage.setVisibility(View.VISIBLE);
			}
			else if("NO".equals(MakeGeyserVO.getIsGeyserAvailable()))
			{
				radBtnGeyserAvailableYes.setChecked(false);
				radBtnGeyserAvailableNo.setChecked(true);
				llgeyser.setVisibility(View.GONE);
				llgeyserDetail.setVisibility(View.GONE);
				errmessage.setVisibility(View.INVISIBLE);
			}
		}

		strMake = MakeGeyserVO.getMakeValue();

		if(strMake.equals(Constants.LABEL_BLANK)){
			strMake = getResources().getString(R.string.title_select_option);
			btnMake.setText(strMake);
			make = -1;
		}else{
			btnMake.setText(strMake);
			make = MakeGeyserVO.getMakeId() - 1;

			System.out.println(" MAKE ID "+make);
			System.out.println("Make text :--"+MakeGeyserVO.getMakeotherText());
			if(MakeGeyserVO.getMakeValue().equals("Other") || MakeGeyserVO.getMakeValue().equals("Both"))
			{
				edtMGOthers.setVisibility(View.VISIBLE);
				edtMGOthers.setText(MakeGeyserVO.getMakeotherText());
			}
			if(make >= 0 && make < radBtnMake_geyser_Make.length){
				radBtnMake_geyser_Make[make].setChecked(true);
			}

			//MakeGeyserVO.setMakeotherText(MakeGeyserVO.getMakeotherText());
		}

		strGeysertype = MakeGeyserVO.getGeyserTypeValue();

		if(strGeysertype.equals(Constants.LABEL_BLANK)){
			strGeysertype = getResources().getString(R.string.title_select_option);
			btnGeyserType.setText(strGeysertype);
			//make = -1;
			geyserType = -1;
		}else{
			btnGeyserType.setText(strGeysertype);
			//	make = MakeGeyserVO.getGeyserTypeId();
			geyserType = MakeGeyserVO.getGeyserTypeId() - 1;
			radBtnMGeyserType[geyserType].setChecked(true);
		}

		strGyserInBathroom = MakeGeyserVO.getIsInsideBathroom();

		if(strGyserInBathroom.equals(Constants.LABEL_BLANK)){
			strGyserInBathroom = getResources().getString(R.string.title_select_option);
			btnGeyserInBathroom.setText(strGyserInBathroom);
			radBtnGeyserInNo.setChecked(false);
			radBtnGeyserInYes.setChecked(false);
		}else{
			btnGeyserInBathroom.setText(MakeGeyserVO.getIsInsideBathroom());
			if("YES".equals(MakeGeyserVO.getIsInsideBathroom()))
			{
				geyserInBathroom = 0;
				radBtnGeyserInNo.setChecked(false);
				radBtnGeyserInYes.setChecked(true);
			}
			else if("NO".equals(MakeGeyserVO.getIsInsideBathroom()))
			{
				geyserInBathroom = 1;
				radBtnGeyserInNo.setChecked(true);
				radBtnGeyserInYes.setChecked(false);
			}

		}
		validateButton();
	}

	/**
	 * Method used to change label values as per parameter to English and Gujarati.
	 * @param id : 1 - English , 2 - Gujarati
	 */
	public void changeLanguage(int id){

		id = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.PREF_LANGUAGE,Constants.LANGUAGE_ENGLISH);
		switch (id) {
			case Constants.LANGUAGE_ENGLISH:
				lblGeyserAvailable.setText(getResources().getText(R.string.title_make_Geyser_IsGeyserAvailable_Eng));
				lblGeyserInBathroom.setText(getResources().getText(R.string.title_make_Geyser_GeyserInsideBathroom_Eng));
				lblGeyserType.setText(getResources().getText(R.string.title_make_Geyser_GeyserType_Eng));
				lblMake.setText(getResources().getText(R.string.title_make_geyser_Make_Eng));
				lblMakeNGeyserHeader.setText(getResources().getText(R.string.header_Make_Geyser_Eng));
				break;

			case Constants.LANGUAGE_GUJRATI:
				lblGeyserInBathroom.setTypeface(Global.typeface_Guj);
				lblGeyserType.setTypeface(Global.typeface_Guj);
				lblMake.setTypeface(Global.typeface_Guj);
				lblMakeNGeyserHeader.setTypeface(Global.typeface_Guj);
				lblGeyserAvailable.setTypeface(Global.typeface_Guj);
				lblGeyserAvailable.setText(getResources().getText(R.string.title_make_Geyser_IsGeyserAvailable_Guj));
				lblGeyserInBathroom.setText(getResources().getText(R.string.title_make_Geyser_GeyserInsideBathroom_Guj));
				lblGeyserType.setText(getResources().getText(R.string.title_make_Geyser_GeyserType_Guj));
				lblMake.setText(getResources().getText(R.string.title_make_geyser_Make_Guj));
				lblMakeNGeyserHeader.setText(getResources().getText(R.string.header_Make_Geyser_Guj));
				break;

			default:
				break;
		}
	}
}