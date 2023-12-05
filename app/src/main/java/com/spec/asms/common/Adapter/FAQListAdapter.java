package com.spec.asms.common.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.vo.FAQMasterVO;
/**
 * An Adapter class to display Faq List.
 * @author jenisha
 *
 */
public class FAQListAdapter extends ArrayAdapter<FAQMasterVO>{

	private List<FAQMasterVO> faqList = new ArrayList<FAQMasterVO>();
	private Context context;
	private int resource; 

	public FAQListAdapter(Context context, int resource,List<FAQMasterVO> objects) {
		super(context, resource, objects);
		this.faqList = objects;
		this.resource = resource;
		this.context = context;

	}
	@Override
	public int getCount() {
		return faqList.size();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		try{
			if(convertView == null){
				LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = layoutInflater.inflate(resource, parent,false);
			}
			
			FAQMasterVO faq = faqList.get(position);
			
			if(faq != null){
				TextView txtQuestion = (TextView)convertView.findViewById(R.id.txtQuestion);
				TextView txtAnswer = (TextView)convertView.findViewById(R.id.txtAnswer);

				txtQuestion.setText(faq.getQuestion());
				txtAnswer.setText(faq.getAnswer());
			}
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":FAQListAdapter:"+ Utility.convertExceptionToString(e));
		}
		return convertView;
	}
}