package com.gabrielglez.cafeteria;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gabrielglez.cafeteria.adapter.ReplacementForNewRevisionAdapter;
import com.gabrielglez.cafeteria.drawing.DrawingView;
import com.gabrielglez.cafeteria.model.Replacement;
import com.gabrielglez.cafeteria.model.ReplacementNotEntity;
import com.gabrielglez.cafeteria.util.DataBaseHelperManager;
import com.gabrielglez.cafeteria.util.DialogService;
import com.gabrielglez.cafeteria.util.FontUtil;
import com.gabrielglez.services.facade.CheckSheetFacade;

public class CreateRevisionActivity extends Activity {

	private List<Replacement> replacementList;
	private List<ReplacementNotEntity> replacementNotEntityList;

	private Button goBackButton;
	private Button saveRevisionButton;

	private TextView nameCustomerTextView;
	private TextView comercialNameTextView;

	private TextView cifCustomerTextView;
	private TextView phoneCustomerTextView;
		
	private TextView operatorTextView;

	private Integer idCustomer;
	private ListView replacementListView;

	private CheckSheetFacade checkSheetFacade;
	public static CreateRevisionActivity createRevisionActivity;
	
	private String m_text;
	private DrawingView mDrawingView;
	
	private TextView titleActionNewRevisionTextView;
	private TextView titleActionToRevisionTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_create_revision);
		setTitle("Nueva revisión");
		
		createRevisionActivity = this;

		if (CustomerToCheckActivity.customerToCheckActivity != null) {
			CustomerToCheckActivity.customerToCheckActivity.finish();
		}

		initComponent();
		setOrientation();
		putCustomerDataInFields();
		createCanvas();
		
	}
	
	
	private void createCanvas(){
		// DRAW
		mDrawingView = new DrawingView( getBaseContext() );
		LinearLayout mDrawingPad = (LinearLayout) findViewById(R.id.singcustomerLinearLayout);
		mDrawingPad.addView(mDrawingView);
		// END DRAW
	}
	

	@Override
	protected void onStart() {
		super.onStart();
		loadReplacementFromBD();
	}
	
	private void setOrientation() {
	    
		int current = getRequestedOrientation();

	    if ( current != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ) {
	        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
	    }
	    
	}

	

	private void loadReplacementFromBD() {

		replacementList = DataBaseHelperManager.getReplacementDAO().getAllReplacementSelected();
		
		
		for ( Replacement replacement : replacementList ){
			
			ReplacementNotEntity replacementNotEntity = new ReplacementNotEntity();
			replacementNotEntity.setId(replacement.getId());
			replacementNotEntity.setDeleted(replacement.getDeleted());
			replacementNotEntity.setName(replacement.getName());
			replacementNotEntity.setObservation(replacement.getObservation());
			
			replacementNotEntityList.add(replacementNotEntity);
		}
		
		
		if (replacementList != null) {
			ReplacementForNewRevisionAdapter replacementAdapter = new ReplacementForNewRevisionAdapter(this, replacementNotEntityList );
			replacementListView.setAdapter(replacementAdapter);
		}
		
	}

	
	public void addObservationReplacement(final View view){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Observación");
		
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
		
		final int rowSelected = replacementListView.getPositionForView(view);
		final ReplacementForNewRevisionAdapter adapter = (ReplacementForNewRevisionAdapter) replacementListView.getAdapter();
		
		if ( adapter.getreplacementNotEntityList().get(rowSelected).getObservationInCheck() != null){
			input.setText(adapter.getreplacementNotEntityList().get(rowSelected).getObservationInCheck() );
		}
		
		
		builder.setView(input);
		builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
			
		    @Override
		    public void onClick(DialogInterface dialog, int which) {

		    	m_text = input.getText().toString();
		        adapter.getreplacementNotEntityList().get(rowSelected).setObservationInCheck(m_text);
		        m_text = "";
		    }
		});
		
		builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.cancel();
		    }
		});

		builder.show();
	}
	

	private void putCustomerDataInFields() {

		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			idCustomer = extras.getInt("id");
			nameCustomerTextView.setText(extras.getString("name"));
			comercialNameTextView.setText(extras.getString("comercialName"));
			cifCustomerTextView.setText(extras.getString("cif"));
			phoneCustomerTextView.setText(extras.getString("phone"));
		}
	}

	private void initComponent() {

		titleActionNewRevisionTextView = (TextView)findViewById(R.id.titleActionNewRevisionTextView);
		titleActionNewRevisionTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		titleActionNewRevisionTextView.setTextSize(60);
		
		
		titleActionToRevisionTextView = (TextView)findViewById(R.id.titleActionToRevisionTextView);
		titleActionToRevisionTextView.setTypeface(FontUtil.getSaginaBoldFont(this));
		titleActionToRevisionTextView.setTextSize(60);
		
		
		replacementNotEntityList = new ArrayList<ReplacementNotEntity>();
		
		checkSheetFacade = new CheckSheetFacade();

		nameCustomerTextView  = (TextView) findViewById(R.id.nameRevisionTextView);
		comercialNameTextView = (TextView) findViewById(R.id.comercialNameRevisionTextView);
		cifCustomerTextView   = (TextView) findViewById(R.id.cifRevisionTextView);
		operatorTextView      = (TextView) findViewById(R.id.operatorNameRevisionTextView);
		
		String user = DataBaseHelperManager.getOperatorDAO().getOperatorByUserName(MainActivity.userOnline).getName();
		operatorTextView.setText(user);
		
		phoneCustomerTextView = (TextView) findViewById(R.id.phoneRevisionTextView);
		replacementListView   = (ListView) findViewById(R.id.custormerToNewCheckListView);
		
		
		goBackButton = (Button) findViewById(R.id.goCrudRevisionBackButton);
		goBackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
				AlertDialog.Builder questionDialog = new AlertDialog.Builder(createRevisionActivity);
				questionDialog.setTitle("Confirmación");
				questionDialog.setMessage("¿Quiere volver atrás realmente?");
				questionDialog.setPositiveButton("Sí", new AlertDialog.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						goBack();
					}
					
				});
				
				questionDialog.setNegativeButton("No", new AlertDialog.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					   dialog.dismiss();
					}
					
				});
				
				questionDialog.show();
			}
				
		});

		saveRevisionButton = (Button) findViewById(R.id.saveNewRevisionButton);
		saveRevisionButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveNewRevision(v);
			}
		});
	}

	protected void saveNewRevision(View v) {

		AlertDialog.Builder questionDialog = new AlertDialog.Builder(this);
		questionDialog.setTitle("Confirmación");
		questionDialog.setMessage("¿La hoja de revisión está correcta ?");
		questionDialog.setPositiveButton("Sí",new DialogInterface.OnClickListener() {
		
			@Override
			public void onClick(DialogInterface dialog, int which) {
				saveCheckSheet();
			}
			
		});

		questionDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}

		});

		questionDialog.show();

	}

	private void saveCheckSheet() {
		Log.i("Salida", "En save checkset function");

		Bitmap customerOperatorSing = mDrawingView.getBitmap();
		
		ReplacementForNewRevisionAdapter listViewAdapterWithSelectedReplacements = (ReplacementForNewRevisionAdapter) replacementListView.getAdapter();
		
		if (checkSheetFacade.createCheckSheetRevision(this , customerOperatorSing , idCustomer , getReplacementsSelected(listViewAdapterWithSelectedReplacements.getreplacementNotEntityList() ))) {
			
			AlertDialog.Builder congratulationsDialog = new AlertDialog.Builder(this);
			congratulationsDialog.setTitle("Correcto");
			congratulationsDialog.setMessage("! Parte de revisión creado con éxito! ");
			congratulationsDialog.setPositiveButton("Aceptar", new AlertDialog.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					goBack();
				}
			});
			congratulationsDialog.show();
			
			
		} else {
			DialogService.errorDialog(this, "No se ha podido crear el parte de revisión","Error");
			goBack();
		}
		
	}

	
	
	private List<ReplacementNotEntity> getReplacementsSelected(List<ReplacementNotEntity> listReplacement){
		
		List<ReplacementNotEntity> listReplacementSelected = new ArrayList<ReplacementNotEntity>();
		
		for ( ReplacementNotEntity replacementSelected : listReplacement){
			
			if ( replacementSelected.isSelected() ){
				listReplacementSelected.add(replacementSelected);
			}
			
		}
		
		return listReplacementSelected;
		
	}
	
	
	
	@Override
	public void onBackPressed() {
		goBack();
	}

	protected void goBack() {
		Intent goBackIntent = new Intent(this, CustomerToCheckActivity.class);
		startActivity(goBackIntent);
		finish();
	}
}