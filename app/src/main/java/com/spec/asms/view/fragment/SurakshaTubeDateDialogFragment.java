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
import com.spec.asms.common.utils.Utility;
import com.spec.asms.view.fragment.MaintanenceForm.DateDialogFragmentListener;

/**
 * Class used to display Date Dialog in Fragment
 * @author jenisha
 */
public class SurakshaTubeDateDialogFragment extends DialogFragment {
	
	static Context dateContext; 
	static int curYear;
	static int curMonth;
	static int curDay;
	static DateDialogFragmentListener dateListener;
	
	public static SurakshaTubeDateDialogFragment newInstance(Context context,DateDialogFragmentListener listener, Calendar now) {
		SurakshaTubeDateDialogFragment dialog = new SurakshaTubeDateDialogFragment();
		dateContext = context;
		dateListener = listener;
		curYear = now.get(Calendar.YEAR);
		curMonth = now.get(Calendar.MONTH);
		curDay = now.get(Calendar.DAY_OF_MONTH);
		return dialog;
	}
	
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		DatePickerDialog dialog = new DatePickerDialog(dateContext, dateSetListener,curYear,curMonth,curDay);
		dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 10000);
		return dialog;
	}
	
	
	private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
		
		public void onDateSet(DatePicker view,int selectedYear,int selectedMonth, int selectedDay) {
			
			final Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR,selectedYear);
		    c.set(Calendar.MONTH, selectedMonth);
		    //c.set(Calendar.DAY_OF_MONTH,selectedDay);
		    
		    //Current Date    
		    @SuppressWarnings("unused")
			Date currentDate = c.getTime();
		  
		   /* //Last 30 days Date
		    final Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.roll(Calendar.DAY_OF_YEAR, -30);
			Date daysBeforeDate = cal.getTime();*/
		
		
		    final Calendar futureCal = Calendar.getInstance();
			futureCal.setTime(new Date());
			futureCal.roll(Calendar.YEAR,+5);
			Date daysAfterDate = futureCal.getTime();
			
			//Selected Date
			final Calendar cale = Calendar.getInstance();
			int year = selectedYear;
			int month = selectedMonth;
			int day = selectedDay;
			cale.set(Calendar.YEAR, year);
			cale.set(Calendar.MONTH, month);
			//cale.set(Calendar.DAY_OF_MONTH, day);
		    
		    Date selectedDate = cale.getTime();
		    System.out.println("Selected Date :::"+selectedDate.toGMTString());
		    System.out.println("Future Date :::"+daysAfterDate.toGMTString());
		    
		    if(selectedDate.after(daysAfterDate)){
		    	Utility.alertDialog(getActivity(),getResources().getString(R.string.error_header_dateTitle),getResources().getString(R.string.errro_header_date_after_five_year));
		    	dateListener.updateChangedDate(curYear, curMonth, curDay);
		    }else{
		    	dateListener.updateChangedDate(year, month, day);
		    }

		}
	};
}