package com.spec.asms.view.settingfragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Global;
import com.spec.asms.common.Adapter.SettingDetailListAdapter;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.CustomerMasterService;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.view.LoginActivity;
import com.spec.asms.vo.UserMasterVO;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
public class DetailFregment extends Fragment {

	private View detailView;
	public CustomerMasterService custService;
	public UserMasterService usermaster;
	private ListView lvUsers;
	private TextView sugContractor;
	private TextView sugTeam;
	private ArrayList<String>users = new ArrayList<String>();
	private List<UserMasterVO>userName;
	private UserMasterVO usermasterVo;
	private int id;
	private TextView contractor;
	private TextView technician;
	private TextView team;
	String contractorName ;
	private TextView txtFileName,txtInstallOn;
	private TextView lblFileName,lblInstallOn;
	
	public static DetailFregment newInstance(){
		DetailFregment detailFregment = new DetailFregment();
		return detailFregment;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


		super.onCreateView(inflater, container, savedInstanceState);
		ApplicationLog.writeToFile(getActivity(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::DetailLogFragment:::");
		
		detailView = inflater.inflate(R.layout.form_details,null);
		sugContractor = (TextView)detailView.findViewById(R.id.lblSugdetailCortactor);
		sugTeam = (TextView)detailView.findViewById(R.id.lblSugdetailTeam);
		lvUsers = (ListView)detailView.findViewById(R.id.lvUsers);
		contractor = (TextView)detailView.findViewById(R.id.lbldetailContractor);
		team = (TextView)detailView.findViewById(R.id.lbldetailTeam);
		technician = (TextView)detailView.findViewById(R.id.lbldetailTechnician);
		txtFileName = (TextView) detailView.findViewById(R.id.txtFileName);
		txtInstallOn = (TextView)detailView.findViewById(R.id.txtInstallOn);
		lblFileName = (TextView) detailView.findViewById(R.id.lblFileName);
		lblInstallOn = (TextView)detailView.findViewById(R.id.lblInstallOn);
		
		changeLanguage(id);
		custService = new CustomerMasterService(getActivity());
		usermaster = new UserMasterService(getActivity());
		usermasterVo = new UserMasterVO();
		userName = new ArrayList<UserMasterVO>();
		userName = usermaster.getAllUsers(Constants.TBL_MST_USER,Constants.USER_MASTER_FIELDS);
		for(int i =0 ; i<userName.size();i++)
		{
			usermasterVo = userName.get(i);
			if(usermasterVo.getRoleID()== 2)
			{
				users.add(usermasterVo.getUserName());

			}else if(usermasterVo.getRoleID() == 3)
			{
				contractorName = usermasterVo.getUserName();
			}
		}
		Collections.sort(users, new Comparator<String>() {

			public int compare(String one, String two) {
				// TODO Auto-generated method stub
				return one.compareToIgnoreCase(two);
			}
		});
		lvUsers.setAdapter(new SettingDetailListAdapter(getActivity(),users));
		sugContractor.setText(contractorName);
		sugTeam.setText(custService.getCustNTeamNames(Constants.JSON_TEAM));
		
		String fileName = custService.getCustNTeamNames(Constants.JSON_RELATIVE_PATH);
		//String fileName = "/tablet/ASMS_Android_Tablet_Rel_V1.0.0.apk";
		if(fileName.equals(Constants.LABEL_BLANK))
			fileName = "/tablet/ASMS_Android_Tablet_Rel_V1.0.0.apk";
		if(fileName.length()>0){
			fileName = fileName.substring(fileName.lastIndexOf("//")+1, fileName.length());
			fileName = fileName.substring(fileName.lastIndexOf("/")+1, fileName.length());
		}
		String appVersion = Utility.getApkVersion(getActivity());
		txtFileName.setText(fileName + Constants.LABEL_SPACE + getResources().getString(R.string.lbl_app_version) + appVersion);
		txtInstallOn.setText(Utility.getConvertedLongToDateTime(getActivity(),Utility.getApkInstallDate(getActivity())));
		
		return detailView; 
	}
	public void changeLanguage(int id){

		id = SharedPrefrenceUtil.getPrefrence(getActivity(),Constants.PREF_LANGUAGE,Constants.LANGUAGE_ENGLISH);
		switch (id){
		case Constants.LANGUAGE_ENGLISH:
			contractor.setText(getResources().getText(R.string.title_detail_contractor_Eng));
			team.setText(getResources().getText(R.string.title_detail_team_Eng));
			technician.setText(getResources().getText(R.string.title_detail_technician_Eng));
			lblFileName.setText(getResources().getText(R.string.title_detail_fileName_Eng));
			lblInstallOn.setText(getResources().getText(R.string.title_detail_fileInsatll_Eng));
			break;

		case Constants.LANGUAGE_GUJRATI:
			contractor.setTypeface(Global.typeface_Guj);
			team.setTypeface(Global.typeface_Guj);
			technician.setTypeface(Global.typeface_Guj);
			lblFileName.setTypeface(Global.typeface_Guj);
			lblInstallOn.setTypeface(Global.typeface_Guj);
			contractor.setText(getResources().getText(R.string.title_detail_contractor_Guj));
			team.setText(getResources().getText(R.string.title_detail_team_Guj));
			technician.setText(getResources().getText(R.string.title_detail_technician_Guj));
			lblFileName.setText(getResources().getText(R.string.title_detail_fileName_Guj));
			lblInstallOn.setText(getResources().getText(R.string.title_detail_fileInsatll_Guj));
			break;

		default:
			break;
		}
	}
}