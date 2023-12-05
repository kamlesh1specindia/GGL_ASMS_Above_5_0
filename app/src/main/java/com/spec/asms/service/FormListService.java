package com.spec.asms.service;

import java.util.ArrayList;

import android.content.Context;

import com.spec.asms.R;
import com.spec.asms.view.fragment.MaintanenceForm;

public class FormListService {	

	public static ArrayList<String> LoadFormList(Context context){

		ArrayList<String> arrayFormList = new ArrayList<String>();
		MaintanenceForm.validateList.clear();
		String[] st = context.getResources().getStringArray(R.array.maintanance_form_list);

		for(int o = 0 ; o < st.length ; o++){			
			arrayFormList.add(st[o]);
			if(arrayFormList.get(o).equalsIgnoreCase("CONFORMANCE") || arrayFormList.get(o).equalsIgnoreCase("GEYSER & MAKE") )
			{
				MaintanenceForm.validateList.add(false);
			}else{
				MaintanenceForm.validateList.add(true);
			}
		}
		return arrayFormList;
	}

	public static ArrayList<String> LoadSettingsList(Context context){

		ArrayList<String> arraySettingList = new ArrayList<String>();
		String[] st = context.getResources().getStringArray(R.array.settings_form_list);

		for(int o = 0 ; o < st.length ; o++){			
			arraySettingList.add(st[o]);
		}
		return arraySettingList;
	}
}