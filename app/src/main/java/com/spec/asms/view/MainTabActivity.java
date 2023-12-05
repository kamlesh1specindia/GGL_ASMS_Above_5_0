package com.spec.asms.view;

import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Global;
import com.spec.asms.common.utils.ApplicationLog;
import com.spec.asms.common.utils.SecurityManager;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.async.AsyncSyncCustomerData;
import com.spec.asms.service.async.AsyncSyncCustomerData.OnResponseListener;
import com.spec.asms.view.fragment.TestingFragment;
import com.spec.asms.view.settingfragment.AdminSettingForm;
import com.spec.asms.view.settingfragment.FAQFragment;
import com.spec.asms.view.settingfragment.HelpFragment;
import com.spec.asms.view.settingfragment.SettingsForm;
import com.spec.asms.view.settingfragment.ViewLogFragment;
import com.spec.asms.vo.ClampingVO;
import com.spec.asms.vo.ConformanceDetailVO;
import com.spec.asms.vo.CustomerFeedbackVO;
import com.spec.asms.vo.GIFittingVO;
import com.spec.asms.vo.KitchenSurakshaTubeVO;
import com.spec.asms.vo.LeakageVO;
import com.spec.asms.vo.MakeGeyserVO;
import com.spec.asms.vo.OtherChecksVO;
import com.spec.asms.vo.OtherKitchenSurakshaTubeVO;
import com.spec.asms.vo.PaintingVO;
import com.spec.asms.vo.RccVO;
import com.spec.asms.vo.TestingVO;

public class MainTabActivity extends Activity implements ActionBar.TabListener{

	private String[] userTabs = new String[]{"Maintenance","Settings","FAQ"};
	private String[] adminTabs = new String[]{"Maintenance","Settings"};
	private String[] tabs = userTabs;
	private TextView mActionBarView;
	private boolean fragment;//it will be used in backpressed state ... to chk which tab user has backed frm
	public static boolean isBack = false; // to bring call close dialog on backpress
	private boolean onHelp = false; // for TabReselect from Help screen , re-loads in sony tab
	public static ActionBar bar;
	private String user;
	protected SharedPreferences pref ;
	private MenuItem lblSugDeviceId;
	private MenuItem lblSugUserName;
	public static MenuItem imgUploader;
//	public static Boolean isCycleRunning;
	private static Bundle mMainFragmentArgs;
	private ViewAssignmentActivity viewAssignmentActivity;
	 protected PowerManager.WakeLock mWakeLock;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		   final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
	        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
	        this.mWakeLock.acquire();

		ApplicationLog.writeToFile(getApplicationContext(), Constants.APP_NAME,Constants.DEBUG);
		Log.d(Constants.DEBUG,":::MainTabActivityStarted:::");
		bar = getActionBar();		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.banner_top));
	    user = SharedPrefrenceUtil.getPrefrence(MainTabActivity.this,Constants.DB_USERNAME,"tech");
	    System.out.println("User Name #################:"+user);
	    if(Utility.isAdmin(MainTabActivity.this)){
	    	tabs = adminTabs;
	    }else{
	    	tabs = userTabs;
	    }
		int i;		
		for (i = 0; i < tabs.length; i++) {
			mActionBarView = (TextView) getLayoutInflater().inflate(
					R.layout.action_bar_custom, null);
			mActionBarView.setText(tabs[i]);
			bar.addTab(bar.newTab().setText(tabs[i]).setCustomView(mActionBarView)
					.setTabListener(this));
		}

		bar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//		bar.setDisplayShowHomeEnabled(true);
		bar.selectTab(bar.getTabAt(0));
	}

	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {		
		switch (tab.getPosition()) {
		case 0:
			fragment = true;
			isBack = true;
			onHelp = false;
			viewAssignmentActivity = new ViewAssignmentActivity();	
			FragmentManager fragmentManager = getFragmentManager();
			ft = fragmentManager.beginTransaction();
			ft.replace(R.id.mainFrg, viewAssignmentActivity);
			ft.commit();			
			break;		
		case 1:
			fragment = false;
			isBack = true;
			SettingsForm.isPassword = false;
			SettingsForm settingsFragment = new SettingsForm();
			AdminSettingForm adminSetingFragment = new AdminSettingForm();
			if(Utility.isAdmin(MainTabActivity.this))
			{
				FragmentManager fmadminSettings = getFragmentManager();
				ft = fmadminSettings.beginTransaction();
				ft.replace(R.id.mainFrg, adminSetingFragment);
				ft.commit();
				
			}else{
			settingsFragment.setActivity(MainTabActivity.this);
			FragmentManager fmSettings = getFragmentManager();
			ft = fmSettings.beginTransaction();
			ft.replace(R.id.mainFrg, settingsFragment);
			ft.commit();
			}
			break;
		case 2:
			isBack = true;
			FAQFragment faqFragment = new FAQFragment();
			FragmentManager fmFAQ = getFragmentManager();
			ft = fmFAQ.beginTransaction();
			ft.replace(R.id.mainFrg, faqFragment);
			ft.commit();
			break;
		default:
			break;
		}
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		isBack = true;
		switch (tab.getPosition()) {
		case 0:
			if(onHelp)
			{
				fragment = true;
				onHelp = false;
				viewAssignmentActivity = new ViewAssignmentActivity();	
				FragmentManager fragmentManager = getFragmentManager();	
				ft = fragmentManager.beginTransaction();
				ft.replace(R.id.mainFrg, viewAssignmentActivity);
				ft.commit();
			}
			break;
		case 1:
			fragment = false;
			onHelp = false;
			SettingsForm settingsFragment = new SettingsForm();
			AdminSettingForm adminSetingFragment = new AdminSettingForm();
			SettingsForm.isPassword = false;
			
			if(Utility.isAdmin(MainTabActivity.this))
			{
				FragmentManager fmadminSettings = getFragmentManager();
				ft = fmadminSettings.beginTransaction();
				ft.replace(R.id.mainFrg, adminSetingFragment);
				ft.commit();
				
			}else{
			settingsFragment.setActivity(MainTabActivity.this);
			FragmentManager fmSettings = getFragmentManager();
			ft = fmSettings.beginTransaction();
			ft.replace(R.id.mainFrg, settingsFragment);
			ft.commit();
			}
			break;
		case 2:
			onHelp = false;
			FAQFragment faqFragment = new FAQFragment();
			FragmentManager fmFAQ = getFragmentManager();
			ft = fmFAQ.beginTransaction();
			ft.replace(R.id.mainFrg, faqFragment);			
			ft.commit();
			break;
		}
	}

	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    if(Utility.isAdmin(MainTabActivity.this)){
	    	inflater.inflate(R.menu.admin_menu, menu);
		    lblSugDeviceId = (MenuItem)menu.findItem(R.id.menulblSugDeviceId);
		    lblSugDeviceId.setTitle(""+Utility.getDeviceId(MainTabActivity.this));
	    }else{
	    	inflater.inflate(R.menu.main_menu, menu);
	    	lblSugUserName = (MenuItem)menu.findItem(R.id.menulblSugUserName);
//	    	imgUploader = (MenuItem)menu.findItem(R.id.imgUploader);
	    	lblSugUserName.setTitle(SharedPrefrenceUtil.getPrefrence(MainTabActivity.this,Constants.DB_USERNAME,Constants.LABEL_BLANK));
	    	
//	    	if(isCycleRunning)
//	    	{
//	    		imgUploader = (MenuItem)menu.findItem(R.id.imgUploader);
//	    		imgUploader.setVisible(true);
//	    	}else{
//	    		imgUploader = (MenuItem)menu.findItem(R.id.imgUploader);
//	    		imgUploader.setVisible(false);
//	    	}
	    	
	    }
		return true;
	}
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		isBack = true;
		switch (item.getItemId()) {
		case R.id.toggleCleanup:
			try{/*
//			if(Utility.isNetAvailable(MainTabActivity.this)){
				AlertDialog.Builder alert = new AlertDialog.Builder(MainTabActivity.this);
				alert.setTitle(Constants.ALERT_TITLE_CLEANUP);
				alert.setOnKeyListener(new OnKeyListener() {
					public boolean onKey(DialogInterface dialog, int keyCode,
							KeyEvent event) {
						if (keyCode == KeyEvent.KEYCODE_BACK) {
							return true;
						}
						return false;
					}
				});
				alert.setMessage(Constants.LABEL_ALERT_CLEANUP_CONFORMATION);
				alert.setPositiveButton(Constants.LABLE_YES, new DialogInterface.OnClickListener() {			
					public void onClick(DialogInterface dialog, int which) {
						try{							
							dialog.dismiss();
							CleanupService cleanupService = new CleanupService(MainTabActivity.this,viewAssignmentActivity.iCleanUpResponseListener);
							cleanupService.execute();
						}catch (Exception e) {
							dialog.dismiss();
							e.printStackTrace();
						}
					}
				});
				alert.setNegativeButton(Constants.LABLE_NO, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				alert.show();
				return true;
//			}else{
//				
//			  Utility.alertDialog(MainTabActivity.this, Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
//			}
			*/
				Log.d(Constants.DEBUG,"MainTabActivity:onOptionsItemSelected:called");
				Map<String, Object> param = new HashMap<String, Object>();
				param.put(Constants.JSON_LOGIN_USERNAME, SecurityManager.encrypt(SharedPrefrenceUtil.getPrefrence(this,Constants.DB_USERNAME, Constants.LABEL_BLANK),Constants.SALT));
				param.put(Constants.JSON_LOGIN_PASSWORD, SecurityManager.encrypt(SharedPrefrenceUtil.getPrefrence(this,Constants.DB_PASSWORD, Constants.LABEL_BLANK),Constants.SALT));
				param.put(Constants.JSON_LOGIN_DEVICEID, SecurityManager.encrypt(Constants.LABLE_DEVICE_ID,Constants.SALT));
				
				AsyncSyncCustomerData asyncSyncCustomerData = new AsyncSyncCustomerData(this,onResponseListener);
				asyncSyncCustomerData.execute(param);
			
			
			}catch (Exception e) {
				e.printStackTrace();
				Log.d(Constants.DEBUG,"MainTabActivity:onOptionsItemSelected:"+ Utility.convertExceptionToString(e));
			}
			return true;
		case R.id.toggleHelp:
			onHelp = true;
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction ft = fragmentManager.beginTransaction();
			HelpFragment helpFragment = new HelpFragment();
			FragmentManager fmFAQ = getFragmentManager();
			ft = fmFAQ.beginTransaction();
			ft.replace(R.id.mainFrg, helpFragment);
			ft.commit();
			return true;
		case R.id.toggleLogout:	
			Global.isFilterRequired = false;
			Utility.createCloseDialog(MainTabActivity.this,MainTabActivity.this);			
			return true;       
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@SuppressWarnings("unused")
	@Override
	public void onBackPressed() {		
		//		super.onBackPressed();
		try{
			FragmentManager fragmentManager = getFragmentManager();		
			int count = fragmentManager.getBackStackEntryCount();	

			

			if(!fragment){
				ViewLogFragment viewLogFragment = (ViewLogFragment) getFragmentManager().findFragmentByTag("log");
				if(viewLogFragment != null && viewLogFragment.isVisible()){
					SettingsForm settingsFragment = new SettingsForm();
					settingsFragment.setActivity(MainTabActivity.this);
					FragmentTransaction ft;
					FragmentManager fmSettings = getFragmentManager();
					ft = fmSettings.beginTransaction();
					ft.replace(R.id.mainFrg, settingsFragment);
					ft.commit();
				}else{
					Utility.createCloseDialog(MainTabActivity.this, MainTabActivity.this);
				}
			}				
			else{
				if(!isBack){

					if(!user.equals("admin"))
					{
						if("OP".equals(TestingFragment.testingStatusCode))					
						{
							createCloseDialog(MainTabActivity.this,MainTabActivity.this);
						}else
						{
							TestingVO.cleanTestingVO();
							GIFittingVO.cleanGiFitting();
							ClampingVO.cleanClampingVO();
							PaintingVO.cleanPaintingVO();
							LeakageVO.cleanLeakage();
							RccVO.cleanRCCVO();
							MakeGeyserVO.cleanMakeGeyser();
							OtherKitchenSurakshaTubeVO.cleanOtherKitchenSurakshaTubeVO();
							KitchenSurakshaTubeVO.cleanKitchenSurakshaTubeVO();
							OtherChecksVO.cleanOtherChecks();
							ConformanceDetailVO.cleanConformanceVO();
							CustomerFeedbackVO.cleanomerFeedbackCustVO();
							isBack = true;
							fragmentManager.popBackStack();
							viewAssignmentActivity = new ViewAssignmentActivity();	
							FragmentManager fragManager = getFragmentManager();			
							FragmentTransaction ft = fragManager.beginTransaction();
							ft.replace(R.id.mainFrg, viewAssignmentActivity);
							ft.commit();
						}
					}else
						{
						TestingVO.cleanTestingVO();
						GIFittingVO.cleanGiFitting();
						ClampingVO.cleanClampingVO();
						PaintingVO.cleanPaintingVO();
						LeakageVO.cleanLeakage();
						RccVO.cleanRCCVO();
						MakeGeyserVO.cleanMakeGeyser();
						OtherKitchenSurakshaTubeVO.cleanOtherKitchenSurakshaTubeVO();
						KitchenSurakshaTubeVO.cleanKitchenSurakshaTubeVO();
						OtherChecksVO.cleanOtherChecks();
						ConformanceDetailVO.cleanConformanceVO();
						CustomerFeedbackVO.cleanomerFeedbackCustVO();
							isBack = true;
							fragmentManager.popBackStack();
							viewAssignmentActivity = new ViewAssignmentActivity();	
							FragmentManager fragManager = getFragmentManager();			
							FragmentTransaction ft = fragManager.beginTransaction();
							ft.replace(R.id.mainFrg, viewAssignmentActivity);
							ft.commit();
						}

				}else
				{
					ViewLogFragment viewLogFragment = (ViewLogFragment) getFragmentManager().findFragmentByTag("log");
					if(viewLogFragment != null && viewLogFragment.isInLayout()){
						TestingVO.cleanTestingVO();
						GIFittingVO.cleanGiFitting();
						ClampingVO.cleanClampingVO();
						PaintingVO.cleanPaintingVO();
						LeakageVO.cleanLeakage();
						RccVO.cleanRCCVO();
						MakeGeyserVO.cleanMakeGeyser();
						OtherKitchenSurakshaTubeVO.cleanOtherKitchenSurakshaTubeVO();
						KitchenSurakshaTubeVO.cleanKitchenSurakshaTubeVO();
						OtherChecksVO.cleanOtherChecks();
						ConformanceDetailVO.cleanConformanceVO();
						CustomerFeedbackVO.cleanomerFeedbackCustVO();
						SettingsForm settingsFragment = new SettingsForm();
						FragmentTransaction ft;
						FragmentManager fmSettings = getFragmentManager();
						ft = fmSettings.beginTransaction();
						ft.replace(R.id.mainFrg, settingsFragment);
						ft.commit();
					}else{
						Utility.createCloseDialog(MainTabActivity.this, MainTabActivity.this);
					}
					
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,"MainTabActivity:onBackPressed:"+ Utility.convertExceptionToString(e));
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		/*try{
		if(!ScreenReceiver.wasScreenOn)
		{
			//Return after screen is on but receiver will be called after resume function
			System.out.println(" --- SCREEN 1-----");
			ScreenReceiver.wasScreenOn = true;
			finish();
			
		}else
		{
			System.out.println(" --- SCREEN 2-----");
			
		}
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,"MainTabActivity:onResume:"+ Utility.convertExceptionToString(e));
		}*/
	}
	
	
	public void createCloseDialog(final Context context, final Activity activity) {
		try{
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle(Constants.LABEL_DIALOG_CONFIRM);
		alert.setOnKeyListener(new OnKeyListener() {

			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					return true;
				}
				return false;
			}
		});
		alert.setMessage(context.getResources()
				.getString(R.string.info_backing));
		alert.setPositiveButton(Constants.LABLE_YES, new OnClickListener() {			
			public void onClick(DialogInterface dialog, int which) {
				try{	
					TestingVO.cleanTestingVO();
					GIFittingVO.cleanGiFitting();
					ClampingVO.cleanClampingVO();
					PaintingVO.cleanPaintingVO();
					LeakageVO.cleanLeakage();
					RccVO.cleanRCCVO();
					MakeGeyserVO.cleanMakeGeyser();
					OtherKitchenSurakshaTubeVO.cleanOtherKitchenSurakshaTubeVO();
					KitchenSurakshaTubeVO.cleanKitchenSurakshaTubeVO();
					OtherChecksVO.cleanOtherChecks();
					ConformanceDetailVO.cleanConformanceVO();
					CustomerFeedbackVO.cleanomerFeedbackCustVO();
					isBack = true;
					viewAssignmentActivity = new ViewAssignmentActivity();	
					FragmentManager fragManager = getFragmentManager();			
					FragmentTransaction ft = fragManager.beginTransaction();
					ft.replace(R.id.mainFrg, viewAssignmentActivity);
					ft.commit();
				}catch (Exception e) {
					e.printStackTrace();
					Log.d(Constants.DEBUG,"MainTabActivity:createCloseDialog:onPossitive:"+ Utility.convertExceptionToString(e));
				}
				dialog.dismiss();
			}
		});
		alert.setNegativeButton(Constants.LABLE_NO, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
		alert.show();
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,"MainTabActivity:createCloseDialog:"+ Utility.convertExceptionToString(e));
		}
	}
	
	 
	 
	  public static void saveMainFragmentState(Bundle args) {
	        mMainFragmentArgs = args;
	    }

	  
	   public static Bundle getSavedMainFragmentState() {
	        return mMainFragmentArgs;
	    }
	
		protected OnResponseListener onResponseListener = new OnResponseListener() {
			public void onSuccess() {
				try{
					viewAssignmentActivity = new ViewAssignmentActivity();	
					FragmentManager fragmentManager = getFragmentManager();	
					FragmentTransaction ft = fragmentManager.beginTransaction();
					ft = fragmentManager.beginTransaction();
					ft.replace(R.id.mainFrg, viewAssignmentActivity);
					ft.commit();
//					ViewAssignmentActivity viewAssignmentActivity = new ViewAssignmentActivity();
//					viewAssignmentActivity.getAsyncCustomerData(MainTabActivity.this);
//					mrunit = custService.getMRUnitList().toArray(new String[custService.getMRUnitList().size()]);
					SharedPrefrenceUtil.setPrefrence(MainTabActivity.this,Constants.LABEL_IS_CUST_AVAILABLE,true);
				}catch(Exception e){
					e.printStackTrace();
					Log.d(Constants.APP_NAME,"MainTabActivity:onResponseListerner:");
					Log.d(Constants.DEBUG,"MainTabActivity:onResponseListerner:"+ Utility.convertExceptionToString(e));
				}
		
			}
			public void onFailure(String message) {
			}
		};
}