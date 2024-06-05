package com.gabrielglez.cafeteria.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gabrielglez.cafeteria.R;
import com.gabrielglez.cafeteria.ShowCheckSheetActivity;
import com.gabrielglez.cafeteria.model.CheckSheet;
import com.gabrielglez.cafeteria.util.DataBaseHelperManager;
import com.gabrielglez.cafeteria.util.DateUtil;

public class RevisionListByCustomerQueryAdapter extends BaseAdapter{
	
	private Activity activity;
	private List<CheckSheet> checkSheetList;
	

	public RevisionListByCustomerQueryAdapter(Activity activity, List<CheckSheet> replacementNotEntityList) {
		this.activity = activity;
		this.checkSheetList = replacementNotEntityList;
	}

	@Override
	public int getCount() {
		return checkSheetList.size();
	}

	@Override
	public Object getItem(int position) {
		return (CheckSheet)checkSheetList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return checkSheetList.get(position).getId();
	}

	@Override
	public View getView(final int position, final View convertView, ViewGroup parent) {

		final CheckSheet checksheet = checkSheetList.get(position);
		
		LayoutInflater inflater = activity.getLayoutInflater();
		final View item = inflater.inflate(R.layout.listrevisionbycustomerquery_layout, null);
		
		//TextView customerNameTextView = (TextView)item.findViewById(R.id.customerQueryName);
		//customerNameTextView.setText( checksheet.getCustomer().getName());
        
        TextView comercialNameCustomer = (TextView) item.findViewById(R.id.customerQueryComercialName);
        comercialNameCustomer.setText( checksheet.getCustomer().getComercialName() );

        TextView dateTextView = (TextView) item.findViewById(R.id.customerQueryDateChecked);
        dateTextView.setText( DateUtil.getStringDateFromDate( checksheet.getDate() ) );
        
        
        //********************************************************************
        //** Queda preparado para cuando haga los pdf, solo descomentar el checkbox
        //** aqui y en el xml de la lista
        //********************************************************************
        //final CheckBox checkedCheckBox = (CheckBox)item.findViewById(R.id.takepdfcheckbox);

        //---------------------------------------
        //Accedo al contenedor padre del check box para poder acceder al boton de al lado y deshabilitarlo
  		//ViewParent viewParent = (LinearLayout)checkedCheckBox.getParent();
  		
  		//Lo casteo a View para poder buscarlo por id 
  		//final View viewButton = (View) viewParent;
        
        
        /*checkedCheckBox.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				Log.d("Check","Pulsado -> " + position + " Soy " + checksheet.getCustomer().getName() );
				checksheet.setSelected(checkedCheckBox.isChecked());
			}
			
        });*/
        
        //checkedCheckBox.setChecked(checksheet.isSelected());
        
       
        Button openCheekSheet = (Button)item.findViewById(R.id.openCheekSheetButton);
        openCheekSheet.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Log.d("Check","Pulsado Abrir -> " + position + " Soy " + checksheet.getCustomer().getName() );
				Log.i("Salida" , "Valor de idCheckSheet -> " + checksheet.getId());
				showCheckSheetActivity(v , checksheet.getId() );
			
			}
        	
        });
        
        
        return item;
        
	}

	protected void showCheckSheetActivity(View v , int idCheckSheet) {
		
		Intent showOneCheckSheet = new Intent(activity , ShowCheckSheetActivity.class);
		showOneCheckSheet.putExtra("idCheckSheet", idCheckSheet );
		activity.startActivity(showOneCheckSheet);
		
	}

	public List<CheckSheet> getCheckSheetList() {
		return checkSheetList;
	}

	public void setCheckSheetList(List<CheckSheet> checkSheetList) {
		this.checkSheetList = checkSheetList;
	}
	
	
	
	
	
}