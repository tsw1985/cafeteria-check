package com.gabrielglez.cafeteria;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gabrielglez.cafeteria.util.FontUtil;
import com.gabrielglez.cafeteria.util.ToastService;

public class MainWindowActivity extends Activity {

	private Button clientesButton;
	private Button operatorButton;
	private Button replacementButton;
	private Button createReplacementTemplaceButton;
	private Button newRevisionButton;
	private Button queryRevistionButton;
	private Button logoutButton;
	
	private TextView loginTextView;
	private TextView textTitleMainWindowTextView;
	private TextView footertitleTextVaraniniweb;
	
	public static MainWindowActivity mainWindowActivity;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_window);
		setTitle("Menú principal");
		mainWindowActivity = this;
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
		
		mainWindowActivity = this;
		
		
		textTitleMainWindowTextView = (TextView)findViewById(R.id.textTitleMainWindowTextView);
		textTitleMainWindowTextView.setTextSize(100);
		textTitleMainWindowTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		
		
		loginTextView = (TextView)findViewById(R.id.loginTextView);
		loginTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		loginTextView.setTextSize(40);
		loginTextView.setText("Bienvenido: " + MainActivity.userOnline);
		
		footertitleTextVaraniniweb = (TextView)findViewById(R.id.footertitleTextVaraniniweb);
		footertitleTextVaraniniweb.setTextSize(30);
		footertitleTextVaraniniweb.setTypeface(FontUtil.getSaginaBoldFont(this));
		
		
		
		
		logoutButton = (Button)findViewById(R.id.logoutButton);
		logoutButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				logout(v);
			}
			
		});
		
		
		
		clientesButton = (Button)findViewById(R.id.clientesButton);
		clientesButton.setTypeface(FontUtil.getSaginaBoldFont(this));
		clientesButton.setTextSize(45);
		clientesButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				customerButtonOnClick(v);
			}
		});
		
		operatorButton = (Button)findViewById(R.id.operatorcrudButton);
		operatorButton.setTypeface(FontUtil.getSaginaBoldFont(this));
		operatorButton.setTextSize(45);
		operatorButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				operatorButtonOnClick(v);
			}
		});
		
		replacementButton = (Button)findViewById(R.id.replacementButton);
		replacementButton.setTypeface(FontUtil.getSaginaBoldFont(this));
		replacementButton.setTextSize(45);
		replacementButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				replacementButtonOnClick(v);
			}
		});
		
		createReplacementTemplaceButton = (Button)findViewById(R.id.checkSheetTemplateButon);
		createReplacementTemplaceButton.setTypeface(FontUtil.getSaginaBoldFont(this));
		createReplacementTemplaceButton.setTextSize(45);
		createReplacementTemplaceButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				createReplacementTemplateButtonOnClick(v);
			}
		});

		newRevisionButton = (Button)findViewById( R.id.newRevisionButton );
		newRevisionButton.setTypeface(FontUtil.getSaginaBoldFont(this));
		newRevisionButton.setTextSize(45);
		newRevisionButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				createNewRevisionForCustomer(v);
			}
		});
		
		queryRevistionButton = (Button)findViewById(R.id.queryRevisionButton);
		queryRevistionButton.setTypeface(FontUtil.getSaginaBoldFont(this));
		queryRevistionButton.setTextSize(45);
		queryRevistionButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				queryRevisionButtonOnClick(v);
			}
		});
		
	}
	


	protected void logout(View v) {

		if ( MainActivity.mainActivity != null ){
			MainActivity.mainActivity.finish();
		}
		
		MainActivity.userOnline = "";
		Intent customerToCheckActivity = new Intent(this , MainActivity.class);
		startActivity(customerToCheckActivity);
		finish();
		
	}


	protected void createNewRevisionForCustomer(View v) {
		Intent customerToCheckActivity = new Intent(this , CustomerToCheckActivity.class);
		startActivity(customerToCheckActivity);
	}


	protected void createReplacementTemplateButtonOnClick(View v) {
		
		Intent createReplacementActivity = new Intent(this , CrudTeplateActivity.class);
		startActivity(createReplacementActivity);
		
		/*if ( MainActivity.userOnline.equals("root")){
			Intent createReplacementActivity = new Intent(this , CrudTeplateActivity.class);
			startActivity(createReplacementActivity);
		}else{
			ToastService.messageToast(this , "No tiene permisos para entrar en esta sección" , Toast.LENGTH_SHORT);
		}*/
	}


	protected void operatorButtonOnClick(View v) {
		if ( MainActivity.userOnline.equals("root")){
			Intent crudOperatorActivity = new Intent(this,CrudOperatorActivity.class);
			startActivity(crudOperatorActivity);
		}else{
			ToastService.messageToast(this , "No tiene permisos para entrar en esta sección" , Toast.LENGTH_SHORT);
		}
	}

	protected void customerButtonOnClick(View v) {
		
		Intent crudCustomerActivity = new Intent(this,CrudCustomersActivity.class);
		startActivity(crudCustomerActivity);
		
		/*if ( MainActivity.userOnline.equals("root")){
			Intent crudCustomerActivity = new Intent(this,CrudCustomersActivity.class);
			startActivity(crudCustomerActivity);
		}else{
			ToastService.messageToast(this , "No tiene permisos para entrar en esta sección" , Toast.LENGTH_SHORT);
		}*/
	}
	
	protected void replacementButtonOnClick(View v) {
		
		Intent crudReplacementActivity = new Intent(this, CrudReplacementActivity.class);
		startActivity(crudReplacementActivity);
		
		/*if ( MainActivity.userOnline.equals("root")){
			Intent crudReplacementActivity = new Intent(this, CrudReplacementActivity.class);
			startActivity(crudReplacementActivity);
		}else{
			ToastService.messageToast(this , "No tiene permisos para entrar en esta sección" , Toast.LENGTH_SHORT);
		}*/
	}
	
	
	protected void queryRevisionButtonOnClick(View v) {
		
		Intent queryRevisionActivity = new Intent(this, QueryRevisionActivity.class);
		startActivity(queryRevisionActivity);
		
		/*if ( MainActivity.userOnline.equals("root")){
			Intent queryRevisionActivity = new Intent(this, QueryRevisionActivity.class);
			startActivity(queryRevisionActivity);
		}else{
			ToastService.messageToast(this , "No tiene permisos para entrar en esta sección" , Toast.LENGTH_SHORT);
		}*/
	}
	
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
	}
}