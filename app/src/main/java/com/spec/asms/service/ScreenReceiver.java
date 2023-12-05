package com.spec.asms.service;

import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Broadcast receiver class to tell when the device screen has being switched off and on.
 * @author shivani
 *
 */

public class ScreenReceiver extends BroadcastReceiver {	  

	public static boolean wasScreenOn = true ;  	     
	/**
	 * An overridden method to set a boolean variable true/false when the device screen turns on/off.
	 * @param context Object of Context, context from where the activity is going to start.
	 * @param intent Object of intent to invoke.
	 */
	@Override
	public void onReceive(Context context, Intent intent) {  
		try{
		if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {
			// do whatever you need to do here
			
			SharedPrefrenceUtil.setPrefrence(context, Constants.LABEL_LOGIN, false);
			SharedPrefrenceUtil.setPrefrence(context, Constants.LABEL_IS_CUST_AVAILABLE, false);
			if(Utility.isAdmin(context))
			{
				SharedPrefrenceUtil.setPrefrence(context,Constants.DB_USERNAME,"tech");
			}
			System.out.println(" -- OFF --- ");
			wasScreenOn = false;    	 

		} else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
			System.out.println(" -- ON --- ");
			wasScreenOn = true;              
		}else{
			wasScreenOn = false; 
		}
		}catch (Exception e) {
			e.getStackTrace();
			Log.d(Constants.DEBUG,":ScreenReceiver:"+ Utility.convertExceptionToString(e));
		}
	}
} 

