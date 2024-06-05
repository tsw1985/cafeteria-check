package com.gabrielglez.cafeteria.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static String getStringActualMonth(){
		
		Calendar calendar  = Calendar.getInstance();
		calendar.setTime( new Date() );
		int month = calendar.get(Calendar.MONTH);
		String actualStringMonth = "";
		
		switch (month) {
		
		case 0:
			actualStringMonth = ("Enero");
			break;
		case 1:
			actualStringMonth = ("Febrero");
			break;
		case 2:
			actualStringMonth = ("Marzo");
			break;
		case 3:
			actualStringMonth = ("Abril");
			break;
		case 4:
			actualStringMonth = ("Mayo");
			break;
		case 5:
			actualStringMonth = ("Junio");
			break;
		case 6:
			actualStringMonth = ("Julio");
			break;
		case 7:
			actualStringMonth = ("Agosto");
			break;
		case 8:
			actualStringMonth = ("Septiembre");
			break;
		case 9:
			actualStringMonth = ("Octubre");
			break;
		case 10:
			actualStringMonth = ("Noviembre");
			break;
		case 11:
			actualStringMonth = ("Diciembre");
			break;
		}
		return actualStringMonth;
	}

	
	public static String getStringDateFromDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		return sdf.format(date);
	}
	
	
	
	
	public static String getStringMonthByDate(Date date){
		
		Calendar calendar  = Calendar.getInstance();
		calendar.setTime( date );
		int month = calendar.get(Calendar.MONTH);
		String actualStringMonth = "";
		
		switch (month) {
		
		case 0:
			actualStringMonth = ("ENERO");
			break;
		case 1:
			actualStringMonth = ("FEBRERO");
			break;
		case 2:
			actualStringMonth = ("MARZO");
			break;
		case 3:
			actualStringMonth = ("ABRIL");
			break;
		case 4:
			actualStringMonth = ("MAYO");
			break;
		case 5:
			actualStringMonth = ("JUNIO");
			break;
		case 6:
			actualStringMonth = ("JULIO");
			break;
		case 7:
			actualStringMonth = ("AGOSTO");
			break;
		case 8:
			actualStringMonth = ("SEPTIEMBRE");
			break;
		case 9:
			actualStringMonth = ("OCTUBRE");
			break;
		case 10:
			actualStringMonth = ("NOVIEMBRE");
			break;
		case 11:
			actualStringMonth = ("DICIEMBRE");
			break;
		}
		return actualStringMonth;
		
		
	}
	
	
	
	
	
	
	
}
