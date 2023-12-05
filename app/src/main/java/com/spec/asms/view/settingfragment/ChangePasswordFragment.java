package com.spec.asms.view.settingfragment;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.SecurityManager;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.service.async.AsyncChangePwd;
import com.spec.asms.service.async.AsyncChangePwd.OnResponseListenerChangePwd;
import com.spec.asms.view.LoginActivity;

/**
 * A Class for Change Password form.
 * @author jenisha
 *
 */
public class ChangePasswordFragment extends Fragment {

	private View testing;	

	public static final String format="kk:mm:ss";	
	public static StringBuffer testingErrorMsg = new StringBuffer();

	public HashMap<String,Boolean> gasLeakageTest=new HashMap<String, Boolean>();

	private Button btnChangePwd;
	private EditText edtCurrentpassword;
	private EditText edtNewpassword;
	private EditText edtConfirmpassword;

	private int userID;

	private String currentPwd,newPwd,confirmPwd;

	private UserMasterService userService;


	/**
	 * A parameterized constructor 
	 * @return ChangePassword Fragment object
	 */
	public static ChangePasswordFragment newInstance() {
		ChangePasswordFragment chngPasswordFragment = new ChangePasswordFragment();
		return chngPasswordFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		testing = inflater.inflate(R.layout.changepasswordscreen,null);
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::ChangePasswordFragmentStarted:::");
		try{
			btnChangePwd = (Button)testing.findViewById(R.id.btnCngpwdChangePwd);		
			edtCurrentpassword = (EditText)testing.findViewById(R.id.edtCngpwdCurrentpwd);
			edtNewpassword = (EditText)testing.findViewById(R.id.edtCngpwdNewpwd);
			edtConfirmpassword = (EditText)testing.findViewById(R.id.edtCngpwdConfirmpwd);
			userService = new UserMasterService(getActivity());
			userID = Integer.parseInt(SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.DB_USER_ID,"0"));
			btnChangePwd.setOnClickListener(new OnClickListener() {
				@SuppressWarnings("unchecked")
				public void onClick(View arg0) {

					currentPwd = edtCurrentpassword.getText().toString().trim();
					newPwd = edtNewpassword.getText().toString().trim();
					confirmPwd = edtConfirmpassword.getText().toString().trim();
					if(userID > 0){
						if(!validate()){

							if(Utility.isNetAvailable(getActivity())){
								Map<String, Object> param = new HashMap<String, Object>();
								param.put(Constants.JSON_LOGIN_USERNAME, SecurityManager.encrypt(SharedPrefrenceUtil.getPrefrence(getActivity(), Constants.DB_USERNAME, Constants.LABEL_BLANK),Constants.SALT));
								param.put(Constants.JSON_LOGIN_PASSWORD, SecurityManager.encrypt(SharedPrefrenceUtil.getPrefrence(getActivity(), Constants.DB_PASSWORD, Constants.LABEL_BLANK),Constants.SALT));
								param.put(Constants.JSON_LOGIN_DEVICEID, SecurityManager.encrypt(Constants.LABLE_DEVICE_ID,Constants.SALT));//Utility.getDeviceId(getActivity()));
								param.put(Constants.JSON_NEW_PASSWORD,SecurityManager.encrypt(edtNewpassword.getText().toString(),Constants.SALT));

								AsyncChangePwd asyncChangePwd= new AsyncChangePwd(getActivity(), onResponseListenerPwd);
								asyncChangePwd.execute(param);

							}else {
								Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
							}
						}
					}
				}
			});
		}catch (Exception e) {
			e.printStackTrace();
		}
		return testing;
	}

	protected OnResponseListenerChangePwd onResponseListenerPwd = new OnResponseListenerChangePwd() {

		public void onSuccess(JSONObject responce) {
			try {
				Log.e(Constants.TAG_LOGIN_ACTIVITY, "in onResponseListenerAuth responce ::: "+responce);
				if(responce != null && responce.equals(Constants.JSON_FAILED)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.JSON_FAILED);
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_JSON_FAILED);
				}else if(responce.has(Constants.NETWORK_EXCEPTION_METHOD)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.NETWORK_EXCEPTION_METHOD);
					Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
				}else if(responce.has(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.CONNECTION_REFUSED_EXCEPTION_METHOD);
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.SOCKET_EXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.SOCKET_EXCEPTION);
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.IOEXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.IOEXCEPTION);
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.XMLPULLPARSEREXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.XMLPULLPARSEREXCEPTION);
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.EXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.EXCEPTION);
					Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.getString("Status").equalsIgnoreCase(Constants.LABEL_TRUE)){
					//					updatePassword();

					String where = "userID = "+userID;
					ContentValues cv = new ContentValues();
					cv.put(Constants.USER_MASTER_FIELDS[3],confirmPwd);//SecurityManager.encrypt(confirmPwd,Constants.SALT));
					cv.put(Constants.USER_MASTER_FIELDS[8],userID);
					cv.put(Constants.USER_MASTER_FIELDS[9],System.currentTimeMillis());// SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));

					if(userService.updateUserByID(Constants.TBL_MST_USER,cv, where)!= -1){
						Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL,Constants.INFO_SUCCESSFUL_PASSWORD_UPDATE);

						clearControls();
//						Utility.chkLoginAlert(userID,getActivity(),getActivity());
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				Log.d(Constants.DEBUG,"ChangePasswordFragment:"+ Utility.convertExceptionToString(e));
			}
		}
		public void onFailure(String message) {
		}
	};

	/**
	 * Method to update password in the database.
	 * 
	 */
	@SuppressWarnings("unused")
	private void updatePassword(){
		currentPwd = edtCurrentpassword.getText().toString().trim();
		newPwd = edtNewpassword.getText().toString().trim();
		confirmPwd = edtConfirmpassword.getText().toString().trim();
		try{
			if(userID > 0){
				if(!validate()){
					String where = "userID = "+userID;
					ContentValues cv = new ContentValues();
					cv.put(Constants.USER_MASTER_FIELDS[3],confirmPwd);//SecurityManager.encrypt(confirmPwd,Constants.SALT));
					cv.put(Constants.USER_MASTER_FIELDS[8],userID);
					cv.put(Constants.USER_MASTER_FIELDS[9],System.currentTimeMillis());// SecurityManager.encrypt(Utility.getcurrentTimeStamp(),Constants.SALT));

					if(userService.updateUserByID(Constants.TBL_MST_USER,cv, where)!= -1){
						Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL,Constants.INFO_SUCCESSFUL_PASSWORD_UPDATE);

						clearControls();
//						Utility.chkLoginAlert(userID,getActivity(),getActivity());
					}
				}
			}else{
			}
		}catch (Exception e) {
			Log.e(Constants.TAG_CHANGE_CREDENTIALS_ACTIVITY,e.getLocalizedMessage());
			Log.d(Constants.DEBUG,"ChangePasswordFragment:"+ Utility.convertExceptionToString(e));
		}
	}

	/**
	 * Method to clearControls.
	 */
	private void clearControls() {
		edtCurrentpassword.setText(Constants.LABEL_BLANK);
		edtNewpassword.setText(Constants.LABEL_BLANK);
		edtConfirmpassword.setText(Constants.LABEL_BLANK);
		currentPwd = Constants.LABEL_BLANK;
		newPwd = Constants.LABEL_BLANK;
		confirmPwd = Constants.LABEL_BLANK;
	}

	/**
	 * Method to validate password fields.
	 * @return boolean : true - No errors , false - Whern any error.
	 */
	private boolean validate() {

		boolean isError = false;

		if(Constants.LABEL_BLANK.equals(currentPwd))
		{
			isError = true;
			Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_GENERAL_ERROR, getResources().getString(R.string.error_entercurrentpwd));

		}else if(Constants.LABEL_BLANK.equals(newPwd)){
			isError = true;
			Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_GENERAL_ERROR,getResources().getString(R.string.error_enternewpwd));

		}else if(Constants.LABEL_BLANK.equals(confirmPwd)){
			isError = true;
			Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,getResources().getString(R.string.error_enterconfirmpwd));

		}else if (!newPwd.equals(confirmPwd)) {
			isError = true;
			Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,getResources().getString(R.string.error_enternewandconfirmsamepwd));

		}else if(!currentPwd.equals(userService.getCurrentpasswordbyuserID(userID))){
			isError = true;
			Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_GENERAL_ERROR,getResources().getString(R.string.error_currentpwd));

		}else if(confirmPwd.length() != 8){
			isError = true;
			Utility.alertDialog(getActivity(),Constants.ALERT_TITLE_GENERAL_ERROR,getResources().getString(R.string.error_pwdLength));

		}else {
			boolean upper = false;
			boolean lower = false;
			boolean number = false;
			for (char c : confirmPwd.toCharArray()) {
				if (Character.isUpperCase(c)) {
					upper = true;
				} else if (Character.isLowerCase(c)) {
					lower = true;
				} else if (Character.isDigit(c)) {
					number = true;
				}
			}
			if (!upper) {
				isError = true;
				Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_GENERAL_ERROR, getResources().getString(R.string.error_uprcasevalidation));
			} else if (!lower) {
				isError = true;
				Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_GENERAL_ERROR,getResources().getString(R.string.error_lwrrcasevalidation));
			} else if (!number) {
				isError = true;
				Utility.alertDialog(getActivity(), Constants.ALERT_TITLE_GENERAL_ERROR,getResources().getString(R.string.error_numvalidation));
			} 
		}
		return isError;
	}
}