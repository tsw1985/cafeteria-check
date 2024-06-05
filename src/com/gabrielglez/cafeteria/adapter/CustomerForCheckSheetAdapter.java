package com.gabrielglez.cafeteria.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gabrielglez.cafeteria.R;
import com.gabrielglez.cafeteria.model.modelutil.CustomerNotEntity;

public class CustomerForCheckSheetAdapter extends BaseAdapter{
	
	private Activity activity;
	private List<CustomerNotEntity> customerList;


	public CustomerForCheckSheetAdapter(Activity activity, List<CustomerNotEntity> customerList) {
		this.activity = activity;
		this.customerList = customerList;
	}

	@Override
	public int getCount() {
		return customerList.size();
	}

	@Override
	public Object getItem(int position) {
		return (CustomerNotEntity)customerList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return customerList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		View vi=convertView;
		
        if(convertView == null) {
        	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	vi = inflater.inflate(R.layout.listcustomernewcheck_item_layout , null);
        }
            
        CustomerNotEntity customer = customerList.get(position);
        
        TextView customerName = (TextView)vi.findViewById(R.id.customernamecheck);
        customerName.setText( customer.getName());
        
        TextView comercialName = (TextView) vi.findViewById(R.id.comercialnamecustomercheck);
        comercialName.setText(customer.getComercialName());
        
        LinearLayout customerLinear = (LinearLayout)vi.findViewById(R.id.customerToCheckRow);
        ImageView imageChecked = (ImageView)vi.findViewById(R.id.customercheckedImageView);

    	
        if ( customer.isChecked()  ){
        	customerLinear.setBackgroundColor(Color.GREEN);
        	imageChecked.setImageResource(R.drawable.check);
        	
        }else{
        	customerLinear.setBackgroundColor(Color.RED);
        	imageChecked.setImageResource(R.drawable.nocheck);
        }
        
        return vi;
	}
}