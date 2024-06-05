package com.gabrielglez.cafeteria.adapter;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gabrielglez.cafeteria.R;
import com.gabrielglez.cafeteria.model.ReplacementNotEntity;

public class ReplacementForNewRevisionAdapter extends BaseAdapter{
	
	private Activity activity;
	private List<ReplacementNotEntity> replacementNotEntityList;
	

	public ReplacementForNewRevisionAdapter(Activity activity, List<ReplacementNotEntity> replacementNotEntityList) {
		this.activity = activity;
		this.replacementNotEntityList = replacementNotEntityList;
	}

	@Override
	public int getCount() {
		return replacementNotEntityList.size();
	}

	@Override
	public Object getItem(int position) {
		return (ReplacementNotEntity)replacementNotEntityList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return replacementNotEntityList.get(position).getId();
	}

	@Override
	public View getView(final int position, final View convertView, ViewGroup parent) {

		final ReplacementNotEntity replacement = replacementNotEntityList.get(position);
		LayoutInflater inflater = activity.getLayoutInflater();
		final View item = inflater.inflate(R.layout.listreplacementfornew_revision_item_layout, null);
		
		TextView replacementName = (TextView)item.findViewById(R.id.replacementnameNewrevision);
        replacementName.setText( replacement.getName());
        
        TextView replacementObservation = (TextView) item.findViewById(R.id.replacementobservationreplacementnameNewrevision);
        replacementObservation.setText(replacement.getObservation() );

        final CheckBox checkedCheckBox = (CheckBox)item.findViewById(R.id.checkedCheckBoxReplacementnameNewrevision);

        //---------------------------------------
        //Accedo al contenedor padre del check box para poder acceder al boton de al lado y deshabilitarlo
  		ViewParent viewParent = (LinearLayout)checkedCheckBox.getParent();
  		
  		//Lo casteo a View para poder buscarlo por id 
  		final View viewButton = (View) viewParent;
        final Button observationButton = (Button)viewButton.findViewById(R.id.observationButtonReplacementnameNewrevision);;
        
        
        checkedCheckBox.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				Log.d("Check","Pulsado -> " + position + " Soy " + replacement.getName() );
				replacement.setSelected(checkedCheckBox.isChecked());
				observationButton.setEnabled(checkedCheckBox.isChecked());
				
				if (checkedCheckBox.isChecked()){
		        	observationButton.setEnabled(true);
		        	observationButton.setBackground(activity.getResources().getDrawable(R.drawable.bluebutton ));
		        }else{
		        	observationButton.setEnabled(false);
		        	observationButton.setBackground(activity.getResources().getDrawable(R.drawable.bluebuttondisabled ));
		        }
				
			}
			
        });
        
        
        checkedCheckBox.setChecked(replacement.isSelected());
        
        if (checkedCheckBox.isChecked()){
        	observationButton.setEnabled(true);
        	observationButton.setBackground(activity.getResources().getDrawable(R.drawable.bluebutton ));
        }else{
        	observationButton.setEnabled(false);
        	observationButton.setBackground(activity.getResources().getDrawable(R.drawable.bluebuttondisabled ));
        }
        
        
        if ( !replacement.isSelected()){
        	replacement.setObservationInCheck("");
        }
        
        return item;
        
	}
	
	
	public List<ReplacementNotEntity> getreplacementNotEntityList() {
		return replacementNotEntityList;
	}
}