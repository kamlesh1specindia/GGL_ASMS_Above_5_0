package com.spec.asms.view.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.service.UserMasterService;

/**
 * A class is used to display SurkshaTube form.  It has methods to set values in form's fields.
 * @author jenisha
 *
 */
public class SurakshaTubeFragment extends Fragment
{
	private View srkshaTbView;

	private Button btnSaveandNext;
	private Button btnPrev;
	private Button btnIsReplaced;
	private EditText edtSize1;
	private EditText edtSize2;	
	private EditText edtSize3;
	private EditText edtSize4;
	private EditText edtSize5;
	private EditText edtSize6;
	private TextView lblIsReplaced;
	private TextView lblSize1;
	private TextView lblSize2;
	private TextView lblSize3;
	private TextView lblSize4;
	private TextView lblSize5;
	private TextView lblSize6;
	private TextView errTxtReplaced;
	private TextView errSize1;
	private TextView errSize2;
	private TextView errSize3;
	private TextView errSize4;
	private TextView errSize5;
	private TextView errSize6;
	private TextView lblSurakshaHeader;
	private int isSurakshaTubeReplaced=-1;
	private int id;
	private int custStatusId;
	private String surakshaTubeoption;
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
	/**
	 * A parameterized constructor 
	 * @param customer Status id: the id for which the profile is to be created
	 * @return SurkshTube Fragment object
	 */
	public static SurakshaTubeFragment newInstance(int custStatusId){
		SurakshaTubeFragment surakshaTubeFragment = new SurakshaTubeFragment();
		Bundle args = new Bundle();
		args.putInt("statusid", custStatusId);
		surakshaTubeFragment.setArguments(args);
		return surakshaTubeFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::SurakshaTubeFragmentStarted:::");
		srkshaTbView = inflater.inflate(R.layout.form_surakshatube,null);
		fragmentManager = getFragmentManager();
		custStatusId = getArguments().getInt("statusid");
		userMasterService = new UserMasterService(getActivity());
		user = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.DB_USERNAME, "tech");
		btnSaveandNext = (Button)srkshaTbView.findViewById(R.id.btnSurakshaSaveandNext);
		btnPrev  = (Button)srkshaTbView.findViewById(R.id.btnSurakshaPrev);
		btnIsReplaced = (Button)srkshaTbView.findViewById(R.id.btnSurakshaTubeReplaced);
		
		rltHeader2 = (RelativeLayout) srkshaTbView.findViewById(R.id.rltHeader2);
		lnHeader2 = (LinearLayout) srkshaTbView.findViewById(R.id.lnHeader2);
		radGroupSurakshaTubeReplaced = (RadioGroup) srkshaTbView.findViewById(R.id.radGroupSurakshaTubeReplaced);
		lblIsReplaced = (TextView)srkshaTbView.findViewById(R.id.lblsurakshaTubeReplaced);
		lblSurakshaHeader = (TextView)srkshaTbView.findViewById(R.id.lblSurakshaTubeHeader);
		
		llSurkshaTube = (LinearLayout) srkshaTbView.findViewById(R.id.llSurkshaTube);
		llSurkshaTubeDetail = (LinearLayout) srkshaTbView.findViewById(R.id.llSurkshaTubeDetail);
		
   radGroupSurakshaTubeReplaced.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				switch (checkedId) {

				case R.id.radBtnYes:
					//btnIsReplaced.setText(radBtnYes.getText().toString());
					//SurakshaTubeVO.setIsReplaced(radBtnYes.getText().toString());

			/*		edtSize1.setEnabled(true);
					edtSize2.setEnabled(true);
					edtSize3.setEnabled(true);
					edtSize6.setEnabled(true);*/
					llSurkshaTube.setVisibility(View.VISIBLE);
					llSurkshaTubeDetail.setVisibility(View.VISIBLE);
					rltHeader2.setVisibility(View.GONE);
					lnHeader2.setVisibility(View.GONE);
					break;

				case R.id.radBtnNo:
					///btnIsReplaced.setText(radBtnNo.getText().toString());
					//SurakshaTubeVO.setIsReplaced(radBtnNo.getText().toString());

					/*edtSize1.setEnabled(false);
					edtSize2.setEnabled(false);
					edtSize3.setEnabled(false);
					edtSize6.setEnabled(false);
					edtSize1.setText(Constants.LABEL_BLANK);
					edtSize2.setText(Constants.LABEL_BLANK);				    
					edtSize3.setText(Constants.LABEL_BLANK);
					edtSize6.setText(Constants.LABEL_BLANK);*/
					
					llSurkshaTube.setVisibility(View.GONE);
					llSurkshaTubeDetail.setVisibility(View.GONE);
					rltHeader2.setVisibility(View.VISIBLE);
					lnHeader2.setVisibility(View.VISIBLE);
					break;
				}
				//validateButton();
			}
		});
		
		/*edtSize1 = (EditText)srkshaTbView.findViewById(R.id.edtSurakshaTube7_6mm1Meter);
		edtSize2 = (EditText)srkshaTbView.findViewById(R.id.edtSurakshaTube7_6mm1_5Meter);	        
		edtSize3 = (EditText)srkshaTbView.findViewById(R.id.edtSurakshaTube12_5mm1_5Meter);
		edtSize4 = (EditText)srkshaTbView.findViewById(R.id.edtSurakshaTubeClampHose8mmPipe);
		edtSize5 = (EditText)srkshaTbView.findViewById(R.id.edtSurakshaTubeClampHose20mmPipe);
		edtSize6 = (EditText)srkshaTbView.findViewById(R.id.edtSurakshaTube12_5mm1Meter);
		lblIsReplaced = (TextView)srkshaTbView.findViewById(R.id.lblsurakshaTubeReplaced);
		lblSurakshaHeader = (TextView)srkshaTbView.findViewById(R.id.lblSurakshaTubeHeader);
		lblSize1 = (TextView)srkshaTbView.findViewById(R.id.lblSurakshaTube7_5mm1Meter);
		lblSize2 = (TextView)srkshaTbView.findViewById(R.id.lblSurakshaTube7_9mm1_5Meter);
		lblSize3 = (TextView)srkshaTbView.findViewById(R.id.lblSurakshaTube12_5mm1_5Meter);
		lblSize4 = (TextView)srkshaTbView.findViewById(R.id.lblSurakshaTubeClampHose8mmPipe);
		lblSize5 = (TextView)srkshaTbView.findViewById(R.id.lblSurakshaTubeClampHose20mmpipe);
		lblSize6 = (TextView) srkshaTbView.findViewById(R.id.lblSurakshaTube12_5mm1Meter);
		errSize1 = (TextView)srkshaTbView.findViewById(R.id.errSurakshaTube7_6mm1Meter);
		errSize2 = (TextView)srkshaTbView.findViewById(R.id.errSurakshaTube7_6mm1_5Meter);
		errSize3 = (TextView)srkshaTbView.findViewById(R.id.errSurakshaTube12_5mm1_5Meter);
		errSize4 = (TextView)srkshaTbView.findViewById(R.id.errSurakshaTubeClampHose8mmPipe);
		errSize5 = (TextView)srkshaTbView.findViewById(R.id.errSurakshaTubeClampHose20mmPipe);
		errSize6 = (TextView) srkshaTbView.findViewById(R.id.errSurakshaTube12_5mm1Meter);
		errTxtReplaced = (TextView)srkshaTbView.findViewById(R.id.errSurakshaTubeReplaced);

		
		radBtnNo = (RadioButton) srkshaTbView.findViewById(R.id.radBtnNo);
		radBtnYes = (RadioButton) srkshaTbView.findViewById(R.id.radBtnYes);
		
		llSurkshaTube = (LinearLayout) srkshaTbView.findViewById(R.id.llSurkshaTube);
		llSurkshaTubeDetail = (LinearLayout) srkshaTbView.findViewById(R.id.llSurkshaTubeDetail);

		isReplaced = btnIsReplaced.getText().toString();
		final String[] dropdownOptions = getResources().getStringArray(R.array.arrayCheckBoolean);
		changeLanguage(id);

		if(custStatusId != userMasterService.getStatusId("OP") || user.equals("admin")){
			surkshatubeDisableViews();
		}

		if(Global.isCycleRunning)
			MaintanenceForm.btnHeaderProcess.setVisibility(View.VISIBLE);
		else
			MaintanenceForm.btnHeaderProcess.setVisibility(View.INVISIBLE);
		
		
		
		//initializeValuesSuraksha();

		btnIsReplaced.setOnClickListener(new OnClickListener(){
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
								edtSize6.setEnabled(true);

							}else{
								edtSize1.setEnabled(false);
								edtSize2.setEnabled(false);
								edtSize3.setEnabled(false);
								edtSize6.setEnabled(false);
								edtSize1.setText(Constants.LABEL_BLANK);
								edtSize2.setText(Constants.LABEL_BLANK);				    
								edtSize3.setText(Constants.LABEL_BLANK);
								edtSize6.setText(Constants.LABEL_BLANK);
							}
							SurakshaTubeVO.setIsReplaced(dropdownOptions[whichButton]);
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

		btnSaveandNext.setOnClickListener(new OnClickListener() {				
			public void onClick(View v) {				

				Utility.changeSelectedForm(5, 4);
				Utility.changeList(5,false);
				MakeNGeyserFragment mkNgyFragment = MakeNGeyserFragment.newInstance(custStatusId);				
				FragmentTransaction ft = fragmentManager.beginTransaction(); 
				ft.replace(R.id.frmLayoutFragment, mkNgyFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		});


		btnPrev.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				Utility.changeSelectedForm(3, 4);
				Utility.changeList(3,false);
				KitchenSurakshaTubeFragment rccFragment = KitchenSurakshaTubeFragment.newInstance(custStatusId);				
				FragmentTransaction ft = fragmentManager.beginTransaction(); 
				ft.replace(R.id.frmLayoutFragment, rccFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		});*/
		return srkshaTbView;
	}

/*	*//**
	 * Method to disable all controls when open form in edit mode.
	 * @return void
	 *//*
	public void surkshatubeDisableViews(){
		btnIsReplaced.setEnabled(false);
				edtSize1.setEnabled(false);
				edtSize2.setEnabled(false);		    
				edtSize3.setEnabled(false);  
				edtSize6.setEnabled(false);
				
		edtSize4.setEnabled(false);
		edtSize5.setEnabled(false);
		radBtnNo.setEnabled(false);
		radBtnYes.setEnabled(false);
		llSurkshaTube.setVisibility(View.GONE);
		llSurkshaTubeDetail.setVisibility(View.GONE);
		
	}
	*//**
	 * Validation method to validate control. If any error ,it will display on error text field.
	 * @return void.
	 *//*
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
			errorList.add(false);
			errTxtReplaced.setText(Constants.ERROR_MARK);
//			errTxtReplaced.setText(Constants.LABEL_BLANK);
		}

		Collections.sort(errorList);
		System.err.println(" Correction Error --- "+ Collections.binarySearch(errorList,true));
		if(Collections.binarySearch(errorList,true) >= 0)
			Utility.changeList(4,true);
		else
			Utility.changeList(4,false);
	}

	*//**
	 * Method used to set values for field and controls.
	 * @return void
	 * 
	 *//*
	private void initializeValuesSuraksha(){

		errSize1.setText(Utility.surakshaTube(Constants.SIZE1,edtSize1.getText().toString(),getActivity()));
		errSize2.setText(Utility.surakshaTube(Constants.SIZE2,edtSize2.getText().toString(),getActivity()));
		errSize3.setText(Utility.surakshaTube(Constants.SIZE3,edtSize3.getText().toString(),getActivity()));
		errSize4.setText(Utility.surakshaTube(Constants.SIZE4,edtSize4.getText().toString(),getActivity()));
		errSize5.setText(Utility.surakshaTube(Constants.SIZE5,edtSize5.getText().toString(),getActivity()));
		errSize6.setText(Utility.surakshaTube(Constants.SIZE6, edtSize6.getText().toString(), getActivity()));
		
		surakshaTubeoption = SurakshaTubeVO.getIsReplaced();
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
				edtSize1.setEnabled(false);
				edtSize2.setEnabled(false);
				edtSize3.setEnabled(false);
				edtSize6.setEnabled(false);
			}else{
				edtSize1.setEnabled(true);
				edtSize2.setEnabled(true);
				edtSize3.setEnabled(true);
				edtSize6.setEnabled(true);
			}
		}else if(surakshaTubeoption.equals("NO")){
			edtSize1.setEnabled(false);
			edtSize2.setEnabled(false);
			edtSize3.setEnabled(false);
			edtSize6.setEnabled(false);
		}

		edtSize1.setText(SurakshaTubeVO.getSize1());
		edtSize2.setText(SurakshaTubeVO.getSize2());		    
		edtSize3.setText(SurakshaTubeVO.getSize3());  
		edtSize4.setText(SurakshaTubeVO.getClampSize1());
		edtSize5.setText(SurakshaTubeVO.getClamSize2());
		edtSize6.setText(SurakshaTubeVO.getSize4());

		validateButton();

	}
	*//**
	 * Method used to change label values as per parameter to English and Gujarati.
	 * @param id : 1 - English , 2 - Gujarati
	 *//*
	public void changeLanguage(int id){

		id = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.PREF_LANGUAGE,Constants.LANGUAGE_ENGLISH);

		switch (id){
		case Constants.LANGUAGE_ENGLISH:
			lblIsReplaced.setText(getResources().getText(R.string.title_surakshaTube_Replaced_Eng));
			lblSize1.setText(getResources().getText(R.string.title_surakshaTube_7_5_mm_1_meter_Eng));
			lblSize2.setText(getResources().getText(R.string.title_surakshaTube_7_9mm_1_5_Meter_Eng));
			lblSize3.setText(getResources().getText(R.string.title_surakshaTube_12_5mm_1_5_Meter_Eng));
			lblSize4.setText(getResources().getText(R.string.title_surakshaTube_ClampHose_8mm_Pipe_Eng));
			lblSize5.setText(getResources().getText(R.string.title_surakshaTube_ClampHose_20mm_pipe_Eng));
			lblSurakshaHeader.setText(getResources().getText(R.string.header_SurakshaTube_Eng));
			lblSize6.setText(getResources().getText(R.string.title_surakshaTube_12_5mm_1_Meter_Eng));
			break;

		case Constants.LANGUAGE_GUJRATI:
			lblIsReplaced.setTypeface(Global.typeface_Guj);
			lblSize1.setTypeface(Global.typeface_Guj);
			lblSize2.setTypeface(Global.typeface_Guj);
			lblSize3.setTypeface(Global.typeface_Guj);
			lblSize4.setTypeface(Global.typeface_Guj);
			lblSize5.setTypeface(Global.typeface_Guj);
			lblSize6.setTypeface(Global.typeface_Guj);
			lblSurakshaHeader.setTypeface(Global.typeface_Guj);
			lblSurakshaHeader.setText(getResources().getText(R.string.header_SurakshaTube_Guj));
			lblIsReplaced.setText(getResources().getText(R.string.title_surakshaTube_Replaced_Guj));
			lblSize1.setText(getResources().getText(R.string.title_surakshaTube_7_5_mm_1_meter_Guj));
			lblSize2.setText(getResources().getText(R.string.title_surakshaTube_7_9mm_1_5_Meter_Guj));
			lblSize3.setText(getResources().getText(R.string.title_surakshaTube_12_5mm_1_5_Meter_Guj));
			lblSize4.setText(getResources().getText(R.string.title_surakshaTube_ClampHose_8mm_Pipe_Guj));
			lblSize5.setText(getResources().getText(R.string.title_surakshaTube_ClampHose_20mm_pipe_Guj));
			lblSize6.setText(getResources().getString(R.string.title_surakshaTube_12_5mm_1_Meter_Guj));

			break;

		default:
			break;
		}
	}

	public void clearValues()
	{
		try{
			if(edtSize1 != null && edtSize2 != null && edtSize3 != null && edtSize4 != null && edtSize5 != null )
			{
				edtSize1.setText(Constants.LABEL_BLANK);
				edtSize2.setText(Constants.LABEL_BLANK);				    
				edtSize3.setText(Constants.LABEL_BLANK);
				edtSize4.setText(Constants.LABEL_BLANK);
				edtSize5.setText(Constants.LABEL_BLANK);
				edtSize6.setText(Constants.LABEL_BLANK);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/*public void setValues()
	{
		try{
			SurakshaTubeVO.setSize1(edtSize1.getText().toString());
			SurakshaTubeVO.setSize2(edtSize2.getText().toString());				    
			SurakshaTubeVO.setSize3(edtSize3.getText().toString());
			SurakshaTubeVO.setClampSize1(edtSize4.getText().toString());
			SurakshaTubeVO.setClamSize2(edtSize5.getText().toString());
			SurakshaTubeVO.setSize4(edtSize6.getText().toString());
			
			clearValues();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	@Override
	public void onPause() {
		super.onPause();
		/*try{
		if("OP".equals(TestingFragment.testingStatusCode))
		{
			validateButton();
			setValues();
		}
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,"SurakshaTubeFragment:"+ Utility.convertExceptionToString(e));
		}*/
	}
}