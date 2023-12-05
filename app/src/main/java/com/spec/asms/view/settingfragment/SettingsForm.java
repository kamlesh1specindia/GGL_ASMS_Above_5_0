package com.spec.asms.view.settingfragment;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Adapter.SettingListAdapter;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.FormListService;

/**
 * A class is used to maintain different forms in Fragment.
 *
 */
public class SettingsForm extends Fragment{
	
	private ProgressDialog progressDialog;
	
	public static ListView formListView;
	public static SettingListAdapter settListAdapter;
	public static boolean isPassword = false;
	public static Activity mActivity;
	private FragmentManager fragmentManager;
	@SuppressWarnings("unused")
	private int userID;	
    public View formView;
    @SuppressWarnings("unused")
	private String user;
	
    public static SettingsForm newInstance(int id){
    	SettingsForm maintanenceForm = new SettingsForm();
    	Bundle args = new Bundle();
		args.putInt("id",id);
		maintanenceForm.setArguments(args);
    	return maintanenceForm; 
    }
    
	public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, Bundle savedInstanceState) {
		
		super.onCreateView(inflater, container, savedInstanceState);
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::SettingsFormStarted:::");
		
		user=SharedPrefrenceUtil.getPrefrence(mActivity,Constants.PREF_USER_ADMIN,"user");
		
		formView = inflater.inflate(R.layout.settingsform,null);
		
		
		SharedPreferences pref = mActivity.getSharedPreferences("com.spec.asms",Context.MODE_PRIVATE);
		userID = pref.getInt("userID",0);
		
		fragmentManager = getFragmentManager();
		formListView = (ListView) formView.findViewById(R.id.listMntnceForm);
			
		LoadList loadList = new LoadList();
		loadList.execute();

		return formView;
	}

	/**
	 * Method used to navigate between fragments on Maintenance form.
	 * @param index : index to display specific fragment.
	 * 
	 */
	private void showFragments(int index){
		try{
			FragmentTransaction ft = fragmentManager.beginTransaction();
			switch (index) {
			case 0:
				ChangeLanguageFragment testingFragment = ChangeLanguageFragment.newInstance();				
				ft.replace(R.id.frmLayoutFragment, testingFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);		
				break;
//			case 1:
//				ChangePasswordFragment chngPasswordFragment = ChangePasswordFragment.newInstance();				
//				ft.replace(R.id.frmLayoutFragment, chngPasswordFragment);
//				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);	
//				break;
			case 1:
				SynchronizeFragment synchronizeFragment= SynchronizeFragment.newInstance();				
				ft.replace(R.id.frmLayoutFragment, synchronizeFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				break;
			case 2:
				DetailFregment detailFregment= DetailFregment.newInstance();				
				ft.replace(R.id.frmLayoutFragment, detailFregment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				break;
			case 3:
				ViewLogFragment faqFragment = new ViewLogFragment();
				ft.replace(R.id.mainFrg, faqFragment,"log");			
				
//				Intent intent = new Intent(getActivity(), ViewLogActivity.class);
//				startActivity(intent);
				break;
			case 4:
				HelpFragment helpFragment = new HelpFragment();
				ft.replace(R.id.frmLayoutFragment, helpFragment);
				
			default:
				break;
			}			
			ft.commit();
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":SettingsForm:showFragment:"+ Utility.convertExceptionToString(e));
		}		
	}
	
	/**
	 * A class is used to load side list in Maintainance Form. 
	 */
	class LoadList extends AsyncTask<Void,Void,ArrayList<String>>{
		
		@Override
		protected void onPreExecute() {		
			super.onPreExecute();
			progressDialog = new ProgressDialog(mActivity);
			progressDialog = ProgressDialog.show(mActivity, Constants.LABEL_BLANK,
					Constants.LABEL_PROGRESS);		
			progressDialog.show();
		}
		
		@Override
		protected ArrayList<String> doInBackground(Void... params) {	
			ArrayList<String> arrayList = FormListService.LoadSettingsList(mActivity);
			
			if(arrayList.size() > 0)
				return arrayList;
			else			
				return null;
		}
		
		@Override
		protected void onPostExecute(final ArrayList<String> result) {

			super.onPostExecute(result);
			progressDialog.dismiss();
			if(result != null){
				settListAdapter = new SettingListAdapter(mActivity,result);
				if(isPassword)
					SettingListAdapter.itemSelected.set(1, true);
				else 
					SettingListAdapter.itemSelected.set(0, true);

				formListView.setAdapter(settListAdapter);
				formListView.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {		
						
						for(int i=0;i<arg0.getChildCount();i++){
							View v = arg0.getChildAt(i);
							if(i!=position){
								v.setBackgroundResource(R.drawable.left_pannel_button_normal);
								TextView tv = (TextView)v.findViewById(R.id.txtFormListRow); 
								tv.setTextColor(getResources().getColor(R.color.form_blue));
							}
						}
						
						for(int i = 0;i<result.size();i++){
							SettingListAdapter.itemSelected.set(i, false);
						}
						SettingListAdapter.itemSelected.set(position, true);
						arg1.setBackgroundDrawable(getResources().getDrawable(R.drawable.left_pannel_button_selected));
						TextView tv = (TextView)arg1.findViewById(R.id.txtFormListRow); 
						tv.setTextColor(getResources().getColor(R.color.white));
						showFragments(position);
					}
				});
				if(isPassword)
					showFragments(1);
				else
					showFragments(0);
			}
			else{
				Utility.alertDialog(mActivity,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_LOADING_DATA);
			}			
		}		
	}
	
	public void setActivity(Activity activity){
		this.mActivity = activity;
	}
}