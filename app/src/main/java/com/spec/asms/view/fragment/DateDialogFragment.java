package com.spec.asms.view.fragment;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.view.fragment.MaintanenceForm.DateDialogFragmentListener;

/**
 * Class used to display Date Dialog in Fragment
 * @author jenisha
 */
public class DateDialogFragment extends DialogFragment {
	
	static Context dateContext; 
	static int curYear;
	static int curMonth;
	static int curDay;
	static DateDialogFragmentListener dateListener;
	
	public static DateDialogFragment newInstance(Context context,DateDialogFragmentListener listener, Calendar now) {
		DateDialogFragment dialog = new DateDialogFragment();
		dateContext = context;
		dateListener = listener;
		curYear = now.get(Calendar.YEAR);
		curMonth = now.get(Calendar.MONTH);
		curDay = now.get(Calendar.DAY_OF_MONTH);
		return dialog;
	}
	
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new DatePickerDialog(dateContext, dateSetListener,curYear,curMonth,curDay);
	}
	
	
	private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
		
		public void onDateSet(DatePicker view,int selectedYear,int selectedMonth, int selectedDay) {
			
			final Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR,selectedYear);
		    c.set(Calendar.MONTH, selectedMonth);
		    c.set(Calendar.DAY_OF_MONTH,selectedDay);
		    
		    //Current Date    
		    @SuppressWarnings("unused")
			Date currentDate = c.getTime();
		  
		    //Last 30 days Date
		    final Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.roll(Calendar.DAY_OF_YEAR, -30);
			Date daysBeforeDate = cal.getTime();
		
		
		    final Calendar futureCal = Calendar.getInstance();
			futureCal.setTime(new Date());
			futureCal.roll(Calendar.DAY_OF_YEAR,+1);
			Date daysAfterDate = futureCal.getTime();
			
			//Selected Date
			final Calendar cale = Calendar.getInstance();
			int year = selectedYear;
			int month = selectedMonth;
			int day = selectedDay;
			cale.set(Calendar.YEAR, year);
			cale.set(Calendar.MONTH, month);
			cale.set(Calendar.DAY_OF_MONTH, day);
		    
		    Date selectedDate = cale.getTime();
		    System.out.println("Selected Date :::"+selectedDate.toGMTString());
		    System.out.println("Future Date :::"+daysAfterDate.toGMTString());
		    
		    if(selectedDate.after(daysAfterDate)){
		    	Utility.alertDialog(getActivity(),getResources().getString(R.string.error_header_dateTitle),getResources().getString(R.string.errro_header_dateFutureMessage));
		    	dateListener.updateChangedDate(curYear, curMonth, curDay);
		    }else if(selectedDate.equals(daysAfterDate)){
		    	Utility.alertDialog(getActivity(),getResources().getString(R.string.error_header_dateTitle),getResources().getString(R.string.errro_header_dateFutureMessage));
		    	dateListener.updateChangedDate(curYear, curMonth, curDay);
		    }else if(selectedDate.before(daysBeforeDate)){
		    	Utility.alertDialog(getActivity(),getResources().getString(R.string.error_header_dateTitle),getResources().getString(R.string.errro_header_datePastMessage));
		    	dateListener.updateChangedDate(curYear, curMonth, curDay);
		    }else{
		    	dateListener.updateChangedDate(year, month, day);
		    }
//		    System.out.println(" ##### Codition Value"+(currentDate.compareTo(selectedDate) * selectedDate.compareTo(daysAfterDate)));
//		    //comparison between dates
//		    /*a.compareTo(d) * d.compareTo(b) > 0;*/
//			if(!((currentDate.compareTo(selectedDate) * selectedDate.compareTo(daysAfterDate)) > 0)){
//				Utility.alertDialog(getActivity(),getResources().getString(R.string.error_header_dateTitle),getResources().getString(R.string.errro_header_dateFutureMessage));
//				// set current date into datepicker
//				System.out.println(" ##### Current Date ::::"+selectedDate.toGMTString());
//				System.out.println(" ###### Future Date ::::"+daysAfterDate.toGMTString());
//				System.out.println(" ##### Codition Value"+(currentDate.compareTo(selectedDate) * selectedDate.compareTo(daysAfterDate)));
//				dateListener.updateChangedDate(curYear, curMonth, curDay);
//			
//			}else if(currentDate.compareTo(selectedDate) * selectedDate.compareTo(daysBeforeDate) > 0){
//				Utility.alertDialog(getActivity(),getResources().getString(R.string.error_header_dateTitle),getResources().getString(R.string.errro_header_datePastMessage));
//				dateListener.updateChangedDate(curYear, curMonth, curDay);
//			
//			}else{
//				dateListener.updateChangedDate(year, month, day);
//			}
		}
	};
}