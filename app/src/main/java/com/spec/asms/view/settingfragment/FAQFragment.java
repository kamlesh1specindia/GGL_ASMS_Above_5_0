package com.spec.asms.view.settingfragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Adapter.FAQListAdapter;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.service.FAQMasterService;
import com.spec.asms.vo.FAQMasterVO;

/**
 * A class for displaying FAQ question & answers.
 * @author mansi
 *
 */
public class FAQFragment extends Fragment {

	private View faqView;

	private ListView formListView;

	private FAQMasterService faqMasterService;

	private ArrayList<FAQMasterVO> arrayFormList ;
	private FAQListAdapter faqListAdapter;


	/**
	 * A parameterized constructor 
	 * @return FAQ Fragment object
	 */
	public static FAQFragment newInstance(){
		FAQFragment faqFragment = new FAQFragment();
		return faqFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		faqView = inflater.inflate(R.layout.form_faq,null);
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::FAQFragment:::");
		
		formListView = (ListView) faqView.findViewById(R.id.lstFAQandHelp);

		faqMasterService = new FAQMasterService(getActivity());
		arrayFormList = new ArrayList<FAQMasterVO>();

		arrayFormList = (ArrayList<FAQMasterVO>) faqMasterService.getAllFAQ(Constants.TBL_MST_FAQ, new String[] {Constants.DB_FAQ_ID,Constants.DB_QUESTION,Constants.DB_ANSWER});
		faqListAdapter = new FAQListAdapter(getActivity(), R.layout.form_setting_list, arrayFormList);
		formListView.setAdapter(faqListAdapter);

		return faqView; 
	}
}