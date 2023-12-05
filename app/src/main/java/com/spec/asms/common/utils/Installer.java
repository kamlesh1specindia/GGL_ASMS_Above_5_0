package com.spec.asms.common.utils;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.content.FileProvider;

/**
 * This is a utility class which installs and uninstalls the apk from other sources.
 * @author Ishan Dave
 *
 */
public class Installer{	
	
	/**
	 * This is a static method which uninstalls the current application and returns boolean value indicating whether the application uninstalled successfully
	 * or not. If file is uninstalled successfully then it returns true else returns false.
	 * @param context Application Context
	 * @return boolean
	 */
	public static boolean uninstall(Context context){
		try{
			String packageName = context.getPackageName();
			Uri packageURI = Uri.parse("package:"+packageName);
			Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
			context.startActivity(uninstallIntent);
		}catch(Exception e){
			Log.d("UNINSTALL", "Error occured while app uninstall: "+e.getMessage());
			return false;
		}
        Log.d("UNINSTALLATION", "App uninstalled");
        return true;
	}
	
	/**
	 * 
	 * This is a static method which installs and apk available in device and returns boolean value indicating whether the application installed successfully
	 * or not. If apk installed successfully then it returns true else returns false.
	 * @param context Application Context
	 * @param downloadPath String representing absolute path of apk file.
	 * @return boolean
	 */
	public static boolean install(Context context, String downloadPath){
		/*try{
			Intent intent = new Intent(Intent.ACTION_VIEW);
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

			} else {

			}
	        intent.setDataAndType(Uri.fromFile(new File(downloadPath)), "application/vnd.android.package-archive");
	        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
	        context.startActivity(intent);
		}catch(Exception e){
			Log.d("INSTALLATION", "Error occured while app installation: "+e.getMessage());
			return false;
		}
        Log.d("INSTALLATION", "App installed");*/

		Intent downloadIntent;

		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				File fileLocation = new File(context.getExternalFilesDir(null), downloadPath);
				Uri apkUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileProvider", fileLocation);

				downloadIntent = new Intent(Intent.ACTION_VIEW);
				downloadIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
				downloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				downloadIntent.setDataAndType(apkUri, "application/vnd.android.package-archive");

				List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(downloadIntent, PackageManager.MATCH_DEFAULT_ONLY);
				for (ResolveInfo resolveInfo : resInfoList) {
					String packageName = resolveInfo.activityInfo.packageName;
					context.grantUriPermission(packageName, apkUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
				}

			} else {
				File fileLocation = new File(context.getFilesDir(), downloadPath);
				downloadIntent = new Intent(Intent.ACTION_VIEW);
				downloadIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				downloadIntent.setDataAndType(Uri.fromFile(fileLocation), "application/vnd.android.package-archive");
			}
			context.startActivity(downloadIntent);
		} catch (Exception e) {
			Log.e("Problem installing", e.getMessage());
			return false;
		}
        return true;
	}
}  
