package com.spec.asms.view;

import android.os.Bundle;
import android.util.Log;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.CoreActivity;
import com.spec.asms.common.utils.ApplicationLog;
/**
 * A Class to display help to end users.
 *
 */
public class HelpActivity extends CoreActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.helpscreen);
		ApplicationLog.writeToFile(getApplicationContext(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::HelpActivityStarted:::");
	}
	
	

}
