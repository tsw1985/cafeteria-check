package com.gabrielglez.cafeteria.adapter;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gabrielglez.cafeteria.R;
import com.gabrielglez.cafeteria.model.Replacement;

public class ReplacementForRevisedTemplateAdapter extends BaseAdapter{
	
	private Activity activity;
	private List<Replacement> replacementList;
	private Button openObservationButton;

	public ReplacementForRevisedTemplateAdapter(Activity activity, List<Replacement> replacementList) {
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
	
		View vi = convertView;
		
        if(convertView == null) {
        	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	vi = inflater.inflate(R.layout.listreplacement_revised_item_layout , null);
        }
            
        final Replacement replacement = replacementList.get(position);
        
        TextView replacementName = (TextView)vi.findViewById(R.id.replacementnamerevised);
        replacementName.setText( replacement.getName());
        
        TextView replacementObservation = (TextView) vi.findViewById(R.id.replacementobservationrevised);
        replacementObservation.setText(replacement.getObservation() );

        Button openObservationButton = (Button) vi.findViewById(R.id.observationInCheckSheetRevisedButton);
        
        if ( replacement.getObservationInCheck().length() > 0 ){
        	Log.i("Salida","SI OBSERVATION");
        	openObservationButton.setBackground(activity.getResources().getDrawable(R.drawable.greenbutton));
        }else{
        	Log.i("Salida","NO OBSERVATION");
        	openObservationButton.setBackground(activity.getResources().getDrawable(R.drawable.bluebutton ));
        	openObservationButton.setEnabled(false);
        }
        
        
		openObservationButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				final Dialog dialog = new Dialog(activity);
				dialog.setContentView(R.layout.customdialog);
				dialog.setTitle("Observación");
	 
				EditText text = (EditText) dialog.findViewById(R.id.observationCheckedEditText);
				text.setText(replacement.getObservationInCheck());
					 
				Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
	 
				dialog.show();
			  }
        
		});
        
        return vi;
	}

	public List<Replacement> getReplacementList() {
		return replacementList;
	}
	public Button getOpenObservationButton() {
		return openObservationButton;
	}
}