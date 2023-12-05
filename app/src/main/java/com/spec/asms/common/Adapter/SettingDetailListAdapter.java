package com.spec.asms.common.Adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spec.asms.R;
/**
 * An adapter for displaying Technician list. 
 *
 */
public class SettingDetailListAdapter extends BaseAdapter {

	static class ViewHolder{
		TextView formName;	
	}

	private Context context;	
	private LayoutInflater mLayoutInflator;
	private ArrayList<String> arrayList;
	public static ArrayList<Boolean> itemSelected = new ArrayList<Boolean>();

	public SettingDetailListAdapter(Context context,ArrayList<String> list) {

		this.context = context;
		mLayoutInflator = LayoutInflater.from(context);
		this.arrayList = list;
		itemSelected.clear();

		for(int i = 0; i < arrayList.size(); i++){
			itemSelected.add(i, false);
		}
	}

	public int getCount() {
		return arrayList.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int index) {
		return index;
	}

	public View getView(final int position,View convertView, final ViewGroup parent) {

		final ViewHolder holder;
		if(convertView == null){

			convertView = mLayoutInflator.inflate(R.layout.list_user,null);
			holder = new ViewHolder();
			holder.formName = (TextView) convertView.findViewById(R.id.lblUserName);
			convertView.setTag(holder);

		}else	
			holder = (ViewHolder) convertView.getTag();   

		holder.formName.setText(arrayList.get(position));
		return convertView;
	}	
}