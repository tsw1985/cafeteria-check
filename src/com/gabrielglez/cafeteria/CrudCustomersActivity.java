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

import com.gabrielglez.cafeteria.adapter.CustomerAdapter;
import com.gabrielglez.cafeteria.model.Customer;
import com.gabrielglez.cafeteria.util.DataBaseHelperManager;
import com.gabrielglez.cafeteria.util.DialogService;
import com.gabrielglez.cafeteria.util.FontUtil;

public class CrudCustomersActivity extends Activity {
	
	private ListView customerListView;
	private Button newCustomerButton;
	private Button crudcustomergobackButton;
	
	private List<Customer> customerList;
	private ImageView editCustomerImageView;
	private ImageView deleteCustomerImageView;
	
	private TextView customersTextView;
	
	protected static CrudCustomersActivity crudCustomersActivity;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crud_customers);
		setTitle("Clientes");
		crudCustomersActivity = this;
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

	
	@Override
	protected void onStart() {
		super.onStart();
		loadCustomersFromBD();
	}
	

	private void initOnClickListener() {
		
		//Button new Customer
		newCustomerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showAddCustomerActivityOnClick(v);
			}
		});
		
		//ItemClick , al pulsar sobre la lista de clientes
		customerListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				//customerListViewOnItemClick(parent,view,position,id);
			}
		});
		
		crudcustomergobackButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goBackButton();
			}
			
		});
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
	
	public void editCustomer(View view){
		Customer customer = (Customer)customerListView.getAdapter().getItem( customerListView.getPositionForView(view));
		
		Intent editCustomerIntent = new Intent(this,NewCustomerDataForm.class);
		editCustomerIntent.putExtra("nameCustomer", customer.getName() );
		editCustomerIntent.putExtra("comercialNameCuster", customer.getComercialName());
		editCustomerIntent.putExtra("cifCustomer", customer.getCif());
		editCustomerIntent.putExtra("addressCustomer", customer.getAddress() );
		editCustomerIntent.putExtra("phoneCustomer", customer.getPhone() );
		editCustomerIntent.putExtra("observationCustomer", customer.getObservation() );
		editCustomerIntent.putExtra("deleted", customer.getDeleted() );
		editCustomerIntent.putExtra("id", customer.getId() );
		editCustomerIntent.putExtra("editdata", true );
		
		startActivity(editCustomerIntent);
	}
	
	
	public void deleteCustomer(View view){
		
		final Customer customer = (Customer)customerListView.getAdapter().getItem( customerListView.getPositionForView(view));
		
		AlertDialog.Builder questionDialog = new AlertDialog.Builder(this);
		questionDialog.setTitle("Confirmación");
		questionDialog.setMessage("¿Realmente quiere eliminar el cliente " + customer.getName() + "?");

		questionDialog.setPositiveButton("Sí", new android.content.DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				
				if ( DataBaseHelperManager.getCustomerDAO().delete(customer.getId())){
					messageCustomerDeleted();
					loadCustomersFromBD();
				}else{
					messageCustomerDeletedError();
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
	
	
	private void messageCustomerDeleted(){
		DialogService.congratulationsDialog(this, "Cliente eliminado con éxito", "Correcto");
	}
	
	private void messageCustomerDeletedError(){
		DialogService.congratulationsDialog(this, "No se ha podido eliminar el cliente", "Error");
	}
	

	
	

	private void initComponent() {
		
		customersTextView = (TextView)findViewById(R.id.customersTextView);
		customersTextView.setTextSize(60);
		customersTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		
		editCustomerImageView = (ImageView)findViewById(R.id.editcustomer);
		deleteCustomerImageView = (ImageView)findViewById(R.id.deletecustomer);
		
		customerListView = (ListView)findViewById(R.id.custormerListView);
		newCustomerButton = (Button)findViewById(R.id.addCustomerButton);
		crudcustomergobackButton = (Button)findViewById(R.id.crudcustomergobackButton);
		
	}

	
	private void loadCustomersFromBD(){
		
		customerList = DataBaseHelperManager.getCustomerDAO().getAllCustomerNoDeleted();
		
		if ( customerList != null){
			CustomerAdapter customerAdapter = new CustomerAdapter(this, customerList);
			customerListView.setAdapter(customerAdapter);	
		}
	}
	
	
	/*protected void customerListViewOnItemClick(AdapterView<?> parent,View view, int position, long id) {
		Customer customer = (Customer)customerListView.getAdapter().getItem( customerListView.getPositionForView(view));
	}*/


	protected void showAddCustomerActivityOnClick(View v) {
		Intent addCustomerActivity = new Intent(this, NewCustomerDataForm.class);
		startActivity(addCustomerActivity);
	}
}