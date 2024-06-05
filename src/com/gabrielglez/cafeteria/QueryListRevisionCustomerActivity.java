package com.gabrielglez.cafeteria;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.gabrielglez.cafeteria.adapter.RevisionListByCustomerQueryAdapter;
import com.gabrielglez.cafeteria.model.CheckSheet;
import com.gabrielglez.cafeteria.util.DataBaseHelperManager;
import com.gabrielglez.cafeteria.util.DateUtil;
import com.gabrielglez.cafeteria.util.FontUtil;

public class QueryListRevisionCustomerActivity extends Activity {
	
	private Button goBackButton;
	private ListView revisionListListView;
	
	private TextView customerNameTextView;
	private TextView customerComercialNameTextView;

	private TextView customerCifTextView;
	private TextView customerAddressTextView;
	
	private TextView customerPhoneTextView;
	private TextView operatorNameTextView;
	private TextView operatorUserNameTextView;
	
	private TextView dateCheckTextView;
	private TextView dateFinalIndividualCheck;
	
	private TextView titleQueryAllresultqueryTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		if ( QueryRevisionActivity.queryRevisionActivity != null ){
			QueryRevisionActivity.queryRevisionActivity.finish();
		}
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_list_revision_customer);
		setTitle("Revisiones");
		
		initComponent();
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
		loadCheckSheets();
	}
	
	
	private void loadCheckSheets() {

		Bundle extras = getIntent().getExtras();
		Date startDate = (Date)extras.get("startDate");
		Date endDate = (Date)extras.get("endDate");
		
		String customerName = extras.getString("customerName");
		String customerComercialName = extras.getString("customerComercialName");
		String customerCif = extras.getString("customerCif");
		String customerAddress = extras.getString("customerAddress"); 
		String customerPhone = extras.getString("customerPhone");
		String customerDeleted = extras.getString("customerDeleted");
		Integer customerId = extras.getInt("customerId");

		
		Integer operatorId = extras.getInt("operatorId");
		String operatorName = extras.getString("operatorName");
		String operatorUser = extras.getString("operatorUser");
		String operatorDni = extras.getString("operatorDni");
		
		
		boolean revisionSelected = true ; //extras.getBoolean("revisionSelected");
		
		operatorNameTextView.setText(operatorName);
		operatorUserNameTextView.setText(operatorUser);
		
		customerNameTextView.setText(customerName);
		customerComercialNameTextView.setText(customerComercialName);
		customerCifTextView.setText(customerCif);
		customerAddressTextView.setText(customerAddress);
		customerPhoneTextView.setText(customerPhone);
		dateCheckTextView.setText(DateUtil.getStringDateFromDate(startDate));
		dateFinalIndividualCheck.setText(DateUtil.getStringDateFromDate(endDate));
		
		List<CheckSheet> list = null;
		
		if ( extras.getBoolean("operatorSelected") == false ){
			list = DataBaseHelperManager.getCheckSheetDAO().getAllCheckSheetBetweenDatesByCustomer(customerId , revisionSelected , startDate , endDate);
			Log.i("Salida","Operador NO SELECCIONADO");
		}
		else if ( extras.getBoolean("operatorSelected") == true ){
			list = DataBaseHelperManager.getCheckSheetDAO().getAllCheckSheetBetweenDatesByCustomerAndOperator(customerId, operatorId  , revisionSelected , startDate, endDate);
			Log.i("Salida","Operador SELECCIONADO");
		}
		
		
		RevisionListByCustomerQueryAdapter revisionAdapter = new RevisionListByCustomerQueryAdapter(this , list);
		revisionListListView.setAdapter(revisionAdapter);
		
		
	}


	private void initComponent() {
		
		titleQueryAllresultqueryTextView = (TextView)findViewById(R.id.titleQueryAllresultqueryTextView);
		titleQueryAllresultqueryTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		titleQueryAllresultqueryTextView.setTextSize(50);
		
		customerNameTextView = (TextView)findViewById(R.id.customerNameIndividualCheck);
		customerComercialNameTextView = (TextView)findViewById(R.id.customerComercialNameIndividualCheck);

		customerCifTextView = (TextView)findViewById(R.id.customerCifIndividualCheck);
		customerAddressTextView = (TextView)findViewById(R.id.customerAddressIndividualCheck);
		
		customerPhoneTextView = (TextView)findViewById(R.id.customerPhoneIndividualCheck);
		operatorNameTextView = (TextView)findViewById(R.id.operatorNameIndividualCheck);
		
		operatorUserNameTextView = (TextView)findViewById(R.id.operatorUserNameIndividualCheck);
		dateCheckTextView = (TextView)findViewById(R.id.dateCheckIndividualCheck);
		
		dateFinalIndividualCheck = (TextView)findViewById(R.id.dateFinalIndividualCheck);
		revisionListListView = (ListView)findViewById(R.id.listIndividualByCustomerCheckedQueryListView);
		
		
		goBackButton = (Button)findViewById(R.id.goBackQueryCustomerBackButton);
		goBackButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				goBack();
			}
			
		});
	}

	@Override
	public void onBackPressed() {
			goBack();
	}

	protected void goBack() {
		Intent goBackIntent = new Intent(this , QueryRevisionActivity.class );
		startActivity(goBackIntent);
		finish();
	}
}