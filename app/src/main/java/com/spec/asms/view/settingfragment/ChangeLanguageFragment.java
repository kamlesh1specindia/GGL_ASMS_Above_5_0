package com.spec.asms.view.settingfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Global;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.SharedPrefrenceUtil;

/**
 * A Class for changing language.
 * @author jenisha
 *
 */
public class ChangeLanguageFragment extends Fragment {

	private View languageView;
	private Button rdBtnEng,rdBtnGuj;
	private TextView Gujrati;
	private int isSelected = 1;


	/**
	 * A parameterized constructor 
	 * @return Change Language Fragment object
	 */
	public static ChangeLanguageFragment newInstance() {
		ChangeLanguageFragment changeLanguageFragment = new ChangeLanguageFragment();
		return changeLanguageFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		

		super.onCreateView(inflater, container, savedInstanceState);
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::ChangeLanguageFragmentStarted:::");
		languageView = inflater.inflate(R.layout.change_language,null);
		Gujrati = (TextView)languageView.findViewById(R.id.lblChngLangGujarati);
		Gujrati.setTypeface(Global.typeface_Guj);
		rdBtnEng = (Button) languageView.findViewById(R.id.btnChngLangEnglish);
		rdBtnGuj = (Button) languageView.findViewById(R.id.btnChngLangGujarati);
		isSelected = SharedPrefrenceUtil.getPrefrence(getActivity(), Constants.PREF_LANGUAGE, Constants.LANGUAGE_ENGLISH);

		if(isSelected == Constants.LANGUAGE_ENGLISH){
			rdBtnEng.setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_button_selected));
			rdBtnGuj.setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_button_normal));
		}else{
			rdBtnGuj.setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_button_selected));
			rdBtnEng.setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_button_normal));
		}

		rdBtnEng.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				rdBtnEng.setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_button_selected));
				rdBtnGuj.setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_button_normal));
				SharedPrefrenceUtil.setPrefrence(getActivity(), Constants.PREF_LANGUAGE,Constants.LANGUAGE_ENGLISH);
			}
		});

		rdBtnGuj.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				rdBtnGuj.setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_button_selected));
				rdBtnEng.setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_button_normal));
				SharedPrefrenceUtil.setPrefrence(getActivity(), Constants.PREF_LANGUAGE,Constants.LANGUAGE_GUJRATI);
			}
		});

		return languageView;
	}	
}