package com.urqa.Collector;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateCollector {
	
	public static String GetDateYYMMDDHHMMSS(Context context){
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
	     Date currentLocalTime = cal.getTime();
	     DateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	     date.setTimeZone(TimeZone.getTimeZone("UTC")); 
	     String localTime = date.format(currentLocalTime); 
	     return localTime;
	     
	     /*
		java.util.Date currenttime = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss",  context.getResources( ).getConfiguration( ).locale);
		String time = formatter.format(currenttime);
		return time;
		*/
	}
}
