package com.spec.asms.view.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Global;
import com.spec.asms.common.Adapter.NonConformancereasonlistAdapter;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.MaintainanceService;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.vo.ConformanceDetailVO;
import com.spec.asms.vo.ConformanceMasterVO;
import com.spec.asms.vo.MaintainanceVO;

/**
 * A class for display Conformance Form. It has methods to set values in form's fields.
 * @author jenisha
 *
 */
public class ConformanceFragment extends Fragment  {

	private Button btnSaveAndNext;
	private Button btnPrev;

	private TextView lblConformanceHeader;
	private TextView lblIsNc;
	private TextView lblConformanceTitle;
	public static ListView reason_list;
	public static EditText edtConformanceReasontext;
	public static TextView errTxtNonConfromanceReason;
	public static TextView errTxtIsNc;

	private View cnfrmanceView;

	private FragmentManager fragmentManager;

	@SuppressWarnings("unused")
	private Typeface typeface;

	private MaintainanceService maintainanceService;
	private NonConformancereasonlistAdapter adapter;
	private UserMasterService masterService;

	public static ArrayList<Boolean> reasonChecked = new ArrayList<Boolean>();
	public static ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();
	public static ArrayList<ConformanceMasterVO> nonConformanceReasonLst;

	public int userID;
	private int id;
	private int custStatusID;
	private String customerNumber;
	private String user = Constants.LABEL_BLANK;
	private String isNC = Constants.LABEL_BLANK;

	public static StringBuffer errorMsg = new StringBuffer();

	public RadioGroup radGroupConformanceIsNc;
	public RadioButton radBtnConformanceIsNcYes,radBtnConformanceIsNcNo;

	/**
	 * A parameterized constructor
	 * @param index the business id for which the profile is to be created
	 * @return ConformaceFragment object
	 */
	public static ConformanceFragment newInstance(int id,int custStatusid, String customerNumber) {
		ConformanceFragment conformanceFragment = new ConformanceFragment();
		Bundle args = new Bundle();
		args.putInt("userid",id);
		args.putInt("statusid",custStatusid);
		args.putString("contactNumber", customerNumber);
		conformanceFragment.setArguments(args);
		return conformanceFragment;

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::ConformanceFragmentStarted:::");
		cnfrmanceView = inflater.inflate(R.layout.conformancescreen,null);
		fragmentManager = getFragmentManager();
		user = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.DB_USERNAME, "tech");
		userID = getArguments().getInt("userid");
		custStatusID = getArguments().getInt("statusid");
		customerNumber = getArguments().getString("contactNumber");
		btnSaveAndNext = (Button)cnfrmanceView.findViewById(R.id.btnSaveAndNext);
		btnPrev = (Button)cnfrmanceView.findViewById(R.id.btnConformancePrev);
		edtConformanceReasontext =(EditText)cnfrmanceView.findViewById(R.id.edtConformanceReasontext);
		errTxtNonConfromanceReason = (TextView)cnfrmanceView.findViewById(R.id.errTxtNonConfromanceReason);
		errTxtIsNc = (TextView)cnfrmanceView.findViewById(R.id.errTxtIsNc);
		lblConformanceHeader = (TextView)cnfrmanceView.findViewById(R.id.lblConformanceHeader);
		lblConformanceTitle = (TextView) cnfrmanceView.findViewById(R.id.lblConformanceTitle);
		reason_list = (ListView)cnfrmanceView.findViewById(R.id.lstNonconformanceReason);
		lblIsNc = (TextView)cnfrmanceView.findViewById(R.id.lblConformanceIsNC);
		radGroupConformanceIsNc = (RadioGroup)cnfrmanceView.findViewById(R.id.radGroupConformanceIsNc);
		radBtnConformanceIsNcNo = (RadioButton)cnfrmanceView.findViewById(R.id.radBtnConformanceIsNcNo);
		radBtnConformanceIsNcYes = (RadioButton)cnfrmanceView.findViewById(R.id.radBtnConformanceIsNcYes);
		maintainanceService = new MaintainanceService(getActivity());
		masterService = new UserMasterService(getActivity());
		typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/lohit_gu.ttf");

		changeLanguage(id);

		if((custStatusID != masterService.getStatusId("OP") && custStatusID != masterService.getStatusId("HCL1")) ||user.equals("admin")){
			ConformanceDiasableViews();
		}

		if(Global.isCycleRunning)
			MaintanenceForm.btnHeaderProcess.setVisibility(View.VISIBLE);
		else
			MaintanenceForm.btnHeaderProcess.setVisibility(View.INVISIBLE);

		btnPrev.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Utility.changeSelectedForm(7, 8);
				Utility.changeList(7,false);
				OtherChecksFragment otherFragment = OtherChecksFragment.newInstance(custStatusID, customerNumber);
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.frmLayoutFragment, otherFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		});

		btnSaveAndNext.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				validateConformanceDetail();
				Utility.changeSelectedForm(9, 8);
				Utility.changeList(9,false);
				CustomerFeedbackFragment feedbackFragment = CustomerFeedbackFragment.newInstance(userID,custStatusID, customerNumber);
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.frmLayoutFragment, feedbackFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		});
		initializeValues();

		radGroupConformanceIsNc.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
					case R.id.radBtnConformanceIsNcYes:
						isNC = radBtnConformanceIsNcYes.getText().toString();
						ConformanceDetailVO.setIsNC(isNC);
						reason_list.setVisibility(View.VISIBLE);

						break;

					case R.id.radBtnConformanceIsNcNo:
						isNC = radBtnConformanceIsNcNo.getText().toString();
						ConformanceDetailVO.setIsNC(isNC);
						reason_list.setVisibility(View.GONE);
						edtConformanceReasontext.setVisibility(View.INVISIBLE);
						for (int i = 0; i < nonConformanceReasonLst.size(); i++) {
							reasonChecked.add(i,false); // initializes all items value with false
							String where = "maintainanceorderid = '"+ MaintainanceVO.getMaintainanceOrderId() +"' and objectid = "+nonConformanceReasonLst.get(i).getConformanceID() ;
							if(masterService.deleteUser(Constants.TBL_DTL_DAMAGE_CAUSE, where) != -1)
							{
							}
						}
						reason_list.clearChoices();

						break;
				}
				if((custStatusID != masterService.getStatusId("OP") && custStatusID != masterService.getStatusId("HCL1")) || user.equals("admin"))
				{
					adapter = new NonConformancereasonlistAdapter(getActivity(),
							R.layout.conformance_list_row,nonConformanceReasonLst,itemChecked,true);
				}else{
					adapter = new NonConformancereasonlistAdapter(getActivity(),
							R.layout.conformance_list_row,nonConformanceReasonLst,itemChecked,false);
				}

				reason_list.setAdapter(adapter);
				validateConformanceDetail();

			}
		});
		return cnfrmanceView;
	}

	/**
	 * Method to disable all controls when open form in edit mode.
	 * @return void
	 */
	public void ConformanceDiasableViews(){
		//		if(user.equals("admin"))		
		//		  reason_list.setEnabled(false);
		edtConformanceReasontext.setEnabled(false);
		radBtnConformanceIsNcNo.setEnabled(false);
		radBtnConformanceIsNcYes.setEnabled(false);
	}

	@Override
	public void onPause() {
		super.onPause();
		validateConformanceDetail();
	}

	/**
	 * Method used to set values for field and controls.
	 * @return void
	 *
	 */
	private void initializeValues()
	{
		isNC = ConformanceDetailVO.getIsNC();
		if(isNC.equals(Constants.LABEL_BLANK))
		{
			isNC = getResources().getString(R.string.title_select_option);
			ConformanceDetailVO.setIsNC(isNC);
			radBtnConformanceIsNcNo.setChecked(false);
			radBtnConformanceIsNcYes.setChecked(false);
		}else
		{
			if("YES".equals(ConformanceDetailVO.getIsNC()))
			{
				radBtnConformanceIsNcNo.setChecked(false);
				radBtnConformanceIsNcYes.setChecked(true);
				reason_list.setVisibility(View.VISIBLE);
			}
			else if("NO".equals(ConformanceDetailVO.getIsNC()))
			{
				radBtnConformanceIsNcNo.setChecked(true);
				radBtnConformanceIsNcYes.setChecked(false);
				reason_list.setVisibility(View.GONE);
				edtConformanceReasontext.setVisibility(View.INVISIBLE);
			}
		}
		nonConformanceReasonLst = (ArrayList<ConformanceMasterVO>) maintainanceService.getNonConformanceReasons(Constants.TBL_MST_CONFORMANCE,Constants.CONFORMANCE_MASTER_FIELDS);
		for(int i = 0; i < nonConformanceReasonLst.size(); i++){
			if(reasonChecked.size() > 0){
				if(nonConformanceReasonLst.get(i).getReason().equalsIgnoreCase("OTHERS")){
					if(reasonChecked.get(i)){
						edtConformanceReasontext.setVisibility(View.VISIBLE);
						edtConformanceReasontext.setText(ConformanceDetailVO.getText());
					}else{
						edtConformanceReasontext.setVisibility(View.INVISIBLE);
					}
				}
				itemChecked.add(i,reasonChecked.get(i));
			}else{
				itemChecked.add(i,false);
			}
		}
		if(custStatusID != masterService.getStatusId("OP") || user.equals("admin"))
		{
			adapter = new NonConformancereasonlistAdapter(getActivity(),
					R.layout.conformance_list_row,nonConformanceReasonLst,itemChecked,true);
		}else{
			adapter = new NonConformancereasonlistAdapter(getActivity(),
					R.layout.conformance_list_row,nonConformanceReasonLst,itemChecked,false);
		}

		reason_list.setAdapter(adapter);
		validateConformanceDetail();
	}

	/**
	 * Validation method to validate control. If any error ,it will display on error text field.
	 * @return void.
	 */
	public void validateConformanceDetail() {
		try{
			MaintanenceForm.validateList.remove(8);
			MaintanenceForm.validateList.add(8, false);
			if(nonConformanceReasonLst.size()> 0)
			{
				for(int i = 0; i < nonConformanceReasonLst.size(); i++){
					if(reasonChecked.size()>0){
						if(reasonChecked.get(i)){
							ConformanceDetailVO.setConformanceId(i);
							if(nonConformanceReasonLst.get(i).getReason().equalsIgnoreCase("OTHERS")){
								ConformanceDetailVO.setText(edtConformanceReasontext.getText().toString());
							}
						}
					}
				}
			}
			ConformanceDetailVO.setIsNC(ConformanceDetailVO.getIsNC());
			isNC = ConformanceDetailVO.getIsNC();
			if(isNC.equals(Constants.LABEL_BLANK))
			{
				isNC = getResources().getString(R.string.title_select_option);
				ConformanceDetailVO.setIsNC(isNC);
				radBtnConformanceIsNcNo.setChecked(false);
				radBtnConformanceIsNcYes.setChecked(false);
			}else
			{
				if("YES".equals(ConformanceDetailVO.getIsNC()))
				{
					radBtnConformanceIsNcNo.setChecked(false);
					radBtnConformanceIsNcYes.setChecked(true);
					reason_list.setVisibility(View.VISIBLE);
				}
				else if("NO".equals(ConformanceDetailVO.getIsNC()))
				{
					radBtnConformanceIsNcNo.setChecked(true);
					radBtnConformanceIsNcYes.setChecked(false);
					reason_list.setVisibility(View.GONE);
					edtConformanceReasontext.setVisibility(View.INVISIBLE);
				}
			}
//			if(errTxtIsNc != null)
//			{
//				if(ConformanceDetailVO.getIsNC().toString().equals(getResources().getString(R.string.title_select_option))){
//					errTxtIsNc.setText(Constants.ERROR_MARK);
//					MaintanenceForm.validateList.remove(8);
//					MaintanenceForm.validateList.add(8,true);
//				}else{
//					errTxtIsNc.setText(Constants.ERROR_MARK);
//					MaintanenceForm.validateList.remove(8);
//					MaintanenceForm.validateList.add(8,false);
//				}
//			}
//			if(isNC.equals("YES"))
//			{
//				if(errTxtNonConfromanceReason != null)
//				{
//					if(isAnyReasonSelected()){
//						errTxtNonConfromanceReason.setText(Constants.ERROR_MARK);
//						MaintanenceForm.validateList.remove(8);
//						MaintanenceForm.validateList.add(8,true);
//
//					}else{
//						//			errTxtNonConfromanceReason.setText(Constants.LABEL_BLANK);
//						errTxtNonConfromanceReason.setText(Constants.ERROR_MARK);
//						MaintanenceForm.validateList.remove(8);
//						MaintanenceForm.validateList.add(8,false);
//
//						for(int i = 0; i < nonConformanceReasonLst.size(); i++){
//							if(reasonChecked.size()>0){
//								if(reasonChecked.get(i)){
//									ConformanceDetailVO.setConformanceId(i);
//									if(nonConformanceReasonLst.get(i).getReason().equalsIgnoreCase("OTHERS")){
//										ConformanceDetailVO.setText(edtConformanceReasontext.getText().toString());
//									}
//								}
//							}
//						}
//					}
//				}
//			}
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,"ConformanceFragment:validateConformanceDetail:"+ Utility.convertExceptionToString(e));
		}
	}

	/*
	*
	* */
	private boolean isAnyReasonSelected(){
		boolean isAny = false;
		for (int i = 0; i < reasonChecked.size(); i++) {
			isAny = isAny || reasonChecked.get(i);
		}
		return isAny;
	}

	/**
	 * Method used to change label values as per parameter to English and Gujarati.
	 * @param id : 1 - English , 2 - Gujarati
	 */
	public void changeLanguage(int id){

		id = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.PREF_LANGUAGE,Constants.LANGUAGE_ENGLISH);
		switch (id){
			case Constants.LANGUAGE_ENGLISH:
				lblIsNc.setText(getResources().getText(R.string.title_conformance_IsNC_Eng));
				lblConformanceHeader.setText(getResources().getText(R.string.header_Conformance_Eng));
				lblConformanceTitle.setText(getResources().getText(R.string.title_Nonconformancereason));
				break;

			case Constants.LANGUAGE_GUJRATI:

				lblConformanceHeader.setTypeface(Global.typeface_Guj);
				lblIsNc.setTypeface(Global.typeface_Guj);
				lblConformanceTitle.setTypeface(Global.typeface_Guj);
				lblConformanceHeader.setText(getResources().getText(R.string.header_Conformance_Guj));
				lblIsNc.setText(getResources().getText(R.string.title_conformance_IsNC_Guj));
				lblConformanceTitle.setText(getResources().getText(R.string.title_Nonconformancereason_Guj));
				break;

			default:
				break;
		}
	}
}