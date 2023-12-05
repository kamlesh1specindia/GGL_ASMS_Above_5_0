package com.spec.asms.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.service.BatchSubmitService;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.service.async.AsyncGetAllMasters;
import com.spec.asms.service.async.AsyncUpdateCustomerStatus;
import com.spec.asms.service.async.AsyncGetAllMasters.OnResponseListener;
import com.spec.asms.service.async.AsyncUpdateCustomerStatus.OnResponseUpdateCustomerStatusId;
import com.spec.asms.view.LoginActivity;
import com.spec.asms.view.MainTabActivity;

/**
 * This class is responsible for downloading a zip or image file.
 * @author spec-india
 *
 */
public class FileDownloder extends AsyncTask<String, Integer, Integer> {
	private final String APP_NAME = "ASMS.apk";
	private final String FOLDER_NAME="/asms/";
	private ProgressDialog downloadProgDialog;
	private Activity activity;
	private Context context; 
	private String donwnloadPath = Environment.getExternalStorageDirectory().getAbsolutePath()+FOLDER_NAME;
	private String filePath = Environment.getExternalStorageDirectory()+FOLDER_NAME;	
	private UserMasterService userService;
	private String strUsername;
	private String strPassword;


	public FileDownloder(Activity activity,Context context,UserMasterService userMaster,String strUsername,String strPassword ){
		this.activity = activity;
		this.context = context;
		this.userService = userMaster;
		this.strPassword = strPassword;
		this.strUsername = strUsername;
	}	 

	// private String destinationFilePath = Environment.getExternalStorageDirectory()+"/SScanBookshelf/Shelves/My_Bookshelf/";
	@Override
	protected Integer doInBackground(String... sUrl) {

		try {

			filePath = Environment.getExternalStorageDirectory()+FOLDER_NAME;
			URL url = new URL(sUrl[0]);
			URLConnection connection = url.openConnection();
			connection.connect();
			//File name
			String fileName = url.getFile();
			fileName = fileName.substring(fileName.lastIndexOf("//")+1, fileName.length());
			fileName = fileName.substring(fileName.lastIndexOf("/")+1, fileName.length());
			filePath = filePath+fileName;

			// this will be useful so that you can show a typical 0-100% progress bar
			int fileLength = connection.getContentLength();

			if(AvailableSpaceHandler.getExternalAvailableSpaceInBytes()>fileLength){
				// download the file
				InputStream input = new BufferedInputStream(url.openStream());
				//OutputStream output;
				File file=new File(donwnloadPath);
				if(file.exists())
					file.delete();

				file.mkdir();

				FileOutputStream fos;
				if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
					//File newAPKFile = new File(context.get(null), file+"/"+APP_NAME);
					File newAPKFile = new File(context.getExternalFilesDir(null), APP_NAME);
					if(newAPKFile.exists())
						newAPKFile.delete();

					newAPKFile.createNewFile();
					fos = new FileOutputStream(newAPKFile);
					donwnloadPath = newAPKFile.getAbsolutePath();
					//fos = new FileOutputStream(file+"/"+APP_NAME);
				} else {
					fos = context.openFileOutput(file+"/"+APP_NAME, context.MODE_PRIVATE);
				}


				//output = new FileOutputStream(file+"/"+APP_NAME);
				byte data[] = new byte[1024];
				long total = 0;
				int count;
				while ((count = input.read(data)) != -1) {
					total += count;
					// publishing the progress....
					publishProgress((int) (total * 100 / fileLength));
					fos.write(data, 0, count);
				}

				fos.flush();
				fos.close();
				input.close();
			} else {
				downloadProgDialog.dismiss();
				return 0;
			}
		} catch (Exception e) {
			downloadProgDialog.dismiss();
			Log.d(Constants.DEBUG,":FileDownloader:"+ Utility.convertExceptionToString(e));
			return 0;
		}

		return 1;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		try{
			downloadProgDialog = new ProgressDialog(this.activity);
			downloadProgDialog.setMessage(Constants.DOWNLOAD_MSG);
			downloadProgDialog.setIndeterminate(false);
			downloadProgDialog.setMax(100);
			downloadProgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			downloadProgDialog.show();
		}catch (Exception e) {
			Log.d(Constants.EXCEPTION,e.getMessage());
		}
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		super.onProgressUpdate(progress);
		try{
			downloadProgDialog.setProgress(progress[0]);
		}catch (Exception e) {
			Log.d(Constants.DEBUG,":FileDownloader:"+ Utility.convertExceptionToString(e));
		}
	}

	@Override
	protected void onPostExecute(Integer result)
	{
		super.onPostExecute(result);
		try{
			downloadProgDialog.dismiss();
			try{
				boolean check = Installer.install(activity, Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ? APP_NAME: donwnloadPath);
				if(!check){
					//Show message there is problem in installation, please try again.
					Utility.alertDialog(context,Constants.ALERT_TITLE_GENERAL_ERROR, Constants.APP_INSTALL_ERROR);
				}else{

				}
			}catch (Exception e) {
				Log.d(Constants.EXCEPTION,e.getMessage());

			}
			/*String message = Constants.APP_UPDATE_MESSAGE;
			alertDialog(activity, Constants.APP_UPDATE_TITLE, message, donwnloadPath+APP_NAME);*/
		}catch (Exception e) {
			Log.d(Constants.DEBUG,":FileDownloader:"+ Utility.convertExceptionToString(e));
		}
	}

	/**
	 * This method Provides alert Dialog 
	 * @param  : Object of Context, context from where the activity is going to start.
	 * @param title   : Tital of Dialog
	 * @param message : Message that we want to Display on Dialog.
	 */
	public void alertDialog(final Activity activity, String title, String message, final String downloadPath){
		try{
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);  
			builder.setCancelable(true);		
			builder.setTitle(title);  
			builder.setMessage(message);
			
			builder.setPositiveButton(Constants.LABLE_NEXT, new DialogInterface.OnClickListener() {			
				public void onClick(DialogInterface dialog, int which) {
					try{
						boolean check = Installer.install(activity, downloadPath);
						if(!check){
							//Show message there is problem in installation, please try again.
							Utility.alertDialog(context,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.APP_INSTALL_ERROR);
						}else{
							
						}
					}catch (Exception e) {
						Log.d(Constants.EXCEPTION,e.getMessage());
						dialog.dismiss();
					}
					dialog.dismiss();
				}
			});
			builder.setNegativeButton(Constants.LABLE_CANCEL, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					try{
						if(Utility.isNetAvailable(context)){
							Log.e(Constants.TAG_LOGIN_ACTIVITY, "in onResponseListenerAuth responce Authentication is true ***************** ::: ");		
							userService.deleteAllMaster();

							Map<String, Object> param = new HashMap<String, Object>();
							param.put(Constants.JSON_LOGIN_USERNAME, SecurityManager.encrypt(strUsername,Constants.SALT));
							param.put(Constants.JSON_LOGIN_PASSWORD, SecurityManager.encrypt(strPassword,Constants.SALT));
							param.put(Constants.JSON_LOGIN_DEVICEID, SecurityManager.encrypt(Constants.LABLE_DEVICE_ID,Constants.SALT));//Utility.getDeviceId(LoginActivity.this));

							AsyncGetAllMasters asyncGetAllMasters= new AsyncGetAllMasters(context,onResponseListener);
							asyncGetAllMasters.execute(param);

							SharedPrefrenceUtil.setPrefrence(context, Constants.DB_USERNAME, strUsername);
							SharedPrefrenceUtil.setPrefrence(context, Constants.DB_PASSWORD, strPassword);
						}
						else {
							Utility.alertDialog(context, Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
						}
					}catch (Exception e) {
						Log.d(Constants.DEBUG,":FileDownloader:"+ Utility.convertExceptionToString(e));
					}
				}
			});
			
			AlertDialog alert = builder.create();  
			alert.show(); 
		}catch (Exception e) {
			Log.d(Constants.DEBUG,":FileDownloader:"+ Utility.convertExceptionToString(e));
		}
	}

	/**
	 * Method call when all master data successfully insert to database from server to navigate to MainTab Activity.
	 */
	protected OnResponseListener onResponseListener = new OnResponseListener() {
		public void onSuccess(JSONObject responce) {
			try {
				
				
				if(responce != null && responce.equals(Constants.JSON_FAILED)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.JSON_FAILED);
					Utility.alertDialog(activity,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_JSON_FAILED);
				}else if(responce.has(Constants.NETWORK_EXCEPTION_METHOD)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.NETWORK_EXCEPTION_METHOD);
					Utility.alertDialog(activity, Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
				}else if(responce.has(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.CONNECTION_REFUSED_EXCEPTION_METHOD);
					Utility.alertDialog(activity,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.SOCKET_EXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.SOCKET_EXCEPTION);
					Utility.alertDialog(activity,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.IOEXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.IOEXCEPTION);
					Utility.alertDialog(activity,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.XMLPULLPARSEREXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.XMLPULLPARSEREXCEPTION);
					Utility.alertDialog(activity,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.getString(Constants.METHOD_AUTHENTICATE).equalsIgnoreCase(Constants.LABEL_TRUE)){
					JSONObject masterData = responce.getJSONObject(Constants.JSON_MASTER_DATA);
					Log.d("JSON", "::::::::: masterData ::: "+masterData);
					Log.d("JSON", "::::::::: masterData.getString(SqlTimeStamp) ::: "+masterData.getString(Constants.JSON_SQL_TIMESTAMP));
					
					//User logged in ...now set shared prefrence
					SharedPrefrenceUtil.setPrefrence(context, Constants.LABEL_LOGIN, true);
					
					Intent i = new Intent(context,MainTabActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//|Intent.FLAG_ACTIVITY_NO_HISTORY);
					context.startActivity(i);
					activity.finish();
					
					/*AsyncUpdateCustomerStatus asyncUpdateCustomerStatus = new AsyncUpdateCustomerStatus(context,onResponseUpdateCustomerStatusId);
					asyncUpdateCustomerStatus.execute();*/
				}else {
					Utility.alertDialog(context, Constants.ALERT_TITLE_LOGIN_ERROR,context.getResources().getString(R.string.error_pwdusername));
				}
			} catch (Exception e) {
				Log.d(Constants.DEBUG,":FileDownloader:"+ Utility.convertExceptionToString(e));
			}
		}
		public void onFailure(String message) {
		}
	};

	
/*protected OnResponseUpdateCustomerStatusId onResponseUpdateCustomerStatusId = new OnResponseUpdateCustomerStatusId() {
		
		@Override
		public void onSuccess(String result) {
			

			
		}
		
		@Override
		public void onFailure(String message) {
			// TODO Auto-generated method stub
			
		}
	};*/
}




