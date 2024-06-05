package com.gabrielglez.cafeteria.util;

import android.content.Context;
import android.widget.Toast;

public class ToastService {
	
	public static void  messageToast(Context context,String text, int duration){
		Toast.makeText(context, text , duration).show();
	}		
	

}
