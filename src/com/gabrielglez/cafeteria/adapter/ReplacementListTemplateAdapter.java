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
import com.gabrielglez.cafeteria.model.Replacement;

public class ReplacementListTemplateAdapter extends BaseAdapter{
	
	private Activity activity;
	private List<Replacement> replacementList;


	public ReplacementListTemplateAdapter(Activity activity, List<Replacement> replacementList) {
		this.activity = activity;
		this.replacementList = replacementList;
	}

	@Override
	public int getCount() {
		return replacementList.size();
	}

	@Override
	public Object getItem(int position) {
		return (Replacement)replacementList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return replacementList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		View vi=convertView;
		
        if(convertView == null) {
        	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	vi = inflater.inflate(R.layout.listreplacementtemplate_item_layout , null);
        }
            
        Replacement replacement = replacementList.get(position);
        
        TextView replacementName = (TextView)vi.findViewById(R.id.replacementnameTemplate);
        replacementName.setText( replacement.getName());
        
        TextView replacementObservation = (TextView) vi.findViewById(R.id.replacementobservation);
        replacementObservation.setText(replacement.getName() );

        return vi;
		
	}

}
