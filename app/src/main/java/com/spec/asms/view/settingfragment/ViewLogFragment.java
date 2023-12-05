package com.spec.asms.view.settingfragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Adapter.ViewLogAdapter;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.LogMasterService;
import com.spec.asms.service.async.AsyncViewLog;
import com.spec.asms.service.async.AsyncViewLog.OnResponseListenerViewLog;
import com.spec.asms.service.async.AsyncViewLogDates;
import com.spec.asms.service.async.AsyncViewLogDates.OnResponseListenerViewLogDates;
import com.spec.asms.vo.LogVO;

public class ViewLogFragment extends Fragment implements OnClickListener, OnCheckedChangeListener{
	
	private View logView;
	private ListView listViewLog;
	private List<LogVO> logVOs;
	private ViewLogAdapter viewLogAdapter;
	private AlertDialog alertDateDialog;
	private Button btnViewLogDialog;
	private Button btnViewLogFind;
//	private Button btnViewLogRefresh;
	private String [] dateArray;
//	private List<LogVO> tempLogList;
	private CheckBox chkLogView;
	private String currentDate; 
	private LogMasterService logMasterService;

	private static boolean isSelected = false;
	private AsyncViewLog asyncViewLog;
	public static Activity mActivity;
	
	public static ViewLogFragment newInstance(Activity activity) {
		mActivity = activity;
		ViewLogFragment viewLogFragment = new ViewLogFragment();
		return viewLogFragment;
	} 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::ViewLogFragment:::");
		
		logView = inflater.inflate(R.layout.view_log,null);
		listViewLog = (ListView) logView.findViewById(R.id.lstViewLog);
		btnViewLogDialog = (Button) logView.findViewById(R.id.btnViewLogDialog);
		btnViewLogFind = (Button) logView.findViewById(R.id.btnViewLogFind);
//		btnViewLogRefresh = (Button) logView.findViewById(R.id.btnViewLogRefresh);
		chkLogView = (CheckBox) logView.findViewById(R.id.chkLogView);
		
		btnViewLogDialog.setOnClickListener(this);
		btnViewLogFind.setOnClickListener(this);
//		btnViewLogRefresh.setOnClickListener(this);
		chkLogView.setOnCheckedChangeListener(this);
		
		logMasterService = new LogMasterService(getActivity());
		String where = Constants.LOG_FIELDS[0] + "<" + Utility.getDateBefore15Days();
		logMasterService.deleteLog(Constants.TBL_MST_LOG,where);
		
		currentDate = Utility.getConvertedDate(getActivity(), Utility.getConvertedLongToDateTime(getActivity(),System.currentTimeMillis()));
		btnViewLogDialog.setText(currentDate);
		
		isSelected = false;

		AsyncViewLogDates asyncViewLogDates = new AsyncViewLogDates(getActivity(), onResponseListenerViewLogDates);
		asyncViewLogDates.execute();
				
		return logView;
	}
	
	OnResponseListenerViewLogDates onResponseListenerViewLogDates = new OnResponseListenerViewLogDates() {
		
		public void onSuccess(List<String> result) {
			// TODO Auto-generated method stub
			List<String> dateList = result;
			if(dateList != null){
				 dateArray = new String[dateList.size()];
				 dateList.toArray(dateArray );
				 createDteDailog(dateArray);
			}
		 
		 asyncViewLog = new AsyncViewLog(getActivity(), onResponseListenerViewLog,currentDate,isSelected);
		 asyncViewLog.execute();
			
		}
		
		public void onFailure(String message) {
			
		}
	};
	

	OnResponseListenerViewLog onResponseListenerViewLog = new OnResponseListenerViewLog() {
		
		public void onSuccess(List<LogVO> result) {
			// TODO Auto-generated method stub
			logVOs = result;
			if(logView != null){
				  viewLogAdapter = new ViewLogAdapter(getActivity(), R.layout.list_view_log, logVOs);
					listViewLog.setAdapter(viewLogAdapter);
			}
		}
		
		public void onFailure(String message) {
			// TODO Auto-generated method stub
			
		}
	};
	
	
	public List<String> getDateList(List<LogVO> logList){
		List<String> list = new ArrayList<String>(0);
		
		for(int i=0;i<logList.size();i++){
			String date = Utility.getConvertedDate(getActivity(), Utility.getConvertedLongToDateTime(getActivity(), logList.get(i).getDatetime()));
			if(!list.contains(date)){
				list.add(date);
			}
		}
		return list;
	}
	
	
	public void createDteDailog(final String [] dateList){
		
		 if(dateList!= null && dateList.length > 0){
			 alertDateDialog = new AlertDialog.Builder(getActivity()).setSingleChoiceItems(dateList,0,new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int whichButton) {
						dialog.dismiss();
						btnViewLogDialog.setText(dateList[whichButton]);
//						selections =  new boolean[dateList.length ];
						
					}
				}).create();
		 }else{
			 btnViewLogDialog.setText(currentDate);
		 }
		
		
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.btnViewLogDialog:
			if(alertDateDialog != null && !alertDateDialog.isShowing()){
				alertDateDialog.show();
			}
			break;
		case R.id.btnViewLogFind:
				 currentDate = btnViewLogDialog.getText().toString();
			     asyncViewLog = new AsyncViewLog(getActivity(), onResponseListenerViewLog,currentDate,isSelected);
			     asyncViewLog.execute();
//				viewLogAdapter = new ViewLogAdapter(getActivity(), R.layout.list_view_log,tempLogList);
//				listViewLog.setAdapter(viewLogAdapter);
			break;
//		case R.id.btnViewLogRefresh:
//			btnViewLogDialog.setText(currentDate);
//			tempLogList = searchByDate(logVOs, btnViewLogDialog.getText().toString());
//			viewLogAdapter = new ViewLogAdapter(getActivity(), R.layout.list_view_log,tempLogList);
//			listViewLog.setAdapter(viewLogAdapter);
//			break;
		default:
			break;
		}
		
	}
	
	public List<LogVO> searchByDate(List<LogVO> list,String searchValue){
		List<LogVO> listLogVOs = new ArrayList<LogVO>(0);
		for(int i = 0;i<list.size();i++){
			String date = Utility.getConvertedDate(getActivity(),Utility.getConvertedLongToDateTime(getActivity(), list.get(i).getDatetime()));
			if(date.equalsIgnoreCase(searchValue))
			{
				listLogVOs.add(list.get(i));
			}
		}
		return listLogVOs;
	}
	
	public List<LogVO>getBlankRecords(List<LogVO> list){
		List<LogVO> listLogVOs = new ArrayList<LogVO>(0);
		for(int i = 0;i<list.size();i++){
			int batchSize =  list.get(i).getBatchsize();
			if(batchSize == 0)
			{
				listLogVOs.add(list.get(i));
			}
		}
		return listLogVOs;
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		isSelected = isChecked;
		if(buttonView == chkLogView){
			if(isChecked){
			     chkLogView.setButtonDrawable(getResources().getDrawable(R.drawable.checkbox_marked));
			}else{
				chkLogView.setButtonDrawable(getResources().getDrawable(R.drawable.checkbox_empty));
			}
			currentDate = btnViewLogDialog.getText().toString();
			asyncViewLog = new AsyncViewLog(getActivity(), onResponseListenerViewLog,currentDate,isSelected);
		    asyncViewLog.execute();
		}
	}
}
