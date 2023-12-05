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
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.vo.PaintingVO;

/**
 * A class is used to display Painting form.  It has methods to set values in form's fields.
 * @author jenisha
 *
 */
public class PaintingFragment extends Fragment{


	/** Called when the activity is first created. */

	private View paintingORing;

	private Button btnPaintSetisfactory;
	private Button btnORingSetisfectory;
	private Button btnNext;
	private Button btnPrev;

	private EditText edtORingforMeter;
	private EditText edtORingforDOM;
	private EditText edtORingforAudco;
	private EditText edtPaint;

	private int isPaintSatisfactory = -1;
	private int isORingSatisfactory = -1;
	private int id;
	public int custStatusid;

	private String  paintOption;
	private String  oRingOption;

	private TextView lblPaintingHeader;
	private TextView lblPaintingPaintSetisfectory;
	private TextView lblPaint;
	private TextView lblOringSetisfectory;
	private TextView lblOringForMeter;
	private TextView lblOringForDOM;
	private TextView lblOringForAudco;
	private TextView errPaint;
	private TextView errOringForMeter;
	private TextView errOringForDOM;
	private TextView errOringForAudco;
	private TextView errPaintStsfcry;
	private TextView errORinfStsfcrty;

	private List<String> dropdownValues ;

	private FragmentManager fragmentManager;
	public static StringBuffer errorMsg = new StringBuffer();
	public UserMasterService userMasterService;
	private String user = Constants.LABEL_BLANK;

	private RadioGroup radGroupPaintingPaintSetisfactory;
	private RadioButton radBtnYes,radBtnNo;

	private RadioGroup radGroupPaintingORingSetisfactory;
	private RadioButton radBtnORingYes,radBtnORingNo;

	private LinearLayout llPaintingPaint;
	private RelativeLayout relPaintingPaint;

	private LinearLayout llOring,llOringDetail;
	private String contactNumber;
	/**
	 * A parameterized constructor
	 * @param customer Status id: the id for which the profile is to be created
	 * @return Painting Fragment object
	 */
	public static PaintingFragment newInstance(int statusid, String contactNumber) {
		PaintingFragment paintingFragment = new PaintingFragment();
		Bundle args = new Bundle();
		args.putInt("custStatusid",statusid);
		args.putString("contactNumber", contactNumber);
		paintingFragment.setArguments(args);
		return paintingFragment;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::PaintingFragmentStarted:::");
		paintingORing = inflater.inflate(R.layout.form_painting,null);
		user = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.DB_USERNAME, "tech");
		fragmentManager = getFragmentManager();
		custStatusid = getArguments().getInt("custStatusid");
		contactNumber = getArguments().getString("contactNumber");
		userMasterService = new UserMasterService(getActivity());
		final String[] dropdownOptions = getResources().getStringArray(R.array.arrayCheckBoolean);

		dropdownValues = Arrays.asList(dropdownOptions);
		btnNext = (Button)paintingORing.findViewById(R.id.btnPaintingNext);
		btnPrev = (Button)paintingORing.findViewById(R.id.btnPaintingPrev);
		lblPaintingHeader = (TextView)paintingORing.findViewById(R.id.lblPaintingHeader);
		lblPaint = (TextView)paintingORing.findViewById(R.id.lblPaintingPaint);
		lblOringSetisfectory = (TextView)paintingORing.findViewById(R.id.lblPaintingORingSetisfactory);
		lblOringForMeter = (TextView)paintingORing.findViewById(R.id.lblpaintingORINGMeter);
		lblOringForDOM = (TextView)paintingORing.findViewById(R.id.lblPaintingOringforDOMRegulator);
		lblOringForAudco = (TextView)paintingORing.findViewById(R.id.lblPaintingOringforAudcoGland);
		lblPaintingPaintSetisfectory = (TextView)paintingORing.findViewById(R.id.lblPaintingPaintSetisfactory);
		errPaint = (TextView)paintingORing.findViewById(R.id.errPaintingPaint);
		errOringForMeter = (TextView)paintingORing.findViewById(R.id.errPaintingORINGMeter);
		errOringForDOM = (TextView)paintingORing.findViewById(R.id.errPaintingOringforDOMRegulator);
		errOringForAudco = (TextView)paintingORing.findViewById(R.id.errPaintingOringforAudcoGland);
		btnPaintSetisfactory = (Button) paintingORing.findViewById(R.id.btnPaintingPaintSetisfactory);
		btnORingSetisfectory =(Button) paintingORing.findViewById(R.id.btnPaintingORingSetisfactory);
		edtPaint = (EditText) paintingORing.findViewById(R.id.edtPaintingPaint);
		edtORingforMeter = (EditText) paintingORing.findViewById(R.id.edtPaintingORINGMeter);
		edtORingforDOM = (EditText) paintingORing.findViewById(R.id.edtPaintingOringforDOMRegulator);
		edtORingforAudco = (EditText) paintingORing.findViewById(R.id.edtPaintingOringforAudcoGland);
		errPaintStsfcry = (TextView) paintingORing.findViewById(R.id.errPaintSetisfactory);
		errORinfStsfcrty = (TextView) paintingORing.findViewById(R.id.errPaintingORingSetisfactory);

		radGroupPaintingPaintSetisfactory = (RadioGroup)paintingORing.findViewById(R.id.radGroupPaintingPaintSetisfactory);
		radBtnYes = (RadioButton)paintingORing.findViewById(R.id.radBtnYes);
		radBtnNo = (RadioButton)paintingORing.findViewById(R.id.radBtnNo);

		radGroupPaintingORingSetisfactory = (RadioGroup)paintingORing.findViewById(R.id.radGroupPaintingORingSetisfactory);
		radBtnORingYes = (RadioButton)paintingORing.findViewById(R.id.radBtnOringYes);
		radBtnORingNo = (RadioButton)paintingORing.findViewById(R.id.radBtnOringNo);

		llPaintingPaint = (LinearLayout) paintingORing.findViewById(R.id.llPaintingPaint);
		relPaintingPaint = (RelativeLayout) paintingORing.findViewById(R.id.relPaintingPaint);

		llOring = (LinearLayout) paintingORing.findViewById(R.id.llOring);
		llOringDetail = (LinearLayout) paintingORing.findViewById(R.id.llOringDetail);

		checkLanguage(id);

		if((custStatusid != userMasterService.getStatusId("OP") && custStatusid != userMasterService.getStatusId("HCL1"))  || user.equals("admin")){
			paintingDisableViews();
		}

		if(Global.isCycleRunning)
			MaintanenceForm.btnHeaderProcess.setVisibility(View.VISIBLE);
		else
			MaintanenceForm.btnHeaderProcess.setVisibility(View.INVISIBLE);

		btnNext.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Utility.changeSelectedForm(3, 2);
				Utility.changeList(3,false);
				RccFragment rccFragment = RccFragment.newInstance(custStatusid, contactNumber);
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.frmLayoutFragment, rccFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		});

		btnPrev.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Utility.changeSelectedForm(1, 2);
				Utility.changeList(1,false);
				ClampingFragment clampingFragment = ClampingFragment.newInstance(custStatusid, contactNumber);
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.frmLayoutFragment, clampingFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		});

		initializeValues();

		radGroupPaintingPaintSetisfactory.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch(checkedId)
				{
					case R.id.radBtnYes :
						btnPaintSetisfactory.setText(radBtnYes.getText().toString());
						PaintingVO.setIsPaintingWork(radBtnYes.getText().toString());
						edtPaint.setEnabled(false);
						edtPaint.setText(Constants.LABEL_BLANK);
						llPaintingPaint.setVisibility(View.GONE);
						relPaintingPaint.setVisibility(View.GONE);
						break;

					case R.id.radBtnNo :
						btnPaintSetisfactory.setText(radBtnNo.getText().toString());
						PaintingVO.setIsPaintingWork(radBtnNo.getText().toString());
						edtPaint.setEnabled(true);
						llPaintingPaint.setVisibility(View.VISIBLE);
						relPaintingPaint.setVisibility(View.VISIBLE);
						break;
				}
				validateButton();

			}
		});


		btnPaintSetisfactory.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try{
					new AlertDialog.Builder(getActivity()).setSingleChoiceItems(dropdownOptions,isPaintSatisfactory, new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog, int whichButton) {

							btnPaintSetisfactory.setText(dropdownOptions[whichButton]);
							isPaintSatisfactory = whichButton;
							SharedPrefrenceUtil.setPrefrence(getActivity(),Constants.PREF_PAINT_SATISFACTORY,whichButton);
							PaintingVO.setIsPaintingWork(dropdownOptions[whichButton]);

							if(dropdownOptions[whichButton].equals("NO") ){
								llPaintingPaint.setVisibility(View.VISIBLE);
								relPaintingPaint.setVisibility(View.VISIBLE);
								edtPaint.setEnabled(true);
							}else{
								llPaintingPaint.setVisibility(View.GONE);
								relPaintingPaint.setVisibility(View.GONE);
								edtPaint.setEnabled(false);
								edtPaint.setText(Constants.LABEL_BLANK);
							}
							validateButton();
							dialog.dismiss();
						}
					})
							.show();
				}catch (Exception e) {
					e.printStackTrace();
					Log.d(Constants.DEBUG,"OtherChecksFragment:"+ Utility.convertExceptionToString(e));
				}
			}
		});

		radGroupPaintingORingSetisfactory.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch(checkedId)
				{
					case R.id.radBtnOringYes:
						btnORingSetisfectory.setText(radBtnORingYes.getText().toString());
						PaintingVO.setIsOringWork(radBtnORingYes.getText().toString());
						llOring.setVisibility(View.VISIBLE);
						llOringDetail.setVisibility(View.VISIBLE);
						edtORingforMeter.setEnabled(true);
						edtORingforDOM.setEnabled(true);
						edtORingforAudco.setEnabled(true);
						break;

					case R.id.radBtnOringNo :
						btnORingSetisfectory.setText(radBtnORingNo.getText().toString());
						PaintingVO.setIsOringWork(radBtnORingNo.getText().toString());
						llOring.setVisibility(View.GONE);
						llOringDetail.setVisibility(View.GONE);
						edtORingforMeter.setText(Constants.BLANK);
						edtORingforDOM.setText(Constants.BLANK);
						edtORingforAudco.setText(Constants.BLANK);
						edtORingforMeter.setEnabled(false);
						edtORingforDOM.setEnabled(false);
						edtORingforAudco.setEnabled(false);
						break;
				}
				validateButton();


			}
		});

		btnORingSetisfectory.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try{
					new AlertDialog.Builder(getActivity()).setSingleChoiceItems(dropdownOptions,isORingSatisfactory, new DialogInterface.OnClickListener(){

						public void onClick(DialogInterface dialog, int whichButton){
							btnORingSetisfectory.setText(dropdownOptions[whichButton]);
							isORingSatisfactory = whichButton;
							SharedPrefrenceUtil.setPrefrence(getActivity(),Constants.PREF_ORING_SATISFACTORY,whichButton);
							PaintingVO.setIsOringWork(dropdownOptions[whichButton]);


							if(dropdownOptions[whichButton].equals("YES")){
								llOring.setVisibility(View.VISIBLE);
								llOringDetail.setVisibility(View.VISIBLE);
								edtORingforMeter.setEnabled(true);
								edtORingforDOM.setEnabled(true);
								edtORingforAudco.setEnabled(true);

							}else{
								llOring.setVisibility(View.GONE);
								llOringDetail.setVisibility(View.GONE);
								edtORingforMeter.setText(Constants.BLANK);
								edtORingforDOM.setText(Constants.BLANK);
								edtORingforAudco.setText(Constants.BLANK);
								edtORingforMeter.setEnabled(false);
								edtORingforDOM.setEnabled(false);
								edtORingforAudco.setEnabled(false);

							}
							validateButton();
							dialog.dismiss();
						}
					})
							.show();
				}catch (Exception e) {
					e.printStackTrace();
					Log.d(Constants.DEBUG,"OtherChecksFragment:"+ Utility.convertExceptionToString(e));
				}
			}
		});
		return paintingORing;
	}

	/**
	 * Validation method to validate control. If any error ,it will display on error text field.
	 * @return void.
	 */
	public void validateButton(){

		ArrayList<Boolean> errorList = new ArrayList<Boolean>();

//		errPaint.setText(Utility.paintinAndOring(getActivity(),Constants.PAINT,edtPaint.getText().toString().trim()));
//
//		if(errPaint.getText().toString().equals(Constants.LABEL_BLANK))
//			errorList.add(false);
//		else
//			errorList.add(true);
//
//		errOringForMeter.setText(Utility.paintinAndOring(getActivity(),Constants.ORING_METER,edtORingforMeter.getText().toString().trim()));
//
//		if(errOringForMeter.getText().toString().equals(Constants.LABEL_BLANK))
//			errorList.add(false);
//		else
//			errorList.add(true);
//
//		errOringForDOM.setText(Utility.paintinAndOring(getActivity(),Constants.ORING_DOM,edtORingforDOM.getText().toString().trim()));
//
//		if(errOringForDOM.getText().toString().equals(Constants.LABEL_BLANK))
//			errorList.add(false);
//		else
//			errorList.add(true);
//
//		errOringForAudco.setText(Utility.paintinAndOring(getActivity(),Constants.ORING_AUDCO,edtORingforAudco.getText().toString().trim()));
//
//		if(errOringForAudco.getText().toString().equals(Constants.LABEL_BLANK))
//			errorList.add(false);
//		else
//			errorList.add(true);
//
		if(btnPaintSetisfactory.getText().toString().equals(getResources().getString(R.string.title_select_option))){
			errorList.add(true);
			errPaintStsfcry.setText(Constants.ERROR_MARK);
		}else{
			if(btnPaintSetisfactory.getText().toString().equalsIgnoreCase("no")) {
				if(edtPaint.getText().toString().equals(Constants.BLANK)) {
					errorList.add(true);
					errPaintStsfcry.setText(Constants.ERROR_MARK);
				} else {
					errorList.add(false);
					errPaintStsfcry.setText(Constants.ERROR_MARK);
				}
			}
			else {
				errorList.add(false);
				errPaintStsfcry.setText(Constants.ERROR_MARK);
			}
//			errPaintStsfcry.setText(Constants.LABEL_BLANK);
		}



		if(btnORingSetisfectory.getText().toString().equals(getResources().getString(R.string.title_select_option))){
			errorList.add(true);
			errORinfStsfcrty.setText(Constants.ERROR_MARK);
		}else{
			if(btnORingSetisfectory.getText().toString().equalsIgnoreCase("yes")) {
				if(edtORingforMeter.getText().toString().equals(Constants.BLANK) || edtORingforDOM.getText().toString().equals(Constants.BLANK) || edtORingforAudco.getText().toString().equals(Constants.BLANK)) {
					errorList.add(true);
					errORinfStsfcrty.setText(Constants.ERROR_MARK);
				} else {
					errorList.add(false);
					errORinfStsfcrty.setText(Constants.ERROR_MARK);
				}
			} else if(btnORingSetisfectory.getText().toString().equalsIgnoreCase("no")) {
				errorList.add(false);
				errORinfStsfcrty.setText(Constants.ERROR_MARK);
			}
//			errORinfStsfcrty.setText(Constants.LABEL_BLANK);
		}

		Collections.sort(errorList);
		System.err.println(" Correction Error --- "+ Collections.binarySearch(errorList,true));
		if(Collections.binarySearch(errorList,true) >= 0)
			Utility.changeList(2,true);
		else
			Utility.changeList(2,false);
	}


	public void setValues()
	{
		try{
			PaintingVO.setPaint(edtPaint.getText().toString());
			System.out.println(" TEST 1 " + edtORingforMeter.getText().toString());
			PaintingVO.setOrMeter(edtORingforMeter.getText().toString());
			System.out.println(" TEST 2 " + edtORingforDOM.getText().toString());
			PaintingVO.setOrDomRegulator(edtORingforDOM.getText().toString());
			System.out.println(" TEST 3 " + edtORingforAudco.getText().toString());
			PaintingVO.setOrAudcoGland(edtORingforAudco.getText().toString());

			clearValues();
		}catch (Exception e) {
			e.getStackTrace();
			Log.d(Constants.DEBUG,"OtherChecksFragment:"+ Utility.convertExceptionToString(e));
		}
	}

	public void clearValues()
	{
		try{
			edtPaint.setText(Constants.LABEL_BLANK);
			edtORingforMeter.setText(Constants.LABEL_BLANK);
			edtORingforDOM.setText(Constants.LABEL_BLANK);
			edtORingforAudco.setText(Constants.LABEL_BLANK);
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,"OtherChecksFragment:"+ Utility.convertExceptionToString(e));
		}
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
			Log.d(Constants.DEBUG,"OtherChecksFragment:"+ Utility.convertExceptionToString(e));
		}
	}

	/**
	 * Method to disable all controls when open form in edit mode.
	 * @return void
	 */

	public void paintingDisableViews(){

		btnPaintSetisfactory.setEnabled(false);
		btnORingSetisfectory.setEnabled(false);

		edtPaint.setEnabled(false);
		radBtnNo.setEnabled(false);
		radBtnYes.setEnabled(false);
		llPaintingPaint.setVisibility(View.GONE);
		relPaintingPaint.setVisibility(View.GONE);

		radBtnORingNo.setEnabled(false);
		radBtnORingYes.setEnabled(false);
		llOring.setVisibility(View.GONE);
		llOringDetail.setVisibility(View.GONE);
		edtORingforMeter.setEnabled(false);
		edtORingforDOM.setEnabled(false);
		edtORingforAudco.setEnabled(false);

	}

	/**
	 * Method used to set values for field and controls.
	 * @return void
	 *
	 */
	private void initializeValues(){

		errPaint.setText(Utility.paintinAndOring(getActivity(),Constants.PAINT,edtPaint.getText().toString().trim()));
		errOringForMeter.setText(Utility.paintinAndOring(getActivity(),Constants.ORING_METER,edtORingforMeter.getText().toString().trim()));
		errOringForDOM.setText(Utility.paintinAndOring(getActivity(),Constants.ORING_DOM,edtORingforDOM.getText().toString().trim()));
		errOringForAudco.setText(Utility.paintinAndOring(getActivity(),Constants.ORING_AUDCO,edtORingforAudco.getText().toString().trim()));

		paintOption = PaintingVO.getIsPaintingWork();
		oRingOption = PaintingVO.getIsOringWork();

		if(paintOption.equals(Constants.LABEL_BLANK))
		{
			paintOption = getResources().getString(R.string.title_select_option);
			radBtnNo.setChecked(false);
			radBtnYes.setChecked(false);
			llPaintingPaint.setVisibility(View.GONE);
			relPaintingPaint.setVisibility(View.GONE);
		}
		if(oRingOption.equals(Constants.LABEL_BLANK))
		{
			oRingOption = getResources().getString(R.string.title_select_option);
			radBtnORingNo.setChecked(false);
			radBtnORingYes.setChecked(false);
		}
		isPaintSatisfactory = dropdownValues.indexOf(paintOption);
		isORingSatisfactory = dropdownValues.indexOf(oRingOption);


		btnPaintSetisfactory.setText(paintOption);
		btnORingSetisfectory.setText(oRingOption);
		System.out.println(" TEST @ P "+ PaintingVO.getPaint());
		edtPaint.setText(PaintingVO.getPaint());
		System.out.println(" TEST @ A "+ PaintingVO.getOrAudcoGland());
		edtORingforAudco.setText(PaintingVO.getOrAudcoGland());

		System.out.println(" TEST @ D "+ PaintingVO.getOrDomRegulator());
		edtORingforDOM.setText(PaintingVO.getOrDomRegulator());
		System.out.println(" TEST @ M "+ PaintingVO.getOrMeter());
		edtORingforMeter.setText(PaintingVO.getOrMeter());


		if(paintOption.equals("NO")){

			radBtnNo.setChecked(true);
			radBtnYes.setChecked(false);
			llPaintingPaint.setVisibility(View.VISIBLE);
			relPaintingPaint.setVisibility(View.VISIBLE);

			if(custStatusid != userMasterService.getStatusId("OP")){
				edtPaint.setEnabled(false);

			}else{
				edtPaint.setEnabled(true);

			}

		}else if(paintOption.equals("YES")){
			edtPaint.setEnabled(false);
			radBtnNo.setChecked(false);
			radBtnYes.setChecked(true);
			llPaintingPaint.setVisibility(View.GONE);
			relPaintingPaint.setVisibility(View.GONE);
		}


		if(oRingOption.equals("YES")){

			radBtnORingNo.setChecked(false);
			radBtnORingYes.setChecked(true);

			if(custStatusid != userMasterService.getStatusId("OP")){

				edtPaint.setEnabled(false);
				edtORingforMeter.setEnabled(false);
				edtORingforDOM.setEnabled(false);
				edtORingforAudco.setEnabled(false);
				llOring.setVisibility(View.VISIBLE);
				llOringDetail.setVisibility(View.VISIBLE);
			}
			else{
				llOring.setVisibility(View.VISIBLE);
				llOringDetail.setVisibility(View.VISIBLE);
				edtORingforMeter.setEnabled(true);
				edtORingforDOM.setEnabled(true);
				edtORingforAudco.setEnabled(true);
			}
		}else if(oRingOption.equals("NO")){
			radBtnORingNo.setChecked(true);
			radBtnORingYes.setChecked(false);

			llOring.setVisibility(View.GONE);
			llOringDetail.setVisibility(View.GONE);
			edtORingforMeter.setEnabled(false);
			edtORingforDOM.setEnabled(false);
			edtORingforAudco.setEnabled(false);
		}



		validateButton();
	}

	/**
	 * Method used to change label values as per parameter to English and Gujarati.
	 * @param id : 1 - English , 2 - Gujarati
	 */
	public void checkLanguage(int id){

		id = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.PREF_LANGUAGE,Constants.LANGUAGE_ENGLISH);

		switch (id){
			case Constants.LANGUAGE_ENGLISH:
				lblPaintingHeader.setText(getResources().getText(R.string.header_painting_Eng));
				lblPaintingPaintSetisfectory.setText(getResources().getText(R.string.title_painting_PaintSetisfactory_Eng));
				lblPaint.setText(getResources().getText(R.string.title_painting_paint_Eng));
				lblOringSetisfectory.setText(getResources().getText(R.string.title_O_RING_Setisfactory_Eng));
				lblOringForMeter.setText(getResources().getText(R.string.title_O_RING_Meter_Eng));
				lblOringForDOM.setText(getResources().getText(R.string.title_O_ring_for_DOM_regulator_Eng));
				lblOringForAudco.setText(getResources().getText(R.string.title_O_ring_for_Audco_gland_Eng));

				break;

			case Constants.LANGUAGE_GUJRATI:
				lblPaintingHeader.setTypeface(Global.typeface_Guj);
				lblPaintingPaintSetisfectory.setTypeface(Global.typeface_Guj);
				lblPaint.setTypeface(Global.typeface_Guj);
				lblOringSetisfectory.setTypeface(Global.typeface_Guj);
				lblOringForMeter.setTypeface(Global.typeface_Guj);
				lblOringForDOM.setTypeface(Global.typeface_Guj);
				lblOringForAudco.setTypeface(Global.typeface_Guj);
				lblPaintingHeader.setText(getResources().getText(R.string.header_painting_Guj));
				lblPaintingPaintSetisfectory.setText(getResources().getText(R.string.title_painting_PaintSetisfactory_Guj));
				lblPaint.setText(getResources().getText(R.string.title_painting_paint_Guj));
				lblOringSetisfectory.setText(getResources().getText(R.string.title_O_RING_Setisfactory_Guj));
				lblOringForMeter.setText(getResources().getText(R.string.title_O_RING_Meter_Guj));
				lblOringForDOM.setText(getResources().getText(R.string.title_O_ring_for_DOM_regulator_Guj));
				lblOringForAudco.setText(getResources().getText(R.string.title_O_ring_for_Audco_gland_Guj));

				break;

			default:
				break;
		}
	}


}