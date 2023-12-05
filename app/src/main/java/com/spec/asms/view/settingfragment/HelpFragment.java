package com.spec.asms.view.settingfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Global;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.SharedPrefrenceUtil;

/**
 * A class for displaying Help
 * @author jenisha
 *
 */
public class HelpFragment extends Fragment{

	private View helpView;
	private TextView txtOperational;
	private TextView txtTempDisconnected;
	private TextView txtForceDisconnected;

	private int id;
	
	/**
	 * A parameterized constructor 
	 * @return Help Fragment object
	 */
	public static HelpFragment newInstance() {
		HelpFragment helpFragment = new HelpFragment();
		return helpFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		helpView = inflater.inflate(R.layout.form_help,null);
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::HelpFragmentStarted:::");
		txtOperational = (TextView) helpView.findViewById(R.id.txtOperational);
		txtTempDisconnected = (TextView) helpView.findViewById(R.id.txtTempDisconnected);
		txtForceDisconnected = (TextView) helpView.findViewById(R.id.txtForceDisconnected);
		changeLanguage(id);
		
			
		return helpView; 
	}
	
	public void changeLanguage(int id){

		id = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.PREF_LANGUAGE,Constants.LANGUAGE_ENGLISH);

		switch (id) {
		case Constants.LANGUAGE_ENGLISH:
			txtOperational.setText(getResources().getString(R.string.lbl_operational_status_Eng));
			txtTempDisconnected.setText(getResources().getString(R.string.lbl_temp_disconnected_status_Eng));
			txtForceDisconnected.setText(getResources().getString(R.string.lbl_force_disconnected_status_Eng));
			break;
		case Constants.LANGUAGE_GUJRATI:
			txtOperational.setTypeface(Global.typeface_Guj);
			txtTempDisconnected.setTypeface(Global.typeface_Guj);
			txtForceDisconnected.setTypeface(Global.typeface_Guj);
			txtOperational.setText(getResources().getString(R.string.lbl_operational_status_Guj));
			txtTempDisconnected.setText(getResources().getString(R.string.lbl_temp_disconnected_status_Guj));
			txtForceDisconnected.setText(getResources().getString(R.string.lbl_force_disconnected_status_Guj));	
			break;

		default:
			break;
		}
	}
	
	
}
