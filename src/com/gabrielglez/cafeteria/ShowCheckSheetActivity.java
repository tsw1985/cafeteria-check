package com.gabrielglez.cafeteria;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gabrielglez.cafeteria.adapter.ReplacementForRevisedTemplateAdapter;
import com.gabrielglez.cafeteria.model.CheckSheetReplacement;
import com.gabrielglez.cafeteria.model.Customer;
import com.gabrielglez.cafeteria.model.Operator;
import com.gabrielglez.cafeteria.model.Replacement;
import com.gabrielglez.cafeteria.util.DataBaseHelperManager;
import com.gabrielglez.cafeteria.util.FontUtil;

public class ShowCheckSheetActivity extends Activity {

	private TextView customerNameRevisedTextView;
	private TextView customercomercialNameRevisedTextView;
	private TextView customerCifRevisedTextView;
	private TextView customerPhoneRevisedTextView;
	private TextView operatorNameRevisedTextView;
	private TextView titleCheckSheetCustomerTextView;
	private TextView replacementCheckSheetRevisedTextView;
	
	private ListView replacementRevisedListView;
	private LinearLayout singcustomerrevisedLinearLayout;
	
	private Button goShowCheckSheetBackButton;
	
	private int idCheckSheet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_check_sheet);
		setTitle("Revisión");

		initComponent();
		loadReplacementList();
		setCustomerData();
		setOrientation();
	
	}

	private void setOrientation() {
		int current = getRequestedOrientation();
	    if ( current != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ) {
	        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
	    }
	}


	
	private void drawCustomerOperatorSing(String fileName) {
		
		String rootFolder = Environment.getExternalStorageDirectory().toString();
		File imageSing = new File(rootFolder , fileName );
		Drawable draw = BitmapDrawable.createFromPath(imageSing.getAbsolutePath());
		singcustomerrevisedLinearLayout.setBackground(draw);
	}



	private void setCustomerData() {

		Bundle extras = getIntent().getExtras();
		if( extras != null){
		
			int idCheckSheet = extras.getInt("idCheckSheet");
			Customer customer = DataBaseHelperManager.getCheckSheetDAO().get(idCheckSheet).getCustomer();
			customerNameRevisedTextView.setText(customer.getName());
			customercomercialNameRevisedTextView.setText(customer.getComercialName());
			customerCifRevisedTextView.setText(customer.getCif());
			customerPhoneRevisedTextView.setText(customer.getPhone());
			
			Operator operator = DataBaseHelperManager.getCheckSheetDAO().get(idCheckSheet).getOperator();
			operatorNameRevisedTextView.setText(operator.getName());
			
			
			String fileName = String.valueOf(idCheckSheet + ".png");
			drawCustomerOperatorSing(fileName);
			
		}
	}


	private void loadReplacementList() {
		
		Collection<CheckSheetReplacement> checkSheetList =  DataBaseHelperManager.getCheckSheetReplacementDAO().getAllCheckSheetByIdCheckSheet(idCheckSheet);
		List<Replacement> replacementListUsed = new ArrayList<Replacement>();
		
		for ( final CheckSheetReplacement checkSheetReplacement : checkSheetList ){

			Replacement replacement = DataBaseHelperManager.getReplacementDAO().get( checkSheetReplacement.getReplacement().getId() );
			Log.i("Salida","Observacion -----> " + checkSheetReplacement.getObservationInCheck() );
			replacement.setObservationInCheck(checkSheetReplacement.getObservationInCheck());
			
			replacementListUsed.add(replacement);

		}
		
		ReplacementForRevisedTemplateAdapter replacementRevisedSelected = new ReplacementForRevisedTemplateAdapter(this , replacementListUsed );
		replacementRevisedListView.setAdapter(replacementRevisedSelected);
		
	}


	private void initComponent() {

		Bundle extras = getIntent().getExtras();
		idCheckSheet = extras.getInt("idCheckSheet");
		
		
		titleCheckSheetCustomerTextView = (TextView)findViewById(R.id.titleCheckSheetCustomerTextView);
		titleCheckSheetCustomerTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		titleCheckSheetCustomerTextView.setTextSize(45);
		
		replacementCheckSheetRevisedTextView = (TextView)findViewById(R.id.replacementCheckSheetRevisedTextView);
		replacementCheckSheetRevisedTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		replacementCheckSheetRevisedTextView.setTextSize(45);
		
		customercomercialNameRevisedTextView = (TextView)findViewById(R.id.comercialNameRevisedTextView);
		customerNameRevisedTextView  = (TextView)findViewById(R.id.customerNameRevisedTextView);
		customerCifRevisedTextView   = (TextView)findViewById(R.id.cifRevisedTextView);
		customerPhoneRevisedTextView = (TextView)findViewById(R.id.phoneRevisedTextView);
		operatorNameRevisedTextView  = (TextView)findViewById(R.id.operatorNameRevisedTextView);
		replacementRevisedListView   = (ListView)findViewById(R.id.custormerReplacementRevisedListView);
		singcustomerrevisedLinearLayout = (LinearLayout)findViewById(R.id.singcustomerrevisedLinearLayout);
		
		goShowCheckSheetBackButton = (Button)findViewById(R.id.goShowCheckSheetBackButton);
		goShowCheckSheetBackButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}