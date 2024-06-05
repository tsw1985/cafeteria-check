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

import com.gabrielglez.cafeteria.adapter.OperatorAdapter;
import com.gabrielglez.cafeteria.model.Operator;
import com.gabrielglez.cafeteria.util.DataBaseHelperManager;
import com.gabrielglez.cafeteria.util.DialogService;
import com.gabrielglez.cafeteria.util.FontUtil;

public class CrudOperatorActivity extends Activity {
	
	private ListView operatorListView;
	private Button newOperatorButton;
	private Button goOperatorBackButton;
	
	private List<Operator> operatorList;
	private ImageView editOperatorImageView;
	private ImageView deleteOperatorImageView;
	
	private TextView titleCrudOperatorTextView;
	
	public static CrudOperatorActivity crudOperatorActivity;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crud_operator);
		setTitle("Operarios");
		crudOperatorActivity = this;
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
		loadOperatorsFromBd();
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

	
	private void initComponent() {
		
		titleCrudOperatorTextView =(TextView)findViewById(R.id.titleCrudOperatorTextView);
		titleCrudOperatorTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		titleCrudOperatorTextView.setTextSize(60);
		
		operatorListView = (ListView)findViewById(R.id.operatorListView);
		newOperatorButton = (Button)findViewById(R.id.addOperatorButton);
		goOperatorBackButton = (Button)findViewById(R.id.goOperatorBackButton);
		editOperatorImageView = (ImageView)findViewById(R.id.editoperator);
		deleteOperatorImageView = (ImageView)findViewById(R.id.deleteoperator);
	}
	
	private void loadOperatorsFromBd() {
		
		operatorList = DataBaseHelperManager.getOperatorDAO().getAllOperatorNoDeleted();
		
		if ( operatorList != null){
			OperatorAdapter operatorAdapter = new OperatorAdapter(this, operatorList);
			operatorListView.setAdapter(operatorAdapter);
		}
	}

	private void initOnClickListener() {
		
		newOperatorButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showAddOperatorActivityOnClick(v);
				
			}
		});
		
		operatorListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view , int position, long id) {
				operatorListViewOnItemClick(parent,view,position,id);
			}
		});
		
		
		goOperatorBackButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goBackButton();
			}
		});
		
	}

	
	public void editOperator(View view){

		Operator operator = (Operator)operatorListView.getAdapter().getItem(operatorListView.getPositionForView(view));
		
		Intent editOperator = new Intent(this , NewOperatorDataForm.class);
		editOperator.putExtra("nameOperator", operator.getName() );
		editOperator.putExtra("dni", operator.getDni());
		editOperator.putExtra("userOperator",operator.getUser() );
		editOperator.putExtra("operatorPassword", operator.getPassword() );
		editOperator.putExtra("editdata", true );
		editOperator.putExtra("id", operator.getId() );
		startActivity(editOperator);
		
	}
	
	public void deleteOperator(View view){
		
		final Operator operator = (Operator)operatorListView.getAdapter().getItem( operatorListView.getPositionForView(view));
		
		
		if ( ! operator.getUser().equals("root") ){
			
		AlertDialog.Builder questionDialog = new AlertDialog.Builder(this);
		questionDialog.setTitle("Confirmación");
		questionDialog.setMessage("¿Realmente quiere eliminar el operario " + operator.getName() + " ?");

		questionDialog.setPositiveButton("Sí", new android.content.DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				
				if ( DataBaseHelperManager.getOperatorDAO().delete( operator.getId())){
					messageOperatorDeleted();
					loadOperatorsFromBd();
				}else{
					messageOperatorDeletedError();
				}
			}
		});
		
		questionDialog.setNegativeButton("NO", new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		questionDialog.show();
		
		}else{
			DialogService.errorDialog(this , "! No puede eliminar el usuario root del sistema !", "Error!");
		}
		
	}
	
	private void messageOperatorDeleted(){
		DialogService.congratulationsDialog(this, "Operador eliminado con éxito", "Correcto");
	}
	
	private void messageOperatorDeletedError(){
		DialogService.congratulationsDialog(this, "No se ha podido eliminar el operador", "Error");
	}
	
	protected void operatorListViewOnItemClick(AdapterView<?> parent,View view, int position, long id) {
		
	}

	protected void showAddOperatorActivityOnClick(View v) {
		Intent addOperatorActivity = new Intent(this, NewOperatorDataForm.class);
		startActivity(addOperatorActivity);
		
	}


}