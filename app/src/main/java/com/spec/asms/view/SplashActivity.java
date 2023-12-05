package com.spec.asms.view;

import android.Manifest;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.Crashlytics;
import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.CoreActivity;
import com.spec.asms.common.Global;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.PermissionHandler;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.BatchSubmitService;
import com.spec.asms.service.UserMasterService;
import io.fabric.sdk.android.Fabric;

public class SplashActivity extends CoreActivity {
	private PermissionHandler permissionHandler;
	public UserMasterService userService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Fabric.with(this, new Crashlytics());
		setContentView(R.layout.splashscreen);
		ApplicationLog.writeToFile(getApplicationContext(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::SplashActivityStarted:::");
		permissionHandler = new PermissionHandler(this);
		if(permissionHandler.isPermissionRequiredToAsk(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE))
			permissionHandler.askPermissions();
		else {
			userService = new UserMasterService(getApplicationContext());
			Global.typeface_Guj = Typeface.createFromAsset(this.getAssets(), Constants.TYPFACE_GUJRATI);
//		loginService = new LoginService();
			if (userService.getAdminURL().equals(Constants.LABEL_BLANK)) {
				//Constants.WS_URL = "http://10.37.55.133/asms/ggclasms.asmx?WSDL";
				Constants.WS_URL = Constants.LABEL_BLANK;
			} else {
				Constants.WS_URL = userService.getAdminURL();
			}
			Constants.LABLE_DEVICE_ID = Utility.getDeviceId(getApplicationContext());
			Log.e(Constants.TAG_SPLASH_ACTIVITY, "DateTime:-" + Utility.getcurrentTimeStamp() + " CONST_DEVICE _ID:--" + Constants.LABLE_DEVICE_ID);
			Handler handler = new Handler();
			handler.removeCallbacks(runnable);
			handler.postDelayed(runnable, Constants.SPLASH_SLEEP_TIME);
		}
//		Log.e(Constants.TAG_SPLASH_ACTIVITY,"DateTime:-"+Utility.getcurrentTimeStamp()+" Device Id --------->"+Utility.getDeviceId(this));
	}
public void doAction() {
	userService = new UserMasterService(getApplicationContext());
	Global.typeface_Guj = Typeface.createFromAsset(this.getAssets(), Constants.TYPFACE_GUJRATI);
//		loginService = new LoginService();
	if (userService.getAdminURL().equals(Constants.LABEL_BLANK)) {
		//Constants.WS_URL = "http://10.37.55.133/asms/ggclasms.asmx?WSDL";
		Constants.WS_URL = Constants.LABEL_BLANK;
	} else {
		Constants.WS_URL = userService.getAdminURL();
	}
	Constants.LABLE_DEVICE_ID = Utility.getDeviceId(getApplicationContext());
	Log.e(Constants.TAG_SPLASH_ACTIVITY, "DateTime:-" + Utility.getcurrentTimeStamp() + " CONST_DEVICE _ID:--" + Constants.LABLE_DEVICE_ID);
	Handler handler = new Handler();
	handler.removeCallbacks(runnable);
	handler.postDelayed(runnable, Constants.SPLASH_SLEEP_TIME);
}
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
										    @NonNull int[] grantResults) {
		permissionHandler.onRequestPermissionResult(requestCode, permissions, grantResults);
	}

	Runnable runnable = new Runnable()
	{
		public void run(){
			boolean isLogin = SharedPrefrenceUtil.getPrefrence(SplashActivity.this, Constants.LABEL_LOGIN, false);
			if(!isLogin){
				Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//|Intent.FLAG_ACTIVITY_NO_HISTORY);
		    	startActivity(intent);
		    	finish();
			}else{

				if(Utility.isAutoSubmitServiceRunning(SplashActivity.this)) 
				{
					Intent i = new Intent(SplashActivity.this,BatchSubmitService.class);
					Log.e("DateTime:-"+Utility.getcurrentTimeStamp()+"LoginActivity",">>>>>>>>>>>> Service stop <<<<<<<<<<< ");
					Log.e("DateTime:-"+Utility.getcurrentTimeStamp()+"LoginActivity",">>>>>>>>>>>> Service start<<<<<<<<<<< ");
					stopService(i);
					Intent i1 = new Intent(SplashActivity.this,BatchSubmitService.class);
					startService(i1);
					
				}else{ //if not running then start service
					Intent i = new Intent(SplashActivity.this,BatchSubmitService.class);
					startService(i);
				}

				Intent intent = new Intent(SplashActivity.this,MainTabActivity.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
		    	startActivity(intent);
		    	finish();
			}
		}
	};
}