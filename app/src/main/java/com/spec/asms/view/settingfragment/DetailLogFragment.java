package com.spec.asms.view.settingfragment;

import java.io.File;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.FileInString;

public class DetailLogFragment extends Fragment{
	
	private View logView;
//	private WebView webViewLog;
	private TextView Tvpreviewreports;
	
	public static DetailLogFragment newInstance() {
		DetailLogFragment viewLogFragment = new DetailLogFragment();
		return viewLogFragment;
	} 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		logView = inflater.inflate(R.layout.detail_view_log,null);
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::DetailLogFragment:::");
		
		Tvpreviewreports = (TextView) logView.findViewById(R.id.Tvpreviewreports);
		
		
//		webViewLog = (WebView) logView.findViewById(R.id.webViewLog);
		
		File filename = new File(Environment.getExternalStorageDirectory()+"/"+Constants.APP_NAME+".log");
		if(filename.exists()){
			String data =  new FileInString().readFileAsString(filename);
//			webViewLog.loadData(data,"text/html", "UTF-8");
			Tvpreviewreports.setText(data);

		}else{
			Toast.makeText(getActivity(), "Log file is not exist.",Toast.LENGTH_LONG);
		}
				
		return logView;
	}

}
