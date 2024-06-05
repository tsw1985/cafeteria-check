package com.gabrielglez.cafeteria;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.gabrielglez.cafeteria.adapter.ReplacementForTemplateAdapter;
import com.gabrielglez.cafeteria.model.Replacement;
import com.gabrielglez.cafeteria.util.DataBaseHelperManager;
import com.gabrielglez.cafeteria.util.FontUtil;

public class CrudTeplateActivity extends Activity {
	
	private ListView replacementListView;
	private List<Replacement> replacementList;
	private Button goToMainWindowButton;
	private TextView titleCrudreplacementTemplateTextView;
	
	protected static CrudTeplateActivity crudTeplateActivity ;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crud_teplate);
		setTitle("Plantilla de revisión");
		crudTeplateActivity = this;
		
		if ( MainWindowActivity.mainWindowActivity != null){
			MainWindowActivity.mainWindowActivity.finish();
		}
		
		initComponent();
		setOrientation();
		
	}
	
	private void setOrientation() {
	    
		int current = getRequestedOrientation();
	    if ( current != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ) {
	        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
	    }
	}

	
	private void initComponent() {
		titleCrudreplacementTemplateTextView = (TextView)findViewById(R.id.titleCrudreplacementTemplateTextView);
		titleCrudreplacementTemplateTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		titleCrudreplacementTemplateTextView.setTextSize(60);
		
		replacementListView = (ListView)findViewById(R.id.listReplacementTemplatelistView);
		goToMainWindowButton =(Button)findViewById(R.id.goMainWindowTemplateBackButton);
		goToMainWindowButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goBack();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.crud_teplate, menu);
		return true;
	}
	
	@Override
	public void onStart(){
		super.onStart();
		loadReplacementFromBD();
	}
	
	@Override
	public void onBackPressed() {
			goBack();
	}
	
	protected void goBack() {
		Intent goBackIntent = new Intent(this , MainWindowActivity.class );
		startActivity(goBackIntent);
		finish();
	}

	private void loadReplacementFromBD() {
		
		replacementList = DataBaseHelperManager.getReplacementDAO().getAllReplacementNoDeleted();
		
		if ( replacementList != null){
			ReplacementForTemplateAdapter customerAdapter = new ReplacementForTemplateAdapter(this, replacementList);
			replacementListView.setAdapter(customerAdapter);	
		}
		
	}
}