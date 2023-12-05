package com.spec.asms.view.fragment;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.ApplicationLog;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class DetailFregment extends Fragment {

	private View detailView;

	public static DetailFregment newInstance(){
		DetailFregment detailFregment = new DetailFregment();
		return detailFregment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		detailView = inflater.inflate(R.layout.form_faq,null);
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
		return detailView; 
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}