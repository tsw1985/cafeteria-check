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
import com.gabrielglez.services.facade.CustomerFacade;

public class NewCustomerDataForm extends Activity {
	
	private Button saveNewCustomerButton ;
	private Button goBackButton;
	
	private EditText nameCustomerEditText;
	private EditText comercialNameCustomerEditText;
	private EditText cifCustomerEditText;
	private EditText addressCustomerEditText;
	private EditText phoneCustomerEditText;
	private EditText observationCustomerEditText;
	
	private CustomerFacade customerFacade;
	private TextView titleActionTextView;
	
	
	private int idCustomer;
	protected static NewCustomerDataForm newCustomerDataForm;
	private boolean editData = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_customer_data_form);
		setTitle("Datos de cliente");
		
		
		if (CrudCustomersActivity.crudCustomersActivity != null)
			CrudCustomersActivity.crudCustomersActivity.finish();
		
		
		newCustomerDataForm = this;
		initComponent();
		putCustomerDataAndChangeActionTitle();
		setOrientation();
	}
	
	private void setOrientation() {
	    
		int current = getRequestedOrientation();

	    if ( current != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ) {
	        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
	    }
	    
	}


	private void putCustomerDataAndChangeActionTitle() {
		
		Bundle extras = getIntent().getExtras();
		
		if ( extras != null ){
			setTitle("Editar Cliente");
			nameCustomerEditText.setText(extras.getString("nameCustomer"));
			comercialNameCustomerEditText.setText(extras.getString("comercialNameCuster"));
			cifCustomerEditText.setText(extras.getString("cifCustomer"));
			addressCustomerEditText.setText(extras.getString("addressCustomer"));
			phoneCustomerEditText.setText(extras.getString("phoneCustomer"));
			observationCustomerEditText.setText(extras.getString("observationCustomer"));
			editData = extras.getBoolean("editdata");
			idCustomer = extras.getInt("id");
			titleActionTextView.setText("Editar cliente");
			editData = true;
			
		}
	}

	private void initComponent() {
		
		customerFacade= new CustomerFacade();
		
		titleActionTextView = (TextView)findViewById(R.id.titleActionNewCustomerTextView);
		titleActionTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		titleActionTextView.setTextSize(60);
		
		nameCustomerEditText = (EditText)findViewById(R.id.nameCustomereditText);
		comercialNameCustomerEditText = (EditText)findViewById(R.id.comercialNameEditText);
		cifCustomerEditText = (EditText)findViewById(R.id.cifCustomerEditText);
		addressCustomerEditText = (EditText)findViewById(R.id.addressCustomerEditText);
		phoneCustomerEditText = (EditText)findViewById(R.id.phoneCustomerEditText);
		observationCustomerEditText = (EditText)findViewById(R.id.observationCustomerEditText);
		goBackButton = (Button)findViewById(R.id.goBackButton);
		goBackButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goBack();
			}
		});
		
		
		saveNewCustomerButton = (Button)findViewById(R.id.saveNewCustomerButton);
		saveNewCustomerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveNewCustomerButtonOnClick(v);
			}
		});
		
	}
	
	
	@Override
	public void onBackPressed() {
			goBack();
	}
	
	

	protected void goBack() {
		Intent goBackIntent = new Intent(this , CrudCustomersActivity.class );
		startActivity(goBackIntent);
		finish();
	}

	protected void saveNewCustomerButtonOnClick(View v) {
		
		String nameCustomer = nameCustomerEditText.getText().toString();
		String comercialNameCustomer = comercialNameCustomerEditText.getText().toString();
		String cifCustomer = cifCustomerEditText.getText().toString();
		String addresCustomer = addressCustomerEditText.getText().toString();
		String phoneCustomer = phoneCustomerEditText.getText().toString();
		String observationCustomer = observationCustomerEditText.getText().toString();

		String error = "";
		
		if ( nameCustomer.equals("")){
			error = error + "Nombre del cliente ,";
		}if ( comercialNameCustomer.equals("")){
			error = error + "Nombre comercial ,";
		}if ( cifCustomer.equals("")){
			error = error + "Cif ,";
		}if ( phoneCustomer.equals("")){
			error = error + "Teléfono ,";
		}if ( addresCustomer.equals("")){
			error = error + "Dirección ,";
		}
		
		
		if ( error.equals("") ){
			
			
			if ( !editData){
			
				if ( customerFacade.create(nameCustomer , comercialNameCustomer , cifCustomer , addresCustomer , phoneCustomer , observationCustomer)){
					//DialogService.congratulationsDialog(this, "Cliente creado con éxito", "Correcto");
					onBackPressed();
				}else{
					DialogService.congratulationsDialog(this, "No se ha podido crear el cliente", "Error");
				}
			
			}else{
				if ( customerFacade.update(nameCustomer , comercialNameCustomer , cifCustomer , addresCustomer , phoneCustomer , observationCustomer , idCustomer )){
					//DialogService.congratulationsDialog(this, "Cliente modificado con éxito", "Correcto");
					onBackPressed();
					
				}else{
					DialogService.congratulationsDialog(this, "No se ha podido crear el cliente", "Error");
				}
			}
			
		}else{
			String errorToShow = error.substring(0 , error.length() -1);
			DialogService.errorDialog(this, "Debe rellenar los siguientes campos: " + errorToShow , "Campos por escribir");
		}
			
	}

	
}