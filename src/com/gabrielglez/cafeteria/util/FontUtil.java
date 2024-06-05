package com.gabrielglez.cafeteria.util;

import android.app.Activity;
import android.graphics.Typeface;

public class FontUtil {
	

	public static Typeface getSaginaBoldFont(Activity activity){
		Typeface font = Typeface.createFromAsset(activity.getAssets() , "saginawbold.ttf");
		return font;
	}
	
	public static Typeface getSaginaLightFont(Activity activity){
		Typeface font = Typeface.createFromAsset(activity.getAssets() , "saginawlight.ttf");
		return font;
	}
	
	public static Typeface getSaginaMediumFont(Activity activity){
		Typeface font = Typeface.createFromAsset(activity.getAssets() , "saginawmedium.ttf");
		return font;
	}
	
}
