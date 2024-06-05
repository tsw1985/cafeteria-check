package com.gabrielglez.cafeteria;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.gabrielglez.cafeteria.adapter.CustomerForCheckSheetAdapter;
import com.gabrielglez.cafeteria.model.modelutil.CustomerNotEntity;
import com.gabrielglez.cafeteria.util.DataBaseHelperManager;
import com.gabrielglez.cafeteria.util.DateUtil;
import com.gabrielglez.cafeteria.util.FontUtil;

public class CustomerToCheckActivity extends Activity {
	
	private List<CustomerNotEntity> customerList;
	private ListView customerToCheckListView;

	private Button searchCustomerButton;
	private Button searchCustomerGoBackButton;
	
	private EditText searchCustomerEditText;

	private RadioButton customerCheckedRadio;
	private RadioButton customerNoChechedRadio;
	private RadioButton allCustomerCheckedRadio;
	
	private TextView customerForMonthTextView;
	private TextView actualMonthTextView;
	
	protected static CustomerToCheckActivity customerToCheckActivity;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_check_to_customer);
		setTitle("Clientes a revisar");
		initComponent();
		
		customerToCheckActivity = this;
		
		if ( CreateRevisionActivity.createRevisionActivity != null){
			CreateRevisionActivity.createRevisionActivity.finish();
		}
		
		
	
		setOrientation();
	}
	
	private void setOrientation() {
	    
		int current = getRequestedOrientation();

	    if ( current != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ) {
	        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
	    }
	}


	
	
	private void initComponent() {

		
		customerForMonthTextView = (TextView)findViewById(R.id.customerForMonthTextView);
		customerForMonthTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		customerForMonthTextView.setTextSize(45);
		
		actualMonthTextView = (TextView)findViewById(R.id.actualMonthTextView);
		actualMonthTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		actualMonthTextView.setTextSize(45);
		
		
		searchCustomerGoBackButton = (Button)findViewById(R.id.searchCustomerGoBackButton);
		searchCustomerGoBackButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goBackButton();
			}
		});
		
		customerToCheckListView = (ListView)findViewById(R.id.customercheckedyesnolistview);
		customerToCheckListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view ,int position , long id) {
				onItemClickCheckListView(parent,view,position,id);
			}
			
		});
		
		searchCustomerEditText = (EditText)findViewById(R.id.searchCustomerEditText);
		
		
		customerCheckedRadio    = (RadioButton)findViewById(R.id.customerCheckedRadio);
		customerNoChechedRadio  = (RadioButton)findViewById(R.id.customerNoCheckedRadio);
		allCustomerCheckedRadio = (RadioButton)findViewById(R.id.allCustomerCheckedButton);
		allCustomerCheckedRadio.setChecked(true);
		
		searchCustomerButton = (Button)findViewById(R.id.searchCustomerToCheckButton);
		searchCustomerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				String option = "";
				
				if ( customerCheckedRadio.isChecked() ){
					option = "checked";
				}
				else if ( customerNoChechedRadio.isChecked()  ) {
					option = "nochecked";
				}
				else if ( allCustomerCheckedRadio.isChecked()  ){
					option = "all";
				}
				
				loadCustomersFromBDByCustomerName( searchCustomerEditText.getText().toString() , option );
			}
		});
		
		setActualMonth();
		
	}

	protected void onItemClickCheckListView(AdapterView<?> parent, View view,int position, long id) {
		
		CustomerNotEntity customerToCheck = (CustomerNotEntity)customerToCheckListView.getAdapter().getItem( customerToCheckListView.getPositionForView(view) );
		
		Intent checkThisCustomerIntent = new Intent(this , CreateRevisionActivity.class);
		
		checkThisCustomerIntent.putExtra("id", customerToCheck.getId());
		checkThisCustomerIntent.putExtra("name", customerToCheck.getName());
		checkThisCustomerIntent.putExtra("comercialName", customerToCheck.getComercialName());
		checkThisCustomerIntent.putExtra("cif", customerToCheck.getCif());
		checkThisCustomerIntent.putExtra("phone", customerToCheck.getPhone());
		
		startActivity(checkThisCustomerIntent);
	}


	private void setActualMonth() {
		actualMonthTextView.setText(DateUtil.getStringActualMonth());
	}

	@Override
	protected void onStart() {
		super.onStart();
		loadCustomersFromBD();
	}
	
	private void loadCustomersFromBD(){
		
		customerList = DataBaseHelperManager.getCustomerDAO().getAllCustomerToCheckInActualMonth();
		
		if ( customerList != null){
			CustomerForCheckSheetAdapter customerAdapter = new CustomerForCheckSheetAdapter(this, customerList);
			customerToCheckListView.setAdapter(customerAdapter);	
		}
		
	}
	
	private void loadCustomersFromBDByCustomerName(String name, String option){
		
		customerList = DataBaseHelperManager.getCustomerDAO().getAllCustomerToCheckInActualMonthByNameAndOption( name , option );
		
		if ( customerList != null){
			CustomerForCheckSheetAdapter customerAdapter = new CustomerForCheckSheetAdapter(this, customerList);
			customerToCheckListView.setAdapter(customerAdapter);	
		}
		
	}
	
	protected void goBackButton() {
		
		if ( MainWindowActivity.mainWindowActivity != null ){
			MainWindowActivity.mainWindowActivity.finish();
		}
		
		Intent mainWindowActivity = new Intent(this , MainWindowActivity.class);
		startActivity(mainWindowActivity);
		finish();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		goBackButton();
	}
	
}