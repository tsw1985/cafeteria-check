package com.gabrielglez.cafeteria.util;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class DialogService {
	
	private static boolean option; 
	
	
	public static void showInformationDialog(Context context, String message , String title){
		
		AlertDialog.Builder infoDialog = new AlertDialog.Builder(context);
		infoDialog.setTitle(title);
		infoDialog.setMessage(message);
		infoDialog.setPositiveButton("Ok", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		infoDialog.show();
	}
	
	public static void congratulationsDialog(Context context , String message, String title){
		AlertDialog.Builder congratulationsDialog = new AlertDialog.Builder(context);
		congratulationsDialog.setTitle(title);
		congratulationsDialog.setMessage(message);
		congratulationsDialog.setIcon(R.drawable.ic_dialog_info);
		congratulationsDialog.setPositiveButton("Ok", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		congratulationsDialog.show();
	}
	
	public static void errorDialog(Context context , String message, String title){
		AlertDialog.Builder congratulationsDialog = new AlertDialog.Builder(context);
		congratulationsDialog.setTitle(title);
		congratulationsDialog.setMessage(message);
		congratulationsDialog.setIcon(R.drawable.ic_lock_idle_alarm);
		congratulationsDialog.setPositiveButton("Ok", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		congratulationsDialog.show();
	}
	
	public static boolean confirmationYES_NO_Dialog(final Activity context, String message, String title){
		
		AlertDialog.Builder questionDialog = new AlertDialog.Builder(context);
		questionDialog.setTitle(title);
		questionDialog.setMessage(message);
		questionDialog.setIcon(R.drawable.ic_lock_idle_alarm);
		questionDialog.setPositiveButton("Sí", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				yes();
				dialog.dismiss();
			}
			
			public void yes(){
				option = true;
			}
			
		});
		
		questionDialog.setNegativeButton("No", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			   dialog.dismiss();
			   no();
			}
			
			public void no(){
				option = false;
			}
		});
		
		questionDialog.show();
		return option;
		
	}
}