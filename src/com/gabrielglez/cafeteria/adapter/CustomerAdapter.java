package com.gabrielglez.cafeteria.adapter;

import java.util.List;

import com.gabrielglez.cafeteria.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gabrielglez.cafeteria.model.Customer;

public class CustomerAdapter extends BaseAdapter{
	
	private Activity activity;
	private List<Customer> customerList;


	public CustomerAdapter(Activity activity, List<Customer> customerList) {
		this.activity = activity;
		this.customerList = customerList;
	}

	@Override
	public int getCount() {
		return customerList.size();
	}

	@Override
	public Object getItem(int position) {
		return (Customer)customerList.get(position);
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
        	vi = inflater.inflate(R.layout.list_item_layout, null);
        }
            
        Customer customer = customerList.get(position);
        
        TextView customerName = (TextView)vi.findViewById(R.id.customername);
        customerName.setText( customer.getName());
        
        TextView comercialName = (TextView) vi.findViewById(R.id.comercialnamecustomer);
        comercialName.setText(customer.getComercialName());

        return vi;
		
	}

}
