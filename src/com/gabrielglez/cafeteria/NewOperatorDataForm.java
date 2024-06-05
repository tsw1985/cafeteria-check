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

import com.gabrielglez.cafeteria.model.Operator;
import com.gabrielglez.cafeteria.util.DataBaseHelperManager;
import com.gabrielglez.cafeteria.util.DialogService;
import com.gabrielglez.cafeteria.util.FontUtil;
import com.gabrielglez.services.facade.OperatorFacade;

public class NewOperatorDataForm extends Activity {

	
	private Button saveNewOperatorButton ;
	private Button goCrudOperatorBackButton;
	
	private EditText nameOperatorEditText;
	private EditText cifOperatorEditText;
	
	private EditText userOperatorEditText;
	private EditText passwordOperatorEditText;
	
	
	private OperatorFacade OperatorFacade;
	private TextView titleActionTextView;
	
	
	private int idOperator;
	protected static NewOperatorDataForm newOperatorDataForm;
	private boolean editData = false;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_operator_data_form);
		setTitle("Datos de operario");
		
		newOperatorDataForm= this;
		if ( CrudOperatorActivity.crudOperatorActivity != null)
			CrudOperatorActivity.crudOperatorActivity.finish();
		
		initComponent();
		putOperatorDataAndChangeActionTitle();
		setOrientation();
	}
	
	private void setOrientation() {
	    
		int current = getRequestedOrientation();
	    if ( current != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ) {
	        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
	    }
	}



	private void putOperatorDataAndChangeActionTitle() {
		
		Bundle extras = getIntent().getExtras();
		
		if ( extras != null ){
			setTitle("Editar Operario");
			nameOperatorEditText.setText(extras.getString("nameOperator"));
			cifOperatorEditText.setText(extras.getString("dni"));
			userOperatorEditText.setText(extras.getString("userOperator"));
			passwordOperatorEditText.setText(extras.getString("operatorPassword"));
			editData = extras.getBoolean("editdata");
			idOperator = extras.getInt("id");
			titleActionTextView.setText("Editar operario");
			editData = true;
		}
	}


	private void initComponent() {

		OperatorFacade = new  OperatorFacade();
		nameOperatorEditText = (EditText)findViewById(R.id.nameOperatoreditText);
		cifOperatorEditText = (EditText)findViewById(R.id.cifOperatorEditText);
		userOperatorEditText = (EditText)findViewById(R.id.usernameOperatorEditText);
		passwordOperatorEditText = (EditText)findViewById(R.id.passwordOperatorEditText);
		titleActionTextView = (TextView)findViewById(R.id.titleActionOperatorTextView);
		titleActionTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		titleActionTextView.setTextSize(70);
		
		
		goCrudOperatorBackButton = (Button)findViewById(R.id.goCrudOperatorBackButton);
		goCrudOperatorBackButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goBack();
			}
		});
		
		
		saveNewOperatorButton = (Button)findViewById(R.id.saveNewOperatorButton);
		saveNewOperatorButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveNewCustomerButtonOnClick(v);
			}
		});
	}

	
	
	
	
	protected void saveNewCustomerButtonOnClick(View v) {
		
		
		String nameOperator     = nameOperatorEditText.getText().toString();
		String cifOperator      = cifOperatorEditText.getText().toString();
		String userNameOperator = userOperatorEditText.getText().toString();
		String passwordOperator = passwordOperatorEditText.getText().toString();

		String error = "";
		
		if ( nameOperator.equals("")){
			error = error + "Nombre del operador ,";
		}if ( cifOperator.equals("")){
			error = error + "Cif ,";
		}if ( userNameOperator.equals("")){
			error = error + "Nombre de usuario ,";
		}if ( passwordOperator.equals("")){
			error = error + "Password ,";
		}
		
		
		if ( error.equals("") ){
			
			//add new operator
			if ( !editData){
			
				if ( ! userNameOperator.equals("root")){
				
					if ( DataBaseHelperManager.getOperatorDAO().getOperatorByUserName(userNameOperator) == null ){
					
						if ( OperatorFacade.create(nameOperator , cifOperator , userNameOperator , passwordOperator )){
							onBackPressed();
						}else{
							DialogService.congratulationsDialog(this, "No se ha podido crear el operador", "Error");
						}
					
					}else{
						DialogService.errorDialog(this, "Ese nombre de usuario ya existe, elija otro. ", "Error");
					}
					
				}else{
					DialogService.errorDialog(this, "No puede crear otro usuario root ", "Error");
				}
				
			
			}else{
				
				//edit operator
				
				if ( ! userNameOperator.equals("root")   ){
					
					Operator actualOperator = DataBaseHelperManager.getOperatorDAO().getOperatorByUserName(userNameOperator); 
					
					if ( actualOperator != null ){
					
						if (  ! actualOperator.getPassword().equals(passwordOperator)){
						
							if ( OperatorFacade.update(nameOperator , cifOperator , userNameOperator , passwordOperator , idOperator )){
								onBackPressed();
							}else{
								DialogService.congratulationsDialog(this, "No se ha podido modificar el operador", "Error");
							}
						
						}else{
							DialogService.errorDialog(this, "Ese nombre de usuario ya existe, elija otro password si quiere guardar los cambios u otro nombre de usuario.", "Error");
						}
					
					}else{
						
							if ( OperatorFacade.update(nameOperator , cifOperator , userNameOperator , passwordOperator , idOperator )){
								onBackPressed();
							}else{
								DialogService.congratulationsDialog(this, "No se ha podido modificar el operador", "Error");
							}
				   }
					
				}else{
					DialogService.congratulationsDialog(this, "No puede crear otro usuario root", "Error");
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
		Intent goBackIntent = new Intent(this , CrudOperatorActivity.class );
		startActivity(goBackIntent);
		finish();
	}
}