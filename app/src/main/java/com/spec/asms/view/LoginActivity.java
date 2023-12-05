package com.spec.asms.view;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.CoreActivity;
import com.spec.asms.common.Global;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.FileDownloder;
import com.spec.asms.common.utils.SecurityManager;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.BatchSubmitService;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.service.async.AsyncAuthenticate;
import com.spec.asms.service.async.AsyncAuthenticate.OnResponseListenerAuth;
import com.spec.asms.service.async.AsyncGetAllMasters;
import com.spec.asms.service.async.AsyncGetAllMasters.OnResponseListener;
import com.spec.asms.service.async.AsyncUpdateCustomerStatus;
import com.spec.asms.service.async.AsyncUpdateCustomerStatus.OnResponseUpdateCustomerStatusId;
import com.spec.asms.vo.UserLockVO;
import com.spec.asms.vo.UserMasterVO;

/**
 * A class is used to login for user, Authenticate user and insert master data to database.
 * 
 */
public class LoginActivity extends CoreActivity implements OnClickListener {

	private EditText edtUsername, edtPassword;
	private TextView lblSafetyTips;
	private TextView lblUserName;
	private TextView lblPassword;
	private TextView lblLoginHead;
	private TextView lblSugDeviceId;
	private TextView lblDeviceId;

	private CheckBox chkLanguage;

	private Button btnLogin;

	private String strUsername;
	private String strPassword;
	private String safetytips[];

	private int userID;
	private int id;

	private UserMasterService userService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginscreen);

		ApplicationLog.writeToFile(getApplicationContext(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::LoginActivityStarted:::");
		Log.d("DEVICE Id", "DEVICE ID: "+Utility.getDeviceId(this));
		
		lblSafetyTips =(TextView)findViewById(R.id.lblLoginSafetyTips);
		safetytips =(getResources().getStringArray(R.array.safetyTips));
		RndomFunction();
		//		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		pref = getSharedPreferences("com.spec.asms",Context.MODE_PRIVATE);
		//userID = pref.getInt("userID", 0);
		userID = Integer.parseInt(SharedPrefrenceUtil.getPrefrence(getApplicationContext(), Constants.DB_USER_ID, "0"));
		userService = new UserMasterService(LoginActivity.this);
		edtUsername = (EditText) findViewById(R.id.edtLoginUsername);
		edtPassword = (EditText) findViewById(R.id.edtLoginPassword);
		lblLoginHead=(TextView)findViewById(R.id.lblLoginLogingBoxTital);
		lblUserName=(TextView)findViewById(R.id.lblLoginEnterUsername);
		lblPassword=(TextView)findViewById(R.id.lblLogibEnterPassword);
		lblSugDeviceId = (TextView)findViewById(R.id.lblSugDeviceId);
		lblDeviceId = (TextView)findViewById(R.id.lblDeviceId);
		chkLanguage = (CheckBox)findViewById(R.id.logniLanguage);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		changeLanguage(id);

		lblSugDeviceId.setText(""+Utility.getDeviceId(getApplicationContext()));
		btnLogin.setOnClickListener(this);	

		chkLanguage.setTypeface(Global.typeface_Guj);
		chkLanguage.setText(getResources().getString(R.string.title_chng_lang_guj));
		chkLanguage.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1){					
					SharedPrefrenceUtil.setPrefrence(LoginActivity.this, Constants.PREF_LANGUAGE,Constants.LANGUAGE_GUJRATI);
				}else{					
					SharedPrefrenceUtil.setPrefrence(LoginActivity.this, Constants.PREF_LANGUAGE,Constants.LANGUAGE_ENGLISH);
				}
				//onCreate(null);
				changeLanguage(id);
			}
		});

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		try {
			if(null != BatchSubmitService.timer){
				BatchSubmitService.timer.cancel();
			}
		} catch (Exception e) {
			Log.d(Constants.DEBUG,"LoginActivity:onResume:"+ Utility.convertExceptionToString(e));
			
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btnLogin:		
			strUsername = edtUsername.getText().toString();
			strPassword = edtPassword.getText().toString();
			Global.isFilterRequired = false;
			SharedPrefrenceUtil.setPrefrence(this, Constants.LABEL_IS_CUST_AVAILABLE, false);
			Intent stopIntent = new Intent(LoginActivity.this,BatchSubmitService.class);
			stopService(stopIntent);

			if(strUsername.equalsIgnoreCase("admin") ){//&& strPassword.equalsIgnoreCase("admin")

				if(strPassword.equalsIgnoreCase("admin"))
				{
					SharedPrefrenceUtil.setPrefrence(LoginActivity.this,Constants.DB_USERNAME,strUsername);
					SharedPrefrenceUtil.setPrefrence(LoginActivity.this,Constants.DB_PASSWORD,strPassword);
					Intent i = new Intent(LoginActivity.this,MainTabActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//|Intent.FLAG_ACTIVITY_NO_HISTORY);
					startActivityForResult(i, Constants.REQ_MENU_ACTIVITY);
				}else{
					clearFields();
					Toast.makeText(getApplicationContext(),getResources().getString(R.string.error_pwdusername),Toast.LENGTH_LONG).show();
				}

			}else{
				String where = "username = '" + strUsername + "' and password = '"+ SecurityManager.encrypt(strPassword,Constants.SALT) + "'";
				UserMasterVO userVO = userService.getUserInfo(Constants.TBL_MST_USER, Constants.USER_MASTER_FIELDS,where);

				if(Utility.isNetAvailable(LoginActivity.this)){
					if(userVO.getIslock() == 1){
						Utility.alertDialog(LoginActivity.this, Constants.ALERT_TITLE_LOGIN_ERROR, Constants.ERROR_PASSWORD_LOCKED);
					}
					else{
						if(!Constants.WS_URL.equals(Constants.BLANK)){
							IsuserAuthenticate();
						}else{
						   Utility.alertDialog(LoginActivity.this, Constants.ALERT_TITLE_GENERAL, Constants.ERROR_WEB_SERVICE_URL_NOT_SET);
						}
					}
				}
				else{
					//					if(SharedPrefrenceUtil.getPrefrence(LoginActivity.this,Constants.LABEL_PASSWORD_LOCK ,false)){
					//						Utility.alertDialog(LoginActivity.this, Constants.ALERT_TITLE_GENERAL, Constants.ERROR_PASSWORD_LOCKED);
					//					}else{
					createOfflineDialog(LoginActivity.this,LoginActivity.this);
					//					System.out.println("User Password Lock "+userVO.getIslock());
					//					if(userVO.getIslock() == 1){
					//						Utility.alertDialog(LoginActivity.this, Constants.ALERT_TITLE_GENERAL, Constants.ERROR_PASSWORD_LOCKED);
					//					}else{
					//						if (userVO.getUserID() > 0) {
					//
					//							ContentValues cv = new ContentValues();
					//							cv.put(Constants.USER_MASTER_FIELDS[4],String.valueOf(System.currentTimeMillis()));
					//							userService.updateUserByID(Constants.TBL_MST_USER, cv, "userid ="+userVO.getUserID());
					//
					//							Intent i = new Intent(LoginActivity.this,MainTabActivity.class);
					//							i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					//							startActivityForResult(i, Constants.REQ_MENU_ACTIVITY);
					//						}
					//						else{
					//							Utility.alertDialog(LoginActivity.this, Constants.ALERT_TITLE_GENERAL, Constants.ERROR_INTERNET_CONNECTION);
					//						}
					//					}
				}
			}
		}
	}

	/**
	 * Method to check if user authenticate or not.
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void IsuserAuthenticate() {
		try {
			if (!validate()) {

				//				String date = String.valueOf(new Date().getTime());
				System.out.println(" FIRST DATE "+String.valueOf(new Date().getTime()));

//				String date = String.valueOf(Utility.getApkInstallDate(LoginActivity.this));
				String appVersion = Utility.getApkVersion(LoginActivity.this);

//				System.out.println("Installation Date :::"+ date);

//				Log.d(Constants.TAG_LOGIN_ACTIVITY, "Date :::: "+date);
				Log.d(Constants.TAG_LOGIN_ACTIVITY, "strUsername :::: "+strUsername);
				Log.d(Constants.TAG_LOGIN_ACTIVITY, "Password :::: "+strPassword);
				Log.d(Constants.TAG_LOGIN_ACTIVITY, "Device id ::::"+Constants.LABLE_DEVICE_ID);

				Map<String, Object> param = new HashMap<String, Object>();
				param.put(Constants.JSON_LOGIN_USERNAME, SecurityManager.encrypt(strUsername,Constants.SALT));
				param.put(Constants.JSON_LOGIN_PASSWORD, SecurityManager.encrypt(strPassword,Constants.SALT));
				param.put(Constants.JSON_LOGIN_DEVICEID, SecurityManager.encrypt(Constants.LABLE_DEVICE_ID,Constants.SALT));//Utility.getDeviceId(LoginActivity.this));
				param.put(Constants.JSON_LOGIN_APPDATE_TIME,SecurityManager.encrypt(appVersion,Constants.SALT));
				param.put(Constants.JSON_LOGIN_ANDROID_API_LEVEL, Build.VERSION.SDK_INT);
				UserLockVO userLockVO = userService.getLockedStatus(strUsername);
				System.out.println(" USER LOCK ISLOCK ********** "+userLockVO.getIsLock());
				System.out.println(" USER LOCK UPDATE DATE ********** "+userLockVO.getPasswordUpdateDate());

				if(userLockVO.getIsLock() != null){
					if(userLockVO.getPasswordUpdateDate().equals(Constants.LABEL_BLANK)){						
						param.put(Constants.JSON_LOGIN_ISLOCKED,SecurityManager.encrypt(Constants.LABLE_DEFAULT_UNLOCK,Constants.SALT));
						param.put(Constants.JSON_LOGIN_PSWRD_UPDATEDATE,SecurityManager.encrypt(Constants.LABEL_BLANK,Constants.SALT));						
					}else{
						
						Utility.chkLoginAlert(strUsername, LoginActivity.this,LoginActivity.this);
						
						UserLockVO userLockVO2 = userService.getLockedStatus(strUsername);
						System.out.println(" USER LOCK ISLOCK 2 ********** "+userLockVO2.getIsLock());
						System.out.println(" USER LOCK UPDATE DATE 2 ********** "+userLockVO2.getPasswordUpdateDate());
						param.put(Constants.JSON_LOGIN_ISLOCKED,SecurityManager.encrypt(userLockVO2.getIsLock(),Constants.SALT));
						param.put(Constants.JSON_LOGIN_PSWRD_UPDATEDATE,SecurityManager.encrypt(userLockVO2.getPasswordUpdateDate(),Constants.SALT));
					}
				}

				Log.e(Constants.TAG_LOGIN_ACTIVITY, "DateTime:-"+Utility.getcurrentTimeStamp()+" Date encrypt :::: "+SecurityManager.encrypt(appVersion,Constants.SALT));
				Log.e(Constants.TAG_LOGIN_ACTIVITY, "DateTime:-"+Utility.getcurrentTimeStamp()+"strUsername encrypt :::: "+SecurityManager.encrypt(strUsername,Constants.SALT));
				Log.e(Constants.TAG_LOGIN_ACTIVITY, "DateTime:-"+Utility.getcurrentTimeStamp()+"Password encrypt :::: "+SecurityManager.encrypt(strPassword,Constants.SALT));
				Log.e(Constants.TAG_LOGIN_ACTIVITY, "DateTime:-"+Utility.getcurrentTimeStamp()+"Device id encrypt :::: "+SecurityManager.encrypt(Constants.LABLE_DEVICE_ID,Constants.SALT));

				AsyncAuthenticate asyncAuthenticate = new AsyncAuthenticate(LoginActivity.this,onResponseListenerAuth);
				asyncAuthenticate.execute(param);

			}else{
				clearFields();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,"LoginActivity:iSAuthenticate:"+ Utility.convertExceptionToString(e));
			
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constants.REQ_MENU_ACTIVITY) {
			if (userID == 0) {
				finish();
			}
			edtUsername.setText(Constants.LABEL_BLANK);
			edtPassword.setText(Constants.LABEL_BLANK);
		}
	}

	/**
	 * Method to validate username and password.
	 * @return boolean : true - if any error , false - if no error.
	 */
	private boolean validate() {
		boolean isError = false;

		if (Constants.LABEL_BLANK.equals(strUsername)) {
			isError = true;
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_enterusername),Toast.LENGTH_LONG).show();
		} else if (Constants.LABEL_BLANK.equals(strPassword)) {
			isError = true;
			Toast.makeText(getApplicationContext(),	getResources().getString(R.string.error_enterpwd),	Toast.LENGTH_LONG).show();
		}
		return isError;
	}

	/**
	 * Method to display random safety tips on login page.
	 */
	private void RndomFunction(){
		lblSafetyTips.setTypeface(Global.typeface_Guj);
		Random random = new Random();
		int currentIndex = random.nextInt(safetytips.length);
		int previousState = SharedPrefrenceUtil.getPrefrence(LoginActivity.this,Constants.PREF_SAFETY_TIPS,currentIndex);
		if(currentIndex == previousState){
			currentIndex = random.nextInt(safetytips.length);
		}
		lblSafetyTips.setText(safetytips[currentIndex]);
		lblSafetyTips.setSelected(true);
		SharedPrefrenceUtil.setPrefrence(LoginActivity.this,Constants.PREF_SAFETY_TIPS,currentIndex);
	}

	/**
	 * 
	 * @param currentDate
	 * @param databaseDate
	 */

	public void dateDifference(long currentDate,long databaseDate){
		long diff=currentDate-databaseDate;
		long diffInDay=diff/(24*60*60*1000);
		if(diffInDay>30 && diffInDay<45){
			//Toast.makeText(this,"change pswd",Toast.LENGTH_LONG).show();
		}else if(diffInDay>45){
			//Toast.makeText(this,"password has been Blocked",Toast.LENGTH_LONG).show();
		}else{
			//Toast.makeText(getBaseContext(), "date difference is...."+diffInDay,Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Method to change language English or Gujarati.
	 * @param id : 1 - English,2 - Gujarati.  
	 */
	public void changeLanguage(int id){
		id = SharedPrefrenceUtil.getPrefrence(this,Constants.PREF_LANGUAGE,Constants.LANGUAGE_ENGLISH);
		switch (id){
		case Constants.LANGUAGE_ENGLISH:
			lblUserName.setTypeface(Global.typeface_Eng);
			lblPassword.setTypeface(Global.typeface_Eng);
			lblLoginHead.setTypeface(Global.typeface_Eng);
			lblDeviceId.setTypeface(Global.typeface_Eng);
			lblUserName.setText(getResources().getText(R.string.title_EnterUsername_Eng));
			lblPassword.setText(getResources().getText(R.string.title_EnterPassword_Eng));
			lblLoginHead.setText(getResources().getText(R.string.title_header_LogingBox_Eng));
			lblDeviceId.setText(getResources().getText(R.string.title_DeviceID_Eng));
			chkLanguage.setChecked(false);
			break;
		case Constants.LANGUAGE_GUJRATI:
			chkLanguage.setChecked(true);
			lblUserName.setTypeface(Global.typeface_Guj);
			lblPassword.setTypeface(Global.typeface_Guj);
			lblLoginHead.setTypeface(Global.typeface_Guj);
			lblDeviceId.setTypeface(Global.typeface_Guj);
			lblDeviceId.setText(getResources().getText(R.string.title_DeviceID_Guj));
			lblUserName.setText(getResources().getText(R.string.title_EnterUsername_Guj));
			lblPassword.setText(getResources().getText(R.string.title_EnterPassword_Guj));
			lblLoginHead.setText(getResources().getText(R.string.title_header_LogingBox_Guj));
			break;
		default:
			break;
		}
	}

	/**
	 * Method call when all master data successfully insert to database from server to navigate to MainTab Activity.
	 */
	protected OnResponseListener onResponseListener = new OnResponseListener() {
		public void onSuccess(JSONObject responce) {
			try {
				//Log.e(Constants.TAG_LOGIN_ACTIVITY, "in onResponseListener :::::: "+responce);

				//				if(SharedPrefrenceUtil.getPrefrence(LoginActivity.this,Constants.LABEL_PASSWORD_LOCK ,false)){
				//					Utility.alertDialog(LoginActivity.this, Constants.ALERT_TITLE_GENERAL, Constants.ERROR_PASSWORD_LOCKED);
				//				}else{
				
				if(responce != null && responce.equals(Constants.JSON_FAILED)){
					Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.JSON_FAILED);
					Utility.alertDialog(LoginActivity.this,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_JSON_FAILED);
				}else if(responce.has(Constants.NETWORK_EXCEPTION_METHOD)){
					Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.NETWORK_EXCEPTION_METHOD);
					Utility.alertDialog(LoginActivity.this, Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
				}else if(responce.has(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD)){
					Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.CONNECTION_REFUSED_EXCEPTION_METHOD);
					Utility.alertDialog(LoginActivity.this,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.SOCKET_EXCEPTION)){
					Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.SOCKET_EXCEPTION);
					Utility.alertDialog(LoginActivity.this,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.IOEXCEPTION)){
					Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.IOEXCEPTION);
					Utility.alertDialog(LoginActivity.this,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.XMLPULLPARSEREXCEPTION)){
					Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.XMLPULLPARSEREXCEPTION);
					Utility.alertDialog(LoginActivity.this,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.EXCEPTION)){
					Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.EXCEPTION);
					Utility.alertDialog(LoginActivity.this,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.getString(Constants.METHOD_AUTHENTICATE).equalsIgnoreCase(Constants.LABEL_TRUE)){

					JSONObject masterData = responce.getJSONObject(Constants.JSON_MASTER_DATA);
					Log.d("JSON", "::::::::: masterData ::: "+masterData);
					Log.d("JSON", "::::::::: masterData.getString(SqlTimeStamp) ::: "+masterData.getString(Constants.JSON_SQL_TIMESTAMP));
					if(Utility.checkTimeDifference(masterData.getString(Constants.JSON_SQL_TIMESTAMP))){
						//User logged in ...now set shared prefrence
						SharedPrefrenceUtil.setPrefrence(LoginActivity.this, Constants.LABEL_LOGIN, true);
						
						if(Utility.isAutoSubmitServiceRunning(LoginActivity.this)) 
						{
							Intent i = new Intent(LoginActivity.this,BatchSubmitService.class);
							Log.e("DateTime:-"+Utility.getcurrentTimeStamp()+"LoginActivity",">>>>>>>>>>>> Service stop <<<<<<<<<<< ");
							Log.e("DateTime:-"+Utility.getcurrentTimeStamp()+"LoginActivity",">>>>>>>>>>>> Service start<<<<<<<<<<< ");
							stopService(i);
							Intent i1 = new Intent(LoginActivity.this,BatchSubmitService.class);
							startService(i1);
							
						}else{ //if not running then start service
							Intent i = new Intent(LoginActivity.this,BatchSubmitService.class);
							startService(i);
						}

						Intent i = new Intent(LoginActivity.this,MainTabActivity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//|Intent.FLAG_ACTIVITY_NO_HISTORY);
						startActivity(i);
						finish();
						
						/*AsyncUpdateCustomerStatus asyncUpdateCustomerStatus = new AsyncUpdateCustomerStatus(LoginActivity.this,onResponseUpdateCustomerStatusId);
						asyncUpdateCustomerStatus.execute();*/
					}else{
						Utility.alertDialog(LoginActivity.this, Constants.ALERT_TITLE_LOGIN_ERROR,Constants.ERROR_SERVER_CONNECTION );
					}
				}else {
					Utility.alertDialog(LoginActivity.this, Constants.ALERT_TITLE_LOGIN_ERROR, getResources().getString(R.string.error_pwdusername));
					clearFields();
				}
				//				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.d(Constants.DEBUG,"LoginActivity:onResponseListener:"+ Utility.convertExceptionToString(e));
			}
		}
		public void onFailure(String message) {
		}
	};
	
/*	protected OnResponseUpdateCustomerStatusId onResponseUpdateCustomerStatusId = new OnResponseUpdateCustomerStatusId() {
		
		@Override
		public void onSuccess(String result) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onFailure(String message) {
			// TODO Auto-generated method stub
			
		}
	};
*/
	/**
	 * Method used when User Authenticate successfully to insert all master data to database.
	 */
	protected OnResponseListenerAuth onResponseListenerAuth = new OnResponseListenerAuth() {

		@SuppressWarnings("unchecked")
		public void onSuccess(JSONObject responce) {
			try {
				
				UserLockVO usrLockVO = userService.getLockedStatus(strUsername);

				Log.d(Constants.DEBUG, "in onResponseListenerAuth responce ::: "+responce);
				if(responce != null){
					String passwordUpdateDate = responce.getString(Constants.JSON_UPDATE_PASSWORD_DATE);
					if(passwordUpdateDate != null && !passwordUpdateDate.equals(Constants.BLANK)){
						ContentValues cv = new ContentValues();
						cv.put(Constants.USER_LOCK_FIELDS[4],passwordUpdateDate);
						String whr = "username = '"+strUsername+"'";
						userService.updateUserByID(Constants.TBL_MST_USER_LOCK,cv,whr);
						Utility.chkLoginAlert(strUsername, LoginActivity.this,LoginActivity.this);
						usrLockVO = userService.getLockedStatus(strUsername);
					}
					
					if(responce.has(Constants.RESPONSE_ERROR)){
						Utility.alertDialog(LoginActivity.this,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_JSON_FAILED);
					}else if(responce != null && responce.equals(Constants.JSON_FAILED)){
						Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.JSON_FAILED);
						Utility.alertDialog(LoginActivity.this,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_JSON_FAILED);
					}else if(responce.has(Constants.NETWORK_EXCEPTION_METHOD)){
						Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.NETWORK_EXCEPTION_METHOD);
						Utility.alertDialog(LoginActivity.this, Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
					}else if(responce.has(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD)){
						Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.CONNECTION_REFUSED_EXCEPTION_METHOD);
						Utility.alertDialog(LoginActivity.this,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
					}else if(responce.has(Constants.SOCKET_EXCEPTION)){
						Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.SOCKET_EXCEPTION);
						Utility.alertDialog(LoginActivity.this,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
					}else if(responce.has(Constants.IOEXCEPTION)){
						Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.IOEXCEPTION);
						Utility.alertDialog(LoginActivity.this,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
					}else if(responce.has(Constants.XMLPULLPARSEREXCEPTION)){
						Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.XMLPULLPARSEREXCEPTION);
						Utility.alertDialog(LoginActivity.this,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
					}else if(responce.has(Constants.EXCEPTION)){
						Log.d(Constants.DEBUG, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.EXCEPTION);
						Utility.alertDialog(LoginActivity.this,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
					}else{
						if(responce.getString(Constants.METHOD_AUTHENTICATE).equalsIgnoreCase(Constants.LABEL_TRUE)){


							//update LOCK if true to 0
							if(usrLockVO.getIsLock() != null)
							{
								if(usrLockVO.getIsLock().equals(Constants.LABLE_DEFAULT_LOCK))
								{
									ContentValues cvCont = new ContentValues();
									cvCont.put(Constants.USER_LOCK_FIELDS[5],Constants.LABLE_DEFAULT_UNLOCK);
									String where = "username = '"+strUsername+"'";

									Log.d(" USER UPDATE "," >>>>>>>>>>> "+userService.updateUserByID(Constants.TBL_MST_USER_LOCK,cvCont,where));

									if(userService.updateUserByID(Constants.TBL_MST_USER_LOCK,cvCont,where)!= -1){
										Log.d(Constants.DEBUG,"LOCK UPDATE added");
									}								
								}
							}

				/*			else if(usrLockVO.getIsLock().equals(Constants.LABLE_DEFAULT_LOCK))
							{
								Utility.alertDialog(LoginActivity.this,Constants.ALERT_TITLE_GENERAL_INFO,"User is Locked. Please contact Admin.");
								ContentValues cvCont = new ContentValues();
								cvCont.put(Constants.USER_LOCK_FIELDS[4],Constants.BLANK);
								String where = "username = '"+strUsername+"'";
								userService.updateUserByID(Constants.TBL_MST_USER_LOCK,cvCont,where);
							}*/

							//Check for Updates available or not
							Log.d(Constants.DEBUG,"IS update avilable:-"+responce.getString(Constants.JSON_IS_UPDATE_AVIAILABLE));
							if(responce.getString(Constants.JSON_IS_UPDATE_AVIAILABLE).equalsIgnoreCase(Constants.LABEL_TRUE))
							{
								Log.d(Constants.DEBUG,"REALTIVE PATH:-"+responce.getString(Constants.JSON_RELATIVE_PATH));
								ContentValues cvCont = new ContentValues();
								cvCont.put(Constants.DB_KEY,Constants.JSON_RELATIVE_PATH);
								cvCont.put(Constants.DB_VALUE,responce.getString(Constants.JSON_RELATIVE_PATH));
								cvCont.put(Constants.DB_CREATEDBY,Integer.parseInt(SharedPrefrenceUtil.getPrefrence(LoginActivity.this, Constants.DB_USER_ID, "0")));
								cvCont.put(Constants.DB_UPDATEDBY,Integer.parseInt(SharedPrefrenceUtil.getPrefrence(LoginActivity.this, Constants.DB_USER_ID, "0")));
								cvCont.put(Constants.DB_CREATEDON,Utility.getcurrentTimeStamp());
								cvCont.put(Constants.DB_UPDATEDON,Utility.getcurrentTimeStamp());

								if(userService.insertUser(Constants.TBL_MST_CUSTOME_PARAMETER,cvCont) != -1){
									Log.d(Constants.DEBUG,"path added");
								}	

								downloadApkDialog(LoginActivity.this,LoginActivity.this,responce.getString(Constants.JSON_RELATIVE_PATH));

							}else{
								if(Utility.isNetAvailable(LoginActivity.this)){
									Log.d(Constants.DEBUG, "in onResponseListenerAuth responce Authentication is true ***************** ::: ");		
									userService.deleteAllMaster();

									Map<String, Object> param = new HashMap<String, Object>();
									param.put(Constants.JSON_LOGIN_USERNAME, SecurityManager.encrypt(strUsername,Constants.SALT));
									param.put(Constants.JSON_LOGIN_PASSWORD, SecurityManager.encrypt(strPassword,Constants.SALT));
									param.put(Constants.JSON_LOGIN_DEVICEID, SecurityManager.encrypt(Constants.LABLE_DEVICE_ID,Constants.SALT));//Utility.getDeviceId(LoginActivity.this));

									SharedPrefrenceUtil.setPrefrence(LoginActivity.this, Constants.DB_USERNAME, strUsername);
									SharedPrefrenceUtil.setPrefrence(LoginActivity.this, Constants.DB_PASSWORD, strPassword);

									AsyncGetAllMasters asyncGetAllMasters= new AsyncGetAllMasters(LoginActivity.this,onResponseListener);
									asyncGetAllMasters.execute(param);									
								}
								else {
									Utility.alertDialog(LoginActivity.this, Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
								}
							}
						}else {
							clearFields();
							//update LOCK if true to 1
							//Utility.chkLoginAlert(strUsername, LoginActivity.this,LoginActivity.this);
							if(usrLockVO.getIsLock().equals(Constants.LABLE_DEFAULT_LOCK))
							{
								Utility.alertDialog(LoginActivity.this,Constants.ALERT_TITLE_GENERAL_INFO,"User is Locked. Please contact Admin.");
								ContentValues cvCont = new ContentValues();
								cvCont.put(Constants.USER_LOCK_FIELDS[4],Constants.BLANK);
								String where = "username = '"+strUsername+"'";
								userService.updateUserByID(Constants.TBL_MST_USER_LOCK,cvCont,where);
							}else{
								Toast.makeText(LoginActivity.this,getResources().getString(R.string.error_pwdusername),Toast.LENGTH_LONG).show();
							}
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				Log.d(Constants.DEBUG,"LoginActivity:onResponseListenerAuth:"+ Utility.convertExceptionToString(e));
				Utility.alertDialog(LoginActivity.this,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_JSON_FAILED);
			}
		}
		public void onFailure(String message) {
		}
	};


	/**
	 * Creates dialog for closing application warning!
	 * @param context Object of Context, context from where the activity is going to start.
	 * @param activity Object of activity , from where the function is called
	 */
	private void createOfflineDialog(final Context context, final Activity activity) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle(Constants.ALERT_TITLE_NETWORK_ERROR);
		alert.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					return true;
				}
				return false;
			}
		});
		alert.setMessage(Constants.ERROR_INTERNET_LOGIN_CONNECTION);
		alert.setPositiveButton(Constants.LABLE_YES, new DialogInterface.OnClickListener() {			
			public void onClick(DialogInterface dialog, int which) {
				try{

					//					if(SharedPrefrenceUtil.getPrefrence(LoginActivity.this,Constants.LABEL_PASSWORD_LOCK ,false)){
					//						Utility.alertDialog(LoginActivity.this, Constants.ALERT_TITLE_GENERAL, Constants.ERROR_PASSWORD_LOCKED);
					//					}else{
					String where = "username = '" + strUsername + "' and password = '"+ SecurityManager.encrypt(strPassword,Constants.SALT) + "'";
					Log.e("Where:-",""+where);
					UserMasterVO userVO = userService.getUserInfo(Constants.TBL_MST_USER, Constants.USER_MASTER_FIELDS,where);
					UserLockVO userLockVO = userService.getLockedStatus(userVO.getUserName());
					Log.e(Constants.TAG_LOGIN_ACTIVITY, "IN ELSE *********userVO :::: *****"+userVO);
					Log.e(Constants.TAG_LOGIN_ACTIVITY, "IN ELSE *********userVO.getUserID() :::: *****"+userVO.getUserID());

					if(userLockVO.getIsLock().equals(Constants.LABLE_DEFAULT_LOCK)){
						Utility.alertDialog(LoginActivity.this,Constants.ALERT_TITLE_GENERAL_INFO,"Password is Locked. Please contact Admin.");
					}else{
						if (userVO.getUserID() > 0) {
							SharedPrefrenceUtil.setPrefrence(context, Constants.DB_USER_ID,String.valueOf(userVO.getUserID()));
							Intent i = new Intent(LoginActivity.this,MainTabActivity.class);
							i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivityForResult(i, Constants.REQ_MENU_ACTIVITY);
						}else{
							dialog.dismiss();
							//Utility.alertDialog(context,Constants.ALERT_TITLE_GENERAL_ERROR, Constants.ERROR_NO_DATA_OFFLINE);
							Toast.makeText(getApplicationContext(),getResources().getString(R.string.error_pwdusername),Toast.LENGTH_LONG).show();
							clearFields();
						}
					}

					//					}
				}catch (Exception e) {
					e.printStackTrace();
					dialog.dismiss();
					Log.d(Constants.DEBUG,"LoginActivity:createOffLineDialog:"+ Utility.convertExceptionToString(e));
				}
				dialog.dismiss();
			}
		});
		alert.setNegativeButton(Constants.LABLE_NO, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//				activity.finish();
				dialog.dismiss();
			}
		});
		alert.show();
	}


	/**
	 * Performing closing of application when keyDown event on back button has occurred.
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			try {				
				finish();
			}catch (Exception e) {
				e.printStackTrace();
				Log.d(Constants.DEBUG,"LoginActivity:onKeyDown:"+ Utility.convertExceptionToString(e));
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
	/**
	 * This method is used to clear user name and password field
	 */
	public void clearFields(){
		edtUsername.setText(Constants.LABEL_BLANK);
		edtPassword.setText(Constants.LABEL_BLANK);
		edtUsername.requestFocus();
	}


	/**
	 * Creates dialog for downloading new apk file!
	 * @param context Object of Context, context from where the activity is going to start.
	 * @param activity Object of activity , from where the function is called
	 */
	private void downloadApkDialog(final Context context, final Activity activity,final String url) {
		try{
			AlertDialog.Builder alert = new AlertDialog.Builder(context);
			alert.setTitle(Constants.LABEL_DIALOG_CONFIRM);
			alert.setCancelable(false);
			alert.setOnKeyListener(new OnKeyListener() {
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK) {
						return true;
					}
					return false;
				}
			});
			alert.setMessage(Constants.LABEL_ALERT_DOWNLOAD_NEW_APK_2);
			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					try{
						//write code here
						FileDownloder fileDownload = new FileDownloder(LoginActivity.this,LoginActivity.this,userService,strUsername,strPassword);
						fileDownload.execute(new String []{ url });
						Toast.makeText(getApplicationContext(),"Downloading.....",Toast.LENGTH_SHORT).show();

					}catch (Exception e) {
						e.printStackTrace();
						dialog.dismiss();
						Log.d(Constants.DEBUG,"LoginActivity:downloadApkDialog:"+ Utility.convertExceptionToString(e));
					}
					dialog.dismiss();
				}
			});
			alert.setNegativeButton(Constants.LABLE_NO, new DialogInterface.OnClickListener() {
				@SuppressWarnings("unchecked")
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					try{
						if(Utility.isNetAvailable(LoginActivity.this)){
							Log.e(Constants.TAG_LOGIN_ACTIVITY, "in onResponseListenerAuth responce Authentication is true ***************** ::: ");		
							userService.deleteAllMaster();

							Map<String, Object> param = new HashMap<String, Object>();
							param.put(Constants.JSON_LOGIN_USERNAME, SecurityManager.encrypt(strUsername,Constants.SALT));
							param.put(Constants.JSON_LOGIN_PASSWORD, SecurityManager.encrypt(strPassword,Constants.SALT));
							param.put(Constants.JSON_LOGIN_DEVICEID, SecurityManager.encrypt(Constants.LABLE_DEVICE_ID,Constants.SALT));//Utility.getDeviceId(LoginActivity.this));

							AsyncGetAllMasters asyncGetAllMasters= new AsyncGetAllMasters(LoginActivity.this,onResponseListener);
							asyncGetAllMasters.execute(param);

							SharedPrefrenceUtil.setPrefrence(LoginActivity.this, Constants.DB_USERNAME, strUsername);
							SharedPrefrenceUtil.setPrefrence(LoginActivity.this, Constants.DB_PASSWORD, strPassword);
						}
						else {
							Utility.alertDialog(LoginActivity.this, Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
						}
					}catch (Exception e) {
						e.printStackTrace();
						Log.d(Constants.DEBUG,"LoginActivity:downloadApkDialog:"+ Utility.convertExceptionToString(e));
					}
				}
			});
			alert.show();
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,"LoginActivity:downloadApkDialog:"+ Utility.convertExceptionToString(e));
		}

	}
}