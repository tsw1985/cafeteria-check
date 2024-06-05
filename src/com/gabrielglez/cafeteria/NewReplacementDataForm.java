package com.gabrielglez.cafeteria;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gabrielglez.cafeteria.util.DialogService;
import com.gabrielglez.cafeteria.util.FontUtil;
import com.gabrielglez.services.facade.ReplacementFacade;

public class NewReplacementDataForm extends Activity {
	
	private Button saveNewReplacementButton ;
	private Button goCrudReplacementBackButton;
	
	private EditText nameReplacementEditText;
	private EditText observationReplacementEditText;
	
	private ReplacementFacade replacementFacade;
	private TextView titleActionTextView;
	
	
	private int idReplacement;
	protected static NewReplacementDataForm newReplacementDataForm;
	private boolean editData = false;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_replacement_data_form);
		setTitle("Datos de repuesto");
		
		newReplacementDataForm= this;
		if ( CrudReplacementActivity.crudreplacementActivity != null)
			CrudReplacementActivity.crudreplacementActivity.finish();
		
		initComponent();
		putReplacementDataAndChangeActionTitle();
		setOrientation();
	}
	
	private void setOrientation() {
	    
		int current = getRequestedOrientation();

	    if ( current != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ) {
	        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
	    }
	    
	}



	private void putReplacementDataAndChangeActionTitle() {
		
		Bundle extras = getIntent().getExtras();
		
		if ( extras != null ){
			setTitle("Editar Repuesto");
			nameReplacementEditText.setText(extras.getString("nameReplacement"));
			observationReplacementEditText.setText(extras.getString("observationReplacement"));
			editData = extras.getBoolean("editdata");
			idReplacement = extras.getInt("id");
			
			titleActionTextView.setText("Editar repuesto");
			editData = true;
		}
	}


	private void initComponent() {
		replacementFacade = new  ReplacementFacade();
		nameReplacementEditText = (EditText)findViewById(R.id.nameReplacementeditText);
		observationReplacementEditText = (EditText)findViewById(R.id.observationReplacementEditText);
		titleActionTextView = (TextView)findViewById(R.id.titleActionReplacementTextView);
		titleActionTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		titleActionTextView.setTextSize(80);

		goCrudReplacementBackButton = (Button)findViewById(R.id.goCrudReplacementBackButton);
		goCrudReplacementBackButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goBack();
			}
		});
		
		
		saveNewReplacementButton = (Button)findViewById(R.id.saveNewReplacementButton);
		saveNewReplacementButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveNewReplacementButtonOnClick(v);
			}
		});
	}

	
	protected void saveNewReplacementButtonOnClick(View v) {
		
		String nameReplacement         = nameReplacementEditText.getText().toString();
		String observationReplacement  = observationReplacementEditText.getText().toString();

		String error = "";
		
		if ( nameReplacement.equals("")){
			error = error + "Nombre del repuesto ,";
		}if ( observationReplacement.equals("")){
			error = error + "Observación ,";
		}
		
		
		if ( error.equals("") ){
			
			
			if ( !editData){
			
				if ( replacementFacade.create(nameReplacement , observationReplacement )){
					//DialogService.congratulationsDialog(this, "Repuesto creado con éxito", "Correcto");
					onBackPressed();
				}else{
					DialogService.congratulationsDialog(this, "No se ha podido crear el repuesto", "Error");
				}
			
			}else{
				if ( replacementFacade.update(nameReplacement , observationReplacement , idReplacement )){
					//DialogService.congratulationsDialog(this, "Repuesto modificado con éxito", "Correcto");
					onBackPressed();
				}else{
					DialogService.congratulationsDialog(this, "No se ha podido crear el repuesto", "Error");
				}
			}
			
		}else{
			
			String errorToShow = error.substring(0 , error.length() -1);
			DialogService.errorDialog(this, "Debe rellenar los siguientes campos: " + errorToShow , "Campos por escribir");
		}
			
	}
	
	@Override
	public void onBackPressed() {
			goBack();
	}

	protected void goBack() {
		Intent goBackIntent = new Intent(this , CrudReplacementActivity.class );
		startActivity(goBackIntent);
		finish();
	}
}