package com.spec.asms.common.Adapter;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.spec.asms.R;
import com.spec.asms.common.utils.SortCollections;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.vo.LogVO;

public class ViewLogAdapter extends ArrayAdapter<LogVO>{

	private Context context;
	private List<LogVO> logList;
	private int resource;
	
	public ViewLogAdapter(Context context, int textViewResourceId,List<LogVO> logList) {
		super(context, textViewResourceId, logList);
		this.context = context;
		this.resource = textViewResourceId;
		this.logList = logList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return logList.size();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(resource, parent,false);
		}
		
		Collections.sort(logList,new SortCollections());
				
		LogVO logVo = logList.get(position);
		
		if(logVo != null){
			
			TextView lblDateTime = (TextView) convertView.findViewById(R.id.lblDateTime);
			TextView lblResponseTime = (TextView) convertView.findViewById(R.id.lblResponseTime);
			TextView lblObjectStatus = (TextView) convertView.findViewById(R.id.lblObjectStatus);
			TextView lblSubmitStatus = (TextView) convertView.findViewById(R.id.lblSubmitStatus);
			TextView lblResponseStatus = (TextView) convertView.findViewById(R.id.lblResponseStatus);
			TextView lblBatchSize = (TextView) convertView.findViewById(R.id.lblBatchSizes);
			TextView lblTotalSubmitted = (TextView) convertView.findViewById(R.id.lblTotalSubmit);
			TextView lblException = (TextView) convertView.findViewById(R.id.lblException);

			lblDateTime.setText(Utility.getConvertedLongToDateTime(context, logVo.getDatetime()));
			if(logVo.getResponsetime() != 0){
				lblResponseTime.setText(Utility.getConvertedLongToDateTime(context,logVo.getResponsetime()));
			}else{
				lblResponseTime.setText(" ");
			}
			lblObjectStatus.setText(logVo.getObjectstatus());
			lblSubmitStatus.setText(logVo.getSubmitstatus());
			lblResponseStatus.setText(logVo.getResponsestatus());
			lblBatchSize.setText(String.valueOf(logVo.getBatchsize()));
			lblTotalSubmitted.setText(String.valueOf(logVo.getTotalsubmitted()));
			lblException.setText(logVo.getException());
			
		}
		return convertView;
	}

}
