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

import com.gabrielglez.cafeteria.model.Customer;
import com.gabrielglez.cafeteria.model.Operator;
import com.gabrielglez.cafeteria.model.Replacement;
import com.gabrielglez.cafeteria.util.DataBaseHelper;
import com.gabrielglez.cafeteria.util.DataBaseHelperManager;
import com.gabrielglez.cafeteria.util.DialogService;
import com.gabrielglez.cafeteria.util.FontUtil;

public class MainActivity extends Activity {
	
	private Button enterButton;
	private EditText userNameEditText;
	private EditText passwordEditText;

	private TextView varaniniTitleTextView;
	private TextView userTextView;
	private TextView passuserTextView;
	private TextView footertitleTextVaraniniFooter;
	
	public static DataBaseHelper bd ;
	
	public static String userOnline ="";
	
	public static MainActivity mainActivity;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("Login");
		
		initComponent();
		initClickListeners();
		bd = new DataBaseHelper(this);
		new DataBaseHelperManager(bd);
		
		createRoot();
		//addData();
		setOrientation();
	}


	private void setOrientation() {
		int current = getRequestedOrientation();
	    if ( current != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ) {
	        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
	    }
	}

	
	private void initClickListeners() {
		enterButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showMainWindowActivity();
			}
		});
		
	}


	protected void showMainWindowActivity() {
		
		String user = userNameEditText.getText().toString();
		String password =passwordEditText.getText().toString();
		
		Operator operator = DataBaseHelperManager.getOperatorDAO().operatorLogin(user, password); 
		
		if ( operator != null ){
			userOnline = operator.getUser();
			
			Intent mainWindowActivityIntent = new Intent(this,MainWindowActivity.class);	
			
			startActivity(mainWindowActivityIntent);
		}else{
			DialogService.errorDialog(this, "Usuario o password incorrecto !!", "Login");
		}
		
		
		

	}


	private void initComponent() {
		
		mainActivity = this;
		
		varaniniTitleTextView = (TextView)findViewById(R.id.titleTextVaranini);
		varaniniTitleTextView.setTextSize(100);
		varaniniTitleTextView.setTypeface(FontUtil.getSaginaBoldFont(this));

		passuserTextView = (TextView)findViewById(R.id.passusertextview);
		passuserTextView.setTextSize(30);
		
		passuserTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		
		userTextView = (TextView)findViewById(R.id.userTextView);
		userTextView.setTextSize(30);
		userTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		
		
		footertitleTextVaraniniFooter = (TextView)findViewById(R.id.footertitleTextVaraniniFooter);
		footertitleTextVaraniniFooter.setTextSize(30);
		footertitleTextVaraniniFooter.setTypeface(FontUtil.getSaginaBoldFont(this));
		
		
		enterButton = (Button)findViewById(R.id.enterButton);
		userNameEditText = (EditText)findViewById(R.id.usernameEditText);
		userNameEditText.clearFocus();
		
		passwordEditText = (EditText)findViewById(R.id.passwordEditText);
	}
	
	
	
	
	
	private void createRoot(){
		
		
		Operator op = DataBaseHelperManager.getOperatorDAO().getOperatorByUserName("root");
		
		if ( op == null ){
		
			Operator operator = new Operator();
			operator.setName("Root");
			operator.setDeleted("NO");
			operator.setDni("00000DS");
			operator.setUser("root");
			operator.setPassword("1234");
			
			DataBaseHelperManager.getOperatorDAO().create(operator);
		
		}
	}
	
	
	private void addData(){
		
		Customer cliente = new Customer();
		cliente.setAddress("Chio");
		cliente.setName("Luciano González Martin");
		cliente.setComercialName("Bar El Molino");
		cliente.setCif("4254874H");
		cliente.setDeleted("NO");
		cliente.setObservation("NADA");
		cliente.setPhone("922852222");
		DataBaseHelperManager.getCustomerDAO().create(cliente);
		
		
		Customer clienteDos = new Customer();
		clienteDos.setAddress("Chio");
		clienteDos.setName("Gustavo Hernandez");
		clienteDos.setComercialName("Bar El Pozo");
		clienteDos.setCif("12345K");
		clienteDos.setDeleted("NO");
		clienteDos.setObservation("NADA");
		clienteDos.setPhone("922851998");
		
		
		DataBaseHelperManager.getCustomerDAO().create(clienteDos);
		
		
		Replacement repuesto1 = new Replacement("Tornillo","Bien","NO");
		Replacement repuesto2 = new Replacement("Cable","Mal","NO");
		Replacement repuesto3 = new Replacement("Cristal","Viejo","NO");

		
		for (int i = 0 ; i < 50 ; i++){
			Replacement repuesto = new Replacement("Cristal"+i,"Viejo"+i,"NO");
			DataBaseHelperManager.getReplacementDAO().create(repuesto);
		}
		

		//DataBaseHelperManager.getReplacementDAO().create(repuesto1);
		//DataBaseHelperManager.getReplacementDAO().create(repuesto2);
		//DataBaseHelperManager.getReplacementDAO().create(repuesto3);
		
		
		Operator gabriel = new Operator();
		gabriel.setName("Gabriel");
		gabriel.setDni("45788741");
		gabriel.setUser("root");
		gabriel.setDeleted("NO");
		gabriel.setUser("root");
		gabriel.setPassword("1234");
		DataBaseHelperManager.getOperatorDAO().create(gabriel);
		
		
		Operator juan = new Operator();
		juan.setName("Juan");
		juan.setDni("5434334");
		juan.setDeleted("NO");
		juan.setUser("jjuani");
		juan.setPassword("1234");
		DataBaseHelperManager.getOperatorDAO().create(juan);
		
		
		/*
		CheckSheet hoja = new CheckSheet();
		hoja.setOperator(gabriel);
		hoja.setDate(new Date());
		hoja.setCustomer(clienteDos);
		DataBaseHelperManager.getCheckSheetDAO().create(hoja);
		
		CheckSheetReplacement checkRepla1 = new CheckSheetReplacement(hoja , repuesto1);
		CheckSheetReplacement checkRepla2 = new CheckSheetReplacement(hoja , repuesto2);
		CheckSheetReplacement checkRepla3 = new CheckSheetReplacement(hoja , repuesto3);
		
		DataBaseHelperManager.getCheckSheetReplacementDAO().create(checkRepla1);
		DataBaseHelperManager.getCheckSheetReplacementDAO().create(checkRepla2);
		DataBaseHelperManager.getCheckSheetReplacementDAO().create(checkRepla3);
		
		hoja.getCheckSheetReplacementList().add(checkRepla1);
		hoja.getCheckSheetReplacementList().add(checkRepla2);
		hoja.getCheckSheetReplacementList().add(checkRepla3);
		
		
		List<CheckSheet> list = DataBaseHelperManager.getCheckSheetDAO().getAllCheckSheet();

			for ( CheckSheet checkSheet : list){
				Log.i("Salida" , "nombre cliente -> "  + checkSheet.getCustomer().getName() );
				
				for ( CheckSheetReplacement checkSheetReplacement : checkSheet.getCheckSheetReplacementList() ){
					Log.i("Salida" , "Repuesto -> " + 
					DataBaseHelperManager.getReplacementDAO().get( checkSheetReplacement.getReplacement().getId() ).getName()  );
				}
			}
			*/
			
		}
		
		
}