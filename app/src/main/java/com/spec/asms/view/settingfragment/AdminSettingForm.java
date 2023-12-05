package com.spec.asms.view.settingfragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Adapter.SettingPassListAdapter;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.SecurityManager;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.ClearDataAdminService;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.service.async.AsyncTestConnection;
import com.spec.asms.service.async.AsyncTestConnection.OnResponseListenerTestConnection;
import com.spec.asms.vo.UserMasterVO;

/**
 * 
 * @author jenisha
 *
 */
public class AdminSettingForm extends Fragment{

	@SuppressWarnings("unused")
	private FragmentManager fragmentManager;

	@SuppressWarnings("unused")
	private int userID;	
	public View formView;

	public Button btnSettingIp;
	public EditText edtSettingIP;
	private Button btnClearData;

	public String strSettingIP;
	public UserMasterService userService;

	private ArrayList<UserMasterVO>userList=new ArrayList<UserMasterVO>();
	private SettingPassListAdapter settingPassAdapter;
	private ListView userListView;
	
//	private RelativeLayout rlaLayoutPassSetting;

	
	public static AdminSettingForm newInstance(int id){
		AdminSettingForm maintanenceForm = new AdminSettingForm();
		Bundle args = new Bundle();
		args.putInt("id",id);
		maintanenceForm.setArguments(args);
		return maintanenceForm; 
	}

	public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		formView = inflater.inflate(R.layout.adminservice,null);
		Log.d(Constants.DEBUG,":::AdminSettingForm:::");
		
		SharedPreferences pref = getActivity().getSharedPreferences("com.spec.asms",Context.MODE_PRIVATE);
		userID = pref.getInt("userID",0);
		userService = new UserMasterService(getActivity());

		fragmentManager = getFragmentManager();
		btnSettingIp = (Button)formView.findViewById(R.id.btnSettingIp);
		btnClearData =(Button)formView.findViewById(R.id.btnClearData);
		edtSettingIP = (EditText)formView.findViewById(R.id.edtSettingIP);
//		rlaLayoutPassSetting = (RelativeLayout)formView.findViewById(R.id.rlaLayoutPassSetting);
		
		if(userService.getAdminURL().equals(Constants.LABEL_BLANK)){
			edtSettingIP.setText(Constants.LABEL_BLANK);
		}else{
			edtSettingIP.setText(userService.getAdminURL());
		}

		userListView =(ListView)formView.findViewById(R.id.list);
		userList = userService.getLockedUsers();
//		
//		if(userList.size() <= 0)
//		{
//			rlaLayoutPassSetting.setVisibility(View.INVISIBLE);
//		}
		System.out.println("USer List size : #######"+userList.size());
		Log.d("Admin Setting Form", "Device ID :::: "+Utility.getDeviceId(getActivity()));
		Log.d("Admin Setting Form", "Setting IP :::: "+strSettingIP);
		Log.d("Admin Menufecture Name ","NAMe ::::"+Utility.getDeviceManufectureName());
		Log.d("Admin Device Model Number ","Model Number ::::"+Utility.getDeviceModelNumber());
		
		settingPassAdapter = new SettingPassListAdapter(getActivity(),getActivity(),userList);
		userListView.setAdapter(settingPassAdapter);

		btnSettingIp.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			public void onClick(View v) {

				strSettingIP = edtSettingIP.getText().toString().trim();
				try{
					Log.d("Admin Setting Form", "Device ID :::: "+Utility.getDeviceId(getActivity()));
					Log.d("Admin Setting Form", "Setting IP :::: "+strSettingIP);
					Log.d("Admin Menufecture Name ","NAMe ::::"+Utility.getDeviceManufectureName());
					Log.d("Admin Device Model Number ","Model Number ::::"+Utility.getDeviceModelNumber());
					
					Constants.WS_URL = strSettingIP;
					if(!strSettingIP.equals(Constants.LABEL_BLANK)){

						if(Utility.isNetAvailable(getActivity())){
							Map<String, Object> param = new HashMap<String, Object>();
							param.put(Constants.JSON_DEVICE_NO,SecurityManager.encrypt(Utility.getDeviceId(getActivity()),Constants.SALT));
							param.put(Constants.JSON_DEVICE_MANUFACTURER,SecurityManager.encrypt(Utility.getDeviceManufectureName(),Constants.SALT));
							param.put(Constants.JSON_DEVICE_MODEL,SecurityManager.encrypt(Utility.getDeviceModelNumber(),Constants.SALT));
							
							AsyncTestConnection asyncTestConnection = new AsyncTestConnection(getActivity(),onResponseListenerTestConnection);
							asyncTestConnection.execute(param);
						}else {
							Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
						}
					}else{
						Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_ENTER_URL);
					}
				}catch(Exception ex){
					ex.printStackTrace();
					Log.d(Constants.DEBUG,":AdminSettingForm:"+ Utility.convertExceptionToString(ex));
				}
			}
		});
		btnClearData.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
				alert.setTitle(Constants.ALERT_TITLE_CLEAR);
				alert.setOnKeyListener(new OnKeyListener() {
					public boolean onKey(DialogInterface dialog, int keyCode,
							KeyEvent event) {
						if (keyCode == KeyEvent.KEYCODE_BACK) {
							return true;
						}
						return false;
					}
				});
				alert.setMessage(Constants.LABEL_ALERT_CLEAR_CONFORMATION);
				alert.setPositiveButton(Constants.LABLE_YES, new DialogInterface.OnClickListener() {			
					public void onClick(DialogInterface dialog, int which) {
						try{							
							dialog.dismiss();
							ClearDataAdminService cleanupService = new ClearDataAdminService(getActivity());
							cleanupService.execute();
							
						}catch (Exception e) {
							dialog.dismiss();
							e.printStackTrace();
						}
					}
				});
				alert.setNegativeButton(Constants.LABLE_NO, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				alert.show();
				
			}
		});

		return formView;
	}
	

	/**
	 * Method used when User Authenticate successfully to insert all master data to database.
	 */
	protected OnResponseListenerTestConnection onResponseListenerTestConnection = new OnResponseListenerTestConnection() {

		public void onSuccess(JSONObject responce) {
			Log.e(Constants.TAG_LOGIN_ACTIVITY, "in onResponseListenerTestConnection :::::: "+responce);

			try{
				if(responce != null && responce.equals(Constants.JSON_FAILED)){
					Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.JSON_FAILED);
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_JSON_FAILED);
				}else if(responce.has(Constants.NETWORK_EXCEPTION_METHOD)){
					Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.NETWORK_EXCEPTION_METHOD);
					Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
				}else if(responce.has(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD)){
					Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.CONNECTION_REFUSED_EXCEPTION_METHOD);
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.SOCKET_EXCEPTION)){
					Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.SOCKET_EXCEPTION);
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.IOEXCEPTION)){
					Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.IOEXCEPTION);
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.XMLPULLPARSEREXCEPTION)){
					Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.XMLPULLPARSEREXCEPTION);
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.EXCEPTION)){
					Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.EXCEPTION);
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.getString(Constants.JSON_TEST_STATUS).equals("True")){

					ContentValues cv = new ContentValues();
					cv.put(Constants.DB_URL, strSettingIP);
					if(userService.insertUser(Constants.TBL_DTL_ADMIN, cv) != -1){
						Log.e("IP Added","IP Added");
						Constants.WS_URL = strSettingIP;
					}
					Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_GENERAL, Constants.TESTING_CONNECTION_SUCESSFUL);
				}else{
					Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_GENERAL, Constants.TESTING_CONNECTION_UNSUCESSFULL);
				}
			}catch(JSONException ex){
				ex.printStackTrace();
				Log.d(Constants.DEBUG,":AdminSettingForm:"+ Utility.convertExceptionToString(ex));
			}
		}

		public void onFailure(String message) {
		}
	};
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
	}
}