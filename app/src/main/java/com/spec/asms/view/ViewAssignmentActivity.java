package com.spec.asms.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnShowListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Global;
import com.spec.asms.common.Adapter.CustomerListAdapter;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.ICleanUpResponseListener;
import com.spec.asms.common.utils.SecurityManager;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.CustomerMasterService;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.service.async.AsyncFindAllCustomer;
import com.spec.asms.service.async.AsyncFindAllCustomer.OnResponseListenerFindCustomer;
import com.spec.asms.service.async.AsyncSyncCustomerData;
import com.spec.asms.service.async.AsyncSyncCustomerData.OnResponseListener;
import com.spec.asms.view.fragment.ConformanceFragment;
import com.spec.asms.view.fragment.TestingFragment;
import com.spec.asms.vo.ClampingVO;
import com.spec.asms.vo.CleanupVO;
import com.spec.asms.vo.ConformanceDetailVO;
import com.spec.asms.vo.CustomerFeedbackVO;
import com.spec.asms.vo.CustomerMasterVO;
import com.spec.asms.vo.GIFittingVO;
import com.spec.asms.vo.KitchenSurakshaTubeVO;
import com.spec.asms.vo.LeakageVO;
import com.spec.asms.vo.MakeGeyserVO;
import com.spec.asms.vo.OtherChecksVO;
import com.spec.asms.vo.OtherKitchenSurakshaTubeVO;
import com.spec.asms.vo.PaintingVO;
import com.spec.asms.vo.RccVO;
import com.spec.asms.vo.TestingVO;
import com.spec.asms.vo.UserMasterVO;
/**
 * A class to display customer List those assign to user for form submission.
 * Class has Searching customer,navigation functionality.
 * @author jenisha
 *
 */
public class ViewAssignmentActivity extends Fragment implements OnClickListener,OnItemSelectedListener{

	public CustomerMasterService custService;
	private UserMasterService masterService;

	private ArrayList<CustomerMasterVO> arrayLstCustomer;
	private List<CustomerMasterVO> LstCustomer;
	public  List<CustomerMasterVO> getAllCustLists;


	public ListView lstCustInfo;

	public static EditText edtViewassignHeaderCustid;
	public static EditText edtViewassignHeaderCustName;
	public static EditText edtMeterNumberValue;
	

	public Button btnViewAssignHeaderRefresh;
	public Button btnViewAssignHeaderCustStatus;
	public Button btnViewAssignHeaderMru;
	public Button btnViewAssignHeaderSocietyName;
	public Button btnViewassignHeaderFind;
	public Button btnViewassignHeaderLoad;
	public ImageButton btnViewassignNext;
	public ImageButton btnViewassignPrev;
	public ImageButton btnViewassignFirst;
	public ImageButton btnViewassignLast;
	public TextView lblViewAssignCustEmptyList;
	public TextView lblViewAssignShwngRsltPageNo;
	public TextView lblViewAssignShwngRsltRowsperpage;
	public TextView lblViewAssignShwngRsltTotrows;
	public TextView lblCustomerId;
	public TextView lbl1stHeaderCustomerId;
	public TextView lblCustomerName;
	public TextView lbl1stHeaderCustomerName;
	public TextView lblWlakInSequance;
	public TextView lblAddress;
	public TextView lblStatus;
	public TextView lblMRUnit;
	public TextView lblSocietyName;
	public TextView txtMeterNumberLbl;

	public CustomerListAdapter adapter;

	public String strCustID;
	public String strCustName;
	public String meterNumber;
	public String strCustStatus;
	public String strMrUnit  = "select";

	public int CustStatus;
	public int id;
	public int counter;
	public int page;
	public int totalpage;
	public int totalrows;
	public int first;
	public int last;
	public int isStatus = 3;
	private static int selectedStatusId = 4;
	private int selectedMRUnitId = 0;


	public View assingmentList;

	private int[] statusid;
	private String[] status;
	private String[] statuscode;
	private String [] mrunit;
	private String [] societyname;
	private  boolean[] selections;
	private List<String> selectedsociety = new ArrayList<String>();


	public CleanupVO cleanupVo;	
	public Context context;
	private boolean loginAlert;
	@SuppressWarnings("unused")
	private UserMasterVO userVO;
	private View view;
//	public static boolean isCycleRunning = false;
	
	
	private AlertDialog alertStatusDialog;
	private AlertDialog alertMRUDialog;
	private AlertDialog alertSocietyDialog ;
	private Bundle mBundle;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::ViewAssignmentActivityStarted:::");
		assingmentList = inflater.inflate(R.layout.viewassignment, null);		
		try {
			masterService = new  UserMasterService(getActivity());
			context = getActivity();
			Global.toValidate = false;


			custService = new CustomerMasterService(context) ;
			arrayLstCustomer = new ArrayList<CustomerMasterVO>();

			int userID = Integer.parseInt(SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USER_ID,"0"));
			System.out.println(" ------- USER ID --------"+userID);
			ContentValues cv = new ContentValues();
			cv.put(Constants.USER_MASTER_FIELDS[4],String.valueOf(System.currentTimeMillis()));
			masterService.updateUserByID(Constants.TBL_MST_USER, cv, "userid ="+userID);

			btnViewAssignHeaderMru = (Button) assingmentList.findViewById(R.id.btnViewAssignHeaderMru);
			btnViewAssignHeaderSocietyName = (Button) assingmentList.findViewById(R.id.btnViewAssignHeaderSocietyName);
			btnViewAssignHeaderRefresh = (Button) assingmentList.findViewById(R.id.btnViewAssignHeaderRefresh);
			
			//spnViewassignHeaderCustStatus = (Spinner)assingmentList.findViewById(R.id.spnViewAssignHeaderCustStatus);
			btnViewAssignHeaderCustStatus = (Button)assingmentList.findViewById(R.id.btnViewAssignHeaderCustStatus);
			edtViewassignHeaderCustid = (EditText)assingmentList.findViewById(R.id.edtViewAssignHeaderCustid);
			edtViewassignHeaderCustName = (EditText)assingmentList.findViewById(R.id.edtViewAssignHeaderCustName);
			edtMeterNumberValue = (EditText) assingmentList.findViewById(R.id.edtMeterNumberValue);
			txtMeterNumberLbl = (TextView) assingmentList.findViewById(R.id.txtMeterNumberLbl);
			btnViewassignHeaderFind = (Button)assingmentList.findViewById(R.id.btnViewAssignHeaderFind);
			btnViewassignNext = (ImageButton)assingmentList.findViewById(R.id.btnViewAssignNext);
			btnViewassignPrev = (ImageButton)assingmentList.findViewById(R.id.btnViewAssignPrev);
			btnViewassignFirst = (ImageButton)assingmentList.findViewById(R.id.btnViewAssignFirst);
			btnViewassignLast = (ImageButton)assingmentList.findViewById(R.id.btnViewAssignLast);
			btnViewassignHeaderLoad = (Button)assingmentList.findViewById(R.id.btnViewAssignHeaderLoad);
			lstCustInfo = (ListView)assingmentList.findViewById(R.id.lstViewAssignCustinfo);
			lstCustInfo.setFooterDividersEnabled(true);
			lblViewAssignCustEmptyList = (TextView)assingmentList.findViewById(R.id.lblViewAssignCustEmptyList);
			lblViewAssignShwngRsltPageNo = (TextView)assingmentList.findViewById(R.id.lblViewAssignShwngRsltPageNo);
			lblViewAssignShwngRsltRowsperpage = (TextView)assingmentList.findViewById(R.id.lblViewAssignShwngRsltRowsperpage);
			lblViewAssignShwngRsltTotrows = (TextView)assingmentList.findViewById(R.id.lblViewAssignShwngRsltTotrows);
			lblAddress = (TextView)assingmentList.findViewById(R.id.lblViewAssign_header_lst_custadd);
			lblCustomerName = (TextView)assingmentList.findViewById(R.id.lblViewAssignHeaderCustName);
			lblCustomerId = (TextView)assingmentList.findViewById(R.id.lblViewAssignHeaderCustid);
			lblStatus = (TextView)assingmentList.findViewById(R.id.lblViewAssignHeaderCustStatus);
			lbl1stHeaderCustomerId = (TextView)assingmentList.findViewById(R.id.lblViewAssign_lst_header_custid);
			lbl1stHeaderCustomerName = (TextView)assingmentList.findViewById(R.id.lblViewAssign_lst_header_custname);
			lblWlakInSequance = (TextView)assingmentList.findViewById(R.id.lblViewAssign_header_lst_walksequnce);
			lblMRUnit = (TextView) assingmentList.findViewById(R.id.lblViewAssignHeaderMRUnit);
			lblSocietyName = (TextView) assingmentList.findViewById(R.id.lblViewAssignHeaderSocietyName);

			btnViewassignHeaderFind.setOnClickListener(ViewAssignmentActivity.this);
			btnViewassignNext.setOnClickListener(ViewAssignmentActivity.this);
			btnViewassignPrev.setOnClickListener(ViewAssignmentActivity.this);
			btnViewassignFirst.setOnClickListener(ViewAssignmentActivity.this);
			btnViewassignLast.setOnClickListener(ViewAssignmentActivity.this);
			btnViewAssignHeaderCustStatus.setOnClickListener(ViewAssignmentActivity.this);
			btnViewAssignHeaderMru.setOnClickListener(ViewAssignmentActivity.this);
			btnViewAssignHeaderSocietyName.setOnClickListener(ViewAssignmentActivity.this);
			btnViewAssignHeaderRefresh.setOnClickListener(ViewAssignmentActivity.this);
			

			view = View.inflate(context, R.layout.list_footer, null);
			lstCustInfo.addFooterView(view); 

			if(Global.isCycleRunning)
				btnViewassignHeaderLoad.setVisibility(View.VISIBLE);
			else
				btnViewassignHeaderLoad.setVisibility(View.INVISIBLE);


			TestingVO.cleanTestingVO();
			GIFittingVO.cleanGiFitting();
			ClampingVO.cleanClampingVO();
			PaintingVO.cleanPaintingVO();
			LeakageVO.cleanLeakage();
			RccVO.cleanRCCVO();
			MakeGeyserVO.cleanMakeGeyser();
			KitchenSurakshaTubeVO.cleanKitchenSurakshaTubeVO();
			OtherKitchenSurakshaTubeVO.cleanOtherKitchenSurakshaTubeVO();
			OtherChecksVO.cleanOtherChecks();
			ConformanceDetailVO.cleanConformanceVO();
			CustomerFeedbackVO.cleanomerFeedbackCustVO();
			KitchenSurakshaTubeVO.cleanKitchenSurakshaTubeVO();

			TestingFragment.itemCheckedLkg.clear();
			ConformanceFragment.reasonChecked.clear();
			changeLanguage(id);
			
			
		    mrunit = custService.getMRUnitList().toArray(new String[custService.getMRUnitList().size()]);
			
			mBundle = MainTabActivity.getSavedMainFragmentState();
			if( mBundle != null && Global.isFilterRequired){
				 strCustID =   mBundle.getString(Constants.CUSTOMER_ID);	
				 strCustName = mBundle.getString(Constants.CUSTOMER_NAME);
				 strCustStatus = mBundle.getString(Constants.CUSTOMER_STATUS);
				 strMrUnit = mBundle.getString(Constants.CUSTOMER_MRUNIT);
				 
				 selectedsociety = mBundle.getStringArrayList(Constants.SELECTED_SOCIETY_LIST);
				 societyname = mBundle.getStringArray(Constants.SOCIETY_LIST);
				 selections = mBundle.getBooleanArray(Constants.SELECTION_OF_SOCIETY);
				
				 status = mBundle.getStringArray(Constants.STATUS_LIST);
				 
				 
				 selectedMRUnitId = mBundle.getInt(Constants.SELECTED_MRU_ID);
				 selectedStatusId = mBundle.getInt(Constants.SELECTED_STATUS);
				 CustStatus =  mBundle.getInt(Constants.SELECTED_STATUS_ID);
				 
				edtViewassignHeaderCustid.setText(strCustID);
			    edtViewassignHeaderCustName.setText(strCustName);
				btnViewAssignHeaderCustStatus.setText(strCustStatus);
				
				
				if(mrunit!= null && mrunit.length > 1){
					if(selectedMRUnitId !=0){
						btnViewAssignHeaderMru.setText(strMrUnit);
					}else{
						btnViewAssignHeaderMru.setText("Select");
					}
					
				}else{
					btnViewAssignHeaderMru.setText("Select");
					btnViewAssignHeaderSocietyName.setText("Select");
					selectedsociety = null;
				}
				
				if(selectedsociety!= null && selectedsociety.size()> 0){
					btnViewAssignHeaderSocietyName.setText(getSocietyNameString(selectedsociety));
				}
			}else{
				status = new String[masterService.getStatus().size()];
				statuscode = new String[masterService.getStatus().size()];
				statusid = new int[masterService.getStatus().size()];
				
				for (int i = 0; i < status.length; i++) {
					status[i] = masterService.getStatus().get(i).getStatusName();
					statusid[i] = masterService.getStatus().get(i).getId();
					statuscode[i] = masterService.getStatus().get(i).getStatusCode();
				}
				setDefaultValue();
				isStatus = (masterService.getStatusId("OP")-1); 
				if(isStatus > 0)
				{
					btnViewAssignHeaderCustStatus.setText(status[isStatus]);
					strCustStatus = status[isStatus];
					CustStatus = isStatus+1;
				}
			
			}
		

			String userName=SharedPrefrenceUtil.getPrefrence(context,  Constants.DB_USERNAME,"tech");
			//			String password=SharedPrefrenceUtil.getPrefrence(context, Constants.DB_PASSWORD,"tech");
			//			String where = "userName = '" + userName + "' and password = '"+ password + "'";
			//		    userVO = masterService.getUserInfo(Constants.TBL_MST_USER, Constants.USER_MASTER_FIELDS,where);
			Log.e(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"DateTime:-"+Utility.getcurrentTimeStamp()+">>>>>>>>>>>> UserName <<<<<<<<<<< "+userName);


			if(!Utility.isAdmin(getActivity().getApplicationContext())){

				loginAlert =  Utility.chkLoginAlert(userName,context,getActivity());

				if(loginAlert){
					expirePwdDialog(getActivity(), getActivity());
				}else{
					if(Global.isFilterRequired){
						AsyncFindAllCustomer asyncFindallcust = new AsyncFindAllCustomer(context, OnResponseListenerFind);
						asyncFindallcust.execute();
					}else{
						setDefaultValue();
						callCustomerService(); //send server request
//						callAsyncCustomerService();
					}
					
				}
				//				if(!Utility.isAutoSubmitServiceRunning(getActivity()))
				//				{
				//					Intent i = new Intent(context,BatchSubmitService.class);
				//					context.startService(i);
				//				}


				//				 userVO = masterService.getUserInfo(Constants.TBL_MST_USER, Constants.USER_MASTER_FIELDS,where);
				/*if(loginAlert){
						if(userVO.getIslock() == 1){
							Intent i = new Intent(getActivity(),LoginActivity.class);
							i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
							startActivity(i);
							getActivity().finish();
						}else
							checkPwdChange(getActivity(), getActivity());
					}else{*/
				//						callCustomerService(); //send server request
				//					}
			}
			else{
				//Fetch data from DB
				/**
				 * If admin is logined in then display data from database
				 */
				if(Global.isFilterRequired){
					AsyncFindAllCustomer asyncFindallcust = new AsyncFindAllCustomer(context, OnResponseListenerFind);
					asyncFindallcust.execute();
				}else{
					getCustomerData();
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,"::ViewAssignmentActivity::"+ Utility.convertExceptionToString(e));
		}
		return assingmentList;
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		 Bundle args = new Bundle();
	       saveInstance(args);
	       MainTabActivity.saveMainFragmentState(args);
	}
	
//	public static void onCleanUp(){
//		selectedStatusId = 4;
//		setDefaultValue();
//		LstCustomer.clear();
//		adapter.notifyDataSetChanged();
//		
//	}
	private  void onRefresh(){
		
		masterService = new  UserMasterService(getActivity());
		status = new String[masterService.getStatus().size()];
		statuscode = new String[masterService.getStatus().size()];
		statusid = new int[masterService.getStatus().size()];
		for (int i = 0; i < status.length; i++) {
			status[i] = masterService.getStatus().get(i).getStatusName();
			statusid[i] = masterService.getStatus().get(i).getId();
			statuscode[i] = masterService.getStatus().get(i).getStatusCode();
		}
		setDefaultValue();
		isStatus = (masterService.getStatusId("OP")-1); 
		if(isStatus > 0)
		{
			btnViewAssignHeaderCustStatus.setText(status[isStatus]);
			strCustStatus = status[isStatus];
			CustStatus = isStatus+1;
		}
		//mrunit = custService.getMRUnitList().toArray(new String[custService.getMRUnitList().size()]);
		
		String userName=SharedPrefrenceUtil.getPrefrence(context,  Constants.DB_USERNAME,"tech");
		//			String password=SharedPrefrenceUtil.getPrefrence(context, Constants.DB_PASSWORD,"tech");
		//			String where = "userName = '" + userName + "' and password = '"+ password + "'";
		//		    userVO = masterService.getUserInfo(Constants.TBL_MST_USER, Constants.USER_MASTER_FIELDS,where);
		Log.e(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"DateTime:-"+Utility.getcurrentTimeStamp()+">>>>>>>>>>>> UserName <<<<<<<<<<< "+userName);


		if(!Utility.isAdmin(getActivity().getApplicationContext())){

			loginAlert =  Utility.chkLoginAlert(userName,context,getActivity());

			if(loginAlert){
				expirePwdDialog(getActivity(), getActivity());
			}else{
				if(Global.isFilterRequired){
					AsyncFindAllCustomer asyncFindallcust = new AsyncFindAllCustomer(context, OnResponseListenerFind);
					asyncFindallcust.execute();
				}else{
					setDefaultValue();
					callCustomerService(); 
					lblViewAssignCustEmptyList.setVisibility(View.INVISIBLE);
					lstCustInfo.setVisibility(View.VISIBLE);//send server request
				}
				
			}
		}
		else{
			//Fetch data from DB
			/**
			 * If admin is logined in then display data from database
			 */
			if(Global.isFilterRequired){
				AsyncFindAllCustomer asyncFindallcust = new AsyncFindAllCustomer(context, OnResponseListenerFind);
				asyncFindallcust.execute();
			}else{
				getCustomerData();
			}
			
		}
	
	}
	 private void saveInstance(Bundle outState) {
	        // put data into bundle
		 	outState.putString(Constants.CUSTOMER_ID, strCustID);
		 	outState.putString(Constants.CUSTOMER_NAME, strCustName);
		 	outState.putString(Constants.CUSTOMER_STATUS,strCustStatus);
		    outState.putString(Constants.CUSTOMER_MRUNIT,strMrUnit);
//		    outState.putStringArray(Constants.MRUNIT_LIST,mrunit);
		    outState.putStringArray(Constants.STATUS_LIST, status);
			outState.putStringArray(Constants.SOCIETY_LIST, societyname);
			outState.putStringArrayList(Constants.SELECTED_SOCIETY_LIST,(ArrayList<String>)selectedsociety);
			outState.putBooleanArray(Constants.SELECTION_OF_SOCIETY, selections);
			outState.putInt(Constants.SELECTED_STATUS, selectedStatusId);
			outState.putInt(Constants.SELECTED_MRU_ID, selectedMRUnitId);
			outState.putInt(Constants.SELECTED_STATUS_ID, CustStatus);
			
	    }
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		saveInstance(outState);
		super.onSaveInstanceState(outState);
//		saveInstance(outState);
		
	}
	
    private void setDefaultValue(){
    	selectedMRUnitId = 0;
    	//selectedStatusId = 7;
    	edtViewassignHeaderCustid.setText("");
		edtViewassignHeaderCustName.setText("");
		edtMeterNumberValue.setText("");
		meterNumber = "";
		btnViewAssignHeaderMru.setText("Select");
		btnViewAssignHeaderSocietyName.setText("Select");
		
    	if(selectedsociety != null){
    		selectedsociety.clear();
    	}
    	for(int i = 0;i<status.length;i++){
    		if(status[i].equalsIgnoreCase("open")){
    			selectedStatusId = i;
    			break;
    		}
    	}
    	
    	if(status != null && status.length >0){
			btnViewAssignHeaderCustStatus.setText(status[selectedStatusId]);
		}
	
		
		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	/**
	 * Method to navigate to next page on listview.
	 */
	private void nextPage() {
		try {
			if(counter >= 1 && page >= 1 && page < totalpage){
				counter += 1;
				first = first+10;
				last = last+10;
				page += 1;
				lblViewAssignShwngRsltPageNo.setText((first+1)+"");
				if(page == totalpage){
					if(last == totalrows){
						lblViewAssignShwngRsltRowsperpage.setText(last+"");
						LstCustomer = arrayLstCustomer.subList(first,last);
					}else{
						LstCustomer = arrayLstCustomer.subList(first,totalrows);
						lblViewAssignShwngRsltRowsperpage.setText(totalrows+"");
					}
				}else{
					lblViewAssignShwngRsltRowsperpage.setText(last+"");
					LstCustomer = arrayLstCustomer.subList(first,last);
				}
				lblViewAssignShwngRsltTotrows.setText(totalrows+"");
				adapter = new CustomerListAdapter(context,R.layout.customer_list_row,LstCustomer,getActivity());
				lstCustInfo.setAdapter(adapter);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":ViewAssignment:nextPage:"+ Utility.convertExceptionToString(e));
		}
	}

	/**
	 * Method to navigate to previous page on listview.
	 */
	private void prevPage() {
		try {
			if(counter > 1 && page > 1 ){
				counter -= 1;
				Log.e(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"prev counter="+counter);
				first = first-10;
				last = last-10;
				page -= 1;

				lblViewAssignShwngRsltPageNo.setText((first+1)+"");
				lblViewAssignShwngRsltRowsperpage.setText(last+"");
				lblViewAssignShwngRsltTotrows.setText(totalrows+"");

				LstCustomer = arrayLstCustomer.subList(first,last);
				adapter = new CustomerListAdapter(context,R.layout.customer_list_row,LstCustomer,getActivity());
				lstCustInfo.setAdapter(adapter);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":ViewAssignment:prevPage:"+ Utility.convertExceptionToString(e));
		}
	}

	/**
	 * Method to navigate to last page on listview.
	 */
	private void lastPage() {
		try {
			if(totalpage > 0){
				counter = totalpage;
				page = totalpage;
				first = totalpage*10-10;
				last = first+10;

				lblViewAssignShwngRsltPageNo.setText((first+1)+"");
				if(last == totalrows){
					lblViewAssignShwngRsltRowsperpage.setText(last+"");
					LstCustomer = arrayLstCustomer.subList(first,last);
				}else{
					lblViewAssignShwngRsltRowsperpage.setText(totalrows+"");
					LstCustomer = arrayLstCustomer.subList(first,totalrows);
				}
				lblViewAssignShwngRsltTotrows.setText(totalrows+"");

				adapter = new CustomerListAdapter(context,R.layout.customer_list_row,LstCustomer,getActivity());
				lstCustInfo.setAdapter(adapter);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":ViewAssignment:lastPage:"+ Utility.convertExceptionToString(e));
		}
	}

	/**
	 * Method to navigate to first page on listview.
	 */
	private void firstPage() {
		try{
			if(totalpage > 0){
				counter = 1;
				page = 1;
				first = 0;
				if(totalrows < 10)
				{
					last = totalrows;
				}else
				{
					last = 10;
				}

				lblViewAssignShwngRsltPageNo.setText((first+1)+"");
				lblViewAssignShwngRsltRowsperpage.setText(last+"");
				lblViewAssignShwngRsltTotrows.setText(totalrows+"");

				LstCustomer = arrayLstCustomer.subList(first,last);
				adapter = new CustomerListAdapter(context,R.layout.customer_list_row,LstCustomer,getActivity());
				lstCustInfo.setAdapter(adapter);
			}
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":ViewAssignment:firstPage:"+ Utility.convertExceptionToString(e));
		}
	}


	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long arg3) {
		strCustStatus = arg0.getItemAtPosition(position).toString();
		CustStatus = position+1;
	}

	public void onNothingSelected(AdapterView<?> arg0) {
	}
	
	public void createStatusDialog(int id){
		alertStatusDialog = new AlertDialog.Builder(getActivity()).setSingleChoiceItems(status,id,new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int whichButton) {
				btnViewAssignHeaderCustStatus.setText(status[whichButton]);
				isStatus = whichButton;
				selectedStatusId = whichButton;
				strCustStatus = status[whichButton];
				CustStatus = isStatus+1;
				if(CustStatus == 1){
					mrunit = null;
				}
//				else{
//					mrunit = custService.getMRUnitList().toArray(new String[custService.getMRUnitList().size()]);
//				}
				dialog.dismiss();
			}
		}).create();
	}

	public void createMRUDailog(int id){
		 mrunit = null;
		 mrunit = custService.getMRUnitList().toArray(new String[custService.getMRUnitList().size()]);
		 if(mrunit!= null && mrunit.length > 1){
			 alertMRUDialog = new AlertDialog.Builder(getActivity()).setSingleChoiceItems(mrunit,id,new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int whichButton) {
						strMrUnit = mrunit[whichButton];

						if(selectedsociety != null){
							selectedsociety.clear();
							btnViewAssignHeaderSocietyName.setText("Select");
						}
						selectedMRUnitId = whichButton;
					
						dialog.dismiss();
						btnViewAssignHeaderMru.setText(strMrUnit);
						societyname = null;
						selections = null;
						alertSocietyDialog = null;
						List<String> societNamesList = custService.getSocietyNameList(strMrUnit);
						if(societNamesList != null && societNamesList.size() > 0){
							Collections.sort(societNamesList, new Comparator<String> () {

								public int compare(String lhs, String rhs) {
									// TODO Auto-generated method stub
									return lhs.compareTo(rhs);
								}
							});
							societyname = societNamesList.toArray(new String[societNamesList.size()]);
							societNamesList =null;
							selections =  new boolean[societyname.length ];
						}
					}
				}).create();
		 }else{
			 if(selectedsociety != null){
				 selectedsociety.clear();
				 
			 }
			 selectedMRUnitId = 0;
			 btnViewAssignHeaderMru.setText("Select");
			 btnViewAssignHeaderSocietyName.setText("Select");
		 }
		
		
	}
	
	public void createSocietyDialog(String []societyname,final boolean []selections){
		if(societyname != null && societyname.length > 0){
			alertSocietyDialog = new AlertDialog.Builder(getActivity()).setMultiChoiceItems(societyname,selections,  new DialogSelectionClickHandler()  )
					.setPositiveButton( "OK", null)
					.setNegativeButton("Clear", null).setCancelable(false) 
					.create();
			alertSocietyDialog.setOnKeyListener(new OnKeyListener() {
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK) {
						if(alertSocietyDialog!= null && alertSocietyDialog.isShowing()){
							if(selectedsociety != null && selectedsociety.size()>0){
								String societyname = getSocietyNameString(selectedsociety);
								if(societyname!= null && !societyname.equals("")){
									btnViewAssignHeaderSocietyName.setText(getSocietyNameString(selectedsociety));
								}else{
									btnViewAssignHeaderSocietyName.setText("Select");
								}
							}
							alertSocietyDialog.dismiss();
						}
						return true;
					}
					return false;
				}
			});

			alertSocietyDialog.setOnShowListener(new OnShowListener() {
				public void onShow(DialogInterface dialog) {
					// TODO Auto-generated method stub
					Button pb = alertSocietyDialog.getButton(AlertDialog.BUTTON_POSITIVE);
					pb.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if(selectedsociety != null && selectedsociety.size() > 0){
								String societyName = getSocietyNameString(selectedsociety).toString();
								if(societyName!= null){
									btnViewAssignHeaderSocietyName.setText(societyName);
								}else{
									btnViewAssignHeaderSocietyName.setText("Select");	
								}
							}
							alertSocietyDialog.dismiss();									
						}					            
					});
					Button nb = alertSocietyDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
					nb.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							// TODO Auto-generated method stub
							for(int i=0;i<selections.length;i++){
								selections[i] = false;
								((AlertDialog)alertSocietyDialog).getListView().setItemChecked(i, false);
							}
							if(selectedsociety != null){
								selectedsociety.clear();
								btnViewAssignHeaderSocietyName.setText("Select");		
							}
						}
					});
				}
			});
			
		}else{
			Toast.makeText(getActivity(), "Please select MR Unit first.",Toast.LENGTH_LONG).show();
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnViewAssignHeaderFind:
	
			try{
				Global.isFilterRequired = true;
				InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(btnViewassignHeaderFind.getWindowToken(), 0);

				strCustID = edtViewassignHeaderCustid.getText().toString().trim();
				strCustName = edtViewassignHeaderCustName.getText().toString().trim();
				meterNumber = edtMeterNumberValue.getText().toString().trim();

				AsyncFindAllCustomer asyncFindallcust = new AsyncFindAllCustomer(context, OnResponseListenerFind);
				asyncFindallcust.execute();



				//				arrayLstCustomer = searchFor(strCustID, strCustName,CustStatus);
				//				
				//				Log.e("Customer List Size:--",""+arrayLstCustomer.size());
				//				if(arrayLstCustomer.size() > 0){
				//					totalrows = arrayLstCustomer.size();
				//					Log.e(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"Search TOTAL  ROWS:-"+totalrows);
				//					totalpage = (int) Math.ceil(totalrows/10.0);
				//					Log.e(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"Search TOTAL Page:-"+totalpage);
				//
				//					counter = 1;
				//					page = 1;
				//					first = 0;
				//					if(totalrows < 10)
				//					{
				//						last = totalrows;
				//					}else
				//					{
				//						last = 10;
				//					}
				//					if(totalrows < last){
				//					//	LstCustomer.clear();
				//						LstCustomer = arrayLstCustomer.subList(first,totalrows);
				//						lblViewAssignShwngRsltRowsperpage.setText(totalrows+"");
				//					}else{
				//						//LstCustomer.clear();
				//						LstCustomer = arrayLstCustomer.subList(first,last);
				//						lblViewAssignShwngRsltRowsperpage.setText(last+"");
				//					}
				//					lblViewAssignShwngRsltPageNo.setText((first+1)+"");
				//					lblViewAssignShwngRsltTotrows.setText(totalrows+"");
				//
				//					adapter = new CustomerListAdapter(context,R.layout.customer_list_row,LstCustomer,getActivity());
				//					lstCustInfo.setAdapter(adapter);
				//					lblViewAssignCustEmptyList.setVisibility(View.INVISIBLE);
				//					lstCustInfo.setVisibility(View.VISIBLE);
				//				}else{
				//					lblViewAssignCustEmptyList.setVisibility(View.VISIBLE);
				//					lstCustInfo.setVisibility(View.INVISIBLE);
				//
				//					lblViewAssignShwngRsltPageNo.setText(0+"");
				//					lblViewAssignShwngRsltRowsperpage.setText(0+"");
				//					lblViewAssignShwngRsltTotrows.setText(0+"");
				//					
				//					totalrows = 0;
				//					totalpage = 0;
				//}
			}catch(Exception e){
				e.printStackTrace();
				Log.d(Constants.DEBUG,":ViewAssignment:btnViewAssignHeaderFind:onClick:"+ Utility.convertExceptionToString(e));
			}
			break;

		case R.id.btnViewAssignNext:
			nextPage();
			break;
		case R.id.btnViewAssignPrev:
			prevPage();
			break;
		case R.id.btnViewAssignFirst:
			firstPage();
			break;			
		case R.id.btnViewAssignLast:
			lastPage();
			break;
		case R.id.btnViewAssignHeaderCustStatus:
			try {
				createStatusDialog(selectedStatusId);
				if(alertStatusDialog!= null && !alertStatusDialog.isShowing()){
					alertStatusDialog.show();
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.d(Constants.DEBUG,":ViewAssignment:btnViewAssignHeaderCustStatus:onClick:"+ Utility.convertExceptionToString(e));
			}
			break;
		case R.id.btnViewAssignHeaderMru:
			try {
				   createMRUDailog(selectedMRUnitId);
				   if(alertMRUDialog != null && !alertMRUDialog.isShowing()){
					   alertMRUDialog.show();
				   }

			} catch (Exception e) {
				e.printStackTrace();
				Log.d(Constants.DEBUG,":ViewAssignment:btnViewAssignHeaderMru:onClick:"+ Utility.convertExceptionToString(e));
			}
			break;
		case R.id.btnViewAssignHeaderSocietyName:
			try {
				if(mrunit != null && mrunit.length >1){
					createSocietyDialog(societyname,selections);
					if(alertSocietyDialog != null && !alertSocietyDialog.isShowing()){
						alertSocietyDialog.show();
					}
				}else{
					if(selectedsociety!= null){
						selectedsociety.clear();
					}
					Toast.makeText(getActivity(), "Please select MR Unit first.",Toast.LENGTH_LONG).show();
				}
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
				Log.d(Constants.DEBUG,":ViewAssignment:btnViewAssignHeaderSocietyName:onClick:"+ Utility.convertExceptionToString(e));
			}
			break;
		case R.id.btnViewAssignHeaderRefresh:
			Global.isFilterRequired = false;
			societyname = null;
			selections = null;
			alertSocietyDialog = null;
			createStatusDialog(selectedStatusId);
			onRefresh();
//			societyname = null;
//			selections = null;
//			alertSocietyDialog = null;
//			selectedStatusId = 4;
//			createStatusDialog(selectedStatusId);
//			setDefaultValue();
//			callCustomerService(); 
			break;
		default:
			break;
		}
	}

	public class DialogSelectionClickHandler implements DialogInterface.OnMultiChoiceClickListener{
		public void onClick( DialogInterface dialog, int clicked, boolean selected ){
			Log.i( "ME", selections[ clicked ] + " selected: " + selected );
			if(selected == true){
				if(selectedsociety!= null){
					selectedsociety.add(societyname[clicked]) ;
					selections[clicked] = true;
				}
			}else{
				if(selectedsociety!= null){
					if(selectedsociety.contains(societyname[clicked])){
						selectedsociety.remove(societyname[clicked]) ;
						selections[clicked] = false;
					}
				}
			}

		}
	}

	
	public class DialogButtonClickHandler implements DialogInterface.OnClickListener
	{
		
		public void onClick( DialogInterface dialog, int clicked ) {
			switch( clicked ) {
			case DialogInterface.BUTTON_POSITIVE:
				if(selectedsociety != null && selectedsociety.size() > 0){
					 btnViewAssignHeaderSocietyName.setText(getSocietyNameString(selectedsociety));
				}else{
					 btnViewAssignHeaderSocietyName.setText("Select");
				}
				   
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				for(int i=0;i<selections.length;i++){
					selections[i] = false;
				}
				if(selectedsociety != null)
				     selectedsociety.clear();
				if(selectedsociety != null && selectedsociety.size() > 0){
					 btnViewAssignHeaderSocietyName.setText(getSocietyNameString(selectedsociety));
				}else{
					 btnViewAssignHeaderSocietyName.setText("Select");
				}
			}
		}
	}



	/**
	 * Method to change language English or Gujarati.
	 * @param id: 1 - English , 2 - Gujarati.
	 */
	public void changeLanguage(int id){
		id = SharedPrefrenceUtil.getPrefrence(context,Constants.PREF_LANGUAGE,Constants.LANGUAGE_ENGLISH);

		switch (id) {
		case Constants.LANGUAGE_ENGLISH:
			lbl1stHeaderCustomerId.setText(getResources().getText(R.string.title_viewassign_custid_Eng));
			lbl1stHeaderCustomerName.setText(getResources().getText(R.string.title_viewassign_custname_Eng));
			lblAddress.setText(getResources().getText(R.string.title_viewassign_custadd_Eng));
			lblCustomerId.setText(getResources().getText(R.string.title_viewassign_custid_Eng));
			lblCustomerName.setText(getResources().getText(R.string.title_viewassign_custname_Eng));
			lblStatus.setText(getResources().getText(R.string.title_viewassign_status_Eng));
			lblWlakInSequance.setText(getResources().getText(R.string.title_viewassign_walksequence_Eng));
			lblMRUnit.setText(getResources().getString(R.string.title_viewassign_mrunit_Eng));
			lblSocietyName.setText(getResources().getString(R.string.title_viewassign_societyname_Eng));
			txtMeterNumberLbl.setText(getResources().getString(R.string.title_view_cust_meter_number_Eng));
			break;
		case Constants.LANGUAGE_GUJRATI:
			lbl1stHeaderCustomerId.setTypeface(Global.typeface_Guj);
			lbl1stHeaderCustomerName.setTypeface(Global.typeface_Guj);
			lblAddress.setTypeface(Global.typeface_Guj);
			lblCustomerId.setTypeface(Global.typeface_Guj);
			lblStatus.setTypeface(Global.typeface_Guj);
			lblWlakInSequance.setTypeface(Global.typeface_Guj);
			lblCustomerName.setTypeface(Global.typeface_Guj);
			lblMRUnit.setTypeface(Global.typeface_Guj);
			lblSocietyName.setTypeface(Global.typeface_Guj);
			txtMeterNumberLbl.setTypeface(Global.typeface_Guj);
			
			lbl1stHeaderCustomerId.setText(getResources().getText(R.string.title_viewassign_custid_Guj));
			lbl1stHeaderCustomerName.setText(getResources().getText(R.string.title_viewassign_custname_Guj));
			lblAddress.setText(getResources().getText(R.string.title_viewassign_custadd_Guj));
			lblCustomerId.setText(getResources().getText(R.string.title_viewassign_custid_Guj));
			lblCustomerName.setText(getResources().getText(R.string.title_viewassign_custname_Guj));
			lblStatus.setText(getResources().getText(R.string.title_viewassign_status_Guj));
			lblWlakInSequance.setText(getResources().getText(R.string.title_viewassign_walksequence_Guj));
			lblMRUnit.setText(getResources().getString(R.string.title_viewassign_mrunit_Guj));
			lblSocietyName.setText(getResources().getString(R.string.title_viewassign_societyname_Guj));
			txtMeterNumberLbl.setText(getResources().getString(R.string.title_view_cust_meter_number_Guj));
			break;
		default:
			break;
		}
	}

	protected OnResponseListener onResponseListener = new OnResponseListener() {
		public void onSuccess() {
			getCustomerData();
			mrunit = custService.getMRUnitList().toArray(new String[custService.getMRUnitList().size()]);
			//			System.err.println(context+" --- "+getActivity());
			SharedPrefrenceUtil.setPrefrence(context,Constants.LABEL_IS_CUST_AVAILABLE,true);
		}
		public void onFailure(String message) {
		}
	};

	/**
	 * Method to get Customer Data from Database.
	 */
	public void getCustomerData(){
		try{
			//	String whereCust = "createdby = " +Integer.parseInt(SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USER_ID,"0")) +" and isdeleted = 0 and statusid ="+ masterService.getStatusId("OP");
			if(masterService == null ){
				masterService = new UserMasterService(getActivity());
			}
			Log.e(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"STATUS ID*******"+"**********"+masterService.getStatusId("OP"));
			// set isdeleted = 1 instead of 0 because in database isdeleted value is 1
			String whereCust = " isdeleted = 0 and statusid ="+ masterService.getStatusId("OP");
			Log.e(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"********where:::"+whereCust);
			arrayLstCustomer = (ArrayList<CustomerMasterVO>) custService.getSelectedCustomers(Constants.TBL_MST_CUSTOMER,Constants.CUSTOMER_MASTER_FIELDS,whereCust);
			//		totalrows = custService.getTotalCustomers();
			//			String whereCustbyUser = "createdby = " +Integer.parseInt(SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USER_ID,"0"));
			//getAllCustLists = (ArrayList<CustomerMasterVO>) custService.getSelectedCustomers(Constants.TBL_MST_CUSTOMER,Constants.CUSTOMER_MASTER_FIELDS,null);
			if(arrayLstCustomer.size() > 0){
				totalrows = arrayLstCustomer.size();
				Log.e(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"TOTAL ROWS:-"+totalrows);
				totalpage = (int) Math.ceil(totalrows/10.0);
				Log.e(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"TOTAL Page:-"+totalpage);

				counter = 1;
				page = 1;
				first = 0;

				if(totalrows < 10)
				{
					last = totalrows;
				}else
				{
					last = 10;
				}

				lblViewAssignShwngRsltPageNo.setText((first+1)+"");
				lblViewAssignShwngRsltRowsperpage.setText(last+"");
				lblViewAssignShwngRsltTotrows.setText(totalrows+"");

				LstCustomer = arrayLstCustomer.subList(first,last);
				adapter = new CustomerListAdapter(context,R.layout.customer_list_row,LstCustomer,getActivity());
				lstCustInfo.setAdapter(adapter);
			}else{
				Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_GENERAL_INFO, "No Customers assigned yet!");
				lblViewAssignCustEmptyList.setVisibility(View.VISIBLE);
				lstCustInfo.setVisibility(View.INVISIBLE);
				
				lblViewAssignShwngRsltPageNo.setText(0+"");
				lblViewAssignShwngRsltRowsperpage.setText(0+"");
				lblViewAssignShwngRsltTotrows.setText(0+"");

				totalrows = 0;
				totalpage = 0;
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			Log.d(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"GetCustomerData:"+ ex.getMessage().toString());
			Log.d(Constants.DEBUG,":ViewAssignment:getCustomerData:"+ Utility.convertExceptionToString(ex));
		}
	}

	
	/*public void getAsyncCustomerData(Activity activity){
		try{
			//	String whereCust = "createdby = " +Integer.parseInt(SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USER_ID,"0")) +" and isdeleted = 0 and statusid ="+ masterService.getStatusId("OP");
		
				masterService = new UserMasterService(activity);
				custService = new CustomerMasterService(activity);
				arrayLstCustomer = new ArrayList<CustomerMasterVO>(0);
				LstCustomer = new ArrayList<CustomerMasterVO>(0);
				
		
			Log.e(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"STATUS ID*******"+"**********"+masterService.getStatusId("OP"));
			String whereCust = " isdeleted = 0 and statusid ="+ masterService.getStatusId("OP");
			Log.e(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"********where:::"+whereCust);
			arrayLstCustomer = (ArrayList<CustomerMasterVO>) custService.getSelectedCustomers(Constants.TBL_MST_CUSTOMER,Constants.CUSTOMER_MASTER_FIELDS,whereCust);
			//		totalrows = custService.getTotalCustomers();
			//			String whereCustbyUser = "createdby = " +Integer.parseInt(SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USER_ID,"0"));
			//getAllCustLists = (ArrayList<CustomerMasterVO>) custService.getSelectedCustomers(Constants.TBL_MST_CUSTOMER,Constants.CUSTOMER_MASTER_FIELDS,null);
			
			LayoutInflater inflater = (LayoutInflater)   activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
			view = inflater.inflate(R.layout.viewassignment, null);
			lblViewAssignShwngRsltPageNo = (TextView)view.findViewById(R.id.lblViewAssignShwngRsltPageNo);
			lblViewAssignShwngRsltRowsperpage = (TextView)view.findViewById(R.id.lblViewAssignShwngRsltRowsperpage);
			lblViewAssignShwngRsltTotrows = (TextView)view.findViewById(R.id.lblViewAssignShwngRsltTotrows);

			lblViewAssignShwngRsltPageNo.setText((first+1)+"");
			lblViewAssignShwngRsltRowsperpage.setText(last+"");
			lblViewAssignShwngRsltTotrows.setText(totalrows+"");
			
			if(arrayLstCustomer.size() > 0){
				totalrows = arrayLstCustomer.size();
				Log.e(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"TOTAL ROWS:-"+totalrows);
				totalpage = (int) Math.ceil(totalrows/10.0);
				Log.e(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"TOTAL Page:-"+totalpage);

				counter = 1;
				page = 1;
				first = 0;

				if(totalrows < 10)
				{
					last = totalrows;
				}else
				{
					last = 10;
				}
				
				

				LstCustomer = arrayLstCustomer.subList(first,last);
				adapter = new CustomerListAdapter(activity,R.layout.customer_list_row,LstCustomer,activity);
				lstCustInfo.setAdapter(adapter);
			}else{
				Utility.alertDialog(activity, Constants.ALERT_TITLE_GENERAL_INFO, "No Customers assigned yet!");
				lblViewAssignCustEmptyList.setVisibility(View.VISIBLE);
				lstCustInfo.setVisibility(View.INVISIBLE);
				
				lblViewAssignShwngRsltPageNo.setText(0+"");
				lblViewAssignShwngRsltRowsperpage.setText(0+"");
				lblViewAssignShwngRsltTotrows.setText(0+"");

				totalrows = 0;
				totalpage = 0;
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			Log.d(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"GetCustomerData:");
		}
	}
	*/
	
	
	/**
	 * Method to call CustomerService for sync data from server.
	 */
	@SuppressWarnings("unchecked")
	public void callCustomerService(){
		try{
			if(Utility.isNetAvailable(getActivity())){
				if(SharedPrefrenceUtil.getPrefrence(context, Constants.LABEL_IS_CUST_AVAILABLE, false)){
					Log.d(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"CallCustomerService: Load Customer Data");
					LoadCustomerData data = new LoadCustomerData();
					data.execute();
				}else{
					Log.d(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"CallCustomerService: AsyncSyncCustomerData called");
					Map<String, Object> param = new HashMap<String, Object>();
					param.put(Constants.JSON_LOGIN_USERNAME, SecurityManager.encrypt(SharedPrefrenceUtil.getPrefrence(getActivity(), Constants.DB_USERNAME, Constants.LABEL_BLANK),Constants.SALT));
					param.put(Constants.JSON_LOGIN_PASSWORD, SecurityManager.encrypt(SharedPrefrenceUtil.getPrefrence(getActivity(), Constants.DB_PASSWORD, Constants.LABEL_BLANK),Constants.SALT));
					param.put(Constants.JSON_LOGIN_DEVICEID, SecurityManager.encrypt(Constants.LABLE_DEVICE_ID,Constants.SALT));

					AsyncSyncCustomerData asyncSyncCustomerData = new AsyncSyncCustomerData(context,onResponseListener);
					asyncSyncCustomerData.execute(param);
				}
			}else{
				getCustomerData();
			}
		}catch(Exception ex){
			Log.d(Constants.DEBUG,":ViewAssignment:callCustomerService:"+ Utility.convertExceptionToString(ex));
		}
	}
//
//	public void callAsyncCustomerService(){
//		try{
//			if(Utility.isNetAvailable(getActivity())){
//					Log.d(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"CallCustomerService: AsyncSyncCustomerData called");
//					Map<String, Object> param = new HashMap<String, Object>();
//					param.put(Constants.JSON_LOGIN_USERNAME, SecurityManager.encrypt(SharedPrefrenceUtil.getPrefrence(getActivity(), Constants.DB_USERNAME, Constants.LABEL_BLANK),Constants.SALT));
//					param.put(Constants.JSON_LOGIN_PASSWORD, SecurityManager.encrypt(SharedPrefrenceUtil.getPrefrence(getActivity(), Constants.DB_PASSWORD, Constants.LABEL_BLANK),Constants.SALT));
//					param.put(Constants.JSON_LOGIN_DEVICEID, SecurityManager.encrypt(Constants.LABLE_DEVICE_ID,Constants.SALT));
//
//					AsyncSyncCustomerData asyncSyncCustomerData = new AsyncSyncCustomerData(context,onResponseListener);
//					asyncSyncCustomerData.execute(param);
//				
//			}else{
//				getCustomerData();
//			}
//		}catch(Exception ex){
//			Log.d(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"CallCustomerService:"+ ex.getMessage().toString());
//		}
//	}
	
	/**
	 * Creates dialog for closing application warning!
	 * @param context Object of Context, context from where the activity is going to start.
	 * @param activity Object of activity , from where the function is called
	 */
	private void expirePwdDialog(Context context, final Activity activity) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle("Alert");
		alert.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					return true;
				}
				return false;
			}
		});

		String userName=SharedPrefrenceUtil.getPrefrence(context,  Constants.DB_USERNAME,"tech");
		String msg = context.getResources().getString(R.string.info_pwdchk_eng) +" "+ Utility.remaingDaysForLockPwd(userName,context,getActivity());
		alert.setMessage(msg);
		alert.setPositiveButton(Constants.LABLE_OK, new DialogInterface.OnClickListener() {			
			public void onClick(DialogInterface dialog, int which) {
				try{							
					dialog.dismiss();
					//					MainTabActivity.bar.selectTab(MainTabActivity.bar.getTabAt(1));
					//					SettingsForm.isPassword = true;

				}catch (Exception e) {
					dialog.dismiss();
					e.printStackTrace();
					Log.d(Constants.DEBUG,":ViewAssignment:expirePwdDialog:"+ Utility.convertExceptionToString(e));
				}
			}
		});
		//		alert.setNegativeButton(Constants.LABLE_LATER, new DialogInterface.OnClickListener() {
		//			public void onClick(DialogInterface dialog, int which) {
		//				dialog.dismiss();
		//				callCustomerService();
		//			}
		//		});
		alert.show();
	}

	class LoadCustomerData extends AsyncTask<Void,Void,ArrayList<CustomerMasterVO>> {
		ProgressDialog prDialog;
		@Override
		protected void onPreExecute() {			
			super.onPreExecute();

			try{
				prDialog = new ProgressDialog(getActivity());
				prDialog.setMessage(Constants.LABEL_BLANK+Constants.LABEL_PROGRESS_CUSTOMER_DB);
				prDialog.setCancelable(false);
				prDialog.show();
				prDialog.setProgress(10);
			}catch (Exception e) {
				e.printStackTrace();
				Log.d(Constants.DEBUG,":ViewAssignment:LoadCustomerData:AsyncTask:"+ Utility.convertExceptionToString(e));
			}

		}

		@Override
		protected ArrayList<CustomerMasterVO> doInBackground(Void... params) {
			ArrayList<CustomerMasterVO> arrayCustomer = new ArrayList<CustomerMasterVO>();
			try {
				//String whereCust = "createdby = " +Integer.parseInt(SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USER_ID,"0")) +" and isdeleted = 0 and statusid ="+ masterService.getStatusId("OP") ;
				String whereCust = "isdeleted = 0 and statusid ="+ masterService.getStatusId("OP");
				//Remove isdeleted = 0 from where condition because of not getting reassignment data
				//String whereCust = "statusid ="+ masterService.getStatusId("OP");
				arrayCustomer = (ArrayList<CustomerMasterVO>) custService.getSelectedCustomers(Constants.TBL_MST_CUSTOMER,Constants.CUSTOMER_MASTER_FIELDS,whereCust);
				System.out.println("array size::: "+arrayCustomer.size());
			} catch (Exception e) {
				e.printStackTrace();
				Log.d(Constants.DEBUG,":ViewAssignment:LoadCustomerData:AsyncTask:"+ Utility.convertExceptionToString(e));
				return null;
			}
			return arrayCustomer;
		}

		@Override
		protected void onPostExecute(ArrayList<CustomerMasterVO> result) {
			super.onPostExecute(result);

			try{
				if(result.size() > 0){
					arrayLstCustomer.clear();
					arrayLstCustomer = result;
					//					String whereCustbyUser = "createdby = " +Integer.parseInt(SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USER_ID,"0"));
					//getAllCustLists = (ArrayList<CustomerMasterVO>) custService.getSelectedCustomers(Constants.TBL_MST_CUSTOMER,Constants.CUSTOMER_MASTER_FIELDS,null); 
					totalrows = result.size();
					Log.e(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"TOTAL ROWS:-"+totalrows);
					totalpage = (int) Math.ceil(totalrows/10.0);
					Log.e(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"TOTAL Page:-"+totalpage);

					counter = 1;
					page = 1;
					first = 0;
					if(totalrows < 10)
					{
						last = totalrows;
					}else
					{
						last = 10;
					}


					lblViewAssignShwngRsltPageNo.setText((first+1)+"");
					lblViewAssignShwngRsltRowsperpage.setText(last+"");
					lblViewAssignShwngRsltTotrows.setText(totalrows+"");

					LstCustomer = result.subList(first,last);
					adapter = new CustomerListAdapter(context,R.layout.customer_list_row,LstCustomer,getActivity());
					lstCustInfo.setAdapter(adapter);
				}else{
					Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_GENERAL_ERROR, "No customer assigned yet!");
					lblViewAssignCustEmptyList.setVisibility(View.VISIBLE);
					lstCustInfo.setVisibility(View.INVISIBLE);

					lblViewAssignShwngRsltPageNo.setText(0+"");
					lblViewAssignShwngRsltRowsperpage.setText(0+"");
					lblViewAssignShwngRsltTotrows.setText(0+"");

					totalrows = 0;
					totalpage = 0;

				}

				prDialog.dismiss();
			}catch (Exception e) {
				e.printStackTrace();
				Log.d(Constants.DEBUG,":ViewAssignment:LoadCustomerData:AsyncTask:"+ Utility.convertExceptionToString(e));
			}

		}	

	}

	


	@Override
	public void onResume() {
		super.onResume();



		
		
		//	   if(userVO != null){
		//		   if(userVO.getIslock() == 1){
		//				Intent i = new Intent(getActivity(),LoginActivity.class);
		//				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
		//				startActivity(i);
		//				getActivity().finish();
		//			}
		//	   }
	}

	//		if(!Utility.isAdmin(getActivity().getApplicationContext())){
	//			
	//			if(SharedPrefrenceUtil.getPrefrence(context,Constants.LABEL_PASSWORD_LOCK ,false)){
	//				Intent i = new Intent(getActivity(),LoginActivity.class);
	//				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
	//				startActivity(i);
	//				getActivity().finish();
	//			}
	//		}


	public String getSocietyNameString(List<String> societyNameList){
		String societyName = null;
		StringBuilder sb = new StringBuilder();
		for(String str : societyNameList){
			sb.append(str).append(","); 
		}
		societyName = sb.toString();
		societyName =  societyName.substring(0, societyName.length() - 1);
		return  societyName;

	}
	/*
	public  ArrayList<CustomerMasterVO> searchFor(String custid,String custname,int status,String mrunit,List<String>societynamelist){

		ArrayList<CustomerMasterVO> returnedArrayList = new ArrayList<CustomerMasterVO>();
		try {

			int j = 0;
			String searchString = custname.toUpperCase();
			String mrunitString = mrunit.toUpperCase().trim();
			if(getAllCustLists != null)
			{
				for(int i=0;i<getAllCustLists.size();i++)
				{
					CustomerMasterVO cust = getAllCustLists.get(i);
					if(status != 0 && !custid.equals("") && !custname.equals("") && !mrunitString.equals("SELECT") && societynamelist!=null && societynamelist.size()>0)
					{
						if(cust.getCustomerID().startsWith(custid) && cust.getCustomername().toUpperCase().contains(searchString) && cust.getStatusId() == status && cust.getMrunit().toUpperCase().contains(mrunitString))
						{
							boolean flag = false;
							for (String societyName : societynamelist) {

								if(cust.getSocietyName().equalsIgnoreCase(societyName)){
									flag = true;
									break;
								}
							}
							if(flag){
								returnedArrayList.add(j,cust);
								j++;
							}
						}
					}else if(!custid.equals("") &&  !mrunitString.equals("SELECT") && societynamelist != null && societynamelist.size()> 0){
						if(cust.getCustomerID().startsWith(custid) && cust.getMrunit().toUpperCase().contains(mrunitString)){
							boolean flag = false;
							for (String societyName : societynamelist) {

								if(cust.getSocietyName().equalsIgnoreCase(societyName)){
									flag = true;
									break;
								}
							}
							if(flag){
								returnedArrayList.add(j,cust);
								j++;
							}
						}

					}else if(!custname.equals("") && !mrunitString.equals("SELECT") && societynamelist != null && societynamelist.size()> 0){
						if(cust.getCustomername().toUpperCase().contains(searchString) && cust.getMrunit().toUpperCase().contains(mrunitString)){
								returnedArrayList.add(j,cust);
								j++;
						}

					}else if(status != 0 && !mrunitString.equals("SELECT") && societynamelist != null && societynamelist.size()> 0){
						if(cust.getStatusId() == status && cust.getMrunit().toUpperCase().contains(mrunitString)){
							boolean flag = false;
							for (String societyName : societynamelist) {

								if(cust.getSocietyName().equalsIgnoreCase(societyName)){
									flag = true;
									break;
								}
							}
							if(flag){
								returnedArrayList.add(j,cust);
								j++;
							}
						}
					}else if(status != 0 && !custname.equals("")){
						if(cust.getCustomername().toUpperCase().contains(searchString) && cust.getStatusId() == status)
						{
							returnedArrayList.add(j,cust);
							j++;
						}

					}else if(status != 0 && !custid.equals("")){
						if(cust.getCustomerID().startsWith(custid) && cust.getStatusId() == status)
						{
							returnedArrayList.add(j,cust);
							j++;
						}

					}else if(status != 0 && !mrunitString.equals("SELECT")){
						if(cust.getStatusId() == status && cust.getMrunit().toUpperCase().contains(mrunitString))
						{
							returnedArrayList.add(j,cust);
							j++;
						}
					}else if(status != 0 && societynamelist !=null && societynamelist.size()>0){
						if(cust.getStatusId() == status)
						{
							boolean flag = false;
							for (String societyName : societynamelist) {

								if(cust.getSocietyName().equalsIgnoreCase(societyName)){
									flag = true;
									break;
								}
							}
							if(flag){
								returnedArrayList.add(j,cust);
								j++;
							}
						}

					}else if(!custid.equals("") && !mrunitString.equals("SELECT")){
						if(cust.getCustomerID().startsWith(custid) &&  cust.getMrunit().toUpperCase().contains(mrunitString))
						{
							returnedArrayList.add(j,cust);
							j++;
						}
					}else if(!custid.equals("") && societynamelist !=null && societynamelist.size()>0){
						if(cust.getCustomerID().startsWith(custid))
						{
							boolean flag = false;
							for (String societyName : societynamelist) {

								if(cust.getSocietyName().equalsIgnoreCase(societyName)){
									flag = true;
									break;
								}
							}
							if(flag){
								returnedArrayList.add(j,cust);
								j++;
							}
						}
					}else if(!custname.equals("") && !mrunitString.equals("SELECT")){
						if(cust.getCustomername().toUpperCase().contains(searchString)&& cust.getMrunit().toUpperCase().contains(mrunitString))
						{
							returnedArrayList.add(j,cust);
							j++;
						}

					}else if(!custname.equals("") && societynamelist !=null && societynamelist.size()>0){
						if(cust.getCustomername().toUpperCase().contains(searchString)){
							boolean flag = false;
							for (String societyName : societynamelist) {

								if(cust.getSocietyName().equalsIgnoreCase(societyName)){
									flag = true;
									break;
								}
							}
							if(flag){
								returnedArrayList.add(j,cust);
								j++;
							}
						}
					}else if(!custid.equals("")){
						if(cust.getCustomerID().startsWith(custid))
						{
							returnedArrayList.add(j,cust);
							j++;
						}

					}else if(!custname.equals("")){

						if(cust.getCustomername().toUpperCase().contains(searchString))
						{
							returnedArrayList.add(j,cust);
							j++;
						}
					}else if(status != 0 ){
						if(cust.getStatusId() == status)
						{
							returnedArrayList.add(j,cust);
							j++;
						}
					}else if(!mrunitString.equals("SELECT")){
						if(cust.getMrunit() == mrunitString)
						{
							returnedArrayList.add(j,cust);
							j++;
						}
					}else if(societynamelist !=null && societynamelist.size()>0){
						boolean flag = false;
						for (String societyName : societynamelist) {
							if(cust.getSocietyName().equalsIgnoreCase(societyName)){
								flag = true;
								break;
							}
						}
						if(flag){
							returnedArrayList.add(j,cust);
							j++;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnedArrayList;
	}
	 */
	
	public List<CustomerMasterVO> getCustomerId(List<CustomerMasterVO> custmoerList,String custid){
		List<CustomerMasterVO> returnedArrayList = new ArrayList<CustomerMasterVO>();
		if(custid != null && !custid.equals("")){
			for(int i=0;i<custmoerList.size();i++)
			{
				CustomerMasterVO customerVo = custmoerList.get(i);
					if(customerVo.getCustomerID().startsWith(custid))
					{
						returnedArrayList.add(customerVo);

					}
			}
		}
		return returnedArrayList;
	}
	
	public List<CustomerMasterVO> getMeterNumber(List<CustomerMasterVO> custmoerList,String meterNumber){
		List<CustomerMasterVO> returnedArrayList = new ArrayList<CustomerMasterVO>();
		if(meterNumber != null && !meterNumber.equals("")){
			for(int i=0;i<custmoerList.size();i++)
			{
				CustomerMasterVO customerVo = custmoerList.get(i);
					if(customerVo.getMeterNumber().startsWith(meterNumber))
					{
						returnedArrayList.add(customerVo);

					}
			}
		}
		return returnedArrayList;
	}
	
	
	public List<CustomerMasterVO> getCustomerName(List<CustomerMasterVO> custmoerList,String custname){
		List<CustomerMasterVO> returnedArrayList = new ArrayList<CustomerMasterVO>();
		String searchName = custname.toUpperCase();
		if(custname!= null && !custname.equals("")){
			for(int i=0;i<custmoerList.size();i++)
			{
				CustomerMasterVO customerVo = custmoerList.get(i);
					if(customerVo.getCustomername().toUpperCase().contains(searchName))
					{
						returnedArrayList.add(customerVo);

					}
			}
		}
		return returnedArrayList;
	}
	
	public List<CustomerMasterVO> getStatusId(List<CustomerMasterVO> custmoerList,int statusid){
		List<CustomerMasterVO> returnedArrayList = new ArrayList<CustomerMasterVO>();
		if(statusid != 0 ){
			for(int i=0;i<custmoerList.size();i++)
			{
				CustomerMasterVO customerVo = custmoerList.get(i);
					if(customerVo.getStatusId() == statusid)
					{
						returnedArrayList.add(customerVo);
					}
			}
	    }
		return returnedArrayList;
	}
	
	public List<CustomerMasterVO> getMRUList(List<CustomerMasterVO> custmoerList,String mrunit){
		List<CustomerMasterVO> returnedArrayList = new ArrayList<CustomerMasterVO>();
		String mrunitString = mrunit.toUpperCase().trim();
		if(!mrunitString.equalsIgnoreCase("SELECT") ){
			
			for(int i=0;i<custmoerList.size();i++)
			{
				CustomerMasterVO customerVo = custmoerList.get(i);
					if(customerVo.getMrunit().equals(mrunitString))
					{
						returnedArrayList.add(customerVo);

					}
			}
	   }
		return returnedArrayList;
	}
	
	
	public List<CustomerMasterVO> getSocietyNameList(List<CustomerMasterVO> custmoerList,List<String>societynamelist){
		List<CustomerMasterVO> returnedArrayList = new ArrayList<CustomerMasterVO>();
		if(societynamelist !=null && societynamelist.size()>0){
			if(societynamelist !=null && societynamelist.size()>0){
				for(int i=0;i<custmoerList.size();i++)
				{
					CustomerMasterVO customerVo = custmoerList.get(i);
						if(societynamelist.contains(customerVo.getSocietyName())){
							returnedArrayList.add(customerVo);
						}
				}
			  }
		}
		return returnedArrayList;
	}
	
	
	public  ArrayList<CustomerMasterVO> searchFor(String custid,String custname,int status,String mrunit,List<String>societynamelist,String meterNumber){
		List<CustomerMasterVO> returnedArrayList = new ArrayList<CustomerMasterVO>();
//		List<CustomerMasterVO> resultArrayList = new ArrayList<CustomerMasterVO>();
		returnedArrayList = getAllCustLists;
		try {
			if(custid != null && !custid.equals("")){
				returnedArrayList = getCustomerId(returnedArrayList, custid);
			}
			if(custname!= null && !custname.equals("")){
				if(returnedArrayList.size()>0){
					returnedArrayList = getCustomerName(returnedArrayList, custname);
				}
			}
			if(status != 0 ){
				if(returnedArrayList.size()>0){
					returnedArrayList = getStatusId(returnedArrayList, status);
				}
			}
			if(!mrunit.equalsIgnoreCase("SELECT") ){
				if(returnedArrayList.size()>0){
					returnedArrayList = getMRUList(returnedArrayList, mrunit);
				}
			}
			if(societynamelist !=null && societynamelist.size()>0){
				if(returnedArrayList.size()>0){
					returnedArrayList = getSocietyNameList(returnedArrayList, societynamelist);
				}
			}
			
			if(meterNumber != null && !meterNumber.equals("")){
				if(returnedArrayList.size()>0){
					returnedArrayList = getMeterNumber(returnedArrayList, meterNumber);
				}
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	   return (ArrayList<CustomerMasterVO>) returnedArrayList;
	}



	protected OnResponseListenerFindCustomer OnResponseListenerFind = new OnResponseListenerFindCustomer() {

		public void onSuccess(ArrayList<CustomerMasterVO> result) {

			getAllCustLists = result;
			arrayLstCustomer = searchFor(strCustID, strCustName,CustStatus,strMrUnit,selectedsociety,meterNumber);


			if(arrayLstCustomer.size() > 0){
				totalrows = arrayLstCustomer.size();
				Log.e(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"Search TOTAL  ROWS:-"+totalrows);
				totalpage = (int) Math.ceil(totalrows/10.0);
				Log.e(Constants.TAG_VIEW_ASSIGN_ACTIVITY,"Search TOTAL Page:-"+totalpage);

				counter = 1;
				page = 1;
				first = 0;
				if(totalrows < 10)
				{
					last = totalrows;
				}else
				{
					last = 10;
				}
				if(totalrows < last){
					//	LstCustomer.clear();
					LstCustomer = arrayLstCustomer.subList(first,totalrows);
					lblViewAssignShwngRsltRowsperpage.setText(totalrows+"");
				}else{
					//LstCustomer.clear();
					LstCustomer = arrayLstCustomer.subList(first,last);
					lblViewAssignShwngRsltRowsperpage.setText(last+"");
				}
				lblViewAssignShwngRsltPageNo.setText((first+1)+"");
				lblViewAssignShwngRsltTotrows.setText(totalrows+"");

				adapter = new CustomerListAdapter(context,R.layout.customer_list_row,LstCustomer,getActivity());
				lstCustInfo.setAdapter(adapter);
				lblViewAssignCustEmptyList.setVisibility(View.INVISIBLE);
				lstCustInfo.setVisibility(View.VISIBLE);
			}else{	
				
				if(Global.isFormSubmit){
					setDefaultValue();
				}
				lblViewAssignCustEmptyList.setVisibility(View.VISIBLE);
				lstCustInfo.setVisibility(View.INVISIBLE);

				lblViewAssignShwngRsltPageNo.setText(0+"");
				lblViewAssignShwngRsltRowsperpage.setText(0+"");
				lblViewAssignShwngRsltTotrows.setText(0+"");

				totalrows = 0;
				totalpage = 0;
				Global.isFormSubmit = false;
			}

		}
		public void onFailure(String message) {

		}
	};

     ICleanUpResponseListener iCleanUpResponseListener = new ICleanUpResponseListener() {
		
		public void onSuccess() {
			Log.d("On Clean Up Sucess ::::::","set defualt Value");
			setDefaultValue();
			if(LstCustomer != null){
				LstCustomer.clear();
				adapter.notifyDataSetChanged();
			}
			lstCustInfo.setVisibility(View.INVISIBLE);
			lblViewAssignCustEmptyList.setVisibility(View.VISIBLE);
			lblViewAssignShwngRsltPageNo.setText(0+"");
			lblViewAssignShwngRsltRowsperpage.setText(0+"");
			lblViewAssignShwngRsltTotrows.setText(0+"");
			if(societyname != null){
				societyname = null;
			}
			
		}
	};
	
}