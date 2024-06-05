package com.gabrielglez.services.facade;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.gabrielglez.cafeteria.MainActivity;
import com.gabrielglez.cafeteria.model.CheckSheet;
import com.gabrielglez.cafeteria.model.CheckSheetReplacement;
import com.gabrielglez.cafeteria.model.Customer;
import com.gabrielglez.cafeteria.model.ReplacementNotEntity;
import com.gabrielglez.cafeteria.util.DataBaseHelperManager;
import com.gabrielglez.cafeteria.util.DialogService;

public class CheckSheetFacade {
	
	public static Bitmap prueba;
	
	
public boolean createCheckSheetRevision(Activity activity , Bitmap bitmap , Integer idCustomer , List<ReplacementNotEntity> replacementList ){
		
	try{
	
		prueba = bitmap;
		
		CheckSheet checkSheet = new CheckSheet();
		checkSheet.setDate(new Date());
		checkSheet.setOperator( DataBaseHelperManager.getOperatorDAO().getOperatorByUserName(MainActivity.userOnline));
		
		int lastCheckSheetId = DataBaseHelperManager.getCheckSheetDAO().getLastIdCheckSheetReplacement();
		Log.i("Salida" , "Ultimo id -----------------------> " + lastCheckSheetId );
		checkSheet.setId(lastCheckSheetId + 1);
		
		
		Customer customer = DataBaseHelperManager.getCustomerDAO().get(idCustomer);
		checkSheet.setCustomer(customer);
		
		for ( ReplacementNotEntity replacement : replacementList){
			
			CheckSheetReplacement checkSheetReplacement = new CheckSheetReplacement();
			checkSheetReplacement.setObservationInCheck( replacement.getObservationInCheck() );
			checkSheetReplacement.setCheckSheet(checkSheet);
			checkSheetReplacement.setReplacement(replacement);
			
			DataBaseHelperManager.getCheckSheetReplacementDAO().create(checkSheetReplacement);
			checkSheet.getCheckSheetReplacementList().add(checkSheetReplacement);
			
			Log.i("Salida","Repuestos elegidos " + replacement.getName() + " Anotacion " + replacement.getObservationInCheck());
			
		}
		
		saveImageSing(activity , bitmap , String.valueOf(lastCheckSheetId +1 ));
		
		//-------------------------------------------------------------------------------------
		DataBaseHelperManager.getCheckSheetDAO().create(checkSheet);	
		Log.i("Salida", "Hoja creada");
	
	
		return true;
	
		}catch(Exception ex){
			Log.e("SQL", "Error al crear hoja " + ex.toString());
			ex.getStackTrace();
			return false;
		}
	
	}


private void saveImageSing(Activity activity , Bitmap bitmap , String fileName ) {
	 
	 try {
		 
		 String state = Environment.getExternalStorageState();
		  
		 if ( state.equals(Environment.MEDIA_MOUNTED ))
		 {
			 String filename = Environment.getExternalStorageDirectory().toString();
			 File f = new File(filename ,fileName + ".png");
			 f.createNewFile();
			 Log.v("draw","Imagen guardada");
			 FileOutputStream out = new FileOutputStream(f);
			 Bitmap bitmapSave = bitmap;
			 bitmapSave.compress(Bitmap.CompressFormat.PNG, 100, out);
			 out.close();
			 
		 }
		 else if ( state.equals(Environment.MEDIA_MOUNTED_READ_ONLY ))
		 {
		    DialogService.errorDialog( activity , "La tarjeta solo está montada como solo lectura. No se puede escribir en ella." , "Error al leer tarjeta SD");
		 }
		 else
		 {
			 DialogService.errorDialog( activity , "Ha habido un error al acceder a la tarjeta o la tablet no dispone de SD Card.", "Error con tarjeta SD");
		 }
		
	
	  } catch (Exception e) {
		  Log.v("draw", "Error al crear imagen " + e.toString() );
	  }
	
}




}