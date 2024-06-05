package com.gabrielglez.cafeteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.gabrielglez.cafeteria.adapter.CustomerForQueryCheckSheetAdapter;
import com.gabrielglez.cafeteria.model.Customer;
import com.gabrielglez.cafeteria.model.Operator;
import com.gabrielglez.cafeteria.util.DataBaseHelperManager;
import com.gabrielglez.cafeteria.util.DateUtil;
import com.gabrielglez.cafeteria.util.DialogService;
import com.gabrielglez.cafeteria.util.FontUtil;

public class QueryRevisionActivity extends Activity {
	
	private List<Customer> customerList;
	private ListView customerCheckedListView;
	
	private DatePicker fromDatePicker;
	private DatePicker toDatePicker;
	
	//private LinearLayout optionQuerylinearLayout;
	private RadioButton customeCheckedRadioButton;
	private RadioButton customerNoCheckedRadioButton;
	
	private Spinner operatorSpinner;
	
	private Button filterCustomerButton;
	private Button filterCustomerToQueryCheckButton;
	private Button goBackButton;
	
	
	private EditText customerNameEditText;
	
	
	private CheckBox activateOperatorCheckBox;
	private CheckBox showDeletedCustomerCheckbox;
	
	public static QueryRevisionActivity queryRevisionActivity;
	
	private TextView titleQueryTextView;
	private TextView fromQueryTextView;
	private TextView operatorQueryTextView;
	private TextView toDateQueryTextView;
	private TextView titleQueryCustomerTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_revision);
		setTitle("Consultar revisiones");
		
		queryRevisionActivity = this;
		
		if ( MainWindowActivity.mainWindowActivity != null){
			MainWindowActivity.mainWindowActivity.finish();
		}
		
		initComponent();
		//loadAllCustomerFromBDByName();
		setOrientation();
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Date startDate = getDateFromDatePicker(fromDatePicker, 0, 0, 0);
		Date endDate = getDateFromDatePicker(toDatePicker, 23, 59, 59);
		getCustomerByCheckedNotChecked("nochecked" , startDate , endDate , null ,  null);
	}

	
	private void setOrientation() {
	    
		int current = getRequestedOrientation();

	    if ( current != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ) {
	        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
	    }
	    
	}

	private void initComponent() {
		
		titleQueryTextView = (TextView)findViewById(R.id.titleQueryTextView);
		titleQueryTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		titleQueryTextView.setTextSize(60);
		
		fromQueryTextView = (TextView)findViewById(R.id.fromQueryTextView);
		fromQueryTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		fromQueryTextView.setTextSize(40);
		
		operatorQueryTextView = (TextView)findViewById(R.id.operatorQueryTextView);
		operatorQueryTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		operatorQueryTextView.setTextSize(40);
		
		toDateQueryTextView = (TextView)findViewById(R.id.toDateQueryTextView);
		toDateQueryTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		toDateQueryTextView.setTextSize(40);
		
		titleQueryCustomerTextView = (TextView)findViewById(R.id.titleQueryCustomerTextView);
		titleQueryCustomerTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		titleQueryCustomerTextView.setTextSize(50);
		
		showDeletedCustomerCheckbox = (CheckBox)findViewById(R.id.showDeletedCustomerCheckbox);
		activateOperatorCheckBox = (CheckBox)findViewById(R.id.activateOperatorCheckBox);
		activateOperatorCheckBox.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				if ( activateOperatorCheckBox.isChecked() ){ 
					operatorSpinner.setEnabled(true);
				}else{
					operatorSpinner.setEnabled(false);
				}
					
			}
			
		});
		
		customerNameEditText = (EditText)findViewById(R.id.filterCustomerQueryEditText);
		
		customerCheckedListView = (ListView)findViewById(R.id.listCustomerCheckedQueryListView);
		fromDatePicker = (DatePicker)findViewById(R.id.fromdateDatePicker);
		toDatePicker   = (DatePicker)findViewById(R.id.todateDatePicker);
	
		customeCheckedRadioButton    = (RadioButton)findViewById(R.id.customerQueryCheckedRadio);
		customerNoCheckedRadioButton = (RadioButton)findViewById(R.id.customerQueryNoCheckedRadio);
		customerNoCheckedRadioButton.setChecked(true);
		
		//listar clientes que tienen o no revision
		filterCustomerToQueryCheckButton = (Button)findViewById(R.id.searchcustomerbyradiooptionbutton);
		filterCustomerToQueryCheckButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				String option = "";
				if ( customeCheckedRadioButton.isChecked()){
					option ="checked";
				}else if ( customerNoCheckedRadioButton.isChecked()){
					option ="nochecked";
				}
				
				Date startDate = getDateFromDatePicker(fromDatePicker, 0, 0, 0);
				Date endDate = getDateFromDatePicker(toDatePicker, 23, 59, 59);
				
				if ( startDate.getTime() > endDate.getTime()){
					DialogService.errorDialog(v.getContext(), "La fecha inicial no puede ser mayor que la fecha final", "Error");
				}else{
					
					Operator operator = null ;		
					
					if ( activateOperatorCheckBox.isChecked() ){
						operator = (Operator)operatorSpinner.getSelectedItem();
					}
					
					getCustomerByCheckedNotChecked(option , startDate , endDate , operator ,  v);
				}
				
			}
		});
		
		
		
		operatorSpinner = (Spinner)findViewById(R.id.operatorCustomerCheckedSpinner);
		operatorSpinner.setEnabled(false);
		
		loadOperatorIntoSpinner();
		
		operatorSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
			
		});

		
		customerCheckedListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view , int position, long id) {
				
				Operator operator = (Operator)operatorSpinner.getSelectedItem();
				
				Date startDate = getDateFromDatePicker(fromDatePicker , 0 , 0 , 0);
				Date endDate =   getDateFromDatePicker(toDatePicker , 23 , 59 , 0); 
				
				if ( startDate.getTime() > endDate.getTime() ){
					DialogService.errorDialog(view.getContext(), "La fecha inicial no puede ser mayor que la fecha final", "Error");
				}else{
					
					String startDateS = DateUtil.getStringDateFromDate(startDate);
					String endDateS = DateUtil.getStringDateFromDate(endDate);
					
					Customer customer = (Customer)customerCheckedListView.getAdapter().getItem( customerCheckedListView.getPositionForView(view) );
	
					Log.i("Salida","Fecha start -> " + startDateS + " En dates " + endDateS);
					Log.i("Salida","Soy operator listview ->" + operator.getName() );
					Log.i("Salida","Customer listview ->" + customer.getName() );
					
					boolean revisionOption = false;
					
					/*if ( customeCheckedRadioButton.isSelected() ){
						revisionOption = true;
					}else if ( customerNoCheckedRadioButton.isSelected() ){
						revisionOption = false;
					} */
					
					
					Log.i("Salida","CHECKBOX STATE -> " + activateOperatorCheckBox.isChecked() );
					getRevisions(customer , operator , revisionOption , activateOperatorCheckBox.isChecked() ,startDate , endDate );
				}
			}
			
		});
		
		
		
		//filtrar clientes por nombre
		filterCustomerButton = (Button)findViewById(R.id.filterCustomerToQueryCheckButton);
		filterCustomerButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				filterCustomerListButton(v);
			}
			
		});
		
		goBackButton = (Button)findViewById(R.id.goMainWindowQueryBackButton);
		goBackButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				goBack();
			}
			
		});
	}
	
	//muestro los clientes revisados o no en la lista listview
	protected void getCustomerByCheckedNotChecked(String option , Date startDate, Date endDate, Operator operator ,  View v) {
		
		customerList = DataBaseHelperManager.getCustomerDAO().getAllCustomerCheckedNotCheckedByOperator( option, operator, startDate, endDate);
		
		if (customerList != null ){
			CustomerForQueryCheckSheetAdapter adapter = new CustomerForQueryCheckSheetAdapter(this , removeDuplicates(customerList));
			customerCheckedListView.setAdapter(adapter);
		}
		
	}

	
	private List<Customer> removeDuplicates(List<Customer> listToRemoveDuplicates){
        LinkedHashSet<Customer> listToSet = new LinkedHashSet<Customer>(listToRemoveDuplicates);
        List<Customer> listWithoutDuplicates = new ArrayList<Customer>(listToSet);
        listToRemoveDuplicates.clear();
        listToRemoveDuplicates.addAll(listWithoutDuplicates);
        return listToRemoveDuplicates;
	}
	
	
	

	private void getRevisions(Customer customer, Operator operator, boolean revision , boolean operatorActivated , Date startDate, Date endDate){
		
		Intent activityQueryListIntent = new Intent( this , QueryListRevisionCustomerActivity.class );
		activityQueryListIntent.putExtra("startDate" , startDate);
		activityQueryListIntent.putExtra("endDate"   , endDate);
		activityQueryListIntent.putExtra("customerName" , customer.getName());
		activityQueryListIntent.putExtra("customerComercialName", customer.getComercialName());
		activityQueryListIntent.putExtra("customerCif" , customer.getCif() );
		activityQueryListIntent.putExtra("customerAddress" , customer.getAddress()  );
		activityQueryListIntent.putExtra("customerPhone" , customer.getPhone()  );
		activityQueryListIntent.putExtra("customerDeleted" , customer.getDeleted() );
		activityQueryListIntent.putExtra("customerId" , customer.getId() );

	
		if ( operator != null ){
			activityQueryListIntent.putExtra("operatorId"  , operator.getId());
			activityQueryListIntent.putExtra("operatorName", operator.getName());
			activityQueryListIntent.putExtra("operatorUser", operator.getUser());
			activityQueryListIntent.putExtra("operatorDni" , operator.getDni());
		}
			
		activityQueryListIntent.putExtra("operatorSelected" , operatorActivated );
		activityQueryListIntent.putExtra("revisionSelected" , revision );
		startActivity(activityQueryListIntent);
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

	private Date getDateFromDatePicker(DatePicker datePicker , int hour , int minute , int second){
	
		int day   = datePicker.getDayOfMonth();
		int month = datePicker.getMonth();
		int year  = datePicker.getYear();
		
		Date date = new GregorianCalendar(year , month , day , hour , minute , second ).getTime();
		
		return date;
	}
	
	private void loadOperatorIntoSpinner(){
		List<Operator> operatorList = DataBaseHelperManager.getOperatorDAO().getAllOperatorNoDeleted();
		ArrayAdapter<Operator> operatorDataAdapter = new ArrayAdapter<Operator>( this , android.R.layout.simple_spinner_dropdown_item ,operatorList );
		operatorSpinner.setAdapter(operatorDataAdapter);
	}

	protected void filterCustomerListButton(View v) {
		
		String customerName = customerNameEditText.getText().toString();

		if ( showDeletedCustomerCheckbox.isChecked() ){
			customerList = DataBaseHelperManager.getCustomerDAO().getAllCustomerByComercialName(customerName , "YES");
		}else{
			customerList = DataBaseHelperManager.getCustomerDAO().getAllCustomerByComercialName(customerName , "NO");
		}
		
		if ( customerList != null){
			CustomerForQueryCheckSheetAdapter adapter = new CustomerForQueryCheckSheetAdapter(this ,customerList);
			customerCheckedListView.setAdapter(adapter);
		}
	}
}