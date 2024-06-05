package com.gabrielglez.cafeteria.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gabrielglez.cafeteria.R;
import com.gabrielglez.cafeteria.model.Operator;

public class OperatorAdapter extends BaseAdapter{
	
	private Activity activity;
	private List<Operator> operatorList;


	public OperatorAdapter(Activity activity, List<Operator> operatorList) {
		this.activity = activity;
		this.operatorList = operatorList;
	}

	@Override
	public int getCount() {
		return operatorList.size();
	}

	@Override
	public Object getItem(int position) {
		return (Operator)operatorList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return operatorList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		View vi=convertView;
		
        if(convertView == null) {
        	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	vi = inflater.inflate(R.layout.listoperator_item_layout, null);
        }
            
        Operator operator = operatorList.get(position);
        
        TextView customerName = (TextView)vi.findViewById(R.id.operatorname);
        customerName.setText( operator.getName());
        
        TextView comercialName = (TextView) vi.findViewById(R.id.operatordni);
        comercialName.setText(operator.getDni());

        return vi;
		
	}

}
