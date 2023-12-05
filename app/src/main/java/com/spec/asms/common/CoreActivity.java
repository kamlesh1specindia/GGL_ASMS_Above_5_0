package com.spec.asms.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Core Activity to define core activities for all activities.
 * @author jenisha 
 * 
 */
public class CoreActivity extends Activity{


	protected Intent intent;
	protected SharedPreferences pref ;


	public void startActivity(Context packageContext, Class<?> clazz){
		Intent intent = new Intent(packageContext, clazz);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivityForResult(intent, 0);
	}

	public void back(){

		setResult(RESULT_OK, intent);
		finish();
	}

}
