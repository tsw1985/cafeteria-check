package com.gabrielglez.cafeteria;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gabrielglez.cafeteria.adapter.ReplacementAdapter;
import com.gabrielglez.cafeteria.model.Replacement;
import com.gabrielglez.cafeteria.util.DataBaseHelperManager;
import com.gabrielglez.cafeteria.util.DialogService;
import com.gabrielglez.cafeteria.util.FontUtil;

public class CrudReplacementActivity extends Activity {
	
	private ListView replacementListView;
	private Button newreplacementButton;
	private Button gobackreplacementButton;
	private List<Replacement> replacementList;
	private ImageView editreplacementImageView;
	private ImageView deletereplacementImageView;
	
	private TextView titleCrudreplacementTextView;
	
	public static CrudReplacementActivity crudreplacementActivity;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crud_replacement);
		setTitle("Repuestos");
		crudreplacementActivity = this;
		initComponent();
		initOnClickListener();
		setOrientation();
	}
	
	
	
	private void setOrientation() {
	    
		int current = getRequestedOrientation();

	    if ( current != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ) {
	        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
	    }
	}

	
	protected void goBackButton() {
		if (MainWindowActivity.mainWindowActivity != null){
			MainWindowActivity.mainWindowActivity.finish();
			Intent mainWindowActivity = new Intent(this , MainWindowActivity.class);
			startActivity(mainWindowActivity);
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		goBackButton();
	}
	
	
	
	
	@Override
	protected void onStart() {
		super.onStart();
		loadreplacementsFromBd();
	}
	
	private void initComponent() {
		
		titleCrudreplacementTextView = (TextView)findViewById(R.id.titleCrudreplacementTextView);
		titleCrudreplacementTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		titleCrudreplacementTextView.setTextSize(60);
		
		replacementListView = (ListView)findViewById(R.id.replacementListView);
		newreplacementButton = (Button)findViewById(R.id.addreplacementButton);
		gobackreplacementButton = (Button)findViewById(R.id.gobackreplacementButton);
		editreplacementImageView = (ImageView)findViewById(R.id.editreplacement);
		deletereplacementImageView = (ImageView)findViewById(R.id.deletereplacement);
	}
	
	private void loadreplacementsFromBd() {
		
		replacementList = DataBaseHelperManager.getReplacementDAO().getAllReplacementNoDeleted();
		
		if ( replacementList != null){
			ReplacementAdapter replacementAdapter = new ReplacementAdapter(this, replacementList);
			replacementListView.setAdapter(replacementAdapter);
		}
	}

	private void initOnClickListener() {
		
		newreplacementButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showAddreplacementActivityOnClick(v);
				
			}
		});
		
		replacementListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view , int position, long id) {
				replacementListViewOnItemClick(parent,view,position,id);
			}
		});
		
		
		gobackreplacementButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goBackButton();
			}
		});
		
		
	}

	
	public void editreplacement(View view){

		Replacement replacement = (Replacement)replacementListView.getAdapter().getItem(replacementListView.getPositionForView(view));
		
		Intent editreplacement = new Intent(this , NewReplacementDataForm.class);
		editreplacement.putExtra("nameReplacement", replacement.getName() );
		editreplacement.putExtra("observationReplacement", replacement.getObservation());
		editreplacement.putExtra("editdata", true );
		editreplacement.putExtra("id", replacement.getId() );
		startActivity(editreplacement);
		
	}
	
	public void deletereplacement(View view){
		
		final Replacement replacement = (Replacement)replacementListView.getAdapter().getItem( replacementListView.getPositionForView(view));
		
		AlertDialog.Builder questionDialog = new AlertDialog.Builder(this);
		questionDialog.setTitle("Confirmación");
		questionDialog.setMessage("¿Realmente quiere eliminar el repuesto " + replacement.getName() + " ?");

		questionDialog.setPositiveButton("Sí", new android.content.DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				
				if ( DataBaseHelperManager.getReplacementDAO().delete( replacement.getId())){
					messagereplacementDeleted();
					loadreplacementsFromBd();
				}else{
					messagereplacementDeletedError();
				}
			}
		});
		
		questionDialog.setNegativeButton("No", new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		questionDialog.show();
		
		
	}
	
	private void messagereplacementDeleted(){
		DialogService.congratulationsDialog(this, "! Repuesto eliminado con éxito !", "Correcto");
	}
	
	private void messagereplacementDeletedError(){
		DialogService.congratulationsDialog(this, "No se ha podido eliminar el repuesto", "Error");
	}
	
	protected void replacementListViewOnItemClick(AdapterView<?> parent,View view, int position, long id) {
		
	}

	protected void showAddreplacementActivityOnClick(View v) {
		Intent addreplacementActivity = new Intent(this, NewReplacementDataForm.class);
		startActivity(addreplacementActivity);
		
	}


}