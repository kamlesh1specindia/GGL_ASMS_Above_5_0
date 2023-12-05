package com.spec.asms.service.async;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.SocketException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.SOAPCaller;
import com.spec.asms.service.UserMasterService;

public class AsyncGetAllMasters extends AsyncTask<Map<String, Object>, Void, JSONObject>{
	private ProgressDialog prDialog = null;
	private UserMasterService userService;
	private JSONObject objTech;
	private JSONObject response = null;
	private OnResponseListener responseListener;
	private Context context;

	public AsyncGetAllMasters(Context context, OnResponseListener responder) {
		this.context = context;
		this.prDialog = new ProgressDialog(context);
		this.responseListener = responder;
	}

	@Override
	protected void onPreExecute() {			
		super.onPreExecute();

		try {
			prDialog = new ProgressDialog(context);
			prDialog.setMessage(Constants.LABEL_BLANK+ Constants.LABEL_PROGRESS_GETALLMASTERS);
			prDialog.setCancelable(false);
			prDialog.show();
			prDialog.setProgress(10);
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncGetAllMaster:"+ Utility.convertExceptionToString(ex));
		}


	}
	@Override
	protected JSONObject doInBackground(Map<String, Object>... params) {
		SOAPCaller soapCaller = SOAPCaller.getInstance();

		Map<String, Object> param = params[0];
//		Log.e("AsyncGetAllMasters",  "DateTime:-"+Utility.getcurrentTimeStamp()+"Get All Master param.size() :::::::::::::::: "+param.size());
		try {
			userService = new UserMasterService(context);
			response = (JSONObject)soapCaller.getResponse(Constants.METHOD_GET_ALL_MARSTERS, param)[0];			
//			Log.d("Response", "******"+response);
			Utility.WriteTextFile("master.txt",""+response);
			JSONObject masterData = response.getJSONObject(Constants.JSON_MASTER_DATA);
//			Log.d("JSON", "::::::::: masterData ::: "+masterData);

	  if(masterData != null){
			if(masterData.has(Constants.JSON_TECHNICIANS)){
				JSONArray technicians = masterData.getJSONArray(Constants.JSON_TECHNICIANS);
				if(technicians != null && technicians.length() > 0){
					Log.d("JSON", "::::::::: technicians ::: "+technicians);
//					Log.d("JSON", "::::::::: technicians.length() ::: "+technicians.length());
					for(int i=0;i<technicians.length();i++){
						objTech = technicians.getJSONObject(i);
					  if(objTech != null){
//						Log.d("JSON", "::::::::: technicians objTech ::: "+objTech);
//						Log.d("JSON", "::::::::: technicians ID ::: "+objTech.getString(Constants.JSON_ID));
//						Log.d("JSON", "::::::::: technicians UserName ::: "+objTech.getString(Constants.JSON_LOGIN_USERNAME));
//						Log.d("JSON", "::::::::: technicians Password ::: "+objTech.getString(Constants.JSON_LOGIN_PASSWORD));
//						Log.d("JSON", "::::::::: technicians UPDATE Password ::: "+objTech.getString(Constants.JSON_LOGIN_PSWRD_UPDATEDATE));

						ContentValues cv = new ContentValues();
						cv.put(Constants.DB_USER_ID,objTech.getString(Constants.JSON_ID));
						cv.put(Constants.DB_USERNAME,objTech.getString(Constants.JSON_LOGIN_USERNAME));
						cv.put(Constants.DB_PASSWORD,objTech.getString(Constants.JSON_LOGIN_PASSWORD));
						if(objTech.getString("UserType").equals("T"))
						{
							cv.put(Constants.DB_ROLE_ID,2);
						}else if(objTech.getString("UserType").equals("C"))
						{
							cv.put(Constants.DB_ROLE_ID,3);
						}
						cv.put(Constants.DB_LAST_LOGIN_TIME,masterData.getString(Constants.JSON_SQL_TIMESTAMP));
						cv.put(Constants.DB_CREATEDBY,objTech.getString(Constants.JSON_ID));
						cv.put(Constants.DB_UPDATEDBY,objTech.getString(Constants.JSON_ID));
						cv.put(Constants.DB_CREATEDON,masterData.getString(Constants.JSON_SQL_TIMESTAMP));
						cv.put(Constants.DB_UPDATEDON,masterData.getString(Constants.JSON_SQL_TIMESTAMP));
						if(response.getString(Constants.METHOD_AUTHENTICATE).equalsIgnoreCase(Constants.LABEL_TRUE)){
							cv.put(Constants.DB_ISSYNC,"1");	
						}else{
							cv.put(Constants.DB_ISSYNC,"0");
						}

						if(userService.insertUser(Constants.TBL_MST_USER,cv) != -1){
							Log.e(Constants.TAG_LOGIN_ACTIVITY,"userInsert");
						}
						
						if(objTech.getString("UserType").equals("T"))
						{
//							System.out.println(" <<<<<<<<< USER TYPE T >>>>>>");
							if(userService.getLockUsers(Constants.TBL_MST_USER_LOCK,objTech.getString(Constants.JSON_LOGIN_USERNAME)).size() > 0)
							{
								ContentValues cvCont = new ContentValues();
								cvCont.put(Constants.USER_LOCK_FIELDS[2],objTech.getString(Constants.JSON_LOGIN_PASSWORD)); //updated password
								cvCont.put(Constants.USER_LOCK_FIELDS[4],objTech.getString(Constants.JSON_LOGIN_PSWRD_UPDATEDATE));
								String where = "username = '"+objTech.getString(Constants.JSON_LOGIN_USERNAME)+"'";
								
								System.out.println(" >>>>>>>>>>>"+userService.updateUserByID(Constants.TBL_MST_USER_LOCK,cvCont,where));
								
								if(userService.updateUserByID(Constants.TBL_MST_USER_LOCK,cvCont,where)!= -1){
									Log.e(Constants.TAG_LOGIN_ACTIVITY,"user LOCK Insert");
								}

							}else{
								ContentValues cvCont = new ContentValues();
								cvCont.put(Constants.USER_LOCK_FIELDS[0],objTech.getString(Constants.JSON_ID));
								cvCont.put(Constants.USER_LOCK_FIELDS[1],objTech.getString(Constants.JSON_LOGIN_USERNAME));
								cvCont.put(Constants.USER_LOCK_FIELDS[2],objTech.getString(Constants.JSON_LOGIN_PASSWORD)); //updated password					
								cvCont.put(Constants.USER_LOCK_FIELDS[3],Constants.LABLE_DEVICE_ID);
								cvCont.put(Constants.USER_LOCK_FIELDS[4],objTech.getString(Constants.JSON_LOGIN_PSWRD_UPDATEDATE));
								cvCont.put(Constants.USER_LOCK_FIELDS[5],Constants.LABLE_DEFAULT_UNLOCK);

								if(userService.insertUser(Constants.TBL_MST_USER_LOCK,cvCont)!= -1){
									Log.e(Constants.TAG_LOGIN_ACTIVITY,"user LOCK Insert");
								}
							}
						}
					  }
//						SharedPrefrenceUtil.setPrefrence(context, Constants.DB_USER_ID, objTech.getString(Constants.JSON_ID));
					}
//					String whr = "username = '"+SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USERNAME, "")+"'";
//					
//					UserMasterVO usVo = userService.getUserInfo(Constants.TBL_MST_USER,Constants.USER_MASTER_FIELDS,whr);
//					SharedPrefrenceUtil.setPrefrence(context, Constants.DB_USER_ID,String.valueOf(usVo.getUserID()));
					
					// netx two lines commentd by Piyush(28-jan)
//					String whr = "username = '"+SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USERNAME, "")+"'";
//					UserMasterVO usVo = userService.getUserInfo(Constants.TBL_MST_USER,Constants.USER_MASTER_FIELDS,whr);
					
					//Change by Piyush(28-jan)
					JSONObject jsonTecnician = technicians.getJSONObject(0);
					if(jsonTecnician != null){
						String userID = jsonTecnician.getString(Constants.JSON_ID);
						SharedPrefrenceUtil.setPrefrence(context, Constants.DB_USER_ID,userID);
					}	
				}	
			}else if(masterData.has("Admin")){
				JSONArray admin = masterData.getJSONArray("Admin");
//				Log.d("JSON", "::::::::: admin ::: "+admin);
//				Log.d("JSON", "::::::::: admin.length() ::: "+admin.length());
				if(admin != null && admin.length() >0){
					for(int i=0;i<admin.length();i++){
						JSONObject obj = admin.getJSONObject(i);
//						Log.d("JSON", "::::::::: admin obj ::: "+obj);
//						Log.d("JSON", "::::::::: admin ID ::: "+obj.getString(Constants.JSON_ID));
//						Log.d("JSON", "::::::::: admin UserName ::: "+obj.getString(Constants.JSON_LOGIN_USERNAME));
//						Log.d("JSON", "::::::::: admin Password ::: "+obj.getString(Constants.JSON_LOGIN_PASSWORD));

						if(obj != null){
							ContentValues cv = new ContentValues();
							cv.put(Constants.DB_USER_ID,obj.getString(Constants.JSON_ID));
							cv.put(Constants.DB_USERNAME,obj.getString(Constants.JSON_LOGIN_USERNAME));
							cv.put(Constants.DB_PASSWORD,obj.getString(Constants.JSON_LOGIN_PASSWORD));
							cv.put(Constants.DB_ROLE_ID,1);
							cv.put(Constants.DB_LAST_LOGIN_TIME,masterData.getString(Constants.JSON_SQL_TIMESTAMP));
							cv.put(Constants.DB_CREATEDBY,obj.getString(Constants.JSON_ID));
							cv.put(Constants.DB_UPDATEDBY,obj.getString(Constants.JSON_ID));
							cv.put(Constants.DB_CREATEDON,masterData.getString(Constants.JSON_SQL_TIMESTAMP));
							cv.put(Constants.DB_UPDATEDON,masterData.getString(Constants.JSON_SQL_TIMESTAMP));
							if(response.getString(Constants.METHOD_AUTHENTICATE).equalsIgnoreCase(Constants.LABEL_TRUE)){
								cv.put(Constants.DB_ISSYNC,"1");	
							}else{
								cv.put(Constants.DB_ISSYNC,"0");
							}

							if(userService.insertUser(Constants.TBL_MST_USER,cv) != -1){
								Log.e(Constants.TAG_LOGIN_ACTIVITY,"userInsert");
							}
						}
					}
				}
			}else if(masterData.has(Constants.JSON_CONTRACTOR)){
				JSONArray contractor = masterData.getJSONArray(Constants.JSON_CONTRACTOR);
//				Log.d("JSON", "::::::::: contractor ::: "+contractor);
//				Log.d("JSON", "::::::::: contractor.length() ::: "+contractor.length());
				if(contractor != null && contractor.length() > 0){
					for(int i=0;i<contractor.length();i++){
						JSONObject obj = contractor.getJSONObject(i);
//						Log.d("JSON", "::::::::: admin obj ::: "+obj);
//						Log.d("JSON", "::::::::: admin ID ::: "+obj.getString(Constants.JSON_ID));
//						Log.d("JSON", "::::::::: admin UserName ::: "+obj.getString(Constants.JSON_LOGIN_USERNAME));
//						Log.d("JSON", "::::::::: admin Password ::: "+obj.getString(Constants.JSON_LOGIN_PASSWORD));

						if(obj != null){
							ContentValues cv = new ContentValues();
							cv.put(Constants.DB_USER_ID,obj.getString(Constants.JSON_ID));
							cv.put(Constants.DB_USERNAME,obj.getString(Constants.JSON_LOGIN_USERNAME));
							cv.put(Constants.DB_PASSWORD,obj.getString(Constants.JSON_LOGIN_PASSWORD));
							cv.put(Constants.DB_ROLE_ID,2);
							cv.put(Constants.DB_LAST_LOGIN_TIME,masterData.getString(Constants.JSON_SQL_TIMESTAMP));
							cv.put(Constants.DB_CREATEDBY,obj.getString(Constants.JSON_ID));
							cv.put(Constants.DB_UPDATEDBY,obj.getString(Constants.JSON_ID));
							cv.put(Constants.DB_CREATEDON,masterData.getString(Constants.JSON_SQL_TIMESTAMP));
							cv.put(Constants.DB_UPDATEDON,masterData.getString(Constants.JSON_SQL_TIMESTAMP));
							if(response.getString(Constants.METHOD_AUTHENTICATE).equalsIgnoreCase(Constants.LABEL_TRUE)){
								cv.put(Constants.DB_ISSYNC,"1");	
							}else{
								cv.put(Constants.DB_ISSYNC,"0");
							}

							if(userService.insertUser(Constants.TBL_MST_USER,cv) != -1){
								Log.e(Constants.TAG_LOGIN_ACTIVITY,"userInsert");
							}
						}
					
					}
				}

			}
			
			JSONObject master = masterData.getJSONObject(Constants.JSON_MASTER);
//			Log.d("JSON", "::::::::: master ::: "+master);
			//			Utility.WriteTextFile("master.txt"," *) "+master);
			if(master != null){
				JSONArray statusCode = master.getJSONArray(Constants.JSON_STATUS_CODE);
//				Log.d("JSON", "::::::::: statusCode ::: "+statusCode);
//				Log.d("JSON", "::::::::: statusCode.length() ::: "+statusCode.length());
				if(statusCode != null && statusCode.length() > 0){
					for(int i=0;i<statusCode.length();i++){
						JSONObject obj = statusCode.getJSONObject(i);
//						Log.d("JSON", "::::::::: statusCode obj ::: "+obj);
//						Log.d("JSON", "::::::::: statusCode ID ::: "+obj.getString(Constants.JSON_ID));
//						Log.d("JSON", "::::::::: statusCode Desc ::: "+obj.getString(Constants.JSON_DESC));
						if(obj != null){
							ContentValues cv = new ContentValues();
							cv.put(Constants.DB_STATUS_ID,i+1);
							cv.put(Constants.DB_STATUS_CODE,obj.getString(Constants.JSON_ID));
							cv.put(Constants.DB_STATUS,obj.getString(Constants.JSON_DESC));

							if(userService.insertUser(Constants.TBL_MST_STATUS,cv) != -1){
								Log.e(Constants.TAG_LOGIN_ACTIVITY,"userInsert");
							}
						}
					}
				}
			

				JSONArray damage = master.getJSONArray(Constants.JSON_DAMAGE);
//				Log.d("JSON", "::::::::: damage ::: "+damage);
//				Log.d("JSON", "::::::::: damage.length() ::: "+damage.length());
				if(damage != null && damage.length() > 0){
					for(int i=0;i<damage.length();i++){
						JSONObject obj = damage.getJSONObject(i);
						if(obj != null){
//							Log.d("JSON", "::::::::: damage obj ::: "+obj);
//							Log.d("JSON", "::::::::: damage Code  ::: "+obj.getString(Constants.JSON_DAMAGE_CODE));
//							Log.d("JSON", "::::::::: damage ObjectID ::: "+obj.getString(Constants.JSON_OBJECT_ID));
//							Log.d("JSON", "::::::::: damage FieldNameGujarati  ::: "+obj.getString(Constants.JSON_FIELD_NAME_GUJ));
//							Log.d("JSON", "::::::::: damage FieldName ::: "+obj.getString(Constants.JSON_FIELD_NAME));
//							Log.d("JSON", "::::::::: damage DamageID  ::: "+obj.getString(Constants.JSON_DAMAGE_ID));

							ContentValues cv = new ContentValues();
							cv.put(Constants.DB_DAMAGE_ID,obj.getString(Constants.JSON_DAMAGE_ID));
							cv.put(Constants.DB_OBJECT_ID,obj.getString(Constants.JSON_OBJECT_ID));
							cv.put(Constants.DB_DAMAGE_CODE,obj.getString(Constants.JSON_DAMAGE_CODE));
							cv.put(Constants.DB_FIELD_NAME_GUJ,obj.getString(Constants.JSON_FIELD_NAME_GUJ));
							cv.put(Constants.DB_FIELDNAME,obj.getString(Constants.JSON_FIELD_NAME));

							if(userService.insertUser(Constants.TBL_MST_DAMAGES,cv) != -1){
								Log.e(Constants.TAG_LOGIN_ACTIVITY,"userInsert");
							}
						}

					}
				}


				JSONObject obje = master.getJSONObject(Constants.JSON_OBJ);
//				Log.d("JSON", "::::::::: obje ::: "+obje);
				if(obje != null){
					JSONArray lstLkg = obje.getJSONArray(Constants.JSON_LIST_LKG);
//					Log.d("JSON", "::::::::: lstLkg ::: "+lstLkg);
//					Log.d("JSON", "::::::::: lstLkg.length() ::: "+lstLkg.length());
					if(lstLkg != null && lstLkg.length() > 0){
						for(int i = 0; i<lstLkg.length(); i++){
							JSONObject obj = lstLkg.getJSONObject(i);
//							Log.d("JSON", "::::::::: lstLkg obj ::: "+obj);
//							Log.d("JSON", "::::::::: lstLkg ID  ::: "+obj.getString(Constants.JSON_OBJECT_ID));
//							Log.d("JSON", "::::::::: lstLkg text ::: "+obj.getString(Constants.JSON_FIELD_NAME));
//							Log.d("JSON", "::::::::: lstLkg FieldNameGujarati  ::: "+obj.getString(Constants.JSON_FIELD_NAME_GUJ));
//							Log.d("JSON", "::::::::: lstLkg Codegroup  ::: "+obj.getString(Constants.JSON_CODE_GROUP));
//							Log.d("JSON", "::::::::: lstLkg Objectcode ::: "+obj.getString(Constants.JSON_OBJECT_CODE));

//							Log.d("JSON", "::::::::: lstLkg child obj ::: "+obj.getString(Constants.JSON_OBJECT_LIST_CHILD_OBJ));
							if(obj != null ){
								ContentValues cv = new ContentValues();
								cv.put(Constants.DB_LEAKAGE_ID,obj.getString(Constants.JSON_OBJECT_ID));
								cv.put(Constants.DB_TEXT,obj.getString(Constants.JSON_FIELD_NAME));
								cv.put(Constants.DB_FIELD_NAME_GUJ,obj.getString(Constants.JSON_FIELD_NAME_GUJ));
								cv.put(Constants.DB_CODE_GROUP,obj.getString(Constants.JSON_CODE_GROUP));
								cv.put(Constants.DB_OBJECT_CODE,obj.getString(Constants.JSON_OBJECT_CODE));

								if(userService.insertUser(Constants.TBL_MST_LEAKAGE,cv) != -1){
								//	Log.e(Constants.TAG_LOGIN_ACTIVITY,"userInsert");
								}

								if(!obj.getString(Constants.JSON_OBJECT_LIST_CHILD_OBJ).equals("null")){
									JSONArray arrayGI = obj.getJSONArray(Constants.JSON_OBJECT_LIST_CHILD_OBJ);

									for(int m=0; m<arrayGI.length(); m++){
										JSONObject objGI = arrayGI.getJSONObject(m);
										ContentValues cvGI = new ContentValues();
										cvGI.put(Constants.DB_MST_GIFITTING_ID,objGI.getString(Constants.JSON_OBJECT_ID));
										cvGI.put(Constants.DB_TEXT,objGI.getString(Constants.JSON_FIELD_NAME));
										cvGI.put(Constants.DB_FIELD_NAME_GUJ,objGI.getString(Constants.JSON_FIELD_NAME_GUJ));
										cvGI.put(Constants.DB_CODE_GROUP,objGI.getString(Constants.JSON_CODE_GROUP));
										cvGI.put(Constants.DB_OBJECT_CODE,objGI.getString(Constants.JSON_OBJECT_CODE));
										cvGI.put(Constants.DB_PARENT_CODE_ID,objGI.getString(Constants.JSON_OBJECT_PARENT_CODE_ID));

										if(userService.insertUser(Constants.TBL_MST_GIFITTING,cvGI) != -1){
									//		Log.e(Constants.TAG_LOGIN_ACTIVITY,"userInsert");
										}
									}
								}
								
							}

						}
					}
					
					JSONArray lstNC = obje.getJSONArray(Constants.JSON_LIST_NC);
//					Log.d("JSON", "::::::::: lstNC ::: "+lstNC);
//					Log.d("JSON", "::::::::: lstNC.length() ::: "+lstNC.length());
					
					if(lstNC != null && lstNC.length() > 0){
						int otherPos = 0;
						
						for(int i = 0; i<lstNC.length(); i++){
							JSONObject obj = lstNC.getJSONObject(i);
//							Log.d("JSON", "::::::::: lstNC obj ::: "+obj);
//							Log.d("JSON", "::::::::: lstNC ID  ::: "+obj.getString(Constants.JSON_OBJECT_ID));
//							Log.d("JSON", "::::::::: lstNC text ::: "+obj.getString(Constants.JSON_FIELD_NAME));
//							Log.d("JSON", "::::::::: lstNC FieldNameGujarati  ::: "+obj.getString(Constants.JSON_FIELD_NAME_GUJ));
//							Log.d("JSON", "::::::::: lstNC Codegroup  ::: "+obj.getString(Constants.JSON_CODE_GROUP));
//							Log.d("JSON", "::::::::: lstNC Objectcode ::: "+obj.getString(Constants.JSON_OBJECT_CODE));
							if(obj != null){
								if(!obj.getString(Constants.JSON_FIELD_NAME).equalsIgnoreCase("OTHERS"))
								{
									ContentValues cv = new ContentValues();
									cv.put(Constants.DB_CONFORMANCE_ID,obj.getString(Constants.JSON_OBJECT_ID));
									cv.put(Constants.DB_REASON,obj.getString(Constants.JSON_FIELD_NAME));
									cv.put(Constants.DB_CODE_GROUP,obj.getString(Constants.JSON_CODE_GROUP));
									cv.put(Constants.DB_OBJECT_CODE,obj.getString(Constants.JSON_OBJECT_CODE));
									cv.put(Constants.DB_FIELD_NAME_GUJ,obj.getString(Constants.JSON_FIELD_NAME_GUJ));

									if(userService.insertUser(Constants.TBL_MST_CONFORMANCE,cv) != -1){
										Log.e(Constants.TAG_LOGIN_ACTIVITY,"userInsert");
									}
								}else
								{
									otherPos = i;
								}
							}
					
						}
						if(lstNC != null){ 
							// to sort and set other field in last (check conformance screen). so it is not in above for loop
							JSONObject object = lstNC.getJSONObject(otherPos);
							if(object != null){
								ContentValues cvNC = new ContentValues();
								cvNC.put(Constants.DB_CONFORMANCE_ID,lstNC.getJSONObject(otherPos).getString(Constants.JSON_OBJECT_ID));
								cvNC.put(Constants.DB_REASON,lstNC.getJSONObject(otherPos).getString(Constants.JSON_FIELD_NAME));
								cvNC.put(Constants.DB_CODE_GROUP,lstNC.getJSONObject(otherPos).getString(Constants.JSON_CODE_GROUP));
								cvNC.put(Constants.DB_OBJECT_CODE,lstNC.getJSONObject(otherPos).getString(Constants.JSON_OBJECT_CODE));
								cvNC.put(Constants.DB_FIELD_NAME_GUJ,lstNC.getJSONObject(otherPos).getString(Constants.JSON_FIELD_NAME_GUJ));

								if(userService.insertUser(Constants.TBL_MST_CONFORMANCE,cvNC) != -1){
									Log.e(Constants.TAG_LOGIN_ACTIVITY,"Other Insert for NC");
								}
							}
							
						}
					}
				}			

				JSONArray nonConformance = master.getJSONArray(Constants.JSON_NON_CONFORMANCE);
//				Log.d("JSON", "::::::::: nonConformance ::: "+nonConformance);
//				Log.d("JSON", "::::::::: nonConformance.length() ::: "+nonConformance.length());
				if(nonConformance != null && nonConformance.length() > 0){
					for(int i=0;i<nonConformance.length();i++){
						JSONObject obj = nonConformance.getJSONObject(i);
						Log.d("JSON", "::::::::: nonConformance obj ::: "+obj);
						Log.d("JSON", "::::::::: nonConformance ID ::: "+obj.getString(Constants.JSON_ID));
						Log.d("JSON", "::::::::: nonConformance Reason ::: "+obj.getString(Constants.JSON_REASON));

						/*ContentValues cv = new ContentValues();
						cv.put(Constants.DB_CONFORMANCE_ID,obj.getString(Constants.JSON_ID));
						cv.put(Constants.DB_REASON,obj.getString(Constants.JSON_REASON));

						if(userService.insertUser(Constants.TBL_MST_CONFORMANCE,cv) != -1){
							Log.e(Constants.TAG_LOGIN_ACTIVITY,"userInsert");
						}*/
					}

				}
				
				JSONArray forms = master.getJSONArray(Constants.JSON_FORMS);
//				Log.d("JSON", "::::::::: forms ::: "+forms);
//				Log.d("JSON", "::::::::: forms.length() ::: "+forms.length());
				if(forms != null && forms.length() > 0){
					for(int i =0;i<forms.length();i++){
						JSONObject form = forms.getJSONObject(i);	
//						Log.d("JSON", "::::::::: Form name ::: "+form.getString(Constants.JSON_FORM));
						if(form != null){
							JSONArray validation = form.getJSONArray(Constants.JSON_VALIDATIONS);
							if(validation != null){
//								Log.d("JSON", "::::::::: validation ::: "+validation);
//								Log.d("JSON", "::::::::: validation.length() ::: "+validation.length());
								for(int j=0;j<validation.length();j++){
									JSONObject obj = validation.getJSONObject(j);
//									Log.d("JSON", "::::::::: validation obj ::: "+obj);
//									Log.d("JSON", "::::::::: validation ID ::: "+obj.getString(Constants.JSON_VALIDATION_RULE_ID));
//									Log.d("JSON", "::::::::: validation MaxValue ::: "+obj.getString(Constants.JSON_MAX_VALUE));
//									Log.d("JSON", "::::::::: validation MinValue ::: "+obj.getString(Constants.JSON_MIN_VALUE));
//									Log.d("JSON", "::::::::: validation FieldName ::: "+obj.getString(Constants.JSON_FIELD_NAME));

									ContentValues cv = new ContentValues();
									cv.put(Constants.DB_VALIDATION_RULE_ID,obj.getString(Constants.JSON_VALIDATION_RULE_ID));
									cv.put(Constants.DB_MAXVALUE,obj.getString(Constants.JSON_MAX_VALUE));
									cv.put(Constants.DB_MINVALUE,obj.getString(Constants.JSON_MIN_VALUE));
									cv.put(Constants.DB_FIELDNAME,obj.getString(Constants.JSON_FIELD_NAME));
									cv.put(Constants.DB_FORMNAME,form.getString(Constants.JSON_FORM));

									if(userService.insertUser(Constants.TBL_MST_VALIDATION_RULE,cv) != -1){
										Log.e(Constants.TAG_LOGIN_ACTIVITY,"userInsert");
									}
								}
							}
						}
						
					}
				}


				//Add constarctor to CustomParameter
				//JSONObject contractor = master.getJSONObject(Constants.JSON_CONTRACTOR);
//				Log.d("JSON","::::::::: Contractor ::::"+master.getString(Constants.JSON_CONTRACTOR));
				if(master != null && !master.getString(Constants.JSON_CONTRACTOR).equals("")){
					ContentValues cvCont = new ContentValues();
					cvCont.put(Constants.DB_KEY,Constants.JSON_CONTRACTOR);
					cvCont.put(Constants.DB_VALUE,master.getString(Constants.JSON_CONTRACTOR));
					cvCont.put(Constants.DB_CREATEDBY,Integer.parseInt(SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USER_ID, "0")));
					cvCont.put(Constants.DB_UPDATEDBY,Integer.parseInt(SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USER_ID, "0")));
					cvCont.put(Constants.DB_CREATEDON,masterData.getString(Constants.JSON_SQL_TIMESTAMP));
					cvCont.put(Constants.DB_UPDATEDON,masterData.getString(Constants.JSON_SQL_TIMESTAMP));

					if(userService.insertUser(Constants.TBL_MST_CUSTOME_PARAMETER,cvCont) != -1){
						Log.e(Constants.TAG_LOGIN_ACTIVITY,"Contractor add");
					}
				}

				JSONArray geyserType = master.getJSONArray(Constants.JSON_GEYSER_TYPE);
//				Log.d("JSON", "::::::::: geyserType ::: "+geyserType);
//				Log.d("JSON", "::::::::: geyserType.length() ::: "+geyserType.length());
				if(geyserType != null && geyserType.length() > 0){
					for(int i=0;i<geyserType.length();i++){
						JSONObject obj = geyserType.getJSONObject(i);
						if(obj != null){
							Log.d("JSON", "::::::::: geyserType obj ::: "+obj);
//							Log.d("JSON", "::::::::: geyserType ID ::: "+obj.getString(Constants.JSON_ID));
//							Log.d("JSON", "::::::::: geyserType Desc ::: "+obj.getString(Constants.JSON_DESC));

							ContentValues cv = new ContentValues();
							cv.put(Constants.DB_GEYSER_TYPE_ID,obj.getString(Constants.JSON_ID));
							cv.put(Constants.DB_TEXT,obj.getString(Constants.JSON_DESC));

							if(userService.insertUser(Constants.TBL_MST_GEYSER_TYPE,cv) != -1){
								Log.e(Constants.TAG_LOGIN_ACTIVITY,"userInsert");
							}
						}
//				
					}
				}


				//Add Team to CustomParameter
				//	JSONObject team = master.getJSONObject(Constants.JSON_TEAM);
//				Log.d("JSON","::::::::: Team ::::"+master.getString(Constants.JSON_TEAM));
				if(master != null && !master.getString(Constants.JSON_TEAM).equals(""))
				{
					ContentValues cvTeam = new ContentValues();
					cvTeam.put(Constants.DB_KEY,Constants.JSON_TEAM);
					cvTeam.put(Constants.DB_VALUE,master.getString(Constants.JSON_TEAM));
					cvTeam.put(Constants.DB_CREATEDBY,Integer.parseInt(SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USER_ID, "0")));
					cvTeam.put(Constants.DB_UPDATEDBY,Integer.parseInt(SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USER_ID, "0")));
					cvTeam.put(Constants.DB_CREATEDON,masterData.getString(Constants.JSON_SQL_TIMESTAMP));
					cvTeam.put(Constants.DB_UPDATEDON,masterData.getString(Constants.JSON_SQL_TIMESTAMP));

					if(userService.insertUser(Constants.TBL_MST_CUSTOME_PARAMETER,cvTeam) != -1){
						Log.e(Constants.TAG_LOGIN_ACTIVITY,"TEam add");
					}
				}


				JSONArray faq = master.getJSONArray(Constants.JSON_FAQ);
//				Log.d("JSON", "::::::::: faq ::: "+faq);
//				Log.d("JSON", "::::::::: faq.length() ::: "+faq.length());
				if(faq != null && faq.length() > 0){
					for(int i=0;i<faq.length();i++){
						JSONObject obj = faq.getJSONObject(i);
						if(obj != null){
//							Log.d("JSON", "::::::::: faq obj ::: "+obj);
//							Log.d("JSON", "::::::::: faq ID ::: "+obj.getString(Constants.JSON_ID));
//							Log.d("JSON", "::::::::: faq Answer ::: "+obj.getString(Constants.JSON_ANSWER));
//							Log.d("JSON", "::::::::: faq Question ::: "+obj.getString(Constants.JSON_QUESTION));

							ContentValues cv = new ContentValues();
							cv.put(Constants.DB_FAQ_ID,obj.getString(Constants.JSON_ID));
							cv.put(Constants.DB_QUESTION,obj.getString(Constants.JSON_QUESTION));
							cv.put(Constants.DB_ANSWER,obj.getString(Constants.JSON_ANSWER));

							if(userService.insertUser(Constants.TBL_MST_FAQ,cv) != -1){
								Log.e(Constants.TAG_LOGIN_ACTIVITY,"userInsert");
							}
						}

					}
				}


				JSONArray cause = master.getJSONArray(Constants.JSON_CAUSE);
				if(cause != null && cause.length() > 0){
//					Log.d("JSON", "::::::::: cause ::: "+cause);
//					Log.d("JSON", "::::::::: cause.length() ::: "+cause.length());
					for(int i=0;i<cause.length();i++){
						JSONObject obj = cause.getJSONObject(i);
						if(obj != null){
//							Log.d("JSON", "::::::::: cause obj ::: "+obj);
//							Log.d("JSON", "::::::::: cause ID  ::: "+obj.getString(Constants.JSON_CAUSE_ID));
//							Log.d("JSON", "::::::::: cause ObjectID ::: "+obj.getString(Constants.JSON_OBJECT_ID));
//							Log.d("JSON", "::::::::: cause code  ::: "+obj.getString(Constants.JSON_CAUSE_CODE));
//							Log.d("JSON", "::::::::: cause FieldName ::: "+obj.getString(Constants.JSON_FIELD_NAME));
//							Log.d("JSON", "::::::::: cause FieldNameGujarati  ::: "+obj.getString(Constants.JSON_FIELD_NAME_GUJ));

							ContentValues cv = new ContentValues();
							cv.put(Constants.DB_CAUSE_ID,obj.getString(Constants.JSON_CAUSE_ID));
							cv.put(Constants.DB_OBJECT_ID,obj.getString(Constants.JSON_OBJECT_ID));
							cv.put(Constants.DB_CAUSE_CODE,obj.getString(Constants.JSON_CAUSE_CODE));
							cv.put(Constants.DB_FIELDNAME,obj.getString(Constants.JSON_FIELD_NAME));
							cv.put(Constants.DB_FIELD_NAME_GUJ,obj.getString(Constants.JSON_FIELD_NAME_GUJ));

							if(userService.insertUser(Constants.TBL_MST_CAUSES,cv) != -1){
								Log.e(Constants.TAG_LOGIN_ACTIVITY,"userInsert");
							}
						}

					}
				}


				JSONArray make = master.getJSONArray(Constants.JSON_MAKE);
				if(make != null && make.length() > 0){
//					Log.d("JSON", "::::::::: make ::: "+make);
//					Log.d("JSON", "::::::::: make.length() ::: "+make.length());
					for(int i=0;i<make.length();i++){
						JSONObject obj = make.getJSONObject(i);
						if(obj != null){
//							Log.d("JSON", "::::::::: make obj ::: "+obj);
//							Log.d("JSON", "::::::::: make ID ::: "+obj.getString(Constants.JSON_ID));
//							Log.d("JSON", "::::::::: make Desc ::: "+obj.getString(Constants.JSON_DESC));

							ContentValues cv = new ContentValues();
							cv.put(Constants.DB_MAKE_ID,obj.getString(Constants.JSON_ID));
							cv.put(Constants.DB_MAKE_TEXT,obj.getString(Constants.JSON_DESC));

							if(userService.insertUser(Constants.TBL_MST_MAKE,cv) != -1){
//								Log.e(Constants.TAG_LOGIN_ACTIVITY,"userInsert");
							}
						}

					}
				}
			}



//			Log.d("JSON", "::::::::: masterData SqlTimeStamp ::: "+masterData.getString(Constants.JSON_SQL_TIMESTAMP));
//			Log.d("JSON", "::::::::: masterData CleanUpPeriod ::: "+masterData.getString(Constants.JSON_CLEANUP_PERIOD));
//			Log.d("JSON", "::::::::: responce Authentication ::: "+response.getString(Constants.METHOD_AUTHENTICATE));

			ContentValues cv = new ContentValues();
			cv.put(Constants.DB_LAST_CLEANUP_DATE,masterData.getString(Constants.JSON_SQL_TIMESTAMP));
			cv.put(Constants.DB_CLEANUP_PEROID,masterData.getString(Constants.JSON_CLEANUP_PERIOD));
			cv.put(Constants.DB_USER_ID,objTech.getString(Constants.JSON_ID));

			if(userService.insertUser(Constants.TBL_DTL_CLEANUP,cv) != -1){
//				Log.e(Constants.TAG_LOGIN_ACTIVITY,"userInsert");
			}
			
//			Log.d("JSON", "::::::::: masterData Auto Submission Interval::: "+masterData.getString(Constants.JSON_AUTO_SUBMISSION_INTERVAL));
			
			if(masterData.getInt(Constants.JSON_AUTO_SUBMISSION_INTERVAL) > 0)
			{
				ContentValues cvTeam = new ContentValues();
				cvTeam.put(Constants.DB_KEY,Constants.JSON_AUTO_SUBMISSION_INTERVAL);
				cvTeam.put(Constants.DB_VALUE,masterData.getInt(Constants.JSON_AUTO_SUBMISSION_INTERVAL));
				cvTeam.put(Constants.DB_CREATEDBY,Integer.parseInt(SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USER_ID, "0")));
				cvTeam.put(Constants.DB_UPDATEDBY,Integer.parseInt(SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USER_ID, "0")));
				cvTeam.put(Constants.DB_CREATEDON,masterData.getString(Constants.JSON_SQL_TIMESTAMP));
				cvTeam.put(Constants.DB_UPDATEDON,masterData.getString(Constants.JSON_SQL_TIMESTAMP));
			
				
				
				ContentValues contentvalue = new ContentValues();
				contentvalue.put(Constants.DB_KEY,Constants.JSON_BATCH_SIZE);
				contentvalue.put(Constants.DB_VALUE, masterData.getInt(Constants.JSON_BATCH_SIZE));
				contentvalue.put(Constants.DB_CREATEDBY,Integer.parseInt(SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USER_ID, "0")));
				contentvalue.put(Constants.DB_UPDATEDBY,Integer.parseInt(SharedPrefrenceUtil.getPrefrence(context, Constants.DB_USER_ID, "0")));
				contentvalue.put(Constants.DB_CREATEDON,masterData.getString(Constants.JSON_SQL_TIMESTAMP));
				contentvalue.put(Constants.DB_UPDATEDON,masterData.getString(Constants.JSON_SQL_TIMESTAMP));
			

				if(userService.insertUser(Constants.TBL_MST_CUSTOME_PARAMETER,cvTeam) != -1){
					Log.e(Constants.TAG_LOGIN_ACTIVITY,"Auto SUbmission Interval add");
					//Toast.makeText(context, "Auto SUbmission Interval added", Toast.LENGTH_LONG).show();
				}
				
				if(userService.insertUser(Constants.TBL_MST_CUSTOME_PARAMETER,contentvalue) != -1){
					Log.e(Constants.TAG_LOGIN_ACTIVITY,"Batch Size add");
					//Toast.makeText(context, "Auto SUbmission Interval added", Toast.LENGTH_LONG).show();
				}
			}
			
	  }
				
	 }catch(JSONException jse){
		  jse.printStackTrace();
		  Log.d(Constants.DEBUG,":AsyncGetAllMaster:"+ Utility.convertExceptionToString(jse));
/*			StackTraceElement stackTrace = jse.getStackTrace()[0];
			String method = stackTrace.getMethodName();*/
			response = new JSONObject();
			try{
				response.put(Constants.JSON_FAILED,Constants.JSON_FAILED);
			}catch(Exception e){
				e.printStackTrace();
			}
	  }catch(ConnectException ce){
		  ce.printStackTrace();
		  Log.d(Constants.DEBUG,":AsyncGetAllMaster:"+ Utility.convertExceptionToString(ce));
		  StackTraceElement stackTrace = ce.getStackTrace()[0];
		  String method = stackTrace.getMethodName();
		  response = new JSONObject();
			try{
				if(method != null && method.equalsIgnoreCase(Constants.NETWORK_EXCEPTION_METHOD)){
					response.put(Constants.NETWORK_EXCEPTION_METHOD,Constants.NETWORK_EXCEPTION_METHOD);
				}else if(method != null && method.equalsIgnoreCase(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD)){
					response.put(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD,Constants.CONNECTION_REFUSED_EXCEPTION_METHOD);
				}else{
					response.put(Constants.JSON_FAILED,Constants.JSON_FAILED);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
	  }catch(SocketException se){
		  se.printStackTrace();
		  Log.d(Constants.DEBUG,":AsyncGetAllMaster:"+ Utility.convertExceptionToString(se));
		  response = new JSONObject();
			try{
				response.put(Constants.SOCKET_EXCEPTION,Constants.SOCKET_EXCEPTION);
			}catch(Exception e){
				e.printStackTrace();
			}
	  }catch(IOException ioe){
		  ioe.printStackTrace();
		  Log.d(Constants.DEBUG,":AsyncGetAllMaster:"+ Utility.convertExceptionToString(ioe));
		  response = new JSONObject();
			try{
				response.put(Constants.IOEXCEPTION,Constants.IOEXCEPTION);
			}catch(Exception e){
				e.printStackTrace();
				Log.d(Constants.DEBUG,":AsyncBatchSubmit:"+ Utility.convertExceptionToString(e));
			}
	  }catch(XmlPullParserException xpe){
		  xpe.printStackTrace();
		  Log.d(Constants.DEBUG,":AsyncGetAllMaster:"+ Utility.convertExceptionToString(xpe));
		  response = new JSONObject();
			try{
				response.put(Constants.XMLPULLPARSEREXCEPTION,Constants.XMLPULLPARSEREXCEPTION);
			}catch(Exception e){
				e.printStackTrace();
			}
	  }catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":AsyncGetAllMaster:"+ Utility.convertExceptionToString(e));
			response = new JSONObject();
			try{
				response.put(Constants.EXCEPTION,Constants.EXCEPTION);
			}catch(Exception ex){
				ex.printStackTrace();
				Log.d(Constants.DEBUG,":AsyncBatchSubmit:"+ Utility.convertExceptionToString(ex));
			}
	  }

		return response;
	}
	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);
		prDialog.dismiss();
		responseListener.onSuccess(result);
	}

	public interface OnResponseListener {
		public void onSuccess(JSONObject responce);
		public void onFailure(String message);
	}
}
