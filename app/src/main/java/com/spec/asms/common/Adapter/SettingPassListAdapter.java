package com.spec.asms.common.Adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.SecurityManager;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.async.AsyncChangePwd;
import com.spec.asms.service.async.AsyncChangePwd.OnResponseListenerChangePwd;
import com.spec.asms.view.LoginActivity;
import com.spec.asms.vo.UserMasterVO;

public class SettingPassListAdapter extends BaseAdapter {

	private ArrayList<UserMasterVO>userList=new ArrayList<UserMasterVO>();
	private LayoutInflater layoutInflator;
//	private Activity activity;
	private Context context;
//	private UserMasterService userService;
	private int userId;

	public SettingPassListAdapter(Context context,Activity activity,ArrayList<UserMasterVO> userList) {

		layoutInflator = LayoutInflater.from(activity);
//		this.activity = activity;
		this.context = context;
		this.userList = userList;
//		userService = new UserMasterService(activity);
	}

	public int getCount() {
		return userList.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if(convertView == null){

			convertView = layoutInflator.inflate(R.layout.user_block_list,null);
			holder = new ViewHolder();
			holder.txtUserName = (TextView) convertView.findViewById(R.id.txtUserName);
			holder.btnUnblock=(Button)convertView.findViewById(R.id.btnSettingUnblock);
			convertView.setTag(holder);

		}else	
			holder = (ViewHolder) convertView.getTag();   
		UserMasterVO userVo=userList.get(position);

		holder.txtUserName.setText(userVo.getUserName());
		holder.btnUnblock.setId(position);

		System.out.println("User Name: ############"+userVo.getUserName());
		System.out.println("User Id ########:"+userVo.getUserID());


		holder.btnUnblock.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("unchecked")
			public void onClick(View v) {
				UserMasterVO userVO=userList.get(position);
				userId=userVO.getUserID();

				System.out.println("User Id ########:"+userId);

				String newPassword = null;

				for(int i=0; i<userVO.getPassword().length(); i++){
					if(Character.isUpperCase(userVO.getPassword().charAt(i))){
						Log.e("SettingPassListAdapter", "DateTime:-"+Utility.getcurrentTimeStamp()+ "in Uppercase %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
					}else if(Character.isLowerCase(userVO.getPassword().charAt(i))){
						Log.e("SettingPassListAdapter",  "DateTime:-"+Utility.getcurrentTimeStamp()+"in LowerCase %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
					}
				}

				if(Utility.isNetAvailable(context)){

					Map<String, Object> param = new HashMap<String, Object>();
					param.put(Constants.JSON_LOGIN_USERNAME, SecurityManager.encrypt(userVO.getUserName(),Constants.SALT));
					param.put(Constants.JSON_LOGIN_PASSWORD, SecurityManager.encrypt(userVO.getPassword(),Constants.SALT));
					param.put(Constants.JSON_LOGIN_DEVICEID, SecurityManager.encrypt(Constants.LABLE_DEVICE_ID,Constants.SALT));//Utility.getDeviceId(getActivity()));
					param.put(Constants.JSON_NEW_PASSWORD,SecurityManager.encrypt(newPassword,Constants.SALT));

					AsyncChangePwd asyncChangePwd= new AsyncChangePwd(context, onResponseListenerPwd);
					asyncChangePwd.execute(param);
				
				}else {
					Utility.alertDialog(context, Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
				}

			}
		});

		return convertView;
	}


	protected OnResponseListenerChangePwd onResponseListenerPwd = new OnResponseListenerChangePwd() {

		public void onSuccess(JSONObject responce) {
			try {
				Log.e(Constants.TAG_LOGIN_ACTIVITY, "in onResponseListenerAuth responce ::: "+responce);
				if(responce != null && responce.equals(Constants.JSON_FAILED)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.JSON_FAILED);
					Utility.alertDialog(context,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_JSON_FAILED);
				}else if(responce.has(Constants.NETWORK_EXCEPTION_METHOD)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.NETWORK_EXCEPTION_METHOD);
					Utility.alertDialog(context, Constants.ALERT_TITLE_NETWORK_ERROR, Constants.ERROR_INTERNET_CONNECTION);
				}else if(responce.has(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.CONNECTION_REFUSED_EXCEPTION_METHOD);
					Utility.alertDialog(context,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.SOCKET_EXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.SOCKET_EXCEPTION);
					Utility.alertDialog(context,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.IOEXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.IOEXCEPTION);
					Utility.alertDialog(context,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.XMLPULLPARSEREXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.XMLPULLPARSEREXCEPTION);
					Utility.alertDialog(context,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.has(Constants.EXCEPTION)){
					Log.e(Constants.TAG_SERVICE, "DateTime:-"+Utility.getcurrentTimeStamp()+Constants.EXCEPTION);
					Utility.alertDialog(context,Constants.ALERT_TITLE_GENERAL_ERROR,Constants.ERROR_SERVER_NOT_AVAILABLE);
				}else if(responce.getString("Status").equalsIgnoreCase(Constants.LABEL_TRUE)){
					//					Utility.alertDialog(context, Constants.ALERT_TITLE_GENERAL, "Your new password is: "+ newPassword);
					//					String where = "userid = "+userId;
					//					ContentValues cv=new ContentValues();
					//					cv.put(Constants.USER_MASTER_FIELDS[3],newPassword);
					//					cv.put(Constants.USER_MASTER_FIELDS[9],System.currentTimeMillis());
					//					cv.put(Constants.USER_MASTER_FIELDS[10],0);
					//					long id=userService.updateUserByID(Constants.TBL_MST_USER,cv, where);
					//					System.out.println("Unblock Button is Clicked");
					//					System.out.println("Updated Date in long"+System.currentTimeMillis());
					//					userVO.setIslock(0);
					//					userList.remove(position);
					//					notifyDataSetChanged();
					//					System.out.println("Unlock Password"+id);
				}

			} catch (Exception e) {
				e.printStackTrace();
				Log.d(Constants.DEBUG,":SettingPassListAdapter:"+ Utility.convertExceptionToString(e));
			}
		}
		public void onFailure(String message) {
		}
	};

}
class ViewHolder 
{
	TextView txtUserName;
	Button btnUnblock;
}
