package com.spec.asms.service;


import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.Utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartupBroadcastReceiver extends BroadcastReceiver {

	public Context context;
	
	@Override
	public void onReceive(Context context, Intent intent) {

		if(intent.getAction().equals(Constants.ACTION_BOOT_COMPLETED))
    	{
				this.context = context;
				Log.i("IN Service", "********** In Action Boot_Completed");
				
//				if(!Utility.isAutoSubmitServiceRunning(context))
//				{
//					Intent i = new Intent(context,BatchSubmitService.class);
//					context.startService(i);
//				}
    	}
	}

}
