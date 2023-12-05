package com.spec.asms.common.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings.Secure;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Global;
import com.spec.asms.common.Adapter.SideListAdapter;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.view.LoginActivity;
import com.spec.asms.view.fragment.MaintanenceForm;
import com.spec.asms.vo.TestingVO;
import com.spec.asms.vo.UserLockVO;
import com.spec.asms.vo.ValidationVO;

/**
 * Utility class to define utility for application.
 * @author jenisha 
 * 
 */
public class Utility 
{
	/**
	 * This Method is Use to get the Current Date and Time of Tablet in a "MM/dd/yyyy hh:mm:ssa"
	 * Format
	 * @return Returns String Containing Date and Time.
	 */
	
	public static String getcurrentTimeStamp(){
		SimpleDateFormat simpleFormatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		String strStartdt = simpleFormatter.format(System.currentTimeMillis());
		return strStartdt;
	}

	public static long getCurrentDateTime(){
		return System.currentTimeMillis();
	}
	
	

	
	/**
	 * This method checks if the internet connection is available or not.Returns True is network is connected else false
	 * @param context Object of Context, context from where the activity is going to start.
	 * @return boolean
	 */
	public static synchronized boolean isNetAvailable(Context context){
		//boolean isNetAvailable = false;
		if ( context != null ){
		/*	ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if ( mgr != null ){
				boolean mobileNetwork = false;
				boolean wifiNetwork = false;

				NetworkInfo mobileInfo = mgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				NetworkInfo wifiInfo = mgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

				if ( mobileInfo != null )
					mobileNetwork = mobileInfo.isAvailable();
				if ( wifiInfo != null )
					wifiNetwork = wifiInfo.isAvailable();

				isNetAvailable = ( mobileNetwork || wifiNetwork );
				System.out.println("In global network  " +isNetAvailable );
			}*/
			
			  ConnectivityManager connectivity = ( ConnectivityManager ) context
		                .getSystemService ( Context.CONNECTIVITY_SERVICE );
		        if ( connectivity != null ) {
		            NetworkInfo[] info = connectivity.getAllNetworkInfo ();
		            if ( info != null )
		                for ( int i = 0 ; i < info.length ; i++ )
		                    if ( info[ i ].getState () == NetworkInfo.State.CONNECTED ) {
		                        //DebugLog.d ( UTAG, "isNetworkAvailable: Yes" );
		                        return true;
		                    }
		        }
		        //DebugLog.d ( UTAG, "isNetworkAvailable: No" );
		        return false;
		}
		return false;
	}

	/**
	 * Returns updated list with changed sequence. If current index is not supported for the new sequence then it returns null. For 
	 * example if 0 index is being sent for Up sequence then it returns null.
	 * @param <T> Any class, list of this class is being sent as a method argument to change the sequence
	 * @param list Object of list in which sequence is required to change.
	 * @param sequence int value representing sequence type. 0 represents the down sequence and 1 represents the up sequence.
	 * @param currentIndex int value representing current index of the object in list. This method change the position of object at current
	 * index in list according to passed sequence value.
	 * @return List<T> Updated list with changed position of the object.
	 */
	public static <T> List<T> changeIndex(List<T> list, int sequence, int currentIndex){
		T tempObj = list.get(currentIndex);

		if(sequence ==Constants.WALK_SEQ_UP && currentIndex > 0){
			list.set(currentIndex, list.get(currentIndex-1));
			list.set(currentIndex-1, tempObj);
		}else if(sequence ==Constants.WALK_SEQ_DOWN && currentIndex < list.size()-1){
			list.set(currentIndex, list.get(currentIndex+1));
			list.set(currentIndex+1, tempObj);
		}else{
			return null;
		}
		return list;
	}

	/**
	 * This methods use to get current date
	 * Date Format : MM/dd/yyyy i.e.8/27/2012
	 * @return curDate : object of String 
	 */
	public static String getCurrentDate()
	{		
		String curDate=(String) DateFormat.format(Constants.DATE_FORMAT, new Date());
		return curDate;
	}

	/**
	 * This method use to get the current time
	 * @param context : object of Context
	 * @return currentTime : object of String
	 */
	public static  String getCurrentTime(Context context) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat timeFormat = new SimpleDateFormat(Constants.TIME_FORMAT);
		String currentTime=timeFormat.format(cal.getTime());

		if (currentTime.equals(null)) {
			alertDialog(context,"Time","Time");
		}
		return currentTime;
	}
	/**
	 * This method is used to convert string date to string time format
	 * @param context : Object of Context, context from where the activity is going to start.
	 * @param timeInString : Object of String,contatining date in string format
	 * @return time : Object of String
	 */
	public static String getConvertedTime(Context context,String timeInString){

		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		SimpleDateFormat timeFormat = new SimpleDateFormat(Constants.TIME_FORMAT);
		Date date;
		String time = null;
		try {
			date = formatter.parse(timeInString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			time = timeFormat.format(cal.getTime()); 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return time;
	}
	
	
	public static String getConvertedDate(Context context,String dateInString){

		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		SimpleDateFormat timeFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date date;
		String time = null;
		try {
			date = formatter.parse(dateInString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			time = timeFormat.format(cal.getTime()); 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return time;
	}
	
	/**
	 * This method is used to convert string date to string date format
	 * @param context : Object of Context, context from where the activity is going to start.
	 * @param dateInString : Object of String,contatining date in string format
	 * @return dateTime : Object of String
	 */
	public static String getConvertedDateTime(Context context,String dateInString){

		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date;
		String dateTime = null;
		try {
			date = formatter.parse(dateInString);
			dateTime = formatter.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return dateTime;
	}
	
	public static String getConvertedLongToDateTime(Context context,long date){

		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date1 ;
		String dateTime = null;
		try {
			date1 = new Date(date);
			dateTime = formatter.format(date1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return dateTime;
	}
	
	public static long getDateBefore15Days(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, - 15);
		return cal.getTimeInMillis();
	}

	
	/**
	 * Method to save date in long type format in shared preference
	 * @param context : Object of Context, context from where the activity is going to start.
	 * @param preference : Object of String,containing preference name with which preference would be stored
	 */
	public static void saveDateInSharedPref(Context context,String preference){
		long dateInLong = 0;
		Calendar curCal = Calendar.getInstance();
		Date date=new Date(curCal.getTimeInMillis());
		dateInLong=date.getTime();
		SharedPrefrenceUtil.setPrefrence(context,preference,dateInLong);
	}

	/**
	 * Method to save time in long type format in shared preference
	 * @param context : Object of Context, context from where the activity is going to start.
	 * @param preference : Object of String,containing preference name with which preference would be stored
	 */
	public static void saveTimeInSharedPref(Context context,String preference){
		long timeInLong = 0;
		Calendar cal=Calendar.getInstance();
		timeInLong=cal.getTimeInMillis();
		SharedPrefrenceUtil.setPrefrence(context,preference,timeInLong);
	}

	/**
	 * This method Provides alert Dialog 
	 * @param context : Object of Context, context from where the activity is going to start.
	 * @param title   : Tital of Dialog
	 * @param message : Message that we want to Display on Dialog.
	 */
	public static void alertDialog(final Context context,String title,String message){

		AlertDialog.Builder builder = new AlertDialog.Builder(context);  
		builder.setCancelable(true);		
		builder.setTitle(title);  
		builder.setMessage(message);
		builder.setPositiveButton(Constants.LABLE_OK,new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		AlertDialog alert = builder.create();  
		alert.show(); 
	}
	/**
	 * Creates dialog for closing application warning!
	 * @param context Object of Context, context from where the activity is going to start.
	 * @param activity Object of activity , from where the function is called
	 */
	public static void createCloseDialog(final Context context, final Activity activity) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle(Constants.LABEL_DIALOG_ALERT);
		alert.setOnKeyListener(new OnKeyListener() {

			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {

					return true;
				}
				return false;
			}
		});
		alert.setMessage(context.getResources()
				.getString(R.string.info_closing));
		alert.setPositiveButton(Constants.LABLE_YES, new OnClickListener() {			
			public void onClick(DialogInterface dialog, int which) {
				try{
					Global.isFilterRequired = false;
					activity.finish();
					Intent i = new Intent(activity,LoginActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					activity.startActivity(i);
					SharedPrefrenceUtil.setPrefrence(context, Constants.LABEL_LOGIN, false);
					SharedPrefrenceUtil.setPrefrence(context, Constants.LABEL_IS_CUST_AVAILABLE, false);
					SharedPrefrenceUtil.setPrefrence(context, Constants.DB_USER_ID,"0");
					if(Utility.isAdmin(context))
					{
						SharedPrefrenceUtil.setPrefrence(context,Constants.DB_USERNAME,"tech");
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				dialog.dismiss();
			}
		});
		alert.setNegativeButton(Constants.LABLE_NO, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});
		alert.show();
	}
	/**
	 * This method validates gas leakage detection test pass based on PressureDrop Value
	 * false : Means Leakage detection test has Failed.
	 * True  : Means Leakage detection test has Passed.
	 * @param pressureDrop  :  Object of String Contain pressureDrop value.
	 * @return isPressureDrop : object of boolean
	 */
	public static boolean isGesLeakageTestPassNew(String pressureDrop)
	{
		boolean isPressureDrop;
		double presureDrop = 0;
		if(!pressureDrop.equals(Constants.LABEL_BLANK)){
			presureDrop = Double.parseDouble(pressureDrop);
		}
			
		if(presureDrop>0)
		{
			isPressureDrop = false;  // it means Leakage detection test has Failed and should display NO.
		}
		else {
			isPressureDrop = true;   // it means Leakage detection test has Passed and should display YES.
		}
		return isPressureDrop;
	}

	/**
	 * This method validates gas leakage detection test pass
	 * @param initialPressure : object of String containing initial pressure
	 * @param finalPressure : object of String containing final pressure
	 * @return isPressure : object of boolean
	 */
	public static boolean isGasLeakageTestPass(String initialPressure,String finalPressure)
	{
		boolean isPressure;
		double iniPressure=0;
		double finPressure=0;
		try
		{

			iniPressure=Double.parseDouble(initialPressure);
			finPressure=Double.parseDouble(finalPressure);


		}
		catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		if((iniPressure>0)&&(finPressure<iniPressure))
		{
			isPressure=true;
		}
		else
		{
			isPressure=false;
		}
		return isPressure;
	}

	/**
	 * This method use to check type of pressure
	 * @param pressureValue : object of String containing pressure value 
	 * @return pressureType : object of String containing type of pressure
	 */
	public static void  checkPressureType(Context context,String pressureValue){
		UserMasterService userService = new UserMasterService(context);
		ValidationVO validationVO = null;
		//double presVal = Double.parseDouble(pressureValue);
		int presVal = Integer.parseInt(pressureValue);

		validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_TESTING,Constants.LABEL_VALIDATE_TESTING_PRESSURETYPE);
		if(validationVO != null){			 
			if(presVal >= Integer.parseInt(validationVO.getMaxValue())){
				TestingVO.setPressureType(Constants.TESTING_PRESSURETYPE_HIGH); 
			}
			if((presVal >= Integer.parseInt(validationVO.getMinValue()))&&(presVal < Integer.parseInt(validationVO.getMaxValue()))){
				TestingVO.setPressureType(Constants.TESTING_PRESSURETYPE_NORMAL);
			}
			if(presVal < Integer.parseInt(validationVO.getMinValue())){
				TestingVO.setPressureType(Constants.TESTING_PRESSURETYPE_LOW);
			}
		}
	}

	/**
	 * This method use to calculate pressure drop value i.e pressureDropValue = Initial Pressure - Final Pressure
	 * @param initialPressure : object of String containing initial pressure value
	 * @param finalPressure : object of String containing final pressure value
	 */
	public static void checkPressureDrop(String initialPressure,String finalPressure)
	{
		try
		{
			double iniPressure = Double.parseDouble(initialPressure);
			double finPressure =Double.parseDouble(finalPressure);
			double pressureDropCalculation=iniPressure-finPressure;
			DecimalFormat df = new DecimalFormat("###.##");
			String pressureDrop =df.format(pressureDropCalculation);
			TestingVO.setPressureDrop(pressureDrop);

		}
		catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
	}

	/**
	 * This Method Display Current Time
	 * @param context : Object of Context, context from where the activity is going to start.
	 */

	public static void TimeValidation(Context context) {
		Calendar c = Calendar.getInstance();
		String time = c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE);
		if (time.equals(null)) {
			alertDialog(context, Constants.ALERT_TITLE_GENERAL_ERROR,
					Constants.ERROR_STRING);
		}

	}

	/**
	 * This boolean method return true and false.
	 * To make ORing and Painting sub fields enable and Disable
	 * @param value    : To validate fields value we use Dialog id 1 for True
	 * 				     ,and 2 for false
	 * @return Returns : True and False based on condition checks
	 */
	public static boolean paintNringDisableView(int value)
	{
		if (value == 1)						
			return true;
		else 					
			return false;
	}

	/**
	 * This method is used to validates fields of Painting Fragment.
	 * This method Validate Blank Fields as well as Values Enter in the Fields. 
	 * @param context : Object of Context, context from where the activity is going to start.
	 * @param id      : id is Used to differentiate each Field.
	 * @param text    : text contain value of fields.
	 * @return        : Returns Error Message for wrong validation and Blank Fields.
	 */
	public static String paintinAndOring(Context context, int id, String text) {

		UserMasterService userService = new UserMasterService(context);
		ValidationVO validationVO = null;

		String errorMsg = Constants.LABEL_BLANK;
		//		if(text.equals(Constants.LABEL_BLANK))
		//		{		
		//
		//			errorMsg = Constants.LABEL_BLANK;			
		//		}
		//		else
		//		{
		//			int value = Integer.parseInt(text);
		switch (id) 
		{
		case Constants.PAINT:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_PAINTING,Constants.LABEL_VALIDATE_PAINTING_PAINT);
			if(validationVO != null)
			{
				//					if (value > Integer.parseInt(validationVO.getMaxValue()))
				//					{

				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
				//					}
				//					else
				//					{
				//
				//						errorMsg = Constants.LABEL_BLANK;
				//					}
				//				}
				//				else
				//				{
				//
				//					errorMsg = Constants.LABEL_BLANK;
			}
			break;		
		case Constants.ORING_METER:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_PAINTING,Constants.LABEL_VALIDATE_PAINTING_ORING_METER);
			if(validationVO != null)
				//				{
				//					if (value > Integer.parseInt(validationVO.getMaxValue()))
				//					{ 

				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
			//					}
			//					else
			//					{
			//
			//						errorMsg = Constants.LABEL_BLANK;
			//					}
			//				}
			//				else
			//				{
			//
			//					errorMsg = Constants.LABEL_BLANK;
			//				}
			break;
		case Constants.ORING_DOM:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_PAINTING,Constants.LABEL_VALIDATE_PAINTING_ORING_DOM);
			if(validationVO != null)
			{
				//					if (value > Integer.parseInt(validationVO.getMaxValue()))
				//					{ 

				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
				//					}
				//					else
				//					{
				//
				//						errorMsg = Constants.LABEL_BLANK;
				//					}
				//				}
				//				else
				//				{
				//
				//					errorMsg = Constants.LABEL_BLANK;
			}
			break;
		case Constants.ORING_AUDCO:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_PAINTING,Constants.LABEL_VALIDATE_PAINTING_ORING_AUDCO);
			if(validationVO != null)
			{
				//					if (value > Integer.parseInt(validationVO.getMaxValue()))
				//					{

				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
				//					}
				//					else
				//					{
				//
				//						errorMsg = Constants.LABEL_BLANK;
				//					}
				//				}
				//				else
				//				{
				//
				//					errorMsg = Constants.LABEL_BLANK;
			}
			break;
		default:
			break;
		}
		//		}
		return errorMsg;
	}

	/**
	 * This method is used to validates fields of Clamping Fragment.
	 * This method Validate Blank Fields as well as Values Enter in the Fields. 
	 * @param context : Object of Context, context from where the activity is going to start.
	 * @param id      : id is Used to differentiate each Field.
	 * @param text    : text contain value of fields.
	 * @return        : Returns Error Message for wrong validation and Blank Fields.
	 */
	public static String Clamping(Context context, int id, String text) 
	{
		UserMasterService userService = new UserMasterService(context);
		ValidationVO validationVO = null;


		String errorMsg = Constants.LABEL_BLANK;
		//		if(text.equals(Constants.LABEL_BLANK))
		//		{
		//			changeList(1,false);
		//			return errorMsg;
		//		}
		//		else
		//		{
		//			int value = Integer.parseInt(text);
		switch (id) 
		{
		case Constants.CLAMP_HALF:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_CLAMPING,Constants.LABEL_VALIDATE_CLAMPING_CLAMP_HALF);
			if(validationVO != null)
			{

				//					if (value > 9)// Integer.parseInt(validationVO.getMaxValue())) 
				//					{
				errorMsg =  Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;//validationVO.getMaxValue();
				//					}
				//					else
				//					{
				//
				//
				//						errorMsg = Constants.LABEL_BLANK;
				//					}
			}
			else
			{
				errorMsg =  Constants.LABEL_BLANK;
			}
			break;
		case Constants.CHEESE_HEAD_SCREW:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_CLAMPING,Constants.LABEL_VALIDATE_CLAMPING_CHEESE_HEAD_SCREW);
			if(validationVO != null)
			{
				//					if (value > Integer.parseInt(validationVO.getMaxValue())) 
				//					{

				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
				//					}
				//					else
				//					{
				//
				//						errorMsg = Constants.LABEL_BLANK;
				//					}
				//				}
				//				else
				//				{
				//
				//					errorMsg = Constants.LABEL_BLANK;
			}
			break;
		case Constants.CLAMP_ONE:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_CLAMPING,Constants.LABEL_VALIDATE_CLAMPING_CLAMP_ONE);
			if(validationVO != null)
			{
				//					if (value > 20) // Integer.parseInt(validationVO.getMaxValue())) 
				//					{
				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;//validationVO.getMaxValue();
				//					}
				//					else
				//					{
				//
				//						errorMsg = Constants.LABEL_BLANK;
				//					}
			}
			else
			{

				errorMsg = Constants.LABEL_BLANK;
			}
			break;
		case Constants.WOOD_SCREW:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_CLAMPING,Constants.LABEL_VALIDATE_CLAMPING_WOOD_SCREW);
			if(validationVO != null)
			{
				//					if (value > Integer.parseInt(validationVO.getMaxValue()))
				//					{

				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
				//					}
				//					else
				//					{
				//
				//						errorMsg = Constants.LABEL_BLANK;
				//					}
			}
			else
			{

				errorMsg = Constants.LABEL_BLANK;
			}
			break;
		case Constants.ROUL_PLUG:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_CLAMPING,Constants.LABEL_VALIDATE_CLAMPING_ROUL_PLUG);
			if(validationVO != null)
			{
				//					if (value > Integer.parseInt(validationVO.getMaxValue()))
				//					{ 

				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
				//					}
				//					else
				//					{
				//
				//						errorMsg = Constants.LABEL_BLANK;
				//					}
				//				}
				//				else
				//				{
				//
				//					errorMsg = Constants.LABEL_BLANK;
			}
			break;
		case Constants.PIPELINE_PROTECITON_CLAMPS:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_CLAMPING,Constants.LABEL_VALIDATE_CLAMPING_SUPPLY_OF_PIPELINE_PROTECTION_CLAMP);
			if(validationVO != null)
			{
				//					if (value > Integer.parseInt(validationVO.getMaxValue()))
				//					{ 

				errorMsg = Constants.WARNING_MESSAGE_2+validationVO.getMaxValue()+Constants.LABEL_DOT;
				//					}
				//					else
				//					{
				//
				//						errorMsg = Constants.LABEL_BLANK;
				//					}
				//				}
				//				else
				//				{
				//
				//					errorMsg = Constants.LABEL_BLANK;
			}
			break;
		default:
			break;
		}	

		return errorMsg;
		//		}
	}

	public static void changeList(int index,boolean value)
	{
		MaintanenceForm.validateList.remove(index);
		MaintanenceForm.validateList.add(index,value);
	}

	/**
	 * This Method is Used to calculate Pressure Drop in Painting Fragment
	 * i.e: PressureDrop = InitialPressure-FinalPressure
	 * @param InitialPressure : Value of Initial Pressure
	 * @param FinalPressure   : Value of Final Pressure
	 * @return Returns        : Value of PressureDrop
	 */
	public static int pressureDropValidation(int InitialPressure,
			int FinalPressure) {
		int pressureDrop;
		pressureDrop = InitialPressure - FinalPressure;
		return pressureDrop;
	}

	/**
	 * This Method Test the GasLeckageDetection and if Condition Satisfies 
	 * than Return true else Return False.
	 * @param InitialPressure : Value of Initial Pressure
	 * @param FinalPressure   : Value of Final Pressure
	 * @return Return         : Boolean Value either True or False
	 */
	public static boolean gasLeakageDetectionTest(int InitialPressure,
			int FinalPressure) {
		if (InitialPressure > 0 && FinalPressure < InitialPressure) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method is used to validates fields of GiFittings Fragment.
	 * This method Validate Blank Fields as well as Values Enter in the Fields. 
	 * @param context : Object of Context, context from where the activity is going to start.
	 * @param id      : id is Used to differentiate each Field.
	 * @param text    : text contain value of fields.
	 * @return        : Returns Error Message for wrong validation and Blank Fields.
	 */
	public static String giFittings(Context context, String text, int id) {

		UserMasterService userService = new UserMasterService(context);
		ValidationVO validationVO = null;

		String errorMsg = Constants.LABEL_BLANK;
		if(text.equals(Constants.LABEL_BLANK))
		{	
			errorMsg = Constants.LABEL_BLANK;
		}
		else
		{
			int value = Integer.parseInt(text);			

			switch (id) {
			case Constants.ELBOW:
				validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_GIFITTINGS,Constants.LABEL_VALIDATE_GIFITTINGS_ELBOW);
				if(validationVO != null)
				{
					//					if (value > Integer.parseInt(validationVO.getMaxValue())) 
					//					{

					errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
					//					}else
					//					{
					//
					//						errorMsg = Constants.LABEL_BLANK;
					//					}
				}
				else
				{

					errorMsg = Constants.LABEL_BLANK;
				}
				break;
			case Constants.TEE:
				validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_GIFITTINGS,Constants.LABEL_VALIDATE_GIFITTINGS_TEE);
				if(validationVO != null)
				{
					//					if (value > Integer.parseInt(validationVO.getMaxValue())) 
					//					{

					errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
					//					}else
					//					{
					//
					//						errorMsg = Constants.LABEL_BLANK;
					//					}
				}
				else
				{

					errorMsg = Constants.LABEL_BLANK;
				}
				break;
			case Constants.HEXNIPPLE:
				validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_GIFITTINGS,Constants.LABEL_VALIDATE_GIFITTINGS_HEXNIPPLE);
				if(validationVO != null)
				{
					//					if (value > Integer.parseInt(validationVO.getMaxValue())) 
					//					{

					errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
					//					}else
					//					{
					//
					//						errorMsg = Constants.LABEL_BLANK;
					//					}
				}
				else
				{

					errorMsg = Constants.LABEL_BLANK;
				}
				break;
			case Constants.UNION:
				validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_GIFITTINGS,Constants.LABEL_VALIDATE_GIFITTINGS_UNION);
				if(validationVO != null)
				{
					//					if (value > Integer.parseInt(validationVO.getMaxValue())) 
					//					{

					errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
					//					}else
					//					{
					//
					//						errorMsg = Constants.LABEL_BLANK;
					//					}
				}
				else
				{

					errorMsg = Constants.LABEL_BLANK;
				}
				break;
			case Constants.PLUG:
				validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_GIFITTINGS,Constants.LABEL_VALIDATE_GIFITTINGS_PLUG);
				if(validationVO != null)
				{
					//					if (value > Integer.parseInt(validationVO.getMaxValue())) 
					//					{

					errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
					//					}else
					//					{
					//
					//						errorMsg = Constants.LABEL_BLANK;
					//					}
				}
				else
				{

					errorMsg = Constants.LABEL_BLANK;
				}
				break;
			case Constants.GICAP:
				validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_GIFITTINGS,Constants.LABEL_VALIDATE_GIFITTINGS_CAP);
				if(validationVO != null)
				{
					//					if (value > Integer.parseInt(validationVO.getMaxValue())) 
					//					{

					errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
					//					}else
					//					{
					//
					//						errorMsg = Constants.LABEL_BLANK;
					//					}
				}
				else
				{

					errorMsg = Constants.LABEL_BLANK;
				}
				break;
			case Constants.GICOUPLING:
				validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_GIFITTINGS,Constants.LABEL_VALIDATE_GIFITTINGS_COUPLING);
				if(validationVO != null)
				{
					//					if (value > Integer.parseInt(validationVO.getMaxValue())) 
					//					{

					errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
					//					}else
					//					{
					//
					//						errorMsg = Constants.LABEL_BLANK;
					//					}
				}
				else
				{

					errorMsg = Constants.LABEL_BLANK;
				}
				break;
			default:
				break;
			}
		}
		return errorMsg;
	}

	/**
	 * This method is used to validates fields of R.C.C. Fragment.
	 * This method Validate Blank Fields as well as Values Enter in the Fields. 
	 * @param context : Object of Context, context from where the activity is going to start.
	 * @param id      : id is Used to differentiate each Field.
	 * @param text    : text contain value of fields.
	 * @return        : Returns Error Message for wrong validation and Blank Fields.
	 */
	public static String rcc(int id, String text, Context context) 
	{
		UserMasterService userService = new UserMasterService(context);
		ValidationVO validationVO = null;

		String errorMsg = Constants.LABEL_BLANK;
		//		if(text.equals(Constants.LABEL_BLANK))
		//		{		
		//
		//			return errorMsg;
		//		}
		//		else
		//		{
		//			int value = Integer.parseInt(text);

		switch (id) {
		case Constants.MS_NAIL:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_RCC,Constants.LABEL_VALIDATE_RCC_MS_NAIL);
			if(validationVO != null)
			{
				//					if (value > Integer.parseInt(validationVO.getMaxValue())) 
				//					{

				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
				//					}
				//					else
				//					{
				//
				//						errorMsg = Constants.LABEL_BLANK;
				//					}
				//				}else
				//				{
				//
				//					errorMsg = Constants.LABEL_BLANK;
			}
			break;
		case Constants.MS_NUT:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_RCC,Constants.LABEL_VALIDATE_RCC_MS_NUT);
			if(validationVO != null)
			{
				//					if (value > Integer.parseInt(validationVO.getMaxValue())) 
				//					{

				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue();
				//					}
				//					else
				//					{
				//
				//						errorMsg = Constants.LABEL_BLANK;
				//					}
				//				}else
				//				{
				//
				//					errorMsg = Constants.LABEL_BLANK;
			}
			break;
		case Constants.RCC_GUARD:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_RCC,Constants.LABEL_VALIDATE_RCC_GUARD);
			if(validationVO != null)
			{
				//					if (value > Integer.parseInt(validationVO.getMaxValue())) 
				//					{

				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
				//					}
				//					else
				//					{
				//
				//						errorMsg = Constants.LABEL_BLANK;
				//					}
				//				}else
				//				{
				//
				//					errorMsg = Constants.LABEL_BLANK;
			}
			break;

		default:
			break;
			//			}
		}
		return errorMsg;
	}
	
	
	
	/**
	 * This method is used to validates fields of R.C.C. Fragment.
	 * This method Validate Blank Fields as well as Values Enter in the Fields. 
	 * @param context : Object of Context, context from where the activity is going to start.
	 * @param id      : id is Used to differentiate each Field.
	 * @param text    : text contain value of fields.
	 * @return        : Returns Error Message for wrong validation and Blank Fields.
	 */
	public static String otherchecks(int id, String text, Context context) 
	{
		UserMasterService userService = new UserMasterService(context);
		ValidationVO validationVO = null;

		String errorMsg = Constants.LABEL_BLANK;

		switch (id) {
		case Constants.SUPPLY_BUSH:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_OTHER_CHECKS,Constants.LABEL_VALIDATE_OTHER_SUPPLY_BUSH);
			if(validationVO != null)
			{
				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
				
			}
			break;
		case Constants.RUBBER_CAP:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_OTHER_CHECKS,Constants.LABEL_VALIDATE_OTHER_RUBBER_CAP);
			if(validationVO != null)
			{
				
				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
				
			}
			break;
		case Constants.BRASS_BALL_VALVE:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_OTHER_CHECKS,Constants.LABEL_VALIDATE_OTHER_BRASS_BALL_VALVE);
			if(validationVO != null)
			{
				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
			}
			break;
			
		case Constants.BRASS_BALL_COCK:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_OTHER_CHECKS,Constants.LABEL_VALIDATE_OTHER_BRASS_BALL_COCK);
			if(validationVO != null)
			{
				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
			}
			break;
			
		case Constants.ISOLATION_BALL_VALE:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_OTHER_CHECKS,Constants.LABEL_VALIDATE_OTHER_ISOLATION_BALL_VALVE);
			if(validationVO != null)
			{
				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
			}
			break;

		default:
			break;
			//			}
		}
		return errorMsg;
	}


	/**
	 * This method is used to validates fields of SurakshaTube Fragment.
	 * This method Validate Blank Fields as well as Values Enter in the Fields. 
	 * @param context : Object of Context, context from where the activity is going to start.
	 * @param id      : id is Used to differentiate each Field.
	 * @param text    : text contain value of fields.
	 * @return        : Returns Error Message for wrong validation and Blank Fields.
	 */
	public static String surakshaTube(int id,String text,Context context)
	{
		UserMasterService userService = new UserMasterService(context);
		ValidationVO validationVO = null;

		String errorMsg = Constants.LABEL_BLANK;
		switch (id) 
		{
		case Constants.SIZE1:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_SURAKSHA,Constants.LABEL_VALIDATE_SURAKSHA_SIZE1);
			if(validationVO != null){
				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
			}
			break;
		case Constants.SIZE2:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_SURAKSHA,Constants.LABEL_VALIDATE_SURAKSHA_SIZE2);
			if(validationVO != null){
			errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
			}
			break;
		case Constants.SIZE3:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_SURAKSHA,Constants.LABEL_VALIDATE_SURAKSHA_SIZE3);
			if(validationVO != null){
				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
			}
			break;
		case Constants.SIZE4:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_SURAKSHA,Constants.LABEL_VALIDATE_SURAKSHA_SIZE4);
			if(validationVO != null){
				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
			}
			break;
		case Constants.SIZE5:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_SURAKSHA,Constants.LABEL_VALIDATE_SURAKSHA_SIZE5);
			if(validationVO != null){
				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
			}
			break;
		case Constants.SIZE6:
			validationVO = userService.getValidationMinMax(Constants.LABEL_VALIDATE_SURAKSHA,Constants.LABEL_VALIDATE_SURAKSHA_SIZE1);
			if(validationVO != null){
				errorMsg = Constants.WARNING_MESSAGE+validationVO.getMaxValue()+Constants.LABEL_DOT;
			}
			break;

		default:
			break;
		}
		return errorMsg;
	}

	/**
	 * This method is use to check which form is selected and respectively highlight list.
	 * @param index1 index of list item to highlight
	 * @param index2 index of list item not to highlight
	 */
	public static void changeSelectedForm(int index1 , int index2)
	{
		SideListAdapter.itemSelected.remove(index1);
		SideListAdapter.itemSelected.add(index1,true);
		SideListAdapter.itemSelected.remove(index2);
		SideListAdapter.itemSelected.add(index2,false);
		MaintanenceForm.sideListAdapter.notifyDataSetInvalidated();
	}

	/**
	 * Returns unique id of current device in which the application is running.
	 * @param context - Object of Context for which device id is required.
	 * @return String
	 */
	public static String getDeviceId(Context context){
		return Secure.getString(context.getContentResolver(),Secure.ANDROID_ID);  
	}

	/**
	 * Returns name of current device's manufacturer name on which the application is running.
	 * @param context - Object of Context for which device id is required.
	 * @return String
	 */
	public static String getDeviceManufectureName(){
		return android.os.Build.MANUFACTURER;  
	}

	/**
	 * Returns name of current device's manufacturer name on which the application is running.
	 * @param context - Object of Context for which device id is required.
	 * @return String
	 */
	public static String getDeviceModelNumber(){
		return android.os.Build.MODEL;  
	}

	/**
	 * This method is used to check the time difference between device and server time.A difference of +- 5 minutes is acceptable.
	 * @param serverTime server time in string format
	 * @return boolean 
	 */
	public static boolean checkTimeDifference(String serverTime){
		boolean isIn5minits = false;
		String temp = serverTime; //"1346155748931";//5.39 Timestamp
		long serverTimeStamp=Long.parseLong(temp);//whatever your server timestamp is, however you are getting it.
		//You may have to use Long.parseLong(serverTimestampString) to convert it from a string
		long l1 = serverTimeStamp-System.currentTimeMillis();
		long l2 = System.currentTimeMillis()-serverTimeStamp;

		//3000(millliseconds in a second)*60(seconds in a minute)*5(number of minutes)=300000
		if ((Math.abs(l1)>=-300000) && (Math.abs(l2)<=300000)){ 
			//server timestamp is within 5 minutes of current system time
			isIn5minits =true;
		} else {
			//server is not within 5 minutes of current system time
			isIn5minits = false;
		}
		return isIn5minits;
	}

	/**
	 * This method is used to give alert to user to change password after 30 days , also gives alerts of password lock after 45 days
	 * @param userid the id of the user logged in.
	 * @param context Object of Context, context from where the activity is going to start.
	 * @param activity Object of Activity calling this function.
	 * @return boolean
	 */
	public static boolean chkLoginAlert(String userName,Context context, Activity activity)
	{
		boolean isAlert = false;
		SimpleDateFormat simpleFormatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		UserMasterService userService = new UserMasterService(context);
		//		String where = "userid = "+userid;
		try
		{
			//			UserMasterVO userVO = userService.getUserInfo(Constants.TBL_MST_USER,Constants.USER_MASTER_FIELDS,where);
			System.out.println(" ********* NAME *********** " +userName);
			UserLockVO userLockVO = userService.getLockedStatus(userName);


			//CreatedDATE//
			Calendar calendarLastLogin = Calendar.getInstance();
			calendarLastLogin.setTimeInMillis(System.currentTimeMillis());
			Date dtLastLogin = calendarLastLogin.getTime();
			Log.i("Utility","***********LastLogin "+simpleFormatter.format(dtLastLogin));

			if(!userLockVO.getPasswordUpdateDate().equals(Constants.LABEL_BLANK))
			{
				long lng = Long.parseLong(userLockVO.getPasswordUpdateDate());

				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(lng);

				//Date dt = simpleFormatter.parse(userVO.getCreatedOn());
				String strCreateddt = simpleFormatter.format(calendar.getTime());
				Log.i("Utility","***********PASSWORD UPDATED ON "+strCreateddt);
				Date dtCreated = simpleFormatter.parse(strCreateddt);

				Calendar calCreated = Calendar.getInstance();
				calCreated.setTime(dtCreated);

				calCreated.add(Calendar.DATE,45);
				Date dateAfterFortyFive = calCreated.getTime();
				Log.i("Utility","***********DateAfterFortyFive "+simpleFormatter.format(dateAfterFortyFive));

				calCreated.add(Calendar.DATE,-15);
				Date dateAfterThirty = calCreated.getTime();
				Log.i("Utility","***********DateAfterThirty "+simpleFormatter.format(dateAfterThirty));

				if(dtLastLogin.after(dateAfterFortyFive))
				{
					isAlert = false;
					System.out.println(" IN SIDE 45 DAYS ^^^^^^^^^^ ");
					//				isAlert = true;
					//				SharedPrefrenceUtil.setPrefrence(context, Constants.LABEL_LOGIN, false);
					////				SharedPrefrenceUtil.setPrefrence(context,Constants.LABEL_PASSWORD_LOCK ,true);
					//				
					//				SharedPrefrenceUtil.setPrefrence(context,Constants.DB_USERNAME,"tech");
					//				
					ContentValues cv = new ContentValues();
					cv.put(Constants.USER_LOCK_FIELDS[5],Constants.LABLE_DEFAULT_LOCK);
					String whr = "username = '"+userName+"'";

					Log.e(" USER UPDATE ","DateTime:-"+Utility.getcurrentTimeStamp()+" >>>>>>>>>>> "+userService.updateUserByID(Constants.TBL_MST_USER_LOCK,cv,whr));

					if(userService.updateUserByID(Constants.TBL_MST_USER_LOCK,cv,whr)!= -1){
						Log.e(Constants.TAG_LOGIN_ACTIVITY,"DateTime:-"+Utility.getcurrentTimeStamp()+"LOCK UPDATE added");
					}	

					//				long id = userService.updateUserByID(Constants.TBL_MST_USER,cv, whr);
					//				
					//				Log.i("Custor Id : Database Entry","###########"+userid);
					//				Log.i("Custor Id : Database Entry","###########"+Constants.USER_MASTER_FIELDS[10]);
					//				Log.i("Date After 45 days : Database Entry","###########"+id);				
				}else if(dtLastLogin.after(dateAfterThirty) && dtLastLogin.before(dateAfterFortyFive)){

					//					long diff = dtLastLogin.getTime() - dateAfterFortyFive.getTime();
					//		            float dayCount = (float) diff / (24 * 60 * 60 * 1000);
					//		            
					//		            System.out.println(" ******** DAYS LEFT ********** "+dayCount);		            
					isAlert = true;

				}else if(dtLastLogin.before(dateAfterThirty)){
					isAlert = false;
				}
			}else isAlert = false;
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return isAlert;
	}

	
	/**
	 * This Method is Use to get remainng days from the logindate and pwd update date
	 * String
	 * @return Returns String of days
	 */
	
	public static String  remaingDaysForLockPwd(String userName,Context context, Activity activity)
	{
		long days = 0;
		try {
		SimpleDateFormat simpleFormatter = new SimpleDateFormat(Constants.DATE_FORMAT);
		UserMasterService userService = new UserMasterService(context);
		Date dtLastLogin,pwdUpdateDt = null;
		System.out.println(" ********* NAME *********** " +userName);
		UserLockVO userLockVO = userService.getLockedStatus(userName);

		//CreatedDATE//
		Calendar calendarLastLogin = Calendar.getInstance();
		calendarLastLogin.setTimeInMillis(System.currentTimeMillis());
		dtLastLogin = calendarLastLogin.getTime();
		
		 Calendar calendarPwdUpdate = Calendar.getInstance();
		if(!userLockVO.getPasswordUpdateDate().equals(Constants.LABEL_BLANK))
		{
			long lng = Long.parseLong(userLockVO.getPasswordUpdateDate());

			
			calendarPwdUpdate.setTimeInMillis(lng);
			//pwdUpdateDt = calendarPwdUpdate.getTime();
			pwdUpdateDt = new Date(lng);
			calendarPwdUpdate.add(Calendar.DATE,45);
			pwdUpdateDt.setDate(pwdUpdateDt.getDate() + 45);
			Log.i("Utility","***********DateAfterFortyFive in Days Count "+simpleFormatter.format(pwdUpdateDt));

		}
		
		Date d1 = pwdUpdateDt;
				//simpleFormatter.parse(simpleFormatter.format(pwdUpdateDt));
		Date d2 = dtLastLogin;
		
		Log.i("Utility","***********Todays Date in Days Count "+simpleFormatter.format(dtLastLogin));
//		days = (calendarPwdUpdate.getTimeInMillis() - calendarLastLogin.getTimeInMillis())/(1000*60*60*24);
		days = ((d1.getTime() - d2.getTime())/(1000*60*60*24))+1;
		//Log.i("Utility","***********Difference:- "+(c1.getTimeInMillis() - c2.getTimeInMillis())/(1000*60*60*24));
		Log.i("Utility","***********Difference:- "+((d1.getTime() - d2.getTime())/(1000*60*60*24)));
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return days+" days!";
	}
	
	/**
	 * This method is used to print Large string into chunks in Log.
	 * @param str the string to print into logs
	 */
	public static void printLargeString(String str){
		int maxLogSize = 1000000000;
		for(int i = 0; i <= str.length() / maxLogSize; i++) {
			int start = i * maxLogSize;
			int end = (i+1) * maxLogSize;
			end = end > str.length() ? str.length() : end;
			Log.v("Utility :", str.substring(start, end));
		}

	}

	/**
	 * This method is used to check user is admin or other user
	 *  @param context Object of Context, context from where the activity is going to start.
	 *  @return isAdmin Object of boolean 
	 */
	public static boolean isAdmin(Context context){
		boolean isAdmin=false;
		String user=SharedPrefrenceUtil.getPrefrence(context,Constants.DB_USERNAME,"tech");
		if(user.equalsIgnoreCase("admin")){
			isAdmin=true;
		}else{
			isAdmin=false;
		}
		return isAdmin;
	}
	/**
	 * This method is used to generate the random number up to 5 decimal. 
	 * @return randomNumber Object of int.
	 */
	public static int getRandomNumber(){
		int   randomNumber=0;
		Random ran=new Random();
		randomNumber =100000+ran.nextInt(900000);
		return randomNumber;
	}
	/**
	 * @param context   : Object of Context for which device id is required.
	 * @param teamName  : String Value contain two character of Name of Team
	 * @return noticeNo : Object of String.
	 */
	public static String getNoticeNo(Context context,String teamName)
	{
		String noticeNo;
		int rendomNo = Utility.getRandomNumber();
		String deviceID = Utility.getDeviceId(context).substring(0,5);
		noticeNo = teamName+deviceID+rendomNo;
		return noticeNo;
		
	}
//	public static String getNoticeNumber(Context context,String noticeNumber)
//	{
//		int length = noticeNumber.length();
//		if(length == 1){
//			noticeNumber = "000"+noticeNumber;
//		}else if(length == 2){
//			noticeNumber = "00"+noticeNumber;
//		}
//		else if(length == 3){
//			noticeNumber = "0"+noticeNumber;
//		}else{
//			if(noticeNumber.equals("0001") || noticeNumber.equals("9999") || noticeNumber.equals(Constants.LABEL_BLANK)){
//				noticeNumber = "0001";
//			}
//		}
//		return noticeNumber;
//	}

	/**
	 * This method is used to get the date of application first installed in device.
	 * @param context Object of Context, context from where the activity is going to start.
	 * @return date Object of String
	 */
	public static long getApkInstallDate(Context context){
		long date = 0;
		long installed = 0;
//		long in =0;
		String appName = null;
		try {
			 appName = context.getPackageManager().getPackageInfo(context.getPackageName(),0).applicationInfo.publicSourceDir;
//			 in = new File(appFile).lastModified();
			installed = context.getPackageManager().getPackageInfo(context.getPackageName(),0).lastUpdateTime;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		Date installDate = new Date(installed);
		System.out.printf("APK INSTALL DATE :---",installDate);
		System.out.printf("APK MODIFIED DATE :==" + appName);
		date = installDate.getTime();
		return date;
	}
	
	public static String getApkVersion(Context context){
		String appVersion = null;
		try {
			appVersion = context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return appVersion;
	}

	/**
	 * This method is used to get the date of application first installed in device.
	 * @param context Object of Context, context from where the activity is going to start.
	 * @return date Object of String
	 */
	public long  getApkInstallOn(Context context){
		long date = 0;
		long installed = 0;
		try {
			installed = context.getPackageManager().getPackageInfo(context.getPackageName(),0).firstInstallTime;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		Date installDate = new Date(installed);
		System.out.printf("APK INSTALL DATE :---",installDate);
		date = installDate.getTime();
		return date;
	}
	
	/**
	 * This method is used to get the date of application first installed in device.
	 * @param context Object of Context, context from where the activity is going to start.
	 * @return date Object of String
	 */
	public long  getApkCreatedOn(Context context){
		long date = 0;
		long installed = 0;
		try {
			installed = context.getPackageManager().getPackageInfo(context.getPackageName(),0).lastUpdateTime;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		Date installDate = new Date(installed);
		System.out.printf("APK INSTALL DATE :---",installDate);
		date = installDate.getTime();
		return date;
	}
	
	/**
	 * This method is used to write data into text file in given path.
	 * @param fileName name of file which will store in the given path.
	 * @param data text which is write into file.
	 */

	// Save settings 
	public static void WriteTextFile(String fileName,String data){

		try{
			File myFile = new File("/sdcard/"+fileName);
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = 
					new OutputStreamWriter(fOut);
			myOutWriter.append(data);
			myOutWriter.close();
			fOut.close();
			//			Toast.makeText(context,"Done writing SD"+fileName,Toast.LENGTH_SHORT).show();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to check whether Auto Batch Submit Service running or not.
	 * @param Context : Object of Context, context from where the activity is going to start.
	*/
	
	public static boolean isAutoSubmitServiceRunning(Context context) {
	    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if ("com.spec.asms.service.BatchSubmitService".equals(service.service.getClassName())) {
	        	Log.e("**********From Service***","DateTime:-"+Utility.getcurrentTimeStamp()+"Service Running");
	            return true;
	        }
	    }
	    return false;
	}
	
	  public static void makeCall(Activity activity,String contactNumber) {
	      Log.i("Make call", "");

	      Intent phoneIntent = new Intent(Intent.ACTION_CALL);
	      phoneIntent.setData(Uri.parse("tel:"+contactNumber));

	      try {
	         activity.startActivity(phoneIntent);
	         Log.i("Finished making a call...", "");
	      } catch (android.content.ActivityNotFoundException ex) {
	         Toast.makeText(activity, 
	         "Call faild, please try again later.", Toast.LENGTH_SHORT).show();
	      }
	   }
	  
	  public static String convertExceptionToString(Throwable e){
		    String errorMessage = Constants.BLANK;
		    StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			errorMessage = sw.toString();
			return errorMessage;
	  }
	
}