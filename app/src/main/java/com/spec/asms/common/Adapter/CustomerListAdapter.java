package com.spec.asms.common.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.Global;
import com.spec.asms.common.utils.SharedPrefrenceUtil;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.CustomerMasterService;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.view.fragment.MaintanenceForm;
import com.spec.asms.view.fragment.OtherChecksFragment;
import com.spec.asms.view.fragment.TestingFragment;
import com.spec.asms.vo.ClampingVO;
import com.spec.asms.vo.ConformanceDetailVO;
import com.spec.asms.vo.CustomerFeedbackVO;
import com.spec.asms.vo.CustomerMasterVO;
import com.spec.asms.vo.GIFittingVO;
import com.spec.asms.vo.KitchenSurakshaTubeVO;
import com.spec.asms.vo.LeakageVO;
import com.spec.asms.vo.MakeGeyserVO;
import com.spec.asms.vo.OtherChecksVO;
import com.spec.asms.vo.OtherKitchenSurakshaTubeVO;
import com.spec.asms.vo.PaintingVO;
import com.spec.asms.vo.RccVO;
import com.spec.asms.vo.TestingVO;
/**
 * An Adapter class to display Customer List.
 * @author jenisha
 *
 */
public class CustomerListAdapter extends ArrayAdapter<CustomerMasterVO>{

	private List<CustomerMasterVO> customerList = new ArrayList<CustomerMasterVO>();
	private Context context;
	private int resource; 
	private Activity activity;
    private List<CustomerMasterVO> custList;
    public UserMasterService masterService;
    public String user;
    private String status;
	private CustomerMasterService custService;
	public CustomerListAdapter(Context context, int resource, List<CustomerMasterVO> objects,Activity actvity) {
		super(context, resource, objects);
		this.customerList = objects;
		this.resource = resource;
		this.context = context;
		this.activity = actvity;
		masterService = new UserMasterService(context);
		this.custService = new CustomerMasterService(context);
		user = SharedPrefrenceUtil.getPrefrence(context,Constants.DB_USERNAME, "tech");

	}
	@Override
	public int getCount() {
		return customerList.size();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		//if(convertView == null){
			LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(resource, parent,false);
		

		final CustomerMasterVO customer = customerList.get(position);
		if(customer != null){
			final TextView customer_id_txt = (TextView)convertView.findViewById(R.id.lblCustomerId);
			final TextView customer_name_txt = (TextView)convertView.findViewById(R.id.lblCustomerName);
			TextView customer_add_txt = (TextView)convertView.findViewById(R.id.lblCustomerAdd);
			ImageView imgCustomerWalkseqUp = (ImageView)convertView.findViewById(R.id.imgCustomerWalkseqUp);
			ImageView imgCustomerWalkseqDown = (ImageView)convertView.findViewById(R.id.imgCustomerWalkseqDown);
			TextView txtCallNumber = (TextView) convertView.findViewById(R.id.lblContactNumber);
			ImageView imgCallIcon = (ImageView) convertView.findViewById(R.id.imgCallIcon);
			ImageView imgStatus = (ImageView) convertView.findViewById(R.id.imgStatus);
			RelativeLayout rltCall = (RelativeLayout) convertView.findViewById(R.id.rltCall);
			

			if(customer.getStatusId() != masterService.getStatusId("OP") || user.equals("admin") ){
				imgCustomerWalkseqDown.setEnabled(false);
				imgCustomerWalkseqUp.setEnabled(false);
			}
			if(position == 0){
				imgCustomerWalkseqUp.setVisibility(View.INVISIBLE);
				imgCustomerWalkseqDown.setVisibility(View.VISIBLE);
			}
			if(position == customerList.size()-1){
				imgCustomerWalkseqDown.setVisibility(View.INVISIBLE);
				imgCustomerWalkseqUp.setVisibility(View.VISIBLE);
			}
			if((position == 0) && (position == customerList.size()-1)){
				imgCustomerWalkseqUp.setVisibility(View.INVISIBLE);
				imgCustomerWalkseqDown.setVisibility(View.INVISIBLE);
			}
			customer_id_txt.setText(customer.getCustomerID()+"");
			customer_name_txt.setText(customer.getCustomername());
			customer_add_txt.setText(customer.getCustomeradd());
/*			ImageSpan is = new ImageSpan(context,R.drawable.icn_call);
	
			SpannableStringBuilder builder = new SpannableStringBuilder();
		    builder.append(customer.getCustomeradd()).append(" ");
		    builder.setSpan(new ImageSpan(activity, R.drawable.icn_call),
		            builder.length() - 1, builder.length(), 0);
		    builder.append(customer.getPhoneNumber());
			customer_add_txt.setText(builder);*/
	
			 final String contactNumber = customer.getPhoneNumber();
			if(contactNumber != null && !contactNumber.equals(Constants.BLANK)){
				imgCallIcon.setVisibility(View.VISIBLE);
				txtCallNumber.setText(contactNumber);
			}else{
				imgCallIcon.setVisibility(View.GONE);
				txtCallNumber.setText(Constants.BLANK);
				
			}
			status = customer.getCustomerStatus();
			if(status != null && status.equalsIgnoreCase(Constants.STATUS_OPERATIONAL)){
				imgStatus.setImageDrawable(activity.getResources().getDrawable(R.drawable.icon_operational));
			}else if(status != null && status.equalsIgnoreCase(Constants.STATUS_TEMPORARY_DISCONNECTED)){
				imgStatus.setImageDrawable(activity.getResources().getDrawable(R.drawable.icon_temp_disconnect));
			}else if(status != null && status.equalsIgnoreCase(Constants.STATUS_FORCE_DISCONNECTED)){
				imgStatus.setImageDrawable(activity.getResources().getDrawable(R.drawable.icon_force_disconnect ));
			}
			
			
			customer_name_txt.setTextColor(context.getResources().getColor(R.color.form_blue));
			customer_name_txt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL), Typeface.NORMAL);

			customer_id_txt.setTextColor(context.getResources().getColor(R.color.form_blue));
			customer_id_txt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL), Typeface.NORMAL);
			customer_id_txt.setOnClickListener(new OnClickListener() {
				CustomerMasterVO customer = customerList.get(position);
				public void onClick(View v) {
					
					TestingVO.cleanTestingVO();
					GIFittingVO.cleanGiFitting();
					ClampingVO.cleanClampingVO();
					PaintingVO.cleanPaintingVO();
					RccVO.cleanRCCVO();
					LeakageVO.cleanLeakage();
					MakeGeyserVO.cleanMakeGeyser();
					KitchenSurakshaTubeVO.cleanKitchenSurakshaTubeVO();
					OtherKitchenSurakshaTubeVO.cleanOtherKitchenSurakshaTubeVO();
					OtherChecksVO.cleanOtherChecks();
					ConformanceDetailVO.cleanConformanceVO();
					CustomerFeedbackVO.cleanomerFeedbackCustVO();
					
					customer_id_txt.setTextColor(context.getResources().getColor(R.color.form_green));
					customer_id_txt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD), Typeface.NORMAL);
					
					System.out.println("Status Id :#######"+customer.getStatusId());

					UserMasterService userService = new UserMasterService(context);
					TestingFragment.testingStatusCode = userService.getStatusCode(customer.getStatusId());
					Global.expiryDate = customer.getExpiryDate();
					/*ArrayList<CustomerMasterVO> getAllCustLists = new ArrayList<CustomerMasterVO>();
					try {
						String whereCustbyUser = "NOT(CustomerStatus = 'OP' OR CustomerStatus = 'HCL1')";
						getAllCustLists = (ArrayList<CustomerMasterVO>) custService.getSelectedCustomers(Constants.TBL_MST_CUSTOMER,Constants.CUSTOMER_MASTER_FIELDS,whereCustbyUser);
					} catch (Exception e) {
						e.printStackTrace();
						Log.d(Constants.DEBUG,":AsyncFindAllCustomer:"+ Utility.convertExceptionToString(e));
					}
					if(getAllCustLists.size() <=3){*/
						MaintanenceForm newFragment = MaintanenceForm.newInstance(customer.getCustomerID(),customer.getCustomername(),customer.getStatusId(),customer.getMaintainanceOrderID(),customer.getPhoneNumber(), customer.getCustomerStatus());
						FragmentManager fragmentManager = activity.getFragmentManager();
						FragmentTransaction ft = fragmentManager.beginTransaction();
						ft.replace(R.id.mainFrg, newFragment);
						ft.commit();
					/*}else {
						AlertDialog.Builder builder = new AlertDialog.Builder(context);
						builder.setCancelable(true);
						builder.setTitle("Alert");
						builder.setMessage("You have more than 50 data pending to upload please first sync with server.");
						builder.setPositiveButton(Constants.LABLE_OK,new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();

							}
						});
						AlertDialog alert = builder.create();
						alert.show();
					}*/

					

				}
			});
			customer_name_txt.setOnClickListener(new OnClickListener() {
				CustomerMasterVO customer = customerList.get(position);
				public void onClick(View v) {
					
					TestingVO.cleanTestingVO();
					GIFittingVO.cleanGiFitting();
					ClampingVO.cleanClampingVO();
					PaintingVO.cleanPaintingVO();
					RccVO.cleanRCCVO();
					LeakageVO.cleanLeakage();
					MakeGeyserVO.cleanMakeGeyser();
					KitchenSurakshaTubeVO.cleanKitchenSurakshaTubeVO();
					OtherKitchenSurakshaTubeVO.cleanOtherKitchenSurakshaTubeVO();
					OtherChecksVO.cleanOtherChecks();
					ConformanceDetailVO.cleanConformanceVO();
					CustomerFeedbackVO.cleanomerFeedbackCustVO();
					
					customer_name_txt.setTextColor(context.getResources().getColor(R.color.form_green));
					customer_name_txt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD), Typeface.NORMAL);
					
					System.out.println("Status Id :#######"+customer.getStatusId());
					
					UserMasterService userService = new UserMasterService(context);
					TestingFragment.testingStatusCode = userService.getStatusCode(customer.getStatusId());
					Global.expiryDate = customer.getExpiryDate();

					/*ArrayList<CustomerMasterVO> getAllCustLists = new ArrayList<CustomerMasterVO>();
					try {
						String whereCustbyUser = "NOT(CustomerStatus = 'OP' OR CustomerStatus = 'HCL1')";
						getAllCustLists = (ArrayList<CustomerMasterVO>) custService.getSelectedCustomers(Constants.TBL_MST_CUSTOMER,Constants.CUSTOMER_MASTER_FIELDS,whereCustbyUser);
					} catch (Exception e) {
						e.printStackTrace();
						Log.d(Constants.DEBUG,":AsyncFindAllCustomer:"+ Utility.convertExceptionToString(e));
					}
					if(getAllCustLists.size() <=3){*/
						MaintanenceForm newFragment = MaintanenceForm.newInstance(customer.getCustomerID(),customer.getCustomername(),customer.getStatusId(),customer.getMaintainanceOrderID(),customer.getPhoneNumber(), customer.getCustomerStatus());
						FragmentManager fragmentManager = activity.getFragmentManager();
						FragmentTransaction ft = fragmentManager.beginTransaction();
						ft.replace(R.id.mainFrg, newFragment);
						ft.commit();
					/*}else {
						AlertDialog.Builder builder = new AlertDialog.Builder(context);
						builder.setCancelable(true);
						builder.setTitle("Alert");
						builder.setMessage("You have more than 50 data pending to upload please first sync with server");
						builder.setPositiveButton(Constants.LABLE_OK,new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();

							}
						});
						AlertDialog alert = builder.create();
						alert.show();
					}*/
				}
			});

			imgCustomerWalkseqUp.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
						int currentIndex=position;
					     custList=new ArrayList<CustomerMasterVO>();
						 custList=Utility.changeIndex(customerList,Constants.WALK_SEQ_UP, currentIndex);
						 if(custList!=null){
							 customerList=custList;
							 notifyDataSetChanged();
						 }
				}
			});

			imgCustomerWalkseqDown.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
						int currentIndex=position;
						custList=new ArrayList<CustomerMasterVO>();
						custList=Utility.changeIndex(customerList,Constants.WALK_SEQ_DOWN, currentIndex);
						if(custList!=null){
							customerList=custList;
							notifyDataSetChanged();
						}
				}
			});
			
			rltCall.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					TelephonyManager tm= (TelephonyManager)activity.getSystemService(Context.TELEPHONY_SERVICE);
					 if(tm.getPhoneType()==TelephonyManager.PHONE_TYPE_NONE){
				        	//No calling functionality
				        	Toast.makeText(activity, activity.getString(R.string.msg_calling_not_supported),Toast.LENGTH_LONG).show();
				      }else{
				        		Utility.makeCall(activity,contactNumber);
				      }
				}
			});
		}
		return convertView;
	}
	
	
}