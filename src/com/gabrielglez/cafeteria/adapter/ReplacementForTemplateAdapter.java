package com.gabrielglez.cafeteria.adapter;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gabrielglez.cafeteria.R;
import com.gabrielglez.cafeteria.model.Replacement;
import com.gabrielglez.cafeteria.util.DataBaseHelperManager;
import com.gabrielglez.cafeteria.util.DialogService;

public class ReplacementForTemplateAdapter extends BaseAdapter{
	
	private Activity activity;
	private List<Replacement> replacementList;

	public ReplacementForTemplateAdapter(Activity activity, List<Replacement> replacementList) {
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
	
		final Replacement replacement = replacementList.get(position);
		LayoutInflater inflater = activity.getLayoutInflater();
		final View vi = inflater.inflate(R.layout.listreplacementfortemplate_item_layout, null);
		
               
        TextView replacementName = (TextView)vi.findViewById(R.id.replacementname);
        replacementName.setText( replacement.getName());
        
        TextView replacementObservation = (TextView) vi.findViewById(R.id.replacementobservation);
        replacementObservation.setText(replacement.getObservation() );

        final CheckBox checkedCheckBox = (CheckBox)vi.findViewById(R.id.addcheckBox);
        checkedCheckBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				replacement.setSelected(checkedCheckBox.isChecked());
				Log.v("draw","Estoy pulsado -> " + checkedCheckBox.isChecked() );
			
				if (  checkedCheckBox.isChecked() ){
					
					if ( DataBaseHelperManager.getReplacementDAO().setLikeUsed(replacement.getId()) == true  ){
						replacement.setUsed("YES");
						
					}else{
						DialogService.errorDialog(activity, "Problema al añadir este repuesto a la plantilla", "Error");
					}
					
				}else{
					
					if ( DataBaseHelperManager.getReplacementDAO().setLikeNotUsed(replacement.getId()) == true){
						replacement.setUsed("NO");
					}else{
						DialogService.errorDialog(activity, "Problema al quitar este repuesto a la plantilla", "Error");
					}
				}
			} //end onclick
			
		}); //end onclick listener
       
        
        if ( replacement.getUsed().equals("YES") ){
        
        	checkedCheckBox.setChecked(true);
        	replacement.setSelected(true);
        
        }else if ( replacement.getUsed().equals("NO")){
        
        	checkedCheckBox.setChecked(false);
        	replacement.setSelected(false);
        
        } 
        
        return vi;
	}

	public List<Replacement> getReplacementList() {
		return replacementList;
	}

	public void setReplacementList(List<Replacement> replacementList) {
		this.replacementList = replacementList;
	}
	
	
	
}