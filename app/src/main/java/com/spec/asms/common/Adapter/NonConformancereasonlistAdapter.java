package com.spec.asms.common.Adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.LayoutParams;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.spec.asms.R;
import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.Utility;
import com.spec.asms.service.MaintainanceDTOService;
import com.spec.asms.service.MaintainanceService;
import com.spec.asms.service.UserMasterService;
import com.spec.asms.view.fragment.ConformanceFragment;
import com.spec.asms.vo.CauseVO;
import com.spec.asms.vo.ConformanceMasterVO;
import com.spec.asms.vo.DamageNCauseDetailVO;
import com.spec.asms.vo.DamageVO;
import com.spec.asms.vo.MaintainanceVO;

/**
 * An Adapter class to display Nonconformance reason List.
 * @author jenisha
 *
 */
public class NonConformancereasonlistAdapter extends ArrayAdapter<ConformanceMasterVO>{

	private List<ConformanceMasterVO> reasonList = new ArrayList<ConformanceMasterVO>();
	private Context context;
	private int resource; 
	private ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();
	private boolean flag;

	private Dialog dialog;
	private RadioGroup radGroupDamage ;
	private RadioGroup radGroupCause;
	private RadioButton[] rbDamage;
	private RadioButton[] rbCause;
	private String strDamage,strCause;

	private UserMasterService userMasterService;
	private MaintainanceService maintaianceService;
	private DamageVO damageVO;
	private CauseVO causeVO;
//	private TextView lblDamageName;

	public NonConformancereasonlistAdapter(Context context,	int resource,
										   List<ConformanceMasterVO> objects,
										   ArrayList<Boolean> checkedItem,
										   boolean flag) {
		super(context, resource, objects);
		this.context = context;
		this.resource = resource;
		reasonList = objects;
		this.flag = flag;
		for (int i = 0; i < objects.size(); i++) {
			itemChecked.add(i,checkedItem.get(i)); // initializes all items value with false
		}
		userMasterService = new UserMasterService(context);
		maintaianceService = new MaintainanceService(context);
	}

	@Override
	public int getCount() {
		return reasonList.size();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if(convertView == null){
			LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(resource, parent,false);
		}

		ConformanceMasterVO reason = reasonList.get(position);
		if(reason != null){

			TextView reasonName = (TextView) convertView.findViewById(R.id.lblCfmReason);
			CheckBox chkReasonSelect = (CheckBox)convertView.findViewById(R.id.chkCfmReason);
			TextView lblDamageName = (TextView) convertView.findViewById(R.id.lblDamageName);

			reasonName.setText(reason.getReason());
			String where = "maintainanceorderid = '"+ MaintainanceVO.getMaintainanceOrderId() +"' and objectid = "+reason.getConformanceID() ;
			DamageNCauseDetailVO dNc = maintaianceService.getDetailDamageCauserByObjectNMorderID(where);
			if(userMasterService.getDamageName(dNc.getDamageId()).equals(Constants.LABEL_BLANK))
			{
				lblDamageName.setText(Constants.LABEL_BLANK);
			}else{
				lblDamageName.setText("Damage : "+userMasterService.getDamageName(dNc.getDamageId()));
			}
			if(flag)
			{
				chkReasonSelect.setEnabled(false);
				chkReasonSelect.setClickable(false);
			}else{
				chkReasonSelect.setEnabled(true);
				chkReasonSelect.setClickable(true);
			}
			chkReasonSelect.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					CheckBox cb = (CheckBox) v.findViewById(R.id.chkCfmReason);
					ConformanceMasterVO reason = reasonList.get(position);

					if (cb.isChecked()) {
						itemChecked.set(position, true);
						if(reason.getConformanceID() == 9)
						{
							ConformanceFragment.edtConformanceReasontext.setVisibility(View.VISIBLE);
							ConformanceFragment.reason_list.smoothScrollToPosition(reasonList.size()-1);
							ConformanceFragment.edtConformanceReasontext.setFocusable(true);
						}else{
							showDialog(context,position);
						}
						ConformanceFragment.reasonChecked = itemChecked;
						notifyDataSetInvalidated();

					} else if (!cb.isChecked()) {
						itemChecked.set(position, false);

						// do some operations here
						//Delete damage cause for that object id from the DetailDamageCause
						if(reason.getConformanceID() == 9){
							ConformanceFragment.edtConformanceReasontext.setVisibility(View.INVISIBLE);
						}
						ConformanceFragment.reasonChecked = itemChecked;
						String where = "maintainanceorderid = '"+ MaintainanceVO.getMaintainanceOrderId() +"' and objectid = "+reason.getConformanceID() ;
						if(userMasterService.deleteUser(Constants.TBL_DTL_DAMAGE_CAUSE, where) != -1)
						{
							notifyDataSetInvalidated();
						}
					}
				}
			});
			chkReasonSelect.setChecked(itemChecked.get(position));
		}
		return convertView;
	}

	public void showDialog(final Context context, final int position){
		try{
			dialog = new Dialog(context);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.chk_dialog);

			radGroupDamage = (RadioGroup)dialog.findViewById(R.id.radGroupDamage);
			radGroupCause = (RadioGroup)dialog.findViewById(R.id.radGroupCause);

			List<DamageVO> listDamageVOs = userMasterService.getDamageInfo(reasonList.get(position).getConformanceID());
			List<CauseVO> listCauseVOs = userMasterService.getCauseInfo(reasonList.get(position).getConformanceID());

			//add radio buttons
			rbDamage = new RadioButton[listDamageVOs.size()];
			for(int i=0; i<listDamageVOs.size(); i++){
				damageVO = listDamageVOs.get(i);
				rbDamage[i] = new RadioButton(context);
				rbDamage[i].setLayoutParams(new RadioGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				rbDamage[i].setText(Constants.LABEL_BLANK+damageVO.getFieldName());
				rbDamage[i].setTextColor(Color.BLACK);
				rbDamage[i].setSingleLine();
				rbDamage[i].setId(i);
				rbDamage[i].setTextSize(15);
				rbDamage[0].setChecked(true);
				radGroupDamage.addView(rbDamage[i]); //the RadioButtons are added to the radioGroup instead of the layout
				strDamage = rbDamage[0].getText().toString();
			}

			//add radio buttons
			rbCause = new RadioButton[listCauseVOs.size()];
			for(int i=0; i<listCauseVOs.size(); i++){
				causeVO = listCauseVOs.get(i);
				rbCause[i] = new RadioButton(context);
				rbCause[i].setLayoutParams(new RadioGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				rbCause[i].setText(Constants.LABEL_BLANK+causeVO.getFieldName());
				rbCause[i].setTextColor(Color.BLACK);
				rbCause[i].setSingleLine();
				rbCause[i].setId(i);
				rbCause[i].setTextSize(15);
				rbCause[0].setChecked(true);
				radGroupCause.addView(rbCause[i]); //the RadioButtons are added to the radioGroup instead of the layout
				strCause = rbCause[0].getText().toString();
			}

			radGroupDamage.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					for(int i=0; i<group.getChildCount(); i++) {
						RadioButton btn = (RadioButton) group.getChildAt(i);
						if(btn.getId() == checkedId) {
							strDamage = btn.getText().toString();
							Log.d("FIrstActivity","Radio1 Text ::: "+strDamage);
							Log.d("FIrstActivity","Radio1 Text btn.getId() ::: "+btn.getId());

							// do something with text
							return;
						}
					}
				}
			});

			radGroupCause.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				public void onCheckedChanged(RadioGroup rg, int checkedId) {
					for(int i=0; i<rg.getChildCount(); i++) {
						RadioButton btn = (RadioButton) rg.getChildAt(i);
						if(btn.getId() == checkedId) {
							strCause = btn.getText().toString();
							Log.d("FIrstActivity","Radio2 Text ::: "+strCause);
							Log.d("FIrstActivity","Radio2 Text btn.getId() ::: "+btn.getId());

							// do something with text
							return;
						}
					}
				}
			});

			Button btnOk = (Button)dialog.findViewById(R.id.btnOk);
			btnOk.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					dialog.dismiss();
					Log.d("FIrstActivity","===========================================");
					Log.d("FIrstActivity","Radio1 Text in ok ::: "+strDamage);
					Log.d("FIrstActivity","Radio2 Text in ok ::: "+strCause);
					Log.d("FIrstActivity","===========================================");
					Log.d("FIrstActivity","selected field id ::: "+reasonList.get(position).getConformanceID());

					Log.d("FIrstActivity","Radio1 Text damageVO.getDamageId() ::: "+userMasterService.getDamageID(strDamage,reasonList.get(position).getConformanceID()));
					Log.d("FIrstActivity","Radio2 Text causeVO.getCauseId() ::: "+userMasterService.getCauseID(strCause,reasonList.get(position).getConformanceID()));

					Log.d("FIrstActivity","===========================================");

					//lblDamageName.setText("Damage :"+strDamage);
					MaintainanceDTOService maintainanceDTOService = new MaintainanceDTOService(context);
					List<Integer> giFittingDatas = maintainanceDTOService.getGIfittingMasterSize();

					Log.d("FIrstActivity","======================= giFittingDatas.size() ===================="+giFittingDatas.size());
					Collections.sort(giFittingDatas);

					if(Collections.binarySearch(giFittingDatas, position) >= 0){
						String where = "objectid = " + position +" and maintainanceorderid = '"+ MaintainanceVO.getMaintainanceOrderId() +"'";
						userMasterService.deleteUser(Constants.TBL_DTL_DAMAGE_CAUSE, where);
					}
					
					ContentValues cv = new ContentValues();
					cv.put(Constants.DB_OBJECT_ID,reasonList.get(position).getConformanceID());
					cv.put(Constants.DB_DAMAGE_ID,userMasterService.getDamageID(strDamage,reasonList.get(position).getConformanceID()));
					cv.put(Constants.DB_CAUSE_ID,userMasterService.getCauseID(strCause,reasonList.get(position).getConformanceID()));
					cv.put(Constants.DB_MAINTAINANCE_ORDER_ID,MaintainanceVO.getMaintainanceOrderId());

					if(userMasterService.insertUser(Constants.TBL_DTL_DAMAGE_CAUSE,cv) != -1){
						//Log.e(Constants.TAG_LOGIN_ACTIVITY,"userInsert");
						notifyDataSetInvalidated();
					}		
				}
			});
			
			dialog.show();

		}catch (Exception e) {
			e.printStackTrace();
			Log.d(Constants.DEBUG,":NonConformancereasonlistAdapter:"+ Utility.convertExceptionToString(e));
		}finally{
			//			strDamage = Constants.LABEL_BLANK;
			//			strCause = Constants.LABEL_BLANK;
		}
	}
}