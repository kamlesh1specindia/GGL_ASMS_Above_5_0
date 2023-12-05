package com.spec.asms.view;

import java.util.ArrayList;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.CoreActivity;
import com.spec.asms.common.Adapter.SettingPassListAdapter;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.vo.UserMasterVO;
/**
 * A class for Admin activity.
 *
 */
public class AdminActivity extends CoreActivity{

	private Button btnLogout;
	private Button btnAdmin;
	private EditText settingIP;
	private String strSettingIP;
	private UserMasterService usermaster;
	
	private ListView userListView;
	private UserMasterService userService;
	private ArrayList<UserMasterVO>userList=new ArrayList<UserMasterVO>();
	private SettingPassListAdapter settingPassAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminservice);
		ApplicationLog.writeToFile(getApplicationContext(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::AdminActivityStarted:::");
		
		btnAdmin = (Button)findViewById(R.id.btnAdmin);
		btnLogout = (Button)findViewById(R.id.btnLogout);
		settingIP = (EditText)findViewById(R.id.edtSettingIP);
		btnAdmin.setOnClickListener(btnClick);
		btnLogout.setOnClickListener(btnClick);
		userListView=(ListView) findViewById(R.id.list);

		userService=new UserMasterService(AdminActivity.this);
		userList=userService.getLockedUsers();
		System.out.println("USer List size : #######"+userList.size());
		settingPassAdapter=new SettingPassListAdapter(AdminActivity.this,AdminActivity.this,userList);
		userListView.setAdapter(settingPassAdapter);
//		btnAdmin.setOnClickListener(btnClick);
//		btnLogout.setOnClickListener(btnClick);
		
		
	}
	
	public OnClickListener btnClick = new OnClickListener() 
	{
		public void onClick(View v) {
			if(v == btnAdmin){
				try{
					strSettingIP=settingIP.getText().toString();
					ContentValues cv = new ContentValues();
					cv.put(Constants.DB_URL, strSettingIP);
					startActivity(AdminActivity.this,AdminService.class);
					usermaster.insertUser(Constants.TBL_DTL_ADMIN, cv);
				}catch(Exception e){
					Log.d(Constants.DEBUG,":AdminActivity:onClick:"+ Utility.convertExceptionToString(e));
				}
		
				
			}else if(v == btnLogout){
				Utility.createCloseDialog(AdminActivity.this,AdminActivity.this);
			}
		}
	};
//	public OnClickListener btnClick = new OnClickListener() 
//	{
//		public void onClick(View v) {
//			if(v == btnAdmin){
//				startActivity(AdminActivity.this,AdminService.class);
//				
//			}else if(v == btnLogout){
//				Utility.createCloseDialog(AdminActivity.this,AdminActivity.this);
//			}
//		}
//	};

}
